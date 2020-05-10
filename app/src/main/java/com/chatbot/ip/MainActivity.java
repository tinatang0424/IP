package com.chatbot.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ScanResult> mWifiScanResultList;
    List<WifiConfiguration> mWifiConfigurationList;
    WifiInfo mWifiInfo;
    private static String SSID; //Wi-Fi名稱
    private static int LEVEL; //Wi-Fi訊號強弱
    private static int NETWORKID; //Wi-Fi連線ID
    private static int IPADRRESS; //Wi-Fi連線位置
    private static String IP; //Wi-Fi IP位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //首先取得Wi-Fi服務控制Manager
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiDetect(mWifiManager);

        //Show IP
        TextView tv = findViewById(R.id.tv_ip);
        tv.setText(IP);

    }

    private void WifiDetect(WifiManager mWifiManager) {
        //※ Wi-Fi偵測 :
        // 先判斷是否有開啟Wi-Fi，有開啟則回傳true沒有則回傳false
        if(mWifiManager.isWifiEnabled())
        {
            //重新掃描Wi-Fi資訊
            mWifiManager.startScan();
            //偵測周圍的Wi-Fi環境(因為會有很多組Wi-Fi，所以型態為List)
            mWifiScanResultList = mWifiManager.getScanResults();
            //手機內已存的Wi-Fi資訊(因為會有很多組Wi-Fi，所以型態為List)
            mWifiConfigurationList = mWifiManager.getConfiguredNetworks();
            //目前已連線的Wi-Fi資訊
            mWifiInfo = mWifiManager.getConnectionInfo();

            for(int i = 0 ; i < mWifiScanResultList.size() ; i++ )
            {
                //手機目前周圍的Wi-Fi環境
                SSID = mWifiScanResultList.get(i).SSID ; //SSID (Wi-Fi名稱)
                LEVEL = mWifiScanResultList.get(i).level ; //LEVEL (Wi-Fi訊號強弱)
            }

            for(int i = 0 ; i < mWifiConfigurationList.size() ; i++ )
            {
                //手機內已儲存(已連線過)的Wi-Fi資訊
                SSID = mWifiConfigurationList.get(i).SSID ;
                NETWORKID = mWifiConfigurationList.get(i).networkId ; //NETWORKID (Wi-Fi連線ID)
            }

            //目前手機已連線(現在連線)的Wi-Fi資訊
            SSID = mWifiInfo.getSSID() ;
            NETWORKID = mWifiInfo.getNetworkId() ;
            IPADRRESS = mWifiInfo.getIpAddress() ; //IPADRRESS (Wi-Fi連線位置)
            IP = String.format("%d.%d.%d.%d", (IPADRRESS & 0xff), (IPADRRESS >> 8 & 0xff), (IPADRRESS >> 16 & 0xff),( IPADRRESS >> 24 & 0xff)) ; //(Wi-Fi IP位置)
        }
        else
        {
            //把Wi-Fi開啟
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(MainActivity.this, "Wi-Fi開啟中", Toast.LENGTH_SHORT).show();
        }

    }

}
