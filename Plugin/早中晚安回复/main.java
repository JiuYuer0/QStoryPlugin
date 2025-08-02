String CONFIG_NAME = "WelcomeConfig";
String NAME_CACHE_PREFIX = "NameCache_";
String GREETING_CONFIG = "GreetingConfig";
String AT_REPLY_CONFIG = "AtReplyConfig";

AddItem("进群提示", "toggleWelcome");
AddItem("早中晚回复", "toggleGreeting");
AddItem("艾特回复", "toggleAtReply"); // 

public void toggleWelcome(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        Toast("这不是群聊哦～");
        return;
    }
    boolean current = getBoolean(CONFIG_NAME, groupUin, false);
    putBoolean(CONFIG_NAME, groupUin, !current);
    if (!current) {
        Toast("进群提示开");
    } else {
        Toast("进群提示关");
    }
}

public void toggleGreeting(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        Toast("这不是群聊哦～");
        return;
    }
    boolean current = getBoolean(GREETING_CONFIG, groupUin, false);
    putBoolean(GREETING_CONFIG, groupUin, !current);
    if (!current) {
        Toast("早中晚问候开");
    } else {
        Toast("早中晚问候关");
    }
}

public void toggleAtReply(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        Toast("这不是群聊哦～");
        return;
    }
    boolean current = getBoolean(AT_REPLY_CONFIG, groupUin, false);
    putBoolean(AT_REPLY_CONFIG, groupUin, !current);
    if (!current) {
        Toast("艾特回复开");
    } else {
        Toast("艾特回复关");
    }
}

public boolean isWelcomeEnabled(String groupUin) {
    return getBoolean(CONFIG_NAME, groupUin, false);
}

public boolean isGreetingEnabled(String groupUin) {
    return getBoolean(GREETING_CONFIG, groupUin, false);
}

public boolean isAtReplyEnabled(String groupUin) {
    return getBoolean(AT_REPLY_CONFIG, groupUin, false);
}

