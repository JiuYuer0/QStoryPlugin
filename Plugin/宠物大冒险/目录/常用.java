
//============↓关于记录玩家最后指令触发相关部分↓=============
 class TimestampWithText {
    private long timestamp;
    private String text;

    public TimestampWithText(long timestamp, String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }
}

// 关于查看触发时间相关部分
class PlayerRecord {
    private TimestampWithText trigger; 
    private String playerId;
    
    public PlayerRecord(TimestampWithText trigger, String playerId) {
        this.trigger = trigger; 
        this.playerId = playerId;
    }
    
    public TimestampWithText getTrigger() { return trigger; }
    public String getPlayerId() { return playerId; }
}

    // 用于记录玩家最后一次触发操作的时间戳（充当指令间隔）
     static Map userLastTriggerTime = new HashMap();
     
    // 用于记录玩家“日常活动”相关指令触发的时间戳
     static Map userDailyTime = new HashMap();
     
    // 用于记录玩家【发动天赋】指令触发的时间戳
     static Map userTalentTime = new HashMap();
     
    // 用于记录玩家【跨群攻击】指令触发的时间戳
     static Map userAttackTime = new HashMap();
     
    // 用于记录玩家【宠物反击】指令触发的时间戳
     static Map userCounterattackTime = new HashMap();

//=================================================


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

private static final int REQUEST_READ_STORAGE_PERMISSION = 1;
    
    // 读取文件内容的方法
    private String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try{
                String line;
                while ((line = reader.readLine())!= null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "读取文件时发生错误";
            }
        } else {
            return "文件不存在";
        }
        return content.toString();
    }

    // 检查并请求存储权限
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE_PERMISSION);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，可以进行文件读取操作
            } else {
                // 权限被拒绝，可以提示用户或进行其他处理
                Toast("存储权限被拒绝，无法读取文件");
            }
        }
    }
    
    
/**
 * 删除指定路径下的所有文件及子目录（递归删除）
 * @param path 要删除的路径（文件或目录）
 * @param qun 群号（用于发送结果提示）
 * @param uin 操作人QQ号（用于艾特提示）
 * @return 拼接后的消息内容（格式：状态标识|消息内容，状态标识：SUCCESS/FAIL）
 */
private String deletePath(String path, String qun, String uin) {
    // 用数组存储成功次数（数组可在递归中传递修改，基本类型不行）
    int[] successCount = {0};
    return deletePathRecursive(path, qun, uin, successCount);
}

/**
 * 递归删除的实际实现方法
 * @param path 要删除的路径
 * @param qun 群号
 * @param uin 操作人QQ号
 * @param successCount 成功删除计数器
 * @return 拼接后的消息内容
 */
private String deletePathRecursive(String path, String qun, String uin, int[] successCount) {
    File file = new File(path);
    StringBuilder msgBuilder = new StringBuilder();
    String status = "FAIL"; // 默认失败状态

    // 路径不存在
    if (!file.exists()) {
        msgBuilder.append(玩家名(qun, uin) + " 路径不存在：").append(path);
        status = "FAIL";
        return "*"+status + "*\n" + msgBuilder.toString();
    }

    try {
        // 处理目录（递归删除子内容）
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles != null) {
                for (File child : childFiles) {
                    // 递归处理子项
                    String childResult = deletePathRecursive(child.getAbsolutePath(), qun, uin, successCount);
                    String[] childParts = childResult.split("\\|", 2);
                    
                    // 子项删除失败，记录消息并终止
                    if ("FAIL".equals(childParts[0])) {
                        if (msgBuilder.length() > 0) {
                            msgBuilder.append("\n");
                        }
                        msgBuilder.append(childParts[1]);
                        return "*"+status + "*\n" + msgBuilder.toString();
                    }
                    // 子项成功不拼接详细信息，计数器已在递归中更新
                }
            }
        }

        // 删除当前文件/目录
        if (file.delete()) {
            successCount[0]++; // 成功删除，计数器+1
            status = "SUCCESS";
        } else {
            msgBuilder.append(玩家名(qun, uin) + " 无法删除：").append(path).append("（可能无权限）");
        }

    } catch (Exception e) {
        msgBuilder.append(玩家名(qun, uin) + " 删除失败：").append(e.getMessage());
    }

    // 最终拼接成功次数（无论成功失败都显示统计结果）
    String resultMsg = msgBuilder.length() > 0 
        ? msgBuilder.toString() + "\n" + 玩家名(qun, uin) + " 成功删除：" + successCount[0]+"次〔文件或目录〕" 
        : 玩家名(qun, uin) + " 成功删除：" + successCount[0]+"次〔文件或目录〕";

    return "*"+status + "*\n" + resultMsg;
}


    // 指定音频目录路径（可更换为其他路径）
    private String audioDirPath = AppPath + "/目录/音频/";
    //private String audioDirPath = "/storage/emulated/0/FunBox/Voice/";
    //↑示例（这是我的Funbox语音包路径）
    

//脚本菜单回调
public void 趴(String qun,String qq,int type) {
    String NAME = readProp(AppPath+"/info.prop","name"); //名称
    String VERSION = readProp(AppPath+"/info.prop","version"); //版本
    String AUTHOR=readProp(AppPath+"/info.prop","author"); //作者
    String title = NAME+" (v"+VERSION+")"; //标题（脚本名称+版本）
    String dd = readFileContent(AppPath + "/desc.txt"); // 获取简介/描述
    // 获取当前时分
    Calendar cal = Calendar.getInstance();
    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    int currentMinute = cal.get(Calendar.MINUTE);
    String timePeriod, mshu, Nickname;
    // 定义时间段判断逻辑
    if (currentHour >= 5 && currentHour < 12) {
        timePeriod = "早上";
    } else if (currentHour >= 12 && currentHour < 14) {
        timePeriod = "中午";
    } else if (currentHour >= 14 && currentHour < 18) {
        timePeriod = "下午";
    } else {
        timePeriod = "晚上";
    }
    
    //增加一些文本内容
    if(getChatType()==2){
      String groupListStr = getString("开放群列表", "开放列表");
      if(groupListStr==null||groupListStr.equals("")||groupListStr.trim().isEmpty()||!groupListStr.contains(qun)){
       mshu="\n\n在本群第一次玩的话，\n需要点击一次‹开/关〖宠物世界〗›后才能正常游玩哦"; 
       Nickname="@" + getMemberName(qun, MyUin);
      }else{mshu=" ";  Nickname="@"+昵称(MyUin);}
    }else{
      mshu="\n\n在私聊里可没法玩哦，快挑选/创建一个群聊,在群聊天界面点击一次‹开/关〖宠物世界〗›后才能正常游玩哦";
      Nickname="@"+昵称(MyUin);
    }

    String Haha =Nickname+"，"+timePeriod+"好！\n我是练习时长两年半的"+AUTHOR+"，喜欢唱、跳、rap、篮球，music～ \n哦呀～哈哈～Amagi～"+mshu+"\n\n------------------------";
 
      //判断是否缺少音频资源
       if (hasAudioInSingleDir(audioDirPath)) {
         // 当前路径存在音频文件，正常打开对话框
         糊脸弹窗(title,Haha+"\n\n"+dd);
       } else { //指定路径无音频文件
          load(AppPath+"/目录/UD.java");
         // releaseAudioPlayer(); //释放音频资源
         tcToast("检测到资源缺失！\n请点击“下载”！");
         showResourceDialog(); //弹出资源下载对话框
       }

    
}


//控制该群聊是否开启【宠物世界】玩法
AddItem("开/关【宠物世界】","开关",PluginID);

//控制是否执行定时刷新怪物（按怪物.java内monsterMap随机）
AddItem("开/关【怪物入侵】","刷新怪物呀",PluginID);

//控制是否给指令信息 加上随机提示
AddItem("开/关【随机提示】","提示",PluginID);

//弹出脚本简介
AddItem("查看脚本简介","趴",PluginID);

//一些设置
AddItem("一些设置","RelSettings",PluginID);


public void 开关(String qun,String qq,int type)
{
  if(getChatType()!=2){ //非群聊
    String message="请在群聊内点击！\n❗不支持在私聊/好友点击！";
    // sendMsg("",MyUin,message);
    tcToast(message);
    return; //直接返回，不执行下面的开关逻辑
  }
       if(!"开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           putString("开放群列表",qun+"_开关状态","开");
           tcToast("已开启本群【宠物世界】玩法");
           新群聊(qun); //判断群号是否已经记录
       }
       else if("开".equals(getString("开放群列表",qun+"_开关状态")))
       {
           putString("开放群列表",qun+"_开关状态",null);
           tcToast("已关闭本群【宠物世界】玩法");
           移除群聊(qun); //在群聊 关闭【宠物世界】时，将此群从列表中移除
       }
}

public void 刷新怪物呀(String qun,String qq,int type) {
    boolean 当前状态 = getBoolean("系统配置","怪物入侵", false); 
    // 默认关闭（false=关闭，true=开启）

        if (!当前状态) { // 仅当未开启时执行操作
            putBoolean("系统配置","怪物入侵", true); // 存储开启状态
            tcToast("成功“开启”全服怪物入侵，将按预设间隔来刷新怪物并投放到已开放群聊内");
        } else {
            putBoolean("系统配置","怪物入侵", false); // 存储关闭状态
            tcToast("已“关闭”全服怪物入侵");
        }
}

public void 提示(String qun,String qq,int type) {
    boolean 当前状态 = getBoolean("系统配置","随机提示", false); 
    // 默认关闭（false=关闭，true=开启）

        if (!当前状态) { // 仅当未开启时执行操作
            putBoolean("系统配置","随机提示", true); // 存储开启状态
            tcToast("成功“开启”随机提示，\n后续指令消息将附加随机提示~");
        } else {
            putBoolean("系统配置","随机提示", false); // 存储关闭状态
            tcToast("已“关闭”随机提示，\n后续消息不再附加随机提示~");
        }
}

//记录玩家账号与人数，方便后续绘制【宠物战榜】与【宠物神榜】
public void 新玩家(String qun, String uin) {
    // 处理群内玩家记录
    String 群列表 = getString(qun + "/玩家列表", "列表");
    int 群人数 = getInt(qun + "/玩家列表", "人数", 0);
    
    if (群列表 == null || !群列表.contains(uin)) {
        if (群人数 == 0) {
            putString(qun + "/玩家列表", "列表", uin);
            putInt(qun + "/玩家列表", "人数", 1);
        } else {
            putString(qun + "/玩家列表", "列表", 群列表 + "、" + uin);
            putInt(qun + "/玩家列表", "人数", 群人数 + 1);
        }

        // 处理全局玩家记录
        String 总列表 = getString("玩家总列表", "总列表");
        int 总人数 = getInt("玩家总列表", "总人数", 0);
        
        if (总列表 == null || !总列表.contains(uin)) {
            if (总人数 == 0) {
                putString("玩家总列表", "总列表", uin);
                putInt("玩家总列表", "总人数", 1);
            } else {
                putString("玩家总列表", "总列表", 总列表 + "、" + uin);
                putInt("玩家总列表", "总人数", 总人数 + 1);
            }
        }
    }
}

//在每个群每次点击“开/关【宠物世界】”时记录状态，方便后续绘制【宠物神榜】
public void 新群聊(String qun)
{
     String 列表=getString("开放群列表","开放列表");
     int 数量 = getInt("开放群列表","开放数量",0);
   if(列表==null||!列表.contains(qun))
   {
     if(数量==0){
       putString("开放群列表","开放列表",qun);
       putInt("开放群列表","开放数量",(数量+1));
     }else{
       putString("开放群列表","开放列表",列表+"、"+qun);
       putInt("开放群列表","开放数量",(数量+1));
     }
   }
}

//在群聊关闭【宠物世界】玩法时，将此群从列表中移除（避免影响双榜结算）
public void 移除群聊(String qun) {
    String 列表 = getString("开放群列表", "开放列表");
    int 数量 = getInt("开放群列表", "开放数量", 0);
    
    if (列表 != null &&列表.contains(qun)) {
        // 按分隔符拆分列表
        String[] 群数组 = 列表.split("、");
        StringBuilder 新列表 = new StringBuilder();
        
        //更新当前开放群列表（移除目标群）
        for (String 现有群 : 群数组) {
            if (!现有群.equals(qun)) {
                if (新列表.length() > 0) {
                    新列表.append("、");
                }
                新列表.append(现有群);
            }
        }
        
        // 更新群列表和开放数量
        putString("开放群列表", "开放列表", 新列表.toString());
        putInt("开放群列表", "开放数量", (数量 - 1));
    }
}

import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

// 获取群聊所有成员账号并按指定格式拼接
public String getGroupMembersUin(String groupUin) {
    // 获取群成员列表
    ArrayList memberList = getGroupMemberList(groupUin);
    // 用于存储成员账号
    StringBuilder uinBuilder = new StringBuilder();
    
    // 遍历成员列表
    for (int i = 0; i < memberList.size(); i++) {
        // 获取成员账号
        String userUin = memberList.get(i).UserUin;
        // 添加到字符串构建器
        uinBuilder.append(userUin);
        // 不是最后一个成员则添加逗号分隔
        if (i < memberList.size() - 1) {
            uinBuilder.append(",");
        }
    }
    
    // 返回拼接好的成员账号字符串
    return uinBuilder.toString();
}


//处理拉黑该玩家部分（就是在列表里加个账号）
public void 玩家加黑(String qun, String account, String uin)
{
     String 列表=getString("游戏黑名单","账号列表");
     int 数量 = getInt("游戏黑名单","拉黑人数",0);
   if(列表==null||!列表.contains(uin))
   {
     if(数量==0){
       putString("游戏黑名单","账号列表",uin);
       putInt("游戏黑名单","拉黑人数",(数量+1));
     }else{
       putString("游戏黑名单","账号列表",列表+"、"+uin);
       putInt("游戏黑名单","拉黑人数",(数量+1));
     }
     发送(qun,"[AtQQ="+account+"]  "+" 操作成功！\n◆「"+uin+"」成功添加至游戏黑名单！\n◆游戏黑名单当前有"+(数量+1)+"人",true);
   }else{
     发送(qun,"[AtQQ="+account+"]  "+" 重复操作！\n◆「"+uin+"」已在游戏黑名单内！\n◆游戏黑名单当前有"+数量+"人",true);
   }
}

//处理取消拉黑该玩家部分（从列表中移除）
public void 玩家去黑(String qun, String account, String uin) {
    String 列表 = getString("游戏黑名单", "账号列表");
    int 数量 = getInt("游戏黑名单", "拉黑人数", 0);
    
    if (列表 != null &&列表.contains(uin)) {
        // 按分隔符拆分列表
        String[] 账号数组 = 列表.split("、");
        StringBuilder 新列表 = new StringBuilder();
        
        //更新当前游戏黑名单（移除目标账号）
        for (String 现有账号 : 账号数组) {
            if (!现有账号.equals(uin)) {
                if (新列表.length() > 0) {
                    新列表.append("、");
                }
                新列表.append(现有账号);
            }
        }
        
        // 更新列表和拉黑人数，并发送消息
        putString("游戏黑名单", "账号列表", 新列表.toString());
        putInt("游戏黑名单", "拉黑人数", (数量 - 1));
        发送(qun,"[AtQQ="+account+"]  "+" 操作成功！\n◆成功将「"+uin+"」从游戏黑名单中移除！\n◆游戏黑名单还剩"+(数量-1)+"人",true);
    }else{
       发送(qun,"[AtQQ="+account+"]  "+" 操作失败！\n◆游戏黑名单内未找到「"+uin+"」！\n◆游戏黑名单当前有"+数量+"人",true);
    }
}

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Path;
import android.graphics.Shader; // 用于BitmapShader纹理渲染
import android.graphics.BitmapShader; // 位图着色器
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


    // 音频播放列表及当前索引
    private List audioList = new ArrayList(); // 音频文件路径列表
    private int currentAudioIndex = 0; // 当前播放音频索引
    private Random random = new Random(); // 随机数生成器
    
    //音频播放列表是否为空（给对话框添加『下载资源包』按钮）
    private boolean isAudioListEmpty = false;
    //↑false=音频列表存在音频文件，true=音频列表为空，会在对话框添加控件
    
// 初始化音频播放列表
private void initAudioList() {
    // 提前清空列表，避免后续统计音频数量出错
    audioList.clear();
    

    File audioDir = new File(audioDirPath);
    
    // 检查目录是否存在
    if (audioDir.exists() && audioDir.isDirectory()) {
        // 获取目录下所有文件
        File[] files = audioDir.listFiles();
        
        if (files != null && files.length > 0) {
            // 遍历目录中的文件
            for (File file : files) {
                // 检查是否为文件且符合音频格式
                if (file.isFile() && isAudioFile(file.getName())) {
                    audioList.add(file.getAbsolutePath());
                }
            }
            
            // 提示找到的音频文件数量
          //  Toast("已扫描到 " + audioList.size() + " 个音频文件");
        } else {
            // 目录存在但无文件时的提示
            Toast("未找到音频文件");
            isAudioListEmpty = true;
        }
    } else {
        // 目录不存在时的提示
        Toast("音频目录不存在：" + audioDirPath);
        isAudioListEmpty = true;
    }
    
    // 随机选择一首音频作为默认播放
    if (!audioList.isEmpty()) {
        currentAudioIndex = random.nextInt(audioList.size());
    } else {
        // 无可用音频时的提示
        Toast("没有找到可播放的音频文件");
        isAudioListEmpty = true;
    }
}

// 音频格式过滤方法（支持常见音频格式）
private boolean isAudioFile(String fileName) {
    // 转换为小写便于比较
    String lowerFileName = fileName.toLowerCase();
    
    // 定义支持的音频格式（不支持.silk之类的）
    return lowerFileName.endsWith(".mp3") || 
           lowerFileName.endsWith(".wav") || 
           lowerFileName.endsWith(".flac") || 
           lowerFileName.endsWith(".aac") ||
           lowerFileName.endsWith(".m4a");
}


   // 音频播放器相关变量
    private MediaPlayer mediaPlayer;
    private ImageButton playButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private ImageButton checkButton; // 勾选图标按钮
    private TextView audioStatusText;
     
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.content.res.Resources;

  private Bitmap createListIconBitmap(int width, int height) {
        // 创建一个空白的 Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 设置画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        // 绘制列表的竖线
        int lineWidth = width / 10;
        int lineSpacing = width / 10;
        int lineHeight = height / 3;
        int startX = width / 2 - lineWidth / 2;
        int startY = height / 2 - lineHeight / 2;

        for (int i = 0; i < 3; i++) {
            canvas.drawRect(startX, startY + i * (lineHeight + lineSpacing), startX + lineWidth, startY + (i + 1) * lineHeight + i * lineSpacing, paint);
        }

        return bitmap;
    }
    
            
   // 添加音频控制控件
    private void addAudioControl(LinearLayout parentLayout, Activity activity) {
        // 创建音频控制布局
        LinearLayout audioLayout = new LinearLayout(activity);
        audioLayout.setOrientation(LinearLayout.VERTICAL);
        audioLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        audioLayout.setBackgroundColor(Color.parseColor("#cd2e2e2e"));
        audioLayout.setGravity(Gravity.CENTER);

        LinearLayout xLayout = new LinearLayout(activity);
        xLayout.setOrientation(LinearLayout.HORIZONTAL);
        xLayout.setGravity(Gravity.CENTER);

       // 创建透明背景的Bitmap
       Bitmap newBitmap = Bitmap.createBitmap(65, 65, Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(newBitmap);
       Paint paint = new Paint();
       
       // 绘制列表图标线条
       paint.setColor(Color.WHITE);
       paint.setStrokeWidth(3); // 线条宽度
       paint.setAntiAlias(true); // 抗锯齿优化线条显示
       
       // 第一条横线
       float lineY1 = 18;
       canvas.drawLine(10, lineY1, 55, lineY1, paint);
       
       // 第二条横线
       float lineY2 = 32;
       canvas.drawLine(10, lineY2, 55, lineY2, paint);
       
       // 第三条横线
       float lineY3 = 46;
       canvas.drawLine(10, lineY3, 55, lineY3, paint);
       
        // 播放列表按钮
        ImageView listButton = new ImageView(activity);
        listButton.setImageBitmap(newBitmap);
        listButton.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
           showAudioListDialog(activity); // 点击展开播放列表
         }
        });
        xLayout.addView(listButton);

        // 上一首按钮
        prevButton = new ImageButton(activity);
        prevButton.setImageResource(android.R.drawable.ic_media_previous);
        prevButton.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
        prevButton.setBackgroundColor(Color.TRANSPARENT);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playPreviousAudio(activity);
            }
        });
        xLayout.addView(prevButton);

        // 播放控制按钮
        playButton = new ImageButton(activity);
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
        playButton.setBackgroundColor(Color.TRANSPARENT);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                togglePlayback(activity);
            }
        });
        xLayout.addView(playButton);

        // 下一首按钮
        nextButton = new ImageButton(activity);
        nextButton.setImageResource(android.R.drawable.ic_media_next);
        nextButton.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
        nextButton.setBackgroundColor(Color.TRANSPARENT);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playNextAudio(activity);
            }
        });
        xLayout.addView(nextButton);
       
       // 可勾选图标按钮
       checkButton = new ImageButton(activity);
       checkButton.setImageResource(android.R.drawable.checkbox_off_background);
       checkButton.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
       checkButton.setBackgroundColor(Color.TRANSPARENT);
       checkButton.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            //勾选状态
           boolean isChecked = getBoolean("系统配置", "音频自动下一首", false);
          
           // 根据状态更换图标
           if (isChecked==false) {
              checkButton.setImageResource(android.R.drawable.checkbox_on_background);
              checkButton.setBackgroundColor(Color.parseColor("#66ccff"));
              putBoolean("系统配置", "音频自动下一首", true);
              tcToast("已开启自动播放下一首");
           } else {
              checkButton.setImageResource(android.R.drawable.checkbox_off_background);
              checkButton.setBackgroundColor(Color.TRANSPARENT);
              putBoolean("系统配置", "音频自动下一首", false);
              tcToast("已关闭自动播放下一首");
           }
        }
      });
      xLayout.addView(checkButton); // 添加到水平布局中        
      
      //勾选状态
       boolean isChecked = getBoolean("系统配置", "音频自动下一首", false);
         // 根据状态更换图标
        if (isChecked==true) {
            checkButton.setImageResource(android.R.drawable.checkbox_on_background);
            checkButton.setBackgroundColor(Color.parseColor("#66ccff"));
        } else {
            checkButton.setImageResource(android.R.drawable.checkbox_off_background);
            checkButton.setBackgroundColor(Color.TRANSPARENT);
        }
      audioLayout.addView(xLayout);

        
        // 音频状态文本
        audioStatusText = new TextView(activity);
        audioStatusText.setText("点击▶播放");
        audioStatusText.setTextSize(12.5f);
        audioStatusText.setTextColor(Color.parseColor("#eeeeee"));
        audioStatusText.setGravity(Gravity.CENTER);
        audioStatusText.setPadding(20, 0, 20, 12);
        audioStatusText.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
           //获取当前位置的音频名称
           String 播放详情=getAudioFileName(audioList.get(currentAudioIndex));
           //获取此控件文本
           String textContent = audioStatusText.getText().toString();
           //没有正在播放的音频或音频名称获取失败
           if(播放详情==null||textContent.length()<=4){return;}
           else{showCustomDialog(activity);}
         }
        });
        audioLayout.addView(audioStatusText);
        
        if (isAudioListEmpty) { //音频列表为空时，更改控件文本
          audioStatusText.setText("音频播放列表为空！请您点击下方按钮来下载资源包");
        }
        
        parentLayout.addView(audioLayout);

        // 初始化播放列表
        initAudioList();
    }

    // 播放上一首音频
    private void playPreviousAudio(Activity activity) {
        if (audioList.isEmpty()) return;

        // 计算上一首索引（循环列表）
        currentAudioIndex = (currentAudioIndex - 1 + audioList.size()) % audioList.size();
        playAudioAt(activity, currentAudioIndex);
    }

    // 播放下一首音频
    private void playNextAudio(Activity activity) {
        if(audioList==null||audioList.isEmpty()) return;

        // 计算下一首索引（循环列表）
        currentAudioIndex = (currentAudioIndex + 1) % audioList.size();
        playAudioAt(activity, currentAudioIndex);
    }
    
    // 播放指定索引的音频
    private void playAudioAt(Activity activity, int index) {
        if (index < 0 || index >= audioList.size()) {
          tcToast("播放列表为空！\n1.你可点击『下载资源压缩包』来获取音频\n2.或者在常用.java 第384行更改音频路径");
          return;
        }

        // 释放当前资源
        releaseAudioPlayer();

        // 初始化新音频
        mediaPlayer = new MediaPlayer();
        try {
            String audioPath = audioList.get(index);
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            playButton.setImageResource(android.R.drawable.ic_media_pause);
            audioStatusText.setText("正在播放: " + getAudioFileName(audioPath));

            // 设置播放完成监听（可根据需求选择是否自动切下一首）
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                    
                    //勾选状态
                    boolean isChecked = getBoolean("系统配置", "音频自动下一首", false);
                    if(isChecked==true){//自动播放下一首
                      playNextAudio(activity);
                    }else{ //释放音频资源
                      audioStatusText.setText("播放完成: " + getAudioFileName(audioList.get(currentAudioIndex)));
                      releaseAudioPlayer();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            tcToast("音频加载失败：" + e.getMessage());
            releaseAudioPlayer();
        }
    }

    // 从路径中提取音频文件名
    private String getAudioFileName(String path) {
        if (path == null) return "未知音频";
        int lastSlash = path.lastIndexOf("/");
        return lastSlash > -1 ? path.substring(lastSlash + 1) : path;
    }

    // 切换音频播放状态
    private void togglePlayback(Activity activity) {
        if (mediaPlayer == null) {
            // 初始化 MediaPlayer 并播放当前索引音频
            playAudioAt(activity, currentAudioIndex);
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setImageResource(android.R.drawable.ic_media_play);
                audioStatusText.setText("已暂停: " + getAudioFileName(audioList.get(currentAudioIndex)));
            } else {
                mediaPlayer.start();
                playButton.setImageResource(android.R.drawable.ic_media_pause);
                audioStatusText.setText("正在播放: " + getAudioFileName(audioList.get(currentAudioIndex)));
            }
        }
    }
    
//————这段方法用于判断指定目录是否存在音频————————↓
// 音频格式（支持常见格式，注：silk格式文件不支持）
private static final String[] AUDIO_EXTENSIONS = {".mp3", ".wav", ".flac", ".aac", ".m4a"};

// 判断文件名是否为音频格式
private static boolean isAudioFile2(String fileName) {
    String lowerFileName = fileName.toLowerCase();
    for (String ext : AUDIO_EXTENSIONS) {
        if (lowerFileName.endsWith(ext)) {
            return true;
        }
    }
    return false;
}

// 检测单个目录是否有音频文件
private static boolean hasAudioInSingleDir(String dirPath) {
    File dir = new File(dirPath);
    if (!dir.exists() || !dir.isDirectory()) {
        return false; // 目录不存在或不是文件夹
    }

    File[] files = dir.listFiles();
    if (files == null) {
        return false; // 目录不可访问
    }

    for (File file : files) {
        if (file.isFile() && isAudioFile2(file.getName())) {
            return true;
        }
    }
    return false;
}
//————这段方法用于判断指定目录是否存在音频————————↑
        
    
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.Collections;
import java.util.Comparator;

private boolean isRandomOrder = false; // 当前是否为随机排序
private boolean isAlphabeticalOrder = false; // 当前是否为A-Z排序
private boolean isLengthDescOrder = false; // 当前是否按名称长度排序（长到短）
private boolean isModifyTimeDescOrder = false; // 是否按修改时间降序（新到旧）
private boolean isFileSizeDescOrder = false; // 是否按文件大小降序（大到小）

private void showAudioListDialog(Activity activity) {
// 对列表进行排序
    if (isRandomOrder) {
       //随机排序（随机打乱播放列表）
        Collections.shuffle(audioList);
    } else if (isAlphabeticalOrder) {
        // A-Z排序
        Collections.sort(audioList, new Comparator() {
            public int compare(String path1, String path2) {
                String name1 = getAudioFileName(path1);
                String name2 = getAudioFileName(path2);
                return name1.compareToIgnoreCase(name2);
            }
        });
    } else if (isLengthDescOrder) {
        // 长度降序
        Collections.sort(audioList, new Comparator() {
            public int compare(String path1, String path2) {
                String name1 = getAudioFileName(path1);
                String name2 = getAudioFileName(path2);
                return name2.length() - name1.length();
            }
        });
    } else if (isFileSizeDescOrder) {
        // 按文件大小降序排序
        Collections.sort(audioList, new Comparator() {
            public int compare(String path1, String path2) {
                try {
                    long size1 = new File(path1).length();
                    long size2 = new File(path2).length();
                    return (int)(size2 - size1); // 降序：大文件排前
                } catch (Exception e) {
                    e.printStackTrace();
                    // 路径无效时按原顺序排列
                    return 0;
                }
            }
        });
    }
    
    // 提取音频文件名列表
    List audioNames = new ArrayList();
    for (String path : audioList) {
        audioNames.add(getAudioFileName(path));
    }
    
    // 创建自定义视图
    LinearLayout rootLayout = new LinearLayout(activity);
    rootLayout.setOrientation(LinearLayout.VERTICAL);
    rootLayout.setPadding(16, 16, 16, 16);
    rootLayout.setBackgroundColor(Color.parseColor("#80333333"));
    
    // 添加标题
    TextView titleView = new TextView(activity);
    titleView.setText("播放列表（共" + audioNames.size() + "首）");
    titleView.setTextSize(22);
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, 16);
    titleView.setTextColor(Color.parseColor("#ffffff"));
    titleView.setShadowLayer(2, 1.2f, 1.2f, Color.parseColor("#fd2233"));
    titleView.setBackgroundColor(Color.parseColor("#000000"));
    rootLayout.addView(titleView);
    
    
    // 添加列表视图
    ListView listView = new ListView(activity);
    ArrayAdapter adapter = new ArrayAdapter(
            activity,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            audioNames);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            playAudioAt(activity, position);
            currentAudioIndex = position; //更新当前播放音频索引
          //  Toast("播放："+getAudioFileName(audioList.get(position)));
            dialog.dismiss();
        }
    });
    rootLayout.addView(listView, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0,
            1.0f));
            
            
    // 添加按钮布局
    LinearLayout buttonLayout = new LinearLayout(activity);
    buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
    buttonLayout.setPadding(0, 16, 0, 0);
    buttonLayout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    buttonLayout.setBackgroundColor(Color.parseColor("#000000"));
    
    //文本
    TextView textwe = new TextView(activity);
    textwe.setText("排序：");
    textwe.setTextSize(15);
    textwe.setPadding(15, 0, 15, 0);
    textwe.setTextColor(Color.parseColor("#ffffff"));
    buttonLayout.addView(textwe);
    
    // 随机排序按钮
    Button randomButton = new Button(activity);
    randomButton.setText("随机");
    randomButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            isRandomOrder = true;
            isAlphabeticalOrder = false;
            isLengthDescOrder = false;
            isFileSizeDescOrder = false;
            randomButton.setBackgroundColor(Color.parseColor("#1E88E5"));
            randomButton.setTextColor(Color.WHITE);
            showAudioListDialog(activity);
            dialog.dismiss();
        }
    });
    buttonLayout.addView(randomButton);
    
    // A-Z排序按钮
    Button azButton = new Button(activity);
    azButton.setText("A-Z");
    azButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            isAlphabeticalOrder = true;
            isRandomOrder = false;
            isLengthDescOrder = false;
            isFileSizeDescOrder = false;
            azButton.setBackgroundColor(Color.parseColor("#1E88E5"));
            azButton.setTextColor(Color.WHITE);
            showAudioListDialog(activity);
            dialog.dismiss();
        }
    });
    buttonLayout.addView(azButton);
    
    //按名称从长到短排序
    Button lengthButton = new Button(activity);
    lengthButton.setText("长度");
    lengthButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            isLengthDescOrder = true;
            isRandomOrder = false;
            isAlphabeticalOrder = false;
            isFileSizeDescOrder = false;
            lengthButton.setBackgroundColor(Color.parseColor("#1E88E5"));
            lengthButton.setTextColor(Color.WHITE);
            showAudioListDialog(activity);
            dialog.dismiss();
        }
    });
    buttonLayout.addView(lengthButton);
    
    // 大小降序按钮
    Button sizeButton = new Button(activity);
    sizeButton.setText("大小");
    sizeButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            isRandomOrder = false;
            isAlphabeticalOrder = false;
            isLengthDescOrder = false;
            isFileSizeDescOrder = true;
            sizeButton.setBackgroundColor(Color.parseColor("#1E88E5"));
            sizeButton.setTextColor(Color.WHITE);
            showAudioListDialog(activity);
            dialog.dismiss();
        }
    });
    buttonLayout.addView(sizeButton);
    
    rootLayout.addView(buttonLayout);
    
    // 给按钮更换背景色
    if (isRandomOrder) {
        randomButton.setShadowLayer(3, 2f, 2f, Color.parseColor("#1E88E5"));
    } else if (isAlphabeticalOrder) {
        azButton.setShadowLayer(3, 2f, 2f, Color.parseColor("#1E88E5"));
    } else if (isLengthDescOrder) {
        lengthButton.setShadowLayer(3, 2f, 2f, Color.parseColor("#1E88E5"));
    } else if (isFileSizeDescOrder) {
        sizeButton.setShadowLayer(3, 2f, 2f, Color.parseColor("#1E88E5"));
    }
    
    // 添加关闭按钮
    Button negativeButton = new Button(activity);
    negativeButton.setText("关闭");
    negativeButton.setBackgroundColor(Color.parseColor("#002266"));
    negativeButton.setTextColor(Color.WHITE);
    negativeButton.setTextSize(20f);
    negativeButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        /*    //取消所有排序
            isRandomOrder = false;
            isAlphabeticalOrder = false;
            isLengthDescOrder = false;
            isFileSizeDescOrder = false;
        */
            dialog.dismiss(); //关闭对话框
        }
    });
    rootLayout.addView(negativeButton);
    
    
    // 创建对话框
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setView(rootLayout);
    
    AlertDialog dialog = builder.create();
    dialog.show();
}


//播放详情（点击播放键下面文本时调用）
private void showCustomDialog(Activity activity) {
    String audioDirName = getAudioFileName(audioList.get(currentAudioIndex));
    String audioFilePath = audioDirPath + audioDirName;
    String ms1, ms2;
    File audioFile = new File(audioFilePath);
    // 判断文件是否存在且为文件（非目录）
    boolean exists = audioFile.exists() && audioFile.isFile();
    if (exists) {
      ms2 = audioDirName;
    } else {
      ms2 = "音频文件不存在！";
    }
    
    // 计算文件大小（KB）
    double fileSize = new File(audioDirPath + audioDirName).length() * 1.0 / 1024;
    String audioDirSize;
    
    // 判断是否超过1000KB，超过则转换为MB
    if (fileSize > 1000) {
       // 向上取整并保留2位小数
       fileSize = Math.ceil((fileSize / 1024) * 100) / 100;
       audioDirSize = fileSize +" MB";
    } else {
       // 未超过1000KB时保留2位小数
       fileSize = Math.ceil(fileSize * 100) / 100;
       audioDirSize = fileSize +" KB";
    }
    
    boolean isChecked = getBoolean("系统配置", "音频自动下一首", false);
    if(isChecked==true){//已勾选自动下一首
      ms1="已勾选自动播放下一首，下一首：\n《"+getAudioFileName(audioList.get(currentAudioIndex+1))+"》";
    }else{ms1="正常";}
    
    LinearLayout mainLayout = new LinearLayout(activity);
    mainLayout.setOrientation(LinearLayout.VERTICAL);
    mainLayout.setPadding(10, 0, 30, 0);

    // 音频行布局
    LinearLayout audioLayout = new LinearLayout(activity);
    audioLayout.setOrientation(LinearLayout.HORIZONTAL);
    TextView audioLabel = new TextView(activity);
    audioLabel.setText("文件：");
    audioLabel.setTextColor(Color.WHITE);
    audioLabel.setTextSize(16);
    audioLabel.setPadding(20, 0, 0, 0);
    TextView audioValue = new TextView(activity);
    audioValue.setText(ms2);
    audioValue.setTextIsSelectable(true);
    audioValue.setPadding(0, 0, 0, 8);
    audioLayout.addView(audioLabel);
    audioLayout.addView(audioValue);
    mainLayout.addView(audioLayout);
    
    // 路径行布局
    LinearLayout pathLayout = new LinearLayout(activity);
    pathLayout.setOrientation(LinearLayout.HORIZONTAL);
    TextView pathLabel = new TextView(activity);
    pathLabel.setText("路径：");
    pathLabel.setTextColor(Color.WHITE);
    pathLabel.setTextSize(16);
    pathLabel.setPadding(20, 0, 0, 0);
    TextView pathValue = new TextView(activity);
    pathValue.setText(audioFilePath);
    pathValue.setTextIsSelectable(true);
    pathValue.setPadding(0, 0, 0, 8);
    pathValue.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          copyToClip(audioFilePath);
          Toast("成功复制该路径！");
        }
    });
    pathLayout.addView(pathLabel);
    pathLayout.addView(pathValue);
    mainLayout.addView(pathLayout);
    
    // 大小行布局
    LinearLayout sizeLayout = new LinearLayout(activity);
    sizeLayout.setOrientation(LinearLayout.HORIZONTAL);
    TextView sizeLabel = new TextView(activity);
    sizeLabel.setText("大小：");
    sizeLabel.setTextColor(Color.WHITE);
    sizeLabel.setTextSize(16);
    sizeLabel.setPadding(20, 0, 0, 0);
    TextView sizeValue = new TextView(activity);
    sizeValue.setText(audioDirSize);
    sizeValue.setPadding(0, 0, 0, 6);
    sizeLayout.addView(sizeLabel);
    sizeLayout.addView(sizeValue);
    mainLayout.addView(sizeLayout);
    
    // 状态栏布局
    LinearLayout statusSizeLayout = new LinearLayout(activity);
    statusSizeLayout.setOrientation(LinearLayout.HORIZONTAL);
    statusSizeLayout.setPadding(0, 0, 0, 20);
    TextView statusSizeLabel = new TextView(activity);
    statusSizeLabel.setText("状态：");
    statusSizeLabel.setTextColor(Color.WHITE);
    statusSizeLabel.setTextSize(16);
    statusSizeLabel.setPadding(20, 0, 0, 0);
    TextView statusSizeValue = new TextView(activity);
    statusSizeValue.setText(ms1);
    statusSizeValue.setPadding(0, 0, 0, 10);
    statusSizeLayout.addView(statusSizeLabel);
    statusSizeLayout.addView(statusSizeValue);
    mainLayout.addView(statusSizeLayout);
    
    //其他文本
    TextView msgLabel = new TextView(activity);
    msgLabel.setText("如果获取失败或出错，可以在showCustomDialog(Activity activity)方法调整，或者去上面路径查看文件“"+audioDirName+"”是否存在");
    msgLabel.setTextSize(11);
    msgLabel.setBackgroundColor(Color.parseColor("#cd2e2e2e"));
    msgLabel.setTextColor(Color.parseColor("#fbda41"));
    msgLabel.setPadding(20, 5, 20, 5);
    msgLabel.setTextIsSelectable(true);
    
    //等音频不在该路径下时，将控件添加到布局（平时不添加）
    if(ms2.equals("音频文件不存在！")){mainLayout.addView(msgLabel);}
    
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle("音频详情")
           .setView(mainLayout)
           .setNegativeButton("取消", null)
           .show();
}


    // 释放音频资源
    private void releaseAudioPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            if (playButton != null) {
                playButton.setImageResource(android.R.drawable.ic_media_play);
            }
            if (audioStatusText != null) {
                audioStatusText.setText("点击重播："+getAudioFileName(audioList.get(currentAudioIndex)));
            }
        }
    }

    //用于提示一些内容
    public void 糊脸弹窗(String title, String msg) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Activity thisActivity = GetActivity();
                    if (thisActivity != null) {
                        thisActivity.runOnUiThread(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(thisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();

                                // 创建垂直线性布局
                                LinearLayout linearLayout = new LinearLayout(thisActivity);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                linearLayout.setPadding(20, 20, 20, 20);
                                
                                String author=readProp(AppPath+"/info.prop","author");
                                
                                // 图片+脚本信息
                                LinearLayout booth = new LinearLayout(thisActivity);
                                booth.setOrientation(LinearLayout.HORIZONTAL);
                                booth.setPadding(10, 20, 10, 0);
                                
                                LinearLayout booth1 = new LinearLayout(thisActivity);
                                booth1.setOrientation(LinearLayout.VERTICAL);
                                booth1.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      booth1.startAnimation(rotateAnimation);
                                  }
                                });
                                
                                LinearLayout booth2 = new LinearLayout(thisActivity);
                                booth2.setOrientation(LinearLayout.VERTICAL);
                                
                                // 标题（脚本名称+版本）
                                TextView titleView = new TextView(thisActivity);
                                titleView.setText(title);
                                titleView.setTextSize(20);
                                titleView.setTextColor(Color.parseColor("#000000"));
                                titleView.setShadowLayer(1, 1.2f, 1.2f, Color.parseColor("#333333"));
                                titleView.setPadding(21, 0, 10, 0);
                                
                                //作者
                                TextView z1 = new TextView(thisActivity);
                                z1.setText("作者："+author);
                                z1.setTextIsSelectable(true);
                                z1.setPadding(21, 0, 10, 0);
                                z1.setTextSize(15);
                                z1.setTextColor(Color.parseColor("#000000"));
                                
                                booth2.addView(titleView);
                                booth2.addView(z1);

                                Bitmap avatar = BitmapFactory.decodeFile(AppPath + "/目录/图片/其他/默认.png");
                                if (avatar == null) { //加载失败时，绘制一些图形到图像控件里
                                    Toast("“默认.png”加载失败");
                                    Bitmap newBitmap = Bitmap.createBitmap(280, 280, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(newBitmap);
                                    Paint paint = new Paint();
                                    
                                    // 1. 绘制球形主体（带皮革质感）
                                    paint.setColor(Color.GRAY);
                                    // 创建皮革纹理图案（通过重复的小圆点模拟）
                                    Bitmap textureBitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
                                    Canvas textureCanvas = new Canvas(textureBitmap);
                                    Paint texturePaint = new Paint();
                                    texturePaint.setColor(Color.parseColor("#FF6A00"));
                                    texturePaint.setStyle(Paint.Style.FILL);
                                    for (int i = 0; i < 4; i++) {
                                        for (int j = 0; j < 4; j++) {
                                            textureCanvas.drawCircle(2.5f + i * 5, 2.5f + j * 5, 1.5f, texturePaint); // 小圆点纹理
                                        }
                                    }
                                    // 将纹理设置为画笔的位图Shader
                                    BitmapShader shader = new BitmapShader(textureBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                                    paint.setShader(shader);
                                    canvas.drawCircle(145, 145, 100, paint);
                                    
                                    // 2. 绘制黑色条纹
                                    paint.setColor(Color.BLACK);
                                    paint.setStrokeWidth(8);
                                    paint.setStyle(Paint.Style.STROKE);
                                    paint.setShader(null); // 清除纹理Shader
                                    
                                    Path stripePath = new Path();
                                    for (int i = 0; i < 8; i++) {
                                        stripePath.reset();
                                        float angle = i * 45f;
                                        float startY = 145 - 100 * (float)Math.sin(Math.toRadians(angle));
                                        float startX = 145 + 100 * (float)Math.cos(Math.toRadians(angle));
                                        float endY = 145 + 100 * (float)Math.sin(Math.toRadians(angle));
                                        float endX = 145 - 100 * (float)Math.cos(Math.toRadians(angle));
                                        
                                        stripePath.moveTo(startX, startY);
                                        stripePath.quadTo(145, 145 - 30, endX, endY);
                                        canvas.drawPath(stripePath, paint);
                                    }
                                    
                                    ImageView picture = new ImageView(thisActivity);
                                    picture.setImageBitmap(newBitmap);
                                    picture.setPadding(30, 10, 10, 10);
                                    booth1.addView(picture);
                                } else {
                                    // 将默认图片添加到布局
                                    ImageView picture = new ImageView(thisActivity);
                                    picture.setImageBitmap(Bitmap.createScaledBitmap(avatar, 280, 280, true));
                                    picture.setPadding(30, 10, 10, 10);
                                    booth1.addView(picture);
                                }
                                
                                booth.addView(booth1);
                                booth.addView(booth2);
                                linearLayout.addView(booth);
                                
                               //分割线（分隔标题部分与正文部分）
                               View underlineView = new View(GetActivity());
                               LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2); // 高度设为2dp
                               underlineParams.topMargin = 8; 
                               underlineView.setLayoutParams(underlineParams);
                               underlineView.setBackgroundColor(Color.parseColor("#333333"));
                               
                               linearLayout.addView(underlineView);
                                
                                //  添加音频控件部分
                                addAudioControl(booth2, thisActivity);
                                
                                // 如果音频列表为空，添加按钮控件到布局中
                                if (isAudioListEmpty) {
                                    load(AppPath+"/目录/UD.java");
                                    //加载指定java文件（以防万一，之前没加载）       
                                    Button downloadBtn = new Button(GetActivity());
                                    downloadBtn.setText("下载资源压缩包");
                                    downloadBtn.setTextSize(16);
                                    downloadBtn.setTextColor(Color.RED);
                                    
                                    downloadBtn.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                     //定义需要检查的路径
                                      if (hasAudioInSingleDir(audioDirPath)) {
                                        // 当前路径存在音频文件，无需处理
                                        Toast("指定路径已有音频，无需重复下载");
                                      } else {
                                        alertDialog.dismiss(); //关闭对话框
                                        // releaseAudioPlayer(); //释放音频资源
                                         showResourceDialog(); //弹出对话框
                                     }
                                    }
                                  });
                                   linearLayout.addView(downloadBtn);
                                }

                                
                                // 处理消息内容
                                String processedMsg = msg;
                                if (processedMsg != null) {
                                    processedMsg = processedMsg.replace("<br>", System.lineSeparator());
                                    processedMsg = processedMsg.replace("\\n", System.lineSeparator());
                                }

                                // 解析颜色标签
                                SpannableString spannable = new SpannableString(processedMsg);
                                handleSpecialColor(spannable, "【", "】", Color.parseColor("#d6a01d"));
                                handleSpecialColor(spannable, "《", "》", Color.parseColor("#ff0000"));
                                handleSpecialColor(spannable, "·", "·", Color.parseColor("#66a9c9"));
                                handleSpecialColor(spannable, "‹", "›", Color.parseColor("#B71B00"));

                                // 配置文本视图并添加到线性布局
                                TextView messageView = new TextView(thisActivity);
                                messageView.setText(spannable);
                                messageView.setTextSize(14f);
                                messageView.setTextColor(Color.parseColor("#454545"));
                                messageView.setPadding(25, 18, 25, 18);
                                messageView.setTextIsSelectable(true);
                                messageView.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      messageView.setVisibility(View.GONE); //隐藏
                                      idle.setVisibility(View.VISIBLE); //可见
                                      int endIndex = msg.indexOf("好");
                                      if (endIndex != -1) { // 确保找到了"好"
                                        String result = msg.substring(0, endIndex + 1); 
                                        // +1 是因为"好"占两个字符位置
                                        idle.setText(result+"  ▼\n现在是"+getTodayDate(2));
                                      }else{
                                        idle.setText(msg.substring(0,13)+"  ▼\n"+getTodayDate(2));
                                      }
                                  }
                                });
                                linearLayout.addView(messageView);
                                

                                //增加一个文本控件
                                TextView idle = new TextView(thisActivity);
                                int endIndex = msg.indexOf("好");
                                if (endIndex != -1) { // 确保找到了"好"
                                  String result = msg.substring(0, endIndex + 1); 
                                  // +1 是因为"好"占两个字符位置
                                  idle.setText(result+"  ▼\n现在是"+getTodayDate(2));
                                }else{
                                  idle.setText(msg.substring(0,13)+"  ▼\n"+getTodayDate(2));
                                }
                                idle.setTextSize(18f);
                                idle.setTextColor(Color.parseColor("#454545"));
                                idle.setPadding(25, 18, 25, 18);
                                idle.setVisibility(View.GONE);//初始化时隐藏
                                idle.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      messageView.setVisibility(View.VISIBLE); //可见
                                      idle.setVisibility(View.GONE); //隐藏
                                  }
                                });
                                linearLayout.addView(idle);
                                
                                
                                
                                alertDialog.setView(linearLayout);
                                
                                // 给控件添加旋转动画
                                RotateAnimation rotateAnimation = new RotateAnimation(
                                        0f, 360f, 
                                        Animation.RELATIVE_TO_SELF, 0.5f, 
                                        Animation.RELATIVE_TO_SELF, 0.5f);
                                rotateAnimation.setDuration(350); 
                                rotateAnimation.setRepeatCount(0); 
                                rotateAnimation.setFillAfter(true); 
                                booth1.startAnimation(rotateAnimation);

                                // 设置“关闭”按钮
                                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        releaseAudioPlayer();
                                    }
                                });

                                // 设置“详情”按钮
                                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "详情", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        releaseAudioPlayer();
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://pitch-engine-3cb.notion.site/218f26ed390e8065a9f8e1bc8b33af32"));
                                        if (intent.resolveActivity(thisActivity.getPackageManager()) != null) {
                                            thisActivity.startActivity(intent);
                                        } else {
                                            Toast.makeText(thisActivity, "未找到浏览器应用", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                        });
                    }
                } catch (Exception e) {
                    if (thisActivity != null) {
                        Toast.makeText(thisActivity, "弹窗异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).start();
    }
    

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
// 处理特定符号包裹的文本颜色
private void handleSpecialColor(Spannable spannable, String startSymbol, String endSymbol, int color) {
    int startIndex = 0;
    while (startIndex < spannable.length()) {
        // 查找开始符号位置
        int start = spannable.toString().indexOf(startSymbol, startIndex);
        if (start == -1) break;
        // 查找结束符号位置
        int end = spannable.toString().indexOf(endSymbol, start + startSymbol.length());
        if (end == -1) break;
        // 计算内容起始位置（跳过符号）
        int contentStart = start + startSymbol.length();
        int contentEnd = end;
        if (contentStart < contentEnd) {
            // 应用颜色Span（仅包裹内容，不包括符号）
            spannable.setSpan(new ForegroundColorSpan(color), contentStart, contentEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        startIndex = end + endSymbol.length(); // 从下一个位置继续查找
    }
}

    

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import java.io.File;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;

Activity ThisActivity = null;
public void initActivity()
{
	ThisActivity = GetActivity();
}


// 写入剪切板
public void copyToClip(String content){
    // 获取系统剪切板管理器的实例
    ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    
    // 创建剪切数据的实例
    // label: 剪切板内容的标签，可为空
    // content: 要设置到剪切板的文本内容
    ClipData clipData = ClipData.newPlainText(null, content);
    
    // 设置剪切板的内容
    manager.setPrimaryClip(clipData);
}


//打开某个界面（会下载群/Q头像）
public void RelSettings(String qun,String qq,int type)
{
   if(getChatType()==2){
     String 路径 =AppPath + "/缓存/头像/"+qun+".png";
     String 头像 ="https://p.qlogo.cn/gh/"+qun+"/"+qun+"/640"; 
      new Thread(new Runnable() {
         public void run() {
             File file = new File(路径);
             if (file.exists()) {
                  debris(qun,"",2);
              } else {
                try {
                      Toast("正在下载群头像，快好啦…");
                      downloadFile(头像, 路径);
                      debris(qun,"",2);
                    } catch (IOException e) {
                            Toast("群("+qun+")头像下载失败！\n\n"+e);
                    }
              }
         }
      }).start();
   }else if(getChatType()==1){
     String 路径 =AppPath + "/缓存/头像/"+qq+".png";
     String 头像 ="http://q2.qlogo.cn/headimg_dl?dst_uin=" + qq + "&spec=640"; 
      new Thread(new Runnable() {
         public void run() {
             File file = new File(路径);
             if (file.exists()) {
                  debris("",qq,1);
              } else {
                try {
                      Toast("正在下载Q头像，快好啦…");
                      downloadFile(头像, 路径);
                      debris("",qq,1);
                    } catch (IOException e) {
                            Toast("QQ("+qq+")头像下载失败！\n\n"+e);
                    }
              }
         }
      }).start();
   }
}

import android.os.Looper;
import android.widget.EditText;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.Editable;
import java.util.HashSet;
import java.util.Date;
import java.text.SimpleDateFormat;


public void debris(String qun, String qq, int num){
    initActivity();
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            String 预览="请点击下方按钮进行设置～";
            //群号+群名称 或者 QQ+昵称
            String name, number, ms1, ms2, imgPath; 
            LinearLayout boothg = new LinearLayout(GetActivity());
            boothg.setOrientation(LinearLayout.VERTICAL);
            boothg.setPadding(10,10,10,0);
         
         //根据群或Q来赋值
         if(num==1){
           String inc=昵称(qq);
           if(!inc.equals("未知")){name=inc;}
           else{name="这是昵称";}
           number=qq; ms1="QQ："; ms2="昵称：";
           imgPath=AppPath+"/缓存/头像/"+qq+".png";
         }else if(num==2){
           name=getGroupName(qun);
           number=qun; ms1="群号："; ms2="群名：";
           imgPath=AppPath+"/缓存/头像/"+qun+".png";
         }

        //群头像 群名字+群号 或 Q头像 昵称+QQ 布局
        LinearLayout booth = new LinearLayout(ThisActivity);
        booth.setOrientation(LinearLayout.HORIZONTAL);
        booth.setPadding(10,20,10,0);
        
        LinearLayout booth1 = new LinearLayout(ThisActivity);
        booth1.setOrientation(LinearLayout.VERTICAL);
        
       TextView z1 = new TextView(ThisActivity);
       z1.setText(ms1+number);
       z1.setTextIsSelectable(true); //设置文本可选
       z1.setPadding(21, 5, 0, 0);z1.setTextSize(16);
       z1.setTextColor(Color.parseColor("#000000"));
        z1.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View v)
          {
             Toast("已复制"+ms1+number,2);
             copyToClip(number);
          }
        });        
        
        TextView z2 = new TextView(ThisActivity);
        z2.setText(ms2+name);
        z2.setTextIsSelectable(true);
        z2.setPadding(21, 0, 10, 0); z2.setTextSize(15);
        z2.setTextColor(Color.parseColor("#000000"));
        z2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View v)
          {
             Toast("已复制"+ms2+name,2);
             copyToClip(name);
          }
        });        
       booth1.addView(z2);booth1.addView(z1);

        Bitmap avatar = BitmapFactory.decodeFile(imgPath);
        ImageView img = new ImageView(ThisActivity);
        img.setImageBitmap(Bitmap.createScaledBitmap(avatar, 120, 120, true));     
        img.setPadding(30,10,10,10);

        booth.addView(img);booth.addView(booth1);boothg.addView(booth);
        if (avatar == null)
        {
          Toast(ms1+"("+number+")头像加载失败");
        }
            TextView TextContent = new TextView(ThisActivity);
            TextContent.setText(预览);
            TextContent.setTextIsSelectable(true); 
            TextContent.setPadding(35,12,20,12);
            TextContent.setTextColor(Color.parseColor("#223344"));
            TextContent.setTextSize(16);
            boothg.addView(TextContent);

            // 新增：横向线性布局（在TextContent下方）
            LinearLayout buttonLayout = new LinearLayout(ThisActivity);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(35, 10, 20, 10);
            buttonLayout.setGravity(Gravity.CENTER); // 按钮居中对齐
            // 设置布局权重，让按钮均匀分布
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            );
            btnParams.setMargins(5, 0, 5, 0); // 按钮间距

            // 1. 群聊设置按钮
            Button groupSettingBtn = new Button(ThisActivity);
            groupSettingBtn.setText("群聊开关");
            groupSettingBtn.setLayoutParams(btnParams);
            groupSettingBtn.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                showGroupSwitchDialog(); // 弹出群聊开关列表对话框
             }
            });

            // 2. 间隔设置按钮
            Button intervalSettingBtn = new Button(ThisActivity);
            intervalSettingBtn.setText("间隔设置");
            intervalSettingBtn.setLayoutParams(btnParams);
            intervalSettingBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // 点击后显示间隔设置对话框
                    showIntervalSettingDialog();
                }
            });

            buttonLayout.addView(groupSettingBtn);
            buttonLayout.addView(intervalSettingBtn);
            boothg.addView(buttonLayout); // 将按钮布局添加到根布局

            final AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setCancelable(true)
                .setView(boothg)
                .setNegativeButton("关闭", null);
            final AlertDialog dialog = builder.show();
            img.setTag(dialog);
        }
    });
}



// 群聊开关列表对话框
private void showGroupSwitchDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity);
    builder.setTitle("群聊“宠物世界”玩法｜开关");

    // 获取所有群列表
    ArrayList groupList = getGroupList();
    final ArrayList validGroupUins = new ArrayList();
    final ArrayList validGroupNames = new ArrayList();
    final boolean[] checkedStates = new boolean[groupList.size()];

    // 遍历群列表，初始化状态
    for (int i = 0; i < groupList.size(); i++) {
        Object groupInfo = groupList.get(i);
        String groupUin = (String) getField(groupInfo, "GroupUin");
        String groupName = (String) getField(groupInfo, "GroupName");
        
        validGroupUins.add(groupUin);
        validGroupNames.add(groupName + "(" + groupUin + ")");
        
        // 初始化开关状态
        String status = getString("开放群列表", groupUin + "_开关状态");
        checkedStates[i] = "开".equals(status);
    }

    // 转换为数组
    final String[] groupNamesArr = new String[validGroupNames.size()];
    validGroupNames.toArray(groupNamesArr);

    // 构建多选列表（模拟开关）
    builder.setMultiChoiceItems(groupNamesArr, checkedStates,
        new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String groupUin = validGroupUins.get(which);
                // 与开关方法逻辑保持一致
                if (isChecked) {
                    putString("开放群列表", groupUin + "_开关状态", "开");
                    新群聊(groupUin); // 调用已有方法添加到开放列表
                    tcToast(groupNamesArr[which]+"\n成功开启“宠物世界”玩法，在群内发送【宠物世界】查看指令");
                } else {
                    putString("开放群列表", groupUin + "_开关状态", null);
                    移除群聊(groupUin); // 调用已有方法从开放列表移除
                    tcToast(groupNamesArr[which]+"\n已关闭“宠物世界”玩法");
                }
            }
        }
    );

    builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });

    builder.show();
}

// 反射获取群信息字段
private Object getField(Object obj, String fieldName) {
    try {
        Field field = obj.getClass().getField(fieldName);
        return field.get(obj);
    } catch (Exception e) {
        return "";
    }
}


// 间隔设置对话框
private void showIntervalSettingDialog() {
    int 指令间隔 = getInt("系统配置", "指令间隔", 1550); 
    int 跨群间隔 = getInt("系统配置", "跨群间隔", 9000); 
    int 丹药间隔 = getInt("系统配置", "丹药间隔", 5000); 
    int 天赋间隔 = getInt("系统配置", "天赋间隔", 9000); 
    int 日常间隔 = getInt("系统配置", "日常间隔", 9000); 
    int 怪物刷新间隔 = getInt("系统配置", "怪物刷新间隔", 300000); //默认五分钟

    AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity);
    builder.setTitle("间隔设置");

    // 根布局（垂直排列）
    LinearLayout rootLayout = new LinearLayout(ThisActivity);
    rootLayout.setOrientation(LinearLayout.VERTICAL);
    rootLayout.setPadding(30, 20, 30, 20);
    
    // 提示文本
    TextView tipText = new TextView(ThisActivity);
    tipText.setText("以下为【全服】通用间隔（单位:毫秒）\n✪提示：1000毫秒=1秒");
    tipText.setTextSize(15f);
    tipText.setTextColor(Color.parseColor("#FFFFFF"));
    tipText.setPadding(25, 0, 25, 15);
    rootLayout.addView(tipText);

    // 1. 指令间隔
    LinearLayout cmdIntervalLayout = new LinearLayout(ThisActivity);
    cmdIntervalLayout.setPadding(0, 5, 0, 5);
    TextView cmdLabel = new TextView(ThisActivity);
    cmdLabel.setText("指令间隔：");
    final EditText cmdInput = new EditText(ThisActivity);
    cmdInput.setText(String.valueOf(指令间隔));
    cmdInput.setHint("单位：毫秒");
    cmdInput.setGravity(Gravity.CENTER);
    cmdInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
    );
    cmdInput.setLayoutParams(inputParams);
    cmdInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=cmdLabel.getText().toString();
            handleInput(s, cmdInput, titles, 500, 7200000, 指令间隔);
        }
    });
    cmdIntervalLayout.addView(cmdLabel);
    cmdIntervalLayout.addView(cmdInput);

    // 2. 跨群间隔
    LinearLayout crossGroupLayout = new LinearLayout(ThisActivity);
    crossGroupLayout.setPadding(0, 5, 0, 5);
    TextView crossLabel = new TextView(ThisActivity);
    crossLabel.setText("跨群间隔：");
    final EditText crossInput = new EditText(ThisActivity);
    crossInput.setText(String.valueOf(跨群间隔));
    crossInput.setHint("单位：毫秒");
    crossInput.setGravity(Gravity.CENTER);
    crossInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    crossInput.setLayoutParams(inputParams);
    crossInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=crossLabel.getText().toString();
            handleInput(s, crossInput, titles, 500, 7200000, 跨群间隔);
            //限制间隔输入：最低500毫秒，最高2小时（7200000毫秒）
        }
    });
    crossGroupLayout.addView(crossLabel);
    crossGroupLayout.addView(crossInput);

    // 3. 丹药间隔
    LinearLayout drugIntervalLayout = new LinearLayout(ThisActivity);
    drugIntervalLayout.setPadding(0, 5, 0, 5);
    TextView drugLabel = new TextView(ThisActivity);
    drugLabel.setText("丹药间隔：");
    final EditText drugInput = new EditText(ThisActivity);
    drugInput.setText(String.valueOf(丹药间隔));
    drugInput.setHint("单位：毫秒");
    drugInput.setGravity(Gravity.CENTER);
    drugInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    drugInput.setLayoutParams(inputParams);
    drugInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=drugLabel.getText().toString();
            handleInput(s, drugInput, titles, 500, 7200000, 丹药间隔);
        }
    });
    drugIntervalLayout.addView(drugLabel);
    drugIntervalLayout.addView(drugInput);

    // 4. 天赋间隔
    LinearLayout talentIntervalLayout = new LinearLayout(ThisActivity);
    talentIntervalLayout.setPadding(0, 5, 0, 5);
    TextView talentLabel = new TextView(ThisActivity);
    talentLabel.setText("天赋间隔：");
    final EditText talentInput = new EditText(ThisActivity);
    talentInput.setText(String.valueOf(天赋间隔));
    talentInput.setHint("单位：毫秒");
    talentInput.setGravity(Gravity.CENTER);
    talentInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    talentInput.setLayoutParams(inputParams);
    talentInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=talentLabel.getText().toString();
            handleInput(s, talentInput, titles, 500, 7200000, 天赋间隔);
        }
    });
    talentIntervalLayout.addView(talentLabel);
    talentIntervalLayout.addView(talentInput);

    // 5. 日常间隔
    LinearLayout DailyIntervalLayout = new LinearLayout(ThisActivity);
    DailyIntervalLayout.setPadding(0, 5, 0, 5);
    TextView DailyLabel = new TextView(ThisActivity);
    DailyLabel.setText("日常间隔：");
    final EditText DailyInput = new EditText(ThisActivity);
    DailyInput.setText(String.valueOf(日常间隔));
    DailyInput.setHint("单位：毫秒");
    DailyInput.setGravity(Gravity.CENTER);
    DailyInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    DailyInput.setLayoutParams(inputParams);
    DailyInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=DailyLabel.getText().toString();
            handleInput(s, DailyInput, titles, 500, 7200000, 日常间隔);
        }
    });
    DailyIntervalLayout.addView(DailyLabel);
    DailyIntervalLayout.addView(DailyInput);

    // 怪物刷新间隔布局
    LinearLayout monsterIntervalLayout = new LinearLayout(ThisActivity);
    monsterIntervalLayout.setPadding(0, 5, 0, 5);
    TextView monsterLabel = new TextView(ThisActivity);
    monsterLabel.setText("怪物刷新间隔：");
    final EditText monsterInput = new EditText(ThisActivity);
    monsterInput.setText(String.valueOf(怪物刷新间隔));
    monsterInput.setHint("单位：毫秒");
    monsterInput.setGravity(Gravity.CENTER);
    monsterInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    monsterInput.setLayoutParams(inputParams);
    monsterInput.addTextChangedListener(new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        public void afterTextChanged(Editable s) {
            String titles=monsterLabel.getText().toString();
            handleInput(s, monsterInput, titles, 60000, 21600000, 怪物刷新间隔);
            //限制间隔输入：最低1分钟，最高6小时（21600000毫秒）
        }
    });
    monsterIntervalLayout.addView(monsterLabel);
    monsterIntervalLayout.addView(monsterInput);

    // 7. 保存和读取按钮布局
    LinearLayout btnLayout = new LinearLayout(ThisActivity);
    btnLayout.setPadding(0, 10, 0, 5);
    btnLayout.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
    btnLayout.setLayoutParams(btnLayoutParams);
    LinearLayout.LayoutParams btparams = new LinearLayout.LayoutParams(
        0,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        1.0f
    );
    btparams.setMargins(5, 0, 5, 0);
    Button saveBtn = new Button(ThisActivity);
    saveBtn.setText("保存");
    saveBtn.setLayoutParams(btparams);
    saveBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // 保存相关间隔输入框的内容
            putInt("系统配置", "指令间隔", Integer.parseInt(cmdInput.getText().toString()));
            putInt("系统配置", "跨群间隔", Integer.parseInt(crossInput.getText().toString()));
            putInt("系统配置", "丹药间隔", Integer.parseInt(drugInput.getText().toString()));
            putInt("系统配置", "天赋间隔", Integer.parseInt(talentInput.getText().toString()));
            putInt("系统配置", "日常间隔", Integer.parseInt(DailyInput.getText().toString()));
            putInt("系统配置", "怪物刷新间隔", Integer.parseInt(monsterInput.getText().toString()));
            tcToast("所有间隔设置已保存更新并应用到【全服】啦");
        }
    });
    Button loadBtn = new Button(ThisActivity);
    loadBtn.setText("读取");
    loadBtn.setLayoutParams(btparams);
    loadBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // 读取保存的间隔 或 默认值
            cmdInput.setText(String.valueOf(指令间隔));
            crossInput.setText(String.valueOf(跨群间隔));
            drugInput.setText(String.valueOf(丹药间隔));
            talentInput.setText(String.valueOf(天赋间隔));
            DailyInput.setText(String.valueOf(日常间隔));
            monsterInput.setText(String.valueOf(怪物刷新间隔));
            tcToast("已读取所有保存的间隔设置");
        }
    });
    btnLayout.addView(saveBtn);
    btnLayout.addView(loadBtn);

    // 添加所有布局到根布局
    rootLayout.addView(cmdIntervalLayout);
    rootLayout.addView(crossGroupLayout);
    rootLayout.addView(drugIntervalLayout);
    rootLayout.addView(talentIntervalLayout);
    rootLayout.addView(DailyIntervalLayout);
    rootLayout.addView(monsterIntervalLayout);
    rootLayout.addView(btnLayout);

    builder.setView(rootLayout);
    builder.setNegativeButton("关闭", null);
    builder.show();
}

