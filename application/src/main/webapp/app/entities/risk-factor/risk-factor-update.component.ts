import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRiskFactor, RiskFactor } from 'app/shared/model/risk-factor.model';
import { RiskFactorService } from './risk-factor.service';

@Component({
  selector: 'jhi-risk-factor-update',
  templateUrl: './risk-factor-update.component.html'
})
export class RiskFactorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    factor: [null, [Validators.required]]
  });

  constructor(protected riskFactorService: RiskFactorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riskFactor }) => {
      this.updateForm(riskFactor);
    });
  }

  updateForm(riskFactor: IRiskFactor): void {
    this.editForm.patchValue({
      id: riskFactor.id,
      factor: riskFactor.factor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const riskFactor = this.createFromForm();
    if (riskFactor.id !== undefined) {
      this.subscribeToSaveResponse(this.riskFactorService.update(riskFactor));
    } else {
      this.subscribeToSaveResponse(this.riskFactorService.create(riskFactor));
    }
  }

  private createFromForm(): IRiskFactor {
    return {
      ...new RiskFactor(),
      id: this.editForm.get(['id'])!.value,
      factor: this.editForm.get(['factor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRiskFactor>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
