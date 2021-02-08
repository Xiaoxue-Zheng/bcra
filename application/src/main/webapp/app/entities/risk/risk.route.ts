import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRisk, Risk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';
import { RiskComponent } from './risk.component';
import { RiskDetailComponent } from './risk-detail.component';
import { RiskUpdateComponent } from './risk-update.component';

@Injectable({ providedIn: 'root' })
export class RiskResolve implements Resolve<IRisk> {
  constructor(private service: RiskService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRisk> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((risk: HttpResponse<Risk>) => {
          if (risk.body) {
            return of(risk.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Risk());
  }
}

export const riskRoute: Routes = [
  {
    path: '',
    component: RiskComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Risks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RiskDetailComponent,
    resolve: {
      risk: RiskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Risks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RiskUpdateComponent,
    resolve: {
      risk: RiskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Risks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RiskUpdateComponent,
    resolve: {
      risk: RiskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Risks'
    },
    canActivate: [UserRouteAccessService]
  }
];
