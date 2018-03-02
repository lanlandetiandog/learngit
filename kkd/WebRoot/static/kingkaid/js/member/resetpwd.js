$(document).ready(function(){
    $(".right-top").click(function(){
        $(".login-box").hide();
        $(".login-two-code").show();
    });
    $(".right-top-view").click(function(){
        $(".login-box").show();
        $(".login-two-code").hide();
    });

    $(".recharge-type").click(function(){
        var curid=$(this).attr("id")
        $(this).addClass("recharge-type-cur").siblings().removeClass("recharge-type-cur");
        if(curid=="mobile_found"){
            $(".mobile_found").show();
            $(".mail_found").hide();
        }
        else{
            $(".mail_found").show();
            $(".mobile_found").hide();
        }
    });
    
    var reset_pwd_form = $("#reset_pwd_form").validate({
        rules: {
        	loginPassword: {
        		required: true,
                pattern: /^(?![a-zA-Z]+$)(?!\d+$)[A-Za-z0-9~!@#$%^&*()_+`\-={}:";'<>?,.\/]{8,20}$/
            },
            dPassword: {
            	equalTo: "#loginPassword"
            }
        },
        messages: {
        	loginPassword: {
                required: "请输入密码",
                pattern: '必须是8-20位字母、数字和符号的组合',
            },
            dPassword: {
                equalTo: "两个密码须保持一致"
            }
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
            //error.appendTo(element.nextUntil(null, "div"));
            error.appendTo(element.parent());
        }
    });
});