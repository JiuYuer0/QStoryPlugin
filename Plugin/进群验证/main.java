import java.util.concurrent.*;

// ===== 可配置常量 =====
// 验证相关配置
final String VERIFY_QUESTION = "#FF0000是什么颜色？\n(不要回复我，不要艾特我)"; // 验证问题
final String[] CORRECT_ANSWERS = {"红色", "red"}; // 正确答案（不区分大小写）
final int VERIFY_TIMEOUT = 600; // 验证超时时间（秒），10分钟
final int BAN_DURATION = 2592000; // 超时禁言时长（秒），30天=2592000秒
final int WRONG_ANSWER_BAN_DURATION = 60; // 回答错误禁言时长（秒），默认60秒
final int NON_VERIFIER_BAN_DURATION = 60 * 60 * 24; // 非验证者发送答案禁言时长（24小时）

// 消息模板
final String WELCOME_MSG = "[AtQQ={0}] 欢迎加入群聊！请回答验证问题：\n{1}\n(限时{2})"; // {0}:成员QQ, {1}:问题, {2}:超时时间
final String SUCCESS_MSG = "[AtQQ={0}] 恭喜 {1} 验证通过！"; // {0}:成员QQ, {1}:成员名称
final String TIMEOUT_MSG = "{0} 未在{1}内完成验证，已被禁言{2}天"; // {0}:成员名称, {1}:超时时间, {2}:禁言天数
final String INCORRECT_MSG = "回答错误！你已被禁言{0}，请重新回答\n{1}"; // {0}:禁言时间, {1}:问题
final String START_VERIFY_MSG = "[AtQQ={0}] 管理员要求你进行验证：\n{1}\n(限时{2})"; // {0}:成员QQ, {1}:问题, {2}:超时时间
final String NON_VERIFIER_WARNING = "[AtQQ={0}] 非验证者禁止回答！已禁言24小时"; // {0}:发送者QQ

// 系统配置
final long CHECK_INTERVAL = 30; // 超时检查间隔（秒）
final long INIT_DELAY = 30; // 初始延迟（秒）

// 存储配置
final String CONFIG_NAME = "GroupVerifyConfig";
final String PENDING_CONFIG = "PendingMembers";

// ===== 辅助函数：格式化时间为分钟+秒 =====
String formatTime(int seconds) {
    if (seconds < 60) {
        return seconds + "秒";
    }
    
    int minutes = seconds / 60;
    int remainingSeconds = seconds % 60;
    
    if (remainingSeconds == 0) {
        return minutes + "分钟";
    }
    
    return minutes + "分钟" + remainingSeconds + "秒";
}

// ===== 辅助函数：获取超时时间字符串 =====
String getTimeoutString() {
    return formatTime(VERIFY_TIMEOUT);
}

// ===== 脚本功能实现 =====
// 创建定时线程池
ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

// 初始化菜单
AddItem("进群验证开关", "toggleVerify");

// 启动定时任务
void startScheduler() {
    // 定期检查超时成员
    scheduler.scheduleAtFixedRate(new Runnable() {
        public void run() {
            checkAllTimeouts();
        }
    }, 
    INIT_DELAY, // 初始延迟
    CHECK_INTERVAL, // 检查间隔
    TimeUnit.SECONDS);
}

// 开关验证功能
public void toggleVerify(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        Toast("请进入群聊后再操作");
        return;
    }
    
    boolean isEnabled = getBoolean(CONFIG_NAME, groupUin, false);
    putBoolean(CONFIG_NAME, groupUin, !isEnabled);
    
    if (!isEnabled) {
        Toast("已开启 " + groupUin + " 群的进群验证");
    } else {
        // 关闭时清除待验证列表
        putString(PENDING_CONFIG, groupUin, null);
        Toast("已关闭 " + groupUin + " 群的进群验证");
    }
}

// 检查验证状态
public boolean isVerifyEnabled(String groupUin) {
    return getBoolean(CONFIG_NAME, groupUin, false);
}

