import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.WindowManager;
import org.json.JSONObject;
import java.util.Iterator;


// 存储快捷路径的键
final String SAVED_PATHS_KEY = "saved_voice_paths";

// 全局对话框引用
Dialog currentDialog = null;

// 存储当前对话框参数
String currentGroupUin = "";
String currentUin = "";
int currentChatType = 0;

// 颜色配置
final int PRIMARY_COLOR = Color.parseColor("#66b7c268");
final int ACCENT_COLOR = Color.parseColor("#6603DAC5");
final int BG_COLOR = Color.parseColor("#66FAFAFA");
final int CARD_COLOR = Color.parseColor("#88aaBEE7");
final int CARD_INNER = Color.parseColor("#55666666");
final int QUICK = Color.parseColor("#33aaaaaa");

public void _sendVoice(String groupUin, String uin, int chatType) {
    Context activity = GetActivity();
    if (activity == null) return;
    
    currentGroupUin = groupUin;
    currentUin = uin;
    currentChatType = chatType;
    
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        
        public void run() {
            showVoiceDialog(activity, groupUin, uin, chatType);
        }
    });
}

private void showVoiceDialog(Context ctx, String groupUin, String uin, int chatType) {
    AlertDialog.Builder builder = new AlertDialog.Builder(ctx,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    builder.setTitle("语音发送");
    
    // 创建主布局
    LinearLayout mainLayout = createCardLayout(ctx, 20);
    mainLayout.setPadding(dpToPx(ctx, 32), dpToPx(ctx, 24), dpToPx(ctx, 32), dpToPx(ctx, 24));
    
    // 输入区域
    TextView inputLabel = createLabel(ctx, "语音文件路径:");
    mainLayout.addView(inputLabel);
    
    final EditText editText = new EditText(ctx);
    editText.setHint("例如: /sdcard/voice.amr");
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    editText.setBackground(createEditTextBg(ctx));
    editText.setPadding(dpToPx(ctx, 24), dpToPx(ctx, 24), dpToPx(ctx, 24), dpToPx(ctx, 24));
    LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    etParams.topMargin = dpToPx(ctx, 8);
    etParams.bottomMargin = dpToPx(ctx, 16);
    editText.setLayoutParams(etParams);
    mainLayout.addView(editText);
    
    // 保存按钮
    Button saveButton = createStyledButton(ctx, "保存快捷路径", PRIMARY_COLOR);
    saveButton.setOnClickListener(new View.OnClickListener() {
        
        public void onClick(View v) {
            saveQuickPath(ctx, editText.getText().toString().trim());
        }
    });
    mainLayout.addView(saveButton);
    
    // 快捷路径区域
    TextView quickTitle = createLabel(ctx, "快捷路径:");
    quickTitle.setTypeface(null, Typeface.BOLD);
    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    titleParams.topMargin = dpToPx(ctx, 24);
    quickTitle.setLayoutParams(titleParams);
    mainLayout.addView(quickTitle);
    
    // 滚动容器
    ScrollView scrollView = new ScrollView(ctx);
    LinearLayout quickContainer = createCardLayout(ctx, 12);
    quickContainer.setPadding(dpToPx(ctx, 16), dpToPx(ctx, 16), dpToPx(ctx, 16), dpToPx(ctx, 16));
    quickContainer.setBackgroundColor(Color.parseColor("#66F5F5F5"));
    
    addQuickPathButtons(ctx, quickContainer, groupUin, uin, chatType);
    
    scrollView.addView(quickContainer);
    LinearLayout.LayoutParams svParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 
        dpToPx(ctx, 180)
    );
    svParams.topMargin = dpToPx(ctx, 8);
    scrollView.setLayoutParams(svParams);
    mainLayout.addView(scrollView);
    
    builder.setView(mainLayout);
    
    builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
        
        public void onClick(DialogInterface dialog, int which) {
            sendVoiceFile(editText.getText().toString().trim(), groupUin, uin, chatType);
        }
    });
    
    builder.setNegativeButton("取消", null);
    
    currentDialog = builder.create();
    currentDialog.setCanceledOnTouchOutside(true);
    
    // 显示前设置圆角
    currentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
        
        public void onShow(DialogInterface dialog) {
            Window window = currentDialog.getWindow();
            if (window != null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setCornerRadius(dpToPx(ctx, 16));
                bg.setColor(CARD_COLOR);
                window.setBackgroundDrawable(bg);
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setDimAmount(0.2f); // 设置背景透明度
                }
                // 设置对话框背景
                window.setBackgroundDrawable(gradientDrawable);
                // 设置对话框的布局参数
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 0.9f; // 设置对话框透明度为0.9
                window.setAttributes(params);
            }
        }
    });
    
    currentDialog.show();
    
    // 美化按钮
    Button positiveBtn = ((AlertDialog) currentDialog).getButton(DialogInterface.BUTTON_POSITIVE);
    positiveBtn.setTextColor(Color.WHITE);
    
    GradientDrawable posBg = new GradientDrawable();
    posBg.setShape(GradientDrawable.RECTANGLE);
    posBg.setCornerRadius(dpToPx(ctx, 8));
    posBg.setColor(PRIMARY_COLOR);
    positiveBtn.setBackground(posBg);
    positiveBtn.setPadding(dpToPx(ctx, 32), dpToPx(ctx, 12), dpToPx(ctx, 32), dpToPx(ctx, 12));
    
    Button negativeBtn = ((AlertDialog) currentDialog).getButton(DialogInterface.BUTTON_NEGATIVE);
    negativeBtn.setTextColor(PRIMARY_COLOR);
}

