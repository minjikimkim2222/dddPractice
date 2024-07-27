package myproject.DDD2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByAge(int age);

    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);

    boolean findByEmail(String email);
}
