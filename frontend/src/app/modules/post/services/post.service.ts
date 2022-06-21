import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'any'
})
export class PostService {

  baseUrl: string = environment.baseUrl + 'post-service/posts/';

  constructor(private http: HttpClient, private authService: AuthService) { }

  likePost(postId: string): Observable<any> {
    return this.http.post<any>(this.baseUrl + postId + '/like', { headers: this.authService.getHeader() })
  }

  dislikePost(postId: string): Observable<any> {
    return this.http.post<any>(this.baseUrl + postId + '/dislike', { headers: this.authService.getHeader() })
  }

  createPost(statusText: string, image: any): Observable<any> {
    let post = { content: statusText, base64Image: image };
    return this.http.post(this.baseUrl, post, { headers: this.authService.getHeader() })
  }

  postComment(postId: string, commentText: string): Observable<any> {
    let comment = { userId: this.authService.getUserId(), comment: commentText }
    return this.http.post(this.baseUrl + postId + '/comment', comment, { headers: this.authService.getHeader() })
  }
}
