import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';
import { CheckboxQuestionItemService } from './checkbox-question-item.service';
import { CheckboxQuestionItemComponent } from './checkbox-question-item.component';
import { CheckboxQuestionItemDetailComponent } from './checkbox-question-item-detail.component';
import { CheckboxQuestionItemUpdateComponent } from './checkbox-question-item-update.component';
import { CheckboxQuestionItemDeletePopupComponent } from './checkbox-question-item-delete-dialog.component';
import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

@Injectable({ providedIn: 'root' })
export class CheckboxQuestionItemResolve implements Resolve<ICheckboxQuestionItem> {
  constructor(private service: CheckboxQuestionItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICheckboxQuestionItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CheckboxQuestionItem>) => response.ok),
        map((checkboxQuestionItem: HttpResponse<CheckboxQuestionItem>) => checkboxQuestionItem.body)
      );
    }
    return of(new CheckboxQuestionItem());
  }
}

export const checkboxQuestionItemRoute: Routes = [
  {
    path: '',
    component: CheckboxQuestionItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CheckboxQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckboxQuestionItemDetailComponent,
    resolve: {
      checkboxQuestionItem: CheckboxQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckboxQuestionItemUpdateComponent,
    resolve: {
      checkboxQuestionItem: CheckboxQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckboxQuestionItemUpdateComponent,
    resolve: {
      checkboxQuestionItem: CheckboxQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const checkboxQuestionItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CheckboxQuestionItemDeletePopupComponent,
    resolve: {
      checkboxQuestionItem: CheckboxQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestionItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
