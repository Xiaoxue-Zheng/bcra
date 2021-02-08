import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRiskAssessmentResult, RiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';
import { RiskAssessmentResultService } from './risk-assessment-result.service';
import { RiskAssessmentResultComponent } from './risk-assessment-result.component';
import { RiskAssessmentResultDetailComponent } from './risk-assessment-result-detail.component';
import { RiskAssessmentResultUpdateComponent } from './risk-assessment-result-update.component';

@Injectable({ providedIn: 'root' })
export class RiskAssessmentResultResolve implements Resolve<IRiskAssessmentResult> {
  constructor(private service: RiskAssessmentResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRiskAssessmentResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((riskAssessmentResult: HttpResponse<RiskAssessmentResult>) => {
          if (riskAssessmentResult.body) {
            return of(riskAssessmentResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RiskAssessmentResult());
  }
}

export const riskAssessmentResultRoute: Routes = [
  {
    path: '',
    component: RiskAssessmentResultComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskAssessmentResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RiskAssessmentResultDetailComponent,
    resolve: {
      riskAssessmentResult: RiskAssessmentResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskAssessmentResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RiskAssessmentResultUpdateComponent,
    resolve: {
      riskAssessmentResult: RiskAssessmentResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskAssessmentResults'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RiskAssessmentResultUpdateComponent,
    resolve: {
      riskAssessmentResult: RiskAssessmentResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RiskAssessmentResults'
    },
    canActivate: [UserRouteAccessService]
  }
];
