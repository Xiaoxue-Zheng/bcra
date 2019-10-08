import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { AccountService } from 'app/core';
import { DisplayConditionService } from './display-condition.service';

@Component({
  selector: 'jhi-display-condition',
  templateUrl: './display-condition.component.html'
})
export class DisplayConditionComponent implements OnInit, OnDestroy {
  displayConditions: IDisplayCondition[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected displayConditionService: DisplayConditionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.displayConditionService
      .query()
      .pipe(
        filter((res: HttpResponse<IDisplayCondition[]>) => res.ok),
        map((res: HttpResponse<IDisplayCondition[]>) => res.body)
      )
      .subscribe(
        (res: IDisplayCondition[]) => {
          this.displayConditions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDisplayConditions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDisplayCondition) {
    return item.id;
  }

  registerChangeInDisplayConditions() {
    this.eventSubscriber = this.eventManager.subscribe('displayConditionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
