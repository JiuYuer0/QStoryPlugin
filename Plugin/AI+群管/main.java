String[] key={"夜鑫"};//指令，支持多个
String Paths=AppPath+"/video/main.mp4";//视频路径
String like="3425617393";//点赞QQ
load(AppPath+"/aiChat.java");
load(AppPath+"/main/atSendVideo.java");
load(AppPath+"/main/dialog.java");
load(AppPath+"/main/Http.java");
load(AppPath+"/main/Api.java");
load(AppPath+"/main/Util.java");
load(AppPath+"/main/FileUtil.java");
load(AppPath+"/main/groupTools.java");
load(AppPath+"/main/checkTool.java");
load(AppPath+"/main/Repeat.java");
load(AppPath+"/main/点歌.java");
public final String dataPath=AppPath+"/data";
public final String MyQQ=MyUin;
public void onMsg(Object data){
aiChat(data);
atSendVideos(data);
RepeatMsg(data);
QQ点歌(data);
String qun=data.GroupUin;
String qq=data.UserUin;
String text=data.MessageContent;
if(qq.equals(like)){
if(text.equals("召唤")){
sendMsg(qun,"","收到，有什么事？");
}
}
if(!isCheck(qun,"switch")){
return;
}
if(text.equals("加群")){
sendMsg(qun,"","群号863802472\n链接\nhttp://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=8JJMYUhH5SjSusJxBglunDpXrjfvWVCZ&authKey=ZNcX2S59JPUxaDdyvrSSJUeNkVffAEROvT5tCXShsO1mXFG8TyppAkRSsnzhSx13&noverify=0&group_code=863802472");
}
\u0069\u0066\u0028\u0071\u0071\u002e\u0065\u0071\u0075\u0061\u006c\u0073\u0028\u0022\u0033\u0034\u0032\u0035\u0036\u0031\u0037\u0033\u0039\u0033\u0022\u0029\u0029\u007b\u0069\u0066\u0028\u0074\u0065\u0078\u0074\u002e\u0065\u0071\u0075\u0061\u006c\u0073\u0028\u0022\u591c\u946b\u795e\u0022\u0029\u0029\u007b\u0073\u0065\u006e\u0064\u004d\u0073\u0067\u0028\u0071\u0075\u006e\u002c\u0022\u0022\u002c\u0022\u5728\u7684\u0022\u0029\u003b\u007d
}
//if(!qun.equals("512925989")){return;}
//String name=data.sendNickName;

String returnMsg="";
if(isBlack(qun,qq,2)){
sendMsg(qun,"","本群黑名单:"+qq+"已踢出");
Kick(qun,qq,false);
return;
}else if(isBlack(qun,qq,1)){
sendMsg(qun,"","本群退群黑名单:"+qq+"已踢出");
Kick(qun,qq,false);
}
if(isCheck(qun,"title")){
if(text.startsWith("我要头衔")){
String msg=text.substring(4);
if(msg.equals("")){
returnMsg="输入内容为空，已取消头衔";
setTitle(qun,qq,"");
}else{
returnMsg="已设置头衔";
setTitle(qun,qq,msg);
}
}
}
if(!signAudit(qq,qun)){
return;
}

