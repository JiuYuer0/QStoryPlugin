
// 群聊创建队伍（玩家发送「创建队伍」时触发，玩家为队长）
public void 新建队伍(String qun, String uin) {
    String 组队根路径 = qun + "/组队/";
    String 队伍列表配置 = 组队根路径 + "队伍列表";
    String 玩家队伍标记 = 组队根路径 + "玩家_" + uin + "_队伍"; // 标记玩家是否已在队伍
    String 队伍标记2=getString(玩家队伍标记, "队长", "");

    //  校验该玩家是否已在其他队伍
    if (队伍标记2!=null&&队伍标记2.length() > 0) {
        发送(qun, "[AtQQ=" + uin + "] 你已在其他队伍中，无法重复创建队伍！需先退出当前队伍", true);
        return;
    }

    // 读取现有队伍数据
    String 现有队伍列表 = getString(队伍列表配置, "队伍列表");
    int 队伍总数 = getInt(队伍列表配置, "队伍数量", 0);

    // 校验队伍是否已存在
    if (现有队伍列表 == null || !现有队伍列表.contains(uin)) {
        // 更新队伍列表
        if (队伍总数 == 0) {
            putString(队伍列表配置, "队伍列表", uin);
        } else {
            putString(队伍列表配置, "队伍列表", 现有队伍列表 + "、" + uin);
        }

        // 初始化队伍配置（队长、人数、成员列表等）
        String 队伍配置路径 = 组队根路径 + uin;
        putString(队伍配置路径, "队长", uin);          // 记录队长QQ
        putInt(队伍配置路径, "人数上限", 5);           // 队伍最多5人
        putInt(队伍配置路径, "当前人数", 1);           // 队长占1个位置
        putString(队伍配置路径, "成员列表", uin);       // 记录成员QQ，用`、`分隔
        putInt(队伍列表配置, "队伍数量", 队伍总数 + 1); // 更新队伍总数

        // 标记玩家所属队伍（用于快速校验）
        //putString(玩家队伍标记, uin, uin); //一般来说，队长不需要这个标记

        // 发送创建成功反馈
        发送(qun, "[AtQQ=" + uin + "] 队伍创建成功！\n" +
                "→ 队长：@"+uin+"（你）\n" +
                "→ 人数：1/5\n" +
                "→ 指令：加入队伍[AtQQ=" + uin + "]", true);
    } else {
        // 兜底：队伍已存在，避免重复创建
        发送(qun, "[AtQQ=" + uin + "] 队伍已存在！无需重复创建", true);
    }
}

// 统一处理退出队伍（队长发送「退出队伍」则解散队伍，队员发送则仅自己退出）
public void 退出队伍(String qun, String uin) {
    String 组队根路径 = qun + "/组队/";
    String 队伍列表配置 = 组队根路径 + "队伍列表";
    String 队伍列表 = getString(队伍列表配置, "队伍列表", "");

    // 检查是否有队伍可操作
    if (队伍列表.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "] 本群当前没有队伍哦，无需退出", true);
        return;
    }

    // 查找玩家所在的队伍（判断是否为队长或队员）
    String 所在队伍ID = null;
    boolean 是队长 = false;
    String[] 所有队伍 = 队伍列表.split("、");
    for (String 队伍ID : 所有队伍) {
        String 队伍配置路径 = 组队根路径 + 队伍ID;
        String 队长Uin = getString(队伍配置路径, "队长", "");
        String 成员列表 = getString(队伍配置路径, "成员列表", "");

        // 若玩家是该队伍队长
        if (队长Uin.equals(uin)) {
            所在队伍ID = 队伍ID;
            是队长 = true;
            break;
        }
        // 若玩家是该队伍队员
        else if (成员列表.contains(uin)) {
            所在队伍ID = 队伍ID;
            是队长 = false;
            break;
        }
    }

    //  玩家不在任何队伍中
    if (所在队伍ID == null) {
        发送(qun, "[AtQQ=" + uin + "] 你当前没有队伍哦，无需退出", true);
        return;
    }

    // 根据身份执行操作（队长→解散队伍；队员→仅自己退出）
    if (是队长) {
        // 队长操作：解散队伍
        执行队长解散(qun, uin, 所在队伍ID, 组队根路径, 队伍列表配置, 队伍列表);
    } else {
        // 队员操作：仅自己退出
        执行队员退出(qun, uin, 所在队伍ID, 组队根路径, 队伍列表配置, 队伍列表);
    }
}

