try 
{
load(AppPath+"/请求.java");
load(AppPath+"/匹配.java");
load(AppPath+"/发送.java");
load(AppPath+"/解密.java");
load(AppPath+"/下载.java");
load(AppPath+"/弹窗.java");
}catch(e){Toast(e);}
addItem("开/关本聊天小说总开关", "自身");
addItem("开/关本聊天小说他人可用开关", "他人");
public void 自身(String kg) {
if("0".equals(getString(kg,"小说总开关"))){
putString(kg, "小说总开关", null);
Toast("本聊天小说总开关已关闭");
}else{
putString(kg, "小说总开关", "0");
QQToast("本聊天小说总开关已开启",2);
}
}
public void 他人(String kg) {
if ("0".equals(getString(kg, "小说他人可用开关"))) {
putString(kg, "小说他人可用开关", null);
Toast("本聊天小说他人可用开关已关闭");
}else{
putString(kg, "小说他人可用开关", "0");
QQToast("本聊天小说他人可用开关已开启",2);
}
}
addItem("脚本口令","cj");
public void cj(String qun){
setTips("脚本口令","首先打开脚本悬浮窗开关\n\n口令:\n预计1章0.1–5秒左右(根据手机性能)\n1、七猫搜+小说名\n2、看小说+序号\n3、下载小说+序号#章节【推荐】\n(下载小说1#3或下载小说3#1-50)\n4、全部下载小说+序号【可能漏章节】\n5、快速下载小说+序号#单章序号【推荐】\n(快速下载小说1#3或快速下载小说3#1-50)\n6、当前小说\n7、整合小说+当前小说口令提示的小说序号\n[下载小说，整合口令均为自身触发]\n[上述口令都是在七猫搜的前提下]\n8、我要赞助【动力】\n\nPS:单章的快速和非快速下载如若缺失章节，再次发送口令(章节范围一致或更大)即可下载补漏。\n     下载不了时可进脚本群艾特管理，告知情况，我去网站验证一下验证码即可，若验证过后还是下不了，请提供小说名和章节数(即哪个章节下不了)\n\n❗声明:\n1、本脚本只用于技术交流，请勿用作商业用途。\n2、如若使用本脚本造成的任何法律犯罪行为均需使用者自行承担，与脚本作者无关。\n3、侵权联系删。");}
if(!"1".equals(getString("初次","初"))){	SET("首次说明","步骤如下：\n\n1、先打开脚本悬浮窗开关\n2、有相应的口令开关和脚本口令说明可点击\n3、因cookie问题，脚本可能需及时更新\n有问题的话，应该是逻辑错误或接口请求问题，可以加群反馈\n\n❗声明:\n1、本脚本只用于技术交流，请勿用作商业用途。\n2、如若使用本脚本造成的任何法律犯罪行为均需使用者自行承担，与脚本作者无关。\n3、侵权联系删。");putString("初次","初","1");}
String 赞助1="/storage/emulated/0/Pictures/YMZ.png";
String 赞助2="/storage/emulated/0/Pictures/WeiXin/YMZ.png";
int CORE_COUNT = Runtime.getRuntime().availableProcessors();
int MAX_THREADS = CORE_COUNT * 1;
ThreadPoolExecutor ext = new ThreadPoolExecutor(
    MAX_THREADS,
    MAX_THREADS,
    1L,
    TimeUnit.SECONDS,
    new LinkedBlockingQueue(500),
    new ThreadPoolExecutor.CallerRunsPolicy()
);
int K2 = Math.min(CORE_COUNT * 4, 50);
ForkJoinPool forkJoinPool = new ForkJoinPool(K2);
String 小说=AppPath+"/数据/小说/小说数据/";
public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String uin = msg.UserUin;
    String qun = msg.GroupUin;
    String qq = MyUin;
    int type=msg.msg.chatType;
    int mtype=msg.msg.msgType;
    String Qun="";        
    long sUin = msg.msg.peerUin;
    String Uin = String.valueOf(sUin+"");
    if(type==2){Qun=qun;}else{Qun=Uin;}
