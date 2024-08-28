package com.vgipl.service.impl;

import com.vgipl.model.User;
import com.vgipl.repository.UserRepository;
import com.vgipl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.getUserById(id);
    }



    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        if(existingUserOptional.isPresent()){
            User existingUser = existingUserOptional.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName((user.getLastName()));
            existingUser.setEmail(user.getEmail());
            String password = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(password);
            existingUser.setCity(user.getCity());
            existingUser.setState(user.getState());

            return addUser(existingUser);

        }else {
            return null;
        }
    }

}
