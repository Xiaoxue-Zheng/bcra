import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';
import { CheckboxQuestionItemService } from './checkbox-question-item.service';

@Component({
  selector: 'jhi-checkbox-question-item-delete-dialog',
  templateUrl: './checkbox-question-item-delete-dialog.component.html'
})
export class CheckboxQuestionItemDeleteDialogComponent {
  checkboxQuestionItem: ICheckboxQuestionItem;

  constructor(
    protected checkboxQuestionItemService: CheckboxQuestionItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.checkboxQuestionItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'checkboxQuestionItemListModification',
        content: 'Deleted an checkboxQuestionItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-checkbox-question-item-delete-popup',
  template: ''
})
export class CheckboxQuestionItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxQuestionItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CheckboxQuestionItemDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.checkboxQuestionItem = checkboxQuestionItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/checkbox-question-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/checkbox-question-item', { outlets: { popup: null } }]);
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
