import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRiskFactor, RiskFactor } from 'app/shared/model/risk-factor.model';
import { RiskFactorService } from './risk-factor.service';
import { RiskFactorComponent } from './risk-factor.component';
import { RiskFactorDetailComponent } from './risk-factor-detail.component';
import { RiskFactorUpdateComponent } from './risk-factor-update.component';

@Injectable({ providedIn: 'root' })
export class RiskFactorResolve implements Resolve<IRiskFactor> {
  constructor(private service: RiskFactorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRiskFactor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((riskFactor: HttpResponse<RiskFactor>) => {
          if (riskFactor.body) {
            return of(riskFactor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RiskFactor());
  }
}

export const riskFactorRoute: Routes = [
  {
    path: '',
    component: RiskFactorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskFactors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RiskFactorDetailComponent,
    resolve: {
      riskFactor: RiskFactorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskFactors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RiskFactorUpdateComponent,
    resolve: {
      riskFactor: RiskFactorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskFactors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RiskFactorUpdateComponent,
    resolve: {
      riskFactor: RiskFactorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskFactors'
    },
    canActivate: [UserRouteAccessService]
  }
];
