import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

// 保底奖励配置（道具在 道具.java）
private String jifenbaodi = "百亿积分卡";  // 积分大保底
private String jinbibaodi = "涅槃";      // 金币大保底
private String jifenSmallBaodi = "一亿积分卡"; // 积分小保底
private String jinbiSmallBaodi = "吞噬卡";   // 金币小保底
// 大小保底次数配置
private final int POINT_SMALL_BAODI = 1000;  // 积分小保底次数
private final int POINT_BIG_BAODI = 2500;   // 积分大保底次数
private final int GOLD_SMALL_BAODI = 500;   // 金币小保底次数
private final int GOLD_BIG_BAODI = 1000;    // 金币大保底次数

// 单次夺宝消耗
final long DB_JIFEN = 1500000; // 积分夺宝单次消耗
final long DB_JINBI = 10;     // 金币夺宝单次消耗


// 积分宝池道具（名称、数量范围、概率%）
private String[][] pointTreasurePool = {
    {"百万积分卡", "10-15", "20"},
    {"焕能丹", "3-5", "15"},
    {"百万积分卡", "1", "5"},
    {"瑶光宝箱", "10-30", "12"},
    {"聚灵丹", "20-30", "10"},
    {"龙珠", "1-2", "8"},
    {"星核", "3-6", "15"},
    {"灵藤", "1", "7"},
    {"玄铁", "15-25", "6"},
    {"凝神丹", "1-4", "2"}
};

// 金币宝池道具
private String[][] goldTreasurePool = {
    {"涅槃", "1-2", "5"},
    {"御体丹", "30-50", "18"},
    {"破锋丹", "30-40", "15"},
    {"长生丹", "15-30", "12"},
    {"瑶光宝箱", "30-50", "10"},
    {"小焕能丹", "50-80", "15"},
    {"经验丹", "5-20", "10"},
    {"灵智丹", "15-25", "8"},
    {"50金币", "1-3", "5"},
    {"吞噬卡", "1-2", "2"}
};

// 获取道具稀有度（根据概率）
private String getRarity(int probability) {
    if (probability <= 5) return "★传说";
    if (probability <= 10) return "☆稀有";
    return "○普通";
}

// 解析数量范围（如"10-15"返回随机数）
private int parseRandomCount(String countStr) {
    if (countStr.contains("-")) {
        String[] parts = countStr.split("-");
        int min = Integer.parseInt(parts[0]);
        int max = Integer.parseInt(parts[1]);
        return min + (int)(Math.random() * (max - min + 1));
    } else {
        return Integer.parseInt(countStr);
    }
}

// 添加道具到背包（使用Map优化效率）
private void addItemToBag(String qun, String uin, String itemName, int count) {
    String bagKey = qun + "/" + uin + "/我的背包";
    // 先获取当前道具数量（默认0）
    long current = Long.parseLong(getString(bagKey, itemName, "0"));
    putString(bagKey, itemName, String.valueOf(current + count));
    
    // 更新道具列表（仅记录存在的道具）
    String itemList = getString(bagKey, "道具列表", "");
    if (!itemList.contains(itemName)) {
        String newList = itemList.isEmpty() ? itemName : itemList + "、" + itemName;
        putString(bagKey, "道具列表", newList);
    }
}


