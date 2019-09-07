package com.iee.webbase.modelConvertDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /** 改造前的addUser方法 */
    @PostMapping
    public User addUser(UserDTO userInputDTO){
        User user = new User();
        user.setUsername(userInputDTO.getUsername());
        user.setAge(userInputDTO.getAge());
        return userService.addUser(user);
    }

    /** 改造后的addUser方法 */
    @GetMapping("/test")
    public UserDTO addUser2(UserDTO userDTO){
        User user =  userDTO.convertToUser();
        User saveResultUser = userService.addUser(user);
        UserDTO result = userDTO.convertFor(saveResultUser);
        return result;
    }
}
