/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerUpdateComponent } from 'app/entities/checkbox-answer/checkbox-answer-update.component';
import { CheckboxAnswerService } from 'app/entities/checkbox-answer/checkbox-answer.service';
import { CheckboxAnswer } from 'app/shared/model/checkbox-answer.model';

describe('Component Tests', () => {
  describe('CheckboxAnswer Management Update Component', () => {
    let comp: CheckboxAnswerUpdateComponent;
    let fixture: ComponentFixture<CheckboxAnswerUpdateComponent>;
    let service: CheckboxAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckboxAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckboxAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckboxAnswer(123);
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
        const entity = new CheckboxAnswer();
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
