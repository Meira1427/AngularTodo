package controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import data.AuthDAO;
import entities.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthDAO authDao;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	  public User register(HttpSession session, @RequestBody User user, HttpServletResponse resp) {
		User u = authDao.register(user);
		if(u == null) {
			resp.setStatus(422);
			return null;
		}
		resp.setStatus(201);
		session.setAttribute("user", u);
	    return u;
	  }
	  
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	  public User login(HttpSession session, @RequestBody User user, HttpServletResponse resp) {
	    User u = authDao.login(user);
	    if(u == null) {
	    		resp.setStatus(401);
	    		return null;
	    }
	    resp.setStatus(200);
	    session.setAttribute("user", u);
	    return u;
	  }
	  
	@RequestMapping(path = "/logout", method = RequestMethod.POST)
	  public Boolean logout(HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		if(session.getAttribute("user") == null) {
			return true;
		}
		return false;
	  }
	  
	@RequestMapping(path = "/unauthorized")
	  public String unauth(HttpServletResponse response) {
	    response.setStatus(401);
	    return "unauthorized";
	  }

}


