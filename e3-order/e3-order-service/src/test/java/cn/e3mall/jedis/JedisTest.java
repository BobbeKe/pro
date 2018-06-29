package cn.e3mall.jedis;

import cn.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JedisTest {
    @Test
    public  void testJedisClient() throws  Exception {

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");
        JedisClient jedisClient = ac.getBean(JedisClient.class);
        jedisClient.set("ORDER", "123");
        Long order = jedisClient.incr("ORDER");
        String s = order.toString();
        System.out.println(s);
    }
}
