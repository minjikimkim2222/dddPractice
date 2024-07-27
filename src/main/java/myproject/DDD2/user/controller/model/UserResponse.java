package myproject.DDD2.user.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final long id;
    private final String loginId;
    private final int age;
    private final String email;
}
