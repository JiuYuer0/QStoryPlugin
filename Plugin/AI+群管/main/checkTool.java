public void putCheck(String qun,String id,boolean check){
SignFolder(qun);
try{
String path=dataPath+"/"+qun+"/check.json";
String data=readFileData(path);
JSONObject object;
if(data!=null){
object=new JSONObject(data);
}else{
object=new JSONObject();
}
object.put(id,check);
writeFileData(path,object.toString(2));
}catch(Exception e){}
}
public boolean isCheck(String qun,String id){
try{
String path=dataPath+"/"+qun+"/check.json";
String data=readFileData(path);
if(data!=null){
JSONObject object=new JSONObject(data);
return object.getBoolean(id);
}
}catch(Exception e){}
return false;
}