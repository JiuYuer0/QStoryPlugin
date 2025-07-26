String myUin=MyUin+"";
String pluginID=PluginID+"";
import android.app.Activity;
String pluginPath=AppPath+"/";

String 羽雫=AppPath+"/pluginConfig/";
String 猫羽雫=AppPath+"/羽雫/";

String pzpath = AppPath + "/配置文件.java";
File file = new File(pzpath);
if (!file.exists()) {
String nr = readFileContent(猫羽雫+"不要动不要删.dex");
新建(pzpath);
put(pzpath,nr);
Toast("未检测到配置文件,生成默认配置");
}

load(pzpath);

//检测是否为好友
import com.tencent.mobileqq.friend.api.IFriendDataService;
public boolean isFriend(String uin){
Object app = BaseApplicationImpl.getApplication().getRuntime();
IFriendDataService Info = app.getRuntimeService(IFriendDataService.class);
boolean m=Info.isFriend(uin);
return m;
}

import java.lang.reflect.*;
import Java.lang.*;
import com.tencent.mobileqq.app.CardHandler;
import com.tencent.common.app.BaseApplicationImpl;

public void sendZan(String targetUin,int num){
Object app=BaseApplicationImpl.getApplication().getRuntime();
int type = 10;
if(isFriend(targetUin))type=1;
CardHandler handler= new CardHandler(app);
byte[] bArr = new byte[10];
            bArr[0] = (byte) 12;
            bArr[1] = (byte) 24;
            bArr[2] = (byte) 0;
            bArr[3] = (byte) 1;
            bArr[4] = (byte) 6;
            bArr[5] = (byte) 1;
            bArr[6] = (byte) 49;
            bArr[7] = (byte) 22;
            bArr[8] = (byte) 1;
            bArr[9] = (byte) 49;
Class clazz = CardHandler.class;
Class[] ParamTYPE = new Class[]{Long.TYPE,Long.TYPE,byte[].class,Integer.TYPE,Integer.TYPE,Integer.TYPE};
Method[] methods = clazz.getMethods();
for (Method m : methods) {
    if (m.getParameterCount() == ParamTYPE.length ) {
        Class[] params = m.getParameterTypes();
        boolean match = true;
        for (int i = 0; i < ParamTYPE.length; i++) {
            if (!params[i].equals(ParamTYPE[i])) {
                match = false;
                break;
            }
        }
        if (match) {
            // 调用匹配的方法
            m.setAccessible(true);
            m.invoke(handler,new Object[]{Long.parseLong(myUin),Long.parseLong(targetUin),bArr,type,num,0});
            break; // 找到匹配的方法后，跳出循环
        }
    }
}
}

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public void Toast(String text){
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                // 创建LinearLayout布局
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(20, 20, 20, 20);

                // 创建渐变背景
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#aaBEE7")); // 设置背景颜色
                gradientDrawable.setCornerRadius(50); // 设置圆角半径
                
                // 设置透明度，0.0f完全透明，1.0f完全不透明
                gradientDrawable.setAlpha((int) (255 * 0.7f)); // 设置50%的透明度

                linearLayout.setBackground(gradientDrawable);

                // 创建TextView并添加到LinearLayout
                TextView textView = new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTextSize(13.0f);
                textView.setText(text);
                linearLayout.addView(textView);
                linearLayout.setGravity(Gravity.CENTER);

                // 创建Toast并设置自定义视图
                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 190);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(linearLayout);
                toast.show();
            } catch (Exception e) {
                // 这里假设sendMsg方法已经被定义，用于发送错误信息
                sendMsg(myUin, "Toast出现错误:" + e.getMessage(), 1);
            }
        }
    });
}

public void loadJava(String str){
load(str);
}

public void sendCard(String str,String str2,int type){
if(type==1) sendCard("",str,str2);
else if(type==2) sendCard(str,"",str2);
}

public void sendMsg(String str,String str2,int type){
String str2=str2.replace("atUin=","AtQQ=").replace("pic=","PicUrl=");
if(type==1) sendMsg("",str,str2);
else if(type==2) sendMsg(str,"",str2);
}

public void sendPtt(String str,String str2,int type){
if(type==1) sendVoice("",str,str2);
else if(type==2) sendVoice(str,"",str2);
}

public void sendVideo(String str,String str2,int type){
if(type==1) sendVideo("",str,str2);
else if(type==2) sendVideo(str,"",str2);
}

public void sendPic(String str,String str2,int type){
if(type==1) sendPic("",str,str2);
else if(type==2) sendPic(str,"",str2);
}

public addItem(String str,String str2){
AddItem(str,str2,pluginID);
}

import com.tencent.qqnt.msg.api.IMsgUtilApi;
import com.tencent.qqnt.kernel.nativeinterface.MsgElement;
import com.tencent.qqnt.kernel.nativeinterface.MsgElement;
import com.tencent.qqnt.kernel.nativeinterface.TextElement;
import com.tencent.qqnt.kernel.nativeinterface.PicElement;
import com.tencent.qqnt.kernel.nativeinterface.VideoElement;
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord;
import com.tencent.qqnt.kernel.nativeinterface.IOperateCallback;
import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.qqnt.kernel.nativeinterface.PttElement;
String versionName=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName.replace(".","");
int QQ_versionName;
if(versionName.length()==3) QQ_versionName=Integer.parseInt(versionName+"0");else QQ_versionName=Integer.parseInt(versionName);

if(QQ_versionName>=9068||包体.equals("TIM")){
import com.tencent.qqnt.kernelpublic.nativeinterface.Contact;
}else{
import com.tencent.qqnt.kernel.nativeinterface.Contact;
}

