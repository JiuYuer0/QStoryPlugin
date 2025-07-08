public String readFileData(String filePath) {
StringBuilder content = new StringBuilder();
try {
FileReader fileReader = new FileReader(filePath);
BufferedReader bufferedReader = new BufferedReader(fileReader);
String line;
while ((line = bufferedReader.readLine()) != null) {
content.append(line).append("\n"); 
}
bufferedReader.close();
} catch (IOException e) {
e.printStackTrace();
return null;
}
return content.toString();
}

public void writeFileData(String Path,String WriteData){
HashMap FileMemCache = new HashMap();
try{
FileMemCache.put(Path,WriteData);
File file = new File(Path);
FileOutputStream fos = new FileOutputStream(file);
if(!file.exists()){
file.createNewFile();}
byte[] bytesArray = WriteData.getBytes();fos.write(bytesArray);fos.flush();
}catch(IOException ioe){
}
}