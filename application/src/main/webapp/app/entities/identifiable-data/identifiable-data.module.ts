import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  IdentifiableDataComponent,
  IdentifiableDataDetailComponent,
  IdentifiableDataUpdateComponent,
  IdentifiableDataDeletePopupComponent,
  IdentifiableDataDeleteDialogComponent,
  identifiableDataRoute,
  identifiableDataPopupRoute
} from './';

const ENTITY_STATES = [...identifiableDataRoute, ...identifiableDataPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IdentifiableDataComponent,
    IdentifiableDataDetailComponent,
    IdentifiableDataUpdateComponent,
    IdentifiableDataDeleteDialogComponent,
    IdentifiableDataDeletePopupComponent
  ],
  entryComponents: [
    IdentifiableDataComponent,
    IdentifiableDataUpdateComponent,
    IdentifiableDataDeleteDialogComponent,
    IdentifiableDataDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraIdentifiableDataModule {}
