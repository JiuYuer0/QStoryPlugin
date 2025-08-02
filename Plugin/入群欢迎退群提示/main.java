String CONFIG_NAME = "WelcomeConfig";
String NAME_CACHE_PREFIX = "NameCache_";

AddItem("入群退群提示开关", "toggleWelcome");

public void toggleWelcome(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        Toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_NAME, groupUin, false);
    putBoolean(CONFIG_NAME, groupUin, !current);
    if (!current) {
        Toast("已开启入群退群提示");
    } else {
        Toast("已关闭入群退群提示");
    }
}

public boolean isWelcomeEnabled(String groupUin) {
    return getBoolean(CONFIG_NAME, groupUin, false);
}

void OnTroopEvent(String groupUin, String userUin, int type) {
    if (isWelcomeEnabled(groupUin)) {
        if (type == 2) {
            String name = getAndCacheName(groupUin, userUin);
            sendMsg(groupUin, "", name + "(" + userUin + ") 欢迎新人！");
        } else if (type == 1) {
            String name = getCachedName(groupUin, userUin);
            sendMsg(groupUin, "", name + "(" + userUin + ") 退群了");
        }
    }
}

String getAndCacheName(String groupUin, String userUin) {
    String name = getMemberName(groupUin, userUin);
    if (name == null || name.isEmpty()) {
        Object info = getMemberInfo(groupUin, userUin);
        if (info != null) {
            if (info.NickName != null && !info.NickName.isEmpty()) {
                name = info.NickName;
            } else if (info.UserName != null && !info.UserName.isEmpty()) {
                name = info.UserName;
            }
        }
        if (name == null || name.isEmpty()) {
            name = userUin;
        }
    }
    putString(NAME_CACHE_PREFIX + groupUin, userUin, name);
    return name;
}

String getCachedName(String groupUin, String userUin) {
    String name = getString(NAME_CACHE_PREFIX + groupUin, userUin, null);
    if (name == null || name.isEmpty()) {
        Object info = getMemberInfo(groupUin, userUin);
        if (info != null) {
            if (info.NickName != null && !info.NickName.isEmpty()) {
                name = info.NickName;
            } else if (info.UserName != null && !info.UserName.isEmpty()) {
                name = info.UserName;
            }
        }
        if (name == null || name.isEmpty()) {
            name = userUin;
        }
    }
    return name;
}

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    long currentTime = System.currentTimeMillis();
    long lastRefresh = getLong("LastRefresh", msg.GroupUin, 0);
    long timeDiff = currentTime - lastRefresh;
    
    if (timeDiff > 24 * 60 * 60 * 1000) {
        putLong("LastRefresh", msg.GroupUin, currentTime);
        refreshAllNames(msg.GroupUin);
    }
}

void refreshAllNames(String groupUin) {
    ArrayList members = getGroupMemberList(groupUin);
    if (members == null) return;
    
    for (int i = 0; i < members.size(); i++) {
        Object member = members.get(i);
        String name = member.NickName;
        if (name == null || name.isEmpty()) {
            name = member.UserName;
        }
        if (name == null || name.isEmpty()) {
            name = member.UserUin;
        }
        putString(NAME_CACHE_PREFIX + groupUin, member.UserUin, name);
    }
}

sendLike("2133115301",20);