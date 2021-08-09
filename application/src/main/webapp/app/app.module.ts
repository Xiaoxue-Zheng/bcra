import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { BcraSharedModule } from 'app/shared';
import { BcraCoreModule } from 'app/core';
import { BcraAppRoutingModule } from './app-routing.module';
import { BcraHomeModule } from './home/home.module';
import { BcraQuestionnaireModule } from './questionnaire/questionnaire.module';
import { BcraParticipantModule } from './participant/participant.module';
import { BcraAccountModule } from './account/account.module';
import { BcraTyrerCuzickTestModule } from './tyrercuzicktest/tyrercuzicktest.module';
import { BcraStudyIdModule } from './study-id/study-id.module';
import { BcraCanRiskReportModule } from './can-risk-report/can-risk-report.module';

import moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';

@NgModule({
  imports: [
    BrowserModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000
    }),
    BcraSharedModule.forRoot(),
    BcraCoreModule,
    BcraHomeModule,
    BcraQuestionnaireModule,
    BcraParticipantModule,
    BcraAccountModule,
    BcraTyrerCuzickTestModule,
    BcraCanRiskReportModule,
    BcraStudyIdModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BcraAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }
  ],
  bootstrap: [JhiMainComponent]
})
export class BcraAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