// 积分夺宝处理
void handlePointTreasure(String qun, String uin, int count) {
    String bagKey = qun + "/" + uin + "/我的背包";
    // 1. 读取背包中夺宝券数量
    long generalTicket = 0;
    try {
        generalTicket = Long.parseLong(getString(bagKey, "通用夺宝券", "0"));
    } catch (Exception e) {
        generalTicket = 0;
    }
    long pointTicket = 0;
    try {
        pointTicket = Long.parseLong(getString(bagKey, "积分夺宝券", "0"));
    } catch (Exception e) {
        pointTicket = 0;
    }
    long totalTicket = generalTicket + pointTicket;

    // 2. 计算需用积分支付的次数（夺宝券不足时）
    int needPointCount = 0;
    if (totalTicket < count) {
        needPointCount = count - (int) totalTicket;
    }

    // 3. 夺宝券足够时，完全跳过积分消耗；不足时才计算积分消耗
    long totalCost = 0;
    if (needPointCount > 0) {
        // 仅计算需积分支付部分的消耗（支持折扣）
        if (needPointCount == 100) {
            totalCost = (long) (DB_JIFEN * 0.7 * 100);
        } else if (needPointCount == 10) {
            totalCost = (long) (DB_JIFEN * 0.9 * 10);
        } else {
            totalCost = DB_JIFEN * needPointCount;
        }

        // 检查积分是否充足
        String configName = qun + "/" + uin + "/我的资产";
        long userPoints;
        try {
            userPoints = Long.parseLong(getString(configName, "积分", "0"));
        } catch (Exception e) {
            userPoints = 0;
        }
        if (userPoints < totalCost) {
            sendMsg(qun, "", "[AtQQ=" + uin + "] 积分不足！当前：" + userPoints + "，还需：" + (totalCost - userPoints));
            return;
        }
        // 扣除积分
        putString(configName, "积分", String.valueOf(userPoints - totalCost));
    }

    // 4. 扣除夺宝券（优先通用，再专用）
    long useGeneral = Math.min(generalTicket, count);
    long usePoint = count - useGeneral;

    // 扣除通用夺宝券
    if (useGeneral > 0) {
        long remainingGeneral = generalTicket - useGeneral;
        if (remainingGeneral > 0) {
            putString(bagKey, "通用夺宝券", String.valueOf(remainingGeneral));
        } else {
            String itemList = getString(bagKey, "道具列表", "");
            String newList = itemList.replace("通用夺宝券、", "").replace("、通用夺宝券", "").replace("通用夺宝券", "");
            putString(bagKey, "道具列表", newList.isEmpty() ? null : newList);
            putString(bagKey, "通用夺宝券", null);
        }
    }

    // 扣除积分夺宝券
    if (usePoint > 0) {
        long remainingPoint = pointTicket - usePoint;
        if (remainingPoint > 0) {
            putString(bagKey, "积分夺宝券", String.valueOf(remainingPoint));
        } else {
            String itemList = getString(bagKey, "道具列表", "");
            String newList = itemList.replace("积分夺宝券、", "").replace("、积分夺宝券", "").replace("积分夺宝券", "");
            putString(bagKey, "道具列表", newList.isEmpty() ? null : newList);
            putString(bagKey, "积分夺宝券", null);
        }
    }

    // 5. 抽取道具并统计（总次数不变）
    Map itemStats = new HashMap();
    List rareItems = new ArrayList();
    boolean isTenDraw = (count == 10);
    int actualDrawCount = isTenDraw ? 10 : count; // 明确实际夺宝次数
    if (isTenDraw) {
        List rarePool = new ArrayList();
        for (String[] item : pointTreasurePool) {
            if (Integer.parseInt(item[2]) <= 10) {
                rarePool.add(item);
            }
        }
        String[] rareItem = rarePool.get(new Random().nextInt(rarePool.size()));
        int rareCount = parseRandomCount(rareItem[1]);
        itemStats.put(rareItem[0], rareCount);
        addItemToBag(qun, uin, rareItem[0], rareCount);
        rareItems.add(rareItem[0] + "×" + rareCount);
        count--; // 十连抽已处理1次，剩余9次
    }
    for (int i = 0; i < count; i++) {
        String[] item = getRandomItem(pointTreasurePool);
        String itemName = item[0];
        int itemCount = parseRandomCount(item[1]);
        itemStats.put(itemName, itemStats.getOrDefault(itemName, 0) + itemCount);
        addItemToBag(qun, uin, itemName, itemCount);
        if (Integer.parseInt(item[2]) <= 10) {
            rareItems.add(itemName + "×" + itemCount);
        }
    }

    // 6. 生成结果文本
    String result = "积分夺宝" + actualDrawCount + "次";
    if (totalTicket > 0) {
        result += "（使用通用夺宝券×" + useGeneral + "，积分夺宝券×" + usePoint + "）";
    }
    if (needPointCount > 0) {
        result += "，消耗积分〔" + totalCost + "〕";
    }
    result += "\n";
    result += "<填充>\n◆获得稀有道具：\n";
    if (rareItems.isEmpty()) {
        result += "无\n";
    } else {
        StringBuilder rareStr = new StringBuilder();
        int rareIndex = 0;
        for (String rare : rareItems) {
            rareStr.append(rare);
            rareIndex++;
            if (rareIndex % 3 == 0) {
                rareStr.append("\n");
            } else if (rareIndex != rareItems.size()) {
                rareStr.append("、");
            }
        }
        result += rareStr.toString() + "\n";
    }
    result += "<填充>\n◆全部奖励（统计）：\n";
    for (Object entryObj : itemStats.entrySet()) {
        Map.Entry entry = (Map.Entry) entryObj;
        String itemName = (String) entry.getKey();
        int prob = 0;
        for (String[] item : pointTreasurePool) {
            if (item[0].equals(itemName)) {
                prob = Integer.parseInt(item[2]);
                break;
            }
        }
        result += "- " + getRarity(prob) + " " + itemName + "×" + entry.getValue() + "\n";
    }

    // 7. 累计夺宝次数，检查保底
    String countKey = qun + "/" + uin + "/夺宝计数";
    long pointCount;
    try {
        pointCount = Long.parseLong(getString(countKey, "积分次数", "0")) + actualDrawCount;
    } catch (Exception e) {
        pointCount = actualDrawCount;
    }

    // 小保底处理
    long smallBaodiThreshold = POINT_SMALL_BAODI;
    long previousCount = pointCount - actualDrawCount;
    long previousSmallCycles = previousCount / smallBaodiThreshold;
    long currentSmallCycles = pointCount / smallBaodiThreshold;
    long smallRewardCount = currentSmallCycles - previousSmallCycles;
    if (smallRewardCount > 0) {
        addItemToBag(qun, uin, jifenSmallBaodi, (int) smallRewardCount);
        result += "*☆小保底奖励：" + jifenSmallBaodi + "×" + smallRewardCount + "*\n";
    }

    // 大保底处理
    while (pointCount >= POINT_BIG_BAODI) {
        addItemToBag(qun, uin, jifenbaodi, 1);
        result += "*★大保底奖励：" + jifenbaodi + "×1*\n";
        pointCount -= POINT_BIG_BAODI;
    }
    putString(countKey, "积分次数", String.valueOf(pointCount));

    // 8. 发送结果
    toImg(玩家名(qun, uin) + " (" + uin + ")\n" + result, "", 0.5, 0, 30, AppPath + "/缓存/其他/" + uin + "_积分夺宝_" + actualDrawCount + ".png", false);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_积分夺宝_" + actualDrawCount + ".png]", false);
}


