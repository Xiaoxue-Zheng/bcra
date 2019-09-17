import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  QuestionnaireComponent,
  QuestionnaireDetailComponent,
  QuestionnaireUpdateComponent,
  QuestionnaireDeletePopupComponent,
  QuestionnaireDeleteDialogComponent,
  questionnaireRoute,
  questionnairePopupRoute
} from './';

const ENTITY_STATES = [...questionnaireRoute, ...questionnairePopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QuestionnaireComponent,
    QuestionnaireDetailComponent,
    QuestionnaireUpdateComponent,
    QuestionnaireDeleteDialogComponent,
    QuestionnaireDeletePopupComponent
  ],
  entryComponents: [
    QuestionnaireComponent,
    QuestionnaireUpdateComponent,
    QuestionnaireDeleteDialogComponent,
    QuestionnaireDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionnaireModule {}
