package com.ddc.server.service;

import com.baomidou.mybatisplus.service.IService;
import com.ddc.server.entity.Admin;
import com.ddc.server.entity.UserThirdparty;
import com.ddc.server.model.ThirdPartyUser;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author dingpengfei
 * @since 2019-05-09
 */
public interface IUserThirdpartyService extends IService<UserThirdparty> {

    Admin insertThirdPartyUser(ThirdPartyUser param, String password)throws Exception;

}
