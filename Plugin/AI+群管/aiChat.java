load(AppPath+"/main/http.java");
load(AppPath+"/main/Api.java");
public void Switch(String qun){
if(getString(qun,"AIChatSwitch")==null){
putString(qun,"AIChatSwitch","1");
Toast("已开启，发送\""+key[0]+"内容\"");
}else{
putString(qun,"AIChatSwitch",null);
Toast("已关闭");
}
}
public void atSend(String qun){
if(getString(qun,"atSend")==null){
putString(qun,"atSend","1");
Toast("已开启");
}else{
putString(qun,"atSend",null);
Toast("已关闭");
}
}
public void video(String qun){
if(getString(qun,"video")==null){
putString(qun,"video","1");
Toast("已开启，查看\"简介内容\"");
}else{
putString(qun,"video",null);
Toast("已关闭");
}
}
public void 点歌(String qun){
if(getString(qun,"dg")==null){
putString(qun,"dg","1");
Toast("已开启，发送\"点歌+名字\"");
}else{
putString(qun,"dg",null);
Toast("已关闭");
}
}
public void aiChat(Object data){
videoMsg(data);
String qun=data.GroupUin;
String qq=data.UserUin;
if(getString(qun,"AIChatSwitch")==null){
return;
}
//if(!qun.equals("512925989")){return;}
String text=data.MessageContent;
if(text.equals("")||data.MessageType!=1){
//Toast(""+data.MessageType);
return;
}
new Thread(new Runnable(){
public void run(){
if(text.equals("重置对话")){
if(deleteID(qq,qun)){
sendReply(qun,data,"重置成功");
}else{
sendReply(qun,data,"重置失败");
}
return;
}
for(String key2:key){
if(text.startsWith(key2)){
String substring=text.substring(key2.length());
String msg=getReturn(substring,qq,qun);
if(!msg.equals("")){
sendReply(qun,data,msg);
break;
}
}
}
}}).start();
}
public void videoMsg(Object data){
String qun=data.GroupUin;
String qq=data.UserUin;
String text=data.MessageContent;
if(getString(qun,"video")!=null){
if(text.equals("美女视频")){
sendReply(qun,data,"正在下载");
new Thread(new Runnable(){
public void run(){
try{
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp4";
download("https://api.lolimi.cn/API/xjj/xjj.php",downPath);
sendVideo(qun,"",downPath);
//Thread.sleep(3000);
// for (int i = 0; i < 10; i++) {
// Thread.sleep(100);
// File f=new File(downPath);
// if(f.delete()){
// break;
// }
// }
}catch(Exception e){
sendReply(qun,data,"失败:"+e);
}
}}).start();
}
if(text.equals("美女视频2")){
sendReply(qun,data,"正在下载");
new Thread(new Runnable(){
public void run(){
try{
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp4";
download("https://api.s01s.cn/API/hssp/",downPath);
sendVideo(qun,"",downPath);
//Thread.sleep(3000);
// for (int i = 0; i < 10; i++) {
// Thread.sleep(100);
// File f=new File(downPath);
// if(f.delete()){
// break;
// }
// }
}catch(Exception e){
sendReply(qun,data,"失败:"+e);
}
}}).start();
}
if(text.equals("萝莉视频")){
sendReply(qun,data,"正在下载");
new Thread(new Runnable(){
public void run(){
try{
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp4";
download("https://api.s01s.cn/API/luoli/",downPath);
sendVideo(qun,"",downPath);
//Thread.sleep(3000);
// for (int i = 0; i < 10; i++) {
// Thread.sleep(100);
// File f=new File(downPath);
// if(f.delete()){
// break;
// }
// }
}catch(Exception e){
sendReply(qun,data,"失败:"+e);
}
}}).start();
}
if(text.equals("跳舞视频1")){
sendReply(qun,data,"正在下载");
new Thread(new Runnable(){
public void run(){
try{
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp4";
download("https://api.yuafeng.cn/API/ly/yzxl.php",downPath);
sendVideo(qun,"",downPath);
//Thread.sleep(3000);
// for (int i = 0; i < 10; i++) {
// Thread.sleep(100);
// File f=new File(downPath);
// if(f.delete()){
// break;
// }
// }
}catch(Exception e){
sendReply(qun,data,"失败:"+e);
}
}}).start();
}
if(text.equals("跳舞视频2")){
sendReply(qun,data,"正在下载");
new Thread(new Runnable(){
public void run(){
try{
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp4";
download("https://api.yuafeng.cn/API/ly/sjxl.php",downPath);
sendVideo(qun,"",downPath);
//Thread.sleep(3000);
// for (int i = 0; i < 10; i++) {
// Thread.sleep(100);
// File f=new File(downPath);
// if(f.delete()){
// break;
// }
// }
}catch(Exception e){
sendReply(qun,data,"失败:"+e);
}
}}).start();
}
if(text.equals("黑丝")){
sendPic(qun,"","https://v2.api-m.com/api/heisi?return=302");
}
if(text.equals("动漫头像")){
sendPic(qun,"","https://api.suyanw.cn/api/sjtx.php");
}

if(text.equals("白丝")){
new Thread(new Runnable(){
public void run(){
String urlData=get("https://v2.xxapi.cn/api/baisi");
try{
JSONObject b=new JSONObject(urlData);
String url=b.getString("data");
sendPic(qun,"",url);
}catch(Exception e){
sendMsg(qun,"","错误"+e);
}
}}).start();
}

if(text.equals("萝莉")){
sendPic(qun,"","https://v.api.aa1.cn/api/api-tx/index.php?wpon=aosijur75fi5huyty5f");
}
}
}
public boolean deleteFolder(File folder) {
        if (!folder.exists()) {
            return true;
        }
       if (folder.isFile()) {
            return folder.delete();
        }
        boolean result = true; 
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) { 
                if (!deleteFolder(f)) { 
                    result = false; 
                }
            }
        }
        if (!folder.delete()) {
            result = false;
        }
        
        return result;
    }
new Thread(new Runnable(){
public void run(){
File file=new File(AppPath+"/cache");
deleteFolder(file);
Thread.sleep(500);
file.mkdirs();
}}).start();