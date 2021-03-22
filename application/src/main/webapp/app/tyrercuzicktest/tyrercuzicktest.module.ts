import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import { tyrerCuzickTestRoute } from './tyrercuzicktest.route';
import { TyrerCuzickTestComponent } from './tyrercuzicktest.component';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild([tyrerCuzickTestRoute])],
  declarations: [TyrerCuzickTestComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraTyrerCuzickTestModule {}
