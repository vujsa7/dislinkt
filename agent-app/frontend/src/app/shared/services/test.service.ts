import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "src/app/core/authentication/auth.service";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'any'
})
export class TestService {

    private baseUrl: string = environment.baseUrl + 'users/';

    constructor(private http: HttpClient, private authService: AuthService) { }

    getUsers(): Observable<any> {
        return this.http.get<any>(this.baseUrl + 'user', { headers: this.authService.getHeader() });
    }

    getAdmins(): Observable<any> {
        return this.http.get<any>(this.baseUrl + 'admin', { headers: this.authService.getHeader() });
    }

    getCompanyOwners(): Observable<any> {
        return this.http.get<any>(this.baseUrl + 'company-owner', { headers: this.authService.getHeader() });
    }

}