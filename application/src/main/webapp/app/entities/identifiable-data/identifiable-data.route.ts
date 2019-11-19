import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IdentifiableData } from 'app/shared/model/identifiable-data.model';
import { IdentifiableDataService } from './identifiable-data.service';
import { IdentifiableDataComponent } from './identifiable-data.component';
import { IdentifiableDataDetailComponent } from './identifiable-data-detail.component';
import { IdentifiableDataUpdateComponent } from './identifiable-data-update.component';
import { IdentifiableDataDeletePopupComponent } from './identifiable-data-delete-dialog.component';
import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';

@Injectable({ providedIn: 'root' })
export class IdentifiableDataResolve implements Resolve<IIdentifiableData> {
  constructor(private service: IdentifiableDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIdentifiableData> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IdentifiableData>) => response.ok),
        map((identifiableData: HttpResponse<IdentifiableData>) => identifiableData.body)
      );
    }
    return of(new IdentifiableData());
  }
}

export const identifiableDataRoute: Routes = [
  {
    path: '',
    component: IdentifiableDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdentifiableData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IdentifiableDataDetailComponent,
    resolve: {
      identifiableData: IdentifiableDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdentifiableData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IdentifiableDataUpdateComponent,
    resolve: {
      identifiableData: IdentifiableDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdentifiableData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IdentifiableDataUpdateComponent,
    resolve: {
      identifiableData: IdentifiableDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdentifiableData'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const identifiableDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IdentifiableDataDeletePopupComponent,
    resolve: {
      identifiableData: IdentifiableDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdentifiableData'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
