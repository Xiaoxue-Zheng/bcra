import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from './question-group.service';

@Component({
  templateUrl: './question-group-delete-dialog.component.html'
})
export class QuestionGroupDeleteDialogComponent {
  questionGroup?: IQuestionGroup;

  constructor(
    protected questionGroupService: QuestionGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionGroupListModification');
      this.activeModal.close();
    });
  }
}
