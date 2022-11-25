'use strict'

$(function(){
	$(document).on('click','#clearName',function(){
		clearText()
	});	
});

function clearText() {
	let textForm = document.getElementById("searchingName");
	console.log('出力確認')
  textForm.value = '';
};