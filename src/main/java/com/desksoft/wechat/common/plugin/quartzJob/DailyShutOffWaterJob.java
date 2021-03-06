package com.desksoft.wechat.common.plugin.quartzJob;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.desksoft.wechat.common.model.Shutoffwaterflow;
import com.desksoft.wechat.common.model.Userbindflow;
import com.desksoft.wechat.common.model.otherbean.CallBackJson;
import com.desksoft.wechat.common.model.otherbean.template.DataItem;
import com.desksoft.wechat.common.model.otherbean.template.TempItem;
import com.desksoft.wechat.common.plugin.Scheduled;
import com.desksoft.wechat.service.TempToJson;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;
/**
 * @author Joker
 * 水务集团的停水模板
 * 
 * success
 */

@Scheduled(fixedDelay=1000*1800) // 每1800秒

public class DailyShutOffWaterJob implements Job {
	
	private static Logger log = Logger.getLogger(DailyShutOffWaterJob.class);
	
	//停水模板ID
	//private static final String moudleId="BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY";
	
	//每批次处理条数
	private static final int maxSize = 1000;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		// 1. 获得推送停水消息,一般不会超过10条. state=0 表示没有推送消息
		List<Shutoffwaterflow> list  = Shutoffwaterflow.dao.getShutoffwaterflowList(0);
		for(Shutoffwaterflow sfWaterFlow : list ){
			//每次取maxSize条,处理。sfWaterFlow充当停水通知模板
			int maxID = 0;
			List<Userbindflow> list2 = Userbindflow.me.getUserBindFlowListById(maxID);
			if(list2.size()>0){
				Shutoffwaterflow.dao.findByIdLoadColumns(sfWaterFlow.getId(), "id,state,mouldID").set("state", 1).set("mouldID", PropKit.get("wx_mouldID_DailyShutOffWaterJob","BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY")).update();
				dealDailyShutOffWater(maxID,sfWaterFlow);
			}
		}
		
	}
	
	
	/**
	 * 处理一个批次10个用户推送
	 * 循环
	 * @param maxID  
	 * @return flag maxID  Map<String,Object>
	 */
	private void dealDailyShutOffWater(int maxID,Shutoffwaterflow sfWaterFlow){
		
		List<Userbindflow> list = Userbindflow.me.getUserBindFlowListById(maxID);
		if(list.size()<=0){
			Shutoffwaterflow.dao.findByIdLoadColumns(sfWaterFlow.getId(), "id,state").set("state", 2).update();
			log.info("当日所有消息推送完毕"+System.currentTimeMillis());
			return;
		}
		DataItem dataItem = getShutOffWaterMudle(sfWaterFlow);
		//处理一个批次10个支付记录
		try {
			//获取分批所有的绑定自来水户号的且关注微信的用户，分别给他们微信号发送
			for(Userbindflow userBindFlow:list){
				String openId=userBindFlow.getOpenID();
				if(StrKit.notBlank(openId)&&openId.length()==28){
					//生成群发模板
					String json=TempToJson.getTempJson(openId, PropKit.get("wx_mouldID_DailyShutOffWaterJob","BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY"),"#FF0000", "", dataItem);
					System.out.println(json);
					getApiConfig();
					ApiResult apiResult = TemplateMsgApi.send(json);
					//如果发送失败
					if(!checking(apiResult)){
						log.info("发送失败的id:"+userBindFlow.getId()+"error:"+apiResult.getJson());
					}
				}
				//System.out.println(apiResult.getJson());
			}
			log.info("成功处理1000个"+System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			if(list.size()>0){
				log.debug(System.currentTimeMillis()+"出现异常，最小id是："+list.get(0).getId()+"此次停水通知的ID"+sfWaterFlow.getId());
			}else{
				log.debug(System.currentTimeMillis()+"出现异常"+"此次停水通知的ID"+sfWaterFlow.getId());
			}
			
		}
		
		if(list.size()==maxSize){
			Userbindflow pr = list.get(list.size()-1);
			 
			 if(null!=pr && null!=pr.getId() && pr.getId()>0){
				 
				 log.debug("maxID:"+pr.getId());
				 
				 //循环
				 dealDailyShutOffWater(pr.getId(),sfWaterFlow);
			 }
		}else{
			Shutoffwaterflow.dao.findByIdLoadColumns(sfWaterFlow.getId(), "id,state").set("state", 2).update();
			log.info("当日所有消息推送完毕"+System.currentTimeMillis());
		}
	}
	
	/**
	 * 检测是否发送成功
	 * @param ApiResult apiResult
	 * @return Boolean
	 */
	public boolean checking(ApiResult apiResult){
		boolean flag = false;
		CallBackJson callBackJson= JsonKit.parse(apiResult.getJson(), CallBackJson.class);
		int status=callBackJson.getErrcode();
		//String errmsg=jsonObject.getString("errmsg");
		if (status==0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 停水模板
	 * @return DataItem
	 */
	public static DataItem getShutOffWaterMudle(Shutoffwaterflow sfWaterFlow){
		DataItem dataItem=new DataItem();
		dataItem.setKeyword1(new TempItem(sfWaterFlow.getKeyword1(),"#FF0000"));
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		//String time=sdf.format(new Date());
		dataItem.setKeyword2(new TempItem(sfWaterFlow.getKeyword2(), "#0000FF"));
		dataItem.setKeyword3(new TempItem(sfWaterFlow.getKeyword3(),"#FF0000"));
		dataItem.setRemark(new TempItem(sfWaterFlow.getRemark(), "#008000"));
		return dataItem;
	}
	
	
	/**
	 * 	// 配置微信 API 相关常量
	 */
	public static void getApiConfig(){
		PropKit.use("jfinal_config.txt");
		ApiConfig ac = new ApiConfig();
		ac.setAppId(PropKit.get("appId").trim());
		ac.setAppSecret(PropKit.get("appSecret").trim());
	    ApiConfigKit.setThreadLocalApiConfig(ac);
	}
}
