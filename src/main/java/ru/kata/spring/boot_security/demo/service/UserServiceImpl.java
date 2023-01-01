package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*@Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }*/
    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Transactional
    @Override
    public void editUser(User user) {
        userRepository.editUser(user);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    //========================================================================
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return userRepository.getUserByUsername(username);
    }
}
