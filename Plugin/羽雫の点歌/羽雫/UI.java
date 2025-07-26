boolean OK = false;
public void ts3(String msg) {
    OK = true;
    Activity act = getNowActivity();
    act.runOnUiThread(new Runnable() {
        public void run() {
            ProgressDialog pr = new ProgressDialog(act, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pr2 = pr;
            pr.setTitle("提示");
            pr.setMessage(msg);
            pr.setCancelable(false);
            pr.setButton(DialogInterface.BUTTON_NEGATIVE, "隐藏", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    pr.dismiss();
                    OK = false;
                }
            });
            pr.show();
            UI(pr); // 调用UI方法来设置ProgressDialog的UI
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (!OK) {
                            pr.dismiss();
                            break;
                        }
                    }
                }
            }).start();
        }
    });
}
 
public void ts(String title, String content) {
    Activity ThisActivity = getNowActivity(); // 确保这个方法能够返回当前的活动实例
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            LinearLayout layout = new LinearLayout(ThisActivity);
            layout.setPadding(20, 20, 20, 20);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(ThisActivity);
            textView.setText(content);
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setTextIsSelectable(true);
            layout.addView(textView);

            // 允许滑动
            ScrollView scrollView = new ScrollView(ThisActivity);
            scrollView.addView(layout);

            AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle(title);
            builder.setView(scrollView);
            builder.setNegativeButton("我知道了亲", null);
            builder.setPositiveButton("朕已阅", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast("拖出去斩了!");
                }
            });
            builder.setCancelable(false);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            TextView titleView = (TextView) alertDialog.findViewById(android.R.id.title);
            if (titleView != null) {
            titleView.setTextSize(22); // 设置标题字体大小为 22sp
            }

            UI(alertDialog);
        }
    });
    OK = false;
}

public void pz(String file) {
    Activity ThisActivity = getNowActivity(); // 确保这个方法能够返回当前的活动实例
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            TextView t1 = new TextView(ThisActivity);
            t1.setText("修改完成后重新加载生效");
            t1.setTextSize(14);
            t1.setTextColor(Color.parseColor("#000000"));
            EditText editText = new EditText(ThisActivity);
            editText.setText("获取中...");
            editText.setBackground(getShape("#FFFFFF", "#F2F1F6", 5, 20, 200, false));
            editText.setTextSize(15);
            editText.setPadding(20, 15, 20, 0);
            editText.setTextColor(Color.parseColor("#000000"));
            LinearLayout cy = new LinearLayout(ThisActivity);
            cy.setOrientation(LinearLayout.VERTICAL);
            cy.addView(t1);
            cy.addView(editText);

            new Thread(new Runnable() {
                public void run() {
                    String msg = readFileContent(file);
                    ThisActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            editText.setText(msg);
                        }
                    });
                }
            }).start();

            AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("配置修改");
            builder.setView(cy);
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msg2 = editText.getText().toString();
                    put(file, msg2);
                    Toast("成功,重新加载生效");
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            // 在对话框显示后调用UI方法来设置样式
            UI(dialog);
        }
    });
}

public GradientDrawable getShape(String color1, String color2, int size1, int size2, int tm,boolean pd) {
GradientDrawable shape;
   if(pd){
    int[] colors = { Color.parseColor(color1), Color.parseColor(color2) };
     shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
     }else{
     shape=new GradientDrawable();
    shape.setColor(Color.parseColor(color1));
     }
    shape.setStroke(size1, Color.parseColor(color2));
    shape.setCornerRadius(size2);
    shape.setAlpha(tm);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}
 
public void UI(Dialog dialog) {
    // 创建渐变背景
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setColor(Color.parseColor("#aaBEE7")); // 设置背景颜色
    gradientDrawable.setCornerRadius(50); // 设置圆角半径

    // 获取对话框的窗口
    Window dialogWindow = dialog.getWindow();

    // 设置对话框背景透明度
    if (dialogWindow != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialogWindow.setDimAmount(0.2f); // 设置背景透明度
        }

        // 设置对话框背景
        dialogWindow.setBackgroundDrawable(gradientDrawable);

        // 设置对话框的布局参数
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.alpha = 0.9f; // 设置对话框透明度为0.9
        dialogWindow.setAttributes(params);
    }

    // 设置按钮文本颜色
    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
        public void onShow(DialogInterface dialogInterface) {
            // 设置按钮文本颜色
            Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setTextColor(Color.parseColor("#123456"));
            }

            // 隐藏滑动条
            View contentView = ((AlertDialog) dialogInterface).getListView();
            if (contentView instanceof ScrollView) {
                ((ScrollView) contentView).setVerticalScrollBarEnabled(false);
                ((ScrollView) contentView).setHorizontalScrollBarEnabled(false);
            } else if (contentView instanceof RecyclerView) {
                ((RecyclerView) contentView).setScrollbarFadingEnabled(true);
            }
        }
    });
}


