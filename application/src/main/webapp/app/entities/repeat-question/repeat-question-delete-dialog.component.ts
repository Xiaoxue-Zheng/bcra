import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRepeatQuestion } from 'app/shared/model/repeat-question.model';
import { RepeatQuestionService } from './repeat-question.service';

@Component({
  selector: 'jhi-repeat-question-delete-dialog',
  templateUrl: './repeat-question-delete-dialog.component.html'
})
export class RepeatQuestionDeleteDialogComponent {
  repeatQuestion: IRepeatQuestion;

  constructor(
    protected repeatQuestionService: RepeatQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.repeatQuestionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'repeatQuestionListModification',
        content: 'Deleted an repeatQuestion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-repeat-question-delete-popup',
  template: ''
})
export class RepeatQuestionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatQuestion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RepeatQuestionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.repeatQuestion = repeatQuestion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/repeat-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/repeat-question', { outlets: { popup: null } }]);
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
