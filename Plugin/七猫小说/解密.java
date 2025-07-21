import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SimpleApiUrlBuilder {
private static final String SIGN_KEY = "d3dGiJc651gSQ8w1";
public static String buildUrl(String id, String chapterId) {
String baseUrl;
Map params = new TreeMap();
params.put("id", id);
if (chapterId == null || chapterId.isEmpty()) {
baseUrl = "https://api-bc.wtzw.com/api/v4/book/detail";
params.put("imei_ip", "2937357107");
params.put("teeny_mode", "0");
} else {
baseUrl = "https://api-ks.wtzw.com/api/v1/chapter/content";
params.put("chapterId", chapterId);
}
String sign = generateSign(params, SIGN_KEY);
params.put("sign", sign);
return baseUrl + "?" + urlEncode(params);
}
private static String generateSign(Map params, String signKey) {
StringBuilder paramStr = new StringBuilder();
for (Map.Entry entry : params.entrySet()) {
paramStr.append(entry.getKey()).append("=").append(entry.getValue());
}
paramStr.append(signKey);
return md5Encode(paramStr.toString());
}
private static String urlEncode(Map params) {
StringBuilder encodedStr = new StringBuilder();
for (Map.Entry entry : params.entrySet()) {
try {
if (encodedStr.length() > 0) {
encodedStr.append("&");
}
encodedStr.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
} catch (UnsupportedEncodingException e) {
Toast(e);
}
}
return encodedStr.toString();
}
private static String md5Encode(String input) {
try {
MessageDigest md = MessageDigest.getInstance("MD5");
byte[] messageDigest = md.digest(input.getBytes());
StringBuilder hexString = new StringBuilder();
for (byte b : messageDigest) {
String hex = Integer.toHexString(0xFF & b);
if (hex.length() == 1) {
hexString.append('0');
}
hexString.append(hex);
}
return hexString.toString();
} catch (NoSuchAlgorithmException e) {
throw new RuntimeException("MD5加密失败", e);
}
}

} 


public static String Aesdecrypt(String origin) throws Exception {
byte[] txt = Base64.getDecoder().decode(origin);

byte[] iv = new byte[16];
System.arraycopy(txt, 0, iv, 0, 16);
byte[] data = new byte[txt.length - 16];
System.arraycopy(txt, 16, data, 0, data.length);
String keyHex = "32343263636238323330643730396531";
byte[] dkey = hexStringToByteArray(keyHex);
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
SecretKeySpec secretKey = new SecretKeySpec(dkey, "AES");
IvParameterSpec ivSpec = new IvParameterSpec(iv);
cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
byte[] decrypted = cipher.doFinal(data);
return new String(decrypted, "UTF-8").trim();
}
public static byte[] hexStringToByteArray(String s) {
int len = s.length();
byte[] data = new byte[len / 2];
for (int i = 0; i < len; i += 2) {
data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
+ Character.digit(s.charAt(i+1), 16));
}
return data;
}

  
