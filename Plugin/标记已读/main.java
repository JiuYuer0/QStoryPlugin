import com.tencent.util.QQToastUtil;
public QQToastUtil QToastUtil=new QQToastUtil();
public void QQToast(String text,int i)
{//方法来自小超
    QToastUtil.showQQToastInUiThread(i,text);
}
/*
温柔与海(3468281461)
群聊一:971642498
群聊二:234152952
此文件任何代码禁止搬运抄袭篡改
模块判断方法参考无心语音面板
*/
String myQQ;
String myPath;
public String module() {
    String App=pluginPath+"";
    if(App.equals("void")) {
        if(AppPath.contains("QTool")) {
            return "QTool";
        }
        return "QStory";
    }
    return "Serendipity";
}
if(module().equals("Serendipity")) {
    myQQ=myUin;
    myPath=pluginPath;
} else {
    myPath=AppPath;
    myQQ=MyUin;
}
public void Load(String Path) {
    if(module().equals("Serendipity")) {
        loadJava(Path);
    } else {
        load(Path);
    }
}

Load(myPath+"/data/Core.java");

if(module().equals("Serendipity")) {
    addItem("标记已读", "readinformation2");
} else if(module().equals("QStory")) {
    AddItem("标记已读", "readinformation1");
} else {
    addItem("标记已读", "readinformation1");
}

if(!module().equals("Serendipity")) {
    Toast("加载完成");
}