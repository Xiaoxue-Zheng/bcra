import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRepeatAnswer } from 'app/shared/model/repeat-answer.model';
import { RepeatAnswerService } from './repeat-answer.service';

@Component({
  selector: 'jhi-repeat-answer-delete-dialog',
  templateUrl: './repeat-answer-delete-dialog.component.html'
})
export class RepeatAnswerDeleteDialogComponent {
  repeatAnswer: IRepeatAnswer;

  constructor(
    protected repeatAnswerService: RepeatAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.repeatAnswerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'repeatAnswerListModification',
        content: 'Deleted an repeatAnswer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-repeat-answer-delete-popup',
  template: ''
})
export class RepeatAnswerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatAnswer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RepeatAnswerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.repeatAnswer = repeatAnswer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/repeat-answer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/repeat-answer', { outlets: { popup: null } }]);
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
