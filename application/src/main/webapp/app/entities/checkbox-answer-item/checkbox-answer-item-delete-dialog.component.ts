import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';
import { CheckboxAnswerItemService } from './checkbox-answer-item.service';

@Component({
  selector: 'jhi-checkbox-answer-item-delete-dialog',
  templateUrl: './checkbox-answer-item-delete-dialog.component.html'
})
export class CheckboxAnswerItemDeleteDialogComponent {
  checkboxAnswerItem: ICheckboxAnswerItem;

  constructor(
    protected checkboxAnswerItemService: CheckboxAnswerItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.checkboxAnswerItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'checkboxAnswerItemListModification',
        content: 'Deleted an checkboxAnswerItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-checkbox-answer-item-delete-popup',
  template: ''
})
export class CheckboxAnswerItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxAnswerItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CheckboxAnswerItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.checkboxAnswerItem = checkboxAnswerItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/checkbox-answer-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/checkbox-answer-item', { outlets: { popup: null } }]);
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
