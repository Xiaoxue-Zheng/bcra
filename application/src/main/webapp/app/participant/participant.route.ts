import { Route } from '@angular/router';

import { ParticipantComponent } from './participant.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';

export const participantRoute: Route = {
  path: 'participants',
  component: ParticipantComponent,
  data: {
    pageTitle: 'Participants'
  },
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  canActivate: [UserRouteAccessService]
};
