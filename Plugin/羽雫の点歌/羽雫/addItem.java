import android.net.Uri;
import android.view.WindowManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.widget.Toast;
import java.lang.Runnable;

addItem("开关设置","开关设置");
addItem("查看指令","查看指令");
addItem("反馈/交流","联系");
addItem("配置修改","配置");
addItem("查看log","log");

public void 开关设置(String qun,String qq,int type)
{
if(type == 2) 开关设置(type,qun,"");
else if(type == 1) 开关设置(type,qq,"");
else 开关设置(type,qq,"");
}

public void 查看指令(String qun,String qq,int type)
{
查看指令(type,qun,"");
}

public void 联系(String qun,String qq,int type)
{
联系(type,qun,"");
}

public void 配置(String qun,String qq,int type)
{
配置(type,qun,"");
}

public void log(String qun,String qq,int type)
{
log(type,qun,"");
}

///////////////////////

public void log(int type, String qun, String Name) {
ts3("正在获取..");
new Thread(new Runnable(){
public void run(){
String nr = readFileContent(羽雫+ "log.txt");
if(nr==null){新建(羽雫+ "log.txt");nr = "当前没有日志哦";}
else if(nr.equals("")) nr = "当前没有日志哦";
ts("log(关闭自动清空)", nr);
put(羽雫+ "log.txt","");
}
}).start();
}

public void 开关设置(int type, String qun, String Name) {
    int i = 0;
    boolean[] boolArr;
    String[] kname;
    String[] ww;
    String typemsg;
    if (type == 2) {
        typemsg = "本群";
    } else if (type == 1) {
        typemsg = "本好友";
    } else {
        Toast("不支持该聊天类型");
        return;
    }
    boolArr = new boolean[10];
    kname = new String[]{"总开关", qun + "-限制", "文件选歌", "转Silk", "音质调试", "音乐卡片", "备用音卡", "云更新", "解析", "播放器"};
    ww = new String[]{"是否启用脚本", typemsg + "使用限制", "文件选歌", "语音选歌转Silk\n(无特殊要求不开)", "音质调试\n(音质选歌)", "使用API签名音卡\n(没有装音乐软件开)", "备用音卡发送\n(理论上可以不安装音乐软件)", "云更新检测", "音乐解析(半成品,能用,不建议开)\nTips:勾选后下次加载生效", "是否加载播放器\nTips:勾选后下次加载生效"};

    for (String tex : kname) {
        if (i == 1) {
            boolArr[i] = 读("猫羽雫", tex, true);
        } else {
            boolArr[i] = 读("猫羽雫", tex, false);
        }
        i++;
    }
    Activity act = getNowActivity();
    act.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(act, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialogBuilder.setTitle(Html.fromHtml("<font color=\"#123456\">开关设置</font>"));
            dialogBuilder.setMultiChoiceItems(ww, boolArr, new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    boolArr[which] = isChecked;
                }
            });
            dialogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#893BFF\">确认</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    for (int j = 0; j < kname.length; j++) {
                        写("猫羽雫", kname[j], boolArr[j]);
                    }
                    Toast("设置成功");
                    dialog.dismiss();
                }
            });
            dialogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#E3319D\">取消</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.setCancelable(false);

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();

            UI(dialog);
        }
    });
}


public void 联系(int type,String qun,String Name){
List contact= new ArrayList();
contact.add("我要赞助");
contact.add("加群反馈(一群)");
contact.add("加群反馈(二群)");
contact.add("加频道(防失联)");
    Activity activity = getNowActivity();
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final CharSequence[] items = contact.toArray(new CharSequence[contact.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选项");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast("正在跳转.....");
                    switch (which) {
                        case 0:
                            ts3("正在获取..");
                            new Thread(new Runnable(){public void run(){
                            sponsor();
                            }}).start();
                            OK=false;
                            break;
                        case 1:
                            jump("mqqapi://card/show_pslcard?src_type=internal&source=sharecard&version=1&uin=822317725&card_type=group&source=qrcode");
                            break;
                        case 2:
                            jump("mqqapi://card/show_pslcard?src_type=internal&source=sharecard&version=1&uin=1023443362&card_type=group&source=qrcode");
                            break;
                        case 3:
                            jump("mqqapi://openhalfscreenweb/?height=1920&url=https://pd.qq.com/s/gwd683tz");
                            break;
                    }
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            UI(dialog);
            dialog.show();
        }
    });
}

public void 配置(int type, String qun, String Name) {
pz(pluginPath+"配置文件.java");
}

public void 查看指令(int type, String qun, String Name) {
    ts3("正在获取..");
    new Thread(new Runnable() {
        public void run() {
            StringBuilder QQkey2 = new StringBuilder();
            for (int i = 0; i < QQkey.length; i++) {
                QQkey2.append(QQkey[i]);
                // 除了最后一个元素外，其他元素后面都添加逗号
                if (i < QQkey.length - 1) {
                    QQkey2.append(", ");
                }
            }
            StringBuilder WYkey2 = new StringBuilder();
            for (int i = 0; i < WYkey.length; i++) {
                WYkey2.append(WYkey[i]);
                // 除了最后一个元素外，其他元素后面都添加逗号
                if (i < WYkey.length - 1) {
                    WYkey2.append(", ");
                }
            }
            StringBuilder KWkey2 = new StringBuilder();
            for (int i = 0; i < KWkey.length; i++) {
                KWkey2.append(KWkey[i]);
                // 除了最后一个元素外，其他元素后面都添加逗号
                if (i < KWkey.length - 1) {
                    KWkey2.append(", ");
                }
            }
            String title = "指令";
            String nr = "QQ音乐:" + QQkey2.toString() 
            + "\n网易云音乐:" + WYkey2.toString()
            + "\n酷我音乐:" + KWkey2.toString() 
            +" \n当前线路:"+web1+"\n线路2:"+web2;
            ts(title, nr);
        }
    }).start();
}


////////////////