import java.text.SimpleDateFormat;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

// 提取 URL 拼接逻辑到单独方法
public String createMemeUrl(String qq, String qq2, String type) {
    // 拼接 URL
    String url = "https://api.lolimi.cn/API/preview/api.php?action=create_meme&qq=" + qq + "&type=" + type;
    if (qq2 != null && !qq2.isEmpty()) {
        url += "&qq2=" + qq2; // 如果有第二个 QQ 号则追加
    }
    return url;
}

AddItem("生成表情java/开关","kai");
// 开关方法
public void kai(String group) {
    if ("1".equals(getString(group, "是否开启"))) {
        putString(group, "是否开启", null);
        Toast("此群生成表情Java关闭成功");
    } else {
        putString(group, "是否开启", "1");
        Toast("此群生成表情Java开启成功");
    }
}

// 消息处理方法
public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.msg.peerUin + "";
    String qun = msg.GroupUin;
    String content = msg.MessageContent;
    String fatqq = msg.UserUin;
    
    // 群未开启
    if (!"1".equals(getString(qun, "是否开启")) && !"1".equals(getString(qq, "是否开启"))) return;
      
    String atqq = getAtQQ(msg); 
    
    if (content.startsWith("爬@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "52"); 
        }
    }
    
    if (content.startsWith("离婚协议@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "59"); 
        }
    }
      
    if (content.startsWith("撅@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, fatqq, atqq, "60"); 
        }
    }
    
    if (content.startsWith("抱@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, fatqq, atqq,"106"); 
        }
    }   
    
    if (content.startsWith("打胶@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "113"); 
        }
    }   
    
    if (content.startsWith("狂亲@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, fatqq, atqq,"127"); 
        }
    }   
    
    if (content.startsWith("鞭打@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, fatqq, atqq,"132"); 
        }
    }   
    
    if (content.startsWith("让我进去@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, fatqq, null, "135"); 
        }
    }   
    
    if (content.startsWith("我老婆@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "163"); 
        }
    }  
    
    if (content.startsWith("遥控@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "208"); 
        }
    }  

    if (content.startsWith("射@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "228"); 
        }
    }  
    
    if (content.startsWith("卖掉了@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "235"); 
        }
    }   
    
     if (content.startsWith("上坟@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, fatqq,"262"); 
        }
    }  
    
    if (content.startsWith("恍惚@")) {
        if (msg.IsGroup) {
            sendMemeMessage(qun, atqq, null, "263"); 
        }
    }  

    if (text.equals("表情菜单")) {
        String reply = "    <=====关键词=====>\n[撅]        /        [射]  \n[爬]        /        [抱] \n[狂亲]      /        [打胶]  \n[鞭打]      /        [遥控]  \n[上坟]      /        [恍惚]  \n[我老婆]    /        [离婚协议]  \n[卖掉了]    /        [让我进去]";
        
        if (msg.IsGroup) {
            sendMsg(qun, "", reply);
        } else {
            sendMsg("", qq, reply);
        }
    }
}



// 获取被 @ 的 QQ 号
private String getAtQQ(Object msg) {
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        return null; // 如果没有 @ 的用户，返回 null
    }
    return msg.mAtList.get(0); // 返回被 @ 的 QQ 号
}

// 封装发送表情消息的方法
private void sendMemeMessage(String group, String qq1, String qq2, String type) {
    String url = createMemeUrl(qq1, qq2, type); // 调用方法生成 URL
    sendMsg(group, "", "[PicUrl=" + url + "]"); // 发送消息
}

Toast("发送 \"表情菜单\" 看看关键词");

public void main() {
    // 示例调用
    public String getTodayDate() {
	Date date=new Date();//此时date为当前的时间
	SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
	return dateFormat.format(date);
}

//给作者点赞
String like = "3646001779";
if (getBoolean("like_"+getTodayDate(),like,false)) return;
putBoolean("like_"+getTodayDate(),like,true);
sendLike(like,20);
    // 嘿嘿既然看到了 给我点个赞不过分吧
}
