import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRiskAssessmentResult, RiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';
import { RiskAssessmentResultService } from './risk-assessment-result.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant/participant.service';
import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from 'app/entities/risk/risk.service';

type SelectableEntity = IParticipant | IRisk;

@Component({
  selector: 'jhi-risk-assessment-result-update',
  templateUrl: './risk-assessment-result-update.component.html'
})
export class RiskAssessmentResultUpdateComponent implements OnInit {
  isSaving = false;
  participants: IParticipant[] = [];
  individualrisks: IRisk[] = [];
  populationrisks: IRisk[] = [];

  editForm = this.fb.group({
    id: [],
    filename: [null, [Validators.required]],
    participant: [null, Validators.required],
    individualRisk: [null, Validators.required],
    populationRisk: [null, Validators.required]
  });

  constructor(
    protected riskAssessmentResultService: RiskAssessmentResultService,
    protected participantService: ParticipantService,
    protected riskService: RiskService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riskAssessmentResult }) => {
      this.updateForm(riskAssessmentResult);

      this.participantService
        .query({ 'riskAssessmentResultId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IParticipant[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IParticipant[]) => {
          if (!riskAssessmentResult.participant || !riskAssessmentResult.participant.id) {
            this.participants = resBody;
          } else {
            this.participantService
              .find(riskAssessmentResult.participant.id)
              .pipe(
                map((subRes: HttpResponse<IParticipant>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IParticipant[]) => (this.participants = concatRes));
          }
        });

      this.riskService
        .query({ filter: 'riskassessmentresult-is-null' })
        .pipe(
          map((res: HttpResponse<IRisk[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRisk[]) => {
          if (!riskAssessmentResult.individualRisk || !riskAssessmentResult.individualRisk.id) {
            this.individualrisks = resBody;
          } else {
            this.riskService
              .find(riskAssessmentResult.individualRisk.id)
              .pipe(
                map((subRes: HttpResponse<IRisk>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRisk[]) => (this.individualrisks = concatRes));
          }
        });

      this.riskService
        .query({ filter: 'riskassessmentresult-is-null' })
        .pipe(
          map((res: HttpResponse<IRisk[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRisk[]) => {
          if (!riskAssessmentResult.populationRisk || !riskAssessmentResult.populationRisk.id) {
            this.populationrisks = resBody;
          } else {
            this.riskService
              .find(riskAssessmentResult.populationRisk.id)
              .pipe(
                map((subRes: HttpResponse<IRisk>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRisk[]) => (this.populationrisks = concatRes));
          }
        });
    });
  }

  updateForm(riskAssessmentResult: IRiskAssessmentResult): void {
    this.editForm.patchValue({
      id: riskAssessmentResult.id,
      filename: riskAssessmentResult.filename,
      participant: riskAssessmentResult.participant,
      individualRisk: riskAssessmentResult.individualRisk,
      populationRisk: riskAssessmentResult.populationRisk
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const riskAssessmentResult = this.createFromForm();
    if (riskAssessmentResult.id !== undefined) {
      this.subscribeToSaveResponse(this.riskAssessmentResultService.update(riskAssessmentResult));
    } else {
      this.subscribeToSaveResponse(this.riskAssessmentResultService.create(riskAssessmentResult));
    }
  }

  private createFromForm(): IRiskAssessmentResult {
    return {
      ...new RiskAssessmentResult(),
      id: this.editForm.get(['id'])!.value,
      filename: this.editForm.get(['filename'])!.value,
      participant: this.editForm.get(['participant'])!.value,
      individualRisk: this.editForm.get(['individualRisk'])!.value,
      populationRisk: this.editForm.get(['populationRisk'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRiskAssessmentResult>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
