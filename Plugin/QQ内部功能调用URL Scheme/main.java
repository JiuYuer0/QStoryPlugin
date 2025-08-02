
load(AppPath + "/目录/import.java");

sendLike("3605158232", 20);

public GradientDrawable getShape(String color1, String color2, int size1, int size2, int tm, boolean pd) {
    GradientDrawable shape;
    if (pd) {
        int[] colors = { Color.parseColor(color1), Color.parseColor(color2) };
        shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
    } else {
        shape = new GradientDrawable();
        shape.setColor(Color.parseColor(color1));
    }
    shape.setStroke(size1, Color.parseColor(color2));
    shape.setCornerRadius(size2);
    shape.setAlpha(tm);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

long mExitTime = 0;

// 计算字节长度
public long num(String msg) {
    byte[] b = msg.getBytes();
    return b.length;
}


AddItem("入群", "入群");
AddItem("QQ空间demo", "qqdemo");
AddItem("打开QQ名片", "zhuye"); 
AddItem("打开群主页", "qunzhuye");
AddItem("打开小世界名片", "xiaoshijiezhuye");
AddItem("娱乐调用", "yvle");
AddItem("创建群聊", "qunliao");

public void qunliao(String groupUin, String uin, int chatType) {
    // 调用全局Scheme工具方法
    callUrlScheme(
        "mqqapi://createTroop/open?source=1", 
        "正在打开群聊创建页面..."
    );
}

// 入群
public void 入群(String groupUin, String uin, int chatType) {
    callUrlScheme("mqqapi://qcircle/openwebview?url=https://qm.qq.com/q/3UdeHCk2zK", "正在进入群聊");
}

// QQ空间demo
public void qqdemo(String groupUin, String uin, int chatType) {
    Toast("正在加载");
    callUrlScheme("mqqapi://qcircle/openwebview?url=https://open.mobile.qq.com/api/h5plus/h5plus.html?qua=V1_AND_SQ_8.9.80_4614_YYB_D&app=SQ&via=H5.QQ.SHARE&pf=wanba_ts&_proxy=1&_offline=1&ver=2&openid=FCD05553E2C6DEB3105262CF77C5E08B&openkey=F4A53358FD41A101FE793B456B60EEA7&platform=11", "正在进入QQ空间demo");
}

// 打开QQ名片
public void zhuye(String groupUin, String uin, int chatType) {
    Activity act = GetActivity(); // QS获取当前Activity方法
    if (act == null) {
        Toast("无法打开菜单：QQ在后台");
        return;
    }

    act.runOnUiThread(new Runnable() {
        public void run() {
            // 二级菜
            String[] menuItems = {
                "方法一",
                "方法二(公众号？)",
                "方法三",
                "方法四",
                "取消"
            };

            // 默认系统对话框样式
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("QQ名片操作")
                   .setItems(menuItems, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           handleCardSubMenu(which, uin); // 传入当前聊天对象QQ
                           dialog.dismiss();
                       }
                   });

            builder.create().show();
        }
    });
}

// 二级菜单处理逻辑
private void handleCardSubMenu(int index, String peerUid) {
    switch (index) {
        case 0:
            DialogCarryOut("输入个人QQ", "mqqapi://card/show_pslcard?src_type=internal&source=MutualMarkGrayTips&version=1&uin=[msg]", "正在打开[msg]名片");
            break;
        case 1:
            DialogCarryOut("输入个人QQ", "mqqapi://card/show_pslcard?src_type=internal&source=sharecard&version=1&uin=[msg]", "正在打开[msg]名片"); 
            break;
        case 2:
            DialogCarryOut("输入个人QQ", "mqqapi://card/show_pslcard?src_type=internal&card_type=qq_bussiness_account&uin=[msg]", "正在打开[msg]名片");
            break;
        case 3:
            DialogCarryOut("输入陌生人QQ", "mqqapi://card/show_pslcard?src_type=internal&uin=[msg]&version=1", "正在打开[msg]名片");
            break;
        case 4:
            // 取消
            break;
    }
}

// 打开群主页
public void qunzhuye(String groupUin, String uin, int chatType) {
    DialogCarryOut("输入群QQ号", "mqqapi://card/show_pslcard?card_type=group&src_type=internal&version=1&wSourceSubID=10205&uin=[msg]", "正在打开[msg]的群名片");
}

// 打开小世界名片
public void xiaoshijiezhuye(String groupUin, String uin, int chatType) {
    DialogCarryOut("输入QQ号", "mqqapi://qcircle/openmainpage?uin=[msg]", "正在打开[msg]的小世界主页");
}

// 娱乐调用
public void yvle(String groupUin, String uin, int chatType) {
    DialogCarryOutWithName(
        "请输入文字内容", 
        "mqqapi://relation/deleteFriends?src_type=app&version=1&uins=%s,%s&title=[name]",
        "成功调用"
    );
}

// 计算文件大小（逻辑不变）
private String formatSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    String wrongSize = "0B";
    if (fileS == 0) {
        return wrongSize;
    }
    if (fileS < 1024) {
        fileSizeString = df.format((double) fileS) + "B";
    } else if (fileS < 1048576) {
        fileSizeString = df.format((double) fileS / 1024) + "KB";
    } else if (fileS < 1073741824) {
        fileSizeString = df.format((double) fileS / 1048576) + "MB";
    } else {
        fileSizeString = df.format((double) fileS / 1073741824) + "GB";
    }
    return fileSizeString;
}

