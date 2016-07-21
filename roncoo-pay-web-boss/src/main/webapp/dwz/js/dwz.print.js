/**
 * @author 张慧华 z@j-ui.com
 * 
 */
(function($){
	$.printBox = function(rel){
		var _printBoxId = 'printBox';
		var $contentBox = rel ? $('#'+rel) : $("body"),
			$printBox = $('#'+_printBoxId);
			
		if ($printBox.size()==0){
			$printBox = $('<div id="'+_printBoxId+'"></div>').appendTo("body");
		}

		$printBox.html($contentBox.html()).find("[layoutH]").height("auto");
		window.print();

	}

})(jQuery);
