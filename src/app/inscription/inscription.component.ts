import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css'],
})
export class InscriptionComponent {
  utilisateur = { nom: '', email: '', prenom: '', psw: '', role: '' };
  messageErreur: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private connexionService: ConnexionService
  ) {}

  inscriptionS() {
    this.messageErreur = '';
    if (
      this.utilisateur.nom &&
      this.utilisateur.email &&
      this.utilisateur.prenom &&
      this.utilisateur.psw &&
      this.utilisateur.role
    ) {
      this.connexionService.inscriptionS(this.utilisateur).subscribe(
        (response: { userId: string; }) => {
          console.log('Inscription réussie', response);

          this.connexionService.setUserId(response.userId);
          this.router.navigate(['/pharmacie']);
        },
        (error: { status: number; }) => {
          console.error("Échec de l'inscription", error);

          if (error.status === 409) {
            this.messageErreur = "L'utilisateur existe déjà.";
            this.utilisateur.nom = '';
            this.utilisateur.psw = '';
            this.utilisateur.email = '';
            this.utilisateur.prenom = '';
          } else {
          }
        }
      );
    } else {
      this.messageErreur = 'Veuillez remplir tous les champs.';
    }
  }
}