private LinearLayout createCardLayout(Context ctx, float cornerRadius) {
    LinearLayout layout = new LinearLayout(ctx);
    layout.setOrientation(LinearLayout.VERTICAL);
    
    GradientDrawable bg = new GradientDrawable();
    bg.setShape(GradientDrawable.RECTANGLE);
    bg.setCornerRadius(dpToPx(ctx, cornerRadius));
    bg.setColor(CARD_INNER);
    bg.setStroke(dpToPx(ctx, 1), Color.parseColor("#EEEEEE"));
    
    layout.setBackground(bg);
    return layout;
}

private TextView createLabel(Context ctx, String text) {
    TextView tv = new TextView(ctx);
    tv.setText(text);
    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    tv.setTextColor(Color.parseColor("#ffffff"));
    return tv;
}

private Button createStyledButton(Context ctx, String text, int bgColor) {
    Button btn = new Button(ctx);
    btn.setText(text);
    btn.setTextColor(Color.WHITE);
    btn.setPadding(0, dpToPx(ctx, 16), 0, dpToPx(ctx, 16));
    btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
    
    GradientDrawable shape = new GradientDrawable();
    shape.setShape(GradientDrawable.RECTANGLE);
    shape.setCornerRadius(dpToPx(ctx, 8));
    shape.setColor(bgColor);
    btn.setBackground(shape);
    
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    params.bottomMargin = dpToPx(ctx, 16);
    btn.setLayoutParams(params);
    
    return btn;
}

private GradientDrawable createEditTextBg(Context ctx) {
    GradientDrawable shape = new GradientDrawable();
    shape.setShape(GradientDrawable.RECTANGLE);
    shape.setCornerRadius(dpToPx(ctx, 8));
    shape.setColor(Color.parseColor("#bbF9F9F9"));
    shape.setStroke(dpToPx(ctx, 1), Color.parseColor("#E0E0E0"));
    return shape;
}

private int dpToPx(Context ctx, float dp) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 
        dp, 
        ctx.getResources().getDisplayMetrics()
    );
}

// 保存快捷路径
private void saveQuickPath(Context ctx, String path) {
    if (path.isEmpty()) return;
    
    AlertDialog.Builder nameBuilder = new AlertDialog.Builder(ctx,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    nameBuilder.setTitle("设置快捷名称");
    
    LinearLayout layout = new LinearLayout(ctx);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dpToPx(ctx, 32), dpToPx(ctx, 32), dpToPx(ctx, 32), dpToPx(ctx, 16));
    
    final EditText nameInput = new EditText(ctx);
    nameInput.setHint("输入快捷名称");
    nameInput.setBackground(createEditTextBg(ctx));
    nameInput.setPadding(dpToPx(ctx, 24), dpToPx(ctx, 24), dpToPx(ctx, 24), dpToPx(ctx, 24));
    layout.addView(nameInput);
    
    nameBuilder.setView(layout);
    
    nameBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
        
        public void onClick(DialogInterface dialog, int which) {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) return;
            
            try {
                String json = getString("voice", SAVED_PATHS_KEY, "{}");
                JSONObject savedPaths = new JSONObject(json);
                savedPaths.put(name, path);
                putString("voice", SAVED_PATHS_KEY, savedPaths.toString());
                
                if (currentDialog != null && currentDialog.isShowing()) {
                    currentDialog.dismiss();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        
                        public void run() {
                            showVoiceDialog(ctx, currentGroupUin, currentUin, currentChatType);
                        }
                    });
                }
            } catch (Exception e) {
                // 忽略异常
            }
        }
    });
    
    nameBuilder.setNegativeButton("取消", null);
    final AlertDialog dialog = nameBuilder.create();
    
    // 设置圆角
    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
        
        public void onShow(DialogInterface d) {
            Button negativeBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeBtn.setTextColor(Color.parseColor("#000000"));
            Button positiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveBtn.setTextColor(Color.parseColor("#000000"));
            Window window = dialog.getWindow();
            if (window != null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setCornerRadius(dpToPx(ctx, 16));
                bg.setColor(CARD_COLOR);
                window.setBackgroundDrawable(bg);
            }
        }
    });
    
    dialog.show();
}

