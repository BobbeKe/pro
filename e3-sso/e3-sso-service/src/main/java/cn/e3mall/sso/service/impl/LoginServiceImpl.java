package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * 登录
 */
@Service
public class LoginServiceImpl implements LoginService {
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

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //执行查询
        List<TbUser> tbUserList = tbUserMapper.selectByExample(example);
        if (tbUserList == null || tbUserList.size() == 0) {
            //不正确就返回登陆失败
            return E3Result.build(400,"用户名或者密码错误");
        }
        //取用户信息
        TbUser user = tbUserList.get(0);
        //比对判断密码是否正确
        if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            //密码错误
            return E3Result.build(400,"用户名或者密码错误");
        }
        //正确就生成token
        String token_tt = UUID.randomUUID().toString();
        //把用户名写入redis, key: token value:用户信息
        user.setPassword(null);//密码应该放在服务端
        jedisClient.set("SESSION:"+token_tt, JsonUtils.objectToJson(user));
        //设置用户session的过期时间
        jedisClient.expire("SESSION:"+token_tt,SESSION_EXPIRE);
        //返回token
        return E3Result.ok(token_tt);
    }

}
