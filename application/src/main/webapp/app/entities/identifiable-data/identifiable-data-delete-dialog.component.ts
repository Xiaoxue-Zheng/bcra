import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';
import { IdentifiableDataService } from './identifiable-data.service';

@Component({
  selector: 'jhi-identifiable-data-delete-dialog',
  templateUrl: './identifiable-data-delete-dialog.component.html'
})
export class IdentifiableDataDeleteDialogComponent {
  identifiableData: IIdentifiableData;

  constructor(
    protected identifiableDataService: IdentifiableDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.identifiableDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'identifiableDataListModification',
        content: 'Deleted an identifiableData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-identifiable-data-delete-popup',
  template: ''
})
export class IdentifiableDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ identifiableData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IdentifiableDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.identifiableData = identifiableData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/identifiable-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/identifiable-data', { outlets: { popup: null } }]);
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
