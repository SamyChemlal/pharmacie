package com.Pharmacie.pharma.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Model.UserDTO;
import com.Pharmacie.pharma.Model.Facture;
import com.Pharmacie.pharma.Model.FactureDTO;
import com.Pharmacie.pharma.Repository.UserRepository;
import com.Pharmacie.pharma.Repository.FactureRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class FactureService {

	private final FactureRepository factureRepository;
	private final UserRepository userRepository;

	@Autowired
	public FactureService(FactureRepository factureRepository, UserRepository userRepository) {
		this.factureRepository = factureRepository;
		this.userRepository = userRepository;
	}

	@Transactional
    public Map<String, Object> ajouterFacture(String clientId, double prixTotal, int quantiteTotale) {
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID : " + clientId));
        System.out.println("ClientId: " + clientId);
        System.out.println("PrixTotal: " + prixTotal);
        System.out.println("QuantiteTotale: " + quantiteTotale);

        Facture facture = new Facture();
        facture.setValeur(prixTotal);
        facture.setQuantites(quantiteTotale);
        facture.setUser(user);

        Facture savedFacture = factureRepository.save(facture);

        Map<String, Object> response = new HashMap<>();
        response.put("factureId", savedFacture.getId());
        response.put("message", "Facture ajoutée avec succès");
        return response;
    }
	public Facture recupererFacture(Long factureId) {
	    return factureRepository.findById(factureId).orElse(null);
	}

	 public FactureDTO convertToDTO(Facture facture) {
	        FactureDTO factureDTO = new FactureDTO();
	        factureDTO.setId(facture.getId());
	        factureDTO.setValeur(facture.getValeur());
	        factureDTO.setQuantites(facture.getQuantites());
	        UserDTO userDTO = new UserDTO();
	        userDTO.setNom(facture.getUser().getNom());
	        userDTO.setEmail(facture.getUser().getEmail());
	        userDTO.setPrenom(facture.getUser().getPrenom());

	        factureDTO.setUser(userDTO);

	        return factureDTO;
	    }
}
