import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File;
import java.lang.InterruptedException;


//道具请使用 道具.java 内的道具

// 怪物类定义
class Monster {
    private long maxHealth;       // 最大血量
    private long currentHealth;   // 当前血量
    private String skill;         // 技能
    private long attack;          // 攻击力
    private String dropItems;     // 掉落物（格式：道具名:数量范围,道具名:数量范围）

    public Monster(long maxHealth, String skill, long attack, String dropItems) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.skill = skill;
        this.attack = attack;
        this.dropItems = dropItems;
    }

    // Getter和Setter
    public long getMaxHealth() { return maxHealth; }
    public long getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(long currentHealth) { this.currentHealth = currentHealth; }
    public String getSkill() { return skill; }
    public long getAttack() { return attack; }
    public String getDropItems() { return dropItems; }
}

// 怪物数据存储Map
Map monsterMap = new HashMap();

// 初始化怪物数据
monsterMap.put("骨狱冥帝·万魂", new Monster(
    12000000L, 
    "幽冥冲击波", 
    135000L, 
    "仙缘秘藏匣:2-3,10软妹币:1-2,破锋丹:3-4"
));
monsterMap.put("炎狱焚天巨兽", new Monster(
    18000000L, 
    "炼狱火海", 
    1750000L, 
    "仙缘盈丰盒:4-6,焚天炎晶:5-7,能量饼干:2-3"
));
monsterMap.put("雷霆战蛟·雷耀", new Monster(
    14000000L, 
    "万雷贯体", 
    1480000L, 
    "仙缘袖珍袋:5-8,星辉石:3-5,一亿积分卡:1-2"
));
monsterMap.put("裂狱战狮·崩岳", new Monster(
    13000000L, 
    "狮吼震魂", 
    1320000L, 
    "仙缘秘藏匣:1-2,长生丹:3-5,100软妹币:2-3"
));
monsterMap.put("灭世魔瞳·深渊", new Monster(
    25000000L, 
    "魔光湮灭", 
    21000000L, 
    "仙缘秘藏匣:6-9,涅槃:1-1,百亿积分卡:1-2"
));
monsterMap.put("苍穹龙帝·破界", new Monster(
    30000000L, 
    "龙息碎星", 
    2400000L, 
    "仙缘秘藏匣:8-12,龙珠:2-3,十亿经验卡:1-2"
));
monsterMap.put("钢铁裁决者·9号", new Monster(
    1600000000L, 
    "激光炮轰", 
    158000000L, 
    "仙缘袖珍袋:6-10,1000金币:3-5,聚灵玉髓:3-4"
));
monsterMap.put("玄水玄武·瀚海", new Monster(
    350000000L, 
    "龟甲镇狱", 
    2380000L, 
    "仙缘盈丰盒:12-20,吞噬卡:1-1,生命之源:2-3"
));
monsterMap.put("凤炎神鸟·涅槃", new Monster(
    290000000L, 
    "不死烈焰", 
    24200, 
    "仙缘秘藏匣:7-10,50金币:4-6,涅槃:1-1"
));
monsterMap.put("噬魂修罗·无常", new Monster(
    950000000L, 
    "勾魂锁喉", 
    10800000L, 
    "仙缘袖珍袋:5-8,小破锋丹:3-4,焕能丹:2-3"
));
monsterMap.put("基泥苔煤·困困", new Monster(
    9500000000L, 
    "真·铁山靠", 
    50800000L, 
    "仙缘秘藏匣:5-8,50软妹币:1-2,吞噬卡:1-2"
));

