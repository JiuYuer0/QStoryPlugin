// 解析函数核心代码
public void analysisVideoOfUrl(Object data, String url){
    // 判断地址是否以http开头
    if (url.startsWith("http")) {
        // 获取由解析地址回传的JSONString
        String content = Http.get("https://qsy.ludeqi.com/api/dsp/320B33804B92854B85713C7937A4F04EBDE9E9C123A4B9BF44/202037625/?url=" + URLEncoder.encode(url, "UTF-8"));
        // 解析JSON内容
        JSONObject jsonContent = new JSONObject(content);        
        // 获取状态码
        String code = jsonContent.getString("code");
        String msg = jsonContent.getString("msg");
        
        // 判断网络状态码
        if (!code.equals("200")) {//Message.sendMsgs(data,, "解析错误");
            //Message.Toast(1, msg);
            Message.sendMsgs(data, msg);
            return;
        } else {
            //获取主要内容
            JSONObject jsonData = jsonContent.getJSONObject("data");
            // 获取相关参数            
            String photoCover = jsonData.optString("photo");//封面图片地址
            String documents = jsonData.optString("title");//视频文案
                
            //判断是不是图集
            if(jsonData.optJSONArray("pics") != null){
                //图集数组
                JSONArray imgObject = jsonData.getJSONArray("pics");                               
                //提示信息                                            
                Message.sendMsgs(data,"[氚解析] " + msg + "[PicUrl=" + photoCover + "]\n[文案] " + documents + "\n[图集]正在尝试发送");   
                //判断是否需要逐条发送
                if(getBoolean("settings", "itemPic", false)){
                    //遍历发送图片
                    for (int i=0; i < imgObject.length(); i++) {
                        //获取项目并发送
                        sendPic(data.GroupUin, data.UserUin , imgObject.getString(i));
                    }
                }else{
                    //初始化一个数据存储容器
                    StringBuilder stringBuilder = new StringBuilder();            
                    stringBuilder.append(documents);
                    //遍历数据表
                    for (int i=0; i < imgObject.length(); i++) {
                        //获取项目
                        String item = imgObject.getString(i);                               
                        //拼接一下
                        stringBuilder.append("[PicUrl=").append(item).append("]\n");
                    }                                      
                    // 转换为字符串
                    String result = stringBuilder.toString();    
                    // 如果有 移除末尾空行
                    while(result.endsWith("\n")){
                        result = result.substring(0, result.length() - 1);
                    }
                    Message.sendMsgs(data, result);  
                }                  
            }else{
                //视频下载地址
                String urls = jsonData.optString("url");//下载地址
                //是否需要返回卡片
                if(!getBoolean("settings", "returnCard", false)){
                    //初始化内容
                    String content = "[氚解析] " + msg + "\n[PicUrl=" + photoCover + "]\n[文案] " + documents + "\n[分享地址] " + ((url.length()>50) ? (MsgUtil.getShortUrl(url)) : (url)) + "\n[视频直链] " + MsgUtil.getShortUrl(urls);
                    //判断是否需要返回视频
                    if(getBoolean("settings", "sendVideos", true)){ 
                        //解析提示                       
                        Message.sendMsgs(data, content + "\n[视频] 视频发送中");
                        // 发送视频
                        MsgUtil.sendVideos(data , documents, urls);
                    }else{
                        Message.sendMsgs(data, content + "\n[视频] 已关闭自动发送");                
                    }
                }else{            
                    //sendCard(data.peerUid,new String[]{"100784518","1","com.baidu.searchbox","氚解析 - If you give me 1M,I while give you the whole world."}, "氚解析", photoCover, documents, urls);
                }
            }
            //撤回卡片命令
            if(MsgUtil.isMe(data.UserUin) && data.MessageType == 2 && getBoolean("settings", "autoRecallCard", false)){
                revokeMsg(data);
            }
        }
    }else{
        Message.sendMsgs(data, "分享地址不合法");
    }
}
// QQ小世界视频解析
public void analysisQQVideoCards(Object data){
    String msgText = data.MessageContent;//内容
    //如果是卡片 而且还是QQ卡片
    if (data.MessageType == 2) {
        //实例化卡片 JSONObject 对象
        JSONObject jsonCard = new JSONObject(msgText);
        //获取卡片信息
        String appArkName = jsonCard.optString("app");   
        String prompt = jsonCard.getString("prompt");     
        if (appArkName.equals("com.tencent.video.lua")) {           
            //撤回卡片
            if(MsgUtil.isMe(data.UserUin) && getBoolean("settings", "autoRecallCard", false)){
                revokeMsg(data);
            }
            //判断一下是不是需要解析的QQ短视频卡片
            if(!prompt.startsWith("[QQ短视频]"))
                return;
            
            //获取卡片里面的Meta内容
            String jsonMeta = jsonCard.optString("meta");
            JSONObject videoString = new JSONObject(jsonMeta);
            String jsonVideo = videoString.optString("video");
            JSONObject jsonContent = new JSONObject(jsonVideo);
            
            // 视频信息
            String nickname = jsonContent.optString("nickname");//发布者
            String jumpURL = jsonContent.optString("jumpUrl");//跳转链接
            String pcJumpURL = jsonContent.optString("pcJumpUrl");//跳转链接
            String title = jsonContent.optString("title");//标题
            String preview = jsonContent.optString("preview");//封面
            String QQUin = MsgUtil.findQQByWeZone(pcJumpURL);//QQ号

            //获取地址内容并截取
            String url = Http.get(pcJumpURL);
            String contentText = url.substring(url.lastIndexOf("window.__INITIAL_STATE__=") + 25);
            String jsonString = contentText.substring(0, contentText.indexOf("(function(){var s;"));

            //将字符串转换为JSON
            JSONObject jsonData = new JSONObject(jsonString);//解析整个JSON文件
            JSONObject shareInfo = jsonData.getJSONObject("shareInfo");//获取分享信息部分的JSON内容
            

            //获取需要的内容
            String videoUrl = jsonData.optString("video");//视频地址            
            JSONObject shareWebInfo = shareInfo.getJSONObject("shareWebInfo");//获取分享地址JSON
            String shareUrl = shareWebInfo.optString("url");//获取分享地址
            
            //是否需要返回卡片
            if(!getBoolean("settings", "returnCard", false)){
                //初始化内容
                String content = "[氚解析] 解析成功\n[PicUrl=" + preview + "]\n[作者] " + nickname + "\n[发布QQ] " + QQUin + "\n[文案] " + title + "\n[分享地址] " + shareUrl;
                //判断是否需要返回视频
                if(getBoolean("settings", "sendVideos", true)){ 
                    //解析提示                       
                    Message.sendMsgs(data, content + "\n[视频] 视频源文件发送中");
                    // 发送视频
                    MsgUtil.sendVideos(data, title, videoUrl);
                }else{
                    Message.sendMsgs(data, content + "\n[视频] 已关闭自动发送");                
                }
            }else{

            }
        }                
    }
}



