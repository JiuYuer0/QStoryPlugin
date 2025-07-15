public class Message{
    //重写语音功能
    public static void sendPtt(Object data, String path) {
        if(data.IsGroup){
            sendVoice(data.GroupUin, "", path);
        }else{
            sendVoice("", String.valueOf(data.msg.peerUin), path);
        }   
    }
    //重写卡片功能
    public static void sendCards(Object data, String content) {
        if(data.IsGroup){
            sendCard(data.GroupUin, "", content);
        }else{
            sendCard("", String.valueOf(data.msg.peerUin), content);
        }        
    }
    //重写消息功能
    public static void sendMsgs(Object data, String content) {
        //判断是不是激活了小尾巴
        if(getBoolean("settings", "functionTails", true) && !content.contains("[PicUrl=")){
            content = "%" + content;//用于移除命的小尾巴
        }
        //判断是不是群组
        if(data.IsGroup){
            sendMsg(data.GroupUin, "", content);
        }else{
            sendMsg("", String.valueOf(data.msg.peerUin), content);
        }        
    }
    //重写消息功能
    public static void sendReplys(Object data, String content) {
        if(data.IsGroup){
            sendReply(data.GroupUin, data, content);
        }else{
            sendMsg("", String.valueOf(data.msg.peerUin), content);
        }  
    }
    //重写发送图片功能
    public static void sendPics(Object data, String content) {
        if(data.IsGroup){
            sendPic(data.GroupUin, "", content);
        }else{
            sendPic("", String.valueOf(data.msg.peerUin), content);
        }        
    }

    //发送视频函数
    public static void sendVideos(Object data, String content) {
        if(data.IsGroup){
            sendVideo(data.GroupUin, "", content);
        }else{
            sendVideo("", String.valueOf(data.msg.peerUin), content);
        }        
    }

    //发送文件函数
    public static void sendFiles(Object data, String content) {
        if(data.IsGroup){
            sendFile(data.GroupUin, "", content);
        }else{
            sendFile("", String.valueOf(data.msg.peerUin), content);
        }        
    }
    //发送文件函数
    public static void mSendFile(String qun, String text, int mtype){//发送文件
        Contact contact=new Contact(mtype, qun, "");
        MsgElement msgElement=QRoute.api(IMsgUtilApi.class).createFileElement(text);
        ArrayList MsgList=new ArrayList();
        MsgList.add(msgElement);
        //IOperateCallback iOperateCallback=new IOperateCallback();
        ((IMsgService) QRoute.api(IMsgService.class)).sendMsg(contact, MsgList, null);
    }
    //对话框函数
    public static void dialog(String title, String content, String tipContent, String negativeBtn, boolean canCelable){
        //获取当前活动
        Activity ThisActivity= GetActivity();
	    // 实例化对话框对象
	    ThisActivity.runOnUiThread(new Runnable(){
	        public void run() {
		        AlertDialog alertDialog = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		        alertDialog.setTitle(title);
		        alertDialog.setMessage(content);
		        alertDialog.setCancelable(canCelable);
		        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeBtn, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
                        if(!tipContent.isEmpty()){
                            Toast(tipContent);  
                        }                  
	    	        }
		        });
	            alertDialog.show();
    	    }
	    });
    }
    // QQ内部Toast
    public static void Toast(int type, String content){// 类型(int) 0：warning  1：error  2：success
        try{ 
            if(type == null){
                type = 2;
            }
            QQToastUtil.showQQToastInUiThread(type, content);        
        }catch(Throwable e){
            Toast("QQToast调用失败：" + e);
        }
    }
}