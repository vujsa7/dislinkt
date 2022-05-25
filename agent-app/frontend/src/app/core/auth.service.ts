import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import jwt_decode from 'jwt-decode';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    
    private userEmail = new BehaviorSubject('');
    userEmailObservable = this.userEmail.asObservable();

    private baseUrl: string = environment.baseUrl;

    constructor(private http: HttpClient) { }

    login(loginRequest: Object): Observable<any> {
        return this.http.post<any>(this.baseUrl + 'auth/sign-in', loginRequest);
    }

    register(registrationRequest: Object): Observable<any> {
        return this.http.post<any>(this.baseUrl + 'auth/sign-up', registrationRequest);
    }

    loginOtp(email: string): Observable<any> {
        return this.http.post<any>(this.baseUrl + 'auth/sign-in-otp', email, {responseType: 'text' as 'json'});
    }

    verifyOtp(signingRequest: { otp: string; username: string; }) : Observable<any> {
        return this.http.post<any>(this.baseUrl + 'auth/sign-in-otp-verify', signingRequest);
    }

    setToken(data: string): void {
        localStorage.setItem('jwtToken', data);
        this.userEmail.next(this.getTokenUsername());
    }

    flushToken(): void{
        localStorage.removeItem('jwtToken');
        this.userEmail.next("");
    }

    getHeader(): HttpHeaders {
        let token = this.getToken();
        let header = new HttpHeaders().set('Content-Type', 'application/json');
        if (token != null) {
            header = new HttpHeaders().set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token);
        }
        return header;
    }

    getFormHeader(): HttpHeaders {
        let token = this.getToken();
        let header = new HttpHeaders();
        if (token != null) {
            header = new HttpHeaders().set('Authorization', 'Bearer ' + token);
        }
        return header;
    }

    getToken(): any {
        return localStorage.getItem('jwtToken');
    }

    getTokenRole(): any {
        let decodedToken = this.getDecodedAccessToken(this.getToken() || '');
        if (decodedToken == null) {
            return 'ROLE_UNSIGNED';
        }
        return decodedToken.role;
    }

    getTokenUsername(): any {
        let decodedToken = this.getDecodedAccessToken(this.getToken() || '');
        if (decodedToken == null) {
            return null;
        }
        return decodedToken.sub;
    }

    getTokenFullName(): any {
        let decodedToken = this.getDecodedAccessToken(this.getToken() || '');
        if (decodedToken == null) {
            return null;
        }
        return decodedToken.fullName;
    }

    getDecodedAccessToken(token: string): any {
        try {
            return jwt_decode(token);
        } catch (Error) {
            return null;
        }
    }

}