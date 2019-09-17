import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckboxQuestion } from 'app/shared/model/checkbox-question.model';
import { CheckboxQuestionService } from './checkbox-question.service';
import { CheckboxQuestionComponent } from './checkbox-question.component';
import { CheckboxQuestionDetailComponent } from './checkbox-question-detail.component';
import { CheckboxQuestionUpdateComponent } from './checkbox-question-update.component';
import { CheckboxQuestionDeletePopupComponent } from './checkbox-question-delete-dialog.component';
import { ICheckboxQuestion } from 'app/shared/model/checkbox-question.model';

@Injectable({ providedIn: 'root' })
export class CheckboxQuestionResolve implements Resolve<ICheckboxQuestion> {
  constructor(private service: CheckboxQuestionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICheckboxQuestion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CheckboxQuestion>) => response.ok),
        map((checkboxQuestion: HttpResponse<CheckboxQuestion>) => checkboxQuestion.body)
      );
    }
    return of(new CheckboxQuestion());
  }
}

export const checkboxQuestionRoute: Routes = [
  {
    path: '',
    component: CheckboxQuestionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckboxQuestionDetailComponent,
    resolve: {
      checkboxQuestion: CheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckboxQuestionUpdateComponent,
    resolve: {
      checkboxQuestion: CheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckboxQuestionUpdateComponent,
    resolve: {
      checkboxQuestion: CheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const checkboxQuestionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CheckboxQuestionDeletePopupComponent,
    resolve: {
      checkboxQuestion: CheckboxQuestionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxQuestions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
