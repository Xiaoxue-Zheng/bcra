import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RadioAnswer } from 'app/shared/model/radio-answer.model';
import { RadioAnswerService } from './radio-answer.service';
import { RadioAnswerComponent } from './radio-answer.component';
import { RadioAnswerDetailComponent } from './radio-answer-detail.component';
import { RadioAnswerUpdateComponent } from './radio-answer-update.component';
import { RadioAnswerDeletePopupComponent } from './radio-answer-delete-dialog.component';
import { IRadioAnswer } from 'app/shared/model/radio-answer.model';

@Injectable({ providedIn: 'root' })
export class RadioAnswerResolve implements Resolve<IRadioAnswer> {
  constructor(private service: RadioAnswerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRadioAnswer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RadioAnswer>) => response.ok),
        map((radioAnswer: HttpResponse<RadioAnswer>) => radioAnswer.body)
      );
    }
    return of(new RadioAnswer());
  }
}

export const radioAnswerRoute: Routes = [
  {
    path: '',
    component: RadioAnswerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RadioAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RadioAnswerDetailComponent,
    resolve: {
      radioAnswer: RadioAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RadioAnswerUpdateComponent,
    resolve: {
      radioAnswer: RadioAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RadioAnswerUpdateComponent,
    resolve: {
      radioAnswer: RadioAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const radioAnswerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RadioAnswerDeletePopupComponent,
    resolve: {
      radioAnswer: RadioAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RadioAnswers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
