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

  getConnectionsForUser(id: any): Observable<any> {
    return this.http.get(this.baseUrl + id, { headers: this.authService.getHeader() })
  }

  modifyConnection(followInfo: { id: string, followerId: string }): Observable<any> {
    return this.http.post(this.baseUrl, followInfo, { headers: this.authService.getHeader() });
  }
}
