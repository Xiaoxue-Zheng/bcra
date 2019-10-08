import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnswerItem } from 'app/shared/model/answer-item.model';
import { AnswerItemService } from './answer-item.service';
import { AnswerItemComponent } from './answer-item.component';
import { AnswerItemDetailComponent } from './answer-item-detail.component';
import { AnswerItemUpdateComponent } from './answer-item-update.component';
import { AnswerItemDeletePopupComponent } from './answer-item-delete-dialog.component';
import { IAnswerItem } from 'app/shared/model/answer-item.model';

@Injectable({ providedIn: 'root' })
export class AnswerItemResolve implements Resolve<IAnswerItem> {
  constructor(private service: AnswerItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnswerItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnswerItem>) => response.ok),
        map((answerItem: HttpResponse<AnswerItem>) => answerItem.body)
      );
    }
    return of(new AnswerItem());
  }
}

export const answerItemRoute: Routes = [
  {
    path: '',
    component: AnswerItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnswerItemDetailComponent,
    resolve: {
      answerItem: AnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnswerItemUpdateComponent,
    resolve: {
      answerItem: AnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnswerItemUpdateComponent,
    resolve: {
      answerItem: AnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const answerItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnswerItemDeletePopupComponent,
    resolve: {
      answerItem: AnswerItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
