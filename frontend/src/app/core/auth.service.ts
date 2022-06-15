import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import jwt_decode from 'jwt-decode';

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

  async isMyProfile(id: string) {
    const userId = await this.getUserId();
    if (userId == id)
      return true;
    else
      return false;
  }

  async getUserId() {
    const value = await this.keyCloackService.getToken();
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
