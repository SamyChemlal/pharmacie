package com.Pharmacie.pharma;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.Pharmacie.pharma.Service.ProduitService;

@SpringBootApplication
@EntityScan(basePackages = "com.Pharmacie.pharma.Model")
public class PharmaApplication {
	@Autowired
    private ProduitService produitService;
	public static void main(String[] args) {
		SpringApplication.run(PharmaApplication.class, args);
	}
	 @PostConstruct
	    public void init() {
	       
	        produitService.genererProduitsPharmaceutiques();
	    }
}
