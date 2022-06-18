import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import * as _ from 'lodash';
import { AuthService } from 'src/app/core/auth.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { environment } from 'src/environments/environment';
import { UpdateEducationDialogComponent } from './components/update-education-dialog/update-education-dialog.component';
import { UpdateExperienceDialogComponent } from './components/update-experience-dialog/update-experience-dialog.component';
import { UpdateProfileDialogComponent } from './components/update-profile-dialog/update-profile-dialog.component';
import { UpdateSkillsInterestsDialogComponent } from './components/update-skills-interests-dialog/update-skills-interests-dialog.component';
import { ConnectionsService } from './services/connections.service';
import { ProfileService } from './services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profile: any;
  isMyProfile!: boolean;
  connections!: Array<any>;
  follows!: boolean;
  loggedInId!: string;
  newProfileImg: any;
  profileImageUrl: string = 'https://images.unsplash.com/photo-1531891437562-4301cf35b7e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzl8fHByb2ZpbGV8ZW58MHx8MHx8&auto=format&fit=crop&w=400&q=60';

  constructor(private route: ActivatedRoute, private profileService: ProfileService, private dialog: MatDialog,
     private authService: AuthService, private connectionsService: ConnectionsService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      
      this.profileService.getProfile(params.get('username')).subscribe(
        async data => {
          this.profile = data;
          this.isMyProfile = this.authService.isMyProfile(this.profile.id);
          this.loggedInId = this.authService.getUserId();
          this.connectionsService.getConnectionsForUser(this.loggedInId).subscribe(
            data => {
              this.connections = data.ids;
              this.checkIfLoggedInUserFollows(this.profile.id);
            }
          );
          this.getProfileImage();
        }
      )
    });

  }

  checkIfLoggedInUserFollows(id: string) {
    if (_.includes(this.connections, id))
      this.follows = true;
    else 
      this.follows = false;
  }


  openUpdateProfileDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      profile: this.profile
    };
    const dialogRef = this.dialog.open(UpdateProfileDialogComponent, dialogConfig);
    dialogRef.componentInstance.okay.subscribe(res => {
      if (res.success) {
        this.profile = res.success;
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Successful",
          message: "You have successfully updated your info.",
          buttonText: "Okay",
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      } else {
        if (res.error.status == 401) {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Unauthorized",
            message: "You do not have the permission to do this",
            buttonText: "Okay",
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        } else {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "User not found",
            message: "The user could not be found in our records.",
            buttonText: "Okay",
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }

      }
    })
  }

  openUpdateSkillsDialog(): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      skills: this.profile.skills,
      dialogType: "skills"
    };
    let dialogRef = this.dialog.open(UpdateSkillsInterestsDialogComponent, dialogConfig);
    dialogRef.componentInstance.okay.subscribe(
      res => {
        if (res.skills) {
          this.profile.skills = res.skills;
          dialogRef.componentInstance.okay.unsubscribe();
        }
      }
    )
  }

  openUpdateInterestsDialog(): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      interests: this.profile.interests,
      dialogType: "interests"
    };
    let dialogRef = this.dialog.open(UpdateSkillsInterestsDialogComponent, dialogConfig);
    dialogRef.componentInstance.okay.subscribe(
      res => {
        if (res.interests) {
          this.profile.interests = res.interests;
          dialogRef.componentInstance.okay.unsubscribe();
        }
      }
    )
  }

  openUpdateExperienceDialog(): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      experiences: this.profile.experience
    };
    let dialogRef = this.dialog.open(UpdateExperienceDialogComponent, dialogConfig);
    dialogRef.componentInstance.okay.subscribe(
      res => {
        this.profile.experience = res;
        dialogRef.componentInstance.okay.unsubscribe();
      }
    )
  }

  openUpdateEducationDialog(): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      education: this.profile.education
    };
    let dialogRef = this.dialog.open(UpdateEducationDialogComponent, dialogConfig);
    dialogRef.componentInstance.okay.subscribe(
      res => {
        this.profile.education = res;
        dialogRef.componentInstance.okay.unsubscribe();
      }
    )
  }

  toggleFollow(){
    let follow = {id: this.loggedInId, followerId: this.profile.id}
    this.connectionsService.modifyConnection(follow).subscribe(
      data => {
        this.follows = data.following;
      }
    )
  }

  getProfileImage(){
    this.profileService.getProfileImage(this.profile.id).subscribe(data => {
      this.profileImageUrl = data.url;
    })
  }

  onFileChanged(event: any): void {
    const file = event.target.files[0]
    const mimeType = file.type;
    if (mimeType.match(/image\/*/) == null) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        title: "Only images are supported",
        message: "Please select an image to upload",
        buttonText: "Okay"
      };
      this.dialog.open(InfoDialogComponent, dialogConfig);
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (_event) => {
      this.newProfileImg = reader.result;
    }

    const image = new FormData();
    image.append('file', file);

    console.log(image)
    this.profileService.updateProfileImage(image).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log(error.message);
      }
    );
  }
}