// 输入处理工具方法（用于限制“间隔设置对话框”输入）
private void handleInput(Editable s, EditText input, String title, int min, int max, int defaultValue) {
    String inputStr = s.toString().trim();
    if (inputStr.isEmpty()) {
        input.setText(String.valueOf(defaultValue));
        tcToast(title+"间隔不能为空，已使用默认值：" + formatTime((long) defaultValue));
        return;
    }
    try {
        int value = Integer.parseInt(inputStr);
        if (value < min) {
            input.setText(String.valueOf(min));
            tcToast(title+"间隔不能小于" + formatTime((long) min)+"，已自动调整");
        } else if (value > max) {
            input.setText(String.valueOf(max));
            tcToast(title+"间隔不能大于" + formatTime((long) max)+"，已自动调整");
        }
    } catch (NumberFormatException e) {
        input.setText(String.valueOf(defaultValue));
        tcToast(title+"请输入有效数字，已使用默认值：" + formatTime((long) defaultValue));
    }
}


// string类型 转 long.类型
    public static long 文转(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
           //内容解析失败，返回为0
            return 0;
        }
    }
    
   // 将 long类型 转换为 String类型
    public static String 转文(long num) 
    {
        return String.valueOf(num);
    }
    

   //下面四个就是加减乘除方法了
    public static long 加(long num1, long num2) {
     return num1 + num2;
    }

    public static long 减(long num1, long num2) {
      return num1 >= num2 ? num1 - num2 : 0; // 小于时返回0
    }


    public static long 乘(long num1, long num2) {
     return num1 * num2;
    }

    public static long 除(long num1, long num2) {
        if (num2 == 0) {
            Toast("除数不能为0，已返回默认值。");
            return 0;
        }
        return num1 / num2;
    }


import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;

    //亿亿单位之内的换算
    public static String 数值转(long number) {
       long 目标1=10000;
       long 目标2=100000000;
       long 目标3=(目标2*10000);
       long 目标4=(目标3*10000);
       long 目标5=(目标4*10000);
       
        if (number < (long) 目标1*10) {
            return String.valueOf(number); //不超过10万，不变
        } else if (number < 目标2) {
            double num = (double) number / 目标1;
            return formatNumber(num)+"W"; // 万
        } else if (number < 目标3) {
            double num = (double) number / 目标2;
            return formatNumber(num)+"E"; // 亿
        } else if (number < 目标4){
           double num = (double) number / 目标3;
            return formatNumber(num)+"WE"; // 万亿
        } else {
            // 超过万亿的数值，使用BigInteger处理
            return convertLargeNumber(BigInteger.valueOf(number));
        }
    }
    
    
// 支持String类型输入，自动判断是否超过long范围并处理
public static String 数值转(String numberStr) {
    // 去除字符串中的非数字字符（防止干扰判断）
    String cleanStr = numberStr.replaceAll("[^\\d]", "");
    if (cleanStr.isEmpty()) {
        return "0"; // 空字符串默认返回0
    }

    // 判断是否超过long最大值（9223372036854775807）
    boolean isBeyondLong = false;
    if (cleanStr.length() > 19) {
        isBeyondLong = true;
    } else if (cleanStr.length() == 19) {
        // 长度等于19位时，直接比较字符串
        isBeyondLong = cleanStr.compareTo("9223372036854775807") > 0;
    }

    if (isBeyondLong) {
        // 超过long范围，用BigInteger处理
        BigInteger bigNumber = new BigInteger(cleanStr);
        return convertLargeNumber(bigNumber);
    } else {
        // 未超过long范围，按原逻辑处理
        long number = Long.parseLong(cleanStr);
        long 目标1 = 10000;
        long 目标2 = 100000000;
        long 目标3 = 目标2 * 10000;
        long 目标4 = 目标3 * 10000;

        if (number < 目标1 * 10) {
            return String.valueOf(number); // 不超过10万，不变
        } else if (number < 目标2) {
            double num = (double) number / 目标1;
            return formatNumber(num) + "W"; // 万
        } else if (number < 目标3) {
            double num = (double) number / 目标2;
            return formatNumber(num) + "E"; // 亿
        } else if (number < 目标4) {
            double num = (double) number / 目标3;
            return formatNumber(num) + "WE"; // 万亿
        } else {
            // 超过万亿但未超过long范围，仍用BigInteger处理
            return convertLargeNumber(BigInteger.valueOf(number));
        }
    }
}

    
    //处理超大数值（从京单位开始）的转换
    private static String convertLargeNumber(BigInteger bigNumber) {
        // 单位数组（按从大到小顺序排列）
        String[] units = {"EE", "TT", "PP", "GG", "MM", "KK", "H", "D", "U", "GS", "AS", "NYT", "BSY", "WL", "DS"};
       // String[] units = {"京", "垓", "秭", "穰", "沟", "涧", "正", "载", "极", "恒河沙", "阿僧祇", "那由他", "不可思议", "无量", "大数"};
        BigInteger unitBase = BigInteger.valueOf(10000); // 每级单位为10000倍关系
        
        // 从京（10^16）开始计算，初始单位基数为10^16（10000^4）
        BigInteger base = unitBase.pow(4); // 1京 = 10000^4 = 10^16
        
        for (int i = 0; i < units.length; i++) {
            // 检查是否达到当前单位的数量级
            if (bigNumber.compareTo(base) < 0) {
                // 前一个单位的数值（未达到当前单位时，使用前一个单位）
                if (i > 0) {
                    base = base.divide(unitBase);
                    return formatBigNumber(bigNumber.divide(base), 2) + units[i - 1];
                }
                // 最小单位为京，无法再降级时返回原始值
                return bigNumber.toString();
            }
            // 升级到下一个单位基数
            base = base.multiply(unitBase);
        }
        
        // 超过最大单位时返回带科学计数法的字符串
        return bigNumber.toString() + "（超出最大单位）";
    }
    
    // 格式化大数值（保留指定小数位）
    private static String formatBigNumber(BigInteger value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        String result = bd.stripTrailingZeros().toPlainString();
        if (result.endsWith(".00")) {
            result = result.substring(0, result.length() - 3);
        }
        return result;
    }
    
// 小数精简到2位，进行四舍五入，并简化结果
private static String formatNumber(double num) {
    BigDecimal bd = new BigDecimal(num);
    bd = bd.setScale(2, RoundingMode.HALF_UP); // 四舍五入，保留2位小数
    String result = bd.stripTrailingZeros().toPlainString();
    if (result.endsWith(".00")) {
        result = result.substring(0, result.length() - 3);
    }
    return result;
}

// 支持任意大数值的换算（基于BigInteger）
public static String 数值转(BigInteger number) {
    // 处理负数
    if (number.compareTo(BigInteger.ZERO) < 0) {
        return number.toString();
    }

    BigInteger 目标1 = BigInteger.valueOf(10000); // 1万
    BigInteger 目标2 = BigInteger.valueOf(100000000); // 1亿
    BigInteger 目标3 = 目标2.multiply(目标1); // 1万亿（10^12）
    BigInteger 目标4 = 目标3.multiply(目标1); // 1千万亿（10^16，即1京）

    // 1. 不超过10万（10*1万）
    if (number.compareTo(目标1.multiply(BigInteger.TEN)) < 0) {
        return number.toString();
    }
    // 2. 万级（10万 ~ 1亿）
    else if (number.compareTo(目标2) < 0) {
        BigDecimal num = new BigDecimal(number).divide(new BigDecimal(目标1), 2, RoundingMode.HALF_UP);
        return formatNumber(num) + "W";
    }
    // 3. 亿级（1亿 ~ 1万亿）
    else if (number.compareTo(目标3) < 0) {
        BigDecimal num = new BigDecimal(number).divide(new BigDecimal(目标2), 2, RoundingMode.HALF_UP);
        return formatNumber(num) + "E";
    }
    // 4. 万亿级（1万亿 ~ 1京）
    else if (number.compareTo(目标4) < 0) {
        BigDecimal num = new BigDecimal(number).divide(new BigDecimal(目标3), 2, RoundingMode.HALF_UP);
        return formatNumber(num) + "WE";
    }
    // 5. 超大数值（≥1京），使用大单位换算
    else {
        return convertLargeNumber(number);
    }
}

// 处理超大数值（从京单位开始）的转换
private static String convertLargeNumber(BigInteger bigNumber) {
    String[] units = {"EE", "TT", "PP", "GG", "MM", "KK", "H", "D", "U", "GS", "AS", "NYT", "BSY", "WL", "DS"};
    BigInteger unitBase = BigInteger.valueOf(10000); // 每级单位为10000倍关系
    BigInteger base = unitBase.pow(4); // 1京 = 10000^4 = 10^16（作为起始单位）

    for (int i = 0; i < units.length; i++) {
        if (bigNumber.compareTo(base) < 0) {
            if (i > 0) {
                base = base.divide(unitBase); // 回退到上一级单位
                BigDecimal num = new BigDecimal(bigNumber).divide(new BigDecimal(base), 2, RoundingMode.HALF_UP);
                return formatNumber(num) + units[i - 1];
            }
            // 若未匹配到任何上级单位，直接返回原始值（理论上不会触发）
            return bigNumber.toString();
        }
        base = base.multiply(unitBase); // 升级到下一级单位
    }

    // 超过最大单位时的处理
    return bigNumber.toString() + "（超出最大单位）";
}

// 格式化带小数的数值（统一处理BigDecimal）
private static String formatNumber(BigDecimal num) {
    // 保留2位小数并四舍五入
    BigDecimal scaled = num.setScale(2, RoundingMode.HALF_UP);
    // 移除末尾多余的0和小数点（如"123.00" → "123"，"45.60" → "45.6"）
    String result = scaled.stripTrailingZeros().toPlainString();
    return result;
}

    
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

// 提取括号内的内容
private static String extractByBrackets(String message){
    Pattern pattern = Pattern.compile("【(.*?)】");
    Matcher matcher = pattern.matcher(message);
    
    if (matcher.find()) {
       String content = matcher.group(1);
       // 去除所有空格（包括首尾和中间）
       content = content.replaceAll("\\s", "");
       return content.isEmpty() ? null : content;
    }
    return null;
}

// 分割@符号前后内容，只返回@前的内容
private static String removeAfterAt(String str) {
    if (str == null) {
        return null;
    }
    String[] parts = str.split("@", 2); // 按@分割，最多保留2部分
    return parts[0]; // 返回@前的部分
}

import java.io.File;
import java.util.Random;
import java.util.Scanner;

// 读取指定文件，从中随机返回一行，统一处理异常情况
public static String 随机提示(File f) {  
    final String DEFAULT_RESULT = "文件处理异常"; // 统一默认返回内容
    
    if (f == null) {  
        return DEFAULT_RESULT; // 文件对象为null时直接返回默认内容
    }
    
    String result = DEFAULT_RESULT; // 初始化默认结果
    Random rand = new Random();  
    int lineNum = 0;  
    Scanner sc = null; // 提前声明Scanner
    
    try {  
        sc = new Scanner(f);
        while (sc.hasNextLine()) {  
            lineNum++;  
            String line = sc.nextLine();  
            if (lineNum == 1) {  
                result = line;  
            } else {  
                if (rand.nextInt(lineNum) == 0) {  
                    result = line;  
                }  
            }  
        }  
        // 处理<br>替换
        if (result != null && result.contains("<br>")) {  
            result = result.replace("<br>", System.lineSeparator());  
        }  
    } catch (FileNotFoundException e) {  
        // 捕获异常后不抛出，直接使用默认结果（已初始化）
    } finally {  
        if (sc != null) { // 增加资源关闭逻辑
            sc.close();
        }  
    }      
    return result;  
}  


// 消息发送方法
public void 发送(String qun, String msg, boolean IsTips)
{  
    boolean 开关状态 = getBoolean("系统配置", "随机提示", false);  
    String finalMsg = msg; // 默认为原消息  

    // 当两个状态都是true时，根据文件内返回内容来决定是否拼接
    if (IsTips && 开关状态) {
        File d = new File(AppPath + "/目录/随机提示.txt");  
        String dd = 随机提示(d); // 随机获取一条内容  
         // 若dd返回内容为"文件处理异常"，则不拼接
        if (!"文件处理异常".equals(dd)) {  
          finalMsg = msg + "\n\n\n" + dd;  
        }  
    }
    sendMsg(qun, "", finalMsg);  
}


// 将true、false转换为特定内容
public static String 查看(boolean bool) {
        if (bool) {
            return "可以";
        } else {
            return "不可以";
        }
    }
    
// 用于换算时间
public static String formatTime(Long ms) {
    Integer ss = 1000;
    Integer mi = ss * 60;
    Integer hh = mi * 60;
    Integer dd = hh * 24;

    Long day = ms / dd;
    Long hour = (ms - day * dd) / hh;
    Long minute = (ms - day * dd - hour * hh) / mi;
    Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
    Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
    
    StringBuffer sb = new StringBuffer();
    if(day > 0) {
        sb.append(day+"天");
    }
    if(hour > 0) {
        sb.append(hour+"小时");
    }
    if(minute > 0) {
        sb.append(minute+"分");
    }
    if(second > 0) {
        sb.append(second+"秒");
    }
    if(milliSecond > 0) {
        sb.append(milliSecond+"毫秒");
    }
    return sb.toString();
}    
    
import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

    // 线程安全缓存，限制最大容量（示例：100条）
    private final Map fileMemCache = new ConcurrentHashMap();
    private static final int MAX_CACHE_SIZE = 100;

    public String read(String filePath) {
        // 先检查缓存
        if (fileMemCache.containsKey(filePath)) {
            return fileMemCache.get(filePath);
        }

        File file = new File(filePath);
        // 自动创建文件（如需）
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast("创建文件失败：" + filePath);
                return "";
            }
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            String text = content.toString();
            // 安全处理首字符截断（仅当首字符是换行符时）
            if (!text.isEmpty() && text.startsWith("\n")) {
                text = text.substring(1);
            }
            // 更新缓存（限制大小）
            if (!text.isEmpty() && fileMemCache.size() < MAX_CACHE_SIZE) {
                fileMemCache.put(filePath, text);
            }
            return text;
        } catch (IOException e) {
            sendMsg("",MyUin,"读取文件失败：" + filePath + ", 错误：" + e.getMessage());
            return "";
        }
    }

    public String readProp(String file, String name2) {
        String content = read(file);
        if (content.isEmpty()) {
            return "";
        }
        Properties props = new Properties();
        try {
            props.load(new StringReader(content));
        } catch (IOException e) {
            sendMsg("",MyUin,"解析属性文件失败：" + e.getMessage());
            return "";
        }
        return props.getProperty(name2, ""); // 不存在时返回空字符串
    }

    
// 数字分位格式化，如果数字为0将替换为特定内容
public static String 标记(long number) 
{
        if (number>=10000000) 
        {
          StringBuilder sb = new StringBuilder();
          String numStr = String.valueOf(number);
          int length = numStr.length();
          int count = 0;
          for (int i = length - 1; i >= 0; i--) {
            sb.append(numStr.charAt(i));
            count++;
            if (count % 4 == 0 && i != 0) {
                sb.append(",");
            }
          }
          return sb.reverse().toString();
        }
         else if (number != 0) {
            return String.valueOf(number);
        }
        else{return "——";}
}    


/**
- 对账号进行脱敏处理
 
- @param account 待脱敏的账号字符串
 
- @return 脱敏后的字符串，规则为：若账号长度小于5位则直接返回原字符串，否则保留前3位和后2位，中间用符号替换

- 示例：1234567890 → 123•••90
**/
   public static String maskAccount(String account) 
   {
        if (account.length() < 5) { // 至少保留前3位和后2位共5位
            return account;
        }
        // 取前3位 + "•••" + 后2位
        return account.substring(0, 3) + "…" + account.substring(account.length() - 2);
    }
    
