/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerSectionUpdateComponent } from 'app/entities/answer-section/answer-section-update.component';
import { AnswerSectionService } from 'app/entities/answer-section/answer-section.service';
import { AnswerSection } from 'app/shared/model/answer-section.model';

describe('Component Tests', () => {
  describe('AnswerSection Management Update Component', () => {
    let comp: AnswerSectionUpdateComponent;
    let fixture: ComponentFixture<AnswerSectionUpdateComponent>;
    let service: AnswerSectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerSectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnswerSectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerSectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerSectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnswerSection(123);
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
        const entity = new AnswerSection();
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
