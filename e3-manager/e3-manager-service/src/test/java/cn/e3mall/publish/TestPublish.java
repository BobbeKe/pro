package cn.e3mall.publish;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//发布服务
public class TestPublish {

    @Test
    public void publlishService() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        while(true){
            Thread.sleep(1000);
        }
    }
}
