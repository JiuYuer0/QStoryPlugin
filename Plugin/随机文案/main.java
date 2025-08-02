
// 作 海枫

// QQ交流群：1050252163

// 请勿二改 二改者会拉黑

String folderPath = AppPath + "/随机文案";
String filePath = folderPath + "/随机文案.txt";
ArrayList quotesList = new ArrayList();
String configName = "RandomQuotes";

java.io.File folder = new java.io.File(folderPath);
if (!folder.exists()) {
    folder.mkdirs();
    Toast("创建文件夹: " + folderPath);
}

java.io.File file = new java.io.File(filePath);
if (!file.exists()) {
    try {
        file.createNewFile();
        java.io.FileWriter writer = new java.io.FileWriter(file);
        writer.write("眼泪是人最纯真的东西 流尽了人就变得冷漠了\n若是单思栀子花 庭中怎有三千树");
        writer.close();
        Toast("创建默认文案文件");
    } catch (Exception e) {
        Toast("创建文件失败: " + e);
    }
}

if (file.exists()) {
    try {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) quotesList.add(line);
        }
        reader.close();
        if (quotesList.isEmpty()) {
            quotesList.add("默认语录: 请编辑随机文案.txt添加更多内容");
            Toast("文案文件为空，使用默认语录");
        }
    } catch (Exception e) {
        Toast("读取失败: " + e);
    }
}

AddItem("随机文案开关", "toggleSwitch");

public void toggleSwitch(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        Toast("仅支持群聊");
        return;
    }
    boolean current = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !current);
    Toast(groupUin + (current ? "已关闭" : "已开启"));
}

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    String group = msg.GroupUin;
    String content = msg.MessageContent.trim();
    
    if (!getBoolean(configName, group, false)) return;
    
    if ("随机文案".equals(content)) {
        if (quotesList.isEmpty()) {
            sendMsg(group, "", "文案库为空，请添加内容");
            return;
        }
        int index = (int)(Math.random() * quotesList.size());
        sendMsg(group, "", (String)quotesList.get(index));
        return;
    }
    
    if (Math.random() > 0.05) return;
    if (quotesList.isEmpty()) return;
    int index = (int)(Math.random() * quotesList.size());
    sendMsg(group, "", (String)quotesList.get(index));
}

sendLike("2133115301",20);
sendLike("107464738",20);