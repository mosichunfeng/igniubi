package cn.neusoft.xuxiao.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public abstract class BeanUtils extends org.springframework.beans.BeanUtils
{
  private static final Map<String, Object> INSTANCE_MAP = new ConcurrentHashMap();

  private static final List<Object> ARRAYLIST_INSTANCE = new ArrayList();

  private static final List<Object> LINKEDLIST_INSTANCE = new LinkedList();

  private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

  public static void copyProperties(Object source, Object target) throws BeansException
  {
    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");
    Class actualEditable = target.getClass();
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    for (PropertyDescriptor targetPd : targetPds)
      if (targetPd.getWriteMethod() != null) {
        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
        if ((sourcePd == null) || (sourcePd.getReadMethod() == null)) continue;
        try {
          Method readMethod = sourcePd.getReadMethod();
          if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
          }
          Object value = readMethod.invoke(source, new Object[0]);

          if (value != null) {
            Method writeMethod = targetPd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
              writeMethod.setAccessible(true);
            }
            writeMethod.invoke(target, new Object[] { value });
          }
        } catch (Throwable ex) {
          throw new FatalBeanException("Could not copy properties from source to target", ex);
        }
      }
  }

  public static void copyPropertiesExcludeNullAndBlank(Object source, Object target)
    throws BeansException
  {
    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");
    Class actualEditable = target.getClass();
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    for (PropertyDescriptor targetPd : targetPds)
      if (targetPd.getWriteMethod() != null) {
        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
        if ((sourcePd == null) || (sourcePd.getReadMethod() == null)) continue;
        try {
          Method readMethod = sourcePd.getReadMethod();
          if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
          }
          Object value = readMethod.invoke(source, new Object[0]);

          if ((value != null) && ((value instanceof String)) && (StringUtil.isEmpty((String)value)))
          {
            continue;
          }

          if (value != null) {
            Method writeMethod = targetPd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
              writeMethod.setAccessible(true);
            }
            writeMethod.invoke(target, new Object[] { value });
          }
        } catch (Throwable ex) {
          throw new FatalBeanException("Could not copy properties from source to target", ex);
        }
      }
  }

  public static void defaultProperties(Object source)
    throws BeansException
  {
    if (null == source) {
      logger.error("source must not be null!");
      return;
    }

    Class actualEditable = source.getClass();
    PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
    for (PropertyDescriptor sourcePd : sourcePds) {
      if ((sourcePd.getWriteMethod() == null) || (sourcePd.getReadMethod() == null)) continue;
      try {
        Method readMethod = sourcePd.getReadMethod();
        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
          readMethod.setAccessible(true);
        }
        Object value = readMethod.invoke(source, new Object[0]);
        if (value == null) {
          Method writeMethod = sourcePd.getWriteMethod();
          if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
            writeMethod.setAccessible(true);
          }
          Object instance = getDefaultProperty(sourcePd.getPropertyType());
          if (null != instance)
            writeMethod.invoke(source, new Object[] { instance });
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static Object getDefaultProperty(Class<?> clazz)
    throws Exception
  {
    if (List.class.isAssignableFrom(clazz)) {
      if ((clazz.equals(ArrayList.class)) || (clazz.equals(List.class))) {
        return ARRAYLIST_INSTANCE;
      }

      if (clazz.equals(LinkedList.class)) {
        return LINKEDLIST_INSTANCE;
      }

      if ((!clazz.isInterface()) && (!Modifier.isAbstract(clazz.getModifiers()))) {
        String clazzType = clazz.getName();
        Object instance = INSTANCE_MAP.get(clazzType);
        if (null == instance) {
          instance = clazz.newInstance();
          INSTANCE_MAP.put(clazzType, instance);
        }
        return instance;
      }
      logger.error("获取LIST接口属性默认值失败，未找到合适的接口类型！");
    }

    return null;
  }
}