//解析哔哩哔哩或者其他视频卡片（理论上支持所有视频卡片
public void analysisVideoOfCards(Object data){
    //判断是不是卡片
    if (data.MessageType == 2) {
        //获取卡片信息
        JSONObject jsonCard = new JSONObject(data.MessageContent);//Message.sendMsgs(data,,"%%" +data.MessageContent);
        String appArkName = jsonCard.optString("app");//获取App类型
        String prompt = jsonCard.optString("prompt");//获取App类型
        //判断是不是腾讯小程序卡片
        if (appArkName.equals("com.tencent.miniapp_01")) {
            //获取详细数据
            JSONObject metaJson = jsonCard.getJSONObject("meta");
            JSONObject detail = metaJson.getJSONObject("detail_1");          
            //获取地址
            String qqDocUrl = detail.optString("qqdocurl");
            //判断地址是否以http开头
            if (qqDocUrl.startsWith("http")) {                
                //哔哩哔哩链接重定向
                if(qqDocUrl.contains("b23.tv")){
                    analysisVideoOfUrl(data, Http.redirect(qqDocUrl));
                }else{
                    analysisVideoOfUrl(data, qqDocUrl);
                }    
            }
        }else{
            //非小程序类型的哔哩哔哩卡片
            if(appArkName.equals("com.tencent.structmsg") && prompt.contains("哔哩哔哩")){
                //获取详细数据
                JSONObject metaJson = jsonCard.getJSONObject("meta");
                JSONObject newsJson = metaJson.getJSONObject("news");    
                //获取地址
                String qqDocUrl = newsJson.optString("jumpUrl");
                //判断地址是否以http开头
                if (qqDocUrl.startsWith("http")) {
                    //哔哩哔哩链接重定向
                    if(qqDocUrl.contains("b23.tv")){
                        analysisVideoOfUrl(data, Http.redirect(qqDocUrl));
                    }else{
                        analysisVideoOfUrl(data, qqDocUrl);
                    }                    
                }               
            }                
        }         
    }
}