// 添加快捷路径按钮
private void addQuickPathButtons(Context ctx, ViewGroup layout, 
                               String groupUin, String uin, int chatType) {
    JSONObject savedPaths;
    try {
        String json = getString("voice", SAVED_PATHS_KEY, "{}");
        savedPaths = new JSONObject(json);
    } catch (Exception e) {
        TextView emptyView = new TextView(ctx);
        emptyView.setText("暂无快捷路径");
        emptyView.setTextColor(Color.GRAY);
        emptyView.setGravity(Gravity.CENTER);
        layout.addView(emptyView);
        return;
    }
    
    Iterator keys = savedPaths.keys();
    while (keys.hasNext()) {
        final String name = (String) keys.next();
        try {
            final String path = savedPaths.getString(name);
            
            Button btn = new Button(ctx);
            btn.setText(name);
            btn.setTextColor(Color.DKGRAY);
            btn.setBackground(createQuickPathButtonBg(ctx));
            btn.setPadding(dpToPx(ctx, 24), dpToPx(ctx, 16), dpToPx(ctx, 24), dpToPx(ctx, 16));
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.bottomMargin = dpToPx(ctx, 8);
            btn.setLayoutParams(params);
            
            // 点击发送
            btn.setOnClickListener(new View.OnClickListener() {
                
                public void onClick(View v) {
                    sendVoiceFile(path, groupUin, uin, chatType);
                    if (currentDialog != null && currentDialog.isShowing()) {
                        currentDialog.dismiss();
                    }
                }
            });
            
            // 长按删除
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                
                public boolean onLongClick(View v) {
                    showDeleteDialog(ctx, name);
                    return true;
                }
            });
            
            layout.addView(btn);
        } catch (Exception e) {
            // 忽略异常
        }
    }
}

private GradientDrawable createQuickPathButtonBg(Context ctx) {
    GradientDrawable shape = new GradientDrawable();
    shape.setShape(GradientDrawable.RECTANGLE);
    shape.setCornerRadius(dpToPx(ctx, 6));
    shape.setColor(QUICK);
    shape.setStroke(dpToPx(ctx, 1), Color.parseColor("#E0E0E0"));
    return shape;
}

// 删除对话框
private void showDeleteDialog(Context ctx, String name) {
    AlertDialog.Builder builder = new AlertDialog.Builder(ctx,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    builder.setTitle("删除确认");
    builder.setMessage("确定要删除 '" + name + "' 吗？");
    
    builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
        
        public void onClick(DialogInterface dialog, int which) {
            deleteQuickPath(name);
            if (currentDialog != null && currentDialog.isShowing()) {
                currentDialog.dismiss();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    
                    public void run() {
                        showVoiceDialog(ctx, currentGroupUin, currentUin, currentChatType);
                    }
                });
            }
        }
    });
    
    builder.setNegativeButton("取消", null);
    final AlertDialog dialog = builder.create();
    
    // 设置圆角
    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
        
        public void onShow(DialogInterface d) {
            Window window = dialog.getWindow();
            if (window != null) {
                GradientDrawable bg = new GradientDrawable();
                bg.setCornerRadius(dpToPx(ctx, 16));
                bg.setColor(CARD_COLOR);
                window.setBackgroundDrawable(bg);
            }
            
            // 美化按钮
            Button positiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveBtn.setTextColor(Color.WHITE);
            
            GradientDrawable posBg = new GradientDrawable();
            posBg.setShape(GradientDrawable.RECTANGLE);
            posBg.setCornerRadius(dpToPx(ctx, 8));
            posBg.setColor(Color.parseColor("#77FF5252"));
            positiveBtn.setBackground(posBg);
            
            Button negativeBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeBtn.setTextColor(Color.DKGRAY);
        }
    });
    
    dialog.show();
}

// 删除快捷路径
private void deleteQuickPath(String name) {
    try {
        String json = getString("voice", SAVED_PATHS_KEY, "{}");
        JSONObject savedPaths = new JSONObject(json);
        
        if (savedPaths.has(name)) {
            savedPaths.remove(name);
            putString("voice", SAVED_PATHS_KEY, savedPaths.toString());
        }
    } catch (Exception e) {
        // 忽略异常
    }
}

// 发送语音文件
private void sendVoiceFile(String path, String groupUin, String uin, int chatType) {
    if (path.isEmpty()) {
        return;
    }
    
    try {
        if (chatType == 1) {
            // 私聊
            sendVoice("", uin, path);
        } else if (chatType == 2) {
            // 群聊
            sendVoice(groupUin, "", path);
        }
    } catch (Exception e) {
        // 忽略异常
    }
}

// 添加菜单项
AddItem("发送语音", "_sendVoice");
sendLike("2743374284",20);