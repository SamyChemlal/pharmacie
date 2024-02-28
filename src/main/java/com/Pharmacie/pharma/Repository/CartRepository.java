package com.Pharmacie.pharma.Repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

	Optional<Cart> findByUserAndNom(User user, String nomProduit);
	List<Cart> findByUser_Id(String userId);


	@Modifying
	@Transactional
	@Query("DELETE FROM Cart c WHERE c.user.id = :userId AND c.nom = :nomProduit")
	void supprimerCommandeByIdAndNom(@Param("userId") String userId, @Param("nomProduit") String nomProduit);

	 @Transactional
	    void deleteByUserId(String userId);
}



