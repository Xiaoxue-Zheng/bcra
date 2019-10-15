import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';
import { ReferralConditionComponent } from './referral-condition.component';
import { ReferralConditionDetailComponent } from './referral-condition-detail.component';
import { ReferralConditionUpdateComponent } from './referral-condition-update.component';
import { ReferralConditionDeletePopupComponent } from './referral-condition-delete-dialog.component';
import { IReferralCondition } from 'app/shared/model/referral-condition.model';

@Injectable({ providedIn: 'root' })
export class ReferralConditionResolve implements Resolve<IReferralCondition> {
  constructor(private service: ReferralConditionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReferralCondition> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReferralCondition>) => response.ok),
        map((referralCondition: HttpResponse<ReferralCondition>) => referralCondition.body)
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

export const referralConditionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReferralConditionDeletePopupComponent,
    resolve: {
      referralCondition: ReferralConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReferralConditions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
