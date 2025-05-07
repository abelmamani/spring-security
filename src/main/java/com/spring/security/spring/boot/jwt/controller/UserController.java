package com.spring.security.spring.boot.jwt.controller;

import com.spring.security.spring.boot.jwt.dto.CreateUserDTO;
import com.spring.security.spring.boot.jwt.dto.UpdateUserDTO;
import com.spring.security.spring.boot.jwt.service.UserService;
import com.spring.security.spring.boot.jwt.utils.ResponseEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            userService.save(createUserDTO);
            return ResponseEntity.ok("Usuario Creado exitosamente");
        }catch (Exception e){
            return ResponseEntityManager.badRequest(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateeUser(@PathVariable("id") Long id, @RequestBody UpdateUserDTO updateUserDTO){
        try{
            userService.update(id, updateUserDTO);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        }catch (Exception e){
            return ResponseEntityManager.badRequest(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id){
        try{
            userService.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntityManager.badRequest(e.getMessage());
        }
    }
}