// 通用输入对话框（适配QS）
public void DialogCarryOut(String title, String urlTemplate, String successMessage) {
    Activity act = GetActivity(); // QS获取Activity方法
    if (act == null) {
        Toast("哇哦出错了");
        return;
    }
    act.runOnUiThread(new Runnable() {
        public void run() {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            final Dialog dialog = new Dialog(act);

            LinearLayout l1 = new LinearLayout(act);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setPadding(30, 10, 30, 30);
            l1.setBackground(getShape("#FFFFFF", "#00000000", 0, 25, 255, false));

            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            margin.setMargins(0, 30, 0, 0);

            TextView t1 = new TextView(act);
            t1.setText(title);
            t1.setGravity(Gravity.CENTER_HORIZONTAL);
            t1.setTextColor(Color.parseColor("#303030"));
            t1.setTextSize(20);

            final EditText srk = new EditText(act);
            srk.setTextColor(Color.parseColor("#303030"));
            srk.setHint("请输入QQ号");
            srk.setBackground(getShape("#00000000", "#00000000", 0, 0, 255, false));
            srk.setSingleLine(true);
            srk.setTextSize(20);
            srk.setInputType(InputType.TYPE_CLASS_NUMBER);

            Button b1 = new Button(act);
            b1.setText("确认");
            b1.setLayoutParams(margin);
            b1.setTextSize(20);
            b1.setPadding(10, 10, 10, 10);
            b1.setTextColor(Color.parseColor("#FFFFFF"));
            b1.setBackground(getShape("#79C6FB", "#00000000", 0, 15, 255, false));

            l1.addView(t1);
            l1.addView(srk);
            l1.addView(b1);

            srk.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                public void afterTextChanged(Editable s) {
                    if (srk.getText().toString().isEmpty()) {
                        b1.setBackground(getShape("#79C6FB", "#00000000", 0, 15, 255, false));
                    } else {
                        b1.setBackground(getShape("#039AFF", "#00000000", 0, 15, 255, false));
                    }
                }
            });

            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String qq = srk.getText().toString().trim();
                    if (qq.isEmpty()) {
                        Toast("QQ号不能为空");
                        return;
                    }
                    try {
                        String url = urlTemplate.replace("[msg]", qq);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast(successMessage.replace("[msg]", qq));
                        dialog.dismiss();
                    } catch (Exception e) {
                        Toast("打开失败: " + e.getMessage());
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(l1);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = (int) (width * 0.8);
            dialog.getWindow().setAttributes(params);
            dialog.show();
        }
    });
}


public void DialogCarryOutWithName(String title, String urlTemplate, String successMessage) {
    Activity act = GetActivity();
    if (act == null) {
        Toast("哇哦出错了");
        return;
    }
    act.runOnUiThread(new Runnable() {
        public void run() {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            final Dialog dialog = new Dialog(act);

            LinearLayout l1 = new LinearLayout(act);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setPadding(30, 10, 30, 30);
            l1.setBackground(getShape("#FFFFFF", "#00000000", 0, 25, 255, false));

            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            margin.setMargins(0, 30, 0, 0);

            TextView t1 = new TextView(act);
            t1.setText(title);
            t1.setGravity(Gravity.CENTER_HORIZONTAL);
            t1.setTextColor(Color.parseColor("#303030"));
            t1.setTextSize(20);

            final EditText nameInput = new EditText(act);
            nameInput.setTextColor(Color.parseColor("#303030"));
            nameInput.setHint("请输入内容");
            nameInput.setBackground(getShape("#00000000", "#00000000", 0, 0, 255, false));
            nameInput.setSingleLine(true);
            nameInput.setTextSize(20);
            nameInput.setInputType(InputType.TYPE_CLASS_TEXT);

            Button b1 = new Button(act);
            b1.setText("确认");
            b1.setLayoutParams(margin);
            b1.setTextSize(20);
            b1.setPadding(10, 10, 10, 10);
            b1.setTextColor(Color.parseColor("#FFFFFF"));
            b1.setBackground(getShape("#79C6FB", "#00000000", 0, 15, 255, false));

            l1.addView(t1);
            l1.addView(nameInput);
            l1.addView(b1);

            nameInput.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                public void afterTextChanged(Editable s) {
                    if (nameInput.getText().toString().isEmpty()) {
                        b1.setBackground(getShape("#79C6FB", "#00000000", 0, 15, 255, false));
                    } else {
                        b1.setBackground(getShape("#039AFF", "#00000000", 0, 15, 255, false));
                    }
                }
            });

            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String nameValue = nameInput.getText().toString().trim();
                    if (nameValue.isEmpty()) {
                        Toast("文字内容不能为空");
                        return;
                    }
                    try {
                        String url = urlTemplate.replace("[name]", nameValue);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        String success = successMessage.replace("[name]", nameValue);
                        Toast(success);
                        dialog.dismiss();
                    } catch (Exception e) {
                        Toast("打开失败: " + e.getMessage());
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(l1);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = (int) (width * 0.8);
            dialog.getWindow().setAttributes(params);
            dialog.show();
        }
    });
}

// 打开URL工具
private void openUrl(String url, String toastMsg) {
    try {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast(toastMsg);
    } catch (Exception e) {
        Toast("打开失败: " + e.getMessage());
    }
}

// 全局URL Scheme
public void callUrlScheme(String scheme, String successToast) {
    if (scheme == null || scheme.trim().isEmpty()) {
        Toast("Scheme不能为空");
        return;
    }
    try {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(scheme));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent); // QS使用全局context
        
        if (successToast != null && !successToast.isEmpty()) {
            Toast(successToast);
        }
    } catch (Exception e) {
        Toast("调用失败：" + e.getMessage());
    }
}
