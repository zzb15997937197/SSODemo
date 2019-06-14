package demo.sso.auth.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.auth.service.UserAccountService;
import demo.sso.common.KeyGenerator;
import demo.sso.common.StringUtil;
import demo.sso.model.User;

/**
 * user login
 */
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private static String DOMAIN = ".test.loc";

	private UserAccountService userAccountService = new UserAccountService();

	/**
	 * 进入登录页
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 获得原始请求的url并保存传递，当登录成功时，让浏览器再次跳转到这个url
		String origUrl = req.getParameter("origUrl");
		req.setAttribute("origUrl", origUrl);

		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}

	/**
	 * 登录表单提交时处理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");

		String origUrl = request.getParameter("origUrl");

		// 按account查找用户
		User user = null;
		try {
			user = userAccountService.findUserByAccount(account);

			if (user != null) {
				if (user.getPasswd().equals(passwd)) { // 判断密码是否正确
					// 1 生成token
					String token = KeyGenerator.generate();
					// 2 将token user存储到全局唯一数据结构中
					TokenUserData.addToken(token, user);
					// 3 写cookie JVM 
					Cookie tokenCookie = new Cookie("token", token);
					tokenCookie.setPath("/");
					//tokenCookie.setDomain(DOMAIN);
					tokenCookie.setHttpOnly(true);
					response.addCookie(tokenCookie);

					// 4 跳转到原请求
					if (StringUtil.isEmpty(origUrl)) {
						origUrl = "login_success";
					} else {
						origUrl = URLDecoder.decode(origUrl, "utf-8");
					}
					response.sendRedirect(origUrl);
				} else {
					backToLoginPage(request, response, account, origUrl,
							"密码不正确");
				}
			} else { // 用户不存在
				backToLoginPage(request, response, account, origUrl, "用户不存在");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			backToLoginPage(request, response, account, origUrl, "发生系统错误！");
		}

	}

	private void backToLoginPage(HttpServletRequest req,
			HttpServletResponse resp, String account, String origUrl,
			String errInfo) throws ServletException, IOException {

		req.setAttribute("account", account);
		req.setAttribute("origUrl", origUrl);
		req.setAttribute("errInfo", errInfo);

		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
	}
}
