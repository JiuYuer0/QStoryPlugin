import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;


 public void downloadFile(String fileURL, String saveDir, String customFileName, int Type, String GroupUin)  {
    URL url = new URL(fileURL);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    int responseCode = httpConn.getResponseCode();

    // 检查响应码是否为HTTP_OK(200)，表示请求成功
    if (responseCode == HttpURLConnection.HTTP_OK) {
        String fileName = customFileName;  // 使用传入的自定义文件名

        InputStream inputStream = httpConn.getInputStream();
        String saveFilePath = saveDir + File.separator + fileName;

        FileOutputStream outputStream = new FileOutputStream(saveFilePath);

        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();

       // System.out.println("文件下载成功: " + saveFilePath);
       
       
       String GroupUintb = GroupUin;

       
       if (Type == 1) {
       sendVideo( GroupUintb, null, saveFilePath);
       }
       
       
    } else {
        Toast("无法下载文件" );
    }
    httpConn.disconnect();
}

 
 
 public static String RedirectUrl(String url)
{
  try
  {
    URL imageUrl = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.setFollowRedirects(true);
    String redirectUrl = connection.getHeaderField("Location");
    if(redirectUrl == null)
    {
      return connection.getURL().toString();
    }
    else
    {
      return redirectUrl;
    }
    connection.disconnect();
  }
  catch(Exception e)
  {
    return url;
  }
}


public static String HttptUrl(String url) {
   try {

            // 创建URL对象
            URL url = new URL(url);
            // 打开连接并获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();    
     
               
                                                                                
       return response.toString();
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
}



//文本判断类型的
public static String msgMatc(float msgTmsgt)
{
   if (msgTmsgt == 1) {
   return "文本/图片";
   } else if (msgTmsgt == 2) {   
   return "卡片";
   } else if (msgTmsgt == 3) {
   return "图文";
   } else if (msgTmsgt == 4) {
   return "语音";
   } else if (msgTmsgt == 5) {
   return "文件";
   } else if (msgTmsgt == 6) {
   return "回复";
   } else {
   return "null";
   }
}

