public void sendReply(Object data,String menu){
sendReplyMsg(data.peerUid,data.msgId,menu,data.type);
}

String 羽雫=pluginPath+"pluginConfig/";
String 猫羽雫=pluginPath+"羽雫/";

String pzpath = pluginPath + "配置文件.java";
File file = new File(pzpath);
if (!file.exists()) {
String nr = readFileContent(猫羽雫+"不要动不要删.dex");
新建(pzpath);
put(pzpath,nr);
Toast("未检测到配置文件,生成默认配置");
}

loadJava(pzpath);

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
if(读("猫羽雫","播放器", false)){
loadJava(猫羽雫 + "搜索.java");
}
}}).start();

