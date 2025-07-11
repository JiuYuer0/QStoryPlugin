import android.os.Environment;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import javax.swing.Timer;



public void Mutualp(msg) {

String qun=msg.GroupUin;
	if("1".equals(getString(qun,"互赞")))
	  {
		if(msg.MessageContent.startsWith("赞我") || msg.MessageContent.startsWith("互赞"))
		{
			Calendar cl = Calendar.getInstance();
            String Day =cl.get(Calendar.YEAR)+"-"+ cl.get(Calendar.DAY_OF_YEAR);
            //Toast(Day);
			if(Day.equals(getString("QQ"+msg.UserUin,"最后点赞")))
			{
			//Toast("点赞重复");
			sendReply(qun,msg,"[AtQQ=" + msg.UserUin + "] 今天点过啦～明天再来呗…");
			}else
			{
				sendLike(msg.UserUin,20);
				sendLike(msg.UserUin,20);
				sendLike(msg.UserUin,10);
			    
			    



				// 这个是带图回复，因为怕消耗太多流量，就注释了 sendMsg(qun,null,"[AtQQ=" + msg.UserUin + "] 给你点赞50下了哦，记得回我~ [PicUrl=https://api.317ak.com/API/bqbjk/dianzan.php?qq=" + msg.UserUin + "]" );
				sendReply(qun,msg,"[AtQQ=" + msg.UserUin + "] 给你点赞50下了哦，记得回我~ ");
				//sendReply(msg.GroupUin,msg,send);
				putString("QQ"+msg.UserUin,"最后点赞",Day);
			}
		}
		}
}