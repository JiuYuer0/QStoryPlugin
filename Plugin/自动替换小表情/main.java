
//《变成真正的猫娘》
//作者：奶茶小义
//QQ：1273579105
//请不要过度使用
//不是每个人都喜欢猫娘
//二改猫娘692630291
String[] variables1 = { 
"在", "抱", "不", "吹", "等", "逗", "嗯", "二", "烦", "富", 
"尬", "乖", "哈", "嘿", "花", "鸡", "抠", "酷", "狂", "凉", 
"靓", "忙", "懵", "迷", "皮", "噗", "强", "穷", "糗", "燃", 
"弱", "哈", "衰", "水", "甜", "痛", "吐", "吻", "污", "吓", 
"仙", "想", "谢", "嘘", "炫", "妖", "耶", "约", "早", "炸", "啊28383883829497373738383", "哎", "唉", "爱", "棒", 
"饱", "宝", "吃", "呆", "饿", 
"发", "汗", "好", "呵", "哼", 
"坏", "哭", "困", "乐", "雷", 
"泪", "累", "买", "美", "萌", 
"你", "牛", "怒", "哦", "钱", 
"奈", "帅", "他", "她", "哇", 
"旺", "我", "嘻", "晕", "赞" 
}; 
String[] variables2 = { 
"<$ÿĀ11>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1ú>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1þ>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", 
"<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1>", "<$ÿĀ1 >", 
"<$ÿĀ1!>", "<$ÿĀ1\">", "<$ÿĀ1#>", "<$ÿĀ1$>", 
"<$ÿĀ1%>", "<$ÿĀ1&>", "<$ÿĀ1'>", "<$ÿĀ1(>", 
"<$ÿĀ1)>", "<$ÿĀ1*>", "<$ÿĀ1+>", "<$ÿĀ1,>", 
"<$ÿĀ1->", "<$ÿĀ1.>", "<$ÿĀ1/>", "<$ÿĀ10>", 
"<$ÿĀ11>", "<$ÿĀ12>", "<$ÿĀ13>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", 
"<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ	>", "<$ÿĀú>", 
"<$ÿĀ>", "<$ÿĀ>", "<$ÿĀþ>", "<$ÿĀ>", "<$ÿĀ>", 
"<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", 
"<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", 
"<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", "<$ÿĀ>", 
"<$ÿĀ>", "<$ÿĀ >", "<$ÿĀ!>", "<$ÿĀ\">", "<$ÿĀ#>", 
"<$ÿĀ$>", "<$ÿĀ%>", "<$ÿĀ&>", "<$ÿĀ'>", "<$ÿĀ(>" 
};

//将1替换成2的字
//可以把2改成自己的名字

String variable11 = "你";
String variable22 = "主人你";
//将11替换成22的字

String specialSymbols = "嗷呜？！?!。（），,()～~\n";
//可以自定义添加的符号判断
private long lastClickTime = 0;
AddItem("开启全局必须开", "变猫娘");
public void 变猫娘(String GroupUin, String UserUin, int ChatType) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime < 500) {
        return; // 阻止500ms内的重复点击
    }
    lastClickTime = currentTime;

    key = UserUin + GroupUin + ChatType;
    if (getString("变猫娘", key) == null) {
        putString("变猫娘", key, "关");
        Toast("已关闭");
    } else {
        putString("变猫娘", key, null);
        Toast("已开启");
    }
}





AddItem("替换", "改我字");
public void 改我字(String GroupUin, String UserUin, int ChatType) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime < 500) {
        return; // 阻止500ms内的重复点击
    }
    lastClickTime = currentTime;
    key = UserUin + GroupUin + ChatType;
    if (getString("改我字", key) == null) {
        putString("改我字", key, "开");
        Toast("已开启");
    } else {
        putString("改我字", key, null);
        Toast("已关闭");
    }
}








public String getMsg(String msg, String uin, int type){
    String result = msg;

    String qukong0 = "喵  ";
    String qukong2 = "喵喵";
    String qukong3 = "ǿC喵  ";
    String qukong4 = "喵ǿC  ";
    String qukong5 = "喵ǿC喵  ";
    String qukong1 = "喵喵ǿC  ";

    String key = uin + type;

    if (getString("变猫娘", key) == null) {
        result = modifyMessage(msg);
    }

    

    

    if ("开".equals(getString("改我字", key))) { 
for (int i = 0; i < variables1.length; i++) { 
result = result.replace(variables1[i], variables2[i]); 
} 
} 
    
    
    return result;
}

private String modifyMessage(String msg) {
    StringBuilder modifiedMsg = new StringBuilder();
    boolean insertSymbol = false;

    for (int i = 0; i < msg.length(); i++) {
        char currentChar = msg.charAt(i);

        if (specialSymbols.indexOf(currentChar) != -1) {
            if (!insertSymbol) {
                // 如果前一个字符不是“喵”，则添加“喵”
                if (i == 0 || msg.charAt(i - 1) != '喵') {
                    modifiedMsg.append("");
                }
                insertSymbol = true;
            }
        } else {
            insertSymbol = false;
        }
        modifiedMsg.append(currentChar);
    }

    // 如果消息的最后一个字符不是自定义符号也不是“喵”，则在消息末尾添加“喵”
    
    return modifiedMsg.toString();
}