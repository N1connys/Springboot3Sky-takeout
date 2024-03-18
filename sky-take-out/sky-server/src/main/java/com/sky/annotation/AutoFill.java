package com.sky.annotation;

import com.sky.enumeration.OperationType;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//自定义注解，用于表示需要进行公共字段填充的方法
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
//数据库操作类型（Update Insert）
    OperationType value();
}