// 金币夺宝处理
void handleGoldTreasure(String qun, String uin, int count) {
    String bagKey = qun + "/" + uin + "/我的背包";
    // 1. 读取背包中夺宝券数量
    long generalTicket = 0;
    try {
        generalTicket = Long.parseLong(getString(bagKey, "通用夺宝券", "0"));
    } catch (Exception e) {
        generalTicket = 0;
    }
    long goldTicket = 0;
    try {
        goldTicket = Long.parseLong(getString(bagKey, "金币夺宝券", "0"));
    } catch (Exception e) {
        goldTicket = 0;
    }
    long totalTicket = generalTicket + goldTicket;

    // 2. 计算需用金币支付的次数（夺宝券不足时）
    int needGoldCount = 0;
    if (totalTicket < count) {
        needGoldCount = count - (int) totalTicket;
    }

    // 3. 夺宝券足够时，完全跳过金币消耗；不足时才计算金币消耗
    long totalCost = 0;
    if (needGoldCount > 0) {
        // 仅计算需金币支付部分的消耗（支持折扣）
        if (needGoldCount == 100) {
            totalCost = (long) (DB_JINBI * 0.7 * 100);
        } else if (needGoldCount == 10) {
            totalCost = (long) (DB_JINBI * 0.9 * 10);
        } else {
            totalCost = DB_JINBI * needGoldCount;
        }

        // 检查金币是否充足
        String configName = qun + "/" + uin + "/我的资产";
        long userGold;
        try {
            userGold = Long.parseLong(getString(configName, "金币", "0"));
        } catch (Exception e) {
            userGold = 0;
        }
        if (userGold < totalCost) {
            sendMsg(qun, "", "[AtQQ=" + uin + "] 金币不足！当前：" + userGold + "，还需：" + (totalCost - userGold));
            return;
        }
        // 扣除金币
        putString(configName, "金币", String.valueOf(userGold - totalCost));
    }

    // 4. 扣除夺宝券（优先通用，再专用）
    long useGeneral = Math.min(generalTicket, count);
    long useGold = count - useGeneral;

    // 扣除通用夺宝券
    if (useGeneral > 0) {
        long remainingGeneral = generalTicket - useGeneral;
        if (remainingGeneral > 0) {
            putString(bagKey, "通用夺宝券", String.valueOf(remainingGeneral));
        } else {
            String itemList = getString(bagKey, "道具列表", "");
            String newList = itemList.replace("通用夺宝券、", "").replace("、通用夺宝券", "").replace("通用夺宝券", "");
            putString(bagKey, "道具列表", newList.isEmpty() ? null : newList);
            putString(bagKey, "通用夺宝券", null);
        }
    }

    // 扣除金币夺宝券
    if (useGold > 0) {
        long remainingGold = goldTicket - useGold;
        if (remainingGold > 0) {
            putString(bagKey, "金币夺宝券", String.valueOf(remainingGold));
        } else {
            String itemList = getString(bagKey, "道具列表", "");
            String newList = itemList.replace("金币夺宝券、", "").replace("、金币夺宝券", "").replace("金币夺宝券", "");
            putString(bagKey, "道具列表", newList.isEmpty() ? null : newList);
            putString(bagKey, "金币夺宝券", null);
        }
    }

    // 5. 抽取道具并统计（总次数不变）
    Map itemStats = new HashMap();
    List rareItems = new ArrayList();
    boolean isTenDraw = (count == 10);
    int actualDrawCount = isTenDraw ? 10 : count; // 明确实际夺宝次数
    if (isTenDraw) {
        List rarePool = new ArrayList();
        for (String[] item : goldTreasurePool) {
            if (Integer.parseInt(item[2]) <= 10) {
                rarePool.add(item);
            }
        }
        String[] rareItem = rarePool.get(new Random().nextInt(rarePool.size()));
        int rareCount = parseRandomCount(rareItem[1]);
        itemStats.put(rareItem[0], rareCount);
        addItemToBag(qun, uin, rareItem[0], rareCount);
        rareItems.add(rareItem[0] + "×" + rareCount);
        count--; // 十连抽已处理1次，剩余9次
    }
    for (int i = 0; i < count; i++) {
        String[] item = getRandomItem(goldTreasurePool);
        String itemName = item[0];
        int itemCount = parseRandomCount(item[1]);
        itemStats.put(itemName, itemStats.getOrDefault(itemName, 0) + itemCount);
        addItemToBag(qun, uin, itemName, itemCount);
        if (Integer.parseInt(item[2]) <= 10) {
            rareItems.add(itemName + "×" + itemCount);
        }
    }

    // 6. 生成结果文本
    String result = "金币夺宝" + actualDrawCount + "次";
    if (totalTicket > 0) {
        result += "（使用通用夺宝券×" + useGeneral + "，金币夺宝券×" + useGold + "）";
    }
    if (needGoldCount > 0) {
        result += "，消耗金币〔" + totalCost + "〕";
    }
    result += "\n";
    result += "<填充>\n◆获得稀有道具：\n";
    if (rareItems.isEmpty()) {
        result += "无\n";
    } else {
        StringBuilder rareStr = new StringBuilder();
        int rareIndex = 0;
        for (String rare : rareItems) {
            rareStr.append(rare);
            rareIndex++;
            if (rareIndex % 3 == 0) {
                rareStr.append("\n");
            } else if (rareIndex != rareItems.size()) {
                rareStr.append("、");
            }
        }
        result += rareStr.toString() + "\n";
    }
    result += "<填充>\n◆全部奖励（统计）：\n";
    for (Object entryObj : itemStats.entrySet()) {
        Map.Entry entry = (Map.Entry) entryObj;
        String itemName = (String) entry.getKey();
        int prob = 0;
        for (String[] item : goldTreasurePool) {
            if (item[0].equals(itemName)) {
                prob = Integer.parseInt(item[2]);
                break;
            }
        }
        result += "- " + getRarity(prob) + " " + itemName + "×" + entry.getValue() + "\n";
    }

    // 7. 累计夺宝次数，检查保底
    String countKey = qun + "/" + uin + "/夺宝计数";
    long goldCount;
    try {
        goldCount = Long.parseLong(getString(countKey, "金币次数", "0")) + actualDrawCount;
    } catch (Exception e) {
        goldCount = actualDrawCount;
    }

    // 小保底处理
    long smallBaodiThreshold = GOLD_SMALL_BAODI;
    long previousCount = goldCount - actualDrawCount;
    long previousSmallCycles = previousCount / smallBaodiThreshold;
    long currentSmallCycles = goldCount / smallBaodiThreshold;
    long smallRewardCount = currentSmallCycles - previousSmallCycles;
    if (smallRewardCount > 0) {
        addItemToBag(qun, uin, jinbiSmallBaodi, (int) smallRewardCount);
        result += "*☆小保底奖励：" + jinbiSmallBaodi + "×" + smallRewardCount + "*\n";
    }

    // 大保底处理
    while (goldCount >= GOLD_BIG_BAODI) {
        addItemToBag(qun, uin, jinbibaodi, 10);
        result += "*★大保底奖励：" + jinbibaodi + "×10*\n";
        goldCount -= GOLD_BIG_BAODI;
    }
    putString(countKey, "金币次数", String.valueOf(goldCount));

    // 8. 发送结果
    toImg(玩家名(qun, uin) + " (" + uin + ")\n" + result, "", 0.5, 0.01, 30, AppPath + "/缓存/其他/" + uin + "_金币夺宝_" + actualDrawCount + ".png", false);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_金币夺宝_" + actualDrawCount + ".png]", false);
}


