import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { QuestionSectionComponent } from './question-section.component';
import { QuestionSectionDetailComponent } from './question-section-detail.component';
import { QuestionSectionUpdateComponent } from './question-section-update.component';
import { QuestionSectionDeleteDialogComponent } from './question-section-delete-dialog.component';
import { questionSectionRoute } from './question-section.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(questionSectionRoute)],
  declarations: [
    QuestionSectionComponent,
    QuestionSectionDetailComponent,
    QuestionSectionUpdateComponent,
    QuestionSectionDeleteDialogComponent
  ],
  entryComponents: [QuestionSectionDeleteDialogComponent]
})
export class BcraQuestionSectionModule {}
