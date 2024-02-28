package com.Pharmacie.pharma.Service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacie.pharma.Model.Produit;
import com.Pharmacie.pharma.Repository.ProduitRepository;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    @Autowired
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }


    public void genererProduitsPharmaceutiques() {
        List<String> nomsProduits = Arrays.asList("Paracétamol", "Aspirine", "Ibuprofène", "Codéine", "Amoxicilline", "Morphine", "Loratadine", "Ranitidine", "Méthamphétamine", "Omeprazole");

        for (String nomProduit : nomsProduits) {
            long quantite = (long) (Math.random() * 100) + 50; 
            double prix = Math.round((Math.random() * 50 + 5) * 100) / 100; 

            if (!produitRepository.existsByNom(nomProduit)) {
                Produit produit = new Produit();
                produit.setNom(nomProduit);
                produit.setQuantite(quantite);
                produit.setPrix(prix);

                produitRepository.save(produit);
            }
        }
    }
    public List<Produit> getListeProduits() {
        return produitRepository.findAll();
    }
    public void decrementerQuantiteProduit(String nomProduit, long quantiteDecrementee) {
        Produit produit = produitRepository.findByNom(nomProduit);

        if (produit != null) {
            long nouvelleQuantite = produit.getQuantite() - quantiteDecrementee;

            if (nouvelleQuantite >= 0) {
                produit.setQuantite(nouvelleQuantite);
                produitRepository.save(produit);
            } else {
                throw new RuntimeException("Quantité insuffisante pour le produit : " + nomProduit);
            }
        } else {
            throw new RuntimeException("Produit introuvable : " + nomProduit);
        }
    }

    public void ajouterProduit(Produit nouveauProduit) {
       
        Produit produitExist = produitRepository.findByNom(nouveauProduit.getNom());

        if (produitExist != null) {
            throw new RuntimeException("Le produit avec le nom " + nouveauProduit.getNom() + " existe déjà.");
        } else {
            
            produitRepository.save(nouveauProduit);
        }
    }
    public boolean existsById(Long id) {
        return produitRepository.existsById(id);
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

}
