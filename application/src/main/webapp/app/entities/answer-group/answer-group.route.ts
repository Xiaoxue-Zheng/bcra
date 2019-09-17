import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnswerGroup } from 'app/shared/model/answer-group.model';
import { AnswerGroupService } from './answer-group.service';
import { AnswerGroupComponent } from './answer-group.component';
import { AnswerGroupDetailComponent } from './answer-group-detail.component';
import { AnswerGroupUpdateComponent } from './answer-group-update.component';
import { AnswerGroupDeletePopupComponent } from './answer-group-delete-dialog.component';
import { IAnswerGroup } from 'app/shared/model/answer-group.model';

@Injectable({ providedIn: 'root' })
export class AnswerGroupResolve implements Resolve<IAnswerGroup> {
  constructor(private service: AnswerGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnswerGroup> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnswerGroup>) => response.ok),
        map((answerGroup: HttpResponse<AnswerGroup>) => answerGroup.body)
      );
    }
    return of(new AnswerGroup());
  }
}

export const answerGroupRoute: Routes = [
  {
    path: '',
    component: AnswerGroupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AnswerGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnswerGroupDetailComponent,
    resolve: {
      answerGroup: AnswerGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnswerGroupUpdateComponent,
    resolve: {
      answerGroup: AnswerGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnswerGroupUpdateComponent,
    resolve: {
      answerGroup: AnswerGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const answerGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnswerGroupDeletePopupComponent,
    resolve: {
      answerGroup: AnswerGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
