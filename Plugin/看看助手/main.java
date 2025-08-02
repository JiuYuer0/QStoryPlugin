// == QStory 脚本 ==
// name = 猫猫看腿助手简化版
// type = 1
// version = 6.1
// author = 猫猫
// id = com.maomao.legsviewer
// time = 2025-08-02

// 全局变量
String ConfigName = "LegsViewerSettings";
String AntiConflictKey = "AntiConflictEnabled";
String R18Key = "R18Enabled";
String helpMessage = "【猫猫看腿助手使用说明】\n"
                   + "1. 支持多种触发词：\n"
                   + "  - 看看腿：随机美腿图片\n"
                   + "  - 看看白丝：白丝主题图\n"
                   + "  - 看看黑丝：黑丝主题图\n"
                   + "  - 看看猫猫：可爱猫咪\n"
                   + "  - 看看jk：JK制服图片\n"
                   + "  - 看看r18：R18内容（仅限本人使用）\n"
                   + "2. 可在菜单中开启/关闭功能\n"
                   + "3. 图片自动加载无需等待";

// API配置
java.util.Map apiMap = new java.util.HashMap();

// 在单独的代码块中添加API映射
{
    apiMap.put("看看腿", "https://api.lolimi.cn/API/meizi/api.php?type=image");
    apiMap.put("看看白丝", "https://v2.xxapi.cn/api/baisi?return=302");
    apiMap.put("看看黑丝", "https://v2.xxapi.cn/api/heisi?return=302");
    apiMap.put("看看猫猫", "https://api.suyanw.cn/api/mao");
    apiMap.put("看看jk", "https://api.yujn.cn/api/jk.php");
    apiMap.put("看看r18", "R18_SPECIAL");
}

// R18 API列表
String[] r18Apis = {
    "https://image.anosu.top/pixiv/direct?r18=1&keyword=azurlane",
    "https://image.anosu.top/pixiv/direct?r18=1&keyword=genshinimpact",
    "https://image.anosu.top/pixiv/direct?r18=1&keyword=arknights"
};

// 确保缓存目录存在
void initCacheDir() {
    java.io.File cacheDir = new java.io.File(AppPath, "cache");
    if (!cacheDir.exists()) {
        cacheDir.mkdirs();
    }
}

// 下载图片到本地并返回路径
String downloadImage(String apiUrl) {
    initCacheDir();
    try {
        // 生成唯一文件名
        String fileName = java.util.UUID.randomUUID().toString() + ".jpg";
        String filePath = new java.io.File(AppPath, "cache/" + fileName).getAbsolutePath();
        
        // 打开API连接
        java.net.URL url = new java.net.URL(apiUrl);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        
        // 检查响应状态
        int responseCode = conn.getResponseCode();
        
        // 处理重定向
        if (responseCode == java.net.HttpURLConnection.HTTP_MOVED_TEMP || 
            responseCode == java.net.HttpURLConnection.HTTP_MOVED_PERM || 
            responseCode == java.net.HttpURLConnection.HTTP_SEE_OTHER) {
            String newUrl = conn.getHeaderField("Location");
            if (newUrl != null) {
                conn.disconnect();
                url = new java.net.URL(newUrl);
                conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                responseCode = conn.getResponseCode();
            }
        }
        
        // 检查最终的响应状态
        if (responseCode != java.net.HttpURLConnection.HTTP_OK) {
            Toast("图片下载失败: HTTP " + responseCode);
            return null;
        }
        
        // 获取输入流
        java.io.InputStream in = conn.getInputStream();
        
        // 创建文件输出流
        java.io.FileOutputStream out = new java.io.FileOutputStream(filePath);
        
        // 读取并写入文件
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        
        // 关闭流
        out.close();
        in.close();
        
        // 检查文件是否有效
        java.io.File file = new java.io.File(filePath);
        if (file.length() == 0) {
            file.delete();
            Toast("下载的图片文件为空");
            return null;
        }
        
        return filePath;
    } catch (Exception e) {
        Toast("图片下载异常: " + e.getMessage());
        return null;
    }
}

