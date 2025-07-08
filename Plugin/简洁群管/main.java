int 艾特禁言时间=60;

/*
该接口由卑微萌新(QQ779412117)开发，使用请保留版权。接口内容全部来自QQ内部，部分参数不准确与本人无关
*/
/*接口说明 

显示群互动标识 SetTroopShowHonour(qun,MyUin,getSkey(),getPskey("clt.qq.com"),1);
显示群聊等级 SetTroopShowLevel(qun,MyUin,getSkey(),getPskey("clt.qq.com"),1);
显示群员头衔 SetTroopShowTitle(qun,MyUin,getSkey(),getPskey("clt.qq.com"),1);

隐藏就是最后1改成0

*/

AddItem("开关艾特禁言","开关艾特禁言方法",PluginID);
AddItem("退群拉黑开关", "退群拉黑开关方法");
String 退群拉黑目录 = AppPath + "/退群拉黑/";
File 退群拉黑文件夹 = new File(退群拉黑目录);

new Object() {
    {
        if (!退群拉黑文件夹.exists()) {
            退群拉黑文件夹.mkdirs();
            Toast("已尝试创建退群拉黑文件");
        }
    }
};

public List 禁言组(String qun){
    Object st=getForbiddenList(qun);
    ArrayList t = new ArrayList();
    for(Object b:st){
        t.add(b.UserUin);
    }
    return t;
}
public String 禁言组文本(String qun){
    Object st=getForbiddenList(qun);
    ArrayList t = new ArrayList();
    int i=1;
    for(Object b:st){
        t.add(i+"."+b.UserName+"("+b.UserUin+")");
        i++;
    }
    String r=t.toString();
    String s=r.replace(" ","");
    String g=s.replace(",","\n");
    String k=g.replace("[","");
    String y=k.replace("]","");
    return y + "\n输入 解禁+序号快速解禁\n输入 踢/踢黑+序号 可快速踢出\n输入全禁可禁言30天\n输入#踢禁言 可踢出上述所有人";
}
public void 开关艾特禁言方法(String qun){
    if("开".equals(getString(qun,"艾特禁言"))){
        putString(qun,"艾特禁言",null);
        Toast("已关闭艾特禁言");
    }else{
        putString(qun,"艾特禁言","开");
        Toast("已开启艾特禁言");
    }
}
public void 退群拉黑开关方法(String qun){
    if("开".equals(getString(qun,"退群拉黑"))){
        putString(qun,"退群拉黑",null);
        Toast("已关闭退群拉黑");
    }else{
        putString(qun,"退群拉黑","开");
        Toast("已开启退群拉黑");
    }
}
import android.os.Environment;
String 路径 = AppPath + "/代管列表/";
File 代管=new File(路径+"代管.txt");
if (!代管.getParentFile().exists()) {
    Toast("发现系统没有指定文件，这将会为您创建一个");
    代管.getParentFile().mkdirs();
    if (!代管.exists()) {
        代管.createNewFile();
    }
    Toast("创建完成，可以开始使用了");
}

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*; 
import java.net.*;
public long GetBkn(String skey){
    int hash = 5381;
    for (int i = 0, len = skey.length(); i < len; i++) 
        hash += (hash << 5) + (int)(char)skey.charAt(i);
    return hash & 2147483647;
}
public String httppost(String urlPath, String cookie,String data){
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    CookieManager cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);
    try {
        URL url = new URL(urlPath);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(2000000);
        uc.setReadTimeout(2000000);
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Cookie",cookie);
        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            Toast("错误:\n"+e);
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}
public String SetTroopShowHonour(String qun,String myQQ,String skey,String pskey,int type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+"&flag="+type+"&bkn="+GetBkn(skey);
        JSONObject json = new JSONObject(httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_honour_flag",cookie,put));
        int ec=json.getInt("ec");
        String em=json.getString("em");
        if(ec==0) return "设置成功";
        else return "设置失败，原因:\n"+em;
    }
    catch(Exception e){
        return "设置失败，原因:\n"+e;
    } 
}
public String SetTroopShowLevel(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"&levelnewflag="+type);
}
public String SetTroopShowTitle(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"&levelflag="+type);
}
public String SetTroopShowInfo(String qun,String myQQ,String skey,String pskey,String type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+type+"&bkn="+GetBkn(skey);
        JSONObject json = new JSONObject(httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_setting",cookie,put));
        int ec=json.getInt("ec");
        String em=json.getString("em");
        if(ec==0) return "设置成功";
        else return "设置失败，原因:\n"+em;
    }
    catch(Exception e){
        return "设置失败，原因:\n"+e;
    } 
}
public static boolean isLetterDigitOrChinese(String str) {
    String regex = "^[\\p{P}a-z0-9A-Z\u4e00-\u9fa5]+$";
    return str.matches(regex);
}
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import android.os.Bundle;
Object app=BaseApplicationImpl.getApplication().getRuntime();
IProfileDataService ProfileData=app.getRuntimeService(IProfileDataService.class);
IProfileProtocolService ProtocolService=app.getRuntimeService(IProfileProtocolService.class);
public Object GetCard(String uin){
    ProfileData.onCreate(app);
    Object card=ProfileData.getProfileCard(uin,false);
    if(card==null||card.iQQLevel==null){
        Bundle bundle =new Bundle();
        bundle.putLong("selfUin",Long.parseLong(MyUin));
        bundle.putLong("targetUin",Long.parseLong(uin));
        bundle.putInt("comeFromType",12);
        ProtocolService.requestProfileCard(bundle);
        return null;
    }else return card;
}
ITroopInfoService TroopInfo=app.getRuntimeService(ITroopInfoService.class);
import android.content.Intent;
import com.tencent.common.app.BaseApplicationImpl;
Object app=BaseApplicationImpl.getApplication().getRuntime();
public void SetTroopAdmin(Object qun,Object qq,int type){
    Intent intent=new Intent();
    intent.putExtra("command",0);
    intent.putExtra("operation", (byte) type);
    intent.putExtra("troop_code",""+qun);
    intent.putExtra("troop_member_uin",""+qq);
    TroopServlet tr=new TroopServlet();
    tr.init(app,null);
    tr.service(intent);
}
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
private static final Map Arab2Chinese = new HashMap() {
    {
        put('零', 0);put('一', 1);
        put('二', 2);put('三', 3);
        put('四', 4);put('五', 5);
        put('六', 6);put('七', 7);
        put('八', 8);put('九', 9);
        put('十', 10);
    }
};
private static final Map UnitMap = new HashMap() {
    {
        put('十', 10);put('百', 100);
        put('千', 1000);put('万', 10000);
    }
};
private static Pattern pattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");
public static Integer CN_zh_int(String chinese) {
    Objects.requireNonNull(chinese);
    Integer result = 0;
    Matcher matcher = pattern.matcher(chinese);
    while (matcher.find()) {
        String res = matcher.group(0);
        if (res.length() == 2) {
            result += Arab2Chinese.get(res.charAt(0)) * UnitMap.get(res.charAt(1));
        } else if (res.length() == 1) {
            if (UnitMap.containsKey(res.charAt(0))) {
                result *= UnitMap.get(res.charAt(0));
            } else if (Arab2Chinese.containsKey(res.charAt(0))) {
                result += Arab2Chinese.get(res.charAt(0));
            }
        }
    }
    if(chinese.startsWith("十")){
        return 10+result;
    }
    return result;
}
public static boolean atMe(Object msg){
    if (null == msg.AtList || 0 == msg.AtList.length)
        return false;
    for (String to_at : msg.AtList)
        if (to_at.equals(MyUin))
            return true;
    return false;
}
public String 论(String u,String a,String b){
    return u.replace(a,b);
}
FileWriter f=null;
BufferedWriter f1=null;
FileReader  fr  =  null;
BufferedReader f2=null;
public  static  void 简写(File ff, String a) throws IOException {
    f=new FileWriter(ff,true);
    f1=new BufferedWriter(f);
    f1.append(a);
    f1.newLine();
    f1.close();
    f.close();
}
public static ArrayList 简取(File ff) throws IOException {
    if(!ff.exists()){
        ff.createNewFile();}
    fr  =  new  FileReader(ff);  
    f2=new BufferedReader(fr);
    String a;
    FileReader reader = new FileReader(ff);
    BufferedReader bReader = new BufferedReader(reader);
    ArrayList list1 = new ArrayList();
    while ((a = bReader.readLine()) != null) {
        list1.add(a);
    }
    fr.close();
    f2.close();
    bReader.close();
    reader.close();
    return list1;
}
public boolean jiandu(String a,ArrayList l1){
    boolean x=false;
    for(int i=0;i<l1.size();i++){
        if(a.contains(l1.get(i).toString())){
            x=true; break;
        }
    }
    return x;
}
public static void 全弃(File ff) throws IOException {
    f=new FileWriter(ff);
    f1=new BufferedWriter(f);
    f1.write("");
    f1.close();
    f.close();
}
public static int 度(String a){
    return a.length();
}
public static void 简弃(File ff,String a) throws IOException {
    ArrayList l1= new ArrayList();
    l1.addAll(简取(ff));
    if(l1.contains(a)){
        l1.remove(a);
    }
    f=new FileWriter(ff);
    f1=new BufferedWriter(f);
    f1.write("");
    f1.close();
    f.close();
    for(int i=0;i<l1.size();i++){
        简写(ff,l1.get(i));
    }
}
public String 名(String uin){
    try{
        Object card=GetCard(uin);
        if(card.strNick!=null){
            return card.strNick;
        }
        else{
            return "未知";
        }
    }catch(Exception e){
        Toast("异常"+e);
        return "未知";
    }
}
public String 组名(ArrayList a){
    ArrayList list = new ArrayList();
    for(String uin : a) {
        list.add(名(uin)+"("+uin+")");
    }
    return list.toString().replace(",","\n");
}
public static boolean isAdmin(String GroupUin, String UserUin) {
    ArrayList groupList = getGroupList();
    for (Object groupInfo : groupList) {
        if (groupInfo.GroupUin.equals(GroupUin)) {
            return groupInfo.GroupOwner.equals(UserUin) || 
                (groupInfo.AdminList != null && groupInfo.AdminList.contains(UserUin));
        }
    }
    return false;
}
public int get_time_int(Object msg,int time){
    int datu = msg.MessageContent.lastIndexOf(" ");
    String date=msg.MessageContent.substring(datu +1); 
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}
public int get_time_int(String msg,int time){
    int datu = msg.lastIndexOf(" ");
    String date = msg.substring(datu +1); 
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}
public int get_time(String msg){
    int datu = msg.lastIndexOf(" ");
    String date=msg.substring(datu +1);
    date=date.trim();
    String t="";
    if(date != null && !"".equals(date)){
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)>=48 && date.charAt(i)<=57){
                t +=date.charAt(i);
            }
        }
    }	
    int time=Integer.parseInt(t);  
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}
public int get_time(Object msg){
    int datu = msg.MessageContent.lastIndexOf(" ");
    String date=msg.MessageContent.substring(datu +1);
    date=date.trim();
    String t="";
    if(date != null && !"".equals(date)){
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)>=48 && date.charAt(i)<=57){
                t +=date.charAt(i);
            }
        }
    }	
    int time=Integer.parseInt(t);  
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}
public File 获取黑名单文件(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        Toast("获取黑名单文件: 群号为空");
        return null;
    }
    File 文件 = new File(退群拉黑目录 + 群号 + ".txt");
    if (!文件.exists()) {
        try {
            文件.createNewFile();
        } catch (Exception e) {
            Toast("创建黑名单文件失败: " + e);
        }
    }
    return 文件;
}
public void 添加黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        Toast("添加黑名单: 群号为空");
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null) return;
    ArrayList 当前名单 = 简取(黑名单文件);
    if (!当前名单.contains(QQ号)) {
        简写(黑名单文件, QQ号);
    }
}
public void 移除黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        Toast("移除黑名单: 群号为空");
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 != null && 黑名单文件.exists()) {
        简弃(黑名单文件, QQ号);
    }
}
public boolean 检查黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        Toast("检查黑名单: 群号为空");
        return false;    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null || !黑名单文件.exists()) return false;
    return 简取(黑名单文件).contains(QQ号);
}
public ArrayList 获取黑名单列表(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        Toast("获取黑名单列表: 群号为空");
        return new ArrayList();
    }
    return 简取(获取黑名单文件(群号));
}
public void OnTroopEvent(String groupUin, String userUin, int type) {
    if (groupUin == null || groupUin.isEmpty()) {
        return;
    }
    String switchState = getString(groupUin, "退群拉黑");
    if (switchState == null || !"开".equals(switchState)) {
        return;
    }
    if (type == 1) {
        if (userUin.equals(MyUin)) return;
        添加黑名单(groupUin, userUin);
        String log = "[" + getMemberName(groupUin, userUin) + "] " + userUin + " 退群，已加入黑名单";
        Toast(log);
    } else if (type == 2) {
        if (检查黑名单(groupUin, userUin)) {
            if (!isAdmin(groupUin, MyUin)) {
                Toast("无管理员权限，无法踢出黑名单成员");
                return;
            }
            Kick(groupUin, userUin, true);
            String log = "检测到黑名单成员 " + getMemberName(groupUin, userUin) + "(" + userUin + ") 入群，已自动踢出";
            Toast(log);
        }
    }
}

