import java.util.HashMap;
import java.util.Map;

// 定义物品类
class Item {
    private String type;          // 道具类型
    private long exchangeGold;    // 兑换消耗金币数量
    private long purchasePoints;  // 购买消耗积分数量
    private long sellPoints;      // 出售获得积分数量
    private long maxUseCount;     // 单次最大使用量
    private boolean canUseDirectly;       // 能否直接使用
    private boolean canTransfer;          // 能否转让
    private boolean canSell;              // 能否出售(获得积分)
    private boolean canPurchase;          // 能否购买(消耗积分购买)
    private boolean canExchange;          // 能否兑换(消耗金币兑换)
    private boolean canUseOnOthers;       // 能否对他人使用
    private boolean canCombine;           // 能否合成
    private boolean canDecompose;         // 能否分解
    private String description;           // 简介（新增）

    public Item(String type, long exchangeGold, long purchasePoints, long sellPoints, long maxUseCount, boolean canUseDirectly, boolean canTransfer, boolean canSell, boolean canPurchase, boolean canExchange, boolean canUseOnOthers, boolean canCombine, boolean canDecompose, String description) {
        this.type = type;
        this.exchangeGold = exchangeGold;
        this.purchasePoints = purchasePoints;
        this.sellPoints = sellPoints;
        this.maxUseCount = maxUseCount;
        this.canUseDirectly = canUseDirectly;
        this.canTransfer = canTransfer;
        this.canSell = canSell;
        this.canPurchase = canPurchase;
        this.canExchange = canExchange;
        this.canUseOnOthers = canUseOnOthers;
        this.canCombine = canCombine;
        this.canDecompose = canDecompose;
        this.description = description;  // 初始化简介
    }

    // 获取简介的方法
    public String getDescription() {
        return description;
    }

    //其他获取相关内容的方法
    public String getType() { return type; }
    public long getExchangeGold() { return exchangeGold; }
    public long getPurchasePoints() { return purchasePoints; }
    public long getSellPoints() { return sellPoints; }
    public long getMaxUseCount() { return maxUseCount; }
    public boolean isCanUseDirectly() { return canUseDirectly; }
    public boolean isCanTransfer() { return canTransfer; }
    public boolean isCanSell() { return canSell; }
    public boolean isCanPurchase() { return canPurchase; }
    public boolean isCanExchange() { return canExchange; }
    public boolean isCanUseOnOthers() { return canUseOnOthers; }    
    public boolean isCanCombine() { return canCombine; }    
    public boolean isCanDecompose() { return canDecompose; }
}


