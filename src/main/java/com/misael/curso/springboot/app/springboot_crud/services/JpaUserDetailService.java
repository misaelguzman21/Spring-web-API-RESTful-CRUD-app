package com.misael.curso.springboot.app.springboot_crud.services;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misael.curso.springboot.app.springboot_crud.entities.User;
import com.misael.curso.springboot.app.springboot_crud.repositories.UserRepository;

@Service
public class  JpaUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true) 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("El usuario %s no existe en el sistema", username));
        }
        User user = optionalUser.orElseThrow();
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), 
                                                                      user.getPassword(), 
                                                                      user.isEnabled(),
                                                                      true,
                                                                      true,
                                                                      true,
                                                                      authorities);
    }
}

