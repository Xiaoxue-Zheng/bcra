import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { RiskFactorComponent } from './risk-factor.component';
import { RiskFactorDetailComponent } from './risk-factor-detail.component';
import { RiskFactorUpdateComponent } from './risk-factor-update.component';
import { RiskFactorDeleteDialogComponent } from './risk-factor-delete-dialog.component';
import { riskFactorRoute } from './risk-factor.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(riskFactorRoute)],
  declarations: [RiskFactorComponent, RiskFactorDetailComponent, RiskFactorUpdateComponent, RiskFactorDeleteDialogComponent],
  entryComponents: [RiskFactorDeleteDialogComponent]
})
export class BcraRiskFactorModule {}