// 处理进群事件
void OnTroopEvent(String groupUin, String userUin, int type) {
    if (type == 2 && isVerifyEnabled(groupUin)) { // 进群事件
        // 跳过自己
        if (userUin.equals(MyUin)) return;
        
        // 获取成员昵称
        String userName = getMemberName(groupUin, userUin);
        if (userName == null || userName.isEmpty()) {
            userName = "新成员";
        }
        
        // 格式化欢迎消息
        String welcomeMsg = WELCOME_MSG
            .replace("{0}", userUin)
            .replace("{1}", VERIFY_QUESTION)
            .replace("{2}", getTimeoutString());
        
        // 在群内发送验证问题
        sendMsg(groupUin, "", welcomeMsg);
        
        // 记录待验证成员
        addPendingMember(groupUin, userUin);
    }
}

// 添加待验证成员
void addPendingMember(String groupUin, String userUin) {
    String pending = getString(PENDING_CONFIG, groupUin, "");
    if (!pending.isEmpty()) {
        pending += ",";
    }
    pending += userUin + ":" + System.currentTimeMillis();
    putString(PENDING_CONFIG, groupUin, pending);
}

// 处理群消息
void onMsg(Object msg) {
    // 只处理群聊消息
    if (!msg.IsGroup) return;
    String groupUin = msg.GroupUin;
    String senderUin = msg.UserUin;
    String content = msg.MessageContent.trim();
    
    // 1. 处理"发起验证"命令
    if (msg.IsSend && content.startsWith("发起验证")) {
        handleStartVerifyCommand(groupUin, content, msg);
        return;
    }
    
    // 检查该群是否开启验证
    if (!isVerifyEnabled(groupUin)) return;
    
    // 2. 检查是否是待验证成员的回复
    String pending = getString(PENDING_CONFIG, groupUin, "");
    if (pending.contains(senderUin)) {
        handleMemberResponse(groupUin, senderUin, content, msg);
    }
    // 3. 处理非验证者发送答案的情况
    else if (isPotentialAnswer(content)) {
        handleNonVerifierResponse(groupUin, senderUin, msg);
    }
}

// 处理"发起验证"命令
void handleStartVerifyCommand(String groupUin, String content, Object msg) {
    // 提取被艾特的成员
    ArrayList atList = msg.mAtList;
    if (atList == null || atList.isEmpty()) {
        sendReply(groupUin, msg, "请艾特需要验证的成员");
        return;
    }
    
    // 为每个被艾特成员发起验证
    for (String userUin : atList) {
        // 跳过自己
        if (userUin.equals(MyUin)) continue;
        
        // 获取成员昵称
        String userName = getMemberName(groupUin, userUin);
        if (userName == null || userName.isEmpty()) {
            userName = "成员";
        }
        
        // 发送验证问题
        String verifyMsg = START_VERIFY_MSG
            .replace("{0}", userUin)
            .replace("{1}", VERIFY_QUESTION)
            .replace("{2}", getTimeoutString());
        
        sendMsg(groupUin, "", verifyMsg);
        
        // 记录待验证成员（覆盖旧记录）
        updatePendingMember(groupUin, userUin);
    }
}

// 更新待验证成员
void updatePendingMember(String groupUin, String userUin) {
    String pending = getString(PENDING_CONFIG, groupUin, "");
    StringBuilder newPending = new StringBuilder();
    boolean found = false;
    
    // 更新现有记录或添加新记录
    if (!pending.isEmpty()) {
        String[] members = pending.split(",");
        for (String member : members) {
            if (!member.isEmpty()) {
                if (member.startsWith(userUin + ":")) {
                    // 更新现有记录
                    if (newPending.length() > 0) newPending.append(",");
                    newPending.append(userUin).append(":").append(System.currentTimeMillis());
                    found = true;
                } else {
                    // 保留其他记录
                    if (newPending.length() > 0) newPending.append(",");
                    newPending.append(member);
                }
            }
        }
    }
    
    // 如果是新成员，添加到列表
    if (!found) {
        if (newPending.length() > 0) newPending.append(",");
        newPending.append(userUin).append(":").append(System.currentTimeMillis());
    }
    
    putString(PENDING_CONFIG, groupUin, newPending.toString());
}

