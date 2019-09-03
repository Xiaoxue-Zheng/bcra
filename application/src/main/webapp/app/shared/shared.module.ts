import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BcraSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BcraSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BcraSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraSharedModule {
  static forRoot() {
    return {
      ngModule: BcraSharedModule
    };
  }
}