import com.tencent.util.QQToastUtil;
public QQToastUtil QToastUtil=new QQToastUtil();
// 类型(int) 0：蓝色叹号 1：红色叹号 2：绿色对号
public void QQToast(String text,int i){
 QToastUtil.showQQToastInUiThread(i,text);
}    
    
import java.text.SimpleDateFormat;
import java.util.Date;
//获取当前日期，并按指定格式返回
public String getTodayDate(int num)
{
     Date date = new Date();
     SimpleDateFormat dateFormat;

     switch (num) {
         case 1:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd");
             break;
         case 2:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
             break;
         case 3:
             dateFormat = new SimpleDateFormat("MM/dd/YYYY");
             break;
         case 4:
             dateFormat = new SimpleDateFormat("HH:mm:ss");
             break;
         case 5:
             dateFormat = new SimpleDateFormat("YYYYMMdd");
             break;
         default:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        }

    return dateFormat.format(date);
}


import android.graphics.Bitmap;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.PorterDuff;  
import android.graphics.PorterDuffXfermode;  
import android.graphics.Shader;  
import android.graphics.Typeface;  
import android.graphics.LinearGradient;  
import android.graphics.ComposeShader;  
import android.graphics.BitmapShader;  
import android.graphics.MaskFilter;  
import android.graphics.BlurMaskFilter;  
import android.graphics.PointF;  
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;  
import java.util.Random;  
import android.util.Log;


  //删除指定文件夹内 文件
	public static void delAllFile(String path)
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				flag = true;
			}
		}
		return;
	}
	
//删除指定路径的文件
public boolean 删除文件(String filePath)
{
    File file = new File(filePath);
    if (file.exists()) {
        return file.delete();
    }
    return false;
}	

// 文字转图片 (图片的宽、高根据文本来决定)
public void toImg(String text, String color, double x, double y, int size, String path, boolean isGradientBackground) {
    // 创建字体对象
    Paint pt = new Paint();
    pt.setTextSize(size);
    pt.setTypeface(Typeface.SERIF);
    pt.setAntiAlias(true); // 抗锯齿

    // 按换行符 \n 分割文本
    String[] lines = text.split("\n");

    // 计算文本行高（假设每行高度相同）
    float lineHeight = pt.descent() - pt.ascent();
    // 计算文本总高度，按照 行数 * 高 计算
    float totalHeight = lines.length * lineHeight;

    // 计算文本总宽度（取最长行的宽度）
    float maxWidth = 0;
    for (String line : lines) {
        float lineWidth = pt.measureText(line);
        if (lineWidth > maxWidth) {
            maxWidth = lineWidth;
        }
    }

    // 增加额外的宽度和高度空间，让图片更合理
    float extraWidth = 50; // 额外宽度
    float extraHeight = 20; // 额外高度
    maxWidth += extraWidth;
    totalHeight += extraHeight;

    // 根据文字尺寸创建实际的Bitmap 和 Canvas
    Bitmap bmp = Bitmap.createBitmap((int) maxWidth, (int) totalHeight, Bitmap.Config.ARGB_8888);
    Canvas cas = new Canvas(bmp);
    
    if (isGradientBackground) { // 开启渐变背景 + 右侧反光
        // 定义背景相关颜色（主渐变 + 反光透明色）
        String color1 = "#FFF5EB"; // 浅米黄  
        String color2 = "#EFE0C8"; // 浅棕黄  
        String reflectColor = "#50FFFFFF"; // 右侧反光（白色透明渐变）

        // 生成随机渐变方向（随机范围：四个对角方向+上下左右）  
        Random random = new Random();  
        int direction = random.nextInt(8); // 0-7代表8个方向  
        int startX = 0, startY = 0, endX = 0, endY = totalHeight; // 默认从上到下  

        // 方向逻辑优化（补充默认方向处理）
        switch (direction) {  
            case 0:  // 从上到下（默认）  
                startX = 0;  
                startY = 0;  
                endX = 0;  
                endY = (int) totalHeight;  
                break;  
            case 1:  // 从左到右  
                startX = 0;  
                startY = 0;  
                endX = (int) maxWidth;  
                endY = 0;  
                break;  
            case 2:  // 从右到左  
                startX = (int) maxWidth;  
                startY = 0;  
                endX = 0;  
                endY = 0;  
                break;  
            case 3:  // 从下到上  
                startX = 0;  
                startY = (int) totalHeight;  
                endX = 0;  
                endY = 0;  
                break;  
            case 4:  // 左上到右下  
                startX = 0;  
                startY = 0;  
                endX = (int) maxWidth;  
                endY = (int) totalHeight;  
                break;  
            case 5:  // 右上到左下  
                startX = (int) maxWidth;  
                startY = 0;  
                endX = 0;  
                endY = (int) totalHeight;  
                break;  
            case 6:  // 左下到右上  
                startX = 0;  
                startY = (int) totalHeight;  
                endX = (int) maxWidth;  
                endY = 0;  
                break;  
            case 7:  // 右下到左上  
                startX = (int) maxWidth;  
                startY = (int) totalHeight;  
                endX = 0;  
                endY = 0;  
                break;  
        }  

        // 右侧反光渐变（叠加在主渐变上）
        float reflectWidth = maxWidth * 0.2f; // 反光宽度占比20%
        LinearGradient reflectGradient = new LinearGradient(
                maxWidth - reflectWidth, 0, // 反光起始X（右侧20%位置）
                maxWidth, 0, // 反光结束X（最右侧）
                new int[]{Color.parseColor("#00FFFFFF"), Color.parseColor(reflectColor)}, // 透明到反光色
                null, 
                Shader.TileMode.CLAMP
        );

        // 组合主渐变和反光渐变（使用PorterDuff.Mode.SRC_OVER混合）
        Paint bgPaint = new Paint();
        bgPaint.setShader(new ComposeShader(
                new LinearGradient(  
                        startX, startY,  
                        endX, endY,  
                        new int[]{Color.parseColor(color1), Color.parseColor(color2)},  
                        null,  
                        Shader.TileMode.CLAMP  
                ), 
                reflectGradient, 
                PorterDuff.Mode.SRC_OVER
        ));
        
        cas.drawRect(0, 0, maxWidth, totalHeight, bgPaint); // 绘制带反光的渐变背景
    } else { // 保持原白色背景
        cas.drawColor(Color.WHITE);
    }
    
    // 计算起始绘制位置
    float startX = (float) (x * bmp.getWidth() - 0.5 * maxWidth + extraWidth / 2);
    float startY = (float) (y * bmp.getHeight() + lineHeight - extraHeight / 3);

    // 逐行绘制文本
    for (int i = 0; i < lines.length; i++) {
        String line = lines[i];
        Paint currentPaint = new Paint(pt); // 复制当前画笔
        
        /// 判断行是否为 <分割>文本内容</分割> 的格式
        if (line.contains("<分割>") && line.contains("</分割>")) {
            int startIndex = line.indexOf("<分割>");
            int endIndex = line.indexOf("</分割>");
            if (startIndex < endIndex) {
                // 绘制 "<分割>" 之前的文本
                String prefix = line.substring(0, startIndex);
                float prefixX = startX;
                float prefixY = startY + i * lineHeight;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);
        
                // 绘制 "<分割>" 和 "</分割>" 之间的文本
                String middle = line.substring(startIndex + 4, endIndex);
                // 获取当前文本大小
                float originalTextSize = currentPaint.getTextSize();
                // 缩放文本大小，这里设置为原文本大小的1.10倍
                currentPaint.setTextSize(originalTextSize * 1.10f);
                // 考虑字体放大后的文本宽度
                float middleWidth = pt.measureText(middle) * 1.10f; 
                float middleY = prefixY;
                float middleX = startX + (maxWidth - extraWidth - middleWidth) / 2;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(middle, middleX, middleY, currentPaint);
        
                // 计算左右两边需要填充的“-”符号数量
                float availableWidth = maxWidth - extraWidth - middleWidth - pt.measureText(prefix);
                float ellipsisWidth = pt.measureText("-");
                int repeatCount = (int) (availableWidth / ellipsisWidth / 2);
                StringBuilder filledLeft = new StringBuilder();
                StringBuilder filledRight = new StringBuilder();
                for (int j = 0; j < repeatCount; j++) {
                    filledLeft.append("-");
                    filledRight.append("-");
                }
        
                // 绘制左边的填充符号
                float leftX = prefixX + pt.measureText(prefix);
                float leftY = prefixY;
                currentPaint.setTextSize(originalTextSize);
                currentPaint.setColor(getValidColor(color));
                cas.drawText(filledLeft.toString(), leftX, leftY, currentPaint);
        
                // 绘制右边的填充符号
                float rightX = middleX + middleWidth;
                float rightY = prefixY;
                cas.drawText(filledRight.toString(), rightX, rightY, currentPaint);
        
                // 绘制 "</分割>" 之后的文本
                String suffix = line.substring(endIndex + 5);
                float suffixX = rightX + pt.measureText(filledRight.toString());
                float suffixY = prefixY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffix, suffixX, suffixY, currentPaint);
                continue;
            }
        }
        
// 判断行中是否包含“换行”关键词（可自定义触发词，如“\n”“换行”等）
if (line.contains("换行")) {
    // 按“换行”分割前后内容
    String[] parts = line.split("换行", 2);
    String firstPart = parts[0];
    String secondPart = parts.length > 1 ? parts[1] : "";
    
    // 绘制第一部分文本
    float firstPartX = startX;
    float firstPartY = startY + i * lineHeight;
    currentPaint.setColor(getValidColor(color));
    cas.drawText(firstPart, firstPartX, firstPartY, currentPaint);
    
    // 计算第二部分文本的Y坐标（下移一行）
    float secondPartY = firstPartY + lineHeight;
    float secondPartX = startX;
    cas.drawText(secondPart, secondPartX, secondPartY, currentPaint);
    
    // 跳过当前循环（后续逻辑不再执行）
    continue;
}

        // 判断行内容是否为 "<填充>"
        if ("<填充>".equals(line)) {
            line = "-";
            // 计算需要重复多少次 "-" 才能填满一行
            float availableWidth = maxWidth - extraWidth;
            float ellipsisWidth = pt.measureText("-");
            int repeatCount = (int) (availableWidth / ellipsisWidth);
            StringBuilder filledLine = new StringBuilder();
            for (int j = 0; j < repeatCount; j++) {
                filledLine.append("-");
            }
            line = filledLine.toString();
            currentPaint.setColor(getValidColor(color));
        } 
        // 判断行中是否包含 "*"，可以把符号内的文本标红
        if (line.contains("*")) {
            int startIndex = line.indexOf("*");
            int endIndex = line.lastIndexOf("*");
            if (startIndex != endIndex) {
                // 绘制 "*" 号之前的文本
                String prefix = line.substring(0, startIndex);
                float prefixX = startX;
                float prefixY = startY + i * lineHeight;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);

                // 绘制 "*" 号之间的文本
                String middle = line.substring(startIndex+1, endIndex);
                float middleX = prefixX + pt.measureText(prefix);
                float middleY = prefixY;
                currentPaint.setColor(Color.RED);
                cas.drawText(middle, middleX, middleY, currentPaint);

                // 绘制 "*" 号之后的文本
                String suffix = line.substring(endIndex + 1);
                float suffixX = middleX + pt.measureText(middle);
                float suffixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffix, suffixX, suffixY, currentPaint);
                continue;
            }
        }
        // 判断行中是否包含 "@" 和 "("，用来显示被@人员
        if (line.contains("@") &&( line.contains("(")||line.contains("您"))) {
            int atIndex = line.indexOf("@");
            int openBracketIndex;
            if(line.contains("您")){openBracketIndex= line.indexOf("您");}
            else if(line.contains("(")){openBracketIndex= line.indexOf("(");}
            if (atIndex < openBracketIndex) {
                // 绘制 "@" 之前的文本
                String prefix = line.substring(0, atIndex);
                float prefixX = startX;
                float prefixY = startY + i * lineHeight;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);

                // 绘制 "@" 和 "(" 之间的文本
                String middle = line.substring(atIndex, openBracketIndex);
                float middleX = prefixX + pt.measureText(prefix);
                float middleY = prefixY;
                currentPaint.setColor(Color.BLUE);
                cas.drawText(middle, middleX, middleY, currentPaint);

                // 绘制 "(" 之后的文本
                String suffix = line.substring(openBracketIndex);
                float suffixX = middleX + pt.measureText(middle);
                float suffixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffix, suffixX, suffixY, currentPaint);
                continue;
            }
        }
        // 判断行中是否包含 "【" 和 "】"
        if (line.contains("【") && line.contains("】")) {
            int startIndex = line.indexOf("【");
            int endIndex = line.indexOf("】");
            if (startIndex < endIndex) {
                // 绘制 "【" 之前的文本
                String prefix = line.substring(0, startIndex);
                float prefixX = startX;
                float prefixY = startY + i * lineHeight;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);

                // 绘制 "【"
                String leftBracket = "【";
                float leftBracketX = prefixX + pt.measureText(prefix);
                float leftBracketY = prefixY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(leftBracket, leftBracketX, leftBracketY, currentPaint);

                // 绘制 "【" 和 "】" 之间的文本
                String middle = line.substring(startIndex + 1, endIndex);
                float middleX = leftBracketX + pt.measureText(leftBracket);
                float middleY = prefixY;
                currentPaint.setColor(Color.parseColor("#FFA500")); // 橙色
                // 设置阴影效果
                currentPaint.setShadowLayer(1, 0, 0, Color.GRAY); 
                cas.drawText(middle, middleX, middleY, currentPaint);
                // 清除阴影效果，避免影响后续绘制
                currentPaint.setShadowLayer(0, 0, 0, 0); 

                // 绘制 "】"
                String rightBracket = "】";
                float rightBracketX = middleX + pt.measureText(middle);
                float rightBracketY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(rightBracket, rightBracketX, rightBracketY, currentPaint);

                // 绘制 "】" 之后的文本
                String suffix = line.substring(endIndex + 1);
                float suffixX = rightBracketX + pt.measureText(rightBracket);
                float suffixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffix, suffixX, suffixY, currentPaint);
                continue;
            }
        }
        
// 先判断行是否包含<起>和<尾>，且格式正确
if (line.contains("<起>") && line.contains("<尾>")) {
    int startIndex = line.indexOf("<起>");
    int endIndex = line.indexOf("<尾>");
    if (startIndex < endIndex) {
        // 处理<起>之前的前置内容（如有）
        if (startIndex > 0) { // 存在前置内容
            String prefix = line.substring(0, startIndex);
            float prefixX = startX;
            float prefixY = startY + i * lineHeight;
            currentPaint.setColor(getValidColor(color));
            cas.drawText(prefix, prefixX, prefixY, currentPaint);
        }
        // 提取<起>和<尾>之间的内容
        String middle = line.substring(startIndex + 3, endIndex); // <起>长度为3
        // 计算格式内容的居右位置（预留2字间隔，假设单字宽度为pt.measureText("字")）
        float singleCharWidth = pt.measureText("字"); // 假设单字宽度，可根据实际调整
        float reservedSpace = singleCharWidth * 2; // 预留2字间隔
        float middleWidth = pt.measureText(middle);
        // 总可用宽度（去除额外宽度后）
        float availableWidth = maxWidth - extraWidth;
        // 格式内容起始X = 可用宽度 - 内容宽度 - 预留间隔
        float middleX = startX + availableWidth - middleWidth - reservedSpace;
        float middleY = startY + i * lineHeight;
        currentPaint.setColor(getValidColor(color)); // 颜色沿用原设置
        cas.drawText(middle, middleX, middleY, currentPaint);
        
        // 处理<尾>之后的内容（可能包含注释符号）
        if (endIndex + 3 < line.length()) { // <尾>长度为3，检查是否有后续内容
            String suffix = line.substring(endIndex + 3);
            // 继续处理后续内容中的注释符号
            if (suffix.contains("//")) {
                int commentIndexInSuffix = suffix.indexOf("//");
                String suffixPrefix = suffix.substring(0, commentIndexInSuffix);
                float suffixPrefixX = middleX + middleWidth + reservedSpace;
                float suffixPrefixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffixPrefix, suffixPrefixX, suffixPrefixY, currentPaint);
                
                String commentPart = suffix.substring(commentIndexInSuffix + 2);
                float commentX = suffixPrefixX + pt.measureText(suffixPrefix);
                float commentY = commentY;
                
                float originalTextSize = currentPaint.getTextSize();
                currentPaint.setTextSkewX(-0.2f); 
                currentPaint.setTextSize(originalTextSize * 0.8f); 
                currentPaint.setColor(getValidColor("#606060"));
                cas.drawText(commentPart, commentX, commentY, currentPaint);
                
                currentPaint.setTextSize(originalTextSize);
            } else {
                // 后续内容无注释时直接绘制
                String suffixContent = line.substring(endIndex + 3);
                float suffixX = middleX + middleWidth + reservedSpace;
                float suffixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffixContent, suffixX, suffixY, currentPaint);
            }
        }
        continue;
    }
}


