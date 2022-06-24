import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth.service';

@Injectable({
  providedIn: 'any'
})
export class ConnectionsService {

  baseUrl: string = 'https://localhost:9090/connection-service/connections/';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getConnectionsAndRequestsForUser(): Observable<any> {
    return this.http.get(this.baseUrl + this.authService.getUserId(), { headers: this.authService.getHeader() })
  }

  modifyConnection(followInfo: { followerId: string, isFollowerPrivate: boolean }): Observable<any> {
    return this.http.post(this.baseUrl, followInfo, { headers: this.authService.getHeader() });
  }
}
