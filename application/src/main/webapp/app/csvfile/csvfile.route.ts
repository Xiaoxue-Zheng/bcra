import { Route } from '@angular/router';

import { CsvFileComponent } from './csvfile.component';

export const csvfileRoute: Route = {
  path: 'csvfiles',
  component: CsvFileComponent,
  data: {
    pageTitle: 'CSV Files'
  }
};
