import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswerGroup, AnswerGroup } from 'app/shared/model/answer-group.model';
import { AnswerGroupService } from './answer-group.service';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from 'app/entities/answer-response';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';

@Component({
  selector: 'jhi-answer-group-update',
  templateUrl: './answer-group-update.component.html'
})
export class AnswerGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  answerresponses: IAnswerResponse[];

  questiongroups: IQuestionGroup[];

  editForm = this.fb.group({
    id: [],
    answerResponseId: [],
    questionGroupId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerGroupService: AnswerGroupService,
    protected answerResponseService: AnswerResponseService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answerGroup }) => {
      this.updateForm(answerGroup);
    });
    this.answerResponseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerResponse[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerResponse[]>) => response.body)
      )
      .subscribe((res: IAnswerResponse[]) => (this.answerresponses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionGroup[]>) => response.body)
      )
      .subscribe((res: IQuestionGroup[]) => (this.questiongroups = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answerGroup: IAnswerGroup) {
    this.editForm.patchValue({
      id: answerGroup.id,
      answerResponseId: answerGroup.answerResponseId,
      questionGroupId: answerGroup.questionGroupId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answerGroup = this.createFromForm();
    if (answerGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.answerGroupService.update(answerGroup));
    } else {
      this.subscribeToSaveResponse(this.answerGroupService.create(answerGroup));
    }
  }

  private createFromForm(): IAnswerGroup {
    return {
      ...new AnswerGroup(),
      id: this.editForm.get(['id']).value,
      answerResponseId: this.editForm.get(['answerResponseId']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerGroup>>) {
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

  trackQuestionGroupById(index: number, item: IQuestionGroup) {
    return item.id;
  }
}
