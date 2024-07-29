package myproject.DDD2.user.repository;

import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByAge(int age);

    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);

    Optional<UserEntity> findByIdAndUserStatus(long id, UserStatus userStatus);

    boolean findByEmail(String email);
}
