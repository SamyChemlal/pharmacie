package com.Pharmacie.pharma.Model;


public class FactureDTO {
    private Long id;
    private double valeur;
    private UserDTO user;
    private Integer quantites;
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Integer getQuantites() {
		return quantites;
	}
	public void setQuantites(Integer quantites) {
		this.quantites = quantites;
	}
	public FactureDTO(Long id, double valeur, UserDTO user, Integer quantites) {
		super();
		this.id = id;
		this.valeur = valeur;
		this.user = user;
		this.quantites = quantites;
	}
	public  FactureDTO() {};
    
}
