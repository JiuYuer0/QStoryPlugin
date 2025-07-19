final String PLUGIN_NAME = "金币系统";
final String DATA_DIR = AppPath + "/金币系统/数据/";
final long WORK_COOLDOWN = 21600000;
final long ROB_COOLDOWN = 10800000;
final long TRANSFER_COOLDOWN = 3600000;
final long FISHING_COOLDOWN = 3600000;
final long ALMS_COOLDOWN = 86400000;
final long REDPACKET_COOLDOWN = 1800000;
final long DUNGEON_COOLDOWN = 43200000;
final long STOCK_COOLDOWN = 28800000;
final long PET_FEED_COOLDOWN = 43200000;
final long HOUSE_INCOME_COOLDOWN = 86400000;
final long ARENA_COOLDOWN = 21600000;
final long BANK_INTERVAL = 86400000;
final long MARKET_REFRESH = 3600000;
final long EQUIP_UPGRADE_COOLDOWN = 43200000;
final long PET_BATTLE_COOLDOWN = 21600000;
final long TEAM_DUNGEON_COOLDOWN = 172800000;
final long DAILY_TASK_RESET = 86400000;
final long LOTTERY_SHOP_COOLDOWN = 3600000;

java.io.File dataDir = new java.io.File(DATA_DIR);
if (!dataDir.exists()) {
    dataDir.mkdirs();
}

java.util.Map weapons = new java.util.HashMap();
{
    java.util.Map stick = new java.util.HashMap();
    stick.put("price", 300);
    stick.put("attack", 5);
    stick.put("defense", 0);
    stick.put("maxLevel", 5);
    weapons.put("木棍", stick);
    
    java.util.Map dagger = new java.util.HashMap();
    dagger.put("price", 800);
    dagger.put("attack", 15);
    dagger.put("defense", 5);
    dagger.put("maxLevel", 10);
    weapons.put("匕首", dagger);
    
    java.util.Map pistol = new java.util.HashMap();
    pistol.put("price", 2000);
    pistol.put("attack", 30);
    pistol.put("defense", 10);
    pistol.put("maxLevel", 15);
    weapons.put("手枪", pistol);
    
    java.util.Map shotgun = new java.util.HashMap();
    shotgun.put("price", 5000);
    shotgun.put("attack", 50);
    shotgun.put("defense", 15);
    shotgun.put("maxLevel", 20);
    weapons.put("散弹枪", shotgun);
    
    java.util.Map armor = new java.util.HashMap();
    armor.put("price", 3000);
    armor.put("attack", 0);
    armor.put("defense", 30);
    armor.put("maxLevel", 15);
    weapons.put("铠甲", armor);
    
    java.util.Map bulletproof = new java.util.HashMap();
    bulletproof.put("price", 4500);
    bulletproof.put("attack", 0);
    bulletproof.put("defense", 45);
    bulletproof.put("maxLevel", 20);
    weapons.put("防弹衣", bulletproof);
    
    java.util.Map lightsaber = new java.util.HashMap();
    lightsaber.put("price", 10000);
    lightsaber.put("attack", 70);
    lightsaber.put("defense", 25);
    lightsaber.put("maxLevel", 25);
    weapons.put("激光剑", lightsaber);
    
    java.util.Map bazooka = new java.util.HashMap();
    bazooka.put("price", 15000);
    bazooka.put("attack", 100);
    bazooka.put("defense", 10);
    bazooka.put("maxLevel", 30);
    weapons.put("火箭筒", bazooka);
    
    java.util.Map trident = new java.util.HashMap();
    trident.put("price", 25000);
    trident.put("attack", 120);
    trident.put("defense", 40);
    trident.put("maxLevel", 35);
    weapons.put("三叉戟", trident);
    
    java.util.Map dragonSword = new java.util.HashMap();
    dragonSword.put("price", 40000);
    dragonSword.put("attack", 180);
    dragonSword.put("defense", 60);
    dragonSword.put("maxLevel", 40);
    weapons.put("龙之剑", dragonSword);
    
    java.util.Map photonBlaster = new java.util.HashMap();
    photonBlaster.put("price", 60000);
    photonBlaster.put("attack", 250);
    photonBlaster.put("defense", 80);
    photonBlaster.put("maxLevel", 45);
    weapons.put("光子冲击炮", photonBlaster);
    
    java.util.Map titanShield = new java.util.HashMap();
    titanShield.put("price", 35000);
    titanShield.put("attack", 20);
    titanShield.put("defense", 150);
    titanShield.put("maxLevel", 40);
    weapons.put("泰坦之盾", titanShield);
    
    java.util.Map venomSpear = new java.util.HashMap();
    venomSpear.put("price", 30000);
    venomSpear.put("attack", 150);
    venomSpear.put("defense", 50);
    venomSpear.put("maxLevel", 35);
    weapons.put("毒液长矛", venomSpear);
    
    java.util.Map celestialBow = new java.util.HashMap();
    celestialBow.put("price", 45000);
    celestialBow.put("attack", 200);
    celestialBow.put("defense", 70);
    celestialBow.put("maxLevel", 40);
    weapons.put("天界神弓", celestialBow);
    
    java.util.Map necronomicon = new java.util.HashMap();
    necronomicon.put("price", 100000);
    necronomicon.put("attack", 300);
    necronomicon.put("defense", 100);
    necronomicon.put("maxLevel", 50);
    weapons.put("死灵之书", necronomicon);
    
    java.util.Map infinityGauntlet = new java.util.HashMap();
    infinityGauntlet.put("price", 200000);
    infinityGauntlet.put("attack", 500);
    infinityGauntlet.put("defense", 200);
    infinityGauntlet.put("maxLevel", 60);
    weapons.put("无限手套", infinityGauntlet);
}

java.util.Map items = new java.util.HashMap();
{
    java.util.Map expCard = new java.util.HashMap();
    expCard.put("price", 500);
    expCard.put("type", "buff");
    items.put("经验卡", expCard);
    
    java.util.Map protectShield = new java.util.HashMap();
    protectShield.put("price", 1000);
    protectShield.put("type", "shield");
    items.put("护盾", protectShield);
    
    java.util.Map fishingRod = new java.util.HashMap();
    fishingRod.put("price", 800);
    fishingRod.put("type", "tool");
    items.put("高级鱼竿", fishingRod);
    
    java.util.Map lottery = new java.util.HashMap();
    lottery.put("price", 200);
    lottery.put("type", "gamble");
    items.put("彩票", lottery);
    
    java.util.Map revivePotion = new java.util.HashMap();
    revivePotion.put("price", 1500);
    revivePotion.put("type", "consumable");
    items.put("复活药水", revivePotion);
    
    java.util.Map petFood = new java.util.HashMap();
    petFood.put("price", 300);
    petFood.put("type", "pet");
    items.put("宠物粮", petFood);
    
    java.util.Map upgradeStone = new java.util.HashMap();
    upgradeStone.put("price", 1000);
    upgradeStone.put("type", "upgrade");
    items.put("强化石", upgradeStone);
    
    java.util.Map treasureMap = new java.util.HashMap();
    treasureMap.put("price", 5000);
    treasureMap.put("type", "special");
    items.put("藏宝图", treasureMap);
    
    java.util.Map festivalToken = new java.util.HashMap();
    festivalToken.put("price", 200);
    festivalToken.put("type", "event");
    items.put("节日代币", festivalToken);
    
    java.util.Map houseDecor = new java.util.HashMap();
    houseDecor.put("price", 3000);
    houseDecor.put("type", "decoration");
    items.put("家园装饰", houseDecor);
    
    java.util.Map insurance = new java.util.HashMap();
    insurance.put("price", 5000);
    insurance.put("type", "bank");
    items.put("存款保险", insurance);
    
    java.util.Map blackMarketToken = new java.util.HashMap();
    blackMarketToken.put("price", 1000);
    blackMarketToken.put("type", "special");
    items.put("黑市令牌", blackMarketToken);
    
    java.util.Map dailyTaskReset = new java.util.HashMap();
    dailyTaskReset.put("price", 100);
    dailyTaskReset.put("type", "special");
    items.put("任务重置券", dailyTaskReset);
}

java.util.Map pets = new java.util.HashMap();
{
    java.util.Map dog = new java.util.HashMap();
    dog.put("price", 5000);
    dog.put("income", 50);
    dog.put("bonus", "打工");
    dog.put("attack", 20);
    dog.put("defense", 30);
    dog.put("hp", 100);
    pets.put("工作犬", dog);
    
    java.util.Map cat = new java.util.HashMap();
    cat.put("price", 3000);
    cat.put("income", 30);
    cat.put("bonus", "钓鱼");
    cat.put("attack", 15);
    cat.put("defense", 20);
    cat.put("hp", 80);
    pets.put("招财猫", cat);
    
    java.util.Map dragon = new java.util.HashMap();
    dragon.put("price", 10000);
    dragon.put("income", 100);
    dragon.put("bonus", "副本");
    dragon.put("attack", 50);
    dragon.put("defense", 60);
    dragon.put("hp", 200);
    pets.put("守护龙", dragon);
    
    java.util.Map rabbit = new java.util.HashMap();
    rabbit.put("price", 2000);
    rabbit.put("income", 25);
    rabbit.put("bonus", "签到");
    rabbit.put("attack", 10);
    rabbit.put("defense", 15);
    rabbit.put("hp", 60);
    pets.put("幸运兔", rabbit);
    
    java.util.Map phoenix = new java.util.HashMap();
    phoenix.put("price", 50000);
    phoenix.put("income", 200);
    phoenix.put("bonus", "战斗");
    phoenix.put("attack", 80);
    phoenix.put("defense", 70);
    phoenix.put("hp", 250);
    pets.put("凤凰", phoenix);
    
    java.util.Map unicorn = new java.util.HashMap();
    unicorn.put("price", 40000);
    unicorn.put("income", 150);
    unicorn.put("bonus", "市场");
    unicorn.put("attack", 60);
    unicorn.put("defense", 80);
    unicorn.put("hp", 220);
    pets.put("独角兽", unicorn);
}

java.util.Map houses = new java.util.HashMap();
{
    java.util.Map cottage = new java.util.HashMap();
    cottage.put("price", 10000);
    cottage.put("income", 100);
    cottage.put("slots", 3);
    houses.put("小木屋", cottage);
    
    java.util.Map villa = new java.util.HashMap();
    villa.put("price", 50000);
    villa.put("income", 500);
    villa.put("slots", 5);
    houses.put("豪华别墅", villa);
    
    java.util.Map castle = new java.util.HashMap();
    castle.put("price", 200000);
    castle.put("income", 2000);
    castle.put("slots", 8);
    houses.put("皇家城堡", castle);
    
    java.util.Map mansion = new java.util.HashMap();
    mansion.put("price", 150000);
    mansion.put("income", 1200);
    mansion.put("slots", 6);
    houses.put("海滨庄园", mansion);
    
    java.util.Map treehouse = new java.util.HashMap();
    treehouse.put("price", 80000);
    treehouse.put("income", 300);
    treehouse.put("slots", 4);
    houses.put("精灵树屋", treehouse);
}

java.util.Map careers = new java.util.HashMap();
{
    java.util.Map warrior = new java.util.HashMap();
    warrior.put("bonus", "打劫");
    warrior.put("effect", "打劫成功率提高20%");
    warrior.put("price", 5000);
    careers.put("战士", warrior);
    
    java.util.Map merchant = new java.util.HashMap();
    merchant.put("bonus", "打工");
    merchant.put("effect", "打工收入提高30%");
    merchant.put("price", 5000);
    careers.put("商人", merchant);
    
    java.util.Map fisherman = new java.util.HashMap();
    fisherman.put("bonus", "钓鱼");
    fisherman.put("effect", "钓鱼收益提高40%");
    fisherman.put("price", 5000);
    careers.put("渔夫", fisherman);
    
    java.util.Map banker = new java.util.HashMap();
    banker.put("bonus", "银行");
    banker.put("effect", "银行利息提高50%");
    banker.put("price", 10000);
    careers.put("银行家", banker);
    
    java.util.Map investor = new java.util.HashMap();
    investor.put("bonus", "股票");
    investor.put("effect", "股票收益下限提高");
    investor.put("price", 15000);
    careers.put("投资人", investor);
    
    java.util.Map loanShark = new java.util.HashMap();
    loanShark.put("bonus", "贷款");
    loanShark.put("effect", "贷款利率降低30%");
    loanShark.put("price", 20000);
    careers.put("高利贷", loanShark);
}

