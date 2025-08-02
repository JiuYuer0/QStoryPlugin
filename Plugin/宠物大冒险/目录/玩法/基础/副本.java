import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;
import java.io.File;

// 定义副本类
class Dungeon {
    private String monsterName;           // 怪物名称
    private long monsterPower;            // 怪物战力
    private long scoreReward;             // 积分奖励
    private long experienceReward;        // 经验奖励
    private String dropItems;             // 掉落物品
    private int levelLimit;               // 等级限制
    
    // 构造方法添加等级限制参数
    public Dungeon(String monsterName, long monsterPower, long scoreReward, long experienceReward, String dropItems, int levelLimit) {
        this.monsterName = monsterName;
        this.monsterPower = monsterPower;
        this.scoreReward = scoreReward;
        this.experienceReward = experienceReward;
        this.dropItems = dropItems;
        this.levelLimit = levelLimit;     // 初始化等级限制
    }
    
    // 获取副本相关信息的方法
    public String getMonsterName() { return monsterName; }
    public long getMonsterPower() { return monsterPower; }
    public long getScoreReward() { return scoreReward; }
    public long getExperienceReward() { return experienceReward; }
    public String getDropItems() { return dropItems; }
    public int getLevelLimit() { return levelLimit; }
   
}

// 创建Map存储副本数据
Map dungeonMap = new HashMap();

// 基础副本（宠物副本1页）
dungeonMap.put("原始森林", new Dungeon("森林巨熊", 50000L, 500L, 800L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 10));  // 等级限制10
dungeonMap.put("元素迷阵", new Dungeon("元素使者", 80000L, 1000L, 1500L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 30));  // 等级限制30
dungeonMap.put("勇者遗迹", new Dungeon("遗迹守卫", 120000L, 2500L, 4500L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 50));  // 等级限制50
dungeonMap.put("神秘幻域", new Dungeon("幻域行者", 150000L, 4000L, 7500L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 80));  // 等级限制80
dungeonMap.put("星辰秘殿", new Dungeon("星灵守卫", 200000L, 5000L, 9000L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 100));  // 等级限制100
dungeonMap.put("混沌回廊", new Dungeon("混沌巨兽", 250000L, 6000L, 11500L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 120));  // 等级限制120
dungeonMap.put("暗影迷城", new Dungeon("暗影刺客", 300000L, 7500L, 15000L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 150));  // 等级限制150
dungeonMap.put("时光裂缝", new Dungeon("时光守卫", 350000L, 9000L, 17500L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 180));  // 等级限制180
dungeonMap.put("梦境深渊", new Dungeon("梦魇领主", 400000L, 10000L, 25000L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 200));  // 等级限制200
dungeonMap.put("灵能禁地", new Dungeon("灵能核心", 450000L, 30000L, 50000L, "小破锋丹、小长生丹、小御体丹、复活石、五千积分卡、五千经验卡", 250));  // 等级限制250
dungeonMap.put("风暴峡谷", new Dungeon("风暴巨龙", 500000L, 32500L, 108000L, "破锋丹、长生丹、御体丹、复活石、一万积分卡、一万经验卡", 300));  // 等级限制300
dungeonMap.put("永恒回廊", new Dungeon("永恒守护者", 600000L, 33000L, 125000L, "破锋丹、长生丹、御体丹、复活石、十万积分卡、十万经验卡", 400));  // 等级限制400
dungeonMap.put("虚空领域", new Dungeon("虚空行者", 700000L, 35000L, 130000L, "破锋丹、长生丹、御体丹、复活石、十万积分卡、十万经验卡", 500));  // 等级限制500
dungeonMap.put("命运之巅", new Dungeon("命运裁决者", 800000L, 38500L, 150000L, "破锋丹、长生丹、御体丹、复活石、十万积分卡、十万经验卡", 600));  // 等级限制600
dungeonMap.put("起源之地", new Dungeon("起源巨人", 900000L, 41000L, 175000L, "破锋丹、长生丹、御体丹、复活石、十万积分卡、十万经验卡", 700));  // 等级限制700
dungeonMap.put("秩序神坛", new Dungeon("秩序天使", 1000000L, 45000L, 205000L, "破锋丹、长生丹、御体丹、复活石、百万积分卡、百万经验卡", 800));  // 等级限制800
dungeonMap.put("幻世奇境", new Dungeon("幻世之主", 1200000L, 50000L, 250000L, "破锋丹、长生丹、御体丹、复活石、百万积分卡、百万经验卡", 900));  // 等级限制900
dungeonMap.put("破晓圣境", new Dungeon("破晓战神", 1500000L, 60000L, 300000L, "破锋丹、长生丹、御体丹、复活石、百万积分卡、百万经验卡", 1000));  // 等级限制1000
dungeonMap.put("超凡之墟", new Dungeon("超凡尊者", 1800000L, 65000L, 310000L, "破锋丹、长生丹、御体丹、复活石、千万积分卡、千万经验卡", 1150));  // 等级限制1150
dungeonMap.put("苍穹之巅", new Dungeon("苍穹大帝", 2000000L, 80000L, 450000L, "破锋丹、长生丹、御体丹、复活石、经验丹、一亿积分卡、一亿经验卡", 1300));  // 等级限制1300
dungeonMap.put("创世回廊", new Dungeon("创世神", 2500000L, 100000L, 600000L, "破锋丹、长生丹、御体丹、复活石、经验丹、一亿积分卡、一亿经验卡", 1500));  // 等级限制1500

