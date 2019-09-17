import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';
import { QuestionGroupQuestionService } from './question-group-question.service';

@Component({
  selector: 'jhi-question-group-question-delete-dialog',
  templateUrl: './question-group-question-delete-dialog.component.html'
})
export class QuestionGroupQuestionDeleteDialogComponent {
  questionGroupQuestion: IQuestionGroupQuestion;

  constructor(
    protected questionGroupQuestionService: QuestionGroupQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.questionGroupQuestionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'questionGroupQuestionListModification',
        content: 'Deleted an questionGroupQuestion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-question-group-question-delete-popup',
  template: ''
})
export class QuestionGroupQuestionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionGroupQuestion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QuestionGroupQuestionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.questionGroupQuestion = questionGroupQuestion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/question-group-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/question-group-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
