package app2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import demo.sso.client.User;

public class PrivilegeFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		// 集成SSO前
		// User loginUser = ((HttpServletRequest)request).getSession().getAttribute("loginUser");
		
		// 从上一个Filter(SSOFilter)存入的属性中获取user，能执行到此处，一定是已登录
		User user = (User) request.getAttribute("user");
		// 可以根据user信息获得与本系统相关的用户业务信息
		// Empl empl = EmplService.findEmplByUser(user.getId());
		
		// 示范一个更详细的需要判定
		String acc = user.getAccount();
		
		if ("zs".equals(acc)) {
			response.getWriter().write("zhangsan is forbidden!");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
