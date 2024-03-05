package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.dto.UserDTO;

public interface UserService {
    void regist(UserDTO userDTO);

    void registUser(UserDTO userDTO);
}
