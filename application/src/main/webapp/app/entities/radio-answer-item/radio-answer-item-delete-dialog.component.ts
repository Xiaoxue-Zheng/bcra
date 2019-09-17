import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRadioAnswerItem } from 'app/shared/model/radio-answer-item.model';
import { RadioAnswerItemService } from './radio-answer-item.service';

@Component({
  selector: 'jhi-radio-answer-item-delete-dialog',
  templateUrl: './radio-answer-item-delete-dialog.component.html'
})
export class RadioAnswerItemDeleteDialogComponent {
  radioAnswerItem: IRadioAnswerItem;

  constructor(
    protected radioAnswerItemService: RadioAnswerItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.radioAnswerItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'radioAnswerItemListModification',
        content: 'Deleted an radioAnswerItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-radio-answer-item-delete-popup',
  template: ''
})
export class RadioAnswerItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioAnswerItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RadioAnswerItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.radioAnswerItem = radioAnswerItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/radio-answer-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/radio-answer-item', { outlets: { popup: null } }]);
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
