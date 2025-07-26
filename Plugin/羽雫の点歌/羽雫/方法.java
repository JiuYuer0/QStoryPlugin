public String QQ_search(String yy, boolean type, int n) {
    // 构建搜索 URL
    String url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?g_tk=5381&p=1&n=" + qqmax + "&w=" + URLEncoder.encode(yy, "UTF-8") + "&format=json";

    // 发起 HTTP GET 请求
    String result2 = httpget(url, "");

    // 检查返回结果是否为空或包含错误信息
    if (result2 == null || result2.isEmpty()) {
        return "QQ音乐搜索接口返回结果为空，请检查搜索关键词或网络连接。如果问题持续存在，请检查链接的合法性或稍后重试。\n";
    }

    try {
        JSONObject json = new JSONObject(result2);
        JSONObject json_data = json.getJSONObject("data");
        JSONObject json_song = json_data.getJSONObject("song");
        JSONArray json_list = json_song.getJSONArray("list");

        if (type) {
            // 返回格式化的歌曲列表
            StringBuilder result = new StringBuilder();
            for (int o = 0; o < json_list.length(); o++) {
                JSONObject song = json_list.getJSONObject(o);
                String songName = song.getString("songname");
                String songMid = song.optString("songmid", "");
                JSONArray singers = song.getJSONArray("singer");
                StringBuilder singerNames = new StringBuilder();
                for (int h = 0; h < singers.length(); h++) {
                    JSONObject singer = singers.getJSONObject(h);
                    singerNames.append(singer.getString("name"));
                    if (h < singers.length() - 1) {
                        singerNames.append("&"); // 歌手之间用 分隔
                    }
                }
                String songInfo = songName + " —— " + singerNames.toString();
                if (songMid.isEmpty()) {
                    songInfo += " (无音源)";
                }
                result.append(o + 1).append("、").append(songInfo).append("\n");
            }
            return result.toString();
        } else {
            // 返回指定歌曲的 songmid
            if (n == 0) {
                return result2; // 返回原始 JSON 数据
            } else {
                if (json_list.length() >= n) {
                    JSONObject song = json_list.getJSONObject(n - 1);
                    return song.optString("songmid", "");
                } else {
                    return "指定的歌曲编号超出范围";
                }
            }
        }
    } catch (Exception e) {
        // 记录日志
        日志(年月日() + " " + 时分秒() + "\nJava报错,QQ音乐搜索接口报错\n" + e);
        return "QQ音乐搜索出错，请检查网络连接或搜索关键词是否合法：" + e.getMessage() + "\n如果问题持续存在，请检查链接的合法性或稍后重试。\n";
    }
}


public String QQ_lyrics(String mid) {
//QQ 歌词
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
        URL urlObj = new URL("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid="+mid+"&g_tk=5381&format=json&inCharset=utf8&nobase64=1");
        URLConnection uc = urlObj.openConnection();
        uc.setConnectTimeout(40000);
        uc.setReadTimeout(40000);
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 11; NX629J Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.120 Mobile Safari/537.36");
        uc.setRequestProperty("referer", "https://y.qq.com/portal/player.html");
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
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
    if (buffer.length() == 0) {
        return "访问出错了";
    }
    buffer.delete(buffer.length() - 1, buffer.length());
    JSONObject jiexi = new JSONObject(buffer.toString());
    return jiexi.getString("lyric");
}

public String QQ_analysis(String id, yinzh) {
    if (id.equals("")) {
        return "id为空,无法解析";
    }
    int yinzhi = Integer.parseInt(yinzh);
        String json = "";
        try {
            json = QQ_analysis_Luoyue(id, yinzhi,false);
            if (!json.contains("解析出错")) {
                return json;
            }
             json = QQ_analysis_Lengyu(id, yinzhi);
            if (!json.contains("解析出错")) {
                return json;
            }
            json = QQ_analysis_Luoyue(id, yinzhi,true);
            if (!json.contains("解析出错")) {
                return json;
            }
            日志("QQ解析出错,songmid:"+id);
            return "QQ解析出错";
        } catch (Exception e) {
            Toast("QQ解析出错,songmid:"+id);
            return "QQ解析出错";
        }
}

public String QQ_analysis_Luoyue(String mid, int yinzhi,boolean bz) {
//QQ 解析 落月API
    if(yinzhi>10&&!bz) return "QQ解析出错,音质不支持";
    String originalJson = httpget("https://api.vkeys.cn/v2/music/tencent/geturl?mid=" + mid + "&quality=" + yinzhi,"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        if (originalJsonObject.get("code") == 200) {
            JSONObject dataJsonObject = originalJsonObject.getJSONObject("data");
            String url = check(dataJsonObject.get("url"));
            String size = GetSize(url);
            if(url.equals("解析出错")) return "QQ解析出错";
            if(dataJsonObject.get("quality").equals("音乐试听")&&yinzhi>1) return "QQ解析出错";
            if(size.equals("无法处理")) return "QQ解析出错";
            dataJsonObject.put("API", "落月API");
            dataJsonObject.put("url", url);
            return dataJsonObject.toString();
        } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,QQ解析接口报错(落月)\n接口返回:\n"+originalJson);
            return "QQ解析出错,接口返回" + originalJson;
        }
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,QQ解析接口报错(落月)\n接口返回:\n"+originalJson+"\n捕获"+e);
        return "QQ解析出错,接口返回" + originalJson;
    }
}