// ================= 代管保护功能 =================
public boolean 是代管(String groupUin, String userUin) {
    if (jiandu(userUin, 简取(代管))) {
        return true;
    }
    if (getString(groupUin, "代管_" + userUin) != null) {
        return true;
    }
    return false;
}

public boolean 有权限操作(String groupUin, String operatorUin, String targetUin) {
    if (operatorUin.equals(MyUin)) {
        return true;
    }
    if (是代管(groupUin, operatorUin)) {
        if (targetUin.equals(MyUin)) {
            sendMsg(groupUin, "", "不能操作机器人");
            return false;
        }
        if (是代管(groupUin, targetUin)) {
            sendMsg(groupUin, "", "不能操作代管: " + targetUin);
            return false;
        }
        return true;
    }
    return false;
}

public boolean 检查代管保护(String groupUin, String targetUin, String operation) {
    if (是代管(groupUin, targetUin)) {
        sendMsg(groupUin, "", "检测到QQ号 " + targetUin + " 为代管，无法被" + operation);
        return true;
    }
    return false;
}

public void onMsg(Object msg){
    String 故=msg.MessageContent;
    String qq=msg.UserUin;
    public boolean 简读用户(File ff) throws IOException {
        if(简取(ff).contains(qq))return true;
        else return false;
    }
    if(msg.MessageContent.startsWith("我要头衔")&&"开".equals(getString(msg.GroupUin,"自助头衔"))){
        String a=msg.MessageContent.substring(4);
        setTitle(msg.GroupUin,qq,a);
    }
    public String isGN(String a,String b){
        if(a.equals(getString(msg.GroupUin,b)))return "✅";
        else return "❌";
    }
    if("开".equals(getString(msg.GroupUin,"艾特禁言"))){
        if(atMe(msg)){
            Forbidden(msg.GroupUin,qq,艾特禁言时间);
        }
    }
    if(msg.UserUin.equals(MyUin)||简读用户(代管)){
        if(msg.MessageContent.equals("显示标识")){
            SetTroopShowHonour(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(msg.GroupUin,"","群聊互动标识已开启,龙王火花已开启");
        }
        if(msg.MessageContent.equals("隐藏标识")){
            SetTroopShowHonour(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(msg.GroupUin,"","群聊互动标识已隐藏,龙王火花已隐藏");
        }
        if(msg.MessageContent.equals("显示等级")){
            SetTroopShowLevel(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(msg.GroupUin,"","群聊头衔等级已开启");
        }
        if(msg.MessageContent.equals("隐藏等级")){
            SetTroopShowLevel(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(msg.GroupUin,"","群聊头衔等级已隐藏");
        }
        if(msg.MessageContent.equals("显示头衔")){
            SetTroopShowTitle(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(msg.GroupUin,"","群聊头衔已开启");
        }
        if(msg.MessageContent.equals("隐藏头衔")){
            SetTroopShowTitle(msg.GroupUin,MyUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(msg.GroupUin,"","群聊头衔已关闭");
        }
        if(msg.MessageContent.equals("群管功能")){
            String a=
                "群管功能:\n"
                +"禁@ 禁言@ 头衔@\n"
                +"@+时间+天|分|秒\n"
                +"解@ 踢@ 踢黑@\n"
                +"禁/解(全体禁言/解禁) \n"
                +"查看禁言列表\n"
                +"全解(解所有人禁言)\n"
                +"添加代管@ 删除代管@\n"
                +"查看/清空 代管\n"
                +"显示/隐藏 头衔|等级|标识\n"
                +"开启/关闭退群拉黑\n"
                +"查看/移除黑名单@\n"
                +"开启/关闭 自助头衔"+isGN("开","自助头衔");
            sendMsg(msg.GroupUin,"",a);
        }
        if(msg.MessageContent.equals("开自助头衔")){
            putString(msg.GroupUin,"自助头衔","开");
            sendMsg(msg.GroupUin,"","自助头衔已开启 大家可以发送 我要头衔xxx来获取头衔");
            return;
        }
        if(msg.MessageContent.equals("关自助头衔")){
            if("开".equals(getString(msg.GroupUin,"自助头衔"))){
                putString(msg.GroupUin,"自助头衔",null);
                sendMsg(msg.GroupUin,"","自助头衔已关闭 你们不要再发我要头衔了!");
                return;
            }else sendMsg(msg.GroupUin,"","未开启无法关闭");
        }
        if (msg.MessageContent.equals("开启退群拉黑")) {
            putString(msg.GroupUin, "退群拉黑", "开");
            sendMsg(msg.GroupUin, "", "退群拉黑已开启");
            return;
        }
        if (msg.MessageContent.equals("关闭退群拉黑")) {
            putString(msg.GroupUin, "退群拉黑", null);
            sendMsg(msg.GroupUin, "", "退群拉黑已关闭");
            return;
        }
        if (msg.MessageContent.equals("查看黑名单")) {
            ArrayList 名单 = 获取黑名单列表(msg.GroupUin);
            if (名单.isEmpty()) {
                sendMsg(msg.GroupUin, "", "本群黑名单为空");
            } else {
                String 名单文本 = "本群黑名单:\n";
                for (int i = 0; i < 名单.size(); i++) {
                    名单文本 += (i + 1) + ". " + 名(名单.get(i).toString()) + "(" + 名单.get(i) + ")\n";
                }
                sendMsg(msg.GroupUin, "", 名单文本);
            }
            return;
        }
        if (msg.MessageContent.startsWith("移除黑名单@") && msg.mAtList.size() > 0) {
            for (String uin : msg.mAtList) {
                移除黑名单(msg.GroupUin, uin);
            }
            sendMsg(msg.GroupUin, "", "已删黑该用户");
            return;
        }
        if(!msg.MessageContent.startsWith("禁言")&&msg.MessageContent.startsWith("禁")&&msg.mAtList.size()>=1){   			
            if(msg.MessageContent.matches("禁 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")){
                if(get_time(msg)>=60*60*24*30+1){
                    sendMsg(msg.GroupUin,"","请控制在30天以内");
                    return;
                }else{
                    for(String u:msg.mAtList){
                        if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                        Forbidden(msg.GroupUin,u,get_time(msg));
                    }
                    return;
                }
            }
            if(msg.MessageContent.matches("禁 ?@[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)")){
                int str1 = msg.MessageContent.lastIndexOf(" ");
                String str =msg.MessageContent.substring(str1 + 1);
                String text=str.replaceAll("[天分时小时分钟秒]","");
                int time=CN_zh_int(text);
                if(get_time_int(msg,time)>=60*60*24*30+1){
                    sendReply(msg.GroupUin,msg,"禁言时间太长无法禁言");return;
                }else{
                    for(String u:msg.mAtList){
                        if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                        Forbidden(msg.GroupUin,u,get_time_int(msg,time));
                    }
                    return;
                }
            }
            if(!Character.isDigit(msg.MessageContent.charAt(msg.MessageContent.length() - 1))){
                for(String u:msg.mAtList){
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,60*60*24*30);
                }
                return;
            }else{
                int  time2= msg.MessageContent.lastIndexOf(" ");
                String time1 = msg.MessageContent.substring(time2 + 1); 
                int time=Integer.parseInt(time1);  
                for(String u:msg.mAtList){  
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,time*60);       
                } 
                return; 
            }
        }    
        if(msg.MessageContent.startsWith("禁言")&&msg.mAtList.size()>=1){ 
            if(msg.MessageContent.matches("禁言 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")){
                if(get_time(msg)>=60*60*24*30+1){
                    sendMsg(msg.GroupUin,"","请控制在30天以内");
                    return;
                }else{
                    for(String u:msg.mAtList){
                        if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                        Forbidden(msg.GroupUin,u,get_time(msg));
                    }
                    return;
                }
            }
            if(msg.MessageContent.matches("禁言 ?@[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)")){
                int str1 = msg.MessageContent.lastIndexOf(" ");
                String str =msg.MessageContent.substring(str1 + 1);
                String text= str.replaceAll("[天分时小时分钟秒]","");
                int time=CN_zh_int(text);
                if(get_time_int(msg,time)>=60*60*24*30+1){
                    sendReply(msg.GroupUin,msg,"禁言时间太长无法禁言");return;
                }else{
                    for(String u:msg.mAtList){
                        if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                        Forbidden(msg.GroupUin,u,get_time_int(msg,time));
                    }
                    return;
                }
            }  
            if(!Character.isDigit(msg.MessageContent.charAt(msg.MessageContent.length() - 1))){
                for(String u:msg.mAtList){
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,60*60*24);
                }
                return;
            }else{
                int time2 = msg.MessageContent.lastIndexOf(" ");
                String time1 = msg.MessageContent.substring(time2 + 1); 
                int time=Integer.parseInt(time1);  
                for(String u:msg.mAtList){  
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,time);       
                } 
                return; 
            }   
        }
        if(msg.MessageContent.startsWith("解")&&msg.mAtList.size()>=1){    	
            for(String 千:msg.mAtList){  
                Forbidden(msg.GroupUin,千,0);
            } 
            return; 
        }
        if(msg.MessageType == 6 &&( msg.MessageContent.equals("解") || msg.MessageContent.equals("解禁"))) {
            Forbidden(msg.GroupUin,msg.ReplyTo,0);
        }
        if(msg.MessageType == 6 && (msg.MessageContent.startsWith("/ban")||msg.MessageContent.startsWith("ban")||msg.MessageContent.startsWith("/dban")||msg.MessageContent.startsWith("dban"))) {
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "踢黑")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Kick(msg.GroupUin,msg.ReplyTo,true);
            sendMsg(msg.GroupUin,"","已踢出"+msg.ReplyTo+"不会再收到该用户入群申请");
        }
        if(msg.MessageType == 6 && (msg.MessageContent.startsWith("/kick")||msg.MessageContent.startsWith("kick"))) {
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "踢出")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Kick(msg.GroupUin,msg.ReplyTo,false);
            sendMsg(msg.GroupUin,"","已踢出"+msg.ReplyTo+"，此用户还可再次申请入群");
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 ?[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")) {
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if(get_time(msg)>=60*60*24*30+1) {
                sendMsg(msg.GroupUin,"","请控制在两位数以内");
                return;
            } else {
                if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
                Forbidden(msg.GroupUin,msg.ReplyTo,get_time(msg));
            }
            return;
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 [0-9]+(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int time = get_time(timeText);
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Forbidden(msg.GroupUin,msg.ReplyTo,time);
            sendMsg(msg.GroupUin,"","已禁言 时长"+time + 原因);
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 [零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String text=str.replaceAll("[^零一二三四五六七八九十百千万]","");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            int time = CN_zh_int(text);
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Forbidden(msg.GroupUin,msg.ReplyTo,get_time_int(timeText,time));
            sendMsg(msg.GroupUin,"","已禁言 时长"+get_time_int(timeText,time) + 原因);
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 ?[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")) {
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if(get_time(msg)>=60*60*24*30+1) {
                sendMsg(msg.GroupUin,"","请控制在两位数以内");
                return;
            } else {
                if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
                Forbidden(msg.GroupUin,msg.ReplyTo,get_time(msg));
            }
            return;
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 [0-9]+(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int time = get_time(timeText);
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Forbidden(msg.GroupUin,msg.ReplyTo,time);
            sendMsg(msg.GroupUin,"","已禁言 时长"+time + 原因);
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 [零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String text=str.replaceAll("[^零一二三四五六七八九十百千万]","");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            int time = CN_zh_int(text);
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            if (检查代管保护(msg.GroupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(msg.GroupUin, qq, msg.ReplyTo)) return;
            Forbidden(msg.GroupUin,msg.ReplyTo,get_time_int(timeText,time));
            sendMsg(msg.GroupUin,"","已禁言 时长"+get_time_int(timeText,time) + 原因);
        }
        if(!msg.MessageContent.startsWith("踢黑")&&msg.MessageContent.startsWith("踢")&&msg.mAtList.size()>=1){
            for(String u:msg.mAtList){
                if (检查代管保护(msg.GroupUin, u, "踢出")) continue;
                if (!有权限操作(msg.GroupUin, qq, u)) continue;
                Kick(msg.GroupUin,u,false);
            }
            sendMsg(msg.GroupUin,"","踢出成功");
            return;
        }
        if(msg.MessageContent.startsWith("踢黑")&&msg.mAtList.size()>=1){
            for(String 千:msg.mAtList){
                if (检查代管保护(msg.GroupUin, 千, "踢黑")) continue;
                if (!有权限操作(msg.GroupUin, qq, 千)) continue;
                Kick(msg.GroupUin,千,true);
            }
            sendMsg(msg.GroupUin,"","已踢出，不会再收到该用户入群申请");
        }
        if(msg.MessageContent.equals("禁")&&msg.mAtList.size()==0){	  
            Forbidden(msg.GroupUin,"",1);return;
        }
        if(msg.MessageType == 1 && msg.MessageContent.equals("解")&&msg.mAtList.size()==0){		    
            Forbidden(msg.GroupUin,"",0);return;
        }
        if(msg.MessageContent.startsWith("头衔@")){    	
            int str = msg.MessageContent.lastIndexOf(" ")+1;
            String text = msg.MessageContent.substring(str);   
            for(String u:msg.mAtList){
                setTitle(msg.GroupUin,u,text);
            }
        }
        if(msg.MessageContent.equals("查看禁言列表")) {
            if(禁言组(msg.GroupUin).size() == 0) {
                sendReply(msg.GroupUin,msg,"当前没有人被禁言");
            } else {
                sendReply(msg.GroupUin,msg,禁言组文本(msg.GroupUin));
            }
        }
        if (msg.MessageContent.matches("^解禁? ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            List list = getForbiddenList(msg.GroupUin);
            String indexStr = msg.MessageContent.replaceAll(" |解","");
            int index = Integer.parseInt(indexStr) - 1;
            Forbidden(msg.GroupUin, 禁言组(msg.GroupUin).get(index), 0);
        }
        if (msg.MessageContent.matches("^踢 ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            List list = getForbiddenList(msg.GroupUin);
            String indexStr = msg.MessageContent.replaceAll(" |踢","");
            int index = Integer.parseInt(indexStr) - 1;
            String targetUin = 禁言组(msg.GroupUin).get(index);
            if (检查代管保护(msg.GroupUin, targetUin, "踢出")) return;
            if (!有权限操作(msg.GroupUin, qq, targetUin)) return;
            Kick(msg.GroupUin, targetUin, false);
            sendMsg(msg.GroupUin,"","已踢出"+targetUin);
        }
        if (msg.MessageContent.matches("^踢黑 ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            List list = getForbiddenList(msg.GroupUin);
            String indexStr = msg.MessageContent.replaceAll(" |踢|黑","");
            int index = Integer.parseInt(indexStr) - 1;
            String targetUin = 禁言组(msg.GroupUin).get(index);
            if (检查代管保护(msg.GroupUin, targetUin, "踢黑")) return;
            if (!有权限操作(msg.GroupUin, qq, targetUin)) return;
            Kick(msg.GroupUin, targetUin, true);
            sendMsg(msg.GroupUin,"","已踢出"+targetUin+"并且不会再收到该成员申请");
        }
        if (msg.MessageContent.matches("^解禁? ?[0-9]{4,10}+$") && msg.MessageContent.length() >= 6){
            List list = getForbiddenList(msg.GroupUin);
            String indexStr = msg.MessageContent.replaceAll(" |解","");
            String uin = indexStr;
            Forbidden(msg.GroupUin, uin, 0);
        }
        if(msg.MessageContent.equals("#踢禁言")) {
            Forbidden(msg.GroupUin, "", 0);
            Object list=getForbiddenList(msg.GroupUin);
            if(list==null||list.size()==0) 
                sendMsg(msg.GroupUin,"", "当前没有人被禁言");
            else{
                String kickListStr = "";
                for(Object ForbiddenList:list){   
                    String u = ForbiddenList.UserUin;
                    if (检查代管保护(msg.GroupUin, u, "踢出")) continue;
                    if (!有权限操作(msg.GroupUin, qq, u)) continue;
                    kickListStr+="\n"+u;
                    Kick(msg.GroupUin, u, false);
                }
                sendMsg(msg.GroupUin,"", "已踢出禁言列表:"+kickListStr);
            }
        }
        if(msg.MessageContent.equals("全禁")){
            Forbidden(msg.GroupUin, "", 0);
            Object list=getForbiddenList(msg.GroupUin);
            if(list==null||list.size()==0) 
                sendMsg(msg.GroupUin,"", "当前没有人被禁言");
            else{
                for(Object ForbiddenList:list){
                    String u = ForbiddenList.UserUin+"";
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    if (!有权限操作(msg.GroupUin, qq, u)) continue;
                    Forbidden(msg.GroupUin, u, 60*60*24*30);
                }
                sendReply(msg.GroupUin,msg, "禁言列表已加倍禁言");
            }
        }
        if(msg.MessageContent.equals("全解")){
            Forbidden(msg.GroupUin, "", 0);
            Object list=getForbiddenList(msg.GroupUin);
            if(list==null||list.size()==0) 
                sendMsg(msg.GroupUin,"", "当前没有人被禁言");
            else{
                for(Object ForbiddenList:list){
                    Forbidden(msg.GroupUin, ForbiddenList.UserUin+"", 0);
                }
                sendReply(msg.GroupUin,msg, "禁言列表已解禁");
            }
        }
        if(qq.equals(MyUin)){
            if(msg.MessageContent.startsWith("添加代管")||msg.MessageContent.startsWith("添加管理员")||msg.MessageContent.startsWith("设置代管")||msg.MessageContent.startsWith("添加老婆")){
                String QQUin = "";
                if(msg.mAtList.size()==0){
                    sendReply(msg.GroupUin,msg,"你艾特的人呢？");
                    return;
                }
                for(String u:msg.mAtList){
                    if(jiandu(u,简取(代管))){
                        sendMsg(msg.GroupUin,"","列表内的"+u+"已经是代管了 已自动略过");
                        continue;
                    }else {
                        简写(代管,u);
                        putString(msg.GroupUin, "代管_" + u, "true");
                    }
                    QQUin = QQUin + u + " ";
                }
                if(QQUin.replace(" ","").equals("")){
                    sendMsg(msg.GroupUin,"","以上代管已经添加过了");
                }else{ sendMsg(msg.GroupUin,"","已添加代管:\n"+QQUin);}
            }
            if(msg.MessageContent.startsWith("删除代管@")||msg.MessageContent.startsWith("删除管理员@")){
                String QQUin="";
                if(msg.mAtList.size()==0){
                    sendReply(msg.GroupUin,msg,"你艾特的人呢？");
                    return;
                }
                for(String 千:msg.mAtList){
                    if(jiandu(千,简取(代管))){
                        简弃(代管,千);
                        putString(msg.GroupUin, "代管_" + 千, null);
                        QQUin = QQUin + 千 + " ";
                    }else sendMsg(msg.GroupUin,"","QQ "+千+"并不是代管");continue;
                }
                sendMsg(msg.GroupUin,"","已删除管理员:\n"+QQUin);
                return;
            }
            if(msg.MessageContent.startsWith("删除代管")||msg.MessageContent.startsWith("删除管理员")){
                String QQUin="";
                String Stext=msg.MessageContent.substring(4).replace(" ","");
                String text=Stext.replaceAll("[\u4e00-\u9fa5]","");
                {
                    if(!text.matches("[0-9]+")){
                        sendReply(msg.GroupUin,msg,"正确方式 : 删除代管+Q号，请不要输入别的字符");
                        return;
                    }
                    if(!jiandu(text,简取(代管))){
                        sendReply(msg.GroupUin,msg,"此人并不是代管");
                        return;
                    }else {
                        简弃(代管,text);
                        putString(msg.GroupUin, "代管_" + text, null);
                    }
                    QQUin = QQUin + text + " ";
                }
                sendMsg(msg.GroupUin,"","已删除管理员:\n"+QQUin);
                return;
            }      
        }
        if(msg.MessageContent.equals("查看代管")){            
            String 代=组名(简取(代管));
            String 代管=论(代,"]","");
            String 管列表=论(代管,"["," ");
            sendMsg(msg.GroupUin,"","当前的代管如下:\n"+管列表);                
        }                   
        if(msg.MessageContent.equals("清空代管")){
            全弃(代管);
            for (String key : getStringKeys(msg.GroupUin)) {
                if (key.startsWith("代管_")) {
                    putString(msg.GroupUin, key, null);
                }
            }
            sendReply(msg.GroupUin,msg,"代管列表已清空");
        }
        if(msg.MessageContent.matches("^@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)+$")&&msg.mAtList.size()>=1){
            if(get_time(msg)>=60*60*24*30+1){
                sendReply(msg.GroupUin,msg,"时间太长无法禁言");
                return;
            }else{
                String QQUin="";
                for(String u:msg.mAtList){
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,get_time(msg));
                }
                return;
            }
        }
        if(msg.MessageContent.matches("^@?[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)+$")&&msg.mAtList.size()>=1){
            String QQUin="";
            int str1 = msg.MessageContent.lastIndexOf(" ");
            String str =msg.MessageContent.substring(str1 + 1);
            String text=str.replaceAll("[天分时小时分钟秒]","");
            int time=CN_zh_int(text);
            if(get_time_int(msg,time)>=60*60*24*30+1){
                sendReply(msg.GroupUin,msg,"禁言时间太长无法禁言");return;
            }else{
                for(String u:msg.mAtList){
                    if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                    Forbidden(msg.GroupUin,u,get_time_int(msg,time));
                }
                return;
            }
        }
        if(msg.MessageContent.matches("^@?[\\s\\S]+([零一二三四五六七八九十]?[十百千万])+$")&&msg.mAtList.size()>=1){  
            int str = msg.MessageContent.lastIndexOf(" ");
            String text =msg.MessageContent.substring(str + 1);
            int time=CN_zh_int(text);
            for(String u:msg.mAtList){
                if (检查代管保护(msg.GroupUin, u, "禁言")) continue;
                Forbidden(msg.GroupUin,u,time*60);
                return;
            }
        }                          
    }
}
Toast("简洁群管Java加载成功");