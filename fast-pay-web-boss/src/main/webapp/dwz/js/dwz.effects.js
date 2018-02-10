/**
 * @author Roger Wu
 */
(function($){
	$.extend($.fn, {
		jBlindUp: function(options){
			var op = $.extend({duration: 500, easing: "swing", call: function(){}}, options);
			return this.each(function(){
				var $this = $(this);
				$(this).animate({height: 0}, {
					step: function(){},
					duration: op.duration,
					easing: op.easing,
					complete: function(){ 
						$this.css({display: "none"});
						op.call();
					}
				});
			});
		},
		jBlindDown: function(options){
			var op = $.extend({to:0, duration: 500,easing: "swing",call: function(){}}, options);
			return this.each(function(){
				var $this = $(this);
				var	fixedPanelHeight = (op.to > 0)?op.to:$.effect.getDimensions($this[0]).height;
				$this.animate({height: fixedPanelHeight}, {
					step: function(){},
					duration: op.duration,
					easing: op.easing,
					complete: function(){ 
					$this.css({display: ""});
					op.call(); }
				});
			});
		},
		jSlideUp:function(options) {
			var op = $.extend({to:0, duration: 500,easing: "swing",call: function(){}}, options);
			return this.each(function(){
				var $this = $(this);
				$this.wrapInner("<div></div>");
				var	fixedHeight = (op.to > 0)?op.to:$.effect.getDimensions($(">div",$this)[0]).height;
				$this.css({overflow:"visible",position:"relative"});
				$(">div",$this).css({position:"relative"}).animate({top: -fixedHeight}, {
					easing: op.easing,
					duration: op.duration,
					complete:function(){$this.html($(this).html());}
				});
				
			});
		},
		jSlideDown:function(options) {
			var op = $.extend({to:0, duration: 500,easing: "swing",call: function(){}}, options);
			return this.each(function(){
				var $this = $(this);
				var	fixedHeight = (op.to > 0)?op.to:$.effect.getDimensions($this[0]).height;
				$this.wrapInner("<div style=\"top:-" + fixedHeight + "px;\"></div>");
				$this.css({overflow:"visible",position:"relative", height:"0px"})
				.animate({height: fixedHeight}, {
					duration: op.duration,
					easing: op.easing,
					complete: function(){  $this.css({display: "", overflow:""}); op.call(); }
				});
				$(">div",$this).css({position:"relative"}).animate({top: 0}, {
					easing: op.easing,
					duration: op.duration,
					complete:function(){$this.html($(this).html());}
				});
			});
		}
	});
	$.effect = {
		getDimensions: function(element, displayElement){
			var dimensions = new $.effect.Rectangle;
			var displayOrig = $(element).css('display');
			var visibilityOrig = $(element).css('visibility');
			var isZero = $(element).height()==0?true:false;
			if ($(element).is(":hidden")) {
				$(element).css({visibility: 'hidden', display: 'block'});
				if(isZero)$(element).css("height","");
				if ($.browser.opera)
					refElement.focus();
			}
			dimensions.height = $(element).outerHeight();
			dimensions.width = $(element).outerWidth();
			if (displayOrig == 'none'){
				$(element).css({visibility: visibilityOrig, display: 'none'});
				if(isZero) if(isZero)$(element).css("height","0px");
			}
			return dimensions;
		}
	}
	$.effect.Rectangle = function(){
		this.width = 0;
		this.height = 0;
		this.unit = "px";
	}
})(jQuery);
