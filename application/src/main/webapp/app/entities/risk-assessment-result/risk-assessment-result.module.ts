import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { RiskAssessmentResultComponent } from './risk-assessment-result.component';
import { RiskAssessmentResultDetailComponent } from './risk-assessment-result-detail.component';
import { RiskAssessmentResultUpdateComponent } from './risk-assessment-result-update.component';
import { RiskAssessmentResultDeleteDialogComponent } from './risk-assessment-result-delete-dialog.component';
import { riskAssessmentResultRoute } from './risk-assessment-result.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(riskAssessmentResultRoute)],
  declarations: [
    RiskAssessmentResultComponent,
    RiskAssessmentResultDetailComponent,
    RiskAssessmentResultUpdateComponent,
    RiskAssessmentResultDeleteDialogComponent
  ],
  entryComponents: [RiskAssessmentResultDeleteDialogComponent]
})
export class BcraRiskAssessmentResultModule {}
