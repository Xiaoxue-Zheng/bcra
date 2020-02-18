import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';

@Component({
  templateUrl: './question-section-delete-dialog.component.html'
})
export class QuestionSectionDeleteDialogComponent {
  questionSection?: IQuestionSection;

  constructor(
    protected questionSectionService: QuestionSectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionSectionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionSectionListModification');
      this.activeModal.close();
    });
  }
}
