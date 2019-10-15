import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDisplayCondition, DisplayCondition } from 'app/shared/model/display-condition.model';
import { DisplayConditionService } from './display-condition.service';
import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from 'app/entities/question-section';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-display-condition-update',
  templateUrl: './display-condition-update.component.html'
})
export class DisplayConditionUpdateComponent implements OnInit {
  isSaving: boolean;

  questionsections: IQuestionSection[];

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    questionIdentifier: [],
    itemIdentifier: [],
    questionSectionId: [],
    questionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected displayConditionService: DisplayConditionService,
    protected questionSectionService: QuestionSectionService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ displayCondition }) => {
      this.updateForm(displayCondition);
    });
    this.questionSectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionSection[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionSection[]>) => response.body)
      )
      .subscribe((res: IQuestionSection[]) => (this.questionsections = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(displayCondition: IDisplayCondition) {
    this.editForm.patchValue({
      id: displayCondition.id,
      questionIdentifier: displayCondition.questionIdentifier,
      itemIdentifier: displayCondition.itemIdentifier,
      questionSectionId: displayCondition.questionSectionId,
      questionId: displayCondition.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const displayCondition = this.createFromForm();
    if (displayCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.displayConditionService.update(displayCondition));
    } else {
      this.subscribeToSaveResponse(this.displayConditionService.create(displayCondition));
    }
  }

  private createFromForm(): IDisplayCondition {
    return {
      ...new DisplayCondition(),
      id: this.editForm.get(['id']).value,
      questionIdentifier: this.editForm.get(['questionIdentifier']).value,
      itemIdentifier: this.editForm.get(['itemIdentifier']).value,
      questionSectionId: this.editForm.get(['questionSectionId']).value,
      questionId: this.editForm.get(['questionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisplayCondition>>) {
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

  trackQuestionSectionById(index: number, item: IQuestionSection) {
    return item.id;
  }

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }
}
