package com.Pharmacie.pharma.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.pharma.Model.Facture;
import com.Pharmacie.pharma.Model.FactureDTO;
import com.Pharmacie.pharma.Service.FactureService;


@RestController
@RequestMapping("/api/facture")
public class FactureController {
	private final FactureService factureService;

	@Autowired
	public FactureController(FactureService factureService) {
		this.factureService = factureService;
	}
	@PostMapping("/Ajouter-Facture/{userId}")
	public ResponseEntity<Map<String, Object>> ajouterFacture(
	        @RequestBody Map<String, Object> payload,
	        @PathVariable String userId) {
	    try {
	        Double prixTotal = ((Number) payload.get("prixTotal")).doubleValue();
	        Integer quantiteTotale = ((Number) payload.get("quantiteTotale")).intValue();

	        Map<String, Object> response = factureService.ajouterFacture(userId, prixTotal, quantiteTotale);
	        return ResponseEntity.ok().body(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", "Erreur lors de l'ajout de la facture: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}



	  @GetMapping("/recuperer/{factureId}")
	    public ResponseEntity<FactureDTO> LaFacture(@PathVariable Long factureId) {
	        try {
	            Facture facture = factureService.recupererFacture(factureId);
	            if (facture != null) {
	                FactureDTO factureDTO = factureService.convertToDTO(facture);
	                return ResponseEntity.ok().body(factureDTO);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).build();
	        }
	  }}



