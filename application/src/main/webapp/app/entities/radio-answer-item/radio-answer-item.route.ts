import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RadioAnswerItem } from 'app/shared/model/radio-answer-item.model';
import { RadioAnswerItemService } from './radio-answer-item.service';
import { RadioAnswerItemComponent } from './radio-answer-item.component';
import { RadioAnswerItemDetailComponent } from './radio-answer-item-detail.component';
import { RadioAnswerItemUpdateComponent } from './radio-answer-item-update.component';
import { RadioAnswerItemDeletePopupComponent } from './radio-answer-item-delete-dialog.component';
import { IRadioAnswerItem } from 'app/shared/model/radio-answer-item.model';

@Injectable({ providedIn: 'root' })
export class RadioAnswerItemResolve implements Resolve<IRadioAnswerItem> {
  constructor(private service: RadioAnswerItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRadioAnswerItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RadioAnswerItem>) => response.ok),
        map((radioAnswerItem: HttpResponse<RadioAnswerItem>) => radioAnswerItem.body)
      );
    }
    return of(new RadioAnswerItem());
  }
}

export const radioAnswerItemRoute: Routes = [
  {
    path: '',
    component: RadioAnswerItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RadioAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RadioAnswerItemDetailComponent,
    resolve: {
      radioAnswerItem: RadioAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RadioAnswerItemUpdateComponent,
    resolve: {
      radioAnswerItem: RadioAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RadioAnswerItemUpdateComponent,
    resolve: {
      radioAnswerItem: RadioAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswerItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const radioAnswerItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RadioAnswerItemDeletePopupComponent,
    resolve: {
      radioAnswerItem: RadioAnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswerItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
