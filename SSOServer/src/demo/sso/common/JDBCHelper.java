package demo.sso.common;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC 辅助类
 * 
 * @author Administrator
 *
 */
public class JDBCHelper {

	// 禁止实例化
	private JDBCHelper() {
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/user_info";
		String user = "root";
		String passwd = "test123";

		return DriverManager.getConnection(url, user, passwd);
	}
}
