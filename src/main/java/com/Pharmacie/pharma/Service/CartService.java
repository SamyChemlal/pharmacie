package com.Pharmacie.pharma.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Model.Cart;
import com.Pharmacie.pharma.Repository.UserRepository;
import com.Pharmacie.pharma.Repository.CartRepository;
import com.Pharmacie.pharma.Autre.CartRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;

	@Autowired
	public CartService(CartRepository cartRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
	}

	public Cart createC(Cart cart) {
		return cartRepository.save(cart);
	}

	public void ajouterC(CartRequest request) {
		String userId = request.getuserId();

		Optional<User> UserOp = userRepository.findById(userId);

		if (UserOp.isPresent()) {
			User user = UserOp.get();
			String nomProduit = request.getProduit();
			Optional<Cart> CartOp = cartRepository.findByUserAndNom(user, nomProduit);

			if (CartOp.isPresent()) {
				Cart cart = CartOp.get();
				cart.setQuantite(cart.getQuantite() + request.getQuantite());
				long prixTotal = cart.getQuantite() * cart.getPrix();
				cart.setPrixTotal(prixTotal);
				cartRepository.save(cart);

				System.out.println("Mise à jour réussie.");
			} else {
				Cart newCart = new Cart();
				newCart.setuser(user);
				newCart.setNom(request.getProduit());
				newCart.setQuantite(request.getQuantite());
				newCart.setPrix(request.getPrix());
				long prixTotal= newCart.getQuantite() * newCart.getPrix();
				newCart.setPrixTotal(prixTotal);
				cartRepository.save(newCart);

				System.out.println("Ajout d'un nouveau produit réussi.");
			}
		} else {
			System.err.println("User non trouvé avec l'ID : " + userId);
		}
	}
	public List<Cart> getCommandesUser(String userId) {
		List<Cart> commandes = cartRepository.findByUser_Id(userId);

		if (commandes.isEmpty()) {
			
			System.out.println("Aucune commande trouvée pour le user avec l'ID : " + userId);
			return Collections.emptyList(); 
		}

		return commandes;
	}
	@Transactional
	public void supprimerCommande(String userId, String nomProduit) {
		cartRepository.supprimerCommandeByIdAndNom(userId, nomProduit);
	}
	@Transactional
    public void supprimerC(String userId) {
        
        cartRepository.deleteByUserId(userId);
    }


}
