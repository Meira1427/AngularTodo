package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.User;

@Repository
@Transactional
public class AuthDAOImpl implements AuthDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	//	Always user this one CRYPTO: import org.springframework.security.crypto.password.PasswordEncoder;
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User register(User u) {
		String encryptedPW = encoder.encode(u.getPassword());
		System.out.println("*****************************************");
		System.out.println(encryptedPW);
		u.setPassword(encryptedPW);
		if(isEmailUnique(u.getEmail())) {
			em.persist(u);
			em.flush();
			return u;
		}
		return null;
	}

	@Override
	public User login(User u) {
		String queryString = "Select u from User u Where u.email = :email";
		List<User> users = em.createQuery(queryString, User.class)
							.setParameter("email", u.getEmail())
							.getResultList();
		if(users.size() > 0) {
			boolean pwMatch = encoder.matches(u.getPassword(), users.get(0).getPassword());
			if(pwMatch) {
				return users.get(0);
			}
		}
		return null;
	}

	@Override
	public boolean isEmailUnique(String email) {
		String queryString = "Select u from User u Where u.email = :email";
		List<User> users = em.createQuery(queryString, User.class)
							 .setParameter("email", email)
							 .getResultList();
		if(users.size() == 0) {
			return true;
		}
		return false;
	}

}
