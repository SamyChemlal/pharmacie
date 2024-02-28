package com.Pharmacie.pharma.Autre;

public class CartRequest {
   private String   Produit ;
    public String getProduit() {
	return Produit;
}

public void setProduit(String produit) {
	Produit = produit;
}

	@Override
public String toString() {
	return "PanierRequest [Produit=" + Produit + ", userId=" + userId + ", quantite=" + quantite + ", Prix="
			+ Prix + "]";
}

	private String userId;
    private int quantite;
    private long Prix ;

    public long getPrix() {
		return Prix;
	}

	public void setPrix(long prix) {
		Prix = prix;
	}

	public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
