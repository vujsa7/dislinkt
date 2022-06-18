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

  constructor(private http: HttpClient, private authService: AuthService) {}

  likePost(postId: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + postId + '/like', { headers: this.authService.getHeader() })
  }

  dislikePost(postId: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + postId + '/dislike', { headers: this.authService.getHeader() })
  }
}
