import android.content.Context;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

// 全局变量（用于存储钱庄名称，可自定义）
String BANK_NAME = "通元钱庄";
// 存储数据的配置文件名
String BANK_CONFIG = "BankData"; //银行数据
String RECORD_CONFIG = "BankRecords"; //银行记录
// 初始默认月利率（0.5%，正数为利息，负数为管理费）
float DEFAULT_RATE = 0.5f; 

/*
 如果想给这些指令的反馈消息加上随机提示，
 可以改成下面这种样式：↓
 发送(qun,"[AtQQ="+uin+"]  这是内容",true);
 
 true=消息后面加上随机提示，false=原消息(不加随机提示)
*/

// 处理玩家存款指令
void handleDeposit(String qun, String uin, String 消息) {
        // 检查账户是否被冻结
        String freezeStatus = getString(BANK_CONFIG, "freeze_" + uin);
        if ("1".equals(freezeStatus)) {
           sendMsg(qun, "", "[AtQQ="+uin+"]  您的账户已被冻结，无法进行此操作");
           return;
        }
 
        // 解析金额
        String[] parts = 消息.split("#");
        if (parts.length != 2) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  指令格式错误，请使用：存款#数量");
            return;
        }
        long amount = 文转(parts[1].trim());
        if (amount <= 0) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  存款金额必须为正数");
            return;
        }
        
        // 验证玩家金币
         long Gold = 文转(getString(qun+"/"+uin+"/我的资产", "金币"));
         if (Gold < amount) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  哎呀，客官您背包里的金币不足"+amount+"呀！\n当前仅【"+Gold+"金币】，还差【"+(amount-Gold)+"金币】才能完成存款。\n\n掌柜挠挠头：“客官要不要先去凑凑数？”");
             return;
         }
        
        // 读取当前账户余额
        String balanceStr = getString(BANK_CONFIG, uin);
        long balance = 文转(balanceStr);
        long newBalance = balance + amount;
        
        // 更新余额
        putString(BANK_CONFIG, uin, 转文(newBalance)); //更新银行账户余额
        putString(qun+"/"+uin+"/我的资产", "金币", 转文(Gold-amount));//更新玩家余额
        // 记录操作
        addRecord(uin, "存入", amount, newBalance);
        
      // 发送反馈
      String 文案=" 叮——————\n"+玩家名(qun,uin)+" 您成功将\n〖"+amount+"〗金币存入【"+BANK_NAME+"】，\n账户余额更新为：〖"+newBalance+"〗金币。\n\n钱庄掌柜拱手笑道：“客官放心，您的金币，咱家定会妥善看管~”";
      toImg(文案,"",0.5,0.01,35,AppPath+"/缓存/其他/"+uin+"存款.png",false);
     发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"存款.png]",false);
}


// 处理玩家取款指令
void handleWithdraw(String qun, String uin, String 消息) {
       // 检查账户是否被冻结
        String freezeStatus = getString(BANK_CONFIG, "freeze_" + uin);
        if ("1".equals(freezeStatus)) {
           sendMsg(qun, "", "[AtQQ="+uin+"]  您的账户已被冻结，无法进行此操作");
           return;
        }
        
        //解析金额
        String[] parts = 消息.split("#");
        if (parts.length != 2) {
            sendMsg(qun, "", "[AtQQ="+uin+"]   指令格式错误，请使用：取款#数量");
            return;
        }
        long withdrawAmount = 文转(parts[1].trim());
        if (withdrawAmount <= 0) {
            sendMsg(qun, "", "[AtQQ="+uin+"]   取款金额必须为正数");
            return;
        }
        
        // 1. 获取当前账户余额
        String balanceStr = getString(BANK_CONFIG, uin);
        long balance = 文转(balanceStr);
        if (balance < withdrawAmount) {
            sendMsg(qun, "", "[AtQQ="+uin+"]   账户余额不足"+withdrawAmount+"\n✦当前余额："+balance+"金币");
            return;
        }
        
        // 2. 获取当前利率（优先读取设置的利率，无则用默认值）
        String rateStr = getString(BANK_CONFIG, "InterestRate");
        float rate = (rateStr == null) ? DEFAULT_RATE : Float.parseFloat(rateStr);
        
        // 3. 计算利息/管理费（按取款金额的月利率计算，简化为单次取款时直接计算）
        // 公式：费用 = 取款金额 × (利率 ÷ 100)，保留整数（四舍五入）
        long fee = Math.round(withdrawAmount * (rate / 100));
        String feeDesc = (fee >= 0) ? "利息" : "管理费";
        fee = Math.abs(fee); // 取绝对值，用正负判断类型
        
        if (rate < 0 && withdrawAmount <= fee) {
          sendMsg(qun, "", "[AtQQ="+uin+"] 取款金额不足抵扣管理费，请减少取款数量");
          return;
        }
        
        // 4. 计算实际到账金额和账户剩余余额
        long actualAmount; // 玩家实际拿到的金币
        long newBalance;   // 账户剩余余额
        if (rate >= 0) {
            // 正利率（利息）：取款时额外增加利息
            actualAmount = withdrawAmount + fee;
            newBalance = balance - withdrawAmount; // 本金扣除，利息由系统额外添加
        } else {
            // 负利率（管理费）：从取款金额中扣除管理费
            actualAmount = withdrawAmount - fee;
            newBalance = balance - withdrawAmount; // 本金扣除
        }
        
        // 5. 验证管理费场景下实际到账金额不为负
        if (actualAmount <= 0) {
            sendMsg(qun, "", "[AtQQ="+uin+"]   取款金额扣除管理费后为负数，请减少取款金额");
            return;
        }
        
        long Gold = 文转(getString(qun+"/"+uin+"/我的资产", "金币"));
        // 6. 更新账户余额
        putString(BANK_CONFIG, uin, 转文(newBalance));
        putString(qun+"/"+uin+"/我的资产", "金币", 转文(Gold+actualAmount));
        
        // 7. 记录操作（包含利率信息）
        String operation = "取出（" + feeDesc + "：" + fee + "枚）";
        addRecord(uin, operation, withdrawAmount, newBalance);
        // 8. 发送反馈
      String 文案=玩家名(qun,uin)+" ("+uin+")\n您从【" + BANK_NAME + "】取出" + withdrawAmount + "枚金币\n(" + feeDesc + "：" + fee + "枚)\n实际到账" + actualAmount + "枚\n账户余额：" + newBalance + "金币\n\n掌柜笑着递过钱袋：“带好您的金币，路上小心~”";
      toImg(文案,"",0.5,0.01,35,AppPath+"/缓存/其他/"+uin+"存款.png",false);
     发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"存款.png]",false);
}


// 处理查询余额指令
void handleCheckBalance(String qun, String uin) {
    long balance = 文转(getString(BANK_CONFIG, uin));
    sendMsg(qun, "", "经【" + BANK_NAME + "】账房查阅，\n"+"[AtQQ="+uin+"]  您当前持有：\n•" + balance + "枚金币");
}

// 处理转账指令（仅银行账户互转，如果要取出可发送〔取款#数量〕指令）
void handleTransfer(String qun, String uin, String 消息) {
        // 检查账户是否被冻结
        String freezeStatus = getString(BANK_CONFIG, "freeze_" + uin);
        if ("1".equals(freezeStatus)) {
           sendMsg(qun, "", "[AtQQ="+uin+"]  您的账户已被冻结，无法进行此操作");
           return;
        }
        
        //解析金额
        String[] parts = 消息.split("#");
        if (parts.length != 3) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  指令格式错误，请使用：转账#玩家ID#数量");
            return;
        }
        String targetUin = parts[1].trim();
        long amount = 文转(parts[2].trim());
        String 玩家列表 = getString("玩家总列表", "总列表");
        if(玩家列表!=null&&!玩家列表.contains(targetUin)){
          发送(qun,"[AtQQ="+uin+"]  "+" 未找到账号为["+玩家账号+"]的玩家！\n◆请检查账号是否输入错误！",true);
          return;
        }
         // 检查对方账户是否被冻结
        String freezeStatus = getString(BANK_CONFIG, "freeze_" + targetUin);
        if ("1".equals(freezeStatus)) {
           sendMsg(qun, "", "[AtQQ="+uin+"]  对方账户已被冻结，无法对其进行此操作");
           return;
        }
        if (amount <= 0) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  转账金额必须为正数");
            return;
        }
        
        // 验证转出方余额
        String balanceStr = getString(BANK_CONFIG, uin);
        long balance = 文转(balanceStr);
        if (balance < amount) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  账户余额不足，无法转账\n✦金币余额："+balance);
            return;
        }
        
        // 读取接收方余额
        String targetBalanceStr = getString(BANK_CONFIG, targetUin);
        long targetBalance = 文转(targetBalanceStr);
        
        // 更新双方余额
        putString(BANK_CONFIG, uin, 转文(balance - amount));
        putString(BANK_CONFIG, targetUin, 转文(targetBalance + amount));
        
        // 记录操作
        addRecord(uin, "转出至" + targetUin, amount, balance - amount);
        addRecord(targetUin, "收到" + uin + "转入", amount, targetBalance + amount);
        
        // 发送反馈
        sendMsg(qun, "", "【" + BANK_NAME + "】\n[AtQQ="+uin+"]  你向" + targetUin + "转赠" + amount + "金币已到账，当前余额：" + (balance - amount) + "金币");
}

// 处理查看明细指令
void handleCheckRecords(String qun, String uin) {
    String records = getString(RECORD_CONFIG, uin);
    if (records.isEmpty()) {
        sendMsg(qun, "", "【" + BANK_NAME + "】暂无交易记录");
        return;
    }
    
    // 拆分记录（最多显示10条）
    StringBuilder sb = new StringBuilder("【" + BANK_NAME + "】近10笔账目：\n");
    sb.append(records);
    toImg(玩家名(qun,uin)+" ("+uin+")\n"+sb.toString().trim(),"",0.5,0.01,35,AppPath+"/缓存/其他/"+uin+"操作明细.png",false);
  发送(qun,"[PicUrl="+AppPath+"/缓存/其他/"+uin+"操作明细.png]",false);
}


// GM清零账户指令
void handleGMReset(String qun, String operatorUin, String 消息) {
        String[] parts = 消息.split("#");
        if (parts.length != 2) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  指令格式错误，请使用：清零账户#玩家ID");
            return;
        }
        String targetUin = parts[1].trim();
        
        // 记录清零前余额（用于日志）
        String oldBalance = getString(BANK_CONFIG, targetUin);
        
        // 清零账户
        putString(BANK_CONFIG, targetUin, null);
        addRecord(targetUin, "GM清零账户（原余额" + oldBalance + "）", 0, 0);
        
        // 发送反馈
        sendMsg(qun, "", "[AtQQ="+uin+"]  已将" + targetUin + "的账户余额清零，操作记录已存档");
}

// GM查询玩家账户指令
void handleGMCheckAccount(String qun, String operatorUin, String 消息) {
        String[] parts = 消息.split("#");
        if (parts.length != 2) {
            sendMsg(qun, "", "[AtQQ="+operatorUin+"]  指令格式错误，请使用：查询玩家账户#玩家ID");
            return;
        }
        String targetUin = parts[1].trim();
        
        // 获取余额和最新记录
        String balance = getString(BANK_CONFIG, targetUin);
        String records = getString(RECORD_CONFIG, targetuin);
        String lastRecord = records.isEmpty() ? "无记录" : records.split("\\|")[records.split("\\|").length - 1];
        
        // 发送反馈
        sendMsg(qun, "", "[AtQQ="+operatorUin+"]  " + targetUin + "账户信息：\n余额：" + balance + "金币\n最新记录：" + lastRecord);
}

// GM冻结账户指令
void handleGMFreeze(String qun, String operatorUin, String 消息) {
        String[] parts = 消息.split("#");
        if (parts.length != 3) {
            sendMsg(qun, "", "[AtQQ="+operatorUin+"]  指令格式错误，请使用：冻结账户#玩家ID#冻结状态（1=冻结，0=解冻）");
            return;
        }
        String targetUin = parts[1].trim();
        String statusStr = parts[2].trim();
        
        // 验证冻结状态参数
        if (!"0".equals(statusStr) && !"1".equals(statusStr)) {
            sendMsg(qun, "", "[AtQQ="+operatorUin+"]  冻结状态只能是0（解冻）或1（冻结）");
            return;
        }
        boolean isFrozen = "1".equals(statusStr);
        
        // 存储冻结状态（使用银行配置文件，键格式："freeze_玩家ID"）
        putString(BANK_CONFIG, "freeze_" + targetUin, statusStr);
        
        // 记录操作
        String operation = isFrozen ? "账户被冻结" : "账户被解冻";
        addRecord(targetUin, operation, 0, 0); // 金额相关参数填0
        
        // 发送反馈
        sendMsg(qun, "", "[AtQQ="+operatorUin+"]  操作成功\n" +
                "✦目标账户：" + targetUin + "\n" +
                "✦当前状态：" + (isFrozen ? "已冻结（无法进行存取款操作）" : "已解冻（可正常使用）"));
}

// GM设置利率指令（预留利率功能，当前仅记录）
void handleSetRate(String qun, String operatorUin, String 消息) {
        String[] parts = 消息.split("#");
        if (parts.length != 2) {
            sendMsg(qun, "", "[AtQQ="+uin+"]  指令格式错误，请使用：设置利率#数值");
            return;
        }
        float rate = Float.parseFloat(parts[1].trim());
        
        // 存储利率（后续利息计算可读取此值）
        putString(BANK_CONFIG, "InterestRate", String.valueOf(rate));
        
        // 发送反馈
        sendMsg(qun, "", "[AtQQ="+operatorUin+"]  " + BANK_NAME + "利率已更新为" + rate + "%");
}

// GM统计全服余额指令（区分0余额账户）
void handleGMCountTotal(String qun, String operatorUin, String 消息) {
    
    // 获取所有玩家ID列表
    String 玩家列表 = getString("玩家总列表", "总列表");
    if (玩家列表 == null || 玩家列表.isEmpty()) {
        sendMsg(qun, "", "[AtQQ=" + operatorUin + "]  全服暂无玩家数据");
        return;
    }
    
    // 拆分玩家ID
    String[] allUins = 玩家列表.split("、");
    long totalBalance = 0;       // 全服总余额
    int positiveCount = 0;       // 余额>0的账户数
    int zeroCount = 0;           // 余额=0的账户数
    int noAccountCount = 0;      // 无账户记录的玩家数（从未存款过）
    
    // 遍历所有玩家计算
    for (String uin : allUins) {
        uin = uin.trim();
        if (uin.isEmpty()) continue;
        
        // 读取玩家账户余额
        String balanceStr = getString(BANK_CONFIG, uin);
        if (balanceStr == null || balanceStr.isEmpty()) {
            // 从未存款过（无账户记录）
            noAccountCount++;
            continue;
        }
        
        long balance = 文转(balanceStr);
        if (balance > 0) {
            totalBalance += balance;
            positiveCount++;
        } else if (balance == 0) {
            zeroCount++;
        }
    }
    
    // 发送统计结果
    sendMsg(qun, "", "[AtQQ=" + operatorUin + "]  \n\n【" + BANK_NAME + "】全服账户统计：\n" +
            "✦总玩家数：" + allUins.length + "人\n" +
            "✦有存款账户（余额>0）：" + positiveCount + "个\n✦总存款：" + 数值转(totalBalance) + "金币\n" +
            "✦零余额账户（余额=0）：" + zeroCount + "个\n" +
            "✦无账户记录（从未存款）：" + noAccountCount + "人");
}


// 添加操作记录
void addRecord(String uin, String type, long amount, long newBalance) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String time = sdf.format(new Date());
    String record2 = time + "\n" + type + " " + amount + "金币，余额：" + newBalance + "金币";
    
    // 读取现有记录（处理null情况）
    String oldRecords = getString(RECORD_CONFIG, uin);
    // 关键修复：如果是null则视为空字符串
    oldRecords = (oldRecords == null) ? "" : oldRecords;
    
    // 拼接新记录（用|分隔）
    String newRecords = oldRecords.isEmpty() ? record2 : oldRecords + "\n<填充>\n" + record2;
    // 存储记录
    putString(RECORD_CONFIG, uin, newRecords);
}