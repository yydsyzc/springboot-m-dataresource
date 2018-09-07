package org.spring.springboot.timer.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/5 8:48
 */
//@Component
public class ScheduledTask {

    private int count=0;

    /**
     * 方法上添加@Scheduled 配置参数
     * 为6秒
     * 就可以实现定时任务
     */
    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 方法上添加@Scheduled 配置参数fixedRate = 6000（6秒）
     * 就可以实现定时任务
     */
    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

    //参数说明
    //@Scheduled 参数可以接受两种定时的设置，⼀种是常⽤的 cron="*/6 * * * * ?" ，
    // ⼀种是fixedRate = 6000 ，两种都可表⽰固定周期执⾏定时任务。
    //@Scheduled(fixedRate = 6000) ：上⼀次开始执⾏时间点之后 6 秒再执⾏。
    // @Scheduled(fixedDelay = 6000) ：上⼀次执⾏完毕时间点之后 6 秒再执⾏。
    // @Scheduled(initialDelay=1000, fixedRate=6000) ：第⼀次延迟 1 秒后执⾏，之后按
    //ﬁxedRate 的规则每 6 秒执⾏⼀次。

    //cron 说明
    //cron ⼀共有 7 位，最后⼀位是年，Spring Boot 定时⽅案中只需要设置 6 位即可：
    //- 第⼀位，表⽰秒，取值 0-59；
    //- 第⼆位，表⽰分，取值 0-59；
    //- 第三位，表⽰⼩时，取值 0-23；
    //- 第四位，⽇期天/⽇，取值 1-31；
    //- 第五位，⽇期⽉份，取值 1-12；
    //- 第六位，星期，取值 1-7，星期⼀、星期⼆…；
    //注：不是第1周、第2周的意思，另外：1表⽰星期天，2表⽰星期⼀。
    //- 第七位，年份，可以留空，取值 1970-2099。
    //（*）星号：可以理解为每的意思，每秒、每分、每天、每⽉、每年……。
    //（?）问号：问号只能出现在⽇期和星期这两个位置，表⽰这个位置的值不确定，每天 3 点执⾏，所以第六位星期的位置是不需要关注的，就是不确定的值。同时，⽇期和星期是两个相互排斥的元素，通过问号来表明不指定值。假如 1 ⽉ 10 ⽇是星期⼀，如果在星期的位置是另指定星期⼆，就前后冲突⽭盾了。
    //（-）减号：表达⼀个范围，如在⼩时字段中使⽤“10-12”，则表⽰从 10~12 点，即 10、11、12。
    //（,）逗号：表达⼀个列表值，如在星期字段中使⽤“1、2、4”，则表⽰星期⼀、星期⼆、星期四。
    //（/）斜杠：如 x/y，x 是开始值，y 是步⻓，⽐如在第⼀位（秒） 0/15 就是，从 0 秒开始，每 15 秒，最后就是 0、15、30、45、60，另 */y，等同于 0/y。
    //0 0 3 * * ? 每天 3 点执⾏。
    //0 5 3 * * ? 每天 3 点 5 分执⾏。
    //0 5 3 ? * * 每天 3 点 5 分执⾏，与上⾯作⽤相同。
    //0 5/10 3 * * ? 每天 3 点的 5 分、15 分、25 分、35 分、45 分、55 分这⼏个时间点执⾏。0 10 3 ? * 1 每周星期天，3点10分 执⾏，注：1 表⽰星期天。
    //0 10 3 ? * 1#3 每个⽉的第 三 个星期，星期天执⾏，# 号只能出现在星期的位置。




}
