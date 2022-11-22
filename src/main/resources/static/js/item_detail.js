/**
 * 
 */
'use strict';
$(function () {
	calc_price();
	$('.size').on('change', function () {
		calc_price();
	});

	$('.checkbox').on('change', function () {
		calc_price();
	});

	$('#itemnum').on('change', function () {
		calc_price();
	});

	// 値段の計算をして変更する関数
	function calc_price() {
		let size = $('.size:checked').val();
		let option_count = $('input[class=checkbox]:checked').length;
		let item_num = $('#itemnum option:selected').val();
		let size_price = 0;
		let option_price = 0;

		
//		let vals = $('span[name=optionName]').map(function(){
//	    		return $(this).text();
//    	}).get();

//		// valueがoption.priceのときの計算方法
//		let vals = $('input[class=checkbox]:checked').map(function(){
//      		return Number($(this).val());
//    	}).get();
//		for(let val of vals){
//			option_price += val; 
//		}
		
		option_price = 300 * option_count; 
		
		if (size == 'S') {
			size_price = $('#priceS').text();
		} else if (size == 'M') {
			size_price = $('#priceM').text();
		} else {
			size_price = $('#priceL').text();
		}
		let price = size_price * item_num + option_price * item_num;
		$('#totalprice').text(price.toLocaleString());
	}
	;
});