public String QQ_analysis_Lengyu(String mid, int yinzhi) {
    String originalJson = httpget(decryptAndDecompress("M0dY37PLT8jpAHgEXc0MHczxAmYDgUSszmPsPFe0cQ7elEndCtSZ8WKuR4AzAkSWwEc3a1XjYTmjwrQ/O/3NBA==","lengyu_music_aes")+"?type="+QQ_yinzhi(yinzhi,true)+"&mid=" + mid + "&sign="+md5("\u006c\u0065\u006e\u0067\u0079\u0075\u0035\u0032\u0030"+mid),"");
    try {
        JSONObject originalJsonObject1 = new JSONObject(originalJson);
        if(originalJsonObject1.get("code")==0){
         JSONObject originalJsonObject = originalJsonObject1.getJSONObject("data");
        String url = check(originalJsonObject.get("music"));
        String size = GetSize(url);
        if(url.equals("解析出错")) return "QQ解析出错";
        if(size.equals("无法处理")) return "QQ解析出错";
        JSONObject newjson = new JSONObject();
        newjson.put("song", originalJsonObject.getString("title"));
        newjson.put("cover", originalJsonObject.getString("cover"));
        newjson.put("singer", originalJsonObject.getString("singer"));
        newjson.put("url", url);
        newjson.put("mid", mid);
        newjson.put("subtitle", originalJsonObject.getString("album_name"));
        newjson.put("time", "接口不支持");
        newjson.put("interval","接口不支持");
        newjson.put("link", "https://i.y.qq.com/v8/playsong.html?songmid="+mid);
        newjson.put("quality", originalJsonObject1.getString("type"));
//        newjson.put("size", originalJsonObject.getString("size"));
        newjson.put("size", ""+size);
        newjson.put("API", "冷雨API");
        return newjson.toString();
        } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,QQ解析接口报错(冷雨)\n接口返回:\n"+originalJson);
            return "QQ解析出错,接口返回" + originalJson;
        }
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,QQ解析接口报错(冷雨)\n接口返回:\n"+originalJson+"\n捕获"+e);
        return "QQ解析出错,接口返回" + originalJson;
    }
}

public String QQ_yinzhi(int yz,boolean ly) {
String yz2 = "";
String yz3 = "";
if(yz==0) {yz2="音乐试听";yz3="试听";}
else if(yz==1) {yz2="有损音质";yz3="低品质";}
else if(yz==2) {yz2="有损音质";yz3="低品质";}
else if(yz==3) {yz2="有损音质";yz3="低品质";}
else if(yz==4) {yz2="标准音质";yz3="低品质";}
else if(yz==5) {yz2="标准音质";yz3="普通";}
else if(yz==6) {yz2="标准音质";yz3="中品质";}
else if(yz==7) {yz2="标准音质";yz3="中品质";}
else if(yz==8) {yz2="HQ高音质";yz3="HQ高品质";}
else if(yz==9) {yz2="HQ高音质（音质增强）";yz3="HQ高品质";}
else if(yz==10) {yz2="SQ无损音质";yz3="SQ无损";}
else if(yz==11) {yz2="Hi-Res音质";yz3="臻品全景声";}
else if(yz==12) {yz2="杜比全景声";yz3="臻品全景声";}
else if(yz==13) {yz2="臻品全景声";yz3="臻品全景声";}
else if(yz==14) {yz2="臻品母带2.0";yz3="臻品母带";}
else {yz2="SQ无损音质";yz3="SQ无损";}
if(ly) return yz3;
else return yz2;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public String WY_search(String yy, boolean type, int n) {
    // 构建搜索 URL，添加时间戳避免缓存问题
    String timestamp = String.valueOf(System.currentTimeMillis());
    String url = "https://music.163.com/api/search/get/web?s=" + URLEncoder.encode(yy, "UTF-8") + "&type=1&limit=" + wymax + "&timestamp=" + timestamp;

    // 发起 HTTP GET 请求
    String result2 = httpget(url, "");

    // 检查返回结果是否为空或包含错误信息
    if (result2 == null || result2.isEmpty()) {
        return "网易云音乐搜索接口返回结果为空，请检查搜索关键词或网络连接\n";
    }

    try {
        JSONObject json = new JSONObject(result2);
        if (json.has("code") && json.getInt("code") != 200) {
            return "网易云音乐接口返回错误代码：" + json.getInt("code")+"\n";
        }

        JSONObject json_result = json.getJSONObject("result");
        JSONArray json_result_songs = json_result.getJSONArray("songs");

        if (type) {
            // 返回格式化的歌曲列表
            StringBuilder result = new StringBuilder();
            for (int o = 0; o < json_result_songs.length(); o++) {
                JSONObject song = json_result_songs.getJSONObject(o);
                String songName = song.getString("name"); // 歌曲名
                JSONArray artists = song.getJSONArray("artists");
                StringBuilder artistNames = new StringBuilder();
                for (int h = 0; h < artists.length(); h++) {
                    JSONObject artist = artists.getJSONObject(h);
                    artistNames.append(artist.getString("name"));
                    if (h < artists.length() - 1) {
                        artistNames.append("&"); // 歌手之间 分隔
                    }
                }
                result.append(o + 1).append("、").append(songName).append(" —— ").append(artistNames).append("\n");
            }
            return result.toString();
        } else {
            // 返回指定歌曲的 ID
            if (n == 0) {
                return result2; // 返回原始 JSON 数据
            } else {
                if (json_result_songs.length() >= n) {
                    JSONObject song = json_result_songs.getJSONObject(n - 1);
                    return song.getLong("id").toString();
                } else {
                    return "指定的歌曲编号超出范围";
                }
            }
        }
    } catch (Exception e) {
        // 记录日志
        日志(年月日() + " " + 时分秒() + "\nJava报错,网易云音乐搜索接口报错\n" + e);
        return "网易云音乐搜索出错，请检查网络连接或搜索关键词是否合法：" + e.getMessage()+"\n";
    }
}

public String WY_lyrics(String id) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
//        URL urlObj = new URL("http://music.163.com/api/song/media?id="+id);
        URL urlObj = new URL("http://music.163.com/api/song/lyric?os=pc&id="+id+"&lv=-1&kv=-1&tv=-1");
        URLConnection uc = urlObj.openConnection();
        uc.setConnectTimeout(40000);
        uc.setReadTimeout(40000);
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 11; NX629J Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.120 Mobile Safari/537.36");
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
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
    if (buffer.length() == 0) {
        return "访问出错了";
    }
    buffer.delete(buffer.length() - 1, buffer.length());
    JSONObject jiexi = new JSONObject(buffer.toString());
    return jiexi.getJSONObject("lrc").getString("lyric");
}

 
public String WY_analysis(String id, yinzh) {
    if (id.equals("")) {
        return "id为空,无法解析";
    }
    int yinzhi = Integer.parseInt(yinzh);
        String json = "";
        try {
            json = WY_analysis_Luoyue(id, yinzhi,false); // 默认 落月
            if (!json.contains("解析出错")) {
                return json;
            }
            json = WY_analysis_XiangSuyun(id, yinzhi,false);
            if (!json.contains("解析出错")) {
                return json;
            }
            json = WY_analysis_Cenguigui(id, yinzhi);
            if (!json.contains("解析出错")) {
                return json;
            }
            json = WY_analysis_Luoyue(id, yinzhi,true);
            if (!json.contains("解析出错")) {
                return json;
            }
            json = WY_analysis_XiangSuyun(id, yinzhi,true);
            if (!json.contains("解析出错")) {
                return json;
            }
            日志("网易解析出错,songmid:"+id);
            return "网易解析出错";
        } catch (Exception e) {
            Toast("网易解析出错,songmid:"+id);
            return "网易解析出错";
        }
}
 


    public String WY_analysis_Luoyue(String id, int yinzhi, boolean bz) {
        if (yinzhi == 9 && !bz) {
            return "网易解析出错,音质不支持";
        }

        String originalJson = httpget("https://api.vkeys.cn/v2/music/netease?id=" + id + "&quality=" + yinzhi, "");

        try {
            // 解析原始JSON字符串
            JSONObject originalJsonObject = new JSONObject(originalJson);

            if (originalJsonObject.getInt("code") == 200) {
                // 获取data字段的内容
                JSONObject dataJsonObject = originalJsonObject.getJSONObject("data");
                String url = check(dataJsonObject.get("url"));
                String sizeStr = extractSize(dataJsonObject.get("size").toString());

                if (sizeStr == null || sizeStr.isEmpty()) {
                    return "网易解析出错";
                }

                try {
                    double size = Double.parseDouble(sizeStr);
                    if (size < 1.3) {
                        return "网易解析出错";
                    }
                } catch (NumberFormatException e) {
                    return "网易解析出错";
                }

                if (url.equals("解析出错")) {
                    return "网易解析出错";
                }

                dataJsonObject.put("API", "落月API");
                dataJsonObject.put("url", url);
                return dataJsonObject.toString();
            } else {
                log(年月日() + " " + 时分秒() + "\nJava报错,网易解析接口报错(落月)\n接口返回:\n" + originalJson);
                return "网易解析出错,接口返回" + originalJson;
            }
        } catch (Exception e) {
            日志(年月日() + " " + 时分秒() + "\nJava报错,网易解析接口报错(落月)\n接口返回:\n" + originalJson + "\n捕获" + e);
            return "网易解析出错,接口返回" + originalJson;
        }
    }

    // 提取数值部分的方法
    private String extractSize(String data) {
        if (data == null) {
            return null;
        }

        // 移除单位（如"MB"）
        int unitIndex = data.indexOf("MB");
        if (unitIndex != -1) {
            return data.substring(0, unitIndex);
        }

        // 如果没有找到单位，返回原始字符串
        return data;
    }

  

