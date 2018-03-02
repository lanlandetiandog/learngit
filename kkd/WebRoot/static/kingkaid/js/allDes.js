// JavaScript Document
/*条件控制*/
$(document).ready(function(){
  $(".des_btnl").click(function(){
    $(".des_btnl_arrow,.data_chart").css("display","block");
	$(".des_btnr_arrow,.infoReport").css("display","none");
  });
  $(".des_btnr").click(function(){
    $(".des_btnr_arrow,.infoReport").css("display","block");
	$(".des_btnl_arrow,.data_chart").css("display","none");	
  });
  loadinfoReport();
});
function loadinfoReport(){
		$.ajax({
			url: ctx+"/website/getYearReport",
			type: "POST",
			dataType: "json",
			success: function(data){
				var yearreport = '';				
				for(var i = 0; i < data.length; i ++){
					yearreport+= '<div class="yReport fl">';
					yearreport+= '   <div class="yImg"><img src="'+data[i].reportpic+'"></div>';
					yearreport+= '   <p class="ytxt">';
					yearreport+= '     <span class="fl">'+data[i].reportname+'</span>';
			/**		yearreport+= '     <a href="#" onclick="javascript:window.open(\''+ctx+'/static/plugin/generic/web/viewer.html?file='+data[i].reportcontent+'\',\'PDF\');" class="fr cobl">点击查看</a>';
			 * 
			 */
					yearreport+= '     <a onclick="_hmt.push([\'_trackEvent\',\'nav\',\'click\',\'report\']);" href="'+data[i].reportcontent+'" target="_blank" class="fr cobl">点击查看</a>'
					yearreport+= '   </p>';
					yearreport+= '</div>';
				}
				$("#infoReport").append(yearreport);;
			}
		})
	}
