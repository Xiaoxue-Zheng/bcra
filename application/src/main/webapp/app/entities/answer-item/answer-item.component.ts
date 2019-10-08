import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswerItem } from 'app/shared/model/answer-item.model';
import { AccountService } from 'app/core';
import { AnswerItemService } from './answer-item.service';

@Component({
  selector: 'jhi-answer-item',
  templateUrl: './answer-item.component.html'
})
export class AnswerItemComponent implements OnInit, OnDestroy {
  answerItems: IAnswerItem[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected answerItemService: AnswerItemService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.answerItemService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnswerItem[]>) => res.ok),
        map((res: HttpResponse<IAnswerItem[]>) => res.body)
      )
      .subscribe(
        (res: IAnswerItem[]) => {
          this.answerItems = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnswerItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnswerItem) {
    return item.id;
  }

  registerChangeInAnswerItems() {
    this.eventSubscriber = this.eventManager.subscribe('answerItemListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
