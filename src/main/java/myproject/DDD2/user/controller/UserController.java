package myproject.DDD2.user.controller;


import lombok.RequiredArgsConstructor;
import myproject.DDD2.user.controller.model.UserCreateRequest;
import myproject.DDD2.user.controller.model.UserResponse;
import myproject.DDD2.user.controller.model.UserUpdateRequest;
import myproject.DDD2.user.converter.UserConverter;
import myproject.DDD2.user.model.User;
import myproject.DDD2.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestBody UserCreateRequest request
            ){
        userService.createUser(request);
        return ResponseEntity
                .ok()
                .body("가입 완료");
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsersByAge(
            @RequestParam int age
    ){
        List<UserResponse> responseList = userService.findAllByAge(age).stream()
                .map(UserConverter::toResponse)
                .toList();

        return ResponseEntity
                .ok()
                .body(responseList);
    }

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
        return ResponseEntity
                .ok()
                .body(UserConverter.toResponse(userService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmail(
            @PathVariable long id,
            @RequestBody UserUpdateRequest request
    ){
        userService.editEmail(id, request);
        return ResponseEntity
                .ok()
                .body("수정 완료");
    }

}