import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionGroup, QuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from './question-group.service';

@Component({
  selector: 'jhi-question-group-update',
  templateUrl: './question-group-update.component.html'
})
export class QuestionGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]]
  });

  constructor(protected questionGroupService: QuestionGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionGroup }) => {
      this.updateForm(questionGroup);
    });
  }

  updateForm(questionGroup: IQuestionGroup): void {
    this.editForm.patchValue({
      id: questionGroup.id,
      identifier: questionGroup.identifier
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionGroup = this.createFromForm();
    if (questionGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.questionGroupService.update(questionGroup));
    } else {
      this.subscribeToSaveResponse(this.questionGroupService.create(questionGroup));
    }
  }

  private createFromForm(): IQuestionGroup {
    return {
      ...new QuestionGroup(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionGroup>>): void {
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
