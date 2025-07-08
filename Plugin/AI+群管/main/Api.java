import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public static String convertTime(long time) {
    try{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
        }catch(Exception e){
        return"时间获取错误";
        }
    }
public int parseTimeBymessage(Object msg){
	int timeStartIndex = msg.MessageContent.lastIndexOf(" ");
	String date = msg.MessageContent.substring(timeStartIndex +1);
	date = date.trim();
	String t="";
	if(date != null && !"".equals(date)){
		for(int i=0;i<date.length();i++){
			if(date.charAt(i)>=48 && date.charAt(i)<=57){
				t +=date.charAt(i);
			}
		}
	}
    int time=Integer.parseInt(t);
	if(date.contains("天")){
		return time*60*60*24;
	}
	else if(date.contains("时") || date.contains("小时") ){
	 	return 60*60*time;
	}
	else if(date.contains("分") || date.contains("分钟") ){
		return 60*time;
    }
    return time;
}

public String getReturn(String text,String uin,String qun){
String id=makeID(uin,qun);
if(id==null){
return "创建ID出错";
}
//Toast("开始"+id);
String url="https://kimi.moonshot.cn/api/chat/"+id+"/completion/stream";
String returnText="";
try{
JSONObject json=new JSONObject("{\"kimiplus_id\": \"kimi\",\"extend\": {\"sidebar\": true},\"model\": \"kimi\",\"use_research\": false,\"use_search\": true,\"messages\": [{\"role\": \"user\",\"content\": \""+text+"\"}],\"refs\": [],\"history\": [],\"scene_labels\": []}");
String msg=post(url,json.toString());
String makeMsg=msg.replace("data: ","");
//sendMsg(qun,"",makeMsg);
Scanner scanner = new Scanner(makeMsg);
while (scanner.hasNextLine()) {
String line = scanner.nextLine();
try{
JSONObject b=new JSONObject(line);
b.getString("view");
b.getString("idx_z");
b.getString("idx_s");
b.getString("event");
returnText+=b.getString("text");
}catch(Exception e){}
}
}catch(Exception e){}
return returnText;
}
public String makeID(String uin,String qun){
String url="https://kimi.moonshot.cn/api/chat";
String id="";
if(getString(qun+uin,"kimiID")==null){
try{
String data=post(url,"{\"name\": \""+uin+"\",\"born_from\": \"chat\",\"kimiplus_id\": \"kimi\",\"is_example\": false,\"source\": \"web\",\"tags\": []}");
JSONObject b=new JSONObject(data);
id=b.getString("id");
putString(qun+uin,"kimiID",id);
}catch(Exception e){
return null;
}
}else{
id=getString(qun+uin,"kimiID");
}
return id;
}
public boolean deleteID(String uin,String qun){
putString(qun+uin,"kimiID",null);
return true;
}