public String WY_analysis_XiangSuyun(String id, int yinzhi,boolean bz) {
if(yinzhi>7&&!bz) return "网易解析出错,音质不支持";
    String originalJson = httpget("https://apiserver.alcex.cn/netease-parse?id=" + id + "&level=" + WY_yinzhi(yinzhi,true),"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        if (originalJsonObject.getInt("status") == 200) {
            JSONObject dataJsonObject = originalJsonObject.getJSONObject("song_info");
            JSONObject dataJsonObject2 = originalJsonObject.getJSONObject("url_info");
            String url = check(dataJsonObject2.get("url"));
            JSONObject newjson = new JSONObject();
            newjson.put("song", dataJsonObject.getString("name"));
            newjson.put("singer", dataJsonObject.getString("artist"));
            newjson.put("url", url);
            newjson.put("link", "https://music.163.com/#/song?id="+id);
            newjson.put("quality", dataJsonObject.getString("level"));
            newjson.put("cover", dataJsonObject.getString("cover"));
            newjson.put("interval", dataJsonObject2.getString("interval"));
            newjson.put("size", dataJsonObject2.getString("size"));
            newjson.put("mid", id);
            newjson.put("API", "像素云API");
            return newjson.toString();
            } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(像素云)\n接口返回:\n"+originalJson);
            return "网易解析出错,接口返回" + originalJson;
        }
    } catch (JSONException e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(像素云)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,JSON解析异常: " + e;
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(像素云)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,发生异常: " + e;
    }
}
public String WY_analysis_Cenguigui(String id, int yinzhi) {
    String originalJson = httpget("https://api.cenguigui.cn/api/netease/music_v1.php?type=json&id=" + id + "&level=" + WY_yinzhi(yinzhi,true),"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        if (originalJsonObject.getInt("code") == 200) {
            JSONObject dataJsonObject = originalJsonObject.getJSONObject("data");
            String url = check(dataJsonObject.get("url"));
            JSONObject newjson = new JSONObject();
            newjson.put("song", dataJsonObject.getString("name"));
            newjson.put("singer", dataJsonObject.getString("artist"));
            newjson.put("url", url);
            newjson.put("link", "https://music.163.com/#/song?id="+id);
            newjson.put("quality", dataJsonObject.getString("format"));
            newjson.put("cover", dataJsonObject.getString("pic"));
            newjson.put("interval", dataJsonObject.getString("duration"));
            newjson.put("size", dataJsonObject.getString("size"));
            newjson.put("mid", id);
            newjson.put("API", "岑鬼鬼API");
            return newjson.toString();
            } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson);
            return "网易解析出错,接口返回" + originalJson;
        }
    } catch (JSONException e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,JSON解析异常: " + e;
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,发生异常: " + e;
    }
}

