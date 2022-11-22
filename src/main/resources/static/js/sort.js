'use strict';

$(function(){
	
	
	$('#searchSelect').on('change', function() { //change=selectで要素の値のが確定したときに
		$('#searchFormSort').val($('#searchSelect').val()) //value値を取得
		$('#searchForm').submit();
	})
	
});