/*  这里是否方便用来分辨道具是否存在以及查询相关设定，道具的作用以及获得途径等等，还需自己实现才行  */
// 创建一个Map来存储物品和各项属性
Map itemMap = new HashMap();

 /*
  // 定义类型顺序
   String[] typeOrder = {"恢复", "消耗", "复活", "仙丹", "精力", "抽奖", "经验", "积分", "金币", "充值", "战力", "转换", "材料", "碎片", "神器", "装备", "特殊", "神秘", "礼包", "宝箱","定制"}; 
   
// 开始 添加道具及其属性
// String ：道具，类型
// long：兑换金币，购买积分，出售积分，单次使用量
// boolean：是否能使用，是否转让，是否出售，是否购买
//boolean：是否兑换，是否可对他人使用，是否能合成，是否能分解
//String ：简介
*/
itemMap.put("破锋丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, true, true, false, false, "对宠物使用后，增加500点攻击")); //500攻击 /颗
itemMap.put("长生丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, true, true, false, false, "对宠物使用后，增加1w点生命上限")); //10000生命 /颗
itemMap.put("御体丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, true, true, false, false, "对宠物使用后，增加500点防御")); //500防御 /颗
itemMap.put("灵智丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, true, true, false, false, "对宠物使用后，增加50点智力")); //50智力 /颗
itemMap.put("焕能丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, true, false, false, false, "使用后可以恢复1000精力")); //1000精力 /颗
itemMap.put("聚灵丹", new Item("仙丹", 5, 10000, 2000, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加10w点经验")); //10w经验 /颗
itemMap.put("小破锋丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加50点攻击")); //50攻击 /颗
itemMap.put("小长生丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加1000点生命上限")); //1000生命 /颗
itemMap.put("小御体丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加50点防御")); //50防御 /颗
itemMap.put("小灵智丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加5点智力")); //5智力 /颗
itemMap.put("小焕能丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, false, false, false, "使用后可以恢复100精力")); //100点精力 /颗
itemMap.put("小聚灵丹", new Item("仙丹", 1, 1000, 200, 9999999, true, true, true, true, false, true, false, false, "对宠物使用后，增加1000点经验")); //1千经验 /颗
itemMap.put("死亡丹", new Item("仙丹", 1, 1000, 200, 1, true, true, true, true, false, true, false, false, "可让目标的宠物‘死亡’\n•示例：使用死亡丹@某人")); 
itemMap.put("凝神丹", new Item("仙丹", 20, 10000, 200, 99999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加200点精力上限；同时也是神器‘太虚镜’的合成材料之一。")); //增加200精力上限 / 颗
itemMap.put("回春丹", new Item("仙丹", 3, 50000, 2000, 1, true, true, true, true, true, false, false, false, "使用后可回满宠物生命")); //恢复100％生命/颗

itemMap.put("绷带", new Item("恢复", 1, 15000, 500, 5, true, true, true, true, true, false, false, false, "恢复宠物20%生命")); //恢复20％生命/条
itemMap.put("鸡汤", new Item("恢复", 1, 5000, 100, 10, true, true, true, true, true, false, false, false, "恢复宠物10%生命")); //恢复10％生命/份
itemMap.put("开心果", new Item("恢复", 1, 5000, 100, 10, true, true, true, true, true, false, false, false, "恢复宠物10点心情")); //恢复10点心情/颗
itemMap.put("生命之源", new Item("恢复", 100, 100, 50, 1, true, false, true, false, false, false, false, false, "一饮而尽即可瞬间恢复全部精力、生命值与心情值，同时解除所有异常状态，绝境重生的必备良药。")); // 恢复精力、生命值、心情值：100% 并解除异常状态 / 瓶

itemMap.put("忘情水", new Item("消耗", 2, 5000, 100, 1, true, true, true, true, true, false, false, false, "一抹清冽，斩断情缘羁绊，使宠物即刻脱离伴侣关系。")); //解除“已婚”状态，让宠物回归单身
itemMap.put("万灵药", new Item("消耗", 3, 7500, 800, 1, true, true, true, true, true, false, false, false, "可解除所有异常状态")); //解除一切异常状态
itemMap.put("永恒之戒", new Item("消耗", 1, 52000, 100, 1, false, true, true, true, true, false, false, false, "宠物求婚时必备")); //戒指，宠物求婚时消耗
itemMap.put("性转符", new Item("消耗", 0, 10000, 100, 1, true, false, true, true, false, false, false, false, "可瞬间扭转角色性别")); //更改宠物性别
itemMap.put("宠物赠送卡", new Item("消耗", 0, 10000, 100, 1, false, true, true, false, false, false, false, false, "用于转让战力不超过1500w的宠物")); //可将宠物赠送给他人（战力限制在1500w）

itemMap.put("信标", new Item("消耗", 20, 0, 100, 1, true, false, true, false, true, false, false, false, "随机召唤怪物，可直接使用")); // 随机召唤怪物
 
itemMap.put("妙手回春天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("锐牙狂威天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("妙手神偷天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("厚土磐佑天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("虚空壁垒天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("疫病之源天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));
itemMap.put("灵蕴丹成天赋包",new Item("消耗", 500, 0, 1000, 1, true, false, true, false, true, false, false, false, "使用后获得该天赋"));

itemMap.put("小精力药", new Item("精力", 1, 1000, 100, 2000, true, true, true, true, true, false, false, false, "使用后可以回复宠物10点精力，每日限制使用2000")); // 10精力值/瓶，每日使用限制量:2000
itemMap.put("中精力药", new Item("精力", 2, 2000, 200, 1000, true, true, true, true, true, false, false, false, "使用后可以回复宠物20点精力，每日限制使用1000")); // 20精力值/瓶，每日使用限制数量:1000
itemMap.put("大精力药", new Item("精力", 5, 5000, 500, 500, true, true, true, true, true, false, false, false, "使用后可以回复宠物40点精力，每日限制使用500")); // 40精力值/瓶，每日使用限制数量:500
itemMap.put("超级精力药", new Item("精力", 8, 8000, 750, 500, true, true, true, true, true, false, false, false, "使用后可以回复宠物200点精力，每日限制使用100")); // 200精力值/瓶，每日使用限制数量:100
itemMap.put("薄荷糖", new Item("精力", 10, 100, 10, 100, true, false, true, false, false, false, false, false, "使用后可以回复宠物1%精力")); // 恢复精力：1% / 颗
itemMap.put("蜂蜜果糖", new Item("精力", 50, 100, 10, 20, true, false, true, false, false, false, false, false, "使用后可以回复宠物5%精力")); // 恢复精力：5% / 颗
itemMap.put("坚果棒", new Item("精力", 100, 100, 10, 10, true, false, true, false, false, false, false, false, "使用后可以回复宠物10%精力")); // 恢复精力：10% / 根
itemMap.put("能量饼干", new Item("精力", 200, 100, 10, 5, true, false, true, false, false, false, false, false, "使用后可以回复宠物20%精力")); // 恢复精力：20% / 块
itemMap.put("巧克力棒", new Item("精力", 250, 100, 10, 4, true, false, true, false, false, false, false, false, "使用后可以回复宠物25%精力")); // 恢复精力：25% / 块
itemMap.put("人参茶", new Item("精力", 300, 100, 10, 3, true, false, true, false, false, false, false, false, "使用后可以回复宠物30%精力")); // 恢复精力：30% / 杯
itemMap.put("活力奶昔", new Item("精力", 500, 100, 10, 2, true, false, true, false, false, false, false, false, "使用后可以回复宠物50%精力")); // 恢复精力：50% / 杯
itemMap.put("超级能量果", new Item("精力", 1000, 100, 10, 1, true, false, true, false, false, false, false, false, "使用后可以回复宠物100%精力")); // 恢复精力：100% / 个

itemMap.put("复活石",new Item("复活", 2, 9999, 10, 1, true, true, true, true, true, false, false, false, "使用后可以将宠物复活，并回复5%生命"));
itemMap.put("灵魂沙漏",new Item("复活", 10, 125000, 10000, 1, true, true, true, true, true, false, false, false, "使用后可以让宠物满血复活"));
itemMap.put("往生花",new Item("复活", 10, 125000, 10000, 1, true, true, true, true, true, false, false, false, "使用后可以让宠物满血复活"));
itemMap.put("九转续命丹",new Item("复活", 10, 125000, 10000, 1, true, true, true, true, true, false, false, false, "使用后可以让宠物满血复活"));

itemMap.put("通用夺宝券", new Item("抽奖", 0, 0, 1000000, 10, false, false, true, false, false, false, false, false, "消耗品，可用于积分或金币抽奖"));
itemMap.put("积分夺宝券", new Item("抽奖", 0, 0, 1000000, 10, false, false, true, false, false, false, false, false, "消耗品，可用于积分抽奖"));
itemMap.put("金币夺宝券", new Item("抽奖", 0, 0, 1000000, 10, false, false, true, false, false, false, false, false, "消耗品，可用于金币抽奖"));

itemMap.put("五千经验卡", new Item("经验", 0, 0, 100, 999999999, true, true, true, false, false, false, false, false, "使用后可获得五千经验"));
itemMap.put("一万经验卡", new Item("经验", 0, 0, 1000, 999999999, true, true, true, false, false, false, false, false, "使用后可获得一万经验"));
itemMap.put("十万经验卡", new Item("经验", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "使用后可获得十万经验"));
itemMap.put("百万经验卡", new Item("经验", 0, 0, 1400, 999999999, true, true, true, false, false, false, false, false, "使用后可获得百万经验"));
itemMap.put("千万经验卡", new Item("经验", 0, 0, 1600, 999999999, true, true, true, false, false, false, false, false, "使用后可获得千万经验"));
itemMap.put("一亿经验卡", new Item("经验", 0, 0, 1800, 999999999, true, true, true, false, false, false, false, false, "使用后可获得1亿经验"));
itemMap.put("十亿经验卡", new Item("经验", 0, 0, 2000, 999999999, true, true, true, false, false, false, false, false, "使用后可获得10亿经验"));
itemMap.put("百亿经验卡", new Item("经验", 0, 0, 2400, 99999999, true, true, true, false, false, false, false, false, "使用后可获得100亿经验"));
itemMap.put("经验丹", new Item("经验", 0, 0, 2400, 999999999, true, true, true, false, false, false, false, false, "使用后可获得10亿经验"));

itemMap.put("五千积分卡", new Item("积分", 0, 0, 100, 999999999, true, true, true, false, false, false, false, false, "使用后可获得五千积分"));
itemMap.put("一万积分卡", new Item("积分", 0, 0, 1000, 999999999, true, true, true, false, false, false, false, false, "使用后可获得一万积分"));
itemMap.put("十万积分卡", new Item("积分", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "使用后可获得十万积分"));
itemMap.put("百万积分卡", new Item("积分", 0, 0, 1400, 999999999, true, true, true, false, false, false, false, false, "使用后可获得百万积分"));
itemMap.put("千万积分卡", new Item("积分", 0, 0, 1600, 999999999, true, true, true, false, false, false, false, false, "使用后可获得千万积分"));
itemMap.put("一亿积分卡", new Item("积分", 0, 0, 1800, 999999999, true, true, true, false, false, false, false, false, "使用后可获得1亿积分"));
itemMap.put("十亿积分卡", new Item("积分", 0, 0, 2000, 999999999, true, true, true, false, false, false, false, false, "使用后可获得10亿积分"));
itemMap.put("百亿积分卡", new Item("积分", 0, 0, 2400, 99999999, true, true, true, false, false, false, false, false, "使用后可获得100亿积分"));

itemMap.put("1金币", new Item("金币", 0, 0, 100, 9999999, true, true, true, false, false, false, false, false, "使用后可获得1金币"));
itemMap.put("10金币", new Item("金币", 0, 0, 1000, 9999999, true, true, true, false, false, false, false, false, "使用后可获得10金币"));
itemMap.put("50金币", new Item("金币", 0, 0, 5000, 9999999, true, true, true, false, false, false, false, false, "使用后可获得50金币"));
itemMap.put("100金币", new Item("金币", 0, 0, 10000, 9999999, true, true, true, false, false, false, false, false, "使用后可获得100金币"));
itemMap.put("1000金币", new Item("金币", 0, 0, 100000, 9999999, true, true, true, false, false, false, false, false, "使用后可获得1000金币"));

itemMap.put("10软妹币", new Item("充值", 0, 0, 1000, 9999999, true, true, true, false, false, false, false, false, "代充券，使用后获得2000金币"));
itemMap.put("30软妹币", new Item("充值", 0, 0, 3000, 9999999, true, true, true, false, false, false, false, false, "代充券，使用后获得6000金币"));
itemMap.put("50软妹币", new Item("充值", 0, 0, 5000, 9999999, true, true, true, false, false, false, false, false, "代充券，使用后获得1万金币"));
itemMap.put("100软妹币", new Item("充值", 0, 0, 120000, 9999999, true, true, true, false, false, false, false, false, "代充券，使用后获得2万金币"));

itemMap.put("20攻击", new Item("战力", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加20点攻击"));
itemMap.put("20防御", new Item("战力", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加20点防御"));
itemMap.put("200生命", new Item("战力", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加200点生命上限"));
itemMap.put("1智力", new Item("战力", 0, 0, 1200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加1点智力"));
itemMap.put("200攻击", new Item("战力", 0, 0, 3200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加200点攻击"));
itemMap.put("200防御", new Item("战力", 0, 0, 3200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加200点防御"));
itemMap.put("2000生命", new Item("战力", 0, 0, 3200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加2000点生命上限"));
itemMap.put("10智力", new Item("战力", 0, 0, 3200, 999999999, true, true, true, false, false, false, false, false, "对宠物使用后，增加10点智力"));

itemMap.put("金蝉", new Item("转换", 0, 0, 1, 1, true, true, false, false, false, false, false, false, "可将宠物为负数的属性强制改为1（用于修复宠物异常属性）")); //将 <1的属性强制转换为1（可挽救数值爆炸的玩家）
itemMap.put("生命转智力", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将2E生命转换为100w智力（转换比例200:1）")); //生命转智力
itemMap.put("生命转攻击", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将200w生命转换为20w攻击")); //生命转攻击
itemMap.put("生命转防御", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将200w生命转换为20w防御")); //生命转防御
itemMap.put("攻击转智力", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w攻击转换为1w智力")); //攻击转智力
itemMap.put("攻击转防御", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w攻击转换为20w防御")); //攻击转防御
itemMap.put("攻击转生命", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w攻击转换为200w生命")); //攻击转生命
itemMap.put("防御转智力", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w防御转换为1w智力")); //防御转智力
itemMap.put("防御转攻击", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w防御转换为20w攻击")); //防御转攻击
itemMap.put("防御转生命", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将20w防御转换为200w生命")); //防御转生命
itemMap.put("智力转攻击", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将1w智力转换为20w攻击")); //智力转攻击
itemMap.put("智力转防御", new Item("转换", 0, 0, 1, 999999999, true, true, false, false, false, false, false, false, "可将1w智力转换为20w防御")); //智力转防御
itemMap.put("智力转生命", new Item("转换", 1, 1, 1, 999999999, true, true, false, false, false, false, false, false, "可将1w智力转换为200w生命")); //智力转生命

itemMap.put("龙珠", new Item("材料", 1, 500000, 50000, 1, false, true, true, true, true, false, false, false, "‘龙珠’体系神器合成材料"));
itemMap.put("星核", new Item("材料", 1, 500000, 50000, 1, false, true, true, true, true, false, false, false, "‘星核’体系神器合成材料"));
itemMap.put("玄铁", new Item("材料", 1, 500000, 50000, 1, false, true, true, true, true, false, false, false, "‘玄铁’体系神器合成材料"));
itemMap.put("灵藤", new Item("材料", 1, 500000, 50000, 1, false, true, true, true, true, false, false, false, "‘灵藤’体系神器合成材料"));
itemMap.put("青冥玄铁", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘青冥剑’合成材料之一"));
itemMap.put("天青藤", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘青冥剑’合成材料之一"));
itemMap.put("剑灵残魂", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘青冥剑’合成材料之一"));
itemMap.put("聚灵玉髓", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘聚灵盏’合成材料之一"));
itemMap.put("日月精华石", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘聚灵盏’合成材料之一"));
itemMap.put("灵木心", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘聚灵盏’合成材料之一"));
itemMap.put("镇魔符箓", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘镇魔幡’合成材料之一"));
itemMap.put("九幽魔藤", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘镇魔幡’合成材料之一"));
itemMap.put("魔王精血", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘镇魔幡’合成材料之一"));
itemMap.put("陨星碎片", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘星陨珠’合成材料之一"));
itemMap.put("星辉石", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘星陨珠’合成材料之一"));
itemMap.put("星辰沙", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘星陨珠’合成材料之一"));
itemMap.put("焚天炎晶", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘焚天印’合成材料之一"));
itemMap.put("赤阳火精", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘焚天印’合成材料之一"));
itemMap.put("炎魔之心", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘焚天印’合成材料之一"));
itemMap.put("玄冰髓", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘玄冰盾’合成材料之一"));
itemMap.put("极地寒晶", new Item("材料", 0, 0, 1000, 1, false, false, true, false, false, false, false, false, "‘玄冰盾’合成材料之一"));
itemMap.put("冰魄之心", new Item("材料", 0, 0, 1250, 1, false, false, true, false, false, false, false, false, "‘玄冰盾’合成材料之一"));

itemMap.put("太虚镜碎片", new Item("碎片", 0, 0, 100000, 1, false, false, true, false, false, false, false, false, "‘太虚镜’的合成材料之一")); //神器主材料，与“凝神丹”可合成神器“太虚镜”

//乱七八糟的神器
itemMap.put("太虚镜", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/太虚镜.jpg]◆攻击+500w\n◆防御+300w\n◆生命+5000w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("青冥剑", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/青冥剑.jpg]◆攻击+650w\n◆防御+250w\n◆生命+4500w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("玄冰盾", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/玄冰盾.jpg]◆攻击+300w\n◆防御+800w\n◆生命+3000w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("焚天印", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/焚天印.jpg]◆攻击+500w\n◆防御+300w\n◆生命+5000w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("星陨珠", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/星陨珠.jpg]◆攻击+500w\n◆防御+300w\n◆生命+5000w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("镇魔幡", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/镇魔幡.jpg]◆攻击+500w\n◆防御+300w\n◆生命+5000w\n◆智力+1w\n◆精力+2.5w"));
itemMap.put("聚灵盏", new Item("神器", 0, 0, 1000000, 1, true, false, true, false, false, false, true, true, "[图=神器/聚灵盏.jpg]◆攻击+400w\n◆防御+300w\n◆生命+4000w\n◆智力+1w\n◆精力+2.5w"));

//“龙珠”体系神器
itemMap.put("聚气青锋", new Item("神器", 0, 0, 100, 1, true, true, true, false, false, false, true, true, "5颗龙珠打造而成"));
itemMap.put("旋影游龙剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10颗龙珠或‘聚气青锋’×2打造而成"));
itemMap.put("龙啸破云刃", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20颗龙珠或‘旋影游龙剑’×2打造而成"));
itemMap.put("裂空星陨剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "40颗龙珠或‘龙啸破云刃’×2打造而成"));
itemMap.put("陨星龙脊剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "80颗龙珠或‘裂空星陨剑’×2打造而成"));
itemMap.put("九霄龙吟剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "160颗龙珠或‘陨星龙脊剑’×2打造而成"));
itemMap.put("混沌盘龙刃", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "320颗龙珠或‘九霄龙吟剑’×2打造而成"));
itemMap.put("万象龙纹戟", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "640颗龙珠或‘混沌盘龙刃’×2打造而成"));
itemMap.put("太虚龙魄枪", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "1280颗龙珠或‘万象龙纹戟’×2打造而成"));
itemMap.put("焚世龙炎刀", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "2560颗龙珠或‘太虚龙魄枪’×2打造而成"));
itemMap.put("镇狱龙渊斧", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "5120颗龙珠或‘焚世龙炎刀’×2打造而成"));
itemMap.put("永恒龙晶锏", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10240颗龙珠或‘镇狱龙渊斧’×2打造而成"));
itemMap.put("混沌祖龙杵", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "15000颗龙珠或‘永恒龙晶锏’×2打造而成"));
itemMap.put("造化天龙钩", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20000颗龙珠或‘混沌祖龙杵’×2打造而成"));
itemMap.put("无上龙尊槊", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "25000颗龙珠或‘造化天龙钩’×2打造而成"));
itemMap.put("混元终焉剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "30000颗龙珠或‘无上龙尊槊’×2打造而成"));


//“星核”体系神器
itemMap.put("星辉凝霜剑", new Item("神器", 0, 0, 100, 1, true, true, true, false, false, false, true, true, "5颗星核打造而成"));
itemMap.put("幻星逐月双剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10颗星核或‘星辉凝霜剑’×2打造而成"));
itemMap.put("星痕破军剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20颗星核或‘幻星逐月双剑’×2打造而成"));
itemMap.put("流光星陨刃", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "40颗星核或‘星痕破军剑’×2打造而成"));
itemMap.put("陨星破晓剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "80颗星核或‘流光星陨刃’×2打造而成"));
itemMap.put("星辰耀世枪", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "160颗星核或‘陨星破晓剑’×2打造而成"));
itemMap.put("星穹龙脊棍", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "320颗星核或‘星辰耀世枪’×2打造而成"));
itemMap.put("星渊万象戟", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "640颗星核或‘星穹龙脊棍’×2打造而成"));
itemMap.put("太虚星魄杖", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "1280颗星核或‘星渊万象戟’×2打造而成"));
itemMap.put("焚星龙炎鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "2560颗星核或‘太虚星魄杖’×2打造而成"));
itemMap.put("镇狱星渊斧", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "5120颗星核或‘焚星龙炎鞭’×2打造而成"));
itemMap.put("永恒星晶锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10240颗星核或‘镇狱星渊斧’×2打造而成"));
itemMap.put("混沌星祖锏", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "15000颗星核或‘永恒星晶锤’×2打造而成"));
itemMap.put("造化星龙枪", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20000颗星核或‘混沌星祖锏’×2打造而成"));
itemMap.put("无上星尊剑", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "25000颗星核或‘造化星龙枪’×2打造而成"));
itemMap.put("混元星穹刃", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "30000颗星核或‘无上星尊剑’×2打造而成"));


// 玄铁体系（锤系）神器
itemMap.put("淬体玄铁槌", new Item("神器", 0, 0, 100, 1, true, true, true, false, false, false, true, true, "5块玄铁打造而成"));
itemMap.put("裂岩陨铁锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10块玄铁或‘淬体玄铁槌’×2打造而成"));
itemMap.put("撼地千钧锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20块玄铁或‘裂岩陨铁锤’×2打造而成"));
itemMap.put("破天碎岳锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "40块玄铁或‘撼地千钧锤’×2打造而成"));
itemMap.put("碎空陨星锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "80块玄铁或‘破天碎岳锤’×2打造而成"));
itemMap.put("震岳凌霄锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "160块玄铁或‘碎空陨星锤’×2打造而成"));
itemMap.put("幽冥鬼煞锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "320块玄铁或‘震岳凌霄锤’×2打造而成"));
itemMap.put("狂澜镇海锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "640块玄铁或‘幽冥鬼煞锤’×2打造而成"));
itemMap.put("霸世吞天锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "1280块玄铁或‘狂澜镇海锤’×2打造而成"));
itemMap.put("怒焰焚天锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "2560块玄铁或‘霸世吞天锤’×2打造而成"));
itemMap.put("灭世混沌锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "5120块玄铁或‘怒焰焚天锤’×2打造而成"));
itemMap.put("永恒不朽锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10240块玄铁或‘灭世混沌锤’×2打造而成"));
itemMap.put("混沌祖神锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "15000块玄铁或‘永恒不朽锤’×2打造而成"));
itemMap.put("造化圣天锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20000块玄铁或‘混沌祖神锤’×2打造而成"));
itemMap.put("无上道尊锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "25000块玄铁或‘造化圣天锤’×2打造而成"));
itemMap.put("混元终焉锤", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "30000块玄铁或‘无上道尊锤’×2打造而成"));


//“灵藤”体系神器
itemMap.put("缠枝灵藤鞭", new Item("神器", 0, 0, 100, 1, true, true, true, false, false, false, true, true, "5段灵藤打造而成"));
itemMap.put("疾风掠影鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10段灵藤或‘缠枝灵藤鞭’×2打造而成"));
itemMap.put("幻影迷踪鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20段灵藤或‘疾风掠影鞭’×2打造而成"));
itemMap.put("流光幻影鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "40段灵藤或‘幻影迷踪鞭’×2打造而成"));
itemMap.put("幽影噬魂鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "80段灵藤或‘流光幻影鞭’×2打造而成"));
itemMap.put("青冥九霄鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "160段灵藤或‘幽影噬魂鞭’×2打造而成"));
itemMap.put("幽冥黄泉鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "320段灵藤或‘青冥九霄鞭’×2打造而成"));
itemMap.put("灵韵万法鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "640段灵藤或‘幽冥黄泉鞭’×2打造而成"));
itemMap.put("天影追魂鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "1280段灵藤或‘灵韵万法鞭’×2打造而成"));
itemMap.put("御灵控魔鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "2560段灵藤或‘天影追魂鞭’×2打造而成"));
itemMap.put("幻世浮生鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "5120段灵藤或‘御灵控魔鞭’×2打造而成"));
itemMap.put("永恒命魂鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "10240段灵藤或‘幻世浮生鞭’×2打造而成"));
itemMap.put("混沌太初鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "15000段灵藤或‘永恒命魂鞭’×2打造而成"));
itemMap.put("造化阴阳鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "20000段灵藤或‘混沌太初鞭’×2打造而成"));
itemMap.put("无上乾坤鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "25000段灵藤或‘造化阴阳鞭’×2打造而成"));
itemMap.put("混元无极鞭", new Item("神器", 0, 0, 100, 1, true, false, true, false, false, false, true, true, "30000段灵藤或‘无上乾坤鞭’×2打造而成"));

itemMap.put("小窝激活卡", new Item("特殊", 10, 66666, 1000, 1, true, false, true, true, true, false, false, false, "使用后激活宠物小窝，解锁一个位置")); //使用后可获得初始小窝位置*1
itemMap.put("小窝进阶卡", new Item("特殊", 200, 0, 1000, 99999, true, false, true, false, true, false, false, false, "使用后小窝位置+1")); //使用后小窝位置+1
itemMap.put("易容丹", new Item("特殊", 500, 0, 100, 1, true, true, true, false, true, false, false, false, "可更换宠物图片；易容效果在进化或涅槃时会失效")); //使用后，在玩家发送图片时，自动更改当前宠物图片
itemMap.put("改名卡", new Item("特殊", 500, 0, 100, 1, true, true, true, false, true, false, false, false, "用于给宠物改名")); //使用后，可改变宠物昵称
itemMap.put("涅槃", new Item("特殊", 1500, 0, 0, 1, true, true, false, false, true, false, false, false, "使用后：宠物进化阶段重置、等级重置为1、经验清零、属性继承100％"));
itemMap.put("吞噬卡", new Item("特殊", 3000, 0, 0, 1, true, true, false, false, true, false, false, false, "一种可以让您的宠物吞噬其他宠物属性的道具。只需发送“宠物吞噬+小窝序号”即可使用，被吞噬的宠物将会被消耗掉，而您的宠物将会获得对应的属性。"));
itemMap.put("终焉织者", new Item("特殊", 1000, 0, 0, 1, true, true, false, false, true, false, false, false, "来自超高维度生物的投影，可直接使用")); //特殊宠物（几乎满战力）
itemMap.put("宠物背景卡", new Item("特殊", 1000, 0, 0, 1, true, true, false, false, true, false, false, false, "可自定义「我的宠物」背景图，能挑选心仪的图片作为背景啦（推荐使用整体偏白色或灰色的图片）"));
itemMap.put("宠物定制卡", new Item("特殊", 5000, 0, 0, 1, true, true, false, false, true, false, false, false, "使用后可自定义“定制”品质宠物"));
itemMap.put("轮回玉", new Item("特殊", 45000, 0, 0, 1, true, false, false, false, false, false, false, false, "使用后宠物进化阶段重置、等级重置为1、经验保留50％、属性继承110％\n（战力≥600EE时无法使用）")); //宠物进化阶段归零、等级重置为1、经验保留50％、当前属性+当前属性*10％；效果逆天，只能上架到限时商店
itemMap.put("溯天玉", new Item("特殊", 65000, 0, 0, 1, true, false, false, false, false, false, false, false, "使用后宠物进化阶段重置、等级重置为1、经验保留100％、属性继承125％\n（战力≥600EE时无法使用）")); //宠物进化阶段归零、等级重置为1、经验保留100％、当前属性+当前属性*25％；效果逆天，上架到限时商店

itemMap.put("仙缘秘藏匣", new Item("礼包", 20, 0, 1000, 99999, true, false, false, false, true, false, false, false, "大份仙缘礼包，使用后获得15个瑶光宝箱"));  //大份礼包 1份=15个瑶光宝箱
itemMap.put("仙缘盈丰盒", new Item("礼包", 13, 0, 800, 99999, true, false, false, false, true, false, false, false, "中份仙缘礼包，使用后获得9个瑶光宝箱"));  //中份礼包 1份=9个瑶光宝箱
itemMap.put("仙缘袖珍袋", new Item("礼包", 6, 0, 500, 99999, true, false, false, false, true, false, false, false, "小份仙缘礼包，使用后获得3个瑶光宝箱"));  //小份礼包 1份=3个瑶光宝箱
itemMap.put("补偿礼包", new Item("礼包", 0, 0, 200, 100000, true, false, false, false, false, false, false, false, "补偿道具，使用后可获得丰厚补偿奖励"));  // 补偿礼包，使用后获得相关补偿

itemMap.put("战神宝箱", new Item("宝箱", 0, 0, 500, 99999, true, false, false, false, false, false, false, false, "排名第一专属宝箱")); //战榜第一专属奖励
itemMap.put("瑶光宝箱", new Item("宝箱", 0, 0, 500, 99999, true, false, false, false, false, false, false, false, "基础资源宝箱，可随机获得一些基础资源")); //随机获得各种道具

itemMap.put("初音未来", new Item("定制", 0, 0, 0, 1, true, true, false, false, false, false, false, false, "测试成员专属定制宠物，使用后可获得‘初音未来’")); // 测试阶段时某成员定制宠物
itemMap.put("风师青玄", new Item("定制", 0, 0, 0, 1, true, true, false, false, false, false, false, false, "测试成员专属定制宠物，使用后可获得‘风师青玄’")); // 测试阶段时，某成员定制宠物

import java.time.DayOfWeek;
import java.time.LocalDate;

     // ↓用于记录玩家部分【使用】指令触发的时间戳
     static Map userUsageTime = new HashMap();

import java.util.regex.Matcher;  
import java.util.regex.Pattern;     
//将当前时间弄成指定格式
//（在该玩家使用易容丹后，发送图片时以这个格式重命名该图片并保存）
public String TimeMark() {
	Date date=new Date();//此时date为当前的时间
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	//设置当前时间的格式
	return dateFormat.format(date);
}     

//定制宠物道具，使用后可以获取该宠物
String[] 定制道具={"初音未来","风师青玄","终焉织者"};

//专门处理消息为"卸下神器"的指令
public void 卸神器(Object data, String qun, String gm, String uin, String msg)
{
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    
    String 宠物名=getString(配置名称,"昵称");
    
    String 状态 = getString(配置名称, "状态");
    String 神器 = getString(配置名称, "神器");
    long 心情 = 文转(getString(配置名称, "心情"));
    long 等级 = 文转(getString(配置名称, "等级"));
    
    if(消息.startsWith("卸下神器")||消息.startsWith("卸除神器")){
          if(状态==null) {
            发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
          }else if(状态.equals("死亡")) {
            发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
          }else if(等级!=0&&心情<=15){
           发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
          }else if(神器==null||神器.equals("无")){
            发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前没有装备神器哦，无需卸下神器",true);
          }else{
             Item selectedItem = (Item) itemMap.get(神器);
             String 类型=selectedItem.getType(); //获取道具类型
             if(selectedItem==null||!类型.equals("神器")){
               putString(配置名称, "神器", "无"); //更新状态
               发送(qun,"[AtQQ="+uin+"]  "+" ["+神器+"]不是神器，已自动清除神器装备状态",true);
             }else{
               神器卸除(qun,uin,神器); //使用专门的方法来处理
             }
          }
    }
}


//专门处理开头为"查看"的指令
public void 查看(Object data, String qun, String gm, String uin, String msg)
{
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";  
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    String 宠物名=getString(配置名称,"昵称");
    
  if(消息.startsWith("查看")){
    String 选定;
    // 从消息中截取“选定”值
    if (消息.length() >= 3) {
       选定 = 消息.substring(2); // 取"查看"后的内容
    }

    // 提前记录指令触发时间
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
    if (选定 == null) { // 没有指定道具
        发送(qun, "[AtQQ="+uin+"]  "+ " 疑似指令出错！\n◆指令：查看+道具名称", true);
        return;
    }

    // 检查道具是否存在
    boolean isFound = itemMap.containsKey(选定);
    if (!isFound) { // 道具不存在，发送提示
        发送(qun, "[AtQQ="+uin+"]  "+ "〔" + 选定 + "〕不存在，请检查是否输入错误！", true);
    } else {
        //获取指定道具的相关信息
        Item selectedItem = (Item) itemMap.get(选定);
        String 类型=selectedItem.getType(); //获取道具类型
        String 简介=selectedItem.getDescription(); //获取道具简介
        String 路径=AppPath+"/目录/图片/"; //图片路径目录
        String 昵称="[AtQQ="+uin+"]  "; //该玩家的群昵称
        String 内容; //欲发送内容
        
        //判断简介是否含有图片，并且是指定类型
        if(简介.contains("[图=")){
          // 拆分图片路径和简介
          String[] parts = 简介.split("\\]");
          String link, text; //拆分后的图片路径与简介
          if (parts.length >= 2) {
            // 提取路径（去掉[图=前缀）
            link = parts[0].substring(3); // "图="占3个字符
            // 提取"]"之后的内容
            text = parts[1];
          
            // 按指定类型来拼接内容
            String result = 昵称 + "[PicUrl=" + 路径 + link + "]" + text;
            switch (类型) {
              case "神器":
              case "特殊":
              case "神秘":
              case "抽奖":
              case "充值":
                 break; // 直接使用result
              default:
                  result = 昵称+简介; // 非指定类型时使用原内容
            }
             内容 = result;
         }else{
           内容=昵称+简介+选定;
         }
        }else {
           if(类型.equals("神器")){
             //获取 该神器 的各项属性
             long[] attrs = itemAttrMap.get(选定); //获取神器对应属性
             long attack = attrs[0];       // 攻击
             long defense = attrs[1];     // 防御
             long hp = attrs[2];          // 生命
             long intelligence = attrs[3]; // 智力
             long energy = attrs[4];      // 精力
             long 生命值 = (long) Math.floor(hp / 10); //取整，方便后续计算
              long 战力提升 = (hp/10)+attack+defense+(intelligence*20);
             String 各项属性="\n•生命："+数值转(hp)+"\n•攻击："+数值转(attack)+"\n•防御："+数值转(defense)+"\n•智力："+数值转(intelligence)+"\n•精力："+数值转(energy)+"\n————————————\n◆战力预计增加"+数值转(战力提升);
             内容=昵称+" "+选定+"："+简介+各项属性;
           }else{
             内容=昵称+" "+选定+"："+简介;
           }
        }
        发送(qun,内容,true);
    }
 }
}


//专门处理开头为"转让"的指令
public void 转让(Object data, String qun, String gm, String uin, String msg)
{
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";  
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    String 宠物名=getString(配置名称,"昵称");
    
 if(消息.startsWith("转让")){
   String bagKey = qun + "/" + uin + "/我的背包";
   String bag = getString(bagKey, "道具列表");
   String 选定, 数字部分, 玩家账号;
  
    if (消息.contains("@") || 消息.contains("-")) { // 处理指定玩家的指令
    String 项目部分, 数量部分, 玩家标识;
    boolean 包含At = 消息.contains("@");
    boolean 包含Dash = 消息.contains("-");
    
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); //提前记录操作时间戳
    
    // 先判断是否为格式3或4（无数量指定，默认数量1）
    if ((包含At && !包含Dash && 消息.split("@").length == 2 && !消息.contains("#")) || 
        (包含Dash && !包含At && 消息.split("-").length == 2 && !消息.contains("#"))) {
        if (包含At) {
            String[] parts = 消息.split("@");
            项目部分 = parts[0].replace("转让", "").trim();
            数量部分 = "1"; // 默认数量1
            玩家标识 = data.mAtList.size() >= 1 ? data.mAtList.get(0) : parts[1].trim();
        } else {
            String[] parts = 消息.split("-");
            项目部分 = parts[0].replace("转让", "").trim();
            数量部分 = "1"; // 默认数量1
            玩家标识 = parts[1].trim();
        }
    } 
    // 处理格式1或2（含数量指定）
    else if ((包含At && 消息.contains("#")) || (包含Dash && 消息.contains("#"))) {
        if (包含At) {
            String[] atParts = 消息.split("@");
            String[] itemParts = atParts[0].split("#");
            if (itemParts.length != 2) {
                发送(qun, "[AtQQ="+uin+"]  "+ "指令格式错误！\n•格式1：转让+道具#数量@玩家 \n•格式2：转让+道具#数量-QQ", true);
                return;
            }
            项目部分 = itemParts[0].replace("转让", "").trim();
            数量部分 = itemParts[1].trim();
            玩家标识 = data.mAtList.size() >= 1 ? data.mAtList.get(0) : atParts[1].trim();
        } else {
            String[] dashParts = 消息.split("-");
            String[] itemParts = dashParts[0].split("#");
            if (itemParts.length != 2) {
                发送(qun, "[AtQQ="+uin+"]  "+ "指令格式错误！\n•格式1：转让+道具#数量@玩家 \n•格式2：转让+道具#数量-QQ", true);
                return;
            }
            项目部分 = itemParts[0].replace("转让", "").trim();
            数量部分 = itemParts[1].trim();
            玩家标识 = dashParts[1].trim();
        }
    } 
    // 格式错误处理
    else {
        发送(qun, "[AtQQ="+uin+"]  "+ "指令格式错误！\n•格式1：转让+道具#数量@玩家 \n•格式2：转让+道具#数量-QQ\n•格式3：转让+道具@玩家 \n•格式4：转让+道具-QQ", true);
        return;
    }
    
    // 统一校验参数有效性
    if (项目部分 == null || 项目部分.isEmpty() || 
        数量部分 == null || 数量部分.isEmpty() || !数量部分.matches("\\d+") || 
        玩家标识 == null || 玩家标识.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 参数缺少或指令格式错误！\n•格式1：转让+道具#数量@玩家 \n•格式2：转让+道具#数量-QQ\n•格式3：转让+道具@玩家 \n•格式4：转让+道具-QQ", true);
        return;
    }
    
    //赋值，方便后续判断或调用
    数字部分 = 数量部分;
    选定 = 项目部分;
    玩家账号 = 玩家标识;
}


    if (选定 == null) { // 没有指定道具
        发送(qun, "[AtQQ="+uin+"]  "+ "缺少参数！\n•格式1：转让+道具#数量@玩家 \n•格式2：转让+道具#数量-QQ\n•格式3：转让+道具@玩家 \n•格式4：转让+道具-QQ", true);
        return;
    }
    
     String 群列表 = getGroupMembersUin(qun); 
     if(群列表!=null&&!群列表.contains(玩家账号)){
       发送(qun,"[AtQQ="+uin+"]  "+" 本群没有QQ为["+玩家账号+"]的成员！\n◆请检查是否输入错误！",true);
       return;
     }

    // 检查道具是否存在
    boolean isFound = itemMap.containsKey(选定);
    if (!isFound) { // 道具不存在，发送提示
        发送(qun, "[AtQQ="+uin+"]  "+ "〔" + 选定 + "〕不存在，请检查是否输入错误！", true);
    } else {
        Item selectedItem = (Item) itemMap.get(选定);
        
        if (!selectedItem.isCanTransfer()) {
          发送(qun, "[AtQQ="+uin+"]  "+ "\n「" + 选定 + "」不能转让！！", true);
          return;
        }
        
        // 处理数字部分（去前导零、默认1）
        if (!数字部分.isEmpty()) {
            if (数字部分.length() > 1) {
                String 处理后数字 = 数字部分.replaceFirst("^0+", "");
                数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
            } else {
                数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
            }
        } else {
            数字部分 = "1"; // 无数字时默认转让1个
        }

        long 数量 = Long.parseLong(数字部分);
        long 道具 = 文转(getString(bagKey, 选定)); // 获取道具数量
        
        if(bag==null){ //背包为空
           发送(qun,"[AtQQ="+uin+"]  "+" \n\n此群没有您的相关数据！\n请发送【领新手礼包】来刷新！ ",true);
        } else if (!bag.contains(选定) || 道具 < 数量) {
          // 背包不存在此道具 或 道具数量不足
          发送(qun, "[AtQQ="+uin+"]  "+ " 你背包中的〔" + 选定 + "〕不足" + 数量 + "个！", true);
        } else if(道具>=数量) { //背包中此道具数量符合需求时
        
          //处理玩家1道具扣除逻辑（发送转让指令的玩家）
           if (减(道具, 数量) >= 1) {
               putString(bagKey, 选定, 转文(减(道具, 数量)));
           } else {
               String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
               if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
               } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
               }
           }
        
        //处理玩家2道具增加逻辑（被发送转让指令玩家所指定的账号/玩家）
        String 指定背包 = getString(qun+"/"+玩家账号+"/我的背包", "道具列表");
        if(指定背包 != null && 指定背包.contains(选定)) {
           long count = 文转(getString(qun+"/"+玩家账号+"/我的背包", 选定));
           putString(qun+"/"+玩家账号+"/我的背包", 选定, 转文(count + 数量));
         } else {
           String newBag = (指定背包 == null ? "" : 指定背包) + "、" + 选定;
           putString(qun+"/"+玩家账号+"/我的背包", "道具列表", newBag);
           putString(qun+"/"+玩家账号+"/我的背包", 选定, 转文(数量));
         }
         
        //发送消息提示
        发送(qun,"[AtQQ="+uin+"]  "+" 转让成功！\n你转让「"+选定+"」×"+数量+"给了[@"+玩家账号+"]！",true);
      }
   }
}
 
}



// 炼丹指令处理方法
public void 炼丹(Object data, String qun, String uin, String msg) {
    if (msg.startsWith("炼丹")) {
        // 筛选所有仙丹类型的道具
        List 仙丹列表 = new ArrayList();
        for (Object key : itemMap.keySet()) {
            String 道具名 = (String) key;
            Item 道具 = (Item) itemMap.get(道具名);
            if (道具.getType().equals("仙丹")) {
                仙丹列表.add(道具名);
            }
        }
        
        if (仙丹列表.isEmpty()) {
            发送(qun, "[AtQQ=" + uin + "]  暂无可用仙丹配方！", true);
            return;
        }
        
        // 随机获取1-5个仙丹
        Random random = new Random();
        int 数量 = random.nextInt(5) + 1; // 数量范围：1-5
        int 随机索引 = random.nextInt(仙丹列表.size());
        String 获得道具 = 仙丹列表.get(随机索引);
        
        // 添加到背包
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        if (bag == null || !bag.contains(获得道具)) {
            // 背包中无此道具，新增
            String newBag = (bag == null ? "" : bag + "、") + 获得道具;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, 获得道具, 转文(数量));
        } else {
            // 背包中已有此道具，累加数量
            long 现有数量 = 文转(getString(bagKey, 获得道具));
            putString(bagKey, 获得道具, 转文(现有数量 + 数量));
        }
        
        // 发送结果提示
        发送(qun, "[AtQQ=" + uin + "]  炼丹成功！\n获得「" + 获得道具 + "」×" + 数量, true);
    }
}


//专门处理消息开头为"使用"的指令
public void 使用(Object data, String qun, String gm, String uin, String msg)
{
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";  
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    String 宠物名=getString(配置名称,"昵称");


        //宠物相关属性
        String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
        String 昵称 = getString(配置名称, "昵称");
        String 名字 = getString(配置名称, "宠物名字");
        String 性别 = getString(配置名称, "性别");
        String 婚况 = getString(配置名称, "婚姻状况");
        String 属性 = getString(配置名称, "属性");
        String 状态 = getString(配置名称, "状态");
        String 神器 = getString(配置名称, "神器");
        String 级别 = getString(配置名称, "级别");
        String 天赋 = getString(配置名称, "天赋");
        String 阶段 = getString(配置名称, "阶段");
        long 等级 = 文转(getString(配置名称, "等级"));
        long 攻击 = 文转(getString(配置名称, "攻击"));
        long 防御 = 文转(getString(配置名称, "防御"));
        long 智力 = 文转(getString(配置名称, "智力"));
        long 心情 = 文转(getString(配置名称, "心情"));
        long 当前精力 = 文转(getString(配置名称, "当前精力"));
        long 精力上限 = 文转(getString(配置名称, "精力上限"));
        long 当前生命 = 文转(getString(配置名称, "当前生命"));
        long 生命上限 = 文转(getString(配置名称, "生命上限"));
        long 当前经验 = 文转(getString(配置名称, "当前经验"));
        long 所需经验 = 文转(getString(配置名称, "下级所需经验"));

// 🤔指令：使用+道具名称#数量（例如：使用小聚灵丹#9999）

/*
指令格式1：使用+道具名称#数量@玩家
指令格式2：使用+道具名称#数量-账号
指令格式3：使用+道具名称@玩家
指令格式4：使用+道具名称-账号
*/
if (消息.startsWith("使用")) {
    long 积分 = 文转(getString(qun + "/" + uin + "/我的资产", "积分"));
    long 金币 = 文转(getString(qun + "/" + uin + "/我的资产", "金币"));
    int interval = getInt("系统配置", "丹药间隔", 5000); // 使用道具间隔时长
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    String 选定, 数字部分, 玩家账号;

    //记录指令触发时间戳
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
    if (消息.contains("-")) { // 处理指定玩家的指令
    String 项目部分, 数量部分, 玩家标识;
    boolean 包含Dash = 消息.contains("-");
    
    // 先判断是否为格式3或4（无数量指定，默认数量1）
    if (包含Dash && 消息.split("-").length == 2 && !消息.contains("#")) {
            String[] parts = 消息.split("-");
            项目部分 = parts[0].replace("使用", "").trim();
            数量部分 = "1"; // 默认数量1
            玩家标识 = parts[1].trim();
    }
    // 处理格式1或2（含数量指定）
    else if (包含Dash && 消息.contains("#")) {  
            String[] dashParts = 消息.split("-");
            String[] itemParts = dashParts[0].split("#");
            if (itemParts.length != 2) {
               发送(qun, "[AtQQ="+uin+"]  "+ "指令格式错误！\n•格式1：使用+道具#数量@玩家 \n•格式2：使用+道具#数量-QQ", true);
                return;
            }
            项目部分 = itemParts[0].replace("使用", "").trim();
            数量部分 = itemParts[1].trim();
            玩家标识 = dashParts[1].trim();
    }
    // 格式错误处理
    else {
        发送(qun, "[AtQQ="+uin+"]  "+ "指令格式错误！\n•格式1：使用+道具#数量@玩家 \n•格式2：使用+道具#数量-QQ\n•格式3：使用+道具@玩家 \n•格式4：使用+道具-QQ", true);
        return;
    }
    
    // 统一校验参数有效性
    if (项目部分 == null || 项目部分.isEmpty() || 
        数量部分 == null || 数量部分.isEmpty() || !数量部分.matches("\\d+") || 
        玩家标识 == null || 玩家标识.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]  "+ "\n参数输入出错或指令格式错误！\n•格式1：使用+道具#数量@玩家 \n•格式2：使用+道具#数量-QQ\n•格式3：使用+道具@玩家 \n•格式4：使用+道具-QQ", true);
        return;
    }
    
    //赋值，方便后续判断或调用
    数字部分 = 数量部分;
    选定 = 项目部分;
    玩家账号 = 玩家标识;
    
}


// 从消息中截取“选定”值（不指定玩家时）
else if (消息.contains("#")) {
       int hashIndex = 消息.indexOf('#');
       选定 = 消息.substring(2, hashIndex); // 从"使用"后一位开始截取到#前
       String[] parts = 消息.split("#");
       数字部分 = parts[1];
    } else if (消息.length() >= 3) {
       选定 = 消息.substring(2); // 无#时取"使用"后的内容
       数字部分 = "1"; //数量固定为1
}

    // 指令间隔逻辑 （使用部分丹药后会有间隔限制）    
    TimestampWithText lastTriggerInfo = userUsageTime.get(qun + "_" + uin);
    if (lastTriggerInfo != null) {
        long lastTime = lastTriggerInfo.getTimestamp();
        long now = System.currentTimeMillis();
        long remain = (lastTime + interval) - now;
        // 还处于冷却中，并且物品类型为仙丹时
        if (remain > 0) {
         if(选定!=null){
            Item selectedItem = (Item) itemMap.get(选定);
            String 物品类型=selectedItem.getType(); //获取物品类型
            if(物品类型.equals("仙丹")){
              发送(qun, "[AtQQ="+uin+"]  "+ " 丹药冷却中，距离下次使用还需等待[" + remain / 1000 + "]秒！", true);
              return; // 直接终止后续逻辑
            }
         }
        }
    }
    
    if (选定 == null) { // 没有指定道具
        发送(qun, "[AtQQ="+uin+"]  "+ " 疑似指令出错！\n◆指令：使用+道具#数量", true);
        return;
    }
    
     String 群列表 = getGroupMembersUin(qun); 
     if(群列表!=null&&玩家账号!=null&&!群列表.contains(玩家账号)){
       发送(qun,"[AtQQ="+uin+"]  "+" 本群没有QQ为["+玩家账号+"]的成员！\n◆请检查是否输入错误！",true);
       return;
     }

     
    // 检查道具是否存在
    boolean isFound = itemMap.containsKey(选定);
    if (!isFound) { // 道具不存在，发送提示
        发送(qun, "[AtQQ="+uin+"]  "+ "〔" + 选定 + "〕不存在，请检查是否输入错误！", true);
    } else {
        Item selectedItem = (Item) itemMap.get(选定);
        
        String 物品类型=selectedItem.getType(); //获取物品类型
        
        if (!selectedItem.isCanUseOnOthers()&&玩家账号!=null) {
          发送(qun, "[AtQQ="+uin+"]  "+ "道具「" + 选定 + "」不能对他人使用！", true);
          return;
        }
        
     String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
     String 目标状态 = getString(目标配置, "状态");
     if(玩家账号!=null&&目标状态==null){
       发送(qun,"[AtQQ="+uin+"]  "+" 目标〔@"+玩家账号+"〕当前没有宠物！无法对其使用相关道具！",true);
     }
     
        
        // 处理数字部分（去前导零、默认1）
        if (!数字部分.isEmpty()) {
            if (数字部分.length() > 1) {
                String 处理后数字 = 数字部分.replaceFirst("^0+", "");
                数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
            } else {
                数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
            }
        } else {
            数字部分 = "1"; // 无数字时默认使用1个
        }

        long 数量 = Long.parseLong(数字部分);
        long 售价 = selectedItem.getSellPoints(); // 获取出售单价
        long 道具 = 文转(getString(bagKey, 选定)); // 获取道具数量
        long 最大量 = selectedItem.getMaxUseCount(); // 获取最大使用数量
        boolean canUse = selectedItem.isCanUseDirectly(); // 判断道具能否直接使用

        if (!canUse) { // 道具不能直接使用
            发送(qun, "[AtQQ="+uin+"]  "+ "「" + selectedItem.getType() + "」类型的〔" + 选定 + "〕不能直接使用！", true);
        } else {
            if(bag==null){
              发送(qun,"[AtQQ="+uin+"]  "+" \n\n此群没有您的相关数据！\n请发送【领新手礼包】来刷新！ ",true);
            }else if (!bag.contains(选定) || 道具 < 数量) {
                // 背包不存在此道具 或 道具数量不足
                发送(qun, "[AtQQ="+uin+"]  "+ " 你背包中的〔" + 选定 + "〕不足" + 数量 + "个！", true);
            } else if (数量 > 最大量) {
                // 数量大于单次最大使用数量
                发送(qun, "[AtQQ="+uin+"]  "+ " 疑似使用数量出错！\n『" + 选定 + "』最大使用数量为[" + 最大量 + "]哦", true);
            }else{ //按道具名称单独设定相关作用
            
              if(选定.equals("回春丹"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(当前生命==生命上限){
                  发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前状态良好，不需要恢复生命值！",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 宠物回血(qun, uin, 1); //恢复百分比生命（比如：1=100%, 0.25=25%）
                 // 计算恢复的生命值 取整
                 double 回血百分比= 1;
                 long 恢复值 = Math.round(生命上限 * 回血百分比);
                 double 恢复占比= (double) 恢复值/生命上限;
                 double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◇生命恢复：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）",true);
               }
              }
              
              else if(选定.equals("开心果"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(当前生命==生命上限){
                  发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前状态良好，不需要恢复生命值！",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 
                 long randomNum = 乘(10, 数量); //恢复心情
                 long 心情变化 = 心情+randomNum;
                 if(心情变化>100){
                   心情变化=100;
                 }
                 putString(配置名称, "心情", 转文(心情变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◇心情恢复：+"+心情变化,true);
               }
              }
              
              else if(选定.equals("绷带"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(当前生命==生命上限){
                  发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前状态良好，不需要恢复生命值！",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 
                 // 计算恢复的生命值 取整
                 double 回血百分比= 0.2*数量;
                 long 恢复值 = Math.round(生命上限 * 回血百分比);
                 double 恢复占比= (double) 恢复值/生命上限;
                 double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
                 宠物回血(qun, uin, 回血百分比); //恢复百分比生命（比如：1=100%）
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◇生命恢复：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）",true);
               }
              }
              
              else if(选定.equals("鸡汤"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(当前生命==生命上限){
                  发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前状态良好，不需要恢复生命值！",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 // 计算恢复的生命值 取整
                 double 回血百分比= 0.1*数量;
                 long 恢复值 = Math.round(生命上限 * 回血百分比);
                 double 恢复占比= (double) 恢复值/生命上限;
                 double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
                 宠物回血(qun, uin, 回血百分比); //恢复百分比生命（比如：1=100%）
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◇生命恢复：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）",true);
               }
              }
              
              else if(选定.equals("复活石"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(!状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物没有死亡哦，无需使用复活道具",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putString(配置名称, "状态", "正常");
                 宠物回血(qun, uin, 0.05); //回复5%生命值
                 // 计算恢复的生命值 取整
                 double 回血百分比=0.05;
                 long 恢复值 = Math.round(生命上限 * 回血百分比);
                 double 恢复占比 = (double) 恢复值/生命上限;
                 double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，将"+昵称+"成功复活!\n◇回复血量：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）",true);
               }
              }
              
              else if(选定.equals("往生花")||选定.equals("灵魂沙漏")||选定.equals("九转续命"))
              {
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(!状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物没有死亡哦，无需使用复活道具",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putString(配置名称, "状态", "正常");
                 宠物回血(qun, uin, 1); //回复100%生命值
                 // 计算恢复的生命值 取整
                 double 回血百分比= 1;
                 long 恢复值 = Math.round(生命上限 * 回血百分比);
                 double 恢复占比= (double) 恢复值/生命上限;
                 double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，将"+昵称+"成功复活!\n◇回复血量：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）",true);
               }
              }
              
              //恢复精力、生命值、心情值：100% 并解除异常状态
              else if(选定.equals("生命之源")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putString(配置名称, "状态", "正常");
                 putString(配置名称, "当前精力", 转文(精力上限));
                 putString(配置名称, "当前生命", 转文(生命上限));
                 putString(配置名称, "心情", "100");
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，您的"+昵称+"恢复至最佳状态!\n◇回复血量：+"+数值转(生命上限)+"\n◇回复精力：+"+数值转(精力上限)+"\n◇回复心情：+100",true);
               }
              }
              
              //溯天玉：进化次数、进化阶段、昵称、易容图统一重置，属性保留并额外增加25%，经验保留100%
              else if(选定.equals("溯天玉")){
                long power = 计算战力(qun, uin, 0); //战力
                String 战力=数值转(power);
                // 先处理字符串，保留数字和小数点
                String pureNumberStr = 战力.replaceAll("[^\\d.]", "");
                // 转换为double类型
                double result = Double.parseDouble(pureNumberStr);
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(战力.contains("-")){
                  发送(qun,玩家名(qun,uin)+" 您的宠物已超出计算范围啦，无法继续使用"+选定,true);
                }else if(战力.contains("EE")&&result>=600){
                  发送(qun,玩家名(qun,uin)+" 您的宠物战力已超出600EE，无法继续使用"+选定,true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 //对宠物属进行调整
                 double 增幅比例=0.25; //加成25% (溯天玉)
                 long 攻击变化=攻击*增幅比例;
                 long 防御变化=防御*增幅比例;
                 long 智力变化=智力*增幅比例;
                 long 精力变化=精力上限*增幅比例;
                 long 生命变化=生命上限*增幅比例;
                 putString(配置名称, "攻击", 转文(攻击+攻击变化));
                 putString(配置名称, "防御", 转文(防御+防御变化));
                 putString(配置名称, "智力", 转文(智力+智力变化));
                 putString(配置名称, "精力上限", 转文(精力上限+精力变化));
                 putString(配置名称, "生命上限", 转文(生命上限+生命变化));
                 putString(配置名称, "状态", "正常");
                 putString(配置名称, "等级", "1");
                 putString(配置名称, "心情", "100");
                 putString(配置名称, "进化层次", "0");
                 putString(配置名称, "昵称", 名字);
                 putString(配置名称, "阶段", "破壳期");
                 putString(配置名称, "当前经验", 转文(当前经验)); 
                 putString(配置名称, "下级所需经验", "100");
                 putString(配置名称, "是否易容", "否");
                 putString(配置名称, "上传图片", null);
               //  宠物回血(qun, uin, 1); //回复100%生命值
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n★等级为1，经验完全保留\n★属性继承125%\n★重置进化阶段\n★还原宠物昵称与图片",true);
               }
              }
              
              //轮回玉：进化次数、进化阶段、昵称、易容图统一重置，属性保留并额外增加10%，经验保留一半
              else if(选定.equals("轮回玉")){
                long power = 计算战力(qun, uin, 0); //战力
                String 战力=数值转(power);
                // 先处理字符串，保留数字和小数点
                String pureNumberStr = 战力.replaceAll("[^\\d.]", "");
                // 转换为double类型
                double result = Double.parseDouble(pureNumberStr);
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if(战力.contains("-")){
                  发送(qun,玩家名(qun,uin)+" 您的宠物已超出计算范围啦，无法继续使用"+选定,true);
                }else if(战力.contains("EE")&&result>=600){
                  发送(qun,玩家名(qun,uin)+" 您的宠物战力已超出600EE，无法继续使用"+选定,true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 //对宠物属进行调整
                 double 增幅比例=0.1; //加成10% (轮回玉)
                 long 攻击变化=攻击*增幅比例;
                 long 防御变化=防御*增幅比例;
                 long 智力变化=智力*增幅比例;
                 long 精力变化=精力上限*增幅比例;
                 long 生命变化=生命上限*增幅比例;
                 long 经验变化=除(当前经验,2);
                 putString(配置名称, "攻击", 转文(攻击+攻击变化));
                 putString(配置名称, "防御", 转文(防御+防御变化));
                 putString(配置名称, "智力", 转文(智力+智力变化));
                 putString(配置名称, "精力上限", 转文(精力上限+精力变化));
                 putString(配置名称, "生命上限", 转文(生命上限+生命变化));
                 putString(配置名称, "状态", "正常");
                 putString(配置名称, "等级", "1");
                 putString(配置名称, "心情", "100");
                 putString(配置名称, "进化层次", "0");
                 putString(配置名称, "昵称", 名字);
                 putString(配置名称, "阶段", "破壳期");
                 putString(配置名称, "当前经验", 转文(经验变化)); 
                 putString(配置名称, "下级所需经验", "100");
                 putString(配置名称, "是否易容", "否");
                 putString(配置名称, "上传图片", null);
                // 宠物回血(qun, uin, 1); //回复100%生命值
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆等级为1，经验保留50%\n◆属性继承110%\n◆重置进化阶段\n◆还原宠物昵称与图片",true);
               }
              }
              
              //涅槃：进化次数、进化阶段、昵称、易容图、经验统一重置，属性保留
              else if(选定.equals("涅槃")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 //对宠物属进行调整
                 putString(配置名称, "状态", "正常");
                 putString(配置名称, "等级", "1");
                 putString(配置名称, "心情", "100");
                 putString(配置名称, "进化层次", "0");
                 putString(配置名称, "昵称", 名字);
                 putString(配置名称, "阶段", "破壳期");
                 putString(配置名称, "当前经验", "0"); 
                 putString(配置名称, "下级所需经验", "100");
                 putString(配置名称, "是否易容", "否");
                 putString(配置名称, "上传图片", null);
                 // 宠物回血(qun, uin, 1); //回复100%生命值
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n•等级为1，清空经验值\n•属性继承100%\n•重置进化阶段\n•还原宠物昵称与图片",true);
               }
              }
              
              //使用天赋包
              else if(选定.equals("锐牙狂威天赋包")||选定.equals("灵蕴丹成天赋包")||选定.equals("疫病之源天赋包")||选定.equals("虚空壁垒天赋包")||选定.equals("厚土磐佑天赋包")||选定.equals("妙手回春天赋包")||选定.equals("妙手神偷天赋包")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 String 更新=选定.replaceAll("天赋包","");
                 putString(配置名称, "天赋", 更新); //更新天赋
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】！您的宠物觉醒了〔"+更新+"〕天赋",true);
               }
              }
              
              //使用改名卡，后续该玩家发送文本消息将被默认为宠物昵称
              else if(选定.equals("改名卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putBoolean(配置名+"状态", "宠物改名", true);
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】！\n\n★请你发送6个字以内的消息，发送的消息将会作为出战宠物的昵称～",true);
               }
              }
              
              //使用易容丹，后续该玩家发送的图片/表情将被默认为宠物图片
              else if(选定.equals("易容丹")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putBoolean(配置名+"状态", "宠物易容", true);
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】！\n\n★请发送接近正方形的图片，该图片会作为当前宠物的图片～",true);
               }
              }
              
              //使用宠物定制卡，后续该玩家按指定格式发送的消息会被拆分为宠物相关属性
              else if(选定.equals("宠物定制卡")){
                if(状态!=null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 putBoolean(配置名+"状态", "宠物定制", true);
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】！\n\n★请按以下格式发送即可定制宠物！\n\n格式：定制#昵称#属性#性别",true);
               }
              }
              
              //使用经验道具
              else if(选定.equals("五千经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=5000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("一万经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("十万经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=100000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("百万经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=1000000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("千万经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("一亿经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=100000000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("十亿经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=1000000000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("百亿经验卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000000000L;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("经验丹")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=1000000000;
                 long 变化=经验2*数量;
                 putString(配置名称, "当前经验", 转文(当前经验+变化)); 
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆经验增加：+"+数值转(变化),true);
               }
              }
              

               //使用积分道具
              else if(选定.equals("五千积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 积分2=5000;
                 long 变化=积分2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("一万积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("十万积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=100000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("百万积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=1000000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("千万积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("一亿积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=100000000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("十亿积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=1000000000;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              else if(选定.equals("百亿积分卡")){
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                 long 经验2=10000000000L;
                 long 变化=经验2*数量;
                 putString(配置名 + "/我的资产", "积分", 转文(积分+变化));
                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆积分增加：+"+数值转(变化),true);
               }
              }
              
              
//使用金币道具
else if(选定.equals("1金币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=1;
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("10金币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=10;
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("50金币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=50;
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("100金币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=100;
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("1000金币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=1000;
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆金币增加：+"+数值转(变化),true);
  }
}

//使用充值道具
else if(选定.equals("10软妹币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=2000;  //充值比例1：200（1软妹币=200金币）
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n•金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("30软妹币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=6000;  //充值比例1：200（1软妹币=200金币）
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n•金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("50软妹币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=10000;  //充值比例1：200（1软妹币=200金币）
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n•金币增加：+"+数值转(变化),true);
  }
}
else if(选定.equals("100软妹币")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 金币2=20000;  //充值比例1：200（1软妹币=200金币）
     long 变化=金币2*数量;
     putString(配置名 + "/我的资产", "金币", 转文(金币+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n•金币增加：+"+数值转(变化),true);
  }
}


//使用战力提升道具
else if(选定.equals("20攻击")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=20;
     long 变化=预设值*数量;
     putString(配置名称, "攻击", 转文(攻击+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆攻击增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化),true);
  }
}
else if(选定.equals("20防御")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=20;
     long 变化=预设值*数量;
     putString(配置名称, "防御", 转文(防御+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆防御增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化),true);
  }
}
else if(选定.equals("200生命")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=200;
     long 变化=预设值*数量;
     putString(配置名称, "生命上限", 转文(生命上限+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆生命增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化/10),true);
  }
}
else if(选定.equals("1智力")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=1;
     long 变化=预设值*数量;
     putString(配置名称, "智力", 转文(智力+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆智力增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化*20),true);
  }
}
else if(选定.equals("200攻击")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=200;
     long 变化=预设值*数量;
     putString(配置名称, "攻击", 转文(攻击+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆攻击增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化),true);
  }
}
else if(选定.equals("200防御")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=200;
     long 变化=预设值*数量;
     putString(配置名称, "防御", 转文(防御+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆防御增加：+"+数值转(变化)+"\n◆战力提升：+"+数值转(变化),true);
  }
}
else if(选定.equals("2000生命")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=2000;
     long 变化=预设值*数量;
     putString(配置名称, "生命上限", 转文(生命上限+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆生命增加：+"+数值转(变化)+"\n▲战力提升：+"+数值转(变化/10),true);
  }
}
else if(选定.equals("10智力")){
  if(状态==null) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
  }else if(状态.equals("死亡")) {
    发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
  }else{
    if (减(道具, 数量) >= 1) {
      putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
      String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString(bagKey, "道具列表", null);
        putString(bagKey, 选定, null);
      } else {
        putString(bagKey, "道具列表", 北);
        putString(bagKey, 选定, null);
      }
    }
     long 预设值=10;
     long 变化=预设值*数量;
     putString(配置名称, "智力", 转文(智力+变化));
     发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"\n◆智力增加：+"+数值转(变化)+"\n▲战力提升：+"+数值转(变化*20),true);
  }
}


 //补偿礼包      
 else if(选定.equals("补偿礼包")){
    if(状态==null) {
        发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
    }else{
          if (减(道具, 数量) >= 1) {
            putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
            String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
            if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
              putString(bagKey, "道具列表", null);
              putString(bagKey, 选定, null);
            } else {
              putString(bagKey, "道具列表", 北);
              putString(bagKey, 选定, null);
            }
          }
        
// 定义道具数组（包含名称和数量）
String[][] itemArray = {
    {"复活石,1000"},
    {"往生花,100"},
    {"灵魂沙漏,100"},
    {"九转续命丹,100"},
    {"生命之源,20"},
    {"回春丹,100"},
    {"绷带,500"},
    {"鸡汤,400"},
    {"经验丹,325000"},
    {"改名卡,3"},
    {"易容丹,3"},
    {"宠物赠送卡,10"},
    {"宠物定制卡,3"},
    {"涅槃,10"},
    {"吞噬卡,5"},
    {"金蝉,5"},
    {"10软妹币,20"},
    {"50软妹币,3"}
};
StringBuilder formattedItem = new StringBuilder();
// 遍历数组处理每个道具
for (String[] item : itemArray) {
    for (String itemInfo : item) {
        // 使用逗号拆分道具信息
        String[] splitResult = itemInfo.split(",");
        String itemName = splitResult[0];        // 道具名称
        String itemCount = splitResult[1];      // 道具数量
        
        // 构建背包存储键
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        
        // 使用StringBuilder拼接带格式的道具信息
        formattedItem.append("\n"+itemName).append("×").append(数值转(文转(itemCount)*数量));
        
        // 判断背包中是否已存在该道具
        if (bag != null && bag.contains(itemName)) {
            // 存在则累加数量
            long count = 文转(getString(bagKey, itemName));
            putString(bagKey, itemName, 转文(count + (Long.parseLong(itemCount)*数量)));
        } else {
            // 不存在则添加新道具
            String newBag = (bag == null ? "" : bag + "、") + itemName;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, itemName,  转文(Long.parseLong(itemCount)*数量));
        }
    }
}
      long 积分变化=数量*4500L;
      long 金币变化=数量*4500L;

        //领取详情（如果上面有变化，这里也要适当修改哦）
        StringBuilder result = new StringBuilder();
        result.append("获得积分："+数值转(积分变化)+"\n获得金币："+数值转(金币变化));
        result.append("\n————————\n获得道具："+formattedItem.toString());
       
        putString(配置名 + "/我的资产", "积分", 转文(积分+积分变化));
        putString(配置名 + "/我的资产", "金币", 转文(金币+金币变化));
         发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，获得以下补偿奖励：\n"+result.toString(),true);
    }
 }
 
 //战神宝箱
 else if(选定.equals("战神宝箱")){
    if(状态==null) {
        发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
    }else{
          if (减(道具, 数量) >= 1) {
            putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
            String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
            if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
              putString(bagKey, "道具列表", null);
              putString(bagKey, 选定, null);
            } else {
              putString(bagKey, "道具列表", 北);
              putString(bagKey, 选定, null);
            }
          }
        
// 定义道具数组（包含名称和数量）
String[][] itemArray = {
    {"一万经验卡,3"},
    {"往生花,2"},
    {"生命之源,1"},
    {"灵智丹,2"},
    {"凝神丹,1"},
    {"焕能丹,1"},
    {"瑶光宝箱,1"}
};
StringBuilder formattedItem = new StringBuilder();
// 遍历数组处理每个道具
for (String[] item : itemArray) {
    for (String itemInfo : item) {
        // 使用逗号拆分道具信息
        String[] splitResult = itemInfo.split(",");
        String itemName = splitResult[0];        // 道具名称
        String itemCount = splitResult[1];      // 道具数量
        
        // 构建背包存储键
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        
        // 使用StringBuilder拼接带格式的道具信息
        formattedItem.append("\n•"+itemName).append("×").append(数值转(文转(itemCount)*数量));
        
        // 判断背包中是否已存在该道具
        if (bag != null && bag.contains(itemName)) {
            // 存在则累加数量
            long count = 文转(getString(bagKey, itemName));
            putString(bagKey, itemName, 转文(count + (Long.parseLong(itemCount)*数量)));
        } else {
            // 不存在则添加新道具
            String newBag = (bag == null ? "" : bag + "、") + itemName;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, itemName,  转文(Long.parseLong(itemCount)*数量));
        }
    }
}

        //道具获得详情       
         发送(qun,"[AtQQ="+uin+"]  "+" 成功开启【"+选定+"】×"+数量+"，获得以下物品："+formattedItem.toString(),true);
    }
 }
 
 
 //使用仙缘礼包
 else if(选定.equals("仙缘秘藏匣")){
    if (状态 == null) {
        发送(qun, "[AtQQ=" + uin + "]  您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连", true);
        return;
    }

    // 扣除道具逻辑
    if (减(道具, 数量) >= 1) {
        putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
        String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
        if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
            putString(bagKey, "道具列表", null);
            putString(bagKey, 选定, null);
        } else {
            putString(bagKey, "道具列表", 北);
            putString(bagKey, 选定, null);
        }
    }
    
    //添加道具逻辑
     long 宝箱增加=数量*15;
     // 添加到背包
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        if (bag == null || !bag.contains("瑶光宝箱")) {
            // 背包中无此道具，新增
            String newBag = (bag == null ? "" : bag + "、") + "瑶光宝箱";
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, "瑶光宝箱", 转文(宝箱增加));
        } else {
            // 背包中已有此道具，累加数量
            long 现有数量 = 文转(getString(bagKey, "瑶光宝箱"));
            putString(bagKey, "瑶光宝箱", 转文(现有数量 + 宝箱增加));
        }
     发送(qun,"[AtQQ="+uin+"]  "+" 成功开启【"+选定+"】×"+数量+"，获得以下物品：\n•瑶光宝箱×"+宝箱增加,true);
 }
 else if(选定.equals("仙缘盈丰盒")){
    if (状态 == null) {
        发送(qun, "[AtQQ=" + uin + "]  您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连", true);
        return;
    }

    // 扣除道具逻辑
    if (减(道具, 数量) >= 1) {
        putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
        String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
        if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
            putString(bagKey, "道具列表", null);
            putString(bagKey, 选定, null);
        } else {
            putString(bagKey, "道具列表", 北);
            putString(bagKey, 选定, null);
        }
    }
    
     //添加道具逻辑
     long 宝箱增加=数量*8;
     // 添加到背包
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        if (bag == null || !bag.contains("瑶光宝箱")) {
            // 背包中无此道具，新增
            String newBag = (bag == null ? "" : bag + "、") + "瑶光宝箱";
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, "瑶光宝箱", 转文(宝箱增加));
        } else {
            // 背包中已有此道具，累加数量
            long 现有数量 = 文转(getString(bagKey, "瑶光宝箱"));
            putString(bagKey, "瑶光宝箱", 转文(现有数量 + 宝箱增加));
        }
     发送(qun,"[AtQQ="+uin+"]  "+" 成功开启【"+选定+"】×"+数量+"，获得以下物品：\n•瑶光宝箱×"+宝箱增加,true);
 }
 else if(选定.equals("仙缘袖珍袋")){
    if (状态 == null) {
        发送(qun, "[AtQQ=" + uin + "]  您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连", true);
        return;
    }

    // 扣除道具逻辑
    if (减(道具, 数量) >= 1) {
        putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
        String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
        if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
            putString(bagKey, "道具列表", null);
            putString(bagKey, 选定, null);
        } else {
            putString(bagKey, "道具列表", 北);
            putString(bagKey, 选定, null);
        }
    }
    
    //添加道具逻辑
     long 宝箱增加=数量*3;
     // 添加到背包
        String bagKey = qun + "/" + uin + "/我的背包";
        String bag = getString(bagKey, "道具列表");
        if (bag == null || !bag.contains("瑶光宝箱")) {
            // 背包中无此道具，新增
            String newBag = (bag == null ? "" : bag + "、") + "瑶光宝箱";
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, "瑶光宝箱", 转文(宝箱增加));
        } else {
            // 背包中已有此道具，累加数量
            long 现有数量 = 文转(getString(bagKey, "瑶光宝箱"));
            putString(bagKey, "瑶光宝箱", 转文(现有数量 + 宝箱增加));
        }
     发送(qun,"[AtQQ="+uin+"]  "+" 成功开启【"+选定+"】×"+数量+"，获得以下物品：\n•瑶光宝箱×"+宝箱增加,true);
 }
 
 // 瑶光宝箱（这个宝箱使用量越大，内容返回速度就越慢）
 else if (选定.equals("瑶光宝箱")) {
    if (状态 == null) {
        发送(qun, "[AtQQ=" + uin + "]  您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连", true);
        return;
    }

    // 扣除道具逻辑
    if (减(道具, 数量) >= 1) {
        putString(bagKey, 选定, 转文(减(道具, 数量)));
    } else {
        String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
        if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
            putString(bagKey, "道具列表", null);
            putString(bagKey, 选定, null);
        } else {
            putString(bagKey, "道具列表", 北);
            putString(bagKey, 选定, null);
        }
    }
    // 定义瑶光宝箱可开出的道具及数量范围
    //（例如：2-5，就是最少2，最多5）
    String[][] itemArray = {
        {"复活石,2-5"}, {"回春丹,1-3"}, {"小破锋丹,5-12"},
        {"小长生丹,5-12"}, {"小御体丹,5-12"}, {"小灵智丹,3-5"},
        {"长生丹,1-3"}, {"破锋丹,1-3"}, {"御体丹,1-3"},
        {"灵智丹,1-2"}, {"龙珠,3-5"}, {"星核,3-5"},
        {"玄铁,3-5"}, {"灵藤,3-5"}, {"太虚镜,1-2"},
        {"小精力药,5-10"}, {"中精力药,5-10"}, {"大精力药,5-10"},
        {"聚灵丹,3-5"}, {"小聚灵丹,10-15"}, {"焕能丹,1-5"},
        {"积分夺宝券,1-2"}, {"金币夺宝券,1-2"}, {"小焕能丹,5-10"}
    };
    int itemTypeCount = itemArray.length; // 道具种类总数

    // 记录获得的道具总和
    Map 获得道具 = new HashMap();
    Random random = new Random();
    // 计算每种道具被选中的总次数
    int[] selectCounts = new int[itemTypeCount]; // 存储每种道具被选中的次数
    for (int i = 0; i < 数量; i++) {
        int 道具索引 = random.nextInt(itemTypeCount); // 随机选择道具
        selectCounts[道具索引]++; // 对应道具的选中次数+1
    }

    // 按选中次数，批量计算每种道具的总数量
    for (int i = 0; i < itemTypeCount; i++) {
        int 选中次数 = selectCounts[i];
        if (选中次数 == 0) {
            continue; // 未被选中的道具直接跳过
        }

        // 解析道具名称和数量范围
        String[] 道具信息 = itemArray[i][0].split(",");
        String 道具名称 = 道具信息[0];
        String[] 数量范围 = 道具信息[1].split("-");
        long 最小数量 = Long.parseLong(数量范围[0]);
        long 最大数量 = Long.parseLong(数量范围[1]);
        long 范围差值 = 最大数量 - 最小数量 + 1;
        // ↑ 数量范围的总可能值（含两端）

        // 批量计算总数量：基础值（选中次数×最小数量）+ 随机浮动值
        long 总数量 = 选中次数 * 最小数量;
        if (范围差值 > 0) {
            // 生成0到（选中次数×范围差值）之间的随机数，作为浮动总和
            long bound = 选中次数 * 范围差值 + 1;
            总数量 += bound > 0 ? (long) (random.nextDouble() * bound) : 0;
        }

        // 累加至获得道具map
        获得道具.put(道具名称, 获得道具.getOrDefault(道具名称, 0L) + 总数量);
    }

// 向背包添加获得的道具（直接遍历获得道具Map，逐行处理）
// 1. 先获取当前背包的道具列表（处理null情况）
String 当前道具列表 = (bag == null) ? "" : bag;

// 2. 遍历获得的所有道具
for (Object key : 获得道具.keySet()) {
    // 强制转换键值类型（确保类型正确）
    String 道具名 = (String) key;
    Long 新增数量 = (Long) 获得道具.get(key);

    // 3. 检查该道具是否已在背包中（精确匹配）
    boolean 已存在 = false;
    if (!当前道具列表.isEmpty()) {
        String[] 现有道具数组 = 当前道具列表.split("、");
        for (String 现有道具 : 现有道具数组) {
            if (现有道具.equals(道具名)) {
                已存在 = true;
                break;
            }
        }
    }

    // 4. 处理已存在的道具：累加数量
    if (已存在) {
        // 读取原有数量（若从未存储过，默认0）
        String 原有数量Str = getString(bagKey, 道具名);
        long 原有数量 = (原有数量Str == null || "".equals(原有数量Str)) ? 0 : 文转(原有数量Str);
        // 计算新数量并更新
        long 新数量 = 原有数量 + 新增数量;
        putString(bagKey, 道具名, 转文(新数量));
    } 
    // 5. 处理新道具：添加到列表并设置数量
    else {
        // 更新道具列表（拼接新道具）
        String 新道具列表 = 当前道具列表.isEmpty() 
            ? 道具名 
            : 当前道具列表 + "、" + 道具名;
        putString(bagKey, "道具列表", 新道具列表);
        // 设置新道具的数量
        putString(bagKey, 道具名, 转文(新增数量));
        // 同步更新当前道具列表（后续道具判断用）
        当前道具列表 = 新道具列表;
    }
}

    // 发送获得道具的消息
    StringBuilder 获得信息 = new StringBuilder();
    获得信息.append("[AtQQ=" + uin + "]  成功开启【" + 选定 + "】×" + 数量 + "，获得：\n");
    for (Map.Entry entry : 获得道具.entrySet()) {
        获得信息.append("•" + entry.getKey() + "×" + entry.getValue() + "\n");
    }
    发送(qun, 获得信息.toString(), true);
}
 
  //一些丹药的使用逻辑
  else if(选定.equals("破锋丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号==null){
                  long 增加值=乘(500,数量);
                  long 属性变化=加(攻击,增加值);
                  putString(配置名称, "攻击", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●攻击增加：+"+数值转(增加值),true);
               }else if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "攻击"));
                  long 增加值=乘(500,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "攻击", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●攻击增加：+"+数值转(增加值),true);
               }
               //更新丹药使用时间戳
              userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("长生丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
         }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "生命上限"));
                  long 增加值=乘(10000,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "生命上限", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●生命增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(10000,数量);
                  long 属性变化=加(生命上限,增加值);
                  putString(配置名称, "生命上限", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●生命增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("御体丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "防御"));
                  long 增加值=乘(500,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "防御", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●防御增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(500,数量);
                  long 属性变化=加(防御,增加值);
                  putString(配置名称, "防御", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●防御增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("灵智丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "智力"));
                  long 增加值=乘(50,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "智力", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●智力增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(50,数量);
                  long 属性变化=加(智力,增加值);
                  putString(配置名称, "智力", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●智力增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("焕能丹")){
     long 增加值=乘(1000,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算需要多少颗焕能丹，精力才不会溢出
        long result = (精力上限 - 当前精力) / 1000;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("聚灵丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "当前经验"));
                  long 增加值=乘(100000,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "攻当前经验", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●经验增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(100000,数量);
                  long 属性变化=加(当前经验,增加值);
                  putString(配置名称, "当前经验", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●经验增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("小破锋丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "攻击"));
                  long 增加值=乘(50,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "攻击", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●攻击增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(50,数量);
                  long 属性变化=加(攻击,增加值);
                  putString(配置名称, "攻击", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●攻击增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("小长生丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "生命上限"));
                  long 增加值=乘(1000,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "生命上限", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●生命增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(1000,数量);
                  long 属性变化=加(生命上限,增加值);
                  putString(配置名称, "生命上限", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●生命增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("小御体丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "防御"));
                  long 增加值=乘(50,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "防御", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●防御增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(50,数量);
                  long 属性变化=加(防御,增加值);
                  putString(配置名称, "防御", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●防御增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("小灵智丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "智力"));
                  long 增加值=乘(5,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "智力", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●智力增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(5,数量);
                  long 属性变化=加(智力,增加值);
                  putString(配置名称, "智力", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●智力增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("小焕能丹")){
     long 增加值=乘(100,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算需要多少颗焕能丹，精力才不会溢出
        long result = (精力上限 - 当前精力) / 100;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
         }
              
               // 更新相关属性
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("小聚灵丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "当前经验"));
                  long 增加值=乘(10000,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "攻当前经验", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】×"+数量+"，对方触发以下效果：\n●经验增加：+"+数值转(增加值),true);
               }else{
                  long 增加值=乘(10000,数量);
                  long 属性变化=加(当前经验,增加值);
                  putString(配置名称, "当前经验", 转文(属性变化));
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●经验增加：+"+数值转(增加值),true);
               }
               
             //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("死亡丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       if(玩家账号!=null){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦",true);
       }else{
         发送(qun,"[AtQQ="+uin+"]  您的宠物已死亡，请勿重复给宠物投喂该道具哦！",true);
       }
    }else if(玩家账号!=null&&目标状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 对方宠物已死亡，无法对其投喂该道具哦！",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               if(玩家账号!=null){
                 String[] 躺板板 = {
                    "大E了，我没有闪","这波啊，这波是肉蛋葱鸡",
                    "难受啊马飞","我裂开了","别搞我心态啊",
                    "年轻人不讲武德","这好吗？这不好",
                    "我劝这位年轻人耗子尾汁","要以和为贵，不要搞窝里斗",
                    "我当时大意了，没有闪","我去，有老六啊！"
                 };
                 Random random = new Random();
                 int index = random.nextInt(躺板板.length);
                 String 随机描述 = 躺板板[index];
                  String 目标配置=qun+"/"+玩家账号+"/宠物小窝/位置_0";
                  long 目标值 = 文转(getString(目标配置, "当前经验"));
                  long 增加值=乘(10000,数量);
                  long 属性变化=加(目标值,增加值);
                  putString(目标配置, "状态", "死亡");
                  putString(目标配置, "当前生命", "0");
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功对〔@"+玩家账号+"〕使用【"+选定+"】，触发以下效果：\n●对方宠物直接死亡\n•对方宠物：“"+随机描述+"”",true);
               }else{
                  putString(配置名称, "当前生命", "0");
                  putString(配置名称, "状态", "死亡");
                  发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】，触发以下效果：\n●宠物直接死亡",true);
               }
               
               //更新丹药使用时间戳
               userUsageTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }
  }
  else if(选定.equals("凝神丹")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性
               long 增加值=乘(200,数量);
               long 属性变化=加(精力上限,增加值);
               putString(配置名称, "精力上限", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力增加：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("忘情水")){
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(!婚况.equals("已婚")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物没有结婚哦，无需使用此道具。",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               putString(配置名称,"婚姻状况","单身");
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】，触发以下效果：\n•宠物「"+宠物名+"」已恢复单身！",true);
     }
  }
  else if(选定.equals("万灵药")){
    String 状态栏=状态; //暂时记录当前状态
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(状态.equals("正常")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物状态正常，无需使用此道具！",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               putString(配置名称,"状态","正常");
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】，触发以下效果：\n•成功解除"+状态栏+"状态！\n•宠物「"+宠物名+"」状态已恢复正常",true);
     }
  }
  else if(选定.equals("性转符")){
    String 状态栏=性别; //暂时记录当前状态
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
              //更新性别变化
              String 性别变化;
              if(性别.equals("男")){
                putString(配置名称,"性别","女");
                性别变化="女";
              }else if(性别.equals("女")){
                putString(配置名称,"性别","男");
                性别变化="男";
              }else if(性别.equals("雄")){
                putString(配置名称,"性别","雌");
                性别变化="雌";
              }else if(性别.equals("雌")){
                putString(配置名称,"性别","雄");
                性别变化="雄";
              }else{
                //随机获取一项
                String[] genders = {"女","男","雌","雄"};
                Random random = new Random();
                int index = random.nextInt(genders.length);
                String randomGender = genders[index]; //随机性别
                putString(配置名称,"性别",randomGender);
                性别变化=randomGender;
              }
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】，触发以下效果：\n•宠物性别已改变！\n•从〔"+状态栏+"〕变化为〔"+性别变化+"〕",true);
     }
  }
  
  //使用精力药相关逻辑
  else if(选定.equals("小精力药")){
     long 增加值=乘(10,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     long 使用记录=文转(getString(配置名+"道具使用限制", 选定)); //已使用数量
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString(配置名+"道具使用限制", 选定+"使用时间"); // 读取使用日期
     
     //更新使用次数（当上次使用日期不等于今天时）
     if(!today.equals(lastRunDate)){
         putString(配置名+"道具使用限制", 选定, "0");
         使用记录 = 文转(getString(配置名+"道具使用限制", 选定));
      }
     
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(使用记录==2000&&today.equals(lastRunDate)) { 
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【2000】个! 剩余可用【0】",true);
       }else if((使用记录+数量)>2000&&today.equals(lastRunDate)) { 
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【2000】个! 剩余可用【"+(2000-使用记录)+"】",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / 10;
        String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
           }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               putString(配置名+"道具使用限制", 选定, 转文(使用记录+数量));
               putString(配置名+"道具使用限制", 选定+"使用时间", today); //记录使用日期
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("中精力药")){
     long 增加值=乘(20,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     long 使用记录=文转(getString(配置名+"道具使用限制", 选定)); //已使用数量
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString(配置名+"道具使用限制", 选定+"使用时间"); // 读取使用日期
     
     //更新使用次数（当上次使用日期不等于今天时）
     if(!today.equals(lastRunDate)){
         putString(配置名+"道具使用限制", 选定, "0");
         使用记录 = 文转(getString(配置名+"道具使用限制", 选定));
      }
     
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(使用记录==1000&&today.equals(lastRunDate)) { 
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【1000】个! 剩余可用【0】",true);
       }else if((使用记录+数量)>1000&&today.equals(lastRunDate)) { 
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【1000】个! 剩余可用【"+(1000-使用记录)+"】",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / 20;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
           }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               putString(配置名+"道具使用限制", 选定, 转文(使用记录+数量));
               putString(配置名+"道具使用限制", 选定+"使用时间", today); //记录使用日期
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("大精力药")){
     long 增加值=乘(40,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     long 使用记录=文转(getString(配置名+"道具使用限制", 选定)); //已使用数量
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString(配置名+"道具使用限制", 选定+"使用时间"); // 读取使用日期
     
     //更新使用次数（当上次使用日期不等于今天时）
     if(!today.equals(lastRunDate)){
         putString(配置名+"道具使用限制", 选定, "0");
         使用记录 = 文转(getString(配置名+"道具使用限制", 选定));
      }
     
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(使用记录==500&&today.equals(lastRunDate)) { 
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【500】个! 剩余可用【0】",true);
       }else if((使用记录+数量)>500&&today.equals(lastRunDate)) { 
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【500】个! 剩余可用【"+(500-使用记录)+"】",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / 40;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               putString(配置名+"道具使用限制", 选定, 转文(使用记录+数量));
               putString(配置名+"道具使用限制", 选定+"使用时间", today); //记录使用日期
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("超级精力药")){
     long 增加值=乘(200,数量); //使用后预计恢复的精力值
     long 属性变化=加(当前精力,增加值); //与当前精力相加
     long 使用记录=文转(getString(配置名+"道具使用限制", 选定)); //已使用数量
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString(配置名+"道具使用限制", 选定+"使用时间"); // 读取使用日期
     
     //更新使用次数（当上次使用日期不等于今天时）
     if(!today.equals(lastRunDate)){
         putString(配置名+"道具使用限制", 选定, "0");
         使用记录 = 文转(getString(配置名+"道具使用限制", 选定));
      }
     
          
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(使用记录==100&&today.equals(lastRunDate)) { 
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【100】个! 剩余可用【0】",true);
       }else if((使用记录+数量)>100&&today.equals(lastRunDate)) { 
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【100】个! 剩余可用【"+(100-使用记录)+"】",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / 200;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               putString(配置名+"道具使用限制", 选定, 转文(使用记录+数量));
               putString(配置名+"道具使用限制", 选定+"使用时间", today); //记录使用日期
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(增加值),true);
     }
  }
  else if(选定.equals("薄荷糖")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.01)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.01);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("蜂蜜果糖")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.05)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.05);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("坚果棒")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.10)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.10);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("能量饼干")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.20)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.20);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("巧克力棒")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.25)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.25);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("人参茶")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.30)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.30);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("活力奶昔")){
     long 总恢复精力 = (long)(数量 * (精力上限 * 0.50)); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / (精力上限*0.50);
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               putString(配置名称, "当前精力", 转文(属性变化));
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  else if(选定.equals("超级能量果")){
     long 总恢复精力 = (long)(数量 * 精力上限); //计算恢复的精力值(取整)
     long 属性变化=加(当前精力,总恢复精力); //与当前精力相加
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(当前精力>=精力上限){
         发送(qun,"[AtQQ="+uin+"]  "+" 您的〔"+宠物名+"〕当前精力已满，不需要恢复精力！",true);
     }else if(属性变化>精力上限){
        //计算精力不会溢出的量
        long result = (精力上限 - 当前精力) / 精力上限;
         String 提示内容="精力即将溢出！";
        if(result>=1){
         提示内容=提示内容+"\n•使用"+result+"个‘"+选定+"’刚好不会溢出哦";
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if(减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
              if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                  putString(bagKey, "道具列表", null);
                  putString(bagKey, 选定, null);
              } else {
                  putString(bagKey, "道具列表", 北);
                  putString(bagKey, 选定, null);
              }
          }
              
               // 更新相关属性与记录
               if(属性变化>精力上限){
                 putString(配置名称, "当前精力", 转文(精力上限));
               }else{
                putString(配置名称, "当前精力", 转文(属性变化));
               }
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n●精力恢复：+"+数值转(总恢复精力),true);
     }
  }
  
  
  //召唤怪物（增加限制）
  else if(选定.equals("信标")){
     long 使用记录=文转(getString(配置名+"道具使用限制", 选定)); //已使用数量
     long 限制数量=100; //限制该道具每日使用数量
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString(配置名+"道具使用限制", 选定+"使用时间"); // 读取使用日期
     
     //更新使用次数（当上次使用日期不等于今天时）
     if(!today.equals(lastRunDate)){
         putString(配置名+"道具使用限制", 选定, "0");
         使用记录 = 文转(getString(配置名+"道具使用限制", 选定));
      }
      
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(使用记录==限制数量&&today.equals(lastRunDate)) { 
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【"+限制数量+"】个! 剩余可用【0】",true);
     }else if((使用记录+数量)>限制数量&&today.equals(lastRunDate)) { 
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]  "+" 该道具每日限制使用【"+限制数量+"】个! 剩余可用【"+(限制数量-使用记录)+"】",true);
     }else{ 
       //使用数量和日期
       putString(配置名+"道具使用限制", 选定, 转文(使用记录+数量));
       putString(配置名+"道具使用限制", 选定+"使用时间", today); //记录使用日期
       //召唤怪物
       召唤怪物(qun,uin);
     }
  }
  
    //激活【宠物小窝】，并解锁第一个位置
    else if(选定.equals("小窝激活卡")){
      int 位置 = getInt(配置名+"我的小窝", "小窝位置", 0); //已解锁的小窝位置
      int 计数 = (位置 + 6 - 1) / 6; //计算拥有的小窝数量（小窝是6个位置为一个小窝，不满6也算作一个）
      if(状态==null) {
          发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
      }else if(位置>=1){
        发送(qun,"[AtQQ="+uin+"]  "+" 您已经有宠物小窝了，无法重复激活\n•当前已解锁"+位置+"个位置（也就是"+计数+"个小窝）",true);
      }else{
        //扣除对应数量的道具
         if (减(道具, 数量) >= 1) {
            putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
            String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
            if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
              putString(bagKey, "道具列表", null);
              putString(bagKey, 选定, null);
            } else {
              putString(bagKey, "道具列表", 北);
              putString(bagKey, 选定, null);
            }
          }
        putInt(配置名+"我的小窝", "小窝位置", 位置+1); //激活小窝并解锁一个位置
        发送(qun,"[AtQQ="+uin+"]  "+" 成功激活小窝！\n•小窝位置+1\n•指令：宠物替换+数字",true);
      }
    }
    
    //增加小窝位置
    else if(选定.equals("小窝进阶卡")){
      int 位置 = getInt(配置名+"我的小窝", "小窝位置", 0); //已解锁的小窝位置
     if(状态==null) {
          发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
      }else if(位置==0){
        发送(qun,"[AtQQ="+uin+"]  "+" 您还没有小窝哦，请先激活宠物小窝\n•指令：使用小窝激活卡",true);
      }else{
         //扣除对应数量的道具
         if (减(道具, 数量) >= 1) {
            putString(bagKey, 选定, 转文(减(道具, 数量)));
          } else {
            String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
            if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
              putString(bagKey, "道具列表", null);
              putString(bagKey, 选定, null);
            } else {
              putString(bagKey, "道具列表", 北);
              putString(bagKey, 选定, null);
            }
          }
        int 计数 = (位置+数量+6-1) / 6; //计算变化后的小窝数量
        int 小窝变化=(位置+数量);
        putInt(配置名+"我的小窝", "小窝位置",小窝变化); //增加小窝位置
        发送(qun,"[AtQQ="+uin+"]  "+"使用成功！\n•位置增加"+数量+"（拥有"+计数+"个小窝）\n•共拥有"+小窝变化+"个位置",true);
      }
    }

  // 一些属性转换道具的逻辑
  else if(选定.equals("生命转智力")){
    long 预设值=200000000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>生命上限){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•生命不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((生命上限-预设值),预设值); //保留一点生命
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=1000000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "生命上限", 转文(生命上限-预扣值));
            putString(配置名称, "智力", 转文(智力+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[200:1]\n•扣除："+数值转(预扣值)+"生命\n•增加："+数值转(增加值)+"智力",true);
     }
  }
  else if(选定.equals("生命转攻击")){
    long 预设值=2000000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>生命上限){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•生命不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((生命上限-预设值),预设值); //保留一点生命
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "生命上限", 转文(生命上限-预扣值));
            putString(配置名称, "攻击", 转文(攻击+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[200:20]\n•扣除："+数值转(预扣值)+"生命\n•增加："+数值转(增加值)+"攻击",true);
     }
  }
  else if(选定.equals("生命转防御")){
    long 预设值=2000000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>生命上限){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•生命不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((生命上限-预设值),预设值); //保留一点生命
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "生命上限", 转文(生命上限-预扣值));
            putString(配置名称, "防御", 转文(防御+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[200:20]\n•扣除："+数值转(预扣值)+"生命\n•增加："+数值转(增加值)+"防御",true);
     }
  }
  else if(选定.equals("攻击转智力")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>攻击){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•攻击不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((攻击-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=10000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "攻击", 转文(攻击-预扣值));
            putString(配置名称, "智力", 转文(智力+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:1]\n•扣除："+数值转(预扣值)+"攻击\n•增加："+数值转(增加值)+"智力",true);
     }
  }
  else if(选定.equals("攻击转防御")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>攻击){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•攻击不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((攻击-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "攻击", 转文(攻击-预扣值));
            putString(配置名称, "防御", 转文(防御+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:1]\n•扣除："+数值转(预扣值)+"攻击\n•增加："+数值转(增加值)+"防御",true);
     }
  }
  else if(选定.equals("攻击转生命")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>攻击){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•攻击不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((攻击-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=2000000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "攻击", 转文(攻击-预扣值));
            putString(配置名称, "生命上限", 转文(生命上限+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:200]\n•扣除："+数值转(预扣值)+"攻击\n•增加："+数值转(增加值)+"生命",true);
     }
  }
  else if(选定.equals("防御转智力")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>防御){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•防御不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((防御-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=10000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "防御", 转文(防御-预扣值));
            putString(配置名称, "智力", 转文(智力+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:1]\n•扣除："+数值转(预扣值)+"防御\n•增加："+数值转(增加值)+"智力",true);
     }
  }
  else if(选定.equals("防御转攻击")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>防御){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•防御不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((防御-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "防御", 转文(防御-预扣值));
            putString(配置名称, "攻击", 转文(攻击+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:1]\n•扣除："+数值转(预扣值)+"防御\n•增加："+数值转(增加值)+"攻击",true);
     }
  }
  else if(选定.equals("防御转生命")){
    long 预设值=200000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>防御){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•防御不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((防御-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=2000000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "防御", 转文(防御-预扣值));
            putString(配置名称, "生命上限", 转文(生命上限+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[20:200]\n•扣除："+数值转(预扣值)+"防御\n•增加："+数值转(增加值)+"生命",true);
     }
  }
  else if(选定.equals("智力转攻击")){
    long 预设值=10000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>智力){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•智力不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((智力-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "智力", 转文(智力-预扣值));
            putString(配置名称, "攻击", 转文(攻击+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[1:20]\n•扣除："+数值转(预扣值)+"智力\n•增加："+数值转(增加值)+"攻击",true);
     }
  }
  else if(选定.equals("智力转防御")){
    long 预设值=10000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>智力){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•智力不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((智力-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=200000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "智力", 转文(智力-预扣值));
            putString(配置名称, "防御", 转文(防御+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[1:20]\n•扣除："+数值转(预扣值)+"智力\n•增加："+数值转(增加值)+"防御",true);
     }
  }
  else if(选定.equals("智力转生命")){
    long 预设值=10000; //待转换属性
    long 预扣值=乘(预设值,数量); //计算扣除的属性
     if(状态==null) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
         发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(预扣值>智力){ // 当前属性不够进行转换
        String 提示内容=" 属性转换失败！\n•智力不足"+数值转(预扣值);
        //计算足够扣除属性的道具数量
        long result = 除((智力-预设值),预设值); //保留一些属性
        if(result>=1){
           提示内容=提示内容+"\n•预计可使用"+result+"个"+选定;
        }
         发送(qun,"[AtQQ="+uin+"]  "+提示内容,true);
     }else{
         if (减(道具, 数量) >= 1) {
             putString(bagKey, 选定, 转文(减(道具, 数量)));
         } else {
             String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
             if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                putString(bagKey, "道具列表", null);
                putString(bagKey, 选定, null);
             } else {
                putString(bagKey, "道具列表", 北);
                putString(bagKey, 选定, null);
             }
         }
             
            long 预定值=2000000; //转换属性
            long 增加值=乘(预定值,数量); //计算转换后的属性
            putString(配置名称, "智力", 转文(智力-预扣值));
            putString(配置名称, "生命上限", 转文(生命上限+增加值));
            发送(qun,"[AtQQ="+uin+"]  "+" 转换成功！\n你使用了〔"+选定+"〕×"+数量+"，触发以下效果：\n•转换比例[1:200]\n•扣除："+数值转(预扣值)+"智力\n•增加："+数值转(增加值)+"生命",true);
     }
  }
    
              //使用宠物定制道具
              else if (Arrays.asList(定制道具).contains(选定)) {
                if(状态!=null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                  
                 // 获取对应的宠物对象
                 Pet randomPet = petMap.get(选定); //宠物名称
                 String quality = randomPet.getQuality(); //品质
                 String attribute = randomPet.getAttribute(); //属性名
                 String gender = randomPet.getGender(); //性别
        
                 //为"随机"时随机获取性别
                 if ("随机".equals(gender)) 
                 {
                    String[] genders = {"雌", "雄"};
                    gender = genders[random.nextInt(genders.length)];
                 }                 
                 
                  // 获取该品质 对应 的属性
                  long[] petAttributes = petAttributeMap.get(quality);
          
                  // 获取初始属性
                  long 攻击 = petAttributes[0];
                  long 防御 = petAttributes[1];
                  long 智力 = petAttributes[2];
                  long 生命上限 = petAttributes[3];
                  long 进化次数 = petAttributes[4];
                  long 下级经验 = 100;
                  
                  String 配置名=qun+"/"+uin+"/";      
                  String 配置名称=配置名+"宠物小窝/位置_0";
                  //“位置_0”就是当前出战宠物
      
                  //存储 定制宠物 初始属性
                  putString(配置名称, "心情", "100");
                  putString(配置名称, "等级", "1");
                  putString(配置名称, "攻击", 转文(攻击));
                  putString(配置名称, "防御", 转文(防御));
                  putString(配置名称, "智力", 转文(智力));
                  putString(配置名称, "当前精力", "100");
                  putString(配置名称, "精力上限", "100");
                  putString(配置名称, "当前生命", 转文(生命上限));
                  putString(配置名称, "生命上限", 转文(生命上限));
                  putString(配置名称, "当前经验", "0");
                  putString(配置名称, "下级所需经验", 转文(下级经验));
                  putString(配置名称, "进化层次", "0");
                  putString(配置名称, "进化上限", 转文(进化次数));
                  putString(配置名称, "昵称", 选定);
                  putString(配置名称, "宠物名字", 选定);
                  putString(配置名称, "性别", gender);
                  putString(配置名称, "属性", attribute);
                  putString(配置名称, "阶段", "破壳期");
                  putString(配置名称, "级别", quality);
                  putString(配置名称, "状态", "正常");
                  putString(配置名称, "神器", "无");
                  putString(配置名称, "天赋", "无");
                  putString(配置名称, "婚姻状况", "单身");
                  putString(配置名称, "是否易容", "否");
                 
                 //发送消息
                 当前宠物(qun,uin,0);
                 发送(qun,"[AtQQ="+uin+"]  "+" 恭喜你获得〔"+quality+"〕级别的‘"+attribute+"’："+选定+" ‹蛋形态› [PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]",false);
               }
              }    
    
          /*    //使用特殊宠物道具（使用后可获得922京战力的宠物)
              else if (选定.equals("终焉织者")) {
                if(状态!=null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
                }else{
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                  
                  
                  String 配置名=qun+"/"+uin+"/";      
                  String 配置名称=配置名+"宠物小窝/位置_0"; //出战宠物位置
                  //存储 定制宠物 初始属性
                  putString(配置名称, "心情", "100");
                  putString(配置名称, "等级", "10086");
                  putString(配置名称, "攻击", "4500000000000000000");
                  putString(配置名称, "防御", "4500000000000000000");
                  putString(配置名称, "智力", "6100000000000000");
                  putString(配置名称, "当前精力", "1000000000");
                  putString(配置名称, "精力上限", "1000000000");
                  putString(配置名称, "当前生命", "900000000000000000");
                  putString(配置名称, "生命上限", "900000000000000000");
                  putString(配置名称, "当前经验", "9999999999999"); //体验版
                  putString(配置名称, "下级所需经验", "100");
                  putString(配置名称, "进化层次", "0"); //当前已进化次数
                  putString(配置名称, "进化上限", "15");
                  putString(配置名称, "昵称", 选定);
                  putString(配置名称, "宠物名字", 选定); //后续可能会用上
                  putString(配置名称, "性别", "??");
                  putString(配置名称, "属性", "不可理解");
                  putString(配置名称, "阶段", "破壳期");
                  putString(配置名称, "级别", "定制");
                  putString(配置名称, "状态", "正常");
                  putString(配置名称, "神器", "无");
                  putString(配置名称, "天赋", "无");
                  putString(配置名称, "婚姻状况", "单身");
                  putString(配置名称, "是否易容", "否");
                 
                 //发送消息
                 当前宠物(qun,uin,0);
                 发送(qun,"[AtQQ="+uin+"]  "+" 恭喜你获得"+选定+" ‹蛋形态› [PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]",false);
               }
              }    
              */
              
              //数值转换为1的道具（可将负数强制替换为1，会掉战力哦）
              else if(选定.equals("金蝉")){
                 // 从配置获取属性值
                 String lvl = getString(配置名称, "等级");
                 String atk = getString(配置名称, "攻击");
                 String def = getString(配置名称, "防御");
                 String intl = getString(配置名称, "智力");
                 String curSta = getString(配置名称, "当前精力");
                 String maxSta = getString(配置名称, "精力上限");
                 String curHP = getString(配置名称, "当前生命");
                 String maxHP = getString(配置名称, "生命上限");
                 String curExp = getString(配置名称, "当前经验");
                 String nextExp = getString(配置名称, "下级所需经验");
                 
                if(状态==null) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
                }else if(状态.equals("死亡")) {
                  发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
                }else if (checkAllAttributes(lvl, atk, def, intl, curSta, maxSta, curHP, maxHP, curExp, nextExp)) { //检查属性是否含有"-"
                  if (减(道具, 数量) >= 1) {
                    putString(bagKey, 选定, 转文(减(道具, 数量)));
                  } else {
                    String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                    if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                      putString(bagKey, "道具列表", null);
                      putString(bagKey, 选定, null);
                    } else {
                      putString(bagKey, "道具列表", 北);
                      putString(bagKey, 选定, null);
                   }
                 }
                  
                  String 变化详情=""; //暂为空字符串

                 // 处理属性值并写回配置
                 if (lvl != null && lvl.contains("-")) {
                     putString(配置名称, "等级", "1");
                     //记录具体属性变化
                     变化详情=变化详情+"\n•等级强制为1";
                 } else {
                     putString(配置名称, "等级", lvl);
                 }
                 
                 if (atk != null && atk.contains("-")) {
                     putString(配置名称, "攻击", "1");
                     变化详情=变化详情+"\n•攻击强制为1";
                 } else {
                     putString(配置名称, "攻击", atk);
                 }
                 
                 if (def != null && def.contains("-")) {
                     putString(配置名称, "防御", "1");
                     变化详情=变化详情+"\n•防御强制为1";
                 } else {
                     putString(配置名称, "防御", def);
                 }
                 
                 if (intl != null && intl.contains("-")) {
                     putString(配置名称, "智力", "1");
                     变化详情=变化详情+"\n•智力强制为1";
                 } else {
                     putString(配置名称, "智力", intl);
                 }
                 
                 if (curSta != null && curSta.contains("-")) {
                     putString(配置名称, "当前精力", "1");
                     变化详情=变化详情+"\n•当前精力强制为1";
                 } else {
                     putString(配置名称, "当前精力", curSta);
                 }
                 
                 if (maxSta != null && maxSta.contains("-")) {
                     putString(配置名称, "精力上限", "1");
                     变化详情=变化详情+"\n•精力上限强制为1";
                 } else {
                     putString(配置名称, "精力上限", maxSta);
                 }
                 
                 if (curHP != null && curHP.contains("-")) {
                     putString(配置名称, "当前生命", "1");
                     变化详情=变化详情+"\n•当前生命强制为1";
                 } else {
                     putString(配置名称, "当前生命", curHP);
                 }
                 
                 if (maxHP != null && maxHP.contains("-")) {
                     putString(配置名称, "生命上限", "1");
                     变化详情=变化详情+"\n•生命上限强制为1";
                 } else {
                     putString(配置名称, "生命上限", maxHP);
                 }
                 
                 if (curExp != null && curExp.contains("-")) {
                     putString(配置名称, "当前经验", "1");
                     变化详情=变化详情+"\n•当前经验强制为1";
                 } else {
                     putString(配置名称, "当前经验", curExp);
                 }
                 
                 if (nextExp != null && nextExp.contains("-")) {
                     putString(配置名称, "下级所需经验", "1");
                     变化详情=变化详情+"\n•经验上限强制为1";
                 } else {
                     putString(配置名称, "下级所需经验", nextExp);
                 }

                 发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】×"+数量+"，触发以下效果：\n◆将宠物负属性改为1",true);
               }else{
                  发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物当前属性值在正常范围，无需使用"+选定+"哦～",true);
               }
              }
              
        else if(选定.equals("宠物背景卡")){
           if(状态==null) {
              发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
           }else{
              if (减(道具, 数量) >= 1) {
                 putString(bagKey, 选定, 转文(减(道具, 数量)));
              } else {
                 String 北 = bag.replaceAll("(、" + 选定 + "|" + 选定 + "、|" + 选定 + ")", "");
                 if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
                   putString(bagKey, "道具列表", null);
                   putString(bagKey, 选定, null);
                 } else {
                   putString(bagKey, "道具列表", 北);
                   putString(bagKey, 选定, null);
                 }
             }
               putBoolean(配置名+"状态", "宠物背景图", true);
               发送(qun,"[AtQQ="+uin+"]  "+" 成功使用【"+选定+"】！\n\n●请发送一张图片，该图片会作为「出战宠物」的背景图片～",true);
           }       
        }
        
        //穿戴神器
        else if(selectedItem.getType().equals("神器")){
          if(状态==null) {
            发送(qun,"[AtQQ="+uin+"]  "+" 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
          }else if(状态.equals("死亡")) {
            发送(qun,"[AtQQ="+uin+"]  "+" 您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
          }else if(神器!=null&&!神器.equals("无")){
            发送(qun,"[AtQQ="+uin+"]  "+" 你的宠物已装备了神器["+神器+"]，请先卸下神器后再来装备"+选定+"\n◆指令：卸下神器",true);
          }else{
            神器穿戴(qun,uin,选定); //使用专门的方法来处理
          }
        }
              
              else{ //道具使用失败提示
               发送(qun,"[AtQQ="+uin+"]  "+" 使用失败！\n「"+选定+"」相关逻辑还没编写",true);
              }
            }
        }
    }
}
 
}



// 辅助类：存储查询配置（描述和属性名）
class QueryConfig {
    private String description; // 指令描述（如"可分解"）
    private String propertyName; // Item类中的属性名（如"canDecompose"）

    public QueryConfig(String description, String propertyName) {
        this.description = description;
        this.propertyName = propertyName;
    }

    public String getDescription() {
        return description;
    }

    public String getPropertyName() {
        return propertyName;
    }
}

// 处理道具查询相关指令的完整方法
void handleItemQuery(String qun, String uin, String msg) {

    // 定义查询指令与对应属性的映射关系
    Map queryConfigMap = new HashMap();
    // 指令格式："查道具X" -> 对应Item类的属性及描述
    queryConfigMap.put("查道具分解", new QueryConfig("*可分解*", "canDecompose"));
    queryConfigMap.put("查道具合成", new QueryConfig("*可合成*", "canCombine"));
    queryConfigMap.put("查道具使用", new QueryConfig("*可直接使用*", "canUseDirectly"));
    queryConfigMap.put("查道具转让", new QueryConfig("*可转让*", "canTransfer"));

    // 匹配用户输入的指令
    QueryConfig matchedConfig = null;
    for (Map.Entry entry : queryConfigMap.entrySet()) {
        if (msg.trim().equals(entry.getKey())) {
            matchedConfig = entry.getValue();
            break;
        }
    }

    // 若指令不匹配，直接返回（不处理）
    if (matchedConfig == null) {
        return;
    }

    // 筛选符合条件的道具
    List qualifiedItems = new ArrayList();
    // 遍历itemMap中所有道具
    for (Object key : itemMap.keySet()) {
        String itemName = (String) key; // 道具名称
        Item item = (Item) itemMap.get(itemName); // 道具对象

        // 根据配置的属性判断是否符合条件
        boolean isQualified = false;
        switch (matchedConfig.getPropertyName()) {
            case "canDecompose":
                isQualified = item.isCanDecompose();
                break;
            case "canCombine":
                isQualified = item.isCanCombine();
                break;
            case "canUseDirectly":
                isQualified = item.isCanUseDirectly();
                break;
            case "canTransfer":
                isQualified = item.isCanTransfer();
                break;
            default:
                isQualified = false; // 未知属性，默认不符合
        }

        // 符合条件则加入结果列表
        if (isQualified) {
            qualifiedItems.add(itemName);
        }
    }

    // 格式化查询结果
    StringBuilder resultMsg = new StringBuilder();
    resultMsg.append(玩家名(qun,uin)+" ("+uin+")\n"); // @发送者
    resultMsg.append("以下为"+matchedConfig.getDescription()).append("的道具列表：\n<填充>\n");

    // 若没有符合条件的道具
    if (qualifiedItems.isEmpty()) {
        resultMsg.append("暂无符合条件的道具");
    } else {
        // 按"每3个道具换行"的规则拼接
        for (int i = 0; i < qualifiedItems.size(); i++) {
            resultMsg.append(qualifiedItems.get(i)); // 添加道具名
            // 不是最后一个道具时，添加分隔符或换行
            if (i != qualifiedItems.size() - 1) {
                // 每3个道具换一行（第3、6、9...个后换行）
                if ((i + 1) % 3 == 0) {
                    resultMsg.append("\n");
                } else {
                    resultMsg.append("、"); // 否则用"、"分隔
                }
            }
        }
    }

    // 发送查询结果
    toImg(resultMsg.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/"+matchedConfig.getDescription()+"道具列表.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+matchedConfig.getDescription()+"道具列表.png]",false);
}



//——>下面方法用于在「使用金蝉」时判断宠物属性是否含有"-"符号<——

/**
 * 检查字符串是否不为null，并且含有指定字符
 * 满足条件返回true，否则返回false
 */
private static boolean checkString(String str, char targetChar) {
    return str != null && str.contains(String.valueOf(targetChar));
}

/**
 * 一次性检查该宠物所有属性参数
 * 含有"-"返回true
 */
private static boolean checkAllAttributes(String lvl, String atk, String def, String intl, String curSta, String maxSta, String curHP, String maxHP, String curExp, String nextExp) {
    return checkString(lvl, '-') ||
           checkString(atk, '-') ||
           checkString(def, '-') ||
           checkString(intl, '-') ||
           checkString(curSta, '-') ||
           checkString(maxSta, '-') ||
           checkString(curHP, '-') ||
           checkString(maxHP, '-') ||
           checkString(curExp, '-') ||
           checkString(nextExp, '-');
}