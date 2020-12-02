package com.winterbe.java8.samples.stream;

import java.util.Optional;
import java.util.function.Function;

/**
 * Optional 核心解决问题：
 *      NullPointerException，只要代码中可能产生null，都可以使用Optional 返回
 * Optional 使用核心场景：
 *      1. 适用于层级处理（依赖上一步操作）的场合。
 *      2. 产生对象的方法若可能返回 null，可以用 Optional 包装。
 *      3. 尽可能延后处理 null 的时机，在过程中使用 Optional 保留不确定性。
 *      4. 尽量避免使用 Optional 作为字段类型
 *
 *      例子网站登录接口调用：
 *          1. 发送 HTTP 请求，得到返回
 *          2. 解析 json，转换成对象
 *          3. 判断结果是否正确
 *          4. 取得鉴权令牌
 *          5. 处理鉴权令牌
 *      Optional 可以设计：  Optional 在过程中保留了不确定性，从而把对 null 的处理移到了若干次操作的最后，以减少出现 NPE 错误的可能
 *          1. 结果 String（可能是 null） –包装–>  Optional<String>
 *          2. Optional<String>  –解析–>  Optional<Json 对象 >
 *          3. Optional<Json 对象>  –Filter 判断成功–>  Optional<Json 对象 >
 *          4. Optional<Json 对象>  –取鉴权令牌–>  Optional<AuthToken>
 *          5. 对 Optional<AuthToken> 进行处理，消除 Optional
 *
 */
public class Optional3 {

    public static void main(String[] args) {

        System.out.println(Optional.ofNullable(null));
    }


}

//demo1 解决产生对象可能为null，并延后处理 null 的时机
class User{
    String name;

    //demo1 解决 产生对象的方法若可能返回 null
    public  String getName(User u) {
        if (u == null || u.name == null)
            return "Unknown";
        return u.name;
    }
    // 正确推荐用法：解决产生对象可能为null，并延后处理 null 的时机
    public  String getName1(User u) {
        return  Optional.ofNullable(u).map(user -> u.name).orElse("Unknown");
        /*lambda 表达式组合使用
        return Optional.ofNullable(u).map(new Function<User, String>() {
            @Override
            public String apply(User user) {
                return u.name;
            }
        }).orElse("Unknown");*/
    }

    //常见的错误、错误、错误 用法！！！
    public String getName2(User user){
        Optional<User> obj = Optional.ofNullable(user); //null 返回 Optional.empty
        //只是用Optional.isPresent 代替了NUll而已
        if(obj.isPresent()){
            return obj.get().name;
        }
        return "Unknown";
    }
}

//demo2 层级嵌套处理null
class Competition{

    User user;

    public User getUser(){
        return null;
    };

    //嵌套实体类获取数据
    public static String getChampionName(Competition comp) throws IllegalArgumentException {
        if (comp != null) {
            User result = comp.getUser();
            if (result != null) {
                return result.name;
            }
        }
        throw new IllegalArgumentException("The value of param comp isn't available.");
    }
    //Optional 结合 lambda 函数式变成返回
    public static String getChampionName1(Competition comp) throws IllegalArgumentException {
        return Optional.ofNullable(comp)
                .map(competition -> competition.getUser())
                .map(user1 -> user1.name)
                .orElseThrow(()->new IllegalArgumentException("The value of param comp isn't available."));
    }
}