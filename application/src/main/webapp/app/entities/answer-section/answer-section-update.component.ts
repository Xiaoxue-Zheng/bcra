import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswerSection, AnswerSection } from 'app/shared/model/answer-section.model';
import { AnswerSectionService } from './answer-section.service';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';
import { AnswerResponseService } from 'app/entities/answer-response';
import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from 'app/entities/question-section';

@Component({
  selector: 'jhi-answer-section-update',
  templateUrl: './answer-section-update.component.html'
})
export class AnswerSectionUpdateComponent implements OnInit {
  isSaving: boolean;

  answerresponses: IAnswerResponse[];

  questionsections: IQuestionSection[];

  editForm = this.fb.group({
    id: [],
    answerResponseId: [null, Validators.required],
    questionSectionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerSectionService: AnswerSectionService,
    protected answerResponseService: AnswerResponseService,
    protected questionSectionService: QuestionSectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answerSection }) => {
      this.updateForm(answerSection);
    });
    this.answerResponseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerResponse[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerResponse[]>) => response.body)
      )
      .subscribe((res: IAnswerResponse[]) => (this.answerresponses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionSectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionSection[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionSection[]>) => response.body)
      )
      .subscribe((res: IQuestionSection[]) => (this.questionsections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answerSection: IAnswerSection) {
    this.editForm.patchValue({
      id: answerSection.id,
      answerResponseId: answerSection.answerResponseId,
      questionSectionId: answerSection.questionSectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answerSection = this.createFromForm();
    if (answerSection.id !== undefined) {
      this.subscribeToSaveResponse(this.answerSectionService.update(answerSection));
    } else {
      this.subscribeToSaveResponse(this.answerSectionService.create(answerSection));
    }
  }

  private createFromForm(): IAnswerSection {
    return {
      ...new AnswerSection(),
      id: this.editForm.get(['id']).value,
      answerResponseId: this.editForm.get(['answerResponseId']).value,
      questionSectionId: this.editForm.get(['questionSectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerSection>>) {
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

  trackQuestionSectionById(index: number, item: IQuestionSection) {
    return item.id;
  }
}