void OnTroopEvent(String groupUin, String userUin, int type) {
    if (isWelcomeEnabled(groupUin)) {
        if (type == 2) {
            // 进群欢迎
            java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Shanghai"));
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            String name = getAndCacheName(groupUin, userUin);
            sendMsg(groupUin, "", name + "(" + userUin + ") ✨小可爱～\n当前时间💫"+formattedTime+"\n这是与你的第一次相见～[PicUrl=https://youke1.picui.cn/s1/2025/07/25/68836a5c81bb4.jpg]");
        } else if (type == 1) {
            // 退群提示
            java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Shanghai"));
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            String name = getCachedName(groupUin, userUin);
            sendMsg(groupUin, "", name + "(" + userUin + ") ❄️离去了\n当前时间❄️"+formattedTime+"\n期待与你下一次见面～[PicUrl=https://youke1.picui.cn/s1/2025/07/25/68836a5c8533a.jpg]");
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
    
    String text = msg.MessageContent;
    if (text == null) return;
  //请修改成自己名字  
    if (isAtReplyEnabled(msg.GroupUin) && text.contains("这里修改")) {
        String[] replies = {
            "艾特我干嘛呀",
            "怎么啦～",
            "再艾特就🌿死你喵！[PicUrl=https://www.pnglog.com/zs3Raj.jpg]",
            "在呢～需要做什么呢？",
            "你想要了？[PicUrl=https://www.pnglog.com/JEr9fm.jpg]",
            "我在这里～想和我聊天嘛～",
            "干什么喵～[PicUrl=https://www.pnglog.com/onVCS6.jpg]"
        };
        
        int randomIndex = (int)(Math.random() * replies.length);
        String reply = replies[randomIndex];
        
        sendMsg(msg.GroupUin, "", reply);
        return;
    }
    
    if (isGreetingEnabled(msg.GroupUin)) {
        java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Shanghai"));
        java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
        String currentHour = now.format(timeFormatter);
        int hour = now.getHour();
        
        if (text.trim().equals("早上好")||text.trim().equals("早安"))  {
            String reply;
            if (hour >= 5 && hour < 9) {
                reply = "✨新的一天开始啦～现在是" + currentHour + "，记得吃早餐哦！[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885260bd4bfe.jpg]";
            } else if (hour >= 9 && hour < 12) {
                reply = "✨都快中午啦～现在才说早上好呀～现在是" + currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e69257a1f.jpg]";
            } else {
                reply = "✨现在都" + currentHour + "啦～说早上好是不是有点晚了呢～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e7b459403.png]";
            }
            sendMsg(msg.GroupUin, "", reply);
        } 
        else if (text.trim().equals("中午好")||text.trim().equals("午安"))  {
            String reply;
            if (hour >= 11 && hour < 14) {
                reply = "💫中午好！现在是" + currentHour + "，该吃饭啦～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e8843a5de.jpg]";
            } else if (hour >= 14 && hour < 18) {
                reply = "💫都下午啦～死杂鱼真笨～现在是"+ currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e923b6c7c.jpg]";
            } else {
                reply = "💫现在都" + currentHour + "了哦～不是中午哦～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e982b4106.jpg]";
            }
            sendMsg(msg.GroupUin, "", reply);
        }
        else if (text.trim().equals("下午好")) {
            String reply;
            if (hour >= 12 && hour < 18) {
                reply = "🍀下午好！现在是" + currentHour + "，下午茶时间到啦～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885e9cdce0d8.jpg]";
            } else if (hour >= 18 && hour < 24) {
                reply = "🍀都晚上啦～现在说下午好有点晚了呢～现在是" + currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885f1e3424db.jpg]";
            } else {
                reply = "🍀现在都" + currentHour + "啦～不是下午哦小笨蛋～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885f29ea8e5d.jpg]";
            }
            sendMsg(msg.GroupUin, "", reply);
        }
        else if (text.trim().equals("晚上好")) {
            String reply;
            if (hour >= 18 && hour < 24) {
                reply = "🌙晚上好！现在是" + currentHour + "，今天过得怎么样呀～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885f8929c512.png]";
            } else if (hour >= 0 && hour < 6) {
                reply = "🌙都凌晨啦，现在说晚上好有点奇怪呢～现在是" + currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885f920b6fb9.jpg]";
            } else {
                reply = "🌙现在都" + currentHour + "啦～不是晚上哦～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885f9a2aa135.jpg]";
            }
            sendMsg(msg.GroupUin, "", reply);
        }
        else if (text.trim().equals("晚安")) {
            String reply;
            if (hour >= 0 && hour < 5) {
                reply = "🌙凌晨" + currentHour + "了，早点休息哦～晚安～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885faae586a7.jpg]";
            } else if (hour >= 5 && hour < 12) {
                reply = "☀️早安！不过现在说晚安是不是太早了？现在是" + currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885faf4aac55.jpg]";
            } else if (hour >= 12 && hour < 18) {
                reply = "🌸下午好~现在说晚安还太早啦！现在是" + currentHour + "[PicUrl=https://youke1.picui.cn/s1/2025/07/27/688602dfe42e0.jpg]";
            } else if (hour >= 18 && hour < 22) {
                reply = "🌸这么早睡觉～现在才" + currentHour + "，不会要去做奇怪的事吧～杂鱼～[PicUrl=https://youke1.picui.cn/s1/2025/07/27/6885fb8b82b47.jpg]";
            } else {
                reply = "现在是" + currentHour + "啦～祝你有个好梦～[PicUrl=https://www.pnglog.com/Lj2T5z.jpg]";
            }
            sendMsg(msg.GroupUin, "", reply);
        }
    else if (text.trim().equals("拜拜")) {
        String reply;
        if (hour >= 7 && hour < 12) {
            reply = "🍀拜拜～早上好～虽然不知道你要干什么～";
        } else if (hour >= 12 && hour < 14) {
            reply = "☀️拜拜～记得吃午饭哦！";
        } else if (hour >= 14 && hour < 18) {
            reply = "🌸拜拜～下午祝你开心！";
        } else {
            reply = "拜拜～我会想你的～";
        }
        sendMsg(msg.GroupUin, "", reply);
        }
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