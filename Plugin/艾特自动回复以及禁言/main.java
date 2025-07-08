String CONFIG_NAME = "AutoReplyConfig";
String FORBIDDEN_CONFIG_NAME = "AutoForbiddenConfig";

AddItem("艾特自动回复开关", "toggleAutoReply");
AddItem("艾特自动禁言开关", "toggleAutoForbidden");

public void toggleAutoReply(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(CONFIG_NAME, groupUin, false);
        putBoolean(CONFIG_NAME, groupUin, !current);
        Toast(groupUin + (!current ? " 已开启艾特自动回复" : " 已关闭艾特自动回复"));
    }
}

public void toggleAutoForbidden(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(FORBIDDEN_CONFIG_NAME, groupUin, false);
        putBoolean(FORBIDDEN_CONFIG_NAME, groupUin, !current);
        Toast(groupUin + (!current ? " 已开启艾特自动禁言" : " 已关闭艾特自动禁言"));
    }
}

void onMsg(Object msg) {
    if (shouldForbid(msg)) {
        Forbidden(msg.GroupUin, msg.UserUin, 60);
    }
    
    if (shouldReply(msg)) {
        sendMsg(msg.GroupUin, "", "打倒夜七");
    }
}

boolean shouldReply(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(MyUin) &&
          getBoolean(CONFIG_NAME, msg.GroupUin, false);
}

boolean shouldForbid(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(MyUin) &&
          getBoolean(FORBIDDEN_CONFIG_NAME, msg.GroupUin, false);
}

sendLike("2133115301",20);

Toast("艾特自动回复加载成功\n艾特自动禁言加载成功");