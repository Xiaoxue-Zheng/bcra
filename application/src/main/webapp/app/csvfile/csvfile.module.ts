import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import { csvfileRoute } from './csvfile.route';
import { CsvFileComponent } from './csvfile.component';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild([csvfileRoute])],
  declarations: [CsvFileComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraCsvFileModule {}
