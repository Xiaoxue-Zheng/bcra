import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';
import { QuestionnaireQuestionGroupService } from './questionnaire-question-group.service';
import { QuestionnaireQuestionGroupComponent } from './questionnaire-question-group.component';
import { QuestionnaireQuestionGroupDetailComponent } from './questionnaire-question-group-detail.component';
import { QuestionnaireQuestionGroupUpdateComponent } from './questionnaire-question-group-update.component';
import { QuestionnaireQuestionGroupDeletePopupComponent } from './questionnaire-question-group-delete-dialog.component';
import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';

@Injectable({ providedIn: 'root' })
export class QuestionnaireQuestionGroupResolve implements Resolve<IQuestionnaireQuestionGroup> {
  constructor(private service: QuestionnaireQuestionGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuestionnaireQuestionGroup> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<QuestionnaireQuestionGroup>) => response.ok),
        map((questionnaireQuestionGroup: HttpResponse<QuestionnaireQuestionGroup>) => questionnaireQuestionGroup.body)
      );
    }
    return of(new QuestionnaireQuestionGroup());
  }
}

export const questionnaireQuestionGroupRoute: Routes = [
  {
    path: '',
    component: QuestionnaireQuestionGroupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionnaireQuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestionnaireQuestionGroupDetailComponent,
    resolve: {
      questionnaireQuestionGroup: QuestionnaireQuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionnaireQuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestionnaireQuestionGroupUpdateComponent,
    resolve: {
      questionnaireQuestionGroup: QuestionnaireQuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionnaireQuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestionnaireQuestionGroupUpdateComponent,
    resolve: {
      questionnaireQuestionGroup: QuestionnaireQuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionnaireQuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const questionnaireQuestionGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QuestionnaireQuestionGroupDeletePopupComponent,
    resolve: {
      questionnaireQuestionGroup: QuestionnaireQuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionnaireQuestionGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
