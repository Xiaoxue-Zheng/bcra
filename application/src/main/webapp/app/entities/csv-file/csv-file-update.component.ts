import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICsvFile, CsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from './csv-file.service';

@Component({
  selector: 'jhi-csv-file-update',
  templateUrl: './csv-file-update.component.html'
})
export class CsvFileUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    fileName: [null, [Validators.required, Validators.minLength(5)]],
    uploadDatetime: [null, [Validators.required]]
  });

  constructor(protected csvFileService: CsvFileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ csvFile }) => {
      this.updateForm(csvFile);
    });
  }

  updateForm(csvFile: ICsvFile) {
    this.editForm.patchValue({
      id: csvFile.id,
      fileName: csvFile.fileName,
      uploadDatetime: csvFile.uploadDatetime != null ? csvFile.uploadDatetime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const csvFile = this.createFromForm();
    if (csvFile.id !== undefined) {
      this.subscribeToSaveResponse(this.csvFileService.update(csvFile));
    } else {
      this.subscribeToSaveResponse(this.csvFileService.create(csvFile));
    }
  }

  private createFromForm(): ICsvFile {
    return {
      ...new CsvFile(),
      id: this.editForm.get(['id']).value,
      fileName: this.editForm.get(['fileName']).value,
      uploadDatetime:
        this.editForm.get(['uploadDatetime']).value != null
          ? moment(this.editForm.get(['uploadDatetime']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICsvFile>>) {
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
