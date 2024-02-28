package com.Pharmacie.pharma.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> existeNomUtilisateur(String nomUtilisateur) {
        return userRepository.findByNom(nomUtilisateur);
    }

    public Optional<User> verifierConnexion(String nomUtilisateur, String psw) {
        return userRepository.findByEmailAndPsw(nomUtilisateur, psw);
    }
   
    public String inscrire(User user) {
        User savedUser = createUser(user);
        return savedUser.getId(); 
    }

	public Optional<User> existeEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	
}
