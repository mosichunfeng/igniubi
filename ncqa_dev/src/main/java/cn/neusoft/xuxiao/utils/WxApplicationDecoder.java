package cn.neusoft.xuxiao.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class WxApplicationDecoder
{
  public static String[] parse(String code)
  {
    String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code&connect_redirect=1";

    String requestUrl = WX_URL.replace("APPID", "wx5aec76b2758db2eb")
      .replace("SECRET", "8170c813d57792f29745ad70189478f6")
      .replace("JSCODE", code)
      .replace("authorization_code", "authorization_code");

    String returnvalue = GET(requestUrl);

    JSONObject convertvalue = new JSONObject();
    System.out.println("ret value ==== >>>" + returnvalue);

    convertvalue = (JSONObject)JSON.parse(returnvalue);

    String openid = (String)convertvalue.get("openid");
    String sessionkey = (String)convertvalue.get("session_key");
    String[] ret = new String[2];
    ret[0] = openid;
    ret[1] = sessionkey;
    return ret;
  }
  public static String[] parse1(String code) {
    String appid = "wx5aec76b2758db2eb";
    String secret = "8170c813d57792f29745ad70189478f6";
    String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

    String oppid = GET(requestUrl);
    System.out.println("Info ===>>>" + oppid);
    String[] ret = new String[2];
    ret[0] = oppid;
    return ret;
  }

  public static String GET(String url)
  {
    String result = "";
    BufferedReader in = null;
    InputStream is = null;
    InputStreamReader isr = null;
    try {
      URL realUrl = new URL(url);
      URLConnection conn = realUrl.openConnection();
      conn.connect();
      Map map = conn.getHeaderFields();
      is = conn.getInputStream();
      isr = new InputStreamReader(is);
      in = new BufferedReader(isr);
      String line;
      while ((line = in.readLine()) != null)
        result = result + line;
    }
    catch (Exception e2) {
    }
    finally {
      try {
        if (in != null) {
          in.close();
        }
        if (is != null) {
          is.close();
        }
        if (isr != null)
          isr.close();
      }
      catch (Exception e2)
      {
      }
    }
    return result;
  }
}