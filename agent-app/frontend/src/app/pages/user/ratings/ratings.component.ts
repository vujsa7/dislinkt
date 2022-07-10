import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { CompanyService } from 'src/app/shared/services/company.service';
import { RateCompanyDialogComponent } from '../rate-company-dialog/rate-company-dialog.component';

@Component({
  selector: 'app-ratings',
  templateUrl: './ratings.component.html',
  styleUrls: ['./ratings.component.scss']
})
export class RatingsComponent implements OnInit {

  companyId: any;
  ratings: any;

  constructor(private route: ActivatedRoute, private companyService: CompanyService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.companyId = this.route.snapshot.params['companyId'];
    this.companyService.getAllRatings(this.companyId).subscribe(
      data => {
        this.ratings = data.ratings;
    })
  }

  rateCompany() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      companyId: this.companyId
    }      
    this.dialog.open(RateCompanyDialogComponent, dialogConfig);
  }

}
