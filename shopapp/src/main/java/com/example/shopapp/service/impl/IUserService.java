package com.example.shopapp.service.impl;

import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.entity.Role;
import com.example.shopapp.entity.User;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.repository.RoleRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private ModelMapper mapper;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exist");
        }
        User newUser = mapper.map(userDTO, User.class);
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRole(role);
        if (userDTO.getFacebookAccId() == 0 && userDTO.getGoogleAccId() == 0) {
            String password = userDTO.getPassword();
        }
        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(String phoneNumber, String password) {

        return null;
    }
}
