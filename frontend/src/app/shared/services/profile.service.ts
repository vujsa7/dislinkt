import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  baseUrl: string = 'http://localhost:9090/profile-service/profiles';

  constructor(private http: HttpClient) {}

  getProfiles(query: string, size?: number): Observable<any> {
    let params;
    if(size){
      params = new HttpParams().set('query', query).set('size', size);
    } else{
      params = new HttpParams().set('query', query);
    }
    
    return this.http.get(this.baseUrl, {params});
  }

  getProfile(username: any): Observable<any> {
    return this.http.get(this.baseUrl + "/" + username);
  }

}
