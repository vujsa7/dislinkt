import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-public-header',
  templateUrl: './public-header.component.html',
  styleUrls: ['./public-header.component.scss']
})
export class PublicHeaderComponent{
  dropdownMenuVisible: boolean = false;
  isVisible: boolean = true;

  constructor(private keycloak: KeycloakService, private router: Router) { 
    router.events.subscribe(
      data => {
        if(this.router.url.includes("onboarding")){
          this.isVisible = false;
        }else {
          this.isVisible = true;
        }
      }
    );
  }

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