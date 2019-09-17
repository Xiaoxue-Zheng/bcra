import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  AnswerResponseComponent,
  AnswerResponseDetailComponent,
  AnswerResponseUpdateComponent,
  AnswerResponseDeletePopupComponent,
  AnswerResponseDeleteDialogComponent,
  answerResponseRoute,
  answerResponsePopupRoute
} from './';

const ENTITY_STATES = [...answerResponseRoute, ...answerResponsePopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnswerResponseComponent,
    AnswerResponseDetailComponent,
    AnswerResponseUpdateComponent,
    AnswerResponseDeleteDialogComponent,
    AnswerResponseDeletePopupComponent
  ],
  entryComponents: [
    AnswerResponseComponent,
    AnswerResponseUpdateComponent,
    AnswerResponseDeleteDialogComponent,
    AnswerResponseDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraAnswerResponseModule {}
