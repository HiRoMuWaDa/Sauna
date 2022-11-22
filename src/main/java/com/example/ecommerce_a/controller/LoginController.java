package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.LoginForm;
import com.example.ecommerce_a.service.LoginService;
import com.example.ecommerce_a.service.ShoppingCartService;

@Controller
@RequestMapping("/shop")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	///////////////////////////////////////////////////
	// ログイン設定 //
	///////////////////////////////////////////////////

	/**
	 * ログイン画面を表示するメソッド return login.html
	 */
	@RequestMapping("/login")
	public String toLogin() {

		return "login";
	}

	@RequestMapping("/login-result")
	public String login(LoginForm form, Model model) {
		User user = loginService.login(form.getEmail(), form.getPassword());

		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}

		session.setAttribute("user", user);
		session.setAttribute("name", user.getName()+"さんこんにちは！");
		System.out.println(user);
		session.setAttribute("name", user.getName() + "さんこんにちは！");

		// logIn前にセッションに入れていたカートの内容をinsert処理したい。
		// ユーザーのセッションからカートの中身を持ってくる
		Order unLoginedCart = (Order) (session.getAttribute("shoppingCart"));
		if (unLoginedCart != null) {

			// ユーザーのカートがあるかないか確認して、持ってくる
			Order shoppingCart = shoppingCartService.getShoppingCartOf(user);

			// ユーザーのカートがなければ、新しく生成してデータベースにインサート。
			if (shoppingCart == null) {
				shoppingCart = new Order();
				shoppingCart.setUserId(user.getId());
				shoppingCart.setId(shoppingCartService.createNewShoppingCart(shoppingCart));
			}

			// 非ログイン時のカートの中身のorderitemにデータベースのorderId(カートの通し番号)を振りつつ挿入していく
			for (OrderItem orderItem : unLoginedCart.getOrderItemList()) {
				orderItem.setOrderId(shoppingCart.getId());
				int generatedOrderItemId = shoppingCartService.insertOrderItem(orderItem);

				// その過程で、出てきたorderItemIdをorderOptionに振りつつ、挿入していく
				for (OrderOption orderOption : orderItem.getOrderOptionList()) {
					orderOption.setOrderItemId(generatedOrderItemId);
					shoppingCartService.insertOrderOption(orderOption);
				}
			}
		}

		if ((session.getAttribute("beforeLogin")) != null) {
			if (((String) session.getAttribute("beforeLogin")).equals("orderconfirm")) {
//				session.setAttribute("beforeLogin", "");
				session.removeAttribute("beforeLogin");
				return "redirect:/shop/orderConfirm";
			}
			if (((String) session.getAttribute("beforeLogin")).equals("sauna")) {
//				session.setAttribute("beforeLogin", "");
				session.removeAttribute("beforeLogin");
				return "redirect:/research";
			}

		}
		return "redirect:/shop";
	}

	///////////////////////////////////////////////////
	// ログアウト設定 //
	///////////////////////////////////////////////////

	/**
	 * ログアウト用のメソッドを定義
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/shop";
	}

}
