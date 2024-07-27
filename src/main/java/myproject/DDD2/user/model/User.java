package myproject.DDD2.user.model;

import lombok.*;
import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserUpdateRequest;

import java.util.Objects;


@Getter
@Builder
public class User {

    private final Long userId;

    private final String loginId;

    private final String password;

    private final int age;

    private final String email;

    public User login(){
        /***
         * 로그인시 발생하는 비지니스 로직수행
         * ex) 로그인시 age가 1증가하는 로직
         */
        return User.builder()
                .userId(userId)
                .loginId(loginId)
                .password(password)
                .age(age + 1)
                .email(email)
                .build();
    }

    public User editEmail(UserUpdateRequest request, String password){
        if(!Objects.equals(this.password, password)){
            throw new IllegalStateException("패스워드 불일치");
        }
        return User.builder()
                .userId(userId)
                .loginId(loginId)
                .password(password)
                .age(age + 1)
                .email(request.getEmail())
                .build();
    }
}
