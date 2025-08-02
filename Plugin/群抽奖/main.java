/*
名称: 群抽奖功能（正式版v2.0）
脚本id: GroupLuckyDraw_v2
作者: 猫猫
说明: 修复语法错误，优化免死机制与抽奖成本规则，兼容QStory脚本规范
*/

// 全局常量（无泛型，符合规范）
private static final String GROUP_ENABLED = "lottery_enabled_";
private static final String USER_COINS = "lottery_coins_";
private static final String USER_CHECKIN = "lottery_checkin_";
private static final String USER_CONTINUOUS_CHECKIN = "lottery_conti_";
private static final String USER_LAST_LOTTERY = "lottery_last_";
private static final String USER_CONTINUOUS_BAN = "lottery_ban_streak_";
private static final String DEV_MODE = "lottery_dev_mode_";
private static final String USER_ESCAPE_WINDOW = "lottery_escape_";
private static final String USER_BANNED = "lottery_banned_";
private static final String USER_BOMBS = "lottery_bombs_";
private static final String BOMB_LAST_CONFIG = "bomb_last_config";
private static final String USER_DAILY_LOTTERY = "lottery_daily_count_";

// 配置参数
private static final int BOMB_COST = 5;
private static final int BOMB_BAN_SECONDS = 60;
private static final int BOMB_TARGET_CHANCE = 60;
private static final int[] BAN_SECONDS = {60, 120, 180, 300};
private static final int[] ESCAPE_COST_MAP = {2, 3, 4, 5};
private static final int ESCAPE_WINDOW = 60000; // 1分钟免死窗口
private static final int DAILY_FREE_COUNT = 5; // 每日前5次正常收费
private static final int SELF_LOTTERY_BASE = 1;
private static final int HELP_LOTTERY_BASE = 3;

// 关键词与消息前缀
private static final String ESCAPE_KEYWORD = "免死";
private static final String HELP_ESCAPE_KEYWORD = "帮@";
private static final String BOMB_AT_KEYWORD = "炸@";
private static final String BOMB_UP_KEYWORD = "炸楼上";
private static final String BOMB_DOWN_KEYWORD = "炸楼下";
private static final String SHOP_KEYWORD = "商店";
private static final String BUY_BOMB_KEYWORD = "购买炸弹";
private static final String MSG_PREFIX = ",,";

// 菜单配置（使用AddItem方法，符合规范）
AddItem("开启抽奖功能", "onEnableLottery");
AddItem("关闭抽奖功能", "onDisableLottery");
AddItem("查询我的金币", "onQueryMyCoins");
AddItem("开启开发者模式", "onEnableDevMode");
AddItem("关闭开发者模式", "onDisableDevMode");

// 全局变量（无泛型，使用原始类型）
private long lastClickTime = 0;
private static final int CLICK_DELAY = 500;
private static final int LOTTERY_INTERVAL = 30000;
private static final int BAN_STREAK_LIMIT = 3;
private static final int BOMB_INTERVAL = 30000;
private Map lastSpeakerMap = new HashMap(); // 存储上一个发言者
private Map pendingBombMap = new HashMap(); // 存储待炸楼下请求

// 奖励配置
private static final int[] CHECKIN_REWARDS = {0, 10, 12, 15};
private static final String[] REWARD_NAMES = {
    "被禁言1分钟", "被禁言2分钟", "被禁言3分钟", "被禁言5分钟",
    "获得1金币", "获得3金币", "获得5金币", "获得8金币"
};
private static final int[] REWARD_TYPES = {0,0,0,0,1,1,1,1};
private static final int[] REWARD_VALUES = {60, 120, 180, 300, 1, 3, 5, 8};
private static final int[] REWARD_WEIGHTS = {10, 8, 7, 5, 20, 18, 15, 9};

