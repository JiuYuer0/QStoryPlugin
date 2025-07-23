public String getCurrentBeijingTime() {
// 获取当前本地时间
LocalDateTime currentLocalTime = LocalDateTime.now();        
// 将本地时间转换成北京时间
ZoneId localZone = ZoneId.systemDefault();
ZoneId beijingZone = ZoneId.of("Asia/Shanghai");
LocalDateTime currentBeijingTime = currentLocalTime.atZone(localZone).withZoneSameInstant(beijingZone).toLocalDateTime();
// 格式化当前北京时间
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
return currentBeijingTime.format(formatter);}
public  接着写(String Path, String WriteData) {
FileOutputStream fos = null;
try {
File file = new File(Path);
if (!file.exists()) {
file.createNewFile();}
fos = new FileOutputStream(file, true);
byte[] bytesArray = WriteData.getBytes();
fos.write(bytesArray);
fos.flush(); 
} catch (IOException ioe) {
ioe.printStackTrace();
} finally {
if (fos != null) {
try {
fos.close();
} catch (IOException e) {
e.printStackTrace();}}}}
public  String 读(String filePath) {
StringBuilder contentBuilder = new StringBuilder();
BufferedReader br = null;
try {
br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
String line;
while ((line = br.readLine()) != null) {
contentBuilder.append(line).append("\n");
}
} catch (IOException e) {
e.printStackTrace();
} finally {
if (br != null) {
try {
br.close();
} catch (IOException e) {
e.printStackTrace();
}
}
}
return contentBuilder.toString();
}