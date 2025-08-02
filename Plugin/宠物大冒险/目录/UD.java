// 此文件平时不加载，仅在脚本目录不存在图片资源时加载调用（用于下载多个资源压缩包）
// 支持批量处理多个≤10MB的资源包，资源包链接来自gitee.com

// 这里的方法稍微改改，好像可以作为脚本自动更新的方法（喜）

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// 批量下载资源包（返回成功下载的数量）
public int batchDownload(String[] urls, String[] savePaths) {
    if (urls.length != savePaths.length) {
        return -1; // 链接与保存路径数量不匹配
    }
    int successCount = 0;
    for (int i = 0; i < urls.length; i++) {
        String result = RD(urls[i], savePaths[i]);
        if ("true".equals(result)) {
            successCount++;
        }
    }
    return successCount;
}

// 单个文件下载（兼容批量调用）
public String RD(String fileUrl, String savePath) {
    // 确保保存目录存在
    File saveFile = new File(savePath);
    if (!saveFile.getParentFile().exists()) {
        boolean mkdirsSuccess = saveFile.getParentFile().mkdirs();
        if (!mkdirsSuccess) {
            return "创建保存目录失败：" + savePath;
        }
    }

    try {
        InputStream inputStream = new URL(fileUrl).openStream();
        FileOutputStream fileOutputStream = new FileOutputStream(savePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        fileOutputStream.close();
        return "true"; // 成功
    } catch (MalformedURLException e) {
        return "URL格式错误: " + e.getMessage();
    } catch (IOException e) {
        return "下载失败: " + e.getMessage();
    }
}

// 批量解压（返回成功解压的数量）
public int batchUnzip(String[] zipFilePaths, String destDir) throws Exception {
    int successCount = 0;
    for (String zipPath : zipFilePaths) {
        String result = unzip(zipPath, destDir);
        if ("true".equals(result)) {
            successCount++;
        }
    }
    return successCount;
}

// 单个zip解压（兼容批量调用）
public String unzip(String zipFilePath, String destDir) throws Exception {
    File dir = new File(destDir);
    if (!dir.exists()) {
        dir.mkdirs();
    }
    
    FileInputStream fis = null;
    ZipInputStream zipIn = null;
    try {
        fis = new FileInputStream(zipFilePath);
        zipIn = new ZipInputStream(fis);
        ZipEntry entry = zipIn.getNextEntry();
        
        while (entry != null) {
            String filePath = destDir + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                createParentDirectory(filePath);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipIn.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            } else {
                File dirFile = new File(filePath);
                dirFile.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
    } finally {
        if (zipIn != null) {
            zipIn.close();
        }
        if (fis != null) {
            fis.close();
        }
    }
    return "true";
}

// 批量删除文件（返回成功删除的数量）
public int batchDelete(String[] filePaths) {
    int successCount = 0;
    for (String path : filePaths) {
        if (delete(path)) {
            successCount++;
        }
    }
    return successCount;
}

// 创建父目录
public void createParentDirectory(String filePath) {
    File file = new File(filePath);
    File parentDir = file.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
        parentDir.mkdirs();
    }
}

// 删除文件（单个文件）
public String deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
        return file.delete() ? "true" : "false";
    } else {
        return "false";
    }
}

// 读取文件
public static String readFile(String path) {
    FileInputStream fis = null;
    try {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return "false";
        }
        byte[] data = new byte[(int) file.length()];
        fis = new FileInputStream(file);
        fis.read(data);
        return new String(data, "UTF-8");
    } catch (IOException e) {
        return "false";
    } finally {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                // 忽略关闭异常
            }
        }
    }
}

// 删除文件（返回boolean类型）
public static boolean delete(String filePath) {
    File file = new File(filePath);
    return file.exists() && file.isFile() && file.delete();
}

// 写入文件内容
public static String writeFile(String content, String path) {
    BufferedWriter writer = null;
    try {
        File file = new File(path);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        writer = new BufferedWriter(new FileWriter(path));
        writer.write(content);
        return "true";
    } catch (IOException e) {
        return "false";
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                // 忽略关闭异常
            }
        }
    }
}



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

