import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswerResponse } from 'app/shared/model/answer-response.model';
import { AccountService } from 'app/core';
import { AnswerResponseService } from './answer-response.service';

@Component({
  selector: 'jhi-answer-response',
  templateUrl: './answer-response.component.html'
})
export class AnswerResponseComponent implements OnInit, OnDestroy {
  answerResponses: IAnswerResponse[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected answerResponseService: AnswerResponseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.answerResponseService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnswerResponse[]>) => res.ok),
        map((res: HttpResponse<IAnswerResponse[]>) => res.body)
      )
      .subscribe(
        (res: IAnswerResponse[]) => {
          this.answerResponses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnswerResponses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnswerResponse) {
    return item.id;
  }

  registerChangeInAnswerResponses() {
    this.eventSubscriber = this.eventManager.subscribe('answerResponseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
