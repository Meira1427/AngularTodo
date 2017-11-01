package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import entities.User;

public class DataSecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object objUser = request.getSession().getAttribute("user");
//		System.out.println("****************************************");
//		System.out.println("User: " + objUser);
//		System.out.println("****************************************");
//		System.out.println("Path: " + request.getRequestURI());
//		System.out.println("[3]: " + request.getRequestURI().split("/")[3]);
//		System.out.println("[4]: " + request.getRequestURI().split("/")[4]);
		if(objUser != null) {
	    		User u = (User)objUser;
	    		String URI = request.getRequestURI(); // => ()/ngTodo/api/user/6/todo
	    		String[] pathsplit = URI.split("/");
	    		int pathUserId = Integer.parseInt(pathsplit[4]);
	    		if(pathUserId == u.getId()) {
	    			return true;
	    		}
	    }
	    response.sendRedirect("auth/unauthorized");
	    return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
