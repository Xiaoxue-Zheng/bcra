import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRadioQuestion } from 'app/shared/model/radio-question.model';
import { RadioQuestionService } from './radio-question.service';

@Component({
  selector: 'jhi-radio-question-delete-dialog',
  templateUrl: './radio-question-delete-dialog.component.html'
})
export class RadioQuestionDeleteDialogComponent {
  radioQuestion: IRadioQuestion;

  constructor(
    protected radioQuestionService: RadioQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.radioQuestionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'radioQuestionListModification',
        content: 'Deleted an radioQuestion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-radio-question-delete-popup',
  template: ''
})
export class RadioQuestionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioQuestion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RadioQuestionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.radioQuestion = radioQuestion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/radio-question', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/radio-question', { outlets: { popup: null } }]);
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
