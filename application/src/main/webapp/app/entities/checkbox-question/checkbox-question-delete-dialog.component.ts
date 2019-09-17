import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckboxQuestion } from 'app/shared/model/checkbox-question.model';
import { CheckboxQuestionService } from './checkbox-question.service';

@Component({
  selector: 'jhi-checkbox-question-delete-dialog',
  templateUrl: './checkbox-question-delete-dialog.component.html'
})
export class CheckboxQuestionDeleteDialogComponent {
  checkboxQuestion: ICheckboxQuestion;

  constructor(
    protected checkboxQuestionService: CheckboxQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.checkboxQuestionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'checkboxQuestionListModification',
        content: 'Deleted an checkboxQuestion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-checkbox-question-delete-popup',
  template: ''
})
export class CheckboxQuestionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxQuestion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CheckboxQuestionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.checkboxQuestion = checkboxQuestion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/checkbox-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/checkbox-question', { outlets: { popup: null } }]);
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
