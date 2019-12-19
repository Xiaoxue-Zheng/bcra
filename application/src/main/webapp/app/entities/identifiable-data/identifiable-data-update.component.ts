import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IIdentifiableData, IdentifiableData } from 'app/shared/model/identifiable-data.model';
import { IdentifiableDataService } from './identifiable-data.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { ICsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from 'app/entities/csv-file';

@Component({
  selector: 'jhi-identifiable-data-update',
  templateUrl: './identifiable-data-update.component.html'
})
export class IdentifiableDataUpdateComponent implements OnInit {
  isSaving: boolean;

  participants: IParticipant[];

  csvfiles: ICsvFile[];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    nhsNumber: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    dateOfBirth: [null, [Validators.required]],
    firstname: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    email: [null, [Validators.maxLength(254)]],
    address1: [null, [Validators.required]],
    address2: [],
    address3: [],
    address4: [],
    address5: [],
    postcode: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(8)]],
    practiceName: [null, [Validators.required]],
    participantId: [null, Validators.required],
    csvFileId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected identifiableDataService: IdentifiableDataService,
    protected participantService: ParticipantService,
    protected csvFileService: CsvFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ identifiableData }) => {
      this.updateForm(identifiableData);
    });
    this.participantService
      .query({ filter: 'identifiabledata-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IParticipant[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParticipant[]>) => response.body)
      )
      .subscribe(
        (res: IParticipant[]) => {
          if (!this.editForm.get('participantId').value) {
            this.participants = res;
          } else {
            this.participantService
              .find(this.editForm.get('participantId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IParticipant>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IParticipant>) => subResponse.body)
              )
              .subscribe(
                (subRes: IParticipant) => (this.participants = [subRes].concat(res)),
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

  updateForm(identifiableData: IIdentifiableData) {
    this.editForm.patchValue({
      id: identifiableData.id,
      nhsNumber: identifiableData.nhsNumber,
      dateOfBirth: identifiableData.dateOfBirth,
      firstname: identifiableData.firstname,
      surname: identifiableData.surname,
      email: identifiableData.email,
      address1: identifiableData.address1,
      address2: identifiableData.address2,
      address3: identifiableData.address3,
      address4: identifiableData.address4,
      address5: identifiableData.address5,
      postcode: identifiableData.postcode,
      practiceName: identifiableData.practiceName,
      participantId: identifiableData.participantId,
      csvFileId: identifiableData.csvFileId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const identifiableData = this.createFromForm();
    if (identifiableData.id !== undefined) {
      this.subscribeToSaveResponse(this.identifiableDataService.update(identifiableData));
    } else {
      this.subscribeToSaveResponse(this.identifiableDataService.create(identifiableData));
    }
  }

  private createFromForm(): IIdentifiableData {
    return {
      ...new IdentifiableData(),
      id: this.editForm.get(['id']).value,
      nhsNumber: this.editForm.get(['nhsNumber']).value,
      dateOfBirth: this.editForm.get(['dateOfBirth']).value,
      firstname: this.editForm.get(['firstname']).value,
      surname: this.editForm.get(['surname']).value,
      email: this.editForm.get(['email']).value,
      address1: this.editForm.get(['address1']).value,
      address2: this.editForm.get(['address2']).value,
      address3: this.editForm.get(['address3']).value,
      address4: this.editForm.get(['address4']).value,
      address5: this.editForm.get(['address5']).value,
      postcode: this.editForm.get(['postcode']).value,
      practiceName: this.editForm.get(['practiceName']).value,
      participantId: this.editForm.get(['participantId']).value,
      csvFileId: this.editForm.get(['csvFileId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentifiableData>>) {
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

  trackParticipantById(index: number, item: IParticipant) {
    return item.id;
  }

  trackCsvFileById(index: number, item: ICsvFile) {
    return item.id;
  }
}
