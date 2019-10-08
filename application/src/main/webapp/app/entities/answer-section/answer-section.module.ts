import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  AnswerSectionComponent,
  AnswerSectionDetailComponent,
  AnswerSectionUpdateComponent,
  AnswerSectionDeletePopupComponent,
  AnswerSectionDeleteDialogComponent,
  answerSectionRoute,
  answerSectionPopupRoute
} from './';

const ENTITY_STATES = [...answerSectionRoute, ...answerSectionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnswerSectionComponent,
    AnswerSectionDetailComponent,
    AnswerSectionUpdateComponent,
    AnswerSectionDeleteDialogComponent,
    AnswerSectionDeletePopupComponent
  ],
  entryComponents: [
    AnswerSectionComponent,
    AnswerSectionUpdateComponent,
    AnswerSectionDeleteDialogComponent,
    AnswerSectionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraAnswerSectionModule {}
