import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionItem } from 'app/shared/model/question-item.model';
import { QuestionItemService } from './question-item.service';

@Component({
  selector: 'jhi-question-item-delete-dialog',
  templateUrl: './question-item-delete-dialog.component.html'
})
export class QuestionItemDeleteDialogComponent {
  questionItem: IQuestionItem;

  constructor(
    protected questionItemService: QuestionItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.questionItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'questionItemListModification',
        content: 'Deleted an questionItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-question-item-delete-popup',
  template: ''
})
export class QuestionItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QuestionItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.questionItem = questionItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/question-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/question-item', { outlets: { popup: null } }]);
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
