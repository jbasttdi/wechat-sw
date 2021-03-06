package com.desksoft.wechat.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.desksoft.wechat.common.model.otherbean.ScoketCode;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

/**
 * 优普 自来水内部系统socket调用接口
 * @author Joker
 *
 */
public class YouPuSocketService {

	

	/**
	 * 用户信息查询 1002
	 * 
	 * @param userNo
	 * @return  String
	 */
	public static String getUserInfoData(String userNo) {
		// 交易码
		String code = "1002";
		String senderHead = getReportHead(code, userNo.length());
		String senderStr = senderHead + "|" + userNo;
		return getSocketCallbackStr(senderStr);
	}


	/**
	 * 1001缴费申请 ：查询欠费信息
	 * 
	 * @param userNo  户号
	 * @param startDate  开始日期
	 * @param endDate  结束日期
	 * @return
	 */
	public static String getpayData(String userNo, String startDate, String endDate) {
	 //if(StrKit.isBlank(startDate)){ startDate = "2016-01"; }
		// 交易码
		String code = "1001";
		//报文体
		String temp = userNo + "|" + startDate + "|" + endDate;
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		System.out.println(senderStr);
		return getSocketCallbackStr(senderStr);
	}

	/**
	 * 1003用水情况 默认是一年 最近一年
	 * 
	 * @param userNo 户号
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getUserWaterInfo(String userNo, String startDate, String endDate) {
		// 交易码
		String code = "1003";
		String temp = userNo + "|" + startDate + "|" + endDate;
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		System.out.println(senderStr);
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 * 2001 缴费确认
	 * 
	 * @param orderNo 进账流水号
	 * @param realNumber 笔数
	 * @param realAddr 水表安装地址
	 * @param realPay  实收费
	 * @param realWorkNo 工号
	 * @param payDate 收费日期
	 * @param feeID  收费ID
	 * @return
	 */
	public static String getchkpay(String orderNo, String realNumber,String realPay,
			 String payDate,String feeIDs) {
		String realAddr = "WX";
		String  realWorkNo = "9994";
		return getchkpay(orderNo, realNumber, realAddr, realPay, realWorkNo, payDate, feeIDs);
	}
	
	/**
	 * 2001 缴费确认
	 * 
	 * @param orderNo 进账流水号
	 * @param realNumber 笔数
	 * @param realAddr 水表安装地址
	 * @param realPay  实收费
	 * @param realWorkNo 工号
	 * @param payDate 收费日期
	 * @param feeID  收费ID
	 * @return
	 */
	public static String getchkpay(String orderNo, String realNumber, String realAddr, String realPay,
			String realWorkNo, String payDate, String feeIDs) {
		// 交易码
		String code = "2001";
		
		String temp = orderNo + "|" + realNumber + "|" + realAddr + "|" 
		+ realPay + "|" + realWorkNo + "|" + payDate + "|" + feeIDs;
		
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 * 2002 冲正 //毙掉
	 * 
	 * @param orderNo
	 * @param realPay
	 * @param payDate
	 * @return
	 */
	private static String getFlushes (String orderNo,String realPay, String payDate) {
		// 交易码
		String code = "2002";
		String temp = realPay  + "|"+ orderNo  + "|" + payDate ;
		
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		System.out.println("send:"+senderStr);
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 *  2003 充值
	 * 
	 * @param userId 客户号
	 * @param orderNo 进账流水
	 * @param realPay 实收款
	 * @param payDate 收款日期
	 * @return
	 */
	public static String getRePayData(String userId, String orderNo,  String realPay,String payDate) {
		// 交易码
		String code = "2003";
		String temp = userId + "|" + orderNo + "|" + realPay + "|" + payDate ;
		
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 * 缴费对账 2004
	 * 
	 * @param accountDate 对账日期(清算日)
	 * @param fileName  对账文件名称
	 * @param length  对账文件长度
	 * @return
	 */
	public static String getChkAccountDataForPay(String accountDate, String fileName,  String length) {
		// 交易码
		String code = "2004";
		String temp = accountDate + "|" + fileName + "|" + length ;
		
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		System.out.println("send:"+senderStr);
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 * 预存对账 2005
	 * 
	 * @param accountDate 对账日期(清算日)
	 * @param fileName  对账文件名称
	 * @param length  对账文件长度
	 * @return
	 */
	public static String getChkAccountDataForrePay(String accountDate, String fileName,  String length) {
		// 交易码
		String code = "2005";
		String temp = accountDate + "|" + fileName + "|" + length ;
		
		// 报文头
		String senderHead = getReportHead(code, temp.length());
		String senderStr = senderHead + "|" + temp;
		System.out.println("send:"+senderStr);
		return getSocketCallbackStr(senderStr);
	}
	
	/**
	 * 返回解析的字符串数据List<String>
	 * @param backString
	 * @return
	 */
	public static List<String> getBackList(String backString){
		String[] temps = backString.split("\\|");
		List<String> list = Arrays.asList(temps);
		return list;
	}
	
	
	/**
	 * 是否查询成功
	 * @param backString
	 * @return
	 */
	public static String  isSuccessConnect(String backString){
		if(StrKit.isBlank(backString)||backString.length()<40){
			return "";
		}
		String[] temps = backString.split("\\|");
		//响应码
		String ReturnCode  = temps[0].substring(42, 44);
		return ReturnCode;
	} 
	
	/**
	 * 获得返回消息
	 * @param backString
	 * @return
	 */
	public static String getBackMsg(String backString){
		if(isSuccessConnect(backString).length()==2){
			String[] temps = backString.split("\\|");
			//响应码
			String returnCode  = temps[0].substring(42, 44).trim();
			String key = "c"+returnCode;
			return ScoketCode.valueOf(key).getValue();
		}else{
			return "";
		}
	}
	
	
	/**
	 * 
	 * @param code
	 *            交易码
	 * @param length
	 *            报文体长度
	 * @return 报文头
	 */
	private static String getReportHead(String code, int length) {
		// 银行代码
		String bankCode = "SZWX01";
		//
		// 交易日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		//String time = "20160103";
		// 报文长度 自动填充12位
		String strLength = String.format("%012d", length);
		// 报文头
		String senderHead = code + bankCode + time + strLength + "000000000000" + "  " + "                  ";
		return senderHead;
	}

	/**
	 * 接口调用
	 * 
	 * @param sendData
	 * @return 
	 */
	private static String getSocketCallbackStr(String sendData) {

		String temp = "";
		byte[] data = sendData.getBytes();
		Socket socket = null;
		try {
			PropKit.use("jfinal_config.txt");
			socket = new Socket(PropKit.get("yp_ip","60.190.200.54"), PropKit.getInt("yp_port",8002));
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(data);
			// receive
			byte[] recdata = new byte[2000];
			in.read(recdata);
			System.out.println("recv:" + new String(recdata, "gb2312"));
			temp = new String(recdata, "gb2312");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
		public static void main(String[] args) {

	// 户号
	String userNo = "93835";//810023 5962 904961 
	//getUserInfoData(userNo);
	getpayData(userNo, null, null);
/*	String accountDate = "2016-03-17";
	String fileName = "SZWX0120160317.YCDZ";
	String length = "73";
	
	getChkAccountDataForPay(accountDate, fileName, length);*/

	}	
}
