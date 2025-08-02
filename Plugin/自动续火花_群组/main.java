// 作 海枫

// QQ交流群：1050252163

// 配置群聊 以下为QStory群，可以改为自己需要续火的群

String[] targetGroups = {"954720846","255413638","948759593"};
String[] fireWords = {"🔥","续火","火苗","保持火花","火火火"}; // 续火内容，自己改
int sendHour = 8; // 时 每天上午八点续火 可自行修改
int sendMinute = 0; // 分 可以修改 不支持精确到秒

// 冷却机制变量
long lastClickTime = 0;

// 定时线程 别动！别动！别动！
new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now=Calendar.getInstance();
                checkAndSend(now);
                Thread.sleep(60000);
            }catch(Exception e){
                Toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndSend(Calendar now){
        int currentHour=now.get(Calendar.HOUR_OF_DAY);
        int currentMinute=now.get(Calendar.MINUTE);
        String today=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour==sendHour&&currentMinute==sendMinute&&!today.equals(getString("GroupFire","lastSendDate"))){
            sendToAllGroups();
            putString("GroupFire","lastSendDate",today);
            Toast("已续火"+targetGroups.length+"个群组");
        }
    }
}).start();

void sendToAllGroups(){
    new Thread(new Runnable(){
        public void run(){
            for(String group:targetGroups){
                try{
                    String word=fireWords[(int)(Math.random()*fireWords.length)];
                    sendMsg(group,"",word);
                    Thread.sleep(5000);
                }catch(Exception e){
                    Toast(group+"续火失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

AddItem("立即续火","keepFireNow");

public void keepFireNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        Toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    sendToAllGroups();
    Toast("已立即续火所有群组");
}

Toast("群组续火花Java加载成功,每天"+sendHour+":"+(sendMinute<10?"0"+sendMinute:sendMinute)+"自动续火");

// 制作不易 支持一下qwq
sendLike("2133115301",20);