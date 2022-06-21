import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'any'
})
export class FeedService {
 

  baseUrl: string = environment.baseUrl + 'post-service/posts';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getFeed(): Observable<any> {
    return this.http.get(this.baseUrl + '/feed', { headers: this.authService.getHeader() })
  }
}
