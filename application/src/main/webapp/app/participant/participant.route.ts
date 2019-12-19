import { Route } from '@angular/router';

import { ParticipantComponent } from './participant.component';

export const participantRoute: Route = {
  path: 'participants',
  component: ParticipantComponent,
  data: {
    pageTitle: 'Participants'
  }
};