java.util.Map fishTypes = new java.util.HashMap();
{
    java.util.Map smallFish = new java.util.HashMap();
    smallFish.put("min", 10);
    smallFish.put("max", 30);
    fishTypes.put("小黄鱼", smallFish);
    
    java.util.Map ribbonFish = new java.util.HashMap();
    ribbonFish.put("min", 20);
    ribbonFish.put("max", 50);
    fishTypes.put("带鱼", ribbonFish);
    
    java.util.Map tuna = new java.util.HashMap();
    tuna.put("min", 50);
    tuna.put("max", 100);
    fishTypes.put("金枪鱼", tuna);
    
    java.util.Map shark = new java.util.HashMap();
    shark.put("min", 100);
    shark.put("max", 200);
    fishTypes.put("鲨鱼", shark);
    
    java.util.Map goldenFish = new java.util.HashMap();
    goldenFish.put("min", 200);
    goldenFish.put("max", 500);
    fishTypes.put("金龙鱼", goldenFish);
    
    java.util.Map lobster = new java.util.HashMap();
    lobster.put("min", 150);
    lobster.put("max", 300);
    fishTypes.put("龙虾", lobster);
    
    java.util.Map crab = new java.util.HashMap();
    crab.put("min", 40);
    crab.put("max", 80);
    fishTypes.put("帝王蟹", crab);
    
    java.util.Map octopus = new java.util.HashMap();
    octopus.put("min", 70);
    octopus.put("max", 150);
    fishTypes.put("章鱼", octopus);
    
    java.util.Map squid = new java.util.HashMap();
    squid.put("min", 60);
    squid.put("max", 120);
    fishTypes.put("鱿鱼", squid);
    
    java.util.Map turtle = new java.util.HashMap();
    turtle.put("min", 80);
    turtle.put("max", 180);
    fishTypes.put("海龟", turtle);
    
    java.util.Map whale = new java.util.HashMap();
    whale.put("min", 300);
    whale.put("max", 600);
    fishTypes.put("鲸鱼", whale);
    
    java.util.Map seahorse = new java.util.HashMap();
    seahorse.put("min", 15);
    seahorse.put("max", 40);
    fishTypes.put("海马", seahorse);
    
    java.util.Map jellyfish = new java.util.HashMap();
    jellyfish.put("min", 25);
    jellyfish.put("max", 60);
    fishTypes.put("水母", jellyfish);
    
    java.util.Map stingray = new java.util.HashMap();
    stingray.put("min", 90);
    stingray.put("max", 180);
    fishTypes.put("魔鬼鱼", stingray);
    
    java.util.Map blueWhale = new java.util.HashMap();
    blueWhale.put("min", 500);
    blueWhale.put("max", 1000);
    fishTypes.put("蓝鲸", blueWhale);
    
    java.util.Map swordfish = new java.util.HashMap();
    swordfish.put("min", 120);
    swordfish.put("max", 250);
    fishTypes.put("剑鱼", swordfish);
    
    java.util.Map anglerfish = new java.util.HashMap();
    anglerfish.put("min", 80);
    anglerfish.put("max", 160);
    fishTypes.put("灯笼鱼", anglerfish);
    
    java.util.Map pufferfish = new java.util.HashMap();
    pufferfish.put("min", 50);
    pufferfish.put("max", 100);
    fishTypes.put("河豚", pufferfish);
    
    java.util.Map narwhal = new java.util.HashMap();
    narwhal.put("min", 200);
    narwhal.put("max", 400);
    fishTypes.put("独角鲸", narwhal);
    
    java.util.Map giantSquid = new java.util.HashMap();
    giantSquid.put("min", 400);
    giantSquid.put("max", 800);
    fishTypes.put("巨型乌贼", giantSquid);
    
    java.util.Map mantaRay = new java.util.HashMap();
    mantaRay.put("min", 150);
    mantaRay.put("max", 300);
    fishTypes.put("蝠鲼", mantaRay);
    
    java.util.Map nautilus = new java.util.HashMap();
    nautilus.put("min", 100);
    nautilus.put("max", 200);
    fishTypes.put("鹦鹉螺", nautilus);
    
    java.util.Map seaDragon = new java.util.HashMap();
    seaDragon.put("min", 300);
    seaDragon.put("max", 600);
    fishTypes.put("海龙", seaDragon);
    
    java.util.Map coralReef = new java.util.HashMap();
    coralReef.put("min", 200);
    coralReef.put("max", 500);
    fishTypes.put("珊瑚礁", coralReef);
    
    java.util.Map deepSeaVent = new java.util.HashMap();
    deepSeaVent.put("min", 1000);
    deepSeaVent.put("max", 2000);
    fishTypes.put("深海热泉", deepSeaVent);
    
    java.util.Map yeqi = new java.util.HashMap();
    yeqi.put("min", -100);
    yeqi.put("max", -100);
    fishTypes.put("夜七", yeqi);
    
    java.util.Map linjiang = new java.util.HashMap();
    linjiang.put("min", 1000);
    linjiang.put("max", 1000);
    fishTypes.put("临江", linjiang);
}

java.util.Map decorations = new java.util.HashMap();
{
    java.util.Map fountain = new java.util.HashMap();
    fountain.put("price", 5000);
    fountain.put("incomeBonus", 10);
    decorations.put("喷泉", fountain);
    
    java.util.Map garden = new java.util.HashMap();
    garden.put("price", 8000);
    garden.put("incomeBonus", 15);
    decorations.put("花园", garden);
    
    java.util.Map statue = new java.util.HashMap();
    statue.put("price", 12000);
    statue.put("incomeBonus", 20);
    decorations.put("雕像", statue);
    
    java.util.Map pool = new java.util.HashMap();
    pool.put("price", 15000);
    pool.put("incomeBonus", 25);
    decorations.put("游泳池", pool);
    
    java.util.Map observatory = new java.util.HashMap();
    observatory.put("price", 20000);
    observatory.put("incomeBonus", 30);
    decorations.put("天文台", observatory);
}

java.util.Map marketItems = new java.util.HashMap();
java.util.Map marketPrices = new java.util.HashMap();
long lastMarketRefresh = 0;

java.util.Map dailyTasks = new java.util.HashMap();
{
    java.util.Map workTask = new java.util.HashMap();
    workTask.put("name", "打工达人");
    workTask.put("goal", 3);
    workTask.put("reward", 500);
    dailyTasks.put("work", workTask);
    
    java.util.Map robTask = new java.util.HashMap();
    robTask.put("name", "打劫高手");
    robTask.put("goal", 2);
    robTask.put("reward", 800);
    dailyTasks.put("rob", robTask);
    
    java.util.Map fishTask = new java.util.HashMap();
    fishTask.put("name", "钓鱼大师");
    fishTask.put("goal", 5);
    fishTask.put("reward", 1000);
    dailyTasks.put("fish", fishTask);
    
    java.util.Map signTask = new java.util.HashMap();
    signTask.put("name", "签到打卡");
    signTask.put("goal", 1);
    signTask.put("reward", 300);
    dailyTasks.put("sign", signTask);
}

void refreshMarket() {
    long now = System.currentTimeMillis();
    if (now - lastMarketRefresh < MARKET_REFRESH) return;
    
    marketItems.clear();
    marketPrices.clear();
    
    java.util.Random rand = new java.util.Random();
    java.util.List weaponNames = new java.util.ArrayList(weapons.keySet());
    java.util.Collections.shuffle(weaponNames);
    
    for (int i = 0; i < 5; i++) {
        String weapon = (String) weaponNames.get(i);
        java.util.Map weaponData = (java.util.Map) weapons.get(weapon);
        int basePrice = (Integer) weaponData.get("price");
        int marketPrice = basePrice * (70 + rand.nextInt(61)) / 100;
        marketItems.put(weapon, "weapon");
        marketPrices.put(weapon, marketPrice);
    }
    
    java.util.List itemNames = new java.util.ArrayList(items.keySet());
    java.util.Collections.shuffle(itemNames);
    
    for (int i = 0; i < 5; i++) {
        String item = (String) itemNames.get(i);
        java.util.Map itemData = (java.util.Map) items.get(item);
        int basePrice = (Integer) itemData.get("price");
        int marketPrice = basePrice * (60 + rand.nextInt(81)) / 100;
        marketItems.put(item, "item");
        marketPrices.put(item, marketPrice);
    }
    
    lastMarketRefresh = now;
}

void handleWork(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastWork = getLongValue(group, "workTime_" + uin, 0L);
    
    if (now - lastWork < WORK_COOLDOWN) {
        sendMsg(group, "", "打工冷却中，请等待" + formatCoolDown(lastWork + WORK_COOLDOWN - now));
        return;
    }
    
    int baseEarn = 100;
    if (hasBuff(group, uin, "经验卡")) {
        baseEarn *= 2;
    }
    
    String career = getCareer(group, uin);
    if ("商人".equals(career)) {
        baseEarn = (int)(baseEarn * 1.3);
    }
    
    int earned = baseEarn + (int)(Math.random() * 201);
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("工作犬")) {
        earned += getPetIncome(group, uin);
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + earned);
    setLongValue(group, "workTime_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 打工赚了" + earned + "金币，总金币:" + (currentGold + earned));
    
    updateDailyTaskProgress(group, uin, "work");
    checkAchievement(group, uin, "work", earned);
}

void handleRob(Object msg) {
    String robberUin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (hasShield(group, msg.mAtList)) {
        sendMsg(group, "", "对方有护盾保护，无法打劫！");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastRob = getLongValue(group, "robTime_" + robberUin, 0L);
    
    if (now - lastRob < ROB_COOLDOWN) {
        sendMsg(group, "", "打劫冷却中，请等待" + formatCoolDown(lastRob + ROB_COOLDOWN - now));
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要打劫的人");
        return;
    }
    
    String victimUin = (String) msg.mAtList.get(0);
    if (victimUin.equals(robberUin)) {
        sendMsg(group, "", "不能打劫自己");
        return;
    }
    
    int victimGold = getGold(group, victimUin);
    if (victimGold <= 0) {
        String victimName = getSafeMemberName(group, victimUin);
        sendMsg(group, "", victimName + " 没有金币，不值得打劫");
        return;
    }
    
    String robberWeaponName = getStringValue(group, "weapon_" + robberUin, "");
    String victimWeaponName = getStringValue(group, "weapon_" + victimUin, "");
    
    int robberAttack = 0;
    int victimDefense = 0;
    if (!robberWeaponName.isEmpty()) {
        java.util.Map robberWeapon = (java.util.Map) weapons.get(robberWeaponName);
        robberAttack = (Integer) robberWeapon.get("attack");
    }
    if (!victimWeaponName.isEmpty()) {
        java.util.Map victimWeapon = (java.util.Map) weapons.get(victimWeaponName);
        victimDefense = (Integer) victimWeapon.get("defense");
    }
    
    int baseRob = 25 + (int)(Math.random() * 46);
    
    String career = getCareer(group, robberUin);
    if ("战士".equals(career)) {
        baseRob = (int)(baseRob * 1.2);
    }
    
    int robbed = baseRob + robberAttack - victimDefense;
    if (robbed < 0) robbed = 0;
    
    boolean criticalHit = false;
    if (!robberWeaponName.isEmpty() && (robberWeaponName.equals("匕首") || 
        robberWeaponName.equals("手枪") || robberWeaponName.equals("散弹枪") || 
        robberWeaponName.equals("激光剑") || robberWeaponName.equals("火箭筒") ||
        robberWeaponName.equals("三叉戟") || robberWeaponName.equals("龙之剑") ||
        robberWeaponName.equals("光子冲击炮") || robberWeaponName.equals("毒液长矛") ||
        robberWeaponName.equals("天界神弓") || robberWeaponName.equals("死灵之书") ||
        robberWeaponName.equals("无限手套"))) {
        
        double criticalChance = 0.0;
        if (robberWeaponName.equals("匕首")) criticalChance = 0.1;
        else if (robberWeaponName.equals("手枪")) criticalChance = 0.2;
        else if (robberWeaponName.equals("散弹枪")) criticalChance = 0.3;
        else if (robberWeaponName.equals("激光剑")) criticalChance = 0.4;
        else if (robberWeaponName.equals("火箭筒")) criticalChance = 0.5;
        else if (robberWeaponName.equals("三叉戟")) criticalChance = 0.6;
        else if (robberWeaponName.equals("龙之剑")) criticalChance = 0.65;
        else if (robberWeaponName.equals("光子冲击炮")) criticalChance = 0.7;
        else if (robberWeaponName.equals("毒液长矛")) criticalChance = 0.55;
        else if (robberWeaponName.equals("天界神弓")) criticalChance = 0.75;
        else if (robberWeaponName.equals("死灵之书")) criticalChance = 0.8;
        else if (robberWeaponName.equals("无限手套")) criticalChance = 1.0;
        
        if (Math.random() < criticalChance) {
            robbed *= 2;
            criticalHit = true;
        }
    }
    
    boolean counterAttack = false;
    int counterLoss = 0;
    if (robberWeaponName.isEmpty() && !victimWeaponName.isEmpty()) {
        counterAttack = true;
        counterLoss = baseRob / 2;
    }
    
    int robberGold = getGold(group, robberUin);
    if (counterAttack) {
        if (robberGold < counterLoss) counterLoss = robberGold;
        setGold(group, robberUin, robberGold - counterLoss);
        setGold(group, victimUin, victimGold + counterLoss);
    } else {
        if (robbed > victimGold) robbed = victimGold;
        setGold(group, robberUin, robberGold + robbed);
        setGold(group, victimUin, victimGold - robbed);
    }
    
    setLongValue(group, "robTime_" + robberUin, now);
    
    String robberName = getSafeMemberName(group, robberUin);
    String victimName = getSafeMemberName(group, victimUin);
    
    StringBuilder result = new StringBuilder();
    String[] battleEmojis = {"⚔️", "🔫", "💥", "✨", "🔥", "💢", "🛡️"};
    String emoji = battleEmojis[(int)(Math.random() * battleEmojis.length)];
    
    result.append(emoji).append(" ");
    if (counterAttack) {
        result.append(robberName).append(" 试图打劫 ")
              .append(victimName).append("，但被反击\n");
        result.append(victimName).append(" 使用 ").append(victimWeaponName)
              .append(" 反抢了 ").append(counterLoss).append("金币");
    } else {
        result.append(robberName);
        if (!robberWeaponName.isEmpty()) {
            result.append(" 使用 ").append(robberWeaponName);
        }
        result.append(" 打劫了 ").append(victimName);
        if (!victimWeaponName.isEmpty()) {
            result.append("(").append(victimWeaponName).append(")");
        }
        result.append("，抢走").append(robbed).append("金币");
        
        if (criticalHit) {
            result.append("\n暴击伤害");
        }
    }
    result.append(" ").append(emoji);
    
    sendMsg(group, "", result.toString());
    updateDailyTaskProgress(group, robberUin, "rob");
    checkAchievement(group, robberUin, "rob", robbed);
}

void handleSign(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new java.util.Date());
    String lastSign = getStringValue(group, "signDate_" + uin, "");
    
    if (lastSign.equals(today)) {
        sendMsg(group, "", "今天已经签到过了");
        return;
    }
    
    int earned = 50 + (int)(Math.random() * 51);
    
    int consecutiveDays = getIntValue(group, "consecutiveDays_" + uin, 0);
    if (!lastSign.isEmpty() && !isYesterday(lastSign, today)) {
        consecutiveDays = 0;
    }
    consecutiveDays++;
    setIntValue(group, "consecutiveDays_" + uin, consecutiveDays);
    
    earned += consecutiveDays * 10;
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("幸运兔")) {
        earned += getPetIncome(group, uin);
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + earned);
    setStringValue(group, "signDate_" + uin, today);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 签到获得" + earned + "金币（连续签到" + consecutiveDays + "天），总金币:" + (currentGold + earned));
    updateDailyTaskProgress(group, uin, "sign");
    checkAchievement(group, uin, "sign", 1);
}

