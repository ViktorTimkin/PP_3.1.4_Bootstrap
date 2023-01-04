package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.addRole(role);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }
}
