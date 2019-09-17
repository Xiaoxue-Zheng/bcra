/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxQuestionUpdateComponent } from 'app/entities/checkbox-question/checkbox-question-update.component';
import { CheckboxQuestionService } from 'app/entities/checkbox-question/checkbox-question.service';
import { CheckboxQuestion } from 'app/shared/model/checkbox-question.model';

describe('Component Tests', () => {
  describe('CheckboxQuestion Management Update Component', () => {
    let comp: CheckboxQuestionUpdateComponent;
    let fixture: ComponentFixture<CheckboxQuestionUpdateComponent>;
    let service: CheckboxQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckboxQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckboxQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckboxQuestion(123);
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
        const entity = new CheckboxQuestion();
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
