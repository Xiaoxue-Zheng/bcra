import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProcedure, Procedure } from 'app/shared/model/procedure.model';
import { ProcedureService } from './procedure.service';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from 'app/entities/answer-response';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';

@Component({
  selector: 'jhi-procedure-update',
  templateUrl: './procedure-update.component.html'
})
export class ProcedureUpdateComponent implements OnInit {
  isSaving: boolean;

  consentresponses: IAnswerResponse[];

  riskassessmentresponses: IAnswerResponse[];

  participants: IParticipant[];

  editForm = this.fb.group({
    id: [],
    consentResponseId: [null, Validators.required],
    riskAssessmentResponseId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected procedureService: ProcedureService,
    protected answerResponseService: AnswerResponseService,
    protected participantService: ParticipantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ procedure }) => {
      this.updateForm(procedure);
    });
    this.answerResponseService
      .query({ filter: 'procedure-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerResponse[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerResponse[]>) => response.body)
      )
      .subscribe(
        (res: IAnswerResponse[]) => {
          if (!this.editForm.get('consentResponseId').value) {
            this.consentresponses = res;
          } else {
            this.answerResponseService
              .find(this.editForm.get('consentResponseId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAnswerResponse>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAnswerResponse>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAnswerResponse) => (this.consentresponses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.answerResponseService
      .query({ filter: 'procedure-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerResponse[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerResponse[]>) => response.body)
      )
      .subscribe(
        (res: IAnswerResponse[]) => {
          if (!this.editForm.get('riskAssessmentResponseId').value) {
            this.riskassessmentresponses = res;
          } else {
            this.answerResponseService
              .find(this.editForm.get('riskAssessmentResponseId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAnswerResponse>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAnswerResponse>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAnswerResponse) => (this.riskassessmentresponses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.participantService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IParticipant[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParticipant[]>) => response.body)
      )
      .subscribe((res: IParticipant[]) => (this.participants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(procedure: IProcedure) {
    this.editForm.patchValue({
      id: procedure.id,
      consentResponseId: procedure.consentResponseId,
      riskAssessmentResponseId: procedure.riskAssessmentResponseId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const procedure = this.createFromForm();
    if (procedure.id !== undefined) {
      this.subscribeToSaveResponse(this.procedureService.update(procedure));
    } else {
      this.subscribeToSaveResponse(this.procedureService.create(procedure));
    }
  }

  private createFromForm(): IProcedure {
    return {
      ...new Procedure(),
      id: this.editForm.get(['id']).value,
      consentResponseId: this.editForm.get(['consentResponseId']).value,
      riskAssessmentResponseId: this.editForm.get(['riskAssessmentResponseId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcedure>>) {
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

  trackAnswerResponseById(index: number, item: IAnswerResponse) {
    return item.id;
  }

  trackParticipantById(index: number, item: IParticipant) {
    return item.id;
  }
}
