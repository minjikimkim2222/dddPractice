package myproject.DDD2.user.service.port;

import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    User getById(long id);

    Optional<User> findById(long id);

    List<User> findByAge(int age);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByLoginIdAndPassword(String loginId, String password);

    boolean isExistingEmail(String email);

    void deleteById(long id);
}
