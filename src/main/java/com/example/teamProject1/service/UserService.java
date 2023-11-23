package com.example.teamProject1.service;

import com.example.teamProject1.model.RoleType;
import com.example.teamProject1.model.User;
import com.example.teamProject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinUser(User user){
        String rawPassword=user.getPassword(); //1234
        String encPassword=bCryptPasswordEncoder.encode(rawPassword);//해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

//    @Transactional
//    public void modiUser(User user){
//       User persistence= userRepository.findById(user.getId()).orElseThrow(()->{
//           return new IllegalArgumentException("회원 찾기 실패");
//       });
//        String rawPassword=user.getPassword(); //1234
//        String encPassword=bCryptPasswordEncoder.encode(rawPassword);//해쉬
//        persistence.setPassword(encPassword);
//        persistence.setEmail(user.getEmail());
//
//    }
    /*@Transactional(readOnly = true)
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
