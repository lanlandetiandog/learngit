var PGEdit_IE32_CLASSID="B97060CE-A619-474C-BE7F-23F02B2E120A";
var PGEdit_IE32_CAB="XACBX.cab#version=1,0,0,1";
var PGEdit_IE32_EXE="XACBX.exe";
var PGEdit_IE32_EXE_VERSION="1,0,0,1";

var PGEdit_IE64_CLASSID="B97060CE-A619-474C-BE7F-23F02B2E120A";
var PGEdit_IE64_CAB="XACBX64.cab#version=1,0,0,1";
var PGEdit_IE64_EXE="XACBX64.exe";

var PGEdit_FF="XACBXFF.exe";
var PGEdit_FF_VERSION="1.0.0.1";

var PGEdit_Linux32="";
var PGEdit_Linux64="";
var PGEdit_Linux_VERSION="";

var PGEdit_MacOs="XACBXMAC.pkg";
var PGEdit_MacOs_VERSION="1.0.0.1";

var PGEdit_MacOs_Safari="XACBXMAC.pkg";
var PGEdit_MacOs_Safari_VERSION="1.0.0.1";

var PGEdit_Update="1";//非IE控件是否强制升级 1强制升级,0不强制升级

if(navigator.userAgent.indexOf("MSIE")<0){
	   navigator.plugins.refresh();
}

