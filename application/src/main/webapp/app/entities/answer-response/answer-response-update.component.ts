import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswerResponse, AnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from './answer-response.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire';

@Component({
  selector: 'jhi-answer-response-update',
  templateUrl: './answer-response-update.component.html'
})
export class AnswerResponseUpdateComponent implements OnInit {
  isSaving: boolean;

  questionnaires: IQuestionnaire[];

  editForm = this.fb.group({
    id: [],
    questionnaireId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerResponseService: AnswerResponseService,
    protected questionnaireService: QuestionnaireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answerResponse }) => {
      this.updateForm(answerResponse);
    });
    this.questionnaireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionnaire[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionnaire[]>) => response.body)
      )
      .subscribe((res: IQuestionnaire[]) => (this.questionnaires = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answerResponse: IAnswerResponse) {
    this.editForm.patchValue({
      id: answerResponse.id,
      questionnaireId: answerResponse.questionnaireId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answerResponse = this.createFromForm();
    if (answerResponse.id !== undefined) {
      this.subscribeToSaveResponse(this.answerResponseService.update(answerResponse));
    } else {
      this.subscribeToSaveResponse(this.answerResponseService.create(answerResponse));
    }
  }

  private createFromForm(): IAnswerResponse {
    return {
      ...new AnswerResponse(),
      id: this.editForm.get(['id']).value,
      questionnaireId: this.editForm.get(['questionnaireId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerResponse>>) {
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

  trackQuestionnaireById(index: number, item: IQuestionnaire) {
    return item.id;
  }
}
