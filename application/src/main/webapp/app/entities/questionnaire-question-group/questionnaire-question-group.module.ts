import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionnaireQuestionGroupComponent,
  QuestionnaireQuestionGroupDetailComponent,
  QuestionnaireQuestionGroupUpdateComponent,
  QuestionnaireQuestionGroupDeletePopupComponent,
  QuestionnaireQuestionGroupDeleteDialogComponent,
  questionnaireQuestionGroupRoute,
  questionnaireQuestionGroupPopupRoute
} from './';

const ENTITY_STATES = [...questionnaireQuestionGroupRoute, ...questionnaireQuestionGroupPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionnaireQuestionGroupComponent,
    QuestionnaireQuestionGroupDetailComponent,
    QuestionnaireQuestionGroupUpdateComponent,
    QuestionnaireQuestionGroupDeleteDialogComponent,
    QuestionnaireQuestionGroupDeletePopupComponent
  ],
  entryComponents: [
    QuestionnaireQuestionGroupComponent,
    QuestionnaireQuestionGroupUpdateComponent,
    QuestionnaireQuestionGroupDeleteDialogComponent,
    QuestionnaireQuestionGroupDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionnaireQuestionGroupModule {}
