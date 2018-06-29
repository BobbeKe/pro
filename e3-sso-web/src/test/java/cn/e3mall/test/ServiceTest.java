package cn.e3mall.test;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.sso.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/*.xml")
public class ServiceTest {
    @Autowired
    private TokenService tokenService;

    @Test
    public void testService() {
        E3Result result = tokenService.getUserByToken("5286ece8-c431-4b51-8ed8-7ba60268d486");
        System.out.println(result.getStatus());
    }



}
