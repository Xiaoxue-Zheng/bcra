import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionGroupComponent,
  QuestionGroupDetailComponent,
  QuestionGroupUpdateComponent,
  QuestionGroupDeletePopupComponent,
  QuestionGroupDeleteDialogComponent,
  questionGroupRoute,
  questionGroupPopupRoute
} from './';

const ENTITY_STATES = [...questionGroupRoute, ...questionGroupPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionGroupComponent,
    QuestionGroupDetailComponent,
    QuestionGroupUpdateComponent,
    QuestionGroupDeleteDialogComponent,
    QuestionGroupDeletePopupComponent
  ],
  entryComponents: [
    QuestionGroupComponent,
    QuestionGroupUpdateComponent,
    QuestionGroupDeleteDialogComponent,
    QuestionGroupDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionGroupModule {}
