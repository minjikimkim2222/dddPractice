package myproject.DDD2.mock;

import myproject.DDD2.common.domain.exception.ResourceNotFoundException;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {

    private long id = 0L;
    private final List<User> data = new ArrayList<>();

    @Override
    public User save(User user) {
        if(user.getUserId() == null || user.getUserId() == 0){
            User newUser = User.builder()
                    .userId(++id)
                    .age(user.getAge())
                    .email(user.getEmail())
                    .loginId(user.getLoginId())
                    .password(user.getPassword())
                    .lastLoginAt(user.getLastLoginAt())
                    .build();
            data.add(newUser);
            return newUser;
        }else{
            data.removeIf(item -> Objects.equals(item.getUserId(), user.getUserId()));
            data.add(user);
            return user;
        }
    }

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream().filter(item -> Objects.equals(item.getUserId(), id)).findFirst();
    }

    @Override
    public List<User> findByAge(int age) {
        return data.stream().filter(item -> Objects.equals(item.getAge(), age)).toList();
    }

    @Override
    public Optional<User> findByLoginIdAndPassword(String loginId, String password) {
        return data.stream().filter(item ->
                Objects.equals(item.getLoginId(), loginId) &&
                Objects.equals(item.getPassword(), password))
                .findFirst();
    }

    @Override
    public boolean isExistingEmail(String email) {
        return data.stream().anyMatch(item -> Objects.equals(item.getEmail(), email));
    }

    @Override
    public void deleteById(long id) {
        data.removeIf(item -> Objects.equals(item.getUserId(), id));
    }
}
