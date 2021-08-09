import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import { canRiskReportRoute } from './can-risk-report.route';
import { CanRiskReportComponent } from './can-risk-report.component';
import { CanRiskReportUploadComponent } from './can-risk-report-upload.component';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(canRiskReportRoute)],
  declarations: [CanRiskReportComponent, CanRiskReportUploadComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCanRiskReportModule {}
