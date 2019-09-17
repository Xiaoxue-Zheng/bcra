import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  NumberCheckboxAnswerComponent,
  NumberCheckboxAnswerDetailComponent,
  NumberCheckboxAnswerUpdateComponent,
  NumberCheckboxAnswerDeletePopupComponent,
  NumberCheckboxAnswerDeleteDialogComponent,
  numberCheckboxAnswerRoute,
  numberCheckboxAnswerPopupRoute
} from './';

const ENTITY_STATES = [...numberCheckboxAnswerRoute, ...numberCheckboxAnswerPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NumberCheckboxAnswerComponent,
    NumberCheckboxAnswerDetailComponent,
    NumberCheckboxAnswerUpdateComponent,
    NumberCheckboxAnswerDeleteDialogComponent,
    NumberCheckboxAnswerDeletePopupComponent
  ],
  entryComponents: [
    NumberCheckboxAnswerComponent,
    NumberCheckboxAnswerUpdateComponent,
    NumberCheckboxAnswerDeleteDialogComponent,
    NumberCheckboxAnswerDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraNumberCheckboxAnswerModule {}
