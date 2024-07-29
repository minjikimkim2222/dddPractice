package myproject.DDD2.user.model;

import myproject.DDD2.mock.TestClockHolder;
import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserUpdateRequest;
import myproject.DDD2.user.converter.UserConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void UserCreateRequest로_객체를_생성할_수_있다(){
        //given
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .loginId("kin123")
                .password("123")
                .age(10)
                .email("kin@naver.com")
                .build();

        //when
        User user = UserConverter.toUser(userCreateRequest);

        //then
        assertThat(user.getUserId()).isNull();
        assertThat(user.getLoginId()).isEqualTo("kin123");
        assertThat(user.getPassword()).isEqualTo("123");
        assertThat(user.getAge()).isEqualTo(10);
        assertThat(user.getEmail()).isEqualTo("kin@naver.com");
    }

    @Test
    void 비밀번호_일치O_UserUpdateRequest로_유저_이메일을_수정할_수_있다(){
        //given
        User user = User.builder()
                .userId(1L)
                .loginId("kin123")
                .password("123")
                .age(10)
                .email("before@naver.com")
                .build();

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email("edit@naver.com")
                .password("123")
                .build();

        //when
        user = user.editEmail(userUpdateRequest);

        //then
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getLoginId()).isEqualTo("kin123");
        assertThat(user.getPassword()).isEqualTo("123");
        assertThat(user.getAge()).isEqualTo(10);
        assertThat(user.getEmail()).isEqualTo("edit@naver.com");
    }

    @Test
    void 비밀번호_일치X_UserUpdateRequest로_유저_이메일을_수정할_수_없다(){
        //given
        User user = User.builder()
                .userId(1L)
                .loginId("kin123")
                .password("123")
                .age(10)
                .email("before@naver.com")
                .build();

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email("edit@naver.com")
                .password("321")
                .build();

        //when


        //then
        assertThrows(IllegalStateException.class, () -> {
            user.editEmail(userUpdateRequest);
        });
        assertThat(user.getEmail()).isNotEqualTo("edit@naver.com");
    }

    @Test
    void 유저로그인시_lastLoginAt은_변경된다(){
        //given(상황환경 세팅)
        User user = User.builder()
                .userId(1L)
                .loginId("kin123")
                .password("123")
                .age(10)
                .email("before@naver.com")
                .build();


        //when(상황발생)
        user = user.login(new TestClockHolder(123123123L));

        //then(검증)
        assertThat(user.getLastLoginAt()).isEqualTo(123123123L);
    }
}