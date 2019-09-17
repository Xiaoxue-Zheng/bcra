import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';
import { RepeatDisplayConditionService } from './repeat-display-condition.service';

@Component({
  selector: 'jhi-repeat-display-condition-delete-dialog',
  templateUrl: './repeat-display-condition-delete-dialog.component.html'
})
export class RepeatDisplayConditionDeleteDialogComponent {
  repeatDisplayCondition: IRepeatDisplayCondition;

  constructor(
    protected repeatDisplayConditionService: RepeatDisplayConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.repeatDisplayConditionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'repeatDisplayConditionListModification',
        content: 'Deleted an repeatDisplayCondition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-repeat-display-condition-delete-popup',
  template: ''
})
export class RepeatDisplayConditionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatDisplayCondition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RepeatDisplayConditionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.repeatDisplayCondition = repeatDisplayCondition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/repeat-display-condition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/repeat-display-condition', { outlets: { popup: null } }]);
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
