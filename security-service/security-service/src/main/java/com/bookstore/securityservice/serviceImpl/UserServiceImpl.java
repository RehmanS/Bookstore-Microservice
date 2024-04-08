package com.bookstore.securityservice.serviceImpl;

import com.bookstore.securityservice.entities.User;
import com.bookstore.securityservice.repos.UserRepository;
import com.bookstore.securityservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user, long id) {
        Optional<User> user1 = userRepository.findById(id); // isPresent vasitesile yoxlamaq ucun Optional lazimdi
        if (user1.isPresent()) {
            User foundUser = user1.get();
            foundUser.setUsername(user.getUsername());
            foundUser.setPassword(user.getPassword());
            foundUser.setAvatarId(user.getAvatarId());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    @Override
    public User getOneUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}
