import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss']
})
export class PublicComponent implements OnInit {

  constructor(private http: HttpClient, private keycloak: KeycloakService) { }

  ngOnInit(): void {
    this.http.get("http://localhost:9090/hello").subscribe();
  }

  redirectToRegisterPage(): void {
    this.keycloak.register();
  }

  redirectToLoginPage(): void {
    this.keycloak.login();
  }

  logout(): void {
    this.keycloak.logout();
  }

}