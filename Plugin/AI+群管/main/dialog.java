import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
public void showTip(){
Activity activity=GetActivity();
new Handler(Looper.getMainLooper()).post(new Runnable() {
public void run() {
Dialog showTipDialog=new Dialog(activity);
WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
int width = wm.getDefaultDisplay().getWidth();
int height = wm.getDefaultDisplay().getHeight();
LinearLayout layout=new LinearLayout(activity);
ImageView image=new ImageView(activity);
Bitmap bitmap=CompressPic(AppPath+"/Pic/main.jpg",540,548);
image.setImageBitmap(bitmap);
layout.setOrientation(LinearLayout.VERTICAL);
layout.setPadding(10,10,10,10);
//layout.setBackground(getShape(LAYOUT_COLOR,10));
layout.addView(image);
showTipDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
showTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
showTipDialog.setContentView(layout);
showTipDialog.show();
Window window = showTipDialog.getWindow();
if (window != null) {
WindowManager.LayoutParams layoutParams = window.getAttributes();
layoutParams.width = (int)width/2+width/6;
window.setAttributes(layoutParams);
}
}});
}
public Bitmap CompressPic(String path,int width,int height){
try{
return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path),width,height,true);
}catch(Exception e){
Toast(""+e);
}
}
showTip();