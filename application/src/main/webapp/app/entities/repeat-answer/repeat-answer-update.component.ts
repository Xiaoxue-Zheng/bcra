import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRepeatAnswer, RepeatAnswer } from 'app/shared/model/repeat-answer.model';
import { RepeatAnswerService } from './repeat-answer.service';

@Component({
  selector: 'jhi-repeat-answer-update',
  templateUrl: './repeat-answer-update.component.html'
})
export class RepeatAnswerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]]
  });

  constructor(protected repeatAnswerService: RepeatAnswerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ repeatAnswer }) => {
      this.updateForm(repeatAnswer);
    });
  }

  updateForm(repeatAnswer: IRepeatAnswer) {
    this.editForm.patchValue({
      id: repeatAnswer.id,
      quantity: repeatAnswer.quantity
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const repeatAnswer = this.createFromForm();
    if (repeatAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.repeatAnswerService.update(repeatAnswer));
    } else {
      this.subscribeToSaveResponse(this.repeatAnswerService.create(repeatAnswer));
    }
  }

  private createFromForm(): IRepeatAnswer {
    return {
      ...new RepeatAnswer(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepeatAnswer>>) {
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
