package com.Pharmacie.pharma.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Pharmacie.pharma.Autre.CustomUserDetails;
import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> clientOptional = userRepository.findByEmail(email);

        if (clientOptional.isPresent()) {
            User user = clientOptional.get();
            System.out.println("ID du client chargé depuis la base de données : " + user.getId());

            return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPsw(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email);
        }
    }

}
