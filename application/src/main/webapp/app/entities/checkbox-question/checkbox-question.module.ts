import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CheckboxQuestionComponent,
  CheckboxQuestionDetailComponent,
  CheckboxQuestionUpdateComponent,
  CheckboxQuestionDeletePopupComponent,
  CheckboxQuestionDeleteDialogComponent,
  checkboxQuestionRoute,
  checkboxQuestionPopupRoute
} from './';

const ENTITY_STATES = [...checkboxQuestionRoute, ...checkboxQuestionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CheckboxQuestionComponent,
    CheckboxQuestionDetailComponent,
    CheckboxQuestionUpdateComponent,
    CheckboxQuestionDeleteDialogComponent,
    CheckboxQuestionDeletePopupComponent
  ],
  entryComponents: [
    CheckboxQuestionComponent,
    CheckboxQuestionUpdateComponent,
    CheckboxQuestionDeleteDialogComponent,
    CheckboxQuestionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCheckboxQuestionModule {}
