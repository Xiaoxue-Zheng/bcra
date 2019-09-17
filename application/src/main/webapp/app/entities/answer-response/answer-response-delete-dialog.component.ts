import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from './answer-response.service';

@Component({
  selector: 'jhi-answer-response-delete-dialog',
  templateUrl: './answer-response-delete-dialog.component.html'
})
export class AnswerResponseDeleteDialogComponent {
  answerResponse: IAnswerResponse;

  constructor(
    protected answerResponseService: AnswerResponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.answerResponseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'answerResponseListModification',
        content: 'Deleted an answerResponse'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-answer-response-delete-popup',
  template: ''
})
export class AnswerResponseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerResponse }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnswerResponseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.answerResponse = answerResponse;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/answer-response', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/answer-response', { outlets: { popup: null } }]);
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
