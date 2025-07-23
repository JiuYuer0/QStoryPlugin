import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
public void setTips(String title, String message) {
Activity thisActivity = GetActivity();
thisActivity.runOnUiThread(new Runnable() {
public void run() {
TextView textView = new TextView(thisActivity);
textView.setText(message);
textView.setTextSize(17);
textView.setTextColor(Color.BLACK);
textView.setTextIsSelectable(true);

LinearLayout layout = new LinearLayout(thisActivity);
layout.setPadding(20, 20, 20, 20);
layout.setOrientation(LinearLayout.VERTICAL);   
layout.addView(textView);

ScrollView scrollView = new ScrollView(thisActivity);
scrollView.addView(layout);

AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
builder.setTitle(title)
.setView(scrollView) 
.setNegativeButton("关闭", null)
.show();

}
});
}

//检测是否为好友
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

import java.text.SimpleDateFormat;
import java.util.Date;
public String getTodayDate() {
Date date=new Date();
SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
return dateFormat.format(date);}
//制作不易，点个小小的赞可以吗🥺(*≧∪≦)
String like1 = "2901256435";//本人
String like3 = "2978118490";//嘿壳
String like4 = "3465221490";//朋友
if (getBoolean("like_"+getTodayDate(),like1,false)) return;
putBoolean("like_"+getTodayDate(),like1,true);
nb(like1,20);
if (getBoolean("like_"+getTodayDate(),like3,false)) return;
putBoolean("like_"+getTodayDate(),like3,true);
nb(like3,20);
if (getBoolean("like_"+getTodayDate(),like4,false)) return;
putBoolean("like_"+getTodayDate(),like4,true);
nb(like4,20);
