/**
 * @desc 兼容不同的浏览器居中scrollCenter
 * @author 张慧华 z@j-ui.com
 */
(function($){
	$.fn.extend({

		getWindowSize: function(){
			if ($.browser.opera) { return { width: window.innerWidth, height: window.innerHeight }; }
			return { width: $(window).width(), height: $(window).height() };
		},
		/**
		 * @param options
		 */		
		scrollCenter: function(options){
			// 扩展参数
			var op = $.extend({ z: 1000000, mode:"WH"}, options);
			
			// 追加到 document.body 并设置其样式
			var windowSize = this.getWindowSize();

			return this.each(function(){
				var $this = $(this).css({
					'position': 'absolute',
					'z-index': op.z
				});
				
				// 当前位置参数
				var bodyScrollTop = $(document).scrollTop();
				var bodyScrollLeft = $(document).scrollLeft();
				var movedivTop = (windowSize.height - $this.height()) / 2 + bodyScrollTop;
				var movedivLeft = (windowSize.width - $this.width()) / 2 + bodyScrollLeft;
				
				if (op.mode == "W") {
					$this.appendTo(document.body).css({
						'left': movedivLeft + 'px'
					});
				} else if (op.model == "H"){
					$this.appendTo(document.body).css({
						'top': movedivTop + 'px'
					});	
				} else {
					$this.appendTo(document.body).css({
						'top': (windowSize.height - $this.height()) / 2 + $(window).scrollTop() + 'px',
						'left': movedivLeft + 'px'
					});
				}
				
				// 滚动事件
				$(window).scroll(function(e){
					var windowSize = $this.getWindowSize();
					var tmpBodyScrollTop = $(document).scrollTop();
					var tmpBodyScrollLeft = $(document).scrollLeft();
					
					movedivTop += tmpBodyScrollTop - bodyScrollTop;
					movedivLeft += tmpBodyScrollLeft - bodyScrollLeft;
					bodyScrollTop = tmpBodyScrollTop;
					bodyScrollLeft = tmpBodyScrollLeft;

					// 以动画方式进行移动
					if (op.mode == "W") {
						$this.stop().animate({
							'left': movedivLeft + 'px'
						});
					} else if (op.mode == "H") {
						$this.stop().animate({
							'top': movedivTop + 'px'
						});
					} else {
						$this.stop().animate({
							'top': movedivTop + 'px',
							'left': movedivLeft + 'px'
						});
					}
					
				});
				
				// 窗口大小重设事件
				$(window).resize(function(){
					var windowSize = $this.getWindowSize();
					movedivTop = (windowSize.height - $this.height()) / 2 + $(document).scrollTop();
					movedivLeft = (windowSize.width - $this.width()) / 2 + $(document).scrollLeft();
					
					if (op.mode == "W") {
						$this.stop().animate({
							'left': movedivLeft + 'px'
						});
					} else if (op.mode == "H") {
						$this.stop().animate({
							'top': movedivTop + 'px'
						});
					} else {
						$this.stop().animate({
							'top': movedivTop + 'px',
							'left': movedivLeft + 'px'
						});
					}
					
				});
			});
			
		}
	});
})(jQuery);