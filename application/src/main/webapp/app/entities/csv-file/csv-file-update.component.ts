import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICsvFile, CsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from './csv-file.service';
import { ICsvContent } from 'app/shared/model/csv-content.model';
import { CsvContentService } from 'app/entities/csv-content';

@Component({
  selector: 'jhi-csv-file-update',
  templateUrl: './csv-file-update.component.html'
})
export class CsvFileUpdateComponent implements OnInit {
  isSaving: boolean;

  contents: ICsvContent[];

  editForm = this.fb.group({
    id: [],
    state: [null, [Validators.required]],
    status: [],
    fileName: [null, [Validators.required, Validators.minLength(5)]],
    uploadDatetime: [null, [Validators.required]],
    contentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected csvFileService: CsvFileService,
    protected csvContentService: CsvContentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ csvFile }) => {
      this.updateForm(csvFile);
    });
    this.csvContentService
      .query({ filter: 'csvfile-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICsvContent[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICsvContent[]>) => response.body)
      )
      .subscribe(
        (res: ICsvContent[]) => {
          if (!this.editForm.get('contentId').value) {
            this.contents = res;
          } else {
            this.csvContentService
              .find(this.editForm.get('contentId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICsvContent>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICsvContent>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICsvContent) => (this.contents = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(csvFile: ICsvFile) {
    this.editForm.patchValue({
      id: csvFile.id,
      state: csvFile.state,
      status: csvFile.status,
      fileName: csvFile.fileName,
      uploadDatetime: csvFile.uploadDatetime != null ? csvFile.uploadDatetime.format(DATE_TIME_FORMAT) : null,
      contentId: csvFile.contentId
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
      state: this.editForm.get(['state']).value,
      status: this.editForm.get(['status']).value,
      fileName: this.editForm.get(['fileName']).value,
      uploadDatetime:
        this.editForm.get(['uploadDatetime']).value != null
          ? moment(this.editForm.get(['uploadDatetime']).value, DATE_TIME_FORMAT)
          : undefined,
      contentId: this.editForm.get(['contentId']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCsvContentById(index: number, item: ICsvContent) {
    return item.id;
  }
}