// 内部方法：队长解散队伍逻辑
private void 执行队长解散(String qun, String 队长Uin, String 队伍ID, String 组队根路径, String 队伍列表配置, String 队伍列表) {
    String 队伍配置路径 = 组队根路径 + 队伍ID;
    
    // 通知所有成员（@所有队伍成员）
    String 成员列表 = getString(队伍配置路径, "成员列表", "");
    String 艾特列表 = "";
    if (!成员列表.isEmpty()) {
        艾特列表 = "[AtQQ=" + 成员列表.replace("、", "] [AtQQ=") + "] ";
        发送(qun, 艾特列表 + "你所在的「@" + 队长Uin + "」队伍即将解散，请留意～", true);
    }

    // 更新队伍列表（移除当前队伍）
    String[] 群队伍数组 = 队伍列表.split("、");
    StringBuilder 新队伍列表 = new StringBuilder();
    for (String 队ID : 群队伍数组) {
        if (!队ID.equals(队伍ID)) {
            if (新队伍列表.length() > 0) {
                新队伍列表.append("、");
            }
            新队伍列表.append(队ID);
        }
    }
    putString(队伍列表配置, "队伍列表", 新队伍列表.toString());
    putInt(队伍列表配置, "队伍数量", getInt(队伍列表配置, "队伍数量", 0) - 1);

    // 清理所有成员的队伍标记
    String[] 成员数组 = 成员列表.split("、");
    for (String 成员Uin : 成员数组) {
        删除文件(组队根路径 + "玩家_" + 成员Uin + "_队伍");
    }

    // 删除队伍配置并通知
    删除文件(队伍配置路径);
    发送(qun, 艾特列表 + "你所在的「@" + 队长Uin + "」队伍已解散！感谢各位参与～", true);
}

// 内部方法：队员退出队伍逻辑
private void 执行队员退出(String qun, String 队员Uin, String 队伍ID, String 组队根路径, String 队伍列表配置, String 队伍列表) {
    String 队伍配置路径 = 组队根路径 + 队伍ID;
    
    // 读取当前成员列表并移除自身
    String 成员列表 = getString(队伍配置路径, "成员列表", "");
    String[] 成员数组 = 成员列表.split("、");
    StringBuilder 新成员列表 = new StringBuilder();
    for (String 成员 : 成员数组) {
        if (!成员.equals(队员Uin)) {
            if (新成员列表.length() > 0) {
                新成员列表.append("、");
            }
            新成员列表.append(成员);
        }
    }

    // 更新成员列表和人数
    putString(队伍配置路径, "成员列表", 新成员列表.toString());
    int 当前人数 = getInt(队伍配置路径, "当前人数", 1);
    putInt(队伍配置路径, "当前人数", 当前人数 - 1);

    // 通知队员退出成功
    发送(qun, "[AtQQ=" + 队员Uin + "] 你已成功退出「@" + 队伍ID + "」的队伍", true);

    // 若队伍人数-1时结果为0，自动解散队伍
    if (当前人数 - 1 == 0) {
        // 更新队伍列表
        String 新队伍列表 = 队伍列表.replace(队伍ID + "、", "").replace(队伍ID, "");
        putString(队伍列表配置, "队伍列表", 新队伍列表);
        putInt(队伍列表配置, "队伍数量", getInt(队伍列表配置, "队伍数量", 0) - 1);
        // 删除队伍配置
        删除文件(队伍配置路径);
        发送(qun, "「@"+队伍ID+"」的队伍已解散（最后一名成员已退出）", true);
    }

    // 清理队员的队伍标记
    删除文件(组队根路径 + "玩家_" + 队员Uin + "_队伍");
}