// 辅助功能（概率抽取+信息展示+双倍概率开关）
// 按概率随机获取道具（支持双倍概率活动）
private String[] getRandomItem(String[][] pool) {
    Random random = new Random();
    int totalProbability = 0;
    boolean isDoubleRate = getBoolean("系统配置","夺宝双倍概率", false);
    
    // 计算总概率（若开启双倍概率则翻倍）
    for (String[] item : pool) {
        int prob = Integer.parseInt(item[2]);
        totalProbability += isDoubleRate ? prob * 2 : prob;
    }
    
    // 生成随机数
    int randomValue = random.nextInt(totalProbability) + 1;
    int currentProb = 0;
    
    // 匹配概率区间
    for (String[] item : pool) {
        int prob = Integer.parseInt(item[2]);
        currentProb += isDoubleRate ? prob * 2 : prob;
        if (randomValue <= currentProb) {
            return item;
        }
    }
    
    // 兜底返回第一个道具（理论上不会触发）
    return pool[0];
}

// 双倍概率活动开关指令（Owner中成员可操作）
void setDoubleRate(String qun, String uin, String command) {
    if (!Arrays.asList(Owner).contains(uin)) {
        sendMsg(qun, "", "[AtQQ=" + uin + "] 你的权限不足，仅GM（管理员）可开关双倍概率");
        return;
    }
    
    // 解析指令（支持"开启双倍概率"或"关闭双倍概率"）
    if (command.contains("开启")) {
        putBoolean("系统配置","夺宝双倍概率", true); // 更新状态
        sendMsg(qun, "", "【系统通知】\n双倍概率活动已开启！所有夺宝道具概率翻倍~");
    } else if (command.contains("关闭")) {
        putBoolean("系统配置","夺宝双倍概率", false); // 更新状态
        sendMsg(qun, "", "【系统通知】\n双倍概率活动已关闭，恢复正常概率");
    } else {
        sendMsg(qun, "", "[AtQQ=" + uin + "] 指令格式错误！\n请使用：开启双倍概率 / 关闭双倍概率");
    }
}

