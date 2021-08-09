import { Routes } from '@angular/router';
import { CanRiskReportComponent } from './can-risk-report.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CanRiskReportUploadComponent } from './can-risk-report-upload.component';

export const canRiskReportRoute: Routes = [
  {
    path: 'can-risk-report',
    component: CanRiskReportComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CLINICIAN'],
      pageTitle: 'CanRisk Reports'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'can-risk-report/upload',
    component: CanRiskReportUploadComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CLINICIAN'],
      pageTitle: 'Upload CanRisk Reports'
    },
    canActivate: [UserRouteAccessService]
  }
];
