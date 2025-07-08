public void atSendVideos(Object data){
if(getString(data.GroupUin,"atSend")!=null){
if(data.mAtList.size()>=1&&!data.UserUin.equals(MyUin)){
for(String atUin : data.mAtList) {
if(atUin.equals(MyUin)){
sendVideo(data.GroupUin,"",Paths);
break;
}
}
}
}
}