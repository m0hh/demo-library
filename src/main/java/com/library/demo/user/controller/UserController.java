package com.library.demo.user.controller;

import com.library.demo.exeption.ApiRequestException;
import com.library.demo.security.jwt.util.JWTUtil;
import com.library.demo.user.entity.User;
import com.library.demo.user.entity.UserAdminSaveDTO;
import com.library.demo.user.entity.UserRequest;
import com.library.demo.user.entity.UserResponse;
import com.library.demo.user.mapper.UserAdminMapper;
import com.library.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil util;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserAdminMapper mapper;

    @Value("${app.user.key}")
    private  String user_key;


    @PostMapping("/signup_admin")
    public ResponseEntity<String> saveUserAdmin(@RequestBody @Valid UserAdminSaveDTO userDTO, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            String errorMessage = String.join(", ", errors);
            throw new ApiRequestException(errorMessage);
        }


        if (!userDTO.getKey().equals(user_key)){
            throw new ApiRequestException("Your Key is incorrect");
        }

        User user = new User();
        mapper.mapUserAdminToUser(userDTO, user);

        Set<String> roles = new HashSet<String>();
        roles.add("Admin");
        user.setRoles(roles);
        Integer id = userService.saveUser(user);
        String message= "User with id '"+id+"' saved succssfully!";
        return ResponseEntity.ok(message);
    }


    @PostMapping("/login_user")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        String token =util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token,"Token generated successfully!"));
    }
}
