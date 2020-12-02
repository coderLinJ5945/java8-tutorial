package com.winterbe.java8.samples.stream;

import java.util.Optional;

/**
 * @author Benjamin Winterberg
 *
 * java.util.Optional : 解决程序员最常见的 NullPointerException 异常问题
 *
 * 正确用法参考:
 * 中文： https://blog.kaaass.net/archives/764
 * 英文原出处： https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 *
 */
public class Optional1 {

    public static void main(String[] args) {
        //Optional.of("bam"); 非null值创建 Optional 对象
        Optional<String> optional = Optional.of("bam");
        //如果值存在返回true 否则 false
        optional.isPresent();           // true
        //获取值
        optional.get();                 // "bam"
        //有值返回值，无值返回替代值
        optional.orElse("fallback");    // "bam"
        /**
         * 正确用法用法：
         * 1. 判断是否存在 optional.ifPresent()
         * 2. 存在返回 optional.get()
         */
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

}