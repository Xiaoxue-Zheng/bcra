import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  NumberCheckboxQuestionComponent,
  NumberCheckboxQuestionDetailComponent,
  NumberCheckboxQuestionUpdateComponent,
  NumberCheckboxQuestionDeletePopupComponent,
  NumberCheckboxQuestionDeleteDialogComponent,
  numberCheckboxQuestionRoute,
  numberCheckboxQuestionPopupRoute
} from './';

const ENTITY_STATES = [...numberCheckboxQuestionRoute, ...numberCheckboxQuestionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NumberCheckboxQuestionComponent,
    NumberCheckboxQuestionDetailComponent,
    NumberCheckboxQuestionUpdateComponent,
    NumberCheckboxQuestionDeleteDialogComponent,
    NumberCheckboxQuestionDeletePopupComponent
  ],
  entryComponents: [
    NumberCheckboxQuestionComponent,
    NumberCheckboxQuestionUpdateComponent,
    NumberCheckboxQuestionDeleteDialogComponent,
    NumberCheckboxQuestionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraNumberCheckboxQuestionModule {}
