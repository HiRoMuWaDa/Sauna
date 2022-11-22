/**
 * 
 */
'use strict';

$(function() {
	$.getJSON("http://localhost:8080/rakurakusauna/shop/search-item-by-name",
		function(data) {
			$('#searchingName').autocomplete({
				source: data,
				minLength: 1,
				position: { my : "left top", at: "left bottom" }
			});
		});
});