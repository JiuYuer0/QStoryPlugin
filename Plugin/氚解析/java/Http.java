public class Http{
    //网络Get申请函数
    public static String get(String url) {
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
        if (buffer.length() == 0) return buffer.toString();
                buffer.delete(buffer.length() - 1, buffer.length());
        return buffer.toString();
    }
    //post申请函数
    public static String post(String urlPath, String cookie, String data) {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader isr = null;
        try {
            URL url = new URL(urlPath);
            uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.setConnectTimeout(10000);
            uc.setReadTimeout(10000);
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.setRequestProperty("Cookie", cookie);
            uc.getOutputStream().write(data.getBytes("UTF-8"));
            uc.getOutputStream().flush();
            uc.getOutputStream().close();
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
                //Toast("错误:\n" + e);
                e.printStackTrace();
            }
        }
        if (buffer.length() == 0) return buffer.toString();
        buffer.delete(buffer.length() - 1, buffer.length());
        return buffer.toString();
    }
    //下载函数
    public static void download(String url,String filepath) throws Exception{
        InputStream input = null;
        try {
                URL mUrl = new URL(url);
                HttpURLConnection urlConn = (HttpURLConnection) mUrl.openConnection();
                input = urlConn.getInputStream();
                byte[] bs = new byte[1024];
                int len;
                FileOutputStream out = new FileOutputStream(filepath, false);
                while((len = input.read(bs)) != -1){
                        out.write(bs, 0, len);
                }
                out.close();
                input.close();
        } catch (IOException e) {
                return;
        } finally {
                try {
                        input.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        return;
    }
    //重定向函数（主要用于图片
    public static String redirect(String urlString) {
        URL urlObject = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.connect();     
        return picUrl= connection.getHeaderField("Location");;
    }
}