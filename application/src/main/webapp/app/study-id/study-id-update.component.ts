import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IStudyId, StudyId } from 'app/shared/model/study-id.model';
import { StudyIdService } from './study-id.service';
import { IParticipant } from 'app/shared/model/participant.model';

@Component({
  selector: 'jhi-study-id-update',
  templateUrl: './study-id-update.component.html'
})
export class StudyIdUpdateComponent implements OnInit {
  isSaving = false;
  participants: IParticipant[] = [];

  editForm = this.fb.group({
    code: [null, [Validators.required]]
  });

  constructor(protected studyIdService: StudyIdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {}

  previousState(): void {
    window.history.back();
  }

  save(): void {
    const studyCodes = this.parseStudyCodes();
    this.studyIdService.create(studyCodes).subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  parseStudyCodes() {
    this.isSaving = true;
    const studyCodesRaw = this.editForm.get('code').value;
    const studyCodes = studyCodesRaw
      .toString()
      .replaceAll('\n', ',')
      .replaceAll('\r', ',')
      .replaceAll(' ', ',')
      .split(',');

    return studyCodes.filter(studyCode => {
      return studyCode.length > 1;
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudyId>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IParticipant): any {
    return item.id;
  }
}
