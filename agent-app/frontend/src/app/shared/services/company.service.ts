import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  requestRegistration(request: any): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'company-registration-requests', request, { headers: this.authService.getHeader() });
  }

  getAllRegistrationRequests(): Observable<any> {
    return this.http.get<any>(this.baseUrl + 'company-registration-requests', { headers: this.authService.getHeader() });
  }

  approveRegistrationRequest(requestId: any): Observable<any> {
    return this.http.put<any>(this.baseUrl + 'company-registration-requests/' + requestId, {}, { headers: this.authService.getHeader() });
  }

  getAllCompanies(): Observable<any> {
    return this.http.get<any>(this.baseUrl + 'companies', { headers: this.authService.getHeader() });
  }

  getMyCompanies(): Observable<any> {
    return this.http.get<any>(this.baseUrl + 'my-companies', { headers: this.authService.getHeader() });
  }

  postRating(rating: any, companyId: string): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'companies/' + companyId + '/ratings', rating, { headers: this.authService.getHeader() });
  }

  getAllRatings(companyId: string): Observable<any> {
    return this.http.get<any>(this.baseUrl + 'companies/' + companyId + '/ratings', { headers: this.authService.getHeader() });
  }
}
