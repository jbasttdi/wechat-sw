package com.desksoft.wechat.common.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.desksoft.wechat.common.model.base.BasePayrecord;
import com.desksoft.wechat.service.StrSQLService;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Payrecord extends BasePayrecord<Payrecord> {
	
	public static final Payrecord dao = new Payrecord();
	
	
	/**
	 * @param type 类型 缴费1A/预存2B
	 * @param openId 微信号
	 * @param userNo 户号
	 * @param fee 金额
	 * @param userid 用户表ID 
	 * @param remark 账单号 A/B+日期+“-”+卡号
	 * @return Boolean 是否插入成功
	 */
	public boolean addPayRecord(int type,String openId,String userNo,int fee,int userid,String remark,int num,String feeIDs){
		
		boolean flag = new Payrecord().set("type", type)
		.set("openId", openId).set("userNo", userNo)
		.set("fee", fee).set("userid", userid)
		.set("remark", remark).set("isDelete", 1)
		.set("realNumber", num).set("feeIDs", feeIDs)
		.set("isChk", 0).set("updateTime", new Date())
		.set("createTime", new Date())
		.save();
		return flag;
	}
	
	
	public Page<Payrecord> getPayRecordPaginateList(int pageNumber, int pageSize,Date startDate,Date endDate,int userNo) {
		String SQL = "from payrecord where isdelete = 1 ";
		SQL = StrSQLService.getSQLByDatePorid(startDate, endDate, SQL);
		if(userNo>0){
			SQL += " and userNo =" + userNo  ;
		}
		SQL +=" order by id asc";
		return paginate(pageNumber, pageSize, "select *", SQL);
	}
	
	public List<Payrecord> getPayRecordListById(int type){
		return getPayRecordListById(0,type);
	}
	
	/**
	 * @param id
	 * @return List<PayRecord>
	 *  前一天的所有信息
	 */
	public List<Payrecord> getPayRecordListById(int miniID,int type){
		return getPayRecordListById(miniID,type,0,10);
	}
	
	/**
	 * @param miniID
	 * @param index
	 * @param num
	 * @return   前一天的所有信息
	 */
	public List<Payrecord> getPayRecordListById(int miniID,int type,int index, int num){
		//查询前一天where id > ? and 	limit ?,?,index,num  当天所有的已付款未对账
		 String sql = "select * from payrecord  where date(createTime)=curdate()-INTERVAL 1 day and isChk=0 and isPaySuccess=1 and remark is not null and type=? "; 
		 List<Payrecord> list = dao.find(sql,type);
		 return list;
	}
	
	public Payrecord getPayRecordByRemark(String remark){
		return dao.findFirst("select * from payrecord where remark = ? ", remark);
	}
	
}
