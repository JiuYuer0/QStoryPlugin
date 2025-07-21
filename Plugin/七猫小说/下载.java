import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public void DownloadNovel(String uin, String text, String qun, Object msg, int type) {
int CORE_COUNT = Runtime.getRuntime().availableProcessors();
int MAX_THREADS = Math.min(CORE_COUNT * 2, 20);
ThreadPoolExecutor executor = new ThreadPoolExecutor(
        MAX_THREADS,
        MAX_THREADS,
        1L,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue(500),
        new ThreadPoolExecutor.CallerRunsPolicy()
    );
String dz = text.substring(text.indexOf("全部下载小说") + 6).trim();
if (dz.isEmpty() || !dz.matches("\\d+")) {
return;}
String jb = 读(AppPath + "/数据/小说/聊天数据/" + qun + "/" + uin + "搜索数据");
if (jb == null || jb.isEmpty()) {
Toast("暂未搜索");return;
}
JSONObject responseObject = new JSONObject(jb);
JSONArray dataArray = responseObject.getJSONArray("data");
int dzIndex = Integer.parseInt(dz) - 1;
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
int totalChapters = chapters.length();
YMZ(qun, "《" + bookName + "》\n预计下载时间约 " + totalChapters * 2 + " 秒。", msg, type);
String filePath = AppPath + "/数据/小说/整本小说/《" + bookName + "》.txt";
File novelFile = new File(filePath);
AtomicInteger nextChapterIndex = new AtomicInteger(1);
Set writtenChapters = new HashSet();
if (novelFile.exists()) {
try {
int writtenLines = (int) Files.lines(novelFile.toPath()).count() / 2;
nextChapterIndex.set(writtenLines + 1);
Toast("检测到中断，继续下载从第" + nextChapterIndex.get() + "章开始");
} catch (IOException e) {
Toast("读取已下载文件进度失败: " + e.getMessage());
return;
}
}
StringBuilder contentBuffer = new StringBuilder();
int BATCH_SIZE = 50;
for (int i = 0; i < totalChapters; i++) {
int chapterIndex = i;
executor.submit(new Runnable() {
public void run() {
int retryCount = 0;
boolean success = false;
while (retryCount < 3 && !success) {
try {
JSONObject chapter = chapters.getJSONObject(chapterIndex);
String chapterId = chapter.optString("id", "");
String chapterTitle = cleanHtml(chapter.optString("title", "未知章节"));
String url = SimpleApiUrlBuilder.buildUrl(bookId, chapterId);
String result = Bget(url);
if (result != null) {
JSONObject re = new JSONObject(result);
result = re.getJSONObject("data").getString("content");
result = 缩进(Aesdecrypt(result));
String jg = "第" + (chapterIndex + 1) + "章\n\n" + result;
synchronized (contentBuffer) {
if (!writtenChapters.contains(chapterIndex + 1)) {
contentBuffer.append(jg).append("\n\n");
writtenChapters.add(chapterIndex + 1);
if ((chapterIndex + 1) % BATCH_SIZE == 0 || chapterIndex + 1 == totalChapters) {
接着写(filePath, contentBuffer.toString());
contentBuffer.setLength(0);
//Toast("已写入第" + (chapterIndex + 1) + "章");
}
} else {
//Toast("第" + (chapterIndex + 1) + "章已写入，跳过重复章节");
}
}
success = true;
} else {
Toast("请求失败 " + result);
}
} catch (Exception e) {
retryCount++;
Toast("下载第" + (chapterIndex + 1) + "章失败，重试 " + retryCount + "/3 次");
}
}
}});
}
executor.shutdown();
try {
if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
executor.shutdownNow();
}
if (contentBuffer.length() > 0) {
接着写(filePath, contentBuffer.toString());
Toast("已写入剩余章节内容，共" + totalChapters + "章完成。");
}
YMZ(qun, "《" + bookName + "》" + totalChapters + "章已下载完成。", msg, type);
} catch (InterruptedException e) {
Toast("下载过程中出错: " + e.getMessage());
YMZ(Qun, "《" + bookName + "》\n"+"请到相应路径查看是否小说下载", msg, type);
}
}
                