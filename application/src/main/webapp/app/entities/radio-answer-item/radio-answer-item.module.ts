import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  RadioAnswerItemComponent,
  RadioAnswerItemDetailComponent,
  RadioAnswerItemUpdateComponent,
  RadioAnswerItemDeletePopupComponent,
  RadioAnswerItemDeleteDialogComponent,
  radioAnswerItemRoute,
  radioAnswerItemPopupRoute
} from './';

const ENTITY_STATES = [...radioAnswerItemRoute, ...radioAnswerItemPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RadioAnswerItemComponent,
    RadioAnswerItemDetailComponent,
    RadioAnswerItemUpdateComponent,
    RadioAnswerItemDeleteDialogComponent,
    RadioAnswerItemDeletePopupComponent
  ],
  entryComponents: [
    RadioAnswerItemComponent,
    RadioAnswerItemUpdateComponent,
    RadioAnswerItemDeleteDialogComponent,
    RadioAnswerItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraRadioAnswerItemModule {}
