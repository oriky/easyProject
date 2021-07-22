package com.zhousj.demo.util;


import com.zhousj.demo.anotation.Validate;
import com.zhousj.demo.exception.ValidateException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对象字段校验工具类
 *
 * @author zhousj
 * @date 2020/11/30
 */
@SuppressWarnings("unused")
public class ValidateUtil {


    /**
     * 字段非空校验
     **/
    public static <T> void validateDto(T t) {
        validateDto(t, false);
    }

    /**
     * 字段非空校验
     **/
    public static <T> void validateDto(T t, boolean allowNull) {
        validateDto(t, allowNull, false);
    }

    /**
     * 字段长度校验
     **/
    public static <T> void validateLenDto(T t, boolean checkLen) {
        validateDto(t, false, checkLen);
    }

    /**
     * 对添加了Validate注解的对象字段进行非空检验和字段长度校验
     *
     * @param t         需校验的对象
     * @param allowNull 是否允许空
     * @param checkLen  是否校验长度
     * @author zhousj
     * @see Validate,ValidateException
     */
    public static <T> void validateDto(T t, boolean allowNull, boolean checkLen) {
        if (allowNull && !checkLen) {
            return;
        }
        Class<?> c = t.getClass();
        Validate annotation;
        String name;
        PropertyDescriptor pd;
        Method readMethod;
        Object object;
        Field[] fields = c.getDeclaredFields();
        if (fields.length > 0) {
            try {
                for (Field field : fields) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    annotation = field.getAnnotation(Validate.class);
                    if (annotation != null) {
                        name = annotation.name().isEmpty() ? field.getName() : annotation.name() + "(" + field.getName() + ")";
                        pd = new PropertyDescriptor(field.getName(), c);
                        readMethod = pd.getReadMethod();
                        object = readMethod.invoke(t);
                        if (!allowNull) {
                            if (!annotation.allowNull() && object == null) {
                                throw new ValidateException(name + "不能为空");
                            }
                        }
                        if (checkLen) {
                            if (object.toString().length() > annotation.maxLen()) {
                                throw new ValidateException(name + "长度不能超过" + annotation.maxLen());
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                throw new ValidateException("校验失败: " + e.getMessage(), e);
            }
        }
    }
}
