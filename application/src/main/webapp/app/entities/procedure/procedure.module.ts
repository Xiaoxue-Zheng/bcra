import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  ProcedureComponent,
  ProcedureDetailComponent,
  ProcedureUpdateComponent,
  ProcedureDeletePopupComponent,
  ProcedureDeleteDialogComponent,
  procedureRoute,
  procedurePopupRoute
} from './';

const ENTITY_STATES = [...procedureRoute, ...procedurePopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProcedureComponent,
    ProcedureDetailComponent,
    ProcedureUpdateComponent,
    ProcedureDeleteDialogComponent,
    ProcedureDeletePopupComponent
  ],
  entryComponents: [ProcedureComponent, ProcedureUpdateComponent, ProcedureDeleteDialogComponent, ProcedureDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraProcedureModule {}
