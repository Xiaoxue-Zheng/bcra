import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from './answer-response.service';
import { AnswerResponseComponent } from './answer-response.component';
import { AnswerResponseDetailComponent } from './answer-response-detail.component';
import { AnswerResponseUpdateComponent } from './answer-response-update.component';
import { AnswerResponseDeletePopupComponent } from './answer-response-delete-dialog.component';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';

@Injectable({ providedIn: 'root' })
export class AnswerResponseResolve implements Resolve<IAnswerResponse> {
  constructor(private service: AnswerResponseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnswerResponse> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnswerResponse>) => response.ok),
        map((answerResponse: HttpResponse<AnswerResponse>) => answerResponse.body)
      );
    }
    return of(new AnswerResponse());
  }
}

export const answerResponseRoute: Routes = [
  {
    path: '',
    component: AnswerResponseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerResponses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnswerResponseDetailComponent,
    resolve: {
      answerResponse: AnswerResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerResponses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnswerResponseUpdateComponent,
    resolve: {
      answerResponse: AnswerResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerResponses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnswerResponseUpdateComponent,
    resolve: {
      answerResponse: AnswerResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerResponses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const answerResponsePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnswerResponseDeletePopupComponent,
    resolve: {
      answerResponse: AnswerResponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerResponses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
