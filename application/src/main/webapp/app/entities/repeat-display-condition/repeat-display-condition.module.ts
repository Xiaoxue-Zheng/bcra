import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RepeatDisplayConditionComponent,
  RepeatDisplayConditionDetailComponent,
  RepeatDisplayConditionUpdateComponent,
  RepeatDisplayConditionDeletePopupComponent,
  RepeatDisplayConditionDeleteDialogComponent,
  repeatDisplayConditionRoute,
  repeatDisplayConditionPopupRoute
} from './';

const ENTITY_STATES = [...repeatDisplayConditionRoute, ...repeatDisplayConditionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RepeatDisplayConditionComponent,
    RepeatDisplayConditionDetailComponent,
    RepeatDisplayConditionUpdateComponent,
    RepeatDisplayConditionDeleteDialogComponent,
    RepeatDisplayConditionDeletePopupComponent
  ],
  entryComponents: [
    RepeatDisplayConditionComponent,
    RepeatDisplayConditionUpdateComponent,
    RepeatDisplayConditionDeleteDialogComponent,
    RepeatDisplayConditionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRepeatDisplayConditionModule {}
