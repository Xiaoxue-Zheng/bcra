import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';
import { NumberCheckboxAnswerService } from './number-checkbox-answer.service';
import { NumberCheckboxAnswerComponent } from './number-checkbox-answer.component';
import { NumberCheckboxAnswerDetailComponent } from './number-checkbox-answer-detail.component';
import { NumberCheckboxAnswerUpdateComponent } from './number-checkbox-answer-update.component';
import { NumberCheckboxAnswerDeletePopupComponent } from './number-checkbox-answer-delete-dialog.component';
import { INumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';

@Injectable({ providedIn: 'root' })
export class NumberCheckboxAnswerResolve implements Resolve<INumberCheckboxAnswer> {
  constructor(private service: NumberCheckboxAnswerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INumberCheckboxAnswer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NumberCheckboxAnswer>) => response.ok),
        map((numberCheckboxAnswer: HttpResponse<NumberCheckboxAnswer>) => numberCheckboxAnswer.body)
      );
    }
    return of(new NumberCheckboxAnswer());
  }
}

export const numberCheckboxAnswerRoute: Routes = [
  {
    path: '',
    component: NumberCheckboxAnswerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'NumberCheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NumberCheckboxAnswerDetailComponent,
    resolve: {
      numberCheckboxAnswer: NumberCheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NumberCheckboxAnswerUpdateComponent,
    resolve: {
      numberCheckboxAnswer: NumberCheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NumberCheckboxAnswerUpdateComponent,
    resolve: {
      numberCheckboxAnswer: NumberCheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const numberCheckboxAnswerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NumberCheckboxAnswerDeletePopupComponent,
    resolve: {
      numberCheckboxAnswer: NumberCheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxAnswers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
