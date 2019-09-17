import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CheckboxQuestionItemComponent,
  CheckboxQuestionItemDetailComponent,
  CheckboxQuestionItemUpdateComponent,
  CheckboxQuestionItemDeletePopupComponent,
  CheckboxQuestionItemDeleteDialogComponent,
  checkboxQuestionItemRoute,
  checkboxQuestionItemPopupRoute
} from './';

const ENTITY_STATES = [...checkboxQuestionItemRoute, ...checkboxQuestionItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CheckboxQuestionItemComponent,
    CheckboxQuestionItemDetailComponent,
    CheckboxQuestionItemUpdateComponent,
    CheckboxQuestionItemDeleteDialogComponent,
    CheckboxQuestionItemDeletePopupComponent
  ],
  entryComponents: [
    CheckboxQuestionItemComponent,
    CheckboxQuestionItemUpdateComponent,
    CheckboxQuestionItemDeleteDialogComponent,
    CheckboxQuestionItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCheckboxQuestionItemModule {}
