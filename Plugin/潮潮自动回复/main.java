import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import java.util.Random;
import java.lang.*;

//群聊默认开启-true 默认关闭-false
//（开关反转：即关变开，开变关）
boolean defGroup = false;

//私聊默认开启-true 默认关闭-false
//（开关反转：即关变开，开变关）
boolean defFriend = true;

//优先级低于上面两个
//群聊-2 私聊-1 全开-3
byte target = 3;

//触发间隔(没有连戳检测，防止刷屏啊) 单位秒
short interval = 10;

//回复类型，随机回复，为空就留个{}
//图文消息写"[PicUrl=图片本地或网络地址]"
//艾特写"[@] "，固定艾特写"[AtQQ=QQ号]"  私聊不生效
//注意这个"[@] "里的空格省略可能会出现@出错（QQ，很神奇吧）
//今日被戳次数写"[times]"
//禁言写[forbidden=时间]，例如[forbidden=10]为10秒
//分隔发送写[next]（内置150ms延迟）
//延迟发送写[delay=时间]，单位毫秒
String[] replyText = {
    "[@] [next]杂鱼~",
    "谁潮我？？",
    "你！" + "[next]不！" + "[next]许！" + "[next]潮！" + "[next]我！" + "[next]了！",
    "今天被潮 [times] 次了",
    "[forbidden=30]你不许潮我了！",
    "[poke]回潮！",
    "别潮了，给你猫图自己玩去[PicUrl=https://api.lolimi.cn/API/chaiq/c.php]"
};
//回复图片链接，直链
//例如  https://api.lolimi.cn/API/chaiq/c.php
String[] replyPicLink = {
};
//回复图片文件夹，本地文件夹
//例如  /storage/emulated/0/表情包
//文件夹内切勿有非表情包文件！！！
String[] replyPicPath = {
};

//随机方式 
//0 全随机
//1-100 文字出现的百分比概率，图片概率则相反
int replyType = 30;

//黑名单QQ，被戳时不会有反应（仍然累计被戳次数）
String[] blackList = {
"123456789"
};

//自己戳自己是否有触发间隔
boolean selfCooldown = false;

//给你们搞的清楚一点，可以自己魔改
//魔改后重发布最好问一下本猫娘
public void Callback_OnRawMsg(Object msg){

    //不知道为什么try catch捕获不到，只能用这种
    //戳戳检测，避免抛出异常
    if(Objects.isNull(msg)
            || msg.elements.size() == 0
            || Objects.isNull(msg.elements)
            || Objects.isNull(msg.elements.get(0))
            || Objects.isNull(msg.elements.get(0).grayTipElement)
            || Objects.isNull(msg.elements.get(0).grayTipElement.XmlElement)
            || Objects.isNull(msg.elements.get(0).grayTipElement.XmlElement.templParam)){
        return;
    }
    
    //解析msg对象，templParam里面是个List，所以get
    Object receive = msg.elements.get(0).grayTipElement.XmlElement;
    String targetQQ = receive.templParam.get("uin_str2");
    //默认只有被戳的是自己生效
    if(targetQQ == null || !targetQQ.equals(MyUin)) return;
    String senderQQ = receive.templParam.get("uin_str1");
    //单独开关检测，没有测试过频道会怎么样
    if(msg.chatType == 1){
        boolean isTrue = getBoolean("switcher", senderQQ, true);
        if(defFriend != isTrue) return;
    }else if(msg.chatType == 2){
        boolean isTrue = getBoolean("switcher", msg.peerUid, true);
        if(defGroup != isTrue) return;
    }else{
        return;
    }
    
    //msg.chatType  1-私聊   2-群聊   其他没测试过
    if((msg.chatType == 1 && target % 2 == 1) || (msg.chatType == 2 && target > 1)){
    
        //被戳计数
        String today = getTodayDate();
        putInt("times",today,getInt("times",today,0)+1);
        putInt("times","total",getInt("times","total",0)+1);

        //黑名单
        for (String qq : blackList){
            if(senderQQ.equals(qq)){
                return;
            }
        }
        
        //触发间隔判断
        if((!senderQQ.equals(MyUin) || (senderQQ.equals(MyUin) && selfCooldown)) &&
                ((msg.chatType == 1 && System.currentTimeMillis()
                        - getLong("interval",senderQQ,0) < interval*1000)
                || (msg.chatType == 2 && System.currentTimeMillis()
                        - getLong("interval",msg.peerUid,0) < interval*1000)))
            return;
        
        //三个数组长度
        short textLength = replyText.length;
        short linkLength = replyPicLink.length;
        short pathLength = replyPicPath.length;
        Random r = new Random();
        
        //配置合法性判断
        if(replyType < 0) replyType = 0;
        if(replyType > 100) replyType = 100;
        if(textLength+linkLength+pathLength == 0){
            Toast("未设置任何回复");
            return;
        }
        if(textLength == 0 && replyType > 0){
            replyType = 0;
        }
        if(linkLength + pathLength == 0 && replyType < 100){
            replyType = 100;
        }
        
        //随机方式
        if(replyType == 0){
        //三个数组总长相加，进行随机选择
            int randomNum = r.nextInt(textLength + linkLength + pathLength);
            if(randomNum < textLength){
            //发送文本
                sendTo(msg,randomNum,0);
            }else if(randomNum < textLength+linkLength){
            //发送直链图片
                sendTo(msg,randomNum-textLength,1);
            }else{
            //发送本地图片
                sendTo(msg,randomNum-textLength-linkLength,2);
            }
            //可能以后会更新的发送语音
            
        }else{
            byte randomNum = r.nextInt(100);
            if(randomNum < replyType || linkLength+pathLength == 0){
                sendTo(msg,r.nextInt(textLength),0);
            }else{
                int randomPic = r.nextInt(linkLength + pathLength);
                if(randomPic < linkLength){
                    sendTo(msg,randomPic,1);
                }else{
                    sendTo(msg,randomPic-linkLength,2);
                }
            }
        }
    }
    //有疑惑的自己取消注释然后去试试戳戳自己
    //Toast(msg);
}

//封装一个发送方法
private void sendTo(Object msg, int index, int sendType){
    String senderQQ = msg.elements.get(0).grayTipElement.XmlElement.templParam.get("uin_str1");
    //随机
    Random r = new Random();
    if(msg.chatType == 1){
        //在私聊时戳自己，sender就是自己，要加一个获取当前窗口
        if(senderQQ.equals(MyUin)){
            senderQQ = getCurrentFriendUin();
        }
        //记录戳戳的时间（冷却）
        //先计冷却再发信息（不然遇到delay就完了）
        if(!senderQQ.equals(MyUin) || (senderQQ.equals(MyUin) && selfCooldown))
            putLong("interval",senderQQ,System.currentTimeMillis());
        if(sendType == 0){
            String[] replyTextArr = replyText[index].split("\\[next\\]");
            for(int i = 0; i < replyTextArr.length; i++){
                sendText(msg.chatType, senderQQ, senderQQ, replyTextArr[i]);
                if(i+1 != replyTextArr.length) delay(150);
            }
        }else if(sendType == 1){
            sendPic("",senderQQ,replyPicLink[index]);
        }else if(sendType == 2){
        //获取文件夹内所有文件，并随机选择一个发送
            File file = new File(replyPicPath[index]);
            File[] files = file.listFiles();
            int randomGif = r.nextInt(files.length);
            String pathGif = files[randomGif].getPath();
            sendPic("",senderQQ,pathGif);
        }
    }else if(msg.chatType == 2){
        //获取群号
        String groupUin = msg.peerUid;
        
        if(!senderQQ.equals(MyUin) || (senderQQ.equals(MyUin) && selfCooldown))
            putLong("interval",groupUin,System.currentTimeMillis());
        if(sendType == 0){
            String[] replyTextArr = replyText[index].split("\\[next\\]");
            for(int i = 0; i < replyTextArr.length; i++){
                sendText(msg.chatType, senderQQ, groupUin, replyTextArr[i]);
                if(i+1 != replyTextArr.length) delay(150);
            }
        }else if(sendType == 1){
            sendPic(groupUin,"",replyPicLink[index]);
        }else if(sendType == 2){
            File file = new File(replyPicPath[index]);
            File[] files = file.listFiles();
            int randomGif = r.nextInt(files.length);
            String pathGif = files[randomGif].getPath();
            sendPic(groupUin,"",pathGif);
        }
    }
}

//延迟
private static void delay(int delayMs) {
    try { Thread.sleep(delayMs); } 
    catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
    }
}

//二次封装
private static void sendText(int type, String senderQQ, String targetUin, String originMsg){
    if(type == 1){
        String replyText = originMsg
            .replace("[@]\n","")
            .replace("[@]","")
            .replaceAll("\\[AtQQ=\\d+\\]","")
            .replace("[times]",getInt("times",getTodayDate(),0) + "")
            .replaceAll("\\[forbidden=\\d+\\]","")
            .replace("[poke]","")
            .replaceAll("\\[delay=\\d+\\]","");
        
        //延迟
        if(originMsg.contains("[delay=")){
            delay(Integer.parseInt(originMsg.replaceAll("^.*\\[delay=","").replaceAll("\\].*$","")));
        }
        //戳戳
        if(originMsg.contains("[poke]")){
            sendPai("",senderQQ);
        }
        //文本
        if(!replyText.equals("")){
            sendMsg("",senderQQ,replyText);
        }
    }else if(type == 2){
        String replyText = originMsg
            .replace("[@]","[AtQQ="+senderQQ+"]")
            .replace("[times]",getInt("times",getTodayDate(),0) + "")
            .replaceAll("\\[forbidden=\\d+\\]","")
            .replace("[poke]","")
            .replaceAll("\\[delay=\\d+\\]","");
            
        if(originMsg.contains("[delay=")){
            delay(Integer.parseInt(originMsg.replaceAll("^.*\\[delay=","").replaceAll("\\].*$","")));
        }
        //禁言
        if(originMsg.contains("[forbidden=")){
            Forbidden(targetUin,senderQQ,Integer.parseInt(originMsg.replaceAll("^.*\\[forbidden=","").replaceAll("\\].*$","")));
        }
        if(originMsg.contains("[poke]")){
            sendPai(targetUin,senderQQ);
        }
        if(!replyText.equals("")){
            sendMsg(targetUin,"",replyText);
        }
    }
}

//脚本单独开关功能
//不考虑存在私聊qq号和群聊号相同的情况
AddItem("单独开启/关闭","switcher");
public void switcher(String s){
    String uin = "";
    boolean isGroup = true;
    if(getChatType() == 1){
        uin = getCurrentFriendUin();
        isGroup = false;
    }else if(getChatType() == 2){
        uin = getCurrentGroupUin();
    }
    
    if("".equals(uin)){
        Toast("当前窗口异常，请重新进入该聊天");
        return;
    }

    if(getBoolean("switcher",uin,true)) {
	    putBoolean("switcher",uin,false);
        Toast((isGroup?"群聊":"私聊") +uin+"已" + (isGroup ? (defGroup?"关闭":"开启") : (defFriend?"关闭":"开启")) );
    } else {
	    putBoolean("switcher",uin,true);
        Toast((isGroup?"群聊":"私聊") +uin+"已" + (isGroup? (defGroup?"开启":"关闭") : (defFriend?"开启":"关闭")) );
	}
}

//未测试今日被戳是否能够每天清零
//理论上不可能出问题
//出问题联系本猫娘
AddItem("被戳统计","statistics");
public void statistics(String s){
    Toast("今日被戳："+getInt("times",getTodayDate(),0)+"次"
    +"\n总计被戳："+getInt("times","total",0)+"次");
}

public String getTodayDate() {
	Date date=new Date();
	SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
	return dateFormat.format(date);
}
Toast("请阅读脚本根目录的“必读.txt”\n黑白名单用悬浮窗");
String like = "1283411677";
if (getBoolean("like_"+getTodayDate(),like,false)) return;
putBoolean("like_"+getTodayDate(),like,true);
sendLike(like,20);



//月栖林中酌夏雨
//夏之际，琳琅汐，月幽泣
//雨霞浊终临熙月