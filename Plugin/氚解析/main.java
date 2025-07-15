/**
 * Source: 氚-Tritium 2957148920
 * OpenSource: Apache License Version
 * 
 * 看到这个的您，应该是其他脚本开发者或者是睿智的用户
 * 如果您是用户，海纳保障您与开发者的所有利益
 * 如果您是开发者，本代码依据开放源许可许可证 Apache License Version 公开代码
 * 允许您在协议规定范围内进行修改、分发与传播
 * 但也请保证您的代码不会危害他人设备安全并依据许可证协议声明版权所属 Copyright © 2020-2024 HainaCloud. All Rights Reserved.
 * 
 * 群特供版本不允许以任何形式修改/分发与传播
 * 违反任意一条者，请您离开 TrackScript 
 *
 * 您也可以加咱的群，与我们一起交流 
 * 不过呐，咱的群比较冷清，别介意哈
 *
 * TrackScript（473229996）脚本群
 * OCSS（174602848）OCSS群
 * PixivTracker（306058479）美图群
 *
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 * TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
 *
 * 1. Definitions."License" shall mean the terms and conditions for use, reproduction, and distribution as defined by Sections 1 through 9 of this document."Licensor" shall mean the copyright owner or entity authorized by the copyright owner that is granting the License."Legal Entity" shall mean the union of the acting entity and all other entities that control, are controlled by, or are under common control with that entity. For the purposes of this definition, "control" means (i) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity."You" (or "Your") shall mean an individual or Legal Entity exercising permissions granted by this License."Source" form shall mean the preferred form for making modifications, including but not limited to software source code, documentation source, and configuration files."Object" form shall mean any form resulting from mechanical transformation or translation of a Source form, including but not limited to compiled object code, generated documentation, and conversions to other media types."Work" shall mean the work of authorship, whether in Source or Object form, made available under the License, as indicated by a copyright notice that is included in or attached to the work (an example is provided in the Appendix below)."Derivative Works" shall mean any work, whether in Source or Object form, that is based on (or derived from) the Work and for which the editorial revisions, annotations, elaborations, or other modifications represent, as a whole, an original work of authorship. For the purposes of this License, Derivative Works shall not include works that remain separable from, or merely link (or bind by name) to the interfaces of, the Work and Derivative Works thereof."Contribution" shall mean any work of authorship, including the original version of the Work and any modifications or additions to that Work or Derivative Works thereof, that is intentionally submitted to Licensor for inclusion in the Work by the copyright owner or by an individual or Legal Entity authorized to submit on behalf of the copyright owner. For the purposes of this definition, "submitted" means any form of electronic, verbal, or written communication sent to the Licensor or its representatives, including but not limited to communication on electronic mailing lists, source code control systems, and issue tracking systems that are managed by, or on behalf of, the Licensor for the purpose of discussing and improving the Work, but excluding communication that is conspicuously marked or otherwise designated in writing by the copyright owner as "Not a Contribution.""Contributor" shall mean Licensor and any individual or Legal Entity on behalf of whom a Contribution has been received by Licensor and subsequently incorporated within the Work.
 *
 * 2. Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute the Work and such Derivative Works in Source or Object form.
 * 
 * 3. Grant of Patent License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer the Work, where such license applies only to those patent claims licensable by such Contributor that are necessarily infringed by their Contribution(s) alone or by combination of their Contribution(s) with the Work to which such Contribution(s) was submitted. If You institute patent litigation against any entity (including a cross-claim or counterclaim in a lawsuit) alleging that the Work or a Contribution incorporated within the Work constitutes direct or contributory patent infringement, then any patent licenses granted to You under this License for that Work shall terminate as of the date such litigation is filed.4. Redistribution. You may reproduce and distribute copies of the Work or Derivative Works thereof in any medium, with or without modifications, and in Source or Object form, provided that You meet the following conditions:You must give any other recipients of the Work or Derivative Works a copy of this License; andYou must cause any modified files to carry prominent notices stating that You changed the files; andYou must retain, in the Source form of any Derivative Works that You distribute, all copyright, patent, trademark, and attribution notices from the Source form of the Work, excluding those notices that do not pertain to any part of the Derivative Works; andIf the Work includes a "NOTICE" text file as part of its distribution, then any Derivative Works that You distribute must include a readable copy of the attribution notices contained within such NOTICE file, excluding those notices that do not pertain to any part of the Derivative Works, in at least one of the following places: within a NOTICE text file distributed as part of the Derivative Works; within the Source form or documentation, if provided along with the Derivative Works; or, within a display generated by the Derivative Works, if and wherever such third-party notices normally appear. The contents of the NOTICE file are for informational purposes only and do not modify the License. You may add Your own attribution notices within Derivative Works that You distribute, alongside or as an addendum to the NOTICE text from the Work, provided that such additional attribution notices cannot be construed as modifying the License.You may add Your own copyright statement to Your modifications and may provide additional or different license terms and conditions for use, reproduction, or distribution of Your modifications, or for any such Derivative Works as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 * 
 * 5. Submission of Contributions. Unless You explicitly state otherwise, any Contribution intentionally submitted for inclusion in the Work by You to the Licensor shall be under the terms and conditions of this License, without any additional terms or conditions. Notwithstanding the above, nothing herein shall supersede or modify the terms of any separate license agreement you may have executed with Licensor regarding such Contributions.
 * 
 * 6. Trademarks. This License does not grant permission to use the trade names, trademarks, service marks, or product names of the Licensor, except as required for reasonable and customary use in describing the origin of the Work and reproducing the content of the NOTICE file.
 * 
 * 7. Disclaimer of Warranty. Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.
 * 
 * 8. Limitation of Liability. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall any Contributor be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the Work (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if such Contributor has been advised of the possibility of such damages.
 * 
 * 9. Accepting Warranty or Additional Liability. While redistributing the Work or Derivative Works thereof, You may choose to offer, and charge a fee for, acceptance of support, warranty, indemnity, or other liability obligations and/or rights consistent with this License. However, in accepting such obligations, You may act only on Your own behalf and on Your sole responsibility, not on behalf of any other Contributor, and only if You agree to indemnify, defend, and hold each Contributor harmless for any liability incurred by, or claims asserted against, such Contributor by reason of your accepting any such warranty or additional liability.
 *
 * END OF TERMS AND CONDITIONS
 *
 */