// 显示积分宝池信息（含稀有度标注）
void showPointPool(String qun, String uin) {
   boolean isDoubleRate = getBoolean("系统配置","夺宝双倍概率", false);
    String msg = "<分割>【积分宝池】</分割>\n";
    for (String[] item : pointTreasurePool) {
        int prob = Integer.parseInt(item[2]);
        msg += "- " + getRarity(prob) + " " + item[0] + " " + item[1] + "（" + item[2] + "%）" 
            + (isDoubleRate ? " [当前双倍概率]" : "") + "\n";
    }
    msg += "<填充>\n-🎁小保底（"+POINT_SMALL_BAODI+"次）：" + jifenSmallBaodi + "×1\n";
    msg += "-🎁大保底（"+POINT_BIG_BAODI+"次）：" + jifenbaodi + "×1\n";
    msg += "// ※十连必出1个稀有道具（概率≤10%）";
    
    // 生成图片并发送
    toImg(msg, "", 0.5, 0.01, 30, AppPath+"/缓存/其他/积分宝池.png",false);
     发送(qun, "[PicUrl="+AppPath+"/缓存/其他/积分宝池.png]",false);
}

// 显示金币宝池信息（含稀有度标注）
void showGoldPool(String qun, String uin) {
   boolean isDoubleRate = getBoolean("系统配置","夺宝双倍概率", false);
    String msg = "<分割>【金币宝池】</分割>\n";
    for (String[] item : goldTreasurePool) {
        int prob = Integer.parseInt(item[2]);
        msg += "- " + getRarity(prob) + " " + item[0] + " " + item[1] + "（" + item[2] + "%）" 
            + (isDoubleRate ? " [当前双倍概率]" : "") + "\n";
    }
    msg += "<填充>\n-🎁小保底（"+GOLD_SMALL_BAODI+"次）：" + jinbiSmallBaodi + "×1\n";
    msg += "-🎁大保底（"+GOLD_BIG_BAODI+"次）：" + jinbibaodi + "×10\n";
    msg += "// ※十连必出1个稀有道具（概率≤10%）";
        
    // 生成图片并发送
    toImg(msg, "", 0.5, 0.01, 30, AppPath+"/缓存/其他/金币宝池.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/金币宝池.png]",false);
}

