/**
 * @author Roger Wu 
 * @version 1.0
 */
(function($){
	$.fn.cssv = function(pre){
		var cssPre = $(this).css(pre);
		return cssPre.substring(0, cssPre.indexOf("px")) * 1;
	};
	$.fn.jBar = function(options){
		var op = $.extend({container:"#container", collapse:".collapse", toggleBut:".toggleCollapse div", sideBar:"#sidebar", sideBar2:"#sidebar_s", splitBar:"#splitBar", splitBar2:"#splitBarProxy"}, options);
		return this.each(function(){
			var jbar = this;
			var sbar = $(op.sideBar2, jbar);
			var bar = $(op.sideBar, jbar);
			$(op.toggleBut, bar).click(function(){
				DWZ.ui.sbar = false;
				$(op.splitBar).hide();
				var sbarwidth = sbar.cssv("left") + sbar.outerWidth();
				var barleft = sbarwidth - bar.outerWidth();
				var cleft = $(op.container).cssv("left") - (bar.outerWidth() - sbar.outerWidth());
				var cwidth = bar.outerWidth() - sbar.outerWidth() + $(op.container).outerWidth();
				$(op.container).animate({left: cleft,width: cwidth},50,function(){
					bar.animate({left: barleft}, 500, function(){
						bar.hide();
						sbar.show().css("left", -50).animate({left: 5}, 200);
						$(window).trigger(DWZ.eventType.resizeGrid);
					});
				});
				$(op.collapse,sbar).click(function(){
					var sbarwidth = sbar.cssv("left") + sbar.outerWidth();
					if(bar.is(":hidden")) {
						$(op.toggleBut, bar).hide();
						bar.show().animate({left: sbarwidth}, 500);
						$(op.container).click(_hideBar);
					} else {
						bar.animate({left: barleft}, 500, function(){
							bar.hide();
						});
					}
					function _hideBar() {
						$(op.container).unbind("click", _hideBar);
						if (!DWZ.ui.sbar) {
							bar.animate({left: barleft}, 500, function(){
								bar.hide();
							});
						}
					}
					return false;
				});
				return false;
			});
			$(op.toggleBut, sbar).click(function(){
				DWZ.ui.sbar = true;
				sbar.animate({left: -25}, 200, function(){				
					bar.show();
				});
				bar.animate({left: 5}, 800, function(){
					$(op.splitBar).show();
					$(op.toggleBut, bar).show();					
					var cleft = 5 + bar.outerWidth() + $(op.splitBar).outerWidth();
					var cwidth = $(op.container).outerWidth() - (cleft - $(op.container).cssv("left"));
					$(op.container).css({left: cleft,width: cwidth});
					$(op.collapse, sbar).unbind('click');
					$(window).trigger(DWZ.eventType.resizeGrid);
				});
				return false;
			});
			$(op.splitBar).mousedown(function(event){
				$(op.splitBar2).each(function(){
					var spbar2 = $(this);
					setTimeout(function(){spbar2.show();}, 100);
					spbar2.css({visibility: "visible",left: $(op.splitBar).css("left")});					
					spbar2.jDrag($.extend(options, {obj:$("#sidebar"), move:"horizontal", event:event,stop: function(){
						$(this).css("visibility", "hidden");
						var move = $(this).cssv("left") - $(op.splitBar).cssv("left");
						var sbarwidth = bar.outerWidth() + move;
						var cleft = $(op.container).cssv("left") + move;
						var cwidth = $(op.container).outerWidth() - move;
						bar.css("width", sbarwidth);
						$(op.splitBar).css("left", $(this).css("left"));
						$(op.container).css({left: cleft,width: cwidth});

					}}));
					return false;					
				});
			});
		});
	}
})(jQuery);
