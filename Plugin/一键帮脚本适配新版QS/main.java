
// 适配的脚本目录，把要适配的脚本目录填到里面
public static final String targetDir = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/关键词";

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

public class CodeReplacer {
    private static final Map REPLACEMENT_MAP = new HashMap();
    private static final Map REPLACEMENT_COUNT = new HashMap();
    private static final String TARGET_TIME = "time=2025-8-1";
    
    public static void init() {
        // 原有替换规则（保持不变）
        REPLACEMENT_MAP.put("\\bGetChatType\\b", "getChatType");
        REPLACEMENT_MAP.put("\\bGetGroupUin\\b", "getCurrentGroupUin");
        REPLACEMENT_MAP.put("\\bGetFriendUin\\b", "getCurrentFriendUin");
        REPLACEMENT_MAP.put("\\bForbidden\\b", "forbidden");
        REPLACEMENT_MAP.put("\\bKick\\b", "kick");
        REPLACEMENT_MAP.put("\\bAddItem\\b", "addItem");
        REPLACEMENT_MAP.put("\\bRemoveItem\\b", "removeItem");
        REPLACEMENT_MAP.put("\\bRemoveItemByName\\b", "removeItemByName");
        REPLACEMENT_MAP.put("\\bGetActivity\\b", "getActivity");
        REPLACEMENT_MAP.put("\\bToast\\b", "toast");
        REPLACEMENT_MAP.put("\\bAppPath\\b", "appPath");
        REPLACEMENT_MAP.put("\\bMyUin\\b", "myUin");
        REPLACEMENT_MAP.put("\\bPluginID\\b", "pluginID");
        REPLACEMENT_MAP.put("\\bOnTroopEvent\\b", "onTroopEvent");
        REPLACEMENT_MAP.put("\\bOnForbiddenEvent\\b", "onForbiddenEvent");
        REPLACEMENT_MAP.put("\\bCallback_OnRawMsg\\b", "callbackOnRawMsg");
        
        for (String key : REPLACEMENT_MAP.keySet()) {
            REPLACEMENT_COUNT.put(key, new AtomicInteger(0));
        }
    }

    public static void start() throws IOException {
        String dir = targetDir;
        File rootDir = new File(dir);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            toast("无效的目录路径");
            return;
        }

        processDirectory(rootDir);
        log("\n替换次数统计:");
        int totalReplacements = 0;
        for (Map.Entry entry : REPLACEMENT_MAP.entrySet()) {
            int count = REPLACEMENT_COUNT.get(entry.getKey()).get();
            totalReplacements += count;
            if (count > 0) {
                String readableKey = entry.getKey().replace("\\b", "");
                log("  " + readableKey + " -> " + entry.getValue() + ": " + count + " 次");
            }
        }
        log("总替换次数: " + totalReplacements);
        toast("适配已完成");
    }
    
    private static void processDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file);
            } else {
                if (file.getName().endsWith(".java")) {
                    processJavaFile(file);
                } else if (file.getName().equals("info.prop")) {
                    processPropFile(file);
                }
            }
        }
    }

    // 处理Java文件（原processFile方法）
    private static void processJavaFile(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            String original = content;

            for (Map.Entry entry : REPLACEMENT_MAP.entrySet()) {
                String regex = entry.getKey();
                String replacement = entry.getValue();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                
                content = content.replaceAll(regex, replacement);
                REPLACEMENT_COUNT.get(regex).addAndGet(count);
            }

            if (!original.equals(content)) {
                // 写回文件
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(content.getBytes());
                    log("已处理文件: " + file.getAbsolutePath());
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        } catch (IOException e) {
            error(e);
        }
    }

    // 新增：处理info.prop文件
    private static void processPropFile(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            String original = content;
            
            // 正则匹配所有time格式（兼容空格和不同日期格式）
            Pattern timePattern = Pattern.compile("time\\s*=\\s*[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
            Matcher matcher = timePattern.matcher(content);
            
            if (matcher.find()) {
                // 替换现有time值
                content = matcher.replaceAll(TARGET_TIME);
            } else {
                // 没有time参数时添加新行
                content += (content.endsWith("\n") ? "" : "\n") + TARGET_TIME;
            }

            if (!original.equals(content)) {
                // 写回文件
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(content.getBytes());
                    log("已处理文件: " + file.getAbsolutePath());
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        } catch (IOException e) {
            error(e);
        }
    }

}
CodeReplacer.init();
CodeReplacer.start();