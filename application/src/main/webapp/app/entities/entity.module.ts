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
        path: 'question-section',
        loadChildren: () => import('./question-section/question-section.module').then(m => m.BcraQuestionSectionModule)
      },
      {
        path: 'question-group',
        loadChildren: () => import('./question-group/question-group.module').then(m => m.BcraQuestionGroupModule)
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.BcraQuestionModule)
      },
      {
        path: 'question-item',
        loadChildren: () => import('./question-item/question-item.module').then(m => m.BcraQuestionItemModule)
      },
      {
        path: 'answer-response',
        loadChildren: () => import('./answer-response/answer-response.module').then(m => m.BcraAnswerResponseModule)
      },
      {
        path: 'answer-section',
        loadChildren: () => import('./answer-section/answer-section.module').then(m => m.BcraAnswerSectionModule)
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
        path: 'answer-item',
        loadChildren: () => import('./answer-item/answer-item.module').then(m => m.BcraAnswerItemModule)
      },
      {
        path: 'display-condition',
        loadChildren: () => import('./display-condition/display-condition.module').then(m => m.BcraDisplayConditionModule)
      },
      {
        path: 'referral-condition',
        loadChildren: () => import('./referral-condition/referral-condition.module').then(m => m.BcraReferralConditionModule)
      },
      {
        path: 'csv-file',
        loadChildren: () => import('./csv-file/csv-file.module').then(m => m.BcraCsvFileModule)
      },
      {
        path: 'participant',
        loadChildren: () => import('./participant/participant.module').then(m => m.BcraParticipantModule)
      },
      {
        path: 'identifiable-data',
        loadChildren: () => import('./identifiable-data/identifiable-data.module').then(m => m.BcraIdentifiableDataModule)
      },
      {
        path: 'csv-content',
        loadChildren: () => import('./csv-content/csv-content.module').then(m => m.BcraCsvContentModule)
      },
      {
        path: 'procedure',
        loadChildren: () => import('./procedure/procedure.module').then(m => m.BcraProcedureModule)
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
