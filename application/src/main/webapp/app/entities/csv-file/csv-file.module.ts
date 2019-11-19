import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import {
  CsvFileComponent,
  CsvFileDetailComponent,
  CsvFileUpdateComponent,
  CsvFileDeletePopupComponent,
  CsvFileDeleteDialogComponent,
  csvFileRoute,
  csvFilePopupRoute
} from './';

const ENTITY_STATES = [...csvFileRoute, ...csvFilePopupRoute];

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CsvFileComponent,
    CsvFileDetailComponent,
    CsvFileUpdateComponent,
    CsvFileDeleteDialogComponent,
    CsvFileDeletePopupComponent
  ],
  entryComponents: [CsvFileComponent, CsvFileUpdateComponent, CsvFileDeleteDialogComponent, CsvFileDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCsvFileModule {}
