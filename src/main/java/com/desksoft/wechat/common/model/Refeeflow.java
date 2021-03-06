package com.desksoft.wechat.common.model;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.desksoft.wechat.common.model.base.BaseRefeeflow;
import com.desksoft.wechat.service.StrSQLService;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Refeeflow extends BaseRefeeflow<Refeeflow> {
	
	public static final Refeeflow dao = new Refeeflow();
	
	public Page<Refeeflow> getRefeeFlowPaginateList(int pageNumber, int pageSize,Date startDate,Date endDate,String openID) {
		String SQL = "from refeeflow where isdelete = 1 ";
		SQL = StrSQLService.getSQLByDatePorid(startDate, endDate, SQL);
		if(StringUtils.isNotBlank(openID)&&!("default".equalsIgnoreCase(openID))){
			SQL += " and openID like '%" + openID + "%'" ;
		}
		SQL +=" order by id asc";
		return paginate(pageNumber, pageSize, "select *", SQL);
	}

	
	public boolean addRefeeFlow(String openid,String refeeOpenid,String Source){
		return new Refeeflow().set("openid", openid).set("refeeOpenid", refeeOpenid)
				.set("updateTime", new Date()).set("createTime",  new Date())
				.set("Source", Source).save();
	}
	
}