if (( "0".equals(getString(Qun, "小说总开关")) && uin.equals(MyUin)) || ( "0".equals(getString(Qun, "小说总开关")) && "0".equals(getString(Qun, "小说他人可用开关")))) {

//https://www.tuofa.org/BookFiles/Html/{{bookId}}/{{chapterId}}.html
if (text.startsWith("七猫搜")) { 
int stx = text.indexOf("七猫搜") + 3;
String keyword = text.substring(stx).trim();
if (keyword.isEmpty()) return;
String jb = Aget("https://www.qimao.com/search/index/?keyword=" + keyword);
if (jb.isEmpty()) {
Toast("未获取到搜索结果");return;
}
JSONArray bookArray = new JSONArray();
String pattern = "<li>\\s*<div class=\"pic\">\\s*<a href=\"/shuku/(\\d+)/\">.*?<img\\s+src=\"([^\"]+)\".*?class=\"s-tit\"><a\\s+href=\"/shuku/\\1/\">(.*?)</a>.*?class=\"s-tags qm-tags clearfix\".*?>(.*?)&nbsp;&nbsp;&nbsp;&nbsp;(.*?)&nbsp;&nbsp;&nbsp;&nbsp;(.*?)</span>.*?class=\"s-des\">(.*?)</span>.*?<em>作&nbsp;&nbsp;&nbsp;&nbsp;者：</em><a.*?>(.*?)</a>.*?<em>更新至：</em><a href=\"([^\"]+)\">(.*?)</a>";
Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
Matcher matcher = regex.matcher(jb);
while (matcher.find()) {
String bookId = matcher.group(1).trim();
String imageUrl = matcher.group(2).trim();
String title = cleanHtml(matcher.group(3).trim());
String tags = cleanHtml(matcher.group(4).trim());
String status = cleanHtml(matcher.group(5).trim());
String wordCount = cleanHtml(matcher.group(6).trim());
String description = cleanHtml(matcher.group(7).trim().replaceAll("\\s+", " "));
String author = cleanHtml(matcher.group(8).trim());
String lastChapterLink = matcher.group(9).trim();
String lastChapter = cleanHtml(matcher.group(10).trim());
JSONObject bookObject = new JSONObject();
bookObject.put("book_id", bookId);
bookObject.put("image_url", imageUrl);
bookObject.put("title", title);
bookObject.put("tags", tags);
bookObject.put("status", status);
bookObject.put("word_count", wordCount);
bookObject.put("description", description);
bookObject.put("author", author);
bookObject.put("last_chapter_link", lastChapterLink);
bookObject.put("last_chapter", lastChapter);
bookArray.put(bookObject);
}
JSONObject resultObject = new JSONObject();
resultObject.put("keyword", keyword);
resultObject.put("book_count", bookArray.length());
resultObject.put("data", bookArray);
String jsonResult = resultObject.toString();
写(AppPath + "/数据/小说/聊天数据/" + Qun + "/" + uin + "搜索数据", jsonResult);
String jg = formatBookNames(jsonResult);
YMZ(Qun, jg, msg, type);
}

if (text.startsWith("看小说")) {
int stx = text.indexOf("看小说") + 3;
String dz = text.substring(stx).trim();
if (dz.isEmpty() || !dz.matches("\\d+")) return;
String jsonData = 读(AppPath + "/数据/小说/聊天数据/" + Qun + "/" + uin + "搜索数据");
if (jsonData.isEmpty()) {
Toast("暂未搜索");return;
}
JSONObject responseObject = new JSONObject(jsonData);
JSONArray dataArray = responseObject.getJSONArray("data");
int dzIndex;
try {
dzIndex = Integer.parseInt(dz) - 1;
} catch (NumberFormatException e) {
Toast("无效的序号");return;
}
if (dzIndex < 0 || dzIndex >= dataArray.length()) {
Toast("无效的序号");
return;}
JSONObject bookObject = dataArray.getJSONObject(dzIndex);
String bookId = bookObject.optString("book_id", "未知");
String bookName = cleanHtml(bookObject.optString("title", "未知"));
String author = cleanHtml(bookObject.optString("author", "未知"));
String thumbUrl = bookObject.optString("image_url", "");
String wordCount = cleanHtml(bookObject.optString("word_count", "未知"));
String tags = cleanHtml(bookObject.optString("tags", "无标签"));
String lastChapter = cleanHtml(bookObject.optString("last_chapter", "无"));
String status = cleanHtml(bookObject.optString("status", "未知"));
String description = cleanHtml(bookObject.optString("description", "无简介"));
String reply = "[PicUrl=" + thumbUrl + "]"
        + "书名: 《" + bookName + "》\n"
        + "作者: " + author + "\n"
        + "标签: " + tags + "\n"
        + "字数: " + wordCount +"("+status+ ")\n"
        + "最新章节: " + lastChapter;
YMZ(Qun, reply, msg, type);
Thread.sleep(1500);
YMZ(Qun, "简介：" + description, msg, type);
}

if (qq.equals(uin)) {
if (text.startsWith("下载小说")) {
int stx = text.indexOf("下载小说") + 4;
String dz = text.substring(stx).trim();
if (dz.isEmpty() || !dz.matches("\\d+.*")) return;
String jb = 读(AppPath + "/数据/小说/聊天数据/" + Qun + "/" + uin + "搜索数据");
if (jb.isEmpty()) {
Toast("暂未搜索");return;
}
JSONObject responseObject = new JSONObject(jb);
JSONArray dataArray = responseObject.getJSONArray("data");
int dzIndex;
try {
dzIndex = Integer.parseInt(dz.split("#")[0].trim()) - 1;
} catch (NumberFormatException e) {
Toast("无效的序号");
return;}
if (dzIndex < 0 || dzIndex >= dataArray.length()) {
Toast("无效的序号");
return;}
JSONObject bookObject = dataArray.getJSONObject(dzIndex);
String bookId = bookObject.optString("book_id", "");
String bookName = cleanHtml(bookObject.optString("title", "未知"));
if (bookId.isEmpty()) {
Toast("未找到小说ID");
return;
}
String jbb = Aget("https://www.qimao.com/api/book/chapter-list?book_id=" + bookId);
JSONObject chapterResponse = new JSONObject(jbb);
JSONArray chapters = chapterResponse.getJSONObject("data").getJSONArray("chapters");
List chapterIds = new ArrayList();
List chapterTitles = new ArrayList();
for (int i = 0; i < chapters.length(); i++) {
JSONObject chapter = chapters.getJSONObject(i);
chapterIds.add(chapter.optString("id", ""));
chapterTitles.add(cleanHtml(chapter.optString("title", "未知章节")));
}
int startChapter, endChapter;
try {
String[] range = dz.split("#")[1].split("-");
startChapter = Integer.parseInt(range[0].trim());
endChapter = (range.length == 2) ? Integer.parseInt(range[1].trim()) : startChapter;
} catch (NumberFormatException e) {
Toast("无效的章节范围");return;}
int totalChapters = chapterIds.size();
if (startChapter < 1 || endChapter > totalChapters || startChapter > endChapter) {
Toast("章节范围超出总章节数: " + totalChapters);
return;}
YMZ(Qun, "《" + bookName + "》\n预计下载时间" + (endChapter - startChapter + 1) * 3 + "秒。", msg, type);
int totalIndex = 0;
for (int i = startChapter - 1; i < endChapter && i < totalChapters; i++) {
String chapterId = chapterIds.get(i);
String chapterTitle = chapterTitles.get(i);
String chapterFilePath = AppPath + "/数据/小说/小说数据/" + bookName + "/第" + (i + 1) + "章.txt";
File chapterFile = new File(chapterFilePath);
if (chapterFile.exists()) {
//Toast("第" + (i + 1) + "章已存在，跳过下载。");
continue;}
String url = SimpleApiUrlBuilder.buildUrl(bookId, chapterId);
String result = Bget(url);
if (result != null) {
try {
JSONObject re = new JSONObject(result);
result = re.getJSONObject("data").getString("content");
result = 缩进(Aesdecrypt(result));
String jg = chapterTitle + "\n\n" + result;
写(chapterFilePath, jg);
totalIndex++;
} catch (Exception e) {
Toast("第" + (i + 1) + "章内容解析失败: " + e.getMessage());
YMZ(Qun, "《" + bookName + "》\n"+"除报错章节，其余章节已下载完成。", msg, type);
}
} else {
Toast("请求失败 " + result);
}
}
YMZ(Qun, "《" + bookName + "》\n" + totalIndex + "章已下载完成。\n\n[请以路径实际文件数量为准", msg, type);
}
}

if (qq.equals(uin)) {
if (text.startsWith("快速下载小说")) {
int stx = text.indexOf("快速下载小说") + 6;
String dz = text.substring(stx).trim();
if (dz.isEmpty() || !dz.matches("\\d+.*")) return;
String jb = 读(AppPath + "/数据/小说/聊天数据/" + Qun + "/" + uin + "搜索数据");
if (jb.isEmpty()) {
Toast("暂未搜索");
return;
}
JSONObject responseObject = new JSONObject(jb);
JSONArray dataArray = responseObject.getJSONArray("data");
int dzIndex;
try {
dzIndex = Integer.parseInt(dz.split("#")[0].trim()) - 1;
} catch (NumberFormatException e) {
Toast("无效的序号");
return;
}
if (dzIndex < 0 || dzIndex >= dataArray.length()) {
Toast("无效的序号");
return;
}
JSONObject bookObject = dataArray.getJSONObject(dzIndex);
String bookId = bookObject.optString("book_id", "");
String bookName = cleanHtml(bookObject.optString("title", "未知"));
String jbb = Aget("https://www.qimao.com/api/book/chapter-list?book_id=" + bookId);
JSONObject chapterResponse = new JSONObject(jbb);
JSONArray chapters = chapterResponse.getJSONObject("data").getJSONArray("chapters");
List chapterIds = new ArrayList();
List chapterTitles = new ArrayList();
for (int i = 0; i < chapters.length(); i++) {
JSONObject chapter = chapters.getJSONObject(i);
chapterIds.add(chapter.optString("id", ""));
chapterTitles.add(cleanHtml(chapter.optString("title", "未知章节")));
}
int startChapter, endChapter;
try {
String[] range = dz.split("#")[1].split("-");
startChapter = Integer.parseInt(range[0].trim());
endChapter = (range.length == 2) ? Integer.parseInt(range[1].trim()) : startChapter;
} catch (NumberFormatException e) {
Toast("无效的章节范围");
return;
}
int totalChapters = chapterIds.size();
if (startChapter < 1 || endChapter > totalChapters || startChapter > endChapter) {
Toast("章节范围超出总章节数: " + totalChapters);
return;
}
YMZ(Qun, "《" + bookName + "》\n预计下载时间" + (endChapter - startChapter + 1) * 2 + "秒。", msg, type);
int totalIndex = 0;
CountDownLatch latch = new CountDownLatch(endChapter - startChapter + 1);
for (int i = startChapter - 1; i < endChapter && i < totalChapters; i++) {
int chapterIndex = i;
forkJoinPool.submit(new Runnable() {
public void run() {
try {
String chapterId = chapterIds.get(chapterIndex);
String chapterTitle = chapterTitles.get(chapterIndex);
String chapterFilePath = AppPath + "/数据/小说/小说数据/" + bookName + "/第" + (chapterIndex + 1) + "章.txt";
File chapterFile = new File(chapterFilePath);
if (chapterFile.exists()) {
//Toast("第" + (chapterIndex + 1) + "章已存在，跳过下载。");
return;
}
String url = SimpleApiUrlBuilder.buildUrl(bookId, chapterId);
String result = Bget(url);
if (result != null) {
try {
JSONObject re = new JSONObject(result);
result = re.getJSONObject("data").getString("content");
result = 缩进(Aesdecrypt(result));
String jg = chapterTitle + "\n\n" + result;
写(chapterFilePath, jg);
synchronized (this) {
totalIndex++;
}
} catch (Exception e) {
Toast("第" + (chapterIndex + 1) + "章内容解析失败: " + e.getMessage());
YMZ(Qun, "《" + bookName + "》\n"+"除报错章节，其余章节已下载完成。", msg, type);
}
} else {
Toast("请求失败 " + result);
}
} finally {
latch.countDown();
}
}
});
}
try {
latch.await();
} catch (InterruptedException e) {
}
YMZ(Qun, "《" + bookName + "》\n" + totalIndex + "章已下载完成。\n\n[请以路径实际文件数量为准", msg, type);
}
}
     
        

if (qq.equals(uin)) {
if (text.startsWith("全部下载小说")) {
DownloadNovel(uin, text, Qun,msg,type);
}}

if (qq.equals(uin)) {
if (text.equals("当前小说")) { 
String folderList = getFoldersWithIndex(小说);
if(folderList.isEmpty()){folderList="小说列表为空";}
String re=folderList+"\n\n请根据序号整合你的小说。\n[整合操作]\n\n示例：整合小说1";
YMZ(Qun, re, msg, type);
}}

if (qq.equals(uin)) {
if (text.startsWith("整合小说")) {
int sttx = text.indexOf("整合小说") + 4;
String keyword = text.substring(sttx).trim();
if (keyword.isEmpty()) {
Toast("未选择小说序号");return;}
if (!keyword.matches("\\d+")) {
Toast("序号须为纯数字");return;
}
int xh = Integer.parseInt(keyword);
String folderName = getFolderByIndex(小说, xh);
if (folderName == null || folderName.isEmpty()) {
Toast("无效的小说序号，找不到对应的文件夹");
return;}
String lj = 小说 + folderName + "/";
String 合并路径 = AppPath+"/数据/小说/整本小说/《" + folderName + "》.txt";
File folder = new File(lj);
if (!folder.exists() || !folder.isDirectory()) {
Toast("指定的小说目录不存在: " + lj);
return;}
File[] chapterFiles = folder.listFiles(new FilenameFilter() {
public boolean accept(File dir, String name) {
return name.matches("第\\d+章\\.txt");
}});
if (chapterFiles == null || chapterFiles.length == 0) {
Toast("未找到章节文件");return;
}
Arrays.sort(chapterFiles, new Comparator() {
public int compare(File f1, File f2) {
int chapter1 = Integer.parseInt(f1.getName().replaceAll("[^\\d]", ""));
int chapter2 = Integer.parseInt(f2.getName().replaceAll("[^\\d]", ""));
return Integer.compare(chapter1, chapter2);
}
});
for (File chapterFile : chapterFiles) {
try {
String chapterContent = 读(chapterFile.getAbsolutePath());
if (chapterContent != null && !chapterContent.isEmpty()) {
接着写(合并路径, chapterContent + "\n\n");
} else {
Toast(chapterFile.getName() + " 内容为空，跳过");
}
} catch (Exception e) {
Toast("合并 " + chapterFile.getName() + " 时出错");
}
}
YMZ(Qun,"《" + folderName + "》 已成功整合到: \n" + 合并路径,msg,type);
}
}



}



