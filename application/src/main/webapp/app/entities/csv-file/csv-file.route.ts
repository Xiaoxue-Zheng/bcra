import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from './csv-file.service';
import { CsvFileComponent } from './csv-file.component';
import { CsvFileDetailComponent } from './csv-file-detail.component';
import { CsvFileUpdateComponent } from './csv-file-update.component';
import { CsvFileDeletePopupComponent } from './csv-file-delete-dialog.component';
import { ICsvFile } from 'app/shared/model/csv-file.model';

@Injectable({ providedIn: 'root' })
export class CsvFileResolve implements Resolve<ICsvFile> {
  constructor(private service: CsvFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICsvFile> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CsvFile>) => response.ok),
        map((csvFile: HttpResponse<CsvFile>) => csvFile.body)
      );
    }
    return of(new CsvFile());
  }
}

export const csvFileRoute: Routes = [
  {
    path: '',
    component: CsvFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CsvFileDetailComponent,
    resolve: {
      csvFile: CsvFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CsvFileUpdateComponent,
    resolve: {
      csvFile: CsvFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvFiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CsvFileUpdateComponent,
    resolve: {
      csvFile: CsvFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvFiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const csvFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CsvFileDeletePopupComponent,
    resolve: {
      csvFile: CsvFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CsvFiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
