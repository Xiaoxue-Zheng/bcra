import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CheckboxAnswerItemComponent,
  CheckboxAnswerItemDetailComponent,
  CheckboxAnswerItemUpdateComponent,
  CheckboxAnswerItemDeletePopupComponent,
  CheckboxAnswerItemDeleteDialogComponent,
  checkboxAnswerItemRoute,
  checkboxAnswerItemPopupRoute
} from './';

const ENTITY_STATES = [...checkboxAnswerItemRoute, ...checkboxAnswerItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CheckboxAnswerItemComponent,
    CheckboxAnswerItemDetailComponent,
    CheckboxAnswerItemUpdateComponent,
    CheckboxAnswerItemDeleteDialogComponent,
    CheckboxAnswerItemDeletePopupComponent
  ],
  entryComponents: [
    CheckboxAnswerItemComponent,
    CheckboxAnswerItemUpdateComponent,
    CheckboxAnswerItemDeleteDialogComponent,
    CheckboxAnswerItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCheckboxAnswerItemModule {}
