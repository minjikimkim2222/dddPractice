package myproject.DDD2.user.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequest {

    private final String email;
}
