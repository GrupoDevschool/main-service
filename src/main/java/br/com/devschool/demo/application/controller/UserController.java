package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.User;
import br.com.devschool.demo.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // show
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id){
        return ResponseEntity.ok(userService.findUserId(id));
    }

    // post
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User User){
        return ResponseEntity.ok(userService.createUser(User));
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User User) {
        return ResponseEntity.ok(userService.updateUser(id, User));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUserId(id);
        return ResponseEntity.ok().build();
    }

}
