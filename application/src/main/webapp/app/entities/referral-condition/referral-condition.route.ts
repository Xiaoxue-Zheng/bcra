import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReferralCondition, ReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';
import { ReferralConditionComponent } from './referral-condition.component';
import { ReferralConditionDetailComponent } from './referral-condition-detail.component';
import { ReferralConditionUpdateComponent } from './referral-condition-update.component';

@Injectable({ providedIn: 'root' })
export class ReferralConditionResolve implements Resolve<IReferralCondition> {
  constructor(private service: ReferralConditionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReferralCondition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((referralCondition: HttpResponse<ReferralCondition>) => {
          if (referralCondition.body) {
            return of(referralCondition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReferralCondition());
  }
}

export const referralConditionRoute: Routes = [
  {
    path: '',
    component: ReferralConditionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReferralConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReferralConditionDetailComponent,
    resolve: {
      referralCondition: ReferralConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReferralConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReferralConditionUpdateComponent,
    resolve: {
      referralCondition: ReferralConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReferralConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReferralConditionUpdateComponent,
    resolve: {
      referralCondition: ReferralConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReferralConditions'
    },
    canActivate: [UserRouteAccessService]
  }
];