// 翻转图片（水平+垂直翻转）
String flipImage(String originalPath) {
    try {
        // 读取原始图片
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inSampleSize = 1; // 不缩放
        android.graphics.Bitmap original = android.graphics.BitmapFactory.decodeFile(originalPath, options);
        if (original == null) {
            Toast("无法读取图片: " + originalPath);
            return null;
        }
        
        // 创建翻转矩阵
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setScale(-1, -1);
        matrix.postTranslate(original.getWidth(), original.getHeight());
        
        // 创建翻转后的图片
        android.graphics.Bitmap flipped = android.graphics.Bitmap.createBitmap(
            original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
        
        // 保存翻转后的图片
        String flippedPath = AppPath + "/cache/flipped_" + java.util.UUID.randomUUID() + ".jpg";
        java.io.FileOutputStream out = new java.io.FileOutputStream(flippedPath);
        flipped.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, out);
        out.close();
        
        return flippedPath;
    } catch (Exception e) {
        Toast("图片翻转失败: " + e.getMessage());
        return null;
    }
}

// 消息排版美化
String formatMessage(String message) {
    try {
        boolean antiConflict = getBoolean(ConfigName, AntiConflictKey, true);
        if (antiConflict) {
            return "，，" + message;
        }
    } catch (Exception e) {
    }
    return message;
}

// 发送格式化消息
void sendFormattedMsg(String group, String user, String message) {
    String formatted = formatMessage(message);
    if (group != null && !group.isEmpty()) {
        sendMsg(group, "", formatted);
    } else if (user != null && !user.isEmpty()) {
        sendMsg("", user, formatted);
    }
}

// 响应消息
void onMsg(Object msg) {
    try {
        String content = (String) ((Object)msg).MessageContent;
        String uin = ((Object)msg).IsGroup ? ((Object)msg).GroupUin : ((Object)msg).PeerUin;
        String senderUin = (String) ((Object)msg).UserUin;
        
        String key = uin + "_enabled";
        boolean enabled = getBoolean(ConfigName, key, true);
        
        String apiUrl = (String) apiMap.get(content);
        
        // 特殊处理R18指令
        if (enabled && "R18_SPECIAL".equals(apiUrl)) {
            boolean r18Enabled = getBoolean(ConfigName, R18Key, false);
            
            if (!r18Enabled) {
                sendFormattedMsg(uin, senderUin, "R18功能未启用");
                return;
            }
            
            if (MyUin.equals(senderUin)) {
                // 在新线程中处理R18消息
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            // 获取随机API
                            java.util.Random random = new java.util.Random();
                            String r18Url = r18Apis[random.nextInt(r18Apis.length)];
                            
                            // 下载图片
                            String localPath = downloadImage(r18Url);
                            if (localPath == null) {
                                Toast("R18图片下载失败");
                                return;
                            }
                            
                            // 翻转图片
                            String flippedPath = flipImage(localPath);
                            if (flippedPath == null) {
                                deleteFile(localPath); // 删除原始图片
                                Toast("R18图片翻转失败");
                                return;
                            }
                            
                            // 删除原始图片
                            deleteFile(localPath);
                            
                            // 在主线程中发送图片
                            android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                            mainHandler.post(new Runnable() {
                                public void run() {
                                    try {
                                        // 发送图片
                                        if (((Object)msg).IsGroup) {
                                            sendPic(((Object)msg).GroupUin, "", flippedPath);
                                        } else {
                                            sendPic("", ((Object)msg).PeerUin, flippedPath);
                                        }
                                        
                                        Toast("已发送R18图片");
                                        
                                        // 10秒后删除翻转后的图片
                                        android.os.Handler handler = new android.os.Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                deleteFile(flippedPath);
                                            }
                                        }, 10000);
                                        
                                        // 1分钟后再次尝试删除（确保文件被删除）
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                deleteFile(flippedPath);
                                            }
                                        }, 60000);
                                    } catch (Exception e) {
                                        Toast("R18图片发送失败: " + e.getMessage());
                                        // 发生异常时删除翻转后的图片
                                        deleteFile(flippedPath);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Toast("R18图片处理失败: " + e.getMessage());
                        }
                    }
                }).start();
            } else {
                sendFormattedMsg(uin, senderUin, "涩涩是不可以的");
            }
            return;
        }
        
        if (enabled && apiUrl != null) {
            // 在新线程中处理普通消息
            new Thread(new Runnable() {
                public void run() {
                    try {
                        // 下载图片
                        String localPath = downloadImage(apiUrl);
                        if (localPath == null) {
                            Toast(content + " 图片下载失败");
                            return;
                        }
                        
                        // 在主线程中发送图片
                        android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                        mainHandler.post(new Runnable() {
                            public void run() {
                                try {
                                    if (((Object)msg).IsGroup) {
                                        sendPic(((Object)msg).GroupUin, "", localPath);
                                    } else {
                                        sendPic("", ((Object)msg).PeerUin, localPath);
                                    }
                                    
                                    // 1分钟后删除缓存
                                    android.os.Handler handler = new android.os.Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            deleteFile(localPath);
                                        }
                                    }, 60000);
                                    
                                    // 5分钟后再次尝试删除（确保文件被删除）
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            deleteFile(localPath);
                                        }
                                    }, 300000);
                                } catch (Exception e) {
                                    Toast(content + " 图片发送失败: " + e.getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast(content + " 图片下载失败: " + e.getMessage());
                    }
                }
            }).start();
        }
    } catch (Exception e) {
        Toast("处理消息出错: " + e.getMessage());
    }
}

