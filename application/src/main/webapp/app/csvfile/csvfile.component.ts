import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map, delay, repeat } from 'rxjs/operators';
import { ICsvFile } from 'app/shared/model/csv-file.model';
import { CsvFileService } from '../entities/csv-file/csv-file.service';
import { JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-csvfile',
  templateUrl: './csvfile.component.html',
  styleUrls: ['./csvfile.component.scss']
})
export class CsvFileComponent implements OnInit, OnDestroy {
  csvFiles: ICsvFile[];
  uploadFile: File = null;
  csvFileSubscription: Subscription;

  constructor(private csvFileService: CsvFileService, protected jhiAlertService: JhiAlertService) {}

  loadFiles(files: FileList) {
    if (files.length === 1) {
      this.uploadFile = files.item(0);
    } else {
      if (files.length > 1) {
        this.jhiAlertService.warning('You may only upload one file at a time.');
      }
      this.uploadFile = null;
    }
  }

  upload() {
    if (this.uploadFile === null) {
      this.jhiAlertService.warning('Choose a file to upload');
    } else {
      this.csvFileService
        .upload(this.uploadFile)
        .pipe(
          filter((response: HttpResponse<ICsvFile[]>) => response.ok),
          map((response: HttpResponse<ICsvFile[]>) => response.body)
        )
        .subscribe(
          (response: ICsvFile[]) => {
            this.showUploadResponse(response);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
  }

  loadAll() {
    this.csvFileSubscription = this.csvFileService
      .findAll()
      .pipe(
        filter((res: HttpResponse<ICsvFile[]>) => res.ok),
        map((res: HttpResponse<ICsvFile[]>) => res.body),
        delay(2000),
        repeat()
      )
      .subscribe((res: ICsvFile[]) => {
        this.csvFiles = res;
      });
  }

  showUploadResponse(response) {
    this.jhiAlertService.clear();
    if (response === 'CREATED') {
      this.jhiAlertService.success('The CSV file was uploaded successfully.', null, null);
    } else if (response === 'UPDATED') {
      this.jhiAlertService.success('The CSV file has been updated.', null, null);
    } else if (response === 'ALREADY_EXISTS') {
      this.jhiAlertService.error('A CSV file with this name has already been uploaded.', null, null);
    } else if (response === 'UPLOAD_ERROR') {
      this.jhiAlertService.error('There was a problem uploading this CSV file.', null, null);
    }
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
    this.csvFileSubscription.unsubscribe();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.clear();
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
