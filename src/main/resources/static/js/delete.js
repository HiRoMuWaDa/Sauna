'use strict'

$(function(){
	$(document).on('click','#clearName',function(){
		clearText()
	});	
});

function clearText() {
	let textForm = document.getElementById("searchingName");
  textForm.value = '';
};