import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from './csv-file.service';

@Component({
  selector: 'jhi-csv-file-delete-dialog',
  templateUrl: './csv-file-delete-dialog.component.html'
})
export class CsvFileDeleteDialogComponent {
  csvFile: ICsvFile;

  constructor(protected csvFileService: CsvFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.csvFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'csvFileListModification',
        content: 'Deleted an csvFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-csv-file-delete-popup',
  template: ''
})
export class CsvFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csvFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CsvFileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.csvFile = csvFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/csv-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/csv-file', { outlets: { popup: null } }]);
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
