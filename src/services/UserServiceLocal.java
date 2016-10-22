package services;

import models.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface UserServiceLocal {
	List<User> findAll();
	Optional<User> findById(int id);
	Optional<User> findByMail(String mail);
	int createUser(User user);
	int updateUser(User user);
	int deleteUser(User user);
}
