package com.Pharmacie.pharma.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.pharma.Model.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {

}