//导入所需类和函数
load(AppPath + "/java/imports.java");
load(AppPath + "/java/MsgUtil.java");
load(AppPath + "/java/Http.java");
load(AppPath + "/java/Message.java");
load(AppPath + "/java/modules.java");
load(AppPath + "/java/function.java");


public void onMsg(Object data) {
    //测试函数
    if(data.MessageContent.equals("#校验")){      
	    if(data.MessageType == 6){
            
        }                
    }
    if(data.MessageContent.startsWith("#测试")){

    }
    if(MsgUtil.isMe(data.UserUin)){
        //Message.sendMsgs(data, data.MessageContent.substring(1));    
    }
    
    if(data.MessageType == 2){
        //Message.sendMsgs(data, data.MessageContent);
    }
    
    // 全局功能
    if(MsgUtil.isMe(data.UserUin)){
        //命令激活脚本（测试用功能
        if(data.MessageContent.equals("#激活解析")){
            if(!getBoolean("settings", "functionMain", false)){
                Message.Toast(2, "脚本已激活");
                putBoolean("settings", "functionMain", true);
            }else{
                Message.Toast(2, "脚本已关闭");
                putBoolean("settings", "functionMain", false);
            }
        }
        if(data.MessageContent.equals("#转移解析缓存")){
            MsgUtil.rebulidCacheData(data);
        }
        if(data.MessageContent.startsWith("#查看缓存信息")){
            //截取参数
            String value = data.MessageContent.substring(7);
            //移除空格
            while(value.startsWith(" ")){
                value = value.substring(1);
            }
            //判断一下有没有参数
            if(value.isEmpty()){
                Message.sendMsgs(data, "输入的参数不合法");
                return;
            }
            MsgUtil.toViewCacheInformation(data, value);
            revokeMsg(data);
        }
        //发送菜单
        if(data.MessageContent.equals("#使用方法")){
            dialogMenu(null);
            //sendPics(data, AppPath + "/img/menu.png");
        }  
        if(data.MessageContent.equals("#文字菜单")){
            Message.sendMsgs(data,  "## 讲在开始之前\n\n我们是基于一个强大的短视频解析脚本的编写，支持80多个主流短视频去水印和常用图集解析，具体的请自行测试（我们不是解析工具，我们只是接口的调用者）\ntips：长按聊天界面右下角加号，激活脚本功能。\n\n使用方法简介\n #使用方法 获取使用功能菜单\n#自动解析 发送卡片后自动解析视频\n#自动撤回 自动撤回发送的解析命令\n#自动撤回卡片 自动撤回发送的解析卡片\n#逐条发送 解析得到的图集逐条发送图片\n#返回视频 解析后发送视频\n#更新日志 查看脚本历史版本的更新日志\n#解析卡片链接 自动解析解析卡片链接\n#自动清理临时文件 临时文件自动清理\n#解析链接+视频分享地址纯链接\n - 支持80多个短视频/图集去水印和解析\n - 理论上是 啥都可以解析 请自行测试\n - # 号和命令之间不允许有空格\n - 命令和链接之间可以有空格\n\n\ntips：聊天界面悬浮球打开脚本菜单，激活脚本功能和使用方法；也就是说基于该api的功能，咱们的脚本拥有几乎解析所有短视频链接的能力\n\n\n\nps：有问题请在右下角加号长按按钮加群反馈，我们会尽快修复");
        }
        //卡片自动解析
        if(data.MessageContent.equals("#自动解析")){
            if(getBoolean("settings", "autoAnalysis", false)){
                Message.sendMsgs(data, "自动卡片解析已关闭");
                putBoolean("settings", "autoAnalysis", false);
            }else{
                Message.sendMsgs(data, "自动卡片解析已激活");
                putBoolean("settings", "autoAnalysis", true);
            }
            //提示信息
            Message.Toast(2, "设置成功");
        }
        //视频解析返回卡片
        if(data.MessageContent.equals("#逐条发送")){
            if(getBoolean("settings", "itemPic", false)){
                Message.sendMsgs(data, "解析图集逐条发送已关闭");
                putBoolean("settings", "itemPic", false);
            }else{
                Message.sendMsgs(data, "解析图集逐条发送已激活");
                putBoolean("settings", "itemPic", true);
            }
            //提示信息
            Message.Toast(2, "设置成功");
        }
        //视频解析返回卡片
        if(data.MessageContent.equals("#返回视频")){
            if(getBoolean("settings", "sendVideos", true)){
                //关闭返回视频功能
                Message.sendMsgs(data, "解析后自动发送视频已关闭");
                putBoolean("settings", "sendVideos", false);
                //自动打开返回卡片
                if(!getBoolean("settings", "returnCard", false)){
                    //Toast("返回卡片自动激活");
                    //putBoolean("settings", "returnCard", true);
                }
            }else{
                //打开返回视频功能
                Message.sendMsgs(data, "解析后自动发送视频已激活");
                putBoolean("settings", "sendVideos", true);
                //自动关闭返回卡片
                if(getBoolean("settings", "returnCard", false)){
                    //Toast("返回卡片自动关闭");
                    //putBoolean("settings", "returnCard", false);
                }
            }
            //提示信息
            Message.Toast(2, "设置成功");
        }
        //自动撤回命令
        if(data.MessageContent.equals("#自动撤回")){
            if(getBoolean("settings", "autoRecallMsg", false)){
                Message.sendMsgs(data, "自动撤回解析命令已关闭");
                putBoolean("settings", "autoRecallMsg", false);
            }else{
                Message.sendMsgs(data, "自动撤回解析命令已激活");
                putBoolean("settings", "autoRecallMsg", true);
            }
            //提示信息
            Message.Toast(2, "设置成功");
        } 
        //解析卡片链接
        if(data.MessageContent.equals("#解析卡片链接")){
            if(getBoolean("settings", "analysisUrl", false)){
                Message.sendMsgs(data, "解析卡片链接已关闭");
                putBoolean("settings", "analysisUrl", false);
            }else{
                Message.sendMsgs(data, "解析卡片链接已激活");
                putBoolean("settings", "analysisUrl", true);
            }
            //提示信息
            Message.Toast(2, "设置成功");
        }
        //自动撤回命令
        if(data.MessageContent.equals("#自动撤回卡片")){
            if(getBoolean("settings", "autoRecallCard", false)){
                Message.sendMsgs(data, "自动撤回需解析卡片已关闭");
                putBoolean("settings", "autoRecallCard", false);
            }else{
                Message.sendMsgs(data, "自动撤回需解析卡片已激活");
                putBoolean("settings", "autoRecallCard", true);
            }
            //提示信息
            Message.Toast(2, "设置成功");
        } 
        //自动撤回命令
        if(data.MessageContent.equals("#自动清理临时文件")){
            if(getBoolean("settings", "removeTempFiles", true)){
                //获取当前活动
                Activity ThisActivity= GetActivity();
	            // 实例化对话框对象
	            ThisActivity.runOnUiThread(new Runnable(){
	                public void run() {
		                AlertDialog alertDialog = new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		                alertDialog.setTitle("警告");
		                alertDialog.setMessage("接下来的操作将会关闭临时文件自动删除功能，关闭后因解析视频等功能，产生的临时视频文件将不会自动删除\n\n您确定继续吗？\n\ntips：关闭后，请您定期处理文件，避免文件堆积浪费存储空间，临时文件存放目录为 /storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/氚解析/temp/");
		                alertDialog.setCancelable(false);
		                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {
                                Message.sendMsgs(data, "自动清理临时文件已关闭");
                                putBoolean("settings", "removeTempFiles", false);
                                Message.Toast(2, "操作成功");
	    	                }
		                });
		                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "我再想想", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {
                                Message.Toast(2, "操作已取消");
	    	                }
		                });
	                    alertDialog.show();
    	            }
	            });                
            }else{
                Message.sendMsgs(data, "自动清理临时文件已激活");
                putBoolean("settings", "removeTempFiles", true);
            }
        }
        //查看更新日志
        if(data.MessageContent.equals("#更新日志")){
            String title = "更新日志";
            String content = getStringOfFiles(AppPath +"/update.log");
            String negativeBtn = "确定";
            String tipContent = "不是吧，这玩意儿也有人看么";
            Message.dialog(title, content, tipContent, negativeBtn, false);
        }
        //查看更新日志
        if(data.MessageContent.equals("#错误日志")){
            String title = "错误日志";
            String content = getStringOfFiles(AppPath +"/error.txt");
            String negativeBtn = "确定";
            String tipContent = "不是吧，这玩意儿也有人看么";
            //判断一下文件是否存在
            if(content == "FileNotFoundException"){
                Message.Toast(1,"没有错误日志");
            }else{
                Message.dialog(title, content, tipContent, negativeBtn, false);
            }
        }        
    }
    
    
    // 解析功能
    if(getBoolean("settings", "functionMain", false)){
        if(MsgUtil.isMe(data.UserUin)){
            //解析视频
            analysisVideos(data); 
            //是否需要解析卡片链接 
            if(getBoolean("settings", "analysisUrl", false)){
                analysisUrlOfCard(data);
            }      
        }else{
            if(getBoolean("functionPeer", data.GroupUin, false)){
               //解析视频
               analysisVideos(data); 
               //是否需要解析卡片链接 
               if(getBoolean("settings", "analysisUrl", false)){
                   analysisUrlOfCard(data);
               }
            }       
        }
    }
}

