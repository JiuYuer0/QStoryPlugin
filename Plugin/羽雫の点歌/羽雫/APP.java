import com.tencent.common.app.BaseApplicationImpl;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import com.tencent.mobileqq.forward.ForwardSDKB77Sender;
import com.tencent.mobileqq.structmsg.AbsShareMsg;
import com.tencent.mobileqq.structmsg.StructMsgForAudioShare;
import com.tencent.mobileqq.structmsg.StructMsgForGeneralShare;
import com.tencent.mobileqq.structmsg.StructMsgForImageShare;
import com.tencent.mobileqq.troop.roamsetting.api.IRoamSettingService;
Object app = BaseApplicationImpl.getApplication().getRuntime();
//String qq = app.getCurrentUin();
PackageManager pm = context.getPackageManager();
ApplicationInfo sAppInfo = pm.getApplicationInfo(包名,PackageManager.GET_META_DATA);
String UUID = sAppInfo.metaData.get("com.tencent.rdm.uuid");
String Version_Code = UUID.substring(0,UUID.indexOf("_"));
int QQ_version=Integer.parseInt(Version_Code);

import com.tencent.mobileqq.app.BaseActivity;
BaseActivity activity;
while(activity==null){
activity=BaseActivity.sTopActivity;
}

import com.tencent.mobileqq.troop.api.ITroopInfoService;
ITroopInfoService TroopInfo = app.getRuntimeService(ITroopInfoService.class);
public String quncode(String qun) {
String code= TroopInfo.getTroopCodeByTroopUin(qun);
if(code==null||code.equals("")) code=qun;
return code;
}
public String qunuin(String code) {
String qun= TroopInfo.getTroopUinByTroopCode(code);
if(qun==null||qun.equals("")) qun=code;
return qun;
}

public void qqsendJSON(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 102021671);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "com.tencent.mobileqq");
    bundle.putString("app_name", " QQ音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

//网易音乐↓
public void wysendJSON(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img ){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 102021671);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "com.tencent.mobileqq");
    bundle.putString("app_name", " 网易云音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

public void kwsendJSON(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img ){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 102021671);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "com.tencent.mobileqq");
    bundle.putString("app_name", " 酷我音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

//QQ音乐↓
public void qqsendJSONs(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 100497308);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "com.tencent.qqmusic");
    bundle.putString("app_name", " QQ音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

//网易音乐↓
public void wysendJSONs(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img ){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 100495085);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "com.netease.cloudmusic");
    bundle.putString("app_name", " 网易云音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

public void kwsendJSONs(String uin1, String uin2, int uintype, String title, String desc, String detail_url, String audio, String img ){
try{
    Bundle bundle = new Bundle();
    bundle.putInt("forward_type", 11);
    bundle.putString("detail_url", detail_url);
    bundle.putString("title", title);
    bundle.putString("image_url_remote", img);
    bundle.putString("audio_url", audio);
    bundle.putInt("req_type", 2);
    if(uintype == 1000)
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", 1);
    }
    else
    {
        bundle.putString("uin", uin1);
        bundle.putInt("uintype", uintype);
        if(uintype == 10014) bundle.putString("guild_id", uin2);
    }
    bundle.putLong("req_share_id", 100243533);
    bundle.putString("desc", desc);
    bundle.putString("res_pkg_name", "cn.kuwo.player");
    bundle.putString("app_name", " 酷我音乐");
    bundle.putBoolean("needChange", true);
    ForwardSDKB77Sender sender = new ForwardSDKB77Sender();
    Object MusicCard = StructMsgForAudioShare.class.getDeclaredConstructor(new Class[]
    {
        Bundle.class
    });
    MusicCard.setAccessible(true);
    AbsShareMsg abs = MusicCard.newInstance(new Object[]
    {
        bundle
    });
    sender.e(app, abs, activity, bundle, "");
}catch(e){
Toast(uin1+" 发送音卡发送失败");
}
}

//群
public void wysendTroopMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=163&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 2);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        wysendJSON(qun, "", 1, title, desc, detail_url, audio, img);
        return;
    }

    wysendJSONs(qun, "", 1, title, desc, detail_url, audio, img);
}


public void qqsendTroopMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=qq&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 2);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        qqsendJSON(qun, "", 1, title, desc, detail_url, audio, img);
        return;
    }

    qqsendJSONs(qun, "", 1, title, desc, detail_url, audio, img);
}

public void kwsendTroopMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=kuwo&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 2);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        kwsendJSON(qun, "", 1, title, desc, detail_url, audio, img);
        return;
    }

    kwsendJSONs(qun, "", 1, title, desc, detail_url, audio, img);
}


//————————————————————————————————

/*
//频道
public void wysendChannelMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    int i = qun.indexOf("&");
    String pin = qun.substring(0, i);
    String channel = qun.substring(i + 1);
    wysendJSON(channel, pin, 10014, title, desc, detail_url, audio, img);
}
public void qqsendChannelMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    int i = qun.indexOf("&");
    String pin = qun.substring(0, i);
    String channel = qun.substring(i + 1);
    qqsendJSON(channel, pin, 10014, title, desc, detail_url, audio, img);
}

//————————————————————————————————


//非好友私聊(临时会话)
public void wysendTroopMemberMusic(String qun, String qq, String title, String desc, String detail_url, String audio, String img) {
//    Toast("非好友卡片将先发到群里再转发到私聊");
    wysendJSON(qunuin(qun), qq, 1000, title, desc, detail_url, audio, img);
}

public void qqsendTroopMemberMusic(String qun, String qq, String title, String desc, String detail_url, String audio, String img) {
//    Toast("非好友卡片将先发到群里再转发到私聊");
    qqsendJSON(qunuin(qun), qq, 1000, title, desc, detail_url, audio, img);
}
*/
//————————————————————————————————


//好友
public void wysendFriendMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=163&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 1);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        wysendJSON(qun, "", 0, title, desc, detail_url, audio, img);
        return;
    }

    wysendJSONs(qun, "", 0, title, desc, detail_url, audio, img);
}

public void qqsendFriendMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=qq&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 1);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        qqsendJSON(qun, "", 0, title, desc, detail_url, audio, img);
        return;
    }

    qqsendJSONs(qun, "", 0, title, desc, detail_url, audio, img);
}

public void kwsendFriendMusic(String qun, String title, String desc, String detail_url, String audio, String img) {
    if (读("猫羽雫", "音乐卡片", false)) {
        try {
            String encodedAudio = URLEncoder.encode(audio, StandardCharsets.UTF_8.toString());
            String encodedImg = URLEncoder.encode(img, StandardCharsets.UTF_8.toString());
            String url = "https://oiapi.net/API/QQMusicJSONArk?format=kuwo&url=" + encodedAudio + "&song=" + title + "&singer=" + desc + "&cover=" + encodedImg + "&jump=" + detail_url;
            String ark = httpget(url, "");
            
            JSONObject json = new JSONObject(ark);
            if (json.getInt("code") == 1) {
                sendCard(qun, json.getString("message"), 1);
                return;
            }
        } catch (Exception e) {
            Toast("解析签名卡片出错");
        }
    }

    if (读("猫羽雫", "备用音卡", false)) {
        kwsendJSON(qun, "", 0, title, desc, detail_url, audio, img);
        return;
    }

    kwsendJSONs(qun, "", 0, title, desc, detail_url, audio, img);
}
