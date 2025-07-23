String CONFIG_NAME = "PrivateAutoReply";

AddItem("私聊自动回复开关", "togglePrivateReply");

public void togglePrivateReply(String groupUin, String userUin, int chatType) {
    if (chatType == 1) {
        boolean currentStatus = getBoolean(CONFIG_NAME, userUin, true);
        putBoolean(CONFIG_NAME, userUin, !currentStatus);
        Toast("自动回复：" + (!currentStatus ? "已开启" : "已关闭")); //本脚本开关默认关闭
    }
}

void onMsg(Object msg) {
    
    if (!msg.IsSend 
        && !msg.IsGroup 
        && getBoolean(CONFIG_NAME, msg.PeerUin, false)
        && GetActivity() == null)
    {
        sendMsg("", msg.PeerUin, "你好，我现在可能不在，你可以稍后再聊。"); //私聊自动回复内容，自行修改
    }
}

if (getString("FirstLoad", "flag") == null) {
    putString("FirstLoad", "flag", "1");
    Toast("私聊自动回复脚本加载成功！");
}

//想要留住雪花 可在掌心里 只会融化的更快