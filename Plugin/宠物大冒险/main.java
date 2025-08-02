//↓这里添加【主人】权限QQ账号↓ || 默认加载账号拥有主人权限
public static String[] Owner={MyUin};
//提醒：添加前需自行检查该账号是否完整或可用😚～

    // 判断账号是否拥有主人权限
    public static boolean isInOwner(String uin) {
        for (String owner : Owner) {
            if (owner.equals(uin)) {
                return true;
            }
        }
        return false;
    }


    // 判断账号权限并返回对应数字（：0=玩家，1=主人）
    public static int checkPermission(String uin) {
        if (isInOwner(uin)) {
            return 1;
        } else {
            return 0;
        }
    }
    
long 加载中时间戳 = System.currentTimeMillis(); //开始加载时的时间戳

String RootPath = AppPath+"/目录/";
load(RootPath+"常用.java");
load(RootPath+"玩法/基础/神器.java");
load(RootPath+"玩法/基础/道具.java");
load(RootPath+"玩法/基础/宠物.java");
load(RootPath+"玩法/基础/副本.java");
load(RootPath+"玩法/基础/组队.java");
load(RootPath+"玩法/基础/怪物.java");
load(RootPath+"玩法/基础/奖池.java");
load(RootPath+"玩法/拓展/钱庄.java");
load(RootPath+"玩法/基础/代发.java");
String ID = readProp(AppPath+"/info.prop","id"); //脚本ID
String NAME = readProp(AppPath+"/info.prop","name"); //脚本名称
String 缓存路径 = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/data/plugin/"+ID+"/"; 
// 像「放生宠物」或「宠物吞噬」指令会用到这个缓存路径

long 加载时间戳=System.currentTimeMillis(); 
//↑加载成功时的时间戳，在发送【运行时长】时会用到

//成功加载时提示↓
Toast("「"+NAME+"」加载成功！\n•耗时："+formatTime(加载时间戳 - 加载中时间戳));




import java.io.File;

    // 图片格式（此脚本常用以下格式的图片）
    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".png"};

    // 检测单个目录是否有图片
    private static boolean hasImageInSingleDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return false; // 目录不存在或不是文件夹
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return false; // 目录不可访问
        }

        for (File file : files) {
            if (file.isFile() && isImageFile(file.getName())) {
                return true;
            }
        }
        return false;
    }

    // 判断文件名是否为图片格式
    private static boolean isImageFile(String fileName) {
        String lowerFileName = fileName.toLowerCase();
        for (String ext : IMAGE_EXTENSIONS) {
            if (lowerFileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // 检测多个目录：是否至少有一个目录存在图片
    public static boolean hasImageInAnyDir(String[] dirPaths) {
        for (String path : dirPaths) {
            if (hasImageInSingleDir(path)) {
                return true; // 找到一个有图片的目录，直接返回true
            }
        }
        return false; // 所有目录都没有图片
    }

    // 检测多个目录：是否所有目录都存在图片
    public static boolean hasImageInAllDirs(String[] dirPaths) {
        for (String path : dirPaths) {
            if (!hasImageInSingleDir(path)) {
                return false; // 有一个目录没有图片，直接返回false
            }
        }
        return true; // 所有目录都有图片
    }

        // 定义多个目录路径
        String[] dirs = {
            RootPath + "图片/宠物/",
            RootPath + "图片/其他/"
        };
        
      if (hasImageInAllDirs(dirs)) {
         // 所有目录都有图片，无需处理
      } else if (hasImageInAnyDir(dirs)) {
        // 至少一个目录有图片，无需处理
     } else {
       // 所有目录都无图片，弹出资源下载对话框
       load(AppPath+"/目录/UD.java"); 
       //加载指定java文件（用于下载资源包）
       showResourceDialog(); 
       //弹出对话框
     }