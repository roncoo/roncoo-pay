/**
 * @author Roger Wu
 * @version 1.0
 */
(function($){
 	$.fn.extend({jresize:function(options) {
        if (typeof options == 'string') {
                if (options == 'destroy') 
					return this.each(function() {
							var dialog = this;		
							$("div[class^='resizable']",dialog).each(function() {
								$(this).hide();
							});
	                });
        }
		return this.each(function(){
			var dialog = $(this);			
			var resizable = $(".resizable");
			$("div[class^='resizable']",dialog).each(function() {
				var bar = this;
				$(bar).mousedown(function(event) {
					$.pdialog.switchDialog(dialog);
					$.resizeTool.start(resizable, dialog, event, $(bar).attr("tar"));
					return false;
				}).show();
			});
		});
	}});
	$.resizeTool = {
		start:function(resizable, dialog, e, target) {
			$.pdialog.initResize(resizable, dialog, target);
			$.data(resizable[0], 'layer-drag', {
				options: $.extend($.pdialog._op, {target:target, dialog:dialog,stop:$.resizeTool.stop})
			});
			$.layerdrag.start(resizable[0], e, $.pdialog._op);
		},
		stop:function(){
			var data = $.data(arguments[0], 'layer-drag');
			$.pdialog.resizeDialog(arguments[0], data.options.dialog, data.options.target);
			$("body").css("cursor", "");
			$(arguments[0]).hide();
		}
	};
	$.layerdrag = { 
		start:function(obj, e, options) {
			if (!$.layerdrag.current) {
				$.layerdrag.current = {
					el: obj,
					oleft: parseInt(obj.style.left) || 0,
					owidth: parseInt(obj.style.width) || 0,
					otop: parseInt(obj.style.top) || 0,
					oheight:parseInt(obj.style.height) || 0,
					ox: e.pageX || e.screenX,
					oy: e.pageY || e.clientY
				};
				$(document).bind('mouseup', $.layerdrag.stop);
				$(document).bind('mousemove', $.layerdrag.drag);
			}
			return $.layerdrag.preventEvent(e);
		},
        drag: function(e) {
                if (!e) var e = window.event;
                var current = $.layerdrag.current;
				var data = $.data(current.el, 'layer-drag');
				var lmove = (e.pageX || e.screenX) - current.ox;
				var tmove = (e.pageY || e.clientY) - current.oy;
				if((e.pageY || e.clientY) <= 0 || (e.pageY || e.clientY) >= ($(window).height() - $(".dialogHeader", $(data.options.dialog)).outerHeight())) return false;
				var target = data.options.target;	
				var width = current.owidth;	
				var height = current.oheight;		
				if (target != "n" && target != "s") {
					width += (target.indexOf("w") >= 0)?-lmove:lmove;
				}
				if (width >= $.pdialog._op.minW) {
					if (target.indexOf("w") >= 0) {
						current.el.style.left = (current.oleft + lmove) + 'px';
					}
					if (target != "n" && target != "s") {
						current.el.style.width = width + 'px';
					}
				}
				if (target != "w" && target != "e") {
					height += (target.indexOf("n") >= 0)?-tmove:tmove;
				}
				if (height >= $.pdialog._op.minH) {
					if (target.indexOf("n") >= 0) {
						current.el.style.top = (current.otop + tmove) + 'px';
					}
					if (target != "w" && target != "e") {
						current.el.style.height = height + 'px';
					}
				}
				return $.layerdrag.preventEvent(e);
        },     
        stop: function(e) {
                var current = $.layerdrag.current;
                var data = $.data(current.el, 'layer-drag');
				$(document).unbind('mousemove', $.layerdrag.drag);
				$(document).unbind('mouseup', $.layerdrag.stop);
                if (data.options.stop) {
                        data.options.stop.apply(current.el, [ current.el ]);
                }
                $.layerdrag.current = null;
				return $.layerdrag.preventEvent(e);
        },
		preventEvent:function(e) {
                if (e.stopPropagation) e.stopPropagation();
                if (e.preventDefault) e.preventDefault();
                return false;
		}
	};
})(jQuery);