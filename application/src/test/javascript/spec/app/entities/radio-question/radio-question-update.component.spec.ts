/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionUpdateComponent } from 'app/entities/radio-question/radio-question-update.component';
import { RadioQuestionService } from 'app/entities/radio-question/radio-question.service';
import { RadioQuestion } from 'app/shared/model/radio-question.model';

describe('Component Tests', () => {
  describe('RadioQuestion Management Update Component', () => {
    let comp: RadioQuestionUpdateComponent;
    let fixture: ComponentFixture<RadioQuestionUpdateComponent>;
    let service: RadioQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RadioQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RadioQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RadioQuestion(123);
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
        const entity = new RadioQuestion();
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
