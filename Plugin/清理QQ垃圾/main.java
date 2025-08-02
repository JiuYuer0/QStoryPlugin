
// 作 海枫

// 二改者二改脚本上传QStory在线脚本既拉黑上传权限

// QQ交流群 1050252163

import java.io.File;

toast("欢迎使用清理QQ垃圾Java");

addItem("清理垃圾", "clearCacheMenu");

public void deleteRecursive(File fileOrDirectory) {
    if (fileOrDirectory == null || !fileOrDirectory.exists()) return;
    
    if (fileOrDirectory.isDirectory()) {
        File[] files = fileOrDirectory.listFiles();
        if (files != null) {
            for (File child : files) {
                deleteRecursive(child);
            }
        }
    }
    fileOrDirectory.delete();
}

// 主清理方法
public void clearCache() {
    try {
        toast("开始清理QQ垃圾...");
        log("开始清理QQ垃圾");
        
        // 强制停止QQ
        Runtime.getRuntime().exec("am force-stop com.tencent.mobileqq");
        log("已强制停止QQ");
        Thread.sleep(1000);
        
        // 清理路劲路径 可自己添加
        String[] cachePaths = {
            // 部分路径 新版QQ有可能没有
            "/sdcard/Tencent/cache",
            "/sdcard/Tencent/MobileQQ/diskcache",
            "/sdcard/Tencent/MobileQQ/Scribble",
            "/sdcard/Tencent/MobileQQ/ScribbleCache",
            "/sdcard/Tencent/MobileQQ/qav",
            "/sdcard/Tencent/MobileQQ/qqmusic",
            "/sdcard/Tencent/MobileQQ/pddata",
            "/sdcard/tencent/QQGallery/log",
            "/sdcard/Tencent/MobileQQ/photo",
            "/sdcard/Tencent/MobileQQ/chatpic",
            "/sdcard/Tencent/MobileQQ/thumb",
            "/sdcard/Tencent/MobileQQ/QQ_Images",
            "/sdcard/Tencent/MobileQQ/QQEditPic",
            "/sdcard/Tencent/MobileQQ/hotpic",
            "/sdcard/Tencent/MobileQQ/shortvideo",
            "/sdcard/Tencent/MobileQQ/qbosssplahAD",
            "/sdcard/Tencent/MobileQQ/.apollo",
            "/sdcard/Tencent/MobileQQ/vas",
            "/sdcard/Tencent/MobileQQ/lottie",
            "/sdcard/Tencent/mini",
            "/sdcard/Tencent/TMAssistantSDK",
            "/sdcard/Tencent/.font_info",
            "/sdcard/Tencent/.hiboom_font",
            "/sdcard/Tencent/.gift",
            "/sdcard/Tencent/.trooprm/enter_effects",
            "/sdcard/Tencent/tbs",
            "/sdcard/Tencent/.pendant",
            "/sdcard/Tencent/.profilecard",
            "/sdcard/Tencent/.sticker_recommended_pics",
            "/sdcard/Tencent/pe",
            "/sdcard/Tencent/.emotionsm",
            "/sdcard/Tencent/msflogs",
            "/sdcard/Tencent/.vaspoke",
            "/sdcard/Tencent/newpoke",
            "/sdcard/Tencent/poke",
            "/sdcard/Tencent/.vipicon",
            "/sdcard/Tencent/DoutuRes",
            "/sdcard/Tencent/funcall",
            "/sdcard/Tencent/QQfile_recv/.trooptmp",
            "/sdcard/Tencent/QQfile_recv/.tmp",
            "/sdcard/Tencent/QQfile_recv/.thumbnails",
            
            // Android/data 部分 不确定是否可以清理
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/diskcache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/Scribble",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/ScribbleCache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qav",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qqmusic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/pddata",
            "/sdcard/Android/data/com.tencent.mobileqq/cache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/photo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/chatpic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/thumb",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/QQ_Images",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/QQEditPic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/hotpic",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/zip_cache",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/video_cache",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/imageV2",
            "/sdcard/Android/data/com.tencent.mobileqq/qzlive",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/shortvideo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qbosssplahAD",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.apollo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/vasrm",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/lottie",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQ_Images/QQEditPic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/mini",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/TMAssistantSDK",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.font_info",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.hiboom_font",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.gift",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.trooprm/enter_effects",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/head",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.pendant",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.profilecard",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.sticker_recommended_pics",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/pe",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.emotionsm",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.vaspoke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/newpoke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/poke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.vipicon",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/DoutuRes",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/funcall",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.trooptmp",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.tmp",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.thumbnails",
            "/sdcard/Android/data/com.tencent.mobileqq/qcircle",
            "/sdcard/Android/data/com.tencent.mobileqq/files/.info",
            "/sdcard/Android/data/com.tencent.mobileqq/files/onelog",
            "/sdcard/Android/data/com.tencent.mobileqq/files/ae/playshow",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/msflogs",
            
            // 其他路径
            "/sdcard/tencent/msflogs",
            "/sdcard/tencent/wns/Logs",
            "/sdcard/tencent/wtlogin",
            "/sdcard/tencent/QQmail/log",
            "/sdcard/tencent/QQmail/qmlog",
            "/sdcard/tencent/qqimsecure/pt",
            "/sdcard/tencent/MobileQQ/ocr/cache",
            "/sdcard/Android/data/com.tencent.mobileqq/files/.info",
            "/sdcard/Android/data/com.tencent.mobileqq/files/ae/camera/capture",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tbslog",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/tbs_live_log",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/tbs_common_log",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.tmp/edit_video",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/TMAssistantSDK/Download/com.tencent.mobileqq",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/mini/files",
            "/sdcard/Tencent/blob/mqq",
            "/sdcard/Tencent/ams/cache",
            "/sdcard/Tencent/com.tencent.weread/euplog.txt",
            "/sdcard/Tencent/imsdkvideocache",
            "/sdcard/Tencent/Midas/Log",
            "/sdcard/Tencent/MobileQQ/bless",
            
            // /data/data 路径 (需要root权限)
            "/data/data/com.tencent.mobileqq/files/group_catalog_temp",
            "/data/data/com.tencent.mobileqq/files/hippy/codecache",
            "/data/data/com.tencent.mobileqq/files/hippy/bundle",
            "/data/data/com.tencent.mobileqq/files/mini"
        };
        
        int deletedCount = 0;
        int errorCount = 0;
        
        for (String path : cachePaths) {
            File target = new File(path);
            if (!target.exists()) {
                log("路径不存在: " + path);
                continue;
            }
            
            try {
                log("清理: " + path);
                deleteRecursive(target);
                deletedCount++;
            } catch (Exception e) {
                log("清理错误: " + path + " - " + e.getMessage());
                errorCount++;
            }
        }
        
        // 重启QQ
        Runtime.getRuntime().exec("am start com.tencent.mobileqq/.activity.SplashActivity");
        log("已重启QQ");
        
        String result = "清理完成！处理" + deletedCount + "个路径";
        if (errorCount > 0) {
            result += "，有" + errorCount + "个错误";
        }
        
        toast(result);
        log(result);
        
    } catch (Exception e) {
        toast("清理出错: " + e.getMessage());
        log("清理出错: " + e.getMessage());
        error(e);
    }
}

void onMsg(Object msg) {
    if (msg.MessageContent.equals("清理垃圾") && !msg.IsSend) {
        new Thread(new Runnable() {
            public void run() {
                clearCache();
            }
        }).start();
    }
}

public void clearCacheMenu(String groupUin, String uin, int chatType) {
    new Thread(new Runnable() {
        public void run() {
            clearCache();
        }
    }).start();
}

sendLike("2133115301",20);