// 一些副本BOSS添加
monsterMap.put("森林巨熊", new Monster(
    500000L, 
    "巨熊拍击", 
    5000L, 
    "仙缘袖珍袋:1-2,小破锋丹:2-3,聚灵丹:1-5"
));
monsterMap.put("元素使者", new Monster(
    800000L, 
    "元素风暴", 
    8000L, 
    "仙缘盈丰盒:1-3,小长生丹:2-4,十万积分卡:1-2"
));
monsterMap.put("秩序天使", new Monster(
    10000000L, 
    "秩序光环", 
    100000L, 
    "仙缘袖珍袋:5-7,御体丹:3-4,百万积分卡:1-2"
));
monsterMap.put("幻世之主", new Monster(
    12000000L, 
    "幻世掌控", 
    120000L, 
    "仙缘盈丰盒:6-8,破锋丹:4-6,百万经验卡:1-2"
));
monsterMap.put("破晓战神", new Monster(
    15000000L, 
    "破晓斩击", 
    150000L, 
    "仙缘秘藏匣:6-7,长生丹:4-6,百万积分卡:2-3"
));
monsterMap.put("灵韵鸡", new Monster(
    1000000L, 
    "灵韵啄击", 
    10000L, 
    "仙缘盈丰盒:1-3,聚灵玉髓:2-4,日月精华石:1-3"
));
monsterMap.put("谜星守护者", new Monster(
    1000000L, 
    "星辉射线", 
    10000L, 
    "仙缘袖珍袋:1-3,陨星碎片:3-5,星辉石:2-4"
));
monsterMap.put("炎魔", new Monster(
    1000000L, 
    "火焰冲击", 
    10000L, 
    "仙缘盈丰盒:1-4,焚天炎晶:2-3,赤阳火精:1-2"
));
monsterMap.put("冰魄寒龙", new Monster(
    1000000L, 
    "寒冰吐息", 
    10000L, 
    "仙缘秘藏匣:1-3,玄冰髓:2-4,极地寒晶:1-3"
));
monsterMap.put("深渊龙影", new Monster(
    1000000L, 
    "龙影突袭", 
    10000L, 
    "仙缘袖珍袋:1-2,龙珠:1-1,复活石:1-2"
));
monsterMap.put("熔炉魔像", new Monster(
    1000000L, 
    "熔炉重击", 
    10000L, 
    "仙缘盈丰盒:1-3,玄铁:3-5,复活石:1-1"
));
monsterMap.put("星辉巡守", new Monster(
    1000000L, 
    "星辉扫射", 
    10000L, 
    "仙缘秘藏匣:1-2,星核:2-3,复活石:1-2"
));
monsterMap.put("古树老妖", new Monster(
    1000000L, 
    "藤蔓缠绕", 
    10000L, 
    "仙缘袖珍袋:1-3,灵藤:3-5,复活石:1-1"
));

// 经验/积分副本BOSS添加
monsterMap.put("经验侍从", new Monster(
    1000000L, 
    "经验汲取", 
    10000L, 
    "仙缘盈丰盒:2-4,一亿经验卡:1-2,千万经验卡:2-3"
));
monsterMap.put("经验天尊", new Monster(
    10000000000L, 
    "天尊赐福", 
    500000000L, 
    "仙缘盈丰盒:4-7,百亿经验卡:1-2,十亿经验卡:2-3,经验丹:2-8"
));
monsterMap.put("积分侍从", new Monster(
    1000000L, 
    "积分掠夺", 
    10000L, 
    "仙缘秘藏匣:2-4,一亿积分卡:1-2,千万积分卡:2-3"
));
monsterMap.put("积分天尊", new Monster(
    10000000000L, 
    "天尊赐分", 
    500000000L, 
    "仙缘秘藏匣:4-7,百亿积分卡:1-2,十亿积分卡:2-3"
));

