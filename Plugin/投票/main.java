load(AppPath+"/请求.java");
load(AppPath+"/弹窗.java");
load(AppPath+"/时间.java");
int sj=29*1000;//投票时间为(29+1)秒，可更改
String 代办=AppPath+"/代办.txt";//代办文件路径
addItem("脚本口令","cj");
public void cj(String qun){
setTips("脚本口令","首先打开脚本悬浮窗开关\n\n口令:\n1、投票禁言@某人 时间\n(或投票禁言Q号#时间)\n2、投票解禁@某人(或投票解禁Q号)\n3、结束本轮禁言投票\n4、结束本轮解禁投票\n5、禁言列表\n6、投票#事件\n7、结束本轮事件投票\n\nPs:投票时间可进main.java进行修改，默认30秒\n若报错代表脚本逻辑，模块本身可能有问题或你所使用的QQ版本不适配，可加群反馈");}
addItem("代办事项","cjm");
public void cjm(String qun){
String jg="";File file = new File(代办);
if (file.exists()) {
jg=读(代办).trim();if(jg.isEmpty()){jg="无代办事项";}
} else {jg="无代办事项";}
setTips("代办事项",jg);}
private static ExecutorService ymz = Executors.newCachedThreadPool();
private static ExecutorService hhh = Executors.newCachedThreadPool();
private static ExecutorService lll = Executors.newCachedThreadPool();
public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String uin = msg.UserUin;
    String qun = msg.GroupUin;
    String qq = MyUin;
    int type=msg.msg.chatType;
    int mtype=msg.msg.msgType;   
    String Qun="";        
    long sUin = msg.msg.peerUin;
    String Uin = String.valueOf(sUin+"");
    if(type==2){Qun=qun;}else{Qun=Uin;}   
    ArrayList list=new ArrayList();   
    String ay = getAuthority(Qun, qq);
    String 时间 = getCurrentBeijingTime();
