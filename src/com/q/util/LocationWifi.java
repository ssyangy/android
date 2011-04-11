package com.q.util;
import java.util.ArrayList;
import java.util.List;  
import com.q.model.CellTowerInfo;
import com.q.model.WifiTowerInfo;
import android.content.Context;  
import android.net.wifi.ScanResult;  
import android.net.wifi.WifiConfiguration;  
import android.net.wifi.WifiInfo;  
import android.net.wifi.WifiManager;  
import android.net.wifi.WifiManager.WifiLock;  
import android.telephony.NeighboringCellInfo;
public class LocationWifi {
	  //����WifiManager����  
	     private WifiManager mWifiManager;  
	     //����WifiInfo����  
	     private WifiInfo mWifiInfo;  
	     //ɨ��������������б�  
	     private List<ScanResult> mWifiList;  
	     //���������б�  
	     private List<WifiConfiguration> mWifiConfiguration;  
	     //����һ��WifiLock  
	     WifiLock mWifiLock;  
	     //������  
	     public boolean isWifiOn(){
	    	 if(mWifiList==null){
	    		 return false;
	    	 }
	    	 return true;
	     }
	     public  LocationWifi(Context context)  
	     {  
	         //ȡ��WifiManager����  
	         mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
	         //ȡ��WifiInfo����  
	         mWifiInfo = mWifiManager.getConnectionInfo();  
	         StartScan();
	        
	     }
	     public  ArrayList<WifiTowerInfo> getNearWifi(){
	    	ArrayList<WifiTowerInfo>temp=new ArrayList<WifiTowerInfo>();
                 for(int i=0;i<mWifiList.size();i++){
	        	 ScanResult now=mWifiList.get(i);
	        	 WifiTowerInfo wifi=new WifiTowerInfo();
	        	 wifi.setMac(now.BSSID);
	        	 wifi.setSignal_strength(now.level);
	        	 wifi.setName(now.SSID);	
	        	 temp.add(wifi);
	         }
	    	 return temp;
	     }

	     //��WIFI  
	     public void OpenWifi()  
	     {  
	         if (!mWifiManager.isWifiEnabled())  
	         {  
	             mWifiManager.setWifiEnabled(true);  
	               
	         }  
	     }  
	     //�ر�WIFI  
	     public void CloseWifi()  
	     {  
	         if (!mWifiManager.isWifiEnabled())  
	         {  
	             mWifiManager.setWifiEnabled(false);   
	         }  
	     }  
	     //����WifiLock  
	     public void AcquireWifiLock()  
	     {  
	         mWifiLock.acquire();  
	     }  
	     //����WifiLock  
	     public void ReleaseWifiLock()  
	     {  
	         //�ж�ʱ������  
	         if (mWifiLock.isHeld())  
	         {  
	             mWifiLock.acquire();  
	         }  
	     }  
	     //����һ��WifiLock  
	     public void CreatWifiLock()  
	     {  
	         mWifiLock = mWifiManager.createWifiLock("Test");  
	     }  
	     //�õ����úõ�����  
	     public List<WifiConfiguration> GetConfiguration()  
	     {  
	         return mWifiConfiguration;  
	     }  
	     //ָ�����úõ������������  
	     public void ConnectConfiguration(int index)  
	     {  
	         //�����������úõ�������������  
	         if(index > mWifiConfiguration.size())  
	         {  
	             return;  
	         }  
	         //�������úõ�ָ��ID������  
	         mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);  
	     }  
	     public void StartScan()  
	     {  
	         mWifiManager.startScan();  
	         //�õ�ɨ����  
	         mWifiList = mWifiManager.getScanResults();  
	         //�õ����úõ���������  
	         mWifiConfiguration = mWifiManager.getConfiguredNetworks();  
	     }  
	     //�õ������б�  
	     public List<ScanResult> GetWifiList()  
	     {  
	         return mWifiList;  
	     }  
	     //�鿴ɨ����  
	     public StringBuilder LookUpScan()  
	     {  
	         StringBuilder stringBuilder = new StringBuilder();  
	         for (int i = 0; i < mWifiList.size(); i++)  
	         {  
	             stringBuilder.append("Index_"+new Integer(i + 1).toString() + ":");  
	             //��ScanResult��Ϣת����һ���ַ�����  
	             //���аѰ�����BSSID��SSID��capabilities��frequency��level  
	             stringBuilder.append((mWifiList.get(i)).toString());  
	             stringBuilder.append("\n");  
	         }  
	         return stringBuilder;  
	     }  
	     //�õ�MAC��ַ  
	     public String GetMacAddress()  
	     {  
	         return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();  
	     }  
	     //�õ�������BSSID  
	     public String GetBSSID()  
	     {  
	         return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();  
	     }  
	     //�õ�IP��ַ  
	     public int GetIPAddress()  
	     {  
	         return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();  
	     }  
	     //�õ����ӵ�ID  
	     public int GetNetworkId()  
	     {  
	         return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();  
	     }  
	     //�õ�WifiInfo��������Ϣ��  
	     public String GetWifiInfo()  
	     {  
	         return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();  
	         
	     }  
	     //���һ�����粢����  
	     public void AddNetwork(WifiConfiguration wcg)  
	     {  
	         int wcgID = mWifiManager.addNetwork(wcg);   
	         mWifiManager.enableNetwork(wcgID, true);   
	     }  
	     //�Ͽ�ָ��ID������  
	     public void DisconnectWifi(int netId)  
	     {  
	         mWifiManager.disableNetwork(netId);  
	         mWifiManager.disconnect();  
         }  
}
