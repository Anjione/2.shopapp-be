package com.example.shopapp.service.impl;

import com.example.shopapp.component.JwtTokenUtil;
import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.entity.Role;
import com.example.shopapp.entity.User;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.PermissionDenyException;
import com.example.shopapp.repository.RoleRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    private final ModelMapper mapper;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exist");
        }
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
        if (role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register admin account");
        }
        User newUser = mapper.map(userDTO, User.class);
        newUser.setRole(role);

        if (userDTO.getFacebookAccId() == 0 && userDTO.getGoogleAccId() == 0) {
            String password = userDTO.getPassword();
            String encodeedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodeedPassword);
        }
        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));
        if (user.isEmpty()) {
            throw new DataNotFoundException("Invalid phonenumber / password");
        }
        User existingUser = user.get();
        //check password
        if (existingUser.getFacebookAccId() == 0 && existingUser.getGoogleAccId() == 0) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new DataNotFoundException("Wrong phonenumber or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());
//        authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
