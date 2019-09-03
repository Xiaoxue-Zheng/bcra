import { NgModule } from '@angular/core';

import { BcraSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [BcraSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [BcraSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BcraSharedCommonModule {}