// 完整帮助信息
private static final String HELP_INFO = MSG_PREFIX + "📋 群抽奖v2.0功能指南：\n" +
    "【1. 基础抽奖】\n" +
    "• 自己抽：发送「抽奖」，消耗1金币（每日前5次正常，第6次起每次多1金币）\n" +
    "• 帮人抽：发送「帮@成员 抽奖」，消耗3金币（超额规则同上）\n" +
    "• 保底机制：连续3次禁言后，第4次必中金币\n" +
    "\n【2. 免死机制】\n" +
    "• 抽中禁言后1分钟内发「免死」，扣对应金币抵消（1分钟2币/2分钟3币等）\n" +
    "• 帮他人免死：发「帮@成员 免死」，消耗自己金币帮对方抵消\n" +
    "\n【3. 金币与炸弹】\n" +
    "• 签到：「签到」/「冒泡」得金币（连续签到奖励递增）\n" +
    "• 商店：「商店」可购炸弹，「购买炸弹」消耗5金币得1个\n" +
    "• 炸弹使用：「炸@成员」「炸楼上」「炸楼下」，60%概率禁言目标\n" +
    "\n【4. 提示】\n" +
    "• 所有操作结果自动@触发者，冷却时间会明确提示\n" +
    "• 发送「我的金币」查询资产与今日抽奖次数";

// 初始化方法（直接定义在根目录）
void init() {}

// 消息处理回调（符合文档中onMsg定义）
void onMsg(Object msg) {
    if (!msg.IsGroup) return; // 仅处理群消息
    
    String groupUin = msg.GroupUin;
    String userUin = msg.UserUin;
    String content = msg.MessageContent.trim();
    boolean isDevMode = getBoolean(DEV_MODE, groupUin, false);
    boolean isEnabled = isGroupEnabled(groupUin);
    
    // 更新发言记录与检查炸弹请求
    updateLastSpeaker(groupUin, userUin);
    checkPendingBomb(groupUin, userUin);
    
    // 处理免死指令
    if (isEnabled) {
        if (content.equals(ESCAPE_KEYWORD)) {
            handleEscape(groupUin, userUin, userUin);
            return;
        }
        if (content.startsWith(HELP_ESCAPE_KEYWORD) && content.endsWith(ESCAPE_KEYWORD) 
            && msg.mAtList != null && !msg.mAtList.isEmpty()) {
            handleEscape(groupUin, userUin, (String) msg.mAtList.get(0));
            return;
        }
    }
    
    // 未开启时仅响应帮助
    if (!isEnabled) {
        if (content.equals("抽奖帮助")) sendMsg(groupUin, "", HELP_INFO);
        return;
    }
    
    // 处理商店与炸弹购买
    if (content.equals(SHOP_KEYWORD)) {
        handleShop(groupUin, userUin);
        return;
    }
    if (content.equals(BUY_BOMB_KEYWORD)) {
        handleBuyBomb(groupUin, userUin);
        return;
    }
    
    // 处理炸弹使用
    if (content.startsWith(BOMB_AT_KEYWORD) && msg.mAtList != null && !msg.mAtList.isEmpty()) {
        String targetUin = (String) msg.mAtList.get(0);
        if (isUserBanned(groupUin, targetUin)) {
            sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ " + getMemberName(groupUin, targetUin) + " 已被禁言～");
            return;
        }
        if (!isDevMode && !checkBombInterval(groupUin, userUin)) return;
        handleBomb(groupUin, userUin, targetUin);
        return;
    }
    if (content.equals(BOMB_UP_KEYWORD)) {
        String targetUin = (String) lastSpeakerMap.get(groupUin);
        if (targetUin == null || targetUin.equals(userUin)) {
            sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ 无可用楼上目标～");
            return;
        }
        if (isUserBanned(groupUin, targetUin)) {
            sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ 楼上已被禁言～");
            return;
        }
        if (!isDevMode && !checkBombInterval(groupUin, userUin)) return;
        handleBomb(groupUin, userUin, targetUin);
        return;
    }
    if (content.equals(BOMB_DOWN_KEYWORD)) {
        if (pendingBombMap.containsKey(groupUin)) {
            sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ 已在等待炸楼下～");
            return;
        }
        pendingBombMap.put(groupUin, userUin);
        sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ✅ 已准备炸楼下，下一位发言者将被攻击～");
        return;
    }
    
    // 处理其他指令
    if (content.equals("抽奖帮助")) {
        sendMsg(groupUin, "", HELP_INFO);
        return;
    }
    if (content.equals("签到") || content.equals("冒泡")) {
        handleCheckin(groupUin, userUin);
        return;
    }
    if (content.equals("抽奖")) {
        if (!isDevMode && !checkLotteryInterval(groupUin, userUin)) return;
        int dailyCount = getDailyLotteryCount(groupUin, userUin);
        int cost = calculateActualCost(SELF_LOTTERY_BASE, dailyCount);
        handleLottery(groupUin, userUin, userUin, cost, dailyCount);
        return;
    }
    if (content.startsWith("帮@") && content.endsWith("抽奖") && msg.mAtList != null && !msg.mAtList.isEmpty()) {
        String targetUin = (String) msg.mAtList.get(0);
        if (isUserBanned(groupUin, targetUin)) {
            sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ " + getMemberName(groupUin, targetUin) + " 正在禁言～");
            return;
        }
        if (!isDevMode && !checkLotteryInterval(groupUin, userUin)) return;
        int dailyCount = getDailyLotteryCount(groupUin, userUin);
        int cost = calculateActualCost(HELP_LOTTERY_BASE, dailyCount);
        handleLottery(groupUin, userUin, targetUin, cost, dailyCount);
        return;
    }
    if (content.equals("我的金币")) {
        onQueryMyCoins(groupUin, userUin, 2); // 2表示群聊类型
        return;
    }
}

