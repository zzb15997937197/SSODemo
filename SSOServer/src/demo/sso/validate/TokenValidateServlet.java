package demo.sso.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.sso.TokenUserData;
import demo.sso.model.User;

/**
 * token 有效性验证
 */
@WebServlet("/validate")
public class TokenValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		User user = TokenUserData.validateToken(token);

		if (user == null) {
			response.getWriter().write("");
		} else {
			// 如果有中文，要考虑乱码问题，此处不处理
			response.getWriter().write(
					"id=" + user.getId() + ";name=" + user.getName()
							+ ";account=" + user.getAccount());
		}
	}

}
