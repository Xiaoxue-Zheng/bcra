import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';
import { QuestionnaireQuestionGroupService } from './questionnaire-question-group.service';

@Component({
  selector: 'jhi-questionnaire-question-group-delete-dialog',
  templateUrl: './questionnaire-question-group-delete-dialog.component.html'
})
export class QuestionnaireQuestionGroupDeleteDialogComponent {
  questionnaireQuestionGroup: IQuestionnaireQuestionGroup;

  constructor(
    protected questionnaireQuestionGroupService: QuestionnaireQuestionGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.questionnaireQuestionGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'questionnaireQuestionGroupListModification',
        content: 'Deleted an questionnaireQuestionGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-questionnaire-question-group-delete-popup',
  template: ''
})
export class QuestionnaireQuestionGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionnaireQuestionGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QuestionnaireQuestionGroupDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.questionnaireQuestionGroup = questionnaireQuestionGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/questionnaire-question-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/questionnaire-question-group', { outlets: { popup: null } }]);
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