if ("4".equals(getString(Qun, "投票"))) {

if(text.startsWith("投票禁言") && msg.IsSend) {
if (ay.equals("管理员") || ay.equals("群主")) {
startVoting(Qun,1);
String keyword1 = "";
String time = "";
int hashIndex = Math.max(text.indexOf("#"), text.indexOf("＃"));
if (!msg.mAtList.isEmpty()) { 
keyword1 = msg.mAtList.get(msg.mAtList.size() - 1);
int str=text.lastIndexOf(" ")+1;
time=text.substring(str);
} else if (hashIndex != -1) { 
keyword1 = text.substring("投票禁言".length(), hashIndex).trim();
time = text.substring(hashIndex + 1).trim(); 
} else {
Toast("请在禁言命令中添加#及时间信息");return;
}
if(keyword1.isEmpty()||time.isEmpty())return;
String jytime = parseTimeBymessage(time);
if (Long.parseLong(jytime) > 30 * 24 * 60 * 60) {
Toast("禁言时间不能超过30天");
} else {
putString("投票禁言", Qun, keyword1 + "＃" + jytime);
YMZ(Qun, "投票对象：" + keyword1 + "\n禁言时间：" + time + "\n按❤️表示同意禁言，☕表示拒绝", type);
}
}else{Toast("并非本群管理");}
}

if(text.contains("投票对象")&&text.contains("按❤️表示同意禁言，☕表示拒绝")&&msg.IsSend&&type==2) {
Set 当前Id = new HashSet();
当前Id.add(66);当前Id.add(28);
for (Integer number : 当前Id) {
WeakReference weakReference = new WeakReference(this); // 修正泛型
AIOEmoReplyAdapter adapter = new AIOEmoReplyAdapter(weakReference);
adapter.t(number, 1, msg.msg);
try {
Thread.sleep(100);
}catch(e){
Toast(e);}
}
Thread.sleep(sj);
sendReply(Qun,msg,"禁言投票结束");
}

if(text.equals("禁言投票结束")&&msg.IsSend&&type==2&&mtype==9) {
String 原始数据=msg.msg.records.toString();
String j1=getLikesCount(原始数据,60);
String j2=getLikesCount(原始数据,66);
String 信息=getString("投票禁言", Qun);
if (信息 == null || !信息.contains("＃")) {
Toast("投票禁言信息无效");return;}
String[] parts = 信息.split("＃");
String keyword = parts[0];
String jytime = parts.length > 1 ? parts[1] : "900"; 
startJoinTask(j1,j2,原始数据,Qun,type,keyword,jytime);
Thread.sleep(3*1000);
list.add(msg.msg.msgId);
recallMsg(type,Qun, list);
list.clear();
endVoting(Qun,1);
}

if(text.equals("结束本轮禁言投票")&&msg.IsSend&&type==2&&mtype==2) {
endVoting(Qun,1);
Toast("已结束本群本轮禁言投票");
}

if(text.equals("禁言列表")&&msg.IsSend&&type==2&&mtype==2) {
String[] Descs = {"red", "orange", "green", "blue", "indigo", "purple", "pink", "grey", "brown", "black"};
Random random = new Random();
int descIndex = random.nextInt(Descs.length);
String Desc = Descs[descIndex];
String jg=getGroupShutUpList(Qun);
setTips("本群禁言列表",jg.replace("↔️","\n").trim());
Thread.sleep(500);
String jtp = "https://api.tangdouz.com/wz/tuw2.php?nr=" + jg + "&ys=" + Desc;
DownloadToFile(jtp,AppPath+"/禁言列表.png");
sendPic(Qun, jtp,type);
}

if(text.startsWith("投票解禁") && msg.IsSend) {
if (ay.equals("管理员") || ay.equals("群主")) {
startVoting(Qun,2);
String keyword = "";    
if (msg.mAtList.size() >= 1) {
for (String atUin : msg.mAtList) {
keyword = atUin;
}
} else {
int sttx = text.indexOf("投票解禁") + 4;
String keyworrd = text.substring(sttx).trim();
if (keyworrd.isEmpty() || !keyworrd.matches("\\d+")) return; 
keyword = keyworrd;
}
putString("投票解禁", Qun, keyword);
YMZ(Qun, "投票对象：" + keyword + "\n按❤️表示同意解禁，☕表示拒绝", type);
}else{Toast("并非本群管理");}
}

if(text.contains("投票对象")&&text.contains("按❤️表示同意解禁，☕表示拒绝")&&msg.IsSend&&type==2) {
Set 当前Id = new HashSet();
当前Id.add(66);当前Id.add(28);
for (Integer number : 当前Id) {
WeakReference weakReference = new WeakReference(this); // 修正泛型
AIOEmoReplyAdapter adapter = new AIOEmoReplyAdapter(weakReference);
adapter.t(number, 1, msg.msg);
try {
Thread.sleep(100);
}catch(e){
Toast(e);}
}
Thread.sleep(sj);
sendReply(Qun,msg,"解禁投票结束");
}

if(text.equals("解禁投票结束")&&msg.IsSend&&type==2&&mtype==9) {
String 原始数据=msg.msg.records.toString();
String j1=getLikesCount(原始数据,60);
String j2=getLikesCount(原始数据,66);
String 信息=getString("投票解禁", Qun);
if (信息 == null) {
Toast("投票解禁信息无效");return;}
startTask(j1,j2,原始数据,Qun,type,信息);
Thread.sleep(3*1000);
list.add(msg.msg.msgId);
recallMsg(type,Qun, list);
list.clear();
endVoting(Qun,2);
}

if(text.equals("结束本轮解禁投票")&&msg.IsSend&&type==2&&mtype==2) {
endVoting(Qun,2);
Toast("已结束本群本轮解禁投票");
}

if((text.startsWith("投票#")||text.startsWith("投票＃"))&&msg.IsSend) {
//String ab= getAuthority(Qun, qq);
//if (ab.equals("管理员") || ab.equals("群主")) {
startVoting(Qun, 3);
int sttx = text.startsWith("投票#") ? text.indexOf("投票#") + 3 : text.indexOf("投票＃") + 3;
String keyword = text.substring(sttx).trim();
if (keyword.isEmpty()) return;
putString("投票事件", Qun, keyword);
putString("投票事件时间", Qun, 时间);
YMZ(Qun, "投票对象：" + keyword + "\n按❤️表示同意此事，☕表示拒绝", type);
//}else{Toast("并非本群管理");}
}

if(text.contains("投票对象")&&text.contains("按❤️表示同意此事，☕表示拒绝")&&msg.IsSend&&type==2) {
Set 当前Id = new HashSet();
当前Id.add(66);当前Id.add(28);
for (Integer number : 当前Id) {
WeakReference weakReference = new WeakReference(this); // 修正泛型
AIOEmoReplyAdapter adapter = new AIOEmoReplyAdapter(weakReference);
adapter.t(number, 1, msg.msg);
try {
Thread.sleep(100);
}catch(e){
Toast(e);}
}
Thread.sleep(sj);
sendReply(Qun,msg,"事件投票结束");
}

if(text.equals("事件投票结束")&&msg.IsSend&&type==2&&mtype==9) {
String 原始数据=msg.msg.records.toString();
String j1=getLikesCount(原始数据,60);
String j2=getLikesCount(原始数据,66);
String 信息=getString("投票事件", Qun);
if (信息 == null) {
Toast("投票事件信息无效");return;}
String 时间=getString("投票事件时间", Qun);
if (时间 == null) {
Toast("投票事件时间无效");return;}
Task(j1,j2,原始数据,Qun,type,信息,时间);
Thread.sleep(3*1000);
list.add(msg.msg.msgId);
recallMsg(type,Qun, list);
list.clear();
endVoting(Qun,3);
}

if(text.equals("结束本轮事件投票")&&msg.IsSend&&type==2&&mtype==2) {
endVoting(Qun,3);
Toast("已结束本群本轮事件投票");
}

}