// 悬浮窗菜单
void onClickFloatingWindow(int type, String uin) {
    // 添加功能开关
    String key = uin + "_enabled";
    boolean enabled = getBoolean(ConfigName, key, true);
    String switchText = "功能开关: " + (enabled ? "✔已开启" : "✖已关闭");
    addTemporaryItem(switchText, "toggleSwitch");
    
    // 添加防冲突开关
    boolean antiConflict = getBoolean(ConfigName, AntiConflictKey, true);
    String antiConflictText = "消息排版美化: " + (antiConflict ? "✔已开启" : "✖已关闭");
    addTemporaryItem(antiConflictText, "toggleAntiConflict");
    
    // 添加R18功能开关
    boolean r18Enabled = getBoolean(ConfigName, R18Key, false);
    String r18Text = "R18功能: " + (r18Enabled ? "✔已开启" : "✖已关闭");
    addTemporaryItem(r18Text, "toggleR18");
    
    // 添加帮助按钮
    addTemporaryItem("使用帮助", "showHelp");
    
    // 添加支持的指令列表
    addTemporaryItem("支持指令列表", "showCommands");
}

// 开关切换回调
void toggleSwitch(String group, String user, int type) {
    String key = group.isEmpty() ? user : group + "_enabled";
    boolean current = getBoolean(ConfigName, key, true);
    putBoolean(ConfigName, key, !current);
    Toast("功能已" + (!current ? "开启" : "关闭"));
}

// 防冲突开关回调
void toggleAntiConflict(String group, String user, int type) {
    boolean current = getBoolean(ConfigName, AntiConflictKey, true);
    putBoolean(ConfigName, AntiConflictKey, !current);
    Toast("消息排版美化已" + (!current ? "开启" : "关闭"));
}

// R18开关回调
void toggleR18(String group, String user, int type) {
    boolean current = getBoolean(ConfigName, R18Key, false);
    putBoolean(ConfigName, R18Key, !current);
    Toast("R18功能已" + (!current ? "开启" : "关闭"));
}

// 帮助回调
void showHelp(String group, String user, int type) {
    sendFormattedMsg(group, user, helpMessage);
}

// 指令列表回调
void showCommands(String group, String user, int type) {
    StringBuilder commands = new StringBuilder("支持指令:\n");
    java.util.Iterator it = apiMap.keySet().iterator();
    while (it.hasNext()) {
        String cmd = (String) it.next();
        commands.append("- ").append(cmd).append("\n");
    }
    commands.append("\n※ R18功能需要额外开启");
    
    sendFormattedMsg(group, user, commands.toString());
}

// 删除文件方法 - 增强版
void deleteFile(String path) {
    try {
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            // 尝试删除文件
            if (file.delete()) {
                // 成功删除
            } else {
                // 如果删除失败，尝试强制删除
                java.lang.Runtime.getRuntime().exec("rm -f \"" + path + "\"");
            }
        }
    } catch (Exception e) {
        // 可选：记录异常
    }
}