// 显示批量资源包下载提示对话框
public void showResourceDialog() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        createMainDialog();
    } else {
        ((Activity) GetActivity()).runOnUiThread(new Runnable() {
            public void run() {
                createMainDialog();
            }
        });
    }
}

// 创建主对话框（支持多个资源包下载）
private void createMainDialog() {
    Context context = GetActivity();
    // 资源包1：宠物图片资源包
    final String url1 = "https://gitee.com/ma-taiyuan/pet-adventure/raw/master/zipfile/%E8%B5%84%E6%BA%90%E5%8C%851.zip";
    // 资源包2：其他图片资源包（副本/神器/怪物等）
    final String url2 = "https://gitee.com/ma-taiyuan/pet-adventure/raw/master/zipfile/%E8%B5%84%E6%BA%90%E5%8C%852.zip";
    // 资源包3：音频资源包
    final String url3 = "https://gitee.com/ma-taiyuan/pet-adventure/raw/master/zipfile/%E8%B5%84%E6%BA%90%E5%8C%853.zip";

    // 存储所有资源包的链接和保存路径
    final String[] downloadUrls = {url1, url2, url3};
    final String destDir = AppPath + "/目录/"; //解压路径
    //保存路径（资源包下载后存放的路径）
    final String[] zipPaths = {
        AppPath + "/目录/资源包1.zip",
        AppPath + "/目录/资源包2.zip",
        AppPath + "/目录/资源包3.zip"
    };

    // 构建对话框内容
    TextView contentView = new TextView(context);
    contentView.setText("检测到指定目录缺少图片/音频资源！\n部分功能/指令可能会出错甚至无效，需下载资源压缩包（共" + downloadUrls.length + "个，每个≤10MB）并解压至脚本路径。\n\n点击“下载”将批量处理，完成后自动解压。\n若失败可复制下方链接到浏览器手动下载：\nhttps://wwva.lanzouq.com/b030pkmbad \n（密码:1vs6）\n\n"
        + "并自行解压至：\n" + destDir);
    contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    contentView.setPadding(20, 20, 20, 20);
    contentView.setGravity(Gravity.START);
    contentView.setTextIsSelectable(true);

    ScrollView scrollView = new ScrollView(context);
    scrollView.setPadding(10, 10, 10, 10);
    scrollView.addView(contentView);

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("资源压缩包待下载…")
           .setView(scrollView)
           .setPositiveButton("下载", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   showBatchProgressDialog(downloadUrls, zipPaths, destDir);
               }
           })
           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });

    AlertDialog dialog = builder.create();
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
}


