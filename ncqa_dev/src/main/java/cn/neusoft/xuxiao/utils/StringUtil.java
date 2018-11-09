package cn.neusoft.xuxiao.utils;

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
}