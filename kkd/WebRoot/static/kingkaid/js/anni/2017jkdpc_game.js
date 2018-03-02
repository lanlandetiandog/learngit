var awardArr = ['','0.3%加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','按摩乳胶枕一个','500元京东E卡一张'];
(function (lib, img, cjs, ss, an) {

var p; // shortcut to reference prototypes
lib.webFontTxtInst = {}; 
var loadedTypekitCount = 0;
var loadedGoogleCount = 0;
var gFontsUpdateCacheList = [];
var tFontsUpdateCacheList = [];
lib.ssMetadata = [];



lib.updateListCache = function (cacheList) {		
	for(var i = 0; i < cacheList.length; i++) {		
		if(cacheList[i].cacheCanvas)		
			cacheList[i].updateCache();		
	}		
};		

lib.addElementsToCache = function (textInst, cacheList) {		
	var cur = textInst;		
	while(cur != exportRoot) {		
		if(cacheList.indexOf(cur) != -1)		
			break;		
		cur = cur.parent;		
	}		
	if(cur != exportRoot) {		
		var cur2 = textInst;		
		var index = cacheList.indexOf(cur);		
		while(cur2 != cur) {		
			cacheList.splice(index, 0, cur2);		
			cur2 = cur2.parent;		
			index++;		
		}		
	}		
	else {		
		cur = textInst;		
		while(cur != exportRoot) {		
			cacheList.push(cur);		
			cur = cur.parent;		
		}		
	}		
};		

lib.gfontAvailable = function(family, totalGoogleCount) {		
	lib.properties.webfonts[family] = true;		
	var txtInst = lib.webFontTxtInst && lib.webFontTxtInst[family] || [];		
	for(var f = 0; f < txtInst.length; ++f)		
		lib.addElementsToCache(txtInst[f], gFontsUpdateCacheList);		

	loadedGoogleCount++;		
	if(loadedGoogleCount == totalGoogleCount) {		
		lib.updateListCache(gFontsUpdateCacheList);		
	}		
};		

lib.tfontAvailable = function(family, totalTypekitCount) {		
	lib.properties.webfonts[family] = true;		
	var txtInst = lib.webFontTxtInst && lib.webFontTxtInst[family] || [];		
	for(var f = 0; f < txtInst.length; ++f)		
		lib.addElementsToCache(txtInst[f], tFontsUpdateCacheList);		

	loadedTypekitCount++;		
	if(loadedTypekitCount == totalTypekitCount) {		
		lib.updateListCache(tFontsUpdateCacheList);		
	}		
};
// symbols:



(lib.cre1 = function() {
	this.initialize(img.cre1);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,100,233);


(lib.cre2 = function() {
	this.initialize(img.cre2);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,113,229);


(lib.cre3png = function() {
	this.initialize(img.cre3png);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,119,219);


(lib.cre4 = function() {
	this.initialize(img.cre4);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,139,216);


(lib.cre5 = function() {
	this.initialize(img.cre5);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,116,217);


(lib.cre6 = function() {
	this.initialize(img.cre6);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,108,223);


(lib.cre7 = function() {
	this.initialize(img.cre7);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,117,217);


(lib.cre8 = function() {
	this.initialize(img.cre8);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,82,219);


(lib.gamebtn2 = function() {
	this.initialize(img.gamebtn2);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,74,30);


(lib.startbtn = function() {
	this.initialize(img.startbtn);
}).prototype = p = new cjs.Bitmap();
p.nominalBounds = new cjs.Rectangle(0,0,343,91);// helper functions:

function mc_symbol_clone() {
	var clone = this._cloneProps(new this.constructor(this.mode, this.startPosition, this.loop));
	clone.gotoAndStop(this.currentFrame);
	clone.paused = this.paused;
	clone.framerate = this.framerate;
	return clone;
}

function getMCSymbolPrototype(symbol, nominalBounds, frameBounds) {
	var prototype = cjs.extend(symbol, cjs.MovieClip);
	prototype.clone = mc_symbol_clone;
	prototype.nominalBounds = nominalBounds;
	prototype.frameBounds = frameBounds;
	return prototype;
	}


(lib.移动禁止点击 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#CE1818").s().p("A4BGYIAAsvMAwDAAAIAAMvg");

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-153.8,-40.8,307.6,81.6);


(lib.startben = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.startbtn();
	this.instance.parent = this;
	this.instance.setTransform(-171.5,-45.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-171.5,-45.5,343,91);


(lib.forbidden2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#D22517").s().p("AoRFAIAAp/IQjAAIAAJ/g");

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-53,-32,106.1,64);


(lib.exchangebtn = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.gamebtn2();
	this.instance.parent = this;
	this.instance.setTransform(-37,-15);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-37,-15,74,30);


(lib.cre1y = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre1();
	this.instance.parent = this;
	this.instance.setTransform(-50,-116.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-50,-116.5,100,233);


(lib.btn8 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre8();
	this.instance.parent = this;
	this.instance.setTransform(-41,-109.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-41,-109.5,82,219);


(lib.btn7 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre2();
	this.instance.parent = this;
	this.instance.setTransform(-56.5,-114.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-56.5,-114.5,113,229);


(lib.btn6 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre6();
	this.instance.parent = this;
	this.instance.setTransform(-54,-111.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-54,-111.5,108,223);


(lib.btn5 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre5();
	this.instance.parent = this;
	this.instance.setTransform(-58,-108.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-58,-108.5,116,217);


(lib.btn4 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre4();
	this.instance.parent = this;
	this.instance.setTransform(-69.5,-108);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-69.5,-108,139,216);


(lib.btn3 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre3png();
	this.instance.parent = this;
	this.instance.setTransform(-59.5,-109.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-59.5,-109.5,119,219);


(lib.btn1 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre1();
	this.instance.parent = this;
	this.instance.setTransform(-50,-116.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = getMCSymbolPrototype(lib.btn1, new cjs.Rectangle(-50,-116.5,100,233), null);


(lib.btn = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.instance = new lib.cre7();
	this.instance.parent = this;
	this.instance.setTransform(-58.5,-108.5);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = getMCSymbolPrototype(lib.btn, new cjs.Rectangle(-58.5,-108.5,117,217), null);


(lib.buji1 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// 图层 1
	this.btn8 = new lib.btn8();
	this.btn8.parent = this;
	this.btn8.setTransform(0.1,0.2,0.697,0.697,0,0,0,0.1,0.2);
	new cjs.ButtonHelper(this.btn8, 0, 1, 1);

	this.btn8_1 = new lib.btn8();
	this.btn8_1.parent = this;
	this.btn8_1.setTransform(0.1,0.2,0.697,0.697,0,0,0,0.1,0.2);
	new cjs.ButtonHelper(this.btn8_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.btn8_1},{t:this.btn8}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-28.5,-76.3,57.2,152.6);


// stage content:
(lib._2017jkdpc_game523 = function(mode,startPosition,loop) {
if (loop == null) { loop = false; }	this.initialize(mode,startPosition,loop,{exchangebtn:0,num1:0});

	// timeline functions:
	this.frame_0 = function() {
		/* 
		兑换奖品
		*/
		
		this.exchangebtn.addEventListener("click", fl_ClickToGoToWebPage_14);
		
		function fl_ClickToGoToWebPage_14() {
			document.getElementById("yel_alert").style.display="block";
		}
	}
	this.frame_2 = function() {
		/* 
		在此处停止
		开始停止
		*/
		this.stop();
		/* 
		start开始点击
		无限次点击
		*/
		
		this.startbtn.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_2.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_2()
		{
			this.gotoAndPlay(3);			
		}
	}
	this.frame_80 = function() {
		/* 
		返回1
		控制所有查看奖品之后重新返回开始
		*/
		
		this.btn8.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_3.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_3()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回2
		*/
		
		this.btn.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_4.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_4()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回3
		*/
		
		this.btn6.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_5.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_5()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回4
		*/
		
		this.btn5.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_6.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_6()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回5
		*/
		
		this.btn4.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_7.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_7()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回6
		*/
		
		this.btn3.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_8.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_8()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		7
		*/
		
		this.btn7.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_9.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_9()
		{
			this.gotoAndPlay(0);
		}
		
		
		/* 
		返回8
		*/
		
		this.btn1.addEventListener("click", fl_ClickToGoToAndPlayFromFrame_10.bind(this));
		
		function fl_ClickToGoToAndPlayFromFrame_10()
		{
			this.gotoAndPlay(0);
		}
		/* 单击以转到 Web 页
		奖品8
		*/
		
		this.btn1.addEventListener("click", fl_ClickToGoToWebPage_12);
		
		function fl_ClickToGoToWebPage_12() {
			
			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				      		   
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
			
		}
		/* 单击以转到 Web 页
		单击指定的元件实例会在新浏览器窗口中加载 URL。
		
		说明:
		1. 用所需 URL 地址替换 http://www.adobe.com。
		   保留引号 ("")。
		*/
		
		this.btn.addEventListener("click", fl_ClickToGoToWebPage_11);
		
		function fl_ClickToGoToWebPage_11() {

			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
			
			
		}
		/* 
		奖品6
		*/
		
		this.btn3.addEventListener("click", fl_ClickToGoToWebPage_9);
		
		function fl_ClickToGoToWebPage_9() {
			
			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
			
		}
		/* 
		奖品5
		*/
		
		this.btn4.addEventListener("click", fl_ClickToGoToWebPage_8);
		
		function fl_ClickToGoToWebPage_8() {

			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				      		   
				  		   }
			 		   }else{
			 			  alert(data.message);
			 			  window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
			
		}
		/* 
		奖品4
		*/
		
		this.btn5.addEventListener("click", fl_ClickToGoToWebPage_7);
		
		function fl_ClickToGoToWebPage_7() {
			
			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				      		   
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
		}
		/* 
		奖品3
		*/
		
		this.btn6.addEventListener("click", fl_ClickToGoToWebPage_6);
		
		function fl_ClickToGoToWebPage_6() {

			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;		
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;		
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			   awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
 				      			   if (contactaddr=='') {
 				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
 				      				   document.getElementById("addr_txt_temp").style.display='block';
 				      			   }else{
 				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
 				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				      		   
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
			
		}
		/* 
		奖品7
		*/
		
		this.btn7.addEventListener("click", fl_ClickToGoToWebPage_10);
		
		function fl_ClickToGoToWebPage_10() {
			
			document.getElementById('yel_alert_box').style.display='block';
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname; 
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
		}
		/*
		奖品1
		*/
		
		this.btn8.addEventListener("click", fl_ClickToGoToWebPage_3);
		
		function fl_ClickToGoToWebPage_3() {
			
           document.getElementById('yel_alert_box').style.display='block';
						
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   var awardid = data.body.awardid;
				  		   var contactaddr = data.body.contactaddr;
				  		   var isvirtual = data.body.isvirtual;
//				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   document.getElementById('yel_alert_box').style.display='none';
				  		   if(awardid==''){
				  			  document.getElementById("game_alert").style.display="block";
							  document.getElementById("none_gift").style.display="block";
				  		   }else{
				  			  awardname = '';
				      		   var num = awardid.charAt(5);		 
				      		   awardname = awardArr[num];   
				      		   
				      		   if(isvirtual!='1'){
				      			   if (contactaddr=='') {
				      				   document.getElementById("memberaddress").innerHTML = "无物流信息，请及时完善";  
				      				   document.getElementById("addr_txt_temp").style.display='block';
				      			   }else{
				      				   document.getElementById("memberaddress").innerHTML = contactaddr;
				      			   }
							   }else{
								   document.getElementById("addr_txt_temp").style.display='none';
							   }
							   document.getElementById("game_alert").style.display="block";
							   document.getElementById("gain_gift").style.display="block";
							   document.getElementById("none_gift").style.display="none";
							   document.getElementById("getaward").innerHTML = "大侠文武双全荣获"+awardname;
				      		   
				  		   }
			 		   }else{
			 			   alert(data.message);
			 			   window.location.reload();
			 		   }       		   
			 	   },error:function(){
			 		  alert("当前参与的人数过多，请稍后再试!");
			 	   }
			   })
		}
		
		
		/*function getlottery(){
			
			document.getElementById('yel_alert_box').style.display='block';
			
			var lotteryresult = new Array();
			var awardid;
			var contactaddr;
			var isvirtual;
			var awardname;
			
			$.ajax({
			 	   type:"POST",
			 	   url: ctx+'/auth/activity/thirdlottery',
			 	   dataType:"json",
			 	   success:function(data){
			 		   if(data.status){
				 		   awardid = data.body.awardid;
				  		   contactaddr = data.body.contactaddr;
				  		   isvirtual = data.body.isvirtual;
				  		   var awardArr = ['',,'3‰加息券一张','现金券10元','现金券50元','现金红包5元','晴雨伞一把','笔记本套装一套','乳胶保健枕一个','500元京东E卡一张'];
				  		   if(awardid==''){
				  			   
				  		   }else{
				  			   awardname = '';
				      		   var num = awardid.charAt(5);
				      		   awardname = awardArr[num];   
				  		   }
			 		   }else{
			 			   alert(data.message);
			 		   }       		   
			 	   }
			   })
			   lotteryresult[0]=awardid;
			   lotteryresult[1]=contactaddr;
			   lotteryresult[2]=isvirtual;
			   lotteryresult[3]=awardname;
		
			return lotteryresult;
		}*/
		
	}
	this.frame_85 = function() {
		stage.enableMouseOver();
		var btn1 = this.btn1;
			btn1.cursor = "pointer";
		
		btn1.addEventListener("mouseover", function(){
			createjs.Tween.get(btn1).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn1.addEventListener("mouseout", function(){
			createjs.Tween.get(btn1).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		
		var btn = this.btn;
			btn.cursor = "pointer";
		
		btn.addEventListener("mouseover", function(){
			createjs.Tween.get(btn).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		
		btn.addEventListener("mouseout", function(){
			createjs.Tween.get(btn).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn3 = this.btn3;
			btn3.cursor = "pointer";
		
		btn3.addEventListener("mouseover", function(){
			createjs.Tween.get(btn3).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn3.addEventListener("mouseout", function(){
			createjs.Tween.get(btn3).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn4 = this.btn4;
			btn4.cursor = "pointer";
		
		btn4.addEventListener("mouseover", function(){
			createjs.Tween.get(btn4).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn4.addEventListener("mouseout", function(){
			createjs.Tween.get(btn4).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn5 = this.btn5;
			btn5.cursor = "pointer";
		
		btn5.addEventListener("mouseover", function(){
			createjs.Tween.get(btn5).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn5.addEventListener("mouseout", function(){
			createjs.Tween.get(btn5).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn6 = this.btn6;
			btn6.cursor = "pointer";
		
		btn6.addEventListener("mouseover", function(){
			createjs.Tween.get(btn6).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn6.addEventListener("mouseout", function(){
			createjs.Tween.get(btn6).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn7 = this.btn7;
			btn7.cursor = "pointer";
		
		btn7.addEventListener("mouseover", function(){
			createjs.Tween.get(btn7).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn7.addEventListener("mouseout", function(){
			createjs.Tween.get(btn7).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
		stage.enableMouseOver();
		var btn8 = this.btn8;
			btn8.cursor = "pointer";
		
		btn8.addEventListener("mouseover", function(){
			createjs.Tween.get(btn8).to({scaleX:1.001, scaleY:1.001, override:true}, 400, createjs.Ease.cubicOut);
		});
		btn8.addEventListener("mouseout", function(){
			createjs.Tween.get(btn8).to({scaleX:0.8, scaleY:0.8, override:true}, 400, createjs.Ease.cubicOut);
		});
	}
	this.frame_90 = function() {
		/* 在此处停止
		开始停止
		只有在抽奖之后才可以重新开始
		*/
		this.stop();
	}

	// actions tween:
	this.timeline.addTween(cjs.Tween.get(this).call(this.frame_0).wait(2).call(this.frame_2).wait(78).call(this.frame_80).wait(5).call(this.frame_85).wait(5).call(this.frame_90).wait(1));

	// p1
	this.btn1 = new lib.btn1();
	this.btn1.parent = this;
	this.btn1.setTransform(777.6,583.4,0.921,0.921,0,0,0,0.4,0.4);
	this.btn1._off = true;

	this.timeline.addTween(cjs.Tween.get(this.btn1).wait(80).to({_off:false},0).wait(11));

	// p2
	this.btn = new lib.btn();
	this.btn.parent = this;
	this.btn.setTransform(333,322.3,0.923,0.923,0,0,0,0.2,0.3);
	this.btn._off = true;

	this.timeline.addTween(cjs.Tween.get(this.btn).wait(80).to({_off:false},0).wait(11));

	// p3
	this.btn3 = new lib.btn3();
	this.btn3.parent = this;
	this.btn3.setTransform(343.9,577.8,0.899,0.899,0,0,0,0.4,0.5);
	this.btn3._off = true;
	new cjs.ButtonHelper(this.btn3, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn3).wait(80).to({_off:false},0).wait(11));

	// p4
	this.btn4 = new lib.btn4();
	this.btn4.parent = this;
	this.btn4.setTransform(109.2,577.3,0.886,0.886,0,0,0,0.3,0.1);
	this.btn4._off = true;
	new cjs.ButtonHelper(this.btn4, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn4).wait(80).to({_off:false},0).wait(11));

	// p5
	this.btn5 = new lib.btn5();
	this.btn5.parent = this;
	this.btn5.setTransform(777.7,328.6,0.864,0.864,0,0,0,0.3,0.3);
	this.btn5._off = true;
	new cjs.ButtonHelper(this.btn5, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn5).wait(80).to({_off:false},0).wait(11));

	// p6
	this.btn6 = new lib.btn6();
	this.btn6.parent = this;
	this.btn6.setTransform(563.3,325.2,0.871,0.871,0,0,0,0.1,0.1);
	this.btn6._off = true;
	new cjs.ButtonHelper(this.btn6, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn6).wait(80).to({_off:false},0).wait(11));

	// p7
	this.btn7 = new lib.btn7();
	this.btn7.parent = this;
	this.btn7.setTransform(567.1,577.8,0.898,0.898,0,0,0,0.1,0.4);
	this.btn7._off = true;
	new cjs.ButtonHelper(this.btn7, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn7).wait(80).to({_off:false},0).wait(11));

	// p8
	this.btn8 = new lib.btn8();
	this.btn8.parent = this;
	this.btn8.setTransform(110.5,323,0.909,0.909,0,0,0,0.1,0.4);
	this.btn8._off = true;
	new cjs.ButtonHelper(this.btn8, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn8).wait(80).to({_off:false},0).wait(11));

	// 禁止
	this.forbidden2 = new lib.forbidden2();
	this.forbidden2.parent = this;
	this.forbidden2.setTransform(697.9,101.1,1.004,1.004,0,0,0,0.1,0.2);
	this.forbidden2.alpha = 0.012;
	this.forbidden2._off = true;
	new cjs.ButtonHelper(this.forbidden2, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.forbidden2).wait(3).to({_off:false},0).to({_off:true},82).wait(6));

	// exchangebtn
	this.exchangebtn = new lib.exchangebtn();
	this.exchangebtn.parent = this;
	this.exchangebtn.setTransform(680.1,106.5,0.9,0.9,0,0,0,0.3,0.3);
	new cjs.ButtonHelper(this.exchangebtn, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.exchangebtn).wait(91));

	// num2金开币余额
	var coinamt = document.getElementById("coinamt").value;
	this.num2 = new cjs.Text(coinamt, "bold 23px 'Microsoft YaHei'", "#FFF600");
	this.num2.name = "num2";
	this.num2.textAlign = "center";
	this.num2.lineHeight = 30;
	this.num2.lineWidth = 80;
	this.num2.parent = this;
	this.num2.setTransform(558.9,95,0.9,0.9);

	this.timeline.addTween(cjs.Tween.get(this.num2).wait(91));

	// num1抽奖次数
	var lotterynums = document.getElementById("lotterynums").value;
	this.text = new cjs.Text(lotterynums, "bold 23px 'Microsoft YaHei'", "#FFF600");
	this.text.textAlign = "center";
	this.text.lineHeight = 30;
	this.text.lineWidth = 50;
	this.text.parent = this;
	this.text.setTransform(218.5,95,0.9,0.9);

	this.timeline.addTween(cjs.Tween.get(this.text).wait(91));

	// txt1
	this.text_1 = new cjs.Text("当前剩余       次游戏机会     账户金开币余额                  个", "22px 'Microsoft YaHei'");
	this.text_1.lineHeight = 31;
	this.text_1.lineWidth = 570;
	this.text_1.parent = this;
	this.text_1.setTransform(118.4,94.9,0.9,0.9);

	this.timeline.addTween(cjs.Tween.get(this.text_1).wait(91));

	// start禁止点击
	this.instance = new lib.移动禁止点击();
	this.instance.parent = this;
	this.instance.setTransform(415.6,36.5,1.004,1.004,0,0,0,0.1,0.2);
	this.instance.alpha = 0.02;
	this.instance._off = true;
	new cjs.ButtonHelper(this.instance, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(3).to({_off:false},0).wait(88));

	// startbtn.png
	this.startbtn = new lib.startben();
	this.startbtn.parent = this;
	this.startbtn.setTransform(429.8,44.6,0.725,0.725,0,0,0,0.4,0.4);
	new cjs.ButtonHelper(this.startbtn, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.startbtn).wait(91));

	// 88
	this.btn8_1 = new lib.btn8();
	this.btn8_1.parent = this;
	this.btn8_1.setTransform(772.2,554.5,0.909,0.909,0,0,0,0.1,0.4);
	new cjs.ButtonHelper(this.btn8_1, 0, 1, 1);

	this.btn8_2 = new lib.btn8();
	this.btn8_2.parent = this;
	this.btn8_2.setTransform(772.2,554.5,0.909,0.909,0,0,0,0.1,0.4);
	new cjs.ButtonHelper(this.btn8_2, 0, 1, 1);

	this.instance_1 = new lib.buji1("synched",0);
	this.instance_1.parent = this;
	this.instance_1.setTransform(772.3,554.3,1.305,1.305,0,0,0,0.1,0.1);
	this.instance_1._off = true;

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.btn8_2},{t:this.btn8_1}]}).to({state:[{t:this.instance_1}]},2).to({state:[{t:this.btn8_1}]},8).to({state:[{t:this.btn8_1}]},8).to({state:[{t:this.btn8_1}]},25).to({state:[{t:this.btn8_1}]},17).to({state:[{t:this.btn8_1}]},13).to({state:[]},7).wait(11));
	this.timeline.addTween(cjs.Tween.get(this.btn8_1).to({_off:true},2).to({_off:false,regX:0.4,regY:0.2,scaleX:0.9,scaleY:0.9,x:771.2,y:326.6},8).to({regX:0.3,regY:0.1,x:492.6,y:311.3},8).wait(42).to({regX:0.1,regY:0.4,scaleX:0.91,scaleY:0.91,x:110.5,y:323},13).to({_off:true},7).wait(11));
	this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(2).to({_off:false},0).to({_off:true,regX:0.4,regY:0.2,scaleX:0.9,scaleY:0.9,x:771.2,y:326.6,mode:"independent"},8).wait(81));

	// 77
	this.btn_1 = new lib.btn();
	this.btn_1.parent = this;
	this.btn_1.setTransform(538.5,559.9,0.923,0.923,0,0,0,0.2,0.3);

	this.timeline.addTween(cjs.Tween.get(this.btn_1).wait(2).to({scaleX:0.9,scaleY:0.9,x:776.5,y:583.9},8).to({regY:0.4,y:316.8},8).to({x:578,y:313},8).wait(38).to({regY:0.3,scaleX:0.92,scaleY:0.92,x:333,y:322.3},15).to({_off:true},1).wait(11));

	// 66
	this.btn6_1 = new lib.btn6();
	this.btn6_1.parent = this;
	this.btn6_1.setTransform(322,561.6,0.871,0.871,0,0,0,0.1,0.1);
	new cjs.ButtonHelper(this.btn6_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn6_1).wait(2).to({scaleX:0.9,scaleY:0.9,x:660.5,y:582},8).to({regX:0.2,x:777},8).to({regY:0.2,y:324.5},8).to({regX:0.1,regY:0.3,x:675.8,y:311.2},5).wait(38).to({regY:19.5,scaleX:0.87,scaleY:0.87,x:563.3,y:342.1},5).to({_off:true},6).wait(11));

	// 55
	this.btn5_1 = new lib.btn5();
	this.btn5_1.parent = this;
	this.btn5_1.setTransform(96.2,566.2,0.864,0.864,0,0,0,0.3,0.1);
	new cjs.ButtonHelper(this.btn5_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn5_1).wait(2).to({scaleX:0.9,scaleY:0.9,x:551.9,y:582},8).to({regX:0.2,x:670.2},8).to({regX:0.3,x:784.7},8).to({regY:19.8,scaleX:0.86,scaleY:0.86,x:777.7,y:345.5},7).to({_off:true},47).wait(11));

	// 44
	this.btn4_1 = new lib.btn4();
	this.btn4_1.parent = this;
	this.btn4_1.setTransform(765,316.3,0.886,0.886,0,0,0,0.1,0.3);
	new cjs.ButtonHelper(this.btn4_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn4_1).wait(2).to({regX:0.2,scaleX:0.9,scaleY:0.9,x:397.4,y:317.6},8).wait(45).to({regX:0.3,x:109.3},10).to({regY:0.1,scaleX:0.89,scaleY:0.89,x:109.2,y:577.3},6).to({_off:true},9).wait(11));

	// 33
	this.btn3_1 = new lib.btn3();
	this.btn3_1.parent = this;
	this.btn3_1.setTransform(540,322.7,0.899,0.899,0,0,0,0.2,0.4);
	new cjs.ButtonHelper(this.btn3_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn3_1).wait(2).to({regY:0.2,scaleX:0.9,scaleY:0.9,x:299.8,y:311.4},8).wait(45).to({regX:0.4,x:97.6},5).to({y:568.9},5).to({regY:0.5,scaleX:0.9,scaleY:0.9,x:343.9,y:577.8},9).to({_off:true},6).wait(11));

	// 22
	this.btn7_1 = new lib.btn7();
	this.btn7_1.parent = this;
	this.btn7_1.setTransform(322.5,320.7,0.898,0.898,0,0,0,0.2,0.3);
	new cjs.ButtonHelper(this.btn7_1, 0, 1, 1);

	this.timeline.addTween(cjs.Tween.get(this.btn7_1).wait(2).to({regX:0.3,regY:0.1,scaleX:0.9,scaleY:0.9,x:192,y:310.8},8).wait(45).to({regX:0.1,x:90.8},0).to({y:570.3},5).to({regY:19.1,scaleX:0.9,scaleY:0.9,x:567.1,y:594.6},14).to({_off:true},6).wait(11));

	// 11
	this.button_1 = new lib.cre1y();
	this.button_1.parent = this;
	this.button_1.setTransform(90.9,317.4,0.9,0.9,0,0,0,0.1,0.2);
	new cjs.ButtonHelper(this.button_1, 0, 1, 1);

	this.btn1_1 = new lib.btn1();
	this.btn1_1.parent = this;
	this.btn1_1.setTransform(777.6,583.4,0.921,0.921,0,0,0,0.4,0.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.button_1}]}).to({state:[{t:this.button_1}]},43).to({state:[{t:this.button_1}]},7).to({state:[{t:this.btn1_1}]},24).to({state:[]},6).wait(11));
	this.timeline.addTween(cjs.Tween.get(this.button_1).wait(50).to({regY:0.1,y:574.9},0).to({_off:true,regX:0.4,regY:0.4,scaleX:0.92,scaleY:0.92,x:777.6,y:583.4},24).wait(17));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(480.8,376.3,780.7,648.6);
/*
startbtn抽奖游戏按钮
cre1-8点击对应人物按钮
*/
lib.properties = {
	width: 870,
	height: 730,
	fps: 24,
	color: "#FFFFFF",
	opacity: 0.00,
	webfonts: {},
	manifest: [
		{src:ctx+"/static/kingkaid/images/anni/cre1.png", id:"cre1"},
		{src:ctx+"/static/kingkaid/images/anni/cre2.png", id:"cre2"},
		{src:ctx+"/static/kingkaid/images/anni/cre3png.png", id:"cre3png"},
		{src:ctx+"/static/kingkaid/images/anni/cre4.png", id:"cre4"},
		{src:ctx+"/static/kingkaid/images/anni/cre5.png", id:"cre5"},
		{src:ctx+"/static/kingkaid/images/anni/cre6.png", id:"cre6"},
		{src:ctx+"/static/kingkaid/images/anni/cre7.png", id:"cre7"},
		{src:ctx+"/static/kingkaid/images/anni/cre8.png", id:"cre8"},
		{src:ctx+"/static/kingkaid/images/anni/gamebtn2.png", id:"gamebtn2"},
		{src:ctx+"/static/kingkaid/images/anni/startbtn.png", id:"startbtn"}
	],
	preloads: []
};




})(lib = lib||{}, images = images||{}, createjs = createjs||{}, ss = ss||{}, bean = bean||{});
var lib, images, createjs, ss, bean;