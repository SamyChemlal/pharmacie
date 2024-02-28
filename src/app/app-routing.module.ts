import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnexionComponent } from './connexion/connexion.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { PharmacieComponent } from './pharmacie/pharmacie.component';
import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  { path: '', component: ConnexionComponent },
  { path: 'inscription', component: InscriptionComponent },
  {path:'Pharmacie',component:PharmacieComponent},
  {path:'pharmacie',component:PharmacieComponent},

  {path:'cart',component:CartComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
