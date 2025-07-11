
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

//ai陪聊功能
public void chatGPTLI (msg) {


Toast("111");
            
            String input = msg.MessageContent;
        // 使用split方法以空格分割字符串
        String[] parts = input.split(" ");
        // 直接获取分割后的最后一部分
        if (parts.length > 1) {
            String result = parts[parts.length - 1];
            try {
                        String qun = msg.GroupUin;
            // 创建URL对象
            
            URL url = new URL("https://xingyu.loveiu.cn/api/zpai.php?msg=" + result + "&sys=宝宝&type=text" );
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();     
          //   JSONObject jsonObject = new JSONObject(response.toString());
            // String cx = jsonObject.getString("content");
                    
                    Toast("222");
            sendReply(qun,msg, response.toString() );
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
        } else {
          //格式不准确
          
          
       }  
           
          

}





 // 随机早上好 。。下面是半成品…………… 
public static String halloChas() {
          
         // 声明并初始化一个字符串数组
        String[] fruits = {
"早安呀～",
"早呀，愿你在今天的时光里邂逅无数小确幸~",
"早上好，愿今日的你被满满的好运环绕~",
"早安，愿新的一天为你带来新的希望与机遇~",
"早，愿你今天的笑容比清晨的花朵还灿烂~",
"早上好，愿你度过元气满满的一天~",
"早安，愿你今日无烦恼，事事皆如意~",
"早呀，希望这一天你能收获满满的幸福~",
"早上好，愿你的每一天都充满惊喜~"
        };

        // 创建 Random 实例
        Random random = new Random();

        // 生成一个随机索引，并根据该索引获取随机文本
        return fruits[random.nextInt(fruits.length)];

}


// 随机
public static String shuoChat() {
          
         // 声明并初始化一个字符串数组
        String[] fruits = {"嘿嘿，北北一直都在呐～", "北北一直都在！怎么啦？", "嗯？北北在呐！", "哎哎？北北是不是犯什么错啦？"};

        // 创建 Random 实例
        Random random = new Random();

        // 生成一个随机索引，并根据该索引获取随机文本
        return fruits[random.nextInt(fruits.length)];

}
