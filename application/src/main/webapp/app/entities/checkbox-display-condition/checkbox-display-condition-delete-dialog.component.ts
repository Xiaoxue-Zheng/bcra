import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';
import { CheckboxDisplayConditionService } from './checkbox-display-condition.service';

@Component({
  selector: 'jhi-checkbox-display-condition-delete-dialog',
  templateUrl: './checkbox-display-condition-delete-dialog.component.html'
})
export class CheckboxDisplayConditionDeleteDialogComponent {
  checkboxDisplayCondition: ICheckboxDisplayCondition;

  constructor(
    protected checkboxDisplayConditionService: CheckboxDisplayConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.checkboxDisplayConditionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'checkboxDisplayConditionListModification',
        content: 'Deleted an checkboxDisplayCondition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-checkbox-display-condition-delete-popup',
  template: ''
})
export class CheckboxDisplayConditionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxDisplayCondition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CheckboxDisplayConditionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.checkboxDisplayCondition = checkboxDisplayCondition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/checkbox-display-condition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/checkbox-display-condition', { outlets: { popup: null } }]);
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
