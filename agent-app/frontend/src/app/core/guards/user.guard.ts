import { Injectable } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { InfoDialogComponent } from "src/app/shared/components/info-dialog/info-dialog.component";
import { AuthService } from "../authentication/auth.service";

@Injectable()
export class UserGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService, private dialog: MatDialog) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        if (this.authService.getTokenRole()!="ROLE_USER") {
            this.router.navigate(['/404']);
            const dialogConfig = new MatDialogConfig();
            dialogConfig.data = {
                title: "Access denied",
                message: "You do not have access to this page.",
                buttonText: "Okay"
            };
            this.dialog.open(InfoDialogComponent, dialogConfig);
            return false;
        }
        return true;
    }

}
