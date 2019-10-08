import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnswerSection } from 'app/shared/model/answer-section.model';
import { AnswerSectionService } from './answer-section.service';
import { AnswerSectionComponent } from './answer-section.component';
import { AnswerSectionDetailComponent } from './answer-section-detail.component';
import { AnswerSectionUpdateComponent } from './answer-section-update.component';
import { AnswerSectionDeletePopupComponent } from './answer-section-delete-dialog.component';
import { IAnswerSection } from 'app/shared/model/answer-section.model';

@Injectable({ providedIn: 'root' })
export class AnswerSectionResolve implements Resolve<IAnswerSection> {
  constructor(private service: AnswerSectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnswerSection> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnswerSection>) => response.ok),
        map((answerSection: HttpResponse<AnswerSection>) => answerSection.body)
      );
    }
    return of(new AnswerSection());
  }
}

export const answerSectionRoute: Routes = [
  {
    path: '',
    component: AnswerSectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnswerSectionDetailComponent,
    resolve: {
      answerSection: AnswerSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnswerSectionUpdateComponent,
    resolve: {
      answerSection: AnswerSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerSections'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnswerSectionUpdateComponent,
    resolve: {
      answerSection: AnswerSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerSections'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const answerSectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnswerSectionDeletePopupComponent,
    resolve: {
      answerSection: AnswerSectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AnswerSections'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
