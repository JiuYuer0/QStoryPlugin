import android.app.*;
import android.os.*;
import android.view.*;
import java.lang.*;
import android.content.*;
import android.widget.*;
import android.media.*;
import java.text.*;
import android.net.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;
import android.graphics.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import pl.droidsonroids.gif.*;
import java.util.regex.*;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.drawable.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import androidx.appcompat.app.AlertDialog;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.tencent.mobileqq.vip.api.IVipColorName;
import com.tencent.qqnt.msg.api.IMsgUtilApi;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.qqnt.kernelpublic.nativeinterface.Contact;
import com.tencent.qqnt.kernel.nativeinterface.Contact;
import java.nio.file.Files;
import java.nio.file.Paths; 
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;
import com.tencent.relation.common.api.IRelationNTUinAndUidApi;
public String getUidFromUin(String uin){
String uidFromUin = ((IRelationNTUinAndUidApi) QRoute.api(IRelationNTUinAndUidApi.class)).getUidFromUin(uin);
return uidFromUin;
}
public void recallMsg(int mtype,String qun,List list){//撤回消息
String qr="";
    if(mtype==1) qr=getUidFromUin(qun);else if(mtype==2) qr=qun;
    for(long msgid:list){
        Contact contact = new Contact(mtype, qr, "");
        ((IMsgService) QRoute.api(IMsgService.class)).recallMsg(contact,msgid,null);//撤回消息
    }
}
public static String getLikesCount(String data, int emojiId) {
String regex = "MsgEmojiLikes\\{emojiId=" + emojiId + ",emojiType=\\d+,likesCnt=(\\d+),";
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher(data);
if (matcher.find()) {
return matcher.group(1); }
return "0";
}
//回应方法
import java.lang.ref.WeakReference;
import com.tencent.mobileqq.aio.msglist.holder.component.msgtail.ui.AIOEmoReplyAdapter;
import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.concurrent.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
public void YMZ(String str,String str2,int type){
if(type==2)sendMsg(str,"",str2);else if(type==1)sendMsg("",str,str2);}
public void sendPic(String str,String str2,int type){
if(type==1)sendPic("",str,str2);else if(type==2)sendPic(str,"",str2);
}
import java.time.*;
import java.util.concurrent.TimeUnit;

public String getTimeDifference(long futureTimestampMillis) {
    long currentMillis = System.currentTimeMillis(); // 获取当前时间（毫秒级）
    
    if (futureTimestampMillis <= currentMillis) return "已到达"; // 未来时间已到达

    long diffMillis = futureTimestampMillis - currentMillis; // 计算时间差（毫秒）

    long seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis) % 60;
    long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60;
    long hours = TimeUnit.MILLISECONDS.toHours(diffMillis) % 24;
    long days = TimeUnit.MILLISECONDS.toDays(diffMillis) % 365;
    long years = TimeUnit.MILLISECONDS.toDays(diffMillis) / 365; // 计算年数

    StringBuilder result = new StringBuilder();
    if (years > 0) result.append(years).append("年");
    if (days > 0) result.append(days).append("天");
    if (hours > 0) result.append(hours).append("时");
    if (minutes > 0) result.append(minutes).append("分");
    if (seconds > 0) result.append(seconds).append("秒");

    return result.toString();
}
public  String convertTimestamp(long timestamp) {
LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");return dateTime.format(formatter);}
public String getGroupShutUpList(String qun) {
 List st =  getForbiddenList(qun);
if (st == null || st.isEmpty()) return "暂无禁言列表\n";
StringBuilder result = new StringBuilder();
int i = 1; 
for (Object b : st) {
//SomeClass bObj = (SomeClass) b; 
String um = b.UserUin;
String nm = b.UserName;
String Time = getTimeDifference(b.Endtime);
String time = convertTimestamp(b.Endtime);
result.append(i).append(". QQ: ").append(um).append(" ↔️    昵称: ").append(nm).append(" ↔️    剩余时长: ").append(Time).append(" ↔️    结束时间: ").append(time).append("↔️");i++;
}
return result.toString();
}
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
public String getAuthority(String qun, String uin)
{
  Object app = BaseApplicationImpl.getApplication().getRuntime();
  ITroopInfoService TroopInfo = app.getRuntimeService(ITroopInfoService.class);
  Object info = TroopInfo.getTroopInfo(qun);
  if(info.isTroopOwner(uin)) return "群主";
  else if(info.isTroopAdmin(uin)) return "管理员";
  else return "群员";}                   
//链接下载文件保存到特定路径
public String DownloadToFile(String url, String filepath) throws Exception
{
  File file = new File(filepath);
  if(!file.getParentFile().exists())
  {
    file.getParentFile().mkdirs();
  }
  InputStream input = null;
  try
  {
    URL ur = new URL(url);
    HttpURLConnection urlConn = (HttpURLConnection) ur.openConnection();
    input = urlConn.getInputStream();
    byte[] bs = new byte[1024];
    int len;
    FileOutputStream out = new FileOutputStream(filepath, false);
    while((len = input.read(bs)) != -1)
    {
      out.write(bs, 0, len);
    }
    out.close();
    input.close();
  }
  catch(IOException e)
  {
    return "失败";
  }
  finally
  {
    try
    {
      input.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
      return "失败";
    }
  }
  return "成功";
}  
addItem("开/关 投票","chaijun");
public void chaijun(String qun){
if("4".equals(getString(qun,"投票"))){
putString(qun, "投票", null);
Toast("已关闭本聊天投票");
}else{putString(qun, "投票", "4");
Toast("本聊天投票已开启");}}       
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.qroute.QRoute;
public void jump(String url) {
((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);} 
