import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRadioQuestionItem } from 'app/shared/model/radio-question-item.model';
import { RadioQuestionItemService } from './radio-question-item.service';

@Component({
  selector: 'jhi-radio-question-item-delete-dialog',
  templateUrl: './radio-question-item-delete-dialog.component.html'
})
export class RadioQuestionItemDeleteDialogComponent {
  radioQuestionItem: IRadioQuestionItem;

  constructor(
    protected radioQuestionItemService: RadioQuestionItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.radioQuestionItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'radioQuestionItemListModification',
        content: 'Deleted an radioQuestionItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-radio-question-item-delete-popup',
  template: ''
})
export class RadioQuestionItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioQuestionItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RadioQuestionItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.radioQuestionItem = radioQuestionItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/radio-question-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/radio-question-item', { outlets: { popup: null } }]);
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
