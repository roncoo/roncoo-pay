/**
 * @author Roger Wu
 */
(function($){
	$.fn.dialogDrag = function(options){
        if (typeof options == 'string') {
                if (options == 'destroy') 
					return this.each(function() {
							var dialog = this;		
							$("div.dialogHeader", dialog).unbind("mousedown");
	                });
        }
		return this.each(function(){
			var dialog = $(this);
			$("div.dialogHeader", dialog).mousedown(function(e){
				$.pdialog.switchDialog(dialog);
				dialog.data("task",true);
				setTimeout(function(){
					if(dialog.data("task"))$.dialogDrag.start(dialog,e);
				},100);
				return false;
			}).mouseup(function(e){
				dialog.data("task",false);
				return false;
			});
		});
	};
	$.dialogDrag = {
		currId:null,
		_init:function(dialog) {
			this.currId = new Date().getTime();
			var shadow = $("#dialogProxy");
			if (!shadow.size()) {
				shadow = $(DWZ.frag["dialogProxy"]);
				$("body").append(shadow);
			}
			$("h1", shadow).html($(".dialogHeader h1", dialog).text());
		},
		start:function(dialog,event){
				this._init(dialog);
				var sh = $("#dialogProxy");
				sh.css({
					left: dialog.css("left"),
					top: dialog.css("top"),
					height: dialog.css("height"),
					width: dialog.css("width"),
					zIndex:parseInt(dialog.css("zIndex")) + 1
				}).show();
				$("div.dialogContent",sh).css("height",$("div.dialogContent",dialog).css("height"));
				sh.data("dialog",dialog);
				dialog.css({left:"-10000px",top:"-10000px"});
				$(".shadow").hide();				
				$(sh).jDrag({
					selector:".dialogHeader",
					stop: this.stop,
					event:event
				});
				return false;
		},
		stop:function(){
			var sh = $(arguments[0]);
			var dialog = sh.data("dialog");
			$(dialog).css({left:$(sh).css("left"),top:$(sh).css("top")});
			$.pdialog.attachShadow(dialog);
			$(sh).hide();
		}
	}
})(jQuery);