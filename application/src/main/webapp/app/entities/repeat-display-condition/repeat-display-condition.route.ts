import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';
import { RepeatDisplayConditionService } from './repeat-display-condition.service';
import { RepeatDisplayConditionComponent } from './repeat-display-condition.component';
import { RepeatDisplayConditionDetailComponent } from './repeat-display-condition-detail.component';
import { RepeatDisplayConditionUpdateComponent } from './repeat-display-condition-update.component';
import { RepeatDisplayConditionDeletePopupComponent } from './repeat-display-condition-delete-dialog.component';
import { IRepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

@Injectable({ providedIn: 'root' })
export class RepeatDisplayConditionResolve implements Resolve<IRepeatDisplayCondition> {
  constructor(private service: RepeatDisplayConditionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRepeatDisplayCondition> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RepeatDisplayCondition>) => response.ok),
        map((repeatDisplayCondition: HttpResponse<RepeatDisplayCondition>) => repeatDisplayCondition.body)
      );
    }
    return of(new RepeatDisplayCondition());
  }
}

export const repeatDisplayConditionRoute: Routes = [
  {
    path: '',
    component: RepeatDisplayConditionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RepeatDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RepeatDisplayConditionDetailComponent,
    resolve: {
      repeatDisplayCondition: RepeatDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RepeatDisplayConditionUpdateComponent,
    resolve: {
      repeatDisplayCondition: RepeatDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RepeatDisplayConditionUpdateComponent,
    resolve: {
      repeatDisplayCondition: RepeatDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const repeatDisplayConditionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RepeatDisplayConditionDeletePopupComponent,
    resolve: {
      repeatDisplayCondition: RepeatDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatDisplayConditions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
