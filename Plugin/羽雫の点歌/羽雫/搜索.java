
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.view.*;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Transformation;
boolean sign = false;
String player_audiourl="";
String player_song="";
String player_singer="";
public List Music_list = new ArrayList();
public List Music_mid = new ArrayList();

public List operate = new ArrayList();//操作
public List platform = new ArrayList();//平台
public List player = new ArrayList();//播放器
operate.add("查看歌曲信息");
operate.add("查看歌曲歌词");
operate.add("发送卡片");
operate.add("发送语音");
operate.add("发送文件");
operate.add("发送链接");
operate.add("发送歌词");
operate.add("播放歌曲");

platform.add("QQ音乐");
platform.add("网易云音乐");
platform.add("酷我音乐");

player.add("搜索音乐");
player.add("当前播放");
player.add("播放历史");
player.add("开始/暂停播放");
player.add("结束当前播放");

addItem("播放器","播放器");

import android.media.MediaPlayer;
import android.util.Log;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void playMusic(String path) {
        // 如果当前正在播放音频，先停止播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                sign=false;
            }
            mediaPlayer.release(); // 释放资源
            mediaPlayer = null;
        }

        // 初始化 MediaPlayer
        mediaPlayer = new MediaPlayer();

        try {
            // 设置数据源
            mediaPlayer.setDataSource(path);

            // 准备播放
            mediaPlayer.prepare();

            // 开始播放
            mediaPlayer.start();

            Toast("开始播放:"+player_song+"----"+player_singer);
            sign=true;

            // 设置播放完成的监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    sign=false;
                }
                mediaPlayer.release(); // 释放资源
                mediaPlayer = null;
                Toast("播放已完毕!");
                }
            });

        } catch (Exception e) {
            Toast("Error setting data source: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                sign=false;
            }
            mediaPlayer.release(); // 释放资源
            mediaPlayer = null;
            player_audiourl="";
            player_song="";
            player_singer="";
            Toast("已结束播放");
        }else{
        Toast("有没有可能他早结束了");
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            sign=false;
            Toast("已暂停");
            mediaPlayer.pause();
        }else{
        Toast("好像早就暂停了");
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
        sign=true;
            Toast("已继续");
            mediaPlayer.start();
        }else{
        Toast("。。。。");
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}

public void 播放器(String qun,String qq,int type)
{
if(type == 2) 播放器(type,qun,"");
else if(type == 1) 播放器(type,qq,"");
}
AudioPlayer audioPlayer = new AudioPlayer();
public void 播放器(int type, String qun, String Name) {
    Activity activity = getNowActivity();
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final CharSequence[] items = player.toArray(new CharSequence[player.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("播放器～～～");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // 根据选择执行对应的操作
                    switch (which) {
                        case 0:
                            搜索音乐(type, qun, "");
                            break;
                        case 1:
                            当前();
                            break;
                        case 2:
                            历史(type,qun);
                            break;
                        case 3:
                            播放();
                            break;
                        case 4:
                            结束();
                            break;
                    }
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            UI(dialog);
            dialog.show();
        }
    });
}


public void 结束(){
audioPlayer.stopMusic();
}

public void 当前(){
if (player_audiourl.trim().isEmpty() && player_song.trim().isEmpty() && player_singer.trim().isEmpty()) Toast("当前暂无歌曲准备播放");
else if(sign) Toast("当前正在播放:"+player_song+"----"+player_singer);
else Toast("当前准备播放:"+player_song+"----"+player_singer);
}

public void 播放(){
if(sign) audioPlayer.pauseMusic();
else audioPlayer.resumeMusic();
}

public void 搜索音乐(int type, String qun, String Name) {
    String gm = 读("播放器", "歌名", "");
    Activity activity = getNowActivity();

    if (activity != null) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                // 创建对话框
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                // 动态读取当前选择的平台
                String currentPlatform = 读("播放器", "平台", "QQ"); // 假设默认是QQ
                dialogBuilder.setTitle("歌曲搜索" + currentPlatform);

                // 创建布局
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(40, 40, 40, 40);

                // 创建输入框并设置圆角背景
                EditText editText = new EditText(activity);
                editText.setHint("请输入歌曲名");
                editText.setText(gm);
                editText.setPadding(10, 10, 10, 10);

                // 设置输入框的圆角背景
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#E0E0E0")); // 设置背景颜色
                gradientDrawable.setCornerRadius(15); // 设置圆角半径
                gradientDrawable.setStroke(1, Color.parseColor("#9E9E9E")); // 设置边框颜色和宽度
                editText.setBackground(gradientDrawable);

                // 添加输入框到布局
                layout.addView(editText);

                // 设置对话框的布局
                dialogBuilder.setView(layout);

                // 设置“确定”按钮
                dialogBuilder.setPositiveButton("确定", null); // 不关闭对话框

                // 设置“选择平台”按钮
                dialogBuilder.setNegativeButton("选择平台", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        选择平台(type,qun);
                    }
                });

                // 显示对话框
                final AlertDialog dialog = dialogBuilder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface d) {
                        // 获取“确定”按钮并设置自定义点击事件
                        Button positiveButton = ((AlertDialog) d).getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String inputText = editText.getText().toString().trim();
                                if (inputText.isEmpty()) {
                                    Toast("请输入有效的值");
                                    return;
                                }
                                ts3("正在获取..");
                                new Thread(new Runnable() {
                                    public void run() {
                                        写("播放器", "歌名", inputText);
                                        选择音乐(type, qun, inputText);
                                    }
                                }).start();
                                dialog.dismiss(); // 关闭对话框
                            }
                        });
                    }
                });

                dialog.show();

                // 美化对话框
                UI(dialog);
            }
        });
    }
}

public boolean QQ_list(String yy) {
    // 清空列表
    Music_list.clear();
    Music_mid.clear();

    // 构建搜索 URL
    String url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?g_tk=5381&p=1&n=60&w=" + URLEncoder.encode(yy, "UTF-8") + "&format=json";

    // 发起 HTTP GET 请求
    String result2 = httpget(url, "");
    // 检查返回结果是否为空或包含错误信息
    if (result2 == null || result2.isEmpty()) {
        return false;
    }

    try {
        JSONObject json = new JSONObject(result2);
        JSONObject json_data = json.getJSONObject("data");
        JSONObject json_song = json_data.getJSONObject("song");
        JSONArray json_list = json_song.getJSONArray("list");

        // 处理歌曲列表
        for (int o = 0; o < json_list.length(); o++) {
            JSONObject song = json_list.getJSONObject(o);
            String songName = song.getString("songname");
            String songMid = song.optString("songmid", "");
            JSONArray singers = song.getJSONArray("singer");
            StringBuilder singerNames = new StringBuilder();
            for (int h = 0; h < singers.length(); h++) {
                JSONObject singer = singers.getJSONObject(h);
                singerNames.append(singer.getString("name"));
                if (h < singers.length() - 1) {
                    singerNames.append("/");
                }
            }
            String songInfo = songName + " —— " + singerNames.toString();
            if (songMid.isEmpty()) {
                songInfo += " (无音源)";
            }

            // 将歌曲信息和 songmid 添加到 Music_list 和 Music_mid
            Music_list.add(songInfo);
            Music_mid.add(songMid);
        }
        return true; // 搜索成功
    } catch (Exception e) {
        // 记录日志
        日志(年月日() + " " + 时分秒() + "\nJava报错,QQ音乐搜索接口报错\n" + e);
        return false; // 搜索失败
    }
}
    public boolean WY_list(String yy) {
        // 清空列表
        Music_list.clear();
        Music_mid.clear();

        // 构建搜索 URL，添加时间戳避免缓存问题
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "https://music.163.com/api/search/get/web?s=" + URLEncoder.encode(yy, "UTF-8") + "&type=1&limit=60&timestamp=" + timestamp;

        // 发起 HTTP GET 请求
        String result2 = httpget(url, "");

        // 检查返回结果是否为空或包含错误信息
        if (result2 == null || result2.isEmpty()) {
            return false;
        }

        try {
            JSONObject json = new JSONObject(result2);
            if (json.has("code") && json.getInt("code") != 200) {
                return false;
            }

            JSONObject result = json.getJSONObject("result");
            JSONArray songs = result.getJSONArray("songs");

            // 处理歌曲列表
            for (int o = 0; o < songs.length(); o++) {
                JSONObject song = songs.getJSONObject(o);
                String songName = song.getString("name");
                String songId = song.optString("id", "");
                JSONArray artists = song.getJSONArray("artists");
                StringBuilder artistNames = new StringBuilder();
                for (int h = 0; h < artists.length(); h++) {
                    JSONObject artist = artists.getJSONObject(h);
                    artistNames.append(artist.getString("name"));
                    if (h < artists.length() - 1) {
                        artistNames.append("/");
                    }
                }
                String songInfo = songName + " —— " + artistNames.toString();

                // 将歌曲信息和 songId 添加到 Music_list 和 Music_mid
                Music_list.add(songInfo);
                Music_mid.add(songId);
            }
            return true; // 搜索成功
        } catch (Exception e) {
            // 记录日志
            日志(年月日() + " " + 时分秒() + "\nJava报错,网易云音乐搜索接口报错\n" + e);
            return false; // 搜索失败
        }
    }
