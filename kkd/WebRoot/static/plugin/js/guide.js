
$(function(){

    showGuide();
    $(".guide1_next").click(function(){
        $(".guide_step1").hide();
        $(".guide_step2").show();
    })
    $(".guide2_next").click(function(){
        $(".guide_step2").hide();
        $(".guide_step3").show();
    })
    $(".guide3_next").click(function(){
        $(".guide_step3").hide();
        $(".guide_step4").show();
    })
    $(".guide4_next").click(function(){
        closeGuide();
    })

})

//关闭弹出框
	function closeGuide() {
        $("#dark_bg").remove();
        $(".guide_content").remove();
    }

//显示新手指导
    function showGuide(){
        var guide ='<div class="guide_content">'; 
            guide +='   <a class="close-guide" onclick="closeGuide()"><img src="'+ctx+'/static/kingkaid/images/close_yindao.png"/></a>';
            guide +='   <div class="guide_step1">';
            guide +='       <a class="guide1_next"></a>';
            guide +='   </div>';
            guide +='   <div class="guide_step2">';
            guide +='       <a class="guide2_next"></a>';
            guide +='   </div>';
            guide +='   <div class="guide_step3">';
            guide +='        <a class="guide3_next"></a>';
            guide +='   </div>';
            guide +='   <div class="guide_step4">';
            guide +='       <a class="guide4_next"></a>';
            guide +='   </div>';
            guide +='</div>';
    		
		create_bg();	
		$("body").append(guide);
    }




