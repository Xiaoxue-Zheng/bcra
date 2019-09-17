import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RadioQuestion } from 'app/shared/model/radio-question.model';
import { RadioQuestionService } from './radio-question.service';
import { RadioQuestionComponent } from './radio-question.component';
import { RadioQuestionDetailComponent } from './radio-question-detail.component';
import { RadioQuestionUpdateComponent } from './radio-question-update.component';
import { RadioQuestionDeletePopupComponent } from './radio-question-delete-dialog.component';
import { IRadioQuestion } from 'app/shared/model/radio-question.model';

@Injectable({ providedIn: 'root' })
export class RadioQuestionResolve implements Resolve<IRadioQuestion> {
  constructor(private service: RadioQuestionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRadioQuestion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RadioQuestion>) => response.ok),
        map((radioQuestion: HttpResponse<RadioQuestion>) => radioQuestion.body)
      );
    }
    return of(new RadioQuestion());
  }
}

export const radioQuestionRoute: Routes = [
  {
    path: '',
    component: RadioQuestionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RadioQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RadioQuestionDetailComponent,
    resolve: {
      radioQuestion: RadioQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RadioQuestionUpdateComponent,
    resolve: {
      radioQuestion: RadioQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RadioQuestionUpdateComponent,
    resolve: {
      radioQuestion: RadioQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const radioQuestionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RadioQuestionDeletePopupComponent,
    resolve: {
      radioQuestion: RadioQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioQuestions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
