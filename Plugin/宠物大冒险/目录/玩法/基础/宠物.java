import java.lang;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


// 存储预设的宠物天赋名称及介绍
Map petTalents = new HashMap();
petTalents.put("锐牙狂威", new String[]{"被动", "神兽“白虎”的赐福，宠物战斗时会额外提升30％攻击力；"});
petTalents.put("厚土磐佑", new String[]{"被动", "神兽“玄武”的赐福，宠物战斗时会额外提高30％防御力；"});
petTalents.put("妙手神偷", new String[]{"主动", "有65％概率可从目标玩家的背包中随机窃取一种道具；\n【指令】：发动天赋@目标"});
petTalents.put("虚空壁垒", new String[]{"被动", "构筑无形的空间屏障，使宠物免疫“妙手神偷”等一切窃取类天赋的侵扰，守护物品万无一失；"});
petTalents.put("灵蕴丹成", new String[]{"主动", "有35％概率可提炼出各种各样的仙丹，每次炼丹需要消耗20万经验、50点精力；\n【指令】：炼丹"});
petTalents.put("妙手回春", new String[]{"主动", "宠物掌握神妙医术，可恢复目标40%最大生命值，并驱散负面状态；\n【指令】：发动天赋@目标"});
petTalents.put("疫病之源", new String[]{"被动", "对目标造成伤害时，有55%概率使其随机感染一种异常状态"});


class AttributePosition {
    private String attribute;
    private int x;
    private int y;

    public AttributePosition(String attribute, int x, int y) {
        this.attribute = attribute; this.x = x; this.y = y;
    }

    public String getAttribute(){return attribute;}
    public int getX(){return x;}
    public int getY(){return y;}
}

class ImagePosition {
    private int x;
    private int y;

    public ImagePosition(int x, int y) {this.x = x;this.y = y;}

    public int getX(){return x;}

    public int getY(){return y;}
}

    //【阶段名-等级限制-进化层次】的对应关系，初始阶段默认为“破壳期”
    // 例如：元素觉醒-100级-第5段进化
    public static String[] stages = {"微光初绽","灵能萌动","幻羽轻扬","星芒初聚","元素觉醒","灵韵盈身","秘能涌动","圣辉闪耀","苍穹共鸣","神念交织","宇宙同调","永恒至境","熵域归一","道纹永寂","万象归墟"};
    public static long[] levels = {2, 10, 30, 70, 100, 120, 150, 160, 180, 200, 220, 250, 290, 350, 400};
    public static long[] evolutionLevels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
 
 //获取层次对应的等级限制
 public static long getLevelByEvolutionLevel(long evolutionLevel) {
        for (int i = 0; i < evolutionLevels.length; i++) {
            if (evolutionLevels[i] == evolutionLevel) {
                return levels[i];
            }
        }
        return 0; // 如果未找到匹配的进化层次，返回 0
    }
    
// 根据进化层次与等级 返回阶段
public static String 宠物阶段(long evolutionLevel, long level) 
{
    // 遍历对应关系数组
    for (int i = 0; i < evolutionLevels.length; i++) {
        // 检查当前进化层次和等级是否满足条件
        if (evolutionLevels[i] == evolutionLevel && level >= levels[i]) {
            // 满足条件则返回对应的阶段字符串
            return stages[i];
        }
    }
    // 如果没有找到匹配的情况，返回一个默认的提示字符串
    return "??";
}

    // 宠物级别
    public static String[] quality = {"普通","精品","稀有","史诗","传说","神级","洪荒","创世","混沌","鸿蒙","虚无","永恒","定制"};
   
       //各个阶段的进化属性
        Map evolutionMap = new HashMap();
        // 进化层次，攻击，防御，智力，精力，生命
        evolutionMap.put(1, new long[]{200000, 100000, 50, 500, 50000000});
        evolutionMap.put(2, new long[]{300000, 200000, 100, 500, 50000000});
        evolutionMap.put(3, new long[]{500000, 300000, 150, 500, 50000000});
        evolutionMap.put(4, new long[]{800000, 500000, 200, 1000, 85000000});
        evolutionMap.put(5, new long[]{1000000, 750000, 250, 1000, 85000000});
        evolutionMap.put(6, new long[]{1300000, 1000000, 300, 1000, 85000000});
        evolutionMap.put(7, new long[]{1600000, 1250000, 350, 2000, 125000000});
        evolutionMap.put(8, new long[]{1900000, 1500000, 400, 2000, 125000000});
        evolutionMap.put(9, new long[]{2150000, 1750000, 450, 2000, 125000000});
        evolutionMap.put(10, new long[]{2500000, 2000000, 500, 5000, 250000000});
        evolutionMap.put(11, new long[]{2750000, 2350000, 600, 7500, 265000000});
        evolutionMap.put(12, new long[]{3200000, 2600000, 800, 9000, 280000000});
        evolutionMap.put(13, new long[]{3500000, 2850000, 1000, 11000, 300000000});
        evolutionMap.put(14, new long[]{4000000, 3000000, 1250, 14000, 350000000});
        evolutionMap.put(15, new long[]{4250000, 3250000, 1500, 16500, 400000000});

     // 各种品质宠物基础属性，进化次数       
     // 格式：品质，(攻击，防御，智力，生命，进化次数)
        Map petAttributeMap = new HashMap();
        petAttributeMap.put("普通", new long[]{20, 10, 5, 100, 4});
        petAttributeMap.put("精品", new long[]{240, 200, 10, 200, 4});
        petAttributeMap.put("稀有", new long[]{700, 350, 18, 350, 5});
        petAttributeMap.put("史诗", new long[]{1200, 600, 30, 600, 5});
        petAttributeMap.put("传说", new long[]{2000, 1000, 50, 1000, 5});
        petAttributeMap.put("神级", new long[]{7500, 7500, 80, 1750, 6});
        petAttributeMap.put("洪荒", new long[]{120000, 120000, 130, 3000, 7});
        petAttributeMap.put("创世", new long[]{600000, 600000, 200, 5000, 8});
        petAttributeMap.put("混沌", new long[]{1200000, 1200000, 300, 8000, 9});
        petAttributeMap.put("鸿蒙", new long[]{1500000, 1500000, 450, 12500, 10});
        petAttributeMap.put("虚无", new long[]{4000000, 4000000, 700, 20000, 11});
        petAttributeMap.put("永恒", new long[]{6000000, 6000000, 1000, 30000, 12});
        petAttributeMap.put("定制", new long[]{7500000, 7500000, 2000, 30000, 15});
       
        
class Pet {
    private String attribute; // 属性名
    private String gender; // 性别
    private String quality; // 品质

    public Pet(String attribute, String gender, String quality) {
        this.attribute = attribute;
        this.gender = gender; 
        this.quality = quality;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getGender() {
        return gender;
    }

    public String getQuality() {
        return quality;
    }
}

// 将petMap的声明和初始化放到一起
// 格式：角色名，{属性名，性别，品质}
private static Map petMap = new HashMap() {{
    //《原神》相关角色
    put("派蒙", new Pet("最好的伙伴", "女", "永恒"));
    put("空", new Pet("异界旅行者", "男", "永恒"));
    put("荧", new Pet("异界旅行者", "女", "永恒"));
    put("安柏", new Pet("西风侦察骑士", "女", "洪荒"));
    put("凯亚", new Pet("西风骑兵队长", "男", "洪荒"));
    put("丽莎", new Pet("图书管理员", "女", "洪荒"));
    put("琴", new Pet("西风代理团长", "女", "创世"));
    put("香菱", new Pet("万民堂大厨", "女", "洪荒"));
    put("枫原万叶", new Pet("浪客少年", "男", "创世"));
    put("托马", new Pet("家政达人", "男", "洪荒"));
    put("北斗", new Pet("南十字船长", "女", "洪荒"));
    put("凝光", new Pet("璃月天权星", "女", "洪荒"));
    put("重云", new Pet("驱邪方士", "男", "洪荒"));
    put("行秋", new Pet("古华派弟子", "男", "洪荒"));
    put("钟离", new Pet("往生堂·客卿", "男", "虚无"));
    put("温迪", new Pet("蒙德吟游诗人", "男", "虚无"));
    put("纳西妲", new Pet("小吉祥草王", "女", "虚无")); 
    put("玛薇卡", new Pet("纳塔的骄阳", "未知", "虚无")); 
    put("那维莱特", new Pet("谕告的潮音", "男", "虚无")); 
    put("雷电影", new Pet("天之鸣神", "女", "虚无")); 
    put("阿蕾奇诺", new Pet("孤瞑厄月", "女", "创世")); 
    put("八重神子", new Pet("鸣神大社宫司", "女", "创世"));
    put("七七", new Pet("救苦度厄真君", "女", "创世"));
    put("芭芭拉", new Pet("蒙德偶像", "女", "洪荒"));
    put("迪奥娜", new Pet("猫尾调酒师", "女", "洪荒"));
    put("可莉", new Pet("逃跑的太阳", "女", "创世"));
    put("莫娜", new Pet("星象占卜师", "女", "创世"));
    put("阿贝多", new Pet("白垩之子", "男", "创世"));
    put("甘雨", new Pet("璃月秘书", "女", "创世"));
    put("魈", new Pet("降魔大圣", "男", "创世"));
    put("胡桃", new Pet("往生堂·堂主", "女", "创世"));
    put("早柚", new Pet("忍里之貉", "女", "洪荒"));
    put("宵宫", new Pet("烟火艺术家", "女", "创世"));
    put("珊瑚宫心海", new Pet("现人神巫女", "女", "创世"));
    put("九条裟罗", new Pet("天领奉行将领", "女", "洪荒"));
    put("五郎", new Pet("海祇军大将", "男", "洪荒"));
    put("荒泷一斗", new Pet("荒坂豪快", "男", "创世"));
    put("久岐忍", new Pet("荒泷派参谋", "女", "洪荒")); 
    put("鹿野院平藏", new Pet("天才侦探", "男", "洪荒"));
    put("夜兰", new Pet("兰生幽谷", "女", "创世"));
    put("神里绫华", new Pet("白露霜华", "女", "创世"));
    put("申鹤", new Pet("孤辰茕怀", "女", "创世"));
    put("云堇", new Pet("红毹婵娟", "女", "创世"));
    put("烟绯", new Pet("智明无邪", "女", "洪荒"));
    put("戴因斯雷布", new Pet("拾枝者", "男", "混沌"));
    put("达达利亚", new Pet("愚人众执行官", "男", "创世"));
    put("女士", new Pet("炎之魔女", "女", "创世"));
    put("散兵", new Pet("稻妻人偶", "男", "创世"));
    
    //《崩坏：星穹铁道》相关
    put("星", new Pet("银河球棒侠", "女", "永恒"));
    put("穹", new Pet("银河球棒侠", "男", "永恒"));
    put("三月七", new Pet("失忆少女", "女", "创世"));
    put("白露", new Pet("衔药龙女", "女", "创世"));
    put("丹恒", new Pet("冷面小青龙", "男", "洪荒"));
    put("克拉拉", new Pet("猩红兔子", "女", "洪荒"));
    put("虎克", new Pet("鼹鼠党老大", "女", "洪荒"));
    put("青雀", new Pet("牌技高手", "女", "洪荒"));

    // 《宝可梦》相关宠物
    put("妙蛙种子", new Pet("草系", "随机", "普通"));
    put("杰尼龟", new Pet("水系", "随机", "普通"));
    put("小火龙", new Pet("火系", "随机", "普通"));
    put("绿毛虫", new Pet("虫系", "随机", "普通"));
    put("独角虫", new Pet("虫毒系", "随机", "普通"));
    put("波波", new Pet("一般飞行系", "随机", "普通"));
    put("小拉达", new Pet("一般系", "随机", "普通"));
    put("阿柏蛇", new Pet("毒系", "随机", "普通"));
    put("穿山鼠", new Pet("地面系", "随机", "普通"));
    put("皮皮", new Pet("妖精系", "随机", "普通"));
    put("六尾", new Pet("火系", "随机", "普通"));
    put("胖丁", new Pet("一般妖精系", "随机", "普通"));
    put("超音蝠", new Pet("毒飞行系", "随机", "普通"));
    put("走路草", new Pet("草毒系", "随机", "普通"));
    put("派拉斯", new Pet("草虫系", "随机", "普通"));
    put("毛球", new Pet("虫草系", "随机", "普通"));
    put("地鼠", new Pet("地面系", "随机", "普通"));
    put("喵喵", new Pet("一般系", "随机", "普通"));
    put("可达鸭", new Pet("水系", "随机", "普通"));
    put("猴怪", new Pet("格斗系", "随机", "普通"));
    put("蚊香蝌蚪", new Pet("水系", "随机", "普通"));
    put("凯西", new Pet("超能力系", "随机", "普通"));
    put("腕力", new Pet("格斗系", "随机", "普通"));
    put("喇叭芽", new Pet("草毒系", "随机", "普通"));
    put("玛瑙水母", new Pet("水毒系", "随机", "普通"));
    put("小火马", new Pet("火系", "随机", "普通"));
    put("呆呆兽", new Pet("一般水系", "随机", "普通"));
    put("小磁怪", new Pet("电钢系", "随机", "普通"));
    put("嘟嘟", new Pet("一般飞行系", "随机", "普通"));
    put("小海狮", new Pet("冰水系", "随机", "普通"));
    put("臭泥", new Pet("毒系", "随机", "普通"));
    put("大舌贝", new Pet("水系", "随机", "普通"));
    put("大钳蟹", new Pet("水系", "随机", "普通"));
    put("雷电球", new Pet("电系", "随机", "普通"));
    put("蛋蛋", new Pet("草超能力系", "随机", "普通"));
    put("卡拉卡拉", new Pet("地面系", "随机", "普通"));
    put("飞腿郎", new Pet("格斗系", "随机", "普通"));
    put("快拳郎", new Pet("格斗系", "随机", "普通"));
    put("大舌头", new Pet("一般系", "随机", "普通"));
    put("瓦斯弹", new Pet("毒系", "随机", "普通"));
    put("吉利蛋", new Pet("一般系", "随机", "普通"));
    put("蔓藤怪", new Pet("草系", "随机", "普通"));
    put("墨海马", new Pet("水系", "随机", "普通"));
    put("角金鱼", new Pet("水系", "随机", "普通"));
    put("海星星", new Pet("水系", "随机", "普通"));
    put("伊布", new Pet("一般系", "随机", "普通"));
    put("多边兽", new Pet("一般系", "随机", "普通"));
    put("菊石兽", new Pet("岩石水系", "随机", "普通"));
    put("化石盔", new Pet("岩石水系", "随机", "普通"));
    put("巴大蝶", new Pet("虫系", "随机", "精品"));
    put("大针蜂", new Pet("虫毒系", "随机", "精品"));
    put("比比鸟", new Pet("一般飞行系", "随机", "精品"));
    put("拉达", new Pet("一般系", "随机", "精品"));
    put("烈雀", new Pet("恶飞行系", "随机", "精品"));
    put("阿柏怪", new Pet("毒系", "随机", "精品"));
    put("雷丘", new Pet("电系", "随机", "精品"));
    put("穿山王", new Pet("地面系", "随机", "精品"));
    put("皮可西", new Pet("妖精系", "随机", "精品"));
    put("九尾", new Pet("火系", "随机", "精品"));
    put("胖可丁", new Pet("一般妖精系", "随机", "精品"));
    put("大嘴蝠", new Pet("毒飞行系", "随机", "精品"));
    put("臭臭花", new Pet("草毒系", "随机", "精品"));
    put("霸王花", new Pet("草毒系", "随机", "精品"));
    put("派拉斯特", new Pet("草虫系", "随机", "精品"));
    put("摩鲁蛾", new Pet("虫草系", "随机", "精品"));
    put("三地鼠", new Pet("地面系", "随机", "精品"));
    put("猫老大", new Pet("一般系", "随机", "精品"));
    put("哥达鸭", new Pet("水系", "随机", "精品"));
    put("火爆猴", new Pet("格斗系", "随机", "精品"));
    put("蚊香君", new Pet("水系", "随机", "精品"));
    put("勇吉拉", new Pet("超能力系", "随机", "精品"));
    put("豪力", new Pet("格斗系", "随机", "精品"));
    put("口呆花", new Pet("草毒系", "随机", "精品"));
    put("大食花", new Pet("草毒系", "随机", "精品"));
    put("毒刺水母", new Pet("水毒系", "随机", "精品"));
    put("隆隆石", new Pet("岩石地面系", "随机", "精品"));
    put("隆隆岩", new Pet("岩石地面系", "随机", "精品"));
    put("烈焰马", new Pet("火系", "随机", "精品"));
    put("呆壳兽", new Pet("一般水系", "随机", "精品"));
    put("三合一磁怪", new Pet("电钢系", "随机", "精品"));
    put("大葱鸭", new Pet("一般飞行系", "随机", "精品"));
    put("嘟嘟利", new Pet("一般飞行系", "随机", "精品"));
    put("白海狮", new Pet("冰水系", "随机", "精品"));
    put("臭臭泥", new Pet("毒系", "随机", "精品"));
    put("刺甲贝", new Pet("水冰系", "随机", "精品"));
    put("鬼斯通", new Pet("幽灵毒系", "随机", "精品"));
    put("素利普", new Pet("超能力系", "随机", "精品"));
    put("素利拍", new Pet("超能力系", "随机", "精品"));
    put("巨钳蟹", new Pet("水系", "随机", "精品"));
    put("顽皮雷弹", new Pet("电系", "随机", "精品"));
    put("椰蛋树", new Pet("草超能力系", "随机", "精品"));
    put("嘎啦嘎啦", new Pet("地面系", "随机", "精品"));
    put("双弹瓦斯", new Pet("毒系", "随机", "精品"));
    put("袋兽", new Pet("一般系", "随机", "精品"));
    put("海刺龙", new Pet("水系", "随机", "精品"));
    put("金鱼王", new Pet("水系", "随机", "精品"));
    put("宝石海星", new Pet("水系", "随机", "精品"));
    put("水伊布", new Pet("水系", "随机", "精品"));
    put("雷伊布", new Pet("电系", "随机", "精品"));
    put("火伊布", new Pet("火系", "随机", "精品"));
    put("多刺菊石兽", new Pet("岩石水系", "随机", "精品"));
    put("镰刀盔", new Pet("岩石水系", "随机", "精品"));
    put("大比鸟", new Pet("一般飞行系", "随机", "稀有"));
    put("大嘴雀", new Pet("恶飞行系", "随机", "稀有"));
    put("尼多后", new Pet("毒地面系", "雌", "稀有"));
    put("尼多王", new Pet("毒地面系", "雄", "稀有"));
    put("卡蒂狗", new Pet("火炎系", "随机", "稀有"));
    put("风速狗", new Pet("火炎系", "随机", "稀有"));
    put("蚊香泳士", new Pet("水系格斗系", "随机", "稀有"));
    put("胡地", new Pet("超能力系", "随机", "稀有"));
    put("怪力", new Pet("格斗系", "随机", "稀有"));
    put("大岩蛇", new Pet("岩石地面系", "随机", "稀有"));
    put("耿鬼", new Pet("幽灵毒系", "随机", "稀有"));
    put("铁甲犀牛", new Pet("地面钢系", "随机", "稀有"));
    put("铁甲暴龙", new Pet("地面钢系", "随机", "稀有"));
    put("飞天螳螂", new Pet("虫飞行系", "随机", "稀有"));
    put("电击兽", new Pet("电系", "随机", "稀有"));
    put("鸭嘴火兽", new Pet("火系", "随机", "稀有"));
    put("大甲", new Pet("虫系", "随机", "稀有"));
    put("肯泰罗", new Pet("一般系", "随机", "稀有"));
    put("乘龙", new Pet("水冰系", "随机", "稀有"));
    put("百变怪", new Pet("一般系", "随机", "稀有"));
    put("魔墙人偶", new Pet("超能力系", "随机", "史诗"));
    put("迷唇姐", new Pet("冰超能力系", "随机", "史诗"));
    put("急冻鸟", new Pet("冰飞行系", "随机", "传说"));
    put("闪电鸟", new Pet("电飞行系", "随机", "传说"));
    put("火焰鸟", new Pet("火飞行系", "随机", "传说"));
    put("迷你龙", new Pet("龙系", "随机", "传说"));
    put("哈克龙", new Pet("龙系", "随机", "传说"));
    put("快龙", new Pet("龙飞行系", "随机", "传说"));
    put("超梦", new Pet("超能力系", "随机", "神级"));
    put("梦幻", new Pet("超能力系", "随机", "神级"));
    put("卡比兽", new Pet("一般系", "随机", "史诗"));
    put("菊草叶", new Pet("草系", "随机", "普通"));
    put("小锯鳄", new Pet("水系", "随机", "普通"));
    put("火球鼠", new Pet("火系", "随机", "普通"));
    put("尾立", new Pet("一般系", "随机", "普通"));
    put("猫头夜鹰", new Pet("一般飞行系", "随机", "普通"));
    put("木守宫", new Pet("草系", "随机", "普通"));
    put("水跃鱼", new Pet("水系", "随机", "普通"));
    put("火稚鸡", new Pet("火系", "随机", "普通"));
    put("拉鲁拉丝", new Pet("超能力妖精系", "随机", "普通"));
    put("蛇纹熊", new Pet("一般系", "随机", "普通"));
    put("草苗龟", new Pet("草系", "随机", "普通"));
    put("小火焰猴", new Pet("火系", "随机", "普通"));
    put("波加曼", new Pet("水系", "随机", "普通"));
    put("姆克儿", new Pet("一般飞行系", "随机", "普通"));
    put("帕奇利兹", new Pet("电系", "随机", "普通"));
    put("藤藤蛇", new Pet("草系", "随机", "普通"));
    put("暖暖猪", new Pet("火系", "随机", "普通"));
    put("水水獭", new Pet("水系", "随机", "普通"));
    put("豆豆鸽", new Pet("一般飞行系", "随机", "普通"));
    put("扒手猫", new Pet("一般系", "随机", "普通"));
    put("哈力栗", new Pet("草系", "随机", "普通"));
    put("火狐狸", new Pet("火系", "随机", "普通"));
    put("呱呱泡蛙", new Pet("水系", "随机", "普通"));
    put("嗡蝠", new Pet("飞行毒系", "随机", "普通"));
    
     // 《王者荣耀》角色
    put("鲁班七号", new Pet("机关小天才", "男", "精品"));
    put("不知火舞", new Pet("扶桑女忍者", "女", "史诗"));
    put("铠", new Pet("长城守卫军", "男", "传说"));
    put("貂蝉", new Pet("绝世舞姬", "女", "稀有"));

     // 《明日方舟》角色
    put("阿米娅", new Pet("罗德岛领袖", "女", "精品"));
    put("银灰", new Pet("喀兰之主", "男", "传说"));
    put("能天使", new Pet("拉特兰天使", "女", "稀有"));
    put("陈", new Pet("龙门警司", "女", "史诗"));
    put("斯卡蒂", new Pet("深海猎人", "女", "神级"));
    put("红豆", new Pet("活泼干员", "女", "普通"));

    // 《海贼王》角色
    put("路飞", new Pet("草帽船长", "男", "创世"));
    put("索隆", new Pet("三刀流剑士", "男", "洪荒"));
    put("娜美", new Pet("天才航海士", "女", "稀有"));
    put("乌索普", new Pet("狙击之王", "男", "精品"));
    put("山治", new Pet("黑足厨师", "男", "史诗"));
    put("乔巴", new Pet("蓝波球医生", "随机", "普通"));

    // 《火影忍者》角色
    put("漩涡鸣人", new Pet("七代目火影", "男", "创世"));
    put("宇智波佐助", new Pet("复仇者忍者", "男", "创世"));
    put("春野樱", new Pet("医疗忍者", "女", "洪荒"));
    put("旗木卡卡西", new Pet("拷贝忍者", "男", "洪荒"));
    put("我爱罗", new Pet("砂隐风影", "男", "洪荒"));
    put("日向雏田", new Pet("温柔日向女", "女", "洪荒"));

    // 《刀剑神域》角色
    put("桐人", new Pet("黑色剑士", "男", "创世"));
    put("亚丝娜", new Pet("闪光剑士", "女", "创世"));
    put("莉法", new Pet("风精灵使", "女", "洪荒"));
    put("西莉卡", new Pet("龙使少女", "女", "洪荒"));
    put("尤吉欧", new Pet("青蔷薇剑士", "男", "洪荒"));

    // 《约会大作战》角色
    put("时崎狂三", new Pet("刻刻帝使者", "女", "创世"));
    put("鸢一折纸", new Pet("白色恶魔", "女", "史诗"));
    put("四糸乃", new Pet("可爱精灵", "女", "稀有"));

    // 《Fate/go》角色
    put("阿尔托莉雅", new Pet("亚瑟王女版", "女", "创世"));
    put("卫宫士郎", new Pet("正义伙伴", "男", "洪荒"));
    put("吉尔伽美什", new Pet("英雄王大人", "男", "创世"));

    // 假面骑士系列的角色
    put("空我", new Pet("全能战士", "男", "洪荒"));
    put("亚极陀", new Pet("大地战士", "男", "洪荒"));
    put("龙骑", new Pet("契约战士", "男", "洪荒"));
    put("555", new Pet("加速战士", "男", "洪荒"));
    put("剑", new Pet("卡牌战士", "男", "洪荒"));
    put("甲斗", new Pet("kabuto战士", "男", "洪荒"));
    put("电王", new Pet("时间战士", "男", "洪荒"));
    put("帝骑", new Pet("世界破坏者", "男", "洪荒"));
    put("巫骑", new Pet("魔法战士", "男", "洪荒"));
    put("铠武", new Pet("水果战士", "男", "洪荒"));
    put("驰骑", new Pet("赛车战士", "男", "洪荒"));
    put("灵骑", new Pet("幽灵战士", "男", "洪荒"));
    put("艾克赛德", new Pet("游戏战士", "男", "洪荒"));
    put("创骑", new Pet("天才战士", "男", "洪荒"));
    put("时王", new Pet("未来的王", "男", "洪荒"));
    put("逢魔时王", new Pet("时间王者", "男", "永恒"));
    
    // 一些奥特曼角色
    put("赛迦", new Pet("宇宙奇迹", "男", "永恒"));
    put("雷杰多", new Pet("等离子究极体", "男", "永恒"));
    put("诺亚", new Pet("传说之光", "男", "永恒"));
    put("奥特之王", new Pet("光之国至尊", "男", "永恒"));
    put("银河", new Pet("未来之光", "男", "洪荒"));
    put("艾克斯", new Pet("数据战士", "男", "洪荒"));
    put("欧布", new Pet("重光战士", "男", "洪荒"));
    put("捷德", new Pet("极恶之子", "男", "洪荒"));
    put("泽塔", new Pet("阿尔法装甲", "男", "洪荒"));
    put("迪迦", new Pet("光之巨人", "男", "混沌"));
    
    //一些乱七八糟的
    put("初音未来", new Pet("虚拟歌姬", "女", "定制"));
    put("风师青玄", new Pet("风师娘娘", "男", "定制"));
    put("终焉织者", new Pet("不可理解", "??", "定制"));
}};

//存储玩家们“砸蛋十连”的相关信息
private static Map playerPetMap = new HashMap();

/*// 砸蛋十连
public static void drawTen(String groupId, String account) {
    playerPetMap.remove(groupId + "_" + account);
    List petNames = new ArrayList();
    // 过滤掉品质为“定制”的宠物，仅保留其他品质
     for (Object key : petMap.keySet()) {
        Pet pet = (Pet) petMap.get(key);
        if (!"定制".equals(pet.getQuality())) { // 跳过“定制”品质的宠物
           petNames.add(key);
        }
     }
    Collections.shuffle(petNames);
    Map petListForAccount = new HashMap();
    for (int i = 0; i < 10; i++) {
        String petName = petNames.get(i);
        Pet pet = petMap.get(petName);
        // 格式：[序号-品质-属性名-宠物名字]
        String format = "[" + (i + 1) + "]-[" + pet.getQuality() + "]-" + pet.getAttribute() + "-" + petName;
        petListForAccount.put(i + 1, format);
    }
    playerPetMap.put(groupId + "_" + account, petListForAccount);
}*/

    // 砸蛋十连方法
    public static void drawTen(String groupId, String account) {
        // 移除该玩家之前的抽取结果
        playerPetMap.remove(groupId + "_" + account);
        
        // 过滤“定制”品质的宠物，收集符合条件的宠物名称
        List petNames = new ArrayList();
        for (String key : petMap.keySet()) {
            Pet pet = petMap.get(key);
            if (!"定制".equals(pet.getQuality())) {
                petNames.add(key);
            }
        }
        
        // 检查是否有符合条件的宠物，无则直接返回
        int petCount = petNames.size();
        if (petCount == 0) {
            return;
        }
        
        // 随机选取指定数量的宠物（允许重复）
        Map petListForAccount = new HashMap();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(petCount);
            // 根据随机索引获取宠物名称
            String petName = petNames.get(randomIndex);
            Pet pet = petMap.get(petName);
            // 格式化结果字符串
            String format = "[" + (i + 1) + "]-[" + pet.getQuality() + "]-" + pet.getAttribute() + "-" + petName;
            petListForAccount.put(i + 1, format);
        }
        
        // 存储该玩家的本次抽取结果
        playerPetMap.put(groupId + "_" + account, petListForAccount);
    }

// 砸蛋百连
public static void drawHundred(String groupId, String account) {
        // 移除该玩家之前的抽取结果
        playerPetMap.remove(groupId + "_" + account);
        
        // 过滤“定制”品质的宠物，收集符合条件的宠物名称
        List petNames = new ArrayList();
        for (String key : petMap.keySet()) {
            Pet pet = petMap.get(key);
            if (!"定制".equals(pet.getQuality())) {
                petNames.add(key);
            }
        }
        
        // 检查是否有符合条件的宠物，无则直接返回
        int petCount = petNames.size();
        if (petCount == 0) {
            return;
        }
        
        // 随机选取指定数量的宠物（允许重复）
        Map petListForAccount = new HashMap();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomIndex = random.nextInt(petCount);
            // 根据随机索引获取宠物名称
            String petName = petNames.get(randomIndex);
            Pet pet = petMap.get(petName);
            // 格式化结果字符串
            String format = "[" + (i + 1) + "]-[" + pet.getQuality() + "]-" + pet.getAttribute() + "-" + petName;
            petListForAccount.put(i + 1, format);
        }
        
        // 存储该玩家的本次抽取结果
        playerPetMap.put(groupId + "_" + account, petListForAccount);
}

// 砸蛋千连
public static void drawThousand(String groupId, String account) {
        // 移除该玩家之前的抽取结果
        playerPetMap.remove(groupId + "_" + account);
        
        // 过滤“定制”品质的宠物，收集符合条件的宠物名称
        List petNames = new ArrayList();
        for (String key : petMap.keySet()) {
            Pet pet = petMap.get(key);
            if (!"定制".equals(pet.getQuality())) {
                petNames.add(key);
            }
        }
        
        // 检查是否有符合条件的宠物，无则直接返回
        int petCount = petNames.size();
        if (petCount == 0) {
            return;
        }
        
        // 随机选取指定数量的宠物（允许重复）
        Map petListForAccount = new HashMap();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int randomIndex = random.nextInt(petCount);
            // 根据随机索引获取宠物名称
            String petName = petNames.get(randomIndex);
            Pet pet = petMap.get(petName);
            // 格式化结果字符串
            String format = "[" + (i + 1) + "]-[" + pet.getQuality() + "]-" + pet.getAttribute() + "-" + petName;
            petListForAccount.put(i + 1, format);
        }
        
        // 存储该玩家的本次抽取结果
        playerPetMap.put(groupId + "_" + account, petListForAccount);
}

// 砸蛋万连
public static void drawTenThousand(String groupId, String account) {
        // 移除该玩家之前的抽取结果
        playerPetMap.remove(groupId + "_" + account);
        
        // 过滤“定制”品质的宠物，收集符合条件的宠物名称
        List petNames = new ArrayList();
        for (String key : petMap.keySet()) {
            Pet pet = petMap.get(key);
            if (!"定制".equals(pet.getQuality())) {
                petNames.add(key);
            }
        }
        
        // 检查是否有符合条件的宠物，无则直接返回
        int petCount = petNames.size();
        if (petCount == 0) {
            return;
        }
        
        // 随机选取指定数量的宠物（允许重复）
        Map petListForAccount = new HashMap();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int randomIndex = random.nextInt(petCount);
            // 根据随机索引获取宠物名称
            String petName = petNames.get(randomIndex);
            Pet pet = petMap.get(petName);
            // 格式化结果字符串
            String format = "[" + (i + 1) + "]-[" + pet.getQuality() + "]-" + pet.getAttribute() + "-" + petName;
            petListForAccount.put(i + 1, format);
        }
        
        // 存储该玩家的本次抽取结果
        playerPetMap.put(groupId + "_" + account, petListForAccount);
}

// 通过序号获取宠物信息，并处理标注特定品质（一般只用于显示）
public static String getPetInfoByIndex2(String account, int index) {
    Map petList = playerPetMap.get(account);
    if (petList != null) {
        String result = (String) petList.get(index);
        if (result != null) {
            // 定义需要处理的品质关键词
            String[] qualityKeywords = {"永恒", "定制"};
            boolean needProcess = false;
            for (String keyword : qualityKeywords) {
                if (result.contains(keyword)) {
                    needProcess = true;
                    break;
                }
            }
            if (needProcess) {
                // 按格式拆分字符串 [序号]-[品质]-属性-宠物名字
                String[] parts = result.split("-");
                if (parts.length >= 4) {
                    // 处理序号和宠物名字
                    String newIndex = "*" + parts[0];
                    String newPetName = parts[3] + "*";
                    // 重新拼接字符串
                    return newIndex + "-" + parts[1] + "-" + parts[2] + "-" + newPetName;
                }
            }
            return result;
        }
    }
    return null;
}

