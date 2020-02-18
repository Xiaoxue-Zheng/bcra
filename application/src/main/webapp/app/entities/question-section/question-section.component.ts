import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';
import { QuestionSectionDeleteDialogComponent } from './question-section-delete-dialog.component';

@Component({
  selector: 'jhi-question-section',
  templateUrl: './question-section.component.html'
})
export class QuestionSectionComponent implements OnInit, OnDestroy {
  questionSections?: IQuestionSection[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionSectionService: QuestionSectionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionSectionService.query().subscribe((res: HttpResponse<IQuestionSection[]>) => (this.questionSections = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionSections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionSection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionSections(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionSectionListModification', () => this.loadAll());
  }

  delete(questionSection: IQuestionSection): void {
    const modalRef = this.modalService.open(QuestionSectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionSection = questionSection;
  }
}