// 神器材料副本（宠物副本2页）
dungeonMap.put("青冥古洞", new Dungeon("青冥剑灵·残", 100000L, 5000L, 9000L, "青冥玄铁、天青藤、剑灵残魂、复活石", 30));  // 等级限制30
dungeonMap.put("灵蕴仙谷", new Dungeon("灵韵鸡", 100000L, 5000L, 9000L, "聚灵玉髓、日月精华石、灵木心、复活石", 30));  // 等级限制30
dungeonMap.put("九幽魔域", new Dungeon("魔焰狱主", 100000L, 5000L, 9000L, "镇魔符箓、九幽魔藤、魔王精血、复活石", 30));  // 等级限制30
dungeonMap.put("星辉遗迹", new Dungeon("谜星守护者", 100000L, 5000L, 9000L, "陨星碎片、星辉石、星辰沙、复活石", 30));  // 等级限制30
dungeonMap.put("焚天火山", new Dungeon("炎魔", 100000L, 5000L, 9000L, "焚天炎晶、赤阳火精、炎魔之心、复活石", 30));  // 等级限制30
dungeonMap.put("玄冰寒渊", new Dungeon("冰魄寒龙", 100000L, 5000L, 9000L, "玄冰髓、极地寒晶、冰魄之心、复活石", 30));  // 等级限制30
dungeonMap.put("龙渊秘窟", new Dungeon("深渊龙影", 100000L, 5000L, 9000L, "龙珠、复活石", 30));  // 等级限制30
dungeonMap.put("玄铁矿脉", new Dungeon("熔炉魔像", 100000L, 5000L, 9000L, "玄铁、复活石", 30));  // 等级限制30
dungeonMap.put("星陨废土", new Dungeon("星辉巡守", 100000L, 5000L, 9000L, "星核、复活石", 30));  // 等级限制30
dungeonMap.put("灵藤迷谷", new Dungeon("古树老妖", 100000L, 5000L, 9000L, "灵藤、复活石", 30));  // 等级限制30

// 经验/积分副本（宠物副本3页）
dungeonMap.put("地级经验", new Dungeon("经验侍从", 100000L, 32500L, 108000L, "一亿经验卡、千万经验卡、百万经验卡", 150));  // 等级限制150
dungeonMap.put("天级经验", new Dungeon("经验使者", 300000L, 38500L, 150000L, "十亿经验卡、一亿经验卡、千万经验卡", 300));  // 等级限制300
dungeonMap.put("帝级经验", new Dungeon("经验之神", 500000L, 45000L, 205000L, "百亿经验卡、经验丹、一亿经验卡", 500));  // 等级限制500
dungeonMap.put("仙级经验", new Dungeon("经验天尊", 1000000L, 100000L, 850000L, "百亿经验卡、十亿经验卡、经验丹、一亿经验卡", 1000));  // 等级限制1000
dungeonMap.put("地级积分", new Dungeon("积分侍从", 100000L, 32500L, 108000L, "一亿积分卡、千万积分卡、百万积分卡", 150));  // 等级限制150
dungeonMap.put("天级积分", new Dungeon("积分使者", 300000L, 38500L, 150000L, "十亿积分卡、一亿积分卡、千万积分卡", 300));  // 等级限制300
dungeonMap.put("帝级积分", new Dungeon("积分之神", 500000L, 45000L, 205000L, "百亿经验卡、十亿积分卡、一亿积分卡", 500));  // 等级限制500
dungeonMap.put("仙级积分", new Dungeon("积分天尊", 1000000L, 100000L, 850000L, "百亿积分卡、十亿积分卡、一亿积分卡", 1000));  // 等级限制1000

// 限时副本，专门掉落道具的
dungeonMap.put("经验秘境", new Dungeon("授道仙人", 1000000L, 50000L, 75000L, "一亿经验卡、经验丹、十亿经验卡、百亿经验卡", 150));  // 等级限制150
dungeonMap.put("积分秘境", new Dungeon("商业巨亨", 1000000L, 75000L, 50000L, "一亿积分卡、十亿积分卡、百亿积分卡", 150));  // 等级限制150
dungeonMap.put("金币秘境", new Dungeon("海盗奇兵", 1000000L, 20000L, 20000L, "1金币、10金币、50金币", 150));  // 等级限制150

