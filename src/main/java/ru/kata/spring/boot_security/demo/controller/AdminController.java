package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminController(UserServiceImpl userService,
                           RoleServiceImpl roleService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/allUsers") //
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("userAdmin", userService.loadUserByUsername(principal.getName()));
        List<User> user = userService.getAllUsers();
        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "allUsers";
    }

    /*@GetMapping("add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }*/

    @PostMapping("/add")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") String role) {

        /*Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(2L));

        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleById(1L));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));*/

        if(role.equals("ROLE_USER")) {
            user.setRoles(Set.of(roleService.getRoleById(2L)));
        } else if(role.equals("ROLE_ADMIN")) {
            user.setRoles(Set.of(roleService.getRoleById(1L)));
        }
        userService.addUser(user);

        return "redirect:/admin/allUsers";
    }

/*    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }*/

    @PostMapping("/edit/{id}")
    public String userUpdate(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(role.equals("ROLE_USER")) {
            user.setRoles(Set.of(roleService.getRoleById(2L)));
        } else if(role.equals("ROLE_ADMIN")) {
            user.setRoles(Set.of(roleService.getRoleById(1L)));
        }
        userService.editUser(user);
        return "redirect:/admin/allUsers";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/allUsers";
    }
}