import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'any'
})
export class AuthService {

  constructor(private keyCloackService: KeycloakService) { }

  getHeader(): HttpHeaders {
    let token = this.keyCloackService.getToken();
    let header = new HttpHeaders().set('Content-Type', 'application/json');
    if (token != null) {
      header = new HttpHeaders().set('Content-Type', 'application/json')
        .set('Authorization', 'Bearer ' + token);
    }
    return header;
  }
}
