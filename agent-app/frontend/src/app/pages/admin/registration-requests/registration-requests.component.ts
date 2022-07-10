import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/shared/services/company.service';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.scss']
})
export class RegistrationRequestsComponent implements OnInit {

  requests: any;

  constructor(private companyService: CompanyService) { }

  ngOnInit(): void {
    this.companyService.getAllRegistrationRequests().subscribe(
      data => {
        this.requests = data.requests;
      }, error => {
        //alert(error.error.error);
    })
  }

  approve(requestId: string) {
    this.companyService.approveRegistrationRequest(requestId).subscribe(
      data => {
        this.requests = this.requests.filter((req: { id: string; }) => req.id != requestId);
      }, error => {
        alert(error.error.error);
    });
  }

}
