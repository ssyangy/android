package com.q.util;
import java.util.List;  
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
	#    * Ψһ���豸ID�� 
	#    * GSM�ֻ��� IMEI �� CDMA�ֻ��� MEID.  
	#    * Return null if device ID is not available. 
	#    */   
    public String getDeviceId(){
    	return tm.getDeviceId();
    }
      /* 
    #    * cdma�绰��λ�� 
    #    *  
    #    */  
    public CellLocation getCdmaCellLocation(){
     return ((CdmaCellLocation)tm.getCellLocation());//CellLocation  
    }
    /* 
    #    * gsm�绰��λ�� 
    #    *  
    #    */  
    public CellLocation getGsmCellLocation(){
     return ((GsmCellLocation)tm.getCellLocation());//CellLocation  
    }
     /* 
    #    * �����ĵ绰����Ϣ: 
    #    * ���ͣ�List<NeighboringCellInfo>  
    #    * ��ҪȨ�ޣ�android.Manifest.permission#ACCESS_COARSE_UPDATES 
    #    */  
    public List<NeighboringCellInfo>getNeighboringCellInfo(){
     return  tm.getNeighboringCellInfo();//List<NeighboringCellInfo> 
    }
       /* 
    #    * ��ȡISO��׼�Ĺ����룬�����ʳ�;���š� 
    #    * ע�⣺�����û���������ע�����Ч�� 
    #    *       ��CDMA�����н��Ҳ���ɿ��� 
    #    */  
    public String getNetworkCountryIso(){
      return  tm.getNetworkCountryIso();//String  
      
    } 
       /* 
    #    * MCC+MNC(mobile country code + mobile network code) 
    #    * ע�⣺�����û���������ע��ʱ��Ч�� 
    #    *    ��CDMA�����н��Ҳ���ɿ��� 
    #    */  
    public String getNetworkOperator(){
      return tm.getNetworkOperator();
    }
     /* 
    #    * ��ǰʹ�õ��������ͣ� 
    #    * ���磺 NETWORK_TYPE_UNKNOWN  ��������δ֪  0 
    #      NETWORK_TYPE_GPRS     GPRS����  1 
    #      NETWORK_TYPE_EDGE     EDGE����  2 
    #      NETWORK_TYPE_UMTS     UMTS����  3 
    #      NETWORK_TYPE_HSDPA    HSDPA����  8  
    #      NETWORK_TYPE_HSUPA    HSUPA����  9 
    #      NETWORK_TYPE_HSPA     HSPA����  10 
    #      NETWORK_TYPE_CDMA     CDMA����,IS95A �� IS95B.  4 
    #      NETWORK_TYPE_EVDO_0   EVDO����, revision 0.  5 
    #      NETWORK_TYPE_EVDO_A   EVDO����, revision A.  6 
    #      NETWORK_TYPE_1xRTT    1xRTT����  7 
    #    */  
    public int getNetworkType(){
      return tm.getNetworkType();//int  
    }
    public void getDetailed(NeighboringCellInfo info){
    	info.getCid();
    	info.getLac();
    	info.getNetworkType();
    }
}
