
//临江踏雨不返

//你总是担心失去谁 可谁又会担心失去你

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

String 路 = AppPath + "/纵有相思零落处/";
String 诗集 = 路 + "/也无旧时折枯枝/";
load(路+"秋诗.java");


public class 江云 {

    public static String[][] 残梦 ={
    {  "关键词"  , "◆关键词◆\n"
               + "●添加方法●删除方法●\n"
               + "●其他命令●其他说明●"},
               
    { "添加方法" , "◆关键词添加方法◆\n"
               + "以下是使用方法\n"
               + "添加关键词+关键词+[处理方式列表]\n"
               + "以下是支持的处理方式 : \n"
               + "回复+文字,禁言+时间,\n"
               + "全体禁言,全体解禁,\n"
               + "撤回,踢出,踢黑,回+文字 \n\n"
               + "延迟+毫秒时间 \n\n"
               + "示范发送 : (仅别人可以触发)\n"
               + "添加关键词 江 [回复雨,禁言60,撤回]\n"
               + "然后发送包含\"江\"的消息就会进行上面的命令\n"
               + "命令是顺序执行的 可以重复添加 要用\",\"分隔\n"
               + "有两条相同的关键词新的处理方式会替代旧的处理方式" },
               
    { "删除方法" , "◆关键词删除方法◆\n"
               + "以下是删除方法\n"
               + "删除关键词+关键词\n"
               + "例如 : 删除关键词 江\n"
               +"(可以发送清空关键词快速清空关键词)"},
               
    { "其他命令" , "◆其他命令◆\n"
               + "●查看关键词●清空关键词●\n"
               + "●查看所有关键词●清空所有关键词●\n"
               + "●正则表达式●查看支持的变量●" },
    { "查看支持的变量" , "◆支持的动态参数◆\n"
                      + "AtQQ 在添加为回复内容时AtQQ会被替换为艾特发送人\n"
                      + "qun 在添加为回复内容时会被替换为本群群号\n"
                      + "敬请期待其他参数"},
               
    { "其他说明" , "本jaja脚本的消息默认自己不触发自己发送的消息(为了防止错误添加导致自己触发自己刷屏)，\n但你可以发送\n \"接受消息\" 来关闭这个限制，发送\n \"屏蔽消息\" 来打开此限制\n\n在添加关键词时如添加了 [撤回] 则会把添加关键词的这条指令消息撤回，并且不会发出添加成功的消息，而是以提示的方法提醒添加成功，此限制会同步所有群" },

    { "正则表达式" , "发送 \"开启正则\" 则会开启正则表达式匹配关键词，发送 \"关闭正则\" 则会关闭正则表达模式 该模式是单个群使用的，您最好在使用该模式前发送 \"详细正则说明\" 来查看与脚本有关的正则说明"},
    
    { "详细正则说明" , "如果不会使用正则表达式可以去搜索引擎搜索 \"正则表达式\" 来学习正则表达式\n\n接下来会说明正则表达式对此脚本的影响\n1.使用正则表达式添加关键词时 \"[]\" 是可以正常匹配处理方式的 因为处理方式的substring方法\"[]\"会自动从文末端开始查找，不会与正则表达式符号\"[]\"产生冲突性\n2.即使你认为你不需要使用此模式，依然推荐你学习正则表达式，因为强大的正则表达式仍然可以帮你解决很多麻烦的匹配词\n另外欢迎加入我们的交流小组 ：634941583"} };
    public int 索引;
    public String 文本;
    public String 关键词;
    public int[] 文本索引 = new int[2];
    public String 待写处理方式;
    public String 关键词;
    public String 解析过程文本;
    public int 禁言时间;
    public long 延迟时间;
    private static Timer timer;
    private static TimerTask task;
    public String 处理类型;
    public JSONArray 处理方式组;
    public JSONObject 所有关键词对象;
    public JSONObject 处理内容;
    public boolean 是否撤回;
    public static Map 静态双列总对象;
    public static JSONObject 组词列表;
    public static JSONArray 处理列表;
    public static boolean 时 = true;
    public static void 刷新() {
        静态双列总对象 = new HashMap();
        File 路集 = new File(诗集);
        if ( !路集.exists() ) {
           路集.mkdirs();
        }
        for (File 千 : 路集.listFiles() ) {
            if (千.getName().matches("\\d+\\.json")&&千.isFile()) {
               静态双列总对象.put(
               千.getName().substring(0,千.getName().lastIndexOf(".")) ,
               秋诗.阅诗(千.getAbsolutePath()) );
            }
        }
    }
    public static void 至少还有你() {
        时 = false;
        if (timer != null) {
           timer.cancel();
        }
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                时=true;
            }
        };
        timer.schedule(task,500);
    }
	protected void finalize() throws Throwable {
		super.finalize();
		//Toast("内存已回收");
	}
}
江云.刷新();

public void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    if (msg.UserUin.equals(MyUin)) {
        new Thread(new Runnable() {
              public void run() {
                  for (int i=0;i<=江云.残梦.length-1;i++) {        
                      if (msg.MessageContent.equals(江云.残梦[i][0])) {
                         sendMsg(msg.GroupUin,"", 江云.残梦[i][1]);
                      }            
                  }
              }
        }).start();
       if (msg.MessageContent.equals("接受消息") || msg.MessageContent.equals("接收消息")) {
          if(getString("关键词","己")==null) {
	        putString("关键词", "己","开");
            sendMsg(msg.GroupUin,"","已关闭自己不触发的限制");
	      } else {
	        sendMsg(msg.GroupUin,"","无需重复关闭此限制");
	      }
       }
       if (msg.MessageContent.equals("屏蔽消息")) {
          if(getString("关键词","己")==null) {
            sendMsg(msg.GroupUin,"","当前已是不接受自身消息的状态");
	      } else {
	        putString("关键词","己",null);
	        sendMsg(msg.GroupUin,"","已开启不触发自己的消息");
	      }
       }
       if (msg.MessageContent.equals("开启正则")) {
          if(getString("正则表达式",msg.GroupUin)==null) {
	        putString("正则表达式",msg.GroupUin,"开");
            sendMsg(msg.GroupUin,"","已开启本群的正则表达式模式");
	      } else {
	        sendMsg(msg.GroupUin,"","无需重复开启此模式");
	      }
       }
       if (msg.MessageContent.equals("关闭正则")) {
          if(getString("正则表达式",msg.GroupUin)==null) {
            sendMsg(msg.GroupUin,"","当前已是普通匹配模式");
	      } else {
	        putString("正则表达式",msg.GroupUin,null);
	        sendMsg(msg.GroupUin,"","已关闭正则表达式匹配 切换为普通模式");
	      }
       }
       
       if (msg.MessageContent.startsWith("添加关键词")) {
          new Thread(new Runnable() {
              public void run() {
              江云.至少还有你();
              try {
                  江云 江 = new 江云();
                  江.索引 = 5;
                  江.文本 = msg.MessageContent.substring(江.索引);
                  if (江.文本.startsWith(" ")) {
                     江.索引++;
                     江.文本 = msg.MessageContent.substring(江.索引);
                  }
                  江.文本索引[0] = 江.文本.lastIndexOf("[")+1;
                  江.文本索引[1] = 江.文本.lastIndexOf("]");
                  if ( 江.文本索引[0]==-1 || 江.文本索引[1]==-1) {
                     江.文本索引[0] = 江.文本.lastIndexOf(" ")+1;
                     江.文本索引[1] = 江.文本.length();
                  }
                  江.待写处理方式 = 江.文本.substring(江.文本索引[0],江.文本索引[1]);
                  江.关键词 = 江.文本.substring(0 , 江.文本索引[0]-1);
                  if (江.关键词.endsWith(" ")) {
                     江.文本索引[0]--;
                     江.关键词 = 江.文本.substring(0 , 江.文本索引[0]-1);
                  }
                  String pattern = "撤回|回复 ?[\\S\\s][^\\]，,]*|回 ?[\\S\\s][^\\]，,]*|踢|踢出|踢黑|禁言 ?\\d+|延迟 ?\\d+|全体禁言|全体解禁|全禁|全解";
                  Pattern r = Pattern.compile(pattern);
                  Matcher m = r.matcher(江.待写处理方式);
                  江.所有关键词对象 =new JSONObject(秋诗.阅诗(诗集+msg.GroupUin+".json"));
                  江.处理方式组 = new JSONArray();
                  int i = 0;
                  while (m.find()) {
                     江.处理类型 = 江.待写处理方式.substring(m.start(), m.end());
                     if (江.处理类型.startsWith("回复")) {
                        江.索引 = 江.处理类型.lastIndexOf("复")+1;
                        江.文本 = 江.处理类型.substring(江.索引);
                        if (江.文本.startsWith(" ")) {
                           江.索引++;
                           江.文本 = 江.处理类型.substring(江.索引);
                        }
                        else if(江.文本.equals("")) {
                            sendMsg(msg.GroupUin,"","没有设置回复内容，已设置为江江大美女");
                           江.文本 = "江江大美女";
                        }
                        江.处理内容 = new JSONObject();
                        if (江.关键词.contains(江.文本) || 江.文本.contains(江.关键词) ) {
                           sendMsg(msg.GroupUin,"","回复内容和关键词重叠 会导致自己触发自己刷屏 已自动开启不接受自己的消息");
                           putString("关键词", "己",null);
	                       Toast("已开启屏蔽自己 不会检测自己发送的关键词");
                        }
                        江.处理内容.put("回复",江.文本);
                        江.处理方式组.put(江.处理内容);
                     }
                     else if (江.处理类型.startsWith("回")) {
                        江.索引 = 江.处理类型.lastIndexOf("回")+1;
                        江.文本 = 江.处理类型.substring(江.索引);
                        if (江.文本.startsWith(" ")) {
                           江.索引++;
                           江.文本 = 江.处理类型.substring(江.索引);
                        }
                        else if(江.文本.equals("")) {
                            sendMsg(msg.GroupUin,"","没有设置回复内容，已设置为江江大美女");
                           江.文本 = "江江大美女";
                        }
                        江.处理内容 = new JSONObject();
                        if (江.关键词.contains(江.文本) || 江.文本.contains(江.关键词) ) {
                           sendMsg(msg.GroupUin,"","回复内容和关键词重叠 会导致自己触发自己刷屏 已自动开启不接受自己的消息");
                           putString("关键词", "己",null);
	                       Toast("已开启屏蔽自己 不会检测自己发送的关键词");
                        }
                        江.处理内容.put("回",江.文本);
                        江.处理方式组.put(江.处理内容);
                     }
                     else if (江.处理类型.startsWith("踢")) {
                        江.处理内容 = new JSONObject();
                        if (江.处理类型.equals("踢黑")) {
                        江.处理内容.put("踢",true);
                        } else {
                        江.处理内容.put("踢",false);
                        }
                        江.处理方式组.put(江.处理内容);
                     }
                     else if (江.处理类型.startsWith("禁言")) {
                    	江.索引 = 江.处理类型.lastIndexOf("言")+1;
                        江.文本 = 江.处理类型.substring(江.索引); 
                        if (江.文本.startsWith(" ")) {
                           江.索引++;
                           江.文本 = 江.处理类型.substring(江.索引);
                        }
                        江.禁言时间 = Integer.parseInt(江.文本);
                        江.处理内容 = new JSONObject();
                        江.处理内容.put("禁言",江.禁言时间);
                        江.处理方式组.put(江.处理内容);
                     }
                     else if (江.处理类型.startsWith("延迟")) {
                    	江.索引 = 江.处理类型.lastIndexOf("迟")+1;
                        江.文本 = 江.处理类型.substring(江.索引); 
                        if (江.文本.startsWith(" ")) {
                           江.索引++;
                           江.文本 = 江.处理类型.substring(江.索引);
                        }
                        江.延迟时间 = Long.parseLong(江.文本);
                        江.处理内容 = new JSONObject();
                        江.处理内容.put("延迟",江.延迟时间);
                        江.处理方式组.put(江.处理内容);
                     }
                     
                     else if (江.处理类型.equals("撤回")) {
                        revokeMsg(msg);
                        江.处理方式组.put("撤回");
                     }
                     else if (江.处理类型.equals("全体禁言") || 江.处理类型.equals("全禁"))
                        江.处理方式组.put("全体禁言");
                        
                     else if (江.处理类型.equals("全体解禁") || 江.处理类型.equals("全解"))
                        江.处理方式组.put("全体解禁");
                        
                     江.所有关键词对象.put(江.关键词,江.处理方式组);
                     i++;
                  }
                  if (i == 0) {
                    sendMsg(msg.GroupUin,"","不包含任何处理方式");
                    return;
                  }                  
                  江.解析过程文本 = "关键词【"+江.关键词+"】\n"
                  +"处理方式\n"
                  +江.待写处理方式;
                  for (int i2=0;i2<=江.处理方式组.length()-1;i2++) {
                       if (江.处理方式组.get(i2).toString().equals("撤回")) {
                            Toast(江.解析过程文本);
                            江.是否撤回 = true;
                            break;
                       }
                  }
                  if (!江.是否撤回) {
                      sendMsg(msg.GroupUin,"",江.解析过程文本);
                  }
                  秋诗.写诗(诗集+msg.GroupUin+".json",江.所有关键词对象.toString());
                  江云.刷新();
                  江 = null;
              } catch(Exception e) {
                  sendMsg(msg.GroupUin,"","不包含任何处理方式或格式错误");
                  e.printStackTrace();
              }
              }
          }).start();          
       }
       if (msg.MessageContent.startsWith("删除关键词")) {
          new Thread(new Runnable() {
              public void run() {
                  江云 江 = new 江云();
                  江.索引 = 5;
                  江.文本 = msg.MessageContent.substring(江.索引);
                  if (江.文本.startsWith(" ")) {
                     江.索引++;
                     江.文本 = msg.MessageContent.substring(江.索引);
                  }
                  江.所有关键词对象 = new JSONObject(秋诗.阅诗(诗集+msg.GroupUin+".json"));
                  if (江.所有关键词对象.isNull(江.文本)) {
                     sendMsg(msg.GroupUin,"","当前群聊不存在关键词\""+江.文本+"\"");
                  }
                  if (!江.所有关键词对象.isNull(江.文本)) {
                     江.所有关键词对象.remove(江.文本);
                     sendMsg(msg.GroupUin,"","已删除关键词\""+江.文本+"\"");
                  }
                  秋诗.写诗(诗集+msg.GroupUin+".json",江.所有关键词对象.toString());
                  江云.刷新();
                  江 = null;
              }
          }).start();
       }   
       if (msg.MessageContent.equals("查看关键词")) {
          new Thread(new Runnable() {
              public void run() {
                  江云.至少还有你();
                  江云.刷新();
                  江云 江 = new 江云();
                  江.所有关键词对象 = new JSONObject(秋诗.阅诗(诗集+msg.GroupUin+".json"));
                  StringBuilder 花莫 = new StringBuilder();
                  花莫.append("当前群的关键词列表 : \n");
                  Iterator it = 江.所有关键词对象.keys();
                  while (it.hasNext()) {
                     String key = it.next();
                     花莫.append(key).append(" 处理方式 : \n");
                     江.处理方式组 = 江.所有关键词对象.getJSONArray(key);
                     for (int i=0;i<=江.处理方式组.length()-1;i++) {
                        花莫.append(江.处理方式组.get(i).toString()+"\n\n");
                     }
                  }
                  花莫.delete(花莫.length()-1,花莫.length());
                  sendMsg(msg.GroupUin,"",花莫.toString());
                  江 = null;
              }
          }).start();
       }
       if (msg.MessageContent.equals("查看所有关键词")) {
          new Thread(new Runnable() {
              public void run() {
                  江云.至少还有你();
                  江云.刷新();
                  江云 江 = new 江云();
                  StringBuilder 花莫 = new StringBuilder();
                  File 路集 = new File(诗集);
                  for (File 千 : 路集.listFiles() ) {
                      if (千.getName().matches("\\d+\\.json")&&千.isFile()) {
                         江.所有关键词对象 = new JSONObject(秋诗.阅诗(诗集+千.getName()));
                         花莫.append("群 "+千.getName().replace(".json","") +": \n");
                         Iterator it = 江.所有关键词对象.keys();
                         while (it.hasNext()) {
                             String key = it.next();
                             花莫.append(key).append(" 处理方式 : \n");
                             江.处理方式组 = 江.所有关键词对象.getJSONArray(key);
                             for (int i=0;i<=江.处理方式组.length()-1;i++) {
                                 花莫.append(江.处理方式组.get(i).toString()+"\n");
                             }
                         }
                      }
                  }
                  花莫.delete(花莫.length()-1,花莫.length());
                  sendMsg(msg.GroupUin,"",花莫.toString());
                  江 = null;
              }
          }).start();
       }
       if (msg.MessageContent.equals("清空所有关键词")) {
          new Thread(new Runnable() {
              public void run() {
                  File 路集 = new File(诗集);
                  for (File 千 : 路集.listFiles() ) {
                      if (千.getName().matches("\\d+\\.json")&&千.isFile()) {
                         秋诗.写诗(千.getAbsolutePath(),"{}");
                      }
                  }
                  路集 = null;
                  江云.刷新();
                  sendMsg(msg.GroupUin,"","所有群关键词已清空");
              }
          }).start();          
       }
       if (msg.MessageContent.equals("清空关键词")) {
          new Thread(new Runnable() {
              public void run() {
                  江云 江 = new 江云();
                  江.所有关键词对象 = new JSONObject();
                  秋诗.写诗(诗集+msg.GroupUin+".json",江.所有关键词对象.toString());
                  sendMsg(msg.GroupUin,"","本群所有关键词已清空");
                  江云.刷新();
                  江 = null;
              }
          }).start();
       }
    }
    if (江云.时) {
        if (江云.静态双列总对象.containsKey(msg.GroupUin)&& (!msg.UserUin.equals(MyUin) || getString("关键词","己") != null )) {
            江云.组词列表 = new JSONObject(江云.静态双列总对象.get(msg.GroupUin));
            Iterator it = 江云.组词列表.keys();
            while (it.hasNext()) {
                String key = it.next();
                if (msg.MessageContent.contains(key)){
                    解析处理方式(key,msg);
                }
                else if (getString("正则表达式",msg.GroupUin)!=null && msg.MessageContent.matches(key) ) {
                    解析处理方式(key,msg);
                }
            }
        }
    }
}

