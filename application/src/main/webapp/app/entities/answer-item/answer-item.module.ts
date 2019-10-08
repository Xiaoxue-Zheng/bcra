import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  AnswerItemComponent,
  AnswerItemDetailComponent,
  AnswerItemUpdateComponent,
  AnswerItemDeletePopupComponent,
  AnswerItemDeleteDialogComponent,
  answerItemRoute,
  answerItemPopupRoute
} from './';

const ENTITY_STATES = [...answerItemRoute, ...answerItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnswerItemComponent,
    AnswerItemDetailComponent,
    AnswerItemUpdateComponent,
    AnswerItemDeleteDialogComponent,
    AnswerItemDeletePopupComponent
  ],
  entryComponents: [AnswerItemComponent, AnswerItemUpdateComponent, AnswerItemDeleteDialogComponent, AnswerItemDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraAnswerItemModule {}
