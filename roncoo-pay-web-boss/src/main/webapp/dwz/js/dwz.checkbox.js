/**
 * @author 张慧华 z@j-ui.com
 */
(function($){
	$.fn.extend({
		
		checkboxCtrl: function(parent){
			return this.each(function(){
				var $trigger = $(this);
				$trigger.click(function(){
					var group = $trigger.attr("group");
					if ($trigger.is(":checkbox")) {
						var type = $trigger.is(":checked") ? "all" : "none";
						if (group) $.checkbox.select(group, type, parent);
					} else {
						if (group) $.checkbox.select(group, $trigger.attr("selectType") || "all", parent);
					}
					
				});
			});
		}
	});
	
	$.checkbox = {
		selectAll: function(_name, _parent){
			this.select(_name, "all", _parent);
		},
		unSelectAll: function(_name, _parent){
			this.select(_name, "none", _parent);
		},
		selectInvert: function(_name, _parent){
			this.select(_name, "invert", _parent);
		},
		select: function(_name, _type, _parent){
			var $parent = $(_parent || document),
				$checkboxLi = $parent.find(":checkbox[name='"+_name+"']");
			switch(_type){
				case "invert":
					$checkboxLi.each(function(){
						this.checked = !this.checked;
					});
					break;
				case "none":
					$checkboxLi.removeAttr('checked');
					break;
				default:
					$checkboxLi.each(function(){
						this.checked = true;
					});

					break;
			}

			$checkboxLi.trigger('change');
		}
	};
})(jQuery);
