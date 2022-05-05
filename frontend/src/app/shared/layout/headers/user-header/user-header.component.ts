import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {
  dropdownMenuVisible: boolean = false;

  constructor(private keycloak: KeycloakService) { }

  ngOnInit(): void {}

  logout(): void {
    this.keycloak.logout();
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }
  
}