// 查看副本列表
public void 宠物副本(String qun, String uin, String msg) {
    String 页数上限 = "3";
    if (msg.equals("宠物副本") || msg.equals("宠物副本1")) {
        String[] 预览 = {"原始森林", "元素迷阵", "勇者遗迹", "神秘幻域", "星辰秘殿", "混沌回廊", "暗影堡垒", "时光裂缝", "梦境深渊", "灵能禁地", "风暴峡谷", "永恒回廊", "虚空领域", "命运之巅", "起源之地", "秩序神坛", "幻世奇境", "破晓圣境", "超凡之墟", "苍穹之巅", "创世回廊"};
                Random random = new Random();
        int randomIndex = random.nextInt(预览.length);
        String 随机副本 = 预览[randomIndex];
        String 前文 = "当前开放副本如下：\n<分割>基础副本</分割>\n";
        String 后文 = "\n ▪翻页：宠物副本+页数\n ▪查看：副本查看+副本名称\n ▪挑战：*进入副本+副本名#次数*\n ▪示例：进入副本" + 随机副本 + "#" + 随机数(10, 9999) + "\nTime：" + getTodayDate(2);
        String 副本 = 前文 + "●：原始森林  Lv ≥ 10\n";
        副本 += "●：元素迷阵  Lv ≥ 30\n";
        副本 += "●：勇者遗迹  Lv ≥ 50\n";
        副本 += "●：神秘幻域  Lv ≥ 80\n";
        副本 += "●：星辰秘殿  Lv ≥ 100\n";
        副本 += "●：混沌回廊  Lv ≥ 120\n";
        副本 += "●：暗影迷城  Lv ≥ 150\n";
        副本 += "●：时光裂缝  Lv ≥ 180\n";
        副本 += "●：梦境深渊  Lv ≥ 200\n";
        副本 += "●：灵能禁地  Lv ≥ 250\n";
        副本 += "●：风暴峡谷  Lv ≥ 300\n";
        副本 += "●：永恒回廊  Lv ≥ 400\n";
        副本 += "●：虚空领域  Lv ≥ 500\n";
        副本 += "●：命运之巅  Lv ≥ 600\n";
        副本 += "●：起源之地  Lv ≥ 700\n";
        副本 += "●：秩序神坛  Lv ≥ 800\n";
        副本 += "●：幻世奇境  Lv ≥ 900\n";
        副本 += "●：破晓圣境  Lv ≥ 1000\n";
        副本 += "●：超凡之墟  Lv ≥ 1150\n";
        副本 += "●：苍穹之巅  Lv ≥ 1300\n";
        副本 += "●：创世回廊  Lv ≥ 1500\n<分割>•页数：1 / " + 页数上限 + " •</分割>" + 后文;
        toImg(副本, "", 0.5, 0.01, 25, AppPath + "/缓存/其他/宠物副本1.png", false);
        发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物副本1.png]", false);
    } else if (msg.length() >= 4) {
        String 去掉前缀 = msg.replace("宠物副本", "");
        if (去掉前缀.matches("\\d+")) {
            int 数字 = Integer.parseInt(去掉前缀);
            if (数字 == 2) {
                String[] 预览 = {"青冥古洞", "灵蕴仙谷", "九幽魔域", "星辉遗迹", "焚天火山", "玄冰寒渊", "龙渊秘窟", "玄铁矿脉", "星陨废土", "灵藤迷谷"};
                Random random = new Random();
                int randomIndex = random.nextInt(预览.length);
                String 随机副本 = 预览[randomIndex];
                String 前文 = "当前开放副本如下：\n<分割>神器材料副本</分割>\n";
                String 后文 = "\n ▪翻页：宠物副本+页数\n ▪查看：副本查看+副本名称\n ▪挑战：*进入副本+副本名#次数*\n ▪示例：进入副本" + 随机副本 + "#" + 随机数(10, 9999) + "\nTime：" + getTodayDate(2);
                String 副本 = 前文 + "●：青冥古洞  Lv ≥ 30\n";
                副本 += "●：灵蕴仙谷  Lv ≥ 30\n";
                副本 += "●：九幽魔域  Lv ≥ 30\n";
                副本 += "●：星辉遗迹  Lv ≥ 30\n";
                副本 += "●：焚天火山  Lv ≥ 30\n";
                副本 += "●：玄冰寒渊  Lv ≥ 30\n";
                副本 += "●：龙渊秘窟  Lv ≥ 30\n";
                副本 += "●：玄铁矿脉  Lv ≥ 30\n";
                副本 += "●：星陨废土  Lv ≥ 30\n";
                副本 += "●：灵藤迷谷  Lv ≥ 30\n<分割>•页数：" + 数字 + " / " + 页数上限 + " •</分割>" + 后文;
                toImg(副本, "", 0.5, 0.01, 25, AppPath + "/缓存/其他/宠物副本" + 数字 + ".png", true);
                发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物副本" + 数字 + ".png]", false);
            } else if (数字 == 3) {
                String[] 预览 = {"地级积分", "地级经验", "天级积分", "天级经验", "帝级经验", "帝级积分", "仙级经验", "仙级积分"};
                Random random = new Random();
                int randomIndex = random.nextInt(预览.length);
                String 随机副本 = 预览[randomIndex];
                String 前文 = "当前开放副本如下：\n<分割>经验副本</分割>\n";
                String 后文 = "\n ▪翻页：宠物副本+页数\n ▪查看：副本查看+副本名称\n ▪挑战：*进入副本+副本名#次数*\n ▪示例：进入副本" + 随机副本 + "#" + 随机数(10, 9999) + "\nTime：" + getTodayDate(2);
                String 副本 = 前文 + "●：地级经验  Lv ≥ 150\n";
                副本 += "●：天级经验  Lv ≥ 300\n";
                副本 += "●：帝级经验  Lv ≥ 500\n";
                副本 += "●：仙级经验  Lv ≥ 1000\n<分割>积分副本</分割>\n";
                副本 += "●：地级积分  Lv ≥ 150\n";
                副本 += "●：天级积分  Lv ≥ 300\n";
                副本 += "●：帝级积分  Lv ≥ 500\n";
                副本 += "●：仙级积分  Lv ≥ 1000\n<分割>•页数：" + 数字 + " / " + 页数上限 + " •</分割>" + 后文;
                toImg(副本, "", 0.5, 0.01, 25, AppPath + "/缓存/其他/宠物副本" + 数字 + ".png", true);
                发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物副本" + 数字 + ".png]", false);
            } else {
                String[] 预览 = {"原始森林", "元素迷阵", "勇者遗迹", "神秘幻域", "星辰秘殿", "混沌回廊", "暗影堡垒", "时光裂缝", "梦境深渊", "灵能禁地", "风暴峡谷", "永恒回廊", "虚空领域", "命运之巅", "起源之地", "秩序神坛", "幻世奇境", "破晓圣境", "超凡之墟", "苍穹之巅", "创世回廊"};
                Random random = new Random();
                int randomIndex = random.nextInt(预览.length);
                String 随机副本 = 预览[randomIndex];
                String 前文 = "当前开放副本如下：\n<分割>基础副本</分割>\n";
                String 后文 = "\n ▪翻页：宠物副本+页数\n ▪查看：副本查看+副本名称\n ▪挑战：*进入副本+副本名#次数*\n ▪示例：进入副本" + 随机副本 + "#" + 随机数(10, 9999) + "\nTime：" + getTodayDate(2);
                String 副本 = 前文 + "●：原始森林  Lv ≥ 10\n";
                副本 += "●：元素迷阵  Lv ≥ 30\n";
                副本 += "●：勇者遗迹  Lv ≥ 50\n";
                副本 += "●：神秘幻域  Lv ≥ 80\n";
                副本 += "●：星辰秘殿  Lv ≥ 100\n";
                副本 += "●：混沌回廊  Lv ≥ 120\n";
                副本 += "●：暗影迷城  Lv ≥ 150\n";
                副本 += "●：时光裂缝  Lv ≥ 180\n";
                副本 += "●：梦境深渊  Lv ≥ 200\n";
                副本 += "●：灵能禁地  Lv ≥ 250\n";
                副本 += "●：风暴峡谷  Lv ≥ 300\n";
                副本 += "●：永恒回廊  Lv ≥ 400\n";
                副本 += "●：虚空领域  Lv ≥ 500\n";
                副本 += "●：命运之巅  Lv ≥ 600\n";
                副本 += "●：起源之地  Lv ≥ 700\n";
                副本 += "●：秩序神坛  Lv ≥ 800\n";
                副本 += "●：幻世奇境  Lv ≥ 900\n";
                副本 += "●：破晓圣境  Lv ≥ 1000\n";
                副本 += "●：超凡之墟  Lv ≥ 1150\n";
                副本 += "●：苍穹之巅  Lv ≥ 1300\n";
                副本 += "●：创世回廊  Lv ≥ 1500\n<分割>•页数：1 / " + 页数上限 + " •</分割>" + 后文;
                toImg(副本, "", 0.5, 0.01, 25, AppPath + "/缓存/其他/宠物副本1.png", true);
                发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物副本1.png]", false);
            }
        }
    }
}


