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
import { ParticipantService } from 'app/entities/participant/participant.service';

@Component({
  selector: 'jhi-study-id-update',
  templateUrl: './study-id-update.component.html'
})
export class StudyIdUpdateComponent implements OnInit {
  isSaving = false;
  participants: IParticipant[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    participant: []
  });

  constructor(
    protected studyIdService: StudyIdService,
    protected participantService: ParticipantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studyId }) => {
      this.updateForm(studyId);

      this.participantService
        .query({ 'studyIdId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IParticipant[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IParticipant[]) => {
          if (!studyId.participant || !studyId.participant.id) {
            this.participants = resBody;
          } else {
            this.participantService
              .find(studyId.participant.id)
              .pipe(
                map((subRes: HttpResponse<IParticipant>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IParticipant[]) => (this.participants = concatRes));
          }
        });
    });
  }

  updateForm(studyId: IStudyId): void {
    this.editForm.patchValue({
      id: studyId.id,
      code: studyId.code,
      participant: studyId.participant
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studyId = this.createFromForm();
    if (studyId.id !== undefined) {
      this.subscribeToSaveResponse(this.studyIdService.update(studyId));
    } else {
      this.subscribeToSaveResponse(this.studyIdService.create(studyId));
    }
  }

  private createFromForm(): IStudyId {
    return {
      ...new StudyId(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      participant: this.editForm.get(['participant'])!.value
    };
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
