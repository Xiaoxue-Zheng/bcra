import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CheckboxAnswer } from 'app/shared/model/checkbox-answer.model';
import { CheckboxAnswerService } from './checkbox-answer.service';
import { CheckboxAnswerComponent } from './checkbox-answer.component';
import { CheckboxAnswerDetailComponent } from './checkbox-answer-detail.component';
import { CheckboxAnswerUpdateComponent } from './checkbox-answer-update.component';
import { CheckboxAnswerDeletePopupComponent } from './checkbox-answer-delete-dialog.component';
import { ICheckboxAnswer } from 'app/shared/model/checkbox-answer.model';

@Injectable({ providedIn: 'root' })
export class CheckboxAnswerResolve implements Resolve<ICheckboxAnswer> {
  constructor(private service: CheckboxAnswerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICheckboxAnswer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CheckboxAnswer>) => response.ok),
        map((checkboxAnswer: HttpResponse<CheckboxAnswer>) => checkboxAnswer.body)
      );
    }
    return of(new CheckboxAnswer());
  }
}

export const checkboxAnswerRoute: Routes = [
  {
    path: '',
    component: CheckboxAnswerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckboxAnswerDetailComponent,
    resolve: {
      checkboxAnswer: CheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckboxAnswerUpdateComponent,
    resolve: {
      checkboxAnswer: CheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckboxAnswerUpdateComponent,
    resolve: {
      checkboxAnswer: CheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const checkboxAnswerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CheckboxAnswerDeletePopupComponent,
    resolve: {
      checkboxAnswer: CheckboxAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CheckboxAnswers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
