import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';
import { NumberCheckboxAnswerService } from './number-checkbox-answer.service';

@Component({
  selector: 'jhi-number-checkbox-answer-delete-dialog',
  templateUrl: './number-checkbox-answer-delete-dialog.component.html'
})
export class NumberCheckboxAnswerDeleteDialogComponent {
  numberCheckboxAnswer: INumberCheckboxAnswer;

  constructor(
    protected numberCheckboxAnswerService: NumberCheckboxAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.numberCheckboxAnswerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'numberCheckboxAnswerListModification',
        content: 'Deleted an numberCheckboxAnswer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-number-checkbox-answer-delete-popup',
  template: ''
})
export class NumberCheckboxAnswerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ numberCheckboxAnswer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NumberCheckboxAnswerDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.numberCheckboxAnswer = numberCheckboxAnswer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/number-checkbox-answer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/number-checkbox-answer', { outlets: { popup: null } }]);
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