// 限时副本开放/关闭时间
int openHour2 = 8; // 开放时间（08:00）
int closeHour2 = 22; // 关闭时间（22:00）

// 查看限时副本列表
public void 限时副本(String qun, String uin, String msg) {
    // 获取当前时间
    Calendar cal = Calendar.getInstance();
    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    int currentMinute = cal.get(Calendar.MINUTE);
    // 计算时差（分钟为单位）
    if (currentHour < openHour2) {
        // 未到开放时间，计算剩余时间（修正分钟进位）
        int totalMinutes = (openHour2 - currentHour) * 60 - currentMinute;
        int remainingHours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;
        发送(qun, "[AtQQ=" + uin + "]  " + " \n「限时副本」未开放！\n（开放时间：" + openHour2 + ":00-" + closeHour2 + ":00）\n预计在" + remainingHours + " 小时 " + remainingMinutes + " 分钟后开放", true);
        return;
    }
    // 已关闭，提示相关内容
    if (currentHour > closeHour2) {
        // 计算已关闭时间（修正分钟进位）
        int totalPassedMinutes = (currentHour - closeHour2) * 60 + currentMinute;
        int passedHours = totalPassedMinutes / 60;
        int passedMinutes = totalPassedMinutes % 60;

        // 计算到次日开放的时间（修正分钟进位）
        int totalTomorrowMinutes = (24 - currentHour + openHour2) * 60 - currentMinute;
        int tomorrowHours = totalTomorrowMinutes / 60;
        int tomorrowMinutes = totalTomorrowMinutes % 60;

        发送(qun, "[AtQQ=" + uin + "]  " + " \n「限时副本」已关闭！\n（开放时间：" + openHour2 + ":00-" + closeHour2 + ":00）\n已关闭 " + passedHours + " 小时 " + passedMinutes + " 分钟\n距离下次开放还剩 " + tomorrowHours + " 小时 " + tomorrowMinutes + " 分钟", true);
        return; // 提前停止，不进行后续逻辑
    }
    String[] 预览 = {"经验秘境", "金币秘境", "积分秘境"};
    Random random = new Random();
    int randomIndex = random.nextInt(预览.length);
    String 随机副本 = 预览[randomIndex];
    String 前文 = "当前开放副本如下：\n<分割>限时副本</分割>\n";
    String 后文 = "▪查看：副本查看+副本名称\n ▪挑战：*挑战+副本名#次数*\n ▪示例：挑战" + 随机副本 + "#" + 随机数(10, 9999) + "\nTime：" + getTodayDate(2);
    String 副本 = 前文 + "●：经验秘境  Lv ≥ 1\n●：金币秘境  Lv ≥ 1\n●：积分秘境  Lv ≥ 1\n<填充>\n" + 后文;
    toImg(副本, "", 0.5, 0.01, 35, AppPath + "/缓存/其他/限时副本.png", true);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/限时副本.png]", false);
}

