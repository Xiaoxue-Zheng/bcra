import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICsvFile } from 'app/shared/model/csv-file.model';

@Component({
  selector: 'jhi-csv-file-detail',
  templateUrl: './csv-file-detail.component.html'
})
export class CsvFileDetailComponent implements OnInit {
  csvFile: ICsvFile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csvFile }) => {
      this.csvFile = csvFile;
    });
  }

  previousState() {
    window.history.back();
  }
}
