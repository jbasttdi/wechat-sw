package com.desksoft.wechat.common.model.otherbean;

public class CallBackJson {
	private int errcode;// 返回码
	private String errmsg;// 信息
	private String msgid;// id

	public CallBackJson() {
	}

	public CallBackJson(int errcode, String errmsg, String msgid) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.msgid = msgid;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

}