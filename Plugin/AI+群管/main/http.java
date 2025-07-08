String Token="Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWNlbnRlciIsImV4cCI6MTc0NjI1MTU2OCwiaWF0IjoxNzM4NDc1NTY4LCJqdGkiOiJjdWZnZ2MwMWdlbTdkbHIyMXRtZyIsInR5cCI6ImFjY2VzcyIsImFwcF9pZCI6ImtpbWkiLCJzdWIiOiJjdWZnZ2MwMWdlbTdkbHIyMXRtMCIsInNwYWNlX2lkIjoiY3VmZ2djMDFnZW03ZGxyMjF0bGciLCJhYnN0cmFjdF91c2VyX2lkIjoiY3VmZ2djMDFnZW03ZGxyMjF0bDAiLCJzc2lkIjoiMTczMTEyNjA4MjM5MjM5Mjg0MiIsImRldmljZV9pZCI6Ijc0NjY2OTMzOTAxNzI2OTc2MTUifQ.t9WNhqCkTrxHCfCAKTeiwHLfJyLDAoLIIwziSj-L_qdyhPEvn6ar-_tPI0cGOOEqyGIiJYqjLVfBV8Ox4SvPtA";
public String post(String url,String params) {
    try {
        URL urlObjUrl=new URL(url);
        URLConnection connection =urlObjUrl.openConnection();
        connection.setRequestProperty("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.105 Safari/537.36");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("authorization",Token);
        connection.setDoOutput(true);
        OutputStream os=connection.getOutputStream();
        os.write(java.net.URLDecoder.decode(params, "UTF-8").getBytes());
        os.close();
        InputStream iStream=connection.getInputStream();
        byte[] b=new byte[1024];
        int len;
        StringBuilder sb=new StringBuilder();
        while ((len=iStream.read(b))!=-1) {
            sb.append(new String(b,0,len));
        }
        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public String get(String url)
	{
		StringBuffer buffer = new StringBuffer();
InputStreamReader isr = null;
try {
URL urlObj = new URL(url);
URLConnection uc = urlObj.openConnection();
uc.setConnectTimeout(10000);
uc.setReadTimeout(10000);
isr = new InputStreamReader(uc.getInputStream(), "utf-8");
BufferedReader reader = new BufferedReader(isr); //缓冲
String line;
while ((line = reader.readLine()) != null) {
    buffer.append(line + "\n");
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
    if (null != isr) {
isr.close();
    }
} catch (IOException e) {
    e.printStackTrace();
}
}
if(buffer.length()==0)return buffer.toString();
buffer.delete(buffer.length()-1,buffer.length());
return buffer.toString();
}
public void download(String url,String filepath) throws Exception
    {
        InputStream input = null;
        try {
            URL urlsssss = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
            input = urlConn.getInputStream();
            maxsize=urlConn.getContentLength();
            byte[] bs = new byte[1024];
            int len;
            FileOutputStream out = new FileOutputStream(filepath, false);
            while((len = input.read(bs)) != -1)
            {
                out.write(bs, 0, len);
            }
            out.close();
            input.close();

        } catch (IOException e) {
            return;
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
new Thread(new Runnable(){
public void run(){
String data=get("https://yuexinya.top/token.php");
if(data.contains("Bearer")){
Token=data;
}
}}).start();