// 挑战限时副本
public void 挑战副本(String qun, String uin, String msg) {
    String 配置名 = qun + "/" + uin + "/";
    long 积分 = 文转(getString(配置名 + "/我的资产", "积分"));
    long 金币 = 文转(getString(配置名 + "/我的资产", "金币"));
    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
    String 宠物名 = getString(配置名称, "昵称");
    
    // 获取当前时间
    Calendar cal = Calendar.getInstance();
    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    int currentMinute = cal.get(Calendar.MINUTE);
    // 检查副本开放状态
    if (currentHour < openHour2) {
        int totalMinutes = (openHour2 - currentHour) * 60 - currentMinute;
        int remainingHours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;
        发送(qun, "[AtQQ=" + uin + "]  \n「限时副本」未开放！\n（开放时间：" + openHour2 + ":00-" + closeHour2 + ":00）\n预计在" + remainingHours + " 小时 " + remainingMinutes + " 分钟后开放", true);
        return;
    }
    if (currentHour > closeHour2) {
        int totalPassedMinutes = (currentHour - closeHour2) * 60 + currentMinute;
        int passedHours = totalPassedMinutes / 60;
        int passedMinutes = totalPassedMinutes % 60;
        int totalTomorrowMinutes = (24 - currentHour + openHour2) * 60 - currentMinute;
        int tomorrowHours = totalTomorrowMinutes / 60;
        int tomorrowMinutes = totalTomorrowMinutes % 60;
        发送(qun, "[AtQQ=" + uin + "]  \n「限时副本」已关闭！\n（开放时间：" + openHour2 + ":00-" + closeHour2 + ":00）\n已关闭 " + passedHours + " 小时 " + passedMinutes + " 分钟\n距离下次开放还剩 " + tomorrowHours + " 小时 " + tomorrowMinutes + " 分钟", true);
        return;
    }
    
    // 解析指令格式
    String 预定 = msg.substring(2);
    String 选定, 次数;
    if (预定 == null || 预定.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "]  \n指令格式错误！\n\n◆指令：挑战+副本名#次数", true);
        return;
    }
    if (msg.contains("#")) {
        int hashIndex = msg.indexOf('#');
        选定 = msg.substring(2, hashIndex);
        String[] parts = msg.split("#");
        次数 = parts[1];
    } else if (msg.length() >= 3) {
        选定 = msg.substring(2);
        次数 = "1";
    } else {
        选定 = null;
        次数 = "1";
    }
    if (选定 == null) {
        发送(qun, "[AtQQ=" + uin + "]  \n疑似指令出错！\n◆指令：挑战+副本名#次数", true);
        return;
    }
    
    // 检查宠物状态
    long 等级 = 文转(getString(配置名称, "等级"));
    long 心情 = 文转(getString(配置名称, "心情"));
    String 状态 = getString(配置名称, "状态");
    String 闭关 = getString(配置名称, "闭关");
    if (状态 == null || 等级 <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + " 您当前还没有宠物，赶紧邂逅您的宠物!\n▫︎指令：砸蛋十连", true);
        return;
    }
    if (状态.equals("死亡")) {
        发送(qun, "[AtQQ=" + uin + "]  " + " 您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石", true);
        return;
    }
    if (闭关 != null &&闭关.equals("闭关")) {
        发送(qun, "[AtQQ=" + uin + "]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!", true);
        return;
    }
    if (心情 <= 15) {
        发送(qun, "[AtQQ=" + uin + "]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2", true);
        return;
    }
    
    // 检查副本合法性
    Dungeon 副本 = (Dungeon) dungeonMap.get(选定);
    if (副本 == null) {
        发送(qun, "[AtQQ=" + uin + "]  \n限时副本内没有名叫「" + 选定 + "」的副本", true);
        return;
    }
    int 副本等级限制 = 副本.getLevelLimit();
    if (等级 < 副本等级限制) {
        发送(qun, "[AtQQ=" + uin + "]  \n宠物等级不足！副本「" + 选定 + "」需要Lv ≥ " + 副本等级限制, true);
        return;
    }
    String[] 预览 = {"经验秘境", "金币秘境", "积分秘境"};
    boolean contains = false;
    for (int i = 0; i < 预览.length; i++) {
        if (预览[i].equals(选定)) {
            contains = true;
            break;
        }
    }
    if (!contains) {
        发送(qun, "[AtQQ=" + uin + "]  \n限时副本内没有名叫「" + 选定 + "」的副本", true);
        return;
    }
    
    // 计算挑战参数
    long 数量 = 文转(次数);
    String 昵称 = getString(配置名称, "昵称");
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 当前经验 = 文转(getString(配置名称, "当前经验"));
    long 耗费 = 乘(50, 数量); //单次精力消耗50＊进入次数=总精力消耗
    if (耗费 > 当前精力) {
        发送(qun, "[AtQQ=" + uin + "]  \n你的宠物当前精力不足[" + 耗费 + "]！", true);
        return;
    }
    if (数量 > 9999999) {
        发送(qun, "[AtQQ=" + uin + "]  \n挑战次数超出限制范围！", true);
        return;
    }
    
    // 计算奖励与掉落
    String 怪物名称 = 副本.getMonsterName();
    long 怪物战力 = 副本.getMonsterPower();
    long 积分奖励 = 副本.getScoreReward();
    long 经验奖励 = 副本.getExperienceReward();
    String 掉落物品 = 副本.getDropItems();
    long 经验变化 = 乘(经验奖励, 数量);
    long 积分变化 = 乘(积分奖励, 数量);
    //计算循环次数
    long loopCount = 数量 / 100;
    if (loopCount == 0) {
        loopCount = 1;
    }
    String[] items = 掉落物品.split("、");
    Random random = new Random();
    Map itemCountMap = new HashMap();
    
    // 根据循环次数来决定随机添加多少次道具
    int loop = (int) loopCount;
    for (int i = 0; i < loop; i++) {
        int randomIndex = random.nextInt(items.length);
        String selectedItem = items[randomIndex];
        int itemCount = random.nextInt(2) + 1;
        Integer count = (Integer) itemCountMap.get(selectedItem);
        if (count == null) {
            itemCountMap.put(selectedItem, itemCount);
        } else {
            itemCountMap.put(selectedItem, count + itemCount);
        }
    }
    
    // 拼接掉落结果
    StringBuffer result = new StringBuffer();
    Object[] keys = itemCountMap.keySet().toArray();
    for (int i = 0; i < keys.length; i++) {
        if (result.length() > 0) {
            result.append("、");
        }
        result.append(keys[i]).append("×").append(itemCountMap.get(keys[i]));
    }
    String finalResult = formatString(result.toString());
    // 去掉 finalResult 末尾的<起>
    if (finalResult.endsWith("<起>")) {
      finalResult = finalResult.substring(0, finalResult.length() - 3);
    }
    
    // 更新背包
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    for (int i = 0; i < keys.length; i++) {
        String itemName = (String) keys[i];
        // 从Object转为Integer
        int count = ((Integer) itemCountMap.get(itemName)).intValue();
        if (bag != null && bag.contains(itemName)) {
            long existCount = 文转(getString(bagKey, itemName));
            putString(bagKey, itemName, 转文(existCount + count));
        } else {
            String newBag = (bag == null ? "" : bag + "、") + itemName;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, itemName, 转文(count));
        }
    }
    
    // 处理战斗结果
    String 副本图 = AppPath + "/目录/图片/副本/" + 选定 + ".jpg";
    String 图路径;
    File file = new File(副本图);
    if (file.exists()) {
        图路径 = "[PicUrl=" + 副本图 + "]";
    } else {
        图路径 = "";
    }
    long 血量扣除 = 怪物战力 * 数量;
    long 生命变化 = 减(当前生命,血量扣除);
    String 状态变化 = "受伤";
    if (生命变化 == 0) {
        状态变化 = "死亡";
        putString(配置名称, "状态", "死亡");
        putString(配置名称, "心情", 转文(减(心情, 10)));
    }
    
    // 更新状态并发送结果
    String 文案 = "<分割>〖" + 昵称 + "  VS  " + 怪物名称 + "〗</分割>\n ▪︎战斗结果：胜利<起> ▫︎消耗精力：" + 数值转(耗费) + "<尾>\n ▪︎获得经验：" + 数值转(经验变化) + "<起> ▫︎血量减少：" + 数值转(血量扣除) + "<尾>\n ▪︎获得积分：" + 数值转(积分变化) + "<起> ▫︎战后状态：" + 状态变化 + "<尾>\n<分割>获得道具</分割>\n" + finalResult;
    putString(配置名称, "当前精力", 转文(当前精力 - 耗费));
    putString(配置名称, "当前生命", 转文(生命变化));
    putString(配置名称, "当前经验", 转文(当前经验 + 经验变化));
    putString(配置名 + "/我的资产", "积分", 转文(积分 + 积分变化));
    toImg(文案, "", 0.5, 0.02, 50, AppPath + "/缓存/其他/" + uin + "_挑战副本.png", false);
    发送(qun, 图路径 + "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_挑战副本.png]", false);
}