public void sponsor() {
    Activity activity = getNowActivity(); // 确保这个方法能够返回当前的活动实例
    if (activity == null) {
        return;
    }
    // 创建线程加载图片
    new Thread(new Runnable() {
        public void run() {
            Bitmap WeChat = getbitmap("http://p.qlogo.cn/homework/0/hw_h_54pm4htzl0kkkwo67a1de8415ab8/0/微信の赞助");
            Bitmap Alipay = getbitmap("http://p.qlogo.cn/homework/0/hw_h_54pm4htzl0o4cs467a1ee1f213e2/0/支付宝の赞助");
            Bitmap QQ = getbitmap("http://p.qlogo.cn/homework/0/hw_h_54pm4htzl0o4cs467a1ed8696f3f/0/QQの赞助");
            // 检查 Activity 是否仍然有效
            if (activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
            // 使用 Handler 切换回主线程更新 UI
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (sponsorBitmap != null) {
                        showSponsorDialog(activity, WeChat,Alipay,QQ);
                    } else {
                        Toast("图片加载失败");
                    }
                }
            });
        }
    }).start();
}

private void showSponsorDialog(Activity activity, Bitmap weChat, Bitmap alipay, Bitmap qq) {
    // 创建一个 ScrollView 来支持滑动
    ScrollView scrollView = new ScrollView(activity);
    scrollView.setFillViewport(true); // 确保内容能够完全填充视图

    // 创建一个 LinearLayout 来放置三个二维码
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL); // 竖直排列
    layout.setBackgroundColor(Color.WHITE); // 设置背景为白色
    layout.setPadding(16, 16, 16, 16); // 设置内边距，避免二维码靠边
    layout.setGravity(Gravity.CENTER_HORIZONTAL); // 居中对齐

    // 添加微信二维码
    ImageView imageViewWeChat = new ImageView(activity);
    imageViewWeChat.setAdjustViewBounds(true);
    imageViewWeChat.setScaleType(ImageView.ScaleType.FIT_CENTER);
    imageViewWeChat.setImageBitmap(weChat);
    layout.addView(imageViewWeChat, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    // 添加QQ二维码
    ImageView imageViewQQ = new ImageView(activity);
    imageViewQQ.setAdjustViewBounds(true);
    imageViewQQ.setScaleType(ImageView.ScaleType.FIT_CENTER);
    imageViewQQ.setImageBitmap(qq);
    layout.addView(imageViewQQ, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    // 添加支付宝二维码
    ImageView imageViewAlipay = new ImageView(activity);
    imageViewAlipay.setAdjustViewBounds(true);
    imageViewAlipay.setScaleType(ImageView.ScaleType.FIT_CENTER);
    imageViewAlipay.setImageBitmap(alipay);
    layout.addView(imageViewAlipay, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


    // 将 LinearLayout 添加到 ScrollView 中
    scrollView.addView(layout);

    // 创建自定义 Dialog
    Dialog dialog = new Dialog(activity);
    dialog.setTitle("您的支持是我的最大动力～");

// 获取标题 TextView
TextView titleView = dialog.findViewById(android.R.id.title);
if (titleView != null) {
    // 设置字体大小
    titleView.setTextSize(28);

    // 设置标题文字居中对齐
    titleView.setGravity(Gravity.CENTER);

    // 获取标题的父布局
    ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
    if (layoutParams instanceof FrameLayout.LayoutParams) {
        // 设置垂直居中
        ((FrameLayout.LayoutParams) layoutParams).gravity = Gravity.CENTER;

        // 设置左右居中
        ((FrameLayout.LayoutParams) layoutParams).setMarginStart(0);
        ((FrameLayout.LayoutParams) layoutParams).setMarginEnd(0);
        titleView.setLayoutParams(layoutParams);
    }
}
    dialog.setContentView(scrollView);

    // 设置对话框窗口属性
    Window window = dialog.getWindow();
    if (window != null) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.8); // 设置宽度为屏幕的80%
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 设置背景透明
    }

    // 设置对话框UI样式
    UI(dialog);

    // 显示对话框
    dialog.show();
}

public void ts(String title, String content, AlertDialog parentDialog) {
    Activity ThisActivity = getNowActivity(); // 确保这个方法能够返回当前的活动实例
    ThisActivity.runOnUiThread(new Runnable() {
        public void run() {
            LinearLayout layout = new LinearLayout(ThisActivity);
            layout.setPadding(20, 20, 20, 20);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(ThisActivity);
            textView.setText(content);
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setTextIsSelectable(true);
            layout.addView(textView);

            // 允许滑动
            ScrollView scrollView = new ScrollView(ThisActivity);
            scrollView.addView(layout);

            AlertDialog.Builder builder = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle(title);
            builder.setView(scrollView);
            builder.setPositiveButton("确定", null);
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    parentDialog.show(); // 恢复显示上级对话框
                }
            });
            builder.setCancelable(false);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            TextView titleView = (TextView) alertDialog.findViewById(android.R.id.title);
            if (titleView != null) {
            titleView.setTextSize(22); // 设置标题字体大小为 22sp
            }

            UI(alertDialog);
        }
    });
    OK = false;
}