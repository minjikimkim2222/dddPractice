package myproject.DDD2.user.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    PUBLIC("공개"),
    PRIVATE("비공개");

    private String description;
}
