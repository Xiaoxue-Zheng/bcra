import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';

@Component({
  selector: 'jhi-question-section-delete-dialog',
  templateUrl: './question-section-delete-dialog.component.html'
})
export class QuestionSectionDeleteDialogComponent {
  questionSection: IQuestionSection;

  constructor(
    protected questionSectionService: QuestionSectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.questionSectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'questionSectionListModification',
        content: 'Deleted an questionSection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-question-section-delete-popup',
  template: ''
})
export class QuestionSectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionSection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QuestionSectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.questionSection = questionSection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/question-section', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/question-section', { outlets: { popup: null } }]);
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
