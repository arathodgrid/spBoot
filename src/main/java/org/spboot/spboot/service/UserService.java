package org.spboot.spboot.service;

import org.spboot.spboot.model.User;
import org.spboot.spboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

        public User saveUser(User user) {
            return userRepository.save(user);
        }

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public void deleteUser(int id) {
            userRepository.deleteById(id);
        }
}
