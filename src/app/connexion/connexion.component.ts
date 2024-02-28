import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.css'],
})
export class ConnexionComponent {
  utilisateur = { email: '', psw: '' };
  messageErreur: string;

  constructor(
    private router: Router,
    private connexionService: ConnexionService
  ) {
    this.messageErreur = '';
  }

  seConnecter() {
    this.messageErreur = ''; // Réinitialise le message d'erreur à chaque tentative

    this.connexionService
      .verifierId(this.utilisateur.email, this.utilisateur.psw)
      .subscribe(
        (resultat: { userId: any; }) => {
          console.log('Résultat de la connexion:', resultat);
          const userId = resultat.userId;
          this.connexionService.setUserId(userId);
          console.log('ID du user:', userId);
          this.router.navigate(['/pharmacie']);
        },
        (erreur: { status: number; }) => {
          if (erreur.status === 401) {
            this.messageErreur = 'Identifiants incorrects. Veuillez réessayer.';
            this.utilisateur.email = '';
            this.utilisateur.psw = '';
          } else {
            console.error('Erreur lors de la connexion:', erreur);
          }
        }
      );
  }
}
