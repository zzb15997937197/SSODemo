package demo.sso.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.client.util.CookieUtil;

/**
 * SSO客户端模块Filter
 * 
 * @author Administrator
 *
 */
public class SSOFilter implements Filter {

	// SSO Server登录页面URL
	private static final String SSO_LOGIN_URL = "/server/login",
			SSO_VALIDATE_URL = "http://localhost:8080/server/validate";

	// 拦截操作
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 从请求中提取token
		String token = CookieUtil.getCookie(req, "token");

		// 本次请求的完整路径
		String origUrl = req.getRequestURL().toString();
		String queryStr = req.getQueryString();
		if (queryStr != null) {
			origUrl += "?" + queryStr;
		}

		// token 不存在，跳转到SSOServer用户登录页
		if (token == null) {
			resp.sendRedirect(SSO_LOGIN_URL + "?origUrl="
					+ URLEncoder.encode(origUrl, "utf-8"));
		} else { // token存在，验证有效性
			URL validateUrl = new URL(SSO_VALIDATE_URL + "?token=" + token);
			HttpURLConnection conn = (HttpURLConnection) validateUrl
					.openConnection();

			conn.connect();
			InputStream is = conn.getInputStream();

			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			String ret = new String(buffer);

			if (ret.length() == 0) { // 返回空字符串，表示 token无效
				resp.sendRedirect(SSO_LOGIN_URL + "?origUrl="
						+ URLEncoder.encode(origUrl, "utf-8"));
			} else {
				String[] tmp = ret.split(";");
				User user = new User();
				for (int i = 0; i < tmp.length; ++i) {
					String[] attrs = tmp[i].split("=");
					switch (attrs[0]) {
					case "id":
						user.setId(Integer.parseInt(attrs[1]));
						break;
					case "name":
						user.setName(attrs[1]);
						break;
					case "account":
						user.setAccount(attrs[1]);
						break;
					}
				}
				request.setAttribute("user", user);
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
