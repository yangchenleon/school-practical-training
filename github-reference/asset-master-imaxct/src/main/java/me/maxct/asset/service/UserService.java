package me.maxct.asset.service;

import me.maxct.asset.domain.User;
import me.maxct.asset.dto.LoginVO;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-24 19:11
 */
public interface UserService {
    Msg<LoginVO> login(String username, String password);

    /**
     * 列出用户
     * @param pageNo 页号
     * @param size 页大小
     * @return return
     */
    Msg listUser(int pageNo, int size);

    /**
     * 新增&更新用户
     * @param user 用户
     * @return return
     */
    Msg saveUser(User user);

    User getUser(Long id);

    /**
     * 部门和用户的汇总列表
     * @return return
     */
    Msg getDepUser();
}
