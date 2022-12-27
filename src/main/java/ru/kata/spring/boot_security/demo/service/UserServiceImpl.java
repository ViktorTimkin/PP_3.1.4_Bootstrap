package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public void editUser(User user) {
        userRepository.editUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    //========================================================================
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return userRepository.getUserByUsername(username);
    }
}
