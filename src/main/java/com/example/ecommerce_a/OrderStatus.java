package com.example.ecommerce_a;

public enum OrderStatus {
	   BEFOREORDER(0, "ご注文前"),
	   WAITINGPAYMENT(1, "未入金"),
	   SENT(2, "ご発送済"),
	   COMPLETED(3, "お届け済"),
	   CANCELED(9, "キャンセル済");
	   ;

	   private final int value;
	   private final String viewName;

	   private OrderStatus(int value, String viewName) {
	       this.value = value;
	       this.viewName = viewName;
	   }

	   public int getValue() {
	       return this.value;
	   }

	   public String getViewName() {
	       return this.viewName;
	   }

}
