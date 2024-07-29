package myproject.DDD2.user.service;

import myproject.DDD2.common.domain.exception.ResourceNotFoundException;
import myproject.DDD2.mock.FakeUserRepository;
import myproject.DDD2.mock.TestClockHolder;
import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserUpdateRequest;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private FakeUserRepository userRepository;

    @BeforeEach
    void init(){
        userRepository = new FakeUserRepository();
        userService = UserService.builder()
                .clockHolder(new TestClockHolder(123123L))
                .userRepository(userRepository)
                .build();
        User user1 = User.builder()
                .userId(1L)
                .loginId("test1")
                .password("123")
                .age(10)
                .email("test1@naver.com")
                .userStatus(UserStatus.PRIVATE)
                .build();
        User user2 = User.builder()
                .userId(2L)
                .loginId("test2")
                .password("321")
                .age(10)
                .email("test2@naver.com")
                .userStatus(UserStatus.PUBLIC)
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void userCreateRequest를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .loginId("test")
                .password("111")
                .age(10)
                .email("test@naver.com")
                .build();
        //when
        User result = userService.createUser(userCreateRequest);

        //then
        assertThat(result.getUserId()).isNotNull();
        assertThat(result.getUserStatus()).isEqualTo(UserStatus.PRIVATE);
    }

    @Test
    void login시_회원상태가_PUBLIC_이다() {
        //given
        String loginId = "test1";
        String password = "123";

        //when
        userService.login(loginId, password);

        //then
        Optional<User> optionalUser = userRepository.findByLoginIdAndPassword(loginId, password);
        assertThat(optionalUser).isPresent();

        User user = optionalUser.get();
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.PUBLIC);
    }

    @Test
    void login은_회원ID_비밀번호가_일치하지_않으면__동작X() {
        String loginId = "test1";
        String password = "111";

        assertThatThrownBy(() ->{
            userService.login(loginId, password);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userUpdateRequest에_포함된비밀번호가_틀리다면_이메일_수정이_불가능_하다(){
        //given(상황환경 세팅)
        userService.login("test1", "123");

        UserUpdateRequest update = UserUpdateRequest.builder()
                .email("edit@naver.com")
                .password("111")
                .build();

        //when(상황발생)


        //then(검증)
        assertThatThrownBy(() -> {
            userService.editEmail(1L, update);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void userUpdateRequest로_이메일_수정은_PUBLIC_상태만_가능_하다(){
        //given(상황환경 세팅)
        UserUpdateRequest update = UserUpdateRequest.builder()
                .email("edit@naver.com")
                .password("123")
                .build();

        userService.login("test1","123");

        //when(상황발생)
        userService.editEmail(1L, update);

        //then(검증)
        Optional<User> optionalUser = userRepository.findByLoginIdAndPassword("test1", "123");
        assertThat(optionalUser).isPresent();

        User user = optionalUser.get();
        assertThat(user.getEmail()).isEqualTo("edit@naver.com");
    }

    @Test
    void userUpdateRequest로_이메일_수정은_PRIVATE_상태는_불가능_하다(){
        //given(상황환경 세팅)
        UserUpdateRequest update = UserUpdateRequest.builder()
                .email("edit@naver.com")
                .password("123")
                .build();

        //when(상황발생)

        //then(검증)
        assertThatThrownBy(() -> {
            userService.editEmail(1L, update);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 회원가입시_중복된_이메일은_불가능_하다(){
        //given(상황환경 세팅)
        UserCreateRequest create = UserCreateRequest.builder()
                .loginId("create")
                .password("111")
                .age(10)
                .email("test1@naver.com")
                .build();
        //when(상황발생)

        //then(검증)
        assertThatThrownBy(() -> {
            userService.createUser(create);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 이메일_수정시_중복된_이메일은_불가능_하다(){
        //given(상황환경 세팅)
        UserUpdateRequest update = UserUpdateRequest.builder()
                .email("test2@naver.com")
                .password("123")
                .build();

        //when(상황발생)

        //then(검증)
        assertThatThrownBy(() -> {
            userService.editEmail(1L, update);
        }).isInstanceOf(IllegalStateException.class);
    }
}