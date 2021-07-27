import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudyId, StudyId } from 'app/shared/model/study-id.model';
import { StudyIdService } from './study-id.service';
import { StudyIdComponent } from './study-id.component';
import { StudyIdDetailComponent } from './study-id-detail.component';
import { StudyIdUpdateComponent } from './study-id-update.component';

@Injectable({ providedIn: 'root' })
export class StudyIdResolve implements Resolve<any> {
  constructor(private service: StudyIdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot) {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return new StudyId();
  }
}

export const studyIdRoute: Routes = [
  {
    path: 'study-id',
    component: StudyIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Study IDs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'study-id/:id/view',
    component: StudyIdDetailComponent,
    resolve: {
      studyId: StudyIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Study IDs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'study-id/new',
    component: StudyIdUpdateComponent,
    resolve: {
      studyId: StudyIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Study IDs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'study-id/:id/edit',
    component: StudyIdUpdateComponent,
    resolve: {
      studyId: StudyIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Study IDs'
    },
    canActivate: [UserRouteAccessService]
  }
];
