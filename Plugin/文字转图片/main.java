load(AppPath+"/常用.java");

//控制是否开启
AddItem("开启/关闭 本群文转图","开关",PluginID);
public void 开关(String qun,String qq,int type)
{
       if(!"开".equals(getString(qun,"开关状态")))
       {
           putString(qun,"开关状态","开");
           Toast("已开启本群「文转图」功能");
       }
       else if("开".equals(getString(qun,"开关状态")))
       {
           putString(qun,"开关状态",null);
           Toast("已关闭本群「文转图」功能");
       }
}

//控制指令是否他人可用
AddItem("开启/关闭 他人可用","他人可用",PluginID);
public void 他人可用(String qun,String qq,int type)
{
       if(!"开".equals(getString(qun,"他人可用")))
       {
           putString(qun,"他人可用","开");
           Toast("开启成功，本群其他人也可以使用指令");
       }
       else if("开".equals(getString(qun,"他人可用")))
       {
           putString(qun,"他人可用",null);
           Toast("关闭成功，仅限自己可使用指令");
       }
}

public void onMsg(Object data)
{
    String 消息=data.MessageContent;
    String qun=data.GroupUin; 
    String uin =data.UserUin;
  
   if(消息.startsWith("转图"))
   {
     if("开".equals(getString(qun,"开关状态")))
     {
       if(uin.equals(MyUin)||"开".equals(getString(qun,"他人可用")))
       {
           String one = 消息.split("#")[1]; //颜色
           String two = 消息.split("#")[2]; //文本大小
           int size = Integer.parseInt(two);
           String three = 消息.split("#")[3]; //文本内容
           toImg(three,one,0.5,0.02,size,AppPath+"/图片缓存/"+uin+"_text_image.png");
           //toImg(文本，颜色，x，y，字体大小，图片保存路径) ↑
           sendMsg(qun,"","[PicUrl="+AppPath+"/图片缓存/"+uin+"_text_image.png]");
       }  
    }else if(uin.equals(MyUin)){
      Toast("本群未开启「文转图」！\n请在悬浮窗内点击开启");
    }
  }

}