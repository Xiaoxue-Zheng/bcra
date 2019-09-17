/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionGroupQuestionUpdateComponent } from 'app/entities/question-group-question/question-group-question-update.component';
import { QuestionGroupQuestionService } from 'app/entities/question-group-question/question-group-question.service';
import { QuestionGroupQuestion } from 'app/shared/model/question-group-question.model';

describe('Component Tests', () => {
  describe('QuestionGroupQuestion Management Update Component', () => {
    let comp: QuestionGroupQuestionUpdateComponent;
    let fixture: ComponentFixture<QuestionGroupQuestionUpdateComponent>;
    let service: QuestionGroupQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionGroupQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuestionGroupQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionGroupQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionGroupQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionGroupQuestion(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionGroupQuestion();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
