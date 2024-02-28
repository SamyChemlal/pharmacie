import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConnexionService {
  private userId: string = '';
  private urlBackend = 'http://localhost:8080/api/connexion';

  constructor(private http: HttpClient) {}

  setUserId(userId: string) {
    this.userId = userId;
  }

  getUserId(): string {
    return this.userId;
  }
  inscriptionS(utilisateur: any): Observable<any> {
    return this.http.post<any>(
      'http://localhost:8080/api/inscription',
      utilisateur
    );
  }

  verifierId(email: string, psw: string): Observable<any> {
    const body = { email, psw };
    return this.http.post<any>(this.urlBackend, body);
  }

  disco(): Observable<any> {
    const url = `${this.urlBackend}/deconnexion`;
    return this.http.post(url, {});
  }
  getRoleId(userId: string): Observable<string> {
    const url = `http://localhost:8080/api/Role/${userId}`;
    return this.http.get<string>(url);
  }
  getDetailsUser(email: string): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/user/details/${email}`);
  }
}
