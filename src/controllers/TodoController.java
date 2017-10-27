package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.TodoDAO;
import entities.Todo;

@RestController
public class TodoController {
	
  @Autowired
  TodoDAO todoDao;
  
  @RequestMapping(path="ping", method=RequestMethod.GET)
	public String ping() {
		return "pong";
	}
	
  //  GET /users/{uid}/todos
  @RequestMapping(path="users/{uid}/todos", method=RequestMethod.GET)
  public Collection<Todo> index(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid) {
	  Collection<Todo> answer = todoDao.index(uid);
	  if(answer==null || answer.size()==0) {
		  res.setStatus(404);
	  }
	  else {
		  res.setStatus(200);
	  }
	  return answer;
  }

  //  GET /users/{uid}/todos/{tid}
  @RequestMapping(path="users/{uid}/todos/{tid}", method=RequestMethod.GET)
  public Todo show(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid, @PathVariable int tid) {
	  Todo todo = todoDao.show(uid, tid);
	  if(todo==null) {
		  res.setStatus(404);
	  }
	  else {
		  res.setStatus(200);
	  }
	  return todo;
  }

  //  POST /users/{uid}/todos
  @RequestMapping(path="users/{uid}/todos", method=RequestMethod.POST)
  public Todo create(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid, @RequestBody String todoJson) {
	  Todo todo = todoDao.create(uid, todoJson);
	  if(todo==null) {
		  res.setStatus(400);
	  }
	  else {
		  res.setStatus(201);
	  }
	  return todo;
  }

  //  PUT /users/{uid}/todos/{tid}
  @RequestMapping(path="users/{uid}/todos/{tid}", method=RequestMethod.PUT)
  public Todo update(HttpServletRequest req, 
		  			HttpServletResponse res, 
		  			@PathVariable int uid, 
		  			@PathVariable int tid, 
		  			@RequestBody String todoJson) {
	  Todo todo = todoDao.update(uid, tid, todoJson);
	  if(todo==null) {
		  res.setStatus(406);
	  }
	  else {
		  res.setStatus(202);
	  }
	  return todo;
  }

  //  DELETE /users/{uid}/todos/{tid}
  @RequestMapping(path="users/{uid}/todos/{tid}", method=RequestMethod.DELETE)
  public Boolean destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid, @PathVariable int tid) {
	  Boolean answer = todoDao.destroy(uid, tid);
	  if(!answer) {
		  res.setStatus(406);
	  }
	  else {
		  res.setStatus(202);
	  }
	  return answer;
  }

}
