import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPageIdentifier, PageIdentifier } from 'app/shared/model/page-identifier.model';
import { PageIdentifierService } from './page-identifier.service';
import { PageIdentifierComponent } from './page-identifier.component';
import { PageIdentifierDetailComponent } from './page-identifier-detail.component';

@Injectable({ providedIn: 'root' })
export class PageIdentifierResolve implements Resolve<IPageIdentifier> {
  constructor(private service: PageIdentifierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageIdentifier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pageIdentifier: HttpResponse<PageIdentifier>) => {
          if (pageIdentifier.body) {
            return of(pageIdentifier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PageIdentifier());
  }
}

export const pageIdentifierRoute: Routes = [
  {
    path: '',
    component: PageIdentifierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageIdentifiers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PageIdentifierDetailComponent,
    resolve: {
      pageIdentifier: PageIdentifierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageIdentifiers'
    },
    canActivate: [UserRouteAccessService]
  }
];
