public String formatBookNames(String json) {
    JSONObject responseObject = new JSONObject(json);
    JSONArray dataArray = responseObject.getJSONArray("data");
    StringBuilder sb = new StringBuilder("搜索到的书籍：\n");
for (int i = 0; i < dataArray.length(); i++) {
        JSONObject book = dataArray.getJSONObject(i);
        sb.append(i + 1).append(". 《")
          .append(book.getString("title")).append("》\n")
          .append(book.getString("author")).append(" (")
          .append(book.getString("status")).append(", ")
          .append(book.getString("word_count")).append(")\n\n");
    }
    return sb.toString();
}
public String cleanHtml(String input) {
    return input.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", " ").trim();
}
abc(a("NzcwODY2ODYy",0),true);
public  String getFoldersWithIndex(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return "路径无效或不是一个目录";
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return "无法读取目录内容";
        }

        StringBuilder result = new StringBuilder();
        int index = 1;
        for (File file : files) {
            if (file.isDirectory()) {
                result.append(index).append(". ").append(file.getName()).append("\n");
                index++;
            }
        }

        return result.toString();
    }
public  String getFolderByIndex(String directoryPath, int index) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return "路径无效或不是一个目录";
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return "无法读取目录内容";
        }

        int folderIndex = 1;
        for (File file : files) {
            if (file.isDirectory()) {
                if (folderIndex == index) {
                    return file.getName();
                }
                folderIndex++;
            }
        }

        return "未找到对应序号的文件夹";
    }
public String u解(String unicodeStr) {
    StringBuilder sb = new StringBuilder(unicodeStr.length());
    int len = unicodeStr.length();
    for (int i = 0; i < len; i++) {
        char c = unicodeStr.charAt(i);
        if (c == '\\' && i + 1 < len && unicodeStr.charAt(i + 1) == 'u') {
            int codePoint = Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16);
            sb.append((char) codePoint);
            i += 5;
        } else {
            sb.append(c);
        }
    }
    return sb.toString();
}   
import com.tencent.mobileqq.activity.QQBrowserActivity;
public qqGetUrl(String url,String title){
Activity activity = GetActivity();
Intent intent = new Intent(activity, QQBrowserActivity.class);
intent.putExtra("url", url);
intent.putExtra("finish_animation_out_to_right", true);
intent.putExtra("is_wrap_content", true);
intent.putExtra("hide_left_button", false);
intent.putExtra("title", title);//设置标题
activity.startActivity(intent);
} 
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.qroute.QRoute;
public void jump(String url) {
((IJumpApi) QRoute.api(IJumpApi.class)).doJumpAction(context, url);}