;(function(jQuery) {
	jQuery.pge = function (options) {
		this.settings = jQuery.extend(true, {}, jQuery.pge.defaults, options);
		this.init();
	};

	jQuery.extend(jQuery.pge, {
		defaults: {
			pgePath: "./ocx/",
			pgeId: "",
			pgeEdittype: 0,
			pgeEreg1: "",
			pgeEreg2: "",
			PGECert: "",
			PGECertSSl: "",
			pgeMaxlength: 12,
			pgeTabindex: 2,
			pgeClass: "ocx_style",
			pgeInstallClass: "ocx_style",
			passLoginStyle:"passLoginStyle",
			pgeOnkeydown:"",
			pgeOnfocus:"",
			pgeFontName:"",
			pgeFontSize:"",
			tabCallback:"",
			pgeBackColor:"",
			pgeForeColor:"",
			AffineX: "",
			AffineY: ""
		},

		prototype: {
			init: function() {				
			    this.pgeDownText="请点此安装控件";
			    this.transType = this.getPageCode();
			    this.osBrowser = this.checkOsBrowser();
				this.pgeVersion = this.getVersion();			    			
				this.isInstalled = this.checkInstall();
			},
			getPageCode:function(){
				code = window.location.href;
				code = code.substring(code.lastIndexOf("/") + 1, code.lastIndexOf("."));
			},
			checkOsBrowser: function() {
				var userosbrowser;
				if((navigator.platform =="Win32") || (navigator.platform =="Windows")){
					if(navigator.userAgent.indexOf("MSIE")>0 || navigator.userAgent.indexOf("msie")>0 || navigator.userAgent.indexOf("Trident")>0 || navigator.userAgent.indexOf("trident")>0){
						if(navigator.userAgent.indexOf("ARM")>0){
							userosbrowser=9; //win8 RAM Touch
							this.pgeditIEExe="";
						}else{
							userosbrowser=1;//windows32ie32
							this.pgeditIEClassid=PGEdit_IE32_CLASSID;
							this.pgeditIECab=PGEdit_IE32_CAB;
							this.pgeditIEExe=PGEdit_IE32_EXE;
						}
					}else{
						userosbrowser=2; //windowsff
						this.pgeditFFExe=PGEdit_FF;
					}
				}else if((navigator.platform=="Win64")){
					if(navigator.userAgent.indexOf("Windows NT 6.2")>0 || navigator.userAgent.indexOf("windows nt 6.2")>0){		
						userosbrowser=1;//windows32ie32
						this.pgeditIEClassid=PGEdit_IE32_CLASSID;
						this.pgeditIECab=PGEdit_IE32_CAB;
						this.pgeditIEExe=PGEdit_IE32_EXE;						
					}else if(navigator.userAgent.indexOf("MSIE")>0 || navigator.userAgent.indexOf("msie")>0 || navigator.userAgent.indexOf("Trident")>0 || navigator.userAgent.indexOf("trident")>0){
						userosbrowser=3;//windows64ie64
						this.pgeditIEClassid=PGEdit_IE64_CLASSID;
						this.pgeditIECab=PGEdit_IE64_CAB;
						this.pgeditIEExe=PGEdit_IE64_EXE;			
					}else{
						userosbrowser=2;//windowsff、谷歌
						this.pgeditFFExe=PGEdit_FF;
					}
				}else if(navigator.userAgent.indexOf("Linux")>0){
					if(navigator.userAgent.indexOf("_64")>0){
						userosbrowser=4;//linux64
						this.pgeditFFExe=PGEdit_Linux64;
					}else{
						userosbrowser=5;//linux32
						this.pgeditFFExe=PGEdit_Linux32;
					}
					if(navigator.userAgent.indexOf("Android")>0){
                        userosbrowser=7;//Android
                     }					
				}else if(navigator.userAgent.indexOf("Macintosh")>0){
					if(navigator.userAgent.indexOf("Safari")>0 && (navigator.userAgent.indexOf("Version/5.1")>0 || navigator.userAgent.indexOf("Version/5.2")>0 || navigator.userAgent.indexOf("Version/6")>0)){
						userosbrowser=8;//macos Safari 5.1 more
						this.pgeditFFExe=PGEdit_MacOs_Safari;
					}else if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
						userosbrowser=6;//macos
						this.pgeditFFExe=PGEdit_MacOs;						
//					}else if(navigator.userAgent.indexOf("Opera")>=0 && (navigator.userAgent.indexOf("Version/11.6")>0 || navigator.userAgent.indexOf("Version/11.7")>0)){
					}else if(navigator.userAgent.indexOf("Opera")>=0 ){
						userosbrowser=6;//macos
						this.pgeditFFExe=PGEdit_MacOs;						
					}else if(navigator.userAgent.indexOf("Safari")>=0){
						userosbrowser=6;//macos
						this.pgeditFFExe=PGEdit_MacOs;			
					}else{
						userosbrowser=0;//macos
						this.pgeditFFExe="";
					}
				}
				return userosbrowser;
			},
			/**
			 * osBrowser
			 * 1:IE
			 * 2:谷歌
			 */
			getpgeHtml: function() {
				
				if (this.osBrowser==1 || this.osBrowser==3) {
					var demo = ""
					demo +=  '<span id="'+this.settings.pgeId+'_pge" class="'+this.settings.passLoginStyle+'"><OBJECT ＋　'; 
					demo += ' ID="' + this.settings.pgeId + '" CLASSID="CLSID:' + this.pgeditIEClassid + '" codebase="' 
					        +this.settings.pgePath+ this.pgeditIECab + '" onkeydown="if(13==event.keyCode || 27==event.keyCode)'+this.settings.pgeOnkeydown+';" onfocus="' + this.settings.pgeOnfocus + '" tabindex="'+this.settings.pgeTabindex+'" class="' + this.settings.pgeClass + '">' 
					        
					        + '<param name="edittype" value="'+ this.settings.pgeEdittype + '"><param name="maxlength" value="' + this.settings.pgeMaxlength +'">' 

							+ '<param name="input2" value="'+ this.settings.pgeEreg1 + '"><param name="input3" value="'+ this.settings.pgeEreg2 + '"></OBJECT></span>'
							
							+ '<span id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;display:none;"><a href="'+this.settings.pgePath+this.pgeditIEExe+'">'+this.pgeDownText+'</a></span>';
					return demo;
				} else if (this.osBrowser==2) {
					if(navigator.userAgent.indexOf("Firefox")>0){
						var pgeOcx='<embed ID="' + this.settings.pgeId + '"  maxlength="'+this.settings.pgeMaxlength+'" input_2="'+this.settings.pgeEreg1+'" input_3="'+this.settings.pgeEreg2+'" edittype="'+this.settings.pgeEdittype+'" type="application/xacbpayedit-edit" tabindex="'+this.settings.pgeTabindex+'" class="' + this.settings.pgeClass + '" ';
						
						if(this.settings.pgeOnkeydown!=undefined && this.settings.pgeOnkeydown!="") pgeOcx+=' input_1013="'+this.settings.pgeOnkeydown+'"';
						
						if(this.settings.tabCallback!=undefined && this.settings.tabCallback!="") pgeOcx+=' input_1009="document.getElementById(\''+this.settings.tabCallback+'\').focus()"';
						
						if(this.settings.pgeFontName!=undefined && this.settings.pgeFontName!="") pgeOcx+=' FontName="'+this.settings.pgeFontName+'"';
						
						if(this.settings.pgeFontSize!=undefined && this.settings.pgeFontSize!="") pgeOcx+=' FontSize='+Number(this.settings.pgeFontSize)+'';					
						switch (code) {
						case "openpay_page":
							pgeOcx+='style="height:47px; width:302px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						case "safetycenter":
							pgeOcx+='style="height:27px; width:154px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						case "myproperty":
							pgeOcx+='style="width:264px; height:34px;" onfocus="' + this.settings.pgeOnfocus + '">'
							break;
						case "deposit_page":
							pgeOcx+='style="height:30px; width:133px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						default:
							break;
						}
					} else {
						var pgeOcx='<embed ID="' + this.settings.pgeId + '"  maxlength="'+this.settings.pgeMaxlength+'" input_2="'+this.settings.pgeEreg1+'" input_3="'+this.settings.pgeEreg2+'" edittype="'+this.settings.pgeEdittype+'" type="application/xacbpayedit-edit" tabindex="'+this.settings.pgeTabindex+'" class="' + this.settings.pgeClass + '" ';
						
						if(this.settings.pgeOnkeydown!=undefined && this.settings.pgeOnkeydown!="") pgeOcx+=' input_1013="'+this.settings.pgeOnkeydown+'"';
						
						if(this.settings.tabCallback!=undefined && this.settings.tabCallback!="") pgeOcx+=' input_1009="document.getElementById(\''+this.settings.tabCallback+'\').focus()"';
						
						if(this.settings.pgeFontName!=undefined && this.settings.pgeFontName!="") pgeOcx+=' FontName="'+this.settings.pgeFontName+'"';
						
						if(this.settings.pgeFontSize!=undefined && this.settings.pgeFontSize!="") pgeOcx+=' FontSize='+Number(this.settings.pgeFontSize)+'';					
						switch (code) {
						case "openpay_page":
							pgeOcx+='style="height:47px; width:302px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						case "safetycenter":
							pgeOcx+='style="height:27px; width:154px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						case "myproperty":
							pgeOcx+='style="width:264px; height:34px;" onfocus="' + this.settings.pgeOnfocus + '">'
							break;
						case "deposit_page":
							pgeOcx+='style="height:30px; width:133px;" onfocus="' + this.settings.pgeOnfocus + '">';
							break;
						default:
							break;
						}
					}
					return pgeOcx;
				} else if (this.osBrowser==6) {
					var html = "";
					if(navigator.userAgent.indexOf("Firefox")>0 && "login" == code || "070500" == code){
						html += '<embed ';
					}else{
						if("login" == code || "070500" == code){
							html += '<embed ';
						}else if("010101" == code || "120102" == code){
							html += '<embed ';
						}else{
							html += '<embed ';
						}
					}
					
					html += 'ID="' + this.settings.pgeId + '" input2="'+ this.settings.pgeEreg1 + '" input3="'+ this.settings.pgeEreg2 + '" input4="'+Number(this.settings.pgeMaxlength)+'" input0="'+Number(this.settings.pgeEdittype)+'" type="application/xacbpayedit-edit" version="'+PGEdit_MacOs_VERSION+'" tabindex="'+this.settings.pgeTabindex+'" class="' + this.settings.pgeClass + '">';
					return html;
				
				} else if (this.osBrowser==8) {
					var html = "";
					if("login" == code || "070500" == code){
						html += '<embed ';
					}else if("010101" == code || "120102" == code){
						html += '<embed ';
					}else{
						html += '<embed ';
					}
					html += 'ID="' + this.settings.pgeId + '" input2="'+ this.settings.pgeEreg1 + '" input3="'+ this.settings.pgeEreg2 + '" input4="'+Number(this.settings.pgeMaxlength)+'" input0="'+Number(this.settings.pgeEdittype)+'" type="application/xacbpayedit-edit" version="'+PGEdit_MacOs_Safari_VERSION+'" tabindex="'+this.settings.pgeTabindex+'" class="' + this.settings.pgeClass + '">';
					return html;
				} else {
					return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;">暂不支持此浏览器</div>';

				}				
			},
		
			
			getDownHtml: function() {
				if (this.osBrowser==1 || this.osBrowser==3) {
					
					return '<span id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;"><a href="'+this.settings.pgePath+this.pgeditIEExe+'">'+this.pgeDownText+'</a></span>';
					
				} else if (this.osBrowser==2 || this.osBrowser==6 || this.osBrowser==8) {
					
					return '<span id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;"><a href="'+this.settings.pgePath+this.pgeditFFExe+'">'+this.pgeDownText+'</a></span>';
				
				} else {

					return '<div id="'+this.settings.pgeId+'_down" class="'+this.settings.pgeInstallClass+'" style="text-align:center;">暂不支持此浏览器</div>';

				}				
			},

			getDownHref: function() {
				if (this.osBrowser==1 || this.osBrowser==3) {
					return this.settings.pgePath+this.pgeditIEExe;
				} else if (this.osBrowser==2 || this.osBrowser==6 || this.osBrowser==8) {
					return this.settings.pgePath+this.pgeditFFExe;
				} else {
					return null;
				}
			},
			
			load: function() {				
				if (!this.checkInstall()) {
					return this.getDownHtml();
				}else{		
					if(this.osBrowser==2){  
						if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_FF_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return this.getDownHtml();	
						}
					} else if (this.osBrowser==6) {
						if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_MacOs_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return this.getDownHtml();	
						}
					}else if (this.osBrowser==8) {
						if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_MacOs_Safari_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return this.getDownHtml();	
						}
					}					
					return this.getpgeHtml();
				}
			},
			
			generate: function() {

				if(this.osBrowser==2){
					   if(this.isInstalled==false){
						   return document.write(this.getDownHtml());	 
					   }else if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_FF_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return document.write(this.getDownHtml());	
						}
					} else if (this.osBrowser==6) {
						if(this.isInstalled==false){
							return document.write(this.getDownHtml());	
						}else if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_MacOs_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return document.write(this.getDownHtml());	
						}
					}else if (this.osBrowser==8) {
						if(this.isInstalled==false){
							return document.write(this.getDownHtml());	
						}else if(this.getConvertVersion(this.pgeVersion)<this.getConvertVersion(PGEdit_MacOs_Safari_VERSION) && PGEdit_Update==1){
							this.setDownText();
							return document.write(this.getDownHtml());
						}
					}
					return document.write(this.getpgeHtml());				
			},
		
			
			pwdclear: function() {
				if (this.checkInstall()) {
					var control = document.getElementById(this.settings.pgeId);
					control.ClearSeCtrl();
				}				
			},
			pwdSetSk: function(s) {
				if (this.checkInstall()) {
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3 || this.osBrowser==6 || this.osBrowser==8) {
							control.input1=s;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							control.input(1,s);
						}					
					} catch (err) {
					}
				}				
			},
			
			pwdResultHash: function() {

				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(7);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							//code = control.get_output1();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			
			pwdResult: function() {

				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output1;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(7);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output1();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			/**
			 * 非对称加密，使用加密指令用于交易密码
			 */
			pwdSmResult: function(aX,aY) {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						var my =aX +"|" +aY;
						if (this.osBrowser==1 || this.osBrowser==3) {
							control.input7=my;
							code = control.output105;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							control.input(200,my);
							code = control.output(7,12);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							control.input13=my;
							code = control.get_output22();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			pwdSm2Result: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						var my =this.settings.AffineX +"|" +this.settings.AffineY;
						if (this.osBrowser==1 || this.osBrowser==3) {
							control.input7=my;
							code = control.output103;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							control.input(200,my);
							code = control.output(7,7);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							control.input13=my;
							code = control.get_output19();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			pwdSm3Result: function() {

				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output102;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(2, 2);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output18();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			pwdSm4Result: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{	
					try {
						var control = document.getElementById(this.settings.pgeId);
						
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output101;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(7,8);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output17();
						}					
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			devInfo:function(){
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output58;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							//control.package=0;
							code = control.output(15);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output20();
						}
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			machineNetwork: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.GetIPMacList();
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							//control.package=0;
							code = control.output(9);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output7(0);
						}
					} catch (err) {

						code = '';

					}
				}
				return code;
			},
			machineDisk: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.GetNicPhAddr(1);
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							//control.package=0;
							code = control.output(11);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output7(2);
						}
					} catch (err) {

						code = '';

					}
				}
				return code;
			},
			machineCPU: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.GetNicPhAddr(2);
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							//control.package=0;
							code = control.output(10);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output7(1);
						}
					} catch (err) {
						code = '';
					}
				}
				return code;
			},
			pwdSimple: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output44;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(13);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output10();
						}
					} catch (err) {
						code = '';
					}
				}
				return code;
			},			
			pwdValid: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = 1;
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							if(control.output1) code = control.output5;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(5);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output5();
						}
					} catch (err) {

						code = 1;

					}
				}
				return code;
			},	
			/**
			 * md5加密
			 */
			pwdHash: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = 0;
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output2;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(2);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output2();
						}
					} catch (err) {

						code = 0;

					}
				}
				return code;
			},	
			/**
			 * 获取输入密码的长度
			 */
			pwdLength: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = 0;
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if (this.osBrowser==1 || this.osBrowser==3) {
							code = control.output3;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							code = control.output(3);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							code = control.get_output3();
						}
					} catch (err) {

						code = 0;

					}
				}
				return code;
			},				
			pwdStrength: function() {
				var code = 0;

				if (!this.checkInstall()) {

					code = 0;

				}

				else{

					try {

						var control = document.getElementById(this.settings.pgeId);

						if (this.osBrowser==1 || this.osBrowser==3) {
							var l=control.output3;
							var n=control.output4;
						} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5) {
							var l=control.output(3);
							var n=control.output(4);
						}else if (this.osBrowser==6 || this.osBrowser==8) {
							var l=control.get_output3();
							var n=control.get_output4();
						}

						if(l==0){
							code = 0;
						}else if(n==1){
							code = 1;//弱
						}else if(n==2 && l>=6){
							code = 2;//中
						}else if(n==3 && l>=6){
							code = 3;//强
						}

					} catch (err) {

						code = 0;

					}

				}		
				return code;
			},
			/**
			 * 检查密码控件是否安装
			 */
			checkInstall: function() {
				try {
					if (this.osBrowser==1) {
						var comActiveX = new ActiveXObject("XACBPayEdit.PassGuard.1");
						//alert(comActiveX.output35);
						if(this.getConvertVersionTwo(comActiveX.output35)<this.getConvertVersionTwo(PGEdit_IE32_EXE_VERSION)){
							return false;
						}
					} else if (this.osBrowser==2 || this.osBrowser==4 || this.osBrowser==5 || this.osBrowser==6 || this.osBrowser==8) {
					    var arr=new Array();
					    if(this.osBrowser==6){
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;
					    }else if(this.osBrowser==8){
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;
					    }else{
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;
					    }
					    
						if(pge_info.indexOf(":")>0){
							arr=pge_info.split(":");
							var pge_version = arr[1];
						}else{
							var pge_version = "";
						}
						
					} else if (this.osBrowser==3) {
						var comActiveX = new ActiveXObject("XACBPayEdit.PassGuard.1");
						if(this.getConvertVersionTwo(comActiveX.output35)<this.getConvertVersionTwo(PGEdit_IE32_EXE_VERSION)){
							return false;
						}
					}
				}catch(e){
					return false;
				}
				return true;
			},
			getConvertVersionTwo:function(version) {
				try {
					if(version==undefined || version==""){
						return 0;
					}else{
						var m=version.split(",");
						var v=parseInt(m[0]*1000)+parseInt(m[1]*100)+parseInt(m[2]*10)+parseInt(m[3]);
						return v;
					}
					return v;
				}catch(e){
					return 0;
				}			
			},
			getConvertVersion:function(version) {
				try {
					if(version==undefined || version==""){
						return 0;
					}else{
						var m=version.split(".");
						var v=parseInt(m[0]*1000)+parseInt(m[1]*100)+parseInt(m[2]*10)+parseInt(m[3]);
						return v;
					}
					return v;
				}catch(e){
					return 0;
				}			
			},
			getVersion: function() {
				try {
					if(navigator.userAgent.indexOf("MSIE")<0){
						var arr=new Array();
					    if(this.osBrowser==6){
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;
					    }else if(this.osBrowser==8){
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;					    	
					    }else{
					    	var pge_info=navigator.plugins['XACBPayEdit'].description;
					    }
						if(pge_info.indexOf(":")>0){
							arr=pge_info.split(":");
							var pge_version = arr[1];
						}else{
							var pge_version = "";
						}
					}
					return pge_version;
				}catch(e){
					return "";
				}					
			},
			setColor: function() {
				var code = '';

				if (!this.checkInstall()) {

					code = '';
				}
				else{
					try {
						var control = document.getElementById(this.settings.pgeId);
						if(this.settings.pgeBackColor!=undefined && this.settings.pgeBackColor!="") control.BackColor=this.settings.pgeBackColor;
						if(this.settings.pgeForeColor!=undefined && this.settings.pgeForeColor!="") control.ForeColor=this.settings.pgeForeColor;
					} catch (err) {

						code = '';

					}
				}
			},			
			setDownText:function(){
				if(this.pgeVersion!=undefined && this.pgeVersion!=""){
						this.pgeDownText="请点此升级控件";
				}
			},	
			/*
			 * 初始化密码控件
			 */
			pgInitialize:function(){
				if(this.checkInstall()){
					if(this.osBrowser==1 || this.osBrowser==3){ 
			            jQuery('#'+this.settings.pgeId+'_pge').show(); 
					}
					
					var control = document.getElementById(this.settings.pgeId);
					
					if(this.settings.pgeBackColor!=undefined && this.settings.pgeBackColor!="") control.BackColor=this.settings.pgeBackColor;
					if(this.settings.pgeForeColor!=undefined && this.settings.pgeForeColor!="") control.ForeColor=this.settings.pgeForeColor;
					
				}else{
					jQuery('#'+this.settings.pgeId+'_pge').hide();	
					if(this.osBrowser==1 || this.osBrowser==3){
						jQuery('#'+this.settings.pgeId+'_down').show();
					}	
					
				}
				
			}
		}
	});	
	
})(jQuery);


var pgeditor=new $.pge({pgePath:"../../js/ocx/",pgeId:"password",pgeEdittype:0,pgeEreg1:"^[0-9]+$",pgeEreg2:"[\\s\\S]{6}",pgeMaxlength:6,pgeTabindex:2,pgeClass:"form-control",tabCallback:"password",pgeOnkeydown:"App.uppassking()"});window.onload=function(){pgeditor.pgInitialize()};
var pgeditor=new $.pge({pgePath:"../../js/ocx/",pgeId:"password",pgeEdittype:0,pgeEreg1:"^[0-9]+$",pgeEreg2:"[\\s\\S]{6}",pgeMaxlength:6,pgeTabindex:2,pgeClass:"form-control",tabCallback:"password",pgeOnkeydown:"App.uppassking()"});window.onload=function(){pgeditor.pgInitialize()};
