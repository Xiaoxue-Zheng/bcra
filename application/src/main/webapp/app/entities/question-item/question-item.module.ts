import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionItemComponent,
  QuestionItemDetailComponent,
  QuestionItemUpdateComponent,
  QuestionItemDeletePopupComponent,
  QuestionItemDeleteDialogComponent,
  questionItemRoute,
  questionItemPopupRoute
} from './';

const ENTITY_STATES = [...questionItemRoute, ...questionItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionItemComponent,
    QuestionItemDetailComponent,
    QuestionItemUpdateComponent,
    QuestionItemDeleteDialogComponent,
    QuestionItemDeletePopupComponent
  ],
  entryComponents: [
    QuestionItemComponent,
    QuestionItemUpdateComponent,
    QuestionItemDeleteDialogComponent,
    QuestionItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionItemModule {}