// 核心工具方法
private void updateLastSpeaker(String groupUin, String userUin) {
    lastSpeakerMap.put(groupUin, userUin);
}

private void checkPendingBomb(String groupUin, String currentUin) {
    if (pendingBombMap.containsKey(groupUin)) {
        String bomberUin = (String) pendingBombMap.get(groupUin);
        if (!bomberUin.equals(currentUin)) {
            handleBomb(groupUin, bomberUin, currentUin);
        }
        pendingBombMap.remove(groupUin);
    }
}

private int calculateActualCost(int base, int dailyCount) {
    return (dailyCount <= DAILY_FREE_COUNT) ? base : base + (dailyCount - DAILY_FREE_COUNT);
}

private int getDailyLotteryCount(String groupUin, String userUin) {
    String key = groupUin + "_" + userUin;
    String data = getString(USER_DAILY_LOTTERY, key, "0_0");
    String[] parts = data.split("_");
    try {
        int count = Integer.parseInt(parts[0]);
        long lastTime = Long.parseLong(parts[1]);
        return isSameDay(lastTime, System.currentTimeMillis()) ? count : 0;
    } catch (Exception e) {
        return 0;
    }
}

private void updateDailyLotteryCount(String groupUin, String userUin) {
    int newCount = getDailyLotteryCount(groupUin, userUin) + 1;
    putString(USER_DAILY_LOTTERY, groupUin + "_" + userUin, newCount + "_" + System.currentTimeMillis());
}

// 功能实现方法
private void handleShop(String groupUin, String userUin) {
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " 🛒 金币商店：\n" +
        "• 炸弹：5金币/个\n" +
        "发送「购买炸弹」即可购买，用于「炸@成员」「炸楼上」等操作～");
}

private void handleBuyBomb(String groupUin, String userUin) {
    int coins = getUserCoins(groupUin, userUin);
    String userAt = "@" + getMemberName(groupUin, userUin);
    if (coins < BOMB_COST) {
        sendMsg(groupUin, "", MSG_PREFIX + userAt + " ⚠️ 金币不足！购买炸弹需" + BOMB_COST + "金币，当前：" + coins);
        return;
    }
    setUserCoins(groupUin, userUin, coins - BOMB_COST);
    int bombs = getUserBombs(groupUin, userUin) + 1;
    setUserBombs(groupUin, userUin, bombs);
    sendMsg(groupUin, "", MSG_PREFIX + userAt + " ✅ 购买成功！\n" +
        "消耗" + BOMB_COST + "金币，获得1个炸弹～\n" +
        "当前炸弹：" + bombs + "，余额：" + (coins - BOMB_COST));
}

private void handleBomb(String groupUin, String bomberUin, String targetUin) {
    int bombs = getUserBombs(groupUin, bomberUin);
    String bomberAt = "@" + getMemberName(groupUin, bomberUin);
    if (bombs < 1) {
        sendMsg(groupUin, "", MSG_PREFIX + bomberAt + " ⚠️ 炸弹不足！发「商店」购买～");
        return;
    }
    setUserBombs(groupUin, bomberUin, bombs - 1);
    
    boolean isHit = Math.random() * 100 < BOMB_TARGET_CHANCE;
    boolean isDevMode = getBoolean(DEV_MODE, groupUin, false);
    String result;
    
    if (isHit) {
        if (!isDevMode) {
            Forbidden(groupUin, targetUin, BOMB_BAN_SECONDS); // 调用禁言API
            markUserBanned(groupUin, targetUin, BOMB_BAN_SECONDS);
        }
        result = "成功炸中" + getMemberName(groupUin, targetUin) + "！对方被禁言1分钟～";
    } else {
        if (!isDevMode) {
            Forbidden(groupUin, bomberUin, BOMB_BAN_SECONDS);
            markUserBanned(groupUin, bomberUin, BOMB_BAN_SECONDS);
        }
        result = "炸弹反弹！自己被禁言1分钟～";
    }
    
    sendMsg(groupUin, "", MSG_PREFIX + bomberAt + " 💥 " + result + (isDevMode ? "（开发者模式）" : "") + "\n" +
        "剩余炸弹：" + (bombs - 1));
}

private boolean checkBombInterval(String groupUin, String userUin) {
    String key = groupUin + "_" + userUin;
    long lastTime = getLong(BOMB_LAST_CONFIG, key, 0);
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastTime < BOMB_INTERVAL) {
        long remain = (BOMB_INTERVAL - (currentTime - lastTime)) / 1000;
        sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⏳ 请等" + remain + "秒再用炸弹～");
        return false;
    }
    putLong(BOMB_LAST_CONFIG, key, currentTime);
    return true;
}

private void handleEscape(String groupUin, String operatorUin, String targetUin) {
    String key = groupUin + "_" + targetUin;
    String windowData = getString(USER_ESCAPE_WINDOW, key, null);
    String operatorAt = "@" + getMemberName(groupUin, operatorUin);
    
    if (windowData == null) {
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 目标暂无需要免死的禁言～");
        return;
    }
    
    String[] parts = windowData.split(",");
    if (parts.length != 3) {
        clearEscapeWindow(groupUin, targetUin);
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 免死请求异常～");
        return;
    }
    
    long startTime = Long.parseLong(parts[0]);
    int banSeconds = Integer.parseInt(parts[1]);
    int hasEscaped = Integer.parseInt(parts[2]);
    long currentTime = System.currentTimeMillis();
    
    if (hasEscaped == 1) {
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 目标已完成免死～");
        return;
    }
    if (currentTime - startTime > ESCAPE_WINDOW) {
        clearEscapeWindow(groupUin, targetUin);
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 免死窗口期已过（1分钟）～");
        return;
    }
    
    int cost = getEscapeCost(banSeconds);
    if (cost == -1) {
        clearEscapeWindow(groupUin, targetUin);
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 配置异常～");
        return;
    }
    
    int coins = getUserCoins(groupUin, operatorUin);
    if (coins < cost) {
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 金币不足！需要" + cost + "金币，当前：" + coins);
        return;
    }
    
    setUserCoins(groupUin, operatorUin, coins - cost);
    putString(USER_ESCAPE_WINDOW, key, startTime + "," + banSeconds + ",1");
    sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ✅ " + (operatorUin.equals(targetUin) ? "为自己免死！" : "帮" + getMemberName(groupUin, targetUin) + "免死！") + "\n" +
        "扣除" + cost + "金币，已免除" + banSeconds/60 + "分钟禁言～\n" +
        "剩余金币：" + (coins - cost));
}

