package demo.sso.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.sso.common.JDBCHelper;
import demo.sso.model.User;

/**
 * 用户与账号数据访问对象
 * 
 * @author Administrator
 *
 */
public class UserAccountDao {

	/**
	 * 按账号查询用户信息
	 * 
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public User findUserByAccount(String account) throws SQLException {

		String sql = "select * from user where account=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User retUser = null;

		try {
			conn = JDBCHelper.getConnection();

			ps = conn.prepareStatement(sql);
			ps.setString(1, account);

			rs = ps.executeQuery();

			if (rs.next()) { // 取到数据，封装成user对象
				retUser = new User();
				retUser.setId(rs.getInt("id"));
				retUser.setName(rs.getString("name"));
				retUser.setAccount(rs.getString("account"));
				retUser.setPasswd(rs.getString("passwd"));
			}

			return retUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e); // 发生异常得新抛出
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

}
