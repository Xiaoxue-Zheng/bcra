import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RepeatAnswer } from 'app/shared/model/repeat-answer.model';
import { RepeatAnswerService } from './repeat-answer.service';
import { RepeatAnswerComponent } from './repeat-answer.component';
import { RepeatAnswerDetailComponent } from './repeat-answer-detail.component';
import { RepeatAnswerUpdateComponent } from './repeat-answer-update.component';
import { RepeatAnswerDeletePopupComponent } from './repeat-answer-delete-dialog.component';
import { IRepeatAnswer } from 'app/shared/model/repeat-answer.model';

@Injectable({ providedIn: 'root' })
export class RepeatAnswerResolve implements Resolve<IRepeatAnswer> {
  constructor(private service: RepeatAnswerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRepeatAnswer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RepeatAnswer>) => response.ok),
        map((repeatAnswer: HttpResponse<RepeatAnswer>) => repeatAnswer.body)
      );
    }
    return of(new RepeatAnswer());
  }
}

export const repeatAnswerRoute: Routes = [
  {
    path: '',
    component: RepeatAnswerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RepeatAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RepeatAnswerDetailComponent,
    resolve: {
      repeatAnswer: RepeatAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RepeatAnswerUpdateComponent,
    resolve: {
      repeatAnswer: RepeatAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatAnswers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RepeatAnswerUpdateComponent,
    resolve: {
      repeatAnswer: RepeatAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatAnswers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const repeatAnswerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RepeatAnswerDeletePopupComponent,
    resolve: {
      repeatAnswer: RepeatAnswerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RepeatAnswers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