// 通过序号获取宠物信息
public static String getPetInfoByIndex(String account, int index) {
    Map petList = playerPetMap.get(account);
    if (petList != null) {
        return petList.get(index);
    }
    return null;
}

// 获取玩家选择的宠物信息，并返回分割后的信息（会删除选择列表）
public static String selectPet(String groupId, String account, int index) {
    String petInfo = getPetInfoByIndex(groupId + "_" + account, index);
    if (petInfo != null) {
        playerPetMap.remove(groupId + "_" + account); //删除该玩家的选择列表
        return petInfo;
    }
    return "无效的选择序号";
}


// 获取玩家预览的宠物信息，并返回分割后的信息
public static String selectPet2(String groupId, String account, int index) {
    String petInfo = getPetInfoByIndex(groupId + "_" + account, index);
    if (petInfo != null) {
        return petInfo;
    }
    return "无效的选择序号";
}


    // 选择宠物方法
    public static void 选择宠物(String qun, String uin, String attribute, String gender, String quality, String Name)
    {
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
      
       //存储 宠物 初始属性
        putString(配置名称, "心情", "100");
        putString(配置名称, "等级", "1");
        putString(配置名称, "攻击", 转文(攻击));
        putString(配置名称, "防御", 转文(防御));
        putString(配置名称, "智力", 转文(智力));
        putString(配置名称, "当前精力", "100");
        putString(配置名称, "精力上限", "100");
        putString(配置名称, "当前生命", 转文(生命上限));
        putString(配置名称, "生命上限", 转文(生命上限));
        putString(配置名称, "当前经验", "100");
        putString(配置名称, "下级所需经验", 转文(下级经验));
        putString(配置名称, "进化层次", "0"); //当前已进化次数
        putString(配置名称, "进化上限", 转文(进化次数));
        putString(配置名称, "昵称", Name);
        putString(配置名称, "宠物名字", Name);
        putString(配置名称, "性别", gender);
        putString(配置名称, "属性", attribute);
        putString(配置名称, "阶段", "破壳期");
        putString(配置名称, "级别", quality);
        putString(配置名称, "状态", "正常");
        putString(配置名称, "神器", "无");
        putString(配置名称, "天赋", "无");
        putString(配置名称, "闭关", "无");
        putString(配置名称, "婚姻状况", "单身");
        putString(配置名称, "是否易容", "否");
    }
    
    
//砸蛋十连时，预览宠物属性
public void 预览(String qun, String uin, String attribute, String gender, String quality, String Name)
{
     String roleImagePath = AppPath + "/目录/图片/其他/默认.png"; 
     String BgImagePath = AppPath + "/目录/图片/其他/宠物背景.png";
     String outputPath = AppPath + "/缓存/宠物/" + uin + "_宠物预览图.png";
     
     //根据对应情况调整宠物图片
     if(!petMap.containsKey(Name)&&quality.equals("定制")){
         String petImagePath = AppPath + "/目录/图片/宠物/定制/" + Name + ".jpg";
         File file = new File(petImagePath);
         if (file.exists()) {
           roleImagePath = petImagePath;
         } else {
           roleImagePath = AppPath + "/目录/图片/其他/默认.png";
         }
     } else {
       String petImagePath = AppPath + "/目录/图片/宠物/" + Name + ".jpg";
       File file = new File(petImagePath);
       if (file.exists()) {
           roleImagePath = petImagePath;
       } else {
          roleImagePath = AppPath + "/目录/图片/其他/默认.png";
       }
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

        String 配置名 = qun + "/" + uin + "/";
        String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_"+number;

        //宠物属性图_右侧显示内容
        String[] attributes = {
                "•心情：★★★★★" ,
                "•精力：100 / 100",
                "•血量：" + 数值转(生命上限) + " / " + 数值转(生命上限),
                "•经验：0 / 100"
        };

        Map attributeMap = new HashMap();
        int baseX = 20;  
        int baseY = 384; 
        int yIncrement = 35; 
        for (int i = 0; i < attributes.length; i++) {
            int x = baseX;
            int y = baseY + i * yIncrement;
            attributeMap.put(attributes[i], new AttributePosition(attributes[i], x, y));
        }
       
       String petImagePath =AppPath+"/目录/图片/宠物/"+Name+".jpg";
       File file = new File(petImagePath);
        if (file.exists()) {
            roleImagePath = petImagePath;
        }

        long 战力 = 生命上限/10+攻击+防御+智力*20;

        //宠物属性图_左侧显示内容
        String[] attributes2 = {
                "•等级：Lv 1",
                "•昵称：" + Name,
                "•性别：" + gender,
                "•阶段：破壳期",
                "•属性：" + attribute,
                "•级别：" + quality,
                "•状态：正常",
                "•神器：无",
                "•天赋：无",
                "•战力：" + 数值转(战力),
                "•智力：" + 数值转(智力),
                "•攻击：" + 数值转(攻击),
                "•防御：" + 数值转(防御)
        };

        Map attributeMap2 = new HashMap();
        int base2X = 384; 
        int base2Y = 42;  
        int yIncrement2 = 36;
        for (int i = 0; i < attributes2.length; i++) {
            int x = base2X;
            int y = base2Y + i * yIncrement2;
            attributeMap2.put(attributes2[i], new AttributePosition(attributes2[i], x, y));
        }
        
      Main main = new Main();
      ImagePosition imagePosObj = Main.extractImagePosition();
      main.toImg2(attributeMap, attributeMap2, imagePosObj, roleImagePath, "#000000", BgImagePath, outputPath, qun, uin, "无");
}


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.*;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class Main {
    public static ImagePosition extractImagePosition() {
        return new ImagePosition(6,4);
    }

    // 加载图片
    private Bitmap loadImage(String path) {
        return BitmapFactory.decodeFile(path);
    }
    
// 绘制宠物属性图片方法
public void toImg2(Map attributes, Map attributes2, ImagePosition imagePos, String roleImagePath, String color, String backgroundImagePath, String outputPath, String qun, String uin, String number) {


    // 加载背景图并缩放
    Bitmap backgroundBmp = loadImage(backgroundImagePath);
    if (backgroundBmp == null) {
        Toast("宠物背景图未加载！");
        return;
    }
    Bitmap scaledBmp = Bitmap.createScaledBitmap(backgroundBmp, 650, 500, true);
    
    // 创建画布
    Bitmap resultBmp = Bitmap.createBitmap(650, 500, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(resultBmp);
    canvas.drawBitmap(scaledBmp, 0, 0, null);
    backgroundBmp.recycle();
    scaledBmp.recycle();

    // 绘制文案底图
    Bitmap bgBitmap = loadImage(AppPath + "/目录/图片/其他/文案底图.png");
    if (bgBitmap != null) {
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, 650, 500, false);
        canvas.drawBitmap(bgBitmap, 0, 0, null);
    }

    // 初始化基础画笔
    Paint pt = new Paint();
    pt.setTextSize(26);
    pt.setTypeface(Typeface.SERIF);
    pt.setAntiAlias(true);
    Typeface boldTypeface = Typeface.create(pt.getTypeface(), Typeface.BOLD);
    pt.setTypeface(boldTypeface);
  

    // 绘制角色图
    Bitmap roleBitmap = loadImage(roleImagePath);
    if (roleBitmap != null) {
        roleBitmap = Bitmap.createScaledBitmap(roleBitmap, 350, 350, false);
        canvas.drawBitmap(roleBitmap, imagePos.getX(), imagePos.getY(), null);
    }

    // -------------------- 处理 attributes 集合 --------------------
    for (Map.Entry entry : attributes.entrySet()) {
        String attribute = (String) entry.getKey();
        AttributePosition position = (AttributePosition) entry.getValue();
        float x = position.getX();
        float y = position.getY();

        // 创建新画笔（避免状态污染）
        Paint currentPaint = new Paint(pt);
        currentPaint.setColor(Color.parseColor(color));
        
         // 添加阴影（阴影半径、偏移量可调整）
        Paint shadowPaint = new Paint();
        shadowPaint.setTextSize(26);
        shadowPaint.setTypeface(Typeface.SERIF);
        shadowPaint.setAntiAlias(true);
        Typeface boldTypeface = Typeface.create(shadowPaint.getTypeface(), Typeface.BOLD);
        shadowPaint.setTypeface(boldTypeface);
        shadowPaint.setShadowLayer(2, -2, -2, Color.parseColor("#6500ffd4"));
        
        Paint shadowPaint2 = new Paint();
        shadowPaint2.setTextSize(26);
        shadowPaint2.setTypeface(Typeface.SERIF);
        shadowPaint2.setAntiAlias(true);
        Typeface boldTypeface = Typeface.create(shadowPaint2.getTypeface(), Typeface.BOLD);
        shadowPaint2.setTypeface(boldTypeface);
        shadowPaint2.setShadowLayer(2, 2, 2, Color.parseColor("#65ff0000"));

        // **关键逻辑：判断是否包含"战力："并添加阴影**
        if (attribute.contains("战力：")) {
            currentPaint.setShadowLayer(1f, 1f, 1f, Color.parseColor("#ff0000")); // 阴影参数：半径、x偏移、y偏移、颜色
        }

        canvas.drawText(attribute, x, y, shadowPaint);  // 先画阴影  
        canvas.drawText(attribute, x, y, shadowPaint2);  // 先画阴影  
        canvas.drawText(attribute, x, y, currentPaint);    // 再画文本主体  


        // 可选：绘制后清除阴影（因每次新建Paint，实际可不执行）
        // currentPaint.clearShadowLayer();
    }

    // -------------------- 处理 attributes2 集合 --------------------
    for (Map.Entry entry2 : attributes2.entrySet()) {
        String attribute = (String) entry2.getKey();
        AttributePosition position = (AttributePosition) entry2.getValue();
        float x = position.getX();
        float y = position.getY();

        Paint currentPaint = new Paint(pt);
        currentPaint.setColor(Color.parseColor(color));
        
        // 添加阴影（阴影半径、偏移量可调整）
        Paint shadowPaint = new Paint();
        shadowPaint.setTextSize(26);
        shadowPaint.setTypeface(Typeface.SERIF);
        shadowPaint.setAntiAlias(true);
        Typeface boldTypeface = Typeface.create(shadowPaint.getTypeface(), Typeface.BOLD);
        shadowPaint.setTypeface(boldTypeface);
        shadowPaint.setShadowLayer(2, -2, -2, Color.parseColor("#6500ffd4"));
        
        Paint shadowPaint2 = new Paint();
        shadowPaint2.setTextSize(26);
        shadowPaint2.setTypeface(Typeface.SERIF);
        shadowPaint2.setAntiAlias(true);
        Typeface boldTypeface = Typeface.create(shadowPaint2.getTypeface(), Typeface.BOLD);
        shadowPaint2.setTypeface(boldTypeface);
        shadowPaint2.setShadowLayer(2, 2, 2, Color.parseColor("#65ff0000"));

        if (attribute.contains("战力：")) {
            currentPaint.setShadowLayer(1f, 1f, 1f, Color.parseColor("#ff0000"));
        }
        canvas.drawText(attribute, x, y, shadowPaint);  // 先画阴影  
        canvas.drawText(attribute, x, y, shadowPaint2);  // 先画阴影  
        canvas.drawText(attribute, x, y, currentPaint);    // 再画文本主体  
    }
    
    //根据宠物状态额外添加一个图层到最上面
    if(!number.equals("无")){ //number参数不等于“无”时
      String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_"+number;
      String 状态 = getString(配置名称, "状态");
      if(状态!=null&&!状态.equals("正常")){
        String 涂层=AppPath + "/目录/图片/其他/"+状态+"涂层.png";
        // 根据状态绘制涂层
        Bitmap TcBitmap = loadImage(涂层);
        if (TcBitmap != null) {
          TcBitmap = Bitmap.createScaledBitmap(TcBitmap, 650, 500, false);
          canvas.drawBitmap(TcBitmap, 0, 0, null);
        }
      }
    }

    // 绘制日期和水印
    Paint datePaint = new Paint(pt);
    datePaint.setTextSize(pt.getTextSize() * 0.5f);
    datePaint.setColor(Color.parseColor("#30000000"));
    String dateText = getTodayDate(1);
    canvas.drawText(dateText, 576, 492, datePaint);

    Paint byPaint = new Paint(pt);
    byPaint.setTextSize(pt.getTextSize() * 0.5f);
    byPaint.setColor(Color.parseColor("#35000000"));
    //获取脚本作者并绘制到指定位置
    String byText = readprop(AppPath + "/info.prop", "author");
    canvas.drawText("By：" + byText, 255, 370, byPaint);
    
    // 保存图片并释放资源
    saveBitmap(resultBmp, outputPath);
    resultBmp.recycle();
    if (bgBitmap != null) bgBitmap.recycle();
}
    
    // 保存Bitmap到文件
    private void saveBitmap(Bitmap bmp, String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        if (!f.exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fs = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fs);
            fs.flush();
            fs.close();
        } catch (Exception e) {
            Toast("未能保存图片");
        }
    }
}

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

    //宠物初始阶段才会调用
    public static void 蛋形态(String eggShellPath, String petPath, String outputPath)
    {
        // 解码图片
        Bitmap petBitmap = BitmapFactory.decodeFile(petPath);
        Bitmap eggShellBitmap = BitmapFactory.decodeFile(eggShellPath);

        // 缩放petBitmap到75*75
        petBitmap = Bitmap.createScaledBitmap(petBitmap, 75, 75, true);
        // 缩放eggShellBitmap到300*300
        eggShellBitmap = Bitmap.createScaledBitmap(eggShellBitmap, 300, 300, true);

        // 创建一个透明背景的300*300的Bitmap
        Bitmap transparentBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transparentBitmap);

        // 将petBitmap贴在透明背景bitmap的x,y坐标上
        canvas.drawBitmap(petBitmap, 173, 156, null);
        // 将eggShellBitmap贴在最上面
        canvas.drawBitmap(eggShellBitmap, 0, 0, null);

        File outputFile = new File(outputPath);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //宠物进化阶段在"破壳期"才会调用
    public static void 破壳图(String eggShellPath, String petPath, String outputPath)
    {
        // 解码图片
        Bitmap petBitmap = BitmapFactory.decodeFile(petPath);
        Bitmap eggShellBitmap = BitmapFactory.decodeFile(eggShellPath);

        // 缩放petBitmap到190*190
        petBitmap = Bitmap.createScaledBitmap(petBitmap, 190, 190, true);
        // 缩放eggShellBitmap到300*300
        eggShellBitmap = Bitmap.createScaledBitmap(eggShellBitmap, 300, 300, true);

        // 创建一个透明背景的300*300的Bitmap
        Bitmap transparentBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transparentBitmap);

        // 将petBitmap贴在透明背景bitmap的x,y坐标上
        canvas.drawBitmap(petBitmap, 55, 30, null);
        // 将eggShellBitmap贴在最上面
        canvas.drawBitmap(eggShellBitmap, 0, 0, null);

        File outputFile = new File(outputPath);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
