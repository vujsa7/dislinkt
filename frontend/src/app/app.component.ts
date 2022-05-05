import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private keycloak: KeycloakService){}

  async ngOnInit(){
    this.authenticated = await this.keycloak.isLoggedIn();
  }
}
