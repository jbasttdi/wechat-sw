package com.desksoft.wechat.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseShutoffwaterflow<M extends BaseShutoffwaterflow<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setKeyword1(java.lang.String keyword1) {
		set("keyword1", keyword1);
	}

	public java.lang.String getKeyword1() {
		return get("keyword1");
	}

	public void setKeyword2(java.lang.String keyword2) {
		set("keyword2", keyword2);
	}

	public java.lang.String getKeyword2() {
		return get("keyword2");
	}

	public void setKeyword3(java.lang.String keyword3) {
		set("keyword3", keyword3);
	}

	public java.lang.String getKeyword3() {
		return get("keyword3");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public void setMouldID(java.lang.String mouldID) {
		set("mouldID", mouldID);
	}

	public java.lang.String getMouldID() {
		return get("mouldID");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		set("isDelete", isDelete);
	}

	public java.lang.Integer getIsDelete() {
		return get("isDelete");
	}

}
