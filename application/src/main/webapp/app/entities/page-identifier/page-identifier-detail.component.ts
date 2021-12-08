import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageIdentifier } from 'app/shared/model/page-identifier.model';

@Component({
  selector: 'jhi-page-identifier-detail',
  templateUrl: './page-identifier-detail.component.html'
})
export class PageIdentifierDetailComponent implements OnInit {
  pageIdentifier: IPageIdentifier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageIdentifier }) => (this.pageIdentifier = pageIdentifier));
  }

  previousState(): void {
    window.history.back();
  }
}