if(type == 2||type==1) {
if(text.equals("加群")&&qq.equals(uin)){
jump("mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=770866862&subsource_id=10019");}}

}

private static void startJoinTask(String j1, String j2, String originalData,String Qun, int type,String keyword, String  jytime) {
Runnable task = new Runnable() {
public void run() {
int j1Count = Integer.parseInt(j1);
int j2Count = Integer.parseInt(j2);
long endTime = System.currentTimeMillis() + 1000; // 1秒钟后结束
while (System.currentTimeMillis() < endTime) {
//每隔 0.5 秒检查一次
try {
Thread.sleep(500);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();}
j1 = getLikesCount(originalData, 60);
j2 = getLikesCount(originalData, 66);
j1Count = Integer.parseInt(j1);
j2Count = Integer.parseInt(j2);
}
int time=Integer.parseInt(jytime);
if (j1Count > j2Count) {
YMZ(Qun, "支持票＜反对票，不禁言", type);
} else if (j2Count > j1Count) {
YMZ(Qun, "支持票＞反对票，开始禁言", type);   
Forbidden(Qun,keyword,time);
} else {
YMZ(Qun, "支持票＝反对票，开始禁言", type);
Forbidden(Qun,keyword,time);
}
}
};
ymz.submit(task);
}

private static void startTask(String j1, String j2, String originalData,String Qun, int type,String keyword) {
Runnable task = new Runnable() {
public void run() {
int j1Count = Integer.parseInt(j1);
int j2Count = Integer.parseInt(j2);
long endTime = System.currentTimeMillis() + 1000; // 1秒钟后结束
while (System.currentTimeMillis() < endTime) {
//每隔 0.5 秒检查一次
try {
Thread.sleep(500);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();}
j1 = getLikesCount(originalData, 60);
j2 = getLikesCount(originalData, 66);
j1Count = Integer.parseInt(j1);
j2Count = Integer.parseInt(j2);
}
if (j1Count > j2Count) {
YMZ(Qun, "支持票＜反对票，不解禁", type);
} else if (j2Count > j1Count) {
YMZ(Qun, "支持票＞反对票，开始解禁", type);   
Forbidden(Qun,keyword,0);
} else {
YMZ(Qun, "支持票＝反对票，开始解禁", type);
Forbidden(Qun,keyword,0);
}
}
};
hhh.submit(task);
}

private static void Task(String j1, String j2, String originalData,String Qun, int type,String keyword,String date) {
Runnable task = new Runnable() {
public void run() {
int j1Count = Integer.parseInt(j1);
int j2Count = Integer.parseInt(j2);
long endTime = System.currentTimeMillis() + 1000; // 1秒钟后结束
while (System.currentTimeMillis() < endTime) {
//每隔 0.5 秒检查一次
try {
Thread.sleep(500);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();}
j1 = getLikesCount(originalData, 60);
j2 = getLikesCount(originalData, 66);
j1Count = Integer.parseInt(j1);
j2Count = Integer.parseInt(j2);
}
String jg="群聊："+Qun+"\n时间："+date+"\n事件："+keyword+"\n\n";
if (j1Count > j2Count) {
YMZ(Qun, "支持票＜反对票，此事作废", type);
} else if (j2Count > j1Count) {
YMZ(Qun, "支持票＞反对票，此事代办", type);   
接着写(代办,jg);Toast("已写入代办事件");
} else {
YMZ(Qun, "支持票＝反对票，此事代办", type);
接着写(代办,jg);Toast("已写入代办事件");
}
}
};
lll.submit(task);
}
    
// 解析时间字符串并返回禁言时长（秒）
public String parseTimeBymessage(String date) {
long time = 0;
java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+)(天|小时|时|分钟|分|秒)").matcher(date);
 while (matcher.find()) {
String value = matcher.group(1); 
String unit = matcher.group(2); 
switch (unit) {
case "天":
time += Long.parseLong(value) * 60 * 60 * 24; 
break;
case "小时":
case "时":
time += Long.parseLong(value) * 60 * 60;
 break;
case "分钟":
case "分":
time += Long.parseLong(value) * 60; 
break;
case "秒":
time += Long.parseLong(value); 
break;
}
}
return String.valueOf(time);
}

/*// 检查该群是否正在投票
private boolean isVoting(String Qun) {
    return "true".equals(getString("投票状态_",Qun));
}*/

// 开始投票（标记该群正在投票）
private void startVoting(String Qun,int type) {
if(type==1)putString("禁言投票状态_", Qun, "true");
else if(type==2)putString("解禁投票状态_", Qun, "true");else if(type==3)putString("事件投票状态_", Qun, "true");
}

// 结束投票（清除该群的投票状态）
private void endVoting(String Qun,int type) {
if(type==1)putString("禁言投票状态_",Qun, "false");
else if(type==2)putString("解禁投票状态_",Qun, "false");else if(type==3)putString("事件投票状态_",Qun, "false");
}