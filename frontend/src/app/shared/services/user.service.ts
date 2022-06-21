import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthService } from "src/app/core/auth.service";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'any'
})
export class UserService {

    baseUrl: string = environment.baseUrl + 'post-service/posts/';

    constructor(private http: HttpClient, private authService: AuthService) { }

    fetchNameAndImage(userId: string) {
        return this.http.get<any>(environment.baseUrl + 'profile-service/profiles/name-and-image/' + userId, { headers: this.authService.getHeader() })
    }
    
}