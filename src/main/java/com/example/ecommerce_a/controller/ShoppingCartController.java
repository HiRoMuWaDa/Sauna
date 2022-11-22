package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.ShoppingCartService;

@Controller
@RequestMapping("/shop/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/view")
	public String cartView(Model model) {

		Order shoppingCart = new Order();

		if (session.getAttribute("user") != null) {
			// ユーザーがログインしている場合
			// ユーザーのカートがあるかないか確認して、持ってくる
			User user = (User) session.getAttribute("user");
			shoppingCart = shoppingCartService.getShoppingCartOf(user);

			if (shoppingCart == null) {
				// まだ注文前オーダーがないとき
				// リクエストスコープに、メッセージを格納する。
				model.addAttribute("nullMessage", "お客様のカートに商品はありません。");
			} else {
				if (shoppingCart.getOrderItemList().size() == 0) {
					// 注文前オーダーはあるけど商品が入ってない時
					// リクエストスコープに、メッセージを格納する。
					model.addAttribute("nullMessage", "お客様のカートに商品はありません。");
				} else {
					// sessionスコープに、現状ショッピングカートに入っている商品のリストを格納する
					session.setAttribute("shoppingCart", shoppingCart);
					// 現状使用しているオーダードメインのtotalPriceを現状に変更する
					shoppingCartService.updateTotalPriceOf(shoppingCart);
				}
			}
		} else {
			// ユーザーがログインしていない場合
			// セッションからショッピングカートの中身を持ってきて、
			shoppingCart = (Order) session.getAttribute("shoppingCart");
			if (shoppingCart == null || shoppingCart.getOrderItemList().size() == 0) {
				// リクエストスコープに、メッセージを格納する。
				model.addAttribute("nullMessage", "お客様のカートに商品はありません。");
			} else {
				// sessionスコープに、現状ショッピングカートに入っている商品のリストを格納する
				session.setAttribute("shoppingCart", shoppingCart);
			}
		}

		return "cart_list";
	}

	@RequestMapping("/insert")
	public String cartInsert(OrderForm orderForm, RedirectAttributes redirectAttributes) {

		// 1.フォームから送られてきた情報のインスタンス化
		// 2.ログイン状態に合わせて、情報の処理
		// 3.現状のカートをセッションスコープに格納し、画面遷移

		// 1.フォームから送られてきた情報のインスタンス化
		// ショッピングカートとそれに含むitemListをインスタンス化
		Order shoppingCart = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
		shoppingCart.setOrderItemList(orderItemList);

		// orderItemとorderOptionをインスタンス化
		OrderItem orderItem = new OrderItem();
		
		// ここでitemIdとQuantityとsizeが移るイメージ
		BeanUtils.copyProperties(orderForm, orderItem);

		// ItemIdをもとにItemの情報をロードする
		orderItem.setItem(shoppingCartService.getItemInfo(orderForm.getItemId()));

		// オーダーオプションリストがnullにならないように空のリストを挿入しておく。
		orderItem.setOrderOptionList(new ArrayList<OrderOption>());

		// オーダーオプションがあるならオーダーオプションをインスタンス化。リストを作って入れていく。
		if (orderForm.getOptionIdList() != null) {
			// オーダーオプションに、optionidとoption情報を入れ、OrderOptionListに格納
			for (Integer optionId : orderForm.getOptionIdList()) {
				OrderOption orderOption = new OrderOption();
				orderOption.setOptionId(optionId);
				orderOption.setOption(shoppingCartService.getOptionInfo(optionId));
				orderItem.getOrderOptionList().add(orderOption);
			}
		}

		// 2.ログイン状態に合わせて、情報の処理

		if (session.getAttribute("user") != null) {
			// ユーザーがログインしている場合
			// OrderItemのorderIdと、OrderOptionのOrderItemIdを入れる必要あり。

			// ユーザーのカートがあるかないか確認して、持ってくる
			User user = (User) session.getAttribute("user");
			shoppingCart = shoppingCartService.getShoppingCartOf(user);

			// ユーザーのカートがなければ、新しく生成してデータベースにインサート。
			if (shoppingCart == null) {
				shoppingCart = new Order();
				shoppingCart.setUserId(user.getId());
				shoppingCart.setId(shoppingCartService.createNewShoppingCart(shoppingCart));
			}

			// データベース挿入用に、OrderItemにorderIdを追加。
			orderItem.setOrderId(shoppingCart.getId());
//			System.out.println("orderitemのorderid:"+orderItem.getOrderId());

			// orderItemを挿入しつつ、OrderItemIdをもらう
			int generatedOrderItemId = shoppingCartService.insertOrderItem(orderItem);

			// orderOptionListを呼び、orderOptionにorderItemIdをセットしながら、データベースに挿入
			for (OrderOption orderOption : orderItem.getOrderOptionList()) {
				orderOption.setOrderItemId(generatedOrderItemId);
				shoppingCartService.insertOrderOption(orderOption);
			}

		} else {
			// ユーザーがログインしていない場合
			
			//　セッションのカートがあれば、持ってくる
			if (session.getAttribute("shoppingCart") != null) {
				shoppingCart = (Order) session.getAttribute("shoppingCart");
			}
			
			// shoppingCartのorderItemListに、OrderItemを足す
			shoppingCart.getOrderItemList().add(orderItem);

		}

		// 3.現状のカートをセッションスコープに格納し、画面遷移

		// sessionスコープに、現状ショッピングカートに入っている商品のリストを格納する
		session.setAttribute("shoppingCart", shoppingCart);

		redirectAttributes.addFlashAttribute("insertedMessage", "カートに商品を追加しました。");
		return "redirect:/shop/cart/view";
	}

	@RequestMapping("/delete")
	public String cartdelete(String id, String index, RedirectAttributes redirectAttributes) {
		// ログイン状況チェック
		if (session.getAttribute("user") != null) {
			// ログインしていればデータベースを対応
			// データベースの削除
			// このidはorderItemIdのこと
			shoppingCartService.deleteItemOf(Integer.parseInt(id));

		} else {
			// ログインしていなければセッションから持ってきて、セッションの中身を更新する
			Order shoppingCart = (Order) session.getAttribute("shoppingCart");
			shoppingCart.getOrderItemList().remove(Integer.parseInt(index));

			session.setAttribute("shoppingCart", shoppingCart);
		}

		redirectAttributes.addFlashAttribute("deletedMessage", "カートに商品を削除しました。");
		return "redirect:/shop/cart/view";

	}

}
