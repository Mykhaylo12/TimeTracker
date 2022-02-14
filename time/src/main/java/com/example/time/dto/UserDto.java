package com.example.time.dto;

import com.example.time.model.User;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String token;
    private Integer rate;

    public static UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        if (user != null) {
            userDto.setId(String.valueOf(user.getId()));
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setRate(user.getRate());
        }
        return userDto;
    }

    public static User dtoToUser(UserDto userDto) {
        User user = new User();
        if (userDto != null) {
            user.setId(Long.parseLong(userDto.getId()));
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setRate(userDto.getRate());
        }
        return user;
    }

}
