/**
 * 
 */
'use strict';
$(function () {
	calc_payment();
	$('#usePoint').on('change', function () {
		calc_payment();
	});

	// 値段の計算をして変更する関数
	function calc_payment() {
		let totalPrice = $('#totalPrice').val();
		let usePoint = 0;
		if($('#usePoint').val() !== ''){
			usePoint = $('#usePoint').val();
		}
		console.log('動いてます');
		let totalPayment = totalPrice - usePoint ;
		let providingPoint = Math.floor(totalPayment * 0.1) ;
		
		$('#calc-total-payment').text(totalPayment.toLocaleString());
		$('#providing-point').text(providingPoint.toLocaleString());
	}
	;
});