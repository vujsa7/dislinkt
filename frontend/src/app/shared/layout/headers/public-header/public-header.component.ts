import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-public-header',
  templateUrl: './public-header.component.html',
  styleUrls: ['./public-header.component.scss']
})
export class PublicHeaderComponent{
  dropdownMenuVisible: boolean = false;

  constructor(private keycloak: KeycloakService) { }

  redirectToRegisterPage(): void {
    this.keycloak.register();
  }

  redirectToLoginPage(): void {
    this.keycloak.login();
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

}