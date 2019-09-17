import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';
import { CheckboxDisplayConditionService } from './checkbox-display-condition.service';
import { CheckboxDisplayConditionComponent } from './checkbox-display-condition.component';
import { CheckboxDisplayConditionDetailComponent } from './checkbox-display-condition-detail.component';
import { CheckboxDisplayConditionUpdateComponent } from './checkbox-display-condition-update.component';
import { CheckboxDisplayConditionDeletePopupComponent } from './checkbox-display-condition-delete-dialog.component';
import { ICheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

@Injectable({ providedIn: 'root' })
export class CheckboxDisplayConditionResolve implements Resolve<ICheckboxDisplayCondition> {
  constructor(private service: CheckboxDisplayConditionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICheckboxDisplayCondition> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CheckboxDisplayCondition>) => response.ok),
        map((checkboxDisplayCondition: HttpResponse<CheckboxDisplayCondition>) => checkboxDisplayCondition.body)
      );
    }
    return of(new CheckboxDisplayCondition());
  }
}

export const checkboxDisplayConditionRoute: Routes = [
  {
    path: '',
    component: CheckboxDisplayConditionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CheckboxDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckboxDisplayConditionDetailComponent,
    resolve: {
      checkboxDisplayCondition: CheckboxDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckboxDisplayConditionUpdateComponent,
    resolve: {
      checkboxDisplayCondition: CheckboxDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckboxDisplayConditionUpdateComponent,
    resolve: {
      checkboxDisplayCondition: CheckboxDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxDisplayConditions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const checkboxDisplayConditionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CheckboxDisplayConditionDeletePopupComponent,
    resolve: {
      checkboxDisplayCondition: CheckboxDisplayConditionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxDisplayConditions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
