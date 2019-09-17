import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';
import { CheckboxAnswerItemService } from './checkbox-answer-item.service';
import { CheckboxAnswerItemComponent } from './checkbox-answer-item.component';
import { CheckboxAnswerItemDetailComponent } from './checkbox-answer-item-detail.component';
import { CheckboxAnswerItemUpdateComponent } from './checkbox-answer-item-update.component';
import { CheckboxAnswerItemDeletePopupComponent } from './checkbox-answer-item-delete-dialog.component';
import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

@Injectable({ providedIn: 'root' })
export class CheckboxAnswerItemResolve implements Resolve<ICheckboxAnswerItem> {
  constructor(private service: CheckboxAnswerItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICheckboxAnswerItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CheckboxAnswerItem>) => response.ok),
        map((checkboxAnswerItem: HttpResponse<CheckboxAnswerItem>) => checkboxAnswerItem.body)
      );
    }
    return of(new CheckboxAnswerItem());
  }
}

export const checkboxAnswerItemRoute: Routes = [
  {
    path: '',
    component: CheckboxAnswerItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CheckboxAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckboxAnswerItemDetailComponent,
    resolve: {
      checkboxAnswerItem: CheckboxAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckboxAnswerItemUpdateComponent,
    resolve: {
      checkboxAnswerItem: CheckboxAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckboxAnswerItemUpdateComponent,
    resolve: {
      checkboxAnswerItem: CheckboxAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const checkboxAnswerItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CheckboxAnswerItemDeletePopupComponent,
    resolve: {
      checkboxAnswerItem: CheckboxAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswerItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
