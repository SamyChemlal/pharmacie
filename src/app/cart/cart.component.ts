import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ConnexionService } from '../connexion.service';
import { Location } from '@angular/common';
import { Facture } from '../facture';
import { Router } from '@angular/router';
import { ProduitService } from '../produit.service';
import { forkJoin } from 'rxjs';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  commandes: any[] = [];
  panierVide: boolean = false;
  totalForm: FormGroup;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private connexionService: ConnexionService,
    private formBuilder: FormBuilder,
    private location: Location,
    private produitService: ProduitService
  ) {
    this.totalForm = this.formBuilder.group({
      prixTotal: [0, Validators.required],
      quantiteTotale: [0, Validators.required],
    });
  }
  facture: Facture | null = null;
  ngOnInit(): void {
    this.getCommandesUser();
    this.initTotalForm();
  }

  getCommandesUser(): void {
    const userId = this.connexionService.getUserId();
    const url = `http://localhost:8080/api/cart/commandes-user/${userId}`;

    this.httpClient.get<any[]>(url).subscribe(
      (response: any[]) => {
        this.commandes = response.map((commande: any) => ({
          ...commande,
          editMode: false,
          commandeForm: this.initCommandeForm(commande),
        }));
        if (this.commandes.length === 0) {
          this.panierVide = true;
        } else {
          this.panierVide = false;
        }
        this.updateTotalForm();
      },
      (error: { error: { message: any; }; status: any; }) => {
        console.error('Erreur lors de la récupération des commandes : ', error);

        if (error.error instanceof ErrorEvent) {
          console.error('Erreur côté user : ', error.error.message);
        } else {
          console.error('Erreur côté serveur : ', error.status, error.error);
        }
      }
    );
  }

  initCommandeForm(commande: any): FormGroup {
    return this.formBuilder.group({
      nom: [commande.nom, Validators.required],
      prix: [commande.prix, Validators.required],
      quantite: [commande.quantite, Validators.required],
      prixTotal: [commande.prixTotal, Validators.required],
    });
  }

  initTotalForm(): void {
    this.totalForm = this.formBuilder.group({
      prixTotal: [0, Validators.required],
      quantiteTotale: [0, Validators.required],
    });
  }

  updateTotalForm(): void {
    const prixTotal = this.commandes.reduce(
      (total, commande) => total + commande.prixTotal,
      0
    );
    const quantiteTotale = this.commandes.reduce(
      (total, commande) => total + commande.quantite,
      0
    );

    this.totalForm.patchValue({
      prixTotal: prixTotal,
      quantiteTotale: quantiteTotale,
    });
  }

  supprimerCommande(commande: any): void {
    const userId = this.connexionService.getUserId();
    const nomProduit = commande.nom;

    const url = `http://localhost:8080/api/cart/supprimer-commande/${userId}/${nomProduit}`;

    this.httpClient.delete(url).subscribe(
      () => {
        this.commandes = this.commandes.filter((c) => c !== commande);
        this.commandes = this.commandes.filter((c) => c !== commande);
        this.updateTotalForm();
      },
      (error: any) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }

  validateQuantity(commande: any, event: Event): void {}

  payer(): void {
    const userId = this.connexionService.getUserId();
    const url3 = `http://localhost:8080/api/facture/Ajouter-Facture/${userId}`;

    const payload = {
      prixTotal: this.totalForm.value.prixTotal,
      quantiteTotale: this.totalForm.value.quantiteTotale,
    };

    this.httpClient.post(url3, payload).subscribe(
      (response: any) => {
        const factureId = response.factureId;

        const decrementObservables = this.commandes.map(
          (commande) =>
            this.produitService.ApresAchat(
              commande.nom,
              commande.quantite
            ),
        );

        forkJoin(decrementObservables).subscribe(
          () => {
            this.supprimerCart();

            const urlFacture = `http://localhost:8080/api/facture/recuperer/${factureId}`;

            this.httpClient.get(urlFacture).subscribe(
              (factureData: any) => {
                this.facture = factureData;
              },
              (error: any) => {
                console.error(
                  'Erreur lors de la récupération des données de la facture : ',
                  error
                );
              }
            );
          },
          (error) => {
            console.error(
              'Erreur lors de la décrémentation de la quantité des produits : ',
              error
            );
          }
        );
      },
      (error: any) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }

  supprimerCart(): void {
    const userId = this.connexionService.getUserId();
    const url2 = `http://localhost:8080/api/cart/supprimer-cart/${userId}`;
    this.httpClient.delete(url2).subscribe(
      () => {
        this.commandes = [];
        this.updateTotalForm();
      },
      (error: any) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }
  retourAccueil() {
    this.router.navigate(['/pharmacie']);
  }
}
