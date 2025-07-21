public void YMZ(String str,String str2,Object msg,int type){
if(type==2)sendReply(str,msg,str2);else if(type==1)sendMsg("",str,str2);}
public void 写(String Path, String WriteData) {
FileOutputStream fos = null;
try {
File file = new File(Path);
if (!file.getParentFile().exists()) {
file.getParentFile().mkdirs();
}
if (!file.exists()) {
file.createNewFile();
}
fos = new FileOutputStream(file);
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
public  接着写(String Path, String WriteData) {
FileOutputStream fos = null;
try {
File file = new File(Path);
if (!file.getParentFile().exists()) {
file.getParentFile().mkdirs();}
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
import com.tencent.util.QQToastUtil;
public QQToastUtil QToastUtil=new QQToastUtil();
// 类型(int) 0：warning 1：error 2：success
public void QQToast(String text,int i)
{
QToastUtil.showQQToastInUiThread(i,text);
}   
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
Toast(e);
} finally {
if (br != null) {
try {
br.close();
} catch (IOException e) {
Toast(e);
}
}
}
return contentBuilder.toString();
}
public  String 缩进(String input) {
StringBuilder indentedString = new StringBuilder();
String[] lines = input.split("\n");
for (String line : lines) {
indentedString.append("    ").append(line).append("\n");
}
return indentedString.toString();
}
import com.tencent.mobileqq.friend.api.IFriendDataService;
public boolean isFriend(String uin){
Object app = BaseApplicationImpl.getApplication().getRuntime();
IFriendDataService Info = app.getRuntimeService(IFriendDataService.class);
boolean m=Info.isFriend(uin);
return m;
}
import java.lang.*;
import com.tencent.mobileqq.app.CardHandler;
import com.tencent.common.app.BaseApplicationImpl;
import java.lang.reflect.*;
import java.lang.reflect.Method;
public void nb(String targetUin,int num){
Object app=BaseApplicationImpl.getApplication().getRuntime();
int type = 10;
if(isFriend(targetUin))type=1;
CardHandler handler= new CardHandler(app);
byte[] bArr = new byte[10];
            bArr[0] = (byte) 12;
            bArr[1] = (byte) 24;
            bArr[2] = (byte) 0;
            bArr[3] = (byte) 1;
            bArr[4] = (byte) 6;
            bArr[5] = (byte) 1;
            bArr[6] = (byte) 49;
            bArr[7] = (byte) 22;
            bArr[8] = (byte) 1;
            bArr[9] = (byte) 49;
Class clazz = CardHandler.class;
Class[] ParamTYPE = new Class[]{Long.TYPE,Long.TYPE,byte[].class,Integer.TYPE,Integer.TYPE,Integer.TYPE};
Method[] methods = clazz.getMethods();
for (Method m : methods) {
    if (m.getParameterCount() == ParamTYPE.length ) {
        Class[] params = m.getParameterTypes();
        boolean match = true;
        for (int i = 0; i < ParamTYPE.length; i++) {
            if (!params[i].equals(ParamTYPE[i])) {
                match = false;
                break;
            }
        }
        if (match) {
            // 调用匹配的方法
            m.setAccessible(true);
            m.invoke(handler,new Object[]{Long.parseLong(MyUin),Long.parseLong(targetUin),bArr,type,num,0});
            break; // 找到匹配的方法后，跳出循环
        }
    }
}
}

