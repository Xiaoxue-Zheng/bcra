import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICsvContent, CsvContent } from 'app/shared/model/csv-content.model';
import { CsvContentService } from './csv-content.service';
import { ICsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from 'app/entities/csv-file';

@Component({
  selector: 'jhi-csv-content-update',
  templateUrl: './csv-content-update.component.html'
})
export class CsvContentUpdateComponent implements OnInit {
  isSaving: boolean;

  csvfiles: ICsvFile[];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    dataContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected csvContentService: CsvContentService,
    protected csvFileService: CsvFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ csvContent }) => {
      this.updateForm(csvContent);
    });
    this.csvFileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICsvFile[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICsvFile[]>) => response.body)
      )
      .subscribe((res: ICsvFile[]) => (this.csvfiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(csvContent: ICsvContent) {
    this.editForm.patchValue({
      id: csvContent.id,
      data: csvContent.data,
      dataContentType: csvContent.dataContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const csvContent = this.createFromForm();
    if (csvContent.id !== undefined) {
      this.subscribeToSaveResponse(this.csvContentService.update(csvContent));
    } else {
      this.subscribeToSaveResponse(this.csvContentService.create(csvContent));
    }
  }

  private createFromForm(): ICsvContent {
    return {
      ...new CsvContent(),
      id: this.editForm.get(['id']).value,
      dataContentType: this.editForm.get(['dataContentType']).value,
      data: this.editForm.get(['data']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICsvContent>>) {
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

  trackCsvFileById(index: number, item: ICsvFile) {
    return item.id;
  }
}
