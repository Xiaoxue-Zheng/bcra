import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswerSection } from 'app/shared/model/answer-section.model';
import { AccountService } from 'app/core';
import { AnswerSectionService } from './answer-section.service';

@Component({
  selector: 'jhi-answer-section',
  templateUrl: './answer-section.component.html'
})
export class AnswerSectionComponent implements OnInit, OnDestroy {
  answerSections: IAnswerSection[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected answerSectionService: AnswerSectionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.answerSectionService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnswerSection[]>) => res.ok),
        map((res: HttpResponse<IAnswerSection[]>) => res.body)
      )
      .subscribe(
        (res: IAnswerSection[]) => {
          this.answerSections = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnswerSections();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnswerSection) {
    return item.id;
  }

  registerChangeInAnswerSections() {
    this.eventSubscriber = this.eventManager.subscribe('answerSectionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
