package com.example.ecommerce_a.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderConfirmForm;
import com.example.ecommerce_a.service.OrderItemService;
import com.example.ecommerce_a.service.OrderService;
import com.example.ecommerce_a.service.ShoppingCartService;
import com.example.ecommerce_a.service.UserService;

@Controller
@RequestMapping("/shop")
public class OrderController {
	@Autowired
	private HttpSession session;

	@Autowired
	private ShoppingCartService shoppingCartService ;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private UserService userService;

	
	
	@ModelAttribute
	public OrderConfirmForm setUpForm() {
		
		OrderConfirmForm form = new OrderConfirmForm();
		
		if (session.getAttribute("user") != null) {
			User user = (User)session.getAttribute("user");
			form.setDestinationName(user.getName());
			form.setDestinationEmail(user.getEmail());
			form.setDestinationZipcode(user.getZipcode());
			form.setDestinationAddress(user.getAddress());
			form.setDestinationTel(user.getTelephone());	
			
			
		}
		
		return form;
//		return new OrderConfirmForm();
	}

	@RequestMapping("/order-confirm")
	public String orderConfirm() {
		return "order_confirm_pointUsable";
	}

	@RequestMapping("/orderConfirm")
		public String orderConfirm(Model model) {
			System.out.println(session.getAttribute("user"));
			
	
			if (session.getAttribute("user") != null) {
				// Order order = orderService.findById();
				Order order = new Order();
				order.setId(((User)session.getAttribute("user")).getId());
				order.setOrderItemList(orderItemService.orderConfirm(order.getId()));
				List<OrderItem> orderItemList = order.getOrderItemList();
				//******
				session.setAttribute("orderItemList", orderItemList);
	
				//ここからポイント使用機能
				User user = (User)session.getAttribute("user");
				Order shoppingCart = (Order) session.getAttribute("shoppingCart");
				
				int totalPrice = shoppingCart.getCalcTotalPrice();
				System.out.println("totalprice"+totalPrice);
				int NowPoint = userService.getPoint(user.getId());
				List<Integer> usablePointList = new ArrayList<>();
	
				for(int i =0 ; i<=NowPoint && i <=totalPrice; i += 100){
					usablePointList.add(i);
				}
	
				System.out.println(usablePointList);
				//******
				model.addAttribute("usablePointList", usablePointList);
				
				
				return "order_confirm_pointUsable";
				
				//ここまでポイント使用機能
				
	//			return "order_confirm_pointUsable";
			} else {
				session.setAttribute("beforeLogin", "orderconfirm");
				return "redirect:/shop/login";
			}
		}

	/**
	 * 注文確認画面より注文情報を受け取り注文完了する 登録情報に不備があった場合、再度注文確認画面を表示させる
	 * 
	 * @param form
	 * @param result
	 * @param model
	 * @return 注文完了画面を表示させるorderFinished()メソッドに遷移させる
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/order-confirmed")
	public String completeOrder(@Validated OrderConfirmForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return orderConfirm(model);
			
		}
		
		User user = (User) session.getAttribute("user"); 
		
		Order order = shoppingCartService.getShoppingCartOf(user);
		
		order.setOrderDate(new Date());
		
		
		order.setStatus(1);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
	
		Date formDate = form.getDeliveryDateAsDate();
		LocalTime formTime = form.getDeliveryTimeAsLocalTime();
		System.out.println("formTime:"+formTime);
		
		LocalDateTime localDateTime = LocalDateTime.of(1900 + formDate.getYear(), 1 + formDate.getMonth(), formDate.getDate(), formTime.getHour(), formTime.getMinute());
		
		//
		
		// 取得した時間が、現時点から3時間後以前の場合はエラー
		//deliveryTimeError "order_confirm_pointUsable" 231
		LocalDateTime threeHourAfterNow = LocalDateTime.now().plusHours(3);
		if (localDateTime.isBefore(threeHourAfterNow)) {
			model.addAttribute("deliveryTimeError","現在より3時間後以降の日時をご入力ください");
			return orderConfirm(model);
		}
		//
		
		
		System.out.println("注文日時:"+localDateTime);
		System.out.println(formDate.getYear());
		order.setDeliveryTime(Timestamp.valueOf(localDateTime));
		order.setPaymentMethod(form.getPaymentMethod());
		
		if(form.getPaymentMethod()==2) {
			order.setStatus(2);
		}else {
			order.setStatus(1);
		}
		
		
		
		order = orderService.completeOrder(order);

		model.addAttribute("order", order);
		//オーダー内容をsessionで持ってくる→クレカで支払い設定→いける
		
		// ここからポイント使用システム
		
				Integer usedPoint = form.getUsePointAsInteger();
				if(usedPoint != 0) {
				User nowUser = (User) session.getAttribute("user");
				User pointUsedUser = userService.updatePoint(nowUser, -1*usedPoint);
				session.setAttribute("user", pointUsedUser);
				}
				

		// ここからポイント付与システム
		// 1.現状のユーザー情報をセッションからもらってくる
		// 2.サービスを使って、DBのポイントを増やす
		// 3.セッションにポイントが更新されたユーザー情報を再度格納する。
		User nowUser = (User) session.getAttribute("user");
		Integer point = (int)((order.getTotalPrice()-usedPoint)*0.1);
		User pointAddUser = userService.updatePoint(nowUser, point);
		session.setAttribute("user", pointAddUser);
		
		return "redirect:/shop/order-finished";
	}
	

	/**
	 * @return 注文完了画面を表示させる
	 */
	@RequestMapping("/order-finished")
	public String orderFinished() {
		return "order_finished";
	}

	@RequestMapping("/show_order_history")
	public String showOrderHistory(Model model) {
		//　ログインしていないときには使えないのでnullチェックなし。
		User user =  (User)session.getAttribute("user");
		List<Order> orderHistory = orderService.showOrderHistoryOf(user);  
		
		
		if(orderHistory == null) {
		} else {
			System.out.println("オーダーサイズ:" + orderHistory.size());
			
			if(orderHistory.size() == 0) {
				model.addAttribute("zeroMessage","ご注文履歴はありません。");
				
			} else {
				for(Order order:orderHistory) {
					System.out.println("order:" + order);
				};
				
				model.addAttribute("orderHistory", orderHistory);
				
			}
		}
		
		return "order_history";
	}
	
}
