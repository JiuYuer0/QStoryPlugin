import java.text.SimpleDateFormat;
import java.util.Date;
//获取当前日期
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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

  //删除指定文件夹内 文件
	public static void delAllFile(String path)
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				flag = true;
			}
		}
		return;
	}


//文本转图片 (图片宽高由文本及字体大小决定)
public void toImg(String text, String color, double x, double y, int size, String path)
{
        // 创建字体对象
        Paint pt = new Paint();
        pt.setTextSize(size);
        pt.setTypeface(Typeface.SERIF);
        pt.setAntiAlias(true); // 抗锯齿

        // 按换行符 \n 分割文本
        String[] lines = text.split("\n");

        // 计算文本行高（假设每行高度相同）
        float lineHeight = pt.descent() - pt.ascent();
        // 计算文本总高度，按照 行数 * 高 计算
        float totalHeight = lines.length * lineHeight;

        // 计算文本总宽度（取最长行的宽度）
        float maxWidth = 0;
        for (String line : lines) {
            float lineWidth = pt.measureText(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
        }

        // 增加额外的宽度和高度空间，让图片更合理
        float extraWidth = 50; // 额外宽度
        float extraHeight = 20; // 额外高度
        maxWidth += extraWidth;
        totalHeight += extraHeight;

        // 根据文字尺寸创建实际的 Bitmap 和 Canvas，背景设置为白色
        Bitmap bmp = Bitmap.createBitmap((int) maxWidth, (int) totalHeight, Bitmap.Config.ARGB_8888);
        Canvas cas = new Canvas(bmp);
        cas.drawColor(Color.WHITE); // 设置背景为白色

        // 计算起始绘制位置
        float startX = (float) (x * bmp.getWidth() - 0.5 * maxWidth + extraWidth / 2);
        float startY = (float) (y * bmp.getHeight() + lineHeight - extraHeight / 3);
        
        // 逐行绘制文本
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            Paint currentPaint = new Paint(pt); // 复制当前画笔

            // 判断行内容是否为 "<填充>"
            if ("<填充>".equals(line))
            {
                line = "-";
                // 计算需要重复多少次 "-" 才能填满一行
                float availableWidth = maxWidth - extraWidth;
                float ellipsisWidth = pt.measureText("-");
                int repeatCount = (int) (availableWidth / ellipsisWidth);
                StringBuilder filledLine = new StringBuilder();
                for (int j = 0; j < repeatCount; j++) {
                    filledLine.append("-");
                }
                line = filledLine.toString();
                currentPaint.setColor(getValidColor("#"+color));
            } 
            // 判断行中是否包含 "//"
            else if (line.contains("//"))
            {
                int commentIndex = line.indexOf("//");
                // 绘制 "//" 之前的文本
                String prefix = line.substring(0, commentIndex);
                float prefixX = startX;
                float prefixY = startY + i * lineHeight;
                currentPaint.setColor(getValidColor("#"+color));
                cas.drawText(prefix, prefixX, prefixY, currentPaint);
            
                // 获取当前文本大小
                float originalTextSize = currentPaint.getTextSize();
            
                // 绘制 "//" 及之后的文本
                String commentPart = line.substring(commentIndex);
                float commentX = prefixX + pt.measureText(prefix);
                float commentY = prefixY;
            
                // 设置文本倾斜，这里设置倾斜-0.2f
                currentPaint.setTextSkewX(-0.2f); 
                // 缩小文本大小，这里设置为原文本大小的0.8倍
                currentPaint.setTextSize(originalTextSize * 0.8f); 
                currentPaint.setColor(Color.GRAY);
                cas.drawText(commentPart, commentX, commentY, currentPaint);
            
                // 恢复原来的文本大小
                currentPaint.setTextSize(originalTextSize);
                continue;
            }
            // 判断行中是否包含 "*"
            else if (line.contains("*"))
            {
                int startIndex = line.indexOf("*");
                int endIndex = line.lastIndexOf("*");
                if (startIndex != endIndex) {
                    // 绘制 "*" 号之前的文本
                    String prefix = line.substring(0, startIndex);
                    float prefixX = startX;
                    float prefixY = startY + i * lineHeight;
                    currentPaint.setColor(getValidColor("#"+color));
                    cas.drawText(prefix, prefixX, prefixY, currentPaint);

                    // 绘制 "*" 号之间的文本
                    String middle = line.substring(startIndex+1, endIndex);
                    float middleX = prefixX + pt.measureText(prefix);
                    float middleY = prefixY;
                    currentPaint.setColor(Color.RED);
                    cas.drawText(middle, middleX, middleY, currentPaint);

                    // 绘制 "*" 号之后的文本
                    String suffix = line.substring(endIndex + 1);
                    float suffixX = middleX + pt.measureText(middle);
                    float suffixY = middleY;
                    currentPaint.setColor(getValidColor("#"+color));
                    cas.drawText(suffix, suffixX, suffixY, currentPaint);
                    continue;
                }
            }
            else {
                currentPaint.setColor(getValidColor("#"+color));
            }

            float lineX = startX;
            float lineY = startY + i * lineHeight;
            cas.drawText(line, lineX, lineY, currentPaint);
        }

      //路径为空 或 未提供路径 时，在默认路径保存为指定文件名
        if ("".equals(path) || path == null) {
            bmptofile(bmp,AppPath+"/图片缓存/text_image.png");
        } else {
            bmptofile(bmp, path);
        }
 }

    // 获取有效的颜色值，如果颜色值无效则返回默认颜色 "#000000"
    private int getValidColor(String color)
    {
        try {
            if (color == null || color.isEmpty()) {
                return Color.parseColor("#000000");
            }
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            return Color.parseColor("#000000");
        }
    }

public void bmptofile(Bitmap bmp,String path)
{
  File f= new File(path);
  if(f.exists()){f.delete();}
  if(!f.exists()){f.getParentFile().mkdirs();}
  FileOutputStream
  fs = new FileOutputStream(path);
  bmp.compress(Bitmap.CompressFormat.PNG,100,fs);
  fs.flush();
}


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

// 下载东西(链接，保存路径)
    private static boolean downloadFile(String urlStr, String localFilePath)
    {
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer))!= -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast("文件下载失败：" + e );
            sendMsg("", MyUin, "文件下载失败：" +e);
            return false;
        }
    }

//加载时，删除图片缓存…
delAllFile(AppPath+"/图片缓存/");