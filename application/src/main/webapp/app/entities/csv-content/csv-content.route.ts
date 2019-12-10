import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CsvContent } from 'app/shared/model/csv-content.model';
import { CsvContentService } from './csv-content.service';
import { CsvContentComponent } from './csv-content.component';
import { CsvContentDetailComponent } from './csv-content-detail.component';
import { CsvContentUpdateComponent } from './csv-content-update.component';
import { CsvContentDeletePopupComponent } from './csv-content-delete-dialog.component';
import { ICsvContent } from 'app/shared/model/csv-content.model';

@Injectable({ providedIn: 'root' })
export class CsvContentResolve implements Resolve<ICsvContent> {
  constructor(private service: CsvContentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICsvContent> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CsvContent>) => response.ok),
        map((csvContent: HttpResponse<CsvContent>) => csvContent.body)
      );
    }
    return of(new CsvContent());
  }
}

export const csvContentRoute: Routes = [
  {
    path: '',
    component: CsvContentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CsvContentDetailComponent,
    resolve: {
      csvContent: CsvContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CsvContentUpdateComponent,
    resolve: {
      csvContent: CsvContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CsvContentUpdateComponent,
    resolve: {
      csvContent: CsvContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvContents'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const csvContentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CsvContentDeletePopupComponent,
    resolve: {
      csvContent: CsvContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvContents'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
