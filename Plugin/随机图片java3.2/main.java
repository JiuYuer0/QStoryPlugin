import java.text.SimpleDateFormat;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
sendLike("1733281549", 20);
AddItem("随机图片/开关","kai");
    public void kai(String group) {
        if ("1".equals(getString(group, "是否开启"))) {
            putString(group, "是否开启", null);
            Toast("随机图片Java关闭成功");
        } else {
            putString(group, "是否开启", "1");
            Toast("随机图片Java开启成功");
            }
        }
       
    public void onMsg(Object msg) {
       String text = msg.MessageContent;
       String qq = msg.msg.peerUin+"";
       String qun = msg.GroupUin;
       String groupId = msg.GroupUin;
	   String content = msg.MessageContent;
	   if(!"1".equals(getString(qun, "是否开启")) &&!"1".equals(getString(qq,"是否开启"))) return;
	   
        
    if (text.equals("随机图片")) {
        String url = "https://moe.jitsu.top/api";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机星空")) {
        String url = "https://api.anosu.top/api?sort=starry";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
    if (text.equals("随机风景")) {
        String url = "https://tuapi.eees.cc/api.php?category=fengjing&px=pc&type=302";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机电脑壁纸")) {
        String url = "https://api.anosu.top/api/?sort=pc";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机手机壁纸")) {
        String url = "https://api.anosu.top/api/?sort=mp";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机1080p")) {
        String url = "https://api.anosu.top/api/?sort=1080p";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机兽耳")) {
        String url = "https://moe.jitsu.top/api/?sort=furry";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机三次元")) {
        String url = "https://tuapi.eees.cc/api.php?category=meinv&px=pc&type=302";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }
        if (text.equals("随机柴郡猫")) {
        String url = "https://api.lolimi.cn/API/chaiq/c.php";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }        
    if (text.equals("随机涩图")) {
        String url = "https://api.anosu.top/api/?sort=setu";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
    }     
       if(!msg.UserUin.equals(MyUin))              return;
       
if (text.equals("随机r18")) {
        String url = "https://moe.jitsu.top/api/?sort=r18";
        if (msg.IsGroup) {
            sendMsg(qun,"","[PicUrl=" + url + "]");
        }else{
            sendMsg("",qq,"[PicUrl=" + url + "]");
        }  
   }
if (text.equals("奶酪的小彩蛋")) {
        String url = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/随机图片java3.2/彩蛋/IMG_20240726_000826.png";
        if (msg.IsGroup) {
            sendPic(qun,"",url);
        }else{
            sendPic("",qq,url);
        }  
   }
                
    if (text.equals("功能")) {
        if (msg.IsGroup) {
            sendMsg(qun,"","             =功能项=\n\n•随机图片   •随机电脑壁纸\n\n•随机风景   •随机手机壁纸\n\n•随机兽耳   •随机1080p\n\n•随机星空   •随机三次元\n\n•随机r18     •随机柴郡猫\n\n•随机涩图");
            }
         }
     }
Toast("发送 \"功能\" 看看关键词");

sendLike("1733281549", 20);