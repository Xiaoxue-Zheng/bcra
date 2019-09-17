import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';
import { NumberCheckboxQuestionService } from './number-checkbox-question.service';
import { NumberCheckboxQuestionComponent } from './number-checkbox-question.component';
import { NumberCheckboxQuestionDetailComponent } from './number-checkbox-question-detail.component';
import { NumberCheckboxQuestionUpdateComponent } from './number-checkbox-question-update.component';
import { NumberCheckboxQuestionDeletePopupComponent } from './number-checkbox-question-delete-dialog.component';
import { INumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';

@Injectable({ providedIn: 'root' })
export class NumberCheckboxQuestionResolve implements Resolve<INumberCheckboxQuestion> {
  constructor(private service: NumberCheckboxQuestionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INumberCheckboxQuestion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NumberCheckboxQuestion>) => response.ok),
        map((numberCheckboxQuestion: HttpResponse<NumberCheckboxQuestion>) => numberCheckboxQuestion.body)
      );
    }
    return of(new NumberCheckboxQuestion());
  }
}

export const numberCheckboxQuestionRoute: Routes = [
  {
    path: '',
    component: NumberCheckboxQuestionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'NumberCheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NumberCheckboxQuestionDetailComponent,
    resolve: {
      numberCheckboxQuestion: NumberCheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NumberCheckboxQuestionUpdateComponent,
    resolve: {
      numberCheckboxQuestion: NumberCheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NumberCheckboxQuestionUpdateComponent,
    resolve: {
      numberCheckboxQuestion: NumberCheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const numberCheckboxQuestionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NumberCheckboxQuestionDeletePopupComponent,
    resolve: {
      numberCheckboxQuestion: NumberCheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NumberCheckboxQuestions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
