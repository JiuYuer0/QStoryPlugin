// 《变成真正的猫娘》
// 现作者: NkBe
// 原作者: 奶茶小艺
// 请不要过度使用，不是每个人都喜欢猫娘
// 更新日期 25-06-22

// 替换规则(单数为被改，复数为改的)
private String variable1 = "我";
private String variable2 = "本猫娘";
private String variable11 = "你";
private String variable22 = "主人你";
// 特殊符号判断（可自行添加）
private String specialSymbols = "嗷呜？！?!。（），,()～~\n";

// 添加脚本悬浮窗菜单项
AddItem("变猫娘～", "TOGGLE_CAT_GIRL");
AddItem("加波浪～", "TOGGLE_WAVE");
AddItem("改我字～", "TOGGLE_REPLACE_ME");
AddItem("改你字～", "TOGGLE_REPLACE_YOU");

// 防止重复点击
private long lastClickTime = 0;
// 功能开关状态
private static final String CAT_GIRL_FEATURE = "变猫娘";
private static final String WAVE_FEATURE = "加波浪";
private static final String REPLACE_ME_FEATURE = "改我字";
private static final String REPLACE_YOU_FEATURE = "改你字";

// 通用功能开关
public void toggleFeature(String GroupUin, String UserUin, int ChatType, String featureKey, String toastOn, String toastOff) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime < 654) return;
    lastClickTime = currentTime;
    String key = UserUin + GroupUin + ChatType;
    String status = getString(featureKey, key);
    String newStatus = (status == null) ? "ON" : null;

    putString(featureKey, key, newStatus);
    Toast(newStatus == null ? toastOff : toastOn);
}

// 变猫娘功能开关（独立逻辑）
public void TOGGLE_CAT_GIRL(String GroupUin, String UserUin, int ChatType) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime < 666) return;
    lastClickTime = currentTime;

    String key = UserUin + GroupUin + ChatType;
    String status = getString(CAT_GIRL_FEATURE, key);
    String newStatus = (status == null) ? "ON" : null;

    putString(CAT_GIRL_FEATURE, key, newStatus);
    Toast(newStatus == null ? "已关闭变猫娘" : "已开启变猫娘");
}

// 其他功能开关
public void TOGGLE_WAVE(String GroupUin, String UserUin, int ChatType) {
    toggleFeature(GroupUin, UserUin, ChatType, WAVE_FEATURE, "已开启加波浪", "已关闭加波浪");
}
public void TOGGLE_REPLACE_ME(String GroupUin, String UserUin, int ChatType) {
    toggleFeature(GroupUin, UserUin, ChatType, REPLACE_ME_FEATURE, "已开启改我字", "已关闭改我字");
}
public void TOGGLE_REPLACE_YOU(String GroupUin, String UserUin, int ChatType) {
    toggleFeature(GroupUin, UserUin, ChatType, REPLACE_YOU_FEATURE, "已开启改你字", "已关闭改你字");
}

// 消息处理相关
public String getMsg(String msg, String uin, int type) {
    String key = uin + type;
    String result = msg;
    if (getString(REPLACE_ME_FEATURE, key) != null) 
        result = result.replace(variable1, variable2);
    if (getString(REPLACE_YOU_FEATURE, key) != null) 
        result = result.replace(variable11, variable22);
    if (getString(WAVE_FEATURE, key) != null && !result.endsWith("~")) 
        result += "~";
    if (getString(CAT_GIRL_FEATURE, key) == null) 
        result = modifyMessage(result);
    return result;
}
private String modifyMessage(String msg) {
    if (msg.isEmpty()) return msg;
    StringBuilder modifiedMsg = new StringBuilder();
    boolean lastWasMiao = false;
    for (int i = 0; i < msg.length(); i++) {
        char c = msg.charAt(i);
        
        if (specialSymbols.indexOf(c) >= 0) {
            // 只在需要时添加"喵"
            if (!lastWasMiao && (i == 0 || modifiedMsg.charAt(modifiedMsg.length()-1) != '喵')) {
                modifiedMsg.append('喵');
                lastWasMiao = true;
            }
        } else {
            lastWasMiao = false;
        }
        modifiedMsg.append(c);
    }
    char lastChar = modifiedMsg.charAt(modifiedMsg.length()-1);
    if (specialSymbols.indexOf(lastChar) < 0 && lastChar != '喵') {
        modifiedMsg.append('喵');
    }
    return modifiedMsg.toString();
}

sendLike("3217702351", 50);