import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RepeatQuestionComponent,
  RepeatQuestionDetailComponent,
  RepeatQuestionUpdateComponent,
  RepeatQuestionDeletePopupComponent,
  RepeatQuestionDeleteDialogComponent,
  repeatQuestionRoute,
  repeatQuestionPopupRoute
} from './';

const ENTITY_STATES = [...repeatQuestionRoute, ...repeatQuestionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RepeatQuestionComponent,
    RepeatQuestionDetailComponent,
    RepeatQuestionUpdateComponent,
    RepeatQuestionDeleteDialogComponent,
    RepeatQuestionDeletePopupComponent
  ],
  entryComponents: [
    RepeatQuestionComponent,
    RepeatQuestionUpdateComponent,
    RepeatQuestionDeleteDialogComponent,
    RepeatQuestionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRepeatQuestionModule {}
