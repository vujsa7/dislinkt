import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';

@Component({
  selector: 'app-otp-dialog',
  templateUrl: './otp-dialog.component.html',
  styleUrls: ['./otp-dialog.component.scss']
})
export class OtpDialogComponent implements OnInit {

  otp: string = "";
  email: string = "";

  constructor(private dialog: MatDialog, private dialogRef: MatDialogRef<OtpDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private authService: AuthService, private router: Router) {
    this.email = data.email;
  }

  ngOnInit(): void {
  }

  verifyOtp() {
    this.authService.verifyOtp({ otp: this.otp, username: this.email}).subscribe(
      data => {
        this.authService.setToken(data.accessToken);
        this.router.navigate(['/home']);
      },
      error => {
        if (error.status == 401){
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Invalid OTP",
            message: "Either OTP password is invalid or it expired in the meantime. Please try again.",
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      }
    )
    this.dialogRef.close();
  }

}