// 处理成员响应
void handleMemberResponse(String groupUin, String userUin, String answer, Object msg) {
    // 检查答案是否正确
    boolean isCorrect = false;
    for (String correct : CORRECT_ANSWERS) {
        if (answer.equalsIgnoreCase(correct)) {
            isCorrect = true;
            break;
        }
    }
    
    if (isCorrect) {
        // 验证通过
        removePendingMember(groupUin, userUin);
        
        // 获取成员昵称
        String userName = getMemberName(groupUin, userUin);
        if (userName == null || userName.isEmpty()) {
            userName = userUin;
        }
        
        // 格式化成功消息
        String successMsg = SUCCESS_MSG
            .replace("{0}", userUin)
            .replace("{1}", userName);
        
        // 回复消息（使用sendReply）
        sendReply(groupUin, msg, successMsg);
    } else {
        // 答案错误 - 禁言用户
        Forbidden(groupUin, userUin, WRONG_ANSWER_BAN_DURATION);
        
        // 格式化错误消息
        String incorrectMsg = INCORRECT_MSG
            .replace("{0}", formatTime(WRONG_ANSWER_BAN_DURATION))
            .replace("{1}", VERIFY_QUESTION);
        
        // 回复消息（使用sendReply）
        sendReply(groupUin, msg, incorrectMsg);
    }
}

// 处理非验证者发送答案
void handleNonVerifierResponse(String groupUin, String senderUin, Object msg) {
    // 禁言发送者
    Forbidden(groupUin, senderUin, NON_VERIFIER_BAN_DURATION);
    
    // 发送警告消息
    String warningMsg = NON_VERIFIER_WARNING.replace("{0}", senderUin);
    sendReply(groupUin, msg, warningMsg);
}

// 检查是否是可能的答案
boolean isPotentialAnswer(String content) {
    content = content.toLowerCase();
    for (String answer : CORRECT_ANSWERS) {
        if (content.contains(answer.toLowerCase())) {
            return true;
        }
    }
    return false;
}

// 定时检查所有群聊的超时成员
void checkAllTimeouts() {
    ArrayList groupList = getGroupList();
    for (Object group : groupList) {
        String groupUin = group.GroupUin;
        if (isVerifyEnabled(groupUin)) {
            checkTimeouts(groupUin);
        }
    }
}

// 检查单个群聊的超时成员
void checkTimeouts(String groupUin) {
    String pending = getString(PENDING_CONFIG, groupUin, "");
    if (pending.isEmpty()) return;
    
    long currentTime = System.currentTimeMillis();
    String[] members = pending.split(",");
    StringBuilder remaining = new StringBuilder();
    boolean updated = false;
    
    for (String member : members) {
        if (member.isEmpty()) continue;
        
        String[] parts = member.split(":");
        if (parts.length < 2) continue;
        
        String userUin = parts[0];
        long joinTime = Long.parseLong(parts[1]);
        long elapsedSeconds = (currentTime - joinTime) / 1000;
        
        if (elapsedSeconds > VERIFY_TIMEOUT) { // 超时处理
            // 禁言处理
            Forbidden(groupUin, userUin, BAN_DURATION);
            
            // 获取成员昵称
            String userName = getMemberName(groupUin, userUin);
            if (userName == null || userName.isEmpty()) {
                userName = userUin;
            }
            
            // 格式化超时消息
            String timeoutMsg = TIMEOUT_MSG
                .replace("{0}", userName)
                .replace("{1}", getTimeoutString())
                .replace("{2}", String.valueOf(BAN_DURATION / 86400));
            
            // 群内通知
            sendMsg(groupUin, "", timeoutMsg);
            updated = true;
        } else {
            // 未超时，保留
            if (remaining.length() > 0) remaining.append(",");
            remaining.append(member);
        }
    }
    
    // 如果有更新，保存新列表
    if (updated) {
        putString(PENDING_CONFIG, groupUin, remaining.toString());
    }
}

// 移除待验证成员
void removePendingMember(String groupUin, String userUin) {
    String pending = getString(PENDING_CONFIG, groupUin, "");
    if (pending.isEmpty()) return;
    
    String[] members = pending.split(",");
    StringBuilder newPending = new StringBuilder();
    boolean updated = false;
    
    for (String member : members) {
        if (member.startsWith(userUin + ":")) {
            updated = true;
            continue;
        }
        if (!member.isEmpty()) {
            if (newPending.length() > 0) newPending.append(",");
            newPending.append(member);
        }
    }
    
    if (updated) {
        putString(PENDING_CONFIG, groupUin, newPending.toString());
    }
}

// 初始化定时任务
startScheduler();
Toast("进群验证脚本已加载！");