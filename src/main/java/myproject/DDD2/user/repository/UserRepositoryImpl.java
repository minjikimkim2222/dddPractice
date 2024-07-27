package myproject.DDD2.user.repository;

import lombok.RequiredArgsConstructor;
import myproject.DDD2.common.domain.exception.ResourceNotFoundException;
import myproject.DDD2.user.converter.UserConverter;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;
import myproject.DDD2.user.service.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user){
        UserEntity userEntity = userJpaRepository.save(UserConverter.toUserEntity(user));
        return UserConverter.toUser(userEntity);
    }


    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserConverter::toUser);
    }

    @Override
    public Optional<User> findByLoginIdAndPassword(String loginId, String password){
        return userJpaRepository.findByLoginIdAndPassword(loginId, password).map(UserConverter::toUser);
    }

    @Override
    public boolean isExistingEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public List<User> findByAge(int age) {
        return userJpaRepository.findByAge(age).stream()
                .map(UserConverter::toUser)
                .toList();
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndUserStatus(id, userStatus)
                .map(UserConverter::toUser);
    }


    @Override
    public void deleteById(long id){
        UserEntity userEntity = findById(id).map(UserConverter::toUserEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        userJpaRepository.delete(userEntity);
    }
}