/*
以下是获取副本对应信息示例：↓
Dungeon Dgn = (Dungeon) dungeonMap.get("青冥古洞");
// 获取怪物名称
String 怪物名称 = Dgn.getMonsterName();
// 获取怪物战力
long 怪物战力 = Dgn.getMonsterPower();
// 获取积分奖励
long 积分奖励 = Dgn.getScoreReward();
// 获取经验奖励
long 经验奖励 = Dgn.getExperienceReward();
// 获取掉落物品
String 掉落物品 = Dgn.getDropItems();
*/

// 查看副本信息
public void 副本查看(String qun, String uin, String msg) {
    String 副本 = msg.substring(4);
    if (副本 == null || "".equals(副本.trim())) { // 为null或空字符串时
        // 随机获取副本名称
        String[] textMsg = {"青冥古洞", "焚天火山", "灵蕴仙谷", "九幽魔域", "星辉遗迹", "玄冰寒渊", "龙渊秘窟", "玄铁矿脉", "星陨废土", "灵藤迷宫"};
        Random random = new Random();
        int index = random.nextInt(textMsg.length); // 生成随机索引
        String randomContent = textMsg[index]; // 随机副本名称
        发送(qun, "[AtQQ=" + uin + "]  疑似格式错误！\n•格式：副本查看+副本名称\n•示例：副本查看" + randomContent, true);
    } else if (!dungeonMap.containsKey(副本)) {
        发送(qun, "[AtQQ=" + uin + "]  \n ●不存在名为「" + 副本 + "」的副本！\n●可发送【宠物副本】查看副本", true);
    } else {
        Dungeon Dgn = (Dungeon) dungeonMap.get(副本);
        // 获取该副本的各项信息
        String 怪物名称 = Dgn.getMonsterName();
        long 怪物战力 = Dgn.getMonsterPower();
        long 积分奖励 = Dgn.getScoreReward();
        long 经验奖励 = Dgn.getExperienceReward();
        String 掉落物品 = Dgn.getDropItems();
        int 等级限制 = Dgn.getLevelLimit(); // 获取等级限制
        String 拼接内容 = "【副本名称】：" + 副本 + "\n【副本首领】：" + 怪物名称 + "（" + 数值转(怪物战力) + "战力）\n【等级要求】：Lv ≥ " + 等级限制 + "\n【积分奖励】：" + 数值转(积分奖励) + "\n【经验奖励】：" + 数值转(经验奖励) + "\n【掉落物品】：" + 掉落物品;
        // 根据该副本是否存在副本图来拼接内容
        String 副本图 = AppPath + "/目录/图片/副本/" + 副本 + ".jpg";
        File file = new File(副本图);
        if (file.exists()) {
            拼接内容 = "[AtQQ=" + uin + "]\n[PicUrl=" + 副本图 + "]" + 拼接内容;
        } else {
            拼接内容 = "[AtQQ=" + uin + "]  \n" + 拼接内容;
        }
        发送(qun, 拼接内容, false); // 发送副本信息
    }
}

