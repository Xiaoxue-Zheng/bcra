import { Route } from '@angular/router';

import { TyrerCuzickTestComponent } from './tyrercuzicktest.component';

export const tyrerCuzickTestRoute: Route = {
  path: 'tyrercuzicktest',
  component: TyrerCuzickTestComponent,
  data: {
    pageTitle: 'Tyrer Cuzick Test'
  }
};
