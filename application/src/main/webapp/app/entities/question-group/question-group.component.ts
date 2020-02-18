import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from './question-group.service';
import { QuestionGroupDeleteDialogComponent } from './question-group-delete-dialog.component';

@Component({
  selector: 'jhi-question-group',
  templateUrl: './question-group.component.html'
})
export class QuestionGroupComponent implements OnInit, OnDestroy {
  questionGroups?: IQuestionGroup[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionGroupService: QuestionGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionGroupService.query().subscribe((res: HttpResponse<IQuestionGroup[]>) => (this.questionGroups = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionGroupListModification', () => this.loadAll());
  }

  delete(questionGroup: IQuestionGroup): void {
    const modalRef = this.modalService.open(QuestionGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionGroup = questionGroup;
  }
}
