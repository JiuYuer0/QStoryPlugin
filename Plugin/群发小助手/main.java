import android.app.Activity;
import android.app.AlertDialog;
import android.os.Looper;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;
import java.util.HashSet;


// 全局常量：存储槽数量（预设10个槽位，可自定义）
final int SAVE_SLOTS = 10; 
// 存储槽键前缀
final String SLOT_PREFIX = "slot_";

// 对时间戳进行换算
public static String formatTime(Long ms) {
    Integer ss = 1000;
    Integer mi = ss * 60;
    Integer hh = mi * 60;
    Integer dd = hh * 24;

    Long day = ms / dd;
    Long hour = (ms - day * dd) / hh;
    Long minute = (ms - day * dd - hour * hh) / mi;
    Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
    Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
    
    StringBuffer sb = new StringBuffer();
    if(day > 0) {
        sb.append(day+"天");
    }
    if(hour > 0) {
        sb.append(hour+"小时");
    }
    if(minute > 0) {
        sb.append(minute+"分");
    }
    if(second > 0) {
        sb.append(second+"秒");
    }
    if(milliSecond > 0) {
        sb.append(milliSecond+"毫秒");
    }
    return sb.toString();
}    

//获取当前日期，并按指定格式返回
public String getTodayDate(int num)
{
     Date date = new Date();
     SimpleDateFormat dateFormat;

     switch (num) {
         case 1:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd");
             break;
         case 2:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
             break;
         case 3:
             dateFormat = new SimpleDateFormat("MM/dd/YYYY");
             break;
         case 4:
             dateFormat = new SimpleDateFormat("HH:mm:ss");
             break;
         default:
             dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        }

    return dateFormat.format(date);
}


long lastToastTime = 0; // 弹Toast最后时间戳
long TOAST_INTERVAL = 3000; // 3秒内只弹一次Toast（1000毫秒=1秒）
private void showToast(String message) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastToastTime >= TOAST_INTERVAL) {
        Toast.makeText(GetActivity(), message, Toast.LENGTH_SHORT).show();
        lastToastTime = currentTime;
    }
}

// 禁止选中的群号列表（这里添加的群号不会出现在“群聊选择对话框”内哦～）
HashSet forbiddenGroups = new HashSet();
{
    forbiddenGroups.add("948759593");
    forbiddenGroups.add("954720846");
    forbiddenGroups.add("255413638");
}
// 以上群号是QS官方群

// 全局变量存储选中的群号、内容和间隔时间
String selectedGroups = "";
String messageContent = "";
int sendDelay = 1500; // 默认间隔时间

// 加载时提前结束之前的群发状态
boolean state2 = getBoolean("群发助手", "群发状态", false);
if(state2==true){
  putBoolean("群发助手","群发状态",false);
}


// 初始化群发对话框
void initMassSenderDialog() {
    // 主线程判断
    if (Looper.myLooper() == Looper.getMainLooper()) {
        showMassSenderDialog();
    } else {
        // 兼容低版本
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                showMassSenderDialog();
            }
        });
    }
}

