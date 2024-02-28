package com.Pharmacie.pharma.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.pharma.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email); 
    Optional<User> findByNom(String Nom); 
    Optional<User> findByEmailAndPsw(String email, String psw);
	
}
