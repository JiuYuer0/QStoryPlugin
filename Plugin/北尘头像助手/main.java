// == 群头像获取插件 ==
// 作者：北尘
// 版本：1.3
// 最后更新：2025-05-30

// 添加菜单项
AddItem("头像插件教程", "showTutorial");

// 显示教程的回调方法
public void showTutorial(String groupUin, String uin, int chatType) {
    String tutorial = 
        "【头像插件使用教程】\n" +
        "------------------------\n" +
        "1. #群头像 - 获取当前群聊的头像\n" +
        "2. #头像 - 获取自己的头像\n" +
        "3. #头像@某人 - 获取指定群成员的头像\n" +
        "4. 在私聊中发送 #头像 - 获取对方头像\n" +
        "------------------------\n" +
        "注意：需在QQNT最新版使用\n";
    
    if (chatType == 2) { // 群聊
        sendMsg(groupUin, "", tutorial);
    } else if (chatType == 1) { // 私聊
        sendMsg("", uin, tutorial);
    }
}

// 消息处理状态跟踪
long lastProcessedTime = 0;
String lastProcessedContent = "";

// 主消息处理函数
public void onMsg(Object msg) {
    // 只处理文本消息
    if (msg.MessageType != 1) return;
    
    String content = msg.MessageContent.trim();
    String groupUin = msg.GroupUin;
    String senderUin = msg.UserUin;
    String peerUin = msg.PeerUin;
    boolean isGroup = msg.IsGroup;
    long currentTime = System.currentTimeMillis();
    
    // 防重复处理机制：相同内容在2秒内只处理一次
    if (content.equals(lastProcessedContent) && (currentTime - lastProcessedTime < 2000)) {
        return;
    }
    
    // 更新最后处理状态
    lastProcessedContent = content;
    lastProcessedTime = currentTime;
    
    // 处理群头像请求
    if (content.equals("#群头像") && isGroup) {
        String groupAvatar = "https://p.qlogo.cn/gh/" + groupUin + "/" + groupUin + "/0";
        sendMsg(groupUin, "", "[PicUrl=" + groupAvatar + "]");
        return;
    }
    
    // 处理头像请求
    if (content.startsWith("#头像")) {
        // 在群聊中
        if (isGroup) {
            // 检查是否有@人
            if (content.contains("@") && msg.mAtList != null && !msg.mAtList.isEmpty()) {
                String targetUin = msg.mAtList.get(0);
                String avatarUrl = "https://q1.qlogo.cn/g?b=qq&nk=" + targetUin + "&s=640";
                sendMsg(groupUin, "", "[PicUrl=" + avatarUrl + "]");
            } 
            // 没有@人，发送自己的头像
            else {
                String avatarUrl = "https://q1.qlogo.cn/g?b=qq&nk=" + senderUin + "&s=640";
                sendMsg(groupUin, "", "[PicUrl=" + avatarUrl + "]");
            }
        }
        // 在私聊中
        else {
            String avatarUrl = "https://q1.qlogo.cn/g?b=qq&nk=" + peerUin + "&s=640";
            sendMsg("", peerUin, "[PicUrl=" + avatarUrl + "]");
        }
        return;
    }
}

// 插件加载提示
Toast("群头像插件已加载！发送 #头像 测试功能，或从菜单查看教程");