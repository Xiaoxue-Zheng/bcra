import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RepeatAnswerComponent,
  RepeatAnswerDetailComponent,
  RepeatAnswerUpdateComponent,
  RepeatAnswerDeletePopupComponent,
  RepeatAnswerDeleteDialogComponent,
  repeatAnswerRoute,
  repeatAnswerPopupRoute
} from './';

const ENTITY_STATES = [...repeatAnswerRoute, ...repeatAnswerPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RepeatAnswerComponent,
    RepeatAnswerDetailComponent,
    RepeatAnswerUpdateComponent,
    RepeatAnswerDeleteDialogComponent,
    RepeatAnswerDeletePopupComponent
  ],
  entryComponents: [
    RepeatAnswerComponent,
    RepeatAnswerUpdateComponent,
    RepeatAnswerDeleteDialogComponent,
    RepeatAnswerDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRepeatAnswerModule {}
