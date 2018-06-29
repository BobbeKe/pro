package cn.e3mall.jedis;

import cn.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JedisClientTest {

    @Test
    public  void testJedisClient() throws  Exception {

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");
        JedisClient jedisClient = ac.getBean(JedisClient.class);
        jedisClient.set("mytest","testvalue");
        System.out.println(jedisClient.get("mytest"));
    }

    @Test
    public  void testJedisClientCluster() throws  Exception {

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");
        JedisClient jedisClient = ac.getBean(JedisClient.class);//同一个实现类
        jedisClient.set("mytest","testvalue");
        System.out.println(jedisClient.get("mytest"));
    }

}
