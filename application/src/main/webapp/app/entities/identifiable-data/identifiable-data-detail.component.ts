import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';

@Component({
  selector: 'jhi-identifiable-data-detail',
  templateUrl: './identifiable-data-detail.component.html'
})
export class IdentifiableDataDetailComponent implements OnInit {
  identifiableData: IIdentifiableData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ identifiableData }) => {
      this.identifiableData = identifiableData;
    });
  }

  previousState() {
    window.history.back();
  }
}
