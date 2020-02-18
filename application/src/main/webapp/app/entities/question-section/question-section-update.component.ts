import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionSection, QuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire/questionnaire.service';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group/question-group.service';

type SelectableEntity = IQuestionnaire | IQuestionGroup;

@Component({
  selector: 'jhi-question-section-update',
  templateUrl: './question-section-update.component.html'
})
export class QuestionSectionUpdateComponent implements OnInit {
  isSaving = false;
  questionnaires: IQuestionnaire[] = [];
  questiongroups: IQuestionGroup[] = [];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    title: [null, [Validators.required]],
    order: [null, [Validators.required]],
    questionnaireId: [null, Validators.required],
    questionGroupId: [null, Validators.required]
  });

  constructor(
    protected questionSectionService: QuestionSectionService,
    protected questionnaireService: QuestionnaireService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionSection }) => {
      this.updateForm(questionSection);

      this.questionnaireService.query().subscribe((res: HttpResponse<IQuestionnaire[]>) => (this.questionnaires = res.body || []));

      this.questionGroupService.query().subscribe((res: HttpResponse<IQuestionGroup[]>) => (this.questiongroups = res.body || []));
    });
  }

  updateForm(questionSection: IQuestionSection): void {
    this.editForm.patchValue({
      id: questionSection.id,
      identifier: questionSection.identifier,
      title: questionSection.title,
      order: questionSection.order,
      questionnaireId: questionSection.questionnaireId,
      questionGroupId: questionSection.questionGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionSection = this.createFromForm();
    if (questionSection.id !== undefined) {
      this.subscribeToSaveResponse(this.questionSectionService.update(questionSection));
    } else {
      this.subscribeToSaveResponse(this.questionSectionService.create(questionSection));
    }
  }

  private createFromForm(): IQuestionSection {
    return {
      ...new QuestionSection(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      title: this.editForm.get(['title'])!.value,
      order: this.editForm.get(['order'])!.value,
      questionnaireId: this.editForm.get(['questionnaireId'])!.value,
      questionGroupId: this.editForm.get(['questionGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionSection>>): void {
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
