import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionGroup, QuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from './question-group.service';
import { QuestionGroupComponent } from './question-group.component';
import { QuestionGroupDetailComponent } from './question-group-detail.component';
import { QuestionGroupUpdateComponent } from './question-group-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionGroupResolve implements Resolve<IQuestionGroup> {
  constructor(private service: QuestionGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionGroup: HttpResponse<QuestionGroup>) => {
          if (questionGroup.body) {
            return of(questionGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionGroup());
  }
}

export const questionGroupRoute: Routes = [
  {
    path: '',
    component: QuestionGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestionGroupDetailComponent,
    resolve: {
      questionGroup: QuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestionGroupUpdateComponent,
    resolve: {
      questionGroup: QuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestionGroupUpdateComponent,
    resolve: {
      questionGroup: QuestionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];
