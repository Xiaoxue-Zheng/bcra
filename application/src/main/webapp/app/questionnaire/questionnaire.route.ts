import { Route } from '@angular/router';

import { QuestionnaireComponent } from './questionnaire.component';

export const questionnaireRoute: Route = {
  path: 'questionnaires',
  component: QuestionnaireComponent,
  data: {
    pageTitle: 'Questionnaires'
  }
};