// 限时副本BOSS添加
monsterMap.put("授道仙人", new Monster(
    15500000000L, 
    "传道之光", 
    1500000000L, 
    "仙缘袖珍袋:3-5,千万经验卡:1-3,一亿经验卡:2-3,十亿经验卡:1-2,经验丹:1-5,百亿经验卡:1-3"
));
monsterMap.put("商业巨亨", new Monster(
    15500000000L, 
    "财富虹吸", 
    1500000000L, 
    "仙缘盈丰盒:3-5,千万积分卡:1-3,一亿积分卡:2-3,十亿积分卡:1-2,百亿积分卡:1-3"
));
monsterMap.put("海盗奇兵", new Monster(
    35500000000L, 
    "海盗突袭", 
    5000000000L, 
    "仙缘秘藏匣:3-5,50金币:4-6,10金币:5-8,100金币:3-5,1000金币:1-3,10软妹币:2-5,30软妹币:2-3,50软妹币:1-2,100软妹币:1-2"
));


// 当前战斗中的怪物（群号 -> 怪物名称）
Map currentFighting = new HashMap();

// 召唤怪物
public void 召唤怪物(String qun, String 玩家Uin) {
   // currentFighting.clear(); //提前清空本群之前的怪物
    if (currentFighting.containsKey(qun)) {
        发送(qun, "[AtQQ=" + 玩家Uin + "] 当前已有怪物，击败后才能召唤新怪物！\n◆指令：攻击怪物", true);
        return;
    }
    
    // 消耗玩家背包中的“信标”道具（数量-1）
    String 背包路径 = qun + "/" + 玩家Uin + "/我的背包";
    String 背包 = getString(背包路径, "道具列表");
    long 信标数量 = 文转(getString(背包路径, "信标", "0"));
    if (信标数量 < 1) {
        发送(qun, "[AtQQ=" + 玩家Uin + "]  背包中没有信标，无法召唤怪物！", true);
        return;
    }
    
    if (信标数量 >= 1) {
         if(减(信标数量, 1) >= 1) {
             putString(背包路径, "信标", 转文(减(信标数量, 1)));
          } else {
             String 备 = 背包.replace("、信标", "");
             String 北 = 备.replace("信标、", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(背包路径, "道具列表", null);
                  putString(背包路径, "信标", null);
              } else {
                  putString(背包路径, "道具列表", 北);
                  putString(背包路径, "信标", null);
              }
          }
    }

    // 随机选择怪物
    String[] 怪物列表 = monsterMap.keySet().toArray(new String[0]);
    String 随机怪物 = 怪物列表[new Random().nextInt(怪物列表.length)];
    Monster 怪物 = monsterMap.get(随机怪物);
    currentFighting.put(qun, 随机怪物);
    
    // 根据是否存在该怪物名称的图片来赋值
    String 怪物呀=随机怪物;
    if(怪物呀.contains("·")){
      怪物呀=怪物呀.replace("·","_"); 
      //将指定符号替换为“_”（避免因符号问题而无法获取图片）
    }
    String 怪物图 = AppPath + "/目录/图片/怪物/" + 怪物呀 + ".jpg";
    String 图路径;
    File file = new File(怪物图);
    if (file.exists()) {
        图路径 = "[PicUrl=" + 怪物图 + "]";
    } else {
        图路径 = "\n"; // 没图，默认为换行
    }

     long 使用记录=文转(getString(qun + "/" + 玩家Uin+"/道具使用限制", "信标")); //已使用数量
     long 限制数量=100;
    // 发送召唤提示
    发送(qun, "[AtQQ=" + 玩家Uin + "] 成功使用「信标」×1，你召唤出【" + 随机怪物 + "】！" + 图路径 +
            "•血量：" + 怪物.getMaxHealth() + "\n" +
            "•技能：" + 怪物.getSkill() + "\n->发送指令【攻击怪物】\n一起击退怪物吧！！\n————————\n✦今日剩余次数：" + 使用记录 + "/"+限制数量, false);
            
}