//本核心函数全局下执行
if (MsgUtil.isStartsUse()){
    //第一次使用提示一下
    dialogMenu(null);
    //发送声明给自己
    sendMsg("", MyUin,  "## 作为用户，当您下载并激活本脚本即刻起，就默认您接受由脚本开发者编写的所有协议与内容，并愿意承担脚本可能出现问题（若您不接受相关规定，您可以当即卸载本脚本）以下是海纳相关协议，也同样适用于本脚本\n\n海纳云用户协议\nhttps://api.hainacloud.cc/protocol/User.html\n\n海纳云隐私协议\nhttps://api.hainacloud.cc/protocol/Yinshi.html\n\n海纳云版权声明\nhttps://api.hainacloud.cc/protocol/Copyright.html\n\n海纳云未成年人保护协议\nhttps://api.hainacloud.cc/protocol/protection.html\n\n如果您作为其他脚本开发者，您还需要严格遵守开源协议 Apache License Version 进行修改分发，您二改造成的所有效果，开发者概不负责");
    putBoolean("settings", "isStartsUse", false); 
}else{
    //测试一下功能
    //dialogMenu(null);
}



//添加菜单项目
AddItem("加入交流群", "joinGroup", PluginID);
AddItem("开/关解析", "functionMain", PluginID);
AddItem("开/关群权限", "functionPeer", PluginID);        
AddItem("使用方法简介", "dialogMenu", PluginID);
AddItem("清理解析缓存", "functionCache", PluginID);
