import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';
import { QuestionSectionComponent } from './question-section.component';
import { QuestionSectionDetailComponent } from './question-section-detail.component';
import { QuestionSectionUpdateComponent } from './question-section-update.component';
import { QuestionSectionDeletePopupComponent } from './question-section-delete-dialog.component';
import { IQuestionSection } from 'app/shared/model/question-section.model';

@Injectable({ providedIn: 'root' })
export class QuestionSectionResolve implements Resolve<IQuestionSection> {
  constructor(private service: QuestionSectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuestionSection> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<QuestionSection>) => response.ok),
        map((questionSection: HttpResponse<QuestionSection>) => questionSection.body)
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

export const questionSectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QuestionSectionDeletePopupComponent,
    resolve: {
      questionSection: QuestionSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuestionSections'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
