package com.Pharmacie.pharma.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.pharma.Model.Cart;
import com.Pharmacie.pharma.Autre.CartRequest;
import com.Pharmacie.pharma.Service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterC(@RequestBody CartRequest request) {
    	System.out.print(request.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            cartService.ajouterC(request);
            return ResponseEntity.ok("{\"message\": \"Ajout réussi.\"}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Utilisateur non authentifié.\"}");
        }
    }
    @GetMapping("/commandes-user/{userId}")
    public ResponseEntity<List<Cart>> getCommandesUser(@PathVariable String userId) {
        System.out.println("Received usertId in controller: " + userId);

        try {
            List<Cart> commandes = cartService.getCommandesUser(userId);
            return ResponseEntity.ok(commandes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    @DeleteMapping("/supprimer-commande/{userId}/{nomProduit}")
    public ResponseEntity<Map<String, String>> supprimerCommande(
            @PathVariable String userId,
            @PathVariable String nomProduit) {
        try {
            cartService.supprimerCommande(userId, nomProduit);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Commande supprimée avec succès.");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Erreur lors de la suppression de la commande.");
            return ResponseEntity.status(500).body(errorMap);
        }
    }
    @DeleteMapping("/supprimer-cart/{userId}")
    public ResponseEntity<Map<String, String>> supprimerC(@PathVariable String userId) {
        try {
            cartService.supprimerC(userId);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "supprimé avec succès.");

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();

           
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Erreur lors de la suppression.");

            return ResponseEntity.status(500).body(errorMap);
        }
    }
   

}




