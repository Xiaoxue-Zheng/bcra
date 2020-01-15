import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IParticipant, Participant } from 'app/shared/model/participant.model';
import { ParticipantService } from './participant.service';
import { IUser, UserService } from 'app/core';
import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';
import { IdentifiableDataService } from 'app/entities/identifiable-data';
import { IProcedure } from 'app/shared/model/procedure.model';
import { ProcedureService } from 'app/entities/procedure';
import { ICsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from 'app/entities/csv-file';

@Component({
  selector: 'jhi-participant-update',
  templateUrl: './participant-update.component.html'
})
export class ParticipantUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  identifiabledata: IIdentifiableData[];

  procedures: IProcedure[];

  csvfiles: ICsvFile[];

  editForm = this.fb.group({
    id: [],
    registerDatetime: [],
    lastLoginDatetime: [],
    userId: [null, Validators.required],
    identifiableDataId: [null, Validators.required],
    procedureId: [null, Validators.required],
    csvFileId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected participantService: ParticipantService,
    protected userService: UserService,
    protected identifiableDataService: IdentifiableDataService,
    protected procedureService: ProcedureService,
    protected csvFileService: CsvFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ participant }) => {
      this.updateForm(participant);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.identifiableDataService
      .query({ 'participantId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IIdentifiableData[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIdentifiableData[]>) => response.body)
      )
      .subscribe(
        (res: IIdentifiableData[]) => {
          if (!this.editForm.get('identifiableDataId').value) {
            this.identifiabledata = res;
          } else {
            this.identifiableDataService
              .find(this.editForm.get('identifiableDataId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IIdentifiableData>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IIdentifiableData>) => subResponse.body)
              )
              .subscribe(
                (subRes: IIdentifiableData) => (this.identifiabledata = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.procedureService
      .query({ 'participantId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProcedure[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProcedure[]>) => response.body)
      )
      .subscribe(
        (res: IProcedure[]) => {
          if (!this.editForm.get('procedureId').value) {
            this.procedures = res;
          } else {
            this.procedureService
              .find(this.editForm.get('procedureId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProcedure>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProcedure>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProcedure) => (this.procedures = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.csvFileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICsvFile[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICsvFile[]>) => response.body)
      )
      .subscribe((res: ICsvFile[]) => (this.csvfiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(participant: IParticipant) {
    this.editForm.patchValue({
      id: participant.id,
      registerDatetime: participant.registerDatetime != null ? participant.registerDatetime.format(DATE_TIME_FORMAT) : null,
      lastLoginDatetime: participant.lastLoginDatetime != null ? participant.lastLoginDatetime.format(DATE_TIME_FORMAT) : null,
      userId: participant.userId,
      identifiableDataId: participant.identifiableDataId,
      procedureId: participant.procedureId,
      csvFileId: participant.csvFileId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const participant = this.createFromForm();
    if (participant.id !== undefined) {
      this.subscribeToSaveResponse(this.participantService.update(participant));
    } else {
      this.subscribeToSaveResponse(this.participantService.create(participant));
    }
  }

  private createFromForm(): IParticipant {
    return {
      ...new Participant(),
      id: this.editForm.get(['id']).value,
      registerDatetime:
        this.editForm.get(['registerDatetime']).value != null
          ? moment(this.editForm.get(['registerDatetime']).value, DATE_TIME_FORMAT)
          : undefined,
      lastLoginDatetime:
        this.editForm.get(['lastLoginDatetime']).value != null
          ? moment(this.editForm.get(['lastLoginDatetime']).value, DATE_TIME_FORMAT)
          : undefined,
      userId: this.editForm.get(['userId']).value,
      identifiableDataId: this.editForm.get(['identifiableDataId']).value,
      procedureId: this.editForm.get(['procedureId']).value,
      csvFileId: this.editForm.get(['csvFileId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackIdentifiableDataById(index: number, item: IIdentifiableData) {
    return item.id;
  }

  trackProcedureById(index: number, item: IProcedure) {
    return item.id;
  }

  trackCsvFileById(index: number, item: ICsvFile) {
    return item.id;
  }
}