// 显示对话框（群发助手）
private void showMassSenderDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(GetActivity());
    builder.setTitle("群发助手");

    // 根布局
    LinearLayout rootLayout = new LinearLayout(GetActivity());
    rootLayout.setOrientation(LinearLayout.VERTICAL);
    rootLayout.setPadding(50, 30, 50, 30);

    // 群号输入框上方的提示文本
    TextView tipText = new TextView(GetActivity());
    tipText.setText("多条内容用#|分隔（如：你好#|你也好）");
    tipText.setTextColor(android.graphics.Color.parseColor("#ffffff"));
    tipText.setTextSize(14);
    tipText.setPadding(0, 0, 0, 15);
    tipText.setTextIsSelectable(true);
    rootLayout.addView(tipText);

    // 群选择布局
    LinearLayout groupSelectLayout = new LinearLayout(GetActivity());
    groupSelectLayout.setOrientation(LinearLayout.HORIZONTAL);
    
    // 群号输入框（限制仅数字输入）
    final EditText groupInput = new EditText(GetActivity());
    groupInput.setHint("请点击选择群聊→");
    groupInput.setGravity(Gravity.CENTER);
    groupInput.setTextSize(18);
    groupInput.setFocusable(false);
    groupInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
        0, LinearLayout.LayoutParams.WRAP_CONTENT
    );
    inputParams.weight = 1.0f;
    groupInput.setLayoutParams(inputParams);
    
    // 选择群按钮
    Button selectBtn = new Button(GetActivity());
    selectBtn.setText("选择群聊");
    selectBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showGroupSelectDialog(groupInput);
        }
    });
    
    groupSelectLayout.addView(groupInput);
    groupSelectLayout.addView(selectBtn);
    rootLayout.addView(groupSelectLayout);

    // 间隔时间设置布局
    LinearLayout delayLayout = new LinearLayout(GetActivity());
    delayLayout.setOrientation(LinearLayout.HORIZONTAL);
    delayLayout.setPadding(0, 20, 0, 20);
    
    // 读取保存的间隔（兼容空值）
    String defaultDelay = getString("群发助手", "发送间隔");
    if (defaultDelay == null || defaultDelay.trim().isEmpty()) {
        defaultDelay = String.valueOf(sendDelay);
    }
    
    // 文本提示
    TextView delayText = new TextView(GetActivity());
    delayText.setText("发送间隔(毫秒)：");
    delayText.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, 
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    
    // 间隔时间输入框（仅数字，增加输入监听）
    final EditText delayInput = new EditText(GetActivity());
    delayInput.setText(defaultDelay);
    delayInput.setGravity(Gravity.CENTER);
    delayInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    LinearLayout.LayoutParams delayParams = new LinearLayout.LayoutParams(
        0, LinearLayout.LayoutParams.WRAP_CONTENT
    );
    delayParams.weight = 1.0f;
    delayInput.setLayoutParams(delayParams);
    // 添加输入限制监听器
    delayInput.addTextChangedListener(new TextWatcher() {
     public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
     public void onTextChanged(CharSequence s, int start, int before, int count) {}
     public void afterTextChanged(Editable s) {
        String input = s.toString().trim();
       
        // 输入为空时处理
        if (input.isEmpty()) {
            delayInput.setText(String.valueOf(sendDelay));
            showToast("间隔不能为空，\n已使用默认值：" + sendDelay + "毫秒");
            return;
        }
        
        try {
            int value = Integer.parseInt(input);
            
            // 输入0时处理
            if (value == 0) {
                delayInput.setText(String.valueOf(sendDelay));
                showToast("间隔不能为0，\n已使用默认值：" + sendDelay + "毫秒");
            }
            // 输入超过120000时处理（＞2分钟）
            else if (value > 120000) {
                delayInput.setText("120000");
                showToast("间隔不能超过120000毫秒，\n已自动调整");
            }
        } catch (NumberFormatException e) {
            // 输入非数字时处理
            delayInput.setText(String.valueOf(sendDelay));
            showToast("请输入有效数字，\n已使用默认值：" + sendDelay + "毫秒");
        }
     }
   });
   
   // 添加刷新图标控件
   ImageView refreshIcon = new ImageView(GetActivity());
   refreshIcon.setImageResource(android.R.drawable.ic_menu_rotate);
   LinearLayout.LayoutParams refreshParams = new LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT
   );
   refreshParams.leftMargin = 10;
   refreshIcon.setLayoutParams(refreshParams);
   refreshIcon.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
       String delay = delayInput.getText().toString().trim();
       if(delay!=null&&!delay.isEmpty()){
         sendDelay = Integer.parseInt(delay);
         showToast("已刷新发送间隔：" + sendDelay + "毫秒");
       }else{
         sendDelay = sendDelay;
         showToast("已恢复默认间隔：" + sendDelay + "毫秒");
       }
    }
   });
   
    delayLayout.addView(delayText);
    delayLayout.addView(delayInput);
    delayLayout.addView(refreshIcon); 
    rootLayout.addView(delayLayout);

    // 多功能按钮布局
    LinearLayout functionLayout = new LinearLayout(GetActivity());
    functionLayout.setOrientation(LinearLayout.HORIZONTAL);
    functionLayout.setWeightSum(4.0f);
    
    // 保存按钮
    Button saveBtn = new Button(GetActivity());
    saveBtn.setText("保存");
    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
        0, LinearLayout.LayoutParams.WRAP_CONTENT
    );
    btnParams.weight = 1.0f;
    saveBtn.setLayoutParams(btnParams);
    saveBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showSaveSlotsDialog(groupInput, delayInput, contentInput);
        }
    });
    
    // 读取按钮
    Button loadBtn = new Button(GetActivity());
    loadBtn.setText("读取");
    loadBtn.setLayoutParams(btnParams);
    loadBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showLoadSlotsDialog(groupInput, delayInput, contentInput);
        }
    });

    // 终止按钮
    Button stopBtn = new Button(GetActivity());
    stopBtn.setText("终止");
    stopBtn.setLayoutParams(btnParams);
    stopBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            boolean state = getBoolean("群发助手", "群发状态", false);
            if (state==true) {
                showToast("正在尝试结束群发状态…");
                putBoolean("群发助手", "群发状态", false);
            } else {
                showToast("当前没有群发任务哦");
            }
        }
    });
    
    // 清空按钮
    Button clearBtn = new Button(GetActivity());
    clearBtn.setText("清空");
    clearBtn.setLayoutParams(btnParams);
    clearBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            contentInput.setText("");
            groupInput.setText("");
            selectedGroups = "";
            messageContent = "";
        }
    });
    
    functionLayout.addView(saveBtn);
    functionLayout.addView(loadBtn);
    functionLayout.addView(stopBtn);
    functionLayout.addView(clearBtn);
    rootLayout.addView(functionLayout);

    // 消息内容输入框布局
    LinearLayout contentLayout = new LinearLayout(GetActivity());
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    
    TextView titleText = new TextView(GetActivity());
    titleText.setText("群发内容：");
    titleText.setTextColor(android.graphics.Color.parseColor("#00ffff"));
    titleText.setTextSize(15);
    contentLayout.addView(titleText);
    
    final EditText contentInput = new EditText(GetActivity());
    contentInput.setId(View.generateViewId());
    int contentInputId = contentInput.getId();
    contentInput.setHint("输入群发内容，多条用#|分隔");
    contentInput.setGravity(Gravity.TOP | Gravity.LEFT);
    contentInput.setTextSize(18);
    contentInput.setMaxWidth(600);
    contentInput.setSingleLine(false);
    contentInput.setMinLines(3);
    contentInput.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, 
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    
    contentLayout.addView(contentInput);
    rootLayout.addView(contentLayout);

    builder.setView(rootLayout);

    // 确定按钮
    builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            selectedGroups = groupInput.getText().toString().trim();
            messageContent = contentInput.getText().toString().trim();
            boolean state = getBoolean("群发助手", "群发状态", false);
            
            if (state==true) {
                ((Activity) GetActivity()).runOnUiThread(new Runnable() {
                    public void run() {
                        showToast("请点击“终止”结束之前的任务！");
                        initMassSenderDialog();
                    }
                });
                return;
            }
            
            if (selectedGroups.isEmpty()) {
                ((Activity) GetActivity()).runOnUiThread(new Runnable() {
                    public void run() {
                        showToast("未选择群聊，请点击选择");
                        initMassSenderDialog();
                    }
                });
                return;
            }
            
            // 获取间隔时间
            try {
                sendDelay = Integer.parseInt(delayInput.getText().toString().trim());
            } catch (Exception e) {
                sendDelay = 1500;
            }

            // 计算群数量和消息数量
            String[] groupArray = selectedGroups.split(",");
            int groupCount = groupArray.length;
            String[] msgArray = messageContent.split("#\\|", -1);
            int msgCount = msgArray.length;
            
            // 计算预计完成时间
            long totalTime = (long) groupCount * msgCount * sendDelay;
            long finishTime = System.currentTimeMillis() + totalTime;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String finishTimeStr = sdf.format(new Date(finishTime));
            String currentTimeStr = sdf.format(new Date(System.currentTimeMillis()));
            
            // 发送前，弹出提示对话框
            AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(GetActivity());
            confirmBuilder.setTitle("发送确认");
            
            LinearLayout confirmLayout = new LinearLayout(GetActivity());
            confirmLayout.setOrientation(LinearLayout.VERTICAL);
            confirmLayout.setPadding(30, 20, 20, 20);
            
            // 目标群聊布局
            LinearLayout groupLayout = new LinearLayout(GetActivity());
            groupLayout.setOrientation(LinearLayout.HORIZONTAL);
            groupLayout.setPadding(0, 0, 0, 10);
            
            TextView groupTitle = new TextView(GetActivity());
            groupTitle.setText("目标群聊：");
            groupTitle.setTextColor(android.graphics.Color.parseColor("#ffffff"));
            groupTitle.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            groupLayout.addView(groupTitle);
            
            TextView groupContent = new TextView(GetActivity());
            groupContent.setText(selectedGroups);
            LinearLayout.LayoutParams groupContentParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            groupContentParams.weight = 1.0f;
            groupContent.setLayoutParams(groupContentParams);
            groupContent.setSingleLine(false);
            groupContent.setTextIsSelectable(true);
            groupLayout.addView(groupContent);
            
            confirmLayout.addView(groupLayout);
            
            // 间隔时间
            TextView delayText = new TextView(GetActivity());
            delayText.setText("发送间隔：" + sendDelay + "毫秒");
            delayText.setTextSize(14);
            delayText.setPadding(0, 0, 0, 10);
            confirmLayout.addView(delayText);
    
            // 消息数量
            TextView msgCountText = new TextView(GetActivity());
            msgCountText.setText("消息数量：（"+msgCount+"条×"+groupCount+"群="+(groupCount*msgCount)+"条）");
            msgCountText.setTextSize(14);
            msgCountText.setPadding(0, 0, 0, 10);
            confirmLayout.addView(msgCountText);
            
            // 分隔线
            TextView divider = new TextView(GetActivity());
            divider.setText("—————————————");
            divider.setTextSize(15);
            divider.setPadding(0, 0, 0, 10);
            confirmLayout.addView(divider);
            
             // 预计完成时间
            TextView timeText = new TextView(GetActivity());
            timeText.setText("现在" + currentTimeStr + ",\n预计在" + finishTimeStr + "完成～");
            timeText.setTextSize(14);
            timeText.setPadding(0, 0, 0, 10);
            confirmLayout.addView(timeText);
            
            // 计算耗费时长
            TextView timeText2 = new TextView(GetActivity());
            timeText2.setText("预计耗时≥" + formatTime(finishTime - System.currentTimeMillis()));
            timeText2.setTextSize(14);
            timeText2.setPadding(0, 0, 0, 10);
            timeText2.setTextColor(android.graphics.Color.parseColor("#66ccff"));
            confirmLayout.addView(timeText2);
            
            // 确认提示
            TextView confirmText = new TextView(GetActivity());
            confirmText.setText("是否继续发送？");
            confirmText.setTextSize(14);
            confirmLayout.addView(confirmText);
            
            confirmBuilder.setView(confirmLayout);
            
            confirmBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    putBoolean("群发助手", "群发状态", true);
                    sendMassMessage();
                }
            });

            confirmBuilder.setNegativeButton("取消", null);
            confirmBuilder.show();
        }
    });
    builder.setNegativeButton("取消", null);
    builder.show();
}