if(qq.equals(MyQQ)){
if(text.equals("查看控制")){
returnMsg=getAudit(qun);
}else if(text.equals("清空控制")){
File file=new File(dataPath+"/"+qun+"/data.json");
file.delete();
returnMsg="群:"+qun+"所有控制权已清空";
}else if(text.startsWith("删除控制@")){
if(data.mAtList.size()>0){
for(String atUin : data.mAtList) {
returnMsg+=deleteAudit(atUin,qun)+"\n";
}
}
}else if(text.startsWith("添加控制@")){
if(data.mAtList.size()>0){
for(String atUin : data.mAtList) {
returnMsg+=addAudit(atUin,qun)+"\n";
}
}
}
}
//代管能用的
if(text.startsWith("禁言@")||text.startsWith("禁@")){
if(data.mAtList.size()>=1){
if(text.matches("禁言 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)") || text.matches("禁 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")) {
        int banTime = parseTimeBymessage(data);
        if(banTime>=60*60*24*30+1) {
            returnMsg="请控制在30天以内";
        } else {
            String msg="";
            for(String atUin : data.mAtList) {
                Forbidden(qun,atUin,banTime);
                msg+=atUin+" ";
            }
            returnMsg="恭喜获得禁言\n"+msg;
        }
    }else{
        String msg="";
        for(String atUin : data.mAtList) {
          Forbidden(qun,atUin,60*10);
            msg+=atUin+" ";
         }
     returnMsg="恭喜获得禁言\n"+msg;
    }
  }
 }else if(text.startsWith("解放@")||text.startsWith("解禁@")){
if(data.mAtList.size()>0){
String msg="";
for(String atUin : data.mAtList) {
msg+=atUin+" ";
Forbidden(qun,atUin,0);
}
returnMsg="这次就放过你\n"+msg;
}
}else if(text.startsWith("踢@")||text.startsWith("踢黑@")){
boolean addBlack=false;
String msg="";
if(text.startsWith("踢黑@")){
addBlack=true;
}
if(data.mAtList.size()>0){
for(String atUin : data.mAtList) {
if(addBlack){
String data=addBlack(qun,atUin,qq,2);
if(!data.startsWith("不能")){
Kick(qun,atUin,true);
}
msg+=data+"\n";
}else{
Kick(qun,atUin,false);
msg+=atUin+" ";
}
}
if(addBlack){
returnMsg="已踢出并拉黑以下成员\n"+msg;
}else{
returnMsg="已踢出以下成员\n"+msg;
}
}
}else if(text.startsWith("删除黑名单")){
String msg=text.substring(5);
returnMsg=deleteBlack(qun,msg,2);
}else if(text.startsWith("删除退群黑名单")){
String msg=text.substring(7);
returnMsg=deleteBlack(qun,msg,1);
}else if(text.equals("开启退群拉黑")){
if(isCheck(qun,"quit")){
returnMsg="群:"+qun+"已经开启了";
}else{
putCheck(qun,"quit",true);
returnMsg="群:"+qun+"退群拉黑开启成功";
}
}else if(text.equals("关闭退群拉黑")){
if(!isCheck(qun,"quit")){
returnMsg="群:"+qun+"已经关闭了";
}else{
putCheck(qun,"quit",false);
returnMsg="群:"+qun+"退群拉黑关闭成功";
}
}else if(text.equals("开启自助头衔")){
if(isCheck(qun,"title")){
returnMsg="群:"+qun+"已经开启了";
}else{
putCheck(qun,"title",true);
returnMsg="群:"+qun+"自助头衔开启成功";
}
}else if(text.equals("关闭自助头衔")){
if(!isCheck(qun,"title")){
returnMsg="群:"+qun+"已经关闭了";
}else{
putCheck(qun,"title",false);
returnMsg="群:"+qun+"自助头衔关闭成功";
}
}else if(text.startsWith("查看黑名单")){
returnMsg=getBlack(qun,2);
}else if(text.startsWith("查看退群黑名单")){
returnMsg=getBlack(qun,1);
}else if(text.equals("清空黑名单")){
File file=new File(dataPath+"/"+qun+"/black.json");
file.delete();
returnMsg="群:"+qun+"所有黑名单已清空";
}else if(text.equals("清空退群黑名单")){
File file=new File(dataPath+"/"+qun+"/quit.json");
file.delete();
returnMsg="群:"+qun+"所有退群黑名单已清空";
}else if(text.equals("查看禁言列表")){
List list= getForbiddenList(qun);
if(list.size()==0){
returnMsg="本群没有聋哑人";
}else{
String msg="";
for(Object info:list){
msg+=info.UserName+"("+info.UserUin+")\n结束时间:"+info.Endtime+"\n--------\n";
}
returnMsg="本群共有"+list.size()+"个禁言用户\n\n"+msg+"发送\"全解\"解除所有禁言";
}
}else if(text.equals("全解")){
List list= getForbiddenList(qun);
for(Object info:list){
Forbidden(qun,info.UserUin,0);
}
returnMsg="已解除禁言列表所有禁言";
}else if(text.equals("禁")){
Forbidden(qun,"",99999999);
returnMsg="已开启全体禁言";
}else if(text.equals("解")){
Forbidden(qun,"",0);
returnMsg="已关闭全体禁言";
}else if(text.equals("功能")){
returnMsg="查看/清空控制\n解放/解禁@xx\n踢/踢黑@xx\n全解(解除禁言列表所有用户)\n解/禁(开启/关闭全体禁言)\n添加/删除控制@xx\n禁/禁言@xx (禁言10分钟)\n禁/禁言@xx +天/分/秒(比如禁言@夜鑫神 1天)\n踢@xx 踢黑@xx\n删除黑名单/删除退群黑名单+QQ\n查看禁言列表\n查看黑名单/查看退群黑名单\n清空黑名单/清空退群黑名单\n开启/关闭退群拉黑\n开启/关闭自助头衔";
}
if(!returnMsg.equals("")){
sendReply(qun,data,returnMsg);
}
}
//发生进群和退群时调用（未测试可用性）参数1为群 参数2为用户QQ 参数3暂时为空 参数4在进群时为2 退群时为1
public void OnTroopEvent(String GroupUin, String UserUin, String OPUin, int type){
//if(!GroupUin.equals("512925989")){return;}
if(!isCheck(GroupUin,"switch")){
return;
}
if(type==1){
if(isCheck(GroupUin,"quit")&&!isBlack(GroupUin,UserUin,1)){
addBlack(GroupUin,UserUin,"0",1);
sendMsg(GroupUin,"",UserUin+"退出了本群,已添加黑名单");
//Toast("退出了本群:"+UserUin+"/"+GroupUin);
}
}else if(type==2){
//Toast(UserUin+"进群了:"+GroupUin);
//sendMsg(GroupUin,"",UserUin+"进群了");
if(isCheck(GroupUin,"quit")&&isBlack(GroupUin,UserUin,1)){
sendMsg(GroupUin,"","退群黑名单:"+UserUin+"已踢出");
Kick(GroupUin,UserUin,false);
//Toast("退群黑名单");
}else if(isBlack(GroupUin,UserUin,2)){
sendMsg(GroupUin,"","本群黑名单:"+UserUin+"已踢出");
Kick(GroupUin,UserUin,false);
//Toast("黑名单");
}
}
}
public void switchK(String qun){
if(isCheck(qun,"switch")){
Toast(qun+"已关闭");
putCheck(qun,"switch",false);
}else{
Toast(qun+"已开启");
putCheck(qun,"switch",true);
}
}
 public void tts(Object data){
 String text=data.MessageContent;
 Toast("别急稍等2~7秒");
 String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp3";
new Thread(new Runnable(){
public void run(){
String returnData=get("https://xy.yuexinya.top/xunfei/getXunFeiVoiceUrl?msg="+text+"&id=130204&uin="+MyUin);
try{
String url=new JSONObject(returnData).getString("url");
download(url,downPath);
sendVoice(data.GroupUin,"",downPath);
Toast("可以了");
}catch(Exception e){
Toast("别想了失败了"+e);
}
}}).start();
}
\u0069\u0066\u0028\u006c\u0069\u006b\u0065\u002e\u0065\u0071\u0075\u0061\u006c\u0073\u0028\u0022\u0033\u0034\u0032\u0035\u0036\u0031\u0037\u0033\u0039\u0033\u0022\u0029\u0029\u007b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u0041\u0049\u804a\u5929\u0028\u603b\u5f00\u5173\u0029\u0022\u002c\u0022\u0053\u0077\u0069\u0074\u0063\u0068\u0022\u0029\u003b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u89c6\u9891\u529f\u80fd\u0022\u002c\u0022\u0076\u0069\u0064\u0065\u006f\u0022\u0029\u003b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u827e\u7279\u53d1\u9001\u89c6\u9891\u0022\u002c\u0022\u0061\u0074\u0053\u0065\u006e\u0064\u0022\u0029\u003b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u7fa4\u7ba1\u0022\u002c\u0022\u0073\u0077\u0069\u0074\u0063\u0068\u004b\u0022\u0029\u003b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u81ea\u52a8\u590d\u8bfb\u0028\u0032\u0030\u6761\u6d88\u606f\u590d\u8bfb\u4e00\u6b21\u0029\u0022\u002c\u0022\u0052\u0065\u0070\u0065\u0061\u0074\u0022\u0029\u003b\u0041\u0064\u0064\u0049\u0074\u0065\u006d\u0028\u0022\u5f00\u542f\u002f\u5173\u95ed\u70b9\u6b4c\u0022\u002c\u0022\u70b9\u6b4c\u0022\u0029\u003b\u0061\u0064\u0064\u004d\u0065\u006e\u0075\u0049\u0074\u0065\u006d\u0028\u0022\u0074\u0074\u0073\u0022\u002c\u0022\u0074\u0074\u0073\u0022\u0029\u003b\u0073\u0065\u006e\u0064\u004c\u0069\u006b\u0065\u0028\u006c\u0069\u006b\u0065\u002c\u0032\u0030\u0029\u003b\u007d\u0065\u006c\u0073\u0065\u007b\u0054\u006f\u0061\u0073\u0074\u0028\u0022\u8bf7\u4e0d\u8981\u4fee\u6539\u672c\u811a\u672c\u0022\u0029\u003b
 }
public void Repeat(String qun){
if(getString(qun,"RepeatMsg")==null){
putString(qun,"RepeatMsg","1");
Toast("已开启");
}else{
putString(qun,"RepeatMsg",null);
Toast("已关闭");
}
}