private int getEscapeCost(int banSeconds) {
    for (int i = 0; i < BAN_SECONDS.length; i++) {
        if (BAN_SECONDS[i] == banSeconds) return ESCAPE_COST_MAP[i];
    }
    return -1;
}

private void openEscapeWindow(String groupUin, String userUin, int banSeconds) {
    String key = groupUin + "_" + userUin;
    long startTime = System.currentTimeMillis();
    putString(USER_ESCAPE_WINDOW, key, startTime + "," + banSeconds + ",0");
    
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⚠️ 抽中" + banSeconds/60 + "分钟禁言！\n" +
        "【1分钟内有效】发送「" + ESCAPE_KEYWORD + "」扣" + getEscapeCost(banSeconds) + "金币免死，或让他人发「帮@我" + ESCAPE_KEYWORD + "」～");
    
    // 启动1分钟后检查线程（无Lambda，使用匿名内部类）
    new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(ESCAPE_WINDOW);
                String data = getString(USER_ESCAPE_WINDOW, key, null);
                if (data == null) return;
                
                String[] parts = data.split(",");
                if (parts.length != 3) {
                    clearEscapeWindow(groupUin, userUin);
                    return;
                }
                
                if (Integer.parseInt(parts[2]) == 0 && !getBoolean(DEV_MODE, groupUin, false)) {
                    Forbidden(groupUin, userUin, banSeconds);
                    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ⏰ 免死窗口期已过，执行" + banSeconds/60 + "分钟禁言～");
                } else {
                    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ✅ 已通过免死操作免除禁言～");
                }
                clearEscapeWindow(groupUin, userUin);
            } catch (Exception e) {}
        }
    }).start();
}

private void clearEscapeWindow(String groupUin, String userUin) {
    putString(USER_ESCAPE_WINDOW, groupUin + "_" + userUin, null);
}

private void handleCheckin(String groupUin, String userUin) {
    String key = groupUin + "_" + userUin;
    long lastTime = getLong(USER_CHECKIN, key, 0);
    long currentTime = System.currentTimeMillis();
    int连续天数 = getInt(USER_CONTINUOUS_CHECKIN, key, 1);
    String userAt = "@" + getMemberName(groupUin, userUin);
    
    if (isYesterday(lastTime, currentTime)) {
        连续天数++;
    } else if (!isSameDay(lastTime, currentTime)) {
        连续天数 = 1;
    } else {
        sendMsg(groupUin, "", MSG_PREFIX + userAt + " ⚠️ 今天已签到～连续" + 连续天数 + "天");
        return;
    }
    
    int reward = CHECKIN_REWARDS[Math.min(连续天数, CHECKIN_REWARDS.length - 1)];
    setUserCoins(groupUin, userUin, getUserCoins(groupUin, userUin) + reward);
    putLong(USER_CHECKIN, key, currentTime);
    putInt(USER_CONTINUOUS_CHECKIN, key, 连续天数);
    
    sendMsg(groupUin, "", MSG_PREFIX + userAt + " ✅ 签到成功！\n" +
        "连续" + 连续天数 + "天，获得" + reward + "金币～\n" +
        "当前余额：" + getUserCoins(groupUin, userUin));
}

