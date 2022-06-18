import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'any'
})
export class AuthService {

  constructor(private keyCloackService: KeycloakService) {

  }

  getHeader(): HttpHeaders {
    let token = this.keyCloackService.getKeycloakInstance().token;
    let header = new HttpHeaders().set('Content-Type', 'application/json');
    if (token != null) {
      header = new HttpHeaders().set('Content-Type', 'application/json')
        .set('Authorization', 'Bearer ' + token);
    }
    return header;
  }

  getFormHeader(): HttpHeaders {
      let token = this.keyCloackService.getKeycloakInstance().token;
      let header = new HttpHeaders();
      if (token != null) {
          header = new HttpHeaders().set('Authorization', 'Bearer ' + token);
      }
      return header;
  }

  isMyProfile(id: string) {
    const userId = this.getUserId();
    if (userId == id)
      return true;
    else
      return false;
  }

  getUserId() {
    const value = this.keyCloackService.getKeycloakInstance().token;
    let token = value;
    if (token == null)
      return null;
    else {
      let decodedToken = this.getDecodedAccessToken(token);
      return decodedToken.sub;
    }
  }

  private getDecodedAccessToken(token: string): any {
    try {
        return jwt_decode(token);
    } catch (Error) {
        return null;
    }
  }
  
}
