import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RepeatQuestion } from 'app/shared/model/repeat-question.model';
import { RepeatQuestionService } from './repeat-question.service';
import { RepeatQuestionComponent } from './repeat-question.component';
import { RepeatQuestionDetailComponent } from './repeat-question-detail.component';
import { RepeatQuestionUpdateComponent } from './repeat-question-update.component';
import { RepeatQuestionDeletePopupComponent } from './repeat-question-delete-dialog.component';
import { IRepeatQuestion } from 'app/shared/model/repeat-question.model';

@Injectable({ providedIn: 'root' })
export class RepeatQuestionResolve implements Resolve<IRepeatQuestion> {
  constructor(private service: RepeatQuestionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRepeatQuestion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RepeatQuestion>) => response.ok),
        map((repeatQuestion: HttpResponse<RepeatQuestion>) => repeatQuestion.body)
      );
    }
    return of(new RepeatQuestion());
  }
}

export const repeatQuestionRoute: Routes = [
  {
    path: '',
    component: RepeatQuestionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RepeatQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RepeatQuestionDetailComponent,
    resolve: {
      repeatQuestion: RepeatQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RepeatQuestionUpdateComponent,
    resolve: {
      repeatQuestion: RepeatQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RepeatQuestionUpdateComponent,
    resolve: {
      repeatQuestion: RepeatQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatQuestions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const repeatQuestionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RepeatQuestionDeletePopupComponent,
    resolve: {
      repeatQuestion: RepeatQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatQuestions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
