import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { PageIdentifierComponent } from './page-identifier.component';
import { PageIdentifierDetailComponent } from './page-identifier-detail.component';
import { pageIdentifierRoute } from './page-identifier.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(pageIdentifierRoute)],
  declarations: [PageIdentifierComponent, PageIdentifierDetailComponent]
})
export class BcraPageIdentifierModule {}