//解析卡片链接
public void analysisUrlOfCard(Object data){
    //判断是不是卡片
    if (data.MessageType == 2) {
        //获取卡片信息
        JSONObject jsonCard = new JSONObject(data.MessageContent);//Message.sendMsgs(data,,"%%" +data.MessageContent);
        String appArkName = jsonCard.optString("app");//获取App类型
        //判断是不是非视频卡片卡片
        if (appArkName.equals("com.tencent.structmsg") || appArkName.equals("com.tencent.troopsharecard") || appArkName.equals("com.tencent.contact.lua") || appArkName.equals("com.tencent.tuwen.lua") || appArkName.equals("com.tencent.eventshare.lua")) {
            //初始化一下参数
            String uin = "", desc = "", ctime = "", sendMsg = "", preview = "", title = "";
            
            //获取卡片数据
            String view = jsonCard.optString("view");//获取视图类型
            JSONObject metaJson = jsonCard.getJSONObject("meta");
            JSONObject newsJson = metaJson.getJSONObject(view);//解析实际卡片数据
            //解析卡片内容
            String jumpUrl = newsJson.optString("jumpUrl");
            //判断特地卡片
            if (appArkName.equals("com.tencent.structmsg") || appArkName.equals("com.tencent.tuwen.lua") || appArkName.equals("com.tencent.eventshare.lua")) {
                preview = newsJson.optString("preview");
                title = newsJson.optString("title");             
            }
            //判断卡片类型解析特殊数据并拼接
            if (appArkName.equals("com.tencent.structmsg")) {
                //配置解析数据
                uin = newsJson.optString("uin");
                desc = newsJson.optString("desc");
                ctime = newsJson.optString("ctime");
                sendMsg = "[PicUrl=" + preview + "]\n[标题] " + title + "\n[创建者] " + uin + "\n[创建时间] " + timeStamp2DateTime(Long.parseLong(ctime + "000")) + "\n[卡片简介] " + desc + "\n[卡片地址] " + jumpUrl;
            }else{
                if(appArkName.equals("com.tencent.tuwen.lua")){
                    //配置解析数据
                    desc = newsJson.optString("desc");
                    ctime = newsJson.optString("ctime");
                    sendMsg = "[PicUrl=" + preview + "]\n[标题] " + title + "\n[创建时间] " + timeStamp2DateTime(Long.parseLong(ctime + "000")) + "\n[卡片简介] " + desc + "\n[卡片地址] " + jumpUrl;
                }else{
                    if(appArkName.equals("com.tencent.contact.lua") || appArkName.equals("com.tencent.troopsharecard")) {
                        preview = newsJson.optString("avatar");
                        nickname = newsJson.optString("nickname");
                        contact = newsJson.optString("contact");
                        String prompt = jsonCard.optString("prompt");
                        //解析配置数据
                        JSONObject configJson = jsonCard.getJSONObject("config");
                        ctime = configJson.optString("ctime");
                        //拼接字符串
                        sendMsg = "[PicUrl=" + preview + "]\n[标题] " + nickname + "\n[信息] " + contact + "-" + prompt + "\n[创建时间] " + timeStamp2DateTime(Long.parseLong(ctime + "000")) +"\n[卡片地址] " + MsgUtil.getShortUrl(jumpUrl);                        
                    }else{
                        //解析配置数据
                        JSONObject configJson = jsonCard.getJSONObject("config");
                        ctime = configJson.optString("ctime");
                        jumpUrl = newsJson.optString("jumpURL");
                        String prompt = jsonCard.optString("prompt");
                        sendMsg = "[PicUrl=" + preview + "]\n[标题] " + prompt + "\n[创建时间] " + timeStamp2DateTime(Long.parseLong(ctime + "000")) + "\n[卡片简介] " + title + "\n[卡片地址] " + jumpUrl;
                    }
                }
            }
            
            //不解析自家的链接卡片
            if(!jumpUrl.contains("hainacloud.cc") && !data.MessageContent.contains("氚卡片")){                
                //判断一下是不是抖音/快手视频
                if(jumpUrl.contains("v.kuaishou.com") || jumpUrl.contains("v.douyin.com")){
                    //解析视频
                    analysisVideoOfUrl(data, jumpUrl);
                }else{
                    //发送解析结果
                    Message.sendMsgs(data, sendMsg);//发送解析内容
                }
                //撤回命令
                if(MsgUtil.isMe(data.UserUin) && getBoolean("settings", "autoRecallMsg", false)){
                    revokeMsg(data);
                }
            }else{
                Message.Toast(1, "不支持的卡片类型");
            }            
        }else{
            //腾讯地图
            if(appArkName.equals("com.tencent.map")){
                //获取信息
                JSONObject metaJson = jsonCard.getJSONObject("meta");
                JSONObject newsJson = metaJson.getJSONObject("Location.Search");//解析实际卡片数据
                
                //解析卡片内容
                String address = newsJson.optString("address");
                String name = newsJson.optString("name");
                String lat = newsJson.optString("lat");
                String lng = newsJson.optString("lng");
                String from_account = newsJson.optString("from_account");
                
                //不解析自家的链接卡片
                if(!data.MessageContent.contains("氚卡片")){            
                    Message.sendMsgs(data, "[经度] " + lng +"\n[纬度] " + lat + "\n[创建者] " + from_account + "\n[地址] " + address + " " + name);//发送解析内容
                }else{
                    Message.Toast(1, "不支持的卡片类型");
                }
            }
            /*
            //腾讯游戏
            if(appArkName.equals("com.tencent.gamecenter.gameshare")){
                //获取信息
                JSONObject metaJson = jsonCard.getJSONObject("meta");
                JSONObject extraJson = jsonCard.getJSONObject("extra");
                JSONObject newsJson = metaJson.getJSONObject("shareData");//解析实际卡片数据
                JSONArray buttonsArray = newsJson.getJSONArray("buttons");
                
                //解析卡片内容
                String url = newsJson.optString("url");
                String type = newsJson.optString("type");
                String uin = extraJson.optString("uin");                
                String prompt = jsonCard.optString("prompt");
                
                
                //初始化一个数据存储容器
                StringBuilder sb = new StringBuilder();        
                //遍历一下按钮
                for (int i=0; i < buttonsArray.length(); i++) {
                    //获取项目
                    JSONObject buttonsObject = buttonsArray.getJSONObject(i);
                    //解析每一条内容
                    String url = buttonsObject.getString("url");
                    String text = buttonsObject.getString("text");
                    
                    //拼接一下字符串
                    sb.append("\n[").append(text).append("] ").append(MsgUtil.getShortUrl(url));
                }
                
                //不解析自家的链接卡片
                if(!data.MessageContent.contains("氚卡片")){            
                    Message.sendMsgs(data, "[PicUrl=" + url +"]\n[简介] " + prompt + "\n[创建者] " + uin + sb.toString());//发送解析内容
                }else{
                    Message.Toast(1, "不支持的卡片类型");
                }
            }
            */
        }
    }
}    
    

