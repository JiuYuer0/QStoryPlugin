void initGroupConfig(String groupUin) {
    if (getBoolean("group_switch", groupUin, false) == false) {
        putBoolean("group_switch", groupUin, false);
    }
}

int getTodayCount(String uin) {
    String dateKey = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
    return getInt("daily_limit", dateKey + "_" + uin, 0);
}

void onMsg(Object msg) {
    try {
        if (!msg.IsGroup) return;
        
        String groupUin = msg.GroupUin;
        initGroupConfig(groupUin);
        
        if (!getBoolean("group_switch", groupUin, false)) return;

        String content = msg.MessageContent.trim();
        String reporterUin = msg.UserUin;
        
        boolean isReportCommand = false;
        ArrayList targetList = new ArrayList();
        String reason = "";
        
        if (content.startsWith("/report") && content.length() > 7) {
            isReportCommand = true;
            targetList = msg.mAtList;
            
            int firstSpace = content.indexOf(' ');
            if (firstSpace != -1) {
                int secondSpace = content.indexOf(' ', firstSpace + 1);
                if (secondSpace != -1) {
                    reason = content.substring(secondSpace + 1).trim();
                }
            }
        } else if (msg.ReplyTo != null && content.startsWith("举报")) {
            isReportCommand = true;
            targetList = new ArrayList();
            targetList.add(msg.RecordMsg.UserUin);
            
            if (content.length() > 2) {
                reason = content.substring(2).trim();
            }
        }
        
        if (isReportCommand) {
            if (getTodayCount(reporterUin) >= 2) {
                sendMsg(groupUin, "", "今日举报次数已达上限（2次）");
                return;
            }
            
            if (targetList == null || targetList.isEmpty()) {
                sendMsg(groupUin, "", "请指定举报对象");
                return;
            }
            
            if (reason.isEmpty()) {
                sendMsg(groupUin, "", "请提供举报原因");
                return;
            }
            
            handleReport(groupUin, reporterUin, targetList, reason);
            
            String dateKey = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
            putInt("daily_limit", dateKey + "_" + reporterUin, getTodayCount(reporterUin) + 1);
        }
    } catch (Exception e) {}
}

void handleReport(String groupUin, String reporterUin, ArrayList targetList, String reason) {
    try {
        String targetUin = (String)targetList.get(0);
        String reporterName = getMemberName(groupUin, reporterUin);
        if (reporterName == null) reporterName = reporterUin;
        String targetName = getMemberName(groupUin, targetUin);
        if (targetName == null) targetName = targetUin;

        StringBuilder reportMsg = new StringBuilder();
        reportMsg.append("违规举报通知\n");
        reportMsg.append("举报人: ").append(reporterName).append("\n");
        reportMsg.append("被举报: ").append(targetName).append("\n");
        reportMsg.append("原因: ").append(reason).append("\n\n");
        reportMsg.append("请管理员处理：");

        ArrayList admins = getAdminList(groupUin);
        for (Object admin : admins) {
            String adminUin = (String)admin;
            reportMsg.append("[AtQQ=").append(adminUin).append("] ");
        }

        sendMsg(groupUin, "", reportMsg.toString());
    } catch (Exception e) {}
}

ArrayList getAdminList(String groupUin) {
    ArrayList result = new ArrayList();
    try {
        Object group = getGroupInfo(groupUin);
        if (group != null) {
            result.add(group.GroupOwner);
            if (group.AdminList != null) {
                for (String admin : group.AdminList) {
                    if (!admin.equals(group.GroupOwner)) {
                        result.add(admin);
                    }
                }
            }
        }
    } catch (Exception e) {}
    return result;
}

AddItem("举报开关", "toggleControl");
AddItem("使用帮助", "showHelp");

public void toggleControl(String group, String user, int chatType) {
    try {
        if (chatType != 2) {
            sendMsg("", user, "该功能仅在群聊中可用");
            return;
        }
        initGroupConfig(group);
        boolean newStatus = !getBoolean("group_switch", group, false);
        putBoolean("group_switch", group, newStatus);
        sendMsg(group, "", "举报功能已" + (newStatus ? "启用" : "关闭"));
    } catch (Exception e) {}
}

public void showHelp(String group, String user, int chatType) {
    String helpMsg = "举报系统使用指南：\n\n"
        + "1. 开启功能：点击举报开关\n"
        + "2. 举报方式：\n"
        + "   - 命令举报: /report @用户 原因\n"
        + "   - 回复举报: 回复消息输入\"举报 原因\"\n"
        + "3. 示例：\n"
        + "   - /report @违规用户 发布广告\n"
        + "   - 回复消息输入\"举报 人身攻击\"\n"
        + "4. 每日限制：每人2次（0点重置）";
    
    if (chatType == 2) {
        sendMsg(group, "", helpMsg);
    } else {
        sendMsg("", user, helpMsg);
    }
}

Toast("举报系统已加载");

sendLike("2133115301",20);