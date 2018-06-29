package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;

public interface LoginService {
    //参数： 用户名 密码
    //业务逻辑
    /*
     *判断用户名和密码是否正确
     * 不正确就返回登陆失败
     * 正确就生成token
     * 把用户名写入redis, key: token value:用户信息
     * 设置用户session的过期时间
     * 写入cookie的业务是表现层的事情
     */
    // 返回值：token
    E3Result userLogin(String username,String password);
}
