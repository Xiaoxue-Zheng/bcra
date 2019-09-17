import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RadioQuestionItemComponent,
  RadioQuestionItemDetailComponent,
  RadioQuestionItemUpdateComponent,
  RadioQuestionItemDeletePopupComponent,
  RadioQuestionItemDeleteDialogComponent,
  radioQuestionItemRoute,
  radioQuestionItemPopupRoute
} from './';

const ENTITY_STATES = [...radioQuestionItemRoute, ...radioQuestionItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RadioQuestionItemComponent,
    RadioQuestionItemDetailComponent,
    RadioQuestionItemUpdateComponent,
    RadioQuestionItemDeleteDialogComponent,
    RadioQuestionItemDeletePopupComponent
  ],
  entryComponents: [
    RadioQuestionItemComponent,
    RadioQuestionItemUpdateComponent,
    RadioQuestionItemDeleteDialogComponent,
    RadioQuestionItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRadioQuestionItemModule {}
