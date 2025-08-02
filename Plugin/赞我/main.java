// 作 海枫

// QQ交流群：1050252163

// 原作者为 言子楪世 已做了优化

// 请不要乱二改脚本 原作者 言子楪世 海枫 未经允许请勿二创！

import java.text.SimpleDateFormat;
import java.util.Date;
java.util.Map groupSettings = new java.util.HashMap();

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String qun = msg.GroupUin;
    
    if (msg.IsGroup) {
        Boolean enabled = (Boolean) groupSettings.get(qun);
        if (enabled == null || !enabled) return;
    }
    
    if (text.equals("赞我")) {
        if (getBoolean("点赞记录_"+getTodayDate(),qq,false)) 
        {
            String reply = "今天已经给你点过赞了 请明天再来";
            if (msg.IsGroup) 
            {
                sendReply(qun,msg,reply);
            }
            else
            {
                sendMsg("",qq,reply);
            }
            return;
        }
        String reply = "已经给你点赞了 记得给我回赞";
        if (msg.IsGroup) 
        {
            sendReply(qun,msg,reply);
        }
        else
        {
            sendMsg("",qq,reply);
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    sendLike(qq,20);
                    putBoolean("点赞记录_"+getTodayDate() ,qq, true);
                } catch (Exception e) {
                    Toast("点赞失败: " + e.getMessage());
                }
            }
        }).start();
    }
}

public String getTodayDate() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return dateFormat.format(date);
}

AddItem("开启本群赞我","开启本群赞我");
AddItem("关闭本群赞我","关闭本群赞我");

public void 开启本群赞我(String g,String u,int t)
{
    if (t != 2) {
        Toast("请在群聊中使用");
        return;
    }
    
    String currentGroup = getCurrentGroupUin();
    if (currentGroup.equals("0")) {
        Toast("未检测到群聊");
        return;
    }
    
    Boolean enabled = (Boolean) groupSettings.get(currentGroup);
    if (enabled == null || !enabled) {
        groupSettings.put(currentGroup, true);
        Toast("已在本群开启赞我功能");
    } else {
        Toast("本群已开启赞我功能");
    }
}

public void 关闭本群赞我(String g,String u,int t)
{
    if (t != 2) {
        Toast("请在群聊中使用");
        return;
    }
    
    String currentGroup = getCurrentGroupUin();
    if (currentGroup.equals("0")) {
        Toast("未检测到群聊");
        return;
    }
    
    Boolean enabled = (Boolean) groupSettings.get(currentGroup);
    if (enabled != null && enabled) {
        groupSettings.put(currentGroup, false);
        Toast("已在本群关闭赞我功能");
    } else {
        Toast("本群未开启赞我功能");
    }
}

sendLike("2133115301",20);