package com.sjd.test.bean;

import com.sjd.test.bean.Student;
/**
 * @Auther sunjid
 * @Date 2019/8/28
 * @Date LomBok注解
 **/

/**
 * @NonNull 这个注解可以用在成员方法或者构造方法的参数前面，
 * 会自动产生一个关于此参数的非空检查，如果参数为空，则抛出一个空指针异常
 *
 * @Cleanup  这个注解用在变量前面，可以保证此变量代表的资源会被自动关闭
 * ，默认是调用资源的close()方法，如果该资源有其它关闭方法，
 * 可使用@Cleanup(“methodName”)来指定要调用的方法
 *
 * @Getter/@Setter
 *
 * @ToString/@EqualsAndHashCode 这两个注解也可以添加限制条件，
 * 例如用@ToString(exclude={“param1”，“param2”})来排除param1和param2两个成员变量，
 * 或者用@ToString(of={“param1”，“param2”})来指定使用param1和param2两个成员变量，
 * @EqualsAndHashCode注解也有同样的用法
 *
 *
 * @NoArgsConstructor/@RequiredArgsConstructor /@AllArgsConstructor
 * 成员变量需要都是非静态的
 *
 * @Data  该注解使用在类上，该注解会提供getter、setter、equals、canEqual、hashCode、toString方法
 *
 * @SneakyThrows : 等同于try/catch 捕获异常
 */

public class LomBok {



}
