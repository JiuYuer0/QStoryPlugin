public class MsgUtil{
    //集成工具类    
    public static boolean isMe(String userQQ){
        return userQQ.equals(MyUin);
    }    
    //获取今日日期
    public static String getTodayDate() {
	    Date date=new Date();//此时date为当前的时间
	    SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
	    return dateFormat.format(date);
    }
    //获取短链接
    public static String getShortUrl(String url){
        return url;
    }
    //判断是不是第一次使用
    public static boolean isStartsUse(){
        return getBoolean("settings", "isStartsUse", true);             
    }
    //截取链接
    public static String matchPicUrl(String content){
        Matcher matcher = Pattern.compile("\\[PicUrl=(.*?)\\]").matcher(content);
        //判断一下是否匹配到了
        if(matcher.find())       
            return matcher.group(1);
        return "没有找到匹配的图片链接";    
    }
    //QQ跳转api
    public static void jump(String url) {
        ((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);
    }
    //通过QQ空间查看QQ
    public static String findQQByWeZone(String url) {
        Matcher matcher = Pattern.compile("%26xsj_author_uin%3D(.*?)%26").matcher(url.replace("\\/", "/"));
        if (matcher.find()) {        
            return (matcher.group(1)).toString();
        } else {
            return "获取QQ失败";
        }
    }
    //删除文件
    public static boolean deleteFile(String filePath) {  
        //初始化
        boolean flag = false;  
	    //实例化对象
	    File file = new File(filePath);  
	    //判断
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    } 
	    //返回值 
	    return flag;
	}
	//发送视频函数
    public static void sendVideos(Object data, String fileName, String videoUrl) {
        //初始化一个文件名
        String path = AppPath + "/temp/[氚解析] Our eyes meet fondly is a spiritual kiss of humanity without sexual desire. 对视，是人类不带情欲的精神接吻.mp4";
        //判断文件夹是否存在
        File fileTempPath = new File(AppPath + "/temp/");
        //没有就自动创建
        if(!fileTempPath.exists()){
            fileTempPath.mkdirs();
        }
        //配置文件名
        if (fileName != null || fileName != ""){
            fileName = fileName.replace("/","").replace("\\","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("|","").replace("<","").replace(">","");
            path = AppPath + "/temp/[氚解析] " + fileName + ".mp4";
        }
        //删除缓存
        clearCacheData(false);
        //初始化Uin
        String Uin = null;
        //下载新的视频
        Http.download(videoUrl, path);
        //创建文件对象
        File file = new File(path);
        //判断消息来源
        if(data.IsGroup){
            Uin = data.GroupUin;
        }else{
            Uin = String.valueOf(data.msg.peerUin);
        }
        //判断文件大小
        if(file.length() > 20971520){
            Message.sendFiles(data, path);//以文件发送
        }else{
            Message.sendVideos(data, path);//以视频发送  
        }     
    }
    //缓存清理模式
    public static void rebulidCacheData(Object data){
        //判断一下有没有文件需要处理        
        if(new File(AppPath + "/temp/").list().length < 1){
            Message.sendMsgs(data, "[执行成功] 共计需要转移的数据项目有 0 条, 执行成功 0 条, 执行失败 0 条");
            Message.Toast(1, "无需要转移的文件");
            return;
        }
        //获取当前活动
        Activity ThisActivity= GetActivity();
        // 实例化对话框对象
	    ThisActivity.runOnUiThread(new Runnable(){
	        public void run() {
		        //初始化对象
                File file1 = new File(AppPath + "/temp/");
                File file2 = new File(new File(AppPath).getParent() + "/Cache Data/");
                String [] arrayOfCache = file1.list();
                String arrlength = arrayOfCache.length.toString();
                //创建对话框
                AlertDialog alertDialog = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		        alertDialog.setTitle("提示");
                alertDialog.setMessage("即将转移脚本数据 共" + arrlength + "条\n\n您确定转移吗？");
                alertDialog.setCancelable(false);
		        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
                        //判断目录是否存在
                        if(!file2.exists()){
                            file2.mkdirs();
                        }
                        //初始化计数器
                        int success = 0, fail = 0;
                        //遍历转移
                        for(String files : arrayOfCache){
                            if(new File(AppPath + "/temp/" + files).renameTo(new File(new File(AppPath).getParent() + "/Cache Data/" + files))){
                                success ++;
                            }else{
                                fail ++;
                            }                         
                        }
                        Message.Toast(2, "文件转移成功");
                        Message.sendMsgs(data, "[执行成功] 共计需要转移的数据项目有 " + arrlength + " 条, 执行成功 " + success + " 条, 执行失败 " + fail + " 条");
	                }
		        });
                alertDialog.show();
            }
	    });    
    }
    //查看缓存信息
    public static void toViewCacheInformation(Object data, String value){
        //创建路径
        String path = AppPath + "/temp/";
        //创建文件对象
        File file = new File(path);        
        //判断获取类型
        if(value.equalsIgnoreCase("cache")){
            path = new File(AppPath).getParent() + "/Cache Data/";
            file = new File(path);
        }
        //创建体积储存容器
        long fileSize = 0L;
        //计算文件数量
        String filesNum = file.list().length.toString();
        //遍历一下文件计算文件总体积
        for(String fileName : file.list()){
            //创建一个临时对象
            File tempFile = new File(path + fileName);
            //判断是不是可以获取到体积
            if (tempFile.exists() && tempFile.isFile()) {
                fileSize = fileSize + tempFile.length();
            }      
        }
        Message.sendMsgs(data, "文件储存：" + value + "\n缓存数量：" + filesNum + " 条 \n缓存体积：" + (fileSize/1024/1024).toString() + " MB");
    }
}