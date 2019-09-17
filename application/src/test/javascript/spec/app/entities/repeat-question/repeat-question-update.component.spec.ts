/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatQuestionUpdateComponent } from 'app/entities/repeat-question/repeat-question-update.component';
import { RepeatQuestionService } from 'app/entities/repeat-question/repeat-question.service';
import { RepeatQuestion } from 'app/shared/model/repeat-question.model';

describe('Component Tests', () => {
  describe('RepeatQuestion Management Update Component', () => {
    let comp: RepeatQuestionUpdateComponent;
    let fixture: ComponentFixture<RepeatQuestionUpdateComponent>;
    let service: RepeatQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RepeatQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RepeatQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RepeatQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RepeatQuestion(123);
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
        const entity = new RepeatQuestion();
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
