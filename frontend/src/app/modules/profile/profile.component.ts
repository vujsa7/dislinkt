import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, ParamMap } from '@angular/router';
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
  isMyProfile: boolean = false;
  following!: Array<any>;
  requests!: Array<any>;
  connectionStatus!: string;
  newProfileImg: any;
  profileImageUrl: string = "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png";
  profileImageLoaded: boolean = false;
  posts: any;


  constructor(private route: ActivatedRoute, private profileService: ProfileService, private dialog: MatDialog,
     private authService: AuthService, private connectionsService: ConnectionsService) { }

  ngOnInit() {
    if(this.isAuthenticated())
      this.fetchAuthenticatedConnections();
    this.route.paramMap.subscribe(params => {
      this.fetchProfileInfo(params);
    });
  }

  private fetchProfileInfo(params: ParamMap) {
    this.profileService.getProfile(params.get('username')).subscribe(
      data => {
        this.profile = data;
        this.initializeProfilePage();
        this.getProfileImage();
        this.getProfilePosts();
      },
      error => {
        this.profileService.getProfileById(params.get('username')!).subscribe(
          data => {
            this.profile = data;
            this.initializeProfilePage();
            this.getProfileImage();
            this.getProfilePosts();
          }
        );
      }
    );
  }

  private fetchAuthenticatedConnections() {
    this.connectionsService.getFollowingsForUser().subscribe(
      data => {
        this.following = data.following;
        this.requests = data.requests;
        this.setConnectionStatus();
      }
    );
  }

  initializeProfilePage() {
    if(this.isAuthenticated()){
      this.isMyProfile = this.authService.isMyProfile(this.profile.id);
      this.setConnectionStatus();
    }
  }

  isAuthenticated(){
    return this.authService.isAuthenticated();
  }

  getProfilePosts() {
    this.posts = [];
    if(this.arePostsAvailabile()){
      this.profileService.getPostsForProfile(this.profile.id).subscribe(
        data => {
          this.posts = data;
        }
      )
    }
  }

  setConnectionStatus() {
    if (_.includes(this.following, this.profile.id))
      this.connectionStatus = "FOLLOWING";
    else if(_.includes(this.requests, this.profile.id))
      this.connectionStatus = "REQUESTED";
    else
      this.connectionStatus = "NO_FOLLOW";
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
    let isPrivate = this.profile.profileType=="PRIVATE"? true: false;
    let follow = { followerId: this.profile.id, isFollowerPrivate:  isPrivate}
    this.connectionsService.modifyConnection(follow).subscribe(
      data => {
        this.connectionStatus = data.connectionStatus;
      }
    )
  }

  getProfileImage(){
    this.profileImageLoaded = false;
    this.profileService.getProfileImage(this.profile.id).subscribe(data => {
      if(data.url != ""){
        this.profileImageUrl = data.url;
      } else {
        this.profileImageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png";
      }
      setTimeout(() => {
        this.profileImageLoaded = true;
      }, 0)
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

    this.profileService.updateProfileImage(image).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log(error.message);
      }
    );
  }

  sortedPosts(){
    return _.sortBy(this.posts, [p => p.postedAt]).reverse();
  }

  arePostsAvailabile(){
    if(this.isMyProfile)
      return true;
    if(this.profile.profileType=="PUBLIC")
      return true;
    else if(this.isAuthenticated() && this.connectionStatus=="FOLLOWING")
      return true;
    else
      return false;
  }
}
