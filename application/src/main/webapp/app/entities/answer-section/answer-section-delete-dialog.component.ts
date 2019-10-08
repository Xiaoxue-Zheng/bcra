import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerSection } from 'app/shared/model/answer-section.model';
import { AnswerSectionService } from './answer-section.service';

@Component({
  selector: 'jhi-answer-section-delete-dialog',
  templateUrl: './answer-section-delete-dialog.component.html'
})
export class AnswerSectionDeleteDialogComponent {
  answerSection: IAnswerSection;

  constructor(
    protected answerSectionService: AnswerSectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.answerSectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'answerSectionListModification',
        content: 'Deleted an answerSection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-answer-section-delete-popup',
  template: ''
})
export class AnswerSectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerSection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnswerSectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.answerSection = answerSection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/answer-section', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/answer-section', { outlets: { popup: null } }]);
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
