import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';
import { AccountService } from 'app/core';
import { IdentifiableDataService } from './identifiable-data.service';

@Component({
  selector: 'jhi-identifiable-data',
  templateUrl: './identifiable-data.component.html'
})
export class IdentifiableDataComponent implements OnInit, OnDestroy {
  identifiableData: IIdentifiableData[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected identifiableDataService: IdentifiableDataService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.identifiableDataService
      .query()
      .pipe(
        filter((res: HttpResponse<IIdentifiableData[]>) => res.ok),
        map((res: HttpResponse<IIdentifiableData[]>) => res.body)
      )
      .subscribe(
        (res: IIdentifiableData[]) => {
          this.identifiableData = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIdentifiableData();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIdentifiableData) {
    return item.id;
  }

  registerChangeInIdentifiableData() {
    this.eventSubscriber = this.eventManager.subscribe('identifiableDataListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
