/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxAnswerUpdateComponent } from 'app/entities/number-checkbox-answer/number-checkbox-answer-update.component';
import { NumberCheckboxAnswerService } from 'app/entities/number-checkbox-answer/number-checkbox-answer.service';
import { NumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';

describe('Component Tests', () => {
  describe('NumberCheckboxAnswer Management Update Component', () => {
    let comp: NumberCheckboxAnswerUpdateComponent;
    let fixture: ComponentFixture<NumberCheckboxAnswerUpdateComponent>;
    let service: NumberCheckboxAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxAnswerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NumberCheckboxAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NumberCheckboxAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NumberCheckboxAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NumberCheckboxAnswer(123);
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
        const entity = new NumberCheckboxAnswer();
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
