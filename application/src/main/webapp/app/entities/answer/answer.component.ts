import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswer } from 'app/shared/model/answer.model';
import { AccountService } from 'app/core';
import { AnswerService } from './answer.service';

@Component({
  selector: 'jhi-answer',
  templateUrl: './answer.component.html'
})
export class AnswerComponent implements OnInit, OnDestroy {
  answers: IAnswer[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected answerService: AnswerService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.answerService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnswer[]>) => res.ok),
        map((res: HttpResponse<IAnswer[]>) => res.body)
      )
      .subscribe(
        (res: IAnswer[]) => {
          this.answers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnswers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnswer) {
    return item.id;
  }

  registerChangeInAnswers() {
    this.eventSubscriber = this.eventManager.subscribe('answerListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
