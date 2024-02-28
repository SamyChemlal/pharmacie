package com.Pharmacie.pharma.Model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Users")
public class User {
	 @Id
	    @GeneratedValue(generator = "uuid2")
	    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	    @Column(name = "id")
	    private String id;

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "email")
    private String email;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "psw")
    private String psw;
    
    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "role")  
    private String role;
    
    public List<Cart> getCart() {
		return carts;
	}

	public void setCart(List<Cart> carts) {
		this.carts = carts;
	}

	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    
    private List<Facture> factures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

   
}
