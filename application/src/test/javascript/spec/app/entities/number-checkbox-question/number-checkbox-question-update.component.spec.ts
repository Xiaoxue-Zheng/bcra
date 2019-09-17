/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxQuestionUpdateComponent } from 'app/entities/number-checkbox-question/number-checkbox-question-update.component';
import { NumberCheckboxQuestionService } from 'app/entities/number-checkbox-question/number-checkbox-question.service';
import { NumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';

describe('Component Tests', () => {
  describe('NumberCheckboxQuestion Management Update Component', () => {
    let comp: NumberCheckboxQuestionUpdateComponent;
    let fixture: ComponentFixture<NumberCheckboxQuestionUpdateComponent>;
    let service: NumberCheckboxQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NumberCheckboxQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NumberCheckboxQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NumberCheckboxQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NumberCheckboxQuestion(123);
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
        const entity = new NumberCheckboxQuestion();
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
