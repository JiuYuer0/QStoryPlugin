
import java.io.*;
import java.lang.StringBuilder;
public class 秋诗 {

    private static FileWriter 逐笔 = null;
    private static BufferedWriter 点墨 = null;
    private static FileReader 阅景 = null;
    private static BufferedReader 默画 = null;
    
    public static void 写诗(String 冬集录,String 文) {
        File 思忆 = new File(冬集录);
        try {
            if (!思忆.exists()) {
               思忆.createNewFile();
            }
            //欲写故人所故事 故人却已不在
            //只因你我皆在风中 聚散不由你我
            逐笔 = new FileWriter(思忆);
            点墨 = new BufferedWriter(逐笔);
            点墨.write(文);
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (点墨 != null)
                   点墨.close();
                if (逐笔 != null)
                   逐笔.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }   
    public static String 阅诗(String 冬集录) {
        File 思忆 = new File(冬集录);
        StringBuilder 落尘 = new StringBuilder();
        try {
            if (!思忆.exists()) {
               思忆.createNewFile();
               写诗(思忆.getAbsolutePath(),"{}");
            }
            String 诗;
            //没办法 在无能为力的瞬间抹了太多眼泪.
            //只能看着回忆 再也找不到你
            阅景  =  new  FileReader(思忆);  
            默画 = new BufferedReader(阅景);            
            while ((诗 = 默画.readLine()) != null) {
                落尘.append(诗);
            }
        } catch(Exception e) {
        toast(""+e);
        e.printStackTrace();
        } finally {
            try {
                if (默画 != null)
                   默画.close();
                if (阅景 != null)
                   阅景.close();
            } catch(Exception e2) {
                e2.printStackTrace();
            }
        }
        return 落尘.toString();
    }
}
