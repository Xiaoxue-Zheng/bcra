import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionSection } from 'app/shared/model/question-section.model';
import { AccountService } from 'app/core';
import { QuestionSectionService } from './question-section.service';

@Component({
  selector: 'jhi-question-section',
  templateUrl: './question-section.component.html'
})
export class QuestionSectionComponent implements OnInit, OnDestroy {
  questionSections: IQuestionSection[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected questionSectionService: QuestionSectionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.questionSectionService
      .query()
      .pipe(
        filter((res: HttpResponse<IQuestionSection[]>) => res.ok),
        map((res: HttpResponse<IQuestionSection[]>) => res.body)
      )
      .subscribe(
        (res: IQuestionSection[]) => {
          this.questionSections = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInQuestionSections();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IQuestionSection) {
    return item.id;
  }

  registerChangeInQuestionSections() {
    this.eventSubscriber = this.eventManager.subscribe('questionSectionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