// 玩家攻击怪物
public void 玩家攻击(String qun, String 玩家Uin, long 玩家攻击值) {
    String 怪物名称 = currentFighting.get(qun);
    if (怪物名称 == null) {
        发送(qun, "[AtQQ=" + 玩家Uin + "] 无怪物可攻击，请先召唤！", true);
        return;
    }
    
    String 配置名 = qun + "/" + 玩家Uin + "/";
    String 配置名称 = qun + "/" + 玩家Uin + "/宠物小窝/位置_0";
    long 心情 = 文转(getString(配置名称, "心情"));
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    
    // 处理怪物图片路径
    String 怪物呀 = 怪物名称;
    if (怪物呀.contains("·")) {
        怪物呀 = 怪物呀.replace("·", "_");
        //将指定符号的替换为_（避免因符号问题而无法获取图片）
    }
    String 怪物图 = AppPath + "/目录/图片/怪物/" + 怪物呀 + ".jpg";
    String 图路径;
    File file = new File(怪物图);
    if (file.exists()) {
        图路径 = "[PicUrl=" + 怪物图 + "]";
    } else {
        图路径 = "\n"; //不存在该怪物图片时，默认为换行
    }
    
    Monster 怪物 = monsterMap.get(怪物名称);
    long 伤害 = (long) (玩家攻击值 * (0.85 + Math.random() * 0.3)); // 伤害浮动
    long 剩余血量 = 怪物.getCurrentHealth() - 伤害;
    boolean 怪物被击败 = 剩余血量 <= 0;
    
    // 异常状态相关变量初始化
    String 技能名 = 怪物.getSkill();
    boolean 需附着异常 = false;
    String 异常状态 = "";
    String 异常提示 = "";
    
    // 判断是否为指定技能的怪物，设置对应异常状态
    switch (技能名) {
        case "寒冰吐息":
            需附着异常 = true;
            异常状态 = "冻结";
            break;
        case "火焰冲击":
            需附着异常 = true;
            异常状态 = "灼烧";
            break;
        case "不死烈焰":
            需附着异常 = true;
            异常状态 = "灼烧";
            break;
        case "炼狱火海":
            需附着异常 = true;
            异常状态 = "灼烧";
            break;
    }
    
    // 计算玩家剩余生命
    long 玩家剩余生命 = 当前生命;
    long 反击伤害 = 0;
    if (!怪物被击败) {
        反击伤害 = (long) (怪物.getAttack() * (0.9 + Math.random() * 0.2));
        玩家剩余生命 = 当前生命 - 反击伤害;
    }
    
    // 45%概率附着异常状态（玩家生命>0时生效）
    if (需附着异常 && 玩家剩余生命 > 0 && Math.random() < 0.45) {
        putString(配置名称, "状态", 异常状态); //更新玩家状态
        异常提示 = "\n【" + 怪物名称 + "】的" + 技能名 + "对你造成了影响，你陷入了" + 异常状态 + "！";
    }
    
    // 处理怪物被击败的情况
    if (怪物被击败) {
        怪物.setCurrentHealth(0);
        putString(配置名称, "当前精力", 转文(当前精力 - 10)); // 扣除精力
        // 发送击败消息（含异常提示）
        发送(qun, "[AtQQ=" + 玩家Uin + "] 造成" + 数值转(伤害) + "点伤害，【" + 怪物名称 + "】被击败！" + 异常提示, false);
        分配掉落(qun, 玩家Uin, 怪物);
    } else {
        // 处理怪物未被击败的情况
        怪物.setCurrentHealth(剩余血量);
        putString(配置名称, "当前精力", 转文(当前精力 - 10)); // 扣除精力
        putString(配置名称, "当前生命", 转文(玩家剩余生命)); // 更新玩家生命
        
        // 处理玩家死亡状态
        if (玩家剩余生命 < 1) {
            putString(配置名称, "状态", "死亡");
            putString(配置名称, "心情", 转文(心情 - 10)); // 扣除心情值
        }
        
        // 发送反击消息（含异常提示）
        发送(qun, "[AtQQ=" + 玩家Uin + "] 造成" + 数值转(伤害) + "点伤害，怪物剩余血量：" + 数值转(剩余血量) + 图路径 
                + "【" + 怪物名称 + "】反击，对你造成" + 数值转(反击伤害) + "点伤害！" 
                + 异常提示 + "\n————————\n•当前怪物还未被击败，\n请发送【攻击怪物】继续进攻！", false);
    }
}


// 分配掉落（区分队伍/个人）
private void 分配掉落(String qun, String 击败者Uin, Monster 怪物) {
    String 配置根 = qun + "/组队/";
    String 队伍列表 = getString(配置根 + "队伍列表", "队伍列表", "");
    String[] 成员数组 = null;
    boolean 有队伍 = false;

    // 检查是否在队伍中
    if (!队伍列表.isEmpty()) {
        for (String 队ID : 队伍列表.split("、")) {
            String 成员列表 = getString(配置根 + 队ID, "成员列表", "");
            if (成员列表.contains(击败者Uin)) {
                成员数组 = 成员列表.split("、");
                有队伍 = true;
                break;
            }
        }
    }
    
    // 获取击败者宠物的昵称
    String 配置名称 = qun + "/" + 击败者Uin + "/宠物小窝/位置_0";
    String 宠物昵称 = getString(配置名称, "昵称");
    // 获取本群怪物名称
    String 怪物名称 = currentFighting.get(qun);

    // 生成掉落物
    String[] 掉落列表 = 怪物.getDropItems().split(",");
    Random random = new Random();
    StringBuilder 分配结果 = new StringBuilder("【" + 宠物昵称 + " KO " + 怪物名称 + "】\n奖励分配如下：\n-------------------\n");

    if (!有队伍) {
        // 个人分配
        String 个人掉落 = 生成随机掉落(掉落列表, random);
        long 个人积分 = 随机积分(20000, 35000);
        更新资产(qun, 击败者Uin, 个人积分, 个人掉落);
        分配结果.append("[@").append(击败者Uin).append("]+").append(个人积分).append("积分\n")
                .append("◆获得：").append(个人掉落).append("\n-------------------");
    } else {
        // 队伍分配
        for (String 成员 : 成员数组) {
            String 成员掉落 = 生成随机掉落(掉落列表, random);
            long 成员积分 = 随机积分(15000, 30000);
            更新资产(qun, 成员, 成员积分, 成员掉落);
            分配结果.append("[@").append(成员).append("]+").append(成员积分).append("积分\n")
                    .append("◆获得：").append(成员掉落).append("\n");
        }
        分配结果.append("-------------------");
    }

    发送(qun, 分配结果.toString(), true);
     currentFighting.remove(qun); //移除怪物

}

// 辅助方法：生成随机掉落
private String 生成随机掉落(String[] 掉落列表, Random random) {
    String 选中物品 = 掉落列表[random.nextInt(掉落列表.length)];
    String[] 物品信息 = 选中物品.split(":");
    String[] 数量范围 = 物品信息[1].split("-");
    int 数量 = Integer.parseInt(数量范围[0]) + random.nextInt(
        Integer.parseInt(数量范围[1]) - Integer.parseInt(数量范围[0]) + 1
    );
    return 物品信息[0] + "×" + 数量;
}

// 辅助方法：生成随机积分
private long 随机积分(long min, long max) {
    if (min >= max) {
        return min; // 避免范围错误
    }
    long range = max - min + 1;
    // 使用nextDouble()生成0.0到1.0之间的随机数，转换为long范围内的值
    return min + (long) (new Random().nextDouble() * range);
}

