import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import moment from 'moment';
import { IIdentifiableData, IdentifiableData } from 'app/shared/model/identifiable-data.model';
import { IdentifiableDataService } from './identifiable-data.service';

@Component({
  selector: 'jhi-identifiable-data-update',
  templateUrl: './identifiable-data-update.component.html'
})
export class IdentifiableDataUpdateComponent implements OnInit {
  isSaving: boolean;
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
    practiceName: [null, [Validators.required]]
  });

  constructor(
    protected identifiableDataService: IdentifiableDataService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ identifiableData }) => {
      this.updateForm(identifiableData);
    });
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
      practiceName: identifiableData.practiceName
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
      practiceName: this.editForm.get(['practiceName']).value
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
}
