import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICsvFile } from 'app/shared/model/csv-file.model';
import { AccountService } from 'app/core';
import { CsvFileService } from './csv-file.service';

@Component({
  selector: 'jhi-csv-file',
  templateUrl: './csv-file.component.html'
})
export class CsvFileComponent implements OnInit, OnDestroy {
  csvFiles: ICsvFile[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected csvFileService: CsvFileService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.csvFileService
      .query()
      .pipe(
        filter((res: HttpResponse<ICsvFile[]>) => res.ok),
        map((res: HttpResponse<ICsvFile[]>) => res.body)
      )
      .subscribe(
        (res: ICsvFile[]) => {
          this.csvFiles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCsvFiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICsvFile) {
    return item.id;
  }

  registerChangeInCsvFiles() {
    this.eventSubscriber = this.eventManager.subscribe('csvFileListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