/*public String WY_analysis_Longzhu(String id, int yinzhi) {
int yinzhi2 = 1;
if(yinzhi==2) yinzhi2==1;
else if(yinzhi==3) yinzhi2=2;
else if(yinzhi==4) yinzhi2=2;
else if(yinzhi==5) yinzhi2=3;
else if(yinzhi==6) yinzhi2=4;
else if(yinzhi==7) yinzhi2=5;
else if(yinzhi==8) yinzhi2=6;
else if(yinzhi==9) yinzhi2=7;
    String originalJson = httpget("https://www.hhlqilongzhu.cn/api/dg_wyymusic.php?type=json&id="+id+"&br="+yinzhi2,"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        if(originalJsonObject.getInt("code")==200){
            String url = check(originalJsonObject.get("music_url"));
            JSONObject newjson = new JSONObject();
            newjson.put("song", originalJsonObject.get("title"));
            newjson.put("singer", originalJsonObject.get("singer"));
            newjson.put("url", url);
            newjson.put("link", "https://music.163.com/#/song?id="+id);
            newjson.put("quality", originalJsonObject.get("id"));
            newjson.put("cover", originalJsonObject.get("cover"));
            newjson.put("size", ""+GetSize(url));
            newjson.put("id", id);
            newjson.put("interval", "接口不支持");
            newjson.put("API", "龙珠API");
            return newjson.toString();
            } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(龙珠)\n接口返回:\n"+originalJson);
            return "网易解析出错,接口返回" + originalJson;
        }
    } catch (JSONException e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(龙珠)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,JSON解析异常: " + e;
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,网易解析接口报错(龙珠)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "网易解析出错,发生异常: " + e;
    }
}*/

