// 作 海枫

// QQ交流群：1050252163

// 配置好友 以下为不知名人物，改为自己的好友

String[] targetFriends = {"5201314","114514","123456"};

// 冷却机制变量
long lastClickTime = 0;

// 定时线程 别动！别动！别动！
new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now=Calendar.getInstance();
                checkAndExecute(now);
                Thread.sleep(60000);
            }catch(Exception e){
                Toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour=now.get(Calendar.HOUR_OF_DAY);
        int currentMinute=now.get(Calendar.MINUTE);
        String today=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour==0&&currentMinute==0&&!today.equals(getString("DailyLike","lastLikeDate"))){
            executeSendLikes();
            putString("DailyLike","lastLikeDate",today);
            Toast("已执行点赞");
        }
    }
}).start();

void executeSendLikes(){
    new Thread(new Runnable(){
        public void run(){
            for(String friend:targetFriends){
                try{
                    sendLike(friend,20);
                    Thread.sleep(3000);
                }catch(Exception e){
                    Toast(friend+"点赞失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

AddItem("立即点赞","likeNow");
AddItem("入群","入群");

public void likeNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        Toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    executeSendLikes();
    Toast("正在执行点赞");
}

public void 入群(String s){
    android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
    intent.setData(android.net.Uri.parse("https://qm.qq.com/q/86xayIJ0li?from=tim"));
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
}

Toast("脚本加载成功 欢迎使用！");

// 制作不易 支持一下qwq
sendLike("2133115301",20);