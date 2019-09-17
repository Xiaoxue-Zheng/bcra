import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  AnswerGroupComponent,
  AnswerGroupDetailComponent,
  AnswerGroupUpdateComponent,
  AnswerGroupDeletePopupComponent,
  AnswerGroupDeleteDialogComponent,
  answerGroupRoute,
  answerGroupPopupRoute
} from './';

const ENTITY_STATES = [...answerGroupRoute, ...answerGroupPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnswerGroupComponent,
    AnswerGroupDetailComponent,
    AnswerGroupUpdateComponent,
    AnswerGroupDeleteDialogComponent,
    AnswerGroupDeletePopupComponent
  ],
  entryComponents: [AnswerGroupComponent, AnswerGroupUpdateComponent, AnswerGroupDeleteDialogComponent, AnswerGroupDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraAnswerGroupModule {}
