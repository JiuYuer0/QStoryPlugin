import android.os.Environment;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import android.content.Context;
import android.os.Build;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
// 如果 getWifiStatus() 方法涉及到 WiFi 相关的操作，还需要以下导入：
import android.net.wifi.WifiManager;


//初始化
String msgChatec;

//初始化时间
long firstLoadTime = System.currentTimeMillis();


//本地账号
String MyUin = MyUin;


//脚本路径
String AppPath=AppPath+"";
//加载导入类
load(AppPath+"/xiaobei/shuiJi.java");
load(AppPath+"/xiaobei/qunguan.java");
load(AppPath+"/xiaobei/Mutualpraise.java");





//弹窗内容
import java.util.Calendar;

AddItem("开启/关闭本群助手","OpenTitle",PluginID);

AddItem("开启/关闭本群陪聊","OpenTitleWchao",PluginID);

AddItem("开启/关闭本群互赞","OpenTitleZhangwo",PluginID);



addMenuItem("测试", "OpenTitleyige");


public void OpenTitleyige(Object msg){
Toast(msg.MessageContent);
}


public void OpenTitleZhangwo(String GroupUin)
{
   String groupUin = GroupUin;
    if ("1".equals(getString(groupUin, "互赞"))) {
    
	putString(GroupUin,"互赞","0");
	//提示
	Toast("本群互赞功能已关闭");
	} else {
	
	putString(GroupUin,"互赞","1");
	//提示
	Toast("本群互赞功能已开启");
	
	}
}




public void OpenTitleWchao(String GroupUin)
{
   String groupUin = GroupUin;
    if ("1".equals(getString(groupUin, "问好"))) {
    
	putString(GroupUin,"问好","0");
	//提示
	Toast("本群艾特陪聊已关闭");
   } else {
   
   
   putString(GroupUin,"问好","1");
	//提示
	Toast("本群艾特陪聊已开启");
   
   }
}




public void OpenTitle(String GroupUin)
{
   String groupUin = GroupUin;
    if ("1".equals(getString(groupUin, "群管"))) {
    
	putString(GroupUin,"群管","0");
	//提示
	Toast("本群助手已关闭");
	} else {
	
	putString(GroupUin,"群管","1");
	//提示
	Toast("本群助手已开启");
	
	}
	
}








public void onMsg(Object msg) {
    
    String groupUin = msg.GroupUin;
    boolean isSend = msg.IsSend;
    String sEnd = null;
    
    msgChatec = msg.MessageContent;    
    qq = msg.UserUin;
    qun = msg.GroupUin;
    
    if ("1".equals(getString(groupUin, "群管"))) {
    
             // 群管
     qunGuangLi(msg.GroupUin,msg.UserUin,msg.IsSend,msg);
                                                                             
    }
    
    
    if ("1".equals(getString(groupUin, "问好"))) {
    if (msg.MessageContent.matches("@.*")) {
    Toast("触发");
    for (String aaa : msg.mAtList) {
       if (aaa.matches(MyUin)) {
       
         chatGPTLI(msg);
       }
     }
    }
    }

     //互赞
     Mutualp(msg);
}


public void OnTroopEvent(String GroupUin, String UserUin, int type)   {
// Toast("当前 " + type );
if ("2".equals(type)) {

boolean chatJoinGroup = getBoolean("hy",GroupUin,false);

if (chatJoinGroup) {
if (getString("qun"+GroupUin, "yu") == null) {


sendMsg(GroupUin, null, "欢迎欢迎！");


} else {

sendMsg(GroupUin, null, "[AtQQ=" + UserUin + "] " + getString("qun"+GroupUin, "yu"));


}

}
}

}

