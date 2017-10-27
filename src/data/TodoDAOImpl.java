package data;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Todo;
import entities.User;

@Transactional
@Repository
public class TodoDAOImpl implements TodoDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Set<Todo> index(int uid) {
		String queryString = "Select t from Todo t where t.user.id = :uid";
		List<Todo> tempList = em.createQuery(queryString, Todo.class)
							.setParameter("uid", uid)
					   		.getResultList();
		Set<Todo> answer = new HashSet<>();
		for (int i = 0; i < tempList.size() ; i++) {
			answer.add(tempList.get(i));
		}
		return answer;
	}

	@Override
	public Todo show(int uid, int tid) {
		Todo todo = new Todo();
		String queryString = "Select t from Todo t where t.user.id = :uid and t.id = :tid";
		List<Todo> tempList = em.createQuery(queryString, Todo.class)
							  .setParameter("uid", uid)
							  .setParameter("tid", tid)
							  .getResultList();
		if(tempList.size() > 0) {
			todo = tempList.get(0);
		}
		return todo;
	}

	@Override
	public Todo create(int uid, String todoJson) {
		ObjectMapper mapper = new ObjectMapper();
		User user = getUserById(uid);
		Todo mappedTodo = null;
		try {
			mappedTodo = mapper.readValue(todoJson, Todo.class);
			mappedTodo.setUser(user);
			em.persist(mappedTodo);
			em.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mappedTodo;
	}

	@Override
	public Todo update(int uid, int tid, String todoJson) {
		Todo managedTodo = em.find(Todo.class, tid);
		if(managedTodo==null) {
			return null;
		}
		User user = getUserById(uid);
		ObjectMapper mapper = new ObjectMapper();
		Todo mappedTodo = null;
		try {
			mappedTodo = mapper.readValue(todoJson, Todo.class);
			mappedTodo.setUser(user);
			managedTodo.setUser(user);
			if(mappedTodo.getTask() != null && mappedTodo.getTask() != "") {
				managedTodo.setTask(mappedTodo.getTask());
			}
			if(mappedTodo.getDescription() != null && mappedTodo.getDescription() != "") {
				managedTodo.setDescription(mappedTodo.getDescription());
			}
			if(mappedTodo.getDescription() != null && mappedTodo.getDescription() != "") {
				managedTodo.setDescription(mappedTodo.getDescription());
			}
			if(mappedTodo.getCompleteDate() != null) {
				managedTodo.setCompleteDate(mappedTodo.getCompleteDate());
			}
			if(mappedTodo.getDueDate() != null && mappedTodo.getDueDate() != "") {
				managedTodo.setDueDate(mappedTodo.getDueDate());
			}
			if(mappedTodo.getCreatedAt() != null) {
				managedTodo.setCreatedAt(mappedTodo.getCreatedAt());
			}
			if(mappedTodo.getUpdatedAt() != null) {
				managedTodo.setUpdatedAt(mappedTodo.getUpdatedAt());
			}
			managedTodo.setCompleted(mappedTodo.isCompleted());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managedTodo;
	}

	@Override
	public Boolean destroy(int uid, int tid) {
		Todo managedTodo = em.find(Todo.class, tid);
		if(managedTodo==null) {
			return false;
		}
		if(managedTodo.getUser().getId() != uid) {
			return false;
		}
		//em.remove(managedTodo); //some bug; this isn't working
		String query = "DELETE FROM Todo t WHERE t.id = :id";
		em.createQuery(query).setParameter("id", tid).executeUpdate();
		return true;
	}
	
	public User getUserById(int uid) {
		User user = new User();
		String queryString = "Select u from User u where u.id = :uid";
		List<User> tempList = em.createQuery(queryString, User.class)
								.setParameter("uid", uid)
								.getResultList();
		if(tempList.size() > 0) {
			user = tempList.get(0);
		}		
		return user;		
	}

}