// 显示我的夺宝信息（含保底进度）
void showMyTreasureInfo(String qun, String uin) {
   boolean isDoubleRate = getBoolean("系统配置","夺宝双倍概率", false);
    String countKey = qun + "/" + uin + "/夺宝计数";
    long pointCount, goldCount;
    try {
        pointCount = Long.parseLong(getString(countKey, "积分次数", "0"));
        goldCount = Long.parseLong(getString(countKey, "金币次数", "0"));
    } catch (Exception e) {
        pointCount = 0;
        goldCount = 0;
    }
    
    // 计算剩余保底次数
    long pointSmallLeft = POINT_SMALL_BAODI - (pointCount % POINT_SMALL_BAODI);
    long pointBigLeft = POINT_BIG_BAODI - (pointCount % POINT_BIG_BAODI);
    long goldSmallLeft = GOLD_SMALL_BAODI - (goldCount % GOLD_SMALL_BAODI);
    long goldBigLeft = GOLD_BIG_BAODI - (goldCount % GOLD_BIG_BAODI);
    
    String msg = 玩家名(qun,uin)+" ("+uin+")\n\n<分割>【我的夺宝】</分割>\n";
    msg += "积分夺宝：\n";
    msg += "- 夺宝次数：" + pointCount + "\n";
    msg += "- 距离小保底：" + pointSmallLeft + "次\n";
    msg += "- 距离大保底：" + pointBigLeft + "次\n<填充>\n";
    msg += "金币夺宝：\n";
    msg += "- 夺宝次数：" + goldCount + "\n";
    msg += "- 距离小保底：" + goldSmallLeft + "次\n";
    msg += "- 距离大保底：" + goldBigLeft + "次\n";
    
    // 提示活动状态
    if (isDoubleRate) {
        msg += "<填充>\n※当前双倍概率活动进行中！所有道具概率翻倍";
    }
        
    // 生成图片并发送
    toImg(msg, "", 0.5, 0.01, 30, AppPath+"/缓存/其他/"+uin+"_我的夺宝.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+uin+"_我的夺宝.png]",false);
}
