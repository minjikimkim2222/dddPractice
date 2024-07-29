package myproject.DDD2.user.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import myproject.DDD2.common.domain.exception.ResourceNotFoundException;
import myproject.DDD2.common.service.port.ClockHolder;
import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserUpdateRequest;
import myproject.DDD2.user.converter.UserConverter;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;
import myproject.DDD2.user.service.port.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
/***
 * 생성 -> PRIVATE
 * 최초 로그인 1회 -> PUBLIC
 * 이메일 수정 -> PUBLIC만 가능
 */
public class UserService {

    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public User getById(long id){
        return userRepository.getById(id);
    }

    @Transactional
    public User createUser(UserCreateRequest userCreateRequest){
        checkDuplicationEmail(userCreateRequest.getEmail());

        User user = UserConverter.toUser(userCreateRequest);
        return userRepository.save(user);
    }
    @Transactional
    public void login(String loginId, String password){
        User user = userRepository.findByLoginIdAndPassword(loginId, password).
                orElseThrow(() -> new ResourceNotFoundException("Users", loginId));

        user = user.login(clockHolder);
        userRepository.save(user); // jpa Repository를 사용하지 않기떄문에 변경사항 저장 필수
    }

    @Transactional
    public void editEmail(long id, UserUpdateRequest userUpdateRequest){
        checkDuplicationEmail(userUpdateRequest.getEmail());

        User user = userRepository.findByIdAndStatus(id, UserStatus.PUBLIC)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));

        user = user.editEmail(userUpdateRequest);
        userRepository.save(user);
    }

    public List<User> findAllByAge(int age){
        return userRepository.findByAge(age);
    }

    private void checkDuplicationEmail(String email) {
        if(userRepository.isExistingEmail(email)){
            throw new IllegalStateException("이메일중복");
        }
    }
}
