import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { TestService } from 'src/app/shared/services/test.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  constructor(private testService: TestService, private dialog: MatDialog) { }
  ngOnInit(): void {
    this.testService.getAdmins().subscribe(
      data => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Success",
          message: "You have the authorization to view this page",
          buttonText: "Okay"
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
    )
  }

}