// 显示保存槽列表对话框
private void showSaveSlotsDialog(EditText groupInput, EditText delayInput, EditText contentInput) {
    // 主线程显示
    if (Looper.myLooper() == Looper.getMainLooper()) {
        createSaveSlotsDialog(groupInput, delayInput, contentInput);
    } else {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                createSaveSlotsDialog(groupInput, delayInput, contentInput);
            }
        });
    }
}

// 创建保存槽对话框（含长按删除功能）
private void createSaveSlotsDialog(EditText groupInput, EditText delayInput, EditText contentInput) {
    AlertDialog.Builder builder = new AlertDialog.Builder(GetActivity());
    builder.setTitle("选择保存位置（可长按删除槽位）");

    // 准备列表数据
    final List slotItems = new ArrayList();
    for (int i = 0; i < SAVE_SLOTS; i++) {
        slotItems.add(getSlotDisplayName(i));
    }

    // 创建列表视图
    ListView listView = new ListView(GetActivity());
    final ArrayAdapter adapter = new ArrayAdapter(
        GetActivity(),
        android.R.layout.simple_list_item_1,
        slotItems
    );
    listView.setAdapter(adapter);

    builder.setView(listView);
    // 添加取消按钮
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    final AlertDialog dialog = builder.create();

    // 列表项点击事件（保存功能）
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String content = contentInput.getText().toString().trim();
            String quntext = groupInput.getText().toString().trim();
            String delay = delayInput.getText().toString().trim();

            if (!content.isEmpty() && !quntext.isEmpty() && !delay.isEmpty()) {
                // 保存到指定槽位
                String slotKey = SLOT_PREFIX + position;
                int groupCount = quntext.split(",").length;
                int contentCount = content.split("#\\|", -1).length;
                putString("群发助手", slotKey + "_content", content);
                putString("群发助手", slotKey + "_groups", quntext);
                putString("群发助手", slotKey + "_delay", delay);
                putString("群发助手", slotKey + "_time", getTodayDate(2));
                showToast("已保存到“位置 " + (position + 1)+"”！\n（" + groupCount + "个群，内容" + contentCount + "条）");
                // 刷新列表显示
                slotItems.set(position, getSlotDisplayName(position));
                adapter.notifyDataSetChanged();
            } else {
                StringBuilder sy = new StringBuilder();
                if (content.isEmpty()) sy.append("内容为空,");
                if (quntext.isEmpty()) sy.append("群聊未选择,");
                if (delay.isEmpty()) sy.append("间隔为空,");
                showToast(sy.toString() + " 保存失败！");
            }
            dialog.dismiss();
        }
    });

    // 列表项长按事件（删除功能）
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
            new AlertDialog.Builder(GetActivity())
                .setTitle("删除确认")
                .setMessage("确定要删除“位置 " + (position + 1) + "”的内容吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String slotKey = SLOT_PREFIX + position;
                        // 清空该槽位数据
                        putString("群发助手", slotKey + "_content", "");
                        putString("群发助手", slotKey + "_groups", "");
                        putString("群发助手", slotKey + "_delay", "");
                        putString("群发助手", slotKey + "_time", "");
                        // 刷新列表
                        slotItems.set(position, getSlotDisplayName(position));
                        adapter.notifyDataSetChanged();
                        showToast("已删除“位置 " + (position + 1) + "”的内容");
                    }
                })
                .setNegativeButton("取消", null)
                .show();
            return true; // 阻止事件传递
        }
    });

    dialog.show();
}

