
ArrayList list = new ArrayList();
Map jymap = new HashMap();
Map qunmap=new HashMap();
Map psmap=new HashMap();
Map listmap=new HashMap();
int time=60*5;//初始禁言时间
int n=3;//初始投票次数
int tptime=60*5;//初始投票时间

String sk=getSkey();
ArrayList list3 = new ArrayList();//目标
ArrayList listqun = new ArrayList();//目标群
Map cxmap = new HashMap();

Map djmap = new HashMap();
ArrayList list4 = new ArrayList();
ArrayList listqun1 = new ArrayList();

public void 琴音(Object 汐)
{
if("开".equals(读("黑名单扫描","开关"))){
扫描黑名单(汐.群,简取(黑名单));  
  }
if("开".equals(读("艾特回","开关")))
{
if(atMe(汐))
{
String 回复内容=读("艾特回复","回复内容");

语(回复内容);
}
}
if(简读用户(黑名单)&&!"开".equals(读("黑名单扫描","开关")))
{//后续可能会更这个自动扫描黑名单
if(是否管理(汐.群))
{
语("黑名单("+汐.用户+")已踢出 -来自全局黑名单");
缓冲(50);
踢黑(汐.用户);
}
else
提示("全局黑名单在("+汐.群+") 内发言，请注意安全");
}

if(读开关("开","勿扰模式"))
{
if(atMe(汐))
{
禁言(汐.用户,60*30);
}

if(故("视频"))
{
deleteFile(路径+"朝花/sdc.mp4");
语("开始下载...");
问云.下载("http://sangbolove.cn/API/mn/mn.php",路径+"朝花/sdc.mp4");
//缓冲(50);
sendVideo(汐.群,路径+"朝花/sdc.mp4",false);
//缓冲(50);
}




}
/*	
if(故始("加密"))
{
语(CL(残花(2)));
}
*/
//踢
Runnable ru =new Runnable(){
public void run(){
for(int i=0;i<抢劫时间*60;i++){
Thread.sleep(1000);
Object pp=""+getpay(MyUin,cxmap.get(list3.get(0)),sk,getPskey("tenpay.com"));
if(pp.equals("true")){
sendMsg(listqun.get(0),"","打劫成功");
Forbidden(listqun.get(0),list3.get(0),0);
list3.remove(list3.get(0));
listqun.remove(listqun.get(0));
break;
}
}
Object pp=""+getpay(MyUin,cxmap.get(list3.get(0)),sk,getPskey("tenpay.com"));
if(pp.equals("false")){
sendMsg(listqun.get(0),"","[AtQQ="+list3.get(0)+"] 未处理打劫付款,踢出处理");
Kick(listqun.get(0),list3.get(0),false);
}
cancelpay(MyUin,cxmap.get(list3.get(0)),sk,getPskey("tenpay.com"));
list3.remove(list3.get(0));
listqun.remove(listqun.get(0));
}};

//禁
Runnable ft =new Runnable(){
public void run(){
for(int i=0;i<抢劫时间*60;i++){
Thread.sleep(1000);
Object pp=""+getpay(MyUin,djmap.get(list4.get(0)),sk,getPskey("tenpay.com"));
if(pp.equals("true")){
sendMsg(listqun1.get(0),"","抢劫成功");
Forbidden(listqun1.get(0),list4.get(0),0);
list4.remove(list4.get(0));
listqun1.remove(listqun1.get(0));
break;
}
}
Object pp=""+getpay(MyUin,djmap.get(list4.get(0)),sk,getPskey("tenpay.com"));
if(pp.equals("false")){
sendMsg(listqun1.get(0),"","[AtQQ="+list4.get(0)+"] 未处理抢劫付款,禁言处理");
Forbidden(listqun1.get(0),list4.get(0),60*60*24*30);
}
cancelpay(MyUin,djmap.get(list4.get(0)),sk,getPskey("tenpay.com"));
list4.remove(list4.get(0));
listqun1.remove(listqun1.get(0));
}};

if(秋(MyUin)||简读用户(代管))
{
if(故始("抢劫@"))
{
for(String u:汐.艾特列表){
new Thread(ft).start();
String cc=sendpay(MyUin,汐.群,u,"抢劫开始",抢劫金额,sk,getPskey("tenpay.com"));
禁言(u,60*抢劫时间+60);
list4.add(u);
listqun1.add(汐.群);
djmap.put(u,cc);
}
语("你遭到了抢劫  请在 "+抢劫时间+ " 分钟 内 付款 ，否则禁言一个月");
}

if(故始("取消收款@"))
{
for(String u:汐.艾特列表)
{
禁言(u,0);
cancelpay(MyUin,rqmap.get(u),sk,getPskey("tenpay.com"));
}
语("入群收费已取消");
}



if(故("查看正在抢劫名单"))
{
语("当前正在被打劫的名单如下 :\n"+list3.toString()+"\n正在抢劫的名单如下:\n"+list4.toString());
}
if(故始("不抢@"))
{
for(String u:汐.艾特列表)
{
cancelpay(MyUin,cxmap.get(u),sk,getPskey("tenpay.com"));
list3.remove(u);
listqun.remove(汐.群);
禁言(u,0);
cancelpay(MyUin,djmap.get(u),sk,getPskey("tenpay.com"));
list4.remove(u);
listqun1.remove(汐.群);
禁言(u,0);
}
语("已取消对本次抢劫");
}

if(故始("打劫@"))
{
for(String u:汐.艾特列表){
new Thread(ru).start();
String cc=sendpay(MyUin,汐.群,u,"抢劫开始",抢劫金额,sk,getPskey("tenpay.com"));
禁言(u,60*抢劫时间+60);
list3.add(u);
listqun.add(汐.群);
cxmap.put(u,cc);
}
语("你遭到了抢劫  请在 "+抢劫时间+ " 分钟 内 付款 ，否则踢出本群");
}



if(故始("怀疑小学生@"))
{
for(String u:汐.艾特列表){
new Thread(ru).start();
String cc=sendpay(MyUin,汐.群,u,"小学生付款证明",抢劫金额,sk,getPskey("tenpay.com"));
禁言(u,60*抢劫时间+60);
list3.add(u);
listqun.add(汐.群);
cxmap.put(u,cc);
}
语("你被怀疑是小学生  请在 "+抢劫时间+ " 分钟 内 付款 ，否则踢出本群");
}

}

if(读开关("1","秩")){

String hh;

Runnable uu=new Runnable()
{public void run()
{
for(int i=0;i<tptime;i++){
Thread.sleep(1000);
if(!"1".equals(getString(汐.群,"一"))){
return false;}}
语("时间到,投票结束,禁言失败");
}};
if("1".equals(getString(汐.群,"秩"))){
if(汐.用户.equals(MyUin)||简取(代管).contains(汐.用户)){
if(汐.消息.startsWith("设置禁言时间")){if(汐.消息.substring(6).equals("")){回("傻狗，时间呢");return;}
time=Integer.parseInt(汐.消息.substring(6));
String time1=time.toString();
语("已设置禁言时间"+时(time));}
if(汐.消息.startsWith("设置投票次数")){
if(汐.消息.substring(6).equals("")){回("傻狗,次数呢");return false;}
n=Integer.parseInt(汐.消息.substring(6));
String nn=n.toString();
语("已设置投票次数"+nn+"次");}
if(汐.消息.startsWith("设置投票时间")){if(汐.消息.substring(6).equals("")){回("傻狗，时间呢");return;}
tptime=Integer.parseInt(汐.消息.substring(6));
String time1=tptime.toString();
语("已设置投票时间"+时(time));}
if(汐.消息.equals("查看禁言时间")){语("当前禁言时间"+时(time));}

}
if(汐.消息.startsWith("投票禁言@")&&"1".equals(getString(汐.群,"一"))){
回("已有投票正在进行，请等待");}
if(汐.消息.startsWith("投票禁言@")&&!"1".equals(getString(汐.群,"一"))){
if(汐.艾特列表.size()==0){回("傻狗,你@的人呢");return;}
 String jyq=汐.msg.AtList[0];
 for(Object group:getGroupList())
{
   if(汐.群.equals(group.GroupUin)){
   if(Arrays.asList(group.AdminList).contains(jyq)&&!group.GroupOwner.equals(MyUin)||jyq.equals(group.GroupOwner)){
   回("傻狗还想禁言管理员？");
   return;
   }}}
putString(汐.群,"一","1");

语("            开始投票\n投票时间："+时(tptime)+"\n投票方式：发送→ 投票赞成 ←\n\n"+n.toString()+"票即可执行禁言");
String jyq=汐.msg.AtList[0];
jymap.put(汐.群,jyq);
qunmap.put(jyq,汐.群);
psmap.put(汐.群,0);
 list = new ArrayList();
listmap.put(jyq,list);
new Thread(uu).start();
 }
if(汐.消息.equals("投票赞成")){
String jyq=jymap.get(汐.群);
if(listmap.get(jyq).contains(汐.用户)&&汐.群.equals(qunmap.get(jyq))&&"1".equals(getString(汐.群,"一"))){回("你已经投过票了");}
if("1".equals(getString(汐.群,"一"))&&!listmap.get(jyq).contains(汐.用户)&&汐.群.equals(qunmap.get(jyq))){
listmap.get(jyq).add(汐.用户);
psmap.put(汐.群,listmap.get(jyq).size());
语("投票成功,目前票数"+psmap.get(汐.群));
}
if(!"1".equals(getString(汐.群,"一"))) {回("目前无投票进行");}
}
if(psmap.get(汐.群)==n){
语("投票成功，执行禁言");
Forbidden(汐.群,jymap.get(汐.群),time);
putString(汐.群,"一",null);
psmap.put(汐.群,0);
}
if(汐.消息.equals("时间到,投票结束,禁言失败")&&汐.用户.equals(MyUin)){
putString(汐.群,"一",null);
psmap.put(汐.群,0);
}
if(汐.消息.equals("中止投票")&&("1".equals(getString(汐.群,"一")))){
if(汐.用户.equals(MyUin)){
putString(汐.群,"一",null);
psmap.put(汐.群,0);
语("投票已中止，票数清零");
}
else 回("傻狗,你以为你是谁？还想中止投票？");
}
}
}

if("开".equals(getString(汐.群,"小号收费")))
{
int 等级int=等级int(汐.用户);
if(等级int<=小号收费等级&&等级int==0)
{
new Thread(ru).start();
String cc=sendpay(MyUin,汐.群,汐.用户,"抢劫开始",抢劫金额,sk,getPskey("tenpay.com"));
禁言(汐.用户,60*抢劫时间+60);
list3.add(汐.用户);
listqun.add(汐.群);
cxmap.put(汐.用户,cc);
}
语("检测到你是等级为 "+等级int+"的小号 请在 "+抢劫时间+ " 分钟 内 付款 ，否则踢出本群");
}



if(读开关("开","自助头衔"))
{
if(故始("我要头衔")||故始("我要头街"))
{
String 文本=汐.消息.substring(4).replace(" ","");

if(汐.消息.contains("扑克")||汐.消息.contains("老虎机")||汐.消息.contains("棋牌")||汐.消息.contains("快乐飞艇")||汐.消息.contains("斗地主")||汐.消息.contains("大法伦"))
{
禁言(汐.用户,60*60*24*10);
撤回();
回("不合规的违法内容,已拒绝该请求");
return;
}
if(!isLetterDigitOrChinese(文本))
{
语("请不要在要头衔时加入 表情 符号 别的奇怪的字符 正确姿势: 我要头衔xxx");
return;
}
if(!故里(11))
{
头衔(汐.用户,文本);
语("你头衔可能长了些，换个六个字或以下的头衔吧");
return;
}
else
头衔(汐.用户,文本);
}
}
//过滤符号
String 消息o=汐.消息.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&;*（）꯭——+|{}【】‘；：”“’。，、？|-]", "");
if(简读(消息o,简取(违禁词))&&!秋(MyUin)&&!简读(汐.用户,简取(代管)))
{
String st=getString(汐.群,"违禁词处理方式");
//先判断是否null避免空指针equals无效 因为null需要用运算符才能判断
if(st!=null){
if(st.equals("踢出"))
{
踢(汐.用户);
撤回();
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语(汐.用户 + " 触发违禁词 已踢出本群");
}
 if(st.equals("踢黑"))
{
踢黑(汐.用户);
撤回();
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语(汐.用户 + " 触发违禁词 已踢出本群并且不再收到该用户的主动申请");
}
 if(st.equals("禁言"))
{
禁言(汐.用户,取数(读(汐.群,"禁言时间")));
撤回();
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语(汐.用户 + " 触发违禁词 已禁言");
}

if(st.equals("拉黑"))
{
简写(黑名单,汐.用户);
写如(汐.用户,"QQ "+汐.用户+" 于 "+getTime()+"在群聊 "+群名(汐.群)+"("+汐.群+") 触发违禁词被拉黑" );
踢黑(汐.用户);

}

}
else
{
禁言(汐.用户,60*20);
撤回();
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语(汐.用户 + " 触发违禁词 已禁言");
}
}

