import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICsvContent } from 'app/shared/model/csv-content.model';

@Component({
  selector: 'jhi-csv-content-detail',
  templateUrl: './csv-content-detail.component.html'
})
export class CsvContentDetailComponent implements OnInit {
  csvContent: ICsvContent;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csvContent }) => {
      this.csvContent = csvContent;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
