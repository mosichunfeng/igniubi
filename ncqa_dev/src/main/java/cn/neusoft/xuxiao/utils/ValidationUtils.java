package cn.neusoft.xuxiao.utils;

import cn.neusoft.xuxiao.exception.BusinessException;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtils
{
  private static Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

  public static void checkNotEmpty(Object obj, String logMsg)
  {
    boolean checkInd = true;
    if (null == obj) {
      checkInd = false;
    }
    else
    {
      if ((obj instanceof String)) {
        String str = (String)obj;
        if (StringUtil.isEmpty(str)) {
          checkInd = false;
        }

      }

      if ((obj instanceof Collection)) {
        Collection collection = (Collection)obj;
        if (CollectionUtils.isEmpty(collection)) {
          checkInd = false;
        }

      }

      if ((obj instanceof Map)) {
        Map map = (Map)obj;
        if (map.isEmpty()) {
          checkInd = false;
        }
      }

    }

    if (!checkInd) {
      logger.warn(logMsg);
      throw new BusinessException(String.valueOf(201), logMsg);
    }
  }

  public static void checkLength(String str, int minLength, int maxLength, String logMsg)
  {
    if (minLength > maxLength) {
      logger.warn("验证方法参数错误！");
      return;
    }
    str = null == str ? "" : str;
    int fieldLen = str.length();
    if ((fieldLen < minLength) || (fieldLen > maxLength)) {
      logger.warn(logMsg);
      throw new BusinessException(String.valueOf(201), logMsg);
    }
  }
}