// 显示读取槽列表对话框
private void showLoadSlotsDialog(EditText groupInput, EditText delayInput, EditText contentInput) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        createLoadSlotsDialog(groupInput, delayInput, contentInput);
    } else {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                createLoadSlotsDialog(groupInput, delayInput, contentInput);
            }
        });
    }
}

// 创建读取槽对话框（含长按删除功能）
private void createLoadSlotsDialog(EditText groupInput, EditText delayInput, EditText contentInput) {
    AlertDialog.Builder builder = new AlertDialog.Builder(GetActivity());
    builder.setTitle("选择读取位置（可长按删除槽位）");

    // 准备列表数据
    final List slotItems = new ArrayList();
    for (int i = 0; i < SAVE_SLOTS; i++) {
        slotItems.add(getSlotDisplayName(i));
    }

    ListView listView = new ListView(GetActivity());
    final ArrayAdapter adapter = new ArrayAdapter(
        GetActivity(),
        android.R.layout.simple_list_item_1,
        slotItems
    );
    listView.setAdapter(adapter);

    builder.setView(listView);
    // 添加取消按钮
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    final AlertDialog dialog = builder.create();

    // 列表项点击事件（读取功能）
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String slotKey = SLOT_PREFIX + position;
            String savedContent = getString("群发助手", slotKey + "_content");
            String savedQuntext = getString("群发助手", slotKey + "_groups");
            String savedDelay = getString("群发助手", slotKey + "_delay");

            if (savedContent != null && !savedContent.isEmpty() 
                && savedQuntext != null && !savedQuntext.isEmpty()
                && savedDelay != null && !savedDelay.isEmpty()) {
                int groupCount = savedQuntext.split(",").length;
                int contentCount = savedContent.split("#\\|", -1).length;
                
                contentInput.setText(savedContent);
                groupInput.setText(savedQuntext);
                delayInput.setText(savedDelay);
                showToast("已读取“位置 " + (position + 1) + " ”的内容\n（" + groupCount + "个群，内容" + contentCount + "条）");
            } else {
                showToast("“位置 " + (position + 1) + "” 没有保存内容");
            }
            dialog.dismiss();
        }
    });

    // 列表项长按事件（删除功能）
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
            String slotKey = SLOT_PREFIX + position;
            String savedContent = getString("群发助手", slotKey + "_content");
            if (savedContent == null || savedContent.isEmpty()) {
                showToast("“位置 " + (position + 1) + "” 无内容可删除");
                return true;
            }

            new AlertDialog.Builder(GetActivity())
                .setTitle("删除确认")
                .setMessage("确定要删除“位置 " + (position + 1) + "”的内容吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 清空该槽位数据
                        putString("群发助手", slotKey + "_content", "");
                        putString("群发助手", slotKey + "_groups", "");
                        putString("群发助手", slotKey + "_delay", "");
                        putString("群发助手", slotKey + "_time", "");
                        // 刷新列表
                        slotItems.set(position, getSlotDisplayName(position));
                        adapter.notifyDataSetChanged();
                        showToast("已删除“位置 " + (position + 1) + "”的内容");
                    }
                })
                .setNegativeButton("取消", null)
                .show();
            return true;
        }
    });

    dialog.show();
}

