//封装seckill
var seckill = {
	//封装秒杀相关的URL地址
	URL : {
		now: function(){
			return "/seckill/now";
		},
		exposer: function(seckillId){
			return "/seckill/"+seckillId+"/exposer";
		},
		execution: function(seckillId,md5) {
			return "/seckill/"+seckillId+"/"+md5+"/execution";
		}
	},	
	//执行秒杀
	handleSeckill: function(seckillId,node){
		//获取秒杀地址，控制显示逻辑，执行秒杀
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			if(result && result['success']){
				var exposer = result['data'];
				if (exposer['exposed']) {
					//开启
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId,md5);
					node.show();
					//绑定一次，防止用户重复点击
					$("#killBtn").one('click',function(){
						//点击之后，禁用按钮
						$(this).addClass('disabled');
						$.post(killUrl,{},function(result){
							if(result && result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								node.html('<span class="lable label-success">'+stateInfo+'</span>').show();
							}
						})
					});
					
				}else{
					//未开启,根据服务器返回的时间重新倒计时
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					seckill.countdown(seckillId,now,start,end);
				}
			}
		});
	},
	//验证手机号是否合法
	validataPhone: function(phone){
		if(phone&&phone.length==11 &&!isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//计时
	countdown: function(seckillId,nowTime,startTime,endTime){
		var seckillBox = $("#seckill-box");
		//时间判断
		if(nowTime > endTime){
			seckillBox.html("秒杀结束");
		}else if(nowTime < startTime){
			//秒杀未开始
			var killTime = new Date(parseInt(startTime));
			
			//调用jQuery-countdown插件,事件绑定
			seckillBox.countdown(killTime,function(event){
				//回调函数
				var format = event.strftime('秒杀倒计时：%D天  %H时  %M分  %S秒');
				seckillBox.html(format);
			}).on('finish.countdown',function(){
				//时间完成后回调时间
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckill(seckillId,seckillBox);
			});
		}else{
			//秒杀开始
			seckill.handleSeckill(seckillId,seckillBox);
		}
	},
	tempTimeToDate: function(tempTime){
		var now = new Date(parseInt(tempTime));
		return now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
	},
	//封装详情页秒杀逻辑
	detail : {
		//详情页初始化
		init : function(params){
				var killPhone = $.cookie('killPhone');
				if(!seckill.validataPhone(killPhone)){
					//cookie没有手机号
					var killPhoneModal = $("#killPhoneModal");
					killPhoneModal.modal({
						show: true,//显示弹出层
						backdrop: 'static',//禁止位置关闭
						keyboard: false//关闭键盘事件
					});
					//登录点击
					$("#killPhoneBtn").click(function(){
						var phone = $("#killPhoneKey").val();
						if(seckill.validataPhone(phone)){
							//将手机号保存到cookie，有效期为7天，只在/seckill路径下有效
							$.cookie('killPhone',phone,{expires:7,path:'/seckill'})
							//验证通过，刷新页面
							window.location.reload();
						}else{
							//改变html的时候要先隐藏一下在显示，会显得很好看
							$("#killPhoneMessage").hide().html('<lable class="label label-danger" style="color:red">手机号错误</label>').show(300);
						}
					});
				}
				//计时开始
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				var seckillId = params['seckillId'];
				$.get(seckill.URL.now(),{},function(result){
					if(result && result['success']){
						var nowTime = result['data'];
						seckill.countdown(seckillId,nowTime,startTime,endTime);
					}
				});
			}
		}
}