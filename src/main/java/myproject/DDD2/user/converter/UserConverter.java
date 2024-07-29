package myproject.DDD2.user.converter;

import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserResponse;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.model.UserStatus;
import myproject.DDD2.user.repository.UserEntity;



public class UserConverter {
    public static User toUser(UserEntity userEntity){
        return User.builder()
                .userId(userEntity.getId())
                .age(userEntity.getAge())
                .email(userEntity.getEmail())
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .build();
    }

    public static User toUser(UserCreateRequest userCreateRequest){
        return User.builder()
                .age(userCreateRequest.getAge())
                .email(userCreateRequest.getEmail())
                .loginId(userCreateRequest.getLoginId())
                .password(userCreateRequest.getPassword())
                .userStatus(UserStatus.PRIVATE)
                .build();
    }


    public static UserEntity toUserEntity(User user){
        return UserEntity.builder()
                .id(user.getUserId())
                .age(user.getAge())
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .build();
    }

    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getUserId())
                .age(user.getAge())
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .build();
    }
}
