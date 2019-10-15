import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  ReferralConditionComponent,
  ReferralConditionDetailComponent,
  ReferralConditionUpdateComponent,
  ReferralConditionDeletePopupComponent,
  ReferralConditionDeleteDialogComponent,
  referralConditionRoute,
  referralConditionPopupRoute
} from './';

const ENTITY_STATES = [...referralConditionRoute, ...referralConditionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReferralConditionComponent,
    ReferralConditionDetailComponent,
    ReferralConditionUpdateComponent,
    ReferralConditionDeleteDialogComponent,
    ReferralConditionDeletePopupComponent
  ],
  entryComponents: [
    ReferralConditionComponent,
    ReferralConditionUpdateComponent,
    ReferralConditionDeleteDialogComponent,
    ReferralConditionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraReferralConditionModule {}
