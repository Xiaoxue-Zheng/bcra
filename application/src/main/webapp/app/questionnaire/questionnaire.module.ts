import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import { questionnaireRoute } from './questionnaire.route';
import { QuestionnaireComponent } from './questionnaire.component';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild([questionnaireRoute])],
  declarations: [QuestionnaireComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraQuestionnaireModule {}
