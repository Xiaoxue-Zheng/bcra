import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RadioQuestionItem } from 'app/shared/model/radio-question-item.model';
import { RadioQuestionItemService } from './radio-question-item.service';
import { RadioQuestionItemComponent } from './radio-question-item.component';
import { RadioQuestionItemDetailComponent } from './radio-question-item-detail.component';
import { RadioQuestionItemUpdateComponent } from './radio-question-item-update.component';
import { RadioQuestionItemDeletePopupComponent } from './radio-question-item-delete-dialog.component';
import { IRadioQuestionItem } from 'app/shared/model/radio-question-item.model';

@Injectable({ providedIn: 'root' })
export class RadioQuestionItemResolve implements Resolve<IRadioQuestionItem> {
  constructor(private service: RadioQuestionItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRadioQuestionItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RadioQuestionItem>) => response.ok),
        map((radioQuestionItem: HttpResponse<RadioQuestionItem>) => radioQuestionItem.body)
      );
    }
    return of(new RadioQuestionItem());
  }
}

export const radioQuestionItemRoute: Routes = [
  {
    path: '',
    component: RadioQuestionItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RadioQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RadioQuestionItemDetailComponent,
    resolve: {
      radioQuestionItem: RadioQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RadioQuestionItemUpdateComponent,
    resolve: {
      radioQuestionItem: RadioQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RadioQuestionItemUpdateComponent,
    resolve: {
      radioQuestionItem: RadioQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestionItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const radioQuestionItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RadioQuestionItemDeletePopupComponent,
    resolve: {
      radioQuestionItem: RadioQuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestionItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