public void sendFile(String qun,String text,int mtype){//发送文件
String qr="";
if(mtype==1) qr=getUidFromUin(qun);else if(mtype==2) qr=qun;
Contact contact = new Contact(mtype, qr, "");
MsgElement msgElement=QRoute.api(IMsgUtilApi.class).createFileElement(text);
ArrayList MsgList=new ArrayList();
MsgList.add(msgElement);
//IOperateCallback iOperateCallback=new IOperateCallback();
((IMsgService) QRoute.api(IMsgService.class)).sendMsg(contact,MsgList,null);
}

public void recallMsg(int mtype,String qun,List list){//撤回消息
String qr="";
if(mtype==1) qr=getUidFromUin(qun);
else if(mtype==2) qr=qun;
for(long msgid:list){
Contact contact = new Contact(mtype, qr, "");
((IMsgService) QRoute.api(IMsgService.class)).recallMsg(contact,msgid,null);//撤回消息
}
}

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public void sendReply(Object data,String menu){
String str2=menu.replace("atUin=","AtQQ=").replace("pic=","PicUrl=");
if(data.msg.chatType==1){
String regex = "\\[AtQQ=(\\d+)\\]";
String replacement = "@"+data.msg.sendNickName;
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher(str2);
String output = matcher.replaceAll(replacement);
sendMsg("",data.msg.peerUin+"",output);
}else if(data.msg.chatType==2){
sendReply(data.GroupUin,data,str2);
}
}

public Activity getNowActivity(){
return GetActivity();
}

public void shutUp(String str,String str2,int i){
Forbidden(str,str2,i);
}

/*public void sendZan(String str,int i){
sendLike(str);
}
*/
public void setGroupMemberTitle(String str,String str2,String str3){
setTitle(str,str2,str3);
}

public void kickGroup(String str,String str2,boolean z){
Kick(str,str2,z);
}


public void shutUpAll(String qun,boolean z){
if(z) Forbidden(qun,"",1);
else Forbidden(qun,"",0);
}

public void shutUpAllFalse(String qun){
Forbidden(qun,"",2);
}

import com.tencent.mobileqq.troop.clockin.handler.TroopClockInHandler;
public void groupClockIn(String qun,String uin){
TroopClockInHandler inHandler;
try{
inHandler = new TroopClockInHandler(app);
}catch(e){
inHandler = new TroopClockInHandler();
}
Method method;
Class clazz = TroopClockInHandler.class;
Class[] ParamTYPE = new Class[]{String.class,String.class,int.class,boolean.class};
Method[] methods = clazz.getDeclaredMethods();
for (Method m : methods) {
    if (m.getParameterCount() == ParamTYPE.length ) {
        Class[] params = m.getParameterTypes();
        boolean match = true;
        for (int i = 0; i < ParamTYPE.length; i++) {
            if (!params[i].equals(ParamTYPE[i])) {
                match = false;
                break;
            }
        }
        if (match) {
            // 调用匹配的方法
            m.setAccessible(true);
            m.invoke(inHandler,new Object[]{qun,uin,0,true});
            break; // 找到匹配的方法后，跳出循环
        }
    }
}
}

new Thread(new Runnable(){
public void run(){
loadJava(猫羽雫 + "import.java");
loadJava(猫羽雫 + "UI.java");
loadJava(猫羽雫 + "owner.java");
statistics();
byd();
}}).start();

new Thread(new Runnable(){
public void run(){
loadJava(猫羽雫 + "方法.java");
loadJava(猫羽雫 + "音乐.java");
loadJava(猫羽雫 + "addItem.java");
loadJava(猫羽雫 + "APP.java");
}}).start();

new Thread(new Runnable(){
public void run(){
if(读("猫羽雫","播放器", false)) loadJava(猫羽雫 + "搜索.java");
if(读("猫羽雫","解析", false)) loadJava(猫羽雫 + "解析.java");
}}).start();


import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;

public boolean checkInstalledPackages(List packageNamesToCheck) {
    try {
        if (context == null) {
            return false; // 如果上下文为空，返回 false
        }

        PackageManager pm = context.getPackageManager();
        List appInfos = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        List installedUserAppPackages = new ArrayList();
        for (int i = 0; i < appInfos.size(); i++) {
            ApplicationInfo appInfo = (ApplicationInfo) appInfos.get(i);
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ||
                    (appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                continue; // 跳过系统应用和更新的系统应用
            }
            installedUserAppPackages.add(appInfo.packageName);
        }

        for (int i = 0; i < packageNamesToCheck.size(); i++) {
            Object targetPackageName = packageNamesToCheck.get(i);
            if (targetPackageName == null || !(targetPackageName instanceof String)) {
                return false; // 如果参数中包含非字符串类型，返回 false
            }
            String packageName = (String) targetPackageName;
            if (!installedUserAppPackages.contains(packageName)) {
                return false; // 如果目标包名不存在于已安装包名中，返回 false
            }
        }

        return true; // 所有目标包名都存在，返回 true

    } catch (Exception e) {
        e.printStackTrace();
        return false; // 如果发生异常，返回 false
    }
}

List packageNamesToCheck = new ArrayList();
packageNamesToCheck.add("com.tencent.qqmusic");//QQ音乐
packageNamesToCheck.add("com.netease.cloudmusic");//网易云音乐
packageNamesToCheck.add("cn.kuwo.player");//酷我音乐

boolean result = checkInstalledPackages(packageNamesToCheck);

if(!读("猫羽雫", "备用音卡", false)&&!读("猫羽雫","音乐卡片", false)&&!result&&Module.equals("QStory")){

Toast("QS用户,未检测到设备上安装的音乐软件\n已自动开启\"备用音卡发送\"功能");
写("猫羽雫", "备用音卡", true);

}

