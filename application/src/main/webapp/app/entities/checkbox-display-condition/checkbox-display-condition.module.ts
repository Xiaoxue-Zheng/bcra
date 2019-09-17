import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CheckboxDisplayConditionComponent,
  CheckboxDisplayConditionDetailComponent,
  CheckboxDisplayConditionUpdateComponent,
  CheckboxDisplayConditionDeletePopupComponent,
  CheckboxDisplayConditionDeleteDialogComponent,
  checkboxDisplayConditionRoute,
  checkboxDisplayConditionPopupRoute
} from './';

const ENTITY_STATES = [...checkboxDisplayConditionRoute, ...checkboxDisplayConditionPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CheckboxDisplayConditionComponent,
    CheckboxDisplayConditionDetailComponent,
    CheckboxDisplayConditionUpdateComponent,
    CheckboxDisplayConditionDeleteDialogComponent,
    CheckboxDisplayConditionDeletePopupComponent
  ],
  entryComponents: [
    CheckboxDisplayConditionComponent,
    CheckboxDisplayConditionUpdateComponent,
    CheckboxDisplayConditionDeleteDialogComponent,
    CheckboxDisplayConditionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCheckboxDisplayConditionModule {}
