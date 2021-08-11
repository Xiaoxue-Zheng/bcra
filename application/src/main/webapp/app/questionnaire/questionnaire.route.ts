import { Route } from '@angular/router';

import { QuestionnaireComponent } from './questionnaire.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const questionnaireRoute: Route = {
  path: 'questionnaires',
  component: QuestionnaireComponent,
  data: {
    pageTitle: 'Questionnaires'
  },
  canActivate: [UserRouteAccessService]
};
