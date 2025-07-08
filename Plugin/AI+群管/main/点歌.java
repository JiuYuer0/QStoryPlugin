String QQMUSIC_COOKIE="uin=o"+MyUin+"; skey="+getSkey()+"; p_uin=o"+MyUin+"; p_skey="+getPskey("y.qq.com")+";";
HashMap dgMap = new HashMap();
public String QQMusicGetWithAgentCookie(String url,String Agent,String Cookie) {
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.setRequestProperty("User-agent",Agent);
				uc.setRequestProperty("Cookie",Cookie);
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
public String GetQQMusicSongUrl2(String SongMid)//来自绿逗以及刺痛
{
try{
	String text = QQMusicGetWithAgentCookie("https://i.y.qq.com/v8/playsong.html?songmid=" + SongMid,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.105 Safari/537.36",QQMUSIC_COOKIE);
		int index = text.indexOf("\"m4aUrl\":\"");
		int index2 = text.indexOf("\",", index + 11);
		String SongUrl = text.substring(index + 10, index2);
		if(!SongUrl.startsWith("http")) {
		return "QQ音乐"+SongMid+"直链3获取失败\n可能需要单独付费或者VIP";
		//return "";
		}
		return SongUrl;
			}
			catch(Throwable e)
		{
			return "QQ音乐"+SongMid+"直链3获取失败\n可能需要单独付费或者VIP\n"+e;
	   //     return ""+e;
		}
}
public String GetQQMusicSongUrl(String SongMid)
{
	try
	{
	String text = QQMusicGetWithAgentCookie("http://u6.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%220%22%2C%22songmid%22%3A%5B%22"+SongMid+"%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%22"+MyQQ+"%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%7D","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.105 Safari/537.36",QQMUSIC_COOKIE);
		JSONObject json = new JSONObject(text);
            String purl=json.get("req_0").get("data").get("midurlinfo").get(0).get("purl");
		String SongUrl = "http://isure.stream.qqmusic.qq.com/"+purl;
		if(!SongUrl.contains("vkey")){
		return GetQQMusicSongUrl2(SongMid);
		}
		return SongUrl;
	}
		catch(Exception e)
		{
		//return "QQ音乐"+SongMid+"直链2获取失败\n可能需要单独付费或者VIP\n"+o;
		return GetQQMusicSongUrl2(SongMid);
		}
//	Toast("QQ音乐"+SongMid+"直链2获取失败");
	return "";
}
public String QQMusicPost(String urlPath, String cookie,String data)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL url = new URL(urlPath);
			uc = (HttpURLConnection) url.openConnection();
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setConnectTimeout(2000000);
			uc.setReadTimeout(2000000);
			uc.setRequestMethod("POST");
			if(cookie != null) {
			CookieManager cookieManager = new CookieManager();
CookieHandler.setDefault(cookieManager);
			uc.setRequestProperty("Cookie",cookie);
			}
			//uc.setRequestProperty("Referer","https://music.163.com");
            uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10; ELE-AL00 Build/HUAWEIELE-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/98.0.4758.102 MQQBrowser/6.2 TBS/046403 Mobile Safari/537.36 V1_AND_SQ_8.9.33_3772_YYB_D QQ/8.9.33.10335 NetType/4G WebP/0.3.0 AppId/537151682 Pixel/1080 StatusBarHeight/75 SimpleUISwitch/0 QQTheme/2031753 StudyMode/0 CurrentMode/0 CurrentFontScale/1.0 GlobalDensityScale/0.90000004 AllowLandscape/false InMagicWin/0");
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
                    myToast("错误:\n"+e);
                }
            }
        if(buffer.length()==0)return buffer.toString();
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
}
public List QQMusicSearch(String msg){
List QQMusicSearchData=new ArrayList();
String data=QQMusicPost("https://u.y.qq.com/cgi-bin/musicu.fcg?_webcgikey=DoSearchForQQMusicDesktop",QQMUSIC_COOKIE,"{\"comm\":{\"format\":\"json\",\"inCharset\":\"utf-8\",\"outCharset\":\"utf-8\",\"notice\":0,\"platform\":\"h5\",\"needNewCode\":1,\"ct\":23,\"cv\":0},\"req\":{\"method\":\"DoSearchForQQMusicDesktop\",\"module\":\"music.search.SearchCgiService\",\"param\":{\"remoteplace\":\"txt.mqq.all\",\"search_type\":0,\"query\":\""+msg+"\",\"page_num\":1,\"num_per_page\":60}}}");
try{
JSONObject json = new JSONObject(data);
json = json.get("req").getJSONObject("data").get("body");
JSONArray array = json.getJSONObject("song").getJSONArray ("list"); 
for(int i = 0; i < array.length(); i++)
{
JSONObject json2 = new JSONObject();
try{
JSONObject newjson = array.getJSONObject(i);
String name2=newjson.getJSONObject("album").getString("name");
String pmid=newjson.getJSONObject("album").getString("pmid");
String name3=newjson.getString("title").replaceAll("<.*?>","");
String mid=newjson.getString("mid");
String time=newjson.getString("interval");
String from=newjson.get("file").get("media_mid");
String musicdownloadurl=newjson.get("file").get("media_mid");
JSONArray array2=newjson.getJSONArray("singer");
json2.put("name",name2);
json2.put("musicurl",musicdownloadurl);
json2.put("picurl","https://y.qq.com/music/photo_new/T002R300x300M000"+pmid+".jpg");
json2.put("title",name3);
json2.put("mid",mid);
json2.put("time",time);
json2.put("from",from);
String singer="";
for(int h=0;h<array2.length();h++)
{
if(h==0) singer = array2.getJSONObject(h).getString("name");
else singer = singer+"/"+array2.getJSONObject(h).getString("name");
}
json2.put("singer",singer);
//msg+=i2+"."+name3+"--"+singer+"["+name2+"]\n";
QQMusicSearchData.add(json2);
}catch(Exception e){}
}
}catch(Exception e){
Toast(""+e);
}
return QQMusicSearchData;
}
public void QQ点歌(Object data){
if(getString(data.GroupUin,"dg")!=null){
String qq=data.UserUin;
String qun=data.GroupUin;

String text=data.MessageContent;
if(text.startsWith("点歌")){
long time=System.currentTimeMillis()/1000;
String msg=text.substring(2);
new Thread(new Runnable(){
public void run(){
List list=QQMusicSearch(msg);
dgClass dgclass=new dgClass();
dgclass.setUin(qq);
dgclass.setTime(time*60*10);//10分钟
dgclass.setID(qq+qun);
dgclass.setList(list);
dgMap.put(qq+qun,dgclass);
String returnMsg="";
for(int i = 0; i < list.size(); i++){
JSONObject json=list.get(i);
String textMsg=json.getString("title")+"-"+json.getString("singer")+"["+json.getString("name")+"]";
textMsg=textMsg.replace("[]","");
returnMsg+=(i+1)+"."+textMsg+"\n";
}
if(list.size()>0){
sendReply(qun,data,returnMsg+"\n--------\n发送序号进行点歌，10分钟内有效");
}else{
sendReply(qun,data,"没有找到");
}
}}).start();
}
if(text.matches("^[0-9]*$")&&dgMap.containsKey(qq+qun)){
int size=Integer.parseInt(text);
long time=System.currentTimeMillis()/1000;
dgClass dgclass=dgMap.get(qq+qun);
if(time>=dgclass.getTime()){
return;
}
int maxSize=dgclass.getList().size();
List list=dgclass.getList();
if(maxSize>=size&&size!=0){
JSONObject json=list.get(size-1);
sendReply(qun,data,"下载中\n"+json.getString("title")+"-"+json.getString("singer")+"["+json.getString("name")+"]");
String url=GetQQMusicSongUrl(json.getString("mid"));
if(url.startsWith("http")){
String downPath=AppPath+"/cache/"+System.currentTimeMillis()+".mp3";
download(url,downPath);
sendVoice(qun,"",downPath);
}else{
sendReply(qun,data,"下载出错\n"+url);
}
}
}
}
}
public class dgClass{
private long time;
private String uin;
private String id;
private List list;
public void setTime(long time){
this.time=time;
}
public long getTime(){
return time;
}
public void setUin(String uin){
this.uin=uin;
}
public String getUin(){
return uin;
}
public void setID(String id){
this.id=id;
}
public String getID(){
return id;
}
public void setList(List list){
this.list=list;
}
public List getList(){
return list;
}
}