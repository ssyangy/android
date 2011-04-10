package com.q.model;

public class WifiTowerInfo {
  private String mac;
  private int signal_strength;
  private String name;

    public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getSignal_strength() {
		return signal_strength;
	}
	public void setSignal_strength(int signalStrength) {
		signal_strength = signalStrength;
	}
	public String getName() {
			return name;
		}
    public void setName(String name) {
			this.name = name;
	}
}
