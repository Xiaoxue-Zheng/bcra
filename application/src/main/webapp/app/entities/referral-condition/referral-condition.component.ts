import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { AccountService } from 'app/core';
import { ReferralConditionService } from './referral-condition.service';

@Component({
  selector: 'jhi-referral-condition',
  templateUrl: './referral-condition.component.html'
})
export class ReferralConditionComponent implements OnInit, OnDestroy {
  referralConditions: IReferralCondition[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected referralConditionService: ReferralConditionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.referralConditionService
      .query()
      .pipe(
        filter((res: HttpResponse<IReferralCondition[]>) => res.ok),
        map((res: HttpResponse<IReferralCondition[]>) => res.body)
      )
      .subscribe(
        (res: IReferralCondition[]) => {
          this.referralConditions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReferralConditions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReferralCondition) {
    return item.id;
  }

  registerChangeInReferralConditions() {
    this.eventSubscriber = this.eventManager.subscribe('referralConditionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
