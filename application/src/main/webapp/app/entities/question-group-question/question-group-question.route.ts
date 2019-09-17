import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuestionGroupQuestion } from 'app/shared/model/question-group-question.model';
import { QuestionGroupQuestionService } from './question-group-question.service';
import { QuestionGroupQuestionComponent } from './question-group-question.component';
import { QuestionGroupQuestionDetailComponent } from './question-group-question-detail.component';
import { QuestionGroupQuestionUpdateComponent } from './question-group-question-update.component';
import { QuestionGroupQuestionDeletePopupComponent } from './question-group-question-delete-dialog.component';
import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';

@Injectable({ providedIn: 'root' })
export class QuestionGroupQuestionResolve implements Resolve<IQuestionGroupQuestion> {
  constructor(private service: QuestionGroupQuestionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuestionGroupQuestion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<QuestionGroupQuestion>) => response.ok),
        map((questionGroupQuestion: HttpResponse<QuestionGroupQuestion>) => questionGroupQuestion.body)
      );
    }
    return of(new QuestionGroupQuestion());
  }
}

export const questionGroupQuestionRoute: Routes = [
  {
    path: '',
    component: QuestionGroupQuestionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionGroupQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestionGroupQuestionDetailComponent,
    resolve: {
      questionGroupQuestion: QuestionGroupQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroupQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestionGroupQuestionUpdateComponent,
    resolve: {
      questionGroupQuestion: QuestionGroupQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroupQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestionGroupQuestionUpdateComponent,
    resolve: {
      questionGroupQuestion: QuestionGroupQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroupQuestions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const questionGroupQuestionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QuestionGroupQuestionDeletePopupComponent,
    resolve: {
      questionGroupQuestion: QuestionGroupQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroupQuestions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
