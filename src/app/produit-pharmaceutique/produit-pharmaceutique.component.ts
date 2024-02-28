import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';
import { ProduitService } from '../produit.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-produit-pharmaceutique',
  templateUrl: './produit-pharmaceutique.component.html',
  styleUrls: ['./produit-pharmaceutique.component.css']
})
export class ProduitPharmaceutiqueComponent implements OnInit {
  produits: any[] = [];

  selectedProduit: any;
  quantiteSelectionnee: number = 1;
  messageQuantite: string | null = null;

  constructor(
    private produitService: ProduitService,
    private connexionService: ConnexionService,
    private cartService: CartService
  ) {}

  ngOnInit() {
    this.produitService.getProduits().subscribe(
      (data) => {
        this.produits = data;
      },
      (erreur) => {
        console.error('Erreur lors de la récupération des produits:', erreur);
      }
    );
  }

  ajouterAuPanier(produit: any): void {
    this.selectedProduit = produit;
    this.quantiteSelectionnee = 1;
    this.messageQuantite = null;
  }

  validerC(): void {
    if (
      this.quantiteSelectionnee &&
      this.quantiteSelectionnee >= 0 &&
      this.quantiteSelectionnee <= this.selectedProduit.quantite
    ) {
      const userId = this.connexionService.getUserId();
      const { nom, prix } = this.selectedProduit;

      this.cartService
        .ajouterAuPanier(nom, this.quantiteSelectionnee, prix, userId)
        .subscribe(
          (data: any) => {
            console.log('Ajout réussi :', data);
          },
          (erreur: any) => {
            console.error("Erreur lors de l'ajout :", erreur);
          }
        );
    } else if (this.quantiteSelectionnee < 0) {
      this.messageQuantite = 'Veuillez sélectionner une quantité positive.';
    } else {
      this.messageQuantite =
        'La quantité sélectionnée est supérieure à la quantité en stock.';
    }
  }
}
