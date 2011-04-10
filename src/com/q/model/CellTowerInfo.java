package com.q.model;

public class CellTowerInfo {
 private int cid;
 private int lac;
 private int signal_strength;
 private int sid;
 private int nid;
 private int bid;
 
 private String radio_type;
 private String mobile_network_code;
 
 public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getLac() {
		return lac;
	}
	public void setLac(int lac) {
		this.lac = lac;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
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
	 
	public int getSignal_strength() {
		return signal_strength;
	}
	public void setSignal_strength(int signalStrength) {
		signal_strength = signalStrength;
	}
}
