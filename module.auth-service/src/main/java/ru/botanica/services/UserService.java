package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.botanica.dto.UserDto;
import ru.botanica.entities.Role;
import ru.botanica.entities.User;
import ru.botanica.repositories.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void createNewUser(UserDto userDto){
        Role role = roleService.getUserRole("ROLE_USER");
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsBanned(false);
        user.setIsActive(true);
        user.setRoles(List.of(role));
        userRepository.save(user);
    }
    @Transactional
    public void activeUser(UserDto userDto){
        User user = findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userDto.getUsername())));
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Transactional
    public void bannedUser(UserDto userDto){
        User user = findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userDto.getUsername())));
        user.setIsBanned(!user.getIsBanned());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDto userDto){
        User user = findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userDto.getUsername())));
        userRepository.delete(user);
    }





}