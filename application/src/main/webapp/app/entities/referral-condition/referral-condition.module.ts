import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { ReferralConditionComponent } from './referral-condition.component';
import { ReferralConditionDetailComponent } from './referral-condition-detail.component';
import { ReferralConditionUpdateComponent } from './referral-condition-update.component';
import { ReferralConditionDeleteDialogComponent } from './referral-condition-delete-dialog.component';
import { referralConditionRoute } from './referral-condition.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(referralConditionRoute)],
  declarations: [
    ReferralConditionComponent,
    ReferralConditionDetailComponent,
    ReferralConditionUpdateComponent,
    ReferralConditionDeleteDialogComponent
  ],
  entryComponents: [ReferralConditionDeleteDialogComponent]
})
export class BcraReferralConditionModule {}
