/**
 * @author Roger Wu
 */
(function($){
	$.fn.jDrag = function(options){
		if (typeof options == 'string') {
			if (options == 'destroy') 
				return this.each(function(){
					$(this).unbind('mousedown', $.rwdrag.start);
					$.data(this, 'pp-rwdrag', null);
				});
		}
		return this.each(function(){
			var el = $(this);
			$.data($.rwdrag, 'pp-rwdrag', {
				options: $.extend({
					el: el,
					obj: el
				}, options)
			});
			if (options.event) 
				$.rwdrag.start(options.event);
			else {
				var select = options.selector;
				$(select, obj).bind('mousedown', $.rwdrag.start);
			}
		});
	};
	$.rwdrag = {
		start: function(e){
			document.onselectstart=function(e){return false};//禁止选择

			var data = $.data(this, 'pp-rwdrag');
			var el = data.options.el[0];
			$.data(el, 'pp-rwdrag', {
				options: data.options
			});
			if (!$.rwdrag.current) {
				$.rwdrag.current = {
					el: el,
					oleft: parseInt(el.style.left) || 0,
					otop: parseInt(el.style.top) || 0,
					ox: e.pageX || e.screenX,
					oy: e.pageY || e.screenY
				};
				$(document).bind("mouseup", $.rwdrag.stop).bind("mousemove", $.rwdrag.drag);
			}
		},
		drag: function(e){
			if (!e)  var e = window.event;
			var current = $.rwdrag.current;
			var data = $.data(current.el, 'pp-rwdrag');
			var left = (current.oleft + (e.pageX || e.clientX) - current.ox);
			var top = (current.otop + (e.pageY || e.clientY) - current.oy);
			if (top < 1) top = 0;
			if (data.options.move == 'horizontal') {
				if ((data.options.minW && left >= $(data.options.obj).cssv("left") + data.options.minW) && (data.options.maxW && left <= $(data.options.obj).cssv("left") + data.options.maxW)) 
					current.el.style.left = left + 'px';
				else if (data.options.scop) {
					if (data.options.relObj) {
						if ((left - parseInt(data.options.relObj.style.left)) > data.options.cellMinW) {
							current.el.style.left = left + 'px';
						}
					} else 
						current.el.style.left = left + 'px';
				}
			} else if (data.options.move == 'vertical') {
					current.el.style.top = top + 'px';
			} else {
				var selector = data.options.selector ? $(data.options.selector, data.options.obj) : $(data.options.obj);
				if (left >= -selector.outerWidth() * 2 / 3 && top >= 0 && (left + selector.outerWidth() / 3 < $(window).width()) && (top + selector.outerHeight() < $(window).height())) { //浏览器缩放后有bug，先注释掉
					current.el.style.left = left + 'px';
					current.el.style.top = top + 'px';
				}
			}
			
			if (data.options.drag) {
				data.options.drag.apply(current.el, [current.el, e]);
			}
			
			return $.rwdrag.preventEvent(e);
		},
		stop: function(e){
			var current = $.rwdrag.current;
			var data = $.data(current.el, 'pp-rwdrag');
			$(document).unbind('mousemove', $.rwdrag.drag).unbind('mouseup', $.rwdrag.stop);
			if (data.options.stop) {
				data.options.stop.apply(current.el, [current.el, e]);
			}
			$.rwdrag.current = null;

			document.onselectstart=function(e){return true};//启用选择
			return $.rwdrag.preventEvent(e);
		},
		preventEvent:function(e){
			if (e.stopPropagation) e.stopPropagation();
			if (e.preventDefault) e.preventDefault();
			return false;			
		}
	};
})(jQuery);