// 获取槽位显示名称
private String getSlotDisplayName(int slotIndex) {
    String slotKey = SLOT_PREFIX + slotIndex;
    String time = getString("群发助手", slotKey + "_time");
    String groups = getString("群发助手", slotKey + "_groups");
    String content = getString("群发助手", slotKey + "_content");

    if (time == null || time.isEmpty() || groups == null || content == null || content.isEmpty()) {
        return (slotIndex + 1) + ". 无内容";
    } else {
        int groupCount = groups.split(",").length;
        int contentCount = content.split("#\\|", -1).length;
        return (slotIndex + 1) + ". " + time + "\n（" + groupCount + "个群，内容" + contentCount + "条）";
    }
}


// 群聊选择对话框（支持多选）
void showGroupSelectDialog(EditText input) {
    // 切换到主线程显示对话框
    if (Looper.myLooper() == Looper.getMainLooper()) {
        createGroupSelectDialog(input);
    } else {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                createGroupSelectDialog(input);
            }
        });
    }
}


// 构建群聊选择对话框
private void createGroupSelectDialog(EditText input) {
    AlertDialog.Builder builder = new AlertDialog.Builder(GetActivity());
    builder.setTitle("选择群聊（可多选）");

    ArrayList groups = getGroupList();
    final ArrayList validGroupUins = new ArrayList(); // 存储有效群号
    ArrayList validGroupNames = new ArrayList(); // 存储群名+群号

    // 过滤禁止的群聊，收集有效群信息
    for (int i = 0; i < groups.size(); i++) {
        Object groupInfo = groups.get(i);
        String groupUin = (String) getField(groupInfo, "GroupUin");
        if (forbiddenGroups.contains(groupUin)) {
            continue; // 跳过禁止的群
        }
        validGroupUins.add(groupUin);
        String groupName = (String) getField(groupInfo, "GroupName");
        validGroupNames.add(groupName + "(" + groupUin + ")");
    }

    // 转换为数组
    final String[] groupNames = validGroupNames.toArray(new String[0]);
    final boolean[] checkedItems = new boolean[groupNames.length]; // 记录选中状态

    // 设置多选列表
    builder.setMultiChoiceItems(groupNames, checkedItems,
        new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked; // 更新选中状态
            }
        }
    );

    // 确定按钮：收集选中的群号
    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            StringBuilder selected = new StringBuilder();
            // 遍历所有选项，收集选中的群号
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    String groupUin = validGroupUins.get(i);
                    if (selected.length() > 0) {
                        selected.append(","); // 用逗号分隔多个群号
                    }
                    selected.append(groupUin);
                }
            }
            // 更新输入框内容
            input.setText(selected.toString());
        }
    });

    // 取消按钮
    builder.setNegativeButton("取消", null);
    builder.show();
}


