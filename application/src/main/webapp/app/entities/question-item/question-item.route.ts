import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuestionItem } from 'app/shared/model/question-item.model';
import { QuestionItemService } from './question-item.service';
import { QuestionItemComponent } from './question-item.component';
import { QuestionItemDetailComponent } from './question-item-detail.component';
import { QuestionItemUpdateComponent } from './question-item-update.component';
import { QuestionItemDeletePopupComponent } from './question-item-delete-dialog.component';
import { IQuestionItem } from 'app/shared/model/question-item.model';

@Injectable({ providedIn: 'root' })
export class QuestionItemResolve implements Resolve<IQuestionItem> {
  constructor(private service: QuestionItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuestionItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<QuestionItem>) => response.ok),
        map((questionItem: HttpResponse<QuestionItem>) => questionItem.body)
      );
    }
    return of(new QuestionItem());
  }
}

export const questionItemRoute: Routes = [
  {
    path: '',
    component: QuestionItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestionItemDetailComponent,
    resolve: {
      questionItem: QuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestionItemUpdateComponent,
    resolve: {
      questionItem: QuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestionItemUpdateComponent,
    resolve: {
      questionItem: QuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const questionItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QuestionItemDeletePopupComponent,
    resolve: {
      questionItem: QuestionItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
