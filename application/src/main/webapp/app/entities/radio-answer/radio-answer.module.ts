import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RadioAnswerComponent,
  RadioAnswerDetailComponent,
  RadioAnswerUpdateComponent,
  RadioAnswerDeletePopupComponent,
  RadioAnswerDeleteDialogComponent,
  radioAnswerRoute,
  radioAnswerPopupRoute
} from './';

const ENTITY_STATES = [...radioAnswerRoute, ...radioAnswerPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RadioAnswerComponent,
    RadioAnswerDetailComponent,
    RadioAnswerUpdateComponent,
    RadioAnswerDeleteDialogComponent,
    RadioAnswerDeletePopupComponent
  ],
  entryComponents: [RadioAnswerComponent, RadioAnswerUpdateComponent, RadioAnswerDeleteDialogComponent, RadioAnswerDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRadioAnswerModule {}
