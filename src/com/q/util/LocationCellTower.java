package com.q.util;
import java.util.ArrayList;
import java.util.List;  

import com.q.model.CellTowerInfo;
import com.q.model.LocationInfo;

import android.content.Context;  
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
public class LocationCellTower  {
	TelephonyManager tm;
	public LocationCellTower(Context context ){
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	 /* 
	#    * 唯一的设备ID： 
	#    * GSM手机的 IMEI 和 CDMA手机的 MEID.  
	#    * Return null if device ID is not available. 
	#    */   
    public String getDeviceId(){
    	return tm.getDeviceId();
    }
      /* 
    #    * cdma电话方位： 
    #    *  
    #    */  
    public CdmaCellLocation getCdmaCellLocation(){
     return ((CdmaCellLocation)tm.getCellLocation());//CellLocation  
    }
    /* 
    #    * gsm电话方位： 
    #    *  
    #    */  
    public CellLocation getGsmCellLocation(){
     return ((GsmCellLocation)tm.getCellLocation());//CellLocation  
    }
     /* 
    #    * 附近的电话的信息: 
    #    * 类型：List<NeighboringCellInfo>  
    #    * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES 
    #    */  
    public List<NeighboringCellInfo>getNeighboringCellInfo(){
     return  tm.getNeighboringCellInfo();//List<NeighboringCellInfo> 
    }
       /* 
    #    * 获取ISO标准的国家码，即国际长途区号。 
    #    * 注意：仅当用户已在网络注册后有效。 
    #    *       在CDMA网络中结果也许不可靠。 
    #    */  
    public String getNetworkCountryIso(){
      return  tm.getNetworkCountryIso();//String  
      
    } 
       /* 
    #    * MCC+MNC(mobile country code + mobile network code) 
    #    * 注意：仅当用户已在网络注册时有效。 
    #    *    在CDMA网络中结果也许不可靠。 
    #    */  
    public String getNetworkOperator(){
      return tm.getNetworkOperator();
    }
     /* 
    #    * 当前使用的网络类型： 
    #    * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0 
    #      NETWORK_TYPE_GPRS     GPRS网络  1 
    #      NETWORK_TYPE_EDGE     EDGE网络  2 
    #      NETWORK_TYPE_UMTS     UMTS网络  3 
    #      NETWORK_TYPE_HSDPA    HSDPA网络  8  
    #      NETWORK_TYPE_HSUPA    HSUPA网络  9 
    #      NETWORK_TYPE_HSPA     HSPA网络  10 
    #      NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4 
    #      NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5 
    #      NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6 
    #      NETWORK_TYPE_1xRTT    1xRTT网络  7 
    #    */  
    public int getNetworkType(){
      return tm.getNetworkType();//int  
    }
    public void getDetailed(NeighboringCellInfo info){
    	info.getCid();
    	info.getLac();
    	info.getNetworkType();
    }
    public ArrayList<CellTowerInfo> getNearGsm(){
    	List<NeighboringCellInfo>temp=getNeighboringCellInfo();
    	ArrayList<CellTowerInfo>data=new ArrayList<CellTowerInfo>();
    	int max=-300;
    	int maxNum=-1;
    	for(int i=0;i<temp.size();i++){
    		NeighboringCellInfo info=temp.get(i);
                int value= info.getRssi()*2-113;
                if(value>max){
                	maxNum=i;               	
                }
    	}
    	if(maxNum!=-1&&maxNum!=0){
    		NeighboringCellInfo tempValue=temp.get(0);
    		temp.set(0, temp.get(maxNum));
    		temp.set(maxNum, tempValue);
    	}
	    for(int i=0;i<temp.size();i++){
    		NeighboringCellInfo info=temp.get(i);
    		CellTowerInfo tempdata=new CellTowerInfo();
    		tempdata.setCid(info.getCid());
    		tempdata.setLac(info.getLac());
    		tempdata.setSignal_strength(info.getRssi()*2-113);
    		data.add(tempdata);
    	}
    	return data;
    }
    public ArrayList<CellTowerInfo>getNearCdma(){
    	ArrayList<CellTowerInfo>data=new ArrayList<CellTowerInfo>();
    	CdmaCellLocation temp=getCdmaCellLocation();
    	CellTowerInfo info=new CellTowerInfo();
    	info.setBid(temp.getBaseStationId());
    	info.setNid(temp.getNetworkId());
    	info.setSid(temp.getSystemId());
    	data.add(info);
    	return data;
    }
    public LocationInfo getCdmaLocation(){
    	CdmaCellLocation tempcdma=getCdmaCellLocation();
    	LocationInfo temp=new LocationInfo();
    	temp.setLatitude(tempcdma.getBaseStationLatitude());
    	temp.setLongitude(tempcdma.getBaseStationLongitude());
    	return temp;
    }
}
