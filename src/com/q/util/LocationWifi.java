package com.q.util;
import java.util.List;  
import android.content.Context;  
import android.net.wifi.ScanResult;  
import android.net.wifi.WifiConfiguration;  
import android.net.wifi.WifiInfo;  
import android.net.wifi.WifiManager;  
import android.net.wifi.WifiManager.WifiLock;  
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
	     public  LocationWifi(Context context)  
	     {  
	         //ȡ��WifiManager����  
	         mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
	         //ȡ��WifiInfo����  
	         mWifiInfo = mWifiManager.getConnectionInfo();  
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