//绘制宠物属性图片（number为零时就是当前出战宠物）
public void 当前宠物(String qun, String uin, long number)
{
     String roleImagePath = AppPath + "/目录/图片/其他/默认.png"; 
     String BgImagePath = AppPath + "/目录/图片/其他/宠物背景.png";
     String outputPath = AppPath + "/缓存/宠物/" + uin + "_宠物图.png";
       String 配置名 = qun + "/" + uin + "/";
        String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_"+number;
        long 当前精力 = 文转(getString(配置名称, "当前精力"));
        long 精力上限 = 文转(getString(配置名称, "精力上限"));
        long 当前生命 = 文转(getString(配置名称, "当前生命"));
        long 生命上限 = 文转(getString(配置名称, "生命上限"));
        long 当前经验 = 文转(getString(配置名称, "当前经验"));
        long 下级所需经验 = 文转(getString(配置名称, "下级所需经验"));

        //宠物属性图_右侧显示内容
        String[] attributes = {
                "•心情：" + 心情转换(qun, uin, number),
                "•精力：" + 数值转(当前精力) + " / " + 数值转(精力上限),
                "•血量：" + 数值转(当前生命) + " / " + 数值转(生命上限),
                "•经验：" + 数值转(当前经验) + " / " + 数值转(下级所需经验)
        };

        Map attributeMap = new HashMap();
        int baseX = 20; 
        int baseY = 384;  
        int yIncrement = 35; 
        for (int i = 0; i < attributes.length; i++) {
            int x = baseX;
            int y = baseY + i * yIncrement;
            attributeMap.put(attributes[i], new AttributePosition(attributes[i], x, y));
        }
        
        String 昵称 = getString(配置名称, "昵称");
        String 名字 = getString(配置名称, "宠物名字");
       String petImagePath =AppPath+"/目录/图片/宠物/"+名字+".jpg";
       File file = new File(petImagePath);
        if (file.exists()) {
            roleImagePath = petImagePath;
        }        
        
               
       //特殊宠物“终焉织者”的背景图
       //（未自定义背景图时，优先使用该宠物的背景图）
        String 特殊 = AppPath + "/目录/图片/其他/终焉织者背景.jpg";
        if(名字.equals("终焉织者")){
           File file2 = new File(特殊);
            if (file2.exists()) {
               BgImagePath=特殊;
            }       
         }        
         
        
      //宠物背景
      //（已使用"宠物背景卡"自定义背景图后，优先使用自定义背景图）
      String 路径 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/宠物背景图.png";
      File bgfile = new File(路径);
        if (bgfile.exists()) {
         // 路径文件存在
         BgImagePath=路径;
        }

        String 性别 = getString(配置名称, "性别");
        String 婚况 = getString(配置名称, "婚姻状况");
        String 属性 = getString(配置名称, "属性");
        String 状态 = getString(配置名称, "状态");
        long 等级 = 文转(getString(配置名称, "等级"));
        long 攻击 = 文转(getString(配置名称, "攻击"));
        long 防御 = 文转(getString(配置名称, "防御"));
        long 智力 = 文转(getString(配置名称, "智力"));
        String 神器 = getString(配置名称, "神器");
        String 级别 = getString(配置名称, "级别");
        String 天赋 = getString(配置名称, "天赋");
        long 战力 = 计算战力(qun, uin, number);
        String 阶段 = getString(配置名称, "阶段");
        
        //这是使用“宠物定制卡”后自定义的宠物的图片
        if(级别.equals("定制")){
         String 定制图 = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";
          File file = new File(定制图);
          if (file.exists()) {
            roleImagePath = 定制图;
          }
        }
        
       //这是使用“易容丹”后的宠物图片
        String 易容 = getString(配置名称, "是否易容");
        String 上传图 = getString(配置名称, "上传图片");
        if(易容.equals("是")){
         roleImagePath = AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png";
        }
        
        //宠物在“破壳期”与“微光初绽”时，宠物图片会发生变化（小彩蛋(bushi)）
        if ("破壳期".equals(阶段)) {
            // 调用图片生成方法
            String 形态 = AppPath + "/目录/图片/其他/蛋形态.png";
            String 宠物图 = roleImagePath;
            String 保存位置 = AppPath + "/缓存/宠物/"+uin+"_宠物蛋形态.png";
            蛋形态(形态, 宠物图, 保存位置);
            roleImagePath = 保存位置;
       }
       else if ("微光初绽".equals(阶段)) {
            // 调用图片生成方法
            String 形态 = AppPath + "/目录/图片/其他/破壳.png";
            String 宠物图 = roleImagePath;
            String 保存位置 = AppPath + "/缓存/宠物/"+uin+"_宠物破壳图.png";
            破壳图(形态, 宠物图, 保存位置);
            roleImagePath = 保存位置;
       }

        //宠物属性图_左侧显示内容
        String[] attributes2 = {
                "•等级：Lv " + 数值转(等级),
                "•昵称：" + 昵称,
                "•性别：" + 性别 + "（"+婚况+"）",
                "•阶段：" + 阶段,
                "•属性：" + 属性,
                "•级别：" + 级别,
                "•状态：" + 状态,
                "•神器：" + 神器,
                "•天赋：" + 天赋,
                "•战力：" + 数值转(战力),
                "•智力：" + 数值转(智力),
                "•攻击：" + 数值转(攻击),
                "•防御：" + 数值转(防御)
        };

        Map attributeMap2 = new HashMap();
        int base2X = 384; 
        int base2Y = 42;  
        int yIncrement2 = 36;
        for (int i = 0; i < attributes2.length; i++) {
            int x = base2X;
            int y = base2Y + i * yIncrement2;
            attributeMap2.put(attributes2[i], new AttributePosition(attributes2[i], x, y));
        }
        
      String xh=String.valueOf(number);
      Main main = new Main();
      ImagePosition imagePosObj = Main.extractImagePosition();
      main.toImg2(attributeMap, attributeMap2, imagePosObj, roleImagePath, "#000000", BgImagePath, outputPath, qun, uin, xh);
}    


public void 宠物小窝(String qun, String uin, int number) {
    // 解码预设图片
    Bitmap BGimg = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/小窝背景.png");
    Bitmap BGsy = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/小窝底图.png");
    Bitmap WJtx = BitmapFactory.decodeFile(AppPath + "/缓存/头像/" + uin + ".png");

    // 缩放预设图片为750*750
    BGimg = Bitmap.createScaledBitmap(BGimg, 750, 750, true);
    BGsy = Bitmap.createScaledBitmap(BGsy, 750, 750, true);
    WJtx = Bitmap.createScaledBitmap(WJtx, 120, 120, true);

    // 创建一个750*750的白色背景的Bitmap
    Bitmap whiteBackground = Bitmap.createBitmap(750, 750, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);

    // 绘制预设图片到whiteBackground
    canvas.drawBitmap(BGimg, 0, 0, null);
    canvas.drawBitmap(BGsy, 0, 0, null);
    canvas.drawBitmap(WJtx, 134, 15, null);

    // 绘制玩家账号
    Paint paint = new Paint();
    paint.setTextSize(13);
    paint.setColor(Color.WHITE);
    paint.setShadowLayer(1, 2, 2, Color.BLACK);
    canvas.drawText("玩家：" + uin, 132, 146, paint);

    // 绘制6个小窝序号
    Paint numberPaint = new Paint();
    numberPaint.setTextSize(25);
    numberPaint.setColor(Color.BLACK);
    numberPaint.setAntiAlias(true);
    numberPaint.setTypeface(Typeface.DEFAULT_BOLD); //字体加粗

    // 第一个阴影的Paint
    Paint shadowPaint1 = new Paint(numberPaint);
    shadowPaint1.setShadowLayer(1, 3, 2, Color.WHITE);

    // 第二个阴影的Paint
    Paint shadowPaint2 = new Paint(numberPaint);
    shadowPaint2.setShadowLayer(1, -2, -3, Color.WHITE);

    // 计算小窝起始序号
    int startNumber = (number - 1) * 6 + 1;

    // 坐标数组，存放每个序号的x,y坐标
    int[] xCoords = {47, 284, 522, 47, 284, 522};
    int[] yCoords = {179, 179, 179, 472, 472, 472};

    for (int i = 0; i < 6; i++) {
        int currentNumber = startNumber + i;
        // 绘制第一个阴影
        canvas.drawText(String.valueOf(currentNumber), xCoords[i], yCoords[i], shadowPaint1);
        // 绘制第二个阴影
        canvas.drawText(String.valueOf(currentNumber), xCoords[i], yCoords[i], shadowPaint2);
        // 绘制原始文本
        canvas.drawText(String.valueOf(currentNumber), xCoords[i], yCoords[i], numberPaint);
    }

    //用于绘制 宠物昵称、宠物等级和宠物品质
    Paint TextPaint = new Paint();
    TextPaint.setTextSize(28);
    TextPaint.setColor(Color.BLACK);
    TextPaint.setAntiAlias(true);
    
    // 用于绘制品质渐变背景的Paint（初始化Shader）
    Paint backgroundPaint = new Paint();
    backgroundPaint.setAntiAlias(true); // 抗锯齿

    // 用于存储坐标的数组，分别对应x坐标和y坐标
    int[] xCoordsForPet = {30, 268, 505, 30, 268, 505};
    int[] yPetCoords = {204, 204, 204, 498, 498, 498};
    int[] yNameCoords = {198, 198, 198, 491, 491, 491};
    int[] yLevelCoords = {441, 441, 441, 734, 734, 734};
    // 定义每个小窝的文本显示宽度（与宠物图片宽度一致）
    int textAreaWidth = 214;

    //判断该序号是否存在宠物并进行绘制
    String 对象 = qun + "/" + uin + "/宠物小窝/位置_";
    for (int i = 0; i < 6; i++) {
        long currentLevel = 文转(getString(对象 + (startNumber + i), "等级"));
        if (currentLevel >= 1) {
            String 配置名称 = 对象 + (startNumber + i);
            String 宠物图片;
            String 昵称 = getString(配置名称, "昵称");
            String 品质 = getString(配置名称, "级别");
            String 名字 = getString(配置名称, "宠物名字");
            String 易容 = getString(配置名称, "是否易容");
            String 上传图 = getString(配置名称, "上传图片");
            long 等级 = 文转(getString(配置名称, "等级"));

            if (易容.equals("是")) {
                宠物图片 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/" + qun + "/" + 上传图 + ".png";
            } else if(!petMap.containsKey(名字)&&品质.equals("定制")){
                String petImagePath = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            }else {
                String petImagePath = AppPath + "/目录/图片/宠物/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            }

            Bitmap pet = BitmapFactory.decodeFile(宠物图片);
            pet = Bitmap.createScaledBitmap(pet, 214, 214, true);
            canvas.drawBitmap(pet, xCoordsForPet[i], yPetCoords[i], null);

            // 计算昵称的居中X坐标
            float nicknameWidth = TextPaint.measureText(昵称);
            float nicknameX = xCoordsForPet[i] + (textAreaWidth - nicknameWidth) / 2;

            // 计算等级的居中X坐标
            String levelText = "Lv." + 等级;
            float levelWidth = TextPaint.measureText(levelText);
            float levelX = xCoordsForPet[i] + (textAreaWidth - levelWidth) / 2;

            // 绘制昵称和等级（阴影+文本）
            TextPaint.setShadowLayer(2, -2, -2, Color.parseColor("#6500ffd4"));
            canvas.drawText(昵称, nicknameX, yNameCoords[i], TextPaint);
            canvas.drawText(levelText, levelX, yLevelCoords[i], TextPaint);

            TextPaint.setShadowLayer(2, 2, 2, Color.parseColor("#65ff0000"));
            canvas.drawText(昵称, nicknameX, yNameCoords[i], TextPaint);
            canvas.drawText(levelText, levelX, yLevelCoords[i], TextPaint);

            // 绘制宠物品质及渐变背景
            // 1.计算品质文本的宽高
            TextPaint.setTextSize(25); //设置文本大小（稍微小一点）
            float qualityWidth = TextPaint.measureText(品质); // 文本宽度
            Paint.FontMetrics fontMetrics = TextPaint.getFontMetrics();
            float qualityHeight = fontMetrics.descent - fontMetrics.ascent; // 文本高度（包含上下间距）
            
            // 2.定义背景与文本的边距
            float padding = 4;
            float bgWidth = qualityWidth + 2 * padding; // 背景宽度=文本宽度+左右边距
            float bgHeight = qualityHeight + 2 * padding; // 背景高度=文本高度+上下边距
            
            // 3.计算背景的位置（文本正下方，左对齐）
            float bgX = xCoordsForPet[i]; 
            float bgY = yNameCoords[i] + 213 - qualityHeight; 
            
            // 4.创建渐变 shader（水平渐变）
            int[] colors = {
                Color.parseColor("#ff0000"), // 红色
                Color.parseColor("#66ccff"), // 天依蓝
                Color.parseColor("#ffffff")  // 白色
            };
            float[] positions = {0f, 0.5f, 1f}; // 颜色位置（0-1，均匀分布）
            LinearGradient gradient = new LinearGradient(
                bgX, bgY,                // 渐变起点（背景左上角）
                bgX + bgWidth, bgY,      // 渐变终点（背景右上角，水平方向）
                colors, 
                positions, 
                Shader.TileMode.CLAMP     // 超出范围时使用边缘颜色
            );
            backgroundPaint.setShader(gradient); // 应用渐变
            
            // 5.绘制渐变背景（圆角矩形）
            canvas.drawRoundRect(
                bgX, bgY, 
                bgX + bgWidth, bgY + bgHeight, 
                5, 5, // 圆角半径
                backgroundPaint
            );
            
            
            //.绘制【品质】文本（在渐变背景上方）
            TextPaint.setShadowLayer(2, -2, -2, Color.parseColor("#65ffffff"));
            canvas.drawText(品质, xCoordsForPet[i]+2, yNameCoords[i] + 213, TextPaint);
            
            TextPaint.setShadowLayer(2, 2, 2, Color.parseColor("#65ffffff"));
            canvas.drawText(品质, xCoordsForPet[i]+2, yNameCoords[i] + 213, TextPaint);
            
            // 绘制宠物战力（渐变背景+白色文字）
            // 1.获取该位置宠物战力
            long 战力 = 计算战力(qun, uin, startNumber + i); 
            
            // 2.定义战力文本及绘制参数
            String powerText = 数值转(战力);
            Paint powerPaint = new Paint(TextPaint); // 复用文本画笔
            powerPaint.setTextSize(24);
            powerPaint.setColor(Color.parseColor("#ffffff")); // 白色文字
            powerPaint.setShadowLayer(2, 1, 1, Color.parseColor("#65000000"));
            
            // 3.计算战力文本宽高
            float powerWidth = powerPaint.measureText(powerText);
            float powerHeight = fontMetrics.descent - fontMetrics.ascent;
            
            // 4.定义战力背景边距（与品质背景保持一致）
            float powerPadding = padding;
            float powerBgWidth = powerWidth + 2 * powerPadding;
            float powerBgHeight = powerHeight + 2 * powerPadding;
            
            // 5.设置战力背景位置（品质背景右侧，间距5px）
            float powerBgX = bgX + bgWidth + 5; 
            float powerBgY = bgY; // 与品质背景对齐
            
            // 6.绘制渐变背景（半透明红 → 半透明黑，水平渐变）
            Paint powerBgPaint = new Paint(backgroundPaint);
            powerBgPaint.setAntiAlias(true); // 抗锯齿
            // 定义渐变颜色
            int[] powerColors = {
                Color.parseColor("#85000000"), // 半透明黑
                Color.parseColor("#85ff0000")  // 半透明红
            };
            float[] powerPositions = {0f, 1f}; // 颜色分布（从左到右渐变）
            // 创建水平渐变（从背景左到右）
            LinearGradient powerGradient = new LinearGradient(
                powerBgX, powerBgY,
                powerBgX + powerBgWidth, powerBgY,
                powerColors,
                powerPositions,
                Shader.TileMode.CLAMP
            );
            powerBgPaint.setShader(powerGradient); // 应用渐变
            // 绘制圆角矩形背景
            canvas.drawRoundRect(
                powerBgX, powerBgY,
                powerBgX + powerBgWidth, powerBgY + powerBgHeight,
                5, 5, // 与品质背景相同圆角
                powerBgPaint
            );
            
            // 7.绘制战力文本（居中显示在背景上）
            float powerTextX = powerBgX + powerPadding;
            float powerTextY = powerBgY + powerPadding - fontMetrics.ascent; // 基线对齐
            canvas.drawText(powerText, powerTextX, powerTextY, powerPaint);
            
            pet.recycle(); //回收临时的Bitmap
        }
    }

    //保存为图片
    saveBitmap2(whiteBackground, AppPath + "/缓存/其他/" + uin + "_宠物小窝_" + number + ".png");

    // 回收临时的Bitmap
    BGimg.recycle();
    BGsy.recycle();
    WJtx.recycle();
}


// 绘制某个群聊的「宠物战榜」图片
public void 宠物战榜(String qun, List topTenList) 
{
    // 解码预设图片
    Bitmap BGimg = BitmapFactory.decodeFile(AppPath+"/目录/图片/其他/战榜背景.png");
    Bitmap BGsy = BitmapFactory.decodeFile(AppPath+"/目录/图片/其他/战榜底图.png");
    Bitmap QLtx = BitmapFactory.decodeFile(AppPath+"/缓存/头像/"+qun+".png");

    // 缩放预设图片为800*1100
    BGimg = Bitmap.createScaledBitmap(BGimg, 800, 1100, true);
    BGsy = Bitmap.createScaledBitmap(BGsy, 800, 1100, true);
    QLtx = Bitmap.createScaledBitmap(QLtx, 64, 64, true);

    // 创建一个800*1100的白色背景的Bitmap
    Bitmap whiteBackground = Bitmap.createBitmap(800, 1100, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);

    // 绘制预设图片到whiteBackground
    canvas.drawBitmap(BGimg, 0, 0, null);
    canvas.drawBitmap(BGsy, 0, 0, null);
    canvas.drawBitmap(QLtx, 207, 5, null);

    // 🎨用于绘制前十玩家的宠物战力 与 群昵称
    Paint numberPaint = new Paint();
    numberPaint.setTextSize(25);
    numberPaint.setColor(Color.WHITE);
    numberPaint.setAntiAlias(true);
    numberPaint.setTypeface(Typeface.DEFAULT_BOLD); //字体加粗   

    // 设置阴影的Paint
    Paint shadowPaint = new Paint(numberPaint);
    shadowPaint.setShadowLayer(1, -2, -2, Color.BLACK);
    
    // 设置第二层阴影的Paint
    Paint shadowPaint2 = new Paint(numberPaint);
    shadowPaint2.setShadowLayer(1, 2, 2, Color.BLACK);

    // 🔥战力的坐标数组，存放每个序号的x,y坐标
    int[] xCoords = {91, 355, 615, 91, 355, 615, 80, 275, 466, 669};
    int[] yCoords = {124, 124, 124, 462, 462, 462, 800, 800, 800, 800};
    
    // 🐱宠物图片的坐标数组，x,y坐标
    //前六名的宽,高=245*245  ， 最后四名的宽,高=180*180
    int[] xPetCoords = {14, 276, 541, 14, 276, 541, 10, 210, 410, 610};
    int[] yPetCoords = {136, 136, 136, 474, 474, 474, 814, 814, 814, 814};
    
    // 😎玩家群昵称的坐标数组，x,y坐标
    int[] xNameCoords = {98, 360, 624, 98, 360, 624, 89, 288, 474, 675};
    int[] yNameCoords = {415, 415, 415, 754, 754, 754, 1038, 1038, 1038, 1038};
    
    // 取实际数据长度与10的较小值，避免越界
    int size = Math.min(topTenList.size(), 10); 
    //循环获取账号信息
    for (int i = 0; i < size; i++) { 
        Map.Entry entry = topTenList.get(i); // 通过索引获取元素
        String account = entry.getKey(); //账号
        Long power = entry.getValue(); //战力
        String 对象 = qun+"/"+account+"/宠物小窝/位置_0"; //账号名下的当前宠物战力
        String 级别 = getString(对象, "级别"); //宠物品质
        String 昵称 = 昵称(qun,account); //玩家名称
        String 名字 = getString(对象, "宠物名字"); //宠物名字（没有易容的情况下，按名字获取宠物图片）
        String 易容 = getString(对象, "是否易容"); //判断是否易容了
        String 上传图 = getString(对象, "上传图片"); //易容后的图片
        String 宠物图片;
        long 等级 = 文转(getString(对象, "等级")); //宠物等级
        
       if (等级 >= 1) {
          // 绘制前十玩家的宠物战力并加上阴影
          canvas.drawText(数值转(power), xCoords[i], yCoords[i], shadowPaint); //阴影
          canvas.drawText(数值转(power), xCoords[i], yCoords[i], shadowPaint2); //阴影2
          canvas.drawText(数值转(power), xCoords[i], yCoords[i], numberPaint); //战力文本
          
          // 绘制前十玩家的群昵称
          canvas.drawText(昵称, xNameCoords[i], yNameCoords[i], shadowPaint); //阴影
          canvas.drawText(昵称, xNameCoords[i], yNameCoords[i], shadowPaint2); //阴影2
          canvas.drawText(昵称, xNameCoords[i], yNameCoords[i], numberPaint); //昵称
          
           if (易容.equals("是")) {
              String petImagePath = AppPath+"/目录/图片/其他/玩家上传/"+account+"/"+qun+"/"+上传图+".png";
               File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            }else if(!petMap.containsKey(名字)&&级别.equals("定制")){
                String petImagePath = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            } else {
                String petImagePath = AppPath+"/目录/图片/宠物/"+名字+".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath+"/目录/图片/其他/默认.png";
                }
            }

            Bitmap pet = BitmapFactory.decodeFile(宠物图片);
            if(i<=5){
              pet = Bitmap.createScaledBitmap(pet, 245, 245, true);
              canvas.drawBitmap(pet, xPetCoords[i], yPetCoords[i], null);
            }
            else if(i>=6){
              pet = Bitmap.createScaledBitmap(pet, 180, 180, true);
              canvas.drawBitmap(pet, xPetCoords[i], yPetCoords[i], null);
            }
          pet.recycle(); //回收临时的Bitmap
       }
    }
    
    //获取该群玩家人数
    String groupPlayerList = getString(qun + "/玩家列表", "列表");
    String[] players = groupPlayerList.split("、"); //按“、”分割
    int count = players.length; // 玩家数量
    if(count>=1){
       String 文本="(此群共"+count+"位玩家)";
          canvas.drawText(文本, 527, 41, shadowPaint); //阴影
          canvas.drawText(文本, 527, 41, shadowPaint2); //阴影2
          canvas.drawText(文本, 527, 41, numberPaint); //文本
    }
    
    //保存为图片
    saveBitmap2(whiteBackground, AppPath+"/缓存/其他/"+qun+"_宠物战榜.png");

    // 回收临时的Bitmap
    BGimg.recycle();
    BGsy.recycle();
    QLtx.recycle();
}

