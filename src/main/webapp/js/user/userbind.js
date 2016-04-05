$(function() {
	
	$("#search").click(function(){
		//校验
		if(isNaN($("#userNo").val())){
			alert("户号格式输入非法！");
			return false;
		}
		if($("#startDate").val()==""&&$("#userNo").val()==""&&$("#endDate").val()==""){
			alert("输入查询条件");
			return false;
		}
		if($("#startDate").val()!=""&&$("#endDate").val()!="" ){
			if(!checkEndTime()){
				return false;
			}
			
		}
		
		var href = $("#_context").val()+HREFURL+"?pageIndex=1&";
		if($("#userNo").val()==""){
			href +="userNo=0"
		}else{
			href +="userNo="+$("#userNo").val();
		}
		if($("#startDate").val()!=""){
			href +="&startDate="+$("#startDate").val();
		}else{
			href +="&startDate=default";
		}
		if($("#endDate").val()!=""){
			href +="&endDate="+$("#endDate").val();
		}else{
			href +="&endDate=default";
		}
		window.location.href = href;
		
	});
	
});

//想全部绑定用户发送停水信息
function sendShutOffWaterInfoFunc(){
	window.location.href = $("#_context").val()+"/shutOffWater/editShutOffWaterInfo";
}


function checkEndTime(){  
    var startTime=$("#startDate").val();  
    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
    var endTime=$("#endDate").val();  
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
    if(end<start){ 
    	alert("结束时间必须大于开始时间");
        return false;  
    }  
    return true;  
} 



