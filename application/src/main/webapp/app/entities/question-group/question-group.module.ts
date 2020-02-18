import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared/shared.module';
import { QuestionGroupComponent } from './question-group.component';
import { QuestionGroupDetailComponent } from './question-group-detail.component';
import { QuestionGroupUpdateComponent } from './question-group-update.component';
import { QuestionGroupDeleteDialogComponent } from './question-group-delete-dialog.component';
import { questionGroupRoute } from './question-group.route';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild(questionGroupRoute)],
  declarations: [QuestionGroupComponent, QuestionGroupDetailComponent, QuestionGroupUpdateComponent, QuestionGroupDeleteDialogComponent],
  entryComponents: [QuestionGroupDeleteDialogComponent]
})
export class BcraQuestionGroupModule {}
