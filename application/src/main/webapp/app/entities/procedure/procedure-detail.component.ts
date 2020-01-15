import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcedure } from 'app/shared/model/procedure.model';

@Component({
  selector: 'jhi-procedure-detail',
  templateUrl: './procedure-detail.component.html'
})
export class ProcedureDetailComponent implements OnInit {
  procedure: IProcedure;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ procedure }) => {
      this.procedure = procedure;
    });
  }

  previousState() {
    window.history.back();
  }
}
