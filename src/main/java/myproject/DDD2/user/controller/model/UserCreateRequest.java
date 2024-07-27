package myproject.DDD2.user.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateRequest {

    private final String loginId;

    private final String password;

    private final int age;

    private final String email;
}
