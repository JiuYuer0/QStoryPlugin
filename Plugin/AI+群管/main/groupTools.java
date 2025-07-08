import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.mobileqq.troop.handler.TroopMngHandler;
import com.tencent.common.app.BaseApplicationImpl;
import java.lang.reflect.Method;
public void deleteTroop(String group){
Object app = BaseApplicationImpl.getApplication().getRuntime();
ITroopInfoService Info = app.getRuntimeService(ITroopInfoService.class);
 Info.deleteTroop(group);
 }
public void disbandGroup(String str,boolean z){//解散群
Object app = BaseApplicationImpl.getApplication().getRuntime();
Method method;
Class clazz = TroopMngHandler.class;
Class[] ParamTYPE = new Class[]{String.class,boolean.class};
Method[] methods = clazz.getDeclaredMethods();
TroopMngHandler Handler=new TroopMngHandler(app);
for (Method m : methods) {
    if (m.getParameterCount() == ParamTYPE.length ) {
        Class[] params = m.getParameterTypes();
        boolean match = true;
        for (int i = 0; i < ParamTYPE.length; i++) {
            if (!params[i].equals(ParamTYPE[i])) {
                match = false;
                break;
            }
        }
        if (match) {
            // 调用匹配的方法
            m.setAccessible(true);
            m.invoke(Handler,new Object[]{str,z});
            break; // 找到匹配的方法后，跳出循环
        }
    }
}
}
//disbandGroup("700842735",false);