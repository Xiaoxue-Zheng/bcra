import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { AccountService } from 'app/core';
import { QuestionGroupService } from './question-group.service';

@Component({
  selector: 'jhi-question-group',
  templateUrl: './question-group.component.html'
})
export class QuestionGroupComponent implements OnInit, OnDestroy {
  questionGroups: IQuestionGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected questionGroupService: QuestionGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.questionGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<IQuestionGroup[]>) => res.ok),
        map((res: HttpResponse<IQuestionGroup[]>) => res.body)
      )
      .subscribe(
        (res: IQuestionGroup[]) => {
          this.questionGroups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInQuestionGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IQuestionGroup) {
    return item.id;
  }

  registerChangeInQuestionGroups() {
    this.eventSubscriber = this.eventManager.subscribe('questionGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
