import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerItem } from 'app/shared/model/answer-item.model';
import { AnswerItemService } from './answer-item.service';

@Component({
  selector: 'jhi-answer-item-delete-dialog',
  templateUrl: './answer-item-delete-dialog.component.html'
})
export class AnswerItemDeleteDialogComponent {
  answerItem: IAnswerItem;

  constructor(
    protected answerItemService: AnswerItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.answerItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'answerItemListModification',
        content: 'Deleted an answerItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-answer-item-delete-popup',
  template: ''
})
export class AnswerItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnswerItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.answerItem = answerItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/answer-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/answer-item', { outlets: { popup: null } }]);
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