public boolean KW_list(String yy) {
    // 清空列表
    Music_list.clear();
    Music_mid.clear();

    // 构建搜索 URL
    String url = "http://search.kuwo.cn/r.s?client=kt&all=" + URLEncoder.encode(yy, "UTF-8") + "&pn=0&rn=60&ver=kwplayer_ar_99.99.99.99&vipver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1";

    // 发起 HTTP GET 请求
    String result2 = httpget(url, "");
    // 检查返回结果是否为空或包含错误信息
    if (result2 == null || result2.isEmpty()) {
        return false;
    }

    try {
        JSONObject json = new JSONObject(result2);
        JSONArray abslist = json.getJSONArray("abslist");

        // 处理歌曲列表
        for (int o = 0; o < abslist.length(); o++) {
            JSONObject song = abslist.getJSONObject(o);
            String songName = song.getString("SONGNAME");
            String songMid = song.getString("DC_TARGETID"); // 酷我音乐的歌曲唯一标识符
            String artistName = song.getString("ARTIST");

            // 构造歌曲信息
            String songInfo = songName + " —— " + artistName;
            if (songMid.isEmpty()) {
                songInfo += " (无音源)";
            }

            // 将歌曲信息和 songmid 添加到 Music_list 和 Music_mid
            Music_list.add(songInfo);
            Music_mid.add(songMid);
        }
        return true; // 搜索成功
    } catch (Exception e) {
        // 记录日志
        日志(年月日() + " " + 时分秒() + "\nJava报错,酷我音乐搜索接口报错\n" + e);
        return false; // 搜索失败
    }
}

public void 选择平台(int type, String qun) {
    Activity activity = getNowActivity();
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final CharSequence[] items = platform.toArray(new CharSequence[platform.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选择平台");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // QQ
                            写("播放器", "平台", "QQ");
                            搜索音乐(type,qun,"");
                            break;
                        case 1: // 网易
                            写("播放器", "平台", "网易");
                            搜索音乐(type,qun,"");
                            break;
                         case 2: // 酷我
                            写("播放器", "平台", "酷我");
                            搜索音乐(type,qun,"");
                            break;
                    }
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            UI(dialog);
            dialog.show();
        }
    });
}

public void 选择音乐(int type, String qun, String yy) {
    String currentPlatform = 读("播放器", "平台", "QQ");
    if (currentPlatform.equals("QQ")) QQ_list(yy);
    else if (currentPlatform.equals("网易")) WY_list(yy);
    else if (currentPlatform.equals("酷我")) KW_list(yy);
    Activity activity = getNowActivity();
    OK=false;
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final CharSequence[] items = Music_list.toArray(new CharSequence[Music_list.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选择音乐");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String mid = Music_mid.get(which);
                    if (!mid.isEmpty()) {
                        new Thread(new Runnable() {
                            public void run() {
                                // 传递对话框作为参数，以便稍后恢复
                                final AlertDialog parentDialog = (AlertDialog) dialog;
                                parentDialog.dismiss(); // 先关闭选择音乐对话框

                                操作音乐(type, qun, mid, parentDialog,currentPlatform); // 将对话框传递给操作音乐方法
                            }
                        }).start();
                    }
                }
            });

            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            UI(dialog);
            dialog.show();
        }
    });
}

   public void 操作音乐(int type, String qun, String mid, AlertDialog parentDialog, String currentPlatform) {
    Activity activity = getNowActivity();
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final CharSequence[] items = operate.toArray(new CharSequence[operate.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选择操作");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final AlertDialog parentDialog = (AlertDialog) dialog;
                    音乐解析(type, qun, mid, which, parentDialog, currentPlatform);
                    dialog.dismiss(); // 关闭操作音乐对话框
//                    parentDialog.show(); // 恢复显示上级对话框
                }
            });

            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() { // 添加返回按钮
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); // 关闭操作音乐对话框
                    parentDialog.show(); // 恢复显示上级对话框
                }
            });

            AlertDialog dialog = builder.create();
            UI(dialog);
            dialog.show();
        }
    });
}
 
