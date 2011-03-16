package com.q.model;

public class CellTowerInfo {
 private String cid;
 private String lac;
 
 
 private String sid;
 private String nid;
 private String bid;
 
 private String radio_type;
 private String mobile_network_code;
 
 public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getRadio_type() {
		return radio_type;
	}
	public void setRadio_type(String radioType) {
		radio_type = radioType;
	}
	public String getMobile_network_code() {
		return mobile_network_code;
	}
	public void setMobile_network_code(String mobileNetworkCode) {
		mobile_network_code = mobileNetworkCode;
	}
}