// 辅助方法：更新玩家资产
private void 更新资产(String qun, String 玩家Uin, long 新增积分, String 新增物品) {
    // 更新积分
    String 资产路径 = qun + "/" + 玩家Uin + "/我的资产";
    long 当前积分 = 文转(getString(资产路径, "积分", "0"));
    putString(资产路径, "积分", 转文(当前积分 + 新增积分));

    // 更新物品
    String 背包路径 = qun + "/" + 玩家Uin + "/我的背包";
    String 现有物品 = getString(背包路径, "道具列表", "");
    String[] 物品信息 = 新增物品.split("×");
    String 物品名 = 物品信息[0];
    int 数量 = Integer.parseInt(物品信息[1]);

    if (现有物品.contains(物品名)) {
        long 现有数量 = 文转(getString(背包路径, 物品名, "0"));
        putString(背包路径, 物品名, 转文(现有数量 + 数量));
    } else {
        String 新物品列表 = 现有物品.isEmpty() ? 物品名 : 现有物品 + "、" + 物品名;
        putString(背包路径, "道具列表", 新物品列表);
        putString(背包路径, 物品名, 转文(数量));
    }
}



// 启动新线程执行定时刷新（根据开关状态控制）
new Thread(new Runnable() {
    public void run() {
        while (true) { // 循环执行
            try {
                // 先判断怪物入侵是否开启
                boolean isMonsterInvasion = getBoolean("系统配置", "怪物入侵", false);
                if (!isMonsterInvasion) {
                    // 若关闭状态，休眠默认间隔后再次检查
                    int refreshInterval = getInt("系统配置", "怪物刷新间隔", 300000);
                    Thread.sleep(refreshInterval);
                    continue; // 跳过本次刷新逻辑
                }

                // 以下为开启状态时的刷新逻辑
                int refreshInterval = getInt("系统配置", "怪物刷新间隔", 300000); // 默认5分钟
                String openGroups = getString("开放群列表", "开放列表"); //已开放“宠物世界”的群列表
                if (openGroups == null || openGroups.isEmpty()) {
                    Thread.sleep(refreshInterval);
                    //无开放群，休眠后继续
                    continue;
                }
                String[] groups = openGroups.split("、"); //以“、”分隔已开放群列表
                Random random = new Random();
                for (String group : groups) {
                  // 刷新前先休眠5秒
                  Thread.sleep(5000); // 避免消息发送太快导致的一些问题
                    
                    currentFighting.remove(group); // 清空该群现有怪物
                    String[] monsterNames = monsterMap.keySet().toArray(new String[0]);
                    String randomMonster = monsterNames[random.nextInt(monsterNames.length)];
                    Monster monster = monsterMap.get(randomMonster);
                    
                    // 处理怪物图片
                    String monsterImgName = randomMonster.contains("·") ? randomMonster.replace("·", "_") : randomMonster;
                    String monsterImgPath = AppPath + "/目录/图片/怪物/" + monsterImgName + ".jpg";
                    File imgFile = new File(monsterImgPath);
                    String imgTag = imgFile.exists() ? "[PicUrl=" + monsterImgPath + "]" : "\n";
                    
                    // 刷新新怪物到群
                    currentFighting.put(group, randomMonster);
                    发送(group, imgTag + "怪物【" + randomMonster + "】侵入本群！！！\n" +
                            "•血量：" + monster.getMaxHealth() + "\n" +
                            "•技能：" + monster.getSkill() + "\n->发送指令【攻击怪物】\n一起击退怪物吧！", false);
                }
                Thread.sleep(refreshInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}).start(); // 启动线程



// 示例：队伍击败怪物的分配效果
/*
【终焉织者 KO 苍穹龙帝·破界】
奖励分配如下：
-------------------
[11451419]+32500积分
◆获得：仙缘秘藏匣×3
[1919810]+28900积分
◆获得：龙珠×1
[114514]+25100积分
◆获得：十亿经验卡×1
-------------------
*/

// 示例：个人击败怪物的分配效果
/*
【逢魔时王 KO 噬魂修罗·无常】
奖励分配如下：
-------------------
[1919810]+21800积分
◆获得：仙缘袖珍袋×6
-------------------
*/
