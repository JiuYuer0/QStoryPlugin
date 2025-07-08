// main.java
public void onMsg(Object msg) {
    String content = msg.MessageContent;
    String groupUin = msg.GroupUin;
    String userUin = msg.PeerUin;

    try {
        // 严格匹配指令
        if (content.trim().equals("#furry好图")) {
            String imgUrl = "https://uapis.cn/api/imgapi/furry/img4k.php";
            
            // 添加发送提示
            Toast("🐾 正在加载高清兽人图片...");
            
            // 根据聊天类型发送
            if (msg.IsGroup) {
                sendPic(groupUin, "", imgUrl);
                Toast("✅ 图片已发送至群聊");
            } else {
                sendPic("", userUin, imgUrl);
                Toast("✅ 图片已私聊发送");
            }
        }
    } catch (Exception e) {
        // 异常处理提示
        Toast("❌ 图片发送失败: " + e.getMessage());
    }
}
