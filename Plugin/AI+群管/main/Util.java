import org.json.JSONObject;
import org.json.JSONArray;
public boolean signAudit(String uin,String groupUin){
if(uin.equals(MyQQ)){
return true;
}
String Path=dataPath+"/"+groupUin+"/data.json";
try{
String data=readFileData(Path);
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(uin)){
return true;
}
}
}
}catch(Exception e){}
return false;
}
public String addAudit(String uin,String groupUin/*,String name*/){
if(uin.equals(MyQQ)){
return "不能添加自己为代管";
}
SignFolder(groupUin);
String Path=dataPath+"/"+groupUin+"/data.json";
String data=readFileData(Path);
JSONObject object;
JSONArray array;
try{
if(data!=null){
object=new JSONObject(data);
array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(uin)){
return "用户:"+uin+"\n已经是代管了";
}
}
}else{
object=new JSONObject();
array=new JSONArray();
object.put("data",array);
}
JSONObject newObject=new JSONObject();
//newObject.put("name",name);
newObject.put("uin",uin);
newObject.put("time",convertTime(System.currentTimeMillis()));
array.put(newObject);
writeFileData(Path,object.toString(2));
return "代管:"+uin+"添加成功";
}catch(Exception e){
return "错误"+e;
}
}
public String deleteAudit(String uin,String groupUin){
boolean OK=false;
String Path=dataPath+"/"+groupUin+"/data.json";
String data=readFileData(Path);
try{
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(uin)){
array.remove(i);
OK=true;
break;
}
}
if(OK){
writeFileData(Path,object.toString(2));
return "代管:"+uin+"删除成功";
}else{
return uin+"不是本群的代管，无法删除";
}
}else{
return uin+"不是本群的代管，无法删除";
}
}catch(Exception e){
return "错误"+e;
}
}

public String getAudit(String groupUin){
String Path=dataPath+"/"+groupUin+"/data.json";
String data=readFileData(Path);
String returnData="";
try{
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
if(array.length()==0){
return "本群还没有代管";
}
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
String msg=/*"用户:"+info.getString("name")+*/"QQ:"+info.getString("uin")+"\n添加时间:"+info.getString("time")+"\n---------\n";
returnData+=msg;
}
return returnData;
}else{
return "本群还没有代管";
}
}catch(Exception e){
return "错误"+e;
}
}
//1退群黑名单 2本群黑名单
public String addBlack(String qun,String qq,String addUin,int type){
if(type==2){
if(qq.equals(addUin)){
return "不能添加自己为黑名单捏";
}
}
SignFolder(qun);
String path;
if(type==1){
path=dataPath+"/"+qun+"/quit.json";
}else{
path=dataPath+"/"+qun+"/black.json";
}
String data=readFileData(path);
JSONObject object;
JSONArray array;
try{
if(data!=null){
object=new JSONObject(data);
array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(qq)){
return "用户:"+qq+"\n已经黑名单了";
}
}
}else{
object=new JSONObject();
array=new JSONArray();
object.put("data",array);
}
JSONObject newObject=new JSONObject();
//newObject.put("name",name);
newObject.put("uin",qq);
newObject.put("time",convertTime(System.currentTimeMillis()));
array.put(newObject);
writeFileData(path,object.toString(2));
return "黑名单:"+qq+"添加成功";
}catch(Exception e){
return "错误"+e;
}
}
public String deleteBlack(String groupUin,String uin,int type){
String path;
boolean OK=false;
if(type==1){
path=dataPath+"/"+groupUin+"/quit.json";
}else{
path=dataPath+"/"+groupUin+"/black.json";
}
String data=readFileData(path);
try{
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(uin)){
array.remove(i);
OK=true;
break;
}
}
if(OK){
writeFileData(path,object.toString(2));
return "黑名单:"+uin+"删除成功";
}else{
return uin+"不是本群的黑名单，无法删除";
}
}else{
return uin+"不是本群的黑名单，无法删除";
}
}catch(Exception e){
return "错误"+e;
}
}
public String getBlack(String groupUin,int type){
String path;
if(type==1){
path=dataPath+"/"+groupUin+"/quit.json";
}else{
path=dataPath+"/"+groupUin+"/black.json";
}
String data=readFileData(path);
String returnData="";
try{
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
if(array.length()==0){
return "本群还没有黑名单";
}
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
String msg=/*"用户:"+info.getString("name")+*/"QQ:"+info.getString("uin")+"\n添加时间:"+info.getString("time")+"\n---------\n";
returnData+=msg;
}
return returnData;
}else{
return "本群还没有黑名单";
}
}catch(Exception e){
return "错误"+e;
}
}
public boolean isBlack(String groupUin,String uin,int type){
String path;
if(type==1){
path=dataPath+"/"+groupUin+"/quit.json";
}else{
path=dataPath+"/"+groupUin+"/black.json";
}
try{
String data=readFileData(path);
if(data!=null){
JSONObject object=new JSONObject(data);
JSONArray array=object.getJSONArray("data");
for(int i=0;i<array.length();i++){
JSONObject info=array.getJSONObject(i);
if(info.getString("uin").equals(uin)){
return true;
}
}
}
}catch(Exception e){}
return false;
}
public void SignFolder(String groupUin){
File file=new File(dataPath+"/"+groupUin);
if(!file.exists()){
file.mkdirs();
}
}