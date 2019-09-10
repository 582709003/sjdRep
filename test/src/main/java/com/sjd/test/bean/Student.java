package com.sjd.test.bean;


import lombok.*;

import java.util.Date;

/**
 * @Auther sunjid
 * @Date 2019/8/28
 * @Date 学生类
 **/
@AllArgsConstructor//不包含类里的已初始化的final字段以及static修饰的字段
@NoArgsConstructor
@Data
public class Student {
   private @NonNull String name ;//@NonNull  对于字段来说，如果设置null值就会报空指针异常

   private final String age = "11";

   private static String city ;


}
