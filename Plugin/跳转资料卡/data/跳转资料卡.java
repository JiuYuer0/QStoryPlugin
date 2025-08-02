import android.app.*;
import android.os.*;
import android.view.*;
import java.lang.*;
import android.content.*;
import android.widget.*;
import android.media.*;
import java.text.*;
import android.net.*;
import java.util.*;
import android.graphics.*;
import android.widget.*;
import android.content.*;
import android.text.*;
Activity ThisActivity = null;
AddItem("跳转资料卡","Join");
public static void joinqun(String qun) {//无心
Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mqq://card/show_pslcard?src_type=internal&version=1&uin=" +qun+ "&card_type=group&source=qrcode"));
intent.addFlags(0x10000000);
context.startActivity(intent);
}
public static void joinqq(String qun) {//无心
Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mqq://card/show_pslcard?uin="+qun+"&card_type=profile&version=1"));
intent.addFlags(0x10000000);
context.startActivity(intent);
}
public void showDialog(String group){
initActivity();
ThisActivity.runOnUiThread(new Runnable() {
public void run() {
AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
builder.setTitle("跳转资料卡");
LinearLayout layout = new LinearLayout(ThisActivity);
layout.setOrientation(LinearLayout.VERTICAL);
TextView textView = new TextView(ThisActivity);
textView.setText(Html.fromHtml("<font color=\"#AED581\">请输入QQ/群号</font>"));
layout.addView(textView);
final EditText editText = new EditText(ThisActivity);
editText.setHint("留空为跳转作者群/QQ资料卡");
layout.addView(editText);
builder.setView(layout);
builder.setPositiveButton("跳转QQ资料卡", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialogInterface, int i) {
if (editText.getText().toString().equals("")) {
joinqq("3468281461");
return;
}
joinqq(editText.getText().toString());
}
}).setNegativeButton("跳转群资料卡", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialogInterface, int i) {
if (editText.getText().toString().equals("")) {
joinqun("763893757");
return;
}
joinqun(editText.getText().toString());
}
});
AlertDialog dialog = builder.create();
dialog.setCanceledOnTouchOutside(true);
dialog.setCancelable(true);
dialog.show();
}
});
}