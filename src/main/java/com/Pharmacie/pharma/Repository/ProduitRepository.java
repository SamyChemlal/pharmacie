package com.Pharmacie.pharma.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.pharma.Model.Produit;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	boolean existsByNom(String nom);

    Produit findByNom(String nomProduit);
}

