package com.social.socialnetwork.service;

import com.social.socialnetwork.model.User;
import com.social.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
      return userRepository.findAll();
    }

    public ResponseEntity<User> getUserById (long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public User setUser (User user) {

//        if(user.getFullName().equals("")) {
//            throw new NullPointerException();
//        } else {
//            return userRepository.save(user);
//        }
        return userRepository.save(user);
    }
}