// 解析命令判断函数
public void analysisVideos(Object data){
    //判断输入内容
    if(data.MessageContent.startsWith("#解析链接")){
        //撤回命令
        if(MsgUtil.isMe(data.UserUin) && getBoolean("settings", "autoRecallMsg", false)){
            revokeMsg(data);
        }    
        //分割字符串获取地址
        String url = data.MessageContent.substring(5);
        //去除空格
        while(url.startsWith(" ")){
            url = url.substring(1);
        }         
        analysisVideoOfUrl(data, url);
    }else{
        //判断消息是不是回复类型
        if(data.MessageType == 6 && data.MessageContent.equals("解析")){  
            //回复信息对象
            Object recordMsg = data.RecordMsg;
            //判断解析内容是不是URL
            if(recordMsg.MessageType == 1 || recordMsg.MessageType == 3){
                //是否以http开头
                if(recordMsg.MessageContent.startsWith("http")){
                    //解析链接
                    analysisVideoOfUrl(recordMsg, recordMsg.MessageContent);
                }else{
                    //判断是不是表情包
                    if(recordMsg.MessageContent.contains("[PicUrl=")){
                        //初始化内容
                        String content = MsgUtil.matchPicUrl(recordMsg.MessageContent);
                        //对图片进行操作
                        if(content.startsWith("http")){
                            Message.sendMsgs(data, "[PicUrl=" + content + "]\n[氚解析] 图片解析\n[昵称] " + recordMsg.SenderNickName + "\n[发送者] " + recordMsg.UserUin + "\n[直链] " + MsgUtil.getShortUrl(content));
                        }else{
                            //提示警告
                            Message.Toast(1, "不支持的消息类型");
                        }
                    }else{
                        // 正则表达式，匹配http后面的所有字符
                        Matcher matcher = Pattern.compile("http.*").matcher(recordMsg.MessageContent);
                        //判断一下是否找到
                        if (matcher.find()) {
                            //解析链接
                            analysisVideoOfUrl(recordMsg, matcher.group());
                        }else{
                            //提示警告
                            Message.Toast(1, "不支持的消息类型");
                        }
                    }    
                }
            }else{
                //判断是不是卡片
                if(recordMsg.MessageType == 2){
                    //解析卡片
                    analysisQQVideoCards(recordMsg);
                    analysisVideoOfCards(recordMsg);
                    analysisUrlOfCard(recordMsg);
                }else{
                    //提示警告
                    Message.Toast(1, "不支持的消息类型");
                }
            }
            //撤回命令
            if(MsgUtil.isMe(data.UserUin) && getBoolean("settings", "autoRecallMsg", false)){
                revokeMsg(data);
            }
        }else{
            //是不是卡片，是否需要解析
            if(getBoolean("settings", "autoAnalysis", false)){        
                analysisQQVideoCards(data);
                analysisVideoOfCards(data);
            }
        }
    }    
}
