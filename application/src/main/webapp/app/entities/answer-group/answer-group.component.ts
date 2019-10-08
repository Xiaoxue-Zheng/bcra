import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswerGroup } from 'app/shared/model/answer-group.model';
import { AccountService } from 'app/core';
import { AnswerGroupService } from './answer-group.service';

@Component({
  selector: 'jhi-answer-group',
  templateUrl: './answer-group.component.html'
})
export class AnswerGroupComponent implements OnInit, OnDestroy {
  answerGroups: IAnswerGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected answerGroupService: AnswerGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.answerGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnswerGroup[]>) => res.ok),
        map((res: HttpResponse<IAnswerGroup[]>) => res.body)
      )
      .subscribe(
        (res: IAnswerGroup[]) => {
          this.answerGroups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnswerGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnswerGroup) {
    return item.id;
  }

  registerChangeInAnswerGroups() {
    this.eventSubscriber = this.eventManager.subscribe('answerGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
