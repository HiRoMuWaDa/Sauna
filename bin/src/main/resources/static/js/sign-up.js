'use strict'

$(function(){
	$(document).on('keyup','#password',function(){
		checkPass()	
	});
	
	$(document).on('keyup','#confirmationPassword',function(){
		checkPass()	
	});
	


function checkPass(){
		let hostUrl = "http://localhost:8080/rakurakusauna/shop/checkConPass";
		let password = $('#password').val();
		let confirmationPassword = $('#confirmationPassword').val();
		console.log('password:'+password);
		console.log('confirmationPassword:'+confirmationPassword);
		
		$.ajax({
			url : hostUrl,
			type : 'post',
			dataType : 'json',
			data :{
				password : password,
				confirmationPassword : confirmationPassword,
			},
			async : true ,
		}).done(function(data){
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			console.log(data.duplicateMessage);
			$('#duplicateMessage').text(data.duplicateMessage);
		}).fail(function(XMLHttpRequest,textStatus,errorThrown){
			alert('エラーが発生しました！')
			console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
			console.log('textStatus : ' + textStatus);
			console.log('errorThrown : ' + errorThrown.message);
		})
	};
	
//郵便番号から住所自動入力
	$(document).on('click','#getAddress',function(){
		let zipcode = $('#zipcode').val();
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
		
		$('#address').val(displayAddress);
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
		$('#signUp').submit();
	});
	
	
	
});
	
	
