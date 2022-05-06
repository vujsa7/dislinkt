import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { ProfileService } from './shared/services/profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private keycloak: KeycloakService, private profileService: ProfileService, private router: Router){}

  async ngOnInit(){
    this.authenticated = await this.keycloak.isLoggedIn();
    if(this.authenticated){
      this.profileService.getAccountFromKeycloak().subscribe(data => {
        this.profileService.getProfileById(data.id).subscribe(data => {
        }, error =>{
          if(error.status == 404){
            this.router.navigate(['/onboarding']);
          }
        })
      })
    }
  }
}
