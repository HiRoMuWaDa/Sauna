'use strict'

$(function(){
//郵便番号から住所自動入力
	$(document).on('click','#getAddress',function(){
		let zipcode = $('#destinationZipcode').val();
		console.log(zipcode);
		$.ajax({
		url: 'http://zipcoda.net/api',
		dataType: 'jsonp',
		data: {
			zipcode: zipcode
		},
		async: true
	}).done(function(data) {
		// 検索成功時にはページに結果を反映
		// コンソールに取得データを表示
		console.log(data);
		console.dir(JSON.stringify(data));
		let displayAddress = data.items[0].pref + data.items[0].address  
//		let displayAddress = data.items[0].components  
		
		$('#destinationAddress').val(displayAddress);
	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		// 検索失敗時には、その旨をダイアログ表示
		alert('正しい結果を得られませんでした。')
		console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
		console.log('textStatus     : ' + textStatus);
		console.log('errorThrown    : ' + errorThrown.message);
		});
	});
	
	
//二重登録防止
	
	$('#register').on('click',function(){
		$('#register').prop('disabled',true);
//		console.log('ボタンを止めました！');
		$('#finish-order').submit();
	});
	
	
	
});
	
	
