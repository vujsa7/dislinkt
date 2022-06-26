import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'any'
})
export class NetworkService {
  
  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  getIncomingFollowRequests(): Observable<any>{
    return this.http.get<any>(this.baseUrl + 'profile-service/profiles/network', { headers: this.authService.getHeader() });
  }

  acceptFollowRequests(id: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + 'connection-service/connections/following-requests/accept/' + id, { headers: this.authService.getHeader() });
  }

  deleteFollowRequests(id: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + 'connection-service/connections/following-requests/delete/' + id, { headers: this.authService.getHeader() });
  }

  unfollowProfile(id: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + 'connection-service/connections/following/delete/' + id, { headers: this.authService.getHeader() });
  }

  removeFollower(id: string): Observable<any>{
    return this.http.post<any>(this.baseUrl + 'connection-service/connections/followers/delete/' + id, { headers: this.authService.getHeader() });
  }

}