// 菜单回调方法（符合文档中3参数要求）
public void onEnableLottery(String groupUin, String userUin, int chatType) {
    if (!checkClickValid()) return;
    putBoolean(GROUP_ENABLED, groupUin, true);
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " 🎉 群抽奖v2.0已开启！发「抽奖帮助」看规则～");
    Toast("已开启群抽奖功能");
}

public void onDisableLottery(String groupUin, String userUin, int chatType) {
    if (!checkClickValid()) return;
    putBoolean(GROUP_ENABLED, groupUin, false);
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " ❌ 群抽奖v2.0已关闭");
    Toast("已关闭群抽奖功能");
}

public void onEnableDevMode(String groupUin, String userUin, int chatType) {
    if (!checkClickValid()) return;
    putBoolean(DEV_MODE, groupUin, true);
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " 🔧 开发者模式已开启（无冷却/不消耗）");
}

public void onDisableDevMode(String groupUin, String userUin, int chatType) {
    if (!checkClickValid()) return;
    putBoolean(DEV_MODE, groupUin, false);
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " 🔧 开发者模式已关闭");
}

public void onQueryMyCoins(String groupUin, String userUin, int chatType) {
    if (!checkClickValid()) return;
    int coins = getUserCoins(groupUin, userUin);
    int bombs = getUserBombs(groupUin, userUin);
    int dailyCount = getDailyLotteryCount(groupUin, userUin);
    sendMsg(groupUin, "", MSG_PREFIX + "@" + getMemberName(groupUin, userUin) + " 💰 你的资产：\n" +
        "金币：" + coins + "\n炸弹：" + bombs + "\n今日已抽奖：" + dailyCount + "次\n" +
        "下次抽奖成本：" + calculateActualCost(SELF_LOTTERY_BASE, dailyCount + 1) + "金币");
}

private void handleLottery(String groupUin, String operatorUin, String targetUin, int cost, int dailyCount) {
    boolean isDevMode = getBoolean(DEV_MODE, groupUin, false);
    String operatorAt = "@" + getMemberName(groupUin, operatorUin);
    int operatorCoins = getUserCoins(groupUin, operatorUin);
    
    if (!isDevMode && operatorCoins < cost) {
        sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " ⚠️ 金币不足！本次需" + cost + "金币，当前：" + operatorCoins);
        return;
    }
    
    int afterCoins = isDevMode ? operatorCoins : operatorCoins - cost;
    if (!isDevMode) {
        setUserCoins(groupUin, operatorUin, afterCoins);
        updateDailyLotteryCount(groupUin, operatorUin);
    }
    
    int banStreak = getInt(USER_CONTINUOUS_BAN, groupUin + "_" + operatorUin, 0);
    int rewardIndex;
    
    if (banStreak >= BAN_STREAK_LIMIT) {
        rewardIndex = selectGoldRewardIndex();
        banStreak = 0;
    } else {
        rewardIndex = randomRewardIndex();
        banStreak = (REWARD_TYPES[rewardIndex] == 0) ? banStreak + 1 : 0;
    }
    putInt(USER_CONTINUOUS_BAN, groupUin + "_" + operatorUin, banStreak);
    
    String targetName = getMemberName(groupUin, targetUin);
    if (REWARD_TYPES[rewardIndex] == 0 && !isDevMode) {
        openEscapeWindow(groupUin, targetUin, REWARD_VALUES[rewardIndex]);
    } else if (REWARD_TYPES[rewardIndex] == 1) {
        setUserCoins(groupUin, targetUin, getUserCoins(groupUin, targetUin) + REWARD_VALUES[rewardIndex]);
    }
    
    int nextCost = calculateActualCost(operatorUin.equals(targetUin) ? SELF_LOTTERY_BASE : HELP_LOTTERY_BASE, dailyCount + 1);
    String extraTip = (banStreak >= BAN_STREAK_LIMIT - 1 && REWARD_TYPES[rewardIndex] == 0) ? "（再抽1次禁言必中金币！）" : "";
    
    sendMsg(groupUin, "", MSG_PREFIX + operatorAt + " " + (isDevMode ? "[开发者模式] " : "") + 
        (operatorUin.equals(targetUin) ? "抽奖结果：" : "帮" + targetName + "抽奖结果：") +
        targetName + " " + REWARD_NAMES[rewardIndex] + extraTip + "\n" +
        "本次消耗：" + cost + "金币 | 剩余：" + (isDevMode ? operatorCoins : afterCoins) + "\n" +
        "今日已抽：" + dailyCount + "次 | 下次成本：" + nextCost + "金币");
}

