import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionItem } from 'app/shared/model/question-item.model';
import { AccountService } from 'app/core';
import { QuestionItemService } from './question-item.service';

@Component({
  selector: 'jhi-question-item',
  templateUrl: './question-item.component.html'
})
export class QuestionItemComponent implements OnInit, OnDestroy {
  questionItems: IQuestionItem[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected questionItemService: QuestionItemService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.questionItemService
      .query()
      .pipe(
        filter((res: HttpResponse<IQuestionItem[]>) => res.ok),
        map((res: HttpResponse<IQuestionItem[]>) => res.body)
      )
      .subscribe(
        (res: IQuestionItem[]) => {
          this.questionItems = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInQuestionItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IQuestionItem) {
    return item.id;
  }

  registerChangeInQuestionItems() {
    this.eventSubscriber = this.eventManager.subscribe('questionItemListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