public void 音乐解析(int type,String qun,String mid,int which, AlertDialog parentDialog, String currentPlatform) {
new Thread(new Runnable() {
public void run() {
switch (which) {
    case 0://查看信息
        ts3("正在获取....");
        查看信息(mid,parentDialog,currentPlatform);
        break;
    case 1://查看歌词
        ts3("正在获取....");
        查看歌词(mid,parentDialog,currentPlatform);
        break;
    case 2:
        ts3("正在发送....");
        发送卡片(type,qun,mid,currentPlatform,"");
        Toast("发送成功");
        break;
    case 3:
        ts3("正在发送....");
        发送语音(type,qun,mid,currentPlatform,"");
        Toast("发送成功");
        break;
    case 4:
        ts3("正在发送....");
        发送文件(type,qun,mid,currentPlatform,"");
        Toast("发送成功");
        break;
    case 5:
        ts3("正在发送....");
        发送链接(type,qun,mid,currentPlatform,"");
        Toast("发送成功");
        break;
    case 6:
        ts3("正在发送....");
        发送歌词(type,qun,mid,currentPlatform);
        Toast("发送成功");
        break;
    case 7:
        ts3("正在尝试播放....");
        playmusic(mid,currentPlatform);
        break;
}
}}).start();
}

public void playmusic(String mid, String currentPlatform) {
try{
List history = 读("播放器", "历史记录", new ArrayList());
List history_mid = 读("播放器", "历史mid", new ArrayList());
String mids;
if(currentPlatform.equals("QQ")){
mids="QQ"+mid;
String quality = "";
if(quality.isEmpty()) quality=qqyz_yy+"";
String jiexii = QQ_analysis(mid,quality);
JSONObject jiexiObj = new JSONObject(jiexii);
player_song = jiexiObj.getString("song");
player_singer = jiexiObj.getString("singer");
player_audiourl = jiexiObj.getString("url");
}else if(currentPlatform.equals("网易")){
mids="网易"+mid;
String quality = "";
if(quality.isEmpty()) quality=wyyz_yy+"";
String jiexii = WY_analysis(mid,quality);
JSONObject jiexiObj = new JSONObject(jiexii);
player_song = jiexiObj.getString("song");
player_singer = jiexiObj.getString("singer");
player_audiourl = jiexiObj.getString("url");
}else if(currentPlatform.equals("酷我")){
mids="酷我"+mid;
String quality = "";
if(quality.isEmpty()) quality=kwyz_yy+"";
String jiexii = KW_analysis(mid,quality);
JSONObject jiexiObj = new JSONObject(jiexii);
player_song = jiexiObj.getString("song");
player_singer = jiexiObj.getString("singer");
player_audiourl = jiexiObj.getString("url");
}
audioPlayer.playMusic(player_audiourl);
OK=false;
if(history_mid != null && !history_mid.contains("QQ" + mid) && !history_mid.contains("网易" + mid)&& !history_mid.contains("酷我" + mid)) {
if(currentPlatform.equals("QQ")){
history.add(player_song+"------"+player_singer+"---QQ音乐");
history_mid.add(mids);
}else if(currentPlatform.equals("网易")){
history.add(player_song+"------"+player_singer+"---网易云音乐");
history_mid.add(mids);
}else if(currentPlatform.equals("酷我")){
history.add(player_song+"------"+player_singer+"---酷我音乐");
history_mid.add(mids);
}
写("播放器", "历史记录",(List) history);
写("播放器", "历史mid",(List) history_mid);
}
}catch(e){
Toast(e);
OK=false;
}
}

public void 查看歌词(String mid, AlertDialog parentDialog, String currentPlatform) {
if(currentPlatform.equals("QQ")){
String lyrics =QQ_lyrics(mid);
ts("查看歌曲歌词",lyrics,parentDialog);
}else if(currentPlatform.equals("网易")){
String lyrics =WY_lyrics(mid);
ts("查看歌曲歌词",lyrics,parentDialog);
}else if(currentPlatform.equals("酷我")){
String lyrics =KW_lyrics(mid);
ts("查看歌曲歌词",lyrics,parentDialog);
}
}