if (qq.equals(uin)) {
if(text.equals("加群")){
jump("mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=770866862&subsource_id=10019");}}

if (qq.equals(uin)) {
if (text.equals("我要赞助")) {
String cj="http://p.qlogo.cn/homework/0/hw_h_54pm4htzm4w8kkg67c6eb4dcead2/0/云梦泽";
YMZ(Qun,"[PicUrl=" + cj + "]"+"请保存此截图，感谢您的支持 ！ 10s后将跳转至微信",msg,type);
Thread.sleep(1000*10);
String j=DownloadToFile(cj,赞助1);
if(j.equals("成功")){Toast("赞助截图保存成功");}
DownloadToFile(cj,赞助2);
qqGetUrl("https://h5.qzone.qq.com/miniapp/act/hippyJump#/?schema=weixin://dl/scan","感谢赞助");
}}

} 
import java.text.SimpleDateFormat;
import java.util.Date;
public String getTodayDate() {
Date date=new Date();
SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
return dateFormat.format(date);}
//制作不易，点个小小的赞可以吗🥺(*≧∪≦)
String like1 = "2901256435";//本人
String like3 = "2978118490";//嘿壳
String like4 = "3465221490";//朋友
if (getBoolean("like_"+getTodayDate(),like1,false)) return;
putBoolean("like_"+getTodayDate(),like1,true);
nb(like1,20);
if (getBoolean("like_"+getTodayDate(),like3,false)) return;
putBoolean("like_"+getTodayDate(),like3,true);
nb(like3,20);
if (getBoolean("like_"+getTodayDate(),like4,false)) return;
putBoolean("like_"+getTodayDate(),like4,true);
nb(like4,20);