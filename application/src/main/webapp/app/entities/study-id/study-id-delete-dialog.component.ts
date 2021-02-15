import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudyId } from 'app/shared/model/study-id.model';
import { StudyIdService } from './study-id.service';

@Component({
  templateUrl: './study-id-delete-dialog.component.html'
})
export class StudyIdDeleteDialogComponent {
  studyId?: IStudyId;

  constructor(protected studyIdService: StudyIdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studyIdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studyIdListModification');
      this.activeModal.close();
    });
  }
}
