import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { AuthService } from 'src/app/core/auth.service';
import { ProfileService } from 'src/app/modules/profile/services/profile.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  authenticated!: boolean;
  dropdownMenuVisible: boolean = false;
  isVisible: boolean = true;
  userId!: string;

  async ngOnInit() {
    this.authenticated = await this.keycloak.isLoggedIn();
    this.userId = this.authService.getUserId();
    if (this.authenticated) {
      this.profileService.getAccountFromKeycloak().subscribe(
        data => {
          this.profileService.getProfileById(data.id).subscribe(
            _ => { },
            error => {
              if (error.status == 404) {
                this.router.navigate(['/onboarding']);
              }
            })
        })
    }
  }

  constructor(private keycloak: KeycloakService, private router: Router, private profileService: ProfileService, private authService: AuthService) {
    router.events.subscribe(
      data => {
        if (this.router.url.includes("onboarding")) {
          this.isVisible = false;
        } else {
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

  logout(): void {
    this.keycloak.logout();
  }

  toggleDropdownMenu(): void {
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

}
