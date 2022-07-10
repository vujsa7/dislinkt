import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { CompanyService } from '../services/company.service';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.scss']
})
export class CompaniesComponent implements OnInit {

  role: string = '';
  companies: any;

  constructor(private authService: AuthService, private companyService: CompanyService, private router: Router) { }

  ngOnInit(): void {
    this.role = this.authService.getTokenRole();
    if (this.role == 'ROLE_USER') {
      this.companyService.getAllCompanies().subscribe(
        data => {
          this.companies = data.companies;
      })
    }

    if (this.role == 'ROLE_COMPANY_OWNER') {
      this.companyService.getMyCompanies().subscribe(
        data => {
          this.companies = data.companies;
      })
    }
  }

  showRatings(companyId: string) {
    this.router.navigate(['ratings', companyId]);
  }

}
