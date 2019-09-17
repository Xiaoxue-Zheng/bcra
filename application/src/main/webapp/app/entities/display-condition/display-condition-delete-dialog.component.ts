import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { DisplayConditionService } from './display-condition.service';

@Component({
  selector: 'jhi-display-condition-delete-dialog',
  templateUrl: './display-condition-delete-dialog.component.html'
})
export class DisplayConditionDeleteDialogComponent {
  displayCondition: IDisplayCondition;

  constructor(
    protected displayConditionService: DisplayConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.displayConditionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'displayConditionListModification',
        content: 'Deleted an displayCondition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-display-condition-delete-popup',
  template: ''
})
export class DisplayConditionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ displayCondition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DisplayConditionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.displayCondition = displayCondition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/display-condition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/display-condition', { outlets: { popup: null } }]);
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
