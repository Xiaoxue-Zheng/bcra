import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RadioQuestionComponent,
  RadioQuestionDetailComponent,
  RadioQuestionUpdateComponent,
  RadioQuestionDeletePopupComponent,
  RadioQuestionDeleteDialogComponent,
  radioQuestionRoute,
  radioQuestionPopupRoute
} from './';

const ENTITY_STATES = [...radioQuestionRoute, ...radioQuestionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RadioQuestionComponent,
    RadioQuestionDetailComponent,
    RadioQuestionUpdateComponent,
    RadioQuestionDeleteDialogComponent,
    RadioQuestionDeletePopupComponent
  ],
  entryComponents: [
    RadioQuestionComponent,
    RadioQuestionUpdateComponent,
    RadioQuestionDeleteDialogComponent,
    RadioQuestionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRadioQuestionModule {}
