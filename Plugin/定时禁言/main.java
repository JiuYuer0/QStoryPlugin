// 配置群聊，多个群聊用“ , ”隔开并给群号加上“ ”
String[] targetGroups={"114514","1314520","5201314","250666"}; //将群聊改为需要禁言的群即可
int[] sendHours={23,7};
int[] sendMinutes={57,0};
String[] sendContents={"禁","解"};

// 心里和脸上一样无所谓就好了
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
        String dateTag=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        for(int i=0;i<sendHours.length;i++){
            if(now.get(Calendar.HOUR_OF_DAY)==sendHours[i]&&now.get(Calendar.MINUTE)==sendMinutes[i]){
                String key="lastSend_"+i;
                if(!dateTag.equals(getString("TimeRecord",key))){
                    sendToGroups(sendContents[i]);
                    putString("TimeRecord",key,dateTag);
                    Toast("已发送："+sendContents[i]);
                }
            }
        }
    }
}).start();

// 假如人生有如果，但求只如初相见
void sendToGroups(String msg){
    new Thread(new Runnable(){
        public void run(){
            for(String group:targetGroups){
                try{
                    sendMsg(group,"",msg); 
                    Thread.sleep(1500);
                }catch(Exception e){
                    Toast(group+"发送失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

// 假如人生有如果，但求只如初相见
AddItem("立即发禁","sendNowJin");
AddItem("立即发解","sendNowJie");

public void sendNowJin(String g,String u,int t){
    sendToGroups("禁");
}

public void sendNowJie(String g,String u,int t){
    sendToGroups("解");
}

// 我不要听别人说，我要听你说，别人怎么骂我无关紧要，可你要懂得我
sendLike("2133115301",20);
sendLike("107464738",20);

// 生如夏花般灿烂 却和浮游般短暂
Toast("定时禁言脚本加载成功！");