// 反射获取字段值
Object getField(Object obj, String fieldName) {
    try {
        Field field = obj.getClass().getField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    } catch (Exception e) {
        e.printStackTrace();
        return "";
    }
}


// 执行群发逻辑
void sendMassMessage() {
    if (selectedGroups.isEmpty() || messageContent.isEmpty()) {
        // 主线程显示提示
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                showToast("请选择群聊并输入内容");
                initMassSenderDialog();
            }
        });
        return;
    }

    final String[] groupArray = selectedGroups.split(",");
    final String[] messages = messageContent.split("#\\|", -1); // 保留所有分割结果

    // 数组空值校验
    if (groupArray == null || groupArray.length == 0) {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                showToast("未选择有效群聊");
            }
        });
        return;
    }
    if (messages == null || messages.length == 0) {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                showToast("未输入有效消息内容");
            }
        });
        return;
    }

    new Thread(new Runnable() {
        public void run() {
            for (String groupUin : groupArray) {
                // 跳过空群号
                if (groupUin == null || groupUin.trim().isEmpty()) {
                    continue;
                }
                for (String msg : messages) {
                   int delay = sendDelay; //发送间隔
                    // 检查是否需要终止群发
                    boolean isRunning = getBoolean("群发助手", "群发状态", false);
                    if (!isRunning) {
                        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
                            public void run() {
                                showToast("群发已终止");
                            }
                        });
                        return;
                    }
                    // 发送非空消息（允许包含空格的消息）
                    if (msg != null && !msg.trim().isEmpty()) {
                        // 执行发送
                        sendMsg(groupUin, "", msg);
                    }
                    // 间隔延迟（避免频率过快）
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 中断后终止任务
                        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
                            public void run() {
                                showToast("群发被中断，可能是发送间隔未能成功读取/解析");
                                putBoolean("群发助手", "群发状态", false);
                            }
                        });
                        return;
                    }
                }
            }
            // 全部发送完成
            ((Activity) GetActivity()).runOnUiThread(new Runnable() {
                public void run() {
                    putBoolean("群发助手", "群发状态", false);
                    showToast("所有消息已发送完成！");
                }
            });
        }
    }).start();
}


// 启动入口
void startMassSender() {
    initMassSenderDialog();
}

// 添加菜单入口
AddItem("开始群发", "startMassSender");
AddItem("终止群发", "stopSend");

// 菜单回调
void startMassSender(String groupUin, String userUin, int chatType) {
    initMassSenderDialog();
}
void stopSend(String groupUin, String userUin, int chatType) {
  //判断方法与“终止”按钮一致
    boolean state = getBoolean("群发助手", "群发状态", false);
    if (state==true) {
         Toast("正在尝试结束群发状态…");
         putBoolean("群发助手", "群发状态", false);
    } else {
         Toast("当前没有群发任务哦");
    }
}
