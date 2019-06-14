package demo.sso.auth.service;

import java.sql.SQLException;

import demo.sso.auth.dao.UserAccountDao;
import demo.sso.common.StringUtil;
import demo.sso.model.User;

/**
 * 用户与账号服务
 * @author Administrator
 *
 */
public class UserAccountService {

	private UserAccountDao userAccountDao = new UserAccountDao();
	
	/**
	 * 按账号查找用户
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public User findUserByAccount(String account) throws SQLException {
		
		// 空白账号返回null
		if (StringUtil.isEmpty(account)) {
			return null;
		}
		
		return userAccountDao.findUserByAccount(account);
	}

}
