import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  DisplayConditionComponent,
  DisplayConditionDetailComponent,
  DisplayConditionUpdateComponent,
  DisplayConditionDeletePopupComponent,
  DisplayConditionDeleteDialogComponent,
  displayConditionRoute,
  displayConditionPopupRoute
} from './';

const ENTITY_STATES = [...displayConditionRoute, ...displayConditionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DisplayConditionComponent,
    DisplayConditionDetailComponent,
    DisplayConditionUpdateComponent,
    DisplayConditionDeleteDialogComponent,
    DisplayConditionDeletePopupComponent
  ],
  entryComponents: [
    DisplayConditionComponent,
    DisplayConditionUpdateComponent,
    DisplayConditionDeleteDialogComponent,
    DisplayConditionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraDisplayConditionModule {}
