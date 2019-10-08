/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionSectionUpdateComponent } from 'app/entities/question-section/question-section-update.component';
import { QuestionSectionService } from 'app/entities/question-section/question-section.service';
import { QuestionSection } from 'app/shared/model/question-section.model';

describe('Component Tests', () => {
  describe('QuestionSection Management Update Component', () => {
    let comp: QuestionSectionUpdateComponent;
    let fixture: ComponentFixture<QuestionSectionUpdateComponent>;
    let service: QuestionSectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionSectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuestionSectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionSection(123);
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
        const entity = new QuestionSection();
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
