import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DisplayCondition } from 'app/shared/model/display-condition.model';
import { DisplayConditionService } from './display-condition.service';
import { DisplayConditionComponent } from './display-condition.component';
import { DisplayConditionDetailComponent } from './display-condition-detail.component';
import { DisplayConditionUpdateComponent } from './display-condition-update.component';
import { DisplayConditionDeletePopupComponent } from './display-condition-delete-dialog.component';
import { IDisplayCondition } from 'app/shared/model/display-condition.model';

@Injectable({ providedIn: 'root' })
export class DisplayConditionResolve implements Resolve<IDisplayCondition> {
  constructor(private service: DisplayConditionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDisplayCondition> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DisplayCondition>) => response.ok),
        map((displayCondition: HttpResponse<DisplayCondition>) => displayCondition.body)
      );
    }
    return of(new DisplayCondition());
  }
}

export const displayConditionRoute: Routes = [
  {
    path: '',
    component: DisplayConditionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'DisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DisplayConditionDetailComponent,
    resolve: {
      displayCondition: DisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DisplayConditionUpdateComponent,
    resolve: {
      displayCondition: DisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DisplayConditionUpdateComponent,
    resolve: {
      displayCondition: DisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const displayConditionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DisplayConditionDeletePopupComponent,
    resolve: {
      displayCondition: DisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DisplayConditions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
