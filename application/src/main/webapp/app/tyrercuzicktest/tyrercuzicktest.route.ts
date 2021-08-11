import { Route } from '@angular/router';

import { TyrerCuzickTestComponent } from './tyrercuzicktest.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const tyrerCuzickTestRoute: Route = {
  path: 'tyrercuzicktest',
  component: TyrerCuzickTestComponent,
  data: {
    pageTitle: 'Tyrer Cuzick Test'
  },
  canActivate: [UserRouteAccessService]
};
