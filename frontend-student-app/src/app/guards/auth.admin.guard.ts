import {ActivatedRouteSnapshot, GuardResult, MaybeAsync, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";

@Injectable()
export class AuthAdminGuard {
  constructor(private router : Router, private authService : AuthService) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if (this.authService.isAuthenticated) {
      let requiredRoles = route.data['roles'];
      let userRoles = this.authService.roles;
      for (let role of userRoles) {
        if (requiredRoles.includes(role)) return true;
      }
      return false;
    }
    this.router.navigateByUrl('/login');
    return false;
  }

}
