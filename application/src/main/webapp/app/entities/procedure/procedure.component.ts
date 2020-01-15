import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProcedure } from 'app/shared/model/procedure.model';
import { AccountService } from 'app/core';
import { ProcedureService } from './procedure.service';

@Component({
  selector: 'jhi-procedure',
  templateUrl: './procedure.component.html'
})
export class ProcedureComponent implements OnInit, OnDestroy {
  procedures: IProcedure[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected procedureService: ProcedureService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.procedureService
      .query()
      .pipe(
        filter((res: HttpResponse<IProcedure[]>) => res.ok),
        map((res: HttpResponse<IProcedure[]>) => res.body)
      )
      .subscribe(
        (res: IProcedure[]) => {
          this.procedures = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProcedures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProcedure) {
    return item.id;
  }

  registerChangeInProcedures() {
    this.eventSubscriber = this.eventManager.subscribe('procedureListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