// 显示批量处理进度对话框
private void showBatchProgressDialog(String[] urls, String[] zipPaths, String destDir) {
    final Context context = GetActivity();
    final Activity activity = (Activity) context;
    final int totalCount = urls.length;

    // 创建布局存放进度信息
    LinearLayout updateLayout = new LinearLayout(context);
    updateLayout.setOrientation(LinearLayout.VERTICAL);
    updateLayout.setPadding(20, 20, 20, 20);

    ScrollView scrollView = new ScrollView(context);
    scrollView.addView(updateLayout);

    // 创建进度对话框
    final AlertDialog dialog = new AlertDialog.Builder(context)
            .setTitle("批量处理中...")
            .setView(scrollView)
            .setCancelable(false)
            .create();
    dialog.show();

    // 开启子线程执行批量操作
    new Thread(new Runnable() {
        public void run() {
            // 1. 批量下载
            final TextView downloadTitle = new TextView(context);
            downloadTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            downloadTitle.setPadding(0, 10, 0, 5);
            downloadTitle.setText("=== 开始下载 " + totalCount + " 个资源包 ===\n 此过程可能耗时较长，请您耐心等待…");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    updateLayout.addView(downloadTitle);
                }
            });

            int downloadSuccess = batchDownload(urls, zipPaths);
            final TextView downloadResult = new TextView(context);
            downloadResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            downloadResult.setPadding(0, 5, 0, 5);
            downloadResult.setText("下载完成：成功" + downloadSuccess + "个，失败" + (totalCount - downloadSuccess) + "个");
            downloadResult.setTextColor(downloadSuccess == totalCount ? Color.parseColor("#00CC00") : Color.parseColor("#FF0000"));
            activity.runOnUiThread(new Runnable() {
                public void run() {
                   //添加控件到布局
                    updateLayout.addView(downloadResult);
                    //更新上个控件的文本
                    downloadTitle.setText("=== 开始下载 " + totalCount + " 个资源包 ===");
                }
            });

            // 2. 批量解压（仅下载成功的文件）
            int unzipSuccess = 0;
            if (downloadSuccess > 0) {
                final TextView unzipTitle = new TextView(context);
                unzipTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                unzipTitle.setPadding(0, 10, 0, 5);
                unzipTitle.setText("=== 开始解压 " + downloadSuccess + " 个资源包 ===\n此过程可能耗时较长，请您耐心等待…");
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        updateLayout.addView(unzipTitle);
                        //更新上个控件的文本内容
                        unzipTitle.setText("=== 开始解压 " + downloadSuccess + " 个资源包 ===");
                    }
                });

                try {
                    unzipSuccess = batchUnzip(zipPaths, destDir);
                } catch (Exception e) {
                    final TextView errorText = new TextView(context);
                    errorText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    errorText.setPadding(0, 5, 0, 5);
                    errorText.setText("解压异常：" + e.getMessage());
                    errorText.setTextColor(Color.parseColor("#FF0000"));
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            updateLayout.addView(errorText);
                        }
                    });
                }

                final TextView unzipResult = new TextView(context);
                unzipResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                unzipResult.setPadding(0, 5, 0, 5);
                unzipResult.setText("解压完成：成功" + unzipSuccess + "个，失败" + (downloadSuccess - unzipSuccess) + "个");
                unzipResult.setTextColor(unzipSuccess == downloadSuccess ? Color.parseColor("#00CC00") : Color.parseColor("#FF0000"));
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        updateLayout.addView(unzipResult);
                    }
                });
            }

            // 3. 批量删除压缩包（仅解压成功的）
            int deleteSuccess = 0;
            if (unzipSuccess > 0) {
                deleteSuccess = batchDelete(zipPaths);
                final TextView deleteResult = new TextView(context);
                deleteResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                deleteResult.setPadding(0, 5, 0, 5);
                deleteResult.setText("删除临时文件：成功" + deleteSuccess + "个，失败" + (unzipSuccess - deleteSuccess) + "个");
                deleteResult.setTextColor(deleteSuccess == unzipSuccess ? Color.parseColor("#00CC00") : Color.parseColor("#FF0000"));
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        updateLayout.addView(deleteResult);
                    }
                });
            }

            // 4. 显示最终结果并添加关闭按钮
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    TextView finalResult = new TextView(context);
                    finalResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    finalResult.setPadding(0, 10, 0, 5);
                    finalResult.setText("全部处理完成！");
                    finalResult.setTextColor(Color.parseColor("#66ccff"));
                    updateLayout.addView(finalResult);

                    Button closeButton = new Button(context);
                    closeButton.setText("关闭");
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.gravity = Gravity.CENTER;
                    params.topMargin = 20;
                    closeButton.setLayoutParams(params);
                    
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    updateLayout.addView(closeButton);
                }
            });
        }
    }).start();
}


//添加菜单入口
AddItem("下载资源压缩包", "XzZip");

// 菜单回调
void XzZip(String groupUin, String userUin, int chatType) {
        // 定义多个目录路径
        String[] dirs = {
            RootPath + "图片/宠物/",
            RootPath + "图片/其他/"
        };
        
      if (hasImageInAllDirs(dirs)&&hasAudioInSingleDir(AppPath+"/目录/音频/")) {
          // 所有目录都有图片/音频，无需处理
         tcToast("指定路径已存在资源文件，无需重复下载");
      } else {
        // 有某个目录无图片/音频时，弹出资源下载对话框
        tcToast("检测到资源缺失！\n请点击“下载”！");
        showResourceDialog(); //弹出对话框
      }
}


