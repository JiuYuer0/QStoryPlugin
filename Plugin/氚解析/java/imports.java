import java.util.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

import java.lang.*;
import java.lang.Thread;
import java.lang.StringBuilder;
import java.lang.Runnable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.text.SimpleDateFormat;

import java.net.URLEncoder;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.tencent.mobileqq.app.BaseActivity;
import com.tencent.mobileqq.jump.api.IJumpApi;
import com.tencent.mobileqq.qroute.QRouteApi;
import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.mobileqq.activity.shortvideo.SendVideoTask;
import com.tencent.util.QQToastUtil;
import com.tencent.qqnt.msg.api.IMsgUtilApi;
import com.tencent.qqnt.kernel.nativeinterface.MsgElement;
import com.tencent.qqnt.kernel.nativeinterface.Contact;
import com.tencent.qqnt.kernel.nativeinterface.MsgElement;
import com.tencent.qqnt.kernel.nativeinterface.TextElement;
import com.tencent.qqnt.kernel.nativeinterface.PicElement;
import com.tencent.qqnt.kernel.nativeinterface.VideoElement;
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord;
import com.tencent.qqnt.kernel.nativeinterface.IOperateCallback;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.qqnt.kernel.nativeinterface.PttElement;

import android.app.Dialog;
import android.app.Activity;
import android.content.Intent;
import android.content.DialogInterface;
import android.app.AlertDialog;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AlertDialog;

import java.net.HttpURLConnection;
import lin.xposed.hook.util.qq.QQNTSendMsgUtils;
