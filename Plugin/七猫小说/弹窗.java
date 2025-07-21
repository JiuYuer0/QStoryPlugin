import android.app.Activity;
import android.content.ClipboardManager;
import android.app.Activity;
import android.content.Context;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
public void SET(String title, String message) {
Activity ThisActivity = GetActivity();
if (ThisActivity == null || ThisActivity.isFinishing()) {
Toast("Activity is null");return;}
ThisActivity.runOnUiThread(new Runnable() {
public void run() {
TextView titleTextView = new TextView(ThisActivity);
titleTextView.setText(title);
titleTextView.setTextSize(28);
titleTextView.setGravity(Gravity.CENTER);
titleTextView.setPadding(20, 40, 20, 20);
Shader titleShader = new LinearGradient(
0, 0, 0, titleTextView.getTextSize(),new int[]{
Color.parseColor("#8A2BE2"), 
Color.parseColor("#4B0082"), 
Color.parseColor("#0000FF"), 
Color.parseColor("#00FF00"), 
Color.parseColor("#FFFF00"), 
Color.parseColor("#FFA500"), 
Color.parseColor("#FF4500"), 
Color.parseColor("#FF0000"), 
Color.parseColor("#FF69B4"), 
Color.parseColor("#FF1493"), 
Color.parseColor("#C71585")
}, null, Shader.TileMode.CLAMP);
titleTextView.getPaint().setShader(titleShader);
TextView messageTextView = new TextView(ThisActivity);
messageTextView.setText(message);
messageTextView.setTextSize(17);
messageTextView.setTextIsSelectable(true);
Shader messageShader = new LinearGradient(
0, 0, 0, messageTextView.getTextSize() * 12,
new int[]{
Color.parseColor("#8A2BE2"), 
Color.parseColor("#4B0082"), 
Color.parseColor("#0000FF"), 
Color.parseColor("#00FF00"), 
Color.parseColor("#FFFF00"), 
Color.parseColor("#FFA500"), 
Color.parseColor("#FF4500"), 
Color.parseColor("#FF0000"), 
Color.parseColor("#FF69B4"), 
Color.parseColor("#FF1493"), 
Color.parseColor("#C71585")
}, null, Shader.TileMode.CLAMP);
messageTextView.getPaint().setShader(messageShader);
LinearLayout layout = new LinearLayout(ThisActivity);
layout.setPadding(20, 20, 20, 20);
layout.setOrientation(LinearLayout.VERTICAL);
layout.addView(messageTextView);
AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
builder.setCustomTitle(titleTextView);
builder.setView(layout);
builder.setPositiveButton("查看作者的群", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int which) {
jump("mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=770866862&subsource_id=10019");
}});
builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int which) {
jump("mqqapi://app/joinImmediately?source_id=3&version=1.0&src_type=app&pkg=com.tencent.mobileqq&cmp=com.tencent.biz.JoinGroupTransitActivity&group_code=770866862&subsource_id=10019");
}});
AlertDialog dialog = builder.create();
dialog.setOnShowListener(new DialogInterface.OnShowListener() {
public void onShow(DialogInterface dialogInterface) {
FrameLayout frameLayout = dialog.findViewById(android.R.id.content);
if (frameLayout != null) {
frameLayout.setBackgroundColor(Color.parseColor("#D0E0FF"));}
Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
if (positiveButton != null) {
positiveButton.setBackgroundColor(Color.parseColor("#D0E0FF"));
positiveButton.setTextColor(Color.BLACK);}
if (negativeButton != null) {
negativeButton.setBackgroundColor(Color.parseColor("#D0E0FF"));
negativeButton.setTextColor(Color.BLACK);}
}});
dialog.show();
}});
}

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