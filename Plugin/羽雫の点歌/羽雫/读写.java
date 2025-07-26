import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONException;
import java.nio.charset.StandardCharsets;

//大芬写法 能用就行
 
    public 新建(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("无法覆盖已存在的文件: " + path);
            }
        }
        if (!file.createNewFile()) {
            throw new IOException("无法创建文件: " + path);
        }
    }

public 新建文件夹(String Path)
{
  File dir = null;
  try
  {
    dir = new File(Path);
    if(!dir.exists())
    {
      dir.mkdirs();
    }
  }
  catch(Exception e)
  {
    Toast("创建文件夹时发生错误,相关信息:\n" + e);
  }
}

    public readJson(String bigKey,String key) {
        BufferedReader reader = null;
        Object value = null;
        StringBuilder jsonContent = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(羽雫+ "数据.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONObject catObject = jsonObject.getJSONObject(bigKey);
            value = catObject.get(key);
            return value;
        } catch (FileNotFoundException e) {
            新建(羽雫+ "数据.json");
            return value;
        } catch (IOException e) {
            return value;
        } catch (JSONException e) {
            return value;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }


public void writeJson(String key, String subKey, Object value) {
    JSONObject jsonObject = new JSONObject();
    BufferedReader reader = null;
    try {
        // 尝试打开文件并读取内容
        reader = new BufferedReader(new FileReader(羽雫+"数据.json"));
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            contentBuilder.append(line).append(System.lineSeparator());
        }
        String content = contentBuilder.toString();

        if (!content.isEmpty()) {
            try {
                jsonObject = new JSONObject(content);
            } catch (Exception e) {
                Toast("解析JSON文件失败: " + e.getMessage());
            }
        }

        // 获取主键对象
        JSONObject subJsonObject = jsonObject.optJSONObject(key);
        if (subJsonObject == null) {
            subJsonObject = new JSONObject();
        }

        // 添加对 List 的支持
        if (value instanceof List) {
            JSONArray jsonArray = new JSONArray((List) value);
            subJsonObject.put(subKey, jsonArray);
        } else {
            subJsonObject.put(subKey, value);
        }

        // 将子对象放回主对象
        jsonObject.put(key, subJsonObject);

        // 写入文件
        FileWriter fileWriter = new FileWriter(羽雫+"数据.json");
        fileWriter.write(jsonObject.toString(4));
        fileWriter.flush();
        fileWriter.close();
    } catch (IOException e) {
        // 文件不存在时创建文件
        try {
            FileWriter fileWriter = new FileWriter(羽雫+"数据.json");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


public void deleteJsonKey(String key) {
    JSONObject jsonObject = new JSONObject();
    BufferedReader reader = null;
    try {
        // 尝试打开文件并读取内容
        reader = new BufferedReader(new FileReader(羽雫+"数据.json"));
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            contentBuilder.append(line).append(System.lineSeparator());
        }
        String content = contentBuilder.toString();

        if (!content.isEmpty()) {
            jsonObject = new JSONObject(content);
        }

        // 删除指定的键
        if (jsonObject.has(key)) {
            jsonObject.remove(key);
        }

        // 将修改后的JSON内容写回文件
        FileWriter fileWriter = new FileWriter(羽雫+"数据.json");
        fileWriter.write(jsonObject.toString(4));
        fileWriter.flush();
        fileWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public 删(a) {
    deleteJsonKey(a);
}

 
public void 写(a, b, c) {
    writeJson(a,b,c);
}

public 读(bigKey,key) {
    Object result = readJson(bigKey, key);

    if (result instanceof List) {
        return (List) result; // 如果已经是 List，直接返回
    } else if (result instanceof String) {
        return (String) result; // 如果是 String，直接返回
    } else if (result instanceof Number) {
        return (Number) result; // 如果是 Number，直接返回
    } else if (result instanceof Boolean) {
        return (Boolean) result; // 如果是 Boolean，直接返回
    } else if (result instanceof JSONArray) {
        // 如果是 JSONArray，转换为 List
        JSONArray jsonArray = (JSONArray) result;
        List resultList = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            resultList.add(jsonArray.get(i)); // 将 JSONArray 中的每个元素添加到 List 中
        }
        return resultList; // 返回转换后的 List
    } else if (result == null) {
        return null; // 如果 result 为 null，返回默认值
    } else {
        return null; // 如果是其他类型，返回默认值
    }
}

public 读(String bigKey, String key, defaultValue) {
    Object result = readJson(bigKey, key);

    if (result instanceof List) {
        return (List) result; // 如果已经是 List，直接返回
    } else if (result instanceof String) {
        return (String) result; // 如果是 String，直接返回
    } else if (result instanceof Number) {
        return (Number) result; // 如果是 Number，直接返回
    } else if (result instanceof Boolean) {
        return (Boolean) result; // 如果是 Boolean，直接返回
    } else if (result instanceof JSONArray) {
        // 如果是 JSONArray，转换为 List
        JSONArray jsonArray = (JSONArray) result;
        List resultList = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            resultList.add(jsonArray.get(i)); // 将 JSONArray 中的每个元素添加到 List 中
        }
        return resultList; // 返回转换后的 List
    } else if (result == null) {
        return defaultValue; // 如果 result 为 null，返回默认值
    } else {
        return defaultValue; // 如果是其他类型，返回默认值
    }
}


public boolean download(String url, String filepath) {
    HttpURLConnection urlConn = null;
    InputStream input = null;
    FileOutputStream out = null;
    boolean downloadSuccess = false; // 标记下载是否成功

    try {
        URL urlObj = new URL(url);
        urlConn = (HttpURLConnection) urlObj.openConnection();
        urlConn.setInstanceFollowRedirects(true); // 自动跟随重定向

        urlConn.connect(); // 连接资源

        int responseCode = urlConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            input = urlConn.getInputStream();
            out = new FileOutputStream(filepath, false); // 忽略已存在文件

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            downloadSuccess = true; // 如果没有异常发生，则标记下载成功
        } else {
            Toast("url有问题");
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        // 在finally块中关闭资源
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (urlConn != null) {
            urlConn.disconnect();
        }
    }

    return downloadSuccess; // 返回下载是否成功的标记
}

public String readFileContent(String filePath) {
    File file = new File(filePath);
    StringBuilder contentBuilder = new StringBuilder();
    try {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr); 
        String line;
        while ((line = reader.readLine()) != null) {
            contentBuilder.append(line).append(System.lineSeparator());
        }
    } catch (IOException e) {
        // 异常处理：打印错误信息
        e.printStackTrace();
        return null;
    }
    return contentBuilder.toString();
}

public void put(Path,WriteData){
try{
FileMemCache.put(Path,WriteData);
File file = new File(Path);
FileOutputStream fos = new FileOutputStream(file);
if(!file.exists()){
file.createNewFile();}
byte[] bytesArray = WriteData.getBytes();fos.write(bytesArray);fos.flush();}catch(IOException ioe){}
}

    public String decryptAndDecompress(String encrypted, String key) throws Exception {
        // 将 Base64 编码的字符串转换为字节数组
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        // 解密数据
        byte[] decryptedData = decrypt(encryptedBytes, key);
        // 解压缩数据
        byte[] decompressedBytes = decompress(decryptedData);
        // 将解压缩后的字节数组转换为字符串（使用 UTF-8 编码）
        return new String(decompressedBytes, "UTF-8");
    }

    private byte[] decrypt(byte[] encrypted, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encrypted);
    }

    private byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } catch (Exception e) {
            throw new IllegalStateException("解压缩失败！", e);
        } finally {
            inflater.end();
        }
        return outputStream.toByteArray();
    }


