package com.javagpt.back.util;

import com.javagpt.common.exception.BeanCopyNotSuccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BeanHelper {

    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    public static void CopySourceIntoDestination(Object source, Object destination){
        Class<?> destinationClass = destination.getClass();
        PropertyDescriptor[] destPds = BeanUtils.getPropertyDescriptors(destinationClass);
        for (PropertyDescriptor destPd : destPds) {
            if (destPd.getWriteMethod() != null){
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), destPd.getName());
                if(sourcePd != null && sourcePd.getReadMethod() != null){
                    Method sourceReadMethod = sourcePd.getReadMethod();
                    if(!Modifier.isPublic(sourceReadMethod.getDeclaringClass().getModifiers())){
                        sourceReadMethod.setAccessible(true);
                    }
                    try {
                        Object sourceValue = sourceReadMethod.invoke(source);
                        if (sourceValue != null){
                            Method destWriteMethod = destPd.getWriteMethod();
                            if(!Modifier.isPublic(destWriteMethod.getDeclaringClass().getModifiers())){
                                destWriteMethod.setAccessible(true);
                            }
                            destWriteMethod.invoke(destination, sourceValue);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        logger.error("destination field: {}, source field: {}, message: {}",
                                destPd.getName(), sourcePd.getName(), e.getMessage());
                        throw new BeanCopyNotSuccessException(e);
                    }
                }
            }
        }
    }
}