public String WY_yinzhi(int yz,boolean yw) {
String yz2 = "";
String yz3 = "";
if(yz==1) {yz2="标准音质";yz3="standard";}
else if(yz==2) {yz2="标准音质";yz3="standard";}
else if(yz==3) {yz2="极高音质";yz3="exhigh";}
else if(yz==4) {yz2="极高音质";yz3="exhigh";}
else if(yz==5) {yz2="无损音质";yz3="lossless";}
else if(yz==6) {yz2="Hi-Res音质";yz3="hires";}
else if(yz==7) {yz2="高清环绕声(高清臻音)";yz3="jyeffect";}
else if(yz==8) {yz2="沉浸环绕声";yz3="sky";}
else if(yz==9) {yz2="超清母带";yz3="jymaster";}
else {yz2="沉浸环绕声";yz3="sky";}
if(yw) return yz3;
else return yz2;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public String kwget(String url) {
	try {
		StringBuffer buffer = new StringBuffer();
		URL urlObj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("User-Agent", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		connection.setRequestProperty("Accept-Encoding", "identity");
		connection.setRequestProperty("Host", "nmobi.kuwo.cn");
		connection.setRequestProperty("Connection", "Keep-Alive");
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			reader.close();
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			reader.close();
		}
		return buffer.toString();
	} catch (Exception e) {
		return null;
	}
}

public String KW_search(String yy, boolean type, int n) {
    // 构建搜索 URL
    String url = "http://search.kuwo.cn/r.s?client=kt&all=" + URLEncoder.encode(yy, "UTF-8") + "&pn=0&rn=" + kwmax + "&ver=kwplayer_ar_99.99.99.99&vipver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerge=1&mobi=1";

    // 发起 HTTP GET 请求
    String result2 = httpget(url, "");

    // 检查返回结果是否为空或包含错误信息
    if (result2 == null || result2.isEmpty()) {
        return "酷我音乐搜索接口返回结果为空，请检查搜索关键词或网络连接。如果问题持续存在，请检查链接的合法性或稍后重试。\n";
    }

    try {
        JSONObject json = new JSONObject(result2);
        JSONArray abslist = json.getJSONArray("abslist");

        if (type) {
            // 返回格式化的歌曲列表
            StringBuilder result = new StringBuilder();
            for (int o = 0; o < abslist.length(); o++) {
                JSONObject song = abslist.getJSONObject(o);
                String songName = song.getString("SONGNAME");
                String albumName = song.getString("ALBUM");
                String artistName = song.getString("ARTIST");
                String songMid = song.getString("DC_TARGETID"); // DC_TARGETID 即为 songmid
                String songInfo = songName + " —— " + artistName;
                if (songMid.isEmpty()) {
                    songInfo += " (无音源)";
                }
                result.append(o + 1).append("、").append(songInfo).append("\n");
            }
            return result.toString();
        } else {
            // 返回指定歌曲的 songmid
            if (n == 0) {
                return result2; // 返回原始 JSON 数据
            } else {
                if (abslist.length() >= n) {
                    JSONObject song = abslist.getJSONObject(n - 1);
                    return song.getString("DC_TARGETID"); // 返回 songmid
                } else {
                    return "指定的歌曲编号超出范围";
                }
            }
        }
    } catch (Exception e) {
        // 记录日志
        日志(年月日() + " " + 时分秒() + "\nJava报错,酷我音乐搜索接口报错\n" + e);
        return "酷我音乐搜索出错，请检查网络连接或搜索关键词是否合法：" + e.getMessage() + "\n如果问题持续存在，请检查链接的合法性或稍后重试。\n";
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;


    public String KW_lyrics(String musicId) {
        // 构建请求 URL
        String urlString = "https://www.kuwo.cn/newh5/singles/songinfoandlrc?musicId=" + musicId;

        StringBuilder buffer = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader reader = null;

        try {
            // 创建 URL 对象
            URL urlObj = new URL(urlString);
            URLConnection uc = urlObj.openConnection();

            // 设置连接参数
            uc.setConnectTimeout(40000);
            uc.setReadTimeout(40000);
            uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 11; NX629J Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.120 Mobile Safari/537.36");
            uc.setRequestProperty("referer", "https://www.kuwo.cn/");

            // 读取响应
            isr = new InputStreamReader(uc.getInputStream(), "utf-8");
            reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            // 解析 JSON 响应
            StringBuilder lyricsBuilder = new StringBuilder();
            JSONObject jsonResponse = new JSONObject(buffer.toString());
            if (jsonResponse.has("data") && jsonResponse.getJSONObject("data").has("lrclist")) {
                JSONArray lrclist = jsonResponse.getJSONObject("data").getJSONArray("lrclist");
                int i = 0;
                while (i < lrclist.length()) {
                    JSONObject lyric = lrclist.getJSONObject(i);
                    String lineLyric = lyric.getString("lineLyric");
                    double time = lyric.getDouble("time");
                    String formattedTime = formatTime(time);

                    // 检查是否包含翻译
                    if (i + 1 < lrclist.length()) {
                        JSONObject nextLyric = lrclist.getJSONObject(i + 1);
                        if (i + 2 < lrclist.length()) {
                            JSONObject nextNextLyric = lrclist.getJSONObject(i + 2);
                            if (nextLyric.getDouble("time") == nextNextLyric.getDouble("time")) {
                                // 当前行是原文，下下一行是翻译
                                lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                                lyricsBuilder.append("译:").append(nextNextLyric.getString("lineLyric")).append("\n");
                                i += 2; // 跳过下两行
                            } else if (lyric.getDouble("time") == nextLyric.getDouble("time")) {
                                // 当前行是原文，下一行是翻译
                                lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                                lyricsBuilder.append("译:").append(nextLyric.getString("lineLyric")).append("\n");
                                i += 2; // 跳过下一行
                            } else {
                                lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                                i++; // 继续处理下一行
                            }
                        } else if (lyric.getDouble("time") == nextLyric.getDouble("time")) {
                            // 当前行是原文，下一行是翻译
                            lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                            lyricsBuilder.append("译:").append(nextLyric.getString("lineLyric")).append("\n");
                            i += 2; // 跳过下一行
                        } else {
                            lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                            i++; // 继续处理下一行
                        }
                    } else {
                        lyricsBuilder.append("[").append(formattedTime).append("] ").append(lineLyric).append("\n");
                        i++; // 继续处理下一行
                    }
                }
                return lyricsBuilder.toString();
            } else {
                return "歌词获取失败，可能歌曲不存在或接口限制";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "访问出错了，请检查链接的合法性或稍后重试";
        }
    }

import java.text.DecimalFormat;

    public String msToMinutesSeconds(long milliseconds) {
        // 将毫秒转换为秒
        long seconds = milliseconds / 1000;

        // 计算分钟数
        long minutes = seconds / 60;

        // 计算剩余的秒数
        long remainingSeconds = seconds % 60;

        // 使用DecimalFormat确保秒数始终以两位数字显示
        DecimalFormat df = new DecimalFormat("00");
        return minutes + ":" + df.format(remainingSeconds);
    }



    public String formatTime(double timeInSeconds) {
        try {
            int totalSeconds = (int) timeInSeconds;
            int milliseconds = (int) ((timeInSeconds - totalSeconds) * 1000);
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;

            // 使用 DecimalFormat 格式化数字
            DecimalFormat df = new DecimalFormat("00");
            DecimalFormat df3 = new DecimalFormat("000");

            String formattedMinutes = df.format(minutes);
            String formattedSeconds = df.format(seconds);
            String formattedMilliseconds = df3.format(milliseconds);

            return formattedMinutes + ":" + formattedSeconds + "." + formattedMilliseconds;
        } catch (Exception e) {
            return "99:99.999";
        }
    }

public String KW_analysis(String id, yinzh) {
    if (id.equals("")) {
        return "id为空,无法解析";
    }
    int yinzhi = Integer.parseInt(yinzh);
        String json = "";
        try {
            json = KW_analysis_Cenguigui(id, yinzhi);
            if (!json.contains("解析出错")) {
                return json;
            }
            json = KW_analysis_beiyong(id,yinzhi);
            if (!json.contains("解析出错")) {
                return json;
            }
            return "酷我解析出错";
            日志("酷我解析出错,songmid:"+id);
        } catch (Exception e) {
            Toast("酷我解析出错,songmid:"+id);
            return "酷我解析出错";
        }
}

public String KW_analysis_Cenguigui(String id, int yinzhi) {
    String originalJson = httpget("https://kw-api.cenguigui.cn?type=song&id=" + id + "&level=" + KW_yinzhi(yinzhi),"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        if (originalJsonObject.getInt("code") == 200) {
            JSONObject dataJsonObject = originalJsonObject.getJSONObject("data");
            String url = check(dataJsonObject.get("url"));
            JSONObject newjson = new JSONObject();
            newjson.put("song", dataJsonObject.getString("name"));
            newjson.put("singer", dataJsonObject.getString("artist"));
            newjson.put("url", url);
            newjson.put("link", "http://www.kuwo.cn/play_detail/"+id);
            newjson.put("quality", dataJsonObject.getString("quality"));
            newjson.put("cover", dataJsonObject.getString("pic"));
            newjson.put("interval", msToMinutesSeconds(dataJsonObject.get("duration")));
            newjson.put("size", dataJsonObject.getString("size"));
            newjson.put("mid", id+"");
            newjson.put("API", "岑鬼鬼API");
            return newjson.toString();
            } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson);
            return "酷我解析出错,接口返回" + originalJson;
        }
    } catch (JSONException e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "酷我解析出错,JSON解析异常: " + e;
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(岑鬼鬼)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "酷我解析出错,发生异常: " + e;
    }
}

public String KW_analysis_beiyong(String id,int yinzhi) {
String quality = "320kmp3";
if(yinzhi==1) quality = "48";
else if(yinzhi==2) quality = "96kwma";
else if(yinzhi==3) quality = "192kogg";
else if(yinzhi==4) quality = "128kmp3";
else if(yinzhi==5) quality = "320kmp3";
else if(yinzhi==6) quality = "2000kflac";
else if(yinzhi==7) quality = "2000kflac";
    String originalJson = kwget("http://nmobi.kuwo.cn/mobi.s?f=web&source=kwplayerhd_ar_4.3.0.8_tianbao_T1A_qirui.apk&type=convert_url_with_sign&rid=" + id + "&br="+ quality);
    String originalJson2 = httpget("https://www.kuwo.cn/newh5/singles/songinfoandlrc?musicId="+ id,"");
    try {
        JSONObject originalJsonObject = new JSONObject(originalJson);
        JSONObject originalJsonObject2 = new JSONObject(originalJson2);
        if (originalJsonObject.getInt("code") == 200&&originalJsonObject2.getInt("status") == 200) {
            JSONObject dataJsonObject = originalJsonObject.getJSONObject("data");
            JSONObject info = originalJsonObject2.getJSONObject("data").getJSONObject("songinfo");
            String url = check(dataJsonObject.get("url"));
            JSONObject newjson = new JSONObject();
            newjson.put("song", info.getString("songName"));
            newjson.put("singer", info.getString("artist"));
            newjson.put("url", url);
            newjson.put("link", "http://www.kuwo.cn/play_detail/"+id);
            newjson.put("quality", dataJsonObject.get("format"));
            newjson.put("cover", info.getString("pic"));
            newjson.put("interval", info.get("songTimeMinutes"));
            newjson.put("size", GetSize(url));
            newjson.put("mid", id);
            newjson.put("API", "备用酷我API");
            return newjson.toString();
            } else {
            日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(备用)\n接口返回:\n"+originalJson);
            return "酷我解析出错,接口返回" + originalJson;
        }
    } catch (JSONException e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(备用)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "酷我解析出错,JSON解析异常: " + e;
    } catch (Exception e) {
        日志(年月日()+" "+时分秒()+"\nJava报错,酷我解析接口报错(备用)\n接口返回:\n"+originalJson+"捕获:"+e);
        return "酷我解析出错,发生异常: " + e;
    }
}

public String KW_yinzhi(int yz) {
String yz2 = "";
if(yz==1) {yz2="acc";}
else if(yz==2) {yz2="wma";}
else if(yz==3) {yz2="ogg";}
else if(yz==4) {yz2="standard";}
else if(yz==5) {yz2="exhigh";}
else if(yz==6) {yz2="lossless";}
else if(yz==7) {yz2="hires";}
else {yz2="lossless";}
return yz2;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public String wlwzt(String text) {
String test =text.replaceAll("\n","\\\\n").replaceAll("&","与");
String tu=post("http://lin.nvmic.com/img?font=6&quality="+piclevel+"&form=json","text="+test);
try{
JSONObject tujson = new JSONObject(tu);
String tu_url = tujson.getString("url");
String path = 羽雫+"图片/"+time+".png";
if(!download(url,path)){
sendReply(Mao,"[atUin="+uin+"]\nOh......网络文转图失败了......");
新建文件夹(羽雫+"图片");
return;
}
return path;
} catch (IOException e) {
return "网络文转图出错"+e;
}
}

public void sendArk(String nr, String qun, int type) {
String nrn = URLEncoder.encode(nr, "UTF-8");
//String ark = post("https://api.s01s.cn/API/ark_wb/","wb="+nr.replaceAll("&","/")+"&wx="+ark_yx);
String ark = post("https://api.s01s.cn/API/ark_wb/","wb="+nrn+"&wx="+ark_yx);
sendCard(qun,ark,type);
}

public void store(msg,from,time,uin){
写(uin,"歌名",msg);
写(uin,"平台",from);
写(uin,"时间",time);
}

public void time_删除(String path){
new Thread(new Runnable() {
public void run() {
try {
Thread.sleep(sleeptime);
} catch (e) {
//问题不大 不提示了
}
删除(path);
}}).start();
}

public String changesilk(String path,Object Mao) {
if(读("猫羽雫","转Silk", false)){
try{
String urls = sendPost(path);
String path2 = fileTosilk(path);
boolean zt = download(urls,path2);
if(!zt){
sendReply(Mao,"[atUin="+data.uin+"]\nOh......转silk失败了......(大概率接口失效)");
新建文件夹(羽雫+"语音");
return;
}else{
删除(path);
return path2;
}
}catch(Exception e){
Toast("转换失败"+getDetailedError(e));
}
}else{
return path;
}
}

public String changesilk(String path) {
if(读("猫羽雫","转Silk", false)){
try{
String urls = sendPost(path);
String path2 = fileTosilk(path);
boolean zt = download(urls,path2);
if(!zt){
Toast("Oh......转silk失败了......(大概率接口失效)");
新建文件夹(羽雫+"语音");
return;
}else{
删除(path);
return path2;
}
}catch(Exception e){
Toast("转换失败"+getDetailedError(e));
}
}else{
return path;
}
}

public String dislodge(String m) {
    m = m.replace("\\", "_")
           .replace("/", "_")
           .replace(":", "：")
           .replace("*", "-")
           .replace("?", "?")
           .replace("\"", "''")
           .replace("<", "＜")
           .replace(">", "＞")
           .replace("|", "");
    return m;
}

public void 日志(msg){
try{
Object nr = readFileContent(羽雫+ "log.txt");
if(nr==null) 新建(羽雫+ "log.txt");
else put(羽雫+ "log.txt",nr+"\n\n"+msg+"\n"+包体+"("+包名+")");
} catch (e) {
Toast("1");
新建(羽雫+ "log.txt");
}
}

public String check(String urlString){
    HttpURLConnection connection = null;
    int responseCode = 404;
    try {
        URL url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();

        // 设置不跟随重定向
        connection.setInstanceFollowRedirects(false);
        
        // 设置连接超时和读取超时时间（毫秒）
        connection.setConnectTimeout(40000);
        connection.setReadTimeout(40000);
        
        // 发送HEAD请求
        connection.setRequestMethod("HEAD");
        
        // 获取响应码
        responseCode = connection.getResponseCode();

        // 检查是否为重定向
        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
            || responseCode == HttpURLConnection.HTTP_MOVED_PERM
            || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            // 获取Location头
            String redirectUrl = connection.getHeaderField("Location");
            // 递归调用方法
            return check(redirectUrl);
        } else if (responseCode == HttpURLConnection.HTTP_OK) {
            return urlString;
        }

    } catch (e) {
        return "解析出错";
    }

        if (connection != null) {
            connection.disconnect();
        }

    return "解析出错";
}

public String GetSize(String fileUrl) {
    try {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();

        int fileSizeBytes = connection.getContentLength();
        if (fileSizeBytes > 0) {
            double fileSizeMB = fileSizeBytes / (1024.0 * 1024.0); // 转换为兆字节
            if (fileSizeMB < 1.3) {
                return "文件太小，大概率API失效，无法处理"; // 判断文件大小
            } else {
                StringBuilder sizeBuilder = new StringBuilder();
                sizeBuilder.append(fileSizeMB);
                int decimalIndex = sizeBuilder.indexOf(".");
                if (decimalIndex != -1 && sizeBuilder.length() - decimalIndex > 3) {
                    sizeBuilder.setLength(decimalIndex + 3); // 保留两位小数
                }
                return sizeBuilder.append("MB").toString(); // 文件大小大于等于1MB，返回格式化后的大小
            }
        } else {
            return "无法处理"; // 如果无法获取文件大小
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "无法处理，发生错误"; // 捕获异常并返回错误信息
    }
}

public String getLocation(String downloadUrl) {
    String na;
    if(downloadUrl == null || "".equals(downloadUrl)) {
        na= "不存在";
    } else {
        URL url = new URL(downloadUrl);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setFollowRedirects(true);
            conn.getResponseCode();
            na=conn.getHeaderField("Location");
            if(na==null) na=conn.getURL().toString();
        } catch (IOException e) {
            na= "未知";
        } finally {
            conn.disconnect();
        }
    }
    return na;
}
///////
public void 发送歌词(int type, String qun, String mid, String currentPlatform) {
    String lyrics = "";
    if (currentPlatform.equals("QQ")) lyrics = QQ_lyrics(mid);
    else if (currentPlatform.equals("网易")) lyrics = WY_lyrics(mid);
    else if (currentPlatform.equals("酷我")) lyrics = KW_lyrics(mid);

    if (lyrics.isEmpty()) {
        Toast("歌词获取失败");
        return;
    }

    if (lyricstype==1) {
        sendArk(lyrics, qun, mtype);
    }else if (lyricstype==2) {
            sendMsg(qun, lyrics, type);
    }else if (lyricstype==3) {
            String path = wlwzt(lyrics);
            sendImg(qun, path, type);
            time_删除(path);
    }else{
            String path = MakeTextPhoto(2, lyrics, picapi, colors);
            sendImg(qun, path, type);
            time_删除(path);
    }

    OK = false;
}

public void 发送链接(int type, String qun, String mid, String currentPlatform, String quality) {
    if (quality.isEmpty()) {
        if (currentPlatform.equals("QQ")) quality = qqyz_url.toString();
        else if (currentPlatform.equals("网易")) quality = wyyz_url.toString();
        else if (currentPlatform.equals("酷我")) quality = kwyz_url.toString();
    }

    String jiexii = "";
    JSONObject jiexiObj = null;

    if (currentPlatform.equals("QQ")) {
        jiexii = QQ_analysis(mid, quality);
        if (jiexii.equals("QQ解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("网易")) {
        jiexii = WY_analysis(mid, quality);
        if (jiexii.equals("网易解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("酷我")) {
        jiexii = KW_analysis(mid, quality);
        if (jiexii.equals("酷我解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else {
        sendMsg(qun, "不支持的平台", type);
        OK = false;
        return;
    }

    String song = jiexiObj.optString("song");
    String pic = jiexiObj.optString("cover");
    String singer = jiexiObj.optString("singer");
    String url = jiexiObj.optString("url");
    String midStr = jiexiObj.optString("mid");
    String interval = jiexiObj.optString("interval");
    String link = jiexiObj.optString("link");
    String qualityStr = jiexiObj.optString("quality");
    String size = jiexiObj.optString("size");
    String api = jiexiObj.optString("API");

    String path = 羽雫 + "图片/" + System.currentTimeMillis() + ".png";
    String menu = "";

    if (currentPlatform.equals("QQ")) {
        menu = "来自QQ音乐(" + api + ")\n" +
               "歌名: " + song + "\n" +
               "歌手: " + singer + "\n" +
               "歌曲mid: " + midStr + "\n" +
               "歌曲小标题: " + jiexiObj.optString("subtitle") + "\n" +
               "发布时间: " + jiexiObj.optString("time") + "\n" +
               "歌曲时长: " + interval + "\n" +
               "歌曲页面: " + link + "\n" +
               "音质: " + qualityStr + "\n" +
               "歌曲大小: " + size + "\n" +
               "播放链接: " + url;
    } else if (currentPlatform.equals("网易")) {
        menu = "来自网易云音乐(" + api + ")\n" +
               "歌名: " + song + "\n" +
               "歌手: " + singer + "\n" +
               "歌曲id: " + midStr + "\n" +
               "歌曲时长: " + interval + "\n" +
               "歌曲页面: " + link + "\n" +
               "音质: " + qualityStr + "\n" +
               "歌曲大小: " + size + "\n" +
               "播放链接: " + url;
    } else if (currentPlatform.equals("酷我")) {
        menu = "来自酷我音乐(" + api + ")\n" +
               "歌名: " + song + "\n" +
               "歌手: " + singer + "\n" +
               "歌曲id: " + midStr + "\n" +
               "歌曲时长: " + interval + "\n" +
               "歌曲页面: " + link + "\n" +
               "音质: " + qualityStr + "\n" +
               "歌曲大小: " + size + "\n" +
               "播放链接: " + url;
    }

    if (!download(pic, path)) {
        sendMsg(qun, menu, type);
        新建文件夹(羽雫 + "图片");
        return;
    }

    sendMsg(qun, menu + "\n[pic=" + path + "]", type);
    time_删除(path);
    OK = false;
}

public void 发送文件(int type, String qun, String mid, String currentPlatform, String quality) {
    if (quality.isEmpty()) {
        if (currentPlatform.equals("QQ")) quality = qqyz_wj.toString();
        else if (currentPlatform.equals("网易")) quality = wyyz_wj.toString();
        else if (currentPlatform.equals("酷我")) quality = kwyz_wj.toString();
    }

    String jiexii = "";
    JSONObject jiexiObj = null;

    if (currentPlatform.equals("QQ")) {
        jiexii = QQ_analysis(mid, quality);
        if (jiexii.equals("QQ解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("网易")) {
        jiexii = WY_analysis(mid, quality);
        if (jiexii.equals("网易解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("酷我")) {
        jiexii = KW_analysis(mid, quality);
        if (jiexii.equals("酷我解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else {
        sendMsg(qun, "不支持的平台", type);
        OK = false;
        return;
    }

    String song = dislodge(jiexiObj.optString("song"));
    String singer = dislodge(jiexiObj.optString("singer"));
    String url = jiexiObj.optString("url");
    String qualityStr = jiexiObj.optString("quality");
    String hz = getFileExtension(url);

    String path = 羽雫 + "文件/" + song + "--" + singer + "(" + qualityStr + ")." + hz;
    String toastMsg = "正在下载" + currentPlatform + "音乐中[" + song + "--" + singer + "]的文件";
    Toast(toastMsg);

    if (!download(url, path)) {
        Toast("Oh......下载失败了......");
        新建文件夹(羽雫 + "文件");
        return;
    }

    sendFile(qun, path, type);
    time_删除(path);
    OK = false;
}

public void 发送语音(int type, String qun, String mid, String currentPlatform, String quality) {
    if (quality.isEmpty()) {
        if (currentPlatform.equals("QQ")) quality = qqyz_yy.toString();
        else if (currentPlatform.equals("网易")) quality = wyyz_yy.toString();
        else if (currentPlatform.equals("酷我")) quality = kwyz_yy.toString();
    }

    String jiexii = "";
    JSONObject jiexiObj = null;

    if (currentPlatform.equals("QQ")) {
        jiexii = QQ_analysis(mid, quality);
        if (jiexii.equals("QQ解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("网易")) {
        jiexii = WY_analysis(mid, quality);
        if (jiexii.equals("网易解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("酷我")) {
        jiexii = KW_analysis(mid, quality);
        if (jiexii.equals("酷我解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else {
        sendMsg(qun, "不支持的平台", type);
        OK = false;
        return;
    }

    String song = dislodge(jiexiObj.optString("song"));
    String singer = dislodge(jiexiObj.optString("singer"));
    String url = jiexiObj.optString("url");
    String hz = getFileExtension(url);
    String path = 羽雫 + "语音/" + time + "." + hz;

    String toastMsg = "正在下载" + currentPlatform + "音乐中[" + song + "--" + singer + "]的音频";
    Toast(toastMsg);

    if (!download(url, path)) {
        Toast("Oh......下载失败了......");
        新建文件夹(羽雫 + "语音");
        return;
    } else {
        path = changesilk(path);
    }

    sendPtt(qun, path, type);
    time_删除(path);
    OK = false;
}

public void 发送卡片(int type, String qun, String mid, String currentPlatform, String quality) {

    if (quality.isEmpty()) {
        if (currentPlatform.equals("QQ")) quality = qqyz_ark.toString();
        else if (currentPlatform.equals("网易")) quality = wyyz_ark.toString();
        else if (currentPlatform.equals("酷我")) quality = kwyz_ark.toString();
    }

    String jiexii = "";
    JSONObject jiexiObj = null;

    if (currentPlatform.equals("QQ")) {
        jiexii = QQ_analysis(mid, quality);
        if (jiexii.equals("QQ解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("网易")) {
        jiexii = WY_analysis(mid, quality);
        if (jiexii.equals("网易解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else if (currentPlatform.equals("酷我")) {
        jiexii = KW_analysis(mid, quality);
        if (jiexii.equals("酷我解析出错")) {
            sendMsg(qun, jiexii, type);
            OK = false;
            return;
        }
        try {
            jiexiObj = new JSONObject(jiexii);
        } catch (JSONException e) {
            sendMsg(qun, "解析JSON出错", type);
            OK = false;
            return;
        }
    } else {
        sendMsg(qun, "不支持的平台", type);
        OK = false;
        return;
    }

    String song = jiexiObj.optString("song");
    String pic = jiexiObj.optString("cover");
    String singer = jiexiObj.optString("singer");
    String url = jiexiObj.optString("url");
    String midStr = jiexiObj.optString("mid");

    if (type == 2) {
        if (currentPlatform.equals("QQ")) {
            qqsendTroopMusic(qun, song, singer, "https://i.y.qq.com/v8/playsong.html?songmid=" + midStr, url, pic);
        } else if (currentPlatform.equals("网易")) {
            wysendTroopMusic(qun, song, singer, "https://music.163.com/#/song?id=" + midStr, url, pic);
        } else if (currentPlatform.equals("酷我")) {
            kwsendTroopMusic(qun, song, singer, "http://www.kuwo.cn/play_detail/" + midStr, url, pic);
        }
    } else if (type == 1) {
        if (currentPlatform.equals("QQ")) {
            qqsendFriendMusic(qun, song, singer, "https://i.y.qq.com/v8/playsong.html?songmid=" + midStr, url, pic);
        } else if (currentPlatform.equals("网易")) {
            wysendFriendMusic(qun, song, singer, "https://music.163.com/#/song?id=" + midStr, url, pic);
        } else if (currentPlatform.equals("酷我")) {
            kwsendFriendMusic(qun, song, singer, "http://www.kuwo.cn/play_detail/"+ midStr, url, pic);
        }
    }
    OK = false;
}

private String getFileExtension(String url) {
    if (url.contains(".flac")) return "flac";
    if (url.contains(".m4a")) return "m4a";
    if (url.contains(".mp3")) return "mp3";
    if (url.contains(".ogg")) return "ogg";
    if (url.contains(".acc")) return "acc";
    if (url.contains(".wma")) return "wma";
    return "mp3";
}