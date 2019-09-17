import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerGroup } from 'app/shared/model/answer-group.model';
import { AnswerGroupService } from './answer-group.service';

@Component({
  selector: 'jhi-answer-group-delete-dialog',
  templateUrl: './answer-group-delete-dialog.component.html'
})
export class AnswerGroupDeleteDialogComponent {
  answerGroup: IAnswerGroup;

  constructor(
    protected answerGroupService: AnswerGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.answerGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'answerGroupListModification',
        content: 'Deleted an answerGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-answer-group-delete-popup',
  template: ''
})
export class AnswerGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnswerGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.answerGroup = answerGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/answer-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/answer-group', { outlets: { popup: null } }]);
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
