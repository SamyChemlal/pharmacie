import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../produit.service';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';

@Component({
  selector: 'app-pharmacie',
  templateUrl: './pharmacie.component.html',
  styleUrls: ['./pharmacie.component.css']
})
export class PharmacieComponent  implements OnInit {
  isAdmin: boolean = false;
  userId: string = '';
  successMessage: string = '';
  errorMessage: string = '';
  prodsupp: string = '';

  constructor(
    private produitservice: ProduitService,
    private route: Router,
    private connexionService: ConnexionService
  ) {}

  redirigerVersAjoutProduit() {
    this.route.navigate(['/Produit']);
  }

  deleteProduct() {
    const productId: string | null = prompt(
      "Veuillez saisir l'ID du produit à supprimer:"
    );
    if (productId !== null && /^\d+$/.test(productId)) {
      this.prodsupp = productId;

      this.produitservice.supp(this.prodsupp).subscribe(
        () => {
          this.successMessage = 'Produit supprimé avec succès';
          this.errorMessage = ''; 
          this.prodsupp = '';
        },
        (error: { status: number; }) => {
          if (error.status === 404) {
            this.errorMessage = "Le produit n'existe pas";
            this.successMessage = '';
          } else {
            console.error('Erreur lors de la suppression du produit : ', error);
          }
        }
      );
    } else if (productId !== null) {
      this.errorMessage =
        'Veuillez saisir un ID valide composé uniquement de chiffres.';
      this.successMessage = '';
    } else {
      this.errorMessage = '';
      this.successMessage = '';
    }
  }

  ngOnInit() {
    this.userId = this.connexionService.getUserId();
    this.connexionService.getRoleId(this.userId).subscribe(
      (response: any) => {
        const role = response.role;

        if (role === 'Admin') {
          this.isAdmin = true;
        } else {
          this.isAdmin = false;
        }
      },
      (error) => {
        console.error(
          'Erreur lors de la récupération du rôle du user : ',
          error
        );
      }
    );
  }
}

