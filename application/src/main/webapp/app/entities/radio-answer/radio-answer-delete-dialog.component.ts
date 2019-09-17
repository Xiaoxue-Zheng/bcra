import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRadioAnswer } from 'app/shared/model/radio-answer.model';
import { RadioAnswerService } from './radio-answer.service';

@Component({
  selector: 'jhi-radio-answer-delete-dialog',
  templateUrl: './radio-answer-delete-dialog.component.html'
})
export class RadioAnswerDeleteDialogComponent {
  radioAnswer: IRadioAnswer;

  constructor(
    protected radioAnswerService: RadioAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.radioAnswerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'radioAnswerListModification',
        content: 'Deleted an radioAnswer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-radio-answer-delete-popup',
  template: ''
})
export class RadioAnswerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioAnswer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RadioAnswerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.radioAnswer = radioAnswer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/radio-answer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/radio-answer', { outlets: { popup: null } }]);
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