// 进入非限时副本的方法
public void 进入副本(String qun, String uin, String msg) {
    String 配置名 = qun + "/" + uin + "/";
    long 积分 = 文转(getString(配置名 + "/我的资产", "积分"));
    long 金币 = 文转(getString(配置名 + "/我的资产", "金币"));
    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
    String 宠物名 = getString(配置名称, "昵称");
    
    // 解析指令格式：进入副本+副本名#次数
    String 预定 = msg.substring(4); // 截取"进入副本"后的内容
    String 选定, 次数;
    if (预定 == null || 预定.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "]  \n指令格式错误！\n\n◆指令：进入副本+副本名#次数", true);
        return;
    }
    
    if (msg.contains("#")) {
        int hashIndex = msg.indexOf('#');
        选定 = msg.substring(4, hashIndex); // 提取副本名
        String[] parts = msg.split("#");
        次数 = parts[1];
    } else {
        选定 = msg.substring(4); // 无次数时默认1次
        次数 = "1";
    }
    
    // 校验副本是否存在且为非限时副本
    if (!dungeonMap.containsKey(选定)) {
        发送(qun, "[AtQQ=" + uin + "]  \n不存在名为「" + 选定 + "」的副本！\n◆可发送【宠物副本】查看所有副本", true);
        return;
    }
    
    // 排除限时副本（限时副本名固定为经验秘境、金币秘境、积分秘境）
    String[] 限时副本列表 = {"经验秘境", "金币秘境", "积分秘境"};
    boolean is限时 = false;
    for (int i = 0; i < 限时副本列表.length; i++) {
        if (限时副本列表[i].equals(选定)) {
            is限时 = true;
            break;
        }
    }
    if (is限时) {
        发送(qun, "[AtQQ=" + uin + "]  \n「" + 选定 + "」为限时副本，请使用【挑战+副本名#次数】指令进入", true);
        return;
    }
    
    // 校验宠物状态
    long 等级 = 文转(getString(配置名称, "等级"));
    long 心情 = 文转(getString(配置名称, "心情"));
    String 状态 = getString(配置名称, "状态");
    String 闭关 = getString(配置名称, "闭关");
    if (状态 == null || 等级 <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + " 您当前还没有宠物，无法进入副本！\n▫︎指令：砸蛋十连获取宠物", true);
        return;
    }
    if (状态.equals("死亡")) {
        发送(qun, "[AtQQ=" + uin + "]  " + " 您的宠物已死亡，请先复活再进入副本！\n•例如：使用复活石", true);
        return;
    }
    if (闭关 != null &&闭关.equals("闭关")) {
        发送(qun, "[AtQQ=" + uin + "]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!", true);
        return;
    }
    if (心情 <= 15) {
        发送(qun, "[AtQQ=" + uin + "]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2", true);
        return;
    }
    
    // 校验等级是否满足副本要求
    Dungeon 副本 = (Dungeon) dungeonMap.get(选定);
    int 副本等级限制 = 副本.getLevelLimit();
    if (等级 < 副本等级限制) {
        发送(qun, "[AtQQ=" + uin + "]  \n宠物等级不足！副本「" + 选定 + "」需要Lv ≥ " + 副本等级限制, true);
        return;
    }
    
    // 校验挑战次数和精力
    long 数量 = 文转(次数);
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 当前经验 = 文转(getString(配置名称, "当前经验"));
    long 单次精力消耗 = 10; // 非限时副本单次消耗精力
    long 总消耗 = 乘(单次精力消耗, 数量);
    
    if (总消耗 > 当前精力) {
        发送(qun, "[AtQQ=" + uin + "]  \n宠物精力不足！需要" + 总消耗 + "点精力，当前仅剩" + 当前精力 + "点", true);
        return;
    }
    if (数量 > 99999) {
        发送(qun, "[AtQQ=" + uin + "]  \n单次进入副本次数上限为99999次，请减少次数", true);
        return;
    }
    
    // 执行副本挑战逻辑
    String 怪物名称 = 副本.getMonsterName();
    long 怪物战力 = 副本.getMonsterPower();
    long 积分奖励 = 副本.getScoreReward();
    long 经验奖励 = 副本.getExperienceReward();
    String 掉落物品 = 副本.getDropItems();
    
    // 计算总奖励
    long 总积分 = 乘(积分奖励, 数量);
    long 总经验 = 乘(经验奖励, 数量);
    
    // 计算循环次数（进入次数/10=道具随机获取次数）
    long loopCountLong = 数量 / 10;
    int loopCount = loopCountLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) loopCountLong;
    if (loopCount == 0) {
        loopCount = 1; // 保底获得一个道具
    }
    
    // 处理掉落物品（随机分配）
    String[] 物品列表 = 掉落物品.split("、");
    Random random = new Random();
    Map 获得物品 = new HashMap();
    for (int i = 0; i < loopCount; i++) {
        String 随机物品 = 物品列表[random.nextInt(物品列表.length)];
        // 从Map获取值时明确转换为Integer
        Integer count = (Integer) 获得物品.get(随机物品);
        if (count == null) {
            获得物品.put(随机物品, 1);
        } else {
            获得物品.put(随机物品, count + 1);
        }
    }
    
    // 更新背包物品
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    Object[] 物品数组 = 获得物品.keySet().toArray();
    for (int i = 0; i < 物品数组.length; i++) {
        String 物品名 = (String) 物品数组[i];
        // 从Object转为Integer，再转为long
        int 物品数量Int = (Integer) 获得物品.get(物品名);
        long 物品数量 = 物品数量Int;
        if (bag != null && bag.contains(物品名)) {
            long 现有数量 = 文转(getString(bagKey, 物品名));
            putString(bagKey, 物品名, 转文(现有数量 + 物品数量));
        } else {
            String newBag = (bag == null ? "" : bag + "、") + 物品名;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, 物品名, 转文(物品数量));
        }
    }
    
    // 计算生命值变化
    long 血量扣除 = 怪物战力 * 数量; // 计算即将扣除的生命值
    long 生命变化 =减(当前生命,血量扣除); // 剩余生命值
    String 状态变化 = "受伤"; // 默认为受伤状态
    if (生命变化 <= 0) {
        生命变化 = 0;
        状态变化 = "死亡";
        putString(配置名称, "状态", "死亡");
        putString(配置名称, "心情", 转文(心情 - 10));
    }
    
    // 处理副本图片路径
    String 副本图 = AppPath + "/目录/图片/副本/" + 选定 + ".jpg";
    String 图路径 = "";
    File file = new File(副本图);
    if (file.exists()) {
        图路径 = "[PicUrl=" + 副本图 + "]";
    }
    
    // 更新宠物状态和资产
    putString(配置名称, "当前精力", 转文(当前精力 - 总消耗));
    putString(配置名称, "当前经验", 转文(当前经验 + 总经验));
    putString(配置名称, "当前生命", 转文(生命变化));
    putString(配置名 + "/我的资产", "积分", 转文(积分 + 总积分));
    
    // 拼接掉落详情
    StringBuffer 掉落详情 = new StringBuffer();
    for (int i = 0; i < 物品数组.length; i++) {
        String 物品名 = (String) 物品数组[i];
        int 数量Int = (Integer) 获得物品.get(物品名);
        掉落详情.append(物品名).append("×").append(数量Int).append("、");
    }
    String 掉落结果 = 掉落详情.length() > 0 ? 掉落详情.substring(0, 掉落详情.length() - 1) : "";
    
    String finalResult = formatString(掉落结果);
    // 去掉 finalResult 末尾的<起>
    if (finalResult.endsWith("<起>")) {
      finalResult = finalResult.substring(0, finalResult.length() - 3);
    }
    
    // 生成战斗结果文案
    String 战斗结果 = "<分割>〖" + 宠物名 + " VS " + 怪物名称 + "〗</分割>\n"
        + " ▪︎战斗结果：胜利<起> ▫︎消耗精力：" + 数值转(总消耗) + "<尾>\n"
        + " ▪︎获得经验：" + 数值转(总经验) + "<起> ▫︎血量减少：" + 数值转(血量扣除) + "<尾>\n"
        + " ▪︎获得积分：" + 数值转(总积分) + "<起> ▫︎战后状态：" + 状态变化 + "<尾>\n"
        + "<分割>获得道具</分割>\n" + finalResult;
    
    // 生成图片并发送
    toImg(战斗结果, "", 0.5, 0.02, 50, AppPath + "/缓存/副本/" + uin + "_" + 选定 + ".png", false);
    发送(qun, 图路径 + "[PicUrl=" + AppPath + "/缓存/副本/" + uin + "_" + 选定 + ".png]", false);
}
