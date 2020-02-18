import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionSection, QuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';
import { QuestionSectionComponent } from './question-section.component';
import { QuestionSectionDetailComponent } from './question-section-detail.component';
import { QuestionSectionUpdateComponent } from './question-section-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionSectionResolve implements Resolve<IQuestionSection> {
  constructor(private service: QuestionSectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionSection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionSection: HttpResponse<QuestionSection>) => {
          if (questionSection.body) {
            return of(questionSection.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionSection());
  }
}

export const questionSectionRoute: Routes = [
  {
    path: '',
    component: QuestionSectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestionSectionDetailComponent,
    resolve: {
      questionSection: QuestionSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestionSectionUpdateComponent,
    resolve: {
      questionSection: QuestionSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestionSectionUpdateComponent,
    resolve: {
      questionSection: QuestionSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionSections'
    },
    canActivate: [UserRouteAccessService]
  }
];
