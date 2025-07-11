
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.*;
 
 
 //脚本路径
String AppPath=AppPath+"";
 //加载导入类
load(AppPath+"/xiaobei/xiazai.java");
  
//定义  
String msgChatec;
String qq;
String qun;
String Mske;


boolean jieXi = false;
boolean Ai = false;
boolean mos = false;
boolean hd = false;

String syqq;
String daan;
       
//群号 msg QQ号 isSend msg
public void qunGuangLi(String groupUin,String userUin,boolean isSend,msg) {

   boolean chatQunguan = false;
   msgChatec = msg.MessageContent;
   qq = userUin;
   qun = groupUin;
   boolean isSend = msg.IsSend;
   boolean mos = false;
   
   boolean chatQunguan = getBoolean("guan",qq,false);
   
   if (("代管触发".equals(getString(qun, "cf")))&&(chatQunguan||isSend)) {
    mos = true;
} else if  ("全部触发".equals(getString(qun, "cf"))) {
    mos = true;
} else {
   mos = false;
   putString(qun,"cf","代管触发");
}



   if(msgChatec.matches("头衔 .*")) {
   String touConten = msg.MessageContent.substring(3); 
   setTitle(qun,qq,touConten); 
   //静默      
      
   } else if(msgChatec.matches("禁@.*")&&(chatQunguan||isSend)) {   
      for (String aaa : msg.mAtList) {
      if (getBoolean("guan",aaa,false)) {
        sendReply(qun,msg,"你这么狠心吗？？？");
      } else {
        Forbidden(qun, aaa,2592000);
        }
      }
   //静默      
      
   } else if(msgChatec.matches("解@.*")&&(chatQunguan||isSend)) {   
      for (String aaa : msg.mAtList) {
        Forbidden(qun, aaa,0);
   }
   } else if(msgChatec.matches("滚@.*")&&(chatQunguan||isSend)) {   
      for (String aaa : msg.mAtList) {
      if (getBoolean("guan",aaa,false)) {
        sendReply(qun,msg,"补药😭");
      } else {
        Kick(qun, aaa, true);
        //sendReply(qun,msg,"芜湖～起飞");
       }
  }
  } else if(msgChatec.matches("加权限@.*")&&isSend) {  
        for (String aaa : msg.mAtList) {
          putBoolean("guan",aaa,true);
        }    
         sendReply(qun,msg,"我记着惹～");
         
   } else if(msgChatec.matches("去权限@.*")&&isSend) {  
        for (String aaa : msg.mAtList) {
          putBoolean("guan",aaa,false);
        }    
        sendReply(qun,msg,"我记住啦～");
        
  } else if(msgChatec.matches("给赞@.*")&&(chatQunguan||isSend)) {
  
  for (String aaa : msg.mAtList) {
          
  sendLike(aaa,20);
  sendLike(aaa,20);
  sendLike(aaa,10);
          
 }    
  
  sendReply(qun,msg,"好～");
  
} else if(msgChatec.matches("菜单.*")&&(chatQunguan||isSend)) {
String touConten = msg.MessageContent.substring(2); 
if(touConten.matches("")) {
sendReply(qun, msg, "禁@QQ\n解@QQ\n滚@QQ\n加权限@QQ\n去权限@QQ\n头衔[空格]内容\n给赞@QQ\n赞[空格]QQ\n进群欢迎开/关\n设置欢迎语[内容]\n切换代管触发\n切换全部触发\n当前\n--\n↑↑基础↑↑\n--\n👉🏻 菜单2" );
} else if (touConten.matches("2")) {
sendReply(qun, msg, "深度思考[空格]问题\nGPT[空格]问题\n坤/🐔\n文案\n语录\n60秒世界\n壁纸\n兽语[空格]内容\n点歌[空格]歌曲名字\n鸽鸽\n取链接\n我要答题\n");
}


} else if(msgChatec.matches("赞.*")&&(chatQunguan||isSend)) {
 String touConten = msg.MessageContent.substring(1); 
 sendLike(touConten,20);
  
 sendMsg(qun, null, "已为 " + touConten + " 点赞20次");

} else if(msgChatec.matches("进群欢迎.*")&&(chatQunguan||isSend)) {
  String touConten = msg.MessageContent.substring(4); 
  if (touConten.matches("开")) {
  putBoolean("hy",qun,true);
  sendReply(qun,msg, "好～" );
  } else if(touConten.matches("关")) {
  putBoolean("hy",qun,false);
   sendReply(qun,msg, "好～" );
  } else {
   sendReply(qun,msg, "No~应该是开或关" );
  }

} else if(msgChatec.matches("设置欢迎语.*")&&(chatQunguan||isSend)) {
String touConten = msg.MessageContent.substring(5); 
putString("qun"+qun,"yu",touConten);
sendReply(qun,msg, "好～" );
}  else if(msgChatec.matches("60秒世界")&&mos) {


sendPic(qun, null, "https://api.yuafeng.cn/API/60s/");


} else if(msgChatec.matches("语录")&&mos) {

   try {
            // 创建URL对象
            URL url = new URL("https://api.yuafeng.cn/API/ly/aiqing.php?type=text");
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();
            sendReply(qun,msg,response.toString());
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }

} else if ((msgChatec.matches("文案"))&&mos) {

   try {
            // 创建URL对象
            URL url = new URL("https://api.yuafeng.cn/API/ly/yhyl.php?type=text");
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();
            sendReply(qun,msg,response.toString());
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }

} else if(msgChatec.matches("我要答题")&&(mos)) {
     if (hd) {
  sendMsg(qun,null,"此功能正在被别人使用，请稍候…！");
  
  } else {
   String qun = qun;
   hd = true;
   syqq = msg.UserUin;
    String httpL = HttptUrl("https://api.tangdouz.com/a/dt.php?f=1");
    JSONObject jsonObject = new JSONObject(httpL);
    String wt = jsonObject.getString("question");
    String da = jsonObject.getString("sele");
    daan = jsonObject.getString("answer");
   sendReply(qun,msg, wt + "\n\n" + da + "\n\n👉🏻发 A ～ D"  );
}

} else if(msgChatec.matches("#解析")&&(chatQunguan||isSend)) {

DMessageData = msg.RecordMsg; //回复的消息
if (DMessageData == null) {
sendReply(qun,msg, "No…" );
} else {
sendReply(qun,msg, "此消息：" + DMessageData.MessageContent );
}

} else if((msgChatec.matches("GPT .*"))&&mos) {
 String touConten = msg.MessageContent.substring(4); 
 String dd = HttptUrl("https://xingyu.loveiu.cn/api/zpai.php?msg=" + touConten + "&sys=&type=text");
 sendReply(qun, msg, "🤔" + dd);
   
  } else if((msgChatec.matches("深度思考 .*"))&&mos) {
 String touConten = msg.MessageContent.substring(5); 
 
   try {
            String qun = qun;
            // 创建URL对象
            sendReply(qun,msg, "正在思考…🤔" );
            URL url = new URL("https://oiapi.net/API/BigModel/?message="  + touConten + "" );
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();    
            try {
             JSONObject jsonObject = new JSONObject(response.toString());
             String cx = jsonObject.getString("message");
                        sendMsg(qun,null, cx );
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
  } else if((msgChatec.matches("坤"))&&mos) {
 
   String qun = qun;
            sendReply(qun,msg, "\n" + "[PicUrl=https://api.317ak.com/API/tp/kun.php]"  );

   } else if((msgChatec.matches("🐔"))&&mos) {
 
   String qun = qun;
            sendReply(qun,msg, "\n" + "[PicUrl=https://a.aa.cab/ikun.api]"  );

   } else if((msgChatec.matches("壁纸"))&&mos) {
 
   String qun = qun;
            sendReply(qun,msg, "\n" + "[PicUrl=https://api.suyanw.cn/api/scenery.php]"  );

   } else if((msgChatec.matches("兽语 .*"))&&mos) {
    String touConten = msg.MessageContent.substring(3); 
 
   try {
            String qun = qun;
            // 创建URL对象
            URL url = new URL("https://api.jkyai.top/API/shouyu/api.php?msg=" + touConten + "&type=text" );
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();     
                   
            sendReply(qun,msg, response.toString() );
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
  } else if((msgChatec.matches("取链接"))&&mos) {
  
  if (Ai) {
  sendMsg(qun,null,"此功能正在被别人使用，请稍候…！");
  
  } else {
  Ai = true;
  syqq = msg.UserUin;
  sendMsg(qun,null,"👉🏻请发送需要获取的图片或表情包。");
    
  }
  
   
  } else if((msgChatec.matches("抽象化 .*"))&&mos) {
  String touConten = msg.MessageContent.substring(4); 
   
        
  } else if((msgChatec.matches("车票 .*"))&&mos) {
   
  
  
  

        
  } else if((msgChatec.matches("鸽鸽"))&&mos) {
  
  
  
   // String fileURL = "https://api.317ak.com/API/yy/kkyy.php";
    String Fine=RedirectUrl("https://api.317ak.com/API/yy/kkyy.php");
    sendVoice( qun, "", Fine);
   
   
  
  
  } else if((msgChatec.matches("点歌 .*"))&&mos) {
  String touConten = msg.MessageContent.substring(3); 
  Mske = touConten;
  String Judian = qun;
  Hfu = msg;
  String yygd = HttptUrl("https://api.fohok.xin/API/qsmusic.php?msg=" + touConten + "&type=text");
    
  sendReply(Judian,msg, "找到以下:\n" + yygd + "\n-\n发送：选1 - 10");
  
  } else if((msgChatec.matches("选.*"))&&mos) {
  String touConten = msg.MessageContent.substring(1); 
  String Judian = qun;
  Hfu = msg;
  
  if (Mske == "") {
  } else {
  
    String yygd = HttptUrl("https://api.fohok.xin/API/qsmusic.php?msg=" + Mske + "&type=json&n=" + touConten );    
  Mske = "";
  JSONObject jsonObject = new JSONObject(yygd);

            // 检查是否成功
  String success = jsonObject.getString("music");
  String Fine=RedirectUrl(success);
    sendVoice( Judian, "", Fine);
    
  }
  
  } else if(msgChatec.matches("切换.*触发")&&(chatQunguan||isSend)) {
  String touConten = msg.MessageContent.substring(2); 
  if (touConten.matches("代管触发")||touConten.matches("全部触发")) {
  putString(qun,"cf",touConten);
  sendReply(qun,msg, "好");
  } else {
  sendReply(qun,msg, "不对哦，应该是代管、全部");
  }
  
  } else if(msgChatec.matches("当前")&&(chatQunguan||isSend)) {
   String dq = getString(qun, "cf");
   sendReply(qun,msg, "触发：" + dq);
 
 } else if((msgChatec.matches(".*打开.*"))&&mos) {
  
  String text = msgChatec;  
  // 定义一个正则表达式来匹配URL
         // 定义一个正则表达式来匹配"作品"关键词及其后的URL
        String combinedRegex = "(https?://[\\w.-]+(?:/[\\w.-]*)*(?:\\?[\\S]*)?)";
        Pattern pattern = Pattern.compile(combinedRegex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);


        // 尝试找到第一个匹配项
        if (matcher.find()) {
        
        if (jieXi == false) {
            jieXi = true;
            String firstUrl = matcher.group(1).trim(); // 获取第二个捕获组，即URL部分
            String qun = qun;
            sendMsg(qun,null,"发现分享，开始解析。");
            
            try {
            String qun = qun;
            // 创建URL对象
            URL url = new URL("https://api.kxzjoker.cn/API/jiexi_video.php?url=" + firstUrl );
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();  
             jieXi = false;  
        
     

             // 创建一个新的JSONObject实例
            JSONObject jsonObject = new JSONObject(response.toString());

            // 检查是否成功
            boolean success = jsonObject.getBoolean("success");
            if (success) {
                // 获取"data"对象
                JSONObject data = jsonObject.getJSONObject("data");

                // 提取所需信息
                String videoTitle = data.getString("video_title");
                String downloadUrl = data.getString("download_url");
              
               
               sendMsg(qun,null,"标题：" + videoTitle );

               String fileURL = downloadUrl;
               String saveDir = AppPath + "/xiaobei/File";
               String customFileName = "sp";  // 自定义文件名

               downloadFile(fileURL, saveDir, customFileName, 1, qun);
             
             } else {
              sendMsg(qun,null,"No…");
             }
                                                                                
            //sendReply(qun,msg, cx);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
            
            } else {
            
            }
        } else {
            //防止频繁调用
        }
  
  } else if(msgChatec.matches(".*")&&(mos)) {
     
  if (Ai) {
  if (msg.UserUin.matches(syqq)) {
  if (msg.MessageType==1) {
  String text = msg.MessageContent;
  boolean containsHttp = text.toLowerCase().contains("http");
  if (containsHttp) {
  
  
        String input = msg.MessageContent;
        
        // 定义正则表达式（不区分大小写）
        Pattern pattern = Pattern.compile(
            "\\[PicUrl=(https?://[^]]+)\\]",  // 匹配模式
            Pattern.CASE_INSENSITIVE           // 忽略大小写
        );
        
        Matcher matcher = pattern.matcher(input);
        
        // 查找所有匹配项
        while (matcher.find()) {
            String url = matcher.group(1);  // 提取第一个捕获组（URL部分）            
           sendMsg(qun,null,"直链提取完毕 > \n" + url);
        }
  Ai = false;
  }
  }
  
  }
  } else if (hd) {
    if (msg.UserUin.matches(syqq)) {
  if (msg.MessageType==1) {
  hd = false;
  String str = msg.MessageContent;
if (str.contains("A") || str.contains("B") || str.contains("C") || str.contains("D")) {
    if (str.matches(daan)) {
    sendMsg(qun,null,"恭喜你回答正确！✨" );
    } else {
    sendMsg(qun,null,"不对哦～答题结束👀\n正确答案：" + daan );
    }
} else {
   sendMsg(qun,null,"不对哦～答题结束👀\n正确答案：" + daan );
} 

  }
  }
  }
  
  }

   
   
   /**违规名字
   
         String input = "";

        // 宽松的URL正则表达式
        String urlRegex = "(https?://|www\\.|[a-zA-Z0-9]+\\.)[^\\s<]{2,}";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(input);


   if (matcher.find()) {
   
   }
   **/
         
         
}


//制作不易，给个赞吧…
sendLike("2045301077",20);
sendLike("2045301077",20);
sendLike("2045301077",10);
