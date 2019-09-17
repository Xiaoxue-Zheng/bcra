import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'questionnaire',
        loadChildren: () => import('./questionnaire/questionnaire.module').then(m => m.BcraQuestionnaireModule)
      },
      {
        path: 'question-group',
        loadChildren: () => import('./question-group/question-group.module').then(m => m.BcraQuestionGroupModule)
      },
      {
        path: 'questionnaire-question-group',
        loadChildren: () =>
          import('./questionnaire-question-group/questionnaire-question-group.module').then(m => m.BcraQuestionnaireQuestionGroupModule)
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.BcraQuestionModule)
      },
      {
        path: 'question-group-question',
        loadChildren: () => import('./question-group-question/question-group-question.module').then(m => m.BcraQuestionGroupQuestionModule)
      },
      {
        path: 'checkbox-question',
        loadChildren: () => import('./checkbox-question/checkbox-question.module').then(m => m.BcraCheckboxQuestionModule)
      },
      {
        path: 'checkbox-question-item',
        loadChildren: () => import('./checkbox-question-item/checkbox-question-item.module').then(m => m.BcraCheckboxQuestionItemModule)
      },
      {
        path: 'radio-question',
        loadChildren: () => import('./radio-question/radio-question.module').then(m => m.BcraRadioQuestionModule)
      },
      {
        path: 'radio-question-item',
        loadChildren: () => import('./radio-question-item/radio-question-item.module').then(m => m.BcraRadioQuestionItemModule)
      },
      {
        path: 'number-checkbox-question',
        loadChildren: () =>
          import('./number-checkbox-question/number-checkbox-question.module').then(m => m.BcraNumberCheckboxQuestionModule)
      },
      {
        path: 'repeat-question',
        loadChildren: () => import('./repeat-question/repeat-question.module').then(m => m.BcraRepeatQuestionModule)
      },
      {
        path: 'answer-response',
        loadChildren: () => import('./answer-response/answer-response.module').then(m => m.BcraAnswerResponseModule)
      },
      {
        path: 'answer-group',
        loadChildren: () => import('./answer-group/answer-group.module').then(m => m.BcraAnswerGroupModule)
      },
      {
        path: 'answer',
        loadChildren: () => import('./answer/answer.module').then(m => m.BcraAnswerModule)
      },
      {
        path: 'checkbox-answer',
        loadChildren: () => import('./checkbox-answer/checkbox-answer.module').then(m => m.BcraCheckboxAnswerModule)
      },
      {
        path: 'checkbox-answer-item',
        loadChildren: () => import('./checkbox-answer-item/checkbox-answer-item.module').then(m => m.BcraCheckboxAnswerItemModule)
      },
      {
        path: 'radio-answer',
        loadChildren: () => import('./radio-answer/radio-answer.module').then(m => m.BcraRadioAnswerModule)
      },
      {
        path: 'radio-answer-item',
        loadChildren: () => import('./radio-answer-item/radio-answer-item.module').then(m => m.BcraRadioAnswerItemModule)
      },
      {
        path: 'number-checkbox-answer',
        loadChildren: () => import('./number-checkbox-answer/number-checkbox-answer.module').then(m => m.BcraNumberCheckboxAnswerModule)
      },
      {
        path: 'repeat-answer',
        loadChildren: () => import('./repeat-answer/repeat-answer.module').then(m => m.BcraRepeatAnswerModule)
      },
      {
        path: 'display-condition',
        loadChildren: () => import('./display-condition/display-condition.module').then(m => m.BcraDisplayConditionModule)
      },
      {
        path: 'checkbox-display-condition',
        loadChildren: () =>
          import('./checkbox-display-condition/checkbox-display-condition.module').then(m => m.BcraCheckboxDisplayConditionModule)
      },
      {
        path: 'repeat-display-condition',
        loadChildren: () =>
          import('./repeat-display-condition/repeat-display-condition.module').then(m => m.BcraRepeatDisplayConditionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraEntityModule {}