boolean isYesterday(String lastDate, String today) {
    try {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date last = sdf.parse(lastDate);
        java.util.Date now = sdf.parse(today);
        long diff = now.getTime() - last.getTime();
        return diff == 86400000;
    } catch (Exception e) {
        return false;
    }
}

void handleTransfer(Object msg) {
    String senderUin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastTransfer = getLongValue(group, "transferTime_" + senderUin, 0L);
    
    if (now - lastTransfer < TRANSFER_COOLDOWN) {
        sendMsg(group, "", "转账冷却中，请等待" + formatCoolDown(lastTransfer + TRANSFER_COOLDOWN - now));
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要赠送金币的人");
        return;
    }
    
    String receiverUin = (String) msg.mAtList.get(0);
    if (receiverUin.equals(senderUin)) {
        sendMsg(group, "", "不能送金币给自己");
        return;
    }
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        for (String part : parts) {
            if (part.matches("\\d+")) {
                amount = Integer.parseInt(part);
                break;
            }
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金币数量必须大于0");
        return;
    }
    
    int senderGold = getGold(group, senderUin);
    if (senderGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    int receiverGold = getGold(group, receiverUin);
    
    setGold(group, senderUin, senderGold - amount);
    setGold(group, receiverUin, receiverGold + amount);
    setLongValue(group, "transferTime_" + senderUin, now);
    
    String senderName = getSafeMemberName(group, senderUin);
    String receiverName = getSafeMemberName(group, receiverUin);
    sendMsg(group, "", senderName + " 赠送了 " + receiverName + " " + amount + "金币");
    checkAchievement(group, senderUin, "transfer", amount);
}

void handleWeaponShop(Object msg) {
    String group = msg.GroupUin;
    StringBuilder sb = new StringBuilder();
    sb.append("武器商店\n");
    sb.append("使用「购买武器 [武器名]」购买\n\n");
    
    for (Object weaponNameObj : weapons.keySet()) {
        String weaponName = (String) weaponNameObj;
        java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
        sb.append(weaponName).append(" | 价格: ").append(weapon.get("price"))
          .append(" | 攻击: ").append(weapon.get("attack"))
          .append(" | 防御: ").append(weapon.get("defense"))
          .append(" | 可强化: ").append(weapon.get("maxLevel")).append("级\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleItemShop(Object msg) {
    String group = msg.GroupUin;
    StringBuilder sb = new StringBuilder();
    sb.append("道具商店\n");
    sb.append("使用「购买道具 [道具名]」购买\n\n");
    
    for (Object itemNameObj : items.keySet()) {
        String itemName = (String) itemNameObj;
        java.util.Map item = (java.util.Map) items.get(itemName);
        sb.append(itemName).append(" | 价格: ").append(item.get("price"));
        
        if (itemName.equals("经验卡")) sb.append(" | 效果: 打工双倍金币");
        else if (itemName.equals("护盾")) sb.append(" | 效果: 防止被抢劫");
        else if (itemName.equals("高级鱼竿")) sb.append(" | 效果: 钓鱼获得更多金币");
        else if (itemName.equals("彩票")) sb.append(" | 效果: 有机会中大奖");
        else if (itemName.equals("复活药水")) sb.append(" | 效果: 副本挑战失败时减少损失");
        else if (itemName.equals("宠物粮")) sb.append(" | 效果: 喂养宠物");
        else if (itemName.equals("强化石")) sb.append(" | 效果: 强化装备");
        else if (itemName.equals("藏宝图")) sb.append(" | 效果: 寻找宝藏");
        else if (itemName.equals("节日代币")) sb.append(" | 效果: 节日活动专用");
        else if (itemName.equals("家园装饰")) sb.append(" | 效果: 装饰家园增加收益");
        else if (itemName.equals("存款保险")) sb.append(" | 效果: 保护存款安全");
        else if (itemName.equals("黑市令牌")) sb.append(" | 效果: 进入黑市交易");
        else if (itemName.equals("任务重置券")) sb.append(" | 效果: 重置每日任务");
        
        sb.append("\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handlePetShop(Object msg) {
    String group = msg.GroupUin;
    StringBuilder sb = new StringBuilder();
    sb.append("宠物商店\n");
    sb.append("使用「购买宠物 [宠物名]」购买\n\n");
    
    for (Object petNameObj : pets.keySet()) {
        String petName = (String) petNameObj;
        java.util.Map pet = (java.util.Map) pets.get(petName);
        sb.append(petName).append(" | 价格: ").append(pet.get("price"))
          .append(" | 每日收益: ").append(pet.get("income"))
          .append(" | 加成: ").append(pet.get("bonus"))
          .append(" | 战力: ").append(pet.get("attack")).append("/").append(pet.get("defense")).append("\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleHouseShop(Object msg) {
    String group = msg.GroupUin;
    StringBuilder sb = new StringBuilder();
    sb.append("房产商店\n");
    sb.append("使用「购买家园 [家园名]」购买\n\n");
    
    for (Object houseNameObj : houses.keySet()) {
        String houseName = (String) houseNameObj;
        java.util.Map house = (java.util.Map) houses.get(houseName);
        sb.append(houseName).append(" | 价格: ").append(house.get("price"))
          .append(" | 每日收益: ").append(house.get("income"))
          .append(" | 装饰槽: ").append(house.get("slots")).append("\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleCareerShop(Object msg) {
    String group = msg.GroupUin;
    StringBuilder sb = new StringBuilder();
    sb.append("职业选择\n");
    sb.append("使用「选择职业 [职业名]」选择\n\n");
    
    for (Object careerNameObj : careers.keySet()) {
        String careerName = (String) careerNameObj;
        java.util.Map career = (java.util.Map) careers.get(careerName);
        sb.append(careerName).append(" | 价格: ").append(career.get("price"))
          .append(" | 加成: ").append(career.get("bonus"))
          .append(" | 效果: ").append(career.get("effect")).append("\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleBuyWeapon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定武器名");
        return;
    }
    String weaponName = parts[1].trim();
    
    if (!weapons.containsKey(weaponName)) {
        sendMsg(group, "", "没有这种武器，发送「武器商店」查看可用武器");
        return;
    }
    
    java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
    int price = (Integer) weapon.get("price");
    int currentGold = getGold(group, uin);
    
    if (currentGold < price) {
        sendMsg(group, "", "金币不足，需要" + price + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - price);
    setStringValue(group, "weapon_" + uin, weaponName);
    setIntValue(group, "weapon_level_" + uin, 1);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 成功购买了 " + weaponName);
}

void handleBuyItem(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定道具名");
        return;
    }
    String itemName = parts[1].trim();
    
    if (!items.containsKey(itemName)) {
        sendMsg(group, "", "没有这种道具，发送「道具商店」查看可用道具");
        return;
    }
    
    java.util.Map item = (java.util.Map) items.get(itemName);
    int price = (Integer) item.get("price");
    int currentGold = getGold(group, uin);
    
    if (currentGold < price) {
        sendMsg(group, "", "金币不足，需要" + price + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - price);
    
    if (itemName.equals("护盾")) {
        setLongValue(group, "shield_" + uin, System.currentTimeMillis() + 86400000);
        sendMsg(group, "", "你获得了24小时护盾保护");
    } else if (itemName.equals("经验卡")) {
        setLongValue(group, "buff_exp_" + uin, System.currentTimeMillis() + 172800000);
        sendMsg(group, "", "你获得了48小时经验卡");
    } else if (itemName.equals("彩票")) {
        handleLottery(msg);
    } else if (itemName.equals("存款保险")) {
        setLongValue(group, "insurance_" + uin, System.currentTimeMillis() + 259200000);
        sendMsg(group, "", "你获得了72小时存款保险");
    } else {
        int count = getIntValue(group, "item_" + itemName + "_" + uin, 0);
        setIntValue(group, "item_" + itemName + "_" + uin, count + 1);
        sendMsg(group, "", "购买成功: " + itemName + " x1");
    }
}

void handleBuyPet(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定宠物名");
        return;
    }
    String petName = parts[1].trim();
    
    if (!pets.containsKey(petName)) {
        sendMsg(group, "", "没有这种宠物，发送「宠物商店」查看可用宠物");
        return;
    }
    
    java.util.Map pet = (java.util.Map) pets.get(petName);
    int price = (Integer) pet.get("price");
    int currentGold = getGold(group, uin);
    
    if (currentGold < price) {
        sendMsg(group, "", "金币不足，需要" + price + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - price);
    setStringValue(group, "pet_" + uin, petName);
    setIntValue(group, "pet_hunger_" + uin, 100);
    setLongValue(group, "pet_feed_time_" + uin, System.currentTimeMillis());
    setIntValue(group, "pet_level_" + uin, 1);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 成功购买了 " + petName);
}

void handleBuyHouse(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定家园名");
        return;
    }
    String houseName = parts[1].trim();
    
    if (!houses.containsKey(houseName)) {
        sendMsg(group, "", "没有这种房产，发送「房产商店」查看可用房产");
        return;
    }
    
    java.util.Map house = (java.util.Map) houses.get(houseName);
    int price = (Integer) house.get("price");
    int currentGold = getGold(group, uin);
    
    if (currentGold < price) {
        sendMsg(group, "", "金币不足，需要" + price + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - price);
    setStringValue(group, "house_" + uin, houseName);
    setLongValue(group, "house_income_time_" + uin, System.currentTimeMillis());
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 成功购买了 " + houseName);
    checkAchievement(group, uin, "house", 1);
}

void handleChooseCareer(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定职业名");
        return;
    }
    String careerName = parts[1].trim();
    
    if (!careers.containsKey(careerName)) {
        sendMsg(group, "", "没有这种职业，发送「职业选择」查看可用职业");
        return;
    }
    
    java.util.Map career = (java.util.Map) careers.get(careerName);
    int cost = (Integer) career.get("price");
    int currentGold = getGold(group, uin);
    
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    setStringValue(group, "career_" + uin, careerName);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 成功选择了 " + careerName + " 职业");
}

void handleLottery(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    int winChance = (int)(Math.random() * 100);
    int prize = 0;
    
    if (winChance < 60) {
        prize = 50;
    } else if (winChance < 85) {
        prize = 200;
    } else if (winChance < 95) {
        prize = 1000;
    } else {
        prize = 5000;
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + prize);
    
    String result = "彩票开奖结果: ";
    if (prize < 200) {
        result += "安慰奖";
    } else if (prize < 1000) {
        result += "三等奖";
    } else if (prize < 5000) {
        result += "二等奖";
    } else {
        result += "头奖!";
    }
    
    result += " 获得" + prize + "金币!";
    sendMsg(group, "", result);
}

void handleFeedPet(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String pet = getPet(group, uin);
    if (pet == null) {
        sendMsg(group, "", "你还没有宠物，无法喂养");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastFeed = getLongValue(group, "pet_feed_time_" + uin, 0L);
    
    if (now - lastFeed < PET_FEED_COOLDOWN) {
        sendMsg(group, "", "宠物喂养冷却中，请等待" + formatCoolDown(lastFeed + PET_FEED_COOLDOWN - now));
        return;
    }
    
    int foodCount = getIntValue(group, "item_宠物粮_" + uin, 0);
    if (foodCount <= 0) {
        sendMsg(group, "", "你没有宠物粮，无法喂养");
        return;
    }
    
    setIntValue(group, "item_宠物粮_" + uin, foodCount - 1);
    setIntValue(group, "pet_hunger_" + uin, 100);
    setLongValue(group, "pet_feed_time_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 喂养了 " + pet + "，宠物饱食度恢复");
}

void handleCollectHouseIncome(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String house = getHouse(group, uin);
    if (house == null || house.isEmpty()) {
        sendMsg(group, "", "你还没有家园，无法收取收益");
        return;
    }
    
    if (!houses.containsKey(house)) {
        sendMsg(group, "", "你的家园配置有误，请联系管理员");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastCollect = getLongValue(group, "house_income_time_" + uin, 0L);
    
    if (now - lastCollect < HOUSE_INCOME_COOLDOWN) {
        sendMsg(group, "", "家园收益冷却中，请等待" + formatCoolDown(lastCollect + HOUSE_INCOME_COOLDOWN - now));
        return;
    }
    
    java.util.Map houseData = (java.util.Map) houses.get(house);
    int income = (Integer) houseData.get("income");
    
    int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
    income = (int)(income * (1 + decorBonus / 100.0));
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + income);
    setLongValue(group, "house_income_time_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 收取了 " + house + " 的收益 " + income + "金币");
}

void handleArenaChallenge(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastArena = getLongValue(group, "arena_time_" + uin, 0L);
    
    if (now - lastArena < ARENA_COOLDOWN) {
        sendMsg(group, "", "竞技场冷却中，请等待" + formatCoolDown(lastArena + ARENA_COOLDOWN - now));
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要挑战的对手");
        return;
    }
    
    String opponentUin = (String) msg.mAtList.get(0);
    if (opponentUin.equals(uin)) {
        sendMsg(group, "", "不能挑战自己");
        return;
    }
    
    int challengerPower = calculateCombatPower(group, uin);
    int opponentPower = calculateCombatPower(group, opponentUin);
    
    int winChance = 50;
    if (challengerPower > opponentPower) {
        winChance += (challengerPower - opponentPower) / 10;
    } else {
        winChance -= (opponentPower - challengerPower) / 10;
    }
    
    if (winChance < 10) winChance = 10;
    if (winChance > 90) winChance = 90;
    
    boolean win = (int)(Math.random() * 100) < winChance;
    int reward = 500 + (int)(Math.random() * 501);
    
    String challengerName = getSafeMemberName(group, uin);
    String opponentName = getSafeMemberName(group, opponentUin);
    
    StringBuilder result = new StringBuilder();
    result.append("⚔️ 竞技场挑战 ⚔️\n");
    result.append(challengerName).append(" 挑战 ").append(opponentName).append("\n");
    result.append("战力对比: ").append(challengerPower).append(" vs ").append(opponentPower).append("\n");
    
    if (win) {
        result.append(challengerName).append(" 获胜！获得").append(reward).append("金币");
        int currentGold = getGold(group, uin);
        setGold(group, uin, currentGold + reward);
        checkAchievement(group, uin, "arena", reward);
    } else {
        result.append(challengerName).append(" 失败！下次再接再厉");
    }
    
    setLongValue(group, "arena_time_" + uin, now);
    sendMsg(group, "", result.toString());
}

int calculateCombatPower(String group, String uin) {
    int power = 0;
    
    String weapon = getStringValue(group, "weapon_" + uin, "");
    if (!weapon.isEmpty()) {
        java.util.Map weaponData = (java.util.Map) weapons.get(weapon);
        int level = getIntValue(group, "weapon_level_" + uin, 1);
        power += (Integer) weaponData.get("attack") * level;
        power += (Integer) weaponData.get("defense") * level;
    }
    
    String career = getCareer(group, uin);
    if (career != null) {
        power += 50;
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pets.containsKey(pet)) {
        int petLevel = getIntValue(group, "pet_level_" + uin, 1);
        java.util.Map petData = (java.util.Map) pets.get(pet);
        power += (Integer) petData.get("attack") * petLevel;
        power += (Integer) petData.get("defense") * petLevel;
    }
    
    String house = getHouse(group, uin);
    if (house != null) {
        power += 20;
    }
    
    int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
    power += decorBonus;
    
    return power;
}

void handleMyWeapon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    int weaponLevel = getIntValue(group, "weapon_level_" + uin, 1);
    
    if (weaponName.isEmpty()) {
        sendMsg(group, "", "你还没有武器，发送「武器商店」购买武器");
        return;
    }
    
    java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
    int baseAttack = (Integer) weapon.get("attack");
    int baseDefense = (Integer) weapon.get("defense");
    int maxLevel = (Integer) weapon.get("maxLevel");
    
    int currentAttack = baseAttack * weaponLevel;
    int currentDefense = baseDefense * weaponLevel;
    
    StringBuilder sb = new StringBuilder();
    sb.append("你的武器: ").append(weaponName).append(" +").append(weaponLevel).append("\n");
    sb.append("攻击力: ").append(currentAttack).append(" (基础").append(baseAttack).append(")\n");
    sb.append("防御力: ").append(currentDefense).append(" (基础").append(baseDefense).append(")\n");
    sb.append("强化上限: ").append(maxLevel).append("级\n");
    sb.append("特殊效果: ");
    
    if (weaponName.equals("匕首") || weaponName.equals("手枪") || 
        weaponName.equals("散弹枪") || weaponName.equals("激光剑") || 
        weaponName.equals("火箭筒") || weaponName.equals("三叉戟") || 
        weaponName.equals("龙之剑") || weaponName.equals("光子冲击炮") || 
        weaponName.equals("毒液长矛") || weaponName.equals("天界神弓") || 
        weaponName.equals("死灵之书") || weaponName.equals("无限手套")) {
        sb.append("攻击时有概率暴击(双倍伤害)");
    } else if (weaponName.equals("铠甲") || weaponName.equals("防弹衣") || 
               weaponName.equals("泰坦之盾")) {
        sb.append("减少受到的伤害");
    } else {
        sb.append("无");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleFishing(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastFishing = getLongValue(group, "fishingTime_" + uin, 0L);
    
    if (now - lastFishing < FISHING_COOLDOWN) {
        sendMsg(group, "", "钓鱼冷却中，请等待" + formatCoolDown(lastFishing + FISHING_COOLDOWN - now));
        return;
    }
    
    Object[] fishNames = fishTypes.keySet().toArray();
    String caughtFish = (String) fishNames[(int)(Math.random() * fishNames.length)];
    java.util.Map fish = (java.util.Map) fishTypes.get(caughtFish);
    
    int minValue = (Integer) fish.get("min");
    int maxValue = (Integer) fish.get("max");
    int fishValue = minValue + (int)(Math.random() * (maxValue - minValue + 1));
    
    if (hasItem(group, uin, "高级鱼竿")) {
        fishValue = (int)(fishValue * 1.5);
    }
    
    String career = getCareer(group, uin);
    if ("渔夫".equals(career)) {
        fishValue = (int)(fishValue * 1.4);
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("招财猫")) {
        fishValue += getPetIncome(group, uin);
    }
    
    int currentGold = getGold(group, uin);
    
    if (caughtFish.equals("夜七")) {
        fishValue = -100;
        setGold(group, uin, currentGold + fishValue);
    } else if (caughtFish.equals("临江")) {
        fishValue = 1000;
        setGold(group, uin, currentGold + fishValue);
    } else {
        setGold(group, uin, currentGold + fishValue);
    }
    
    setLongValue(group, "fishingTime_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    
    StringBuilder result = new StringBuilder();
    result.append(name).append(" 钓到了 ");
    
    if (caughtFish.equals("夜七")) {
        result.append("夜七！被罚款100金币 😭");
    } else if (caughtFish.equals("临江")) {
        result.append("临江！获得1000金币大奖 🎉");
    } else {
        result.append(caughtFish).append("，卖出获得").append(fishValue).append("金币");
    }
    
    if (caughtFish.equals("鲸鱼") || caughtFish.equals("蓝鲸")) {
        result.append("\n鲸鱼，超级大奖");
    } else if (caughtFish.equals("金龙鱼")) {
        result.append("\n金龙鱼，太幸运了");
    } else if (caughtFish.equals("深海热泉")) {
        result.append("\n发现深海宝藏！");
    } else if (caughtFish.equals("巨型乌贼")) {
        result.append("\n深海巨怪！");
    } else if (caughtFish.equals("独角鲸")) {
        result.append("\n稀有独角兽般的生物！");
    } else if (caughtFish.equals("海龙")) {
        result.append("\n传说中的海龙！");
    }
    
    sendMsg(group, "", result.toString());
    updateDailyTaskProgress(group, uin, "fish");
    checkAchievement(group, uin, "fish", fishValue);
}

void handleDungeon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastDungeon = getLongValue(group, "dungeonTime_" + uin, 0L);
    
    if (now - lastDungeon < DUNGEON_COOLDOWN) {
        sendMsg(group, "", "副本冷却中，请等待" + formatCoolDown(lastDungeon + DUNGEON_COOLDOWN - now));
        return;
    }
    
    String[] dungeons = {"巨龙巢穴", "海盗宝藏", "亡灵古墓", "天空之城", "深渊地牢"};
    String dungeon = dungeons[(int)(Math.random() * dungeons.length)];
    
    int baseReward = 500;
    int risk = (int)(Math.random() * 100);
    int result = 0;
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("守护龙")) {
        baseReward += getPetIncome(group, uin);
    }
    
    boolean hasRevive = hasItem(group, uin, "复活药水");
    
    if (risk < 40) {
        result = -(int)(baseReward * 0.7);
        if (hasRevive) {
            result = (int)(result * 0.5);
            removeItem(group, uin, "复活药水");
        }
    } else {
        result = baseReward + risk * 10;
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + result);
    setLongValue(group, "dungeonTime_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    String outcome = result > 0 ? "成功挑战" : "挑战失败";
    
    StringBuilder sb = new StringBuilder();
    sb.append(name).append(" 探索[").append(dungeon).append("] ").append(outcome).append("，");
    
    if (result > 0) {
        sb.append("获得").append(result).append("金币");
    } else {
        sb.append("损失").append(-result).append("金币");
        if (hasRevive) {
            sb.append(" (复活药水减少了一半损失)");
        }
    }
    
    sendMsg(group, "", sb.toString());
    checkAchievement(group, uin, "dungeon", result);
}

void handleStock(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastStock = getLongValue(group, "stockTime_" + uin, 0L);
    
    if (now - lastStock < STOCK_COOLDOWN) {
        sendMsg(group, "", "股票冷却中，请等待" + formatCoolDown(lastStock + STOCK_COOLDOWN - now));
        return;
    }
    
    String[] stocks = {"金币矿业", "鱼市集团", "武器科技", "冒险公会", "魔法商店"};
    String stock = stocks[(int)(Math.random() * stocks.length)];
    
    double change = (Math.random() * 40) - 20;
    int investment = 1000;
    
    int profit = (int)(investment * change / 100);
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + profit);
    setLongValue(group, "stockTime_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    String trend = profit > 0 ? "上涨" : "下跌";
    
    sendMsg(group, "", name + " 投资了" + stock + "，股票" + trend + Math.abs(change) + "%，" +
            (profit > 0 ? "赚取" : "亏损") + Math.abs(profit) + "金币");
    checkAchievement(group, uin, "stock", profit);
}

void handleProfile(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    int gold = getGold(group, uin);
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    int weaponLevel = getIntValue(group, "weapon_level_" + uin, 1);
    int defense = 0;
    int attack = 0;
    
    if (!weaponName.isEmpty()) {
        java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
        defense = (Integer) weapon.get("defense") * weaponLevel;
        attack = (Integer) weapon.get("attack") * weaponLevel;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("个人信息\n");
    sb.append("金币: ").append(gold).append("\n");
    sb.append("存款: ").append(deposit).append("\n");
    sb.append("武器: ").append(weaponName.isEmpty() ? "无" : weaponName + "+" + weaponLevel).append("\n");
    sb.append("攻击: ").append(attack).append(" | 防御: ").append(defense).append("\n");
    
    String career = getCareer(group, uin);
    if (career != null) {
        sb.append("职业: ").append(career).append("\n");
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pets.containsKey(pet)) {
        int hunger = getIntValue(group, "pet_hunger_" + uin, 100);
        int petLevel = getIntValue(group, "pet_level_" + uin, 1);
        sb.append("宠物: ").append(pet).append(" Lv.").append(petLevel)
          .append(" | 饱食度: ").append(hunger).append("\n");
    }
    
    String house = getHouse(group, uin);
    if (house != null) {
        long lastCollect = getLongValue(group, "house_income_time_" + uin, 0L);
        long nextCollect = lastCollect + HOUSE_INCOME_COOLDOWN;
        long remain = nextCollect - System.currentTimeMillis();
        
        if (remain > 0) {
            sb.append("家园: ").append(house).append(" | 收益冷却: ").append(formatCoolDown(remain)).append("\n");
        } else {
            sb.append("家园: ").append(house).append(" | 可收取收益\n");
        }
        
        int decorCount = getIntValue(group, "decor_count_" + uin, 0);
        int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
        sb.append("装饰: ").append(decorCount).append("个 | 收益加成: ").append(decorBonus).append("%\n");
    }
    
    if (hasBuff(group, uin, "经验卡")) {
        sb.append("经验卡: 生效中\n");
    }
    
    if (hasShield(group, uin)) {
        sb.append("护盾: 生效中\n");
    }
    
    String achievements = getStringValue(group, "achievements_" + uin, "");
    if (!achievements.isEmpty()) {
        sb.append("成就: ").append(achievements).append("\n");
    }
    
    String title = getStringValue(group, "title_" + uin, "");
    if (!title.isEmpty()) {
        sb.append("称号: ").append(title).append("\n");
    }
    
    int consecutiveDays = getIntValue(group, "consecutiveDays_" + uin, 0);
    sb.append("连续签到: ").append(consecutiveDays).append("天\n");
    
    int power = calculateCombatPower(group, uin);
    sb.append("综合战力: ").append(power);
    
    sendMsg(group, "", sb.toString());
}

void handleAlms(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastAlms = getLongValue(group, "almsTime_" + uin, 0L);
    
    if (now - lastAlms < ALMS_COOLDOWN) {
        sendMsg(group, "", "施舍冷却中，请等待" + formatCoolDown(lastAlms + ALMS_COOLDOWN - now));
        return;
    }
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) {
            amount = Integer.parseInt(parts[1]);
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金币数量必须大于0");
        return;
    }
    
    int senderGold = getGold(group, uin);
    if (senderGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    java.util.List members = getGroupMemberList(group);
    if (members == null || members.size() < 3) {
        sendMsg(group, "", "群成员不足，无法施舍");
        return;
    }
    
    java.util.List receivers = new java.util.ArrayList();
    java.util.Random random = new java.util.Random();
    
    while (receivers.size() < 3) {
        int index = random.nextInt(members.size());
        Object member = members.get(index);
        String receiverUin = (String) member.UserUin;
        if (!receiverUin.equals(uin)) {
            receivers.add(receiverUin);
        }
    }
    
    int perPerson = amount / 3;
    if (perPerson < 1) {
        sendMsg(group, "", "施舍金额太少，每人至少1金币");
        return;
    }
    
    setGold(group, uin, senderGold - amount);
    
    for (Object receiverUinObj : receivers) {
        String receiverUin = (String) receiverUinObj;
        int currentGold = getGold(group, receiverUin);
        setGold(group, receiverUin, currentGold + perPerson);
    }
    
    setLongValue(group, "almsTime_" + uin, now);
    
    String senderName = getSafeMemberName(group, uin);
    StringBuilder result = new StringBuilder();
    result.append(senderName).append(" 施舍了 ").append(amount).append("金币\n");
    result.append("受赠者: ");
    
    for (int i = 0; i < receivers.size(); i++) {
        String receiverUin = (String) receivers.get(i);
        String receiverName = getSafeMemberName(group, receiverUin);
        result.append(receiverName);
        if (i < receivers.size() - 1) {
            result.append(", ");
        }
    }
    
    result.append("\n每人获得: ").append(perPerson).append("金币");
    
    sendMsg(group, "", result.toString());
    checkAchievement(group, uin, "alms", amount);
}

void handleRedPacket(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastRedPacket = getLongValue(group, "redPacketTime_" + uin, 0L);
    
    if (now - lastRedPacket < REDPACKET_COOLDOWN) {
        sendMsg(group, "", "发红包冷却中，请等待" + formatCoolDown(lastRedPacket + REDPACKET_COOLDOWN - now));
        return;
    }
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    int count = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (parts.length > 2) count = Integer.parseInt(parts[2]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「发红包 金额 人数」格式");
        return;
    }
    
    if (amount <= 0 || count <= 0) {
        sendMsg(group, "", "金额和人数必须大于0");
        return;
    }
    
    if (count < 2 || count > 10) {
        sendMsg(group, "", "红包人数必须在2-10人之间");
        return;
    }
    
    int senderGold = getGold(group, uin);
    if (senderGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    java.util.List members = getGroupMemberList(group);
    if (members == null || members.size() < count) {
        sendMsg(group, "", "群成员不足");
        return;
    }
    
    java.util.List receivers = new java.util.ArrayList();
    java.util.Random random = new java.util.Random();
    
    while (receivers.size() < count) {
        int index = random.nextInt(members.size());
        Object member = members.get(index);
        String receiverUin = (String) member.UserUin;
        if (!receiverUin.equals(uin)) {
            receivers.add(receiverUin);
        }
    }
    
    int[] amounts = new int[count];
    int remaining = amount;
    
    for (int i = 0; i < count - 1; i++) {
        int max = remaining - (count - i - 1);
        int min = 1;
        if (max <= min) max = min + 1;
        
        int part = min + random.nextInt(max - min);
        amounts[i] = part;
        remaining -= part;
    }
    amounts[count - 1] = remaining;
    
    setGold(group, uin, senderGold - amount);
    
    StringBuilder result = new StringBuilder();
    result.append(getSafeMemberName(group, uin)).append(" 发送了红包\n");
    result.append("总金额: ").append(amount).append("金币 | 份数: ").append(count).append("\n");
    
    for (int i = 0; i < count; i++) {
        String receiverUin = (String) receivers.get(i);
        int gold = amounts[i];
        int currentGold = getGold(group, receiverUin);
        setGold(group, receiverUin, currentGold + gold);
        
        String receiverName = getSafeMemberName(group, receiverUin);
        result.append(receiverName).append(" 获得 ").append(gold).append("金币\n");
    }
    
    setLongValue(group, "redPacketTime_" + uin, now);
    sendMsg(group, "", result.toString());
}

void handleRank(Object msg) {
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    java.util.Properties props = loadGroupData(group);
    java.util.Map goldMap = new java.util.HashMap();
    
    for (Object keyObj : props.keySet()) {
        String key = (String) keyObj;
        if (key.startsWith("gold_")) {
            String uin = key.substring(5);
            try {
                int gold = Integer.parseInt(props.getProperty(key));
                if (gold > 0) {
                    goldMap.put(uin, gold);
                }
            } catch (Exception e) {
            }
        }
    }
    
    if (goldMap.isEmpty()) {
        sendMsg(group, "", "暂无排行榜数据");
        return;
    }
    
    java.util.List list = new java.util.ArrayList(goldMap.entrySet());
    
    java.util.Collections.sort(list, new java.util.Comparator() {
        public int compare(Object o1, Object o2) {
            java.util.Map.Entry e1 = (java.util.Map.Entry) o1;
            java.util.Map.Entry e2 = (java.util.Map.Entry) o2;
            int v1 = (Integer) e1.getValue();
            int v2 = (Integer) e2.getValue();
            return v2 - v1;
        }
    });
    
    StringBuilder sb = new StringBuilder();
    sb.append("金币排行榜\n");
    int rank = 1;
    
    for (Object entryObj : list) {
        if (rank > 10) break;
        java.util.Map.Entry entry = (java.util.Map.Entry) entryObj;
        String uin = (String) entry.getKey();
        int gold = (Integer) entry.getValue();
        String name = getSafeMemberName(group, uin);
        
        String weaponName = getStringValue(group, "weapon_" + uin, "");
        String weaponInfo = weaponName.isEmpty() ? "" : "[" + weaponName + "]";
        
        sb.append(rank).append(". ")
          .append(name).append(weaponInfo).append("(").append(uin).append(") : ")
          .append(gold).append("金币\n");
        rank++;
    }
    
    sendMsg(group, "", sb.toString());
}

void checkAchievement(String group, String uin, String type, int value) {
    java.util.Map achievements = new java.util.HashMap();
    achievements.put("rich", "大富翁");
    achievements.put("robber", "江洋大盗");
    achievements.put("fisher", "钓鱼大师");
    achievements.put("giver", "慈善家");
    achievements.put("worker", "打工人");
    achievements.put("homeowner", "房产大亨");
    achievements.put("petlover", "宠物达人");
    achievements.put("arena", "竞技之王");
    achievements.put("banker", "金融巨鳄");
    achievements.put("trader", "市场专家");
    achievements.put("blacksmith", "锻造大师");
    achievements.put("teamplayer", "团队之星");
    
    int gold = getGold(group, uin);
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    int totalWealth = gold + deposit;
    String currentAchs = getStringValue(group, "achievements_" + uin, "");
    
    if (totalWealth >= 100000 && currentAchs.indexOf("大富翁") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "大富翁";
        setStringValue(group, "title_" + uin, "大富豪");
        sendMsg(group, "", "成就解锁: 大富翁！获得称号「大富豪」");
    }
    
    if (type.equals("rob") && value >= 500 && currentAchs.indexOf("江洋大盗") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "江洋大盗";
        setStringValue(group, "title_" + uin, "江洋大盗");
        sendMsg(group, "", "成就解锁: 江洋大盗！获得称号「江洋大盗」");
    }
    
    if (type.equals("fish") && value >= 500 && currentAchs.indexOf("钓鱼大师") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "钓鱼大师";
        setStringValue(group, "title_" + uin, "钓鱼宗师");
        sendMsg(group, "", "成就解锁: 钓鱼大师！获得称号「钓鱼宗师」");
    }
    
    if (type.equals("alms") && value >= 1000 && currentAchs.indexOf("慈善家") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "慈善家";
        setStringValue(group, "title_" + uin, "慈善大使");
        sendMsg(group, "", "成就解锁: 慈善家！获得称号「慈善大使」");
    }
    
    if (type.equals("work") && value >= 250 && currentAchs.indexOf("打工人") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "打工人";
        setStringValue(group, "title_" + uin, "勤劳标兵");
        sendMsg(group, "", "成就解锁: 打工人！获得称号「勤劳标兵」");
    }
    
    if (getHouse(group, uin) != null && currentAchs.indexOf("房产大亨") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "房产大亨";
        setStringValue(group, "title_" + uin, "地产大亨");
        sendMsg(group, "", "成就解锁: 房产大亨！获得称号「地产大亨」");
    }
    
    if (getPet(group, uin) != null && currentAchs.indexOf("宠物达人") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "宠物达人";
        setStringValue(group, "title_" + uin, "宠物专家");
        sendMsg(group, "", "成就解锁: 宠物达人！获得称号「宠物专家」");
    }
    
    if (type.equals("arena") && value >= 1000 && currentAchs.indexOf("竞技之王") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "竞技之王";
        setStringValue(group, "title_" + uin, "竞技场冠军");
        sendMsg(group, "", "成就解锁: 竞技之王！获得称号「竞技场冠军」");
    }
    
    if (deposit >= 50000 && currentAchs.indexOf("金融巨鳄") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "金融巨鳄";
        setStringValue(group, "title_" + uin, "金融巨头");
        sendMsg(group, "", "成就解锁: 金融巨鳄！获得称号「金融巨头」");
    }
    
    if (getIntValue(group, "market_sales_" + uin, 0) >= 10 && currentAchs.indexOf("市场专家") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "市场专家";
        setStringValue(group, "title_" + uin, "贸易大师");
        sendMsg(group, "", "成就解锁: 市场专家！获得称号「贸易大师」");
    }
    
    if (getIntValue(group, "upgrade_success_" + uin, 0) >= 20 && currentAchs.indexOf("锻造大师") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "锻造大师";
        setStringValue(group, "title_" + uin, "神匠");
        sendMsg(group, "", "成就解锁: 锻造大师！获得称号「神匠」");
    }
    
    if (getIntValue(group, "team_dungeon_" + uin, 0) >= 5 && currentAchs.indexOf("团队之星") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "团队之星";
        setStringValue(group, "title_" + uin, "团队领袖");
        sendMsg(group, "", "成就解锁: 团队之星！获得称号「团队领袖」");
    }
    
    if (!currentAchs.equals(getStringValue(group, "achievements_" + uin, ""))) {
        setStringValue(group, "achievements_" + uin, currentAchs);
    }
}

boolean hasBuff(String group, String uin, String buff) {
    long expire = getLongValue(group, "buff_" + buff + "_" + uin, 0L);
    return expire > System.currentTimeMillis();
}

boolean hasShield(String group, String uin) {
    long expire = getLongValue(group, "shield_" + uin, 0L);
    return expire > System.currentTimeMillis();
}

boolean hasShield(String group, java.util.List uins) {
    for (Object uinObj : uins) {
        String uin = (String) uinObj;
        if (hasShield(group, uin)) {
            return true;
        }
    }
    return false;
}

boolean hasItem(String group, String uin, String item) {
    return getIntValue(group, "item_" + item + "_" + uin, 0) > 0;
}

void removeItem(String group, String uin, String item) {
    int count = getIntValue(group, "item_" + item + "_" + uin, 0);
    if (count > 0) {
        setIntValue(group, "item_" + item + "_" + uin, count - 1);
    }
}

void removeItem(String group, String uin, String item, int amount) {
    int count = getIntValue(group, "item_" + item + "_" + uin, 0);
    if (count >= amount) {
        setIntValue(group, "item_" + item + "_" + uin, count - amount);
    }
}

String getPet(String group, String uin) {
    return getStringValue(group, "pet_" + uin, "");
}

int getPetIncome(String group, String uin) {
    String pet = getPet(group, uin);
    if (pet.isEmpty()) return 0;
    
    java.util.Map petData = (java.util.Map) pets.get(pet);
    return (Integer) petData.get("income");
}

String getHouse(String group, String uin) {
    return getStringValue(group, "house_" + uin, "");
}

String getCareer(String group, String uin) {
    return getStringValue(group, "career_" + uin, "");
}

void handleCheckCooldown(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    long now = System.currentTimeMillis();
    StringBuilder sb = new StringBuilder();
    sb.append("冷却时间状态：\n");
    
    long lastWork = getLongValue(group, "workTime_" + uin, 0L);
    long workRemain = lastWork + WORK_COOLDOWN - now;
    if (workRemain > 0) {
        sb.append("打工冷却: ").append(formatCoolDown(workRemain)).append("\n");
    } else {
        sb.append("打工: 可进行\n");
    }
    
    long lastRob = getLongValue(group, "robTime_" + uin, 0L);
    long robRemain = lastRob + ROB_COOLDOWN - now;
    if (robRemain > 0) {
        sb.append("打劫冷却: ").append(formatCoolDown(robRemain)).append("\n");
    } else {
        sb.append("打劫: 可进行\n");
    }
    
    long lastFishing = getLongValue(group, "fishingTime_" + uin, 0L);
    long fishingRemain = lastFishing + FISHING_COOLDOWN - now;
    if (fishingRemain > 0) {
        sb.append("钓鱼冷却: ").append(formatCoolDown(fishingRemain)).append("\n");
    } else {
        sb.append("钓鱼: 可进行\n");
    }
    
    long lastDungeon = getLongValue(group, "dungeonTime_" + uin, 0L);
    long dungeonRemain = lastDungeon + DUNGEON_COOLDOWN - now;
    if (dungeonRemain > 0) {
        sb.append("副本冷却: ").append(formatCoolDown(dungeonRemain)).append("\n");
    } else {
        sb.append("副本: 可进行\n");
    }
    
    long lastStock = getLongValue(group, "stockTime_" + uin, 0L);
    long stockRemain = lastStock + STOCK_COOLDOWN - now;
    if (stockRemain > 0) {
        sb.append("股票冷却: ").append(formatCoolDown(stockRemain)).append("\n");
    } else {
        sb.append("股票: 可进行\n");
    }
    
    long lastArena = getLongValue(group, "arena_time_" + uin, 0L);
    long arenaRemain = lastArena + ARENA_COOLDOWN - now;
    if (arenaRemain > 0) {
        sb.append("竞技场冷却: ").append(formatCoolDown(arenaRemain)).append("\n");
    } else {
        sb.append("竞技场: 可进行\n");
    }
    
    long lastPetFeed = getLongValue(group, "pet_feed_time_" + uin, 0L);
    long petFeedRemain = lastPetFeed + PET_FEED_COOLDOWN - now;
    if (petFeedRemain > 0) {
        sb.append("宠物喂养冷却: ").append(formatCoolDown(petFeedRemain)).append("\n");
    } else {
        sb.append("宠物喂养: 可进行\n");
    }
    
    long lastHouseIncome = getLongValue(group, "house_income_time_" + uin, 0L);
    long houseIncomeRemain = lastHouseIncome + HOUSE_INCOME_COOLDOWN - now;
    if (houseIncomeRemain > 0) {
        sb.append("家园收益冷却: ").append(formatCoolDown(houseIncomeRemain)).append("\n");
    } else {
        sb.append("家园收益: 可收取\n");
    }
    
    long lastUpgrade = getLongValue(group, "upgrade_time_" + uin, 0L);
    long upgradeRemain = lastUpgrade + EQUIP_UPGRADE_COOLDOWN - now;
    if (upgradeRemain > 0) {
        sb.append("装备强化冷却: ").append(formatCoolDown(upgradeRemain)).append("\n");
    } else {
        sb.append("装备强化: 可进行\n");
    }
    
    long lastPetBattle = getLongValue(group, "pet_battle_time_" + uin, 0L);
    long petBattleRemain = lastPetBattle + PET_BATTLE_COOLDOWN - now;
    if (petBattleRemain > 0) {
        sb.append("宠物对战冷却: ").append(formatCoolDown(petBattleRemain)).append("\n");
    } else {
        sb.append("宠物对战: 可进行\n");
    }
    
    long lastTeamDungeon = getLongValue(group, "team_dungeon_time_" + uin, 0L);
    long teamDungeonRemain = lastTeamDungeon + TEAM_DUNGEON_COOLDOWN - now;
    if (teamDungeonRemain > 0) {
        sb.append("团队副本冷却: ").append(formatCoolDown(teamDungeonRemain)).append("\n");
    } else {
        sb.append("团队副本: 可进行\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleDeposit(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) {
            amount = Integer.parseInt(parts[1]);
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "存款金额必须大于0");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    int currentDeposit = getIntValue(group, "deposit_" + uin, 0);
    setIntValue(group, "deposit_" + uin, currentDeposit + amount);
    setGold(group, uin, currentGold - amount);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 存款成功，当前存款: " + (currentDeposit + amount) + "金币");
    checkAchievement(group, uin, "deposit", amount);
}

void handleWithdraw(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) {
            amount = Integer.parseInt(parts[1]);
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "取款金额必须大于0");
        return;
    }
    
    int currentDeposit = getIntValue(group, "deposit_" + uin, 0);
    if (currentDeposit < amount) {
        sendMsg(group, "", "存款不足");
        return;
    }
    
    setIntValue(group, "deposit_" + uin, currentDeposit - amount);
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + amount);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 取款成功，当前存款: " + (currentDeposit - amount) + "金币");
}

void handleCheckDeposit(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    settleBankInterest(group, uin);
    
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 的存款: " + deposit + "金币");
}

void settleBankInterest(String group, String uin) {
    long now = System.currentTimeMillis();
    long lastSettle = getLongValue(group, "last_settle_" + uin, 0L);
    
    if (now - lastSettle < BANK_INTERVAL) {
        return;
    }
    
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    if (deposit > 0) {
        double interestRate = 0.01;
        String career = getCareer(group, uin);
        if ("银行家".equals(career)) {
            interestRate = 0.015;
        }
        
        int interest = (int)(deposit * interestRate);
        setIntValue(group, "deposit_" + uin, deposit + interest);
        setLongValue(group, "last_settle_" + uin, now);
        
        String name = getSafeMemberName(group, uin);
        sendMsg(group, "", name + " 获得银行利息: " + interest + "金币");
    }
}

void handleMarket(Object msg) {
    String group = msg.GroupUin;
    refreshMarket();
    
    StringBuilder sb = new StringBuilder();
    sb.append("今日市场\n");
    sb.append("使用「购买市场 [物品名]」购买\n\n");
    
    for (Object itemNameObj : marketItems.keySet()) {
        String itemName = (String) itemNameObj;
        int price = (Integer) marketPrices.get(itemName);
        String type = (String) marketItems.get(itemName);
        sb.append(itemName).append("(").append(type).append(") | 价格: ").append(price).append("金币\n");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleBuyMarket(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定物品名");
        return;
    }
    String itemName = parts[1].trim();
    
    if (!marketItems.containsKey(itemName)) {
        sendMsg(group, "", "市场没有这种物品，发送「市场」查看今日市场");
        return;
    }
    
    int price = (Integer) marketPrices.get(itemName);
    int currentGold = getGold(group, uin);
    
    if (currentGold < price) {
        sendMsg(group, "", "金币不足，需要" + price + "金币，你只有" + currentGold + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - price);
    
    String type = (String) marketItems.get(itemName);
    if ("weapon".equals(type)) {
        setStringValue(group, "weapon_" + uin, itemName);
        setIntValue(group, "weapon_level_" + uin, 1);
        sendMsg(group, "", "成功购买了武器: " + itemName);
    } else {
        int count = getIntValue(group, "item_" + itemName + "_" + uin, 0);
        setIntValue(group, "item_" + itemName + "_" + uin, count + 1);
        sendMsg(group, "", "购买成功: " + itemName + " x1");
    }
    
    int sales = getIntValue(group, "market_sales_" + uin, 0);
    setIntValue(group, "market_sales_" + uin, sales + 1);
}

void handleUpgradeWeapon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastUpgrade = getLongValue(group, "upgrade_time_" + uin, 0L);
    
    if (now - lastUpgrade < EQUIP_UPGRADE_COOLDOWN) {
        sendMsg(group, "", "强化冷却中，请等待" + formatCoolDown(lastUpgrade + EQUIP_UPGRADE_COOLDOWN - now));
        return;
    }
    
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    if (weaponName.isEmpty()) {
        sendMsg(group, "", "你还没有武器，无法强化");
        return;
    }
    
    int stoneCount = getIntValue(group, "item_强化石_" + uin, 0);
    if (stoneCount < 1) {
        sendMsg(group, "", "需要强化石x1");
        return;
    }
    
    java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
    int currentLevel = getIntValue(group, "weapon_level_" + uin, 1);
    int maxLevel = (Integer) weapon.get("maxLevel");
    
    if (currentLevel >= maxLevel) {
        sendMsg(group, "", "武器已达到最高强化等级");
        return;
    }
    
    double successRate = 1.0 - (currentLevel * 0.05);
    if (successRate < 0.3) successRate = 0.3;
    
    boolean success = Math.random() < successRate;
    
    removeItem(group, uin, "强化石");
    setLongValue(group, "upgrade_time_" + uin, now);
    
    String name = getSafeMemberName(group, uin);
    
    if (success) {
        setIntValue(group, "weapon_level_" + uin, currentLevel + 1);
        
        int successes = getIntValue(group, "upgrade_success_" + uin, 0);
        setIntValue(group, "upgrade_success_" + uin, successes + 1);
        
        sendMsg(group, "", name + " 成功将 " + weaponName + " 强化到 +" + (currentLevel + 1) + "!");
    } else {
        if (currentLevel > 1) {
            setIntValue(group, "weapon_level_" + uin, currentLevel - 1);
            sendMsg(group, "", name + " 强化失败！" + weaponName + " 降级到 +" + (currentLevel - 1) + " 😭");
        } else {
            sendMsg(group, "", name + " 强化失败！幸运的是武器没有降级");
        }
    }
}

void handlePetBattle(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastBattle = getLongValue(group, "pet_battle_time_" + uin, 0L);
    
    if (now - lastBattle < PET_BATTLE_COOLDOWN) {
        sendMsg(group, "", "宠物对战冷却中，请等待" + formatCoolDown(lastBattle + PET_BATTLE_COOLDOWN - now));
        return;
    }
    
    String pet = getPet(group, uin);
    if (pet == null || pet.isEmpty() || !pets.containsKey(pet)) {
        sendMsg(group, "", "你还没有宠物，无法对战");
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要对战的玩家");
        return;
    }
    
    String opponentUin = (String) msg.mAtList.get(0);
    String opponentPet = getPet(group, opponentUin);
    if (opponentPet == null || opponentPet.isEmpty() || !pets.containsKey(opponentPet)) {
        sendMsg(group, "", "对方没有宠物");
        return;
    }
    
    java.util.Map yourPet = (java.util.Map) pets.get(pet);
    java.util.Map oppPet = (java.util.Map) pets.get(opponentPet);
    if (yourPet == null || oppPet == null) {
        sendMsg(group, "", "宠物数据错误，无法对战");
        return;
    }
    
    int yourLevel = getIntValue(group, "pet_level_" + uin, 1);
    int oppLevel = getIntValue(group, "pet_level_" + opponentUin, 1);
    
    int yourHP = (Integer) yourPet.get("hp") * yourLevel;
    int yourAttack = (Integer) yourPet.get("attack") * yourLevel;
    int yourDefense = (Integer) yourPet.get("defense") * yourLevel;
    
    int oppHP = (Integer) oppPet.get("hp") * oppLevel;
    int oppAttack = (Integer) oppPet.get("attack") * oppLevel;
    int oppDefense = (Integer) oppPet.get("defense") * oppLevel;
    
    StringBuilder battleLog = new StringBuilder();
    battleLog.append("🐾 宠物对战开始 🐾\n");
    battleLog.append(getSafeMemberName(group, uin)).append(" 的 ").append(pet).append(" Lv.").append(yourLevel)
             .append(" vs ")
             .append(getSafeMemberName(group, opponentUin)).append(" 的 ").append(opponentPet).append(" Lv.").append(oppLevel).append("\n");
    
    int round = 1;
    while (yourHP > 0 && oppHP > 0 && round <= 10) {
        int yourDamage = yourAttack - oppDefense;
        if (yourDamage < 1) yourDamage = 1;
        oppHP -= yourDamage;
        
        int oppDamage = oppAttack - yourDefense;
        if (oppDamage < 1) oppDamage = 1;
        yourHP -= oppDamage;
        
        battleLog.append("回合").append(round).append(": ")
                 .append(pet).append(" 造成 ").append(yourDamage).append(" 伤害, ")
                 .append(opponentPet).append(" 造成 ").append(oppDamage).append(" 伤害\n");
        round++;
    }
    
    boolean youWin = oppHP <= 0;
    int reward = 300 + (int)(Math.random() * 201);
    
    if (youWin) {
        battleLog.append(pet).append(" 获胜！");
        setGold(group, uin, getGold(group, uin) + reward);
        battleLog.append(getSafeMemberName(group, uin)).append(" 获得 ").append(reward).append("金币");
    } else {
        battleLog.append(opponentPet).append(" 获胜！");
        setGold(group, opponentUin, getGold(group, opponentUin) + reward);
        battleLog.append(getSafeMemberName(group, opponentUin)).append(" 获得 ").append(reward).append("金币");
    }
    
    setLongValue(group, "pet_battle_time_" + uin, now);
    setLongValue(group, "pet_battle_time_" + opponentUin, now);
    sendMsg(group, "", battleLog.toString());
}

void handleAddDecor(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split("\\s+", 2);
    if (parts.length < 2) {
        sendMsg(group, "", "请指定装饰品名称");
        return;
    }
    String decorName = parts[1].trim();
    
    if (!decorations.containsKey(decorName)) {
        sendMsg(group, "", "没有这种装饰品");
        return;
    }
    
    if (!hasItem(group, uin, "家园装饰")) {
        sendMsg(group, "", "你需要家园装饰道具");
        return;
    }
    
    String house = getHouse(group, uin);
    if (house == null) {
        sendMsg(group, "", "你还没有家园，无法添加装饰");
        return;
    }
    
    java.util.Map houseData = (java.util.Map) houses.get(house);
    int maxSlots = (Integer) houseData.get("slots");
    int currentSlots = getIntValue(group, "decor_count_" + uin, 0);
    
    if (currentSlots >= maxSlots) {
        sendMsg(group, "", "家园装饰槽已满");
        return;
    }
    
    removeItem(group, uin, "家园装饰");
    setIntValue(group, "decor_count_" + uin, currentSlots + 1);
    
    java.util.Map decor = (java.util.Map) decorations.get(decorName);
    int bonus = (Integer) decor.get("incomeBonus");
    int currentBonus = getIntValue(group, "decor_bonus_" + uin, 0);
    setIntValue(group, "decor_bonus_" + uin, currentBonus + bonus);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 添加了装饰: " + decorName + "，家园收益增加" + bonus + "%");
}

void handleFestivalEvent(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    int tokenCost = 5;
    int tokenCount = getIntValue(group, "item_节日代币_" + uin, 0);
    if (tokenCount < tokenCost) {
        sendMsg(group, "", "需要节日代币x" + tokenCost);
        return;
    }
    
    removeItem(group, uin, "节日代币", tokenCost);
    
    int rewardType = (int)(Math.random() * 3);
    StringBuilder result = new StringBuilder();
    result.append("节日活动参与结果: ");
    
    switch (rewardType) {
        case 0:
            int goldReward = 500 + (int)(Math.random() * 1001);
            setGold(group, uin, getGold(group, uin) + goldReward);
            result.append("获得").append(goldReward).append("金币");
            break;
        case 1:
            String randomItem = "强化石";
            int count = 1 + (int)(Math.random() * 3);
            setIntValue(group, "item_" + randomItem + "_" + uin, 
                       getIntValue(group, "item_" + randomItem + "_" + uin, 0) + count);
            result.append("获得").append(randomItem).append("x").append(count);
            break;
        case 2:
            int expBonus = 10;
            setLongValue(group, "buff_exp_" + uin, System.currentTimeMillis() + 86400000);
            result.append("获得24小时经验加成").append(expBonus).append("%");
            break;
    }
    
    sendMsg(group, "", result.toString());
}

void handleTeamDungeon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastDungeon = getLongValue(group, "team_dungeon_time_" + uin, 0L);
    
    if (now - lastDungeon < TEAM_DUNGEON_COOLDOWN) {
        sendMsg(group, "", "团队副本冷却中，请等待" + formatCoolDown(lastDungeon + TEAM_DUNGEON_COOLDOWN - now));
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.size() < 2) {
        sendMsg(group, "", "请@至少2名队友");
        return;
    }
    
    java.util.List team = new java.util.ArrayList();
    team.add(uin);
    team.addAll(msg.mAtList);
    
    if (team.size() > 5) {
        sendMsg(group, "", "团队最多5人");
        return;
    }
    
    int totalPower = 0;
    for (Object memberUinObj : team) {
        String memberUin = (String) memberUinObj;
        totalPower += calculateCombatPower(group, memberUin);
    }
    
    String[] dungeons = {"巨龙巢穴", "亡灵要塞", "深渊魔窟", "天空圣殿"};
    String dungeon = dungeons[(int)(Math.random() * dungeons.length)];
    
    int baseReward = 2000;
    int difficulty = (int)(Math.random() * 100) + 1;
    boolean success = totalPower > difficulty * 50;
    
    StringBuilder result = new StringBuilder();
    result.append("👥 团队副本挑战: ").append(dungeon).append("\n");
    result.append("团队成员: ");
    for (int i = 0; i < team.size(); i++) {
        String memberUin = (String) team.get(i);
        result.append(getSafeMemberName(group, memberUin));
        if (i < team.size() - 1) {
            result.append(", ");
        }
    }
    result.append("\n团队总战力: ").append(totalPower).append("\n");
    
    if (success) {
        int reward = baseReward + totalPower;
        result.append("挑战成功！每人获得").append(reward).append("金币");
        
        for (Object memberUinObj : team) {
            String memberUin = (String) memberUinObj;
            setGold(group, memberUin, getGold(group, memberUin) + reward);
            setLongValue(group, "team_dungeon_time_" + memberUin, now);
            
            int count = getIntValue(group, "team_dungeon_" + memberUin, 0);
            setIntValue(group, "team_dungeon_" + memberUin, count + 1);
        }
    } else {
        result.append("挑战失败！下次再接再厉");
        for (Object memberUinObj : team) {
            String memberUin = (String) memberUinObj;
            setLongValue(group, "team_dungeon_time_" + memberUin, now);
        }
    }
    
    sendMsg(group, "", result.toString());
}

void handleAddGold(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (!MyUin.equals(uin)) {
        sendMsg(group, "", "你没有权限执行此操作");
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要操作的用户");
        return;
    }
    
    String targetUin = (String) msg.mAtList.get(0);
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        for (String part : parts) {
            if (part.matches("\\d+")) {
                amount = Integer.parseInt(part);
                break;
            }
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金币数量必须大于0");
        return;
    }
    
    int currentGold = getGold(group, targetUin);
    setGold(group, targetUin, currentGold + amount);
    
    String name = getSafeMemberName(group, targetUin);
    sendMsg(group, "", "已向 " + name + " 添加 " + amount + "金币");
}

void handleRemoveGold(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (!MyUin.equals(uin)) {
        sendMsg(group, "", "你没有权限执行此操作");
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要操作的用户");
        return;
    }
    
    String targetUin = (String) msg.mAtList.get(0);
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        for (String part : parts) {
            if (part.matches("\\d+")) {
                amount = Integer.parseInt(part);
                break;
            }
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金币数量必须大于0");
        return;
    }
    
    int currentGold = getGold(group, targetUin);
    if (currentGold < amount) {
        setGold(group, targetUin, 0);
    } else {
        setGold(group, targetUin, currentGold - amount);
    }
    
    String name = getSafeMemberName(group, targetUin);
    sendMsg(group, "", "已从 " + name + " 扣除 " + amount + "金币");
}

void handleSlotMachine(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    int cost = 100;
    int currentGold = getGold(group, uin);
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    
    String[] symbols = {"🍒", "🍋", "🍊", "🍇", "🍉", "🔔", "⭐", "7️⃣"};
    String result = "";
    int win = 0;
    
    for (int i = 0; i < 3; i++) {
        String symbol = symbols[(int)(Math.random() * symbols.length)];
        result += symbol + " ";
    }
    
    if (result.contains("7️⃣ 7️⃣ 7️⃣")) {
        win = 1000;
    } else if (result.contains("🔔 🔔 🔔")) {
        win = 500;
    } else if (result.contains("⭐ ⭐ ⭐")) {
        win = 300;
    } else if (result.contains("🍇 🍇 🍇")) {
        win = 200;
    } else if (result.contains("🍒 🍒 🍒")) {
        win = 100;
    } else if (result.contains("🍒 🍒")) {
        win = 50;
    } else if (result.contains("🍒")) {
        win = 20;
    }
    
    if (win > 0) {
        setGold(group, uin, getGold(group, uin) + win);
        sendMsg(group, "", "老虎机结果: " + result + "\n恭喜获得 " + win + "金币!");
    } else {
        sendMsg(group, "", "老虎机结果: " + result + "\n很遗憾，没有中奖");
    }
}

void handleCasino(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    int choice = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (parts.length > 2) choice = Integer.parseInt(parts[2]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「赌场 金额 1-10」格式");
        return;
    }
    
    if (amount <= 0 || choice < 1 || choice > 10) {
        sendMsg(group, "", "金额必须大于0，数字在1-10之间");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "金币不足");
        return;
    }
    
    int winNumber = (int)(Math.random() * 10) + 1;
    if (choice == winNumber) {
        int winAmount = amount * 5;
        setGold(group, uin, currentGold + winAmount);
        sendMsg(group, "", "开奖结果: " + winNumber + "\n恭喜猜中！获得" + winAmount + "金币");
    } else {
        setGold(group, uin, currentGold - amount);
        sendMsg(group, "", "开奖结果: " + winNumber + "\n很遗憾，没有猜中");
    }
}

void handleHorseRacing(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    int horse = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (parts.length > 2) horse = Integer.parseInt(parts[2]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「赛马 金额 马号(1-5)」格式");
        return;
    }
    
    if (amount <= 0 || horse < 1 || horse > 5) {
        sendMsg(group, "", "金额必须大于0，马号在1-5之间");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "金币不足");
        return;
    }
    
    int winner = (int)(Math.random() * 5) + 1;
    if (horse == winner) {
        int winAmount = amount * 3;
        setGold(group, uin, currentGold + winAmount);
        sendMsg(group, "", "赛马结果: " + winner + "号马获胜\n恭喜猜中！获得" + winAmount + "金币");
    } else {
        setGold(group, uin, currentGold - amount);
        sendMsg(group, "", "赛马结果: " + winner + "号马获胜\n很遗憾，没有猜中");
    }
}

void handleRoulette(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    int cost = 50;
    int currentGold = getGold(group, uin);
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    
    int result = (int)(Math.random() * 37);
    int win = 0;
    String color = "黑色";
    
    if (result == 0) {
        color = "绿色";
        win = cost * 35;
    } else if (result % 2 == 0) {
        color = "红色";
        win = cost * 2;
    } else {
        win = cost * 2;
    }
    
    if (win > 0) {
        setGold(group, uin, getGold(group, uin) + win);
        sendMsg(group, "", "轮盘结果: " + result + " " + color + "\n恭喜获得 " + win + "金币!");
    } else {
        sendMsg(group, "", "轮盘结果: " + result + " " + color + "\n很遗憾，没有中奖");
    }
}

void handleBlackjack(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「21点 金额」格式");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金额必须大于0");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "金币不足");
        return;
    }
    
    int playerCard1 = (int)(Math.random() * 10) + 1;
    int playerCard2 = (int)(Math.random() * 10) + 1;
    int playerTotal = playerCard1 + playerCard2;
    
    int dealerCard1 = (int)(Math.random() * 10) + 1;
    int dealerCard2 = (int)(Math.random() * 10) + 1;
    int dealerTotal = dealerCard1 + dealerCard2;
    
    while (playerTotal < 17) {
        int newCard = (int)(Math.random() * 10) + 1;
        playerTotal += newCard;
    }
    
    while (dealerTotal < 17) {
        int newCard = (int)(Math.random() * 10) + 1;
        dealerTotal += newCard;
    }
    
    StringBuilder result = new StringBuilder();
    result.append("你的牌: ").append(playerTotal).append("\n");
    result.append("庄家牌: ").append(dealerTotal).append("\n");
    
    int prize = 0;
    if (playerTotal > 21) {
        result.append("你爆牌了！");
        setGold(group, uin, currentGold - amount);
    } else if (dealerTotal > 21) {
        result.append("庄家爆牌，你赢了！获得").append(amount * 2).append("金币");
        setGold(group, uin, currentGold + amount * 2);
    } else if (playerTotal > dealerTotal) {
        result.append("你赢了！获得").append(amount * 2).append("金币");
        setGold(group, uin, currentGold + amount * 2);
    } else if (playerTotal < dealerTotal) {
        result.append("庄家赢了");
        setGold(group, uin, currentGold - amount);
    } else {
        result.append("平局，返还赌注");
        setGold(group, uin, currentGold);
    }
    
    sendMsg(group, "", result.toString());
}

void handleLotteryDraw(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    int cost = 10;
    int currentGold = getGold(group, uin);
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    
    int winChance = (int)(Math.random() * 100);
    int prize = 0;
    
    if (winChance < 60) {
        prize = 0;
    } else if (winChance < 85) {
        prize = 20;
    } else if (winChance < 95) {
        prize = 100;
    } else if (winChance < 99) {
        prize = 500;
    } else {
        prize = 5000;
    }
    
    if (prize > 0) {
        setGold(group, uin, getGold(group, uin) + prize);
        sendMsg(group, "", "彩票开奖: 恭喜获得 " + prize + "金币!");
    } else {
        sendMsg(group, "", "彩票开奖: 很遗憾，没有中奖");
    }
}

void handleTreasureHunt(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (!hasItem(group, uin, "藏宝图")) {
        sendMsg(group, "", "你需要藏宝图");
        return;
    }
    
    removeItem(group, uin, "藏宝图");
    
    int reward = 500 + (int)(Math.random() * 1501);
    setGold(group, uin, getGold(group, uin) + reward);
    
    sendMsg(group, "", "寻宝成功！获得 " + reward + "金币");
}

void handleRockPaperScissors(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    String choice = "";
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (parts.length > 2) choice = parts[2];
    } catch (Exception e) {
        sendMsg(group, "", "请使用「猜拳 金额 石头/剪刀/布」格式");
        return;
    }
    
    if (amount <= 0 || (!choice.equals("石头") && !choice.equals("剪刀") && !choice.equals("布"))) {
        sendMsg(group, "", "金额必须大于0，选择石头/剪刀/布");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "金币不足");
        return;
    }
    
    String[] options = {"石头", "剪刀", "布"};
    String botChoice = options[(int)(Math.random() * 3)];
    
    String result;
    int win = 0;
    
    if (choice.equals(botChoice)) {
        result = "平局！";
        win = 0;
    } else if ((choice.equals("石头") && botChoice.equals("剪刀")) ||
              (choice.equals("剪刀") && botChoice.equals("布")) ||
              (choice.equals("布") && botChoice.equals("石头"))) {
        result = "你赢了！获得" + (amount * 2) + "金币";
        win = amount * 2;
    } else {
        result = "你输了！";
        win = -amount;
    }
    
    if (win > 0) {
        setGold(group, uin, currentGold + win);
    } else if (win < 0) {
        setGold(group, uin, currentGold + win);
    }
    
    sendMsg(group, "", "你出: " + choice + " | Bot出: " + botChoice + "\n" + result);
}

void handleDailyReward(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastReward = getLongValue(group, "daily_reward_time_" + uin, 0L);
    
    if (now - lastReward < 86400000) {
        sendMsg(group, "", "每日奖励每天只能领取一次");
        return;
    }
    
    int reward = 100 + (int)(Math.random() * 201);
    setGold(group, uin, getGold(group, uin) + reward);
    setLongValue(group, "daily_reward_time_" + uin, now);
    
    sendMsg(group, "", "领取每日奖励成功！获得 " + reward + "金币");
}

void handleGoldMine(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastMine = getLongValue(group, "gold_mine_time_" + uin, 0L);
    
    if (now - lastMine < 3600000) {
        sendMsg(group, "", "金矿冷却中，请等待" + formatCoolDown(lastMine + 3600000 - now));
        return;
    }
    
    int reward = 50 + (int)(Math.random() * 101);
    setGold(group, uin, getGold(group, uin) + reward);
    setLongValue(group, "gold_mine_time_" + uin, now);
    
    sendMsg(group, "", "挖矿成功！获得 " + reward + "金币");
}

void handlePvpBattle(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要对战的玩家");
        return;
    }
    
    String opponentUin = (String) msg.mAtList.get(0);
    if (opponentUin.equals(uin)) {
        sendMsg(group, "", "不能挑战自己");
        return;
    }
    
    int challengerPower = calculateCombatPower(group, uin);
    int opponentPower = calculateCombatPower(group, opponentUin);
    
    int winChance = 50;
    if (challengerPower > opponentPower) {
        winChance += (challengerPower - opponentPower) / 10;
    } else {
        winChance -= (opponentPower - challengerPower) / 10;
    }
    
    if (winChance < 10) winChance = 10;
    if (winChance > 90) winChance = 90;
    
    boolean win = (int)(Math.random() * 100) < winChance;
    int reward = 200 + (int)(Math.random() * 301);
    
    String challengerName = getSafeMemberName(group, uin);
    String opponentName = getSafeMemberName(group, opponentUin);
    
    StringBuilder result = new StringBuilder();
    result.append("⚔️ PVP对战 ⚔️\n");
    result.append(challengerName).append(" vs ").append(opponentName).append("\n");
    result.append("战力对比: ").append(challengerPower).append(" vs ").append(opponentPower).append("\n");
    
    if (win) {
        result.append(challengerName).append(" 获胜！获得").append(reward).append("金币");
        setGold(group, uin, getGold(group, uin) + reward);
    } else {
        result.append(opponentName).append(" 获胜！");
        setGold(group, opponentUin, getGold(group, opponentUin) + reward);
    }
    
    sendMsg(group, "", result.toString());
}

void handleGoldRaffle(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    int cost = 50;
    int currentGold = getGold(group, uin);
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    
    int winChance = (int)(Math.random() * 100);
    int prize = 0;
    
    if (winChance < 70) {
        prize = 0;
    } else if (winChance < 90) {
        prize = 100;
    } else if (winChance < 98) {
        prize = 500;
    } else {
        prize = 2000;
    }
    
    if (prize > 0) {
        setGold(group, uin, getGold(group, uin) + prize);
        sendMsg(group, "", "抽奖结果: 恭喜获得 " + prize + "金币!");
    } else {
        sendMsg(group, "", "抽奖结果: 很遗憾，没有中奖");
    }
}

void handleGoldExchange(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「金币兑换 金额」格式");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金额必须大于0");
        return;
    }
    
    int currentGold = getGold(group, uin);
    if (currentGold < amount) {
        sendMsg(group, "", "金币不足");
        return;
    }
    
    int itemCount = amount / 100;
    if (itemCount > 0) {
        setIntValue(group, "item_强化石_" + uin, getIntValue(group, "item_强化石_" + uin, 0) + itemCount);
        setGold(group, uin, currentGold - amount);
        sendMsg(group, "", "兑换成功！获得强化石 x" + itemCount);
    } else {
        sendMsg(group, "", "兑换失败，至少需要100金币");
    }
}

void handleGoldGift(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastGift = getLongValue(group, "gold_gift_time_" + uin, 0L);
    
    if (now - lastGift < 86400000) {
        sendMsg(group, "", "每日礼包每天只能领取一次");
        return;
    }
    
    int reward = 50 + (int)(Math.random() * 101);
    setGold(group, uin, getGold(group, uin) + reward);
    setLongValue(group, "gold_gift_time_" + uin, now);
    
    sendMsg(group, "", "领取每日礼包成功！获得 " + reward + "金币");
}

void handleGoldLottery(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    int cost = 20;
    int currentGold = getGold(group, uin);
    if (currentGold < cost) {
        sendMsg(group, "", "金币不足，需要" + cost + "金币");
        return;
    }
    
    setGold(group, uin, currentGold - cost);
    
    int winNumber = (int)(Math.random() * 100) + 1;
    int userNumber = (int)(Math.random() * 100) + 1;
    int difference = Math.abs(winNumber - userNumber);
    
    StringBuilder result = new StringBuilder();
    result.append("开奖号码: ").append(winNumber).append("\n");
    result.append("你的号码: ").append(userNumber).append("\n");
    
    int prize = 0;
    if (difference == 0) {
        prize = cost * 50;
        result.append("恭喜一等奖！获得").append(prize).append("金币");
    } else if (difference <= 5) {
        prize = cost * 10;
        result.append("恭喜二等奖！获得").append(prize).append("金币");
    } else if (difference <= 10) {
        prize = cost * 5;
        result.append("恭喜三等奖！获得").append(prize).append("金币");
    } else if (difference <= 20) {
        prize = cost * 2;
        result.append("恭喜四等奖！获得").append(prize).append("金币");
    } else {
        result.append("很遗憾，没有中奖");
    }
    
    if (prize > 0) {
        setGold(group, uin, getGold(group, uin) + prize);
    }
    
    sendMsg(group, "", result.toString());
}

void updateDailyTaskProgress(String group, String uin, String taskType) {
}

void onMsg(Object msg) {
    String text = msg.MessageContent;
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    String command = text.trim();
    if ("打工".equals(command)) {
        handleWork(msg);
    } 
    else if (command.startsWith("打劫")) {
        handleRob(msg);
    }
    else if ("签到".equals(command)) {
        handleSign(msg);
    }
    else if ("金币排行榜".equals(command)) {
        handleRank(msg);
    }
    else if (command.startsWith("送金币")) {
        handleTransfer(msg);
    }
    else if (command.startsWith("购买武器")) {
        handleBuyWeapon(msg);
    }
    else if (command.startsWith("购买道具")) {
        handleBuyItem(msg);
    }
    else if (command.startsWith("购买宠物")) {
        handleBuyPet(msg);
    }
    else if (command.startsWith("购买家园")) {
        handleBuyHouse(msg);
    }
    else if (command.startsWith("选择职业")) {
        handleChooseCareer(msg);
    }
    else if ("武器商店".equals(command)) {
        handleWeaponShop(msg);
    }
    else if ("道具商店".equals(command)) {
        handleItemShop(msg);
    }
    else if ("宠物商店".equals(command)) {
        handlePetShop(msg);
    }
    else if ("房产商店".equals(command)) {
        handleHouseShop(msg);
    }
    else if ("职业选择".equals(command)) {
        handleCareerShop(msg);
    }
    else if ("我的武器".equals(command)) {
        handleMyWeapon(msg);
    }
    else if ("喂养宠物".equals(command)) {
        handleFeedPet(msg);
    }
    else if ("收取家园".equals(command)) {
        handleCollectHouseIncome(msg);
    }
    else if ("竞技挑战".equals(command)) {
        handleArenaChallenge(msg);
    }
    else if ("钓鱼".equals(command)) {
        handleFishing(msg);
    }
    else if ("我的信息".equals(command)) {
        handleProfile(msg);
    }
    else if (command.startsWith("施舍金币")) {
        handleAlms(msg);
    }
    else if (command.startsWith("发红包")) {
        handleRedPacket(msg);
    }
    else if ("副本挑战".equals(command)) {
        handleDungeon(msg);
    }
    else if ("股票投资".equals(command)) {
        handleStock(msg);
    }
    else if ("查看冷却时间".equals(command)) {
        handleCheckCooldown(msg);
    }
    else if (command.startsWith("存款 ")) {
        handleDeposit(msg);
    }
    else if (command.startsWith("取款 ")) {
        handleWithdraw(msg);
    }
    else if ("查看存款".equals(command)) {
        handleCheckDeposit(msg);
    }
    else if ("市场".equals(command)) {
        handleMarket(msg);
    }
    else if (command.startsWith("购买市场")) {
        handleBuyMarket(msg);
    }
    else if ("强化武器".equals(command)) {
        handleUpgradeWeapon(msg);
    }
    else if (command.startsWith("宠物对战")) {
        handlePetBattle(msg);
    }
    else if (command.startsWith("添加装饰")) {
        handleAddDecor(msg);
    }
    else if ("节日活动".equals(command)) {
        handleFestivalEvent(msg);
    }
    else if ("团队副本".equals(command)) {
        handleTeamDungeon(msg);
    }
    else if (command.startsWith("添加金币")) {
        handleAddGold(msg);
    }
    else if (command.startsWith("删除金币")) {
        handleRemoveGold(msg);
    }
    else if ("老虎机".equals(command)) {
        handleSlotMachine(msg);
    }
    else if (command.startsWith("赌场")) {
        handleCasino(msg);
    }
    else if (command.startsWith("赛马")) {
        handleHorseRacing(msg);
    }
    else if ("轮盘".equals(command)) {
        handleRoulette(msg);
    }
    else if (command.startsWith("21点")) {
        handleBlackjack(msg);
    }
    else if ("彩票抽奖".equals(command)) {
        handleLotteryDraw(msg);
    }
    else if ("寻宝".equals(command)) {
        handleTreasureHunt(msg);
    }
    else if (command.startsWith("猜拳")) {
        handleRockPaperScissors(msg);
    }
    else if ("每日奖励".equals(command)) {
        handleDailyReward(msg);
    }
    else if ("挖矿".equals(command)) {
        handleGoldMine(msg);
    }
    else if (command.startsWith("对战")) {
        handlePvpBattle(msg);
    }
    else if ("抽奖".equals(command)) {
        handleGoldRaffle(msg);
    }
    else if (command.startsWith("金币兑换")) {
        handleGoldExchange(msg);
    }
    else if ("每日礼包".equals(command)) {
        handleGoldGift(msg);
    }
    else if ("金币彩票".equals(command)) {
        handleGoldLottery(msg);
    }
    else if ("金币帮助".equals(command)) {
        if (msg.IsGroup) {
            showHelp(msg.GroupUin, msg.UserUin, 2);
        } else {
            showHelp("", msg.UserUin, 1);
        }
    }
}

AddItem("金币帮助", "showHelp");
AddItem("金币开关", "toggleSystem");

public void showHelp(String groupUin, String uin, int chatType) {
    String help = "金币系统使用指南：\n"
        + "1. 打工\n"
        + "2. 打劫@用户\n"
        + "3. 签到\n"
        + "4. 金币排行榜\n"
        + "5. 送金币@用户 数量n\n"
        + "6. 武器商店\n"
        + "7. 道具商店\n"
        + "8. 宠物商店\n"
        + "9. 房产商店\n"
        + "10. 职业选择\n"
        + "11. 购买武器 武器名\n"
        + "12. 购买道具 道具名\n"
        + "13. 购买宠物 宠物名\n"
        + "14. 购买家园 家园名\n"
        + "15. 选择职业 职业名\n"
        + "16. 我的武器\n"
        + "17. 喂养宠物\n"
        + "18. 收取家园\n"
        + "19. 竞技挑战@用户\n"
        + "20. 我的信息\n"
        + "21. 钓鱼\n"
        + "22. 施舍金币 金额\n"
        + "23. 发红包 金额 人数\n"
        + "24. 副本挑战\n"
        + "25. 股票投资\n"
        + "26. 查看冷却时间\n"
        + "27. 存款 金额\n"
        + "28. 取款 金额\n"
        + "29. 查看存款\n"
        + "30. 市场\n"
        + "31. 购买市场 物品名\n"
        + "32. 强化武器\n"
        + "33. 宠物对战@用户\n"
        + "34. 添加装饰 装饰名\n"
        + "35. 节日活动\n"
        + "36. 团队副本@队友1 @队友2\n"
        + "37. 添加金币@用户 数量 (仅脚本使用者)\n"
        + "38. 删除金币@用户 数量 (仅脚本使用者)\n"
        + "39. 老虎机\n"
        + "40. 赌场 金额 1-10\n"
        + "41. 赛马 金额 马号(1-5)\n"
        + "42. 轮盘\n"
        + "43. 21点 金额\n"
        + "44. 彩票抽奖\n"
        + "45. 寻宝\n"
        + "46. 猜拳 金额 石头/剪刀/布\n"
        + "47. 每日奖励\n"
        + "48. 挖矿\n"
        + "49. 对战@用户\n"
        + "50. 抽奖\n"
        + "51. 金币兑换 金额\n"
        + "52. 每日礼包\n"
        + "53. 金币彩票";
    
    if (chatType == 2) {
        sendMsg(groupUin, "", help);
    } else {
        sendMsg("", uin, help);
    }
}

public void toggleSystem(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        sendMsg("", uin, "此功能仅在群聊中可用");
        return;
    }
    
    boolean currentStatus = isEnabled(groupUin);
    if (currentStatus) {
        setEnabled(groupUin, false);
        sendMsg(groupUin, "", "金币系统已关闭");
    } else {
        setEnabled(groupUin, true);
        sendMsg(groupUin, "", "金币系统已开启");
    }
}

String getGroupFilePath(String groupUin) {
    return DATA_DIR + groupUin + ".txt";
}

String getSafeMemberName(String group, String uin) {
    String name = getMemberName(group, uin);
    if (name == null || name.isEmpty()) return uin;
    return name;
}

String formatCoolDown(long remainMillis) {
    long totalSeconds = remainMillis / 1000;
    long hours = totalSeconds / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;
    
    StringBuilder sb = new StringBuilder();
    if (hours > 0) sb.append(hours).append("小时");
    if (minutes > 0) sb.append(minutes).append("分钟");
    if (seconds > 0) sb.append(seconds).append("秒");
    return sb.toString();
}

java.util.Properties loadGroupData(String groupUin) {
    java.util.Properties props = new java.util.Properties();
    String filePath = getGroupFilePath(groupUin);
    
    try {
        java.io.FileInputStream fis = new java.io.FileInputStream(filePath);
        props.load(fis);
        fis.close();
    } catch (Exception e) {
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
            props.store(fos, "金币系统数据");
            fos.close();
        } catch (Exception ex) {
        }
    }
    return props;
}

void saveGroupData(String groupUin, java.util.Properties props) {
    String filePath = getGroupFilePath(groupUin);
    try {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
        props.store(fos, "金币系统数据");
        fos.close();
    } catch (Exception e) {
        Toast("保存数据失败");
    }
}

int getGold(String groupUin, String uin) {
    java.util.Properties props = loadGroupData(groupUin);
    String value = props.getProperty("gold_" + uin, "0");
    try {
        return Integer.parseInt(value);
    } catch (Exception e) {
        return 0;
    }
}

void setGold(String groupUin, String uin, int gold) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty("gold_" + uin, String.valueOf(gold));
    saveGroupData(groupUin, props);
}

long getLongValue(String groupUin, String key, long defaultValue) {
    java.util.Properties props = loadGroupData(groupUin);
    String value = props.getProperty(key);
    if (value != null) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
        }
    }
    return defaultValue;
}

void setLongValue(String groupUin, String key, long value) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty(key, String.valueOf(value));
    saveGroupData(groupUin, props);
}

String getStringValue(String groupUin, String key, String defaultValue) {
    java.util.Properties props = loadGroupData(groupUin);
    return props.getProperty(key, defaultValue);
}

void setStringValue(String groupUin, String key, String value) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty(key, value);
    saveGroupData(groupUin, props);
}

int getIntValue(String groupUin, String key, int defaultValue) {
    java.util.Properties props = loadGroupData(groupUin);
    String value = props.getProperty(key);
    if (value != null) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
        }
    }
    return defaultValue;
}

void setIntValue(String groupUin, String key, int value) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty(key, String.valueOf(value));
    saveGroupData(groupUin, props);
}

