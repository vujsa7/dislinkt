import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { TestService } from 'src/app/shared/services/test.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(private testService: TestService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.testService.getUsers().subscribe(
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