// 绘制「宠物神榜」图片
public void 宠物神榜(List topTenList) 
{
    // 解码预设图片
    Bitmap BGimg = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/神榜模版.png");

    // 缩放预设图片
    BGimg = Bitmap.createScaledBitmap(BGimg, 700, 1100, true);

    // 创建一个白色背景的Bitmap
    Bitmap whiteBackground = Bitmap.createBitmap(700, 1100, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);

    // 绘制预设图片到whiteBackground
    canvas.drawBitmap(BGimg, 0, 0, null);

    // 🎨用于绘制前十玩家的宠物战力
    Paint numberPaint = new Paint();
    numberPaint.setTextSize(28);
    numberPaint.setColor(Color.BLACK);
    numberPaint.setAntiAlias(true);
    numberPaint.setTypeface(Typeface.DEFAULT_BOLD); // 字体加粗

    // 设置阴影的Paint
    Paint shadowPaint = new Paint(numberPaint);
    shadowPaint.setShadowLayer(1, -2, -2, Color.WHITE);

    // 设置第二层阴影的Paint
    Paint shadowPaint2 = new Paint(numberPaint);
    shadowPaint2.setShadowLayer(1, 2, 2, Color.WHITE);

    // 🔥战力的坐标数组，存放每个序号的x,y坐标
    int[] xCoords = {237, 111, 318, 524, 111, 318, 524, 111, 318, 524};
    int[] yCoords = {128, 378, 378, 378, 600, 600, 600, 822, 822, 822};

    // 🐱宠物图片的坐标数组，x,y坐标（图片宽高：190*190，160*160）
    int[] xPetCoords = {184, 65, 270, 475, 65, 270, 475, 65, 270, 475};
    int[] yPetCoords = {133, 385, 385, 385, 605, 605, 605, 825, 825, 825};

    // 🧙🏻‍♂️玩家图片的坐标数组，x,y坐标（图片宽高：60*60，50*50）
    int[] xPlayCoords = {184, 65, 270, 475, 65, 270, 475, 65, 270, 475};
    int[] yPlayCoords = {264, 495, 495, 495, 715, 715, 715, 935, 935, 935};

    // 取实际数据长度与10的较小值，避免越界
    int size = Math.min(topTenList.size(), 10);
    
    //半透明背景绘制Paint
    Paint bgPaint = new Paint();
    bgPaint.setColor(Color.parseColor("#45ffffff"));
    bgPaint.setAntiAlias(true); // 抗锯齿

    // 循环获取账号信息
    for (int i = 0; i < size; i++) {
        Map.Entry entry = topTenList.get(i); // 通过索引获取元素
        String[] keyParts = entry.getKey().split("-"); // 解析群号和账号
        String 群号 = keyParts[0];
        String account = keyParts[1]; // 账号
        Long power = entry.getValue(); // 战力
        String 对象 = 群号 + "/" + account + "/宠物小窝/位置_0"; // 账号名下的当前宠物战力
        String 级别 = getString(对象, "级别"); //宠物品质
        String 名字 = getString(对象, "宠物名字"); // 宠物名字
        String 易容 = getString(对象, "是否易容"); // 判断是否易容了
        String 上传图 = getString(对象, "上传图片"); // 易容后的图片标记
        String 宠物图片;
        String 玩家头像 = AppPath + "/缓存/头像/" + account + ".png";
        long 等级 = 文转(getString(对象, "等级")); // 宠物等级

        if (等级 >= 1) {
            // 绘制前十玩家的宠物战力并加上阴影
            canvas.drawText(数值转(power), xCoords[i], yCoords[i], shadowPaint); // 阴影
            canvas.drawText(数值转(power), xCoords[i], yCoords[i], shadowPaint2); // 阴影2
            canvas.drawText(数值转(power), xCoords[i], yCoords[i], numberPaint); // 战力文本
            if(易容==null||级别==null) {
                String petImagePath = AppPath + "/目录/图片/宠物/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            }else if (易容.equals("是")) {
                String petImagePath = AppPath+"/目录/图片/其他/玩家上传/"+account+"/"+群号+"/"+上传图+".png";
               File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            } else if(!petMap.containsKey(名字)&&级别.equals("定制")){
                String petImagePath = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            } else {
                String petImagePath = AppPath + "/目录/图片/宠物/" + 名字 + ".jpg";
                File file = new File(petImagePath);
                if (file.exists()) {
                    宠物图片 = petImagePath;
                } else {
                    宠物图片 = AppPath + "/目录/图片/其他/默认.png";
                }
            }

            Bitmap pet = BitmapFactory.decodeFile(宠物图片);
            Bitmap play = BitmapFactory.decodeFile(玩家头像);

            if (i == 0) {
                // 第1名：宠物图190*190，头像60*60
                pet = Bitmap.createScaledBitmap(pet, 190, 190, true);
                canvas.drawBitmap(pet, xPetCoords[i], yPetCoords[i], null);
                // 绘制62*62半透明背景
                canvas.drawRect(xPlayCoords[i]-2, yPlayCoords[i]-2, xPlayCoords[i] + 62, yPlayCoords[i] + 62, bgPaint);
                play = Bitmap.createScaledBitmap(play, 60, 60, true);
                canvas.drawBitmap(play, xPlayCoords[i], yPlayCoords[i], null);
            } else if (i >= 1) {
                // 第2-10名：宠物图160*160，头像50*50
                pet = Bitmap.createScaledBitmap(pet, 160, 160, true);
                canvas.drawBitmap(pet, xPetCoords[i], yPetCoords[i], null);
                // 绘制52*52半透明背景
                canvas.drawRect(xPlayCoords[i]-2, yPlayCoords[i]-2, xPlayCoords[i] + 52, yPlayCoords[i] + 52, bgPaint);
                play = Bitmap.createScaledBitmap(play, 50, 50, true);
                canvas.drawBitmap(play, xPlayCoords[i], yPlayCoords[i], null);
            }
            // 回收临时的Bitmap
            pet.recycle();
            play.recycle();
        }
    }

    // 保存为图片
    saveBitmap2(whiteBackground, AppPath + "/缓存/其他/宠物神榜.png");

    // 回收临时的Bitmap
    BGimg.recycle();
    whiteBackground.recycle();
}


// 绘制「宠物击杀榜」图片，会通过之前构建的两个列表来获取相关参数
public void 宠物击杀榜(List topTen1, List topTen2) 
{
    // 解码预设图片
    Bitmap BGimg = BitmapFactory.decodeFile(AppPath+"/目录/图片/其他/宠物击杀榜.png");

    // 缩放预设图片
    BGimg = Bitmap.createScaledBitmap(BGimg, 936, 1664, true);

    // 创建白色背景的Bitmap
    Bitmap whiteBackground = Bitmap.createBitmap(936, 1664, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);

    // 绘制预设图片到whiteBackground
    canvas.drawBitmap(BGimg, 0, 0, null);

    // 🎨用于绘制文本内容
    Paint numberPaint = new Paint();
    numberPaint.setTextSize(32);
    numberPaint.setColor(Color.BLACK);
    numberPaint.setAntiAlias(true);
    numberPaint.setTypeface(Typeface.DEFAULT_BOLD); //字体加粗   

    // 设置阴影的Paint
    Paint shadowPaint = new Paint(numberPaint);
    shadowPaint.setShadowLayer(1, -2, -2, Color.WHITE);
    
    // 🗡️击杀数的坐标数组（左侧）
    int[] xCoords = {324, 324, 324, 324, 324, 324, 324, 324, 324, 324};
    int[] yCoords = {412, 518, 622, 730, 834, 938, 1042, 1146, 1250, 1354};
    
    // 🥷🏻玩家账号的坐标数组（左侧）
    int[] xUinCoords = {156, 156, 156, 156, 156, 156, 156, 156, 156, 156};
    int[] yUinCoords = {412, 518, 622, 730, 834, 938, 1042, 1146, 1250, 1354};
    
    // 🧙🏻‍♂️玩家图片的坐标数组，x,y坐标（左侧，图片宽高：88*88）
    int[] xPlayCoords = {65, 65, 65, 65, 65, 65, 65, 65, 65, 65};
    int[] yPlayCoords = {336, 443, 548, 653, 758, 863, 968, 1073, 1178, 1283};
    
   //——————————绘制左侧：击杀榜部分——————————
    int size1 = Math.min(topTen1.size(), 10);  // 取实际长度与10的较小值，避免越界
    //循环获取账号信息
    for (int i = 0; i < size1; i++) {
        Map.Entry entry = topTen1.get(i); // 通过索引获取元素
        String account = entry.getKey(); // 获取账号
        int 击杀数 = getInt("宠物击杀次数", account, 0);
        String str = Integer.toString(击杀数);
        String 玩家头像 = AppPath+"/缓存/头像/"+account+".png";
       if (击杀数 >= 1) {
          // 绘制前十玩家的相关参数并加上阴影
          canvas.drawText(maskAccount(account), xUinCoords[i], yUinCoords[i], shadowPaint); //阴影
          canvas.drawText(maskAccount(account), xUinCoords[i], yUinCoords[i], numberPaint); //文本
          canvas.drawText(str, xCoords[i], yCoords[i], shadowPaint); 
          canvas.drawText(str, xCoords[i], yCoords[i], numberPaint); 
          
          Bitmap play = BitmapFactory.decodeFile(玩家头像);
          play = Bitmap.createScaledBitmap(play, 88, 88, true);
          canvas.drawBitmap(play, xPlayCoords[i], yPlayCoords[i], null);
          //回收临时的Bitmap
           play.recycle();
       }
    }
    
     // 🩸被击杀数的坐标数组（右侧）
    int[] xCoords2 = {826, 826, 826, 826, 826, 826, 826, 826, 826, 826};
    int[] yCoords2 = {532, 638, 744, 850, 956, 1062, 1168, 1274, 1380, 1486};
    
    // 🥷🏻玩家账号的坐标数组（右侧）
    int[] xUinCoords2 = {650, 650, 650, 650, 650, 650, 650, 650, 650, 650};
    int[] yUinCoords2 = {532, 638, 744, 850, 956, 1062, 1168, 1274, 1380, 1486};
    
    // 🧙🏻‍♂️玩家图片的坐标数组，x,y坐标（右侧，图片宽高：88*88）
    int[] xPlayCoords2 = {560, 560, 560, 560, 560, 560, 560, 560, 560, 560};
    int[] yPlayCoords2 = {457, 563, 668, 773, 878, 983, 1088, 1193, 1298, 1403};
    
   //——————————绘制右侧：被击杀榜部分——————————
    int size2 = Math.min(topTen2.size(), 10);  // 取实际长度与10的较小值，避免越界
    //循环获取账号信息
    for (int i = 0; i < size2; i++) {
        Map.Entry entry = topTen2.get(i); // 通过索引获取元素
        String account = entry.getKey(); // 获取账号
        int 被击杀 = getInt("宠物被击杀次数", account, 0);
        String str = Integer.toString(被击杀);
        String 玩家头像 = AppPath+"/缓存/头像/"+account+".png";
       if (被击杀 >= 1) {
          // 绘制前十玩家的相关参数并加上阴影
          canvas.drawText(maskAccount(account), xUinCoords2[i], yUinCoords2[i], shadowPaint); //阴影
          canvas.drawText(maskAccount(account), xUinCoords2[i], yUinCoords2[i], numberPaint); //文本
          canvas.drawText(str, xCoords2[i], yCoords2[i], shadowPaint); 
          canvas.drawText(str, xCoords2[i], yCoords2[i], numberPaint); 
          
          Bitmap play2 = BitmapFactory.decodeFile(玩家头像);
          play2 = Bitmap.createScaledBitmap(play2, 88, 88, true);
          canvas.drawBitmap(play2, xPlayCoords2[i], yPlayCoords2[i], null);
          //回收临时的Bitmap
          play2.recycle();
       }
    }    
    
    //保存为图片
    saveBitmap2(whiteBackground, AppPath+"/缓存/其他/宠物击杀榜.png");

    // 回收临时的Bitmap
    BGimg.recycle();
}

