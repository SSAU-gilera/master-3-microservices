package com.userservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/exists/{userId}")
    public Boolean doesUserExist(@PathVariable("userId") Integer userId) {
        return userService.doesUserExist(userId);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable("userId") Integer userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            String userInformation = userDTO.getUserId() + " - " + userDTO.getName() + " (" + userDTO.getEmail() + ") - " + userDTO.getCompanyName();
            return ResponseEntity.ok(userInformation);
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @GetMapping("/{userId}/name")
    public String getUserNameByID(@PathVariable("userId") Integer userId) {
        try {
            return userService.getUserNameByID(userId);
        } catch (ResponseStatusException ex) {
            return "not found";
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok("User successfully created");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @PostMapping("/enable")
    public ResponseEntity<String> enableUser(@RequestBody UserDTO userDTO) {
        try {
            userService.changeUserEnabledField(userDTO.getUserId(),true);
            return ResponseEntity.ok("User successfully enabled");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @PostMapping("/disable")
    public ResponseEntity<String> disableUser(@RequestBody UserDTO userDTO) {
        try {
            userService.changeUserEnabledField(userDTO.getUserId(), false);
            return ResponseEntity.ok("User successfully disabled");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @PostMapping("/change/name")
    public ResponseEntity<String> changeUserName(@RequestBody UserDTO userDTO) {
        try {
            userService.changeUserName(userDTO.getUserId(),userDTO.getName());
            return ResponseEntity.ok("User name successfully updated");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @PostMapping("/change/email")
    public ResponseEntity<String> changeUserEmail(@RequestBody UserDTO userDTO) {
        try {
            userService.changeUserEmail(userDTO.getUserId(),userDTO.getEmail());
            return ResponseEntity.ok("User email successfully updated");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @PostMapping("/change/company")
    public ResponseEntity<String> changeUserCompany(@RequestBody UserDTO userDTO) {
        try {
            userService.changeUserCompany(userDTO.getUserId(),userDTO.getCompanyId());
            return ResponseEntity.ok("User company successfully updated");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

}