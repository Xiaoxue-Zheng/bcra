/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatAnswerUpdateComponent } from 'app/entities/repeat-answer/repeat-answer-update.component';
import { RepeatAnswerService } from 'app/entities/repeat-answer/repeat-answer.service';
import { RepeatAnswer } from 'app/shared/model/repeat-answer.model';

describe('Component Tests', () => {
  describe('RepeatAnswer Management Update Component', () => {
    let comp: RepeatAnswerUpdateComponent;
    let fixture: ComponentFixture<RepeatAnswerUpdateComponent>;
    let service: RepeatAnswerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatAnswerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RepeatAnswerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RepeatAnswerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RepeatAnswerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RepeatAnswer(123);
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
        const entity = new RepeatAnswer();
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
