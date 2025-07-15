//定义一些函数 添加菜单项目等情况下 需要使用到
public void joinGroup(String GroupUin) {
    MsgUtil.jump("mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=473229996&subsource_id=10019");
}
public void functionMain(String GroupUin) {
    if(!getBoolean("settings", "functionMain", false)){
        Message.Toast(2, "脚本已激活");
        putBoolean("settings", "functionMain", true);
    }else{
        Message.Toast(2, "脚本已关闭");
        putBoolean("settings", "functionMain", false);
    }
}
public void functionPeer(String GroupUin) {
    if(!getBoolean("functionPeer", GroupUin, false)){
        Toast(GroupUin + " 群员使用权限已激活");
        putBoolean("functionPeer", GroupUin, true);
    }else{
        Toast(GroupUin + " 群员使用权限已关闭");
        putBoolean("functionPeer", GroupUin, false);
    }
}
public void dialogMenu(String GroupUin) {
    String title = "使用方法简介";
    String content = "#使用方法 获取使用功能菜单\n#自动解析 发送卡片后自动解析视频\n#自动撤回 自动撤回发送的解析命令\n#自动撤回卡片 自动撤回发送的解析卡片\n#逐条发送 解析得到的图集逐条发送图片\n#返回视频 解析后发送视频\n#更新日志 查看脚本历史版本的更新日志\n#解析卡片链接 自动解析解析卡片链接\n#自动清理临时文件 临时文件自动清理\n#解析链接+视频分享地址纯链接\n - 支持80多个短视频/图集去水印和解析\n - 理论上是 啥都可以解析 请自行测试\n - # 号和命令之间不允许有空格\n - 命令和链接之间可以有空格 \n - 回复视频分享链接/图片/表情包/视频卡片「解析」二字，均可以触发解析功能\n\n\ntips：聊天界面悬浮球打开脚本菜单，激活脚本功能和使用方法；也就是说基于该api的功能，咱们的脚本拥有几乎解析所有短视频链接的能力";
    String negativeBtn = "确定";
    String tipContent = "朕知道了";
    Message.dialog(title, content, tipContent, negativeBtn, false);
}
public void functionCache(String GroupUin) {
    clearCacheData(true);
}

//缓存清理模式
public void clearCacheData(boolean isClear){
    //判断是否需要立刻清理
    if(isClear){
        //获取当前活动
        Activity ThisActivity= GetActivity();
        // 实例化对话框对象
	    ThisActivity.runOnUiThread(new Runnable(){
	        public void run() {
		        AlertDialog alertDialog = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		        alertDialog.setTitle("警告");
                alertDialog.setMessage("接下来的操作将会是不可逆的，这会清空所有因解析产生的临时文件\n\n您确定继续吗？");
		        alertDialog.setCancelable(false);
		        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
                        //遍历删除
                        for(String files : new File(AppPath + "/temp/").list()){
                            new File(AppPath + "/temp/" + files).delete();
                        }   
                        Message.Toast(2, "操作成功");
	                }
		        });
		        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "我再想想", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message.Toast(2, "操作已取消");
	                }
		        });
                alertDialog.show();
            }
	    });
    }else{
        //自动删除临时文件
        if(getBoolean("settings", "removeTempFiles", true)){
            if(getBoolean("configs", "cache_" + MsgUtil.getTodayDate(), true)){
                //遍历删除
                for(String files : new File(AppPath + "/temp/").list()){
                    new File(AppPath + "/temp/" + files).delete();
                }
                putBoolean("configs", "cache_" + MsgUtil.getTodayDate(), false);
            }
        }
    }    
}

public String getStringOfFiles(String path){
    //读取文件
    File file=new File(path);
    //判断一下文件在不在
    if(!file.exists())
        return "FileNotFoundException";
    //尝试获取内容
	try{
	    //实例化 Scanner 对象
	    Scanner scanner=new Scanner(file);		    		    
	    //创建对象    
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (scanner.hasNext()) {
            i++;
            result.append(scanner.nextLine()).append("\n");
        }	    
        // 如果文件不为空
        if (i > 0) {                        
            return result.toString();
        }else{
            return "FileNotFoundException";
        }            	    
	}catch (FileNotFoundException e) {
        Message.Toast(1, "数据文件缺失异常");
        return "FileNotFoundException";
    }
}

//时间戳换算时间
public String timeStamp2DateTime(long timeStamp){ 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    return sdf.format(new Date(timeStamp));        
}
