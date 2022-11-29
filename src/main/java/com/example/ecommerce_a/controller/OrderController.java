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
		//OrderConfimの値にuserの値をセットformで返す
		
		return form;
//		return new OrderConfirmForm();
	}

	@RequestMapping("/order-confirm")
	public String orderConfirm() {
		return "order_confirm_pointUsable";
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
		//エラーが出たら戻される
		//formにはuserの情報がある		
		User user = (User) session.getAttribute("user"); 
		//user情報の取得
		
		Order order = shoppingCartService.getShoppingCartOf(user);
		//ショッピングカートからオーダー内容を取得
		order.setOrderDate(new Date());
		//オーダーに日付をセット
		order.setStatus(1);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
	//orderにformのデータをセットする
		Date formDate = form.getDeliveryDateAsDate();
		//getDeliveryDateAsDate→OrderconfimFormから　配達予定日の取得
		LocalTime formTime = form.getDeliveryTimeAsLocalTime();
		System.out.println("formTime:"+formTime);
		
		LocalDateTime localDateTime = LocalDateTime.of(1900 + formDate.getYear(), 1 + formDate.getMonth(), formDate.getDate(), formTime.getHour(), formTime.getMinute());
		//deliveryTImeASLocalTime→配達時間
		//LocalDateTime~→カレンダー
		
		
		// 取得した時間が、現時点から3時間後以前の場合はエラー
		LocalDateTime threeHourAfterNow = LocalDateTime.now().plusHours(3);
		if (localDateTime.isBefore(threeHourAfterNow)) {
			model.addAttribute("deliveryTimeError","現在より3時間後以降の日時をご入力ください");
			return orderConfirm(model);
		}
		//return orderConfirm→三時間以内ならエラーして下にあるorderConfirmに遷移
		
		
		System.out.println("注文日時:"+localDateTime);
		System.out.println(formDate.getYear());
		order.setDeliveryTime(Timestamp.valueOf(localDateTime));
		order.setPaymentMethod(form.getPaymentMethod());
		//PaymentMethod→支払方法
		if(form.getPaymentMethod()==2) {
			order.setStatus(2);
		}else {
			order.setStatus(1);
		}
		//status→合計金額　引数１が代引き　２がクレジット	
		
		order = orderService.completeOrder(order);

//sqlが入って戻ってくる、orderに追加される
	
	
		model.addAttribute("order", order);
		//リクエストｽｺｰﾌﾟに追加
		
		
		//テスト候補→リクエストのorderにsqlが入っているか確認する
		//前提条件→ログイン、CSVで注文データ追加
		
		// ここからポイント使用システム
		/*
		 * Pointがある場合→sessionにuserデータを入れる、使う場合はuseduserを使う
		 * 
		 */
		
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
	//注文完了画面の表示、returnで商品一覧へ
	

	@RequestMapping("/orderConfirm")
	public String orderConfirm(Model model) {
		System.out.println(session.getAttribute("user"));
		///rakurakusauna/shop/orderConfirm→注文確認画面
		//System.out.println(session.getAttribute("user"));→userデータのコンソール表示
		

		if (session.getAttribute("user") != null) {
		
			// Order order = orderService.findById();
			Order order = new Order();
			order.setId(((User)session.getAttribute("user")).getId());
			order.setOrderItemList(orderItemService.orderConfirm(order.getId()));
			List<OrderItem> orderItemList = order.getOrderItemList();//←選ばれた注文データが入っている
			session.setAttribute("orderItemList", orderItemList);
			System.out.println(orderItemList);
			//１．userがいる場合、session.getAttribute("user")).getId());からidの取得しorder.setId(((User)にセット、買う人のデータ
			//２．setOrderItemListにorderItemService.orderConfirm(order.getId()))から注文データをもらいセットする
			//３．List<OrderItem> orderItemList →先ほどセットした商品を orderItemListに追加して一つの注文内容にする
			//４．注文内容をsessionに入れる
			//５．リスト型でデータがとられているはず
         
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
			model.addAttribute("usablePointList", usablePointList);
			
			
			return "order_confirm_pointUsable";
			
			//ここまでポイント使用機能
			
//			return "order_confirm_pointUsable";
		} else {
			session.setAttribute("beforeLogin", "orderconfirm");
			return "redirect:/shop/login";
		}
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