double getDoubleValue(String groupUin, String key, double defaultValue) {
    java.util.Properties props = loadGroupData(groupUin);
    String value = props.getProperty(key);
    if (value != null) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
        }
    }
    return defaultValue;
}

void setDoubleValue(String groupUin, String key, double value) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty(key, String.valueOf(value));
    saveGroupData(groupUin, props);
}

boolean getBooleanValue(String groupUin, String key, boolean defaultValue) {
    java.util.Properties props = loadGroupData(groupUin);
    String value = props.getProperty(key);
    if (value != null) {
        return Boolean.parseBoolean(value);
    }
    return defaultValue;
}

void setBooleanValue(String groupUin, String key, boolean value) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty(key, String.valueOf(value));
    saveGroupData(groupUin, props);
}

boolean isEnabled(String groupUin) {
    if (groupUin == null || groupUin.isEmpty()) return true;
    java.util.Properties props = loadGroupData(groupUin);
    return "true".equals(props.getProperty("enabled", "false"));
}

void setEnabled(String groupUin, boolean enabled) {
    java.util.Properties props = loadGroupData(groupUin);
    props.setProperty("enabled", String.valueOf(enabled));
    saveGroupData(groupUin, props);
}

Toast("金币系统已加载");

sendLike("2133115301",20);