public void 查看信息(String mid, AlertDialog parentDialog, String currentPlatform) {
try{
if(currentPlatform.equals("QQ")){
String quality = "";
if(quality.isEmpty()) quality=qqyz_url+"";
String jiexii = QQ_analysis(mid,quality);
JSONObject jiexiObj = new JSONObject(jiexii);
String song = jiexiObj.getString("song");
String singer = jiexiObj.getString("singer");
String url = jiexiObj.getString("url");
String mid = jiexiObj.getString("mid");
String subtitle = jiexiObj.getString("subtitle");
String timee = jiexiObj.getString("time");
String interval = jiexiObj.getString("interval");
String link = jiexiObj.getString("link");
String quality = jiexiObj.getString("quality");
String size = jiexiObj.getString("size");
String api = jiexiObj.getString("API");
String menu = "来自QQ音乐("+api+")"+
               "\n歌名: "+song+
               "\n歌手: "+singer+
               "\n歌曲mid: "+mid+
               "\n歌曲小标题: "+subtitle+
               "\n发布时间: "+timee+
               "\n歌曲时长: "+interval+
               "\n歌曲页面: "+link+
               "\n音质: "+quality+
               "\n歌曲大小: "+size+
               "\n播放链接: "+url;
ts("查看歌曲信息",menu,parentDialog);
}else if(currentPlatform.equals("网易")){
String quality = "";
if(quality.isEmpty()) quality=wyyz_url+"";
String jiexii = WY_analysis(mid,quality);
JSONObject jiexi = new JSONObject(jiexii);
String song=jiexi.get("song");
String singer=jiexi.get("singer");
String url=jiexi.get("url");
String mid=jiexi.get("id").toString();
String interval=jiexi.get("interval");
String link=jiexi.get("link");
String quality=jiexi.get("quality");
String size=jiexi.get("size");
String api=jiexi.get("API");
String menu = "来自网易云音乐("+api+")"+
               "\n歌名: "+song+
               "\n歌手: "+singer+
               "\n歌曲id: "+mid+
               "\n歌曲时长: "+interval+
               "\n歌曲页面: "+link+
               "\n音质: "+quality+
               "\n歌曲大小: "+size+
               "\n播放链接: "+url;
ts("查看歌曲信息",menu,parentDialog);
}else if(currentPlatform.equals("酷我")){
String quality = "";
if(quality.isEmpty()) quality=kwyz_url+"";
String jiexii = KW_analysis(mid,quality);
JSONObject jiexi = new JSONObject(jiexii);
String song=jiexi.get("song");
String pic=jiexi.get("cover");
String singer=jiexi.get("singer");
String url=jiexi.get("url");
String mid=jiexi.get("id").toString();
String interval=jiexi.get("interval");
String link=jiexi.get("link");
String quality=jiexi.get("quality");
String size=jiexi.get("size");
String api=jiexi.get("API");
String path = 羽雫+"图片/"+time+".png";
String menu = "来自酷我音乐("+api+")"+
               "\n歌名: "+song+
               "\n歌手: "+singer+
               "\n歌曲id: "+mid+
               "\n歌曲时长: "+interval+
               "\n歌曲页面: "+link+
               "\n音质: "+quality+
               "\n歌曲大小: "+size+
               "\n播放链接: "+url;
ts("查看歌曲信息",menu,parentDialog);
}
}catch(e){
ts("提示","获取失败!",parentDialog);
}
}
public void 历史(int type, String qun) {
    Activity activity = getNowActivity();
    // 读取历史记录和历史mid
    try {
        List history = 读("播放器", "历史记录", new ArrayList());
        List history_mid = 读("播放器", "历史mid", new ArrayList());

        // 在主线程中运行对话框逻辑
        activity.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("历史记录");
                builder.setItems(history.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which >= 0 && which < history_mid.size()) {
                            String mid = history_mid.get(which);

                            // 检查 mid 是否有效
                            if (mid == null || mid.isEmpty()) {
                                Toast("无效的 mid");
                                return;
                            }

                            ts3("正在尝试播放......");
                                new Thread(new Runnable() {
                                    public void run() {
                                    final AlertDialog parentDialog = (AlertDialog) dialog;
                                    //playmusic(mid);
                                    String currentPlatform = mid.substring(0, 2);
                                    mid = mid.substring(2);
                                    操作音乐(type, qun, mid, parentDialog,currentPlatform);
                                    OK=false;
                                    }
                                }).start();
                        }
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                UI(dialog);
                dialog.show();
            }
        });
    } catch (Exception e) {
        Toast("读取历史记录失败: " + e.getMessage());
        return;
    }
}