if(读关开("防止封群"))
{//敏感词已转义
if(汐.消息.contains("\u8d4c\u535a")||汐.消息.contains("\u6597\u5730\u4e3b")||汐.消息.contains("\u516d\u5408")||汐.消息.contains("\u5feb\u4e50\u98de\u8247"))
{
Forbidden(汐.群,汐.用户,60*60*24*10);
revokeMsg(汐.msg);
提示("有人在［"+朝云(汐.群)+"］发了可能封群相关的违禁词");
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语("("+汐.用户+") 发送了很严重的违禁词，已被禁言");
}
if(汐.消息.contains("扑克")||汐.消息.contains("老虎机")||汐.消息.contains("棋牌")||汐.消息.contains("快乐飞艇")||汐.消息.contains("斗地主")||汐.消息.contains("大法伦")||汐.消息.contains("一分"))
{
禁言(汐.用户,60*60*24*10);
撤回();
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
回("严重的违禁词内容(作 : 不好意思小傻瓜，我已经料到你的想法了哦)");
return;
}
}

if(读关开("防止炸群"))
{






if(汐.消息.contains("ܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶܶ")||汐.消息.contains("🐢🐢🎥🗣️😏🇨")||汐.消息.contains("💀🎥🗣️😏🇨")||汐.消息.contains("ۙ")){
撤回();
禁言(汐.用户,60*60);
if(简读开关(管理操作)||等("关",读("全局1","管理操作提示")))return;
语(汐.msg.SenderNickName+"("+汐.用户+")"+"疑似发送一条卡屏消息，已撤回");
}
if(汐.消息.contains("com.tencent.gamecenter.gameshare")&&(汐.消息.contains(".mp3")||汐.消息.contains(".mp4")))
{
禁言(汐.用户,60*60);
撤回();
语("发送音乐卡片 自动禁言");

}



}




}