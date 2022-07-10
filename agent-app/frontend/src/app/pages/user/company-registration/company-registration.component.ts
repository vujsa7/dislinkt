import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { CompanyService } from 'src/app/shared/services/company.service';

@Component({
  selector: 'app-company-registration',
  templateUrl: './company-registration.component.html',
  styleUrls: ['./company-registration.component.scss']
})
export class CompanyRegistrationComponent implements OnInit {

  registerCompanyForm!: FormGroup;

  constructor(private companyService: CompanyService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.registerCompanyForm = new FormGroup({
      companyName: new FormControl('', [Validators.required]),
      companyWebsite: new FormControl(''),
      companyPhoneNumber: new FormControl('', [Validators.required]),
      companyAddress: new FormControl('', [Validators.required]),
      companyDescription: new FormControl('', [Validators.required])
    });
  }

  onSubmit() {
    this.companyService.requestRegistration(this.registerCompanyForm.value).subscribe(
      data => {
        this.showSuccessDialog();
        this.registerCompanyForm.reset();
      }, error => {
        this.showErrorDialog(error.error.error);
        console.log(error);
      })
  }

  showSuccessDialog() {
    const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Success",
          message: "You have successfully sent the registration request to our administrator",
          buttonText: "Okay"
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
  }

  showErrorDialog(errorMessage: string) {
    const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Error",
          message: errorMessage,
          buttonText: "Close"
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
  }

}
