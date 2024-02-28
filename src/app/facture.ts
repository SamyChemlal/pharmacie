import { User } from "./user";
export interface Facture {
  id: number;
  valeur: number;
  quantites: number;
  user: User;
}
