import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRisk, Risk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';

@Component({
  selector: 'jhi-risk-update',
  templateUrl: './risk-update.component.html'
})
export class RiskUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    lifetimeRisk: [null, [Validators.required]],
    probNotBcra: [null, [Validators.required]],
    probBcra1: [null, [Validators.required]],
    probBcra2: [null, [Validators.required]]
  });

  constructor(protected riskService: RiskService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ risk }) => {
      this.updateForm(risk);
    });
  }

  updateForm(risk: IRisk): void {
    this.editForm.patchValue({
      id: risk.id,
      lifetimeRisk: risk.lifetimeRisk,
      probNotBcra: risk.probNotBcra,
      probBcra1: risk.probBcra1,
      probBcra2: risk.probBcra2
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const risk = this.createFromForm();
    if (risk.id !== undefined) {
      this.subscribeToSaveResponse(this.riskService.update(risk));
    } else {
      this.subscribeToSaveResponse(this.riskService.create(risk));
    }
  }

  private createFromForm(): IRisk {
    return {
      ...new Risk(),
      id: this.editForm.get(['id'])!.value,
      lifetimeRisk: this.editForm.get(['lifetimeRisk'])!.value,
      probNotBcra: this.editForm.get(['probNotBcra'])!.value,
      probBcra1: this.editForm.get(['probBcra1'])!.value,
      probBcra2: this.editForm.get(['probBcra2'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRisk>>): void {
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
