import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckboxAnswer } from 'app/shared/model/checkbox-answer.model';
import { CheckboxAnswerService } from './checkbox-answer.service';

@Component({
  selector: 'jhi-checkbox-answer-delete-dialog',
  templateUrl: './checkbox-answer-delete-dialog.component.html'
})
export class CheckboxAnswerDeleteDialogComponent {
  checkboxAnswer: ICheckboxAnswer;

  constructor(
    protected checkboxAnswerService: CheckboxAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.checkboxAnswerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'checkboxAnswerListModification',
        content: 'Deleted an checkboxAnswer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-checkbox-answer-delete-popup',
  template: ''
})
export class CheckboxAnswerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxAnswer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CheckboxAnswerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.checkboxAnswer = checkboxAnswer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/checkbox-answer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/checkbox-answer', { outlets: { popup: null } }]);
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