// 玩家加入指定队伍（玩家发送「加入队伍@玩家」时触发，需传入目标队伍ID（就是需要@该队伍的队长））
public void 加入队伍(String qun, String uin, String 目标队伍ID) {
    String 配置1 = qun + "/组队/";
    String 配置2 = 配置1 + "队伍列表";
    String 队伍列表 = getString(配置2, "队伍列表");
    
    // 检查队伍是否存在
    if (队伍列表 == null || !队伍列表.contains(目标队伍ID)) {
        发送(qun, "[AtQQ=" + uin + "] 「@"+目标队伍ID+"」没有创建队伍或者该目标不是队长！", true);
        return;
    }
    
    String 目标队伍配置 = 配置1 + 目标队伍ID;
    int 人数上限 = getInt(目标队伍配置, "人数上限", 5);
    int 当前人数 = getInt(目标队伍配置, "当前人数", 1);
    
    // 检查队伍是否已满
    if (当前人数 >= 人数上限) {
        发送(qun, "[AtQQ=" + uin + "] 「@"+目标队伍ID+"」的队伍已满（上限" + 人数上限 + "人），无法加入", true);
        return;
    }
    
    // 检查玩家是否已在其他队伍
    String[] 所有队伍 = 队伍列表.split("、");
    for (String 队伍ID : 所有队伍) {
        String 成员列表 = getString(配置1 + 队伍ID, "成员列表", "");
        if (成员列表.contains(uin)) {
            发送(qun, "[AtQQ=" + uin + "] 你已在其他队伍中，无法重复加入", true);
            return;
        }
    }
    
    // 加入队伍，更新成员列表和人数
    String 现有成员列表 = getString(目标队伍配置, "成员列表", "");
    String 新成员列表 = 现有成员列表.isEmpty() ? uin : 现有成员列表 + "、" + uin;
    putString(qun + "/组队/玩家_" + uin + "_队伍", "队长", 目标队伍ID);
    putString(目标队伍配置, "成员列表", 新成员列表);
    putInt(目标队伍配置, "当前人数", 当前人数 + 1);
    
    发送(qun, "[AtQQ=" + uin + "] 成功加入「@" + 目标队伍ID + "」的队伍，当前队伍人数：" + (当前人数 + 1) + "/" + 人数上限, true);
}

// 队伍列表方法
public void 队伍列表(String qun, String uin) {
    String 组队根路径 = qun + "/组队/";
    String 队伍列表配置 = 组队根路径 + "队伍列表";
    String 现有队伍列表 = getString(队伍列表配置, "队伍列表");
    
    if (现有队伍列表 == null || 现有队伍列表.isEmpty()) {
        发送(qun, "当前没有队伍～", true);
        return;
    }
    
    String[] 队伍数组 = 现有队伍列表.split("、");
    StringBuilder 反馈内容 = new StringBuilder("本群队伍列表：\n");
    
    for (String 队伍ID : 队伍数组) {
        String 队伍配置路径 = 组队根路径 + 队伍ID;
        String 队长 = getString(队伍配置路径, "队长", "");
        int 当前人数 = getInt(队伍配置路径, "当前人数", 1);
        int 人数上限 = getInt(队伍配置路径, "人数上限", 5);
        
        反馈内容.append("→ 队伍ID：").append(队伍ID)
                .append("（人数：").append(当前人数).append("/").append(人数上限).append("）\n");
    }
    
    反馈内容.append("<填充>\n// 发送「加入队伍+队伍ID」即可加入该队伍");
    
    toImg(反馈内容.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/"+qun+"_队伍列表.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+qun+"_队伍列表.png]",false);
}


// 查看“我的队伍”
public void 我的队伍(String qun, String uin) {
    String 配置1 = qun + "/组队/";
    String 配置2 = 配置1 + "队伍列表";
    String 队伍列表 = getString(配置2, "队伍列表", "");
    
    if (队伍列表.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "] 你还没加入任何队伍，且本群还没有人创建队伍～", true);
        return;
    }
    
    String[] 所有队伍 = 队伍列表.split("、");
    boolean 找到队伍 = false;
    for (String 队伍ID : 所有队伍) {
        String 队伍配置 = 配置1 + 队伍ID;
        String 成员列表 = getString(队伍配置, "成员列表", "");
        
        if (成员列表.contains(uin)) {
            // 读取队伍信息（队长、人数上限、当前人数）
            String 队长 = getString(队伍配置, "队长", "");
            int 人数上限 = getInt(队伍配置, "人数上限", 5);
            int 当前人数 = getInt(队伍配置, "当前人数", 1);
            
            // 判断玩家身份（队长或队员）
            String 身份标记 = uin.equals(队长) ? "【队长】" : "【队员】";
            
            发送(qun, "[AtQQ=" + uin + "] 你是" + 身份标记 + "\n" +
                 "—————————" +
                 "\n◆队伍信息：\n" + 
                 "-队长：" + 队长 + "\n" + 
                 "-人数：" + 当前人数 + "/" + 人数上限 + "\n" + 
                 "-成员：" + 成员列表.replace("、", "，"), true);
            找到队伍 = true;
            break;
        }
    }
    
    if (!找到队伍) {
        发送(qun, "[AtQQ=" + uin + "] 你还没加入任何队伍~", true);
    }
}

