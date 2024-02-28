package com.Pharmacie.pharma.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "Nom")
	private String nom;
	@Column(name = "Quantite")
	private long Quantite;
	@Column(name = "Prix")
	private long Prix;
	@Column(name = "prix_total")
	private long PrixTotal;
	public long getPrixTotal() {
		return PrixTotal;
	}
	public void setPrixTotal(long prixTotal) {
		PrixTotal = prixTotal;
	}
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;
	public User getuser() {
		return user;
	}
	public void setuser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public long getQuantite() {
		return Quantite;
	}
	public void setQuantite(long quantite) {
		Quantite = quantite;
	}
	public long getPrix() {
		return Prix;
	}
	public void setPrix(long prix) {
		Prix = prix;
	}
	public Cart(Long id, String nom, long quantite, long prix) {
		super();
		this.id = id;
		this.nom = nom;
		Quantite = quantite;
		Prix = prix;
	}
	@Override
	public String toString() {
		return "Panier [id=" + id + ", nom=" + nom + ", Quantite=" + Quantite + ", Prix=" + Prix + "]";
	}
public Cart() {};

}
