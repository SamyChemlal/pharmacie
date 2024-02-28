package com.Pharmacie.pharma.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Facture")
public class Facture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
    @Column(name = "valeur")
    private double valeur;

    @ManyToOne
    @JoinColumn(name = "user_id")  
    private User user;


    @Column(name = "quantites")
    private Integer quantites;

    
    public Facture() {}

    public Facture(double valeur, User user, Integer quantites) {
        this.valeur = valeur;
        this.user = user;
        this.quantites = quantites;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getQuantites() {
		return quantites;
	}

	public void setQuantites(Integer quantites) {
		this.quantites = quantites;
	}
}
