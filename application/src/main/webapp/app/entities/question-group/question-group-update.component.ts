import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
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
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]]
  });

  constructor(protected questionGroupService: QuestionGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionGroup }) => {
      this.updateForm(questionGroup);
    });
  }

  updateForm(questionGroup: IQuestionGroup) {
    this.editForm.patchValue({
      id: questionGroup.id,
      identifier: questionGroup.identifier
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionGroup>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
