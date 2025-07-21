import android.app.*;
import java.lang.*;
import java.text.*;
import android.net.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import android.content.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
public String Aget(String url) {
    StringBuilder response = new StringBuilder();
    HttpURLConnection connection = null;
    try {
        URL requestUrl = new URL(url);
        connection = (HttpURLConnection) requestUrl.openConnection();
     String[] userAgents = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1",
            "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36"
        };
        String userAgent = userAgents[new Random().nextInt(userAgents.length)];
 connection.setRequestProperty("User-Agent", userAgent);
 connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
   connection.setRequestProperty("Connection", "keep-alive");
   String fakeIp = "192.168." + new Random().nextInt(255) + "." + new Random().nextInt(255);
   connection.setRequestProperty("X-Forwarded-For", fakeIp);
  connection.setRequestProperty("Client-IP", fakeIp);
  connection.setRequestProperty("Cookie", "Hm_lvt_1b6d0fc94c391c78c2fbeda715896432=1741061266; HMACCOUNT=0EAF414387B5124E; sajssdk_2015_cross_new_user=1; _csrf-frontend=354da55b60a31195e6a33e11a356c2fb36b4e30fb63fa6f9f28b7e909fd404aea%3A2%3A%7Bi%3A0%3Bs%3A14%3A%22_csrf-frontend%22%3Bi%3A1%3Bs%3A32%3A%22MrDbAZeCnhMxD9i86LAqlTDNpkRDJapc%22%3B%7D; acw_tc=0aef812717410912619444823e010945501e915ca4d49b967fa1b46a697205; qimao-token=eyJhbGciOiJSUzI1NiIsImNyaXQiOlsiaXNzIiwianRpIiwiaWF0IiwiZXhwIl0sImtpZCI6IjE2MzUxMzQ0MDAiLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3NDE2OTYxMjgsImlhdCI6MTc0MTA5MTMyOCwiaXNzIjoiL2FwaS9wYy92MS9sb2dpbi9pbmRleCIsImp0aSI6InBjIiwidXNlciI6eyJ1aWQiOjUxNjAwMDM4MSwibmlja25hbWUiOiLkuIPnjKvkuablj4tfMDkyMjUxNTc1NjU1IiwicmVnVGltZSI6MTY5NTM1MjQ2NywidmlwRXhwaXJlQXQiOjE3NDM3NzMyNDcsIm51dCI6MTY5NTM1NDk2M319.o0P-jUJr3jOWtkr-qNqzIO_1TA30StoGoWfz6UN3r6YazVmZPTSkyV-JkkctUuqUJGAfonHTn00p6YYrMZf0b2lIfXibQk_gDWcsErRYPmOvi8bv8SKoUn2LQKU6ocX5osi0lVc0dm1J_jOaHCrnD5gkbst-gYLAuOZiYHM3ZHx8MKGcm2ees2qbWk8ZfieMCIaO62T5P6RArORJvPBee62xNz_D_FdZqIvZd9oE2LPunD_NCubBsqZdIedGeG2WwLplMSoo4BiI_sBtC57rsHLyXnjnPC6zumqABYm8SszbyOzJeOLFEIyFH2FbmodCQm-OfoKC_KboislVN6utW7JJgXmcKl_z_1K6N6b5UB6iKU799XPpLrwMgn2K--U5h5HYKy0TJyKyoH73kdXdFwn1-dWS1YEhNCuJwOB-b7K4vu6VkynGb_mh99hSZM1EMmgjbTmK2X4fW_QsTPNivu3PfcEgGHXzcVCWhdHHevMkamOJYxFyUevyA2YY_BXqltBW-olQ4sos3bNKdrtNjMIRLKq-1TKConbiYV9gFAcJZUVlV1KNxtrnnbuHD2rFli6YNRNvCUCIjkeGDXO5hsEeClOjyLhTddiiB0ryvcXg1W2Zwt79IcziQO7kER487zoh-Io-trLxn8nM3daZyIeXTSzHxNWeHoLi0saB7lM; Hm_lpvt_1b6d0fc94c391c78c2fbeda715896432=1741091333; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22516000381%22%2C%22first_id%22%3A%221955f569df516c-06be1f0b06d007c-4b1f1453-321474-1955f569df61d3%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk1NWY1NjlkZjUxNmMtMDZiZTFmMGIwNmQwMDdjLTRiMWYxNDUzLTMyMTQ3NC0xOTU1ZjU2OWRmNjFkMyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjUxNjAwMDM4MSJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22516000381%22%7D%2C%22%24device_id%22%3A%221955f569df516c-06be1f0b06d007c-4b1f1453-321474-1955f569df61d3%22%7D");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setInstanceFollowRedirects(false);  // 处理手动重定向

        int responseCode = connection.getResponseCode();
        
        // 处理重定向
        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || 
            responseCode == HttpURLConnection.HTTP_MOVED_PERM || 
            responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            String newUrl = connection.getHeaderField("Location");
            return sendGetRequest(newUrl);  // 递归调用处理重定向
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();
        } else {
            Toast("请求失败，响应码：" + responseCode);
        }

    } catch (IOException e) {
        Toast(e);
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }
    return response.toString();
}
public String DownloadToFile(String url, String filepath) throws Exception
{
  File file = new File(filepath);
  if(!file.getParentFile().exists())
  {
    file.getParentFile().mkdirs();
  }
  InputStream input = null;
  try
  {
    URL ur = new URL(url);
    HttpURLConnection urlConn = (HttpURLConnection) ur.openConnection();
    input = urlConn.getInputStream();
    byte[] bs = new byte[1024];
    int len;
    FileOutputStream out = new FileOutputStream(filepath, false);
    while((len = input.read(bs)) != -1)
    {
      out.write(bs, 0, len);
    }
    out.close();
    input.close();
  }
  catch(IOException e)
  {
    return "失败";
  }
  finally
  {
    try
    {
      input.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
      return "失败";
    }
  }
  return "成功";
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public static String Bget(String url) throws Exception {
        URL requestUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

        // 设置请求方法为 GET
        connection.setRequestMethod("GET");

        // 设置请求头
        connection.setRequestProperty("net-env", "1");
        connection.setRequestProperty("reg", "");
        connection.setRequestProperty("channel", "qm-aliyun_lf");
        connection.setRequestProperty("is-white", "0");
        connection.setRequestProperty("platform", "android");
        connection.setRequestProperty("application-id", "com.kmxs.reader");
        connection.setRequestProperty("AUTHORIZATION", "eyJhbGciOiJSUzI1NiIsImNyaXQiOlsiaXNzIiwianRpIiwiaWF0IiwiZXhwIl0sImtpZCI6IjE1MzEyMDM3NjkiLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3NDM4MTcyMDYsImlhdCI6MTc0MTIyNTIwNiwiaXNzIjoiaHR0cHM6Ly94aWFvc2h1by53dHp3LmNvbS9hcGkvdjEvbG9naW4vdG91cmlzdCIsImp0aSI6InRvdXJpc3QiLCJ1c2VyIjp7InVpZCI6NzM3NjYyNjM2LCJuaWNrbmFtZSI6Iua4uOWuojAzMDY5NzU0NDk0OCIsImltZWkiOiIiLCJ1dWlkIjoiZmZmZmZmZmYtZDk3MS0wNjFmLTAwMDAtMDAwMDAwMDAwMDAwIiwiZGV2aWNlSWQiOiIyMDI1MDMwNDE5MzY1MDkwOWQ5YzU3MGY4OGIyOTE3MGFlNTUxNWVjMzUyNmMwMDE4MjFiMjJiYmMxMjVmZiIsInJlZ1RpbWUiOjE3NDExOTEyMjYsInZpcEV4cGlyZUF0IjowLCJzbV9pZCI6IjIwMjUwMzA2MDAxMzQ2ODk0YTQzMWU5ZGQ3MzVlZTg5Yzk2Nzk5MzM1NDA1MDQwMGE5Yjg5ZDUyZjMzYjcyIiwibnV0IjowLCJpZnUiOjAsImlzX3JiZiI6MCwiYWN0X2lkIjowLCJiaW5kX2F0IjowLCJzdWlkIjoiIzY3NzMxNTgzMTMiLCJ0X21vZGUiOjF9fQ.T1SKQvdjgB2Z5cUNS4yL3RQ5mAgD7WrkjQUekkdmanOEMR9B0nOK694velMHGjk2qVOToAVMRkk5g8PtXJ2FbOK57AoLcy-O9KvTHuBkToYXskpeaKifurWFtG6i86OmyiHVrRm5La-FBMntZizcdoXYY19NVUM_Y5e3Opvhq-U");
        connection.setRequestProperty("app-version", "75200");
        connection.setRequestProperty("qm-params", "cLG5Ozo7paHWH2x14qJQm3HjHzNjmqR7uaU1paHWHTHeNe-rgT4L4lgENTsz4Tg5taG-pCp14lfQmqF5A5HLgIHUgIgwNI0Yge4UgI-wAqFY4efEgy4rAyHLAh0Egyo2NhfnNqRTgefLNzgwgI0rgTo5gTG54zgngTRzp5HjHz2Qpq-5A5H5taGQ4qg5A5H5taGQBlk2BaHWH-UGHIKKRfF5taGD4q2-HTZ54eu54hgYpy4YgzFYpqkxNLHjHzJxmqFQBzdQ4loTmyf5A5GTNlGxge2-pT-LpI22py0EH5w5OyxDBzfQByRlpqw5A5GHH5w5OlReOl2DB5U1paHWHT0ENI0LgTfLgIOENT05taGeBERL4lRUmqF5A5HTNTOEge0UAIgngLHjHSNM4Ck14UJe4lJLp3HWHT97NhgrghFwgh0rghHLgh9wAaHjHSNYOLUlpCH5A5HngaHjHSkLuCNMpqFQmqF5A5HwgI9wgI9wgI9wgI9wgI9wgI9wgI9wgI9wgI9wgI9wgI9wgI95taGUuq2-HTZ5pzpzpzpzpz4QpI-Eg3MwNTozth9wgI9QgI9wgI9wgI9wgI9wH5w5uln5tq2Qpq-5A5H5taGEByHQuq2-HTZ5He0LAIHwge9rNT056F==");
        connection.setRequestProperty("sign", "e455ef03cbb26d6d1f5a1f51c5aa0cdd");
        connection.setRequestProperty("QM-uaf", "20250306-737662636");
        connection.setRequestProperty("QM-it", "1741191226");
        connection.setRequestProperty("QM-ii", "1882319305");
        connection.setRequestProperty("no-permiss", "3");
        connection.setRequestProperty("User-Agent", "webviewversion/0");
        connection.setRequestProperty("Host", "api-ks.wtzw.com");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Accept-Encoding", "gzip");
// 获取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();

            // 检查是否为 GZIP 响应
            String encoding = connection.getContentEncoding();
            if ("gzip".equalsIgnoreCase(encoding)) {
                inputStream = new GZIPInputStream(inputStream);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return response.toString();
        } else {
            throw new Exception("请求失败，响应码: " + responseCode);
        }
    }
import java.util.Base64; 
public String a(String b, int c) {
if (b == null || b.isEmpty()) { 
return "";} 
try {
if (c == 1) { 
return Base64.getEncoder().encodeToString(b.getBytes()); 
} else { 
byte[] d = Base64.getDecoder().decode(b); 
return new String(d);
} } catch (IllegalArgumentException e) { 
return ""; }}
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.qqnt.kernel.nativeinterface.IOperateCallback;
import com.tencent.qqnt.kernel.api.IKernelService;
import com.tencent.qqnt.kernel.nativeinterface.IKernelGroupService;
public void abc(String str,boolean t) {
Object app = BaseApplicationImpl.getApplication().getRuntime();
IKernelService runtimeService = app.getRuntimeService(IKernelService.class, "all");
Object service = runtimeService.getGroupService();
service.setTop(Long.parseLong(str), t, null);
}
 

