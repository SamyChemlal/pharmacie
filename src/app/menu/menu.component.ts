import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
  export class MenuComponent {
    constructor(
      private router: Router,
      private connexionService: ConnexionService
    ) {}
    seDeconnecter() {
      this.connexionService.disco().subscribe(
        (resultat) => {
          this.router.navigate(['']);
        },
        (erreur) => {
        }
      );
    }
  }
  

