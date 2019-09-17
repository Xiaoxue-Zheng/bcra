import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CheckboxAnswerComponent,
  CheckboxAnswerDetailComponent,
  CheckboxAnswerUpdateComponent,
  CheckboxAnswerDeletePopupComponent,
  CheckboxAnswerDeleteDialogComponent,
  checkboxAnswerRoute,
  checkboxAnswerPopupRoute
} from './';

const ENTITY_STATES = [...checkboxAnswerRoute, ...checkboxAnswerPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CheckboxAnswerComponent,
    CheckboxAnswerDetailComponent,
    CheckboxAnswerUpdateComponent,
    CheckboxAnswerDeleteDialogComponent,
    CheckboxAnswerDeletePopupComponent
  ],
  entryComponents: [
    CheckboxAnswerComponent,
    CheckboxAnswerUpdateComponent,
    CheckboxAnswerDeleteDialogComponent,
    CheckboxAnswerDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCheckboxAnswerModule {}