// 判断行是否包含<始>和<终>，且格式正确（与上面基本一致）
if (line.contains("<始>") && line.contains("<终>")) {
    int startIndex = line.indexOf("<始>");
    int endIndex = line.indexOf("<终>");
    if (startIndex < endIndex) {
        // 处理前置内容（不变）
        if (startIndex > 0) { 
            String prefix = line.substring(0, startIndex);
            float prefixX = startX;
            float prefixY = startY + i * lineHeight;
            currentPaint.setColor(getValidColor(color));
            cas.drawText(prefix, prefixX, prefixY, currentPaint);
        }
        
        // 提取中间内容并设置样式
        String middle = line.substring(startIndex + 3, endIndex);
        float singleCharWidth = pt.measureText("字");
        float reservedSpace = singleCharWidth * 2;
        float middleWidth = pt.measureText(middle);
        float availableWidth = maxWidth - extraWidth;
        float middleX = startX + availableWidth - middleWidth - reservedSpace;
        float middleY = startY + i * lineHeight;
        
        // 新增：修改中间内容的文本大小和颜色
        float originalTextSize = currentPaint.getTextSize();
        currentPaint.setTextSize(originalTextSize * 0.8f); // 缩小到80%
        currentPaint.setColor(getValidColor("#606060")); // 灰色
        cas.drawText(middle, middleX, middleY, currentPaint);
        
        // 恢复原始文本大小，以便后续内容绘制
        currentPaint.setTextSize(originalTextSize);
        
        // 处理后续内容（调整逻辑以适配新坐标）
        if (endIndex + 3 < line.length()) {
            String suffix = line.substring(endIndex + 3);
            if (suffix.contains("//")) {
                int commentIndexInSuffix = suffix.indexOf("//");
                String suffixPrefix = suffix.substring(0, commentIndexInSuffix);
                float suffixPrefixX = middleX + middleWidth + reservedSpace;
                float suffixPrefixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffixPrefix, suffixPrefixX, suffixPrefixY, currentPaint);
                
                String commentPart = suffix.substring(commentIndexInSuffix + 2);
                float commentX = suffixPrefixX + pt.measureText(suffixPrefix);
                float commentY = commentY;
                
                float originalCommentSize = currentPaint.getTextSize();
                currentPaint.setTextSkewX(-0.2f); 
                currentPaint.setTextSize(originalCommentSize * 0.8f); 
                currentPaint.setColor(getValidColor("#606060"));
                cas.drawText(commentPart, commentX, commentY, currentPaint);
                currentPaint.setTextSize(originalCommentSize);
            } else {
                String suffixContent = line.substring(endIndex + 3);
                float suffixX = middleX + middleWidth + reservedSpace;
                float suffixY = middleY;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffixContent, suffixX, suffixY, currentPaint);
            }
        }
        continue;
    }
}


// 判断行中是否包含 "//"
if (line.contains("//")) {
    int commentIndex = line.indexOf("//");
    // 绘制 "//" 之前的文本
    String prefix = line.substring(0, commentIndex);
    float prefixX = startX;
    float prefixY = startY + i * lineHeight;
    currentPaint.setColor(getValidColor(color));
    cas.drawText(prefix, prefixX, prefixY, currentPaint);

    // 获取当前文本大小
    float originalTextSize = currentPaint.getTextSize();

    // 绘制 "//" 及之后的文本
    String commentPart = line.substring(commentIndex + 2); // 跳过"//"符号
    float commentX = prefixX + pt.measureText(prefix);
    float commentY = prefixY;

    // 设置文本倾斜，这里设置倾斜-0.2f
    currentPaint.setTextSkewX(-0.2f); 
    // 缩小文本大小，这里设置为原文本大小的0.8倍
    currentPaint.setTextSize(originalTextSize * 0.8f); 
    currentPaint.setColor(getValidColor("#606060"));
    cas.drawText(commentPart, commentX, commentY, currentPaint);

    // 恢复原来的文本大小
    currentPaint.setTextSize(originalTextSize);
    continue;
}

        // 判断此行是否包含“<居中>文本内容</居中>”这种格式
        if (line.contains("<居中>") && line.contains("</居中>")) {
            int startIndex = line.indexOf("<居中>");
            int endIndex = line.indexOf("</居中>");
            if (startIndex < endIndex) {
                // 绘制 "<居中>" 之前的文本
                String prefix = line.substring(0, startIndex);
                float prefixX = startX;
                // 增加额外间距
                float prefixY = startY + i * lineHeight + lineHeight * 0.05f; 
                currentPaint.setColor(getValidColor(color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);

                // 绘制 "<居中>" 和 "</居中>" 之间的文本
                String middle = line.substring(startIndex + 4, endIndex);
                float middleWidth = pt.measureText(middle);
                // 同样增加额外间距
                float middleY = prefixY + lineHeight * 0.05f; 
                float middleX = startX + (maxWidth - extraWidth - middleWidth) / 2;
                // 获取当前文本大小
                float originalTextSize = currentPaint.getTextSize();
                // 缩放文本大小，这里设置为原文本大小的1.25倍
                currentPaint.setTextSize(originalTextSize * 1.25f);
                currentPaint.setColor(getValidColor(color));
                cas.drawText(middle, middleX, middleY, currentPaint);

                // 绘制 "</居中>" 之后的文本
                String suffix = line.substring(endIndex + 5);
                // 增加额外间距
                float suffixY = middleY + lineHeight * 0.05f; 
                float suffixX = middleX + middleWidth;
                currentPaint.setColor(getValidColor(color));
                cas.drawText(suffix, suffixX, suffixY, currentPaint);
                continue;
            }
        } else {
            //默认使用传入的颜色
            currentPaint.setColor(getValidColor(color));
        }

        float lineX = startX;
        float lineY = startY + i * lineHeight;
        //绘制无特殊符号或格式的文本
        cas.drawText(line, lineX, lineY, currentPaint);
    }
    
    //路径为空 或 未提供路径 时，在默认路径保存为指定文件名
    if ("".equals(path) || path == null) {
        bmptofile2(bmp,AppPath+"/其他/text_image.png");
    } else {
      //按提供的路径+文件名来保存
        bmptofile2(bmp, path);
    }
}


    // 获取有效的颜色值，如果颜色值无效则返回默认颜色 "#000000"
    private int getValidColor(String color)
    {
        try {
            if (color == null || color.isEmpty()) {
                return Color.parseColor("#000000");
            }
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) { 
             //解析失败，也默认返回"#000000"
            return Color.parseColor("#000000");
        }
    }
    
//加载图片文件为Bitmap
private Bitmap loadImageFromPath(String path) {
    try {
        if (path == null || path.isEmpty()) {
            return null;
        }
        
        // 优化内存加载：先获取图片尺寸，再按比例缩放
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        
        // 计算缩放比例（避免内存溢出）
        int maxWidth = 1000; // 最大宽度限制
        int maxHeight = 800; // 最大高度限制
        int scale = 1;
        while (options.outWidth / scale > maxWidth || 
               options.outHeight / scale > maxHeight) {
            scale *= 2;
        }
        
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, options);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

//将Bitmap保存为指定图像文件的方法
public void bmptofile2(Bitmap bmp,String path)
{
  File f= new File(path);
  if(f.exists()){f.delete();}
  if(!f.exists()){f.getParentFile().mkdirs();}
  FileOutputStream fs = new FileOutputStream(path);
  bmp.compress(Bitmap.CompressFormat.JPEG,50,fs); //压缩图片质量
  fs.flush();
}


//获取群名
public String getGroupName(String qun){
for(Object z:getGroupList()){
if(qun.equals(z.GroupUin)){
return z.GroupName;}}
	return "";
}

//判断是否仅包含数字 
public static boolean isLetterDigitOrChinese(String str)
{
  String regex = "[0-9]+";
  return str.matches(regex);
}
 

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

// 下载东西(链接，保存路径)
    private static boolean downloadFile(String urlStr, String localFilePath)
    {
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer))!= -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast("文件下载失败：" + e );
            sendMsg("", MyUin, "文件下载失败：" +e);
            return false;
        }
    }
    
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.*;

HashMap FileMemCache = new HashMap();
public 读(String FilePath)
{
  try
  {
    if(FileMemCache.containsKey(FilePath))
    {
      return FileMemCache.get(FilePath);
    }
    File file = new File(FilePath);
    if(!file.exists())
    {
      file.createNewFile();
    }
    InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
    BufferedReader bf = new BufferedReader(inputReader);
    String Text = "";
    while((str = bf.readLine()) != null)
    {
      Text = Text + "\n" + str;
    }
    if(Text.isEmpty())
    {
      return "";
    }
    FileMemCache.put(FilePath, Text.substring(1));
    return Text.substring(1);
  }
  catch(IOException ioe)
  {
    return "";
  }
}

public String readprop(String file, String name2)
{
  String PropFile = file;
  String text = 读(PropFile);
  Properties props = new Properties();
  props.load(new StringReader(text));
  String name = props.getProperty(name2);
  return name;
}

//来自《简洁群管》
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import android.os.Bundle;
Object app=BaseApplicationImpl.getApplication().getRuntime();
IProfileDataService ProfileData=app.getRuntimeService(IProfileDataService.class);
IProfileProtocolService ProtocolService=app.getRuntimeService(IProfileProtocolService.class);

public Object GetCard(String uin){
ProfileData.onCreate(app);
Object card=ProfileData.getProfileCard(uin,false);
if(card==null||card.iQQLevel==null)
{
Bundle bundle =new Bundle();
bundle.putLong("selfUin",Long.parseLong(MyUin));
bundle.putLong("targetUin",Long.parseLong(uin));
bundle.putInt("comeFromType",12);
ProtocolService.requestProfileCard(bundle);
return null;
}
else return card;
}
ITroopInfoService TroopInfo=app.getRuntimeService(ITroopInfoService.class);
import com.tencent.mobileqq.filemanager.app.FileManagerEngine;
FileManagerEngine file=null;
public boolean upload(String qun,String filepath)
{
if(file==null) file=new FileManagerEngine(BaseApplicationImpl.sApplication.getAppRuntime(MyUin));
    return file.a(filepath,qun,1,1);
}
public String 昵称(String uin){
try{
Object card=GetCard(uin);
if(card.strNick!=null){
return card.strNick;}
else{return "未知";}
}catch(Exception e){Toast("异常"+e);return "未知";}
}
//——————————————————————————

import java.util.Random;
public static int 随机数(int number1, int number2) 
{
        Random random = new Random();
        // 确保 number1 是较小的数，number2 是较大的数
        int min = Math.min(number1, number2);
        int max = Math.max(number1, number2);
        // 生成 [min, max] 之间的随机整数
        return min + random.nextInt(max - min + 1);
}

    private static final long INTELLIGENCE_FACTOR = 114150000; //智力加成系数
    private static final Random RANDOM = new Random(); //随机数生成器
 
   //用于【跨群攻击】、【宠物攻击】、【宠物反击】等指令的伤害计算
    public static long 伤害计算(long attackerAttack, long attackerIntel, long defenderDefense, boolean isOurAttack, float minRatio, float maxRatio) {
        long baseDamage;
        double intelBonus = 1 + (double) attackerIntel / INTELLIGENCE_FACTOR;

        if (attackerAttack >= defenderDefense) {
            baseDamage = attackerAttack - defenderDefense;
            if (isOurAttack) {
                // 生成浮动比例（0.9~1.1等）
                double randomRatio = minRatio + RANDOM.nextDouble() * (maxRatio - minRatio);
                baseDamage = (long) (baseDamage * intelBonus * randomRatio);
            } else {
                baseDamage = (long) (baseDamage * intelBonus);
            }
        } else {
            if (!isOurAttack) {
                // 使用nextDouble()计算随机区间
                long minBase = 8000000;
                long maxBase = 8500000;
                long range = maxBase - minBase + 1; // 区间长度
                // 生成[0, range)的随机数，转换为long后加上minBase
                baseDamage = minBase + (long) (RANDOM.nextDouble() * range);
                baseDamage = (long) (baseDamage * intelBonus); // 应用智力加成
            } else {
                baseDamage = 0;
            }
        }

        // 当伤害为0时，先取攻击属性的千分之一（向下取整），若仍为0则强制返回1
        return baseDamage == 0 ? (attackerAttack / 1000 == 0 ? 1 : attackerAttack / 1000) : baseDamage;
    }
    
    
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

// 颜色列表定义为静态数组
private static final String[] COLOR_ARRAY = {
    "#FF0000", "#0000FF", "#FF00FF", "#808080", "#5E3D50", "#8F77B5", 
    "#7B90D2", "#b7d07a", "#428675", "#1e9eb3", "#45b787", "#29b7cb", 
    "#158bb8", "#61649f", "#f43e06", "#f9a633", "#fbda41", "#5C3317"
};

// 获取随机颜色
public String getRandomColor() {
    return COLOR_ARRAY[RANDOM.nextInt(COLOR_ARRAY.length)];
}

// 创建形状Drawable
public GradientDrawable getShape(String color1, String color2, int strokeWidth, int radius, int alpha, boolean isGradient) {
    GradientDrawable shape = new GradientDrawable();
    shape.setShape(GradientDrawable.RECTANGLE); //设置形状
    
    if (isGradient) {
        // 渐变模式：设置颜色数组
        int[] colors = {
            Color.parseColor(color1),
            Color.parseColor(color2)
        };
        shape.setColors(colors);
        shape.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
    } else {
        // 纯色模式
        shape.setColor(Color.parseColor(color1));
    }
    
    // 设置描边（宽度+颜色）
    shape.setStroke(strokeWidth, Color.parseColor(color2));
    // 设置圆角半径
    shape.setCornerRadius(radius);
    // 设置透明度
    shape.setAlpha(alpha);
    
    return shape;
}


// 自定义Toast显示方法
public void tcToast(String text) {
    // 主线程判断逻辑
    if (Looper.myLooper() == Looper.getMainLooper()) {
        createAndShowToast(text);
    } else {
        // 使用Activity的runOnUiThread确保主线程执行
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                createAndShowToast(text);
            }
        });
    }
}

// 创建并显示Toast
private void createAndShowToast(String text) {
  Context context = GetActivity();
    try {
        // 创建容器布局
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(40, 40, 40, 40);
        // 设置背景（使用随机颜色作为描边）
        linearLayout.setBackground(getShape("#000000", getRandomColor(), 5, 20, 180, false));
        
        // 创建文本控件
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ));
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(14.5f);
        textView.setText(text);
        textView.setLineSpacing(4.0f, 1.0f); //行间距
        
        linearLayout.addView(textView);
        linearLayout.setGravity(Gravity.CENTER);
        
        // 配置Toast
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 40);
        toast.setDuration(Toast.LENGTH_SHORT); //设置Toast提示时长
        // Toast.LENGTH_LONG 约3.5秒
        // Toast.LENGTH_SHORT 约2秒
        toast.setView(linearLayout);
        toast.show();
        
    } catch (Exception e) { //异常时，使用正常的Toast提示方法
        Toast(text);
       // sendMsg("", MyUin, "tcToast方法出现错误:\n" + e.getMessage());
    }
}


    
import java.util.Map;  
import java.util.HashMap;  
import java.util.List;  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.LinkedHashSet;  
import java.util.Comparator;  
import java.util.Collections;
import java.util.Map.Entry;  
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 战榜奖励结算方法（会按已开放【宠物世界】玩法的群列表来发放奖励）
void settleBattleRankRewards() 
{
    String groupListStr = getString("开放群列表", "开放列表");
    String[] groupList = groupListStr.split("、");

    for (String qun : groupList) { // 遍历开放群列表
      //获取该群所有成员账号
      String 成员列表 = getGroupMembersUin(qun);
    
        // 当 群号无效或该群不存在‘我’时，直接跳过
        if (qun.equals("0")||!成员列表.contains(MyUin)) {
          Toast("群[" + qun + "]不存在或本账号不在此群，跳过结算");
            continue;
        }

        // 处理群内玩家数据
        String groupPlayerList = getString(qun + "/玩家列表", "列表");
        
        if (groupPlayerList == null || groupPlayerList.equals("null")) {
            Toast("群[" + qun + "]无玩家数据，跳过战榜结算");
            continue;
        }

        // 解析玩家战力 
        Map playerPowerMap = new HashMap();
        String[] players = groupPlayerList.split("、");
        
        for (String uin : players) {
            long power = 计算战力(qun, uin, 0); // 按“位置_0”来获取战力
            if (power > 0) {
                playerPowerMap.put(uin, power); // 记录玩家战力
            }
        }

        // 战力排序（传统Comparator实现，非Lambda）
        List sortedList = new ArrayList(playerPowerMap.entrySet());
        sortedList.sort(new Comparator() { // 传统排序写法保留
            public int compare(Map.Entry o1, Map.Entry o2) {
                return Long.compare(o2.getValue(), o1.getValue()); // 降序排列
            }
        });

        // 处理前十奖励
        int endIndex = Math.min(sortedList.size(), 10);
        List topTen = sortedList.subList(0, endIndex);
        StringBuilder rewardLog = new StringBuilder(); 
        //↑发送时会用到(Q号会进行加密)
        StringBuilder rewardNewLog = new StringBuilder(); 
        //不对账号加密（后续可以用来查看账号）

        for (int i = 0; i < topTen.size(); i++) {
            Map.Entry entry = topTen.get(i);
            String uin = entry.getKey().toString();
            long power = (Long) entry.getValue();
            int rank = i + 1; // 当前排名
            
            //  积分/金币奖励 
            long addScore, addCoin;
            switch (rank) {
                case 1:  addScore = 1000000; addCoin = 60; break;
                case 2:  addScore = 900000; addCoin = 45; break;
                case 3:  addScore = 800000; addCoin = 40; break;
                case 4:  addScore = 700000; addCoin = 35; break;
                case 5:  addScore = 600000; addCoin = 30; break;
                case 6:  addScore = 500000; addCoin = 25; break;
                case 7:  addScore = 400000; addCoin = 20; break;
                case 8:  addScore = 300000; addCoin = 15; break;
                case 9:  addScore = 200000; addCoin = 10; break;
                case 10: addScore = 100000; addCoin = 5; break;
                default: continue;
            }
            
            // 道具奖励
            String[][] rewardItems;
            String rewardDesc;
            switch (rank) {
                case 1:  
                    rewardItems = new String[][]{{"战神宝箱", "1"}, {"积分夺宝券", "10"}, {"瑶光宝箱", "60"}};
                    rewardDesc = "战神宝箱×1、积分夺宝券×10、瑶光宝箱×60"; 
                    break;
                case 2:  
                    rewardItems = new String[][]{{"瑶光宝箱", "50"}, {"积分夺宝券", "8"}};
                    rewardDesc = "瑶光宝箱×50、积分夺宝券×8"; 
                    break;
                case 3:  
                    rewardItems = new String[][]{{"瑶光宝箱", "40"}, {"积分夺宝券", "5"}};
                    rewardDesc = "瑶光宝箱×40、积分夺宝券×5"; 
                    break;
                case 4:  
                    rewardItems = new String[][]{{"瑶光宝箱", "30"}, {"积分夺宝券", "5"}};
                    rewardDesc = "瑶光宝箱×30、积分夺宝券×5"; 
                    break;
                case 5:  
                    rewardItems = new String[][]{{"瑶光宝箱", "25"}, {"积分夺宝券", "3"}};
                    rewardDesc = "瑶光宝箱×25、积分夺宝券×3"; 
                    break;
                case 6:  
                    rewardItems = new String[][]{{"瑶光宝箱", "20"}, {"积分夺宝券", "3"}};
                    rewardDesc = "瑶光宝箱×20、积分夺宝券×3"; 
                    break;
                case 7:  
                    rewardItems = new String[][]{{"瑶光宝箱", "15"}, {"积分夺宝券", "3"}};
                    rewardDesc = "瑶光宝箱×15、积分夺宝券×3"; 
                    break;
                case 8:  
                    rewardItems = new String[][]{{"瑶光宝箱", "10"}, {"积分夺宝券", "2"}};
                    rewardDesc = "瑶光宝箱×10、积分夺宝券×2"; 
                    break;
                case 9:  
                    rewardItems = new String[][]{{"瑶光宝箱", "5"}, {"积分夺宝券", "2"}};
                    rewardDesc = "瑶光宝箱×5、积分夺宝券×2"; 
                    break;
                case 10:  
                    rewardItems = new String[][]{{"瑶光宝箱", "3"}, {"积分夺宝券", "1"}};
                    rewardDesc = "瑶光宝箱×3、积分夺宝券×1"; 
                    break;
                default: continue;
            }
            
            // 整合奖励日志（账号会加密）
            rewardLog.append("【第").append(rank).append("名】：").append(玩家名(qun,uin)+" ("+maskAccount(uin)+")")
                .append("\n「当前战力」：*").append(数值转(power)+"*")
                .append("\n「货币奖励」：+").append(addScore).append("积分、 +").append(addCoin).append("金币")
                .append("\n「道具奖励」：").append(rewardDesc).append("\n<填充>\n");
                
            // 整合奖励日志（账号不进行加密，方便后续【战榜+序号】指令查阅）
            rewardNewLog.append("【第").append(rank).append("名】：").append(玩家名(qun,uin)+" (QQ："+uin+")")
                .append("\n「当前战力」：*").append(数值转(power)+"*")
                .append("\n「货币奖励」：+").append(addScore).append("积分、 +").append(addCoin).append("金币")
                .append("\n「道具奖励」：").append(rewardDesc).append("\n<填充>\n");
            
            // 处理资产（积分/金币）
            String assetKey = qun + "/" + uin + "/我的资产";
            long currentScore = 文转(getString(assetKey, "积分"));
            long currentCoin = 文转(getString(assetKey, "金币"));
            putString(assetKey, "积分", 转文(currentScore + addScore));
            putString(assetKey, "金币", 转文(currentCoin + addCoin));
            
            // 处理背包道具
            String prefix = qun + "/" + uin + "/我的背包";
            String backpack = getString(prefix, "道具列表");
            
            // 若backpack为null，初始化为空字符串
            if (backpack == null) {
                backpack = ""; // 初始化空背包状态
            }
            
            for (String[] item : rewardItems) {
                String itemName = item[0];
                long itemCount = 文转(item[1]);
                
                if (!backpack.contains(itemName)) {
                    String newBackpack = backpack.isEmpty() ? itemName : backpack + "、" + itemName;
                    putString(prefix, "道具列表", newBackpack);
                    backpack = newBackpack; // 更新当前背包状态
                }
                
                long current = 文转(getString(prefix, itemName));
                putString(prefix, itemName, 转文(current + itemCount));
            }
        }

        // 去除最后多余换行符
        if (rewardLog.length() > 0) {
            rewardLog.deleteCharAt(rewardLog.length() - 1);
        }
        if (rewardNewLog.length() > 0) {
            rewardNewLog.deleteCharAt(rewardNewLog.length() - 1);
        }

        int count = players.length; //该群玩家人数
        String NewLog = rewardLog.toString(); //已拼接好的奖励记录
        String msg = "// 群聊："+getGroupName(qun)+"（本群有"+count+"名玩家) \n<分割>战榜☼奖励</分割>\n" + NewLog + "\n⏰Time：" + getTodayDate(2)+"\n//  可发送〖双榜记录〗来查看近期记录";
        putString("双榜结算记录/" + getTodayDate(1) + "/" + qun + "_战榜奖励记录", "奖励记录", rewardNewLog.toString());  //记录当天奖励详情（后续可能会调用）
        toImg(msg, "", 0.5, 0.01, 32, AppPath + "/缓存/其他/" + qun + "_战榜奖励.png",true); //绘制为图片
        发送(qun, "【战榜结算通知】\n本群战榜奖励已发放！[PicUrl=" + AppPath + "/缓存/其他/" + qun + "_战榜奖励.png] 可发送『我的背包』查看",false); //奖励发放通知
        Toast("群[" + qun + "]战榜奖励结算完成");
    }
}


// 神榜奖励结算方法（这里的“GroupUin”是指定群号，将会在这个指定群号发送消息）
void settleGodRankRewards(String GroupUin) 
{
    String openGroups = getString("开放群列表", "开放列表"); // 获取开放群列表
    if (openGroups == null || openGroups.equals("null")) {
        Toast("无开放群数据，跳过神榜结算");
        return;
    }
    
    // 收集全服玩家最高战力
    Map playerMaxPower = new HashMap();
    Map playerMaxGroup = new HashMap();
    String[] groups = openGroups.split("、");
    for (String group : groups) {
        String groupPlayers = getString(group + "/玩家列表", "列表");
        if (groupPlayers != null) {
            String[] players = groupPlayers.split("、");
            for (String uin : players) {
                long power = 计算战力(group, uin, 0); // 获取该玩家当前宠物战力
                if (power > 0) {
                    if (!playerMaxPower.containsKey(uin) || power > playerMaxPower.get(uin)) {
                        playerMaxPower.put(uin, power);
                        playerMaxGroup.put(uin, group);
                    }
                }
            }
        }
    }
    
    // 排序全服前十
    List sortedList = new ArrayList(playerMaxPower.entrySet());
    sortedList.sort(new Comparator() {
        public int compare(Entry o1, Entry o2) {
            return Long.compare(o2.getValue(), o1.getValue()); // 降序
        }
    });
    List topTen = sortedList.subList(0, Math.min(sortedList.size(), 10));
    
    // 拼接奖励日志（整合排版） & 发放奖励
    StringBuilder rewardLog = new StringBuilder(); //账号加密
    StringBuilder rewardNewLog = new StringBuilder(); //不对账号进行加密
    for (int i = 0; i < topTen.size(); i++) {
        Map.Entry entry = topTen.get(i);
        String uin = entry.getKey().toString();
        long power = (Long) entry.getValue();
        int rank = i + 1;
        String qun = playerMaxGroup.get(uin).toString();
        String reward = getRankReward(rank); // 返回此排名对应奖励
        
        // -------------------- 奖励发放逻辑 --------------------
        String[] rewardItems = reward.split(" "); // 按空格拆分奖励项
        for (String item : rewardItems) {
            String[] parts = item.split("×"); // 拆分道具和数量
            if (parts.length != 2) continue; // 过滤无效格式
            
            String propName = parts[0];
            long propCount = 文转(parts[1]);
            String assetKey = qun + "/" + uin + "/我的资产";
            String bagKey = qun + "/" + uin + "/我的背包";
            
            // 处理积分和金币
            if ("积分".equals(propName)) {
                long current = 文转(getString(assetKey, "积分"));
                putString(assetKey, "积分", 转文(current + propCount));
            } else if ("金币".equals(propName)) {
                long current = 文转(getString(assetKey, "金币"));
                putString(assetKey, "金币", 转文(current + propCount));
            } else { // 处理背包道具（使用原有背包逻辑）
                String bag = getString(bagKey, "道具列表");
                if (bag != null && bag.contains(propName)) {
                    long count = 文转(getString(bagKey, propName));
                    putString(bagKey, propName, 转文(count + propCount));
                } else {
                    String newBag = (bag != null ? bag + "、" : "") + propName;
                    putString(bagKey, "道具列表", newBag);
                    putString(bagKey, propName, 转文(propCount));
                }
            }
        }
        // -------------------- 奖励发放逻辑结束 --------------------
        
        // 拼接奖励日志（账号加密）
        rewardLog.append("【全服第").append(rank).append("名】：")
         .append(玩家名(qun, uin) + " (" + maskAccount(uin) + ")")
         .append("\n「最高战力」：*").append(数值转(power)+"*")
         .append("\n「基础奖励」：");
         
         // 拆分奖励并分组（前两项为基础奖励，后续为道具奖励）
         String[] rewardItems = reward.split(" ");
         if (rewardItems.length >= 2) {
            // 基础奖励（积分、金币）
           rewardLog.append(rewardItems[0]).append(" ").append(rewardItems[1]).append("\n");
           // 道具奖励（从第3项开始）
           if (rewardItems.length > 2) {
              rewardLog.append("「道具奖励」：");
              for (int j = 2; j < rewardItems.length; j++) {
                  rewardLog.append(rewardItems[j]);
                if (j < rewardItems.length - 1) {
                    rewardLog.append(" ");
                }
              }
           }
         } else {
            rewardLog.append(reward).append("\n");
         }
          rewardLog.append("\n<填充>\n");
          
          // 拼接奖励日志（账号不加密）
          rewardNewLog.append("【全服第").append(rank).append("名】：")
          .append(玩家名(qun, uin) + " (QQ：" + uin + ")")
          .append("\n「最高战力」：*").append(数值转(power)+"*")
          .append("\n「基础奖励」：");
          
          // 同理处理不加密日志的奖励分组
          if (rewardItems.length >= 2) {
             rewardNewLog.append(rewardItems[0]).append(" ").append(rewardItems[1]).append("\n");
             if (rewardItems.length > 2) {
                rewardNewLog.append("「道具奖励」：");
                for (int j = 2; j < rewardItems.length; j++) {
                  rewardNewLog.append(rewardItems[j]);
                  if (j < rewardItems.length - 1) {
                    rewardNewLog.append(" ");
                  }
                }
             }
          } else {
             rewardNewLog.append(reward).append("\n");
          }
            rewardNewLog.append("\n<填充>\n");

    }
    
    // 去除最后多余换行符
    if (rewardLog.length() > 0) {
        rewardLog.deleteCharAt(rewardLog.length() - 1);
    }
    if (rewardNewLog.length() > 0) {
        rewardNewLog.deleteCharAt(rewardNewLog.length() - 1);
    }
    
    String newLog = rewardLog.toString();
    String today = getTodayDate(1); // 格式如"2025-05-19"
    String logKey = "双榜结算记录/" + today + "/神榜奖励记录";
    putString(logKey, "奖励记录", rewardNewLog.toString()); 
    
    // 绘制图片并发送（逻辑与战榜几乎一致）
    String 玩家列表 = getString("玩家总列表", "总列表");
    int count = 玩家列表.split("、").length; //玩家总人数
    String msg = "// 全服共 "+count+" 名玩家（注：如果同账号在多群发展也只记录一次） \n<分割>神榜☀️奖励</分割>\n" + newLog + "\n⏰Time：" + getTodayDate(2)+"\n//可发送〖双榜记录〗来查看近期奖励结算记录";
    String imgPath = AppPath + "/缓存/其他/神榜奖励.png";
    toImg(msg, "", 0.5, 0.01, 32, imgPath,true); // 生成神榜奖励图片
    Toast("全服神榜奖励结算完成"); //提示，可换成 QQToast("全服神榜奖励结算完成",2);
    
    //发送消息到指令触发群
    String groupListStr = getString("开放群列表", "开放列表");
    int count = groupListStr.split("、").length; //已开放群数量
    sendMsg(GroupUin,"","【神榜结算通知】\n全服神榜奖励已发放！[PicUrl=" + imgPath + "]");
}


//神榜排名奖励（要有空格）
private String getRankReward(long rank) {
    String rawReward;
    switch ((int) rank) {
        case 1: rawReward = "积分×10000000 金币×500 涅槃×3 宠物定制卡×2 蜂蜜果糖×5 战神宝箱×3 金币夺宝券×20 瑶光宝箱×100"; break;
        case 2: rawReward = "积分×8500000 金币×450 涅槃×2 宠物定制卡×1 金币夺宝券×15 蜂蜜果糖×4 瑶光宝箱×80"; break;
        case 3: rawReward = "积分×7500000 金币×350 涅槃×2 宠物定制卡×1 金币夺宝券×10 蜂蜜果糖×3 瑶光宝箱×70"; break;
        case 4: rawReward = "积分×6500000 金币×300 涅槃×1 金币夺宝券×10 蜂蜜果糖×2 瑶光宝箱×60"; break;
        case 5: rawReward = "积分×6500000 金币×300 涅槃×1 金币夺宝券×8 蜂蜜果糖×2 瑶光宝箱×60"; break;
        case 6: rawReward = "积分×5500000 金币×250 涅槃×1 金币夺宝券×8 蜂蜜果糖×1 瑶光宝箱×50"; break;
        case 7: rawReward = "积分×4500000 金币×200 金币夺宝券×5 蜂蜜果糖×1 瑶光宝箱×50"; break;
        case 8: rawReward = "积分×3500000 金币×150 金币夺宝券×5 薄荷糖×5 瑶光宝箱×40"; break;
        case 9: rawReward = "积分×2500000 金币×100 金币夺宝券×2 薄荷糖×3 瑶光宝箱×30"; break;
        case 10: rawReward = "积分×1500000 金币×50 积分夺宝券×3 薄荷糖×1 瑶光宝箱×10"; break;
        default: return "无奖励";
    }
    return rawReward;
}

    // 暂存 战榜与神榜 奖励记录日期
     List RewardRecord = new ArrayList();


//加载时，删除图片缓存…
delAllFile(AppPath+"/缓存/宠物/");
delAllFile(AppPath+"/缓存/头像/");
delAllFile(AppPath+"/缓存/其他/");