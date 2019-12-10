import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICsvContent } from 'app/shared/model/csv-content.model';
import { CsvContentService } from './csv-content.service';

@Component({
  selector: 'jhi-csv-content-delete-dialog',
  templateUrl: './csv-content-delete-dialog.component.html'
})
export class CsvContentDeleteDialogComponent {
  csvContent: ICsvContent;

  constructor(
    protected csvContentService: CsvContentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.csvContentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'csvContentListModification',
        content: 'Deleted an csvContent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-csv-content-delete-popup',
  template: ''
})
export class CsvContentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csvContent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CsvContentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.csvContent = csvContent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/csv-content', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/csv-content', { outlets: { popup: null } }]);
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
