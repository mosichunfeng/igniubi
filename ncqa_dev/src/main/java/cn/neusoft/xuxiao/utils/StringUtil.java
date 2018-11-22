package cn.neusoft.xuxiao.utils;

import org.junit.Test;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StringUtil
{
  public static boolean isEmpty(String str)
  {
    return (null == str) || (str.trim().length() == 0);
  }

  public static boolean isEquals(String arg0, String arg1)
  {
    if ((isEmpty(arg0)) && (isEmpty(arg1))) {
      return true;
    }

    return (!isEmpty(arg0)) && (!isEmpty(arg1)) && (arg0.trim().equals(arg1.trim()));
  }

    /**
     * [{"0":"B"},{"1":"C"},{"2":"A,B"},{"3":"D"},{"4":"D"},{"5":"C"},{"6":"C"},{"7":"B"},{"8":"B"},{"9":"A"}]
     * @return
     */
	public static String getUUid(){
		return UUID.randomUUID().toString().replace("-", "");
	}

    public static Map<Integer, String> StringToMap(String src){
        String trim = src.replace('[', ' ').replace(']', ' ').trim();
        System.out.println(trim);

       String[] split = trim.split(",");

        Map<Integer,String> map = new HashMap<>();

        for(String s : split){
            String t = s.replace("{"," ").replace("}"," ").trim();
            String[] split1 = t.split(":");
            String key = split1[0].replace('"',' ').trim();
            map.put(Integer.valueOf(key),split1[1]);
        }
        return  map;
    }
}