import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';

@Component({
  selector: 'app-company-owner',
  templateUrl: './company-owner.component.html',
  styleUrls: ['./company-owner.component.scss']
})
export class CompanyOwnerComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {}

  logout(){
    this.authService.flushToken();
  }

}
