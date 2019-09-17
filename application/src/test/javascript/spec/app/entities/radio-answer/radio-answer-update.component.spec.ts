/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioAnswerUpdateComponent } from 'app/entities/radio-answer/radio-answer-update.component';
import { RadioAnswerService } from 'app/entities/radio-answer/radio-answer.service';
import { RadioAnswer } from 'app/shared/model/radio-answer.model';

describe('Component Tests', () => {
  describe('RadioAnswer Management Update Component', () => {
    let comp: RadioAnswerUpdateComponent;
    let fixture: ComponentFixture<RadioAnswerUpdateComponent>;
    let service: RadioAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioAnswerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RadioAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RadioAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RadioAnswer(123);
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
        const entity = new RadioAnswer();
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
