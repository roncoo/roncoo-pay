/**
 * @author 张慧华 z@j-ui.com
 */

(function($){
	var menu, shadow, hash;
	$.fn.extend({
		contextMenu: function(id, options){
			var op = $.extend({
				    shadow : true,
				    bindings:{},
					ctrSub:null
				}, options
			);
			
			if (!menu) {
				menu = $('<div id="contextmenu"></div>').appendTo('body').hide();
			}
			if (!shadow) {
				shadow = $('<div id="contextmenuShadow"></div>').appendTo('body').hide();
			}
			
			hash = hash || [];
			hash.push({
				id : id,
				shadow: op.shadow,
				bindings: op.bindings || {},
				ctrSub: op.ctrSub
			});
			
			var index = hash.length - 1;
			$(this).bind('contextmenu', function(e) {
				display(index, this, e, op);
				return false;
			});
			return this;
		}
	});
	
	function display(index, trigger, e, options) {
		var cur = hash[index];

		var content = $(DWZ.frag[cur.id]);
		content.find('li').hoverClass();
	
		// Send the content to the menu
		menu.html(content);
	
		$.each(cur.bindings, function(id, func) {
			$("[rel='"+id+"']", menu).bind('click', function(e) {
				hide();
				func($(trigger), $("#"+cur.id));
			});
		});
		
		var posX = e.pageX;
		var posY = e.pageY;
		if ($(window).width() < posX + menu.width()) posX -= menu.width();
		if ($(window).height() < posY + menu.height()) posY -= menu.height();

		menu.css({'left':posX,'top':posY}).show();
		if (cur.shadow) shadow.css({width:menu.width(),height:menu.height(),left:posX+3,top:posY+3}).show();
		$(document).one('click', hide);
		
		if ($.isFunction(cur.ctrSub)) {cur.ctrSub($(trigger), $("#"+cur.id));}
	}
	
	function hide() {
		menu.hide();
		shadow.hide();
	}
})(jQuery);

