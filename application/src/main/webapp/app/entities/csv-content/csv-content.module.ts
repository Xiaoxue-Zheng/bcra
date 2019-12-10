import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CsvContentComponent,
  CsvContentDetailComponent,
  CsvContentUpdateComponent,
  CsvContentDeletePopupComponent,
  CsvContentDeleteDialogComponent,
  csvContentRoute,
  csvContentPopupRoute
} from './';

const ENTITY_STATES = [...csvContentRoute, ...csvContentPopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CsvContentComponent,
    CsvContentDetailComponent,
    CsvContentUpdateComponent,
    CsvContentDeleteDialogComponent,
    CsvContentDeletePopupComponent
  ],
  entryComponents: [CsvContentComponent, CsvContentUpdateComponent, CsvContentDeleteDialogComponent, CsvContentDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCsvContentModule {}
