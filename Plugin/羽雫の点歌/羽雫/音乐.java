public void Mao(Object Mao){
Data data = new Data();
data.put(Mao,judge());
String quntext = data.quntext;//信息
String qun = data.qun;//群 QQ 
String uin = data.uin;//发送人
String qq = myUin;//机器人
int mtype = data.mtype;//接受类型
int msgtype = data.msgtype;//信息类型
long time = data.time;//时间戳
long msgid = data.msgid;//id

if(mtype==4||mtype==100){
//Toast("不支持频道或者非好友私聊");
return;
}

if(读("猫羽雫","总开关",false)){

if (!读("猫羽雫", qun + "-限制", true) || uin.equals(qq)) {

for(String QQkey2:QQkey){
if(quntext.startsWith(QQkey2)){
//String msg=quntext.substring(4);
String msg=quntext.substring(QQkey2.length());
if(msg.isEmpty()){
sendReply(Mao,"未输入内容");
return;
}
for(String wjc2:wjc){
if(msg.contains(wjc2)){
sendReply(Mao,"带有违禁词,点歌失败");
return;
}
}
删(qq);
String result=QQ_search(msg,true,0);
String tabulation="选歌/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"+序号\n";
if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接1音质14\n";
String menu = "————QQ音乐————\n" + 
           result + 
           "———————————\n" + 
           tips +
           "在" + maxtime + "秒内有效";
if(mysendtype==0){//直接返回
String mid = QQ_search(msg,false,Chose);
发送卡片(mtype,qun,mid,"QQ","");
}else if(mysendtype==1){//json卡片
sendArk(menu,qun,mtype);
store(msg,"QQ",time,uin);
return;
}else if(mysendtype==2){//文本
sendReply(Mao,"[atUin="+uin+"]\n"+menu);
store(msg,"QQ",time,uin);
return;
}else if(mysendtype==3){//网络文转图
String path = wlwzt(menu);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"QQ",time,uin);
time_删除(path);
return;
}else{//本地文转图
String path = MakeTextPhoto(1,menu,picapi,colors);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"QQ",time,uin);
time_删除(path);
return;
}
}
}

for(String WYkey2:WYkey){
if(quntext.startsWith(WYkey2)){
//String msg=quntext.substring(4);
String msg=quntext.substring(WYkey2.length());
if(msg.isEmpty()){
sendReply(Mao,"未输入内容");
return;
}
for(String wjc2:wjc){
if(msg.contains(wjc2)){
sendReply(Mao,"带有违禁词,点歌失败");
return;
}
}
删(qq);
String result=WY_search(msg,true,0);
String tabulation="选歌/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"+序号\n";
if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接1音质9\n";
String menu = "————网易云音乐————\n" + 
           result + 
           "———————————\n" + 
           "选歌发送 "+tabulation+"+序号\n" + 
           "在" + maxtime + "秒内有效";
if(mysendtype==0){//直接返回
String mid = WY_search(msg,false,Chose);
发送卡片(mtype,qun,mid,"网易","");
}else if(mysendtype==1){//json卡片
sendArk(menu,qun,mtype);
store(msg,"网易",time,uin);
return;
}else if(mysendtype==2){//文本
sendReply(Mao,"[atUin="+uin+"]\n"+menu);
store(msg,"网易",time,uin);
return;
}else if(mysendtype==3){//网络文转图
String path = wlwzt(menu);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"网易",time,uin);
time_删除(path);
return;
}else{//本地文转图
String path = MakeTextPhoto(1,menu,picapi,colors);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"网易",time,uin);
time_删除(path);
return;
}
}
}

for(String KWkey2:KWkey){
if(quntext.startsWith(KWkey2)){
//String msg=quntext.substring(4);
String msg=quntext.substring(KWkey2.length());
if(msg.isEmpty()){
sendReply(Mao,"未输入内容");
return;
}
for(String wjc2:wjc){
if(msg.contains(wjc2)){
sendReply(Mao,"带有违禁词,点歌失败");
return;
}
}
删(qq);
String result=KW_search(msg,true,0);
String tabulation="选歌/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"+序号\n";
if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接1音质7\n";
String menu = "————酷我音乐————\n" + 
           result + 
           "———————————\n" + 
           tips +
           "在" + maxtime + "秒内有效";
if(mysendtype==0){//直接返回
String mid = KW_search(msg,false,Chose);
发送卡片(mtype,qun,mid,"酷我","");
}else if(mysendtype==1){//json卡片
sendArk(menu,qun,mtype);
store(msg,"酷我",time,uin);
return;
}else if(mysendtype==2){//文本
sendReply(Mao,"[atUin="+uin+"]\n"+menu);
store(msg,"酷我",time,uin);
return;
}else if(mysendtype==3){//网络文转图
String path = wlwzt(menu);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"酷我",time,uin);
time_删除(path);
return;
}else{//本地文转图
String path = MakeTextPhoto(1,menu,picapi,colors);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
store(msg,"酷我",time,uin);
time_删除(path);
return;
}
}
}


if (quntext.matches("选歌\\s*\\d+\\s*")||quntext.matches("卡片\\s*\\d+")) {
quntext = quntext.substring(2).replaceAll("[^\\d]", "");
int qunxz = Integer.parseInt(quntext.trim());
if(time-读(uin,"时间",0)>maxtime){
sendReply(Mao,"点歌已过期");
return;
}
String pt=读(uin,"平台");
String yy=读(uin,"歌名");
String mid;
if(pt.equals("QQ")){
if (qunxz > 0 && qunxz <= qqmax) {
mid = QQ_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("网易")){
if (qunxz > 0 && qunxz <= wymax) {
mid = WY_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("酷我")){
if (qunxz > 0 && qunxz <= kwmax) {
mid = KW_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}
发送卡片(mtype,qun,mid,pt,"");
}

if (quntext.matches("语音\\s*\\d+")) {
quntext = quntext.substring(2).replaceAll("[^\\d]", "");
int qunxz = Integer.parseInt(quntext.trim());
if(time-读(uin,"时间",0)>maxtime){
sendReply(Mao,"点歌已过期");
return;
}
String pt=读(uin,"平台");
String yy=读(uin,"歌名");
String mid;
if(pt.equals("QQ")){
if (qunxz > 0 && qunxz <= qqmax) {
mid = QQ_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("网易")){
if (qunxz > 0 && qunxz <= wymax) {
mid = WY_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("酷我")){
if (qunxz > 0 && qunxz <= kwmax) {
mid = KW_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}
发送语音(mtype,qun,mid,pt,"");
}

if (quntext.matches("链接\\s*\\d+")) {
quntext = quntext.substring(2).replaceAll("[^\\d]", "");
int qunxz = Integer.parseInt(quntext.trim());
if(time-读(uin,"时间",0)>maxtime){
sendReply(Mao,"点歌已过期");
return;
}
String pt=读(uin,"平台");
String yy=读(uin,"歌名");
String mid;
if(pt.equals("QQ")){
if (qunxz > 0 && qunxz <= qqmax) {
mid = QQ_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("网易")){
if (qunxz > 0 && qunxz <= wymax) {
mid = WY_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("酷我")){
if (qunxz > 0 && qunxz <= kwmax) {
mid = KW_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}
发送链接(mtype,qun,mid,pt,"");
}


if (quntext.matches("歌词\\s*\\d+")) {
quntext = quntext.substring(2).replaceAll("[^\\d]", "");
int qunxz = Integer.parseInt(quntext.trim());
if(time-读(uin,"时间",0)>maxtime){
sendReply(Mao,"点歌已过期");
return;
}
String pt=读(uin,"平台");
String yy=读(uin,"歌名");
String mid="";
if(pt.equals("QQ")){
if (qunxz > 0 && qunxz <= qqmax) {
mid = QQ_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("网易")){
if (qunxz > 0 && qunxz <= wymax) {
mid = WY_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("酷我")){
if (qunxz > 0 && qunxz <= kwmax) {
mid = KW_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}
发送歌词(mtype,qun,mid,pt);
}

if (读("猫羽雫","文件选歌", false)) {

if (quntext.matches("文件\\s*\\d+")) {
int qunxz = Integer.parseInt(quntext.substring(2).trim());
if(time-读(uin,"时间",0)>maxtime){
sendReply(Mao,"点歌已过期");
return;
}
String pt=读(uin,"平台");
String yy=读(uin,"歌名");
String mid;
if(pt.equals("QQ")){
if (qunxz > 0 && qunxz <= qqmax) {
mid = QQ_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("网易")){
if (qunxz > 0 && qunxz <= wymax) {
mid = WY_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}else if(pt.equals("酷我")){
if (qunxz > 0 && qunxz <= kwmax) {
mid = KW_search(yy,false,qunxz);
}else{
sendReply(Mao,jimao);
}
}
发送文件(mtype,qun,mid,pt,"");
}


}

if(读("猫羽雫","音质调试", false)){

if(quntext.equals("#音质帮助")){
String text = "QQ音乐音质选择:"+
			"\n0   音乐试听"+
			"\n1   有损音质"+
			"\n2   有损音质"+
			"\n3   有损音质"+
			"\n4   标准音质"+
			"\n5   标准音质"+
			"\n6   标准音质"+
			"\n7   标准音质"+
			"\n8   HQ高音质"+
			"\n9   HQ高音质（音质增强）"+
			"\n10   SQ无损音质"+
			"\n11   Hi-Res音质"+
			"\n12   杜比全景声"+
			"\n13   臻品全景声"+
			"\n14   臻品母带2.0"+
			"\n网易云音乐音质选择"+
			"\n1   标准（64k）"+
			"\n2   标准（128k）"+
			"\n3   HQ极高（192k）"+
			"\n4   HQ极高（320k）"+
			"\n5   SQ无损"+
			"\n6   高解析度无损（Hi-Res）"+
			"\n7   高清臻音（Spatial Autio）"+
			"\n8   沉浸环绕声（Surround Autio）"+
			"\n9   超清母带（Master）"+
			"\n酷我音乐音质选择"+
			"\n1   acc 普通音质"+
			"\n2   wma 普通音质"+
			"\n3   ogg 标准音质"+
			"\n4   standard 低音质"+
			"\n5   exhigh 高音质"+
			"\n6   lossless 无损"+
			"\n7   hires HiRes音质";
String path = MakeTextPhoto(1,text,picapi,colors);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
time_删除(path);
return;
}

if(quntext.matches("(选歌|卡片|语音|链接|文件)(\\d+).*音质(\\d+)")){
try{
Pattern pattern = Pattern.compile("(选歌|卡片|语音|链接|文件)(\\d+).*音质(\\d+)");
Matcher matcher = pattern.matcher(quntext);
if (matcher.find()){
String yy=读(uin,"歌名");
String pt=读(uin,"平台");
String mid;
String yz;
int xz = Integer.valueOf(matcher.group(2).trim()); // 选歌序号
int qunyz = Integer.valueOf(matcher.group(3).trim()); // 音质
String ms = matcher.group(1).trim(); // 模式
ms = ms.isEmpty() ? "卡片" : ms;
if(!ms.equals("卡片")&&!ms.equals("选歌")&&!ms.equals("语音")&&!ms.equals("文件")&&!ms.equals("链接")) ms = "卡片";
if(qunyz==0){
String text = "QQ音乐音质选择:"+
			"\n0   音乐试听"+
			"\n1   有损音质"+
			"\n2   有损音质"+
			"\n3   有损音质"+
			"\n4   标准音质"+
			"\n5   标准音质"+
			"\n6   标准音质"+
			"\n7   标准音质"+
			"\n8   HQ高音质"+
			"\n9   HQ高音质（音质增强）"+
			"\n10   SQ无损音质"+
			"\n11   Hi-Res音质"+
			"\n12   杜比全景声"+
			"\n13   臻品全景声"+
			"\n14   臻品母带2.0"+
			"\n网易云音乐音质选择"+
			"\n1   标准（64k）"+
			"\n2   标准（128k）"+
			"\n3   HQ极高（192k）"+
			"\n4   HQ极高（320k）"+
			"\n5   SQ无损"+
			"\n6   高解析度无损（Hi-Res）"+
			"\n7   高清臻音（Spatial Autio）"+
			"\n8   沉浸环绕声（Surround Autio）"+
			"\n9   超清母带（Master）"+
			"\n酷我音乐音质选择"+
			"\n1   acc 普通音质"+
			"\n2   wma 普通音质"+
			"\n3   ogg 标准音质"+
			"\n4   standard 低音质"+
			"\n5   exhigh 高音质"+
			"\n6   lossless 无损"+
			"\n7   hires HiRes音质";
String path = MakeTextPhoto(1,text,picapi,colors);
sendReply(Mao,"[atUin="+uin+"]\n[pic="+path+"]");
time_删除(path);
return;
}
if(pt.equals("QQ")){
yz=QQ_yinzhi(qunyz,false);
mid = QQ_search(yy,false,xz);
}else if(pt.equals("网易")){
yz = WY_yinzhi(qunyz,false);
mid = WY_search(yy,false,xz);
}else if(pt.equals("酷我")){
yz = KW_yinzhi(qunyz);
mid = KW_search(yy,false,xz);
}
try{
sendReply(Mao,"[atUin="+uin+"]\n预计输出音质:"+yz);
if(ms.equals("卡片")||ms.equals("选歌")){
发送卡片(mtype,qun,mid,pt,qunyz.toString());
}else if(ms.equals("语音")){
发送语音(mtype,qun,mid,pt,qunyz.toString());
}else if(ms.equals("链接")){
发送链接(mtype,qun,mid,pt,qunyz.toString());
}else if(ms.equals("文件")){
发送文件(mtype,qun,mid,pt,qunyz.toString());
}
}catch(e){
日志(年月日()+" "+时分秒()+"\nJava报错,接口返回:"+jiexii+"\n捕获:"+e);
sendReply(Mao,"啊哦,报错了.....");
}
}
}catch(e){
Toast(e);
}
}

}

}
}
}
