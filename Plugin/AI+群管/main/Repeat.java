HashMap RepeatMap = new HashMap();
public void RepeatMsg(Object data){
String qun=data.GroupUin;
if(getString(data.GroupUin,"RepeatMsg")!=null){
String text=data.MessageContent;
if(RepeatMap.containsKey(qun)){
int value=RepeatMap.get(qun);
if(value>=20){
RepeatMap.put(qun,0);
if(!text.equals("")){
sendMsg(qun,"",text);
}
}else{
RepeatMap.put(qun,value+1);
}
}else{
RepeatMap.put(qun,1);
}
}
}