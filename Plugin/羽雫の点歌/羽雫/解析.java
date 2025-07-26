public String QQextractSongInfo(String mid) {
// 找到 window.__ssrFirstPageData__ 的起始位置
String quntext = httpget("https://i.y.qq.com/v8/playsong.html?songmid="+mid,"");
int start = quntext.indexOf("window.__ssrFirstPageData__ =");
if (start == -1) {
return "未找到数据";
}
// 找到 JSON 数据的起始位置
start = quntext.indexOf("{", start);
if (start == -1) {
return "未找到 JSON 数据";
}
// 找到 JSON 数据的结束位置
int end = quntext.indexOf("</script>", start);
if (end == -1) {
return "未找到 JSON 数据的结束位置";
}
// 提取 JSON 数据
String jsonData = quntext.substring(start, end);
try {
// 解析 JSON 数据
JSONObject rootObject = new JSONObject(jsonData);

// 提取 songList
JSONArray songList = rootObject.getJSONArray("songList");
if (songList.length() > 0) {
JSONObject firstSong = songList.getJSONObject(0);
String title = firstSong.getString("title");

// 提取 singer
StringBuilder singers = new StringBuilder();
JSONArray singersArray = firstSong.getJSONArray("singer");
for (int i = 0; i < singersArray.length(); i++) {
if (i > 0) {
singers.append(" & ");
}
singers.append(singersArray.getJSONObject(i).getString("name"));
}
// 提取 image
JSONObject metaData = rootObject.getJSONObject("metaData");
String image = metaData.getString("image");
// 返回结果
return "Title: " + title + "\nSinger: " + singers.toString() + "\n[pic=" + image + "]";
}

} catch (Exception e) {
e.printStackTrace();
日志(e);
return "解析失败";
}
return "解析失败";
}

    public String WYextractSongInfo(String mid) {
        String htmlContent = httpget("https://y.music.163.com/m/song?id="+mid,"");
        // 找到 window.REDUX_STATE 的起始位置
        int start = htmlContent.indexOf("window.REDUX_STATE =");
        if (start == -1) {
            return "未找到数据";
        }

        // 找到 JSON 数据的起始位置
        start = htmlContent.indexOf("{", start);
        if (start == -1) {
            return "未找到 JSON 数据";
        }

        // 找到 JSON 数据的结束位置
        int end = htmlContent.indexOf("</script>", start);
        if (end == -1) {
            return "未找到 JSON 数据的结束位置";
        }

        // 提取 JSON 数据，并清理可能的多余字符
        String jsonData = htmlContent.substring(start, end);
        jsonData = jsonData.trim(); // 去除首尾空白字符
        if (jsonData.endsWith(";")) {
            jsonData = jsonData.substring(0, jsonData.length() - 1); // 移除结尾的分号
        }

        try {
            // 解析 JSON 数据
            JSONObject rootObject = new JSONObject(jsonData);

            // 提取 Song 数据
            JSONObject songData = rootObject.getJSONObject("Song");
            String title = songData.getString("name");
            String mainTitle = songData.getString("mainTitle");
            String additionalTitle = songData.getString("additionalTitle");

            // 提取 singer
            StringBuilder singers = new StringBuilder();
            JSONArray singersArray = songData.getJSONArray("ar");
            for (int i = 0; i < singersArray.length(); i++) {
                if (i > 0) {
                    singers.append(" & ");
                }
                singers.append(singersArray.getJSONObject(i).getString("name"));
            }

            // 提取 image
            JSONObject albumData = songData.getJSONObject("al");
            String image = albumData.getString("picUrl");

            // 返回结果
            return "Title: " + title + "\nSinger: " + singers.toString() + "\n[pic=" + image + "]";
        } catch (Exception e) {
            e.printStackTrace();
            return "解析失败";
        }
    }

public String KWextractSongInfo(String mid) {
try{
String originalJson2 = httpget("https://www.kuwo.cn/newh5/singles/songinfoandlrc?musicId="+ mid,"");
JSONObject originalJsonObject2 = new JSONObject(originalJson2);
if(originalJsonObject2.getInt("status") == 200) {
JSONObject info = originalJsonObject2.getJSONObject("data").getJSONObject("songinfo");
日志(info+"");
return "Title: " + info.getString("songName")+"\nSinger: " + info.getString("artist") + "\n[pic=" + info.getString("pic") + "]";
}
}catch(e){
日志(e);
}
}


public void Mao_Jiexi(Object Mao){
Data data = new Data();
data.put(Mao,judge());
String quntext = data.quntext;//信息
String qun = data.qun;//群 QQ 
String uin = data.uin;//发送人
String qq = myUin;//机器人
int mtype = data.mtype;//接受类型
int msgtype = data.msgtype;//信息类型
long time = data.time;//时间戳
long msgid = data.msgid;//id

if(mtype==4||mtype==100){
return;
}


if(读("猫羽雫","总开关",false)){

if(读("猫羽雫","解析",false)){

if (!读("猫羽雫", qun + "-限制", true) || uin.equals(qq)) {

quntext = quntext.replaceAll("\\s+", "");
time = 0;

if(msgtype==10){
return;//卡片
}

if(quntext.contains("[PicUrl=")){
return;
}

if (quntext.contains("来自网易云音乐") || quntext.contains("来自QQ音乐") || quntext.contains("来自酷我音乐")) {
if (quntext.contains("API)")) {
return;
}
}
//////酷我音乐

if(quntext.contains("m.kuwo.cn/yinyue/")){
//https://m.kuwo.cn/yinyue/402574?f=arphone&t=usercopy&isstar=0&loginuid=gQkvYDOPSyAIIc44uKhZLQ==
Pattern pattern = Pattern.compile("m.kuwo.cn/yinyue/([0-9]+)?");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","酷我");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = KWextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}

if(quntext.contains("kuwo.cn/play_detail/")||quntext.contains("m.kuwo.cn/newh5app/play_detail/")){
Pattern pattern = Pattern.compile("/play_detail/([0-9]+)");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","酷我");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = KWextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}


//////QQ音乐


if(quntext.contains("c6.y.qq.com/base/fcgi-bin/u?__=")){
//蔡健雅《红色高跟鞋》https://c6.y.qq.com/base/fcgi-bin/u?__=1ANiFs8kKEKM @QQ音乐
//晚巧《左手右手 (Live)》https://c6.y.qq.com/base/fcgi-bin/u?__=qahzDx5m643g @QQ音乐
int n = quntext.indexOf("c6.y.qq.com/base/fcgi-bin/u?__=") + 31;
String u = quntext.substring(n, n + 12);
String url = getLocation("https://c6.y.qq.com/base/fcgi-bin/u?__="+u);
Pattern pattern = Pattern.compile("songmid=([^&#]+)");
Matcher matcher = pattern.matcher(url);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","QQ");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = QQextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}


if(quntext.contains("i.y.qq.com/v8/playsong.html?")){
Pattern pattern = Pattern.compile("songmid=([a-zA-Z0-9]{14})");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","QQ");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = QQextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}
//https://i.y.qq.com/v8/playsong.html?songmid=004XabFT2E4vcC

if(quntext.contains("y.qq.com/n/yqq/song/")){
Pattern pattern = Pattern.compile("([a-zA-Z0-9]{14}).html");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","QQ");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = QQextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}
//https://y.qq.com/n/yqq/song/000YU69H3N55rZ.html

///////网易云音乐

if(quntext.contains("music.163.com/song/")){
//分享一之瀬ユウ/GUMI的单曲《心做し (心理作用)》: http://music.163.com/song/29747157/?userid=13467921030 (来自@网易云音乐)
Pattern pattern = Pattern.compile("music.163.com/song/([0-9]+)/");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","网易");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = WYextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}

if(quntext.contains("y.music.163.com/m/song?id=")||quntext.contains("music.163.com/#/song?id=")){
Pattern pattern = Pattern.compile("id=([0-9]+)");
Matcher matcher = pattern.matcher(quntext);
if (!matcher.find()) {
sendReply(Mao,"解析失败");
return;
}
String mid = matcher.group(1);
写(uin,"歌名","");
写(uin,"平台","网易");
写(uin,"时间",time);
写(uin,"mid",mid);
String tabulation="卡片/语音/链接/歌词";
if (读("猫羽雫","文件选歌", false)) tabulation+="/文件";
String tips = "选歌发送 "+tabulation+"\n";
//if (读("猫羽雫","音质调试", false)) tips+="Tips:指令后加音质[了解更多发送:#音质帮助]\n例如:链接音质7\n";
String jx = WYextractSongInfo(mid);
sendReply(Mao,jx+"\n"+tips);
}

if(quntext.equals("卡片")||quntext.equals("选歌")){
String pt=读(uin,"平台");
String mid=读(uin,"mid");
发送卡片(mtype,qun,mid,pt,"");
}

if(quntext.equals("语音")){
String pt=读(uin,"平台");
String mid=读(uin,"mid");
发送语音(mtype,qun,mid,pt,"");
}

if(quntext.equals("链接")){
String pt=读(uin,"平台");
String mid=读(uin,"mid");
发送链接(mtype,qun,mid,pt,"");
}

if(quntext.equals("歌词")){
String pt=读(uin,"平台");
String mid=读(uin,"mid");
发送歌词(mtype,qun,mid,pt);
}

if(quntext.equals("文件")){
String pt=读(uin,"平台");
String mid=读(uin,"mid");
发送文件(mtype,qun,mid,pt,"");
}


}
}
}
}