// 绘制「跨群攻击」图片
public void 跨群攻击(String q1, String u1, long a1, String q2, String u2, long a2) {
    // 基础图片初始化
    Bitmap BGimg = null;
    try {
        BGimg = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/跨群模版.png");
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (BGimg == null) return; // 防止空指针

    // 缩放背景图
    BGimg = Bitmap.createScaledBitmap(BGimg, 1200, 768, true);

    // 创建白色背景画布
    Bitmap whiteBackground = Bitmap.createBitmap(1200, 768, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);
    canvas.drawBitmap(BGimg, 0, 0, null);
    BGimg.recycle(); // 提前回收背景图节省内存

    // -------------------- 攻击方宠物处理 --------------------
    String attackerPath = q1 + "/" + u1 + "/宠物小窝/位置_0";
    String attackerPetName = getString(attackerPath, "宠物名字");
    String attackerAttr = getString(attackerPath, "属性");
    String attackerQuality = getString(attackerPath, "级别");
    String attackerTalent = getString(attackerPath, "天赋");
    String attackerDisguise = getString(attackerPath, "是否易容");
    String attackerUploadImg = getString(attackerPath, "上传图片");
    String attackerPetImgPath;
    String attackerAvatarPath = AppPath + "/缓存/头像/" + u1 + ".png";
    

    // 处理宠物图片路径（含空指针检查）
    if ("是".equals(attackerDisguise)) {
        attackerPetImgPath = AppPath + "/目录/图片/其他/玩家上传/" + u1 + "/" + q1 + "/" + attackerUploadImg + ".png";
    } else if(!petMap.containsKey(attackerPetName)&&attackerQuality.equals("定制")){
          String petImagePath = AppPath + "/目录/图片/宠物/定制/" + attackerPetName + ".jpg";
          File file = new File(petImagePath);
         if (file.exists()) {
                attackerPetImgPath = petImagePath;
          } else {
                attackerPetImgPath = AppPath + "/目录/图片/其他/默认.png";
         }
    }else {
        String defaultPath = AppPath + "/目录/图片/宠物/" + attackerPetName + ".jpg";
        attackerPetImgPath = new File(defaultPath).exists() ? defaultPath : AppPath + "/目录/图片/其他/默认.png";
    }

    // -------------------- 防御方宠物处理 --------------------
    String defenderPath = q2 + "/" + u2 + "/宠物小窝/位置_0";
    String defenderPetName = getString(defenderPath, "宠物名字");
    String defenderAttr = getString(defenderPath, "属性");
    String defenderQuality = getString(defenderPath, "级别");
    String defenderTalent = getString(defenderPath, "天赋");
    String defenderDisguise = getString(defenderPath, "是否易容");
    String defenderUploadImg = getString(defenderPath, "上传图片");
    String defenderPetImgPath;
    String defenderAvatarPath = AppPath + "/缓存/头像/" + u2 + ".png";
    
    // 处理防御方宠物图片路径
    if ("是".equals(defenderDisguise)) {
        defenderPetImgPath = AppPath + "/目录/图片/其他/玩家上传/" + u2 + "/" + q2 + "/" + defenderUploadImg + ".png";
    } else if(!petMap.containsKey(defenderPetName)&&defenderQuality.equals("定制")){
          String petImagePath = AppPath + "/目录/图片/宠物/定制/" + defenderPetName + ".jpg";
          File file = new File(petImagePath);
         if (file.exists()) {
                defenderPetImgPath = petImagePath;
          } else {
                defenderPetImgPath = AppPath + "/目录/图片/其他/默认.png";
         }
    } else {
        String defaultPath = AppPath + "/目录/图片/宠物/" + defenderPetName + ".jpg";
        defenderPetImgPath = new File(defaultPath).exists() ? defaultPath : AppPath + "/目录/图片/其他/默认.png";
    }

    // -------------------- 绘图工具初始化 --------------------
    Paint textPaint = new Paint();
    textPaint.setTextSize(33);
    textPaint.setColor(Color.WHITE);
    textPaint.setAntiAlias(true);
    textPaint.setTypeface(Typeface.DEFAULT_BOLD);

    Paint shadowPaint = new Paint(textPaint);
    shadowPaint.setShadowLayer(1, 1, 1, Color.BLACK);

    Paint textPaint2 = new Paint();
    textPaint2.setTextSize(42);
    textPaint2.setColor(Color.RED);
    textPaint2.setAntiAlias(true);
    textPaint2.setTypeface(Typeface.DEFAULT_BOLD);
    
    Paint shadowPaint2 = new Paint(textPaint2);
    shadowPaint2.setShadowLayer(1, 2, 2, Color.BLACK);
    
    Paint shadowPaint3 = new Paint(textPaint2);
    shadowPaint3.setShadowLayer(1, -2, -2, Color.WHITE);

    // 加载被击败图片（含异常处理）
    Bitmap defeatedBitmap = null;
    try {
        defeatedBitmap = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/被击败啦.png");
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (defeatedBitmap != null) {
        defeatedBitmap = Bitmap.createScaledBitmap(defeatedBitmap, 360, 360, true);
    }

    // -------------------- 生命条计算 --------------------
    // 攻击方生命
    long attackerCurHp = 文转(getString(attackerPath, "当前生命"));
    long attackerMaxHp = 文转(getString(attackerPath, "生命上限"));
    float attackerHpRatio = attackerMaxHp == 0 ? 0 : (float) attackerCurHp / attackerMaxHp;
    Rect attackerHpRect = new Rect(191, 90, 191 + (int) (292 * attackerHpRatio), 90 + 61);
    Rect attackerHpGrayRect = new Rect(attackerHpRect.right, 90, 191 + 292, 90 + 61);

    // 防御方生命
    long defenderCurHp = 文转(getString(defenderPath, "当前生命"));
    long defenderMaxHp = 文转(getString(defenderPath, "生命上限"));
    float defenderHpRatio = defenderMaxHp == 0 ? 0 : (float) defenderCurHp / defenderMaxHp;
    int defenderRedWidth = (int) (292 * defenderHpRatio);
    Rect defenderHpRedRect = new Rect(721 + 292 - defenderRedWidth, 90, 721 + 292, 90 + 61);
    Rect defenderHpGrayRect = new Rect(721, 90, defenderHpRedRect.left, 90 + 61);

    // -------------------- 绘制生命条 --------------------
    Paint hpPaint = new Paint();
    hpPaint.setStyle(Paint.Style.FILL);
    hpPaint.setStrokeWidth(2);

    // 攻击方生命条
    hpPaint.setColor(Color.RED);
    canvas.drawRect(attackerHpRect, hpPaint);
    hpPaint.setColor(Color.GRAY);
    canvas.drawRect(attackerHpGrayRect, hpPaint);

    // 防御方生命条
    hpPaint.setColor(Color.RED);
    canvas.drawRect(defenderHpRedRect, hpPaint);
    hpPaint.setColor(Color.GRAY);
    canvas.drawRect(defenderHpGrayRect, hpPaint);

    // -------------------- 绘制攻击方元素 --------------------
    // 玩家头像（添加空指针检查）
    Bitmap attackerAvatar = safeDecodeBitmap(attackerAvatarPath);
    if (attackerAvatar != null) {
        attackerAvatar = Bitmap.createScaledBitmap(attackerAvatar, 119, 119, true);
        canvas.drawBitmap(attackerAvatar, 62, 60, null);
        attackerAvatar.recycle();
    }

    // 宠物图片
    Bitmap attackerPetImg = safeDecodeBitmap(attackerPetImgPath);
    if (attackerPetImg != null) {
        attackerPetImg = Bitmap.createScaledBitmap(attackerPetImg, 360, 360, true);
        canvas.drawBitmap(attackerPetImg, 121, 204, null);
        if (attackerCurHp == 0 && defeatedBitmap != null) {
            canvas.drawBitmap(defeatedBitmap, 121, 204, null);
        }
        attackerPetImg.recycle();
    }
    
    // 定义背景画笔（半透明白色）
    Paint bgPaint = new Paint();
    bgPaint.setColor(Color.parseColor("#80ffffff")); // 半透明白色（80是透明度，范围0-FF）
    bgPaint.setStyle(Paint.Style.FILL);
    bgPaint.setAntiAlias(true); // 抗锯齿，使背景边缘更平滑

    //绘制文本内容
    canvas.drawText(数值转(attackerCurHp), 277, 136, textPaint); //攻击方的当前生命
    // 字体大小42，边距5像素（可根据需要调整）
    float textSize = 42f;
    float padding = 5f;
    // 绘制a2文字的背景和文字
    String text2 = "-" + 数值转(a2);
    float text2X = 128f;
    float text2Y = 250f; // 文字基线y坐标
    // 计算背景矩形（垂直方向基于基线居中）
    float bgTop = text2Y-46;
    float bgBottom = text2Y +10;
    float bgLeft = text2X - padding/2;
    float bgRight = text2X + textPaint2.measureText(text2) + padding;
    canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, bgPaint);
    canvas.drawText(text2, text2X, text2Y, textPaint2); //被防御方攻击的伤害
    canvas.drawText("-"+数值转(a2), 128, 250, shadowPaint2); //输出阴影
    canvas.drawText("-"+数值转(a2), 128, 250, shadowPaint3); //输出阴影（第二层）
    canvas.drawText("【"+attackerAttr+"】", 203, 80, textPaint); //宠物属性
    canvas.drawText("【"+attackerAttr+"】", 203, 80, shadowPaint); //宠物属性阴影
    if(attackerTalent.equals("锐牙狂威"))
    {
        String 描述="["+attackerTalent+"]，攻击+30%";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }
    else if(attackerTalent.equals("厚土磐佑"))
    {
        String 描述="["+attackerTalent+"]，防御+30%";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }
    else if(attackerTalent.equals("疫病之源"))
    {
        String 描述="["+attackerTalent+"]，55%概率使目标状态异常";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }


    // -------------------- 绘制防御方元素 --------------------
    // 玩家头像
    Bitmap defenderAvatar = safeDecodeBitmap(defenderAvatarPath);
    if (defenderAvatar != null) {
        defenderAvatar = Bitmap.createScaledBitmap(defenderAvatar, 119, 119, true);
        canvas.drawBitmap(defenderAvatar, 1022, 60, null);
        defenderAvatar.recycle();
    }

    // 宠物图片
    Bitmap defenderPetImg = safeDecodeBitmap(defenderPetImgPath);
    if (defenderPetImg != null) {
        defenderPetImg = Bitmap.createScaledBitmap(defenderPetImg, 360, 360, true);
        canvas.drawBitmap(defenderPetImg, 724, 204, null);
        if (defenderCurHp == 0 && defeatedBitmap != null) {
            canvas.drawBitmap(defeatedBitmap, 724, 204, null);
        }
        defenderPetImg.recycle();
    }

    // 绘制文本信息
    canvas.drawText(数值转(defenderCurHp), 786, 136, textPaint); //防御方的当前生命
    String text1 = "-" + 数值转(a1);
    float text1X = 730f;
    float text1Y = 250f;
    float bgLeft1 = text1X - padding;
    float bgRight1 = text1X + textPaint2.measureText(text1) + padding;
    canvas.drawRect(bgLeft1, bgTop, bgRight1, bgBottom, bgPaint);
    canvas.drawText(text1, text1X, text1Y, textPaint2); //攻击防御方的伤害
    canvas.drawText("-"+数值转(a1), 730, 250, shadowPaint2); //输出阴影
    canvas.drawText("-"+数值转(a1), 730, 250, shadowPaint3); //输出阴影（第二层）
    canvas.drawText("【"+defenderAttr+"】", 730, 80, textPaint); //宠物属性
    canvas.drawText("【"+defenderAttr+"】", 730, 80, shadowPaint); //宠物属性阴影
    if(defenderTalent.equals("锐牙狂威"))
    {
        String 描述="["+defenderTalent+"]，攻击+30%";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }
    else if(defenderTalent.equals("厚土磐佑"))
    {
        String 描述="["+defenderTalent+"]，防御+30%";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }
    else if(defenderTalent.equals("疫病之源"))
    {
        String 描述="["+defenderTalent+"]，55%概率使目标状态异常";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }

    // -------------------- 绘制心形背景 --------------------
    Bitmap heartBitmap = safeDecodeBitmap(AppPath + "/目录/图片/其他/跨群心形.png");
    if (heartBitmap != null) {
        heartBitmap = Bitmap.createScaledBitmap(heartBitmap, 1200, 768, true);
        canvas.drawBitmap(heartBitmap, 0, 0, null);
        heartBitmap.recycle();
    }

    // 保存图片（添加异常处理）
    try {
        String imgPrefix = q1 + u1 + "&" + q2 + u2;
        saveBitmap2(whiteBackground, AppPath + "/缓存/其他/" + imgPrefix + "_跨群攻击.png");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        whiteBackground.recycle();
        if (defeatedBitmap != null) defeatedBitmap.recycle();
    }
}


// 绘制「宠物反击」图片（根据【跨群攻击】所记录的仇敌来反击）
public void 宠物反击(String q1, String u1, long a1, String q2, String u2, long a2) {
    // 基础图片初始化
    Bitmap BGimg = null;
    try {
        BGimg = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/反击模版.png");
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (BGimg == null) return; // 防止空指针

    // 缩放背景图
    BGimg = Bitmap.createScaledBitmap(BGimg, 1200, 768, true);

    // 创建白色背景画布
    Bitmap whiteBackground = Bitmap.createBitmap(1200, 768, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(whiteBackground);
    canvas.drawColor(Color.WHITE);
    canvas.drawBitmap(BGimg, 0, 0, null);
    BGimg.recycle(); // 提前回收背景图节省内存

    // -------------------- 攻击方宠物处理 --------------------
    String attackerPath = q1 + "/" + u1 + "/宠物小窝/位置_0";
    String attackerPetName = getString(attackerPath, "宠物名字");
    String attackerAttr = getString(attackerPath, "属性");
    String attackerQuality = getString(attackerPath, "级别");
    String attackerTalent = getString(attackerPath, "天赋");
    String attackerDisguise = getString(attackerPath, "是否易容");
    String attackerUploadImg = getString(attackerPath, "上传图片");
    String attackerPetImgPath;
    String attackerAvatarPath = AppPath + "/缓存/头像/" + u1 + ".png";
    

    // 处理宠物图片路径（含空指针检查）
    if ("是".equals(attackerDisguise)) {
        attackerPetImgPath = AppPath + "/目录/图片/其他/玩家上传/" + u1 + "/" + q1 + "/" + attackerUploadImg + ".png";
    } else if(!petMap.containsKey(attackerPetName)&&attackerQuality.equals("定制")){
          String petImagePath = AppPath + "/目录/图片/宠物/定制/" + attackerPetName + ".jpg";
          File file = new File(petImagePath);
         if (file.exists()) {
                attackerPetImgPath = petImagePath;
          } else {
                attackerPetImgPath = AppPath + "/目录/图片/其他/默认.png";
         }
    } else {
        String defaultPath = AppPath + "/目录/图片/宠物/" + attackerPetName + ".jpg";
        attackerPetImgPath = new File(defaultPath).exists() ? defaultPath : AppPath + "/目录/图片/其他/默认.png";
    }

    // -------------------- 防御方宠物处理 --------------------
    String defenderPath = q2 + "/" + u2 + "/宠物小窝/位置_0";
    String defenderPetName = getString(defenderPath, "宠物名字");
    String defenderAttr = getString(defenderPath, "属性");
    String defenderQuality = getString(defenderPath, "级别");
    String defenderTalent = getString(defenderPath, "天赋");
    String defenderDisguise = getString(defenderPath, "是否易容");
    String defenderUploadImg = getString(defenderPath, "上传图片");
    String defenderPetImgPath;
    String defenderAvatarPath = AppPath + "/缓存/头像/" + u2 + ".png";
    

    // 处理防御方宠物图片路径
    if ("是".equals(defenderDisguise)) {
        defenderPetImgPath = AppPath + "/目录/图片/其他/玩家上传/" + u2 + "/" + q2 + "/" + defenderUploadImg + ".png";
    } else if(!petMap.containsKey(defenderPetName)&&defenderQuality.equals("定制")){
          String petImagePath = AppPath + "/目录/图片/宠物/定制/" + defenderPetName + ".jpg";
          File file = new File(petImagePath);
         if (file.exists()) {
                defenderPetImgPath = petImagePath;
          } else {
                defenderPetImgPath = AppPath + "/目录/图片/其他/默认.png";
         }
    } else {
        String defaultPath = AppPath + "/目录/图片/宠物/" + defenderPetName + ".jpg";
        defenderPetImgPath = new File(defaultPath).exists() ? defaultPath : AppPath + "/目录/图片/其他/默认.png";
    }

    // -------------------- 绘图工具初始化 --------------------
    Paint textPaint = new Paint();
    textPaint.setTextSize(33);
    textPaint.setColor(Color.WHITE);
    textPaint.setAntiAlias(true);
    textPaint.setTypeface(Typeface.DEFAULT_BOLD);

    Paint shadowPaint = new Paint(textPaint);
    shadowPaint.setShadowLayer(1, 1, 1, Color.BLACK);

    Paint textPaint2 = new Paint();
    textPaint2.setTextSize(42);
    textPaint2.setColor(Color.RED);
    textPaint2.setAntiAlias(true);
    textPaint2.setTypeface(Typeface.DEFAULT_BOLD);
    
    Paint shadowPaint2 = new Paint(textPaint2);
    shadowPaint2.setShadowLayer(1, 2, 2, Color.BLACK);
    
    Paint shadowPaint3 = new Paint(textPaint2);
    shadowPaint3.setShadowLayer(1, -2, -2, Color.WHITE);

    // 加载被击败图片（含异常处理）
    Bitmap defeatedBitmap = null;
    try {
        defeatedBitmap = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/被击败啦.png");
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (defeatedBitmap != null) {
        defeatedBitmap = Bitmap.createScaledBitmap(defeatedBitmap, 360, 360, true);
    }

    // -------------------- 生命条计算 --------------------
    // 攻击方生命
    long attackerCurHp = 文转(getString(attackerPath, "当前生命"));
    long attackerMaxHp = 文转(getString(attackerPath, "生命上限"));
    float attackerHpRatio = attackerMaxHp == 0 ? 0 : (float) attackerCurHp / attackerMaxHp;
    Rect attackerHpRect = new Rect(191, 90, 191 + (int) (292 * attackerHpRatio), 90 + 61);
    Rect attackerHpGrayRect = new Rect(attackerHpRect.right, 90, 191 + 292, 90 + 61);

    // 防御方生命
    long defenderCurHp = 文转(getString(defenderPath, "当前生命"));
    long defenderMaxHp = 文转(getString(defenderPath, "生命上限"));
    float defenderHpRatio = defenderMaxHp == 0 ? 0 : (float) defenderCurHp / defenderMaxHp;
    int defenderRedWidth = (int) (292 * defenderHpRatio);
    Rect defenderHpRedRect = new Rect(721 + 292 - defenderRedWidth, 90, 721 + 292, 90 + 61);
    Rect defenderHpGrayRect = new Rect(721, 90, defenderHpRedRect.left, 90 + 61);

    // -------------------- 绘制生命条 --------------------
    Paint hpPaint = new Paint();
    hpPaint.setStyle(Paint.Style.FILL);
    hpPaint.setStrokeWidth(2);

    // 攻击方生命条
    hpPaint.setColor(Color.RED);
    canvas.drawRect(attackerHpRect, hpPaint);
    hpPaint.setColor(Color.GRAY);
    canvas.drawRect(attackerHpGrayRect, hpPaint);

    // 防御方生命条
    hpPaint.setColor(Color.RED);
    canvas.drawRect(defenderHpRedRect, hpPaint);
    hpPaint.setColor(Color.GRAY);
    canvas.drawRect(defenderHpGrayRect, hpPaint);

    // -------------------- 绘制攻击方元素 --------------------
    // 玩家头像（添加空指针检查）
    Bitmap attackerAvatar = safeDecodeBitmap(attackerAvatarPath);
    if (attackerAvatar != null) {
        attackerAvatar = Bitmap.createScaledBitmap(attackerAvatar, 119, 119, true);
        canvas.drawBitmap(attackerAvatar, 62, 60, null);
        attackerAvatar.recycle();
    }

    // 宠物图片
    Bitmap attackerPetImg = safeDecodeBitmap(attackerPetImgPath);
    if (attackerPetImg != null) {
        attackerPetImg = Bitmap.createScaledBitmap(attackerPetImg, 360, 360, true);
        canvas.drawBitmap(attackerPetImg, 121, 204, null);
        if (attackerCurHp == 0 && defeatedBitmap != null) {
            canvas.drawBitmap(defeatedBitmap, 121, 204, null);
        }
        attackerPetImg.recycle();
    }
    
    //绘制文本内容
    canvas.drawText(数值转(attackerCurHp), 277, 136, textPaint); //攻击方的当前生命
    canvas.drawText("-"+数值转(a2), 128, 250, textPaint2); //被防御方攻击的伤害
    canvas.drawText("-"+数值转(a2), 128, 250, shadowPaint2); //输出阴影
    canvas.drawText("-"+数值转(a2), 128, 250, shadowPaint3); //输出阴影（第二层）
    canvas.drawText("【"+attackerAttr+"】", 203, 80, textPaint); //宠物属性
    canvas.drawText("【"+attackerAttr+"】", 203, 80, shadowPaint); //宠物属性阴影
    if(attackerTalent.equals("锐牙狂威"))
    {
        String 描述="["+attackerTalent+"]，攻击+30%";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }
    else if(attackerTalent.equals("厚土磐佑"))
    {
        String 描述="["+attackerTalent+"]，防御+30%";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }
    else if(attackerTalent.equals("疫病之源"))
    {
        String 描述="["+attackerTalent+"]，55%概率使目标状态异常";
        canvas.drawText(描述, 62, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 62, 620, shadowPaint); //阴影
    }


    // -------------------- 绘制防御方元素 --------------------
    // 玩家头像
    Bitmap defenderAvatar = safeDecodeBitmap(defenderAvatarPath);
    if (defenderAvatar != null) {
        defenderAvatar = Bitmap.createScaledBitmap(defenderAvatar, 119, 119, true);
        canvas.drawBitmap(defenderAvatar, 1022, 60, null);
        defenderAvatar.recycle();
    }

    // 宠物图片
    Bitmap defenderPetImg = safeDecodeBitmap(defenderPetImgPath);
    if (defenderPetImg != null) {
        defenderPetImg = Bitmap.createScaledBitmap(defenderPetImg, 360, 360, true);
        canvas.drawBitmap(defenderPetImg, 724, 204, null);
        if (defenderCurHp == 0 && defeatedBitmap != null) {
            canvas.drawBitmap(defeatedBitmap, 724, 204, null);
        }
        defenderPetImg.recycle();
    }

    // 绘制文本信息
    canvas.drawText(数值转(defenderCurHp), 786, 136, textPaint); //防御方的当前生命
    canvas.drawText("-"+数值转(a1), 730, 250, textPaint2); //攻击防御方的伤害
    canvas.drawText("-"+数值转(a1), 730, 250, shadowPaint2); //输出阴影
    canvas.drawText("-"+数值转(a1), 730, 250, shadowPaint3); //输出阴影（第二层）
    canvas.drawText("【"+defenderAttr+"】", 730, 80, textPaint); //宠物属性
    canvas.drawText("【"+defenderAttr+"】", 730, 80, shadowPaint); //宠物属性阴影
    if(defenderTalent.equals("锐牙狂威"))
    {
        String 描述="["+defenderTalent+"]，攻击+30%";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }
    else if(defenderTalent.equals("厚土磐佑"))
    {
        String 描述="["+defenderTalent+"]，防御+30%";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }
    else if(defenderTalent.equals("疫病之源"))
    {
        String 描述="["+defenderTalent+"]，55%概率使目标状态异常";
        canvas.drawText(描述, 722, 620, textPaint); //宠物天赋
        canvas.drawText(描述, 722, 620, shadowPaint); //阴影
    }

    // -------------------- 绘制心形背景 --------------------
    Bitmap heartBitmap = safeDecodeBitmap(AppPath + "/目录/图片/其他/跨群心形.png");
    if (heartBitmap != null) {
        heartBitmap = Bitmap.createScaledBitmap(heartBitmap, 1200, 768, true);
        canvas.drawBitmap(heartBitmap, 0, 0, null);
        heartBitmap.recycle();
    }

    // 保存图片（添加异常处理）
    try {
        String imgPrefix = q1 + u1 + "&" + q2 + u2;
        saveBitmap2(whiteBackground, AppPath + "/缓存/其他/" + imgPrefix + "_宠物反击.png");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        whiteBackground.recycle();
        if (defeatedBitmap != null) defeatedBitmap.recycle();
    }
}

// ===================== 辅助方法 =====================
/** 安全解码Bitmap（防止空指针） */
private Bitmap safeDecodeBitmap(String path) {
    try {
        if (path == null) return null;
        return BitmapFactory.decodeFile(path);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

//将 Bitmap保存为图片 的方法
private void saveBitmap2(Bitmap bitmap, String filePath) {
    try {
        FileOutputStream out = new FileOutputStream(filePath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out); //压缩图片质量
        out.flush();
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


//获取 该「玩家」群昵称
public static String 玩家名(String qun, String uin) {
    String 昵称 = "@" + getMemberName(qun, uin);
    if (昵称.length() > 6) {
        昵称 = 昵称.substring(0, 6) + "… ";
    }
    return 昵称 + " "; 
}

//获取 该「玩家」群昵称（绘制战榜图会用到）
public static String 昵称(String qun, String uin) {
    String 昵称 = getMemberName(qun, uin);
    if (昵称.length() > 5) {
        昵称 = 昵称.substring(0, 5) + "… ";
    }
    return 昵称 + " "; 
}

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// 处理 「签到」指令，以及连签天数达标积分奖励
public void 签到(String qun, String uin) {
    String 配置名 = qun + "/" + uin + "/";
    String 上次签到 = getString(配置名 + "/我的签到", "签到日期");
    String 玩家名 = "[AtQQ="+uin+"]  ";
    int 连签 = getInt(配置名 + "我的签到", "连续签到天数", 0);
    int 累签 = getInt(配置名 + "我的签到", "累计签到天数", 0);
    long 积分 = 文转(getString(配置名 + "/我的资产", "积分"));
    long 金币 = 文转(getString(配置名 + "/我的资产", "金币")); 

    //将当前日期转为 yyyy-mm-dd 格式
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String todayStr = today.format(formatter);

    // 判断今天是否已签到
    if (todayStr.equals(上次签到)) {
        消息内容 = 玩家名 + "今天已签到过了，请明天再来！";
        发送(qun, 消息内容,true);
        return; // 返回消息内容，而不是直接 return
    }

    long 积分变化;
    long 金币变化 = 150; // 每天签到奖励

    // 判断是否首次签到
    if (上次签到 == null || 上次签到.isEmpty()) {
        积分变化 = 20000;
        连签 = 1;
    } else {
        // 解析上次签到日期
        LocalDate lastSignDate = LocalDate.parse(上次签到, formatter);
        // 计算预期的上次签到日期（一般为“昨天”）
        LocalDate expectedDate = today.minusDays(1);
        // 判断是否连续签到
        if (lastSignDate.equals(expectedDate)) {
            连签++;
            // 处理连签天数超过30天的情况
            if (连签 > 30) {
                连签 = 1;
            }
            积分变化 = 20000 + 350 * 连签;
        } else {
            连签 = 1;
            积分变化 = 20000;
        }
    }

    // 连签天数达标奖励
    long 积分奖励 = 0;
    long 金币奖励 = 0; 

    if (连签 <= 4) {
        积分奖励 = 0; 金币奖励 = 0;
    } else if (连签 == 5) {
        积分奖励 = 10000000; 金币奖励 = 1000; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "\n连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    } else if (连签 == 10) {
        积分奖励 = 100000000; 金币奖励 = 2000; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "\n连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    } else if (连签 == 15) {
        积分奖励 = 150000000; 金币奖励 = 4500; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "\n连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    } else if (连签 == 20) {
        积分奖励 = 200000000; 金币奖励 = 7000; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    } else if (连签 == 25) {
        积分奖励 = 500000000; 金币奖励 = 10000; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "\n连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    } else if (连签 == 30) {
        积分奖励 = 1000000000; 金币奖励 = 15000; 
        积分变化 += 积分奖励; 金币变化 += 金币奖励;
        发送(qun, 玩家名 + "\n连续签到" + 连签 + "天，恭喜你获得连签天数达标奖励！\n💠积分：+" +积分奖励+ "\n🪙金币：+" + 金币奖励,true);
    }

    // 计算总积分
    积分 = 加(积分, 积分变化);
    // 计算总金币
    金币 = 加(金币, 金币变化);

    // 更新配置文件
    putString(配置名 + "/我的资产", "积分", 转文(积分));
    putString(配置名 + "/我的资产", "金币", 转文(金币));
    putString(配置名 + "/我的签到", "签到日期", todayStr);
    putInt(配置名 + "我的签到", "连续签到天数", 连签);
    putInt(配置名 + "我的签到", "累计签到天数", 累签 + 1);
    
    // 解码预设图片
    Bitmap BGimg = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/签到背景.png");
    Bitmap BGsy = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/签到底图.png");
    Bitmap WJtx = BitmapFactory.decodeFile(AppPath + "/缓存/头像/" + uin + ".png");

    // 缩放预设图片
    BGimg = Bitmap.createScaledBitmap(BGimg, 785, 350, true);
    BGsy = Bitmap.createScaledBitmap(BGsy, 785, 350, true);
    WJtx = Bitmap.createScaledBitmap(WJtx, 245, 245, true);

    // 创建一个透明Bitmap
    Bitmap Background = Bitmap.createBitmap(785, 350, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(Background);
    canvas.drawColor(Color.parseColor("#00000000"));

    // 绘制预设图片到Bitmap Background
    canvas.drawBitmap(BGimg, 0, 0, null);
    canvas.drawBitmap(BGsy, 0, 0, null);
    canvas.drawBitmap(WJtx, 38, 24, null);

    // 绘制玩家信息
    Paint paint = new Paint();
    paint.setTextSize(24);
    paint.setColor(Color.parseColor("#ffffff"));
    
    // 定义阴影的偏移量数组
    int[] shadowX = {1, -1};
    int[] shadowY = {1, -1};
    int[] shadowColors = {Color.parseColor("#aaff0000"), Color.parseColor("#aa00ffd4")};
    
    // 循环绘制带有不同阴影效果的玩家信息文本
    for (int i = 0; i < shadowX.length; i++) {
        paint.setShadowLayer(1, shadowX[i], shadowY[i], shadowColors[i]);
        canvas.drawText("昵称："+玩家名(qun,uin), 41, 292, paint);
        canvas.drawText("账号：" + uin, 41, 322, paint);
    }
    
    // 绘制日期文本(做水印用,主要防止图片过期)
    Paint newPaint = new Paint();
    newPaint.setTextSize(16);
    newPaint.setColor(Color.parseColor("#25ffffff"));
    canvas.drawText(getTodayDate(2), 471, 330, newPaint);
    
    // 绘制签到相关文案
    Paint textpaint = new Paint();
    textpaint.setTextSize(36);
    textpaint.setColor(Color.parseColor("#ffffff"));
    textpaint.setTypeface(Typeface.DEFAULT_BOLD); //字体加粗
    
    // 循环绘制带有不同阴影效果的签到相关文本
    for (int i = 0; i < shadowX.length; i++) {
        textpaint.setShadowLayer(1, shadowX[i], shadowY[i], shadowColors[i]);
        canvas.drawText("签到成功！", 382, 75, textpaint);
        canvas.drawText("积分：+"+(积分变化-积分奖励), 382, 125, textpaint);
        canvas.drawText("金币：+"+(金币变化-金币奖励), 382, 175, textpaint);
        canvas.drawText("累签："+(累签+1), 382, 225, textpaint);
        canvas.drawText("连签："+连签+" / 30", 382, 275, textpaint);
    }

    //保存为图片并发送
    saveBitmap2(Background, AppPath + "/缓存/其他/" + uin + "_签到.png");
    发送(qun, "[PicUrl="+AppPath + "/缓存/其他/" + uin + "_签到.png]",false);
}

    // 计算战斗力 （生命/10 加 攻+防 加 智力*20）
    private static long 计算战力(String qun, String uin, long number)
    {
      String 配置名=qun+"/"+uin+"/";
      String 配置名称=配置名+"宠物小窝/位置_"+number;
      long 当前生命 = 文转(getString(配置名称, "当前生命"));
      long 攻击 = 文转(getString(配置名称, "攻击"));
      long 防御 = 文转(getString(配置名称, "防御"));
      long 智力 = 文转(getString(配置名称, "智力"));
        return 当前生命/10+攻击+防御+智力*20;
    }
    
    // 心情转换方法
    public static String 心情转换(String qun, String uin, long number)
    {
        String configName = qun + "/" + uin + "/";
        long mood = 文转(getString(configName + "/宠物小窝/位置_" + number, "心情"));

        // 假设心情最大值为 100，总共显示 5 个符号
        int starCount = 0;
        if (mood > 0) {
            starCount = (int) (mood >= 15? (mood * 5 / 100.0) : 1);
        }
        int totalSymbols = 5;
        StringBuilder symbolBuilder = new StringBuilder();
        if (mood == 0) {
            for (int i = 0; i < totalSymbols; i++) {
                symbolBuilder.append("〼");
            }
        } else {
            for (int i = 0; i < starCount; i++) {
                symbolBuilder.append("★");
            }
            for (int i = starCount; i < totalSymbols; i++) {
                symbolBuilder.append("☆");
            }
        }

        // 返回转换后的符号
        String moodSymbol = symbolBuilder.toString();
        return moodSymbol;
    }
    
    
    // 计算升级提升了多少战力
    public static long 升战力(long attack, long defense, long life) {
       return life+attack+defense;
    }    
    
//按提供的百分比来给宠物回血，如果超出生命上限则强制与生命上限一致
public static void 宠物回血(String qun, String uin, double regen)
{
    String 配置名 = qun + "/" + uin + "/";
    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 生命上限 = 文转(getString(配置名称, "生命上限"));

    // 计算恢复的生命值 取整
    double 回血百分比= regen;
    long 恢复的生命值 = Math.round(生命上限 * 回血百分比);

    // 计算恢复后的当前生命
    long 新的当前生命 = 当前生命 + 恢复的生命值;

    // 判断是否超过生命上限
    if (新的当前生命 > 生命上限) {
        新的当前生命 = 生命上限;
    }

    // 更新当前生命
    putString(配置名称, "当前生命", 转文(新的当前生命));
}    
    
// 宠物升级方法，可批量升级
public static void 宠物升级(String qun, String uin, long 升级次数) {
    String 配置名 = qun + "/" + uin + "/";
    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";

    long 当前经验 = 文转(getString(配置名称, "当前经验", "0"));
    long 下级所需经验 = 文转(getString(配置名称, "下级所需经验", "100")); // 初始默认100
    long 等级 = 文转(getString(配置名称, "等级", "1")); // 初始默认1级
    long 攻击 = 文转(getString(配置名称, "攻击", "10"));
    long 防御 = 文转(getString(配置名称, "防御", "5"));
    long 生命上限 = 文转(getString(配置名称, "生命上限", "100"));

    // 记录提升的属性
    long 总提升攻击 = 0;
    long 总提升防御 = 0;
    long 总提升生命上限 = 0;
    int 总等级提升 = 0;
    long 消耗的经验 = 0;

    // 计算可以升级的总次数（分阶段经验公式，300级后提升难度）
    long 剩余经验 = 当前经验;
    while (剩余经验 >= 下级所需经验 && 升级次数 > 0) {
        剩余经验 -= 下级所需经验;
        消耗的经验 += 下级所需经验;
        等级++;
        升级次数--;
        总等级提升++;

        // 经验增长公式：300级前分段增长，300级后提升难度
        if (等级 <= 10) {
            // 1-10级：缓慢增长（基础100，每级+50）
            下级所需经验 = 100 + (等级 - 1) * 50;
        } else if (等级 <= 30) {
            // 11-30级：中等增长（基础600，每级+200）
            下级所需经验 = 600 + (等级 - 10) * 200;
        } else if (等级 <= 50) {
            // 31-50级：较快增长（基础4600，每级+500）
            下级所需经验 = 4600 + (等级 - 30) * 500;
        } else if (等级 <= 300) {
            // 51-300级：稳步增长（基础14600，每级+1000）
            下级所需经验 = 14600 + (等级 - 50) * 1000;
        } else {
            // 300级以上：保持后期难度
            下级所需经验 = (long) Math.floor((等级 + 35) * 1750 * 5 + 下级所需经验);
        }
    }

    // 一次性提升属性
    for (int i = 0; i < 总等级提升; i++) {
        long 提升攻击 = (long) Math.floor((等级 + 20) * 40.25 * 4);
        long 提升防御 = (long) Math.floor((等级 + 20) * 38.45 * 2.5);
        long 提升生命上限 = (long) Math.floor((等级 + 20) * 44.25 * 30);

        攻击 += 提升攻击;
        防御 += 提升防御;
        生命上限 += 提升生命上限;

        总提升攻击 += 提升攻击;
        总提升防御 += 提升防御;
        总提升生命上限 += 提升生命上限;
    }

    // 更新属性
    putString(配置名称, "攻击", 转文(攻击));
    putString(配置名称, "防御", 转文(防御));
    putString(配置名称, "生命上限", 转文(生命上限));
    putString(配置名称, "等级", 转文(等级));
    putString(配置名称, "当前经验", 转文(剩余经验));
    putString(配置名称, "下级所需经验", 转文(下级所需经验));

    Random random = new Random();
    String[] 装饰符 = {"◆", "★", "•", "▲", "❏", "ღ", "₪"};
    String 宠物名 = getString(配置名称, "昵称", "无名宠物");
    String 玩家名 = "[AtQQ=" + uin + "]  ";
    String 装饰 = 装饰符[random.nextInt(装饰符.length)];
    long 生命值 = (long) Math.floor(总提升生命上限 / 10);
    long 战力 = 升战力(总提升攻击, 总提升防御, 生命值);

    // 生成报告
    StringBuilder Reportr = new StringBuilder();
    Reportr.append(玩家名);
    Reportr.append("  您的宠物【" + 宠物名 + "】成功升级至 LV·").append(等级).append("！").append("\n------------------\n");
    Reportr.append(装饰).append(" 等级提升：+").append(数值转(总等级提升)).append("\n");
    Reportr.append(装饰).append(" 经验减少：-").append(数值转(消耗的经验)).append("\n");
    Reportr.append(装饰).append(" 生命提升：+").append(数值转(总提升生命上限)).append("\n");
    Reportr.append(装饰).append(" 攻击提升：+").append(数值转(总提升攻击)).append("\n");
    Reportr.append(装饰).append(" 防御提升：+").append(数值转(总提升防御)).append("\n");
    Reportr.append(装饰).append(" 战力提升：+").append(数值转(战力)).append("\n");

    String 报告 = Reportr.toString();
    发送(qun, 报告, true);
    宠物回血(qun, uin, 1); // 恢复血量至100%
}


// 宠物进化方法（单次进化）
public static void 宠物进化(String qun, String uin) 
{
        String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
        String 玩家名 = "[AtQQ="+uin+"]  ";
        String 级别 = getString(配置名称, "级别"); //宠物品质
        String 宠物名 = getString(配置名称, "昵称");
        String 原名 = getString(配置名称, "宠物名字");
        String 易容 = getString(配置名称, "是否易容"); // 判断是否易容了
        String 上传图 = getString(配置名称, "上传图片"); // 易容后的图片标记
        String petImagePath =AppPath+"/目录/图片/宠物/"+原名+".jpg";
         
            if (易容.equals("是")) {
                String petImagePath2 = AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png";
               File file = new File(petImagePath2);
                if (file.exists()) {
                    petImagePath = petImagePath2;
                } else {
                    petImagePath = AppPath + "/目录/图片/其他/默认.png";
                }
            } else if(!petMap.containsKey(原名)&&级别.equals("定制")){
                String petImagePath2 = AppPath + "/目录/图片/宠物/定制/" + 原名 + ".jpg";
                File file = new File(petImagePath2);
                if (file.exists()) {
                    petImagePath = petImagePath2;
                } else {
                    petImagePath = AppPath + "/目录/图片/其他/默认.png";
                }
            }else{
              File file = new File(petImagePath);
               if (!file.exists()) {
                petImagePath = AppPath + "/目录/图片/其他/默认.png";
               }
            }
        String 阶段 = getString(配置名称, "阶段");
        String 级别 = getString(配置名称, "级别");
        long 等级 = 文转(getString(配置名称, "等级"));
        long 进化层次 = 文转(getString(配置名称, "进化层次"));
        long 进化上限 = 文转(getString(配置名称, "进化上限"));
        long 攻击 = 文转(getString(配置名称, "攻击"));
        long 防御 = 文转(getString(配置名称, "防御"));
        long 智力 = 文转(getString(配置名称, "智力"));
        long 精力上限 = 文转(getString(配置名称, "精力上限"));
        long 生命上限 = 文转(getString(配置名称, "生命上限"));
        long 进化目标 = 进化层次 + 1;
           if (进化目标 > 进化上限) {
              进化目标 = 进化上限;
           }
        long 级别限制=getLevelByEvolutionLevel(进化目标);

        if (进化层次 == 进化上限) {
            // 进化层次已经达到上限，发送提示信息
            String 提示 = 玩家名 + " 你的【" + 宠物名 + "】已达到〔"+级别+"〕品质的进化上限，无法继续进化！\n————————————\n◆指令：进化流程";
            发送(qun, 提示,false);
        }
        else if (等级 >= 级别限制 && 进化层次 < 进化上限) 
        {
            long[] 进化属性 = evolutionMap.get((int)进化目标);
            long 攻=进化属性[0];
            long 防=进化属性[1];
            long 智=进化属性[2];
            long 精=进化属性[3];
            long 血=进化属性[4];
            攻击 = 攻击+攻;
            防御 = 防御+防;
            智力 = 智力+智;
            精力上限 = 精力上限+精;
            生命上限 = 生命上限+血;
            //更新属性
            putString(配置名称, "进化层次", 转文(进化目标));
            putString(配置名称, "阶段", 宠物阶段(进化目标, 等级));
            putString(配置名称, "攻击", 转文(攻击));
            putString(配置名称, "防御", 转文(防御));
            putString(配置名称, "智力", 转文(智力));
            putString(配置名称, "精力上限", 转文(精力上限));
            putString(配置名称, "生命上限", 转文(生命上限));
            putString(配置名称, "是否易容", "否"); 
            //↑进化后会强制使用原宠物图片

         long 战力=智*20+攻+防+血/10;
         // 使用 StringBuilder 进行字符串拼接
         StringBuilder resultBuilder = new StringBuilder();
        if ("破壳期".equals(阶段)) {
            // 调用破壳动图方法
            String eggShellPath = AppPath + "/目录/图片/其他/破壳.png";
            String outputPath = AppPath + "/缓存/宠物/"+uin+"_宠物破壳图.png";
            破壳图(eggShellPath, petImagePath, outputPath);
            resultBuilder.append("[PicUrl="+outputPath+"]"+玩家名+" 你的【"+原名+"】成功破壳啦！\n");
        }else{
          resultBuilder.append("[PicUrl="+petImagePath+"]"+玩家名+" 你的【"+原名+"】");
        }
        resultBuilder.append("成功进化至[Lv.").append(等级).append("·").append(宠物阶段(进化目标, 等级)).append("]\n");
         resultBuilder.append("————————————\n");
         resultBuilder.append("•智力：+").append(智).append("\n");
         resultBuilder.append("•精力：+").append(精).append("\n");
         resultBuilder.append("•攻击：+").append(攻).append("\n");
         resultBuilder.append("•防御：+").append(防).append("\n");
         resultBuilder.append("•生命：+").append(血).append("\n");
         resultBuilder.append("————————————\n");
         resultBuilder.append("•战力：+").append(数值转(战力));

           String result = resultBuilder.toString();
           发送(qun, result,false);
           宠物回血(qun,uin,1); //回血100%
        } else {
            List evolutionInfoList = new ArrayList();
            for (int i = 0; i < evolutionLevels.length; i++) {
              if (evolutionLevels[i] <= 进化上限) {
                  String info = "["+evolutionLevels[i] + "]-Lv." + levels[i] + "-" + stages[i];
                  evolutionInfoList.add(info);
              }
           }

          StringBuilder result = new StringBuilder();
          for (String info : evolutionInfoList) {
             result.append("\n•"+info).append("↓");
          }
          //去掉最后面的符号
          if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
           String 报告=玩家名+" \n你的【"+宠物名+"】等级低于[lv."+级别限制+"]，无法进化！以下是「"+级别+"」级别宠物的进化流程：\n————————————"+result.toString()+"\n————————————\n◆进化层次-等级-阶段";
           发送(qun, 报告,false);
       }
}



// 宠物进化方法（支持多次进化）
public static void 宠物进化(String qun, String uin, long times) {
    // 校验次数有效性
    if (times <= 0) {
        String 提示 = 玩家名(qun, uin) + " 进化次数必须大于0！";
        发送(qun, 提示, false);
        return;
    }
    
    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
    String 原阶段 = getString(配置名称, "阶段"); //这是开始进化前的阶段
    
    // 定义统计变量
    long totalAttack = 0; //统计提升攻击
    long totalDefense = 0; //统计防御
    long totalIntellect = 0; //统计智力
    long totalStamina = 0; //统计精力
    long totalHealth = 0; //统计生命
    int successCount = 0; //进化成功次数
    
    // 循环执行进化逻辑
    for (long i = 0; i < times; i++) {
        String 玩家名 = "[AtQQ="+uin+"]  ";
        String 阶段 = getString(配置名称, "阶段");
        String 级别 = getString(配置名称, "级别");
        String 宠物名 = getString(配置名称, "昵称");
        long 等级 = 文转(getString(配置名称, "等级"));
        long 进化层次 = 文转(getString(配置名称, "进化层次"));
        long 进化上限 = 文转(getString(配置名称, "进化上限"));
        long 攻击 = 文转(getString(配置名称, "攻击"));
        long 防御 = 文转(getString(配置名称, "防御"));
        long 智力 = 文转(getString(配置名称, "智力"));
        long 精力上限 = 文转(getString(配置名称, "精力上限"));
        long 生命上限 = 文转(getString(配置名称, "生命上限"));
        long 进化目标 = 进化层次 + 1;
        if (进化目标 > 进化上限) {
            进化目标 = 进化上限;
        }
        long 级别限制 = getLevelByEvolutionLevel(进化目标);

        if (进化层次 == 进化上限) {
            // 进化层次已达上限
            String 提示 = 玩家名 + " 你的【" + 宠物名 + "】已达到〔" + 级别 + "〕品质的进化上限，无法继续进化！\n————————————\n◆指令：进化流程";
            发送(qun, 提示, false);
            break; // 终止后续循环
        } 
        else if (等级 >= 级别限制 && 进化层次 < 进化上限) {
            long[] 进化属性 = evolutionMap.get((int)进化目标);
            long 攻 = 进化属性[0];
            long 防 = 进化属性[1];
            long 智 = 进化属性[2];
            long 精 = 进化属性[3];
            long 血 = 进化属性[4];
            攻击 = 攻击 + 攻;
            防御 = 防御 + 防;
            智力 = 智力 + 智;
            精力上限 = 精力上限 + 精;
            生命上限 = 生命上限 + 血;
            // 更新属性
            putString(配置名称, "进化层次", 转文(进化目标));
            putString(配置名称, "阶段", 宠物阶段(进化目标, 等级));
            putString(配置名称, "攻击", 转文(攻击));
            putString(配置名称, "防御", 转文(防御));
            putString(配置名称, "智力", 转文(智力));
            putString(配置名称, "精力上限", 转文(精力上限));
            putString(配置名称, "生命上限", 转文(生命上限));
            putString(配置名称, "是否易容", "否"); 

            // 统计进化属性（替代原发送逻辑）
            totalAttack += 攻;
            totalDefense += 防;
            totalIntellect += 智;
            totalStamina += 精;
            totalHealth += 血;
            successCount++;
            
            宠物回血(qun, uin, 1); // 回血100%
        } else {
            // 等级不足，终止后续进化
            List evolutionInfoList = new ArrayList();
            for (int j = 0; j < evolutionLevels.length; j++) {
                if (evolutionLevels[j] <= 进化上限) {
                    String info = "[" + evolutionLevels[j] + "]-Lv." + levels[j] + "-" + stages[j];
                    evolutionInfoList.add(info);
                }
            }

            StringBuilder result = new StringBuilder();
            for (String info : evolutionInfoList) {
                result.append("\n•" + info).append("↓");
            }
            if (result.length() > 0) {
                result.deleteCharAt(result.length() - 1);
            }
            String 报告 = 玩家名 + " \n你的【" + 宠物名 + "】等级低于[lv." + 级别限制 + "]，无法进化！以下是「" + 级别 + "」级别宠物的进化流程：\n————————————" + result.toString() + "\n————————————\n◆进化层次-等级-阶段";
            发送(qun, 报告, false);
            break; // 终止后续循环
        }
    }
    
    // 循环结束后统一发送统计结果
    if (successCount > 0) {
        String 玩家名 = "[AtQQ="+uin+"]  ";
        String 现阶段 = getString(配置名称, "阶段"); //这是进化后的阶段
        String 级别 = getString(配置名称, "级别"); //宠物品质
        String 宠物名 = getString(配置名称, "昵称");
        String 原名 = getString(配置名称, "宠物名字");
        String 易容 = getString(配置名称, "是否易容"); // 判断是否易容了
        String 上传图 = getString(配置名称, "上传图片"); // 易容后的图片标记
        String petImagePath =AppPath+"/目录/图片/宠物/"+原名+".jpg";
            if (易容.equals("是")) {
                String petImagePath2 = AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png";
               File file = new File(petImagePath2);
                if (file.exists()) {
                    petImagePath = petImagePath2;
                } else {
                    petImagePath = AppPath + "/目录/图片/其他/默认.png";
                }
            } else if(!petMap.containsKey(原名)&&级别.equals("定制")){
                String petImagePath2 = AppPath + "/目录/图片/宠物/定制/" + 原名 + ".jpg";
                File file = new File(petImagePath2);
                if (file.exists()) {
                    petImagePath = petImagePath2;
                } else {
                    petImagePath = AppPath + "/目录/图片/其他/默认.png";
                }
            } else {
               File file = new File(petImagePath);
               if (!file.exists()) {
                 petImagePath = AppPath + "/目录/图片/其他/默认.png";
               }            
            }

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("[PicUrl=" + petImagePath + "]" + 玩家名 + " 你的【" + 原名 + "】成功完成 " + successCount + " 次进化！\n▲阶段变化："+原阶段+" ➻ "+现阶段+"\n");
        resultBuilder.append("————————————\n");
        resultBuilder.append("•智力总计提升：").append(数值转(totalIntellect)).append("\n");
        resultBuilder.append("•精力总计提升：").append(数值转(totalStamina)).append("\n");
        resultBuilder.append("•攻击总计提升：").append(数值转(totalAttack)).append("\n");
        resultBuilder.append("•防御总计提升：").append(数值转(totalDefense)).append("\n");
        resultBuilder.append("•生命总计提升：").append(数值转(totalHealth)).append("\n");
        resultBuilder.append("————————————\n");
        
        long 战力 = totalIntellect * 20 + totalAttack + totalDefense + totalHealth / 10;
        resultBuilder.append("•总战力提升：").append(数值转(战力));
        
        String result = resultBuilder.toString();
        发送(qun, result, false);
    }
}

 
