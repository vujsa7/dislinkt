import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent{
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

  logout(): void {
    this.keycloak.logout();
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }
  
}
