package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.aggregate.UserEntity;
import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void regist(UserDTO userDTO) {

    }

    @Transactional
    @Override
    public void registUser(UserDTO userDTO) {

        /* 설명. userId가 비어있는 상태인데 UUID를 활용하여 서버측에서 회원의 고유한 아이디를 생성한다. */
        userDTO.setUserId(UUID.randomUUID().toString());

        /* 설명. 필드값이 정확히 일치할 때만 매칭하기 위해 STRICT 모드 상태로 modelMapper를 설정한다.(STANDARD -> STRICT) */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

        userEntity.setEncryptedPwd("암호화된 비밀번호");

        userRepository.save(userEntity);

    }
}
