package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;

/**
 * 根据token查询用户信息
 * <p>Title: TokenService</p>
 * <p>Description: </p>
 * @version 1.0
 */
public interface TokenService {

	E3Result getUserByToken(String token_tt);
}