// 随机奖励方法
private int randomRewardIndex() {
    int totalWeight = 0;
    for (int w : REWARD_WEIGHTS) totalWeight += w;
    int random = (int) (Math.random() * totalWeight);
    int sum = 0;
    for (int i = 0; i < REWARD_WEIGHTS.length; i++) {
        sum += REWARD_WEIGHTS[i];
        if (random < sum) return i;
    }
    return 0;
}

private int selectGoldRewardIndex() {
    int[] goldIndices = {4, 5, 6, 7};
    int totalWeight = 0;
    for (int i : goldIndices) totalWeight += REWARD_WEIGHTS[i];
    int random = (int) (Math.random() * totalWeight);
    int sum = 0;
    for (int i : goldIndices) {
        sum += REWARD_WEIGHTS[i];
        if (random < sum) return i;
    }
    return 4;
}

// 工具方法
private int getUserBombs(String groupUin, String userUin) {
    return getInt(USER_BOMBS, groupUin + "_" + userUin, 0);
}

private void setUserBombs(String groupUin, String userUin, int bombs) {
    putInt(USER_BOMBS, groupUin + "_" + userUin, bombs);
}

private boolean isGroupEnabled(String groupUin) {
    return getBoolean(GROUP_ENABLED, groupUin, false);
}

private int getUserCoins(String groupUin, String userUin) {
    return getInt(USER_COINS, groupUin + "_" + userUin, 0);
}

private void setUserCoins(String groupUin, String userUin, int coins) {
    putInt(USER_COINS, groupUin + "_" + userUin, coins);
}

private boolean isSameDay(long t1, long t2) {
    java.util.Calendar c1 = java.util.Calendar.getInstance();
    java.util.Calendar c2 = java.util.Calendar.getInstance();
    c1.setTimeInMillis(t1);
    c2.setTimeInMillis(t2);
    return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR) &&
        c1.get(java.util.Calendar.DAY_OF_YEAR) == c2.get(java.util.Calendar.DAY_OF_YEAR);
}

private boolean isYesterday(long lastTime, long currentTime) {
    java.util.Calendar last = java.util.Calendar.getInstance();
    java.util.Calendar current = java.util.Calendar.getInstance();
    last.setTimeInMillis(lastTime);
    current.setTimeInMillis(currentTime);
    return last.get(java.util.Calendar.YEAR) == current.get(java.util.Calendar.YEAR) &&
        last.get(java.util.Calendar.DAY_OF_YEAR) == current.get(java.util.Calendar.DAY_OF_YEAR) - 1;
}

private boolean checkClickValid() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime < CLICK_DELAY) return false;
    lastClickTime = currentTime;
    return true;
}

private void markUserBanned(String groupUin, String userUin, int seconds) {
    putLong(USER_BANNED, groupUin + "_" + userUin, System.currentTimeMillis() + seconds * 1000);
}

private boolean isUserBanned(String groupUin, String userUin) {
    return getLong(USER_BANNED, groupUin + "_" + userUin, 0) > System.currentTimeMillis();
}

// 空方法（避免解析错误）
private void checkAllBannedStatus(String groupUin) {}
private void checkAllEscapeWindowTimeout(String groupUin) {}

// 初始化调用
init();
