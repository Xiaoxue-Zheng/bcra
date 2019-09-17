import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionGroupQuestionComponent,
  QuestionGroupQuestionDetailComponent,
  QuestionGroupQuestionUpdateComponent,
  QuestionGroupQuestionDeletePopupComponent,
  QuestionGroupQuestionDeleteDialogComponent,
  questionGroupQuestionRoute,
  questionGroupQuestionPopupRoute
} from './';

const ENTITY_STATES = [...questionGroupQuestionRoute, ...questionGroupQuestionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionGroupQuestionComponent,
    QuestionGroupQuestionDetailComponent,
    QuestionGroupQuestionUpdateComponent,
    QuestionGroupQuestionDeleteDialogComponent,
    QuestionGroupQuestionDeletePopupComponent
  ],
  entryComponents: [
    QuestionGroupQuestionComponent,
    QuestionGroupQuestionUpdateComponent,
    QuestionGroupQuestionDeleteDialogComponent,
    QuestionGroupQuestionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionGroupQuestionModule {}
