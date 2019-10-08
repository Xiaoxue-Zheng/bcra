import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionSectionComponent,
  QuestionSectionDetailComponent,
  QuestionSectionUpdateComponent,
  QuestionSectionDeletePopupComponent,
  QuestionSectionDeleteDialogComponent,
  questionSectionRoute,
  questionSectionPopupRoute
} from './';

const ENTITY_STATES = [...questionSectionRoute, ...questionSectionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionSectionComponent,
    QuestionSectionDetailComponent,
    QuestionSectionUpdateComponent,
    QuestionSectionDeleteDialogComponent,
    QuestionSectionDeletePopupComponent
  ],
  entryComponents: [
    QuestionSectionComponent,
    QuestionSectionUpdateComponent,
    QuestionSectionDeleteDialogComponent,
    QuestionSectionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionSectionModule {}
