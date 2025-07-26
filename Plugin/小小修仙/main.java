//加载菜单变量
String Switch,On,Off;
On = "[已开启]";
Off = "[已关闭]";

// 点击菜单按钮
public void SwitchToggle(String ToggleName, String ToggleID) {
    // 切换菜单按钮
    if (getString(ToggleID, "开关") == null) {
        putString(ToggleID, "开关", "On");
        RemoveItem(ToggleName+Off);
    } else {
        putString(ToggleID, "开关", null);
        RemoveItem(ToggleName+On);
    }
    //刷新菜单开关按钮
    RefreshToggle("自动修炼", "toggleAutoPractice");
    RefreshToggle("消息隐藏", "toggleMsgHide");
    RefreshButton("快捷面板", "toggleQuickUI");
}

// 刷新菜单开关方法
public void RefreshToggle(String ToggleName, String ToggleID) {
    if (getString(ToggleID, "开关") == null) {
        Switch = Off;
    } else {Switch = On;}
    RemoveItem(ToggleName+Switch);
    AddItem(ToggleName+Switch, ToggleID);
}

//刷新菜单按钮方法
public void RefreshButton(String ToggleName, String ToggleID) {
    RemoveItem(ToggleName);
    AddItem(ToggleName, ToggleID);
}

//开关按钮初始化及恢复记忆状态
RefreshToggle("自动修炼", "toggleAutoPractice");
RefreshToggle("消息隐藏", "toggleMsgHide");
RefreshButton("快捷面板", "toggleQuickUI");

//开关变量恢复记忆
String AutoPractice,MsgHide,MyName;
if (getString("toggleAutoPractice", "开关") != null) {AutoPractice = "On";}
if (getString("toggleMsgHide", "开关") != null) {MsgHide = "On";}
Toast("小小修仙Beta 初始化完成!");

//开关"自动修炼"被点击
public void toggleAutoPractice(String groupUin, String userUin, int chatType) {
    SwitchToggle("自动修炼", "toggleAutoPractice");
    if (getString("toggleAutoPractice", "开关") == null) {
        AutoPractice = "Off"; //关闭自动修炼
        Toast("从现在开始，完成修炼后将停止");
    } else {
        AutoPractice = "On"; //开启自动修炼
        Toast("从现在开始，完成修炼后将进入下一轮");
    }
}

//开关"消息隐藏"被点击
public void toggleMsgHide(String groupUin, String userUin, int chatType) {
    SwitchToggle("消息隐藏", "toggleMsgHide");
    if (getString("toggleMsgHide", "开关") == null) {
        MsgHide = "Off"; //关闭消息隐藏
        Toast("从现在开始，停止减少消息，已减少的不可恢复");
    } else {
        MsgHide = "On"; //开启消息隐藏
        Toast("从现在开始，将会减少部分消息，仅自己看不到");
    }
}

//按钮"便捷菜单"被点击
public void toggleQuickUI(String groupUin, String userUin, int chatType) {
    Toast("功能还没开始做, 别太心急^_^");
}

//回复回调方法
void onMsg(Object msg){
    //初始化消息(通用)
    String UUin = msg.UserUin;//Q号
    String UName = msg.SenderNickName;//Q名
    String Text = msg.MessageContent;//消息内容
    boolean IsGroup = msg.IsGroup;//是不是群组消息
    int Type = msg.MessageType;//消息类型 (1:文字或图,2:卡片,3:图文,4:语音,5:文件,6:回复)
    
    if (IsGroup) {  //初始化消息(群组)
        String GUin = msg.GroupUin;//群号
        if(msg.IsSend) {MyName=UName;}
        
        //消息隐藏功能实现
        if (MsgHide == "On") {
            if (Text.matches("@[\\s\\S]{0,}(修炼|突破|.{0,}宗门任务.{0,})")) {
                deleteMsg(msg);
            //revokeMsg(msg);
            }
            if (UUin.equals("3889001741")) {
                if (Text.matches("@[\\s\\S]{0,}\n开始🙏修炼\\.{3}")) {
                    Toast("已开始修炼");
                    deleteMsg(msg);
                }
            }
        }
    
        //自动修炼功能实现
        if (AutoPractice == "On") {
            if (Text.matches("@[\\s\\S]{0,}本次修炼增加[0-9]{0,}修为,(回复气血：[0-9]{0,}|气血已回满！),(回复真元：[0-9]{0,}|真元已回满！)\n新手教程\\S邀请奖励")) {
            sendMsg(GUin, "", "[AtQQ=3889001741]修炼");
            }
        }
    }
    
}