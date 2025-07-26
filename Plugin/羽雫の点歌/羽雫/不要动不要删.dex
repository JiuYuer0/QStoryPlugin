String[] QQkey={"扣扣点歌","QQ点歌","Qq点歌","qQ点歌","qq点歌"};//QQ
String[] WYkey={"网易点歌","网易云点歌","WY点歌","Wy点歌","wY点歌","wy点歌"};//网易
String[] KWkey={"酷我点歌","KW点歌","Kw点歌","kW点歌","kw点歌"};//酷我
/*点歌触发指令
1.尽量设置文字开头的
2.别设置 链接/歌词/语音/选歌/文件 冲突指令的
3.别设置数字/表情/空格开头的
4.字母的话要区分大小写,比如设置了"dg"那别人发送"DG"/Dg"/dG将不会触发*/

int mengban=200;//点歌图片增加一层白色蒙版(防止部分字看不清)范围0-255,255为没有越大蒙版越清晰
float blurred=15f;//图片模糊(1f-25f),1f为不模糊
String picapi=pluginPath+"a.jpg";//本地文转图的底图
//填直链或者路径 别填错

String colors="#FFFFFF";//本地文转图的字体颜色 16进制
//留空黑色 填随机可以随机颜色
String textface="";//本地文转图字体路径 错填默认系统字体
int mysendtype=4;//点歌返回模式 0直接返回选择Chose返回歌曲
int lyricstype=4;//歌词返回模式
//点歌/歌词返回模式可选
// 1.json文卡 仅wifi可用(好像)
// 2.text文本 容易刷屏
// 3.接口文图片 有点慢
// 4.本地文转图(缩放)
// 5.本地文转图2(横屏无缩放 半成品)

int sleeptime=10000;//下载的文件(图片/语音/文件)保存时间 单位毫秒
int Chose=1;//当mysendtype=0时 直接返回的歌曲 范围是1到qmax/wymax
int piclevel=8;//点歌图片返回的质量 2-30 1会访问出错
//图片质量越高图越大发送越慢 毕竟一图4.6mb()
//图片质量越高越好看(bushi)
int maxtime=600;//点歌后多少秒后过期(单位:秒)
String ark_yx="猫羽雫～～～";//文卡外显

int qqmax=20;//QQ点歌最多显示数量 1-60
int qqyz_ark=8;//QQ点歌卡片音质(默认)
int qqyz_url=14;//QQ点歌链接音质(默认)
int qqyz_yy=2;//QQ点歌语音音质(默认)
int qqyz_wj=6;//QQ点歌文件音质(默认)
/*QQ音质选择
0	音乐试听
1	有损音质
2	有损音质
3	有损音质
4	标准音质
5	标准音质
6	标准音质
7	标准音质
8	HQ高音质
9	HQ高音质（音质增强）
10	SQ无损音质
11	Hi-Res音质
12	杜比全景声
13	臻品全景声
14	臻品母带2.0*/

int wymax=20;//网易点歌最多显示数量 1-100
int wyyz_ark=5;//网易点歌卡片音质(默认)
int wyyz_url=9;//网易点歌链接音质(默认)
int wyyz_yy=1;//网易点歌语音音质(默认)
int wyyz_wj=6;//网易点歌文件音质(默认)
/*网易音质选择
1	标准（64k）
2	标准（128k）
3	HQ极高（192k）
4	HQ极高（320k）
5	SQ无损
6	高解析度无损（Hi-Res）
7	高清臻音（Spatial Autio）
8	沉浸环绕声（Surround Autio）
9	超清母带（Master）*/

int kwmax=20;//酷我点歌最多显示数量 1-100
int kwyz_ark=6;//酷我点歌卡片音质(默认)
int kwyz_url=6;//酷我点歌链接音质(默认)
int kwyz_yy=1;//酷我点歌语音音质(默认)
int kwyz_wj=6;//酷我点歌文件音质(默认)
/*酷我音质选择
1	acc 普通音质
2    wma 普通音质
3    ogg 标准音质
4    standard 低音质
5    exhigh 高音质
6    lossless 无损
7    hires HiRes音质
(ape,zp,hifi,sur,jymaster音质不提供服务)*/

String jimao="不是，你确定选这首?";//当选歌序号超出搜索范围时返回的
String[] wjc={"\u4e60\u8fd1\u5e73","\u9093\u5c0f\u5e73","\u6bdb\u6cfd\u4e1c","\u8001\u864e\u673a","\u767e\u5bb6\u4e50","\u5e78\u8fd0\u98de\u8247","\u5feb\u4e50\u98de\u8247","\u516d\u5408\u5f69","\u8840\u9992\u5934","\u6597\u5730\u4e3b","\u9f99\u864e\u6597","\u4ec0\u4e48\u89c6\u9891","\u65f6\u65f6\u5f69","\u8840\u6c34\u5723\u7075","\u6cd5\u8f6e\u529f","\u89c2\u97f3\u6cd5\u95e8","\u68ad\u54c8","\u4e00\u5206\u4e24\u5206\u4e09\u5206","\u4e00\u5206\u4e24\u5757","\u5706\u987f\u6cd5\u95e8","\u95e8\u5f92\u4f1a","\u7b97\u547d","\u732b\u7fbd\u96eb"};//点歌违禁词,点歌时候包含里面的将不会点歌,防止被↓号,↓群,可以自己加,我这是几个常见的下去下号违禁词(已做编码处理)
