import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AuthService } from 'src/app/core/auth.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'post-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {

  statusText: string = "";
  postImage: any = null;

  constructor(private dialog: MatDialog, private postService: PostService, private authService: AuthService) { }

  ngOnInit(): void {
  }

  canCreatePost(): boolean {
    if (this.statusText != "" || this.postImage != null)
      return true;
    return false;
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
      this.postImage = reader.result;
    }

    const image = new FormData();
    image.append('file', file);
  }

  createPost() {
    if (this.canCreatePost()) {
      this.postService.createPost(this.statusText, this.postImage).subscribe(
        data => {
          window.location.reload();
        }
      )
    }
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

}
