import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';
import { NumberCheckboxQuestionService } from './number-checkbox-question.service';

@Component({
  selector: 'jhi-number-checkbox-question-delete-dialog',
  templateUrl: './number-checkbox-question-delete-dialog.component.html'
})
export class NumberCheckboxQuestionDeleteDialogComponent {
  numberCheckboxQuestion: INumberCheckboxQuestion;

  constructor(
    protected numberCheckboxQuestionService: NumberCheckboxQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.numberCheckboxQuestionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'numberCheckboxQuestionListModification',
        content: 'Deleted an numberCheckboxQuestion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-number-checkbox-question-delete-popup',
  template: ''
})
export class NumberCheckboxQuestionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ numberCheckboxQuestion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NumberCheckboxQuestionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.numberCheckboxQuestion = numberCheckboxQuestion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/number-checkbox-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/number-checkbox-question', { outlets: { popup: null } }]);
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
