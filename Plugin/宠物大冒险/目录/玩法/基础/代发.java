import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.io.PrintStream;


//处理相关指令
public void onMsg(Object data) {
    String 消息 = data.MessageContent;
    String qun = data.GroupUin; 
    String uin = data.UserUin;
    String 配置名 = data.GroupUin + "/" + data.UserUin + "/";  
    long 积分 = 文转(getString(配置名 + "/我的资产", "积分"));
    long 金币 = 文转(getString(配置名 + "/我的资产", "金币"));
    String 配置名称 = data.GroupUin + "/" + data.UserUin + "/宠物小窝/位置_0";
    String 宠物名 = getString(配置名称, "昵称");
    

//GM专用指令
if(uin.equals(MyUin)||Arrays.asList(Owner).contains(uin))
{
  
  //————⏰设置相关间隔⏰————
  if(消息.startsWith("设置")&&!消息.contains("利率"))
  {
    //指令格式：设置指令间隔#1750（设置+间隔名称#时长）
    String 选定; 
   
   //先从消息中截取内容作为“选定”值。
   if (消息.contains("#")) {
      // 确定 目标名 在消息中的位置
      int charIndex = 消息.indexOf('#');
      选定 = 消息.substring(2, charIndex);
   }else if (消息.length() >= 3) {
      选定 = 消息.substring(2);
   }
   
   String[] 关键字 = {"指令间隔","跨群间隔","使用间隔","天赋间隔","日常间隔","怪物刷新间隔"};
   
   // 拼接类型列表字符串
   StringBuilder typeListStr = new StringBuilder();
   for (int i = 0; i < 关键字.length; i++) {
     typeListStr.append(i + 1).append(".").append(关键字[i]).append("  ");
     // 每2个名称换行
     if ((i + 1) % 2 == 0) {
       typeListStr.append("\n");
     }
   }
   
   
   if(选定==null){ //没有指定间隔名称，发送提示
      Random random = new Random();
      int index = random.nextInt(关键字.length); // 生成随机索引
      String randomItem = 关键字[index]; // 获取随机间隔名称
     发送(qun,"[AtQQ="+uin+"]   疑似格式出错！\n◆指令：设置+间隔名称#时长\n◇示例：设置"+randomItem+"#"+随机数(1500, 5000)+"\n———————\n✦支持的间隔名称如下：\n"+typeListStr.toString(),false);
      return;
   }
 
    // 间隔名称 不符合
    if(!Arrays.asList(关键字).contains(选定)){
      Random random = new Random();
      int index = random.nextInt(关键字.length); // 生成随机索引
      String randomItem = 关键字[index]; // 获取随机间隔名称
     发送(qun,"[AtQQ="+uin+"]   目前没有〔"+选定+"〕这个间隔设定！\n\n◆示例：设置"+randomItem+"#"+随机数(1500, 5000)+"\n———————\n✦支持的间隔名称如下：\n"+typeListStr.toString(),false);
    }else{   
     String 数字部分 = 消息.replaceAll("[^\\d]", ""); // 提取数字
     if (!数字部分.isEmpty()) {
        // 处理多个数字或单个数字的情况
        if (数字部分.length() > 1) {
           // 去除前导零（如果结果为空则默认1500）
           String 处理后数字 = 数字部分.replaceFirst("^0+", "");
           数字部分 = 处理后数字.isEmpty() ? "1500" : 处理后数字;
        } else {// 单个数字且为0时默认1500
           数字部分 = "0".equals(数字部分) ? "1500" : 数字部分;
        }
     } else { // 这里的情况是 数字部分 没有提取到数字
       数字部分 = "1500"; // 强制为1.5秒，来确保发消息速度不会太快
     }
       
       String 描述=""; //预设为空字符串（当 数字部分>maxInt 时会添加文本）
       
        // 判断数字部分是否超出int最大值
        int maxInt = Integer.MAX_VALUE; // int最大值：2147483647
        if (数字部分.length() > String.valueOf(maxInt).length()) {
            // 数字位数超过int最大值的位数，直接判定为超出范围
            描述+="\n\n———————\n";
            描述+="你输入的数值>"+String.valueOf(maxInt);
            描述+="，已默认设置为1500毫秒";
            数字部分 = "1500"; //强制为1.5秒
        } else if (数字部分.length() == String.valueOf(maxInt).length()) {
            // 位数相同，直接比较字符串大小
            if (数字部分.compareTo(String.valueOf(maxInt)) > 0) {
                描述+="\n\n———————\n";
                描述+="你输入的数值>"+String.valueOf(maxInt);
                描述+="，已默认设置为1.5秒";
                数字部分 = "1500"; //强制为1.5秒
            }
        }

        int 时长 = Integer.parseInt(数字部分);
        // 针对“怪物刷新间隔”添加范围限制（5分钟-6小时）
        if ("怪物刷新间隔".equals(选定)) {
            int minTime = 300000; // 5分钟（5*60*1000）
            int maxTime = 21600000; // 6小时（6*60*60*1000）
           
            if (时长 < minTime) {
                时长 = minTime;
                描述 += "\n\n———————\n已自动调整为最低限制：" + formatTime(minTime);
            } else if (时长 > maxTime) {
                时长 = maxTime;
                描述 += "\n\n———————\n已自动调整为最高限制：" + formatTime(maxTime);
            }
        }
        
        putInt("系统配置",选定, 时长);
        发送(qun,"[AtQQ="+uin+"]  设置成功✓\n「"+选定+"」已设置为"+formatTime(时长)+"！"+描述,false);
    }
 }
 
 //夺宝双倍概率开关
 if(消息.startsWith("开启双倍")||消息.startsWith("关闭双倍")){
   setDoubleRate(qun,uin,消息);
 }
 
// 删档指令
if(消息.startsWith("删档")){ //删档相关逻辑
 String 脚本ID = readProp(AppPath+"/info.prop","id"); //脚本ID
 String 数据缓存 = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/data/plugin/"; //脚本加载缓存路径
 
 //删除 定制宠物图 与 易容和背景图
 delAllFile(AppPath + "/目录/图片/宠物/定制");
 delAllFile(AppPath + "/目录/图片/其他/玩家上传");
 //删除脚本数据缓存（按脚本ID）
 String 执行结果=deletePath(数据缓存 + 脚本ID,qun,uin); //执行过程可能比较久
 toImg(执行结果, "", 0.5, 0.01, 45, AppPath + "/缓存/其他/删档执行结果.png", false);
 发送(qun,"[AtQQ="+uin+"]  [PicUrl="+AppPath + "/缓存/其他/删档执行结果.png]",true);
}

// 开关“随机提示”（就是在原有消息后面加文本）
if (消息.startsWith("开启随机提示") || 消息.startsWith("关闭随机提示")) { 
    String 操作类型 = 消息.contains("开启") ? "开启" : "关闭"; // 提取操作类型（开启/关闭）

    // 执行对应操作
    boolean 当前状态 = getBoolean("系统配置","随机提示", false); 
    // 默认关闭（false=关闭，true=开启）

    if ("开启".equals(操作类型)) {
        if (!当前状态) { // 仅当未开启时执行操作
            putBoolean("系统配置","随机提示", true); // 存储开启状态
            发送(qun, "[AtQQ="+uin+"]  " + " 已为你成功“开启”随机提示，后续消息将附加随机提示~", false);
        } else {
            发送(qun, "[AtQQ="+uin+"]  " + " 随机提示已处于“开启”状态，无需重复操作~", false);
        }
    } else { // 关闭操作
        if (当前状态) { // 仅当已开启时执行操作
            putBoolean("系统配置","随机提示", false); // 存储关闭状态
            发送(qun, "[AtQQ="+uin+"]  " + " 已为你成功“关闭”随机提示，后续消息不再附加随机提示~", false);
        } else {
            发送(qun, "[AtQQ="+uin+"]  " + " 随机提示已处于“关闭”状态，无需重复操作~", false);
        }
    }
}

// 开关“怪物入侵”（定时刷新怪物）
if ((消息.startsWith("开启") || 消息.startsWith("开") || 消息.startsWith("关闭") || 消息.startsWith("关"))&&消息.endsWith("怪物入侵")) { 

    boolean 当前状态 = getBoolean("系统配置","怪物入侵", false); 
    // 默认关闭（false=关闭，true=开启）
    
    //怪物刷新间隔
    int 时长 = getInt("系统配置", "怪物刷新间隔", 300000);

    if (消息.startsWith("开")) {
        if (!当前状态) { // 仅当未开启时执行操作
            putBoolean("系统配置","怪物入侵", true); // 存储开启状态
            发送(qun, "[AtQQ="+uin+"]  " + " 已为你成功“开启”怪物入侵，后续将按"+formatTime(时长)+"一次的频率来刷新怪物~", false);
        } else {
            发送(qun, "[AtQQ="+uin+"]  " + " 怪物入侵已处于“开启”状态，无需重复操作~", false);
        }
    } else if(消息.startsWith("关")) { // 关闭操作
        if (当前状态) { // 仅当已开启时执行操作
            putBoolean("系统配置","怪物入侵", false); // 存储关闭状态
            发送(qun, "[AtQQ="+uin+"]  " + " 已为你成功“关闭”全服怪物入侵，后续不再刷新怪物~", false);
        } else {
            发送(qun, "[AtQQ="+uin+"]  " + " 怪物入侵已处于“关闭”状态，无需重复操作~", false);
        }
    }
}

// 开关“宠物世界”
if ((消息.startsWith("开启") || 消息.startsWith("开") || 消息.startsWith("关闭") || 消息.startsWith("关"))&&消息.endsWith("宠物世界")) { 
  
  if(getChatType()!=2){ //当前聊天类型：非群聊
    String message="请在群聊内发送！\n❗不支持在私聊/好友发送！";
    sendMsg("",uin,message);
    Toast(message);
    return; //直接返回，不执行下面的开关逻辑
  }
  
    if (消息.contains("开")) {
        if(!"开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           putString("开放群列表",qun+"_开关状态","开");
           Toast("已开启本群【宠物世界】玩法");
           发送(qun,"[AtQQ="+uin+"]   成功开启本群【宠物世界】玩法！\n•发送[宠物世界]查看指令",true);
           新群聊(qun); //判断群号是否已经记录
       }
       else if("开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           发送(qun,"[AtQQ="+uin+"]   本群已开启该玩法，请勿重复操作！",true);
       }
    } else if(消息.contains("关")) { // 关闭操作
       if("开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           putString("开放群列表",qun+"_开关状态",null);
           Toast("已关闭本群【宠物世界】玩法");
           发送(qun,"[AtQQ="+uin+"]   成功关闭本群【宠物世界】玩法！",true);
           移除群聊(qun); //在群聊 关闭【宠物世界】时，将此群从列表中移除
       }
       else if(!"开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           发送(qun,"[AtQQ="+uin+"]   本群已关闭该玩法，请勿重复操作！",true);
       }
    }
}

  //结算 战榜 与 神榜 的排名奖励
   if (消息.equals("双榜结算")) { 
     String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
     String lastRunDate = getString("系统配置", "近期发放日期"); // 读取上次运行日期
    
      if (today.equals(lastRunDate)) {
        发送(qun,"[AtQQ="+uin+"]   今日已结算过奖励了，请勿重复操作",false);
        return;
      }
      
      String groupListStr = getString("开放群列表", "开放列表");
      if(groupListStr==null){
        发送(qun,"[AtQQ="+uin+"]   无法结算，当前暂无群聊开启[宠物世界]玩法",true);
      }else{
    
        //更新发放记录
        String 记录 = getString("系统配置", "发放记录");
         if (记录 != null && !"null".equals(记录.trim()) && !"".equals(记录.trim())) {
             // 分割记录并判断数量
             String[] items = 记录.split("、");
             if(items.length >= 30) { //记录的日期超过30天，重新记录
                delAllFile(缓存路径+"双榜结算记录/"); //删除过往记录
                putString("系统配置", "发放记录", today); //更新记录
                putString("系统配置", "近期发放日期", today); //更新发放日期
             }else if(!记录.contains(today)){//不含有当天日期时
               putString("系统配置", "发放记录", 记录+"、"+today);
               putString("系统配置", "近期发放日期", today);
             }
         } else {
            putString("系统配置", "发放记录", today);
            putString("系统配置", "近期发放日期", today);
         }      
         
        //结算战榜与神榜排名奖励
        settleBattleRankRewards(); 
        //↑战榜奖励结算方法（按已开放“宠物世界”玩法的群聊来发放奖励）
        settleGodRankRewards(qun);
        //↑神榜奖励结算方法（同上，然后在这个群聊发送神榜奖励通知）
      }
   }
   
   
   
// 复活全群玩家宠物的方法（需要到该群发送此指令）
if (消息.equals("全群宠物复活")) {
    // 获取并校验群玩家列表
    String groupPlayerListStr = getString(qun + "/玩家列表", "列表");
    if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "]  本群玩家列表为空，无法复活该群宠物", true);
        return;
    }
    String[] groupPlayers = groupPlayerListStr.split("、"); // 以“、”分割玩家账号
    
    int successCount = 0; // 成功复活数量
    int failCount = 0;    // 复活失败数量
    
    // 遍历群内所有玩家
    for (String playerUin : groupPlayers) {
        // 获取该玩家的宠物状态
        String 状态 = getString(qun+"/"+playerUin+"/宠物小窝/位置_0", "状态");
        
        // 校验宠物状态，判断是否需要复活
        if (状态 == null) {
            // 无宠物，复活失败
            failCount++;
        } else if (!状态.equals("死亡")) {
            // 宠物未死亡，无需复活（算失败）
            failCount++;
        } else {
            // 执行复活操作
            putString(qun+"/"+playerUin+"/宠物小窝/位置_0", "状态", "正常");
            宠物回血(qun, playerUin, 1); // 恢复生命
            successCount++;
        }
    }
    
    // 发送统计结果
    发送(qun, "[AtQQ=" + uin + "]  全群宠物复活操作完成！\n" +
            "✅ 成功复活：" + successCount + "人\n" +
            "❌ 复活失败：" + failCount + "人（无宠物或宠物未死亡）", true);
    
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}
   

//查看近期玩家指令操作时间（每次加载都会重新开始记录）
if (消息.equals("查看触发时间")) {
    if (userLastTriggerTime.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]  " + "\n\n暂无指令触发记录～", true);
    } else {
        // 按「群号」分组（同一群的所有玩家记录合并）
        Map groupByQun = new HashMap(); // 低版本兼容，使用原始类型
        
        for (Map.Entry entry : userLastTriggerTime.entrySet()) {
            String key = (String) entry.getKey();
            TimestampWithText value = (TimestampWithText) entry.getValue();
            
            String[] parts = key.split("_", 2);
            if (parts.length < 2) continue;
            String qunKey = parts[0];
            
            if (!groupByQun.containsKey(qunKey)) {
                groupByQun.put(qunKey, new ArrayList());
            }
            // 传入 value 和玩家ID（parts[1]）
            ((List) groupByQun.get(qunKey)).add(new PlayerRecord(value, parts[1]));
        }
        
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (Map.Entry groupEntry : groupByQun.entrySet()) {
            String qunId = (String) groupEntry.getKey();
            List records = (List) groupEntry.getValue();
            
            sb.append("\n【群聊信息】：").append(getGroupName(qunId)).append("（").append(qunId).append("）\n");
            
            for (Object obj : records) {
                PlayerRecord record = (PlayerRecord) obj;
                TimestampWithText trigger = record.getTrigger();
                String playerId = record.getPlayerId();
                Date date = new Date(trigger.getTimestamp());
                String playerName = 玩家名(qunId, playerId);
                
                sb.append("\n『玩家信息』: ").append(playerName).append("(").append(playerId).append(")\n");
                sb.append("『触发指令』: ").append(trigger.getText()).append("\n");
                sb.append("『触发时间』: ").append(sdf.format(date)).append("\n");
            }
            sb.append("<填充>\n");
        }
        
        String 内容 = "*玩家最后触发指令时间（按群分组）：*\n<填充>\n" + sb.toString();
        toImg(内容, "", 0.5, 0.01, 35, AppPath + "/缓存/其他/" + uin + "_text_image.png", true);
        发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_text_image.png]", false);
    }
}

//↓———————关于游戏黑名单相关指令——————————↓
if(消息.startsWith("加游戏黑名单")){
  String 玩家标识; //后续会提取玩家账号
     if(data.mAtList.size()>=1) 
     {
        玩家标识=data.mAtList.get(0);
     }
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(6); //去掉指令前缀
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去掉非数字字符
        玩家标识 = 目标.replaceAll("[^\\d]", "");
       }
     }
     
     if(玩家标识==null) {
       发送(qun,"[AtQQ="+uin+"]  疑似未指定账号！\n◆指令：加游戏黑名单@玩家\n◆指令：加游戏黑名单+QQ号",true);       
     }else{
       //获取已开启[宠物世界]的群聊列表
       String groupListStr = getString("开放群列表", "开放列表");
       String[] groupList = groupListStr.split("、"); //以“、”拆分
       boolean hasUin = false; // 默认false，判断是否找到目标账号
       
       // 遍历所有开放群
       for (String qun : groupList) {
         //获取该群所有成员账号
         String membersList = getGroupMembersUin(qun);
   
         // 检查成员列表中是否包含目标账号
         if(membersList.contains(玩家标识)) {
            hasUin = true;
            break; // 找到后立即终止循环
         }
       }
       
       if(hasUin==false){
         发送(qun,"[AtQQ="+uin+"]   没有在已开放[宠物世界]玩法的群聊里找到账号「"+玩家标识+"」\n ▶请检查是否艾特或账号输入错误\n——————————\n◆指令：加游戏黑名单@玩家\n◆指令：加游戏黑名单+QQ号",true);
       }else{
         // 转换数组为集合并判断包含关系
         boolean isExist2 = Arrays.asList(Owner).contains(玩家标识);
         if (isExist2) {
           发送(qun,"[AtQQ="+uin+"]   「"+玩家标识+"」是GM，不可被添加至游戏黑名单！",true);
         } else {
           玩家加黑(qun,uin,玩家标识); //用已有方法进行处理
           //参数为：群号，操作方账号，目标账号）
         }
      }
    }
}

//去除该账号黑名单
if(消息.startsWith("删游戏黑名单")){
  String 玩家标识; //后续会提取玩家账号
     if(data.mAtList.size()>=1) 
     {
        玩家标识=data.mAtList.get(0);
     }
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(6); //去掉指令前缀
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去掉非数字字符
        玩家标识 = 目标.replaceAll("[^\\d]", "");
       }
     }
     
     if(玩家标识==null) {
       发送(qun,"[AtQQ="+uin+"]  疑似未指定账号！\n◆指令：删游戏黑名单@玩家\n◆指令：删游戏黑名单+QQ号",true);       
     }else{
       //获取已开启[宠物世界]的群聊列表
       String groupListStr = getString("开放群列表", "开放列表");
       String[] groupList = groupListStr.split("、"); //以“、”拆分
       boolean hasUin = false; // 默认false，判断是否找到目标账号
       
       // 遍历所有开放群
       for (String qun : groupList) {
         //获取该群所有成员账号
         String membersList = getGroupMembersUin(qun);
   
         // 检查成员列表中是否包含目标账号
         if(membersList.contains(玩家标识)) {
            hasUin = true;
            break; // 找到后立即终止循环
         }
       }
       
       if(hasUin==false){
         发送(qun,"[AtQQ="+uin+"]   没有在已开放[宠物世界]玩法的群聊里找到账号「"+玩家标识+"」\n ▶请检查是否艾特或账号输入错误\n——————————\n◆指令：加游戏黑名单@玩家\n◆指令：加游戏黑名单+QQ号",true);
       }else{
         // 转换数组为集合并判断包含关系
         boolean isExist2 = Arrays.asList(Owner).contains(玩家标识);
         if (isExist2) {
           发送(qun,"[AtQQ="+uin+"]   「"+玩家标识+"」是GM，不会被添加至游戏黑名单，无需多此一举！",true);
         } else {
           玩家去黑(qun,uin,玩家标识); //用已有方法进行处理
           //参数为：群号，操作方账号，目标账号）
         }
       }
     }
}


//查看游戏黑名单
if(消息.equals("游戏黑名单")||消息.equals("查看游戏黑名单")){
  // 黑名单中获取账号列表并拆分
  String 列表 = getString("游戏黑名单", "账号列表");

  // 判断条件：列表为null、空字符串，或去除首尾空格后无内容
  if (列表 == null || 列表.trim().isEmpty()) {
    发送(qun,"[AtQQ="+uin+"]   查看失败！\n当前暂无玩家被添加到游戏黑名单哦！",true);
    return; //终止，不执行后续逻辑
  }
  
  // 去重并打乱顺序（使用LinkedHashSet保持顺序）
  String[] 账号数组 = 列表.split("、"); // 以“、”拆分
  Set uniqueSet = new LinkedHashSet(Arrays.asList(账号数组));
  List member = new ArrayList(uniqueSet);
  Collections.shuffle(member);
  // 构建结果字符串
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < member.size(); i++) {
    String account = member.get(i);
    sb.append(" •" + (i + 1) + "：@" + 昵称(account) + " (" + account + ")");
        
    if (i != member.size() - 1) {
        sb.append("\n");
    }
  }
  
  //绘制为图片并发送
   String text="<分割>游戏黑名单（共"+member.size()+"位）</分割>\n"+sb.toString()+"\n<填充>\n// •添加：加游戏黑名单@玩家 \n// •删除：加游戏黑名单";
   toImg(text,"",0.5,0.03,32,AppPath+"/缓存/其他/游戏黑名单.png",false);
   发送(qun,"[AtQQ="+uin+"]   [PicUrl="+AppPath+"/缓存/其他/游戏黑名单.png]",true);
}
//↑————————游戏黑名单相关指令——————————↑


 /*
 主要用于奖赏玩家，指令格式有下面几种：↓
  
 【奖励指定玩家】：
 1. 奖励+道具名称#数量@玩家（如：奖励复活石#666@野兽先辈）
 2. 奖励+道具名称#数量-QQ号（如：奖励复活石#123-114514）
 ————————————————
 【奖励本群玩家】：（按本群已记录的玩家列表来奖励）
 1. 奖励本群+道具名称#数量（如：奖励本群复活石#666）   
 ————————————————
 【奖励全群玩家】：（给群内所有人奖励，可能存在bug）
 1. 奖励全群+道具名称#数量（如：奖励全群复活石#666）   
 ————————————————
 【奖励全服玩家】
 1. 奖励全服+道具名称#数量（如：奖励全服复活石#666）   
 ————————————————*/
 
if (消息.startsWith("奖励")) { // 处理奖励相关指令
    // 区分奖励类型：指定玩家/本群/全服
    if (消息.contains("@") || 消息.contains("-")) { // 奖励指定玩家（含@或QQ号）
        String 项目部分, 数量部分, 玩家标识;
        if (消息.contains("@") && data.mAtList.size() >= 1) {  
          String[] parts = 消息.split("@");  
          if(消息.contains("#")){
            项目部分 = parts[0].split("#")[0].replace("奖励", "");  
            数量部分 = parts[0].split("#")[1];  
          }else{
            项目部分 = parts[0].replace("奖励", "").trim();
            数量部分 = "1"; // 默认数量1
          }
          玩家标识 = data.mAtList.get(0);  
          // 校验三部分是否有效  
         if (项目部分 == null || 项目部分.trim().isEmpty() || 数量部分 == null || 数量部分.trim().isEmpty() || 玩家标识 == null || 玩家标识.trim().isEmpty()) {  
           发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：奖励+道具#数量@玩家 \n•格式2：奖励+道具#数量-QQ", true);  
           return;
         }
       } else if (消息.contains("-")) {  
         // 同理，拆分后校验三部分  
         String[] parts = 消息.split("-");  
          if(消息.contains("#")){
            项目部分 = parts[0].split("#")[0].replace("奖励", "");  
            数量部分 = parts[0].split("#")[1];  
          }else{
            项目部分 = parts[0].replace("奖励", "").trim();
            数量部分 = "1"; // 默认数量1
          }
         玩家标识 = parts[1];  
        if (项目部分 == null || 项目部分.trim().isEmpty() || 数量部分 == null || 数量部分.trim().isEmpty() || 玩家标识 == null || 玩家标识.trim().isEmpty()) {
          发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：奖励+道具#数量@玩家 \n•格式2：奖励+道具#数量-QQ", true);  
         return;
        }  
      } else {  
       发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：奖励+道具#数量@玩家 \n•格式2：奖励+道具#数量-QQ", true);  
       return;
      }  
     
      // 校验道具名是否存在（完全匹配itemMap中的键）
     if (!itemMap.containsKey(项目部分) && !项目部分.equals("金币") && !项目部分.equals("积分")) {
       发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + 项目部分 + "〕不存在或输入错误！", true);
       return;
     }
 
     String 群列表 = getGroupMembersUin(qun); 
     if(群列表!=null&&!群列表.contains(玩家标识)){
       发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+玩家标识+"]的成员！",true);
       return;
     }
     
      //奖励指定玩家对应数量的金币/积分
      if(项目部分.equals("金币")||项目部分.equals("积分")){
        if(文转(数量部分)>999999999){ //限制货币最大奖励数量
          发送(qun,"[AtQQ="+uin+"]   货币奖励数量超出限制！\n（限制：1～999999999）",true);
          return;
        }else{
          long 货币 = 文转(getString(qun+"/"+玩家标识+"/我的资产", 项目部分));
          long 数量 = 文转(数量部分);
          putString(qun+"/"+玩家标识+"/我的资产", 项目部分, 转文(货币+数量));
          发送(qun,"[AtQQ="+uin+"]   奖励成功！\n•目标玩家：〔@" + 玩家标识 + "〕\n•奖励内容：【" + 项目部分 + "】×" + 数量部分+"！\n◆可发送[我的资产]查看",true);
        }
      }else{ //奖励指定数量的道具给玩家
        String bagKey = qun+"/"+玩家标识+"/我的背包";
        String bag = getString(bagKey, "道具列表");
        long 数量 = 文转(数量部分);
        if(bag != null && bag.contains(项目部分)) {
           long count = 文转(getString(bagKey, 项目部分));
           putString(bagKey, 项目部分, 转文(count + 数量));
         } else {
           String newBag = (bag == null ? "" : bag) + "、" + 项目部分;
           putString(bagKey, "道具列表", newBag);
           putString(bagKey, 项目部分, 转文(数量));
         }
        发送(qun,"[AtQQ="+uin+"]   奖励成功！\n向玩家〔@" + 玩家标识 + "〕发放【" + 项目部分 + "】×" + 数量部分+"！\n◆可发送[我的背包]查看",true);
      }
    } else if (消息.contains("本群")) { 
    // 奖励本群玩家（按本群已记录的玩家列表来奖励）
      String[] parts = 消息.replace("奖励本群", "").split("#", 2); 
      // 限制分割为2部分，避免索引越界
      if (parts.length < 2) {
        发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励本群+道具#数量", true);
        return;
      }
      String item = parts[0];  //道具
      String count = parts[1];  //数量
    
      // 校验 道具 与 数量 是否为空
      if (item == null || item.trim().isEmpty() || count == null || count.trim().isEmpty()) {
          发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励本群+道具#数量", true);  
         return;
      }  
        
       // 校验道具名是否存在（完全匹配itemMap中的键）
      if (!itemMap.containsKey(item) && !item.equals("金币") && !item.equals("积分")) {
        发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + item + "〕不存在或输入错误！", true);
        return;
      }
      
      // 获取并校验群玩家列表
      String groupPlayerListStr = getString(qun + "/玩家列表", "列表"); 
      if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]   本群玩家列表为空，无法发放奖励", true);
        return;
      }
      String[] groupPlayers = groupPlayerListStr.split("、"); // 以“、”分割玩家账号
      
      //检验奖励数量是否超出限制
      if((item.equals("金币")||item.equals("积分"))&&文转(count)>999999999){
         发送(qun,"[AtQQ="+uin+"]   货币奖励数量超出限制！\n（限制：1～999999999）",true);
         return;
      }
      if(itemMap.containsKey(item)&&文转(count)>9999999){
         发送(qun,"[AtQQ="+uin+"]   道具奖励数量超出限制！\n（限制：1～999999）",true);
         return;
      }
    
      int successCount = 0; // 成功人数
      int failCount = 0; // 失败人数
      
      for (String player : groupPlayers) {
        if (player.isEmpty()) { // 过滤空账号
          failCount++;
          continue;
        }
        try {
         // 奖励逻辑
         if (item.equals("金币") || item.equals("积分")) {
           long currency = 文转(getString(qun + "/" + player + "/我的资产", item));
           long amount = 文转(count);
           putString(qun + "/" + player + "/我的资产", item, 转文(currency + amount));
         } else { // 奖励道具
           String bagKey = qun + "/" + player + "/我的背包";
           String bag = getString(bagKey, "道具列表");
           long amount = 文转(count);
           if (bag != null && bag.contains(item)) {
              long countInBag = 文转(getString(bagKey, item));
              putString(bagKey, item, 转文(countInBag + amount));
           } else {
              String newBag = (bag == null ? "" : bag) + "、" + item;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, item, 转文(amount));
           }
        }
          successCount++; // 操作成功计数
       } catch (Exception e) { // 捕获可能的异常（如数据转换失败）
          failCount++; // 失败计数
       }
     }
     
     // 生成结果提示
     int total = successCount + failCount; //该群玩家人数（已去除空账号）
     发送(qun,"[AtQQ="+uin+"]   本群奖励发放完成！\n——————————\n★实际奖励："+item+"×"+count+"\n◆玩家人数："+total+"位\n◇操作成功："+successCount+"人\n◇操作失败："+failCount+"人",true);
    } else if (消息.contains("全群")) {
      // 奖励全群玩家（给群内所有人奖励）
      String[] parts = 消息.replace("奖励全群", "").split("#", 2); 
      // 限制分割为2部分，避免索引越界
      if (parts.length < 2) {
        发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励全群+道具#数量", true);
        return;
      }
      String item = parts[0];  //道具
      String count = parts[1];  //数量
    
      // 校验 道具 与 数量 是否为空
      if (item == null || item.trim().isEmpty() || count == null || count.trim().isEmpty()) {
          发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励全群+道具#数量", true);  
         return;
      }  
        
       // 校验道具名是否存在（完全匹配itemMap中的键）
      if (!itemMap.containsKey(item) && !item.equals("金币") && !item.equals("积分")) {
        发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + item + "〕不存在或输入错误！", true);
        return;
      }
      
      // 获取并校验群玩家列表
      String groupPlayerListStr = getGroupMembersUin(qun); 
      if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]   本群玩家列表为空，无法发放奖励", true);
        return;
      }
      String[] groupPlayers = groupPlayerListStr.split(","); // 以“、”分割玩家账号
      
      //检验奖励数量是否超出限制
      if((item.equals("金币")||item.equals("积分"))&&文转(count)>999999999){
         发送(qun,"[AtQQ="+uin+"]   货币奖励数量超出限制！\n（限制：1～999999999）",true);
         return;
      }
      if(itemMap.containsKey(item)&&文转(count)>9999999){
         发送(qun,"[AtQQ="+uin+"]   道具奖励数量超出限制！\n（限制：1～999999）",true);
         return;
      }
    
      int successCount = 0; // 成功人数
      int failCount = 0; // 失败人数
      
      for (String player : groupPlayers) {
        if (player.isEmpty()) { // 过滤空账号
          failCount++;
          continue;
        }
        try {
         // 奖励逻辑
         if (item.equals("金币") || item.equals("积分")) {
           long currency = 文转(getString(qun + "/" + player + "/我的资产", item));
           long amount = 文转(count);
           putString(qun + "/" + player + "/我的资产", item, 转文(currency + amount));
         } else { // 奖励道具
           String bagKey = qun + "/" + player + "/我的背包";
           String bag = getString(bagKey, "道具列表");
           long amount = 文转(count);
           if (bag != null && bag.contains(item)) {
              long countInBag = 文转(getString(bagKey, item));
              putString(bagKey, item, 转文(countInBag + amount));
           } else {
              String newBag = (bag == null ? "" : bag) + "、" + item;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, item, 转文(amount));
           }
        }
          successCount++; // 操作成功计数
       } catch (Exception e) { // 捕获可能的异常（如数据转换失败）
          failCount++; // 失败计数
       }
     }
     
     // 生成结果提示
     int total = successCount + failCount; //该群玩家人数（已去除空账号）
     发送(qun,"[AtQQ="+uin+"]   全群奖励发放完成！\n——————————\n★实际奖励："+item+"×"+count+"\n◆玩家人数："+total+"位\n◇操作成功："+successCount+"人\n◇操作失败："+failCount+"人",true);
   } else if (消息.contains("全服")) { // 奖励全服玩家
      String[] parts = 消息.replace("奖励全服", "").split("#", 2); 
      // 限制分割为2部分，避免索引越界
      if (parts.length < 2) {
        发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励全服+道具#数量", true);
        return;
      }
      String item = parts[0];  //道具
      String count = parts[1];  //数量
    
      // 校验 道具 与 数量 是否为空
      if (item == null || item.trim().isEmpty() || count == null || count.trim().isEmpty()) {
          发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：奖励全服+道具#数量", true);  
         return;
      }  
        
       // 校验道具名是否存在（完全匹配itemMap中的键）
      if (!itemMap.containsKey(item) && !item.equals("金币") && !item.equals("积分")) {
        发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + item + "〕不存在或输入错误！", true);
        return;
      }
      
      // 获取并校验群玩家列表
      String groupPlayerListStr = getString("玩家总列表", "总列表"); 
      if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]   全服玩家列表为空，无法发放奖励", true);
        return;
      }
      String[] groupPlayers = groupPlayerListStr.split("、"); // 以“、”分割玩家账号
      
      //检验奖励数量是否超出限制
      if((item.equals("金币")||item.equals("积分"))&&文转(count)>999999999){
         发送(qun,"[AtQQ="+uin+"]   货币奖励数量超出限制！\n（限制：1～999999999）",true);
         return;
      }
      if(itemMap.containsKey(item)&&文转(count)>9999999){
         发送(qun,"[AtQQ="+uin+"]   道具奖励数量超出限制！\n（限制：1～999999）",true);
         return;
      }
    
int successCount = 0; // 成功人数  
int failCount = 0; // 失败人数  

// 获取所有开放群列表  
String 群列表 = getString("开放群列表", "开放列表");  
String[] 群组列表 = 群列表.split("、");  

for (String qun : 群组列表) { // 遍历每个开放群  
    // 玩家列表存储结构为 qun + "/玩家列表"，值为以“、”分隔的玩家字符串  
    String playerListStr = getString(qun + "/玩家列表", "列表");  
    String[] groupPlayers = playerListStr.split("、"); // 拆分玩家列表  

    for (String player : groupPlayers) { // 遍历群内每个玩家  
        if (player.isEmpty()) { // 过滤空账号  
            failCount++;  
            continue;  
        }  
        try {  
            // 原奖励逻辑（注意 qun 已变为当前遍历的群变量）  
            if (item.equals("金币") || item.equals("积分")) {  
                long currency = 文转(getString(qun + "/" + player + "/我的资产", item));  
                long amount = 文转(count);  
                putString(qun + "/" + player + "/我的资产", item, 转文(currency + amount));  
            } else { // 奖励道具  
                String bagKey = qun + "/" + player + "/我的背包";  
                String bag = getString(bagKey, "道具列表");  
                long amount = 文转(count);  
                if (bag != null && bag.contains(item)) {  
                    long countInBag = 文转(getString(bagKey, item));  
                    putString(bagKey, item, 转文(countInBag + amount));  
                } else {  
                    String newBag = (bag == null ? "" : bag) + "、" + item;  
                    putString(bagKey, "道具列表", newBag);  
                    putString(bagKey, item, 转文(amount));  
                }  
            }  
            successCount++; // 操作成功计数  
        } catch (Exception e) { // 捕获可能的异常（如数据转换失败）  
            failCount++; // 失败计数  
        }  
    }  
}  

     
     // 生成结果提示
     int total = successCount + failCount; //该群玩家人数（已去除空账号）
     发送(qun,"[AtQQ="+uin+"]   全服奖励发放完成！\n——————————\n★实际奖励："+item+"×"+count+"\n◆玩家人数："+total+"位\n◇操作成功："+successCount+"人\n◇操作失败："+failCount+"人",true);
   } else {
    发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n以下是奖励指令的格式：\n•1：奖励+道具#数量@玩家\n•2：奖励+道具#数量-QQ号\n•3：奖励本群+道具#数量\n•4：奖励全群+道具#数量\n•5：奖励全服+道具#数量", true);
   }
}

/*
 主要用于扣除玩家货币或道具，指令格式有下面几种：↓
  
 【扣除指定玩家】：
 1. 扣除+道具名称#数量@玩家（如：扣除复活石#666@野兽先辈）
 2. 扣除+道具名称#数量-QQ号（如：扣除复活石#123-114514）
 ————————————————
 【扣除本群玩家】：（按本群已记录的玩家列表来扣除）
 1. 扣除本群+道具名称#数量（如：扣除本群复活石#666）   
 ————————————————
 【扣除全群玩家】：（扣除群内所有人的道具/货币，可能存在bug）
 1. 扣除全群+道具名称#数量（如：扣除全群复活石#666）   
 ————————————————
 【扣除全服玩家】
 1. 扣除全服+道具名称#数量（如：扣除全服复活石#666）   
 ————————————————*/
if (消息.startsWith("扣除")) { // 处理扣除相关指令
    // 区分扣除类型：指定玩家/本群/全服
    if (消息.contains("@") || 消息.contains("-")) { // 扣除指定玩家（含@或QQ号）
        String 项目部分, 数量部分, 玩家标识;
        if (消息.contains("@") && data.mAtList.size() >= 1) {  
            String[] parts = 消息.split("@");  
          if(消息.contains("#")){
            项目部分 = parts[0].split("#")[0].replace("扣除", "");  
            数量部分 = parts[0].split("#")[1];  
          }else{
            项目部分 = parts[0].replace("扣除", "").trim();
            数量部分 = "1"; // 默认数量1
          }
            玩家标识 = data.mAtList.get(0);  
            // 校验数量是否为正整数
            if (!数量部分.matches("\\d+") || 文转(数量部分) <= 0) {  
                发送(qun, "[AtQQ="+uin+"]   扣除数量必须为正整数！", true);  
                return;
            }
            // 基础校验
            if (项目部分 == null || 项目部分.trim().isEmpty() || 玩家标识 == null || 玩家标识.trim().isEmpty()) {  
                发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：扣除+道具#数量@玩家 \n•格式2：扣除+道具#数量-QQ", true);  
                return;
            }
        } else if (消息.contains("-")) {  
            String[] parts = 消息.split("-");  
          if(消息.contains("#")){
            项目部分 = parts[0].split("#")[0].replace("扣除", "");  
            数量部分 = parts[0].split("#")[1];  
          }else{
            项目部分 = parts[0].replace("扣除", "").trim();
            数量部分 = "1"; // 默认数量1
          }
            玩家标识 = parts[1];  
            // 校验数量是否为正整数
            if (!数量部分.matches("\\d+") || 文转(数量部分) <= 0) {  
                发送(qun, "[AtQQ="+uin+"]   扣除数量必须为正整数！", true);  
                return;
            }
            // 其他基础校验
            if (项目部分 == null || 项目部分.trim().isEmpty() || 玩家标识 == null || 玩家标识.trim().isEmpty()) {  
                发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：扣除+道具#数量@玩家 \n•格式2：扣除+道具#数量-QQ", true);  
                return;
            }
        } else {  
            发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n•格式1：扣除+道具#数量@玩家 \n•格式2：扣除+道具#数量-QQ", true);  
            return;
        }  
        
        String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(玩家标识)){
            发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+玩家标识+"]的成员！\n◆请检查是否输入错误！",true);
            return;
        }

        // 校验道具名是否存在
        if (!itemMap.containsKey(项目部分) && !项目部分.equals("金币") && !项目部分.equals("积分")) {
            发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + 项目部分 + "〕不存在或输入错误！", true);
            return;
        }  

        // 扣除指定玩家对应数量的金币/积分
        if (项目部分.equals("金币") || 项目部分.equals("积分")) {
            long 货币 = 文转(getString(qun + "/" + 玩家标识 + "/我的资产", 项目部分)); 
            long 数量 = 文转(数量部分);
            long 实际扣除 = Math.min(货币, 数量); 
            if (实际扣除 <= 0) { 
                发送(qun, "[AtQQ="+uin+"]  " + " \n玩家〔@" + 玩家标识 + "〕的" + 项目部分 + "余额不足！\n当前余额：" + 货币 + "，最多可扣除：" + 货币, true);
                return;
            }
            putString(qun + "/" + 玩家标识 + "/我的资产", 项目部分, 转文(货币 - 实际扣除)); 
            发送(qun, "[AtQQ="+uin+"]  " + " 扣除成功！\n从玩家〔@" + 玩家标识 + "〕资产扣除【" + 项目部分 + "】×" + 实际扣除 + "！", true);
        } else { // 扣除指定数量的道具
            String bagKey = qun + "/" + 玩家标识 + "/我的背包";
            long 数量 = 文转(数量部分);
            long countInBag = 文转(getString(bagKey, 项目部分)); 
            
            // **新增：先检查当前道具数量是否为0，若为0则提前清理数据**
            if (countInBag <= 0) {
                String bag = getString(bagKey, "道具列表");
                String newBag = bag.replace("、" + 项目部分, "").replace(项目部分 + "、", "").replace(项目部分, "");
                putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                putString(bagKey, 项目部分, null);
                发送(qun, "[AtQQ="+uin+"]  " + " \n玩家〔@" + 玩家标识 + "〕的【" + 项目部分 + "】数量为0，已自动清理数据！", true);
                return;
            }
            
            long 实际扣除 = Math.min(countInBag, 数量); 
            if (实际扣除 <= 0) { 
                发送(qun, "[AtQQ="+uin+"]  " + " \n玩家〔@" + 玩家标识 + "〕的【" + 项目部分 + "】数量不足！\n当前数量：" + countInBag + "，最多可扣除：" + countInBag, true);
                return;
            }
            
            String bag = getString(bagKey, "道具列表");
            if (countInBag == 实际扣除) { 
                String newBag = bag.replace("、" + 项目部分, "").replace(项目部分 + "、", "").replace(项目部分, "");
                putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                putString(bagKey, 项目部分, null); 
            } else { 
                putString(bagKey, 项目部分, 转文(countInBag - 实际扣除));
            }
            发送(qun, "[AtQQ="+uin+"]  " + " 扣除成功！\n从玩家〔@" + 玩家标识 + "〕背包扣除【" + 项目部分 + "】×" + 实际扣除 + "！\n◆可发送[我的背包]查看", true);
        }
    } else if (消息.contains("本群")) { 
       // 扣除本群玩家（按本群已记录的玩家列表来扣除）
        String[] parts = 消息.replace("扣除本群", "").split("#", 2); 
        if (parts.length < 2) {
            发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：扣除本群+道具#数量", true);
            return;
        }
        String item = parts[0];  
        String count = parts[1];  
        // 校验数量是否为正整数
        if (!count.matches("\\d+") || 文转(count) <= 0) {  
            发送(qun, "[AtQQ="+uin+"]   扣除数量必须为正整数！", true);  
            return;
        }
        // 其他基础校验
        if (item == null || item.trim().isEmpty()) {  
            发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：扣除本群+道具#数量", true);  
            return;
        }  
        if (!itemMap.containsKey(item) && !item.equals("金币") && !item.equals("积分")) {
            发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + item + "〕不存在或输入错误！", true);
            return;
        }  

        String groupPlayerListStr = getString(qun + "/玩家列表", "列表"); 
        if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
            发送(qun, "[AtQQ="+uin+"]   本群玩家列表为空，无法扣除"+item+"！", true);
            return;
        }
        String[] groupPlayers = groupPlayerListStr.split("、"); 

        int successCount = 0; 
        int failCount = 0; 

        for (String player : groupPlayers) {
            if (player.isEmpty()) { 
                failCount++;
                continue;
            }
            try {
                long amount = 文转(count);
                if (item.equals("金币") || item.equals("积分")) {
                    long currency = 文转(getString(qun + "/" + player + "/我的资产", item));
                    long 实际扣除 = Math.min(currency, amount); 
                    if (实际扣除 <= 0) { 
                        failCount++;
                        continue;
                    }
                    putString(qun + "/" + player + "/我的资产", item, 转文(currency - 实际扣除));
                    successCount++; 
                } else { // 扣除道具
                    String bagKey = qun + "/" + player + "/我的背包";
                    long countInBag = 文转(getString(bagKey, item));
                    
                    // **新增：先检查当前道具数量是否为0，若为0则提前清理数据**
                    if (countInBag <= 0) {
                        String bag = getString(bagKey, "道具列表");
                        String newBag = bag.replace("、" + item, "").replace(item + "、", "").replace(item, "");
                        putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                        putString(bagKey, item, null);
                        continue; // 跳过计数，不纳入成功/失败统计
                    }
                    
                    long 实际扣除 = Math.min(countInBag, amount); 
                    if (实际扣除 <= 0) { 
                        failCount++;
                        continue;
                    }
                    String bag = getString(bagKey, "道具列表");
                    if (countInBag == 实际扣除) { 
                        String newBag = bag.replace("、" + item, "").replace(item + "、", "").replace(item, "");
                        putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                        putString(bagKey, item, null); 
                    } else {
                        putString(bagKey, item, 转文(countInBag - 实际扣除));
                    }
                    successCount++; 
                }
            } catch (Exception e) { 
                failCount++;
            }
        }

        发送(qun, "[AtQQ="+uin+"]  " + " 本群扣除完成！\n——————————\n★实际扣除：" + item + "×" + count + "\n◆玩家人数：" + (successCount + failCount) + "位\n◇成功扣除：" + successCount + "人\n◇扣除失败：" + failCount + "人", true);
    }else if (消息.contains("全服")) { // 扣除全服玩家
        String[] parts = 消息.replace("扣除全服", "").split("#", 2); 
        if (parts.length < 2) {
            发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：扣除全服+道具#数量", true);
            return;
        }
        String item = parts[0];  
        String count = parts[1];  
        // 校验数量是否为正整数
        if (!count.matches("\\d+") || 文转(count) <= 0) {  
            发送(qun, "[AtQQ="+uin+"]   扣除数量必须为正整数！", true);  
            return;
        }
        // 其他基础校验
        if (item == null || item.trim().isEmpty()) {  
            发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n•格式：扣除全服+道具#数量", true);  
            return;
        }  
        if (!itemMap.containsKey(item) && !item.equals("金币") && !item.equals("积分")) {
            发送(qun, "[AtQQ="+uin+"]  " + " \n〔" + item + "〕不存在或输入错误！", true);
            return;
        }  

        String groupPlayerListStr = getString("玩家总列表", "总列表"); 
        if (groupPlayerListStr == null || groupPlayerListStr.isEmpty()) {
            发送(qun, "[AtQQ="+uin+"]   全服玩家列表为空，无法扣除"+item, true);
            return;
        }
        String[] groupPlayers = groupPlayerListStr.split("、"); 

        int successCount = 0;  
        int failCount = 0;  

        String 群列表 = getString("开放群列表", "开放列表");  
        String[] 群组列表 = 群列表.split("、");  

        for (String qun : 群组列表) {  // 遍历所有开放群  
            String groupPlayersStr = getString(qun + "/玩家列表", "列表"); 
            if (groupPlayersStr == null || groupPlayersStr.isEmpty()) continue;
            String[] groupPlayersArray = groupPlayersStr.split("、");  // 按分隔符拆分账号

            for (String player : groupPlayersArray) {  
                if (player.isEmpty()) {  
                    failCount++;  
                    continue;  
                }  
                try {  
                    long amount = 文转(count);  
                    if (item.equals("金币") || item.equals("积分")) {  
                        long currency = 文转(getString(qun + "/" + player + "/我的资产", item));
                        long 实际扣除 = Math.min(currency, amount); // 取实际可扣除数量
                        if (实际扣除 <= 0) { 
                            failCount++;
                            continue;
                        }
                        putString(qun + "/" + player + "/我的资产", item, 转文(currency - 实际扣除));
                    } else {  
                        String bagKey = qun + "/" + player + "/我的背包";
                        long countInBag = 文转(getString(bagKey, item));
                        
                        // **新增：先检查当前道具数量是否为0，若为0则提前清理数据**
                        if (countInBag <= 0) {
                            String bag = getString(bagKey, "道具列表");
                            String newBag = bag.replace("、" + item, "").replace(item + "、", "").replace(item, "");
                            putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                            putString(bagKey, item, null);
                            continue;
                        }
                        
                        long 实际扣除 = Math.min(countInBag, amount); 
                        if (实际扣除 <= 0) { 
                            failCount++;
                            continue;
                        }
                        String bag = getString(bagKey, "道具列表");
                        if (countInBag == 实际扣除) { 
                            String newBag = bag.replace("、" + item, "").replace(item + "、", "").replace(item, "");
                            putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
                            putString(bagKey, item, null); // 删除道具数量键
                        } else {
                            putString(bagKey, item, 转文(countInBag - 实际扣除));
                        }
                    }  
                    successCount++;  
                } catch (Exception e) {  
                    failCount++;  
                }  
            }  
        } 

        发送(qun, "[AtQQ="+uin+"]  " + " 全服扣除完成！\n——————————\n★实际扣除：" + item + "×" + count + "\n◆玩家人数：" + (successCount + failCount) + "位\n◇扣除成功：" + successCount + "人\n◇扣除失败：" + failCount + "人", true);
    } else {
        发送(qun, "[AtQQ="+uin+"]  指令格式错误！\n以下是扣除指令的格式：\n•1：扣除+道具#数量@玩家\n•2：扣除+道具#数量-QQ号\n•3：扣除本群+道具#数量\n•4：扣除全群+道具#数量\n•5：扣除全服+道具#数量", true);
    }
}


//发放礼包指令
if (消息.startsWith("发放礼包")) {
    String[] mainParts = 消息.replace("发放礼包", "").split("\\[|\\]|-", 2);
    if (mainParts.length < 2) {
        发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！\n•格式：发放礼包+礼包名称[道具#数量]-礼包数量-可领取次数\n•多道具格式：发放礼包+礼包名称[道具1#数量,道具2#数量]-礼包数量-可领取次数", true);
        return;
    }
    
    String 礼包名称 = mainParts[0].trim();
    String 礼包内容部分 = mainParts[1];
    String[] contentAndCountAndTimes = 礼包内容部分.split("-", 3); // 拆分为3部分：道具列表、礼包数量、可领取次数
    
    if (contentAndCountAndTimes.length < 3) {
        发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！缺少礼包数量或可领取次数\n•格式：发放礼包+礼包名称[道具#数量]-礼包数量-可领取次数", true);
        return;
    }
    
    String 道具列表部分 = contentAndCountAndTimes[0].replaceAll("\\]$", "");
    String 礼包数量 = contentAndCountAndTimes[1];
    String 可领取次数 = contentAndCountAndTimes[2]; // 新增：玩家可领次数参数
    
    // 校验礼包数量
    if (文转(礼包数量) <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包数量必须为正整数！", true);
        return;
    }
    
    // 校验可领取次数
    if (文转(可领取次数) <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "可领取次数必须为正整数！", true);
        return;
    }
    
    // 校验礼包名称
    if (礼包名称.isEmpty() || 礼包名称.contains("[") || 礼包名称.contains("]") || 礼包名称.contains("-")) {
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包名称格式错误，不能包含[ ] - 等特殊字符！", true);
        return;
    }
    
    // 解析道具列表
    String[] 道具项 = 道具列表部分.split(",");
    if (道具项.length == 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包中未包含任何道具！", true);
        return;
    }
    
    // 校验道具格式和存在性
    StringBuilder 无效道具 = new StringBuilder();
    for (String 道具 : 道具项) {
        String[] 道具数量 = 道具.split("#", 2);
        if (道具数量.length != 2) {
            无效道具.append(道具).append("、");
            continue;
        }
        
        String 道具名 = 道具数量[0].trim();
        String 数量 = 道具数量[1].trim();
        
        if (文转(数量) <= 0) {
            无效道具.append(道具).append("、");
            continue;
        }
        
        if (!itemMap.containsKey(道具名) && !道具名.equals("金币") && !道具名.equals("积分")) {
            无效道具.append(道具名).append("、");
        }
    }
    

    if (无效道具.length() > 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "以下道具格式错误或不存在：\n" + 无效道具.toString().substring(0, 无效道具.length() - 1), true);
        return;
    }
    
    
    // 存储礼包到发放列表（优化道具存储方式）
    String bag = getString("发放礼包/"+qun+"/礼包发放列表", "礼包列表");
    if (bag != null && !bag.contains(礼包名称)) {
       String newBag = (bag == null ? "" : bag) + "、" + 礼包名称;
       putString("发放礼包/"+qun+"/礼包发放列表", "礼包列表", newBag);
       putString("发放礼包/"+qun+"/"+礼包名称, "道具列表", 道具列表部分);
       putString("发放礼包/"+qun+"/"+礼包名称, "初始数量", 礼包数量);
       putString("发放礼包/"+qun+"/"+礼包名称, "剩余数量", 礼包数量);
       putString("发放礼包/"+qun+"/"+礼包名称, "创建时间", getTodayDate(2));
       putString("发放礼包/"+qun+"/"+礼包名称, "可领取次数", 可领取次数); // 新增：存储可领取次数
       
       // 新增：拆分道具与数量单独存储
       for (String 道具 : 道具项) {
           String[] 道具数量 = 道具.split("#", 2);
           if (道具数量.length == 2) {
               String 道具名 = 道具数量[0].trim();
               String 数量 = 道具数量[1].trim();
               putString("发放礼包/"+qun+"/"+礼包名称, 道具名, 数量);
           }
       }
    }else{
       putString("发放礼包/"+qun+"/礼包发放列表", "礼包列表", 礼包名称);
       putString("发放礼包/"+qun+"/"+礼包名称, "道具列表", 道具列表部分);
       putString("发放礼包/"+qun+"/"+礼包名称, "初始数量", 礼包数量);
       putString("发放礼包/"+qun+"/"+礼包名称, "剩余数量", 礼包数量);
       putString("发放礼包/"+qun+"/"+礼包名称, "创建时间", getTodayDate(2));
       putString("发放礼包/"+qun+"/"+礼包名称, "可领取次数", 可领取次数); // 新增：存储可领取次数
       
       // 新增：拆分道具与数量单独存储
       for (String 道具 : 道具项) {
           String[] 道具数量 = 道具.split("#", 2);
           if (道具数量.length == 2) {
               String 道具名 = 道具数量[0].trim();
               String 数量 = 道具数量[1].trim();
               putString("发放礼包/"+qun+"/"+礼包名称, 道具名, 数量);
           }
       }
    }
    
    String logKey = "发放礼包/"+qun+"/礼包发放日志";
    String 道具清单=道具列表部分.replaceAll("＃","×").replaceAll(",","、");
    String 日志内容 = "【" +getTodayDate(1)+ "】\n" +
                      "管理员[" + uin + "]创建礼包：" + 礼包名称 + "\n" +
                      "道具：" + 道具清单 + "\n" +
                      "总数量：" + 礼包数量 + " 份\n" +
                      "玩家可领取次数：" + 可领取次数 + " 次\n";
    String 现有日志 = getString(logKey, "日志");
    putString(logKey, "日志", (现有日志 == null ? "" : 现有日志 + "\n") + 日志内容);
        
    发送(qun, "[AtQQ=" + uin + "]  " + "礼包创建成功！\n★礼包名称：" + 礼包名称 + "\n★道具内容：" + 道具清单+ "\n★剩余数量：" + 礼包数量 + " 份\n★玩家可领取次数：" + 可领取次数 + " 次\n\n◆可发送[礼包详情]查看本群可领取的礼包", false);
}

//删除礼包
if(消息.startsWith("删除礼包")){
    String 礼包列表键 = getString("发放礼包/"+qun+"/礼包发放列表", "礼包列表");
    if (礼包列表键==null||礼包列表键.trim().isEmpty()||礼包列表键.equals("")) {
        发送(qun, "[AtQQ=" + uin + "]  " + "本群暂无GM发放礼包！", true);
        return;
    }
    
    String 名称=消息.substring(4).replaceAll("\\s","");
    if(!礼包列表键.contains(名称)){
      sendReply(qun,data,"发放礼包列表内不存在〔"+名称+"〕礼包！");
    }else{
      String 备 = 礼包列表键.replace("、" + 名称, "");
      String 北 = 备.replace(名称 + "、", "");
      if (北 == null || "".equals(北.trim()) || "null".equals(北.trim().toLowerCase())) {
        putString("发放礼包/"+qun+"/礼包发放列表", "礼包列表", null);
      } else {
        putString("发放礼包/"+qun+"/礼包发放列表", "礼包列表", 北);
        //删除指定礼包（也删除这个礼包的领取状态）
        删除文件(缓存路径+"发放礼包/"+qun+"/"+名称);
        String playerList = getString(qun + "/玩家列表", "列表");            
        String[] players = playerList.split("、");
            for (String player : players) {
              删除文件(缓存路径+"发放礼包/"+qun+"/领取状态/"+player+"/"+名称);
            }
      }
      sendReply(qun, data, "成功删除〔"+名称+"〕礼包！\n•该礼包已从发放礼包列表移除～");
    }
}

//重置礼包
if(消息.startsWith("重置礼包")){
    String 礼包列表键 = getString("发放礼包/"+qun+"/礼包发放列表", "礼包列表");
    if (礼包列表键==null||礼包列表键.trim().isEmpty()||礼包列表键.equals("")) {
        发送(qun, "[AtQQ=" + uin + "]  " + "本群暂无GM发放礼包！", true);
        return;
    }
    
    String 名称=消息.substring(4).replaceAll("\\s","");
    if(!礼包列表键.contains(名称)){
      sendReply(qun,data,"发放礼包列表内不存在〔"+名称+"〕礼包！");
    }else{
      String 份量=getString("发放礼包/"+qun+"/"+名称, "初始数量");
      putString("发放礼包/"+qun+"/"+名称,"剩余数量",份量); //重置该礼包数量
      //删除该群玩家此礼包的领取状态
        String playerList = getString(qun + "/玩家列表", "列表");            
        String[] players = playerList.split("、");
            for (String player : players) {
              删除文件(缓存路径+"发放礼包/"+qun+"/领取状态/"+player+"/"+名称);
            }
       sendReply(qun, data, "☑︎已重置〔"+名称+"〕礼包相关状态～");
    }
}

  //银行系统相关gm指令 
     if (消息.startsWith("冻结账户#")) {
        handleGMFreeze(qun, uin, 消息);
    }
     if (消息.startsWith("设置利率#")) {
        handleSetRate(qun, uin, 消息);
    }

//更新玩家背包里物品的指令（一般情况下用不到）
if (消息.startsWith("背包替换")) { // 处理背包替换相关指令
    // 区分更新类型：指定玩家/本群/全服
    if (消息.contains("-")) { // 背包替换指定玩家（仅支持-分隔QQ号）
        String[] parts = 消息.split("-");
        String qqPart = parts[parts.length - 1]; // 提取QQ号
        String updatePart = parts[0].replace("背包替换", "");
        
        // 解析旧道具和新道具
        if (!updatePart.contains("#")) {
            发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！\n•格式：背包替换+旧道具#新道具-QQ号", true);
            return;
        }
        String[] itemParts = updatePart.split("#", 2);
        String oldItem = itemParts[0].trim();
        String newItem = itemParts[1].trim();
        
        // 校验道具是否存在（旧道具需存在，新道具需在itemMap中）
        if (!itemMap.containsKey(oldItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "旧道具〔" + oldItem + "〕不存在！", true);
            return;
        }
        if (!itemMap.containsKey(newItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "新道具〔" + newItem + "〕不存在！", true);
            return;
        }
        
        // 提取玩家QQ号（仅处理-格式）
        String playerQQ = qqPart;
        
        // 校验玩家是否在本群
        String groupMembers = getGroupMembersUin(qun);
        if (groupMembers != null && !groupMembers.contains(playerQQ)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "本群没有QQ为[" + playerQQ + "]的成员！", true);
            return;
        }
        
        // 执行背包替换
        String bagKey = qun + "/" + playerQQ + "/我的背包";
        long oldItemCount = 文转(getString(bagKey, oldItem)); // 获取旧道具数量
        
        if (oldItemCount <= 0) {
            发送(qun, "[AtQQ=" + uin + "]  " + "玩家〔-" + playerQQ + "〕背包中没有〔" + oldItem + "〕！", true);
            return;
        }
        
        // 处理旧道具替换逻辑
        String bagList = getString(bagKey, "道具列表");
        if (bagList != null) {
            // 从道具列表中移除旧道具（处理多种前后缀情况）
            bagList = bagList.replace("、" + oldItem, "").replace(oldItem + "、", "").replace(oldItem, "");
            
            // 检查新道具是否已存在
            if (bagList.contains(newItem)) {
                // 新道具已存在，累加数量
                long newItemCount = 文转(getString(bagKey, newItem));
                putString(bagKey, newItem, 转文(newItemCount + oldItemCount));
            } else {
                // 新道具不存在，添加到列表并设置数量
                String newBagList = bagList.isEmpty() ? newItem : bagList + "、" + newItem;
                putString(bagKey, "道具列表", newBagList);
                putString(bagKey, newItem, 转文(oldItemCount));
            }
        }
        
        // 删除旧道具数量记录（明确设置为null）
        putString(bagKey, oldItem, null);
        发送(qun, "[AtQQ=" + uin + "]  " + "成功将玩家〔-" + playerQQ + "〕背包中的〔" + oldItem + "〕×" + oldItemCount + "替换为〔" + newItem + "〕！", true);
    } else if (消息.contains("本群")) { 
        // 背包替换本群玩家（逻辑不变）
        String updatePart = 消息.replace("背包替换本群", "").trim();
        if (!updatePart.contains("#")) {
            发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！\n•格式：背包替换本群+旧道具#新道具", true);
            return;
        }
        String[] itemParts = updatePart.split("#", 2);
        String oldItem = itemParts[0].trim();
        String newItem = itemParts[1].trim();
        
        // 校验道具是否存在
        if (!itemMap.containsKey(oldItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "旧道具〔" + oldItem + "〕不存在！", true);
            return;
        }
        if (!itemMap.containsKey(newItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "新道具〔" + newItem + "〕不存在！", true);
            return;
        }
        
        // 获取本群玩家列表
        String groupPlayerList = getString(qun + "/玩家列表", "列表");
        if (groupPlayerList == null || groupPlayerList.isEmpty()) {
            发送(qun, "[AtQQ=" + uin + "]  " + "本群玩家列表为空，无法更新！", true);
            return;
        }
        String[] players = groupPlayerList.split("、");
        
        int successCount = 0;
        int failCount = 0;
        
        for (String player : players) {
            if (player.isEmpty()) {
                failCount++;
                continue;
            }
            try {
                String bagKey = qun + "/" + player + "/我的背包";
                long oldItemCount = 文转(getString(bagKey, oldItem));
                
                if (oldItemCount <= 0) {
                    failCount++;
                    continue;
                }
                
                // 处理背包替换
                String bagList = getString(bagKey, "道具列表");
                if (bagList != null) {
                    bagList = bagList.replace("、" + oldItem, "").replace(oldItem + "、", "").replace(oldItem, "");
                    
                    if (bagList.contains(newItem)) {
                        long newItemCount = 文转(getString(bagKey, newItem));
                        putString(bagKey, newItem, 转文(newItemCount + oldItemCount));
                    } else {
                        String newBagList = bagList.isEmpty() ? newItem : bagList + "、" + newItem;
                        putString(bagKey, "道具列表", newBagList);
                        putString(bagKey, newItem, 转文(oldItemCount));
                    }
                }
                putString(bagKey, oldItem, null);
                successCount++;
            } catch (Exception e) {
                failCount++;
            }
        }
        
        发送(qun, "[AtQQ=" + uin + "]  " + "本群背包替换完成！\n——————————\n★更新：〔" + oldItem + "〕→〔" + newItem + "〕\n◆玩家人数：" + (successCount + failCount) + "位\n◇成功更新：" + successCount + "人\n◇更新失败：" + failCount + "人", true);
    } else if (消息.contains("全服")) { 
        // 背包替换全服玩家（逻辑不变）
        String updatePart = 消息.replace("背包替换全服", "").trim();
        if (!updatePart.contains("#")) {
            发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！\n•格式：背包替换全服+旧道具#新道具", true);
            return;
        }
        String[] itemParts = updatePart.split("#", 2);
        String oldItem = itemParts[0].trim();
        String newItem = itemParts[1].trim();
        
        // 校验道具是否存在
        if (!itemMap.containsKey(oldItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "旧道具〔" + oldItem + "〕不存在！", true);
            return;
        }
        if (!itemMap.containsKey(newItem)) {
            发送(qun, "[AtQQ=" + uin + "]  " + "新道具〔" + newItem + "〕不存在！", true);
            return;
        }
        
        // 获取全服开放群列表
        String groupList = getString("开放群列表", "开放列表");
        if (groupList == null || groupList.isEmpty()) {
            发送(qun, "[AtQQ=" + uin + "]  " + "全服开放群列表为空，无法更新！", true);
            return;
        }
        String[] groups = groupList.split("、");
        
        int totalSuccess = 0;
        int totalFail = 0;
        
        for (String group : groups) {
            String playerList = getString(group + "/玩家列表", "列表");
            if (playerList == null || playerList.isEmpty()) continue;
            
            String[] players = playerList.split("、");
            int groupSuccess = 0;
            int groupFail = 0;
            
            for (String player : players) {
                if (player.isEmpty()) {
                    groupFail++;
                    continue;
                }
                try {
                    String bagKey = group + "/" + player + "/我的背包";
                    long oldItemCount = 文转(getString(bagKey, oldItem));
                    
                    if (oldItemCount <= 0) {
                        groupFail++;
                        continue;
                    }
                    
                    // 处理背包替换
                    String bagList = getString(bagKey, "道具列表");
                    if (bagList != null) {
                        bagList = bagList.replace("、" + oldItem, "").replace(oldItem + "、", "").replace(oldItem, "");
                        
                        if (bagList.contains(newItem)) {
                            long newItemCount = 文转(getString(bagKey, newItem));
                            putString(bagKey, newItem, 转文(newItemCount + oldItemCount));
                        } else {
                            String newBagList = bagList.isEmpty() ? newItem : bagList + "、" + newItem;
                            putString(bagKey, "道具列表", newBagList);
                            putString(bagKey, newItem, 转文(oldItemCount));
                        }
                    }
                    putString(bagKey, oldItem, null);
                    groupSuccess++;
                } catch (Exception e) {
                    groupFail++;
                }
            }
            
            totalSuccess += groupSuccess;
            totalFail += groupFail;
        }
        
        发送(qun, "[AtQQ=" + uin + "]  " + "全服背包替换完成！\n——————————\n★更新：〔" + oldItem + "〕→〔" + newItem + "〕\n◆玩家人数：" + (totalSuccess + totalFail) + "位\n◇成功更新：" + totalSuccess + "人\n◇更新失败：" + totalFail + "人", true);
    } else {
        发送(qun, "[AtQQ=" + uin + "]  " + "指令格式错误！\n以下是背包替换指令的格式：\n•1：背包替换+旧道具#新道具-QQ号\n•2：背包替换本群+旧道具#新道具\n•3：背包替换全服+旧道具#新道具", true);
    }
}

 //代替玩家操作的指令。
 //格式：代@玩家+【指令内容】
 if(消息.startsWith("代"))
 {
    String 玩家, 玩家2, 后续;
    if(消息.contains("@")){
      //艾特人数为1时
      if(data.mAtList.size()==1){
         玩家 = data.mAtList.get(0);
         String 括号内容 = extractByBrackets(消息);
         if (括号内容 != null) {
            后续=括号内容;
           // Toast(后续); //提示该指令的内容
         }
         
          // 校验参数是否有效
         if (玩家 == null || 玩家.trim().isEmpty() || 后续 == null || 后续.trim().isEmpty()) {  
           发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n ●格式：代@玩家【指令内容】 \n ○示例：代@玩家【签到】", true);  
           return;
         }
      }
      //艾特人数大于1时
      else if(data.mAtList.size()>1){
        玩家 = data.mAtList.get(0);
        玩家2 = data.mAtList.get(1);
         String 括号内容 = extractByBrackets(消息);
         if (括号内容 != null) {
            后续=removeAfterAt(括号内容)+"-"+玩家2;
            //Toast(后续);
         }
         
          // 校验参数是否有效
         if (玩家 == null || 玩家.trim().isEmpty() || 玩家2 == null || 玩家2.trim().isEmpty() || 后续 == null || 后续.trim().isEmpty()) {  
           发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n ●格式：代@玩家【指令内容】 \n ○示例：代@玩家【砸蛋十连】", true);  
           return;
         }
      }
    }else if (!消息.contains("@")) {
        int startIndex = "代".length();
        int endIndex = 消息.indexOf("【");
        if (startIndex < endIndex) {
            String 数字部分 = 消息.substring(startIndex, endIndex).trim();
            // 校验是否为纯数字（玩家ID通常为数字）
            if (数字部分.matches("\\d+")) {
              //去除非数字部分
                玩家 = 数字部分.replaceAll("[^\\d]", "");
            }
        }
        String 括号内容 = extractByBrackets(消息);
        if (括号内容 != null) {
            后续 = 括号内容;
        }
        
        // 校验参数有效性
        if (玩家 == null || 玩家.trim().isEmpty() || 后续 == null || 后续.trim().isEmpty()) {
            发送(qun, "[AtQQ="+uin+"]  " + " 指令格式错误！\n ●格式：代@玩家【指令内容】\n ●格式：代+QQ【指令内容】\n ○示例：代@玩家【签到】\n ○示例：代123456【砸蛋】", true);
            return;
        }
    }else{
      发送(qun, "[AtQQ="+uin+"]   指令格式错误！\n ●格式：代@玩家【指令内容】 \n ○示例：代@玩家【宠物小窝】", true);  
       return;
    }
    
    //按相关指令处理
    if(后续.startsWith("使用")){
      使用(data,qun,uin,玩家,后续);
    }
    else if(后续.startsWith("卸下神器")||后续.startsWith("卸除神器")){
      卸神器(data,qun,uin,玩家,后续); //用相关方法处理
    }
    else if(后续.startsWith("转让")){
      转让(data,qun,uin,玩家,后续);
    }else if(消息.startsWith("代")){
      代发(data,qun,uin,玩家,后续); //代替玩家发送其他指令
    }
 }

}

if ("开".equals(getString("开放群列表", qun + "_开关状态"))) {

    String 黑名单 = getString("游戏黑名单", "账号列表");
    if(黑名单!=null&&黑名单.contains(uin)){
      // 此玩家账号在游戏黑名单内，忽略ta的指令
      return;
    }
    
    
    // ————↓ 在周一删除“击杀榜”数据 ↓————
       // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前日期是周几
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        // 判断当前日期是否为周一
        boolean isMonday = dayOfWeek == DayOfWeek.MONDAY;
        if (isMonday) { 
            String today = getTodayDate(1); 
            //↑ 获取当前日期（格式：YYYY-MM-DD）
            String lastRunDate = getString("系统配置", "击杀榜清空日期");
            //↑读取上次运行日期
            
            if (!today.equals(lastRunDate)) {
              删除文件(缓存路径+"宠物击杀次数"); 
              删除文件(缓存路径+"宠物被击杀次数"); 
              putString("系统配置", "击杀榜清空日期", today); //更新执行日期
              Toast("〖宠物击杀榜〗数据已清除成功！");
            }
        }
    //—————↑ 在周一删除“击杀榜”数据 ↑——————
    
    
    
    // 判断玩家是否在指令间隔内触发过指令（指令间隔效果）
    TimestampWithText lastTriggerInfo = userLastTriggerTime.get(qun + "_" + uin);
    if (lastTriggerInfo != null) {
        Long lastTriggerTime = lastTriggerInfo.getTimestamp();
        Long currentTime = System.currentTimeMillis();
        int JG = getInt("系统配置", "指令间隔", 1550); // 指令间隔；默认1.5秒左右

        if (currentTime - lastTriggerTime < JG) {
            // 玩家在短时间内已经触发过操作，不执行后续逻辑
            return;
        }
        
    
String 定制记录 = getString("定制宠物", "宠物列表");
boolean 定制 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物定制", false);

// 在使用过（宠物定制卡）后，收集该玩家发送的文本信息作为宠物相关属性
if (data.MessageType == 1 && 消息.startsWith("定制") && 定制 == true && !消息.contains("PicUrl=http")) {

    // 按"#"拆分消息并提取参数
    String[] params = 消息.split("#");
    
    // 校验参数数量
    if (params.length != 4) {
        发送(qun, "[AtQQ="+uin+"]  "+ "错误：消息格式不正确，应为[定制#昵称#属性#性别]'", true);
        return;
    }
    
    if (params.length >= 4) { // 确保参数数量足够
        String nickname = params[1];
        String attribute = params[2];
        String value = params[3];
        
        // 校验参数长度
        if (nickname.length() > 6) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「昵称」长度不能超过6个字符！", true);
            return;
        }
        if (attribute.length() > 6) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「属性」长度不能超过6个字符！", true);
            return;
        }
        if (value.length() > 3) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「性别」长度不能超过3个字符！", true);
            return;
        }
        
        // 收集空参数提示
        StringBuilder errorMsg = new StringBuilder();
        if (nickname.trim().isEmpty()) {
            errorMsg.append("昵称参数为空；\n");
        }
        if (attribute.trim().isEmpty()) {
            errorMsg.append("属性参数为空；\n");
        }
        if (value.trim().isEmpty()) {
            errorMsg.append("性别参数为空；\n");
        }
        
        // 输出合并后的错误提示
        if (errorMsg.length() > 0) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 检验错误：\n" + errorMsg.toString() + "\n☛请重新按格式发送！\n•格式：定制#昵称#属性#性别", true);
            return;
        }
        
        // 提取公共属性数据
        long[] petAttributes = petAttributeMap.get("定制");
        long 攻击 = petAttributes[0];
        long 防御 = petAttributes[1];
        long 智力 = petAttributes[2];
        long 生命上限 = petAttributes[3];
        long 进化次数 = petAttributes[4];
        long 下级经验 = 100;
        
        // 昵称重复检查逻辑
        boolean containsNickname = false;

        if (定制记录 != null && !"null".equals(定制记录) && !"".equals(定制记录.trim())) {
            // 按"、"分割定制记录
            String[] 宠物数组 = 定制记录.split("、");
            // 遍历处理每个分割后的子字符串
            for (String petItem : 宠物数组) {
                // 对每个子字符串按","分割
                String[] items = petItem.split(",");
                
                // 遍历判断是否包含nickname
                for (String item : items) {
                    if (item.equals(nickname)) {
                        containsNickname = true;
                        break;
                    }
                }
                if (containsNickname) {
                    break;
                }
            }
        }
        
        // 输出判断结果并处理不同场景
        if (petMap.containsKey(nickname)) {
            // 存在时获取对应的Pet对象
           Pet pet = (Pet) petMap.get(nickname);
           // 输出提示信息和品质
           发送(qun,"[AtQQ="+uin+"]   昵称重复！\n「" + nickname + "」已存在，其品质为：" + pet.getQuality()+"\n\n•：请使用其他昵称吧～",true);
        } else if (containsNickname) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 昵称重复！\n消息中昵称「" + nickname + "」已被占用，请使用其他昵称吧～", true);
        } else {
            // 存储定制宠物初始属性（公共逻辑提取）
            putString(配置名称, "心情", "100");
            putString(配置名称, "等级", "1");
            putString(配置名称, "攻击", 转文(攻击));
            putString(配置名称, "防御", 转文(防御));
            putString(配置名称, "智力", 转文(智力));
            putString(配置名称, "当前精力", "1000000");
            putString(配置名称, "精力上限", "100");
            putString(配置名称, "当前生命", 转文(生命上限));
            putString(配置名称, "生命上限", 转文(生命上限));
            putString(配置名称, "当前经验", "9999999999999"); // 体验版
            putString(配置名称, "下级所需经验", 转文(下级经验));
            putString(配置名称, "进化层次", "0"); // 当前已进化次数
            putString(配置名称, "进化上限", 转文(进化次数));
            putString(配置名称, "昵称", nickname);
            putString(配置名称, "宠物名字", nickname); // 后续可能会用上
            putString(配置名称, "性别", value);
            putString(配置名称, "属性", attribute);
            putString(配置名称, "阶段", "破壳期");
            putString(配置名称, "级别", "定制");
            putString(配置名称, "状态", "正常");
            putString(配置名称, "神器", "无");
            putString(配置名称, "天赋", "无");
            putString(配置名称, "婚姻状况", "单身");
            putString(配置名称, "是否易容","否");
            putBoolean(配置名 + "状态", "宠物定制", false);
            putBoolean(配置名+"状态","宠物图片", true);
                    
            // 处理宠物列表存储逻辑
            String 组合 = nickname + "," + attribute + "," + value;
            String newPetList = (定制记录 != null && !定制记录.isEmpty()) ? 定制记录 + "、" + 组合 : 组合;
            putString("定制宠物", "宠物列表", newPetList);
                    
            // 发送成功消息
            发送(qun, "[AtQQ="+uin+"]  "+ " ✓校验通过!\n————————\n【昵称】：" + nickname + "\n【属性】：" + attribute + "\n【性别】：" + value + "\n————————\n•☛请发送一张接近正方形的图片，该图片会作为当前定制宠物的图片", false);
        }
    }
}

        boolean 改名 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物改名", false);
        // 在使用过（改名卡）后，收集该玩家发送的文本信息作为宠物昵称
        if (data.MessageType == 1 && 改名 == true && !消息.contains("PicUrl=http")) {
            String processedMessage = 消息.replaceAll("\n", "").replaceAll("\\s", "");
            // 替换换行空格  ↑
            
            // 判断处理后的内容  
            if (processedMessage.isEmpty()) { // 全为空格或换行（处理后为空）  
                发送(qun, "[AtQQ="+uin+"]  "+ "失败！\n你的消息中没有可用内容，请重新发送消息", true);
            } else if(processedMessage.length() > 6) { // 大于6  
                发送(qun, "[AtQQ="+uin+"]  "+ " 改名失败！\n当前消息字数>6，请重新发送消息！", true);          
            } else if (petMap.containsKey(processedMessage)) { 
              //宠物库存在该昵称的宠物
              // 存在时获取对应的Pet对象
             Pet pet = (Pet) petMap.get(nickname);
             // 输出提示信息和品质
             发送(qun,"[AtQQ="+uin+"]   昵称重复！\n「" + processedMessage + "」已存在，其品质为：" + pet.getQuality()+"\n\n•：请使用其他昵称吧～",true);
           }else { // 长度≤6  
                发送(qun, "[AtQQ="+uin+"]   成功！\n你的宠物已改名为「" + processedMessage + "」！\n可发送[我的宠物]查看变化", true);
                putBoolean(配置名 + "状态", "宠物改名", false);
                putString(配置名称, "昵称", processedMessage);
            }  
        }
           
        boolean 作图 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物图片", false);
        // 下载图片作为定制宠物的图片
        if (作图 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (宠物名 == null) {
                发送(qun, "[AtQQ="+uin+"]  "+ " 当前没有宠物", true);
                return;
            }
            
            String 名字 = getString(配置名称, "宠物名字");
            String 级别 = getString(配置名称, "级别");
            if (petMap.containsKey(名字)) {
               发送(qun,"[AtQQ="+uin+"]  图片保存失败！\n你的当前宠物〔"+名字+"〕不是“使用宠物定制卡”后的宠物！",true);
               return;
            } else if (!petMap.containsKey(名字)&&级别.equals("定制")) {
               if (matcher.find()) {  
                   String link = matcher.group(1); // 提取括号内的链接部分  
                   String 路径 = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";  

                   File 头像文件 = new File(路径);
                   File parentDir = 头像文件.getParentFile();  
                   if (!parentDir.exists()) {  
                      parentDir.mkdirs(); // 先创建父目录  
                   }  
               
                   try {  
                         downloadFile(link, 路径);
                         // 无论文件是否存在，直接下载/覆盖
                         当前宠物(qun,uin,0); //绘制该玩家当前宠物
                         发送(qun, "[AtQQ="+uin+"]  "+ " 保存成功！\n你的定制宠物图片已更换！\n•效果如下↓[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]", true);
                         putBoolean(配置名 + "状态", "宠物图片", false);
                    } catch (IOException e) {  
                       e.printStackTrace();  
                      Toast(uin+"宠物定制图下载失败");  
                    }
               }  
            }
        }
        
        boolean 宠物背景 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物背景图", false);
        // 下载图片作为指定玩家的宠物背景图
        if (宠物背景 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (matcher.find()) {  
                String link = matcher.group(1); // 提取括号内的链接部分  
                String 路径 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/宠物背景图.png";  
                
                File 文件 = new File(路径);  
                File parentDir = 文件.getParentFile();  
                if (!parentDir.exists()) {  
                    parentDir.mkdirs(); // 先创建父目录  
                }  
                
                try {  
                    downloadFile(link, 路径);
                   // 无论文件是否存在，直接下载/覆盖
                 } catch (IOException e) {  
                     e.printStackTrace();  
                     Toast("图片下载失败");  
                 }

                当前宠物(qun,uin,0); //绘制该玩家当前宠物
                发送(qun, "[AtQQ="+uin+"]  "+ " 保存成功！\n你的宠物背景图已更换！\n•效果如下↓[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]", true);
                putBoolean(配置名 + "状态", "宠物背景图", false);
            }  
        }
        
           
        boolean 易容 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物易容", false);
        // 在使用过（易容丹）后，收集该玩家发送的图片作为宠物图片（易容图）
        if (易容 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (matcher.find()) {  
                String link = matcher.group(1); // 提取括号内的链接部分  
                String 路径 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/" + qun + "/" + TimeMark() + ".png";  

                File 头像文件 = new File(路径);
                File parentDir = 头像文件.getParentFile();  
                if (!parentDir.exists()) {  
                    parentDir.mkdirs(); // 先创建父目录  
                }  
               
                try {  
                    downloadFile(link, 路径);
                   // 无论文件是否存在，直接下载/覆盖
                 } catch (IOException e) {  
                     e.printStackTrace();  
                     Toast("图片下载失败");  
                 }
                
                //判断之前是否已易容
                String 是否易容=getString(配置名称, "是否易容");
                String 易容图=getString(配置名称, "上传图片"); //易容图片的文件名
                if(是否易容.equals("是")){
                  //删除之前的易容图缓存
                  删除文件(AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+易容图+".png");
                }

                putBoolean(配置名 + "状态", "宠物易容", false);
                putString(配置名称, "是否易容", "是");
                putString(配置名称, "上传图片", TimeMark());
                
                当前宠物(qun,uin,0); //绘制该玩家当前宠物
                发送(qun, "[AtQQ="+uin+"]  "+ " 易容成功！\n你的宠物图片已更换！\n•效果如下↓[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]", true);
            }  
        }
        
    }
  
  //使用相关道具/神器
  if(消息.startsWith("使用")){
    String 玩家;
    if(消息.contains("@")){
      if(data.mAtList.size()>=1){
         玩家 = data.mAtList.get(0);
         消息=removeAfterAt(消息)+"-"+玩家;
      }
    }
    使用(data,qun,"",uin,消息); //用相关方法处理
  }

   //卸下神器
  else if(消息.startsWith("卸下神器")||消息.startsWith("卸除神器")){
     卸神器(data,qun,"",uin,消息); //用相关方法处理
   }

/*转让道具指令

 格式1：使用+道具名称#数量@玩家
 格式2：使用+道具名称#数量-账号
 格式3：使用+道具名称@玩家
 格式4：使用+道具名称-账号
*/
else if(消息.startsWith("转让")){
  转让(data,qun,"",uin,消息); //用相关方法处理
}

 //判断是否为指令
 else if(消息!=null&&!消息.trim().isEmpty()){
      //将艾特部分替换为QQ号
      if(消息.contains("@")){
        if(data.mAtList.size()>=1){
         消息=removeAfterAt(消息)+data.mAtList.get(0);
        }
      }
     一些指令(data,qun,"",uin,消息);
   }
   
 }
}


/*专门处理：GM账号发送的格式为 {代@玩家【指令】} 的指令

 qun=群号，gm=GM账号，
 uin=被gm艾特的玩家账号，
 msg=后续内容(可能为指令)
 
 （这个gm参数可能在下面有用吧...有点久，忘记了）
*/
public void 代发(Object data, String qun, String gm, String uin, String msg){
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";  
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    String 宠物名=getString(配置名称,"昵称");
    
if ("开".equals(getString("开放群列表", qun + "_开关状态"))) {

     String 黑名单 = getString("游戏黑名单", "账号列表");
    if(黑名单!=null&&黑名单.contains(uin)){
      // 此玩家账号在游戏黑名单内，忽略ta的指令
      发送(qun,玩家名(qun,gm)+" [@"+uin+"]在游戏黑名单内，ta无法触发指令！即便是您使用（代@玩家【内容】）指令也不行哦",true);
      return;
    }
    
    
    // ————↓ 在周一删除“击杀榜”数据 ↓————
       // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前日期是周几
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        // 判断当前日期是否为周一
        boolean isMonday = dayOfWeek == DayOfWeek.MONDAY;
        if (isMonday) { 
            String today = getTodayDate(1); 
            //↑ 获取当前日期（格式：YYYY-MM-DD）
            String lastRunDate = getString("系统配置", "击杀榜清空日期");
            //↑读取上次运行日期
            
            if (!today.equals(lastRunDate)) {
              删除文件(缓存路径+"宠物击杀次数"); 
              删除文件(缓存路径+"宠物被击杀次数"); 
              putString("系统配置", "击杀榜清空日期", today); //更新执行日期
              Toast("〖宠物击杀榜〗数据已清除成功！");
            }
        }
    //—————↑ 在周一删除“击杀榜”数据 ↑——————
    
    
    
    // 判断玩家是否在指令间隔内触发过指令（指令间隔效果）
    TimestampWithText lastTriggerInfo = userLastTriggerTime.get(qun + "_" + uin);
    if (lastTriggerInfo != null) {
        Long lastTriggerTime = lastTriggerInfo.getTimestamp();
        Long currentTime = System.currentTimeMillis();
        int JG = getInt("系统配置", "指令间隔", 1550); // 指令间隔；默认1.5秒左右

        if (currentTime - lastTriggerTime < JG) {
            // 玩家在短时间内已经触发过操作，不执行后续逻辑
            return;
        }
        
    
String 定制记录 = getString("定制宠物", "宠物列表");
boolean 定制 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物定制", false);

// 在使用过（宠物定制卡）后，收集该玩家发送的文本信息作为宠物相关属性
if (data.MessageType == 1 && 消息.startsWith("定制") && 定制 == true && !消息.contains("PicUrl=http")) {

    // 按"#"拆分消息并提取参数
    String[] params = 消息.split("#");
    
    // 校验参数数量
    if (params.length != 4) {
        发送(qun, "[AtQQ="+uin+"]  "+ "错误：消息格式不正确，应为[定制#昵称#属性#性别]'", true);
        return;
    }
    
    if (params.length >= 4) { // 确保参数数量足够
        String nickname = params[1];
        String attribute = params[2];
        String value = params[3];
        
        // 校验参数长度
        if (nickname.length() > 6) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「昵称」长度不能超过6个字符！", true);
            return;
        }
        if (attribute.length() > 6) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「属性」长度不能超过6个字符！", true);
            return;
        }
        if (value.length() > 3) {
            发送(qun, "[AtQQ="+uin+"]  "+ "\n错误：「性别」长度不能超过3个字符！", true);
            return;
        }
        
        // 收集空参数提示
        StringBuilder errorMsg = new StringBuilder();
        if (nickname.trim().isEmpty()) {
            errorMsg.append("昵称参数为空；\n");
        }
        if (attribute.trim().isEmpty()) {
            errorMsg.append("属性参数为空；\n");
        }
        if (value.trim().isEmpty()) {
            errorMsg.append("性别参数为空；\n");
        }
        
        // 输出合并后的错误提示
        if (errorMsg.length() > 0) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 检验错误：\n" + errorMsg.toString() + "\n☛请重新按格式发送！\n•格式：定制#昵称#属性#性别", true);
            return;
        }
        
        // 提取公共属性数据
        long[] petAttributes = petAttributeMap.get("定制");
        long 攻击 = petAttributes[0];
        long 防御 = petAttributes[1];
        long 智力 = petAttributes[2];
        long 生命上限 = petAttributes[3];
        long 进化次数 = petAttributes[4];
        long 下级经验 = 100;
        
        // 昵称重复检查逻辑
        boolean containsNickname = false;

        if (定制记录 != null && !"null".equals(定制记录) && !"".equals(定制记录.trim())) {
            // 按"、"分割定制记录
            String[] 宠物数组 = 定制记录.split("、");
            // 遍历处理每个分割后的子字符串
            for (String petItem : 宠物数组) {
                // 对每个子字符串按","分割
                String[] items = petItem.split(",");
                
                // 遍历判断是否包含nickname
                for (String item : items) {
                    if (item.equals(nickname)) {
                        containsNickname = true;
                        break;
                    }
                }
                if (containsNickname) {
                    break;
                }
            }
        }
        
        // 输出判断结果并处理不同场景
        if (petMap.containsKey(nickname)) {
            // 存在时获取对应的Pet对象
           Pet pet = (Pet) petMap.get(nickname);
           // 输出提示信息和品质
           发送(qun,"[AtQQ="+uin+"]   昵称重复！\n「" + nickname + "」已存在，其品质为：" + pet.getQuality()+"\n\n•：请使用其他昵称吧～",true);
        } else if (containsNickname) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 昵称重复！\n消息中昵称「" + nickname + "」已被占用，请使用其他昵称吧～", true);
        } else {
            // 存储定制宠物初始属性（公共逻辑提取）
            putString(配置名称, "心情", "100");
            putString(配置名称, "等级", "1");
            putString(配置名称, "攻击", 转文(攻击));
            putString(配置名称, "防御", 转文(防御));
            putString(配置名称, "智力", 转文(智力));
            putString(配置名称, "当前精力", "1000000");
            putString(配置名称, "精力上限", "100");
            putString(配置名称, "当前生命", 转文(生命上限));
            putString(配置名称, "生命上限", 转文(生命上限));
            putString(配置名称, "当前经验", "9999999999999"); // 体验版
            putString(配置名称, "下级所需经验", 转文(下级经验));
            putString(配置名称, "进化层次", "0"); // 当前已进化次数
            putString(配置名称, "进化上限", 转文(进化次数));
            putString(配置名称, "昵称", nickname);
            putString(配置名称, "宠物名字", nickname); // 后续可能会用上
            putString(配置名称, "性别", value);
            putString(配置名称, "属性", attribute);
            putString(配置名称, "阶段", "破壳期");
            putString(配置名称, "级别", "定制");
            putString(配置名称, "状态", "正常");
            putString(配置名称, "神器", "无");
            putString(配置名称, "天赋", "无");
            putString(配置名称, "婚姻状况", "单身");
            putString(配置名称, "是否易容","否");
            putBoolean(配置名 + "状态", "宠物定制", false);
            putBoolean(配置名+"状态","宠物图片", true);
                    
            // 处理宠物列表存储逻辑
            String 组合 = nickname + "," + attribute + "," + value;
            String newPetList = (定制记录 != null && !定制记录.isEmpty()) ? 定制记录 + "、" + 组合 : 组合;
            putString("定制宠物", "宠物列表", newPetList);
                    
            // 发送成功消息
            发送(qun, "[AtQQ="+uin+"]  "+ " ✓校验通过!\n————————\n【昵称】：" + nickname + "\n【属性】：" + attribute + "\n【性别】：" + value + "\n————————\n•☛请发送一张接近正方形的图片，该图片会作为当前定制宠物的图片", false);
        }
    }
}

        boolean 改名 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物改名", false);
        // 在使用过（改名卡）后，收集该玩家发送的文本信息作为宠物昵称
        if (data.MessageType == 1 && 改名 == true && !消息.contains("PicUrl=http")) {
            String processedMessage = 消息.replaceAll("\n", "").replaceAll("\\s", "");
            // 替换换行空格  ↑
            
            // 判断处理后的内容  
            if (processedMessage.isEmpty()) { // 全为空格或换行（处理后为空）  
                发送(qun, "[AtQQ="+uin+"]  "+ "失败！\n你的消息中没有可用内容，请重新发送消息", true);
            } else if(processedMessage.length() > 6) { // 大于6  
                发送(qun, "[AtQQ="+uin+"]  "+ " 改名失败！\n当前消息字数>6，请重新发送消息！", true);          
            } else if (petMap.containsKey(processedMessage)) { 
              //宠物库存在该昵称的宠物
              // 存在时获取对应的Pet对象
             Pet pet = (Pet) petMap.get(nickname);
             // 输出提示信息和品质
             发送(qun,"[AtQQ="+uin+"]   昵称重复！\n「" + processedMessage + "」已存在，其品质为：" + pet.getQuality()+"\n\n•：请使用其他昵称吧～",true);
           }else { // 长度≤6  
                发送(qun, "[AtQQ="+uin+"]   成功！\n你的宠物已改名为「" + processedMessage + "」！\n可发送[我的宠物]查看变化", true);
                putBoolean(配置名 + "状态", "宠物改名", false);
                putString(配置名称, "昵称", processedMessage);
            }  
        }
           
        boolean 作图 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物图片", false);
        // 下载图片作为定制宠物的图片
        if (作图 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (宠物名 == null) {
                发送(qun, "[AtQQ="+uin+"]  "+ " 当前没有宠物", true);
                return;
            }
            
            String 名字 = getString(配置名称, "宠物名字");
            String 级别 = getString(配置名称, "级别");
            if (petMap.containsKey(名字)) {
               发送(qun,"[AtQQ="+uin+"]  图片保存失败！\n你的当前宠物〔"+名字+"〕不是“使用宠物定制卡”后的宠物！",true);
               return;
            } else if (!petMap.containsKey(名字)&&级别.equals("定制")) {
               if (matcher.find()) {  
                   String link = matcher.group(1); // 提取括号内的链接部分  
                   String 路径 = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";  

                   File 头像文件 = new File(路径);
                   File parentDir = 头像文件.getParentFile();  
                   if (!parentDir.exists()) {  
                      parentDir.mkdirs(); // 先创建父目录  
                   }  
               
                   try {  
                         downloadFile(link, 路径);
                         // 无论文件是否存在，直接下载/覆盖
                         当前宠物(qun,uin,0); //绘制该玩家当前宠物
                         发送(qun, "[AtQQ="+uin+"]  "+ " 保存成功！\n你的定制宠物图片已更换！\n•效果如下↓[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]", true);
                         putBoolean(配置名 + "状态", "宠物图片", false);
                    } catch (IOException e) {  
                       e.printStackTrace();  
                      Toast(uin+"宠物定制图下载失败");  
                    }
               }  
            }
        }
        
        boolean 宠物背景 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物背景图", false);
        // 下载图片作为指定玩家的宠物背景图
        if (宠物背景 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (matcher.find()) {  
                String link = matcher.group(1); // 提取括号内的链接部分  
                String 路径 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/宠物背景图.png";  
                
                File 文件 = new File(路径);  
                File parentDir = 文件.getParentFile();  
                if (!parentDir.exists()) {  
                    parentDir.mkdirs(); // 先创建父目录  
                }  
                
                try {  
                    downloadFile(link, 路径);
                   // 无论文件是否存在，直接下载/覆盖
                 } catch (IOException e) {  
                     e.printStackTrace();  
                     Toast("图片下载失败");  
                 }

                当前宠物(qun,uin,0); //绘制该玩家当前宠物
                发送(qun, "[AtQQ="+uin+"]  "+ " 保存成功！\n你的宠物背景图已更换！\n•效果如下↓[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]", true);
                putBoolean(配置名 + "状态", "宠物背景图", false);
            }  
        }
        
           
        boolean 易容 = getBoolean(data.GroupUin+"/"+data.UserUin+"/状态", "宠物易容", false);
        // 在使用过（易容丹）后，收集该玩家发送的图片作为宠物图片（易容图）
        if (易容 == true && 消息.contains("PicUrl=http")) {
            String regex = "\\[PicUrl=([^\\]]+)\\]";  
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(消息);  
            if (matcher.find()) {  
                String link = matcher.group(1); // 提取括号内的链接部分  
                String 路径 = AppPath + "/目录/图片/其他/玩家上传/" + uin + "/" + qun + "/" + TimeMark() + ".png";  

                File 头像文件 = new File(路径);
                File parentDir = 头像文件.getParentFile();  
                if (!parentDir.exists()) {  
                    parentDir.mkdirs(); // 先创建父目录  
                }  
               
                try {  
                    downloadFile(link, 路径);
                   // 无论文件是否存在，直接下载/覆盖
                 } catch (IOException e) {  
                     e.printStackTrace();  
                     Toast("图片下载失败");  
                 }
                
                //判断之前是否已易容
                String 是否易容=getString(配置名称, "是否易容");
                String 易容图=getString(配置名称, "上传图片"); //易容图片的文件名
                if(是否易容.equals("是")){
                  //删除之前的易容图缓存
                  删除文件(AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+易容图+".png");
                }
                
                发送(qun, "[AtQQ="+uin+"]  "+ " 易容成功！\n你的宠物图片已更换！\n•可发送[我的宠物]查看变化", true);
                putBoolean(配置名 + "状态", "宠物易容", false);
                putString(配置名称, "是否易容", "是");
                putString(配置名称, "上传图片", TimeMark());
            }  
        }
        
    }


//判断是否为指令
 if(消息!=null&&!消息.trim().isEmpty()){
   一些指令(data,qun,gm,uin,消息);
 }
   
   
} //← 已开启[宠物世界]玩法
}



//一些指令的集合处理
public void 一些指令(Object data, String qun, String gm, String uin, String msg)
{
    String 消息=msg+"";
    String 配置名=qun+"/"+uin+"/";  
    long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
    long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
    String 配置名称=qun+"/"+uin+"/宠物小窝/位置_0";
    String 宠物名=getString(配置名称,"昵称");
    int 权限等级 = checkPermission(uin); //权限等级：0=玩家，1=主人

    //夺宝系统相关指令
    if(消息.startsWith("夺宝系统")){
      userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); //记录操作时间
       StringBuilder 文案 = new StringBuilder("----夺宝系统----\n");
      文案.append("积分夺宝  金币夺宝\n");
      文案.append("积分十连  金币十连\n");
      文案.append("积分百连  金币百连\n");
      文案.append("积分宝池  金币宝池\n");
      文案.append("我的夺宝  我的资产\n");
      文案.append("开启双倍概率\n");
      文案.append("关闭双倍概率");
      发送(qun,文案.toString(),false);
    } else if (消息.equals("积分夺宝")) {
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handlePointTreasure(qun, uin, 1);
    } else if (消息.equals("金币夺宝")||消息.equals("点券夺宝")) {
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handleGoldTreasure(qun, uin, 1);
    } else if (消息.equals("积分十连")) {
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handlePointTreasure(qun, uin, 10);
    } else if (消息.equals("金币十连")||消息.equals("点券十连")) {
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handleGoldTreasure(qun, uin, 10);
    } else if (消息.equals("积分百连")) {
       userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handlePointTreasure(qun, uin, 100);
    } else if (消息.equals("金币百连")||消息.equals("点券百连")) {
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        handleGoldTreasure(qun, uin, 100);
    } else if (消息.equals("积分宝池")) {
      userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        showPointPool(qun, uin);
    } else if (消息.equals("金币宝池")||消息.equals("点券宝池")) {
      userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        showGoldPool(qun, uin);
    } else if (消息.equals("我的夺宝")) {
      userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        showMyTreasureInfo(qun, uin);
    }
    
    
  //“组队打怪”相关指令
  if(消息.startsWith("我的队伍")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     我的队伍(qun,uin); //返回相关内容
  }
  if(消息.startsWith("队伍列表")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     队伍列表(qun,uin); //返回相关内容
  }
  if(消息.startsWith("加入队伍")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     String 目标;
      if(data.mAtList.size()>=1){
         目标 = data.mAtList.get(0);
      }else if (data.mAtList == null || data.mAtList.size() == 0) {
         // 判断是否含有数字
         boolean 含有数字 = 消息.matches(".*\\d+.*");
         if (含有数字) {
            // 去掉非数字的内容
            String 纯数字 = 消息.replaceAll("\\D", "");
            目标 = 纯数字;
         } else {
            目标 = ""; //无数字，默认为空字符串
         }
      }
      
     if(目标 == null || "".equals(目标) ||目标.isEmpty()){
       发送(qun,"[AtQQ="+uin+"]  指令格式错误！\n◆格式1：加入队伍@玩家\n◆格式2：加入队伍+QQ号",true);
       return;
     }

     // 校验目标是否在本群
     String groupMembers = getGroupMembersUin(qun);
     if (groupMembers != null && !groupMembers.contains(目标)) {
        发送(qun, "[AtQQ=" + uin + "]  " + "本群没有QQ为[" + 目标 + "]的成员！请重新输入！", true);
         return;
     }
    
     加入队伍(qun,uin,目标); //返回相关内容
  }
  if(消息.startsWith("创建队伍")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     新建队伍(qun,uin); //返回相关内容
  }
  if(消息.startsWith("退出队伍")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     退出队伍(qun,uin); //返回相关内容
  }
  if(消息.startsWith("攻击怪物")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        String 配置名 = qun + "/" + uin + "/";
        String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
        String 状态 = getString(配置名称, "状态");
        String 闭关 = getString(配置名称, "闭关");
        String 天赋 = getString(配置名称, "天赋");
        String 昵称 = getString(配置名称, "昵称");
        String 名字 = getString(配置名称, "宠物名字");
        long 等级 = 文转(getString(配置名称, "等级"));
        long 攻击 = 文转(getString(配置名称, "攻击"));
        long 防御 = 文转(getString(配置名称, "防御"));
        long 智力 = 文转(getString(配置名称, "智力"));
        long 心情 = 文转(getString(配置名称, "心情"));
        long 当前精力 = 文转(getString(配置名称, "当前精力"));
        long 精力上限 = 文转(getString(配置名称, "精力上限"));
        long 当前生命 = 文转(getString(配置名称, "当前生命"));
        long 生命上限 = 文转(getString(配置名称, "生命上限"));
    if(等级<1) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }else if(当前精力<10){
       发送(qun,"[AtQQ="+uin+"]   您的宠物精力不足10，没有动力来进行任何操作～",true);
     }else{
       玩家攻击(qun,uin,攻击);
     }
  }
  
  
//“日常活动”相关指令
if(消息.startsWith("洗髓")||消息.startsWith("修炼")||消息.startsWith("双修")||消息.startsWith("冥想")||消息.startsWith("玩耍")||消息.startsWith("闭关")||消息.startsWith("出关")||消息.startsWith("打工")||消息.startsWith("探险")||消息.startsWith("学习")){
  
  //记录玩家指令触发时间戳（常用）
  userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));

  // 指令间隔逻辑 
  int DailyInterval = getInt("系统配置", "日常间隔", 9000); 
  //默认9秒，可以适当往上增加一些
  String key = qun + "_" + uin;
  TimestampWithText lastTriggerInfo = userDailyTime.get(key);

  if (lastTriggerInfo != null) {
     long lastTime = lastTriggerInfo.getTimestamp();
     long now = System.currentTimeMillis();
     long remain = (lastTime + DailyInterval) - now;
        
    if (remain >= 1000) { // 天赋间隔≥1秒
       发送(qun, "[AtQQ="+uin+"]  时间还没到，距离你下次活动还差[" + remain/1000 + "]秒！",true);
       return; // 直接终止后续逻辑
    }else if (remain >= 1) { // 剩余时间小于1秒（1000毫秒），但≥1毫秒
       发送(qun, "[AtQQ="+uin+"]  时间还没到，距离你下次活动还差[" + remain+"]毫秒！",true); 
       return;
    }
  }

    String 配置名称 = qun + "/" + uin + "/宠物小窝/位置_0";
    String 状态 = getString(配置名称, "状态");
    String 闭关 = getString(配置名称, "闭关");
    String 天赋 = getString(配置名称, "天赋");
    String 昵称 = getString(配置名称, "昵称");
    String 名字 = getString(配置名称, "宠物名字");
    String 婚况 = getString(配置名称, "婚姻状况");
    long 等级 = 文转(getString(配置名称, "等级"));
    long 攻击 = 文转(getString(配置名称, "攻击"));
    long 防御 = 文转(getString(配置名称, "防御"));
    long 智力 = 文转(getString(配置名称, "智力"));
    long 心情 = 文转(getString(配置名称, "心情"));
    long 当前精力 = 文转(getString(配置名称, "当前精力"));
    long 精力上限 = 文转(getString(配置名称, "精力上限"));
    long 当前经验 = 文转(getString(配置名称, "当前经验"));
    long 当前生命 = 文转(getString(配置名称, "当前生命"));
    long 生命上限 = 文转(getString(配置名称, "生命上限"));
    long 基础经验 = 5; // 每秒5经验（出关时计算收益）
    long 时长上限 = 2592000000L; // 闭关上限为30天
    if(等级<1) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
     }else if(闭关!=null&&消息.startsWith("出关")&&!闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物没有闭关哦!",true);
    }else if(闭关!=null&&消息.startsWith("闭关")&&闭关.equals("闭关")){
        long 当前时间戳 = System.currentTimeMillis(); //系统当前时间戳
        long 闭关时间戳 = 文转(getString(配置名称, "时间戳"));
        long 累计闭关 = 当前时间戳 - 闭关时间戳;
        long 闭关时长 = 累计闭关 / 1000; // 闭关时长(秒)
        if(闭关时长>时长上限){
          //闭关时长超过30天，按30天来算
          闭关时长=时长上限;
          msh="（30天封顶）";
        }
        long 经验收益 = 闭关时长*基础经验;
       发送(qun,"[AtQQ="+uin+"]   您的宠物已经在闭关中，如需停止闭关，请发送【出关】!\n✦当前已闭关："+formatTime(累计闭关)+"\n✦预计能增加："+数值转(经验收益)+"经验",true);
    }else if(闭关!=null&&!消息.startsWith("出关")&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
    }else if(!消息.startsWith("玩耍")&&等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }else if(消息.startsWith("玩耍")&&心情>=100){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情畅快，无需进行此操作！",true);
    }else if(消息.startsWith("双修")&&婚况.equals("单身")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物还没有结婚哦，无需进行此操作！",true);
    }else if(消息.startsWith("冥想")&&当前生命>=生命上限){
       发送(qun,"[AtQQ="+uin+"]   您的宠物并没有受伤，无需进行此操作！",true);
    }else if(消息.startsWith("洗髓")&&智力>100000){
       发送(qun,"[AtQQ="+uin+"]   你的宠物智力>100000，无法继续使用该指令来提升哦！",true);
    }else if(当前精力<10){
       发送(qun,"[AtQQ="+uin+"]   您的宠物精力不足10，没有动力来进行任何操作～",true);
    }else{ //开始处理指令对应的逻辑
    
       //记录玩家“日常活动”相关指令触发时间戳
       userDailyTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        Random random = new Random(); //随机数生成器实例
        // 一些地图名称，下面部分指令的文案会用到
        String[] 地图名称 = {
         "幽暗森林", "熔岩秘境", "冰封神殿", "迷雾沼泽", "上古遗迹",
         "星空幻境", "深渊裂隙", "断魂谷", "试炼之塔", "龙巢地宫",
         "遗忘神殿", "毒瘴湿地", "雷鸣废墟", "月光林地", "暗影石窟",
         "黄金矿洞", "末日火山", "寂静墓园", "幻境迷宫", "通天塔",
         "海底龙宫", "沙漠神殿", "冰封雪域", "火焰山", "迷雾山城",
         "骸骨荒原", "星辰大海", "虚空裂隙", "圣灵教堂", "诅咒古堡",
         "极光冰原", "炽热沙漠", "迷雾森林", "废弃工厂", "魔法学院",
         "试炼秘境", "恶魔巢穴", "天使圣殿", "亡灵沼泽", "机械迷城"
       };
       //“探险”指令能获得的道具数组（要 道具.java 内已有的道具哦）
        String[] 道具名称 = {
         "一万积分卡", "五千积分卡", "瑶光宝箱", "小破锋丹", "小御体丹",
         "复活石", "往生花", "经验丹", "10金币", "破锋丹","御体丹",
         "小精力药", "中精力药", "大精力药", "生命之源", "薄荷糖",
         "万灵药", "死亡丹", "太虚镜", "长生丹", "小长生丹","信标",
         "绷带", "聚灵丹", "开心果", "小聚灵丹", "小焕能丹","龙珠",
         "星核", "玄铁", "灵藤", "小窝进阶卡", "50金币","易容丹",
         "改名卡", "蜂蜜果糖","九转续命丹"
       };
        
      if(消息.startsWith("洗髓")){
        long randomNum = 随机数(200, 300);
        //扣除精力，并增加随机智力
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "智力", 转文(智力+randomNum));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】正在洗髓伐毛！";
        文案+="\n------------------";
        文案+="\n●洗髓耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●增加智力：+"+randomNum;
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("打工")){
        long randomNum = 随机数(10000, 50000); //随机经验
        int index = random.nextInt(地图名称.length); // 生成随机索引
        String randomContent = 地图名称[index]; // 随机地图名称
        //扣除精力，并增加随机积分
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分+randomNum));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】正在·"+randomContent+"·努力搬砖！";
        文案+="\n------------------";
        文案+="\n●打工耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●增加积分：+"+randomNum;
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("闭关")){
        long 时间戳=System.currentTimeMillis();
        //扣除精力，并将状态改为“闭关”
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "闭关", "闭关"); //更新闭关状态
        putString(配置名称, "时间戳", 转文(时间戳)); //记录当前时间戳
        //返回文案,并发送
        String 文案="您的【"+昵称+"】开始闭关啦！";
        文案+="\n------------------";
        文案+="\n●减少精力：-10点";
        文案+="\n○按照闭关时长获得收益";
        文案+="\n○停止闭关请发送【出关】";
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("出关")){
        long 当前时间戳 = System.currentTimeMillis(); //系统当前时间戳
        long 闭关时间戳 = 文转(getString(配置名称, "时间戳"));
        long 出关时间戳 = 当前时间戳 - 闭关时间戳;
        long 闭关时长 = 出关时间戳 / 1000; // 闭关时长(秒)
        String msh=""; //超过30天时，添加文本（平时为空字符串）
        if(闭关时长>时长上限){
          //闭关时长超过30天，按30天来算
          闭关时长=时长上限;
          msh="（30天封顶）";
        }
        long 经验收益 = 闭关时长*基础经验;
        //扣除精力，并按闭关时长来计算经验收益
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "闭关", "无"); //更新闭关状态
        putString(配置名称, "当前经验", 转文(当前经验+经验收益));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】出关啦！";
        文案+="\n------------------";
        文案+="\n●闭关时长："+formatTime(出关时间戳)+msh;
        文案+="\n●减少精力：-10点";
        文案+="\n●增加经验：+"+数值转(经验收益);
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("修炼")){
        long randomNum = 随机数(10000, 50000); //随机积分
        int index = random.nextInt(地图名称.length); // 生成随机索引
        String randomContent = 地图名称[index]; // 随机地图名称
        //扣除精力，并增加随机经验
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "当前经验", 转文(当前经验+randomNum));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】正在·"+randomContent+"·修炼！";
        文案+="\n------------------";
        文案+="\n●修炼耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●增加经验：+"+randomNum;
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("双修")){
        long randomNum = 随机数(10000, 50000); //随机经验
        int index = random.nextInt(地图名称.length); // 生成随机索引
        String randomContent = 地图名称[index]; // 随机地图名称
        //扣除精力，并增加随机经验*2
        long DoubleNum=randomNum*2;
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "当前经验", 转文(当前经验+DoubleNum));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】正在·"+randomContent+"·与道侣双修！";
        文案+="\n------------------";
        文案+="\n●双修耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●增加经验：+"+DoubleNum;
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("玩耍")){
        long randomNum = 随机数(10, 30); //随机心情
        //扣除精力，并增加随机心情值
        putString(配置名称, "当前精力", 转文(当前精力-10));
        putString(配置名称, "心情", 转文(心情+randomNum));
        //返回文案,并发送
        String 文案="您的【"+昵称+"】出门玩耍啦！";
        文案+="\n------------------";
        文案+="\n●玩耍耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●心情恢复：+"+randomNum;
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("探险")){
        String bagKey = qun+"/"+uin+"/我的背包";
        String bag = getString(bagKey, "道具列表");
        int index = random.nextInt(道具名称.length); // 生成随机道具索引
        String randomContent = 道具名称[index]; // 随机道具
        int index2 = random.nextInt(地图名称.length); // 生成随机地图索引
        String randomContent2 = 地图名称[index]; // 随机地图名称
        //扣除精力，并获得随机道具×1
        putString(配置名称, "当前精力", 转文(当前精力-10));
        if (bag == null || !bag.contains(randomContent)) {
            // 背包中无此道具，新增
            String newBag = (bag == null ? "" : bag + "、") + randomContent;
            putString(bagKey, "道具列表", newBag);
            putString(bagKey, randomContent, "1");
        } else {
            // 背包中已有此道具，累加数量
            long 现有数量 = 文转(getString(bagKey, randomContent));
            putString(bagKey, randomContent, 转文(现有数量+1));
        }
        //获取该道具的相关信息
        Item selectedItem = (Item) itemMap.get(randomContent);
        String 类型=selectedItem.getType(); //获取道具类型
        String 简介=selectedItem.getDescription(); //获取道具简介
        //返回文案,并发送
        String 文案="您的【"+昵称+"】在"+randomContent2+"探险时获得了1个"+randomContent+"！";
        文案+="本次耗时"+formatTime((long) DailyInterval)+"！";
        文案+="\n物品说明:〔"+类型+"〕"+简介.replace("\n","");
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("学习")){
        long randomNum = 随机数(3, 30); //随机属性值
        // 属性字符串数组
        String[] textMsg={"智力","攻击","防御","生命上限"};
        int index = random.nextInt(textMsg.length); // 生成随机索引
        String randomContent = textMsg[index]; // 随机属性
        //扣除精力，并增加随机属性
        putString(配置名称, "当前精力", 转文(当前精力-10));
        if(randomContent.equals("智力")){
          putString(配置名称, randomContent, 转文(智力+randomNum));
        }else if(randomContent.equals("攻击")){
          putString(配置名称, randomContent, 转文(攻击+randomNum));
        }else if(randomContent.equals("生命上限")){
          putString(配置名称, randomContent, 转文(生命上限+randomNum));
        }else if(randomContent.equals("防御")){
          putString(配置名称, randomContent, 转文(防御+randomNum));
        }
        //返回文案,并发送
        String 文案="您的【"+昵称+"】正在专心学习！";
        文案+="\n------------------";
        文案+="\n●学习耗时：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●增加属性：+"+randomNum+"（"+randomContent+"）";
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      else if(消息.startsWith("冥想")){
        double minPercent = 0.05; // 最小5%
        double maxPercent = 0.35; // 最大35%
        // 生成[minPercent, maxPercent]之间的随机数（包含边界）
        double randomPercent = minPercent + random.nextDouble() * (maxPercent - minPercent);
        // 计算恢复的生命值 取整
        double 回血百分比= randomPercent;
        long 恢复值 = Math.round(生命上限 * 回血百分比);
        double 恢复占比= (double) 恢复值/生命上限;
        double 恢复百分比 = Math.round(恢复占比 * 100 * 10) / 10.0;
        宠物回血(qun, uin, 回血百分比); //恢复百分比生命（比如：1=100%）                 
        putString(配置名称, "当前精力", 转文(当前精力-10)); //扣除精力
        //返回文案,并发送
        String 文案="您的【"+昵称+"】进入冥想中！";
        文案+="\n------------------";
        文案+="\n●冥想时间：+"+formatTime((long) DailyInterval);
        文案+="\n●减少精力：-10点";
        文案+="\n●恢复血量：+"+数值转(恢复值)+"（"+formatNumber(恢复百分比)+"%）";
        文案+="\n------------------";
        发送(qun,"[AtQQ="+uin+"]  "+文案,true);
      }
      
    }
  
}


  //使用相关道具/神器
  if(消息.startsWith("使用")){
    String 玩家;
    if(消息.contains("@")){
      if(data.mAtList.size()>=1){
         玩家 = data.mAtList.get(0);
         消息=removeAfterAt(消息)+"-"+玩家;
      }
    }
    使用(data,qun,"",uin,消息); //用相关方法处理
  }

   //卸下神器
   if(消息.startsWith("卸下神器")||消息.startsWith("卸除神器")){
     卸神器(data,qun,"",uin,消息); //用相关方法处理
   }

/*转让道具指令

 格式1：使用+道具名称#数量@玩家
 格式2：使用+道具名称#数量-账号
 格式3：使用+道具名称@玩家
 格式4：使用+道具名称-账号
*/
if(消息.startsWith("转让")){
    String 玩家;
    if(消息.contains("@")){
      if(data.mAtList.size()>=1){
         玩家 = data.mAtList.get(0);
         消息=removeAfterAt(消息)+"-"+玩家;
      }
    }
  转让(data,qun,"",uin,消息); //用相关方法处理
}


//合成指令（可合成道具/神器）
if (消息.startsWith("合成")) {

      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
      
    String 选定;
    long 数量 = 1; // 默认数量为1

    // 移除"合成"指令头
    String 内容 = 消息.substring(2).trim();

    // 按第一个"#"分割（只分割一次，避免道具名含#的问题）
    String[] parts = 内容.split("#", 2); // 限制分割为2部分

    if (parts.length == 2) {
        // 有#符号：提取道具名和数量
        选定 = parts[0].trim();
        String 数字部分 = parts[1].trim();

        // 处理数字部分（去前导零）
        if (!数字部分.isEmpty()) {
            if (数字部分.length() > 1) {
                String 处理后数字 = 数字部分.replaceFirst("^0+", "");
                数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
            } else {
                数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
            }
            // 转换为long（需捕获异常）
            try {
                数量 = Long.parseLong(数字部分);
            } catch (NumberFormatException e) {
                数量 = 1; // 数字格式错误时默认1
            }
        }
    } else {
        // 无#符号：整个内容作为道具名，数量默认1
        选定 = 内容.trim();
    }

    // 校验道具名是否为空
    if (选定.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "] 指令格式错误！\n•格式：合成+道具名#数量\n•示例：合成龙啸破云刃#3", true);
        return;
    }

    // 检查道具是否存在
    boolean isFound = itemMap.containsKey(选定);
    if (!isFound) { // 道具不存在，发送提示
        发送(qun, "[AtQQ="+uin+"]  "+ "〔" + 选定 + "〕不存在，请检查是否输入错误！", true);
    } else {
        Item selectedItem = (Item) itemMap.get(选定);
        String 物品类型=selectedItem.getType(); //获取物品类型
        if (!selectedItem.isCanCombine()) {
          发送(qun, "[AtQQ="+uin+"]  「" + 选定 + "」无法合成！", true);
          return;
        }
        
        //合成神器
       if(物品类型.equals("神器")){
         onSynthesize(qun,uin,选定,数量);
       }
   }
     
    
}

//分解指令（可分解道具/神器）
if (消息.startsWith("分解")) {
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
      
    String 选定;
    long 数量 = 1; // 默认数量为1

    // 移除"合成"指令头
    String 内容 = 消息.substring(2).trim();

    // 按第一个"#"分割（只分割一次，避免道具名含#的问题）
    String[] parts = 内容.split("#", 2); // 限制分割为2部分

    if (parts.length == 2) {
        // 有#符号：提取道具名和数量
        选定 = parts[0].trim();
        String 数字部分 = parts[1].trim();

        // 处理数字部分（去前导零）
        if (!数字部分.isEmpty()) {
            if (数字部分.length() > 1) {
                String 处理后数字 = 数字部分.replaceFirst("^0+", "");
                数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
            } else {
                数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
            }
            // 转换为long（需捕获异常）
            try {
                数量 = Long.parseLong(数字部分);
            } catch (NumberFormatException e) {
                数量 = 1; // 数字格式错误时默认1
            }
        }
    } else {
        // 无#符号：整个内容作为道具名，数量默认1
        选定 = 内容.trim();
    }

    // 校验道具名是否为空
    if (选定.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "] 指令格式错误！\n•格式：分解+道具名#数量\n•示例：分解聚气青锋#3", true);
        return;
    }

    // 检查道具是否存在
    boolean isFound = itemMap.containsKey(选定);
    if (!isFound) { // 道具不存在，发送提示
        发送(qun, "[AtQQ="+uin+"]  "+ "〔" + 选定 + "〕不存在，请检查是否输入错误！", true);
    } else {
        Item selectedItem = (Item) itemMap.get(选定);
        String 物品类型=selectedItem.getType(); //获取物品类型
        if (!selectedItem.isCanDecompose()) {
          发送(qun, "[AtQQ="+uin+"]  「" + 选定 + "」无法分解！", true);
          return;
        }
        
        //分解神器
       if(物品类型.equals("神器")){
         onDecompose(qun,uin,选定,数量);
       }
   }
     
    
}


//道具列表设定查询
if(消息.startsWith("查道具")){
   userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
 
   handleItemQuery(qun,uin,消息);
}

// 限制内容拼接为正则表达式（用|分隔表示“或”）
String 查看限制 = "龙珠系列|星核系列|灵藤系列|玄铁系列|常规系列|天赋";

// 查看一些内容（道具简介/神器列表）
if (消息.startsWith("查看")) {
  if(!消息.matches(".*(" + 查看限制 + ").*")){
     //消息中不包含限制内容
    查看(data, qun, "", uin, 消息);
  }else{
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    if(消息.contains("龙珠系列")){
      StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
      文案.append("// 龙珠系列神器\n");
      文案.append("[1]聚气青锋：消耗龙珠×5可直接合成，聚气青锋×2可合成旋影游龙剑。\n");
      文案.append("[2]旋影游龙剑，消耗龙珠×10可直接合成，旋影游龙剑×2可合成龙啸破云刃。\n");
      文案.append("[3]龙啸破云刃，消耗龙珠×20可直接合成，龙啸破云刃×2可合成裂空星陨剑。\n");
      文案.append("[4]裂空星陨剑，消耗龙珠×40可直接合成，裂空星陨剑×2可合成陨星龙脊剑。\n");
      文案.append("[5]陨星龙脊剑，消耗龙珠×80可直接合成，陨星龙脊剑×2可合成九霄龙吟剑。\n");
      文案.append("[6]九霄龙吟剑，消耗龙珠×160可直接合成，九霄龙吟剑×2可合成混沌盘龙刃。\n");
      文案.append("[7]混沌盘龙刃，消耗龙珠×320可直接合成，混沌盘龙刃×2可合成万象龙纹戟。\n");
      文案.append("[8]万象龙纹戟，消耗龙珠×640可直接合成，万象龙纹戟×2可合成太虚龙魄枪。\n");
      文案.append("[9]太虚龙魄枪，消耗龙珠×1280可直接合成，太虚龙魄枪×2可合成焚世龙炎刀。\n");
      文案.append("[10]焚世龙炎刀，消耗龙珠×2560可直接合成，焚世龙炎刀×2可合成镇狱龙渊斧。\n");
      文案.append("[11]镇狱龙渊斧，消耗龙珠×5120可直接合成，镇狱龙渊斧×2可合成永恒龙晶锏。\n");
      文案.append("[12]永恒龙晶锏，消耗龙珠×10240可直接合成，永恒龙晶锏×2可合成混沌祖龙杵。\n");
      文案.append("[13]混沌祖龙杵，消耗龙珠×15000可直接合成，混沌祖龙杵×2可合成造化天龙钩。\n");
      文案.append("[14]造化天龙钩，消耗龙珠×20000可直接合成，造化天龙钩×2可合成无上龙尊槊。\n");
      文案.append("[15]无上龙尊槊，消耗龙珠×25000可直接合成，无上龙尊槊×2可合成混元终焉剑。\n");
      文案.append("[16]混元终焉剑：龙珠×30000可直接合成。\n<填充>\n//✦指令：合成+道具名\n//✦例如：合成聚气青锋");
       toImg(文案.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/龙珠系列.png",false);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/龙珠系列.png]",false);
    }
    if(消息.contains("星核系列")){
      StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
      文案.append("// \"星核\"系列神器\n");
      文案.append("[1]星辉凝霜剑：消耗星核×5可直接合成，星辉凝霜剑×2可合成幻星逐月双剑。\n");
      文案.append("[2]幻星逐月双剑：消耗星核×10可直接合成，幻星逐月双剑×2可合成星痕破军剑。\n");
      文案.append("[3]星痕破军剑：消耗星核×20可直接合成，星痕破军剑×2可合成流光星陨刃。\n");
      文案.append("[4]流光星陨刃：消耗星核×40可直接合成，流光星陨刃×2可合成陨星破晓剑。\n");
      文案.append("[5]陨星破晓剑：消耗星核×80可直接合成，陨星破晓剑×2可合成星辰耀世枪。\n");
      文案.append("[6]星辰耀世枪：消耗星核×160可直接合成，星辰耀世枪×2可合成星穹龙脊棍。\n");
      文案.append("[7]星穹龙脊棍：消耗星核×320可直接合成，星穹龙脊棍×2可合成星渊万象戟。\n");
      文案.append("[8]星渊万象戟：消耗星核×640可直接合成，星渊万象戟×2可合成太虚星魄杖。\n");
      文案.append("[9]太虚星魄杖：消耗星核×1280可直接合成，太虚星魄杖×2可合成焚星龙炎鞭。\n");
      文案.append("[10]焚星龙炎鞭：消耗星核×2560可直接合成，焚星龙炎鞭×2可合成镇狱星渊斧。\n");
      文案.append("[11]镇狱星渊斧：消耗星核×5120可直接合成，镇狱星渊斧×2可合成永恒星晶锤。\n");
      文案.append("[12]永恒星晶锤：消耗星核×10240可直接合成，永恒星晶锤×2可合成混沌星祖锏。\n");
      文案.append("[13]混沌星祖锏：消耗星核×15000可直接合成，混沌星祖锏×2可合成造化星龙枪。\n");
      文案.append("[14]造化星龙枪：消耗星核×20000可直接合成，造化星龙枪×2可合成无上星尊剑。\n");
      文案.append("[15]无上星尊剑：消耗星核×25000可直接合成，无上星尊剑×2可合成混元星穹刃。\n");
      文案.append("[16]混元星穹刃：消耗星核×30000可直接合成。\n<填充>\n//✦指令：合成+道具名\n//✦例如：合成星辉凝霜剑");
       toImg(文案.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/星核系列.png",false);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/星核系列.png]",false);
    }
    if(消息.contains("灵藤系列")){
      StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
      // 添加"灵藤"体系神器
      文案.append("// \"灵藤\"系列神器\n");
      文案.append("[1]缠枝灵藤鞭：消耗灵藤×5可直接合成，缠枝灵藤鞭×2可合成疾风掠影鞭。\n");
      文案.append("[2]疾风掠影鞭：消耗灵藤×10可直接合成，疾风掠影鞭×2可合成幻影迷踪鞭。\n");
      文案.append("[3]幻影迷踪鞭：消耗灵藤×20可直接合成，幻影迷踪鞭×2可合成流光幻影鞭。\n");
      文案.append("[4]流光幻影鞭：消耗灵藤×40可直接合成，流光幻影鞭×2可合成幽影噬魂鞭。\n");
      文案.append("[5]幽影噬魂鞭：消耗灵藤×80可直接合成，幽影噬魂鞭×2可合成青冥九霄鞭。\n");
      文案.append("[6]青冥九霄鞭：消耗灵藤×160可直接合成，青冥九霄鞭×2可合成幽冥黄泉鞭。\n");
      文案.append("[7]幽冥黄泉鞭：消耗灵藤×320可直接合成，幽冥黄泉鞭×2可合成灵韵万法鞭。\n");
      文案.append("[8]灵韵万法鞭：消耗灵藤×640可直接合成，灵韵万法鞭×2可合成天影追魂鞭。\n");
      文案.append("[9]天影追魂鞭：消耗灵藤×1280可直接合成，天影追魂鞭×2可合成御灵控魔鞭。\n");
      文案.append("[10]御灵控魔鞭：消耗灵藤×2560可直接合成，御灵控魔鞭×2可合成幻世浮生鞭。\n");
      文案.append("[11]幻世浮生鞭：消耗灵藤×5120可直接合成，幻世浮生鞭×2可合成永恒命魂鞭。\n");
      文案.append("[12]永恒命魂鞭：消耗灵藤×10240可直接合成，永恒命魂鞭×2可合成混沌太初鞭。\n");
      文案.append("[13]混沌太初鞭：消耗灵藤×15000可直接合成，混沌太初鞭×2可合成造化阴阳鞭。\n");
      文案.append("[14]造化阴阳鞭：消耗灵藤×20000可直接合成，造化阴阳鞭×2可合成无上乾坤鞭。\n");
      文案.append("[15]无上乾坤鞭：消耗灵藤×25000可直接合成，无上乾坤鞭×2可合成混元无极鞭。\n");
      文案.append("[16]混元无极鞭：消耗灵藤×30000可直接合成。\n//✦指令：合成+道具名\n//✦例如：合成缠枝灵藤鞭");
       toImg(文案.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/灵藤系列.png",false);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/灵藤系列.png]",false);
    }
    if(消息.contains("玄铁系列")){
      StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
      文案.append("// 玄铁系列神器\n");
      文案.append("[1]淬体玄铁槌：消耗玄铁×5可直接合成，淬体玄铁槌×2可合成裂岩陨铁锤。\n");
      文案.append("[2]裂岩陨铁锤：消耗玄铁×10可直接合成，裂岩陨铁锤×2可合成撼地千钧锤。\n");
      文案.append("[3]撼地千钧锤：消耗玄铁×20可直接合成，撼地千钧锤×2可合成破天碎岳锤。\n");
      文案.append("[4]破天碎岳锤：消耗玄铁×40可直接合成，破天碎岳锤×2可合成碎空陨星锤。\n");
      文案.append("[5]碎空陨星锤：消耗玄铁×80可直接合成，碎空陨星锤×2可合成震岳凌霄锤。\n");
      文案.append("[6]震岳凌霄锤：消耗玄铁×160可直接合成，震岳凌霄锤×2可合成幽冥鬼煞锤。\n");
      文案.append("[7]幽冥鬼煞锤：消耗玄铁×320可直接合成，幽冥鬼煞锤×2可合成狂澜镇海锤。\n");
      文案.append("[8]狂澜镇海锤：消耗玄铁×640可直接合成，狂澜镇海锤×2可合成霸世吞天锤。\n");
      文案.append("[9]霸世吞天锤：消耗玄铁×1280可直接合成，霸世吞天锤×2可合成怒焰焚天锤。\n");
      文案.append("[10]怒焰焚天锤：消耗玄铁×2560可直接合成，怒焰焚天锤×2可合成灭世混沌锤。\n");
      文案.append("[11]灭世混沌锤：消耗玄铁×5120可直接合成，灭世混沌锤×2可合成永恒不朽锤。\n");
      文案.append("[12]永恒不朽锤：消耗玄铁×10240可直接合成，永恒不朽锤×2可合成混沌祖神锤。\n");
      文案.append("[13]混沌祖神锤：消耗玄铁×15000可直接合成，混沌祖神锤×2可合成造化圣天锤。\n");
      文案.append("[14]造化圣天锤：消耗玄铁×20000可直接合成，造化圣天锤×2可合成无上道尊锤。\n");
      文案.append("[15]无上道尊锤：消耗玄铁×25000可直接合成，无上道尊锤×2可合成混元终焉锤。\n");
      文案.append("[16]混元终焉锤：消耗玄铁×30000可直接合成。\n<填充>\n//✦指令：合成+道具名\n//✦例如：合成淬体玄铁槌");
       toImg(文案.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/玄铁系列.png",false);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/玄铁系列.png]",false);
    }
    if(消息.contains("常规系列")){
      StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
      文案.append("// 常规系列神器（属性加成较低）\n");
      文案.append("[1]青冥剑：消耗青冥玄铁×1、天青藤×1、剑灵残魂×1可直接合成。\n");
      文案.append("[2]聚灵盏：消耗聚灵玉髓×1、日月精华石×1、灵木心×1可直接合成。\n");
      文案.append("[3]镇魔幡：消耗镇魔符箓×1、九幽魔藤×1、魔王精血×1可直接合成。\n");
      文案.append("[4]星陨珠：消耗陨星碎片×1、星辉石×1、星辰沙×1可直接合成。\n");
      文案.append("[5]焚天印：消耗焚天炎晶×1、赤阳火精×1、炎魔之心×1可直接合成。\n");
      文案.append("[6]玄冰盾：消耗玄冰髓×1、极地寒晶×1、冰魄之心×1可直接合成。\n");
      文案.append("[7]太虚镜：消耗太虚镜碎片×1、凝神丹×1可直接合成。\n<填充>\n//✦指令：合成+道具名\n//✦例如：合成聚灵盏");
       toImg(文案.toString(), "", 0.5, 0.01, 30, AppPath+"/缓存/其他/其他神器.png",false);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/其他神器.png]",false);
    }
  }
}
 
 // 查看天赋列表
 if(消息.equals("天赋列表")){
   发送(qun,"[PicUrl="+AppPath+"/目录/图片/其他/天赋列表.jpg]",false);
 }

//发动天赋@玩家
if(消息.startsWith("发动天赋")){
     String 对象=qun+"/"+uin+"/宠物小窝/位置_0";
     String 昵称 = getString(对象, "昵称");
     String 天赋 = getString(对象, "天赋");
     String 闭关 = getString(对象, "闭关");
     String 状态 = getString(对象, "状态");
     long 等级1 = 文转(getString(对象, "等级"));
     long 心情 = 文转(getString(配置名称, "心情"));
     long 当前经验 = 文转(getString(对象, "当前经验"));
     long 当前精力 = 文转(getString(对象, "当前精力"));
     long 当前生命 = 文转(getString(对象, "当前生命"));
     long 生命上限 = 文转(getString(对象, "生命上限"));
     
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     
     // 指令间隔逻辑 
      int talentInterval = getInt("系统配置", "天赋间隔", 9000); 
      //默认9秒，可以适当往上增加一些
      String key = qun + "_" + uin;
      TimestampWithText lastTriggerInfo = userTalentTime.get(key);
    
      if (lastTriggerInfo != null) {
        long lastTime = lastTriggerInfo.getTimestamp();
        long now = System.currentTimeMillis();
        long remain = (lastTime + talentInterval) - now;
        
        if (remain >= 1000) { // 天赋间隔≥1秒
            发送(qun, "[AtQQ="+uin+"]  "+ " 天赋技能冷却中，距离下次发动还差[" + remain/1000 + "]秒！",true);
            return; // 直接终止后续逻辑
        }else if (remain >= 1) { // 剩余时间小于1秒（1000毫秒），但≥1毫秒
           发送(qun, "[AtQQ="+uin+"]  "+ " 天赋技能冷却中，距离下次发动还差[" + remain+"]毫秒！",true); 
           return;
        }
      }
     
     if(等级1<1) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
     }else if(等级1!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if(天赋.equals("无")){
       发送(qun,"[AtQQ="+uin+"]  您的宠物当前未觉醒天赋\n✦可发送【宠物觉醒】来觉醒天赋",true);
     }
     // 发动天赋+QQ
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(4);
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去除非数字字符
        目标 = 目标.replaceAll("[^\\d]", "");
       }
       String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(目标)){
           发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+目标+"]的成员！\n◆请检查是否输入错误！",true);
           return;
        }
        
        String[] info = petTalents.get(天赋);
        String type = info[0]; //类型：主动/被动
        String description = info[1]; //天赋描述
        
       String 堆箱=qun+"/"+目标+"/宠物小窝/位置_0";
       String 状态2 = getString(堆箱, "状态");
       String 天赋2 = getString(堆箱, "天赋");
       long 等级2 = 文转(getString(堆箱, "等级"));
       long 当前生命2 = 文转(getString(堆箱, "当前生命"));
       long 生命上限2 = 文转(getString(堆箱, "生命上限"));
       
        //执行天赋逻辑
        if(天赋.equals("妙手回春")){
          if(等级2<1) {
            发送(qun,"[AtQQ="+uin+"]  \n ["+目标+"]当前还没有宠物，无法对其发动天赋",true);
          }else if(当前精力>=10){
            if(当前生命2==生命上限2||当前生命2>生命上限2){
               发送(qun,"[AtQQ="+uin+"]  对方当前不需要恢复生命值！",true);
            }else if(!状态2.equals("死亡")){
              // 计算恢复的生命值（生命上限的40%）
              long 恢复值 = (long) (生命上限2 * 0.4);
              // 计算恢复后的当前生命（不会超过生命上限）
              当前生命2 = Math.min(当前生命2 + 恢复值, 生命上限2);
              String 状态变化="";
              if(!状态2.equals("正常")&&!状态2.equals("死亡")){
                状态变化="\n•状态变化：‘"+状态2+"’→‘正常’";
              }
              putString(对象, "当前精力", 转文(当前精力-10));
              putString(堆箱, "状态", "正常");
              putString(堆箱, "当前生命", 转文(当前生命2));
              发送(qun,"[AtQQ="+uin+"]  成功将对方宠物生命恢复至"+数值转(当前生命2)+状态变化,true);
              //记录天赋指令操作时间戳
              userTalentTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
            }else{
              发送(qun,"[AtQQ="+uin+"]  对方宠物已死亡，无法通过天赋指令恢复生命哦",true);
            }
          }else if(当前精力<10){
            发送(qun,"[AtQQ="+uin+"]  出战宠物当前精力不足10，指令发动失败！",true);
          }
        }else if(天赋.equals("妙手神偷")){
         String 道具 = getString(qun + "/" + 目标 + "/我的背包", "道具列表");
         long 次数上限=50;  //该天赋每日使用上限
         long 使用记录=文转(getString(配置名+"天赋限制", 天赋)); //已使用次数
         String today = getTodayDate(1); // 获取当前日期（格式：YYYY-MM-DD）
         String lastRunDate = getString(配置名+"天赋限制", 天赋+"使用时间"); // 读取使用日期
         //更新使用次数（当上次使用日期不等于今天时）
          if(!today.equals(lastRunDate)){
            putString(配置名+"天赋限制", 天赋, "0");
            使用记录 = 文转(getString(配置名+"天赋限制", 天赋));
          }
          
          if (道具 == null || "".equals(道具)) {
            发送(qun, "[AtQQ="+uin+"]  对方背包没有任何物品！",true);
          }else if(天赋2!=null&&天赋2.equals("虚空壁垒")){
            发送(qun,"[AtQQ="+uin+"]  偷取失败！\n对方宠物觉醒了【"+天赋2+"】，能免疫“妙手神偷”天赋效果",true);
          }else if(使用记录==次数上限&&today.equals(lastRunDate)) {
          //道具已达今日使用上限时
         发送(qun,"[AtQQ="+uin+"]   该天赋每日限制使用【"+次数上限+"】次! 今日剩余可用【0】次",true);
        }else if((使用记录+1)>次数上限&&today.equals(lastRunDate)) {
          //使用数量超出限制时
         发送(qun,"[AtQQ="+uin+"]   该天赋每日限制使用【"+次数上限+"】次! 今日剩余可用【"+(次数上限-使用记录)+"】次",true);
          }else if(当前精力>=100){
             // 确保道具列表不为空且分割后有元素
            String[] parts = 道具.split("、");
            if (道具 != null && !道具.isEmpty() && parts.length > 0) {
              // 创建随机数生成器
              Random random = new Random();
              // 生成0到parts长度-1之间的随机索引
              int randomIndex = random.nextInt(parts.length);
              // 根据随机索引获取随机道具
              String 随机道具 = parts[randomIndex];
              long 道具数量 = 文转(getString(qun+"/"+目标+"/我的背包", 随机道具));
             
               // 计算上限：道具数量的1/20（向下取整），若小于1则强制为1
               long 上限 = 道具数量 / 20;
               if (上限 < 1) { 上限 = 1;}
               // 生成1到上限（含）之间的随机数
               long 随机数量;
               if (上限 <= Integer.MAX_VALUE) {
                 // 未超过int范围时，用nextInt
                 随机数量 = random.nextInt((int)上限) + 1;
               } else {
                 // 超过int范围时，用nextLong计算
                 long range = 上限;
                 long fraction = (long)(range * random.nextDouble());
                 随机数量 = fraction + 1;
               }
               
              //扣除精力，更新天赋次数
              putString(对象, "当前精力", 转文(当前精力-100));
              putString(配置名+"天赋限制", 天赋, 转文(使用记录+1));
              putString(配置名+"天赋限制", 天赋+"使用时间", today); //记录使用日期
              //65%概率成功偷取
              if(Math.random() <= 0.65){
                //从目标背包扣除，然后添加到“我方”背包”
                deductItem(qun+"/"+目标+"/我的背包", 随机道具, 随机数量);
                
                //添加到我方背包
                String bagKey = qun + "/" + uin + "/我的背包";
                // 先获取当前道具数量（默认0）
                long current = Long.parseLong(getString(bagKey, 随机道具, "0"));
                putString(bagKey, 随机道具, String.valueOf(current + 随机数量));
   
               // 更新道具列表（仅记录存在的道具）
               String itemList = getString(bagKey, "道具列表", "");
               if (!itemList.contains(随机道具)) {
                  String newList = itemList.isEmpty() ? 随机道具 : itemList + "、" + 随机道具;
                  putString(bagKey, "道具列表", newList);
               }
                
                发送(qun,"[AtQQ="+uin+"]  你的「"+昵称+"」趁[AtQQ="+目标+"] 不注意，偷取到了【"+随机道具+"】×"+随机数量+"\n•今日次数："+(使用记录+1)+"/"+次数上限+"\n•当前精力：-100",true);
              }else{
                发送(qun,"[AtQQ="+uin+"]  【"+天赋+"】天赋触发失败！没有在[AtQQ="+目标+"] 身上偷取到道具！\n◆当前精力：-100\n◆今日次数："+(使用记录+1)+"/"+次数上限,true);
              }
              userTalentTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
             }else{
               发送(qun,"[AtQQ="+uin+"]  请给对方留点东西吧～",true);
             }
            }else if(当前精力<100){ 
            发送(qun,"[AtQQ="+uin+"]  出战宠物当前精力不足100，指令发动失败！",true);
          }
        }else if(天赋.equals("灵蕴丹成")){
          发送(qun,"[AtQQ="+uin+"]  “"+天赋+"”不能通过此指令触发！\n✦发动指令为【炼丹】",true);
        }else if(type.equals("被动")){
          发送(qun,"[AtQQ="+uin+"]  “"+天赋+"”为被动天赋，不能通过此指令触发！",true);
        }
     }else{
       发送(qun,"[AtQQ="+uin+"]   \n疑似指令出错或参数不全！\n--------------\n•指令：发动天赋@玩家",true);
     }
   }


// 灵蕴丹成的天赋指令
if(消息.startsWith("炼丹")){
     String 对象=qun+"/"+uin+"/宠物小窝/位置_0";
     String 昵称 = getString(对象, "昵称");
     String 天赋 = getString(对象, "天赋");
     String 闭关 = getString(对象, "闭关");
     String 状态 = getString(对象, "状态");
     long 等级1 = 文转(getString(对象, "等级"));
     long 心情 = 文转(getString(配置名称, "心情"));
     long 当前经验 = 文转(getString(对象, "当前经验"));
     long 当前精力 = 文转(getString(对象, "当前精力"));
     long 当前生命 = 文转(getString(对象, "当前生命"));
     long 生命上限 = 文转(getString(对象, "生命上限"));
     
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     
          
     // 指令间隔逻辑 
      int talentInterval = getInt("系统配置", "天赋间隔", 9000); 
      //默认9秒，可以适当往上增加一些
      String key = qun + "_" + uin;
      TimestampWithText lastTriggerInfo = userTalentTime.get(key);
    
      if (lastTriggerInfo != null) {
        long lastTime = lastTriggerInfo.getTimestamp();
        long now = System.currentTimeMillis();
        long remain = (lastTime + talentInterval) - now;
        
        if (remain >= 1000) { // 天赋间隔≥1秒
            发送(qun, "[AtQQ="+uin+"]  "+ " 天赋技能冷却中，距离下次发动还差[" + remain/1000 + "]秒！",true);
            return; // 直接终止后续逻辑
        }else if (remain >= 1) { // 剩余时间小于1秒（1000毫秒），但≥1毫秒
           发送(qun, "[AtQQ="+uin+"]  "+ " 天赋技能冷却中，距离下次发动还差[" + remain+"]毫秒！",true); 
           return;
        }
      }
     
     if(等级1<1) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
     }else if(等级1!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if(天赋.equals("无")){
       发送(qun,"[AtQQ="+uin+"]  您的宠物当前未觉醒天赋\n✦可发送【宠物觉醒】来觉醒天赋",true);
     }else if(!天赋.equals("灵蕴丹成")){
       发送(qun,"[AtQQ="+uin+"]  “"+天赋+"”不能通过此指令触发！",true);
     }else if(天赋.equals("灵蕴丹成")){
       long 条件1 = 500000;  //经验消耗/次
       long 条件2 = 20;  //精力消耗/次
       if(当前经验<条件1){
         发送(qun,"[AtQQ="+uin+"]   太可惜了，您的宠物经验不足,指令无法发动！\n【所需["+条件+"]经验】",true);
       }else if(当前精力<条件2){
         发送(qun,"[AtQQ="+uin+"]   太可惜了，您的宠物精力不足,指令发动失败!\n【所需["+条件2+"]精力】",true);
       }else{
         //扣除经验与精力
         当前经验 -= 条件1; 当前精力 -= 条件2;
         putString(对象, "当前精力", 转文(当前精力));
         putString(对象, "当前经验", 转文(当前经验));
          //35%概率炼丹成功
         if(Math.random() <= 0.35){
            炼丹(data,qun,uin,消息); //丹药获取方法
         }else{
           发送(qun,"[AtQQ="+uin+"] 【"+天赋+"】天赋触发失败！ \n♢精力：-"+条件2+"\n♢经验：-"+条件1,true);
         }
         //记录天赋触发时间戳
         userTalentTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       }
     }
}
 
 //神器列表
 if(消息.startsWith("神器列表")){
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       
   StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n<填充>\n");
   
   文案.append("⒈ 龙珠系列\n⒉ 星核系列\n");
   文案.append("⒊ 灵藤系列\n⒋ 玄铁系列\n");
   文案.append("⒌ 常规系列\n<填充>\n");
   文案.append("// 发送「查看+系列名」即可查看");
   toImg(文案.toString(),"",0.5,0.01,30,AppPath+"/缓存/其他/神器列表.png",false);
   发送(qun, "[PicUrl="+AppPath+"/缓存/其他/神器列表.png]",false);
 }
        
 //仙丹列表
 if(消息.startsWith("仙丹列表")){
   userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       
StringBuilder 文案 = new StringBuilder(玩家名(qun,uin)+"("+uin+")\n\n<分割>仙丹列表</分割>\n");
文案.append(" 死亡丹    //可让目标的宠物‘死亡’\n");
文案.append(" 凝神丹    //对宠物使用后，增加200点精力上限\n");
文案.append(" 回春丹    //使用后可回满宠物生命\n");
文案.append(" 破锋丹    //对宠物使用后，增加500点攻击\n");
文案.append(" 长生丹    //对宠物使用后，增加1w点生命上限\n");
文案.append(" 御体丹    //对宠物使用后，增加500点防御\n");
文案.append(" 灵智丹    //对宠物使用后，增加50点智力\n");
文案.append(" 焕能丹    //使用后可以恢复1000精力\n");
文案.append(" 聚灵丹    //对宠物使用后，增加10w点经验\n");
文案.append(" 小破锋丹    //对宠物使用后，增加50点攻击\n");
文案.append(" 小长生丹    //对宠物使用后，增加1000点生命上限\n");
文案.append(" 小御体丹    //对宠物使用后，增加50点防御\n");
文案.append(" 小灵智丹    //对宠物使用后，增加5点智力\n");
文案.append(" 小焕能丹    //使用后可以恢复100精力\n");
文案.append(" 小聚灵丹    //对宠物使用后，增加1000点经验\n<填充>\n");
文案.append("  ✦指令：使用+道具名\n  ✦示例：使用长生丹#666");
   toImg(文案.toString(),"",0.5,0.01,30,AppPath+"/缓存/其他/仙丹列表.png",false);
   发送(qun, "[PicUrl="+AppPath+"/缓存/其他/仙丹列表.png]",false);
 }

   //指令：宠物副本+数字
   if(消息.startsWith("宠物副本"))
   {
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
      宠物副本(qun,uin,消息); //从方法中返回相关内容
   }
   
  //副本查看指令
  if(消息.startsWith("副本查看")){
    userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    副本查看(qun,uin,消息); //从方法中返回副本相关信息
  }
      

//限时开放的副本，用于获取经验道具或充值道具等
if(消息.startsWith("限时副本")){
  userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  限时副本(qun,uin,消息);
}

//挑战限时副本
if (消息.startsWith("挑战")) {
  userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  挑战副本(qun,uin,消息);
}
    
//进入副本
if (消息.startsWith("进入")) {
  userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  进入副本(qun,uin,消息);
}
    
  
//查看从加载脚本时到现在的时长
if(消息.equals("运行时长")){
  long 初始=加载时间戳;
  long 现在=System.currentTimeMillis();
  long 时长=现在-初始;
  
  // 定义时间格式（如：年-月-日 时:分:秒）
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8")); // 设定北京时间
        
  // 转换初始时间戳为日期时间
  Date 初始Date = new Date(初始);
  String 初始时间 = sdf.format(初始Date);
        
  // 转换现在时间戳为日期时间
  Date 现在Date = new Date(现在);
  String 现在时间 = sdf.format(现在Date);
   
  // 组装内容 并绘制为图片后发送到当前群   
  String 内容=玩家名(qun,uin)+" ("+uin+")\n\n►加载时间："+初始时间+"\n►现在时间："+现在时间+"\n<填充>\n 脚本《"+NAME+"》；\n已运行*"+formatTime(时长)+"*啦！";
   toImg(内容, "", 0.5, 0.02, 35, AppPath + "/缓存/其他/运行时长.png",true);
   发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/运行时长.png]", false);
}

//查看当前出战宠物图片
if(消息.startsWith("查宠物图")){
  userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  long 等级 = 文转(getString(配置名称, "等级"));
  String 状态 = getString(配置名称, "状态");
  String 描述;
  if (状态 == null ||等级 <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + " 您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连", true);
    } else {
       String roleImagePath = AppPath + "/目录/图片/其他/默认.png"; 
        String 昵称 = getString(配置名称, "昵称");
        String 名字 = getString(配置名称, "宠物名字");
       String petImagePath =AppPath+"/目录/图片/宠物/"+名字+".jpg";
       File file = new File(petImagePath);
        if (file.exists()) {
            roleImagePath = petImagePath;
            描述="（宠物原图）";
        }

        String 级别 = getString(配置名称, "级别");
        if(级别.equals("定制")){
         String 定制图 = AppPath + "/目录/图片/宠物/定制/" + 名字 + ".jpg";
          File file = new File(定制图);
          if (file.exists()) {
            roleImagePath = 定制图;
            描述="（宠物原图（DIY））";
          }
        }
        
       
        String 易容 = getString(配置名称, "是否易容");
        String 上传图 = getString(配置名称, "上传图片");
        if(易容.equals("是")){
         roleImagePath = AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png";
         描述="（易容效果）";
        }
        String 本名 = getString(配置名称, "宠物名字");
       sendReply(qun, data, "[PicUrl="+roleImagePath+"]"+"宠物名称："+本名+"\n"+描述);
    }
}

//查看该账号的宠物背景图片（宠物背景卡）
if(消息.startsWith("查背景图")){
  userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       String BgImagePath = AppPath + "/目录/图片/其他/宠物背景.png"; 
       String DiyBgPath =AppPath+"/目录/图片/其他/玩家上传/"+uin+"/宠物背景图.png";
       String 描述;
       File file = new File(DiyBgPath);
        if (file.exists()) {
            BgImagePath = DiyBgPath;
            描述="（自定义背景图）";
        }else{
          描述="（默认背景图）";
        }
     sendReply(qun, data, "[PicUrl="+BgImagePath+"]"+描述);
}

//返回可能存在的相关指令
if(消息.equals("查")){
userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  发送(qun,"[AtQQ="+uin+"]  \n你是否想发送以下指令？\n•1.查看+道具名称\n•2.查询+道具名称\n•3.副本查看+副本名称\n•4.查宠物图\n•5.查背景图\n•6.查道具+(分解/合成/转让/使用)",true);
}


 //领榜指令不符，给出提示
if(消息.startsWith("领战榜奖励")||消息.startsWith("领神榜奖励")){
   if(宠物名==null||宠物名.isEmpty()){
     发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
   }else{
    String 消息=消息.substring(0,5);
     发送(qun,"[AtQQ="+uin+"]   该指令不适配！\n〔"+消息+"〕在这里没用哦，毕竟这边不是一整天都在运行。\n\n如果着急领战榜或神榜奖励，可以找「GM列表」内的人发送〔双榜结算〕进行结算",true);
   }
}
    
// 银行系统相关指令处理逻辑
if(消息.startsWith("银行系统")||消息.startsWith("钱庄系统")){
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  StringBuilder 文案 = new StringBuilder("<分割>"+消息+"</分割>\n"); 
  文案.append("•：存款#数量\n");
  文案.append("•：取款#数量\n");
  文案.append("•：转账#账号#数量\n");
  文案.append("•：我的余额\n");
  文案.append("•：操作明细\n");
  文案.append("•：全服存款统计\n");
  文案.append("•：设置利率#利率\n");
  文案.append("•：冻结账户#账号\n<填充>");
  toImg(文案.toString()+"\n//存入货币类型：金币\n//支持跨群存取（指可在其他群存/取）","",0.5,0.01,35,AppPath+"/缓存/其他/银行系统.png",false);
  发送(qun,"[PicUrl="+AppPath+"/缓存/其他/银行系统.png]",false);
}
     if (消息.startsWith("存款#")) {
        handleDeposit(qun, uin, 消息);
        // 记录用户触发时间
       userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
     if (消息.startsWith("取款#")) {
        handleWithdraw(qun, uin, 消息);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
     if (消息.equals("我的余额")) {
        handleCheckBalance(qun, uin);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
     if (消息.startsWith("转账#")) {
        handleTransfer(qun, uin, 消息);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
     if (消息.equals("操作明细")) {
        handleCheckRecords(qun, uin);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    } 
     if (消息.startsWith("全服存款统计")) {
        handleGMCountTotal(qun, uin, 消息);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }

//礼包系统相关指令
if(消息.startsWith("礼包系统")){
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  StringBuilder 文案 = new StringBuilder("<分割>礼包系统</分割>\n"); 
  文案.append("•：我抢<始>抢夺当前存在的礼包<终>\n");
  文案.append("•：礼包详情<始>查看群礼包领取详情<终>\n");
  文案.append("•：礼包列表<始>查看礼包列表<终>\n");
  文案.append("•：发放礼包<始>发放礼包到本群<终>\n");
  文案.append("•：删除礼包<始>删除指定礼包<终>\n");
  文案.append("•：重置礼包<始>重置礼包数量<终>\n<填充>");
  toImg(文案.toString()+"\n//部分指令仅GM可用","#181403",0.5,0.01,35,AppPath+"/缓存/其他/礼包系统.png",false);
  发送(qun,"[PicUrl="+AppPath+"/缓存/其他/礼包系统.png]",false);
}

//查看预设的礼包列表
if(消息.startsWith("礼包列表")){
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  String 礼包列表="新手礼包、基础资源、万事如意、破茧成蝶";  //礼包名称
  String[] 礼包名称列表 = 礼包列表.split("、");
    StringBuilder 礼包信息 = new StringBuilder(玩家名(qun,uin)+ " ("+uin+")\n\n<分割>礼包ღ列表</分割>\n〖礼包名称〗<起>✦领取状态<尾>\n\n");
    for (String 礼包名称 : 礼包名称列表) {
     String 状态名称="领"+礼包名称;
     boolean 状态 = getBoolean(配置名 + "状态", 状态名称, false);
     String 领取显示;
     if(状态==true){领取显示="✔";}else{领取显示="✘";}
        礼包信息.append("〖 ").append(礼包名称).append("〗");
        礼包信息.append(" <起>•领取：").append(领取显示).append("<尾>");
        礼包信息.append("\n");
    }
    
    toImg(礼包信息.toString()+"<填充>\n •领取：*领+礼包名称*\n •示例：领新手礼包","",0.5,0.01,35,AppPath+"/缓存/其他/礼包列表.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/礼包列表.png]" , false);
}

//“领+礼包名称”指令，示例：领新手礼包
if(消息.startsWith("领")&&!消息.contains("战榜奖励")&&!消息.contains("神榜奖励")){
  String one=消息.substring(1).replaceAll("\\s","");
  if(one.length()>=1){
    if(one.equals("新手礼包")){
      long 积分变化=2000000;
      long 金币变化=3000;
      String 状态名称="领"+one.trim();
      boolean 状态 = getBoolean(配置名 + "状态", 状态名称, false);
      if(状态==false){
        // 定义道具数组（包含名称和数量）
        String[][] itemArray = {
         {"复活石,1000"},{"往生花,100"},{"万灵药,20000"},
         {"小精力药,200000"},{"中精力药,150000"},{"大精力药,75000"},
         {"超级精力药,2000"},{"1000金币,20"},{"信标,50"},
         {"百亿积分卡,200"},{"经验丹,2000"},{"开心果,1000"},
         {"小窝激活卡,1"},{"小窝进阶卡,15"},{"涅槃,2"},
         {"吞噬卡,2"},{"积分夺宝券,20"},{"金币夺宝券,20"},
         {"通用夺宝券,50"},{"10软妹币,5"},{"补偿礼包,1"}
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
           formattedItem.append("\n"+itemName).append("×").append(数值转(文转(itemCount)));        
           // 判断背包中是否已存在该道具
           if (bag != null && bag.contains(itemName)) {
             // 存在则累加数量
             long count = 文转(getString(bagKey, itemName));
             putString(bagKey, itemName, 转文(count + Long.parseLong(itemCount)));
           } else {
              // 不存在则添加新道具
              String newBag = (bag == null ? "" : bag + "、") + itemName;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, itemName, itemCount);
           }
         }
        }
         putString(配置名 + "/我的资产", "积分", 转文(积分+积分变化));
         putString(配置名 + "/我的资产", "金币", 转文(金币+金币变化));
         putBoolean(配置名 + "状态", 状态名称, true); //更新领取状态
         
         //领取详情
         StringBuilder result = new StringBuilder();
         result.append(" \n成功领取「"+one+"」礼包！\n获得积分："+数值转(积分变化)+"\n获得金币："+数值转(金币变化));
         result.append("\n————————\n获得道具："+formattedItem.toString());
         新玩家(qun,uin); //记录新玩家
         发送(qun, 玩家名(qun,uin)+ result.toString(),false);
      } else {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你已领取过〔"+one+"〕礼包，不可重复领取！",true);
      }
    }
    else if(one.equals("基础资源")){
      long 积分变化=200000;
      long 金币变化=300;
      String 状态名称="领"+one.trim();
      boolean 状态 = getBoolean(配置名 + "状态", 状态名称, false);
      if(状态==false){
        // 定义道具数组（包含名称和数量）
        String[][] itemArray = {
         {"复活石,10000"},{"往生花,1000"},{"小精力药,2000000"},
         {"中精力药,1500000"},{"大精力药,750000"},
         {"超级精力药,20000"},{"百亿积分卡,200"},{"经验丹,2000"},
         {"小窝进阶卡,15"},{"龙珠,90000"},{"星核,90000"},
         {"灵藤,90000"},{"玄铁,90000"},{"凝神丹,150"}
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
           formattedItem.append("\n"+itemName).append("×").append(数值转(文转(itemCount)));        
           // 判断背包中是否已存在该道具
           if (bag != null && bag.contains(itemName)) {
             // 存在则累加数量
             long count = 文转(getString(bagKey, itemName));
             putString(bagKey, itemName, 转文(count + Long.parseLong(itemCount)));
           } else {
              // 不存在则添加新道具
              String newBag = (bag == null ? "" : bag + "、") + itemName;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, itemName, itemCount);
           }
         }
        }
         putString(配置名 + "/我的资产", "积分", 转文(积分+积分变化));
         putString(配置名 + "/我的资产", "金币", 转文(金币+金币变化));
         putBoolean(配置名 + "状态", 状态名称, true); //更新领取状态
         
         //领取详情
         StringBuilder result = new StringBuilder();
         result.append(" \n成功领取「"+one+"」礼包！\n获得积分："+数值转(积分变化)+"\n获得金币："+数值转(金币变化));
         result.append("\n————————\n获得道具："+formattedItem.toString());
         新玩家(qun,uin); //记录新玩家
         发送(qun, 玩家名(qun,uin)+ result.toString(),false);
      } else {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你已领取过〔"+one+"〕礼包，不可重复领取！",true);
      }
    }
    else if(one.equals("万事如意")){
      long 积分变化=200000;
      long 金币变化=30000;
      String 状态名称="领"+one.trim();
      boolean 状态 = getBoolean(配置名 + "状态", 状态名称, false);
      if(状态==false){
        // 定义道具数组（包含名称和数量）
        String[][] itemArray = {
         {"生命之源,1000"},{"涅槃,50"},{"吞噬卡,30"},
         {"长生丹,999999"},{"御体丹,999999"},
         {"破锋丹,999999"},{"聚灵丹,999999"},
         {"经验丹,100000"},{"凝神丹,150"}
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
           formattedItem.append("\n"+itemName).append("×").append(数值转(文转(itemCount)));        
           // 判断背包中是否已存在该道具
           if (bag != null && bag.contains(itemName)) {
             // 存在则累加数量
             long count = 文转(getString(bagKey, itemName));
             putString(bagKey, itemName, 转文(count + Long.parseLong(itemCount)));
           } else {
              // 不存在则添加新道具
              String newBag = (bag == null ? "" : bag + "、") + itemName;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, itemName, itemCount);
           }
         }
        }
         putString(配置名 + "/我的资产", "积分", 转文(积分+积分变化));
         putString(配置名 + "/我的资产", "金币", 转文(金币+金币变化));
         putBoolean(配置名 + "状态", 状态名称, true); //更新领取状态
         
         //领取详情
         StringBuilder result = new StringBuilder();
         result.append(" \n成功领取「"+one+"」礼包！\n获得积分："+数值转(积分变化)+"\n获得金币："+数值转(金币变化));
         result.append("\n————————\n获得道具："+formattedItem.toString());
         新玩家(qun,uin); //记录新玩家
         发送(qun, 玩家名(qun,uin)+ result.toString(),false);
      } else {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你已领取过〔"+one+"〕礼包，不可重复领取！",true);
      }
    }
    else if(one.equals("破茧成蝶")){
      long 积分变化=200000;
      long 金币变化=300000;
      String 状态名称="领"+one.trim();
      boolean 状态 = getBoolean(配置名 + "状态", 状态名称, false);
      if(状态==false){
        // 定义道具数组（包含名称和数量）
        String[][] itemArray = {
         {"生命之源,50"},{"轮回玉,5"},{"溯天玉,1"},
         {"金蝉,99"},{"终焉织者,1"},{"信标,100"}
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
           formattedItem.append("\n"+itemName).append("×").append(数值转(文转(itemCount)));        
           // 判断背包中是否已存在该道具
           if (bag != null && bag.contains(itemName)) {
             // 存在则累加数量
             long count = 文转(getString(bagKey, itemName));
             putString(bagKey, itemName, 转文(count + Long.parseLong(itemCount)));
           } else {
              // 不存在则添加新道具
              String newBag = (bag == null ? "" : bag + "、") + itemName;
              putString(bagKey, "道具列表", newBag);
              putString(bagKey, itemName, itemCount);
           }
         }
        }
         putString(配置名 + "/我的资产", "积分", 转文(积分+积分变化));
         putString(配置名 + "/我的资产", "金币", 转文(金币+金币变化));
         putBoolean(配置名 + "状态", 状态名称, true); //更新领取状态
         
         //领取详情
         StringBuilder result = new StringBuilder();
         result.append(" \n成功领取「"+one+"」礼包！\n获得积分："+数值转(积分变化)+"\n获得金币："+数值转(金币变化));
         result.append("\n————————\n获得道具："+formattedItem.toString());
         新玩家(qun,uin); //记录新玩家
         发送(qun, 玩家名(qun,uin)+ result.toString(),false);
      } else {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你已领取过〔"+one+"〕礼包，不可重复领取！",true);
      }
    }else{
      发送(qun,"[AtQQ="+uin+"]  领取失败！\n“"+one+"”礼包不在「礼包列表」内！\n————————\n•指令：领+礼包名称\n\n•发送[礼包列表]查看礼包",false);
    }
  }else if(消息.equals("领")){
    发送(qun,"[AtQQ="+uin+"]  疑似指令格式错误!\n————————\n•指令：领+礼包名称\n\n•发送[礼包列表]查看礼包",false);
  }
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}

//查看礼包详情指令（本群发放礼包领取状态）
if (消息.startsWith("礼包详情")) { // 处理查看礼包指令

   userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 

    String 礼包列表键 = getString("发放礼包/"+qun+"/礼包发放列表", "礼包列表");
    
    if (礼包列表键==null||礼包列表键.trim().isEmpty()||礼包列表键.equals("")) {
        发送(qun, "[AtQQ=" + uin + "]  " + "本群暂无可领取的礼包！", true);
        return;
    }
  
     String[] 礼包名称列表 = 礼包列表键.split("、");
    StringBuilder 礼包信息 = new StringBuilder(玩家名(qun,uin)+ " ("+uin+")\n\n<分割>发放礼包列表（"+礼包名称列表.length+"种）</分割>\n");
    for (String 礼包名称 : 礼包名称列表) {
        if (礼包名称.equals("道具列表") || 礼包名称.equals("剩余数量") || 礼包名称.equals("创建时间")) {
            continue; // 跳过非礼包名称的键
        }
        
        String 道具列表 = getString("发放礼包/"+qun+"/"+礼包名称, "道具列表");
        String 剩余数量 = getString("发放礼包/"+qun+"/"+礼包名称, "剩余数量");
        String 创建时间 = getString("发放礼包/"+qun+"/"+礼包名称, "创建时间");
        
        String 已领取记录键 = "发放礼包/"+qun+"/领取状态/"+uin+"/"+礼包名称;
        String 已领取次数_str = getString(已领取记录键, "次数");
        int 已领取次数 = 已领取次数_str == null ? 0 : 文转(已领取次数_str);
        String 礼包键 = "发放礼包/"+qun+"/"+礼包名称;
        String 可领取次数_str = getString(礼包键, "可领取次数");
        int 可领取次数 = 文转(可领取次数_str);
        
        if (道具列表 == null || 剩余数量 == null) {
            continue; // 跳过数据不完整的礼包
        }
        String 道具详情=道具列表.replaceAll("#","×").replaceAll(",","、");
        礼包信息.append("★ *").append(礼包名称).append("* ★\n");
        礼包信息.append("  道具列表：").append(formatPropDetails(道具详情,2)).append("\n");
        礼包信息.append("  剩余数量：").append(剩余数量).append("份  （*领取状态："+已领取次数+" / "+可领取次数+"*）\n");
        礼包信息.append("  创建时间：").append(创建时间);
        礼包信息.append("\n<填充>\n");
    }
    
    toImg(礼包信息.toString()+"\n// ✦：可发送「我抢」进行抢夺！\n// ✦：可发送「礼包系统」查看指令\n// 群聊："+getGroupName(qun),"",0.5,0.01,35,AppPath+"/缓存/其他/礼包详情.png",false);
    发送(qun, "[PicUrl="+AppPath+"/缓存/其他/礼包详情.png]" , false);
}

// 抢夺本群已发放的礼包
if (消息.equals("我抢")) {
    // 记录用户触发时间
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 

    // 获取礼包发放列表
    String 礼包列表键 = getString("发放礼包/" + qun + "/礼包发放列表", "礼包列表");
    if (礼包列表键 == null || 礼包列表键.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "]  " + "本群暂无可领取的礼包！", true);
        return;
    }

    // 遍历筛选有效礼包
    String[] 原始礼包列表 = 礼包列表键.split("、");
    List 有效礼包列表 = new ArrayList();

    for (String 礼包名称 : 原始礼包列表) {
        // 1. 检测礼包可领取次数
        String 礼包键 = "发放礼包/" + qun + "/" + 礼包名称;
        String 可领取次数_str = getString(礼包键, "可领取次数");
        if (可领取次数_str == null) continue; // 数据异常，跳过

        int 可领取次数 = 文转(可领取次数_str);

        // 2. 检测玩家已领取次数
        String 已领取记录键 = "发放礼包/" + qun + "/领取状态/" + uin + "/" + 礼包名称;
        String 已领取次数_str = getString(已领取记录键, "次数");
        int 已领取次数 = 已领取次数_str == null ? 0 : 文转(已领取次数_str);

        // 3. 检测礼包剩余数量
        String 剩余数量_str = getString(礼包键, "剩余数量");
        long 剩余数量 = 剩余数量_str == null ? 0 : 文转(剩余数量_str);

        // 筛选条件：可领取次数 > 已领取次数 且 剩余数量 >= 1
        if (可领取次数 > 已领取次数 && 剩余数量 >= 1) {
            有效礼包列表.add(礼包名称);
        }
    }

    // 无有效礼包时的处理
    if (有效礼包列表.isEmpty()) {
        发送(qun, "[AtQQ=" + uin + "]  " + "所有发放的礼包已领完或你领取次数已用光！", true);
        return;
    }

    // 从有效礼包中随机选择一个
    int randomIndex = new Random().nextInt(有效礼包列表.size());
    String 礼包名称 = 有效礼包列表.get(randomIndex);

    // 获取礼包详细信息
    String 礼包键 = "发放礼包/" + qun + "/" + 礼包名称;
    String 剩余数量_str = getString(礼包键, "剩余数量");
    String 道具列表 = getString(礼包键, "道具列表");

    if (剩余数量_str == null || 道具列表 == null) {
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包数据异常，无法领取！", true);
        return;
    }

    long 剩余数量 = 文转(剩余数量_str);
    if (剩余数量 <= 0) {
        // 剩余数量为0时移除礼包
        礼包列表键 = 礼包列表键.replace(礼包名称 + "、", "").replace("、" + 礼包名称, "").replace(礼包名称, "");
        putString("发放礼包/" + qun + "/礼包发放列表", "礼包列表", 礼包列表键);
        删除文件(缓存路径 + "发放礼包/" + qun + "/" + 礼包名称);
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包【" + 礼包名称 + "】已被领完！", true);
        return;
    }

    // 解析道具列表（提前拆分，方便多次随机）
    String[] 道具项 = 道具列表.split(",");
    if (道具项.length == 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "礼包【" + 礼包名称 + "】中没有可领取的道具！", true);
        return;
    }

    // 计算用户剩余可领取次数
    String 可领取次数_str = getString(礼包键, "可领取次数");
    int 可领取次数 = 文转(可领取次数_str);
    String 已领取记录键 = "发放礼包/" + qun + "/领取状态/" + uin + "/" + 礼包名称;
    String 已领取次数_str = getString(已领取记录键, "次数");
    int 已领取次数 = 已领取次数_str == null ? 0 : 文转(已领取次数_str);
    int 剩余可领取次数 = 可领取次数 - 已领取次数;

    // 计算实际领取次数（取剩余可领取次数和礼包剩余数量的最小值）
    int 实际领取次数 = Math.min(剩余可领取次数, (int)剩余数量);
    if (实际领取次数 <= 0) {
        发送(qun, "[AtQQ=" + uin + "]  " + "无法领取，剩余可领取次数或礼包数量不足！", true);
        return;
    }

    // 执行一次性领取逻辑（每次领取随机选道具）
    StringBuilder 领取详情 = new StringBuilder();
    Random random = new Random(); // 复用随机对象，提高效率
    for (int i = 0; i < 实际领取次数; i++) {
        // 每次领取都随机选择一个道具（核心改进点）
        String 随机道具 = 道具项[random.nextInt(道具项.length)];
        String[] 道具信息 = 随机道具.split("#", 2);
        if (道具信息.length != 2) {
            领取详情.append("★第").append(i + 1).append("次：道具数据异常，领取失败\n");
            continue; // 跳过异常道具，继续下一次
        }
        String 道具名 = 道具信息[0].trim();

        // 检查该道具是否还有剩余（避免单个道具被领完的情况）
        String 道具总数量_str = getString(礼包键, 道具名);
        if (道具总数量_str == null || 文转(道具总数量_str) <= 0) {
            领取详情.append("★第").append(i + 1).append("次：道具【").append(道具名).append("】已发放完毕\n");
            continue;
        }

        // 计算单次领取数量范围（确保最小值为1）
        long totalCount = 文转(道具总数量_str);
        long minCount = Math.max(totalCount / 10, 1);
        long maxCount = Math.max(totalCount / 8, 1);
        if (maxCount < minCount) maxCount = minCount;
        long 单次领取数量 = minCount + random.nextInt((int) (maxCount - minCount + 1));

        // 记录领取日志
        String logKey = "发放礼包/" + qun + "/礼包领取日志";
        String 日志内容 = "【" + getTodayDate(3) + "】\n" +
                          "玩家[" + uin + "]通过\"我抢\"指令，从礼包「" + 礼包名称 + "」抢夺到" +
                          道具名 + "×" + 单次领取数量 + "\n";
        String 现有日志 = getString(logKey, "日志");
        putString(logKey, "日志", (现有日志 == null ? "" : 现有日志 + "\n") + 日志内容);

        // 发放道具到背包
        String bagKey = qun + "/" + uin + "/我的背包";
        long currentCount = 文转(getString(bagKey, 道具名));
        putString(bagKey, 道具名, 转文((currentCount > 0 ? currentCount : 0) + 单次领取数量));

        // 首次获得该道具时，添加到背包列表
        if (currentCount <= 0) {
            String bag = getString(bagKey, "道具列表");
            putString(bagKey, "道具列表", (bag == null ? "" : bag + "、") + 道具名);
        }

        // 记录单次领取详情
        领取详情.append("★第").append(i + 1).append("次：").append(道具名).append("×").append(单次领取数量).append("\n");

        // 减少该道具的总数量（避免无限领取）
        putString(礼包键, 道具名, 转文(totalCount - 单次领取数量));
    }

    // 更新数据
    // 1. 更新礼包剩余数量
    putString(礼包键, "剩余数量", 转文(剩余数量 - 实际领取次数));
    // 2. 更新用户已领取次数
    putString(已领取记录键, "次数", 转文(已领取次数 + 实际领取次数));
    // 3. 首次领取时记录礼包到用户领取列表
    String 已领取礼包键 = "发放礼包/" + qun + "/领取状态/" + uin;
    String 已领取礼包 = getString(已领取礼包键, "礼包列表");
    if (已领取次数 == 0) {
        putString(已领取礼包键, "礼包列表", (已领取礼包 == null ? "" : 已领取礼包 + "、") + 礼包名称);
    }

    // 发送领取结果
    String 道具领取详情=玩家名(qun,uin)+" ("+uin+")\n●您成功领取 " + 实际领取次数 + " 次「*" + 礼包名称 + "*」礼包！\n<填充>\n" +领取详情.toString() +"\n<填充>\n●礼包剩余数量：" + (剩余数量 - 实际领取次数) + "份\n●剩余可领次数：" + (剩余可领取次数 - 实际领取次数) + "次";
    toImg(道具领取详情,"",0.5,0.01,35,AppPath+"/缓存/其他/"+uin+"_领取详情.png",false);
    发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"_领取详情.png]",false);
}

        
    //查看GM列表
    if(消息.equals("GM列表")||消息.equals("gm列表")||消息.equals("管理列表")){
       if (Owner == null || Owner.length == 0) {
         发送(qun,"[AtQQ="+uin+"]  "+消息+"为空！",true);
       }else{
        // 利用Set去重（自动去重且保证唯一性）
        Set uniqueSet = new LinkedHashSet(Arrays.asList(Owner)); 
        List member = new ArrayList(uniqueSet); // 转换为List便于后续操作
      
        Collections.shuffle(member); // 打乱顺序        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < member.size(); i++) {
            sb.append("•"+(i + 1) + "：@" +昵称(member.get(i)) +" ("+ member.get(i)+")");
            if (i != member.size() - 1) {
                sb.append("\n");
            }
        }
        String text="<分割>GM列表（共"+member.size()+"位）</分割>\n"+sb.toString()+"\n<填充>\n//请自行辨别是否为可用账号喔";
        toImg(text,"",0.5,0.03,32,AppPath+"/缓存/其他/GMList.png",false);
        发送(qun,"[AtQQ="+uin+"]   [PicUrl="+AppPath+"/缓存/其他/GMList.png]",true);
      }
         userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
    
  if(消息.startsWith("GM指令")||消息.startsWith("管理指令")||消息.startsWith("gm指令")){
     String 内容;
     if(权限等级==1){内容="[AtQQ="+uin+"]   你拥有【GM】权限";}
     else{内容="[AtQQ="+uin+"]   你是【玩家】";}
      String ImagePath = AppPath + "/目录/图片/其他/gm指令.jpg";
       发送(qun,内容+"[PicUrl="+ImagePath+"]",false);
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
  }
    
      if(消息.equals("帮助")||消息.startsWith("菜单")||消息.startsWith("宠物大冒险")||消息.startsWith("宠物世界")){
      String ImagePath = AppPath + "/目录/图片/其他/宠物世界.jpg";
       发送(qun,"[PicUrl="+ImagePath+"]",false);
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
  }
    
    //查询 战榜与神榜 的结算记录
    if(消息.equals("双榜记录")||消息.equals("双榜结算记录")){
      String 记录 = getString("系统配置", "发放记录");
      if (记录 != null) {
        String[] 日期列表 = 记录.split("、");  
        List Record = new ArrayList(); //暂时存储
        for (String 日期 : 日期列表) {  
          Record.add(日期); // 遍历添加日期到列表中
        }
        RewardRecord.clear(); //先清空原始列表
        RewardRecord.addAll(Record); //从Record内获取并添加所有元素到…
        
        // 然后对列表进行自然排序
        Collections.sort(RewardRecord);
        
        // 按顺序拼接元素为字符串，用指定符号分隔（当前用"\n"分隔）
        StringBuilder sb = new StringBuilder();
        String today = getTodayDate(1); // 获取今天日期
        for (int i = 0; i < RewardRecord.size(); i++) {
          String date = RewardRecord.get(i).toString();
          sb.append("(" + (i + 1) + ")：").append(date);
          if(date.equals(today)) { // 判断是否为今天
            sb.append("〔*今日*〕"); // 标记
          } 
          if(i != RewardRecord.size() - 1) { // 非最后元素，都加上"\n"
            sb.append("\n");
          }
        }
        String result = sb.toString(); 
        String textMsg="//仅保留30条记录，超出则重新记录\n<分割>奖励结算记录</分割>\n"+result+"\n<填充>\n// 注：括号内数字为序号！\n☆指令：*战榜+序号*\n☆示例：战榜"+随机数(1,RewardRecord.size())+"\n\n★指令：*神榜+序号*\n★示例：神榜"+随机数(1,RewardRecord.size());
        toImg(textMsg,"",0.5,0.02,32,AppPath+"/缓存/其他/"+uin+"_结算记录.png",false);
        发送(qun,"[AtQQ="+uin+"]   [PicUrl="+AppPath+"/缓存/其他/"+uin+"_结算记录.png]",true);
      }else{
        // 利用Set去重（自动去重且保证唯一性）
        Set uniqueSet = new LinkedHashSet(Arrays.asList(Owner)); 
        List member = new ArrayList(uniqueSet); // 转换为List便于后续操作
      
        Collections.shuffle(member); // 打乱顺序        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < member.size(); i++) {
            sb.append("("+(i + 1) + ")：" + member.get(i));
            if (i != member.size() - 1) {
                sb.append("\n");
            }
        }
        String text="<分割>GM列表（共"+member.size()+"位）</分割>\n"+sb.toString()+"\n<填充>\n// 请自行辨别是否为可用账号喔";
        toImg(text,"",0.5,0.03,32,AppPath+"/缓存/其他/GMList.png",false);
        发送(qun,"[AtQQ="+uin+"]   \n😲目前还没有奖励结算记录哦！[PicUrl="+AppPath+"/缓存/其他/GMList.png]Ţ可以找GM来发送【双榜结算】来发放奖励并更新记录～",false);
      }
        userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
    }
    
    //查询 战榜奖励记录（一般是按日期与群号来获取）
    if(消息.startsWith("战榜"))
    {
      String 记录 = getString("系统配置", "发放记录");
      if (记录 == null) {
         // 利用Set去重（自动去重且保证唯一性）
        Set uniqueSet = new LinkedHashSet(Arrays.asList(Owner)); 
        List member = new ArrayList(uniqueSet); // 转换为List便于后续操作
      
        Collections.shuffle(member); // 打乱顺序        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < member.size(); i++) {
            sb.append("("+(i + 1) + ")：" + member.get(i));
            if (i != member.size() - 1) {
                sb.append("\n");
            }
        }
        String text="<分割>GM列表（共"+member.size()+"位）</分割>\n"+sb.toString()+"\n<填充>\n// 请自行辨别是否为可用账号喔";
        toImg(text,"",0.5,0.03,32,AppPath+"/缓存/其他/GMList.png",false);
        发送(qun,"[AtQQ="+uin+"]   \n😲目前还没有奖励结算记录哦！[PicUrl="+AppPath+"/缓存/其他/GMList.png]Ţ可以找GM来发送【双榜结算】来发放奖励并更新记录～",true);
      }else{
        String[] 日期列表 = 记录.split("、");  
        List Record = new ArrayList(); //暂时存储
        for (String 日期 : 日期列表) {  
          Record.add(日期); // 遍历添加日期到列表中
        }
        RewardRecord.clear(); //先清空原始列表
        RewardRecord.addAll(Record); 
        //从Record内获取并添加所有元素到RewardRecord
        
        Collections.sort(RewardRecord); //对列表进行自然排序
        String str=消息.substring(2); //截取"战榜"两字之后的内容
        //当字数长度为0时 或 不是纯数字时 提示↓
        if(!isLetterDigitOrChinese(str)||str.length()==0)
        {  
           发送(qun,"[AtQQ="+uin+"]   指令格式错误\n•指令：战榜+序号\n•示例：战榜"+随机数(1,RewardRecord.size()),true);
        }else{  
          int number=RewardRecord.size(); //列表元素数量
          int a1=Integer.parseInt(str);//string转int.
          int result = a1 - 1; // 减1操作
          //在{数字＞列表内元素数量}或{数字-1 < 0}时提示
          if(a1 > number || result<0){
            发送(qun,"[AtQQ="+uin+"]   \n序号范围在：1～"+number+"\n\n示例：战榜"+随机数(1,number),true);
          }else{
            String Element = (String) RewardRecord.get(result); 
            //↑获取列表中对应日期
            String 群记录=getString("双榜结算记录/"+Element+"/"+qun+ "_战榜奖励记录","奖励记录");          
            if (群记录 == null) {
              发送(qun,"[AtQQ="+uin+"]   \n本群没有{"+Element+"}的奖励结算记录！",true);
            }else{
              String msg = "//群聊："+getGroupName(qun)+"（结算记录源自："+Element+" ）\n<分割>☼战榜奖励记录☼</分割>\n" + 群记录+"\n//温馨提示：这是之前的奖励结算记录，不会再次发放奖励哦";
              toImg(msg,"",0.5,0.01,32,AppPath+"/缓存/其他/"+qun + "_"+Element+"_战榜奖励.png",true); //绘制为图片
              发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+qun + "_"+Element+"_战榜奖励.png]",false);
            }          
          }
        }
      }
        userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    }
    
    
    //查询 神榜奖励记录（一般是按日期来获取）
    if(消息.startsWith("神榜"))
    {
      String 记录 = getString("系统配置", "发放记录");
      if (记录 == null) {
         // 利用Set去重（自动去重且保证唯一性）
        Set uniqueSet = new LinkedHashSet(Arrays.asList(Owner)); 
        List member = new ArrayList(uniqueSet); // 转换为List便于后续操作
      
        Collections.shuffle(member); // 打乱顺序        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < member.size(); i++) {
            sb.append("("+(i + 1) + ")：" + member.get(i));
            if (i != member.size() - 1) {
                sb.append("\n");
            }
        }
        String text="<分割>GM列表（共"+member.size()+"位）</分割>\n"+sb.toString()+"\n<填充>\n// 请自行辨别是否为可用账号喔";
        toImg(text,"",0.5,0.03,32,AppPath+"/缓存/其他/GMList.png",false);
        发送(qun,"[AtQQ="+uin+"]   \n😲目前还没有奖励结算记录哦！[PicUrl="+AppPath+"/缓存/其他/GMList.png]Ţ可以找GM来发送【双榜结算】来发放奖励并更新记录～",true);
      }else{
        String[] 日期列表 = 记录.split("、");  
        List Record = new ArrayList(); //暂时存储
        for (String 日期 : 日期列表) {  
          Record.add(日期); // 遍历添加日期到列表中
        }
        RewardRecord.clear(); //先清空原始列表
        RewardRecord.addAll(Record); 
        //从Record内获取并添加所有元素到RewardRecord
        
        Collections.sort(RewardRecord); //对列表进行自然排序
        String str=消息.substring(2); //截取"神榜"两字之后的内容
        //当字数长度为0时 或 不是纯数字时 提示↓
        if(!isLetterDigitOrChinese(str)||str.length()==0)
        {  
           发送(qun,"[AtQQ="+uin+"]   指令格式错误\n•指令：神榜+序号\n•示例：神榜"+随机数(1,RewardRecord.size()),true);
        }else{  
          int number=RewardRecord.size(); //列表元素数量
          int a1=Integer.parseInt(str);//string转int.
          int result = a1 - 1; // 减1操作
          if(a1 > number || result<0){
            发送(qun,"[AtQQ="+uin+"]   \n序号范围在：1～"+number+"\n\n示例：神榜"+随机数(1,number),true);
          }else{
            String Element = (String) RewardRecord.get(result); 
            //↑获取列表中对应日期
            String 旧记录=getString("双榜结算记录/"+Element+"/神榜奖励记录","奖励记录");
            if (旧记录 == null) {
              发送(qun,"[AtQQ="+uin+"]   \n没有{"+Element+"}的神榜结算记录！",true);
            }else{
              String msg = "// 结算记录源自："+Element+"（战力与奖励都是在这一天记录的）\n<分割>☼神榜奖励记录☼</分割>\n" + 旧记录+"\n// 温馨提示：这是之前的奖励结算记录，不会再次发放奖励哦";
              toImg(msg,"",0.5,0.01,32,AppPath+"/缓存/其他/"+Element+"_神榜奖励.png",true); //绘制为图片
              发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+Element+"_神榜奖励.png]",false);
            }          
          }
        }
      }
        userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    }
    

   //“查询”道具设定指令，可以查看道具设定（参考用，实际可能会变化）
   if(消息.startsWith("查询"))
   {
       String 内容=消息.substring(2);
        // 获取道具的Item对象
        Item reviveStone = itemMap.get(内容);
        StringBuilder sy = new StringBuilder();
        if (reviveStone != null){
            sy.append(玩家名(qun,uin)+" ("+uin+")\n您查询的【"+内容+"】设定如下↓");
            sy.append("\n<填充>\n    〖物品类型〗     ： *" + reviveStone.getType()+"*");
            sy.append("\n〖是否可以兑换〗 ： " + 查看(reviveStone.isCanExchange()));
            sy.append("\n〖是否可以购买〗 ： " + 查看(reviveStone.isCanPurchase()));
            sy.append("\n〖是否可以出售〗 ： " + 查看(reviveStone.isCanSell()));
            sy.append("\n〖兑换所需金币〗 ： " + 标记(reviveStone.getExchangeGold()));
            sy.append("\n〖购买所需积分〗 ： " + 标记(reviveStone.getPurchasePoints()));
            sy.append("\n〖出售所得积分〗 ： " + 标记(reviveStone.getSellPoints()));
            sy.append("\n〖最大使用数量〗 ： " + reviveStone.getMaxUseCount());
            sy.append("\n〖能否对Ta使用〗： " + 查看(reviveStone.isCanUseOnOthers()));
            sy.append("\n〖是否可以使用〗 ： " + 查看(reviveStone.isCanUseDirectly()));
            sy.append("\n〖是否可以转让〗 ： " + 查看(reviveStone.isCanTransfer()));
            sy.append("\n〖是否可以合成〗 ： " + 查看(reviveStone.isCanCombine()));
            sy.append("\n〖是否可以分解〗 ： " + 查看(reviveStone.isCanDecompose())+"\n<填充>\nTime："+getTodayDate(2));
            sy.append("\n//设定仅供参考，不一定准确");
            toImg(sy.toString(),"",0.5,0.02,26,AppPath+"/缓存/其他/"+uin+"_text_image.png",false);
          发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"_text_image.png]",false);
          // 清空sy的内容
          sy.setLength(0);
        } else {
            发送(qun,"[AtQQ="+uin+"] \n未找到【"+内容+"】信息",false);
        }
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
       //↑记录玩家指令触发时间戳
    }
    
   //让玩家查询自己身份级别（空架子，仅做示范）
   if(消息.equals("我的身份"))
   { 
     String 内容;
     if(权限等级==1){内容="[AtQQ="+uin+"] 你拥有【GM】权限\n发送【GM指令】可查看相关指令";}
     else{内容="[AtQQ="+uin+"] 你是【玩家】";}
     发送(qun,内容,true);
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
    
   //“签到”指令，连续签到达到5/10/15/20/25/30天时，可以获得更多积分/金币奖励
   if(消息.equals("签到"))
   {
        String 头像路径 = AppPath + "/缓存/头像/" + uin + ".png";
        String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + uin + "&spec=640";
        File 呆河马 = new File(头像路径);
        if (!呆河马.exists()) {
        try {
                downloadFile(玩家头像, 头像路径);
                //↑保存该玩家头像；绘制小窝图片时将会用到
            } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + uin + "」头像下载失败");
            }
        }
     签到(qun,uin); //处理签到奖励
     新玩家(qun,uin); //记录新玩家
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }    
   
   //“砸蛋”指令，随机获取一个宠物
   if(消息.equals("砸蛋"))
   {
      long 等级 = 文转(getString(配置名称, "等级"));
      if(等级 >= 1){
        发送(qun,"[AtQQ="+uin+"]   您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
      }else if(积分>=20)
       {
          // 过滤掉品质为“定制”的宠物，仅保留其他品质
          List keys = new ArrayList();
          for (Object key : petMap.keySet()) {
            Pet pet = (Pet) petMap.get(key);
            if (!"定制".equals(pet.getQuality())) { // 跳过“定制”品质的宠物
               keys.add(key);
            }
          }

          Random random = new Random();
          int randomIndex = random.nextInt(keys.size());
          String randomKey = keys.get(randomIndex);
          // 获取对应的宠物对象
          Pet randomPet = petMap.get(randomKey); //宠物名称
          String quality = randomPet.getQuality(); //品质
          String attribute = randomPet.getAttribute(); //属性名
          String gender = randomPet.getGender(); //性别
         //为"随机"时随机获取性别
          if ("随机".equals(gender)) 
          {
             String[] genders = {"雌", "雄"};
             gender = genders[random.nextInt(genders.length)];
          }
       putString(配置名+"/我的资产", "积分", 转文(积分-20));   
       选择宠物(qun, uin, attribute, gender, quality, randomKey); 当前宠物(qun,uin,0);
       发送(qun,"[AtQQ="+uin+"]   恭喜你获得〔"+attribute+"〕属性的"+randomKey+"‹蛋形态› [PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]",false);
      }else{发送(qun,"[AtQQ="+uin+"]   您的积分不足[20]，无法进行砸蛋！",true);}
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }   

   //“砸蛋十连”指令，随机获取十个宠物组成列表供玩家挑选
   if(消息.startsWith("砸蛋十连"))
   {
      long 等级 = 文转(getString(配置名称, "等级"));
      if(等级 >= 1){
        发送(qun,"[AtQQ="+uin+"]   您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
      }else if(积分>=200)
       {
             drawTen(qun, uin);
             StringBuilder result = new StringBuilder();
              for (int i = 1; i <= 10; i++)
              {
                String petInfo = getPetInfoByIndex2(qun+"_"+uin, i);
                result.append("\n"+petInfo);
              }
              putString(配置名+"/我的资产", "积分", 转文(积分-200)); //扣除本次十连的积分
             String 内容=玩家名(qun,uin)+" ("+uin+")\n<填充>"+result.toString()+"\n<填充>\n// ◈格式：序号-级别-属性-宠物名\n// ◈选择：选择+序号\n// ◈预览：预览+序号\n"+getTodayDate(2);
             toImg(内容,"",0.5,0.01,30,AppPath+"/缓存/其他/"+qun+"_"+uin+"_砸蛋十连.png",false);
          发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+qun+"_"+uin+"_砸蛋十连.png]",false);
       }else{发送(qun,"[AtQQ="+uin+"]   您的积分不足[200]，无法进行砸蛋！",true);}
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }

   //“砸蛋百连”指令（这指令生成的内容太长了）
   if(消息.startsWith("砸蛋百连"))
   {
      long 等级 = 文转(getString(配置名称, "等级"));
      if(等级 >= 1){
        发送(qun,"[AtQQ="+uin+"]   您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv."+等级+"-"+宠物名,true);
      }else if(积分>=2000)
       {
             drawHundred(qun, uin);
             StringBuilder result = new StringBuilder();
              for (int i = 1; i <= 100; i++)
              {
                String petInfo = getPetInfoByIndex2(qun+"_"+uin, i);
                result.append("\n"+petInfo);
              }
              putString(配置名+"/我的资产", "积分", 转文(积分-2000)); //扣除积分
             String 内容=玩家名(qun,uin)+" ("+uin+")\n<填充>"+result.toString()+"\n<填充>\n// ◈格式：序号-级别-属性-宠物名\n// ◈选择：选择+序号\n// ◈预览：预览+序号\n"+getTodayDate(2);
             toImg(内容,"",0.5,0.005,30,AppPath+"/缓存/其他/"+qun+"_"+uin+"_砸蛋百连.png",false);
          发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+qun+"_"+uin+"_砸蛋百连.png]",false);
       }else{发送(qun,"[AtQQ="+uin+"]   您的积分不足[2000]，无法进行砸蛋！",true);}
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
 
//处理“砸蛋x连”指令后续内容，可按序号挑选宠物
if (消息.startsWith("选择")) {
    if (消息.length() == 2) {
        发送(qun,"[AtQQ="+uin+"]   格式错误\n•指令：选择+序号\n•示例：选择"+随机数(1,10),true);
    } else {
        int index = Integer.parseInt(消息.substring(2).trim());
        String selectedPet = selectPet(String.valueOf(qun), String.valueOf(uin), index);
        long 等级 = 文转(getString(配置名称, "等级"));
        if (等级 >= 1) {
            发送(qun,"[AtQQ="+uin+"]  "+ " 您已经有〔Lv." + 等级 + "-" + 宠物名 + "〕了，贪多嚼不烂哦!\n•指令：宠物放生",true);
        } else if (!"无效的选择序号".equals(selectedPet)) {
            String[] parts = selectedPet.split("-");
            if (parts.length == 4) {
                String serialNumber = parts[0].replace("[", "").replace("]", ""); //序号
                String quality = parts[1].replace("[", "").replace("]", ""); //品质
                String attribute = parts[2]; //属性名
                String petName = parts[3]; //宠物名

                // 从petMap中获取宠物的Pet对象
                Pet name = petMap.get(petName);
                String gender = name.getGender();
                //随机获取性别
                if ("随机".equals(gender)) {
                    String[] genders = {"雌", "雄"};
                    Random random = new Random();
                    gender = genders[random.nextInt(genders.length)];
                }
                //删除该玩家选择列表
                playerPetMap.remove(groupId + "_" + account);

                选择宠物(qun, uin, attribute, gender, quality, petName);
                当前宠物(qun, uin,0);
                发送(qun,"[AtQQ="+uin+"]  "+ " 恭喜你获得〔" + attribute + "〕属性的" + petName + "‹蛋形态› [PicUrl=" + AppPath + "/缓存/宠物/" + uin + "_宠物图.png]",false);
            }
        } else {
            发送(qun,"[AtQQ="+uin+"]  "+ " 请先[砸蛋十连]，再来挑选宠物喔！\n•指令：砸蛋十连",true);
        }
    }
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}

//处理“砸蛋十连”指令后续内容，可按序号预览该宠物属性
if (消息.startsWith("预览")) {
    if (消息.length() == 2) {
        发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n•指令：预览+序号\n•示例：预览"+随机数(1,10),true);
    } else {
        int index = Integer.parseInt(消息.substring(2).trim());
        String selectedPet = selectPet2(String.valueOf(qun), String.valueOf(uin), index);
        if (!"无效的选择序号".equals(selectedPet)) {
            String[] parts = selectedPet.split("-");
            if (parts.length == 4) {
                String serialNumber = parts[0].replace("[", "").replace("]", ""); //序号
                String quality = parts[1].replace("[", "").replace("]", ""); //品质
                String attribute = parts[2].replace("[", "").replace("]", ""); //属性名
                String petName = parts[3].replace("[", "").replace("]", ""); //宠物名

                // 从petMap中获取宠物的Pet对象
                Pet name = petMap.get(petName);
                String gender = name.getGender();

                预览(qun, uin, attribute, gender, quality, petName);
                发送(qun,"[AtQQ="+uin+"]  "+ " 你预览的序号〔" + index + "〕宠物属性如下：[PicUrl=" + AppPath + "/缓存/宠物/" + uin + "_宠物预览图.png]\n\n如果对此宠物感到满意，\n可发送【选择"+index+"】来选择ta",false);
            }
        } else {
            发送(qun,"[AtQQ="+uin+"]  "+ " 请先[砸蛋十连]，再来预览宠物属性哦！\n•指令：砸蛋十连",true);
        }
    }
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}

   //“我的宠物”指令，可查看出战宠物信息
   if(消息.equals("我的宠物")||消息.equals("宠物属性")||消息.equals("宠物信息"))
   {
     if(宠物名==null||宠物名.isEmpty())
     {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else{
       当前宠物(qun,uin,0);
       发送(qun,"[PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]",false);
     }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
   //“宠物升级”指令，可批量升级。如：宠物升级9999
   if(消息.startsWith("宠物升级"))
   {
       long 当前经验 = 文转(getString(配置名称, "当前经验"));
       long 下级经验 = 文转(getString(配置名称, "下级所需经验"));
       long 心情 = 文转(getString(配置名称, "心情"));
       long 等级 = 文转(getString(配置名称, "等级"));
       String 状态 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "状态");
       String 闭关 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "闭关");
     if(等级 <= 0){
        发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•指令：使用复活石",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if(当前经验 < 下级经验){
       发送(qun,"[AtQQ="+uin+"]   您的【"+宠物名+"】经验不足，无法升级！\n升级到Lv·"+(等级+1)+"级，\n还需要"+数值转(下级经验-当前经验)+"经验值",true);
     }
     else if (消息.equals("宠物升级")) {
        宠物升级(qun, uin, 1L);
     }
     else if (消息.length() >= 5)
     {
            String 去掉前缀 = 消息.replace("宠物升级", "");
            if (去掉前缀.matches("\\d+")){
                long 次数 = 文转(去掉前缀);
                if (次数 > 99999) {
                  发送(qun,"[AtQQ="+uin+"]   输入数值超出范围！\n范围在1～99999",true);
                } else {
                  宠物升级(qun,uin,次数);
                }
            }
          else {
                // 判断是否含有数字
                boolean 含有数字 = 去掉前缀.matches(".*\\d+.*");
                if (含有数字) {
                    // 去掉不是数字的内容
                    String 纯数字 = 去掉前缀.replaceAll("\\D", "");
                    long 次数 = 文转(纯数字);
                    宠物升级(qun,uin,次数);
                } else {
                   long 次数 =1;
                   宠物升级(qun,uin,次数);
                }
            }
        }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }      
   
   //“宠物进化”，按阶段增加属性。发送「进化流程」可查看详情
   if(消息.startsWith("宠物进化"))
   {
      long 等级 = 文转(getString(配置名称, "等级"));
      long 心情 = 文转(getString(配置名称, "心情"));
      String 状态 = getString(配置名称, "状态");
      String 闭关 = getString(配置名称, "闭关");
      if(状态==null||等级 <= 0){
        发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
      }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if (消息.equals("宠物进化")) {
        宠物进化(qun,uin); //单次进化
     }
     else if (消息.length() >= 5)
     {
            String 去掉前缀 = 消息.replace("宠物进化", "");
            if (去掉前缀.matches("\\d+")){
                long 次数 = 文转(去掉前缀);
                if (次数 > 99999) {
                  发送(qun,"[AtQQ="+uin+"]   输入数值超出范围！\n范围在1～99999",true);
                } else {
                  宠物进化(qun,uin,次数);
                }
            }
          else {
                // 判断是否含有数字
                boolean 含有数字 = 去掉前缀.matches(".*\\d+.*");
                if (含有数字) {
                    // 去掉不是数字的内容
                    String 纯数字 = 去掉前缀.replaceAll("\\D", "");
                    long 次数 = 文转(纯数字);
                    宠物进化(qun,uin,次数);
                } else {
                   宠物进化(qun,uin);
                }
            }
        }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }   
   
   //“进化流程”指令，可查看一些内容
   if(消息.equals("进化流程")) 
   {
            String filePath = AppPath + "/缓存/其他/进化流程.png";
            File file = new File(filePath);
            if (!file.exists()) {
                // 获取全部品质的各项属性与进化次数
                StringBuilder allAttributes = new StringBuilder();
                for (String q : quality) {
                    if (petAttributeMap.containsKey(q)) {
                        allAttributes.append("品质: ").append(q).append(",  初始属性: ");
                        for (long attr : petAttributeMap.get(q)) {
                            allAttributes.append(attr).append(", ");
                        }
                        allAttributes.append("\n");
                    }
                }

                // 获取最后一个元素
                String lastElement = quality[quality.length - 1];
                // 获取最高品质的进化次数
                long[] eternalAttributes = petAttributeMap.get(lastElement);
                int eternalEvolveTimes = (int) eternalAttributes[4];
                // 获取每个进化层次的各项属性
                StringBuilder evolveAttributes = new StringBuilder();
                for (int i = 1; i <= eternalEvolveTimes; i++) {
                    long[] evolveAttr = evolutionMap.get(i);
                    evolveAttributes.append("进化次数: ").append(i).append(",  属性: ");
                    for (long attr : evolveAttr) {
                        evolveAttributes.append(attr).append(", ");
                    }
                    evolveAttributes.append("\n");
                }

                // 获取 evolutionInfoList 最后的内容
                long lastOne = evolutionLevels[evolutionLevels.length - 1];
                List evolutionInfoList = new ArrayList();
                for (int i = 0; i < evolutionLevels.length; i++) {
                    if (evolutionLevels[i] <= lastOne) {
                        String info = "阶段" + evolutionLevels[i] + "：" + stages[i] + "——" + levels[i] + "——" + evolutionLevels[i];
                        evolutionInfoList.add(info);
                    }
                }
                // 将获取的进化流程拼接到一起
                StringBuilder result = new StringBuilder();
                for (String info : evolutionInfoList) {
                    result.append("\n" + info);
                }

                // 拼接内容
                StringBuilder finalResult = new StringBuilder();
                finalResult.append("*全部品质初始属性（品质从低到高）*:\n// 格式：品质，初始属性｛攻击，防御，智力，生命，进化次数上限｝\n").append(allAttributes).append("<填充>\n");
                finalResult.append("*全阶段进化提升属性*:\n// 格式：当前进化次数，提升属性｛攻击，防御，智力，精力，生命｝\n").append(evolveAttributes).append("<填充>\n");
                finalResult.append("*宠物进化流程（按最高品质获取）*:\n// 格式：进化阶段——等级要求——当前进化次数").append(result.toString()).append("\n");

                String 内容 = finalResult.toString() + "<填充>\n<起>Time：" + getTodayDate(1) + " <尾>";
                toImg(内容, "", 0.5, 0.01, 25, AppPath + "/缓存/其他/进化流程.png",true);
                发送(qun,玩家名(qun,uin)+ " [PicUrl=" + AppPath + "/缓存/其他/进化流程.png]",true);
            } else {
                发送(qun,玩家名(qun,uin)+ " [PicUrl=" + AppPath + "/缓存/其他/进化流程.png]",true);
            }
             userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     }

  //“宠物觉醒”指令，消耗固定数量的精力与经验可觉醒天赋
   if(消息.equals("宠物觉醒")||消息.equals("天赋觉醒")||消息.equals("觉醒天赋"))
   {
        // 1. 获取所有键（天赋名称）并转为List
        List talentNames = new ArrayList(petTalents.keySet());
        // 随机获取一个天赋名称
        Random random = new Random();
        String randomTalent = talentNames.get(random.nextInt(talentNames.size()));
        // 获取对应的类型和介绍
        String[] info = petTalents.get(randomTalent);
        String type = info[0];
        String description = info[1];
        String 状态 = getString(配置名称, "状态");
        String 闭关 = getString(配置名称, "闭关");
       long 当前精力 = 文转(getString(配置名称, "当前精力"));
       long 当前经验 = 文转(getString(配置名称, "当前经验"));
       long 心情 = 文转(getString(配置名称, "心情"));
       long 等级 = 文转(getString(配置名称, "等级"));
       int 条件 = 2000000;
     if(等级 <= 0){
        发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if(当前经验<条件){
       发送(qun,"[AtQQ="+uin+"]   太可惜了，"+type+"天赋【"+randomTalent+"】擦肩而过\n您的宠物经验不足,无法觉醒！\n【所需["+条件+"]经验】",true);
     }else if(当前精力<100)
     {
       发送(qun,"[AtQQ="+uin+"]   太可惜了，"+type+"天赋【"+randomTalent+"】擦肩而过\n您的宠物精力不足,无法觉醒!\n【所需[100]精力】",true);
     }else{
       String 天赋 = getString(配置名称, "天赋");
       if(天赋.equals("无")||天赋==null||天赋.isEmpty())
       {
         当前经验 -= 条件; 当前精力 -= 100;
         putString(配置名称,"天赋",randomTalent);
         putString(配置名称, "当前精力", 转文(当前精力));
         putString(配置名称, "当前经验", 转文(当前经验));
         发送(qun,"[AtQQ="+uin+"]   你的宠物「"+宠物名+"」已成功觉醒，获得天赋：【"+randomTalent+"】\n\n【类型】："+type+"\n【介绍】："+description,false);
       }else{
         当前经验 -= 条件; 当前精力 -= 100;
         putString(配置名称,"天赋",randomTalent);
         putString(配置名称, "当前精力", 转文(当前精力));
         putString(配置名称, "当前经验", 转文(当前经验));
         发送(qun,"[AtQQ="+uin+"]   你的宠物「"+宠物名+"」已成功觉醒，获得新天赋：【"+randomTalent+"】，替换了原先的天赋【"+天赋+"】\n\n【类型】："+type+"\n【介绍】："+description,false);
       }
     }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }     
   
  // 查看“宠物小窝”，1个小窝=6个宠物位置
  //（小窝进阶卡：小窝宠物位置+1 || 小窝激活卡：激活第一个小窝宠物位置）
  if(消息.startsWith("宠物小窝")) 
  {
    int 数量 = getInt(配置名 + "我的小窝", "小窝位置", 0);
    int 计数 = (数量 + 6 - 1) / 6;  //计算拥有的小窝数量
    String 剩余部分 = 消息.substring(4).trim();
    String 可能的数字 = 剩余部分.isEmpty() ? "1" : 剩余部分;
    String 纯净数值 = 可能的数字.replaceAll("[^\\d]", ""); // 过滤非数字的字符
    纯净数值 = 纯净数值.replaceFirst("^0+", ""); // 去除前导零
    long 数值 = 纯净数值.isEmpty() ? 1 : Long.parseLong(纯净数值);
    
    if (数量 == 0) {
        发送(qun, "[AtQQ="+uin+"]   您还没有宠物小窝哦！\n•指令：使用小窝激活卡",true);
    } else if(数值>计数){
      发送(qun,"[AtQQ="+uin+"]   你拥有的小窝数量小于"+数值+"，最高支持查看"+计数+"。\n•示例：宠物小窝"+计数,true);
    } else {
        String 剩余内容 = 消息.substring("宠物小窝".length()).trim();
        String 头像路径 = AppPath + "/缓存/头像/" + uin + ".png";
        String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + uin + "&spec=640";
        File 呆河马 = new File(头像路径);
        if (!呆河马.exists()) {
        try {
                downloadFile(玩家头像, 头像路径);
                //↑保存该玩家头像；绘制小窝图片时将会用到
            } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + uin + "」头像下载失败");
            }
        }
        
        if (剩余内容.isEmpty()) {
            宠物小窝(qun, uin, 1);
            发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_宠物小窝_1.png]",false);
        } else {
          try {
                int 数字 = Integer.parseInt(剩余内容);
                宠物小窝(qun, uin, 数字);
                发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_宠物小窝_" + 数字 + ".png]",false);
            } catch (NumberFormatException e) {
                发送(qun,"[AtQQ="+uin+"]   格式错误！\n指令：宠物小窝+纯数字\n示例：宠物小窝"+随机数(1,10),true);
            }
        }
    }
     userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  }
  
//“出战宠物”与“小窝位置”宠物相互替换的指令
//格式：宠物替换+数字
//新增效果：1.替换到小窝自动回血  2.自动解除异常状态（除死亡之外）
if (消息.startsWith("宠物替换")) {
    int 数量 = getInt(配置名 + "我的小窝", "小窝位置", 0);
    long 心情 = 文转(getString(配置名称, "心情"));
    long 等级 = 文转(getString(配置名称, "等级"));
    String 状态 = getString(配置名称, "状态");
    String 闭关 = getString(配置名称, "闭关");
   
    if (数量 == 0) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 您还没有宠物小窝哦！\n指令：使用小窝激活卡",true);
    } else if (状态!=null&&状态.equals("死亡")) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    } else {
        String y = 消息.replaceAll("宠物替换", "");

        //当y字数长度为0时 或 y不是纯数字时 提示↓
        if (y.length() == 0 || !isLetterDigitOrChinese(y)) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 指令格式错误！\n指令：宠物替换+数字\n示例：宠物替换" + 随机数(1, 99),true);
        } else {
            int 小窝序号 = Integer.parseInt(y);
             if (小窝序号 == 0) {
                发送(qun, "[AtQQ="+uin+"]  "+ " \n\n替换位置不能为[0]！\n示例：宠物替换" + 随机数(1, 99),true);
            } else if (数量 < 小窝序号) {
                发送(qun, "[AtQQ="+uin+"]  "+ " 目前只能使用序号1-" + 数量 + "号位置，其他位置还未解锁～\n•指令：使用小窝进阶卡",true);
            } else if (小窝序号 != 0 && 数量 >= 小窝序号) {
                // ↓待替换宠物属性（假设这里是当前出战宠物）
                String 对象 = qun + "/" + uin + "/宠物小窝/位置_0"; //出战宠物所在位置
                String 心情 = getString(对象, "心情");
                String 性别 = getString(对象, "性别");
                String 婚况 = getString(对象, "婚姻状况");
                String 属性 = getString(对象, "属性");
                String 状态 = getString(对象, "状态");
                long 等级 = 文转(getString(对象, "等级"));
                long 攻击 = 文转(getString(对象, "攻击"));
                long 防御 = 文转(getString(对象, "防御"));
                long 智力 = 文转(getString(对象, "智力"));
                String 神器 = getString(对象, "神器");
                String 级别 = getString(对象, "级别");
                String 天赋 = getString(对象, "天赋");
                String 阶段 = getString(对象, "阶段");
                String 昵称 = getString(对象, "昵称");
                String 名字 = getString(对象, "宠物名字");
                String 易容 = getString(对象, "是否易容");
                String 上传图 = getString(对象, "上传图片");
                String 结婚对象 = getString(对象, "结婚对象");
                long 进化层次 = 文转(getString(对象, "进化层次"));
                long 进化上限 = 文转(getString(对象, "进化上限"));
                long 当前精力 = 文转(getString(对象, "当前精力"));
                long 精力上限 = 文转(getString(对象, "精力上限"));
                long 当前生命 = 文转(getString(对象, "当前生命"));
                long 生命上限 = 文转(getString(对象, "生命上限"));
                long 当前经验 = 文转(getString(对象, "当前经验"));
                long 下级经验 = 文转(getString(对象, "下级所需经验"));

                // ↓被替换宠物属性 （假设这里被替换前是在小窝里第一个位置）
                String 堆箱 = qun + "/" + uin + "/宠物小窝/位置_" + 小窝序号;
                String 心情2 = getString(堆箱, "心情");
                String 性别2 = getString(堆箱, "性别");
                String 婚况2 = getString(堆箱, "婚姻状况");
                String 属性2 = getString(堆箱, "属性");
                String 状态2 = getString(堆箱, "状态");
                long 等级2 = 文转(getString(堆箱, "等级"));
                long 攻击2 = 文转(getString(堆箱, "攻击"));
                long 防御2 = 文转(getString(堆箱, "防御"));
                long 智力2 = 文转(getString(堆箱, "智力"));
                String 神器2 = getString(堆箱, "神器");
                String 级别2 = getString(堆箱, "级别");
                String 天赋2 = getString(堆箱, "天赋");
                String 阶段2 = getString(堆箱, "阶段");
                String 昵称2 = getString(堆箱, "昵称");
                String 名字2 = getString(堆箱, "宠物名字");
                String 易容2 = getString(堆箱, "是否易容");
                String 上传图2 = getString(堆箱, "上传图片");
                String 结婚对象2 = getString(堆箱, "结婚对象");
                long 进化层次2 = 文转(getString(堆箱, "进化层次"));
                long 进化上限2 = 文转(getString(堆箱, "进化上限"));
                long 当前精力2 = 文转(getString(堆箱, "当前精力"));
                long 精力上限2 = 文转(getString(堆箱, "精力上限"));
                long 当前生命2 = 文转(getString(堆箱, "当前生命"));
                long 生命上限2 = 文转(getString(堆箱, "生命上限"));
                long 当前经验2 = 文转(getString(堆箱, "当前经验"));
                long 下级经验2 = 文转(getString(堆箱, "下级所需经验"));
                

             if(等级==0&&等级2==0){
                  发送(qun,"[AtQQ="+uin+"]   本次替换毫无意义！\n因为「当前宠物」与「小窝位置_"+小窝序号+"」这两个位置都没有宠物！",true);
            }else{
                
               // “被替换”位置没有宠物，只将「当前宠物」放到该序号位置上
                if (等级2 == 0) {
                   宠物回血(qun, uin, 1); //提前恢复宠物生命
                   
                    //记录被替换后的小窝序号宠物信息
                    putString(堆箱, "心情", 心情);
                    putString(堆箱, "等级", 转文(等级));
                    putString(堆箱, "攻击", 转文(攻击));
                    putString(堆箱, "防御", 转文(防御));
                    putString(堆箱, "智力", 转文(智力));
                    putString(堆箱, "当前精力", 转文(当前精力));
                    putString(堆箱, "精力上限", 转文(精力上限));
                    putString(堆箱, "当前生命", 转文(当前生命));
                    putString(堆箱, "生命上限", 转文(生命上限));
                    putString(堆箱, "当前经验", 转文(当前经验));
                    putString(堆箱, "下级所需经验", 转文(下级经验));
                    putString(堆箱, "进化层次", 转文(进化层次));
                    putString(堆箱, "进化上限", 转文(进化上限));
                    putString(堆箱, "昵称", 昵称);
                    putString(堆箱, "宠物名字", 名字);
                    putString(堆箱, "性别", 性别);
                    putString(堆箱, "属性", 属性);
                    putString(堆箱, "阶段", 阶段);
                    putString(堆箱, "级别", 级别);
                    putString(堆箱, "状态", "正常");
                    putString(堆箱, "神器", 神器);
                    putString(堆箱, "天赋", 天赋);
                    putString(堆箱, "婚姻状况", 婚况);
                    putString(堆箱, "结婚对象", 结婚对象);
                    putString(堆箱, "是否易容", 易容);
                    putString(堆箱, "上传图片", 上传图);
                    
                    //删除“位置_0”文件，避免宠物无限复制（也就是当前宠物）
                    删除文件(缓存路径+配置名称); 
                }
               
               //当前没有出战宠物，将小窝宠物替换出来
               else if (等级 == 0) {
                    //记录被替换后的小窝序号宠物信息
                    putString(对象, "心情", 心情2);
                    putString(对象, "等级", 转文(等级2));
                    putString(对象, "攻击", 转文(攻击2));
                    putString(对象, "防御", 转文(防御2));
                    putString(对象, "智力", 转文(智力2));
                    putString(对象, "当前精力", 转文(当前精力2));
                    putString(对象, "精力上限", 转文(精力上限2));
                    putString(对象, "当前生命", 转文(当前生命2));
                    putString(对象, "生命上限", 转文(生命上限2));
                    putString(对象, "当前经验", 转文(当前经验2));
                    putString(对象, "下级所需经验", 转文(下级经验2));
                    putString(对象, "进化层次", 转文(进化层次2));
                    putString(对象, "进化上限", 转文(进化上限2));
                    putString(对象, "昵称", 昵称2);
                    putString(对象, "宠物名字", 名字2);
                    putString(对象, "性别", 性别2);
                    putString(对象, "属性", 属性2);
                    putString(对象, "阶段", 阶段2);
                    putString(对象, "级别", 级别2);
                    putString(对象, "状态", "正常");
                    putString(对象, "神器", 神器2);
                    putString(对象, "天赋", 天赋2);
                    putString(对象, "婚姻状况", 婚况2);
                    putString(对象, "结婚对象", 结婚对象2);
                    putString(对象, "是否易容", 易容2);
                    putString(对象, "上传图片", 上传图2);
                    
                    //删除该位置文件，避免宠物无限复制
                    删除文件(缓存路径+qun+"/"+uin+"/宠物小窝/位置_" + 小窝序号); 
                    
                    宠物回血(qun, uin, 1); //恢复替换出来的宠物生命
                }

                // 该序号的位置有数据，开始相互交换数据
                else {
                    //记录替换后的“出战宠物”信息
                    putString(对象, "心情", 心情2);
                    putString(对象, "等级", 转文(等级2));
                    putString(对象, "攻击", 转文(攻击2));
                    putString(对象, "防御", 转文(防御2));
                    putString(对象, "智力", 转文(智力2));
                    putString(对象, "当前精力", 转文(当前精力2));
                    putString(对象, "精力上限", 转文(精力上限2));
                    putString(对象, "当前生命", 转文(当前生命2));
                    putString(对象, "生命上限", 转文(生命上限2));
                    putString(对象, "当前经验", 转文(当前经验2));
                    putString(对象, "下级所需经验", 转文(下级经验2));
                    putString(对象, "进化层次", 转文(进化层次2));
                    putString(对象, "进化上限", 转文(进化上限2));
                    putString(对象, "昵称", 昵称2);
                    putString(对象, "宠物名字", 名字2);
                    putString(对象, "性别", 性别2);
                    putString(对象, "属性", 属性2);
                    putString(对象, "阶段", 阶段2);
                    putString(对象, "级别", 级别2);
                    putString(对象, "状态", "正常");
                    putString(对象, "神器", 神器2);
                    putString(对象, "天赋", 天赋2);
                    putString(对象, "婚姻状况", 婚况2);
                    putString(对象, "结婚对象", 结婚对象2);
                    putString(对象, "是否易容", 易容2);
                    putString(对象, "上传图片", 上传图2);

                    //记录被替换后的小窝序号宠物信息
                    putString(堆箱, "心情", 心情);
                    putString(堆箱, "等级", 转文(等级));
                    putString(堆箱, "攻击", 转文(攻击));
                    putString(堆箱, "防御", 转文(防御));
                    putString(堆箱, "智力", 转文(智力));
                    putString(堆箱, "当前精力", 转文(当前精力));
                    putString(堆箱, "精力上限", 转文(精力上限));
                    putString(堆箱, "当前生命", 转文(当前生命));
                    putString(堆箱, "生命上限", 转文(生命上限));
                    putString(堆箱, "当前经验", 转文(当前经验));
                    putString(堆箱, "下级所需经验", 转文(下级经验));
                    putString(堆箱, "进化层次", 转文(进化层次));
                    putString(堆箱, "进化上限", 转文(进化上限));
                    putString(堆箱, "昵称", 昵称);
                    putString(堆箱, "宠物名字", 名字);
                    putString(堆箱, "性别", 性别);
                    putString(堆箱, "属性", 属性);
                    putString(堆箱, "阶段", 阶段);
                    putString(堆箱, "级别", 级别);
                    putString(堆箱, "状态", "正常");
                    putString(堆箱, "神器", 神器);
                    putString(堆箱, "天赋", 天赋);
                    putString(堆箱, "婚姻状况", 婚况);
                    putString(堆箱, "结婚对象", 结婚对象);
                    putString(堆箱, "是否易容", 易容);
                    putString(堆箱, "上传图片", 上传图);
                    
                   宠物回血(qun, uin, 1); //提前恢复宠物生命
                }
                发送(qun, "[AtQQ="+uin+"]  "+ " 宠物替换成功！",true);
            }
           }
        }
    }
   userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));  
}


  // 可以查看小窝里的宠物属性
  if(消息.startsWith("宠物预览"))
  {
    int 数量 = getInt(配置名 + "我的小窝", "小窝位置", 0);
    if (数量 == 0) {
        发送(qun, "[AtQQ="+uin+"]   您还没有宠物小窝哦！\n指令：使用小窝激活卡",true);
    } else {
        String 剩余内容 = 消息.substring("宠物预览".length()).trim();
        long 小窝序号 = 文转(剩余内容);
        if (剩余内容.isEmpty()) {
            发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n指令：宠物预览+数字\n示例：宠物预览"+随机数(1,99),true);
        }else if(小窝序号!=0&&数量>=小窝序号){
          // ↓待替换宠物属性（假设这里是当前出战宠物）
          String 对象=qun+"/"+uin+"/宠物小窝/位置_"+小窝序号; //被查询的宠物位置
          long 等级 = 文转(getString(对象, "等级"));
          String 名字 = getString(对象, "宠物名字");
          if(等级<1){
            发送(qun,"[AtQQ="+uin+"]   \n小窝序号["+小窝序号+"]当前没有宠物！",true);
          }else{
            String 昵称 = getString(qun+"/"+uin+"/宠物小窝/位置_"+小窝序号, "昵称");
            long 等级 = 文转(getString(qun+"/"+uin+"/宠物小窝/位置_"+小窝序号, "等级"));
            当前宠物(qun,uin,小窝序号);
            发送(qun,"[AtQQ="+uin+"]  [PicUrl="+AppPath+"/缓存/宠物/"+uin+"_宠物图.png]【位置】："+小窝序号+"\n【昵称】："+昵称+"\n【等级】：Lv."+等级,true);
          }
        }else if(小窝序号==0){
          发送(qun,"[AtQQ="+uin+"]   \n\n位置不能为[0]！\n示例：宠物预览"+随机数(1,99),true);
        }else if(数量<小窝序号){
          发送(qun,"[AtQQ="+uin+"]   目前只能使用序号1-"+数量+"号位置，其他位置还未解锁～\n指令：使用小窝进阶卡",true);
        }
    }
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
  
   //过渡指令
   if(消息.equals("宠物复活")){
     String 状态 = getString(配置名称, "状态");
     String 闭关 = getString(配置名称, "闭关");
     long 等级 = 文转(getString(配置名称, "等级"));
     if(状态==null) {
       发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(!状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物没有死亡哦，无需复活",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else{
       putString(配置名称, "状态", "正常");
       宠物回血(qun, uin, 1);
       发送(qun,"[AtQQ="+uin+"]   成功！\n你的宠物【"+宠物名+"】成功复活！生命已恢复至100%！",true);
    }
    userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
   }
   
   //过渡指令
   if(消息.equals("宠物回血")){
     String 状态 = getString(配置名称, "状态");
     String 闭关 = getString(配置名称, "闭关");
     long 心情 = 文转(getString(配置名称, "心情"));
     long 等级 = 文转(getString(配置名称, "等级"));
     if(状态==null) {
       发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•示例：宠物复活",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else{
       宠物回血(qun, uin, 1);
       发送(qun,"[AtQQ="+uin+"]   成功！\n你的宠物【"+宠物名+"】生命已恢复！",true);
    }
    userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); 
   }
   
   //“宠物放生”指令，可放生不需要的宠物
   if(消息.equals("宠物放生")||消息.equals("放生宠物"))
   {
      long 等级 = 文转(getString(配置名称, "等级"));
      boolean 状态 = getBoolean(配置名+"状态", "宠物放生", false);
      String 级别=getString(配置名称,"级别");
      if(状态==true){
        发送(qun,"[AtQQ="+uin+"]   危险操作❗️\n即将放生〔LV."+等级+"-"+级别+"-"+宠物名+"〕\n放生后宠物会离开，是否确认放生该宠物？\n◇指令：确定放生",true);
      }else if(等级 <= 0){
        发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
      }else{
          发送(qun,"[AtQQ="+uin+"]   危险操作❗️\n即将放生〔LV."+等级+"-"+级别+"-"+宠物名+"〕\n放生后宠物会离开，是否确认放生该宠物？\n◇指令：确定放生",true);
          putBoolean(配置名+"状态", "宠物放生", true);
       }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
   //判断是否存在宠物放生请求，已有请求则在“确定放生”后，删除该位置文件
   if(消息.equals("确定放生")||消息.equals("确认放生"))
   {
      boolean 状态 = getBoolean(配置名+"状态", "宠物放生", false);
      long 等级 = 文转(getString(配置名称, "等级"));
      String[] 跑路 = {"屁颠屁颠的走了！","蹦蹦跳跳的走了～","脚底抹油，瞬间消失得无影无踪","像被发射的火箭一样，嗖的一下冲出去了","一边哼着跑调的歌，一边晃晃悠悠地离开","假装淡定，实则脚步匆忙地溜了","以百米冲刺的速度跑远了"};
        Random random = new Random();
        int index = random.nextInt(跑路.length);
        String 随机描述 = 跑路[index];
                
     if(状态==true&&等级>=1){
        String 原名=getString(配置名称,"宠物名字");
        String 品质=getString(配置名称,"级别");
        String 易容 = getString(配置名称, "是否易容");
        //判断该宠物是否为“定制”宠物，如果是则删除图片缓存、删除定制记录
        if(!petMap.containsKey(原名)&&品质.equals("定制")){
          String 定制记录 = getString("定制宠物", "宠物列表");

          boolean containsNickname = false; //标记

          if (定制记录 != null && !"null".equals(定制记录) && !"".equals(定制记录.trim()))
          {
            // 按"、"分割定制记录
            String[] 宠物数组 = 定制记录.split("、");
            // 遍历处理每个分割后的子字符串
            for (String petItem : 宠物数组) {
                // 对每个子字符串按","分割
                String[] items = petItem.split(",");
                
                // 遍历判断是否包含nickname
                for (String item : items) {
                    if (item.equals(原名)) {
                        containsNickname = true;
                        break;
                    }
                }
                if (containsNickname) {
                    break;
                }
            }
          }
          if(containsNickname){
            if (定制记录.isEmpty()) {
             return; // 列表为空，无需删除
            }
            String[] petArray = 定制记录.split("、");
            StringBuilder newPetList = new StringBuilder();
            for (String pet : petArray) {
              if (!pet.startsWith(原名 + ",")) {
                if (newPetList.length() > 0) {
                  newPetList.append("、");
                }
                newPetList.append(pet);
              }
            }
            String result = newPetList.toString();
            if (result.length() == 0) {
              删除文件(缓存路径+"定制宠物"); //删除文件
            } else {
             putString("定制宠物", "宠物列表", newPetList.toString());
             //更新定制记录（去掉放生宠物后）
            }
            删除文件(AppPath+"/目录/图片/宠物/定制/"+原名+".jpg");
          }
        }else if(易容.equals("是")){
          //删除对应易容图片
          String 上传图 = getString(配置名称, "上传图片");
          删除文件(AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png");
        }
      发送(qun,"[AtQQ="+uin+"]   成功放生宠物，您的「"+宠物名+"」"+随机描述,true);
      putBoolean(配置名+"状态", "宠物放生", false);
      删除文件(缓存路径+配置名称); //删除“位置_0”文件可达到成功放生宠物的效果        
    }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
  
   //清空背包指令
   if(消息.equals("清空背包")){
      String 道具 = getString(qun + "/" + uin + "/我的背包", "道具列表");
      String 群列表 = getString(qun + "/玩家列表", "列表");
      if(群列表==null||!群列表.contains(uin)){
         发送(qun,"[AtQQ="+uin+"]   \n\n此群没有您的相关数据！\n请发送【领新手礼包】来刷新！",true);
      } else if (道具 == null || "null".equals(道具)) {
         发送(qun, "[AtQQ="+uin+"]  "+ " 您的背包早已空空如也啦，无需重复操作",true);
      } else {
         发送(qun,"[AtQQ="+uin+"]   危险操作❗️\n即将清空背包所有物品，是否继续操作？\n◆指令：继续清空",true);
         putBoolean(配置名+"状态", "清空背包", true);
      }
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
   //确定清空背包
   if(消息.equals("继续清空")){
      String 道具 = getString(qun + "/" + uin + "/我的背包", "道具列表");
      boolean 状态 = getBoolean(配置名+"状态", "清空背包", false);
      if(状态==true&&道具!=null){
        删除文件(缓存路径+qun+"/"+uin+"/我的背包");  //清空背包
        //删除文件(缓存路径+qun+"/"+uin+"/我的资产");  //清空资产（积分与金币）
        //删除文件(缓存路径+qun+"/"+uin+"/状态");  //清空领取状态
        putBoolean(配置名+"状态", "清空背包", false); //更改状态
        发送(qun,"[AtQQ="+uin+"]   已清空你背包中所有物品！",true);
      }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
     
  // 指令：宠物吞噬+数字  （消耗「吞噬卡」*1）
  if(消息.startsWith("宠物吞噬"))
  {
    int 数量 = getInt(配置名 + "我的小窝", "小窝位置", 0);
    long 心情 = 文转(getString(配置名称, "心情"));
    String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; //出战宠物所在位置
    long 等级1 = 文转(getString(对象, "等级")); //出战宠物等级
    String 前置 = qun + "/" + uin + "/我的背包";
    long 道具=文转(getString(前置, "吞噬卡"));
    String 状态 = getString(配置名称, "状态");
    String 闭关 = getString(配置名称, "闭关");
    if(消息.equals("宠物吞噬")){
      发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n指令：宠物吞噬+数字\n示例：宠物吞噬"+随机数(1,99),true);
    }else if (数量 == 0) {
        发送(qun, "[AtQQ="+uin+"]   您还没有宠物小窝哦！\n指令：使用小窝激活卡",true);
    } else if(等级1==0) {
        发送(qun,"[AtQQ="+uin+"]   您当前还没有宠物，赶紧邂逅您的宠物!\n◇指令：砸蛋十连",true);
    }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级1!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }else if(道具<1) {
      发送(qun,"[AtQQ="+uin+"]   \n您当前背包中【吞噬卡】不足1张",true);
    }else {
        String 剩余内容 = 消息.substring("宠物吞噬".length()).trim();
        int 小窝序号 = Integer.parseInt(剩余内容);
        String 堆箱=qun+"/"+uin+"/宠物小窝/位置_"+小窝序号; //小窝宠物位置
        long 等级2 = 文转(getString(堆箱, "等级")); //小窝宠物等级
        
        if (剩余内容.isEmpty() ||!剩余内容.matches("\\d+")) {
            发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n指令：宠物吞噬+数字\n示例：宠物吞噬"+随机数(1,99),true);
        }else if(小窝序号!=0&&数量>=小窝序号){
             //计算当前宠物战力并换算
             long power = 计算战力(qun, uin, 0); //当前战力
             String 战力=数值转(power);
             String pureNumberStr = 战力.replaceAll("[^\\d.]", "");
             // 转换为double类型
             double result = Double.parseDouble(pureNumberStr);
        
             //计算小窝位置的战力
             long otherPower = 计算战力(qun, uin, 小窝序号); //小窝宠物战力
             String other战力 = 数值转(otherPower);
             String otherPureNumberStr = other战力.replaceAll("[^\\d.]", "");
             double otherResult = Double.parseDouble(otherPureNumberStr);
             
             double totalPower = result + otherResult; //两个位置战力相加
        
          if(等级2==0){
            发送(qun,"[AtQQ="+uin+"]   \n被选中位置["+小窝序号+"]，暂无宠物！",true);
          }else if(战力.contains("-")){
            发送(qun,玩家名(qun,uin)+" 您的当前宠物已超出计算范围啦，无法进行吞噬",true);
          }else if(战力.contains("EE")&&result>=600){
           发送(qun,玩家名(qun,uin)+" 您的当前宠物战力已超出600EE，无法进行吞噬",true);
          }else if(other战力.contains("-")){
            发送(qun,玩家名(qun,uin)+" 位置["+小窝序号+"]宠物战力已超出计算范围啦，无法进行吞噬",true);
          }else if(other战力.contains("EE")&&otherResult>=600){
           发送(qun,玩家名(qun,uin)+" 位置["+小窝序号+"]宠物战力已超出600EE，无法进行吞噬",true);
          }else if(战力.contains("EE")&&other战力.contains("EE")&&totalPower>=920){
            发送(qun,玩家名(qun,uin)+" 两个位置的宠物战力相加将超出920EE，不允许发动吞噬！",true);
          }else{
          // ↓当前出战宠物属性
          String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; 
          long 攻击 = 文转(getString(对象, "攻击"));
          long 防御 = 文转(getString(对象, "防御"));
          long 智力 = 文转(getString(对象, "智力"));
          long 精力上限 = 文转(getString(对象, "精力上限"));
          long 生命上限 = 文转(getString(对象, "生命上限"));
          
          // ↓被选中位置的宠物属性
          String 堆箱=qun+"/"+uin+"/宠物小窝/位置_"+小窝序号;
          long 攻击2 = 文转(getString(堆箱, "攻击"));
          long 防御2 = 文转(getString(堆箱, "防御"));
          long 智力2 = 文转(getString(堆箱, "智力"));
          long 精力上限2 = 文转(getString(堆箱, "精力上限"));
          long 生命上限2 = 文转(getString(堆箱, "生命上限"));
          
          //将“被选中位置”宠物属性，加到“出战宠物”身上
          long 攻击变化 = 加(攻击, 攻击2);
          long 防御变化 = 加(防御, 防御2);
          long 智力变化 = 加(智力, 智力2);
          long 精力变化 = 加(精力上限, 精力上限2);
          long 生命变化 = 加(生命上限, 生命上限2);
          long 战 = 加(攻击2,防御2);
          long 力 = 加(除(生命上限2,10),乘(智力2,20));
          String 名字1 = getString(对象, "昵称");
          String 名字2 = getString(堆箱, "昵称");
          
         putString(对象, "攻击", 转文(攻击变化));
         putString(对象, "防御", 转文(防御变化));
         putString(对象, "智力", 转文(智力变化));
         putString(对象, "精力上限", 转文(精力变化));
         putString(对象, "生命上限", 转文(生命变化));
         if((道具-1)>=1){
          putString(前置, "吞噬卡", 转文((道具-1)));
         }else{
           String 背包 = getString(qun + "/" + uin + "/我的背包", "道具列表");
           String 备=背包.replaceAll("、吞噬卡","");
           String 北=备.replaceAll("吞噬卡、","");
           // 先判断是否为null
           if (北 == null) {
            // 处理null情况
            putString(前置,"道具列表",null);
            putString(前置,"吞噬卡",null);
           } else {
            // 去除首尾空格后判断是否为空字符串或"null"
            String trimStr = 北.trim();
            if ("".equals(trimStr) || "null".equals(trimStr.toLowerCase())) {
              putString(前置,"道具列表",null);
              putString(前置,"吞噬卡",null);
            } else {
              putString(前置,"道具列表",北);
              putString(前置,"吞噬卡",null);
          }
         }
        }
       
         //判断该宠物是否为“定制”宠物，并且不在petMap里
         //如果是则删除图片缓存、删除定制记录
        String 原名=getString(堆箱,"宠物名字");
        String 品质=getString(堆箱,"级别");
        String 易容 = getString(堆箱, "是否易容");
        
        if(!petMap.containsKey(原名)&&品质.equals("定制")){
          String 定制记录 = getString("定制宠物", "宠物列表");

          boolean containsNickname = false; //标记

          if (定制记录 != null && !"null".equals(定制记录) && !"".equals(定制记录.trim()))
          {
            // 按"、"分割定制记录
            String[] 宠物数组 = 定制记录.split("、");
            // 遍历处理每个分割后的子字符串
            for (String petItem : 宠物数组) {
                // 对每个子字符串按","分割
                String[] items = petItem.split(",");
                
                // 遍历判断是否包含nickname
                for (String item : items) {
                    if (item.equals(原名)) {
                        containsNickname = true;
                        break;
                    }
                }
                if (containsNickname) {
                    break;
                }
            }
          }
          if(containsNickname){
            if (定制记录.isEmpty()) {
             return; // 列表为空，无需删除
            }
            String[] petArray = 定制记录.split("、");
            StringBuilder newPetList = new StringBuilder();
            for (String pet : petArray) {
              if (!pet.startsWith(原名 + ",")) {
                if (newPetList.length() > 0) {
                  newPetList.append("、");
                }
                newPetList.append(pet);
              }
            }
            String result = newPetList.toString();
            if (result.length() == 0) {
              删除文件(缓存路径+"定制宠物"); //删除文件
            } else {
             putString("定制宠物", "宠物列表", newPetList.toString());
             //更新定制记录（去掉放生宠物后）
            }
            删除文件(AppPath+"/目录/图片/宠物/定制/"+原名+".jpg");
          }
        }else if(易容.equals("是")){
          //删除对应易容图片
          String 上传图 = getString(堆箱, "上传图片");
          删除文件(AppPath+"/目录/图片/其他/玩家上传/"+uin+"/"+qun+"/"+上传图+".png");
        }
           删除文件(缓存路径+堆箱); //这个位置宠物已被吞噬，腾出位置
           宠物回血(qun, uin, 1); //给宠物血量恢复至100%
          发送(qun,"[PicUrl="+AppPath+"/目录/图片/其他/吞噬.jpg]"+"[AtQQ="+uin+"]   \n你的「"+名字1+"」成功吞噬了「"+名字2+"」！\n消耗道具：吞噬卡×1\n————————\n生命：+"+数值转(生命上限2)+"\n精力：+"+数值转(精力上限2)+"\n攻击：+"+数值转(攻击2)+"\n防御：+"+数值转(防御2)+"\n智力：+"+数值转(智力2)+"\n————————\n战力：+"+数值转(加(战,力)),true);
         }
        }else if(小窝序号==0){
          发送(qun,"[AtQQ="+uin+"]   \n\n吞噬位置不能为[0]！\n示例：宠物吞噬"+随机数(1,99),true);
        }else if(数量<小窝序号){
          发送(qun,"[AtQQ="+uin+"]   目前您的小窝位置只有序号1-"+数量+"号位置，其他位置还未解锁～\n指令：使用小窝进阶卡",true);
        }
       }
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
  }
  
  //从petMap与定制记录里搜索宠物，并返回信息
  if(消息.startsWith("宠物图鉴")){
     String 关键词;
    
     //截取指定类型或道具
     if(消息.length()>=5){
       关键词=消息.substring(4);
     }
     
     //提前记录时间戳
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     
     // 去掉指令前缀之后没有内容时
     if(关键词==null||关键词.trim().length()==0){
        String 指令头=消息.substring(0, 4);
        String 文本;
        Set keys = petMap.keySet();
        if (keys.isEmpty()) {
           Toast("警告：petMap为空，将会影响部分指令运行");
           文本="派蒙"; //keys为空，返回"派蒙"
        }else{
          String[] keyArray = keys.toArray(new String[0]);
          Random random = new Random();
          int randomIndex = random.nextInt(keyArray.length);
          文本=keyArray[randomIndex];
        }
           发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n •格式："+指令头+"+宠物名称\n •示例："+指令头+文本,true);
          return; //不执行后续逻辑
     }
     
    // 搜索宠物（检查是否存在于 petMap或定制记录 里）
    String[] petInfo = searchPet(关键词); //进行搜索
    if (petInfo != null) {
       String 属性=petInfo[0];
       String 性别=petInfo[1];
       String 品质=petInfo[2];
       long[] petAttributes = petAttributeMap.get(品质); //品质对应的属性
       long 进化次数 = petAttributes[4]; //获取进化次数
       预览(qun, uin, 属性, 性别, 品质, 关键词);
       发送(qun,玩家名(qun,uin)+ " 详情如下：\n •名称："+关键词+"（"+性别+"）\n •级别："+品质+"（"+进化次数+"次进化） [PicUrl=" + AppPath + "/缓存/宠物/" + uin + "_宠物预览图.png]",false);
    } else {
      发送(qun,"[AtQQ="+uin+"]   失败！\n●未找到名为（"+关键词+"）的宠物\n•请检查是否输入完整",true);
    }
  }
  
  //从petMap与定制记录里搜索包含特定内容的宠物，并返回拼接后的信息
  if(消息.startsWith("宠物搜索")){
     String 关键词;
    
     //截取指定类型或道具
     if(消息.length()>=5){
       关键词=消息.substring(4);
     }
     
     //提前记录时间戳
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     
     // 去掉指令前缀之后没有内容时
     if(关键词==null||关键词.trim().length()==0){
        String 指令头=消息.substring(0, 4);
        String 文本;
        Set keys = petMap.keySet();
        if (keys.isEmpty()) {
           Toast("警告：petMap为空，将会影响部分指令运行");
           文本="派蒙"; //keys为空，返回"派蒙"
        }else{
          String[] keyArray = keys.toArray(new String[0]);
          Random random = new Random();
          int randomIndex = random.nextInt(keyArray.length);
          文本=keyArray[randomIndex];
        }
           发送(qun,"[AtQQ="+uin+"]   指令格式错误！\n •格式："+指令头+"+内容\n •示例："+指令头+文本,true);
          return; //不执行后续逻辑
     }
     
    // 搜索名称包含指定内容的宠物
    String result = searchPetsWithIndex(关键词);
    if (result != null&&!result.contains("未找到")) {
       String 内容 = 玩家名(qun,uin)+ " ("+uin+")"+result+" •查看：宠物图鉴+名称\n// Time："+getTodayDate(2);
       toImg(内容, "", 0.5, 0.01, 35, AppPath+"/缓存/其他/"+uin+"_宠物搜索.png",true);
       发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+uin+"_宠物搜索.png]",false);
    } else {
      发送(qun,"[AtQQ="+uin+"]   搜索失败！\n●未找到名称里含有（"+关键词+"）的宠物\n•可输入其他内容重试",true);
    }
  }
  
  //消耗10精力，可探查目标宠物信息
   if(消息.startsWith("宠物侦查"))
   {
       String 状态 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "状态");
       String 闭关 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "闭关");
       long 心情 = 文转(getString(配置名称, "心情"));
       long 等级 = 文转(getString(配置名称, "等级"));
       long 当前精力 = 文转(getString(配置名称, "当前精力"));
       
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       
     if(宠物名==null||宠物名.isEmpty()) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
     }else if(当前精力<10) {
       发送(qun,"[AtQQ="+uin+"]   你的宠物「"+宠物名+"」精力不足[10]！",true);
     }
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(4);
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去掉非数字字符
        目标 = 目标.replaceAll("[^\\d]", "");
       }
       String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(目标)){
           发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+目标+"]的成员！\n◆请检查是否输入错误！",true);
           return;
        }
       String 堆箱=qun+"/"+目标+"/宠物小窝/位置_0";
       String 宠物名2=getString(qun+"/"+目标+"/宠物小窝/位置_0","昵称");
       if(宠物名2==null||宠物名2.isEmpty()) {
        发送(qun,"[AtQQ="+uin+"]   对方当前还没有宠物哦！",true);
       }else{
         putString(配置名称,"当前精力",转文(减(当前精力,10)));
         当前宠物(qun,目标,0);
         发送(qun,"[AtQQ="+uin+"]   侦查成功,精力-10,您侦查的宠物信息如下：[PicUrl="+AppPath+"/缓存/宠物/"+目标+"_宠物图.png]",false);
       }
     } else {
       发送(qun,"[AtQQ="+uin+"]  疑似指令参数缺失！\n◆指令：宠物侦查@玩家\n◆指令：宠物侦查+QQ号",true);       
     }
   }  
   
   // 攻击别人的宠物
   if(消息.startsWith("宠物攻击"))
   {
     String 对象=qun+"/"+uin+"/宠物小窝/位置_0";
     String 昵称 = getString(对象, "昵称");
     String 属性 = getString(对象, "属性");
     String 天赋 = getString(对象, "天赋");
     String 状态 = getString(对象, "状态");
     String 闭关 = getString(对象, "闭关");
     long 等级1 = 文转(getString(对象, "等级"));
     long 心情 = 文转(getString(对象, "心情"));
     long 攻击 = 文转(getString(对象, "攻击"));
     long 防御 = 文转(getString(对象, "防御"));
     long 智力 = 文转(getString(对象, "智力"));
     long 当前经验 = 文转(getString(对象, "当前经验"));
     long 当前生命 = 文转(getString(对象, "当前生命"));
     long 生命上限 = 文转(getString(对象, "生命上限"));
     
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
     
     if(等级1<1) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
     }else if(等级1!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }
     // 宠物攻击+QQ号（示例：宠物攻击1008611）
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(4);
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去除非数字字符
        目标 = 目标.replaceAll("[^\\d]", "");
       }
       String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(目标)){
           发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+目标+"]的成员！\n◆请检查是否输入错误！",true);
           return;
        }
       String 堆箱=qun+"/"+目标+"/宠物小窝/位置_0";
       String 状态2 = getString(堆箱, "状态");
       long 等级2 = 文转(getString(堆箱, "等级"));
       if(等级2<1) {
         发送(qun,"[AtQQ="+uin+"]  \n ["+目标+"]当前还没有宠物，无法对其发起攻击",true);
       }else if(状态2.equals("死亡")){
        发送(qun,"[AtQQ="+uin+"]   \n["+目标+"]的宠物已死亡，无法对其宠物发起攻击！",true);
       }else{
            String 天赋2 = getString(堆箱, "天赋");
            String 昵称2 = getString(堆箱, "昵称");
            String 属性2 = getString(堆箱, "属性");
            long 心情2 = 文转(getString(堆箱, "心情"));
            long 攻击2 = 文转(getString(堆箱, "攻击"));
            long 防御2 = 文转(getString(堆箱, "防御"));
            long 智力2 = 文转(getString(堆箱, "智力"));
            long 当前生命2 = 文转(getString(堆箱, "当前生命"));
            String 天赋描述="";
            String 天赋描述2="";
            //攻击方天赋加成
            if(天赋.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击 * 0.3);  
              攻击 = 攻击 + 攻击加成;  //攻击方的攻击力(含天赋加成)
              天赋描述="\n我方天赋：["+天赋+"]，攻击+30%";
            }
            else if(天赋.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御 * 0.3);  
              防御 = 防御 + 防御加成;  //攻击方的防御力(含天赋加成)
              天赋描述="\n我方天赋：["+天赋+"]，防御+30%";
            }else if(天赋.equals("疫病之源")){
              天赋描述="\n我方天赋：["+天赋+"]，55%概率使目标感染异常状态";
            }
                        
            //防御方天赋加成
            if(天赋2.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击2 * 0.3);  
              攻击2 = 攻击2 + 攻击加成;  //防御方的攻击力(含天赋加成)
              天赋描述2="\n对方天赋：["+天赋2+"]，攻击+30%";
            }
            else if(天赋2.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御2 * 0.3);  
              防御2 = 防御2 + 防御加成;  //防御方的防御力(含天赋加成)
              天赋描述2="\n对方天赋：["+天赋2+"]，防御+30%";
            }else if(天赋2.equals("疫病之源")){
              天赋描述2="\n对方天赋：["+天赋+"]，55%概率使目标感染异常状态";
            }
            
            
            //相关数值计算
            long 攻击方输出=伤害计算(攻击, 智力, 防御2, true, 0.9f, 1.1f);
            long 防御方输出=伤害计算(攻击2, 智力2, 防御, true, 0.9f, 1.1f);
            long 生命变化=减(当前生命,防御方输出);
            long 生命变化2=减(当前生命2,攻击方输出);
            
         if(生命变化<1){
           putString(对象, "当前生命", "0");
           putString(对象, "状态", "死亡");
           putString(对象, "心情", 转文(心情-10)); //扣心情值
         }else{
           putString(对象, "当前生命", 转文(生命变化));
         }
         if(生命变化2<1){
           putString(堆箱, "当前生命", "0");
           putString(堆箱, "状态", "死亡");
           putString(堆箱, "心情", 转文(心情2-10)); //扣心情值
         }else{
           putString(堆箱, "当前生命", 转文(生命变化2));
         }
         long 等级差=减(等级1,等级2);
         if(等级差==0){等级差=50;} //保证能够有0.1%经验加成
         long 经验值 = (long) (Math.random() * (99999 - 55000 + 1) + 55000); 
         // 生成55000-99999随机经验值
        
        // 计算经验加成比例（每50级0.1%，向下取整计算总段数）
        double 等级段数 = (double)等级差 / 50;
        if (等级段数 <= 0) { //小于等于0时，强制为0.001
            等级段数 = 0.001;  // 可调整最小值
        }
        double 加成比例 = 等级段数 * 0.1 / 100; // 转换为小数比例（0.1%=0.001）
        
        // 计算经验变化（包含加成）
        long 经验变化 = (long) Math.floor(当前经验 + 经验值 * (1 + 加成比例)); //向下取整(去除小数部分)
       
         String 结果="你的宠物摸了对方一下";
         if(生命变化>=1&&生命变化2>=1){
           结果="你的宠物摸了对方一下";
           // 异常状态判定
          if(天赋.equals("疫病之源") && 攻击方输出 > 0){
              // 30%概率触发
              if(Math.random() <= 0.55){
                  // 随机异常状态列表
                  String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                  String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                  putString(堆箱, "状态", 随机状态);
                  结果 += "\n→ 触发效果：使对方"+昵称2+"感染【"+随机状态+"】";
              }
          }
          if(天赋2.equals("疫病之源") && 防御方输出 > 0){
              if(Math.random() <= 0.55){
                  String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                  String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                  putString(对象, "状态", 随机状态);
                  结果 += "\n→ 触发效果：使我方"+昵称+"感染【"+随机状态+"】";
              }
          }
         }
         else if(生命变化>=1&&生命变化2<1){
           结果="你的宠物*直接KO对方宠物*\n●经验：+"+经验变化;
           putString(对象, "当前经验", 转文(经验变化));
           // 异常状态判定
          if(天赋2.equals("疫病之源") && 防御方输出 > 0){
              if(Math.random() <= 0.55){
                  String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                  String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                  putString(对象, "状态", 随机状态);
                  结果 += "\n→ 触发效果：使我方"+昵称+"感染【"+随机状态+"】";
              }
          }
         }
         else if(生命变化<1&&生命变化2<1){
           结果="*双方宠物同归于尽*(＊ﾟﾛﾟ)!!";
         }
         else if(生命变化<1&&生命变化2>=1){
           结果="你的宠物直接*被对方宠物KO了*";
           // 异常状态判定
          if(天赋.equals("疫病之源") && 攻击方输出 > 0){
              // 30%概率触发
              if(Math.random() <= 0.55){
                  // 随机异常状态列表
                  String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                  String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                  putString(堆箱, "状态", 随机状态);
                  结果 += "\n→ 触发效果：使对方"+昵称2+"感染【"+随机状态+"】";
              }
          }
         }
         String 文案=玩家名(qun,uin)+" ("+uin+")\n\n<分割>【 "+昵称+"  VS  "+昵称2+" 】</分割>\n属性: ["+属性+"] -- ["+属性2+"]"+天赋描述+天赋描述2+"\n"+结果+"\n<填充>\n对方血量扣除：-"+数值转(攻击方输出)+"\n我方血量扣除：-"+数值转(防御方输出)+"\n对方剩余血量："+数值转(生命变化2)+"\n我方剩余血量："+数值转(生命变化);
         toImg(文案,"",0.5,0.03,35,AppPath+"/缓存/其他/"+uin+"_宠物攻击.png",true);
         发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"_宠物攻击.png]",false);
       }       
     }
     else{
       发送(qun,"[AtQQ="+uin+"]   \n疑似指令出错或参数不全！\n--------------\n•指令1：宠物攻击@玩家\n•指令2：宠物攻击+QQ号",true);
     }
   }
   
   //将宠物赠送给他人（限制宠物战力在1500W以下才能送）
   if(消息.startsWith("宠物赠送"))
   {
       String 状态 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "状态");
       String 闭关 = getString(qun+"/"+uin+"/宠物小窝/位置_0", "闭关");
       String 成员 = 消息.substring(4);
       long 心情 = 文转(getString(配置名称, "心情"));
       long 等级 = 文转(getString(配置名称, "等级"));
       
       userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
       
     if(宠物名==null||宠物名.isEmpty()) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(状态.equals("死亡")) {
       发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
     }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }
     
     //可通过对方账号来进行宠物赠送
     else if(!消息.contains("@")&&消息.length()>=9)
     {
       // 判断目标是否为纯数字
       String 目标=消息.substring(4);
       boolean isPureNumber = 目标.matches("\\d+");
       if (!isPureNumber) {
        // 去掉非数字字符
        目标 = 目标.replaceAll("[^\\d]", "");
       }
       
        String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(目标)){
           发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+目标+"]的成员！\n◆请检查是否输入错误！",true);
           return;
        }

       String 堆箱=qun+"/"+目标+"/宠物小窝/位置_0";
       String 宠物名2=getString(qun+"/"+目标+"/宠物小窝/位置_0","昵称");
       long 道具=文转(getString(qun+"/"+uin+"/我的背包","宠物赠送卡"));
       long 积分呀 = 文转(getString(qun+"/"+目标+"/我的资产", "积分"));
       if(道具<1) {
        发送(qun,"[AtQQ="+uin+"]   \n您当前背包中【宠物赠送卡】不足1张",true);
       }else if(计算战力(qun,uin,0)>15000000){
         发送(qun,"[AtQQ="+uin+"]   你的【"+宠物名+"】战力超过1500W，无法赠送！",true);
       }else if(宠物名2!=null) {
        发送(qun,"[AtQQ="+uin+"]   对方当前已有宠物哦！",true);
       }else if(积分呀<1){
         发送(qun,"[AtQQ="+uin+"]   该账号不存在或无积分数据,无法进行宠物赠送!\n◇指令:宠物赠送+对方QQ\n◇指令:宠物赠送@对方",true);
       }else{
          String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; //出战宠物所在位置
          String 心情 = getString(对象, "心情");
          String 性别 = getString(对象, "性别");
          String 婚况 = getString(对象, "婚姻状况");
          String 属性 = getString(对象, "属性");
          String 状态 = getString(对象, "状态");
          long 等级 = 文转(getString(对象, "等级"));
          long 攻击 = 文转(getString(对象, "攻击"));
          long 防御 = 文转(getString(对象, "防御"));
          long 智力 = 文转(getString(对象, "智力"));
          String 神器 = getString(对象, "神器");
          String 级别 = getString(对象, "级别");
          String 天赋 = getString(对象, "天赋");
          String 阶段 = getString(对象, "阶段");
          String 昵称 = getString(对象, "昵称");
          String 名字 = getString(对象, "宠物名字");
          String 易容 = getString(对象, "是否易容");
          String 上传图 = getString(对象, "上传图片");
          long 进化层次 = 文转(getString(对象, "进化层次"));
          long 进化上限 = 文转(getString(对象, "进化上限"));
          long 当前精力 = 文转(getString(对象, "当前精力"));
          long 精力上限 = 文转(getString(对象, "精力上限"));
          long 当前生命 = 文转(getString(对象, "当前生命"));
          long 生命上限 = 文转(getString(对象, "生命上限"));
          long 当前经验 = 文转(getString(对象, "当前经验"));
          long 下级经验 = 文转(getString(对象, "下级所需经验"));
         String 堆箱=qun+"/"+目标+"/宠物小窝/位置_0"; //对方当前宠物位置
         if(!状态.equals("正常")){
           发送(qun,"[AtQQ="+uin+"]   你的宠物状态异常，无法进行此操作！",true);
         }else{
           putString(堆箱, "心情", 心情);
           putString(堆箱, "等级", 转文(等级));
           putString(堆箱, "攻击", 转文(攻击));
           putString(堆箱, "防御", 转文(防御));
           putString(堆箱, "智力", 转文(智力));
           putString(堆箱, "当前精力", 转文(当前精力));
           putString(堆箱, "精力上限", 转文(精力上限));
           putString(堆箱, "当前生命", 转文(当前生命));
           putString(堆箱, "生命上限", 转文(生命上限));
           putString(堆箱, "当前经验", 转文(当前经验));
           putString(堆箱, "下级所需经验", 转文(下级经验));
           putString(堆箱, "进化层次", 转文(进化层次));
           putString(堆箱, "进化上限", 转文(进化上限));
           putString(堆箱, "昵称", 昵称);
           putString(堆箱, "宠物名字", 名字);
           putString(堆箱, "性别", 性别);
           putString(堆箱, "属性", 属性);
           putString(堆箱, "阶段", 阶段);
           putString(堆箱, "级别", 级别);
           putString(堆箱, "状态", 状态);
           putString(堆箱, "神器", 神器);
           putString(堆箱, "天赋", 天赋);
           putString(堆箱, "婚姻状况", "单身");
           putString(堆箱, "是否易容", 易容);
           putString(堆箱, "上传图片", 上传图);
         if((道具-1)>=1){
          putString(qun+"/"+uin+"/我的背包", "宠物赠送卡", 转文((道具-1)));
         }else{
           String 背包 = getString(qun+"/"+uin+"/我的背包","道具列表");
           String 备=背包.replaceAll("、宠物赠送卡","");
           String 北=备.replaceAll("宠物赠送卡、","");
           // 先判断是否为null
           if (北 == null) {
            // 处理null情况
            putString(qun+"/"+uin+"/我的背包","道具列表",null);
            putString(qun+"/"+uin+"/我的背包","宠物赠送卡",null);
           } else {
            // 去除首尾空格后判断是否为空字符串或"null"
            String trimStr = 北.trim();
            if ("".equals(trimStr) || "null".equals(trimStr.toLowerCase())) {
              putString(qun+"/"+uin+"/我的背包","道具列表",null);
              putString(qun+"/"+uin+"/我的背包","宠物赠送卡",null);
            } else {
              putString(qun+"/"+uin+"/我的背包","道具列表",北);
              putString(qun+"/"+uin+"/我的背包","宠物赠送卡",null);
          }}}
           删除文件(缓存路径+对象); //这个位置的宠物已赠送出去了，腾出位置
           发送(qun,"[AtQQ="+uin+"]   成功将"+昵称+"(LV·"+等级+"级)赠送给【@"+getMemberName(qun,(目标))+" 】!\n扣除道具：宠物赠送卡×1",true);
         }
       }
     } else {
       发送(qun,"[AtQQ="+uin+"]  疑似指令参数缺失！\n◆指令：宠物赠送@玩家\n◆指令：宠物赠送+QQ号",true);       
     }
   }
   
// 宠物向对方宠物发起求婚
if (消息.startsWith("宠物求婚")) {
    String 对象 = qun + "/" + uin + "/宠物小窝/位置_0";
    String 婚况 = getString(对象, "婚姻状况");
    String 对方 = getString(对象, "结婚对象");
    String 状态 = getString(对象, "状态");
    String 闭关 = getString(对象, "闭关");
    long 心情 = 文转(getString(配置名称, "心情"));
    long 等级 = 文转(getString(配置名称, "等级"));
    
    userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
    if (宠物名 == null || 宠物名.isEmpty()) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
    } else if (状态.equals("死亡")) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    } else if (婚况.equals("已婚")) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 你的宠物已经和【" + 对方 + "】的宠物结婚啦！不能滥情哦！",true);
    }
  
      //使用账号进行宠物求婚
    else if (!消息.contains("@") && 消息.length() >= 9) {
        String 目标 = 消息.substring(4);
        // 正则匹配纯数字并过滤非数字字符
        目标 = 目标.replaceAll("[^\\d]", ""); 
        
        String 群列表 = getGroupMembersUin(qun); 
        if(群列表!=null&&!群列表.contains(目标)){
           发送(qun,"[AtQQ="+uin+"]   本群没有QQ为["+目标+"]的成员！\n◆请检查是否输入错误！",true);
           return;
        }

        String 堆箱 = qun + "/" + 目标 + "/宠物小窝/位置_0";
        String 宠物名2 = getString(qun + "/" + 目标 + "/宠物小窝/位置_0", "昵称");
        String 婚况2 = getString(堆箱, "婚姻状况");
        String 求婚方 = getString(qun + "/" + 目标 + "/状态", "求婚方");
        boolean 求婚状态 = getBoolean(qun + "/" + 目标 + "/状态", "宠物求婚", false);
        long 道具 = 文转(getString(qun + "/" + uin + "/我的背包", "永恒之戒"));
        if (宠物名2 == null || 宠物名2.isEmpty()) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 对方当前还没有宠物哦！",true);
        } else if (婚况2.equals("已婚")) { 
            发送(qun, "[AtQQ="+uin+"]  "+ " 对方的【" + 宠物名2 + "】不是单身哦！",true);
        } 
        else if (求婚方 != null && 求婚方.equals(uin)) { 
            发送(qun, "[AtQQ="+uin+"]  "+ " 请勿重复提交请求，请耐心等待【@" + 目标 + "】同意求婚！\n\n•同意：同意求婚\n•拒绝：拒绝求婚",true);
        } else if (求婚状态) { 
            发送(qun, "[AtQQ="+uin+"]  "+ " 对方当前已被【@" + 求婚方 + "】抢先一步求婚了！",t);
        } else if (道具 < 1) {
            发送(qun, "[AtQQ="+uin+"]  "+ " \n您当前背包中【永恒之戒】不足1颗",true);
        } else {
            // 更新双方求婚状态
            putBoolean(qun + "/" + 目标 + "/状态", "宠物求婚", true);
            putString(qun + "/" + 目标 + "/状态", "求婚方", uin);
            putString(qun + "/" + 目标 + "/状态", "被求婚方", 目标);
            putString(qun + "/" + uin + "/状态", "求婚方", uin);
            putString(qun + "/" + uin + "/状态", "被求婚方", 目标);
            if ((道具 - 1) >= 1) {
                putString(qun + "/" + uin + "/我的背包", "永恒之戒", 转文((道具 - 1)));
            } else {
                String 背包 = getString(qun + "/" + uin + "/我的背包", "道具列表");
                String 备 = 背包 != null ? 背包.replaceAll("、永恒之戒", "").replaceAll("永恒之戒、", "") : ""; // 合并替换操作并处理null
                if (备.trim().isEmpty()) {
                    putString(qun + "/" + uin + "/我的背包", "道具列表", null);
                    putString(qun + "/" + uin + "/我的背包", "永恒之戒", null);
                } else {
                    putString(qun + "/" + uin + "/我的背包", "道具列表", 备);
                    putString(qun + "/" + uin + "/我的背包", "永恒之戒", null);
                }
            }
            发送(qun, "[AtQQ="+uin+"]  "+ " \n——————\n消耗：永恒之戒×1\n向【@" + getMemberName(qun, (目标)) + "】发起了求婚！\n请耐心等待对方同意～\n——————\n•指令：同意求婚\n•指令：拒绝求婚",true);
        }
    } else {
        发送(qun, "[AtQQ="+uin+"]  "+ "疑似指令参数缺失！\n◆指令：宠物求婚@玩家\n◆指令：宠物求婚+QQ号",true);
    }
}  
  
   //同意他人的宠物结婚请求
   if(消息.equals("同意求婚"))
   {
     String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; 
     String 婚况 = getString(对象, "婚姻状况");
     String 对方 = getString(对象, "结婚对象");
     String 求婚方=getString(qun+"/"+uin+"/状态","求婚方");
     String 被求婚方=getString(qun+"/"+uin+"/状态","被求婚方");
     String 堆箱=qun+"/"+求婚方+"/宠物小窝/位置_0"; 
     boolean 状态 = getBoolean(qun+"/"+uin+"/状态", "宠物求婚", false);
     if(宠物名==null||宠物名.isEmpty()){
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(消息.equals("已婚")){
       发送(qun,"[AtQQ="+uin+"]   你的宠物已经和【"+对方+"】的宠物结婚啦！不能滥情哦！",true);
     }else if(求婚方==null||被求婚方==null||状态==false){
       发送(qun,"[AtQQ="+uin+"]   你的宠物当前还没有人发起求婚～",true);
     }else if(求婚方.equals(uin)){
       发送(qun,"[AtQQ="+uin+"]   你是求婚发起人，请耐心等待【@"+被求婚方+"】同意求婚！\n\n•同意：同意求婚\n•拒绝：拒绝求婚",true);
     }else{
           发送(qun,"[PicUrl="+AppPath+"/目录/图片/其他/喜结连理.jpg]恭喜@"+求婚方+" 的〔"+getString(堆箱, "昵称")+"〕与@"+被求婚方+" 的〔"+getString(对象, "昵称")+"〕喜结连理！\n……………………\nįįįĮĮĮ\n愿往后朝夕与共，冷暖相知，风雨同舟，共度余生。",false);
         //更新双方宠物结婚状态
         putBoolean(qun+"/"+uin+"/状态", "宠物求婚", false);
         putString(对象,"婚姻状况","已婚");
         putString(对象,"结婚对象",求婚方);
         putString(堆箱,"婚姻状况","已婚");
         putString(堆箱,"结婚对象",被求婚方);
         putString(qun+"/"+uin+"/状态","求婚方",null);
         putString(qun+"/"+uin+"/状态","被求婚方",null);
         putString(qun+"/"+求婚方+"/状态","求婚方",null);
         putString(qun+"/"+求婚方+"/状态","被求婚方",null);
     }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));     
   }
   
   //拒绝他人的宠物结婚请求
   if(消息.equals("拒绝求婚"))
   {
     String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; 
     String 求婚方=getString(qun+"/"+uin+"/状态","求婚方");
     String 被求婚方=getString(qun+"/"+uin+"/状态","被求婚方");
     String 堆箱=qun+"/"+求婚方+"/宠物小窝/位置_0"; 
     boolean 状态 = getBoolean(qun+"/"+uin+"/状态", "宠物求婚", false);
     if(宠物名==null||宠物名.isEmpty()) {
       发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
     }else if(求婚方==null||被求婚方==null||状态==false){
       发送(qun,"[AtQQ="+uin+"]   你的宠物当前还没有未处理的求婚请求～",true);
     }else{
         //更新双方宠物结婚状态
         putBoolean(qun+"/"+uin+"/状态", "宠物求婚", false);
         putString(qun+"/"+uin+"/状态","求婚方",null);
         putString(qun+"/"+uin+"/状态","被求婚方",null);
         putString(qun+"/"+求婚方+"/状态","求婚方",null);
         putString(qun+"/"+求婚方+"/状态","被求婚方",null);
        发送(qun,"[AtQQ="+uin+"]   你成功撤销了此次求婚！",true);
     }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
//获取该群的宠物战榜（从高到底排序）
if (消息.equals("宠物战榜")) 
{
    String 群列表 = getString(qun + "/玩家列表", "列表");
    
    if (群列表 == null ||群列表.equals("null")) { 
        发送(qun, "[AtQQ="+uin+"]  "+ " 本群暂无玩家，无法绘制战榜图\n可发送【领新手礼包】成为首位玩家！",true);
    } else {
        String 头像路径 = AppPath + "/缓存/头像/" + qun + ".png";
        String 群聊头像 = "https://p.qlogo.cn/gh/"+qun+"/"+qun+"/640";
        File 呆河马 = new File(头像路径);
        if (!呆河马.exists()) {
        try {
                downloadFile(群聊头像, 头像路径);
                //↑保存该群头像；绘制战榜图片时将会用到
            } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("群聊「" + qun + "」头像下载失败");
            }
        }
        String[] 玩家列表 = 群列表.split("、");
        Map playerPowerMap = new HashMap(); // 存储格式：账号-战力
        
        for (String 玩家账号 : 玩家列表) {
            long 战力 = 计算战力(qun, 玩家账号, 0); 
            if (战力 != 0) {
                playerPowerMap.put(玩家账号, 战力);
            }
        }
        
        // 传统 Comparator 实现（非 Lambda 写法）
        List sortedList = new ArrayList(playerPowerMap.entrySet());
        sortedList.sort(new Comparator() { 
            public int compare(Map.Entry o1, Map.Entry o2) {
                // 降序排序：o2.getValue() - o1.getValue()
                return Long.compare(o2.getValue(), o1.getValue()); 
            }
        });
        
        int endIndex = Math.min(sortedList.size(), 10);
        List topTenList = sortedList.subList(0, endIndex); // 取前十
        
        宠物战榜(qun, topTenList); //按取得的战力前十，来绘制战榜图片
        发送(qun, "[PicUrl="+AppPath + "/缓存/其他/" + qun + "_宠物战榜.png]",false);
    }
        userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}

//获取全部玩家的宠物战力进行比较，然后绘制神榜图（从高到底排序）
if (消息.equals("宠物神榜")) {
    String 玩家列表 = getString("玩家总列表", "总列表");
    if (玩家列表 == null ||玩家列表.equals("null")) { 
        发送(qun, "[AtQQ="+uin+"]  "+ " 全服暂无玩家拥有宠物，无法绘制神榜图片\n可发送【领新手礼包】成为首位玩家！",true);
    } else {
        String 群列表 = getString("开放群列表", "开放列表");
        String[] 群组列表 = 群列表.split("、");
        // 记录玩家最高战力及其对应群号，后续会以最高战力来排序
        Map playerMaxPower = new HashMap(); // 玩家账号 -> 最高战力
        Map playerMaxGroup = new HashMap(); // 玩家账号 -> 最高战力群号

        for (String 群号 : 群组列表) {
            String 群玩家 = getString(群号 + "/玩家列表", "列表");
            if (群玩家 != null) {
                String[] 玩家列表 = 群玩家.split("、");
                for (String 玩家账号 : 玩家列表) {
                    long 战力 = 计算战力(群号, 玩家账号, 0);
                    if (战力 != 0) {
                        // 对比当前玩家是否已有更高战力
                        if (!playerMaxPower.containsKey(玩家账号) || 战力 > playerMaxPower.get(玩家账号)) {
                            playerMaxPower.put(玩家账号, 战力);
                            playerMaxGroup.put(玩家账号, 群号); // 记录最高战力对应的群号
                        }
                    }
                }
            }
        }

        // 重构数据：将玩家-群-战力组合为新的Map（群号-玩家账号 -> 战力）
        Map powerMap = new HashMap();
        for (String 玩家账号 : playerMaxPower.keySet()) {
            String key = playerMaxGroup.get(玩家账号) + "-" + 玩家账号;
            powerMap.put(key, playerMaxPower.get(玩家账号));
        }

        // 传统 Comparator 实现（非 Lambda 写法）
        List sortedList = new ArrayList(powerMap.entrySet());
        sortedList.sort(new Comparator() { 
            public int compare(Map.Entry o1, Map.Entry o2) {
                // 降序排序：o2.getValue() - o1.getValue()
                return Long.compare(o2.getValue(), o1.getValue()); 
            }
        });
        
        int endIndex = Math.min(sortedList.size(), 10);
        List topTenList = sortedList.subList(0, endIndex); // 取前十
        
        //下载保存神榜前十玩家的头像
        for (int i = 0; i < topTenList.size(); i++) 
        {
          Map.Entry entry = topTenList.get(i);
          String[] keyParts = entry.getKey().split("-");
          String 玩家账号 = keyParts[1]; // 提取玩家账号
    
          // 构造头像路径和下载URL
          String 头像路径 = AppPath+"/缓存/头像/"+玩家账号+".png";
          String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin="+玩家账号+"&spec=640";
         
           File 头像文件 = new File(头像路径);
           if (!头像文件.exists()) {
             try {
                  downloadFile(玩家头像, 头像路径);
              } catch (IOException e) {
                 e.printStackTrace();
                 Toast("玩家「"+玩家账号+"」头像下载失败");
              }
            }
          }
       
        宠物神榜(topTenList); //按取得的战力前十，来绘制神榜图片
        发送(qun, "[PicUrl="+AppPath + "/缓存/其他/宠物神榜.png]",false);
    }
        userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}

//将“击杀榜”与“被击杀榜”绘制到一张图里
if(消息.equals("宠物击杀榜"))
{
    String 玩家列表 = getString("玩家总列表", "总列表");
    if (玩家列表 == null ||玩家列表.equals("null")) { 
        发送(qun, "[AtQQ="+uin+"]  "+ " 当前还没有玩家哦，快发送【签到】成为首个玩家吧！\n\n然后发送【砸蛋十连】来挑选宠物",true);
    } else {
        String[] 账号列表 = 玩家列表.split("、");
        Map Kill = new HashMap(); // 玩家账号 -> 击杀数
        Map killed = new HashMap(); // 玩家账号 -> 被击杀数
        
        //获取相关数据
        for (String 账号 : 账号列表)
        {
              int 击杀数 = getInt("宠物击杀次数", 账号, 0);
              int 被击杀数 = getInt("宠物被击杀次数", 账号, 0);
              if (击杀数 != 0) {
                Kill.put(账号, 击杀数);
              }
              
              if (被击杀数 != 0) {
                 killed.put(账号, 被击杀数);
              }         
        }

        // 击杀榜——进行排序
        List sorted1 = new ArrayList(Kill.entrySet());
        sorted1.sort(new Comparator() { 
            public int compare(Map.Entry o1, Map.Entry o2) {
                return o2.getValue() - o1.getValue(); //降序排序

            }
        });
        
        int endIndex1 = Math.min(sorted1.size(), 10);
        List topTen1 = sorted1.subList(0, endIndex1); // 取前十

        //下载保存{击杀榜}前十玩家的头像
      if(!topTen1.isEmpty()) 
      {
        for (int i = 0; i < topTen1.size(); i++) 
        {
          Map.Entry entry = topTen1.get(i);
          String 玩家账号 = entry.getKey(); // 提取玩家账号
    
          // 构造头像路径和下载URL
          String 头像路径 = AppPath+"/缓存/头像/"+玩家账号+".png";
          String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin="+玩家账号+"&spec=640";
         
           File 头像文件 = new File(头像路径);
           if (!头像文件.exists()) {
             try {
                  downloadFile(玩家头像, 头像路径);
              } catch (IOException e) {
                 e.printStackTrace();
                 Toast("玩家「"+玩家账号+"」头像下载失败");
              }
            }
          }
        }
          
        // 被击杀榜——进行排序
        List sorted2 = new ArrayList(killed.entrySet());
        sorted2.sort(new Comparator() { 
            public int compare(Map.Entry o1, Map.Entry o2) {
                return o2.getValue() - o1.getValue(); //降序排序
            }
        });
        
        int endIndex2 = Math.min(sorted2.size(), 10);
        List topTen2 = sorted2.subList(0, endIndex2); // 取前十
        
        //下载保存{被击杀榜}前十玩家的头像
        //（账号与击杀榜的可能不一致，所以这一步不能省略）
      if (!topTen2.isEmpty()) 
      {
        for (int i = 0; i < topTen2.size(); i++) 
        {
          Map.Entry entry = topTen2.get(i);
          String 玩家账号 = entry.getKey(); // 提取玩家账号
    
          // 构造头像路径和下载URL
          String 头像路径 = AppPath+"/缓存/头像/"+玩家账号+".png";
          String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin="+玩家账号+"&spec=640";
         
           File 头像文件 = new File(头像路径);
           if (!头像文件.exists()) {
             try {
                  downloadFile(玩家头像, 头像路径);
              } catch (IOException e) {
                 e.printStackTrace();
                 Toast("玩家「"+玩家账号+"」头像下载失败");
              }
            }
          }
       }
     宠物击杀榜(topTen1,topTen2); //根据两个榜单来绘制图片
     发送(qun, "[PicUrl="+AppPath + "/缓存/其他/宠物击杀榜.png]",false);
   }
}


//随机在一个群里挑战一个玩家（仅限已开放【宠物世界】玩法的群）
if(消息.equals("跨群攻击")) {
    String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; //指令触发者的当前宠物
    String 天赋 = getString(对象, "天赋");
    String 昵称 = getString(对象, "昵称");
    String 状态 = getString(对象, "状态");
    String 闭关 = getString(对象, "闭关");
    long 等级 = 文转(getString(对象, "等级"));
    long 攻击 = 文转(getString(对象, "攻击"));
    long 防御 = 文转(getString(对象, "防御"));
    long 智力 = 文转(getString(对象, "智力"));
    long 心情 = 文转(getString(对象, "心情"));
    long 当前生命 = 文转(getString(对象, "当前生命"));
    
    if(等级<1) {
        发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
    }else if(状态.equals("死亡")){
        发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
    }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }else{
        String 玩家总列表 = getString("玩家总列表", "总列表");
        int 人数 = 玩家总列表.split("、").length; //玩家总人数
        String 群组列表 = getString("开放群列表", "开放列表"); 
        String[] 群组数组 = 群组列表.split("、");
        List 群号列表 = new ArrayList(Arrays.asList(群组数组));
        
        if (群号列表.isEmpty()) { 
        // 这个判断在【跨群攻击】里按理来说是不会触发的，但以防万一还是加上吧
           发送(qun,"[AtQQ="+uin+"]   当前暂无开放《宠物世界》的群聊，请等待其他群开启玩法或创建新群！",true); 
            return;
        }
        
       // 指令间隔逻辑 
      int attackInterval = getInt("系统配置", "跨群间隔", 9000); 
      //默认9秒，可以适当往上增加一些
      String key = qun + "_" + uin;
      TimestampWithText lastTriggerInfo = userAttackTime.get(key);
    
      if (lastTriggerInfo != null) {
        long lastTime = lastTriggerInfo.getTimestamp();
        long now = System.currentTimeMillis();
        long remain = (lastTime + attackInterval) - now;
        
        if (remain >= 1000) { // 攻击间隔≥1秒
            发送(qun, "[AtQQ="+uin+"]  "+ " 跨群攻击冷却中，距离下次攻击还差[" + remain/1000 + "]秒！",true);
            return; // 直接终止后续逻辑
        }else if (remain >= 1) { // 剩余时间小于1秒（1000毫秒），但≥1毫秒
           发送(qun, "[AtQQ="+uin+"]  "+ " 跨群攻击冷却中，距离下次攻击还差[" + remain+"]毫秒！",true); 
           return;
        }
      }

        Collections.shuffle(群号列表); // 打乱群顺序
        
        String 可用群号 = null; // 用于存储最终找到的可用群号
        String 可用账号 = null; // 用于存储最终找到的可用账号
        
        for (String 群号 : 群号列表) { // 遍历所有群
            String 玩家列表 = getString(群号 + "/玩家列表", "列表");
            
            if (玩家列表 != null && !"null".equals(玩家列表.trim()) && !"".equals(玩家列表.trim())) {
                String[] 玩家数组 = 玩家列表.split("、");
                List 账号列表 = new ArrayList(Arrays.asList(玩家数组));
                Collections.shuffle(账号列表); // 打乱玩家顺序
                
                for (String 账号 : 账号列表) { // 遍历当前群所有玩家
                
                    // 跳过：当前指令触发账号
                    if (账号.equals(uin)){
                        continue;
                    }
                    
                    String pz = 群号 + "/" + 账号 + "/宠物小窝/位置_0";
                    long 生命 = 文转(getString(pz, "当前生命"));
                    
                    if (生命 > 0) { // 当前生命>0 才能视作可挑战对象
                        可用账号 = 账号; // 记录可用账号
                        break; // 找到后立即退出玩家循环
                    }
                }
                
                if (可用账号 != null) { // 如果当前群找到可用账号
                    可用群号 = 群号; // 记录可用群号
                    break; // 立即退出群循环
                }
            }
        }
        
        // 处理【跨群攻击】战斗相关
        if (可用账号 != null) {
        
          String 头像路径 = AppPath + "/缓存/头像/" + uin + ".png";
          String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + uin + "&spec=640";
          File 呆河马 = new File(头像路径);
          if (!呆河马.exists()) {
             try {
                  downloadFile(玩家头像, 头像路径);
                  //↑保存该玩家头像；绘制图片时将会用到
             } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + uin + "」头像下载失败");
             }
          }
          
          String 头像路径2 = AppPath + "/缓存/头像/" + 可用账号 + ".png";
          String 玩家头像2 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + 可用账号 + "&spec=640";
          File 可达鸭 = new File(头像路径2);
          if (!可达鸭.exists()) {
             try {
                  downloadFile(玩家头像2, 头像路径2);
                  //↑保存该玩家头像；绘制图片时将会用到
             } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + 可用账号 + "」头像下载失败");
             }
          }
        
            String 堆箱=可用群号+"/"+可用账号+"/宠物小窝/位置_0"; //防御方的当前宠物
            String 天赋2 = getString(堆箱, "天赋");
            String 昵称2 = getString(堆箱, "昵称");
            long 心情2 = 文转(getString(堆箱, "心情"));
            long 攻击2 = 文转(getString(堆箱, "攻击"));
            long 防御2 = 文转(getString(堆箱, "防御"));
            long 智力2 = 文转(getString(堆箱, "智力"));
            long 当前生命2 = 文转(getString(堆箱, "当前生命"));
            
            //攻击方天赋加成
            if(天赋.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击 * 0.3);  
              攻击 = 攻击 + 攻击加成;  //攻击方的攻击力(含天赋加成)
            }
            else if(天赋.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御 * 0.3);  
              防御 = 防御 + 防御加成;  //攻击方的防御力(含天赋加成)
            }
            //防御方天赋加成
            if(天赋2.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击2 * 0.3);  
              攻击2 = 攻击2 + 攻击加成;  //防御方的攻击力(含天赋加成)
            }
            else if(天赋2.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御2 * 0.3);  
              防御2 = 防御2 + 防御加成;  //防御方的防御力(含天赋加成)
            }
            
            
            //相关数值计算
            long 攻击方输出=伤害计算(攻击, 智力, 防御2, true, 0.9f, 1.1f);
            long 防御方输出=伤害计算(攻击2, 智力2, 防御, true, 0.9f, 1.1f);
            long 生命变化=减(当前生命,防御方输出);
            long 生命变化2=减(当前生命2,攻击方输出);
            
            //双方资产数据
            long 积分 = 文转(getString(qun+"/"+uin+"/我的资产", "积分")); //⚔️攻击方积分
            long 金币 = 文转(getString(qun+"/"+uin+"/我的资产", "金币")); //⚔️攻击方金币
            long 积分2 = 文转(getString(可用群号+"/"+可用账号+"/我的资产", "积分")); //🛡️防御方积分
            long 金币2 = 文转(getString(可用群号+"/"+可用账号+"/我的资产", "金币")); //🛡️防御方金币
            
            String 状态变化="";
            // 优先判断双方宠物是否同时死亡
            if (生命变化 < 1 && 生命变化2 < 1) {
                // 攻击方宠物死亡处理
                putString(对象, "当前生命", "0");
                putString(对象, "状态", "死亡");
                putString(对象, "心情", 转文(心情-10)); //扣心情值
                int 攻击方被击杀数 = getInt("宠物被击杀次数", uin, 0);
                putInt("宠物被击杀次数", uin, 攻击方被击杀数 + 1);
                
                // 防御方宠物死亡处理
                putString(堆箱, "当前生命", "0");
                putString(堆箱, "状态", "死亡");
                putString(堆箱, "心情", 转文(心情2-10)); //扣心情值
                int 防御方被击杀数 = getInt("宠物被击杀次数", 可用账号, 0);
                putInt("宠物被击杀次数", 可用账号, 防御方被击杀数 + 1);
                
                // 双方各增加1次击杀
                int 攻击方击杀数 = getInt("宠物击杀次数", uin, 0);
                putInt("宠物击杀次数", uin, 攻击方击杀数 + 1);             
                int 防御方击杀数 = getInt("宠物击杀次数", 可用账号, 0);
                putInt("宠物击杀次数", 可用账号, 防御方击杀数 + 1);
                
                // 标记双方当前仇人
                putString(可用群号+"/"+可用账号+"/跨群相关","当前仇敌账号",uin);
                putString(qun+"/"+uin+"/跨群相关","当前仇敌账号",可用账号);
                putString(可用群号+"/"+可用账号+"/跨群相关","仇敌所在群聊",qun);
                putString(qun+"/"+uin+"/跨群相关","仇敌所在群聊",可用群号);
                putInt(可用群号+"/"+可用账号+"/跨群相关","可反击次数",5);
                putInt(qun+"/"+uin+"/跨群相关","可反击次数",5);
                
                //发放 击杀奖励 与 被击杀补偿 （这里是双方都有击杀奖励与被击杀补偿）
                //🎁每一次击杀的奖励为：150w积分、5金币。
                //🎁每一次被击杀的补偿奖励为：50w积分。
                long 积分变化 = 加(积分, 1500000);
                long 积分变化2 = 加(积分2, 1500000);
                long 金币变化 = 加(金币, 5);
                long 金币变化2 = 加(金币2, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(qun+"/"+uin+"/我的资产", "金币", 转文(金币变化));
                putString(可用群号+"/"+可用账号+"/我的资产", "积分", 转文(积分变化2));
                putString(可用群号+"/"+可用账号+"/我的资产", "金币", 转文(金币变化2));
            } 
            // 攻击方单独死亡
            else if (生命变化 < 1) {
                putString(对象, "当前生命", "0");
                putString(对象, "状态", "死亡");
                putString(对象, "心情", 转文(心情-10)); //扣心情值
                int 被击杀数 = getInt("宠物被击杀次数", uin, 0);
                putInt("宠物被击杀次数", uin, 被击杀数 + 1);
                int 防御方击杀数 = getInt("宠物击杀次数", 可用账号, 0); 
                putInt("宠物击杀次数", 可用账号, 防御方击杀数 + 1);
                
                 // 标记当前仇人
                putString(qun+"/"+uin+"/跨群相关","当前仇敌账号",可用账号);
                putString(qun+"/"+uin+"/跨群相关","仇敌所在群聊",可用群号);
                putInt(qun+"/"+uin+"/跨群相关","可反击次数",5);
                
                //发放 击杀奖励 与 被击杀补偿 （自动发放，不通知）
                long 积分变化 = 加(积分, 500000);
                long 积分变化2 = 加(积分2, 1500000);
                long 金币变化2 = 加(金币2, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(可用群号+"/"+可用账号+"/我的资产", "积分", 转文(积分变化2));
                putString(可用群号+"/"+可用账号+"/我的资产", "金币", 转文(金币变化2));
                
                // 异常状态判定
              if(天赋.equals("疫病之源") && 攻击方输出 > 0){
                  // 30%概率触发
                  if(Math.random() <= 0.55){
                      // 随机异常状态列表
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      // 存储异常状态
                      putString(堆箱, "状态", 随机状态);
                      状态变化="-----状态变化----\n右侧：感染“"+随机状态+"”状态";
                  }
              }
            } 
            // 防御方单独死亡
            else if (生命变化2 < 1) {
                putString(堆箱, "当前生命", "0");
                putString(堆箱, "状态", "死亡");
                putString(堆箱, "心情", 转文(心情2-10)); //扣心情值
                int 击杀数 = getInt("宠物击杀次数", uin, 0);
                putInt("宠物击杀次数", uin, 击杀数 + 1);
                int 防御方被击杀数 = getInt("宠物被击杀次数", 可用账号, 0);
                putInt("宠物被击杀次数", 可用账号, 防御方被击杀数 + 1);
                
                // 标记当前仇人
                putString(可用群号+"/"+可用账号+"/跨群相关","当前仇敌账号",uin);
                putString(可用群号+"/"+可用账号+"/跨群相关","仇敌所在群聊",qun);
                putInt(可用群号+"/"+可用账号+"/跨群相关","可反击次数",5);
                
                //发放 击杀奖励 与 被击杀补偿 （自动发放，不会额外通知）
                long 积分变化 = 加(积分, 1500000);
                long 积分变化2 = 加(积分2, 500000);
                long 金币变化 = 加(金币, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(qun+"/"+uin+"/我的资产", "金币", 转文(金币变化));
                putString(可用群号+"/"+可用账号+"/我的资产", "积分", 转文(积分变化2));
                
               // 异常状态判定
              if(天赋2.equals("疫病之源") && 防御方输出 > 0){
                  if(Math.random() <= 0.55){
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      putString(对象, "状态", 随机状态);
                      状态变化="-----状态变化----\n左侧：感染“"+随机状态+"”状态";
                  }
              }
            } 
            //双方存活，更新双方当前生命
            else {
                putString(对象, "当前生命", 转文(生命变化));
                putString(堆箱, "当前生命", 转文(生命变化2));

                // 异常状态判定
              if(天赋.equals("疫病之源") && 攻击方输出 > 0){
                  // 30%概率触发
                  if(Math.random() <= 0.55){
                      // 随机异常状态列表
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      // 存储异常状态
                      putString(堆箱, "状态", 随机状态);
                      状态变化=状态变化+"-----状态变化----\n右侧：感染“"+随机状态+"”状态";
                  }
              }
              if(天赋2.equals("疫病之源") && 防御方输出 > 0){
                  if(Math.random() <= 0.55){
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      putString(对象, "状态", 随机状态);
                      状态变化=状态变化+"\n左侧：感染“"+随机状态+"”状态";
                  }
              }      
            }

            跨群攻击(qun,uin,攻击方输出,可用群号,可用账号,防御方输出); //生成图片
            //：跨群攻击(攻击方{群,Q,伤害}，防御方{群,Q,伤害})
            String 图片前缀=qun+uin+"&"+可用群号+可用账号;
            发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+图片前缀+"_跨群攻击.png]"+状态变化,false); 
            //↑↑ 图片发送到【攻击方】所在群
            发送(可用群号,"[AtQQ="+可用账号+"]  [PicUrl="+AppPath+"/缓存/其他/"+图片前缀+"_跨群攻击.png]"+状态变化,false); 
            //↑↑图片发送到【防御方】所在群
            
             userAttackTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));             
             //↑记录玩家本次跨群的时间戳，用于计算指令CD
        }else{ 
            发送(qun,"[AtQQ="+uin+"]   当前仅剩你的宠物存活哦～\n发送【宠物击杀榜】查看击杀数",true);
        }
    }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); //发送【查看触发时间】可查看该群最近指令触发记录。
     //↑记录指令触发时的时间戳（统计此群玩家最近一条发言）
}

//可反击对方
if(消息.equals("宠物反击")) {
    String 群组列表 = getString("开放群列表", "开放列表"); 
    String 对象=qun+"/"+uin+"/宠物小窝/位置_0"; //指令触发者的当前宠物
    String 昵称 = getString(对象, "昵称");
    String 天赋 = getString(对象, "天赋");
    String 状态 = getString(对象, "状态");
    String 闭关 = getString(对象, "闭关");
    long 心情 = 文转(getString(配置名称, "心情"));
    long 等级 = 文转(getString(对象, "等级"));
    long 攻击 = 文转(getString(对象, "攻击"));
    long 防御 = 文转(getString(对象, "防御"));
    long 智力 = 文转(getString(对象, "智力"));
    long 当前生命 = 文转(getString(对象, "当前生命"));
    String 仇敌账号 = getString(qun+"/"+uin+"/跨群相关","当前仇敌账号");
    String 仇敌所在 = getString(qun+"/"+uin+"/跨群相关","仇敌所在群聊");
    int 反击次数 = getInt(qun+"/"+uin+"/跨群相关","可反击次数",0);
    
    String pz = 仇敌所在+"/"+仇敌账号+"/宠物小窝/位置_0";
    String 名字 = getString(pz, "宠物名字");
    long 生命 = 文转(getString(pz, "当前生命"));
    
    if(等级<1) {
        发送(qun,"[AtQQ="+uin+"]   你当前还没有宠物，请去邂逅自己的宠物！\n◇指令：砸蛋十连",true);
    }else if(状态.equals("死亡")){
        发送(qun,"[AtQQ="+uin+"]   您的宠物已死亡，请先复活再进行操作哦！\n•例如：使用复活石",true);
    }else if(闭关!=null&&闭关.equals("闭关")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物在闭关中，无法进行其他操作！如需停止闭关，请发送【出关】!",true);
    }else if(!状态.equals("正常")){
       发送(qun,"[AtQQ="+uin+"]   您的宠物状态异常，请先解除异常再进行操作\n✦例如：使用万灵药",true);
     }else if(等级!=0&&心情<=15){
       发送(qun,"[AtQQ="+uin+"]   您的宠物心情低落，请先恢复宠物心情再操作吧\n✦例如：使用开心果#2",true);
    }else if(反击次数==0){
        发送(qun,"[AtQQ="+uin+"]   你已反击成功或次数不足！",true);
    }else if(!群组列表.contains(仇敌所在)){
        发送(qun,"[AtQQ="+uin+"]   反击失败！\n仇敌[@"+仇敌账号+"]所在群聊已关闭【"+NAME+"】玩法！",true);
    }else if(名字==null||名字.equals("null")||名字.isEmpty()){
        发送(qun,"[AtQQ="+uin+"]   无法反击！\n仇敌[@"+仇敌账号+"]当前没有宠物！",true);
    }else if(生命<1){
        发送(qun,"[AtQQ="+uin+"]   无法反击！\n仇敌[@"+仇敌账号+"]当前宠物已死亡啦！",true);
    }else if(反击次数>=1){
 
       // 指令间隔逻辑 
      int attackInterval = 5000; // 5秒间隔
      String key = qun + "_" + uin;
      TimestampWithText lastTriggerInfo = userCounterattackTime.get(key);
    
      if (lastTriggerInfo != null) {
        long lastTime = lastTriggerInfo.getTimestamp();
        long now = System.currentTimeMillis();
        long remain = (lastTime + attackInterval) - now;
        
        if (remain >=1) { // 剩余时间≥1
          long Second=remain/1000;
          String msg="宠物反击冷却中，距离下次反击还差";
          if(Second>=1){
            msg=msg+"["+Second+"]秒！";
          }else{
            msg=msg+"["+remain+"]毫秒！";
          }
            发送(qun, "[AtQQ="+uin+"]  "+msg,true);
            return; // 直接终止后续逻辑
        }
      }
        
          String 头像路径 = AppPath + "/缓存/头像/" + uin + ".png";
          String 玩家头像 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + uin + "&spec=640";
          File 呆河马 = new File(头像路径);
          if (!呆河马.exists()) {
             try {
                  downloadFile(玩家头像, 头像路径);
                  //↑保存该玩家头像；绘制图片时将会用到
             } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + uin + "」头像下载失败");
             }
          }
          
          String 头像路径2 = AppPath + "/缓存/头像/" + 仇敌账号 + ".png";
          String 玩家头像2 = "http://q2.qlogo.cn/headimg_dl?dst_uin=" + 仇敌账号 + "&spec=640";
          File 可达鸭 = new File(头像路径2);
          if (!可达鸭.exists()) {
             try {
                  downloadFile(玩家头像2, 头像路径2);
                  //↑保存该玩家头像；绘制图片时将会用到
             } catch (IOException e) {
                // 处理下载文件时的异常
                e.printStackTrace();
                Toast("玩家「" + 仇敌账号 + "」头像下载失败");
             }
          }
        
            String 天赋2 = getString(pz, "天赋");
            String 昵称2 = getString(pz, "昵称");
            long 心情2 = 文转(getString(pz, "心情"));
            long 攻击2 = 文转(getString(pz, "攻击"));
            long 防御2 = 文转(getString(pz, "防御"));
            long 智力2 = 文转(getString(pz, "智力"));
            long 当前生命2 = 文转(getString(pz, "当前生命"));
            
            //攻击方天赋加成
            if(天赋.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击 * 0.3);  
              攻击 = 攻击 + 攻击加成;  //攻击方的攻击力(含天赋加成)
            }
            else if(天赋.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御 * 0.3);  
              防御 = 防御 + 防御加成;  //攻击方的防御力(含天赋加成)
            }
            //防御方天赋加成
            if(天赋2.equals("锐牙狂威")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 攻击加成 = Math.round(攻击2 * 0.3);  
              攻击2 = 攻击2 + 攻击加成;  //防御方的攻击力(含天赋加成)
            }
            else if(天赋2.equals("厚土磐佑")){
              // 计算天赋30%加成值（使用浮点数避免整数除法精度丢失）
              long 防御加成 = Math.round(防御2 * 0.3);  
              防御2 = 防御2 + 防御加成;  //防御方的防御力(含天赋加成)
            }
            
            
            //相关数值计算
            long 攻击方输出=伤害计算(攻击, 智力, 防御2, true, 0.9f, 1.1f);
            long 防御方输出=伤害计算(攻击2, 智力2, 防御, true, 0.9f, 1.1f);
            long 生命变化=减(当前生命,防御方输出);
            long 生命变化2=减(当前生命2,攻击方输出);
            
            //双方资产数据
            long 积分 = 文转(getString(qun+"/"+uin+"/我的资产", "积分")); //⚔️反击方积分
            long 金币 = 文转(getString(qun+"/"+uin+"/我的资产", "金币")); //⚔️反击方金币
            long 积分2 = 文转(getString(仇敌所在+"/"+仇敌账号+"/我的资产", "积分")); //🛡️防御方积分
            long 金币2 = 文转(getString(仇敌所在+"/"+仇敌账号+"/我的资产", "金币")); //🛡️防御方金币
            String 状态变化="";
            // 优先判断双方宠物是否同时死亡
            if (生命变化 < 1 && 生命变化2 < 1) {
                // 攻击方宠物死亡处理
                putString(对象, "当前生命", "0");
                putString(对象, "状态", "死亡");
                putString(对象, "心情", 转文(心情-10)); //扣心情值
                int 攻击方被击杀数 = getInt("宠物被击杀次数", uin, 0);
                putInt("宠物被击杀次数", uin, 攻击方被击杀数 + 1);
                
                // 防御方宠物死亡处理
                putString(pz, "当前生命", "0");
                putString(pz, "状态", "死亡");
                putString(pz, "心情", 转文(心情2-10)); //扣心情值
                int 防御方被击杀数 = getInt("宠物被击杀次数", 仇敌账号, 0);
                putInt("宠物被击杀次数", 仇敌账号, 防御方被击杀数 + 1);
                
                // 双方各增加1次击杀
                int 攻击方击杀数 = getInt("宠物击杀次数", uin, 0);
                putInt("宠物击杀次数", uin, 攻击方击杀数 + 1);             
                int 防御方击杀数 = getInt("宠物击杀次数", 仇敌账号, 0);
                putInt("宠物击杀次数", 仇敌账号, 防御方击杀数 + 1);
                
                // 清除双方当前仇人
                删除文件(缓存路径+qun+"/"+uin+"/跨群相关"); 
                删除文件(缓存路径+仇敌所在+"/"+仇敌账号+"/跨群相关"); 
                
                //发放 击杀奖励 与 被击杀补偿 （这里是双方都有击杀奖励与被击杀补偿）
                //🎁每一次击杀的奖励为：150w积分、5金币。
                //🎁每一次被击杀的补偿奖励为：50w积分。
                long 积分变化 = 加(积分, 1500000);
                long 积分变化2 = 加(积分2, 1500000);
                long 金币变化 = 加(金币, 5);
                long 金币变化2 = 加(金币2, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(qun+"/"+uin+"/我的资产", "金币", 转文(金币变化));
                putString(仇敌所在+"/"+仇敌账号+"/我的资产", "积分", 转文(积分变化2));
                putString(仇敌所在+"/"+仇敌账号+"/我的资产", "金币", 转文(金币变化2));
            } 
            // 攻击方单独死亡
            else if (生命变化 < 1) {
                putString(对象, "当前生命", "0");
                putString(对象, "状态", "死亡");
                putString(对象, "心情", 转文(心情-10)); //扣心情值
                int 被击杀数 = getInt("宠物被击杀次数", uin, 0);
                putInt("宠物被击杀次数", uin, 被击杀数 + 1);
                int 防御方击杀数 = getInt("宠物击杀次数", 仇敌账号, 0); 
                putInt("宠物击杀次数", 仇敌账号, 防御方击杀数 + 1);
                
                 // 清除当前仇人
                 删除文件(缓存路径+qun+"/"+uin+"/跨群相关");
                 
                 // 异常状态判定
              if(天赋.equals("疫病之源") && 攻击方输出 > 0){
                  // 30%概率触发
                  if(Math.random() <= 0.55){
                      // 随机异常状态列表
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      // 存储异常状态
                      putString(pz, "状态", 随机状态);
                      状态变化="-----状态变化----\n右侧：感染“"+随机状态+"”状态";
                  }
              }
                
                //发放 击杀奖励 与 被击杀补偿 （自动发放，不通知）
                long 积分变化 = 加(积分, 500000);
                long 积分变化2 = 加(积分2, 1500000);
                long 金币变化2 = 加(金币2, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(仇敌所在+"/"+仇敌账号+"/我的资产", "积分", 转文(积分变化2));
                putString(仇敌所在+"/"+仇敌账号+"/我的资产", "金币", 转文(金币变化2));
            } 
            // 防御方单独死亡
            else if (生命变化2 < 1) {
                putString(pz, "当前生命", "0");
                putString(pz, "状态", "死亡");
                putString(pz, "心情", 转文(心情2-10)); //扣心情值
                int 击杀数 = getInt("宠物击杀次数", uin, 0);
                putInt("宠物击杀次数", uin, 击杀数 + 1);
                int 防御方被击杀数 = getInt("宠物被击杀次数", 仇敌账号, 0);
                putInt("宠物被击杀次数", 仇敌账号, 防御方被击杀数 + 1);
                
                // 清除当前仇人
                删除文件(缓存路径+仇敌所在+"/"+仇敌账号+"/跨群相关"); 
                
                //发放 击杀奖励 与 被击杀补偿 （自动发放，不会额外通知）
                long 积分变化 = 加(积分, 1500000);
                long 积分变化2 = 加(积分2, 500000);
                long 金币变化 = 加(金币, 5);
                putString(qun+"/"+uin+"/我的资产", "积分", 转文(积分变化));
                putString(qun+"/"+uin+"/我的资产", "金币", 转文(金币变化));
                putString(仇敌所在+"/"+仇敌账号+"/我的资产", "积分", 转文(积分变化2));
                // 异常状态判定
              if(天赋2.equals("疫病之源") && 防御方输出 > 0){
                  if(Math.random() <= 0.55){
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      putString(对象, "状态", 随机状态);
                      状态变化="-----状态变化----\n左侧：感染“"+随机状态+"”状态";
                  }
              }
            } 
            //双方存活，更新双方当前生命
            else {
                putString(对象, "当前生命", 转文(生命变化));
                putString(pz, "当前生命", 转文(生命变化2));
                
                // 异常状态判定
              if(天赋.equals("疫病之源") && 攻击方输出 > 0){
                  // 30%概率触发
                  if(Math.random() <= 0.55){
                      // 随机异常状态列表
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      // 存储异常状态
                      putString(pz, "状态", 随机状态);
                      状态变化="-----状态变化----\n右侧：感染“"+随机状态+"”状态";
                  }
              }
              if(天赋2.equals("疫病之源") && 防御方输出 > 0){
                  if(Math.random() <= 0.55){
                      String[] 异常状态 = {"中毒", "灼烧", "麻痹", "冻结", "眩晕"};
                      String 随机状态 = 异常状态[(int)(Math.random() * 异常状态.length)];
                      putString(对象, "状态", 随机状态);
                      状态变化=状态变化+"\n左侧宠物：感染“"+随机状态+"”状态";
                  }
              }
                //反击次数-1 或 清空仇敌记录与反击次数
                if(反击次数>1){
                  putInt(qun+"/"+uin+"/跨群相关", "可反击次数", 反击次数-1);
                }else{删除文件(缓存路径+qun+"/"+uin+"/跨群相关");}
            }

            宠物反击(qun,uin,攻击方输出,仇敌所在,仇敌账号,防御方输出); //生成图片
            //：宠物反击(攻击方{群,Q,伤害}，防御方{群,Q,伤害})
            String 图片前缀=qun+uin+"&"+仇敌所在+仇敌账号;
            发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+图片前缀+"_宠物反击.png]"+状态变化,false); 
            //↑↑ 图片发送到【攻击方】所在群
            发送(仇敌所在,"[AtQQ="+仇敌账号+"]  [PicUrl="+AppPath+"/缓存/其他/"+图片前缀+"_宠物反击.png]"+状态变化,false); 
            //↑↑图片发送到【防御方】所在群
            
             userCounterattackTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));             
             //↑记录玩家本次反击的时间戳，用于计算指令CD
    }
     userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); //发送【查看触发时间】可查看该群最近指令触发记录。
     //↑记录指令触发时的时间戳（统计此群玩家最近一条发言）
}

//单位换算指令
if(消息.startsWith("换算")){
    String 目标=消息.substring(2);
    boolean isPureNumber = 目标.matches("\\d+");
    if (!isPureNumber) {
        // 去除非数字字符
        目标 = 目标.replaceAll("[^\\d]", "");
    }
    if(目标.isEmpty()){
        发送(qun,"[AtQQ="+uin+"]  \n你没有输入数字哦",true);
        return;
    }
    
    // 判断目标是否超过long最大值
    // 比较字符串长度，long最大值是19位，超过则直接判定为过大
    if (目标.length() > 19) {
        发送(qun,"[AtQQ="+uin+"]  \n您输入的数字＞"+Long.MAX_VALUE+"，超出计算范围哦",true);
        return;
    }
    // 长度等于19时，直接与long最大值字符串比较
    if (目标.length() == 19 && 目标.compareTo("9223372036854775807") > 0) {
        发送(qun,"[AtQQ="+uin+"]  \n您输入的数字＞"+Long.MAX_VALUE+"，超出计算范围哦",true);
        return;
    }
    
    long Value = 文转(目标);
    发送(qun,玩家名(qun,uin)+" \n目标数值："+Value+"\n单位换算："+数值转(Value)+"\n\n✦提示：万=W，亿=E，万亿=WE，亿亿（京）=EE",false);
}

   
   //查看玩家资产信息
   if(消息.startsWith("我的资产"))
   {
     String 群列表 = getString(qun + "/玩家列表", "列表");
     if(群列表==null||!群列表.contains(uin)){
       发送(qun,"[AtQQ="+uin+"]   \n\n此群没有您的相关数据！\n请发送【签到】或【领新手礼包】来刷新！",true);
     }else{
      // 随机提示文本
      String[] textMsg={"发送〖GM列表〗可查看GM账号哦","〖出售〗指令支持全部出售啦！","连签天数达标的话，会有相应奖励发放～","〖限时商店〗每天8:00-22:00 限时开放","“宠物进化”支持多次进化啦\n格式：宠物进化+次数","[限时商店]里有很多好东西哦"};
      Random random = new Random();
      int index = random.nextInt(textMsg.length); // 生成随机索引
      String randomContent = textMsg[index]; // 随机内容
      String 内容=玩家名(qun,uin)+" ("+uin+")\n您的资产情况如下：\n<填充>\n"+" ●积分："+标记(积分)+" \n ●金币："+标记(金币)+" \n<填充>\n"+randomContent+"\nTime："+getTodayDate(2);
      toImg(内容,"",0.5,0.03,35,AppPath+"/缓存/其他/"+uin+"_我的资产.png",false);
      发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"_我的资产.png]",false);
     }
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   }
   
  //用金币兑换积分
  if(消息.startsWith("兑换积分")){
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息)); //记录操作时间戳
      long 积分 = 文转(getString(配置名+"/我的资产", "积分"));
      long 金币 = 文转(getString(配置名+"/我的资产", "金币"));
      long 兑换值=500000; //1金币=50w积分
      String 数字部分 = 消息.substring(4);
      
      // 去掉所有非数字字符
      String 纯数字 = 数字部分.replaceAll("\\D", "");
      数字部分 = 纯数字;
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
      if(金币<数量){
         发送(qun,"[AtQQ="+uin+"]   你拥有的金币不足"+数值转(数量)+"，无法兑换"+数值转(兑换值*数量)+"积分",true);
      }else{
        long 积分变化=乘(兑换值,数量);
        putString(配置名+"/我的资产", "金币", 转文(金币-数量));
        putString(配置名+"/我的资产", "积分", 转文(积分+积分变化));
       发送(qun,"[AtQQ="+uin+"]   兑换成功！\n\n◆消耗"+数值转(数量)+"金币\n◆成功兑换"+数值转(积分变化)+"积分！",true);
      }
  }
      
//查看玩家背包详情，会按类型对道具进行排序
if(消息.startsWith("我的背包")) {
    String 道具 = getString(qun + "/" + uin + "/我的背包", "道具列表");
    String 群列表 = getString(qun + "/玩家列表", "列表");
    
    if(群列表==null||!群列表.contains(uin)){
        发送(qun,"[AtQQ="+uin+"]   \n\n此群没有您的相关数据！\n请发送【领新手礼包】来刷新！",true);
    } else if (道具 == null || "null".equals(道具)) {
        发送(qun, "[AtQQ="+uin+"]  "+ " \n您的背包里面空空如也哦(¬_¬'')",true);
    } else {
        // 分割道具字符串并去重
        String[] parts = 道具.split("、");
        List propList = new ArrayList(new LinkedHashSet(Arrays.asList(parts)));
        
        // 定义类型顺序
        String[] typeOrder = {"恢复", "消耗", "复活", "仙丹", "精力", "抽奖", "经验", "积分", "金币", "充值", "战力", "转换", "材料", "碎片", "神器", "装备", "特殊", "神秘", "礼包", "宝箱"};
        
        // 按类型排序道具
        Collections.sort(propList, new Comparator() {
            public int compare(String prop1, String prop2) {
                Item item1 = itemMap.get(prop1);
                Item item2 = itemMap.get(prop2);
                int index1 = item1 != null ? Arrays.asList(typeOrder).indexOf(item1.getType()) : typeOrder.length;
                int index2 = item2 != null ? Arrays.asList(typeOrder).indexOf(item2.getType()) : typeOrder.length;
                return Integer.compare(index1, index2);
            }
        });
        
        StringBuilder result = new StringBuilder();
        // 遍历处理每个道具（使用long类型数量）
        for (String prop : propList) {
            String quantityStr = getString(qun + "/" + uin + "/我的背包", prop);
            long quantity = quantityStr != null ? Long.parseLong(quantityStr) : 0;  // 改为long类型
            
            // 校验道具存在性和数量>0（long类型判断）
            if (itemMap.containsKey(prop) && quantity > 0) {
                if (itemMap.containsKey(prop)) {
                    Item item = itemMap.get(prop);
                    result.append("●[").append(item.getType()).append("]:" + prop).append("×").append(quantity).append("\n");
                } else {
                    result.append("●[*未知*]:" + prop).append("×").append(quantity).append("\n");
                }
            }
        }
        
        // 绘制结果图片（处理空结果）
        if (result.length() == 0) {
            发送(qun, "[AtQQ="+uin+"]  "+ " \n您的背包里面空空如也哦(¬_¬'')",true);
        } else {
            int propTypeCount = result.toString().split("\n").length;
            String 内容 = 玩家名(qun,uin)+ " ("+uin+")\n您的背包如下：\n<填充>\n" + result.toString() + "<填充>\n总计：" + propTypeCount + "种物品  " + getTodayDate(1);
            toImg(内容, "", 0.5, 0.005, 35, AppPath + "/缓存/其他/" + uin + "_我的背包.png",false);
            发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/" + uin + "_我的背包.png]",false);
        }
    }
    userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}


//道具搜索指令
if(消息.startsWith("道具搜索")) {
String 关键词;
// 截取指定类型或道具关键词
if(消息.startsWith("道具搜索类型")){
关键词 = 消息.substring(6);
} else {
关键词 = 消息.substring(4);
}
 
// 记录时间戳
userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));

// 定义类型顺序
String[] typeOrder = {"恢复", "消耗", "复活", "仙丹", "精力", "抽奖", "经验", "积分", "金币", "充值", "战力", "转换", "材料", "碎片", "神器", "装备", "特殊", "神秘", "礼包", "宝箱", "定制"};
// 拼接类型列表字符串
StringBuilder typeListStr = new StringBuilder();
for (int i = 0; i < typeOrder.length; i++) {
typeListStr.append(i + 1).append(".").append(typeOrder[i]).append("  ");
// 每3个类型换行
if ((i + 1) % 3 == 0) {
typeListStr.append("\n");
}
}
 
// 关键词为空时提示格式
if(关键词 == null || 关键词.trim().length() == 0){
发送(qun, "[AtQQ=" + uin + "]  请输入需要搜索的关键词！\n\n•格式1：道具搜索+关键词\n•格式2：道具搜索类型+类型\n\n✦支持的类型如下：\n" +
typeListStr.toString(), false);
return;
}
 

// 存储匹配的道具
List propList = new ArrayList();
 
if(!消息.contains("类型")){ // 按道具名称搜索
// 遍历itemMap匹配名称包含关键词的道具
for(String propName : itemMap.keySet()){
if(propName.contains(关键词)){
propList.add(propName);
}
}
} else { // 按类型搜索
// 遍历itemMap匹配类型包含关键词的道具
for(String propName : itemMap.keySet()){
Item item = itemMap.get(propName);
if(item != null && item.getType().contains(关键词)){
propList.add(propName);
}
}
}
 
// 无匹配结果处理
if(propList.isEmpty()){
String 提示文本 = 消息.contains("类型") ?
"未搜索到类型〔" + 关键词 + "〕相关道具\n\n✦支持的类型如下：\n" +
typeListStr.toString() :
"未搜索到名称包含〔" + 关键词 + "〕的道具";
发送(qun, "[AtQQ=" + uin + "]  " + 提示文本 + "\n•可输入其他内容重试", true);
return;
}
 
// 按类型顺序排序
Collections.sort(propList, new Comparator() {
public int compare(String prop1, String prop2) {
Item item1 = itemMap.get(prop1);
Item item2 = itemMap.get(prop2);
int index1 = item1 != null ? Arrays.asList(typeOrder).indexOf(item1.getType()) : typeOrder.length;
int index2 = item2 != null ? Arrays.asList(typeOrder).indexOf(item2.getType()) : typeOrder.length;
return Integer.compare(index1, index2);
}
});
 
// 拼接搜索结果
StringBuilder result = new StringBuilder();
for(String propName : propList){
Item item = itemMap.get(propName);
if(item != null){
// 关键词高亮处理
String highlightedName = 消息.contains("类型") ?
propName :
propName.replace(关键词, "*" + 关键词 + "*");
result.append("●[").append(item.getType()).append("]：")
.append(highlightedName).append("\n");
}
}
 
// 生成并发送结果图片
int 道具总数 = propList.size();
String 内容 = 玩家名(qun, uin) + " (" + uin + ")\n关键词〔"+关键词+"〕，道具搜索结果如下：\n<填充>\n" +
result.toString() + "<填充>\n总计：" + 道具总数 + "种物品 " + getTodayDate(1);
String 图片路径 = AppPath + "/缓存/其他/" + uin + "_道具搜索.png";
toImg(内容, "", 0.5, 0.01, 35, 图片路径, false);
发送(qun, "[PicUrl=" + 图片路径 + "]", false);
}

// 背包搜索指令
if(消息.startsWith("背包搜索")) {
    String 道具 = getString(qun+"/"+uin+"/我的背包", "道具列表");
    String 群列表 = getString(qun+"/玩家列表", "列表");
    String 关键词;
    
    //截取指定类型或道具
    if(消息.startsWith("背包搜索类型")){
      关键词=消息.substring(6);
    }else if(消息.startsWith("背包搜索")){
      关键词=消息.substring(4);
    }
    
      //提前记录时间戳
      userLastTriggerTime.put(qun+"_"+uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
    if(群列表==null||!群列表.contains(uin)){
        发送(qun,"[AtQQ="+uin+"]   \n\n此群没有您的相关数据！\n请发送【领新手礼包】来刷新！",true);
    } else if (道具 == null || "null".equals(道具)) {
        发送(qun, "[AtQQ="+uin+"]  "+ " \n您的背包里面空空如也哦(¬_¬'')",true);
    } else if(关键词==null||关键词.trim().length()==0){
        发送(qun,"[AtQQ="+uin+"]   请输入需要搜索的关键词！\n\n•格式1：背包搜索+道具\n•格式2：背包搜索类型+类型",true);
    } else if(!消息.contains("类型")){  //不按类型搜索，搜索道具
       // 将字符串按分隔符分割成数组
       String[] parts = 道具.split("、");
       List propList = new ArrayList();  // 存储匹配的道具
       
       // 判断背包是否包含关键词，并收集匹配项
       for (String prop : parts) {
          if (prop.contains(关键词)) {
             propList.add(prop);
          }
       }
       
       if (propList.isEmpty()) { //背包中没有匹配的道具
          发送(qun,"[AtQQ="+uin+"]   背包中未搜索到〔"+关键词+"〕相关道具",true);
          return;
       } else {
          // 定义类型顺序
          String[] typeOrder = {"恢复", "消耗", "复活", "仙丹", "精力", "抽奖", "经验", "积分", "金币", "充值", "战力", "转换", "材料", "碎片", "神器", "装备", "特殊", "神秘", "礼包", "宝箱"};
        
          // 使用匿名内部类实现 Comparator 并对 propList 进行排序
          Collections.sort(propList, new Comparator() {
            public int compare(String prop1, String prop2) {
                Item item1 = itemMap.get(prop1);
                Item item2 = itemMap.get(prop2);
                int index1 = item1 != null ? Arrays.asList(typeOrder).indexOf(item1.getType()) : typeOrder.length;
                int index2 = item2 != null ? Arrays.asList(typeOrder).indexOf(item2.getType()) : typeOrder.length;
                return Integer.compare(index1, index2);
            }
          });
        
          StringBuilder result = new StringBuilder();
          // 遍历 List 挨个处理道具并拼接（增加存在性和数量校验）
          for (String prop : propList) {
              String quantityStr = getString(qun+"/"+uin+"/我的背包", prop);
              long quantity = quantityStr != null ? Long.parseLong(quantityStr) : 0;  // 改为long类型
            
              // 校验道具是否存在且数量>0
              if (itemMap.containsKey(prop) || quantity > 0) {
                  if (itemMap.containsKey(prop)) {
                      Item item = itemMap.get(prop);
                      //在关键词前后加上*
                      String highlightedProp = prop.replace(关键词, "*" + 关键词 + "*");
                      result.append("●[").append(item.getType()).append("]:"+highlightedProp).append("×").append(quantity).append("\n");
                  } else {
                      result.append("●[未知]:"+prop).append("×").append(quantity).append("\n");
                 }
              }
          }
        
        
          // 绘制拼接后的内容并保存为图片（处理空结果情况）
          if (result.length() == 0) {
             发送(qun, "[AtQQ="+uin+"]  "+ " \n搜索结果为空哦(¬_¬'')",true);
          } else {
             int propTypeCount = result.toString().split("\n").length; // 按行统计有效道具数
             String 内容 = 玩家名(qun,uin)+ " ("+uin+")\n搜索结果如下：\n<填充>\n"+result.toString()+"<填充>\n总计："+propTypeCount+"种物品 "+getTodayDate(1);
             toImg(内容, "", 0.5, 0.01, 35, AppPath+"/缓存/其他/"+uin+"_我的背包.png",false);
             发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+uin+"_我的背包.png]",false);
          }       
       }
    } else if(消息.contains("类型")){ //按类型搜索
       // 将字符串按分隔符分割成数组
       String[] parts = 道具.split("、");
       List propList = new ArrayList();  // 存储匹配的道具
       
       // 定义类型顺序
       String[] typeOrder = {"恢复", "消耗", "复活", "仙丹", "精力", "抽奖", "经验", "积分", "金币", "充值", "战力", "转换", "材料", "碎片", "神器", "装备", "特殊", "神秘", "礼包", "宝箱"};
       
       // 核心匹配逻辑
       for (String prop : parts) {
          Item item = itemMap.get(prop);
          if (item != null) {
            String 类型 = item.getType();
            // 判断类型是否包含关键词（如"战力"包含"力"）
            if (类型.contains(关键词)) {
               propList.add(prop);
            }
          }
       }
       
       if (propList.isEmpty()) { //背包中没有匹配的道具
          发送(qun,"[AtQQ="+uin+"]   背包中未搜索到类型〔"+关键词+"〕相关道具",true);
          return;
       } else {
          // 使用匿名内部类实现 Comparator 并对 propList 进行排序
          Collections.sort(propList, new Comparator() {
            public int compare(String prop1, String prop2) {
                Item item1 = itemMap.get(prop1);
                Item item2 = itemMap.get(prop2);
                int index1 = item1 != null ? Arrays.asList(typeOrder).indexOf(item1.getType()) : typeOrder.length;
                int index2 = item2 != null ? Arrays.asList(typeOrder).indexOf(item2.getType()) : typeOrder.length;
                return Integer.compare(index1, index2);
            }
          });
        
          StringBuilder result = new StringBuilder();
          // 遍历 List 挨个处理道具并拼接（增加存在性和数量校验）
          for (String prop : propList) {
              String quantityStr = getString(qun+"/"+uin+"/我的背包", prop);
              long quantity = quantityStr != null ? Long.parseLong(quantityStr) : 0;  // 改为long类型
            
              // 校验道具是否存在且数量>0
              if (itemMap.containsKey(prop) || quantity > 0) {
                  if (itemMap.containsKey(prop)) {
                      Item item = itemMap.get(prop);
                      result.append("●[").append(item.getType()).append("]:"+prop).append("×").append(quantity).append("\n");
                  } else {
                      result.append("●[未知]:"+prop).append("×").append(quantity).append("\n");
                 }
              }
          }
        
          // 绘制拼接后的内容并保存为图片（处理空结果情况）
          if (result.length() == 0) {
             发送(qun, "[AtQQ="+uin+"]  "+ " \n搜索结果为空哦(¬_¬'')",true);
          } else {
             int propTypeCount = result.toString().split("\n").length; // 按行统计有效道具数
             String 内容 = 玩家名(qun,uin)+ " ("+uin+")\n搜索结果如下：\n<填充>\n"+result.toString()+"<填充>\n总计："+propTypeCount+"种物品 "+getTodayDate(1);
             toImg(内容, "", 0.5, 0.005, 35, AppPath+"/缓存/其他/"+uin+"_我的背包.png",false);
             发送(qun, "[PicUrl="+AppPath+"/缓存/其他/"+uin+"_我的背包.png]",false);
          }       
       }
    }
}

//↓ 在【宠物商店】里售卖的道具清单，“购买”指令也会用到这些
String[] 积分商店道具 = {
       "开心果","鸡汤","绷带","回春丹","小精力药","中精力药","大精力药","超级精力药","性转符","永恒之戒","小窝激活卡","宠物赠送卡","龙珠","星核","玄铁","灵藤","忘情水","万灵药","复活石","小聚灵丹","小焕能丹","小长生丹","小御体丹","小破锋丹","小灵智丹","焕能丹","聚灵丹","破锋丹","御体丹","长生丹","灵智丹"
};


if (消息.startsWith("宠物商店")) {
    // 自动计算页数（每10个一页，向上取整）
    int 道具总数 = 积分商店道具.length;
    int 页数上限 = (道具总数 + 9) / 10; // 道具不足10个也占一页
    
    String 目标页数 = "1";
    if (消息.equals("宠物商店")) { // 默认显示第1页
        目标页数 = "1";
    } else if (消息.length() >= 5) { // 处理带页数的指令
        String 去掉前缀 = 消息.substring(4);
        if (去掉前缀.matches("\\d+")) { // 校验是否为数字
            int 数字 = Integer.parseInt(去掉前缀);
            // 限制页数范围，超出时默认为第1页
            目标页数 = (数字 > 页数上限) ? "1" : Math.min(数字, 页数上限) + "";
        }
    }
    
    int 当前页 = Integer.parseInt(目标页数);
    int 开始索引 = (当前页 - 1) * 10; // 计算当前页起始索引
    int 结束索引 = Math.min(开始索引 + 10, 道具总数); // 防止越界
    
    // 处理售卖道具为空的情况
    if (结束索引 <= 开始索引) { 
        发送(qun, "[AtQQ="+uin+"]  当前宠物商店暂无可售卖的道具~", true);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        return; // 提前终止后续逻辑
    }
    
    // 截取当前页的道具列表（数组无重复，按顺序截取）
    String[][] 售卖道具 = new String[结束索引 - 开始索引][2];
    for (int i = 0; i < 售卖道具.length; i++) {
        String 道具名 = 积分商店道具[开始索引 + i];
        售卖道具[i] = new String[]{道具名, "purchasePoints"}; // 统一价格字段（积分）
    }
    
    StringBuilder sy = new StringBuilder();
    for (String[] prop : 售卖道具) {
        String propName = prop[0];
        String priceField = prop[1];
        Item item = (Item) itemMap.get(propName);
        
        String priceStr;
        if (item == null) {
            priceStr = "此道具不存在！";
        } else {
            long price = 0;
            try {
                // 根据价格字段获取对应价格
                price = "purchasePoints".equals(priceField) ? item.getPurchasePoints() : item.getExchangeGold(); 
                priceStr = String.valueOf(price);
            } catch (Exception e) {
                priceStr = "-1"; // 异常时标记错误价格
            }
        }
        
        String type = item != null ? item.getType() : "未知";
        sy.append("[").append(type).append("]·").append(propName).append("<起>"+priceStr+"<尾>").append("\n"); 
        // ↑拼接内容〔格式：类型•道具  价格〕
    }
    
    // 生成随机示例商品（从当前页道具中随机选取）
    Random random = new Random();
    int index = random.nextInt(售卖道具.length);
    String[] 示例商品 = 售卖道具[index];
    String 后文 = "\n•翻页：宠物商店+页数\n•指令：*购买+物品#数量*\n•示例：购买" + 示例商品[0] + "#" + random.nextInt(9998)+2 + "\nTime：" + getTodayDate(2);
    String 内容 = "//在此商店可消耗对应积分来购买下列商品\n<分割>宠物⊹︎商店</分割>\n" + sy.toString().trim() + "\n<分割>•页数：" + 当前页 + " / " + 页数上限 + " •</分割>" + 后文;
    toImg(内容, "", 0.5, 0.02, 30, AppPath + "/缓存/其他/宠物商店" + 当前页 + ".png",true);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物商店" + 当前页 + ".png]", false);
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}


//指令：购买+道具名称#数量
if (消息.startsWith("购买")) {
   long 积分 = 文转(getString(qun+"/"+uin+"/我的资产","积分"));
   String bagKey = qun+"/"+uin+"/我的背包";
   String bag = getString(bagKey, "道具列表");
   String 选定; 
   
   //记录指令触发时间（被记录的时间戳可用于间隔判断）
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   
   //先从消息中截取内容作为“选定”值。
   if (消息.contains("#")) {
      // 确定 道具名 在消息中的位置
      int charIndex = 消息.indexOf('#');
      选定 = 消息.substring(2, charIndex);
   }else if (消息.length() >= 3) {
      选定 = 消息.substring(2);
   }
   
   if(选定==null){ //没有指定道具，发送提示
      Random random = new Random();
      int index = random.nextInt(积分商店道具.length); // 生成随机索引
      String randomItem = 积分商店道具[index]; // 获取随机道具名称
     发送(qun,"[AtQQ="+uin+"]   疑似指令出错！\n◆指令：购买+道具#数量\n◇示例：购买"+randomItem+"#"+随机数(2, 9999),true);
      return;
    }

    // 检查这个道具是否在 积分商店道具 里
    boolean isFound = false;
    for (String prop : 积分商店道具) { 
      if (prop.equals(选定)) { // 道具 在 积分商店道具 里
           isFound = true; // 修改状态
           break; // 停止遍历
       }
    }

    if (!isFound) {  // 选定道具不在 积分商店道具 里
       发送(qun, "[AtQQ="+uin+"]  "+ " 宠物商店没有上架〔" + 选定 + "〕哦～\n\n◆指令：购买+道具#数量",true);
     } else {
      // 获取该道具在 itemMap 里设定的价格 (在 道具.java)
       Item selectedItem = (Item) itemMap.get(选定);
       if (selectedItem == null) {
         发送(qun, "[AtQQ="+uin+"]  "+ " 宠物商店没有上架〔" + 选定 + "〕，并且在道具库也没有查询到相关数据～\n\n◆指令：购买+道具#数量",true);
       } else 
       {
         String 数字部分 = 消息.replaceAll("[^\\d]", ""); // 提取数字
         if (!数字部分.isEmpty()) {
            // 处理多个数字或单个数字的情况
           if (数字部分.length() > 1) {
             // 去除前导零（如果结果为空则默认1）
             String 处理后数字 = 数字部分.replaceFirst("^0+", "");
             数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
           } else {// 单个数字且为0时默认1
             数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
           }
         } else { // 这里的情况是 数字部分 没有提取到数字
           数字部分 = "1"; // 强制为1，来确保能正常消费
         }
          long 数量 = Long.parseLong(数字部分);
          long 售价 = selectedItem.getPurchasePoints(); // 获取单价
          long 积分 = 文转(getString(qun + "/" + uin + "/我的资产", "积分"));
          // ↓进行计算（在 常用.java 已定义）
          long 消费 = 乘(售价, 数量); 
          if(数量>999999){ //购买量超出单次限制
            发送(qun,"[AtQQ="+uin+"]   疑似购买数量出错！",true);
            return;
          }
                
          if (消费 > 积分) {
             long 预计 = 除(积分, 售价);
             if (预计 >= 1) {
               发送(qun, "[AtQQ="+uin+"]  "+ " 无法购买！你的积分不足" + 数值转(消费) + "，还差" + 数值转(消费 - 积分) + "积分哦！\n\n诶嘿，你的积分当前能买到" + 除(积分, 售价) + "个" + 选定 + "呦～",true);
             } else {
               发送(qun, "[AtQQ="+uin+"]  "+ " 无法购买！你的积分不足" + 数值转(消费) + "，还差" + (消费 - 积分) + "积分哦！",true);
             }
         } else {
           if (bag != null && bag.contains(选定)) {
             long count = 文转(getString(bagKey, 选定));
             putString(bagKey, 选定, 转文(count + 数量));
           } else {
             String newBag = bag + "、"+选定;
             putString(bagKey, "道具列表", newBag);
             putString(bagKey, 选定, 转文(数量));
           }
           putString(qun + "/" + uin + "/我的资产", "积分", 转文(积分 - 消费)); 
           //获取该道具的相关信息
           Item selectedItem = (Item) itemMap.get(选定);
           String 类型=selectedItem.getType(); //获取道具类型
           String 简介=selectedItem.getDescription(); //获取道具简介
           发送(qun, "[AtQQ="+uin+"]  "+ " 成功购入「" + 选定 + "」×" + 数量 + "！消耗 " + 数值转(消费) + " 积分，当前道具已存入背包~ \n" + "◆可发送【我的背包】查看物品\n物品说明："+简介, true);
        }
       }
     }
}

// 在【神秘商店】里售卖的道具清单，“兑换”指令也会用到这些
String[] 神秘商店道具 = {
       "开心果","鸡汤","绷带","回春丹","小精力药","中精力药","大精力药","超级精力药","永恒之戒","忘情水",       
       "龙珠","星核","玄铁","灵藤","复活石","灵魂沙漏","往生花","九转续命丹","小窝激活卡","小窝进阶卡",
       "万灵药","凝神丹","焕能丹","破锋丹","御体丹","长生丹","灵智丹","仙缘袖珍袋","仙缘盈丰盒","仙缘秘藏匣",
       "信标","鸿蒙召唤令","易容丹","改名卡","锐牙狂威天赋包","灵蕴丹成天赋包","疫病之源天赋包","虚空壁垒天赋包","厚土磐佑天赋包","妙手回春天赋包","妙手神偷天赋包",
       "宠物背景卡","涅槃","吞噬卡"
};


if (消息.startsWith("神秘商店")) {
    // 自动计算页数（每10个一页，向上取整）
    int 道具总数 = 神秘商店道具.length;
    int 页数上限 = (道具总数 + 9) / 10; // 道具不足10个也占一页
    
    String 目标页数 = "1";
    if (消息.equals("神秘商店")) { // 默认显示第1页
        目标页数 = "1";
    } else if (消息.length() >= 5) { // 处理带页数的指令
        String 去掉前缀 = 消息.substring(4);
        if (去掉前缀.matches("\\d+")) { // 校验是否为数字
            int 数字 = Integer.parseInt(去掉前缀);
            // 限制页数范围，超出时默认为第1页
            目标页数 = (数字 > 页数上限) ? "1" : Math.min(数字, 页数上限) + "";
        }
    }
    
    int 当前页 = Integer.parseInt(目标页数);
    int 开始索引 = (当前页 - 1) * 10; // 计算当前页起始索引
    int 结束索引 = Math.min(开始索引 + 10, 道具总数); // 防止越界
    
    // 处理售卖道具为空的情况
    if (结束索引 <= 开始索引) { 
        发送(qun, "[AtQQ="+uin+"]  当前神秘商店暂无可兑换的道具~", true);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        return; // 提前终止后续逻辑
    }
    
    // 截取当前页的道具列表（数组无重复，按顺序截取）
    String[][] 售卖道具 = new String[结束索引 - 开始索引][2];
    for (int i = 0; i < 售卖道具.length; i++) {
        String 道具名 = 神秘商店道具[开始索引 + i];
        售卖道具[i] = new String[]{道具名, "exchangeGold"}; // 统一价格字段（金币）
    }
    
    StringBuilder sy = new StringBuilder();
    for (String[] prop : 售卖道具) {
        String propName = prop[0];
        String priceField = prop[1];
        Item item = (Item) itemMap.get(propName);
        
        String priceStr;
        if (item == null) {
            priceStr = "此道具不存在！";
        } else {
            long price = 0;
            try {
                // 根据价格字段获取对应价格
                price = "purchasePoints".equals(priceField) ? item.getPurchasePoints() : item.getExchangeGold(); 
                priceStr = String.valueOf(price);
            } catch (Exception e) {
                priceStr = "-1"; // 异常时标记错误价格
            }
        }
        
        String type = item != null ? item.getType() : "未知";
        sy.append("[").append(type).append("]·").append(propName).append("<起>"+priceStr+"<尾>").append("\n"); 
        // ↑拼接内容〔格式：类型•道具  价格〕
    }
    
    // 生成随机示例商品（从当前页道具中随机选取）
    Random random = new Random();
    int index = random.nextInt(售卖道具.length);
    String[] 示例商品 = 售卖道具[index];
    String 后文 = "\n•翻页：神秘商店+页数\n•指令：*兑换+物品#数量*\n•示例：兑换" + 示例商品[0] + "#" + random.nextInt(9998)+2 + "\nTime：" + getTodayDate(2);
    String 内容 = "//在此商店可消耗对应金币来兑换道具\n<分割>神秘★商店</分割>\n" + sy.toString().trim() + "\n<分割>•页数：" + 当前页 + " / " + 页数上限 + " •</分割>" + 后文;
    toImg(内容, "", 0.5, 0.01, 32, AppPath + "/缓存/其他/神秘商店" + 当前页 + ".png",true);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/神秘商店" + 当前页 + ".png]", false);
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}


//指令：兑换+道具名称#数量
if (消息.startsWith("兑换")&&!消息.startsWith("兑换积分")) { //不指定兑换积分(分开处理)
   long 金币 = 文转(getString(qun+"/"+uin+"/我的资产","金币"));
   String bagKey = qun+"/"+uin+"/我的背包";
   String bag = getString(bagKey, "道具列表");
   String 选定; 
   
   //记录指令触发时间（被记录的时间戳可用于间隔判断）
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
   
   //先从消息中截取内容作为“选定”值。
   if (消息.contains("#")) {
      // 确定 道具名 在消息中的位置
      int charIndex = 消息.indexOf('#');
      选定 = 消息.substring(2, charIndex);
   }else if (消息.length() >= 3) {
      选定 = 消息.substring(2);
   }
   
   if(选定==null){ //没有指定道具，发送提示
      Random random = new Random();
      int index = random.nextInt(神秘商店道具.length); // 生成随机索引
      String randomItem = 神秘商店道具[index]; // 获取随机道具名称
     发送(qun,"[AtQQ="+uin+"]   疑似指令出错！\n◆指令：兑换+道具#数量\n◇示例：兑换"+randomItem+"#"+随机数(2, 9999),true);
      return;
    }

    // 检查这个道具是否在 神秘商店道具 里
    boolean isFound = false;
    for (String prop : 神秘商店道具) { 
      if (prop.equals(选定)) { // 道具 在 神秘商店道具 里
           isFound = true; // 修改状态
           break; // 停止遍历
       }
    }

    if (!isFound) {  // 选定道具不在 神秘商店道具 里
       发送(qun, "[AtQQ="+uin+"]  "+ " 神秘商店没有上架〔" + 选定 + "〕哦～\n\n◆指令：兑换+道具#数量",true);
     } else {
      // 获取该道具在 itemMap 里设定的价格 (在 道具.java)
       Item selectedItem = (Item) itemMap.get(选定);
       if (selectedItem == null) {
         发送(qun, "[AtQQ="+uin+"]  "+ " 神秘商店没有上架〔" + 选定 + "〕，并且在道具库也没有查询到相关数据～\n\n◆指令：兑换+道具#数量",true);
       } else 
       {
         String 数字部分 = 消息.replaceAll("[^\\d]", ""); // 提取数字
         if (!数字部分.isEmpty()) {
            // 处理多个数字或单个数字的情况
           if (数字部分.length() > 1) {
             // 去除前导零（如果结果为空则默认1）
             String 处理后数字 = 数字部分.replaceFirst("^0+", "");
             数字部分 = 处理后数字.isEmpty() ? "1" : 处理后数字;
           } else {// 单个数字且为0时默认1
             数字部分 = "0".equals(数字部分) ? "1" : 数字部分;
           }
         } else { // 这里的情况是 数字部分 没有提取到数字
           数字部分 = "1"; // 强制为1，来确保能正常消费
         }
          long 数量 = Long.parseLong(数字部分);
          long 售价 = selectedItem.getExchangeGold(); // 获取单价
          long 金币 = 文转(getString(qun + "/" + uin + "/我的资产", "金币"));
          // ↓进行计算（在 常用.java 已定义）
          long 消费 = 乘(售价, 数量); 
          if(数量>999999){ //购买量超出单次限制
            发送(qun,"[AtQQ="+uin+"]   疑似兑换数量出错！",true);
            return;
          }
                
          if (消费 > 金币) {
             long 预计 = 除(金币, 售价);
             if (预计 >= 1) {
               发送(qun, "[AtQQ="+uin+"]  "+ " 兑换失败！你的金币不足[" + 消费 + "]，还差" + (消费 - 金币) + "金币哦！\n\n诶嘿，你的金币当前能兑换到" + 除(金币, 售价) + "个" + 选定 + "呦～",true);
             } else {
               发送(qun, "[AtQQ="+uin+"]  "+ " 兑换失败！你的金币不足[" + 消费 + "]，还差" + (消费 - 金币) + "金币哦！",true);
             }
         } else {
           if (bag != null && bag.contains(选定)) {
             long count = 文转(getString(bagKey, 选定));
             putString(bagKey, 选定, 转文(count + 数量));
           } else {
             String newBag = bag + "、"+选定;
             putString(bagKey, "道具列表", newBag);
             putString(bagKey, 选定, 转文(数量));
           }
           putString(qun + "/" + uin + "/我的资产", "金币", 转文(金币 - 消费)); 
           //获取该道具的相关信息
           Item selectedItem = (Item) itemMap.get(选定);
           String 简介=selectedItem.getDescription(); //获取道具简介
           发送(qun, "[AtQQ="+uin+"]  "+ " 成功兑换「" + 选定 + "」×" + 数量 + "！消耗 " + 数值转(消费) + " 金币，当前道具已存入背包~ \n" + "◆可发送【我的背包】查看物品\n物品说明："+简介, true);
        }
       }
     }
}


/*  
 （已优化）指令格式有下面三种：
   
 1. 出售+道具名称#数量（如：出售10软妹币#5）
 2. 出售+道具名称+数字（如：出售复活石1）
 3. 出售全部+道具名称（如：出售全部复活石）   */
if (消息.startsWith("出售")) {
    long 积分 = 文转(getString(qun + "/" + uin + "/我的资产", "积分"));
    String bagKey = qun + "/" + uin + "/我的背包";
    String bag = getString(bagKey, "道具列表");
    String 选定, 关键词;

    // 记录指令触发时间
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));

    // =========== 优先校验道具是否存在===========
    String 原始消息 = 消息.substring(2).trim(); // 去除"出售"前缀并去空格
    String 可能的道具名 = "";
    String 关键词标识 = "按量"; // 默认非“全部”场景
    

    // 处理“出售全部”场景（支持带#数量，如“出售全部通用夺宝券#50”）
    if (原始消息.startsWith("全部")) {
        关键词标识 = "全部";
        // 提取“全部”后的字符串，忽略#及后续内容（如“通用夺宝券#50”→“通用夺宝券”）
        String 候选道具名 = 原始消息.substring(2); // 截取“全部”后的所有内容
        if (候选道具名.contains("#")) {
            候选道具名 = 候选道具名.split("#")[0].trim(); // 去除#及后面的数量
        } else {
            候选道具名 = 候选道具名.trim();
        }
        
        if (候选道具名.isEmpty()) { // 道具名为空时，提示指令格式
           发送(qun, 玩家名(qun,uin) + "疑似指令出错！\n◆按量：出售+道具名#数量\n◆全部：出售全部+道具名\n◆默认：出售+道具名", true);
           return;
        }
        
        // 校验道具名是否存在（完全匹配itemMap中的键）
        if (!itemMap.containsKey(候选道具名)) {
            发送(qun, 玩家名(qun,uin) + " \n〔" + 候选道具名 + "〕不存在或输入错误！", true);
            return;
        }
        可能的道具名 = 候选道具名;
    } 
    // 处理非“全部”场景（含#或直接跟数字/无数字）
    else {
        // 遍历itemMap，找到与原始消息匹配的最长前缀作为道具名（如“复活石#1”→“复活石”）
        for (String propName : itemMap.keySet()) {
            if (原始消息.startsWith(propName)) {
                if (propName.length() > 可能的道具名.length()) {
                    可能的道具名 = propName; // 记录最长匹配的道具名
                }
            }
        }
        
        if (原始消息.isEmpty()) { // 原始消息为空时，提示指令格式
           发送(qun, 玩家名(qun,uin) + "疑似指令出错！\n◆按量：出售+道具名#数量\n◆全部：出售全部+道具名\n◆默认：出售+道具名", true);
           return;
        }
        
        // 若未找到匹配的道具名（包括含数字的道具名）
        if (可能的道具名.isEmpty()) {
            发送(qun, 玩家名(qun,uin) + " \n〔" + 原始消息 + "〕不存在或输入错误！", true);
            return;
        }
    }

    // ================== 道具校验结束 ==================

    选定 = 可能的道具名;
    关键词 = 关键词标识; // 确定是“全部”或“按量”场景

    // 校验道具是否可出售
    Item selectedItem = (Item) itemMap.get(选定);
    if (!selectedItem.isCanSell()) {
        发送(qun, 玩家名(qun,uin) + " 「" + 选定 + "」不可出售！", true);
        return;
    }

    // 处理出售数量（区分“全部”和“按量”）
    if (关键词.equals("全部")) {
        // 全部出售逻辑（忽略用户输入的#数量，直接出售背包中所有道具）
        long 道具数量 = 文转(getString(bagKey, 选定));
        if (道具数量 < 1 || !bag.contains(选定)) {
            发送(qun, 玩家名(qun,uin) + " 你背包中的〔" + 选定 + "〕不足1个！", true);
            return;
        }
        // 清空道具（因为道具全部卖出了）
        String newBag = bag.replace(选定 + "、", "").replace("、" + 选定, "");
        putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
        putString(bagKey, 选定, null);
        // 计算积分
        long 收入 = selectedItem.getSellPoints() * 道具数量;
        putString(bagKey + "/我的资产", "积分", 转文(积分 + 收入));
        发送(qun, 玩家名(qun,uin) + " 成功出售【" + 选定 + "】×" + 道具数量 + "，获得[" + 数值转(收入) + "]积分！", true);
    } else {
        // 按量出售逻辑（提取数量）
        String 剩余部分 = 原始消息.substring(可能的道具名.length()).trim();
        String 可能的数量字符串 = 剩余部分.isEmpty() ? "1" : 剩余部分;
        String 纯净数量 = 可能的数量字符串.replaceAll("[^\\d]", ""); // 过滤非数字的字符
        纯净数量 = 纯净数量.replaceFirst("^0+", ""); // 去除前导零
        long 数量 = 纯净数量.isEmpty() ? 1 : Long.parseLong(纯净数量);

        // 校验数量限制
        if (数量 > 99999) {
            发送(qun, 玩家名(qun,uin) + "\n出售数量超出限制（最大99999）！", true);
            return;
        }

        // 校验背包数量
        long 道具数量 = 文转(getString(bagKey, 选定));
        if (道具数量 < 数量 || !bag.contains(选定)) {
            发送(qun, 玩家名(qun,uin) + " 你背包中的〔" + 选定 + "〕不足" + 数量 + "个！", true);
            return;
        }

        // 扣除道具
        if (道具数量 - 数量 >= 1) {
            putString(bagKey, 选定, 转文(道具数量 - 数量));
        } else {
            String newBag = bag.replace(选定 + "、", "").replace("、" + 选定, "");
            putString(bagKey, "道具列表", newBag.isEmpty() ? null : newBag);
            putString(bagKey, 选定, null);
        }
        // 计算积分
        long 收入 = selectedItem.getSellPoints() * 数量;
        putString(bagKey + "/我的资产", "积分", 转文(积分 + 收入));
        发送(qun, 玩家名(qun,uin) + " 成功出售【" + 选定 + "】×" + 数量 + "，获得[" + 数值转(收入) + "]积分！", true);
    }
}


//在【宠物市场】上架的宠物，【领养+宠物名】指令可以用到
// "宠物名称,积分价格"；宠物是在 petMap 里的
String[] 市场上架宠物 = {
    // 普通品质
    "妙蛙种子,100000", "杰尼龟,100000", "小火龙,100000",
    // 精品品质
    "烈焰马,200000", "大针蜂,200000", "嘎啦嘎啦,200000",
    // 稀有品质
    "乘龙,300000", "飞天螳螂,300000", "电击兽,300000",
    // 史诗品质
    "魔墙人偶,400000", "卡比兽,400000", "迷唇姐,400000",
    // 传说品质
    "急冻鸟,500000", "闪电鸟,500000", "火焰鸟,500000",
    // 神级品质
    "超梦,600000", "梦幻,600000", "斯卡蒂,600000",
    // 洪荒品质
    "安柏,700000", "驰骑,700000", "龙骑,700000", "空我,700000", "甲斗,700000",
    // 创世品质
    "琴,99999999", "枫原万叶,99999999", "申鹤,99999999", "三月七,99999999", "白露,99999999",
    // 混沌品质
    "戴因斯雷布,129999999",
    // 永恒品质
    "空,159999999","荧,159999999","星,159999999","穹,159999999","派蒙,159999999"
};

//根据“市场上架宠物”来构建[宠物市场]相关内容
if (消息.startsWith("宠物市场")) {
    int 宠物数量 = 市场上架宠物.length;
    int 页数上限 = (宠物数量 + 9) / 10;
    String 目标页数 = "1";
    
    if (消息.equals("宠物市场")) {
        目标页数 = "1";
    } else if (消息.length() >= 5) {
        String 去掉前缀 = 消息.substring(4);
        if (去掉前缀.matches("\\d+")) {
            int 数字 = Integer.parseInt(去掉前缀);
            目标页数 = (数字 > 页数上限) ? "1" : Math.min(数字, 页数上限) + "";
        }
    }
    
    int 当前页 = Integer.parseInt(目标页数);
    int 开始索引 = (当前页 - 1) * 10;
    int 结束索引 = Math.min(开始索引 + 10, 宠物数量);
    
    if (结束索引 <= 开始索引) { 
        发送(qun, "[AtQQ="+uin+"]  当前宠物市场暂无可领养的宠物~", true);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        return; 
    }
    
    // 截取当前页宠物列表
    String[][] 领养宠物 = new String[结束索引 - 开始索引][2];
    for (int i = 0; i < 领养宠物.length; i++) {
        String[] 宠物拆分 = 市场上架宠物[开始索引 + i].split(",");
        领养宠物[i][0] = 宠物拆分[0].trim(); // 宠物名称
        领养宠物[i][1] = 宠物拆分[1].trim(); // 价格
    }

    StringBuilder sy = new StringBuilder();
    for (String[] prop : 领养宠物) {
        String propName = prop[0];
        String priceStr = prop[1];
        
        // 从petMap获取宠物品质
        Pet pet = petMap.get(propName);
        String quality = pet != null ? pet.getQuality() : "未知"; // 安全校验
        long num = 文转(priceStr);  
        
        if (pet == null) {
            priceStr = "此宠物不存在！";
            quality = "未知";
        } else if(num < 1 ) {
            priceStr = "积分价格未定义！";
        }
        
        sy.append("[").append(quality).append("]·").append(propName) .append("<起>").append(priceStr).append("<尾>").append("\n"); 
        //↑拼接内容（格式：类型•宠物名字  积分）
    }
    
    Random random = new Random();
    int index = random.nextInt(领养宠物.length);
    String[] 示例宠物 = 领养宠物[index];
    String 后文 = "\n•翻页：宠物市场+页数\n•指令：*领养+宠物名称*\n•示例：领养" + 示例宠物[0] + "\nTime：" + getTodayDate(2);
    String 内容 = "//在此页面可消耗对应积分来领养宠物\n<分割>宠物☆市场</分割>\n" + sy.toString().trim() + "\n<分割>•页数：" + 当前页 + " / " + 页数上限 + " •</分割>" + 后文;
    toImg(内容, "", 0.5, 0.01, 30, AppPath + "/缓存/其他/宠物市场" + 当前页 + ".png",true);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/宠物市场" + 当前页 + ".png]", false);
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}


//指令：领养+宠物名称
if (消息.startsWith("领养")) {
    // 获取用户宠物等级
    long 等级 = 文转(getString(qun + "/" + uin + "/宠物小窝/位置_0", "等级"));
    
    // 记录指令触发时间（用于间隔判断）
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
    // 检查是否已有宠物
    if (等级 >= 1) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 您已经有宠物了，贪多嚼不烂哦!\n当前宠物：Lv." + 等级 + "-" + 宠物名, true);
        return;
    }
    
    String 选定;
    // 截取宠物名（消息长度≥3时，从第2位开始截取）
    if (消息.length() >= 3) {
        选定 = 消息.substring(2);
    }
    
    // 未指定宠物名时的处理
    if (选定 == null) { 
        Random random = new Random();
        int index = random.nextInt(市场上架宠物.length);
        String[] 示例宠物 = 市场上架宠物[index];
        发送(qun, "[AtQQ="+uin+"]  "+ " 疑似指令出错！\n◆指令：领养+宠物名字\n◇示例：领养" + 示例宠物[0], true);
        return;
    }

    // 检查宠物是否在市场上架
    int 积分价格 = 0;  
    boolean isPetFound = false;  
    
    for (String 宠物信息 : 市场上架宠物) {  
        String[] 拆分结果 = 宠物信息.split(",");  
        
        // 格式错误时终止循环
        if (拆分结果.length != 2) {  
            break; 
        }  
        
        String 宠物名 = 拆分结果[0].trim();  
        String 积分字符串 = 拆分结果[1].trim();  

        // 匹配宠物名
        if (宠物名.equals(选定)) {  
            isPetFound = true;  
            积分价格 = Integer.parseInt(积分字符串);  
            break; // 找到后终止循环
        }
    }  

    // 处理宠物查找结果
    if (!isPetFound) {
        发送(qun, "[AtQQ="+uin+"]  "+ " 宠物「" + 选定 + "」未在市场上架，请检查宠物名称是否正确。", true);  
    } else {
        // 获取宠物对象
        Pet selectedPet = petMap.get(选定);
        
        if (selectedPet == null) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 领养失败！\n没有查询到〔" + 选定 + "〕，请检查宠物名称是否正确～", true);
        } else if (积分价格 < 0) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 宠物「" + 选定 + "」在市场没有拟定好积分价格！", true);  
        } else {
            // 检查积分是否足够
            long 积分 = 文转(getString(qun + "/" + uin + "/我的资产", "积分"));
            
            if (积分价格 > 积分) {
                发送(qun, "[AtQQ="+uin+"]  "+ " 领养[" + 选定 + "]失败！\n你的积分不足[" + 数值转(积分价格) + "]，还差" + 数值转(积分价格 - 积分) + "积分哦！", true);
            } else {   
                // 扣除积分并记录宠物信息
                String quality = selectedPet.getQuality(); // 品质
                String attribute = selectedPet.getAttribute(); // 属性名
                String gender = selectedPet.getGender(); // 性别
                
                //扣除对应积分并存储宠物相关数据
                putString(qun + "/" + uin + "/我的资产", "积分", 转文(积分 - 积分价格));
                选择宠物(qun, uin, attribute, gender, quality, 选定); // 选定宠物并存储数据
                当前宠物(qun, uin, 0); // 生成图片
                
                // 发送成功消息
                发送(qun, 
                    "[AtQQ="+uin+"]  "+ " 领养成功！\n" +
                    "•消耗积分：" + 数值转(积分价格) + "\n" +
                    "•领养宠物：[" + quality + "]-" + 选定 + "\n" +
                    "————————\n" +
                    "详细宠物信息如下：[PicUrl=" + AppPath + "/缓存/宠物/" + uin + "_宠物图.png]", 
                    false);
            }
        }
    }
}


// 在【限时商店】上架的道具，【交易+道具】指令可以用到
// 格式：道具名,交易消耗金币
String[] 限时上架道具 = {
    "凝神丹,15","生命之源,100", "薄荷糖,10", "蜂蜜果糖,50", "坚果棒,100", "能量饼干,200",
    "巧克力棒,250", "人参茶,300", "活力奶昔,500", "超级能量果,1000", "经验丹,120", "金蝉,500", "宠物背景卡,1050", "宠物定制卡,2500", "轮回玉,5000000", "溯天玉,12500000", "生命转智力,15", "生命转攻击,15", "生命转防御,15", "智力转攻击,15", "智力转防御,15",
    "智力转生命,15", "防御转攻击,15", "防御转智力,15", "防御转生命,15", "攻击转智力,15", "攻击转防御,15", "攻击转生命,15"
};


//「限时商店」开放时间范围
int openHour = 8; //开放时间
int closeHour = 22; //关闭时间

//根据道具构建相关内容，【限时商店】可在指定时间开放
if(消息.startsWith("限时商店")){
    int 道具数量 = 限时上架道具.length;
    int 页数上限 = (道具数量 + 9) / 10; //道具不足10个也占一页
    String 目标页数 = "1";
    
// 获取当前时间
Calendar cal = Calendar.getInstance();
int currentHour = cal.get(Calendar.HOUR_OF_DAY);
int currentMinute = cal.get(Calendar.MINUTE);


// 计算时差（分钟为单位）
if (currentHour < openHour) {
    // 未到开放时间，计算剩余时间（修正分钟进位）
    int totalMinutes = (openHour - currentHour) * 60 - currentMinute;
    int remainingHours = totalMinutes / 60;
    int remainingMinutes = totalMinutes % 60;
    发送(qun,"[AtQQ="+uin+"]   \n「限时商店」未开放！\n（开放时间："+openHour+":00-"+closeHour+":00）\n预计在" + remainingHours + " 小时 " + remainingMinutes + " 分钟后开放",true);
    return;
} 

// 已关闭，提示相关内容
if (currentHour > closeHour) {
    // 计算已关闭时间（修正分钟进位）
    int totalPassedMinutes = (currentHour - closeHour) * 60 + currentMinute;
    int passedHours = totalPassedMinutes / 60;
    int passedMinutes = totalPassedMinutes % 60;
    
    // 计算到次日开放的时间（修正分钟进位）
    int totalTomorrowMinutes = (24 - currentHour + openHour) * 60 - currentMinute;
    int tomorrowHours = totalTomorrowMinutes / 60;
    int tomorrowMinutes = totalTomorrowMinutes % 60;
    
    发送(qun,"[AtQQ="+uin+"]   \n「限时商店」已关闭！\n（开放时间："+openHour+":00-"+closeHour+":00）\n已关闭 " + passedHours + " 小时 " + passedMinutes + " 分钟\n距离下次开放还剩 " + tomorrowHours + " 小时 " + tomorrowMinutes + " 分钟",true);
    return; // 提前停止，不进行后续逻辑
}


    // 获取目标页数
    if (消息.equals("限时商店")) {
        目标页数 = "1";
    } else if (消息.length() >= 5) {
        String 去掉前缀 = 消息.substring(4);
        if (去掉前缀.matches("\\d+")) {
            int 数字 = Integer.parseInt(去掉前缀);
            目标页数 = (数字 > 页数上限) ? "1" : Math.min(数字, 页数上限) + "";
        }
    }
    
    int 当前页 = Integer.parseInt(目标页数);
    int 开始索引 = (当前页 - 1) * 10;
    int 结束索引 = Math.min(开始索引 + 10, 道具数量);
    
    if (结束索引 <= 开始索引) { 
        发送(qun, "[AtQQ="+uin+"]  当前限时商店暂无可交换的道具~", true);
        userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
        return; 
    }
    
    // 截取当前页宠物列表
    String[][] 交易道具 = new String[结束索引 - 开始索引][2];
    for (int i = 0; i < 交易道具.length; i++) {
        String[] 道具拆分 = 限时上架道具[开始索引 + i].split(",");
        交易道具[i][0] = 道具拆分[0].trim(); //去除空格
        交易道具[i][1] = 道具拆分[1].trim(); //去除空格
    }

    StringBuilder sy = new StringBuilder();
    for (String[] prop : 交易道具) {
        String propName = prop[0];
        String priceStr = prop[1];
        
        // 从itemMap获取道具类型
        Item item = (Item) itemMap.get(propName);
        String type = item != null ? item.getType() : "*未知*";        
        long num = 文转(priceStr);  
        
        if (item == null) {
            priceStr = "此道具不存在！";
        } else if(num < 1 ) {
            priceStr = "价格未定义！";
        }
        
        sy.append("[").append(type).append("]·").append(propName) .append("<起>").append(priceStr).append("<尾>").append("\n"); 
        //↑拼接内容（格式：类型•道具名字  积分）        
    
    }
    
// 计算已开放时长（支持跨天，如开放时间为前一天20:00，当前为次日9:00）
int totalOpenMinutes = 0;
if (currentHour >= openHour) {
    totalOpenMinutes = (currentHour - openHour) * 60 + currentMinute;
} else {
    // 跨天情况：当前时间在开放时间前一天
    totalOpenMinutes = ((24 - openHour) + currentHour) * 60 + currentMinute;
}
int openedHours = totalOpenMinutes / 60;
int openedMinutes = totalOpenMinutes % 60;

// 计算剩余时长（支持跨天，如关闭时间为次日5:00，当前为23:00）
int totalRemainingMinutes = 0;
if (currentHour < closeHour) {
    totalRemainingMinutes = (closeHour - currentHour) * 60 - currentMinute;
} else {
    // 跨天情况：当前时间在关闭时间后一天
    totalRemainingMinutes = ((24 - currentHour) + closeHour) * 60 - currentMinute;
}
int remainingHours = totalRemainingMinutes / 60;
int remainingMinutes = totalRemainingMinutes % 60;

// 原有计算逻辑不变，仅修改消息组装部分
String message = "// 限时商店正在开放中，已开放 " + openedHours + " 小时 " + openedMinutes + " 分钟\n" + "// 距离关闭还剩 " + (remainingHours > 0 ? remainingHours + " 小时 " : "") + remainingMinutes + " 分钟";
    
    //拼接文本    
    Random random = new Random();
    int index = random.nextInt(交易道具.length);
    String[] 示例道具 = 交易道具[index];
    String 后文 = "\n•翻页：限时商店+页数\n•指令：*交易+道具名#数量*\n•示例：交易" + 示例道具[0] + "#"+随机数(2,99999)+"\nTime：" + getTodayDate(2)+"\n"+message;
    String 内容 = "//在此页面可消耗对应金币来交易道具\n<分割>↝限时•商店↜</分割>\n" + sy.toString().trim() + "\n<分割>•页数：" + 当前页 + " / " + 页数上限 + " •</分割>" + 后文;
    toImg(内容, "", 0.5, 0.01, 30, AppPath + "/缓存/其他/限时商店" + 当前页 + ".png",true);
    发送(qun, "[PicUrl=" + AppPath + "/缓存/其他/限时商店" + 当前页 + ".png]", false);
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
}


//“限时商店”的配套指令，格式【交易+道具#数量】
if (消息.startsWith("交易")||消息.startsWith("交换")) {
    long 金币 = 文转(getString(qun+"/"+uin+"/我的资产","金币"));
    String bagKey = qun+"/"+uin+"/我的背包";
    String bag = getString(bagKey, "道具列表");
    String 选定; 

    // 记录指令触发时间（被记录的时间戳可用于间隔判断）
    userLastTriggerTime.put(qun + "_" + uin, new TimestampWithText(System.currentTimeMillis(), 消息));
    
// 获取当前时间
Calendar cal = Calendar.getInstance();
int currentHour = cal.get(Calendar.HOUR_OF_DAY);
int currentMinute = cal.get(Calendar.MINUTE);

// 计算时差（分钟为单位）
if (currentHour < openHour) {
    // 未到开放时间，计算剩余时间（修正分钟进位）
    int totalMinutes = (openHour - currentHour) * 60 - currentMinute;
    int remainingHours = totalMinutes / 60;
    int remainingMinutes = totalMinutes % 60;
    发送(qun,"[AtQQ="+uin+"]   「限时商店」未开放！\n（开放时间："+openHour+":00-"+closeHour+":00）\n预计在" + remainingHours + " 小时 " + remainingMinutes + " 分钟后开放",true);
    return;
} 

// 已关闭，提示相关内容
if (currentHour > closeHour) {
    // 计算已关闭时间（修正分钟进位）
    int totalPassedMinutes = (currentHour - closeHour) * 60 + currentMinute;
    int passedHours = totalPassedMinutes / 60;
    int passedMinutes = totalPassedMinutes % 60;
    
    // 计算到次日开放的时间（修正分钟进位）
    int totalTomorrowMinutes = (24 - currentHour + openHour) * 60 - currentMinute;
    int tomorrowHours = totalTomorrowMinutes / 60;
    int tomorrowMinutes = totalTomorrowMinutes % 60;
    
    发送(qun,"[AtQQ="+uin+"]   「限时商店」已关闭！\n（开放时间："+openHour+":00-"+closeHour+":00）\n已关闭 " + passedHours + " 小时 " + passedMinutes + " 分钟\n距离下次开放还剩 " + tomorrowHours + " 小时 " + tomorrowMinutes + " 分钟",true);
    return; // 提前停止，不进行后续逻辑
}

    //初始化限时道具价格映射（根据【限时上架道具】来拆分获取）
    Map 限时道具价格Map = new HashMap();
    for (String prop : 限时上架道具) {
       String[] split = prop.split(",");
       if (split.length == 2) {
          限时道具价格Map.put(split[0], Long.parseLong(split[1]));
       }
    }

    // 先从消息中截取内容作为“选定”值
    if (消息.contains("#")) {
        int charIndex = 消息.indexOf('#');
        选定 = 消息.substring(2, charIndex);
    } else if (消息.length() >= 3) {
        选定 = 消息.substring(2);
    }

    if (选定 == null) { // 没有指定道具，发送提示
        Random random = new Random();
        int index = random.nextInt(限时上架道具.length); // 生成随机索引
        String[] randomPropSplit = 限时上架道具[index].split(",");
        String randomItem = randomPropSplit[0]; // 获取随机道具名称
        发送(qun,"[AtQQ="+uin+"]   疑似指令出错！\n◆指令：交易+道具#数量\n◇示例：交易"+randomItem+"#"+随机数(2, 9999),true);
        return;
    }

    // 检查道具是否在限时上架列表中
    if (!限时道具价格Map.containsKey(选定)) { 
        发送(qun, "[AtQQ="+uin+"]  "+ " 限时商店没有上架〔" + 选定 + "〕哦～\n\n◆指令：交易+道具#数量",true);
        return;
    }

    // 获取价格与数量处理
    long 售价 = 限时道具价格Map.get(选定);
    String 数字部分 = 消息.replaceAll("[^\\d]", ""); // 提取数字
    
    if (数字部分.isEmpty()) {
        数字部分 = "1"; // 无数字默认兑换1个
    } else {
        // 处理前导零和零数量（统一转为1）
        String 处理后数字 = 数字部分.replaceFirst("^0+", "");
        数字部分 = 处理后数字.isEmpty() || "0".equals(处理后数字) ? "1" : 处理后数字;
    }

    long 数量 = Long.parseLong(数字部分);
    if (数量 > 999999) { // 购买量超出单次限制
        发送(qun,"[AtQQ="+uin+"]   \n交易数量不能超过999999！",true);
        return;
    }

    long 消费 = 售价 * 数量; // 直接使用拆分后的价格计算
    if (消费 > 金币) {
        long 预计 = 金币 / 售价;
        if (预计 >= 1) {
            发送(qun, "[AtQQ="+uin+"]  "+ " 交易失败！你的金币不足[" + 消费 + "]，还差" + (消费 - 金币) + "金币哦！\n\n你的金币当前能交易到" + 预计 + "个" + 选定 + "～",true);
        } else {
            发送(qun, "[AtQQ="+uin+"]  "+ " 交易失败！你的金币不足[" + 消费 + "]，还差" + (消费 - 金币) + "金币哦！",true);
        }
        return;
    }

    // 处理背包逻辑
    if (bag != null && bag.contains(选定)) {
        long count = 文转(getString(bagKey, 选定));
        putString(bagKey, 选定, 转文(count + 数量));
    } else {
        String newBag = (bag == null ? "" : bag + "、") + 选定;
        putString(bagKey, "道具列表", newBag);
        putString(bagKey, 选定, 转文(数量));
    }
    putString(qun + "/" + uin + "/我的资产", "金币", 转文(金币 - 消费)); 
     //获取该道具的相关信息
     Item selectedItem = (Item) itemMap.get(选定);
     String 简介=selectedItem.getDescription(); //获取道具简介
    发送(qun, "[AtQQ="+uin+"]  "+ " 成功交易「" + 选定 + "」×" + 数量 + "！消耗 " + 数值转(消费) + " 金币，已存入背包~ \n◆可发送【我的背包】查看物品\n物品说明："+简介, true);
}



}

//将礼包中的道具格式化（添加空格）
public static String formatPropDetails(String propDetails, int number) {
    if (propDetails == null || propDetails.isEmpty()) {
        return propDetails;
    }

    // 按"、"分割道具列表
    String[] props = propDetails.split("、");
    if (props.length <= number) {
        // 不足指定个数元素直接返回
        return propDetails;
    }

    StringBuilder result = new StringBuilder();
    int groupCount = 0; // 记录当前是第几组

    for (int i = 0; i < props.length; i++) {
        result.append(props[i]);

        // 不是最后一个元素时添加分隔符
        if (i < props.length - 1) {
            result.append("、");

            // 每 number 个元素为一组，处理分组
            if ((i + 1) % number == 0) {
                groupCount++;
                if (groupCount == 1) {
                    result.append("\n                      ");
                } else {
                    result.append("\n                      ");
                }
            }
        }
    }


    return result.toString();
}




//将内容格式化
public static String formatString(String input) {
    // 按"、"分割字符串
    String[] parts = input.split("、");
    if (parts.length == 0) {
        return "";
    }

    StringBuilder result = new StringBuilder();

    // 遍历处理每个元素
    for (int i = 0; i < parts.length; i++) {
        // 为每个元素前面添加两个空格
        result.append("  ");
        // 添加当前元素
        result.append(parts[i]);

        // 判断数组长度，至少两个元素时才添加标记
        if (parts.length >= 2) {
            if (i % 2 == 0) {
                result.append("<起>");
            } else {
                result.append("<尾>");

                // 每两组元素后添加换行符（非最后一组）
                if (i + 1 < parts.length) {
                    result.append("\n");
                }
            }
        }
    }

    return result.toString();
}


/*
 * 搜索宠物并返回相关信息： [属性, 性别, 品质]
 * 未找到返回null
 */
public static String[] searchPet(String petName) {
    // 1. 先从petMap中搜索
    if (petMap.containsKey(petName)) {
        Pet pet = (Pet) petMap.get(petName);
        return new String[]{pet.getAttribute(), pet.getGender(), pet.getQuality()};
    }
    
    // 2. 若petMap未找到，从定制记录中搜索
    String 定制记录 = getString("定制宠物", "宠物列表");
    if (定制记录 != null && !定制记录.isEmpty()) {
        String[] 宠物数组 = 定制记录.split("、");
        for (String petItem : 宠物数组) {
            String[] items = petItem.split(",");
            if (items.length >= 1 && items[0].equals(petName)) {
                // 定制记录中找到，品质固定为"定制"
                String attribute = items.length >= 2 ? items[1] : "未知属性";
                String gender = items.length >= 3 ? items[2] : "未知性别";
                return new String[]{attribute, gender, "定制"};
            }
        }
    }
    
    // 3. 未找到宠物
    return null;
}


/**
 * 搜索宠物名称中包含关键词的宠物（序号展示）
 * 返回 拼接后的宠物信息字符串，匹配关键词部分用*包裹
 */
public static String searchPetsWithIndex(String keyword) {
    if (keyword == null || keyword.isEmpty()) {
        return "搜索关键词不能为空";
    }
    
    StringBuilder result = new StringBuilder();
    int index = 1;  // 序号计数器
    
    // 1. 从petMap中搜索
    for (Object keyObj : petMap.keySet()) {
        String petName = (String) keyObj;
        if (petName.contains(keyword)) {
            // 给匹配的关键词前后添加*符号
            String highlightedName = petName.replace(keyword, "*" + keyword + "*");
            Pet pet = (Pet) petMap.get(petName);
            long[] petAttributes = petAttributeMap.get(pet.getQuality());
            long 进化次数 = petAttributes[4]; //获取进化次数
            result.append(" ●"+index++).append(". 「名称」：").append(highlightedName)
                 .append("（"+pet.getGender()+"）\n       「属性」：").append(pet.getAttribute())
                 .append("\n       「品质」：").append(pet.getQuality()).append("（"+进化次数+"次进化）\n<填充>\n");
        }
    }
    
    // 2. 从定制记录中搜索
    String customRecords = getString("定制宠物", "宠物列表");
    if (customRecords != null && !customRecords.isEmpty()) {
        String[] petArray = customRecords.split("、");
        for (String petItem : petArray) {
            String[] items = petItem.split(",");
            if (items.length >= 1 && items[0].contains(keyword)) {
                // 给匹配的关键词前后添加*符号
                String highlightedName = items[0].replace(keyword, "*" + keyword + "*");
                String attribute = items.length >= 2 ? items[1] : "未知属性";
                String gender = items.length >= 3 ? items[2] : "未知性别";
                long[] petAttributes = petAttributeMap.get("定制");
                long 进化次数 = petAttributes[4]; //获取进化次数
                result.append(" ●"+index++).append(". 「名称」：").append(highlightedName)
                      .append("（"+gender+"）\n       「属性」：").append(attribute)
                      .append("\n       「品质」：定制（"+进化次数+"次进化）\n<填充>\n");
            }
        }
    }
    
    // 3. 返回结果
    return result.length() > 0 ? "\n// 找到"+(index-1)+"个相关宠物，详情如下：\n<填充>\n"+result.toString() : "未找到名称包含 \"" + keyword + "\" 的宠物";
}