public static void 解析处理方式(String text,Object msg) {
    new Thread(new Runnable() {
        public void run() {
            江云.处理列表 = 江云.组词列表.getJSONArray(text);
            for (int i=0;i<=江云.处理列表.length()-1;i++) {
                Object 处理方式 = 江云.处理列表.get(i);
                if (处理方式 instanceof JSONObject) {
                    if (!处理方式.isNull("回复")) {
                        String content = (String)处理方式.get("回复");
                        content = content.replace("AtQQ","[AtQQ="+msg.UserUin+"]");
                        content = content.replace("qun",msg.GroupUin);
                        sendMsg(msg.GroupUin,"",content);
                   }
                   if (!处理方式.isNull("回")) {
                        String content = (String)处理方式.get("回");
                        content = content.replace("AtQQ","[AtQQ="+msg.UserUin+"]");
                        content = content.replace("qun",msg.GroupUin);
                        sendReply(msg.GroupUin,msg,content);
                   }
                   if (!处理方式.isNull("禁言")) {
                        Forbidden(msg.GroupUin,msg.UserUin,(int)处理方式.get("禁言"));
                   }
                   if (!处理方式.isNull("延迟")) {
                        Thread.sleep( (long) 处理方式.get("延迟") );
                   }
                   if (!处理方式.isNull("踢")) {
                        Kick(msg.GroupUin,msg.UserUin,(boolean)处理方式.get("踢"));
                   }                
                }
                if (处理方式 instanceof String) {
                    if (处理方式.equals("撤回"))
                        revokeMsg(msg);
                    if (处理方式.equals("全体禁言"))
                        Forbidden(msg.GroupUin,"",1);
                    if (处理方式.equals("全体解禁"))
                        Forbidden(msg.GroupUin,"",0);
                }
            }
        }
    }).start();
}
AddItem("检测自己","关键词",PluginID);
public void 关键词(String s)
{
if(getString("关键词","己")==null)
	{
	putString("关键词", "己","开");
    Toast("已关闭屏蔽自己 会检测自己发送的关键词");
	}
	else{
	putString("关键词", "己",null);
	Toast("已开启屏蔽自己 不会检测自己发送的关键词");
	}
}

AddItem("正则表达式","正则表达式",PluginID);
public void 正则表达式(String s)
{
if(getString("正则表达式",s)==null)
	{
	putString("正则表达式",s,"开");
    Toast("已开启本群的正则表达式模式");
	}
	else{
	putString("正则表达式",s,null);
	Toast("已关闭本群的正则表达式模式");
	}
}
AddItem("开关加载提示","加载提示",PluginID);
public void 加载提示(String s)
{
if(getString("加载提示","开关")==null)
	{
	putString("加载提示","开关","关");
    Toast("已关闭加载提示");
	}
	else{
	putString("加载提示","开关",null);
	Toast("已开启加载提示");
	}
}
if (getString("加载提示","开关")==null)
Toast("发送 \"关键词\" 查看使用说明");


// 希望有人懂你的言外之意 更懂你的欲言又止.
// 我始终做不到说走就走.