/**
 * @author Roger Wu
 * @version 1.0
 */
(function($){

	$.fn.extend({
		jTask:function(options){
			return this.each(function(){
				var $task = $(this);
				var id = $task.attr("id");
				$task.click(function(e){
					var dialog = $("body").data(id);
					if ($task.hasClass("selected")) {
						$("a.minimize", dialog).trigger("click");
					} else {
						if (dialog.is(":hidden")) {
							$.taskBar.restoreDialog(dialog);
						} else
							$(dialog).trigger("click");
					}
					$.taskBar.scrollCurrent($(this));
					return false;
				});
				$("div.close", $task).click(function(e){
					$.pdialog.close(id)
					return false;
				}).hoverClass("closeHover");
				
				$task.hoverClass("hover");
			});
		}
	});
	$.taskBar = {
		_taskBar:null,
		_taskBox:null,
		_prevBut:null,
		_nextBut:null,
		_op:{id:"taskbar", taskBox:"div.taskbarContent",prevBut:".taskbarLeft",prevDis:"taskbarLeftDisabled", nextBut:".taskbarRight",nextDis:"taskbarRightDisabled", selected:"selected",boxMargin:"taskbarMargin"},
		init:function(options) {
			var $this = this;
			$.extend(this._op, options);
			this._taskBar = $("#" + this._op.id);
			if (this._taskBar.size() == 0) {
				this._taskBar = $(DWZ.frag["taskbar"]).appendTo($("#layout"));
				
				this._taskBar.find(".taskbarLeft").hoverClass("taskbarLeftHover");
				this._taskBar.find(".taskbarRight").hoverClass("taskbarRightHover");
			}
			this._taskBox = this._taskBar.find(this._op.taskBox);
			this._taskList = this._taskBox.find(">ul");
			this._prevBut = this._taskBar.find(this._op.prevBut);
			this._nextBut = this._taskBar.find(this._op.nextBut);
			this._prevBut.click(function(e){$this.scrollLeft()});
			this._nextBut.click(function(e){$this.scrollRight()});

			this._contextmenu(this._taskBox); // taskBar右键菜单
		},

		_contextmenu:function(obj) {
			$(obj).contextMenu('dialogCM', {
				bindings:{
					closeCurrent:function(t,m){
						var obj = t.isTag("li")?t:$.taskBar._getCurrent();
						$("div.close", obj).trigger("click");
					},
					closeOther:function(t,m){
						var selector = t.isTag("li")?("#" +t.attr("id")):".selected";
						var tasks = $.taskBar._taskList.find(">li:not(:"+selector+")");
						tasks.each(function(i){
							$("div.close",tasks[i]).trigger("click");
						});
					},
					closeAll:function(t,m){
						var tasks = $.taskBar._getTasks();
						tasks.each(function(i){
							$("div.close",tasks[i]).trigger("click");
						});
					}
				},
				ctrSub:function(t,m){
					var mCur = m.find("[rel='closeCurrent']");
					var mOther = m.find("[rel='closeOther']");
					if(!$.taskBar._getCurrent()[0]) {
						mCur.addClass("disabled");
						mOther.addClass("disabled");
					} else {
						if($.taskBar._getTasks().size() == 1)
							mOther.addClass("disabled");
					}
				}
			});
		},
		_scrollCurrent:function(){
			var iW = this._tasksW(this._getTasks());
			if (iW > this._getTaskBarW()) {
				var $this = this;
				var lTask = $(">li:last-child", this._taskList);
				var left = this._getTaskBarW() - lTask.position().left - lTask.outerWidth(true);
				this._taskList.animate({
					left: left + 'px'
				}, 200, function(){
					$this._ctrlScrollBut();
				});
			} else {
				this._ctrlScrollBut();
			}
		},
		_getTaskBarW:function(){
			return this._taskBox.width()- (this._prevBut.is(":hidden")?this._prevBut.width()+2:0) - (this._nextBut.is(":hidden")?this._nextBut.width()+2:0);
		},
		_scrollTask:function(task){
			var $this = this;
			if(task.position().left + this._getLeft()+task.outerWidth() > this._getBarWidth()) {
				var left = this._getTaskBarW()- task.position().left  - task.outerWidth(true) - 2;
				this._taskList.animate({left: left + 'px'}, 200, function(){
					$this._ctrlScrollBut();
				});
			} else if(task.position().left + this._getLeft() < 0) {
				var left = this._getLeft()-(task.position().left + this._getLeft());
				this._taskList.animate({left: left + 'px'}, 200, function(){
					$this._ctrlScrollBut();
				});
			}
		},
		/**
		 * 控制左右移动按钮何时显示与隐藏
		 */
		_ctrlScrollBut:function(){
			var iW = this._tasksW(this._getTasks());
			if (this._getTaskBarW() > iW) {
				this._taskBox.removeClass(this._op.boxMargin);
				this._nextBut.hide();
				this._prevBut.hide();
				if(this._getTasks().eq(0)[0])this._scrollTask(this._getTasks().eq(0));
			} else {
				this._taskBox.addClass(this._op.boxMargin);
				this._nextBut.show().removeClass(this._op.nextDis);
				this._prevBut.show().removeClass(this._op.prevDis);
				if (this._getLeft() >= 0){
					this._prevBut.addClass(this._op.prevDis);
				}
				if (this._getLeft() <= this._getTaskBarW() - iW) {
					this._nextBut.addClass(this._op.nextDis);
				} 
			}
		},
		_getLeft: function(){
			return this._taskList.position().left;
		},
		/**
		 * 取得第一个完全显示在taskbar上的任务
		 */
		_visibleStart: function(){
			var iLeft = this._getLeft();
			var jTasks = this._getTasks();
			for (var i=0; i<jTasks.size(); i++){
				if (jTasks.eq(i).position().left + jTasks.eq(i).outerWidth(true) + iLeft >= 0) return jTasks.eq(i);
			}
			return jTasks.eq(0);
		},
		/**
		 * 取得最后一个完全显示在taskbar上的任务
		 */
		_visibleEnd: function(){
			var iLeft = this._getLeft();
			var jTasks = this._getTasks();
			for (var i=0; i<jTasks.size(); i++){
				if (jTasks.eq(i).position().left + jTasks.eq(i).outerWidth(true) + iLeft > this._getBarWidth()) return jTasks.eq(i);
			}
			return jTasks.eq(jTasks.size()-1);
		},
		/**
		 * 取得所有的任务
		 */
		_getTasks:function(){
			return this._taskList.find(">li");
		},
		/**
		 * 计算所传入的所有任务的宽度和
		 * @param {Object} jTasks
		 */
		_tasksW:function(jTasks){
			var iW = 0;
			jTasks.each(function(){
				iW += $(this).outerWidth(true);
			});
			return iW;
		},
		_getBarWidth: function() {
			return this._taskBar.innerWidth();
		},
		/**
		 * 在任务栏上新加一个任务
		 * @param {Object} id
		 * @param {Object} title
		 */
		addDialog: function(id, title){
			this.show();
			var task = $("#"+id,this._taskList);
			if (!task[0]) {
				var taskFrag = '<li id="#taskid#"><div class="taskbutton"><span>#title#</span></div><div class="close">Close</div></li>';
				this._taskList.append(taskFrag.replace("#taskid#", id).replace("#title#", title));
				task = $("#"+id,this._taskList);
				task.jTask();
			} else {
				$(">div>span", task).text(title);
			}
			this._contextmenu(task);
			this.switchTask(id);
			this._scrollTask(task);
		},
		/**
		 * 关闭一个任务
		 * @param {Object} id
		 */
		closeDialog: function(obj){
			var task = (typeof obj == 'string')? $("#"+obj, this._taskList):obj;

			if (task.size() == 0) return; // fix bug for minable=false

			task.remove();
			if(this._getTasks().size() == 0){
				this.hide();
			}			
			this._scrollCurrent();
		},
		/**
		 * 
		 * @param {Object} id or dialog
		 */
		restoreDialog:function(obj){
			var dialog = (typeof obj == 'string')?$("body").data(obj):obj;
			var id = (typeof obj == 'string')?obj:dialog.data("id");
			var task = $.taskBar.getTask(id);
			$(".resizable").css({top: $(window).height()-60,left:$(task).position().left,height:$(task).outerHeight(),width:$(task).outerWidth()
			}).show().animate({top:$(dialog).css("top"),left: $(dialog).css("left"),width:$(dialog).css("width"),height:$(dialog).css("height")},250,function(){
				$(this).hide();
				$(dialog).show();
				$.pdialog.attachShadow(dialog);
			});
			$.taskBar.switchTask(id);
		},
		/**
		 * 把任务变成不是当前的
		 * @param {Object} id
		 */
		inactive:function(id){
			$("#" + id, this._taskList).removeClass("selected");
		},
		/**
		 * 向左移一个任务
		 */
		scrollLeft: function(){
			var task = this._visibleStart();
			this._scrollTask(task);
		},
		/**
		 * 向右移一个任务
		 */
		scrollRight: function(){
			var task = this._visibleEnd();
			this._scrollTask(task);
		},
		/**
		 * 移出当前点击的任务
		 * @param {Object} task
		 */
		scrollCurrent:function(task){
			this._scrollTask(task);
		},
		/**
		 * 切换任务
		 * @param {Object} id
		 */
		switchTask:function(id) {
			this._getCurrent().removeClass("selected");
			this.getTask(id).addClass("selected");
		},
		_getCurrent:function() {
			return this._taskList.find(">.selected");
		},
		getTask:function(id) {
			return $("#" + id, this._taskList);
		},
		/**
		 * 显示任务栏
		 */
		show:function(){
			if (this._taskBar.is(":hidden")) {
				this._taskBar.css("top", $(window).height() - 34 + this._taskBar.outerHeight()).show();
				this._taskBar.animate({
					top: $(window).height() - this._taskBar.outerHeight()
				}, 500);
			}
		},
		/**
		 * 隐藏任务栏
		 */
		hide:function(){
			this._taskBar.animate({
				top: $(window).height() - 29 + this._taskBar.outerHeight(true)
			}, 500,function(){
				$.taskBar._taskBar.hide();
			});
		}
	}
})(jQuery);