import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { StudyIdComponent } from './study-id.component';
import { StudyIdDetailComponent } from './study-id-detail.component';
import { StudyIdUpdateComponent } from './study-id-update.component';
import { StudyIdDeleteDialogComponent } from './study-id-delete-dialog.component';
import { studyIdRoute } from './study-id.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(studyIdRoute)],
  declarations: [StudyIdComponent, StudyIdDetailComponent, StudyIdUpdateComponent, StudyIdDeleteDialogComponent],
  entryComponents: [StudyIdDeleteDialogComponent]
})
export class BcraStudyIdModule {}
