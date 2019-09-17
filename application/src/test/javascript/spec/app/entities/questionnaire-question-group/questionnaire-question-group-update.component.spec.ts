/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionnaireQuestionGroupUpdateComponent } from 'app/entities/questionnaire-question-group/questionnaire-question-group-update.component';
import { QuestionnaireQuestionGroupService } from 'app/entities/questionnaire-question-group/questionnaire-question-group.service';
import { QuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';

describe('Component Tests', () => {
  describe('QuestionnaireQuestionGroup Management Update Component', () => {
    let comp: QuestionnaireQuestionGroupUpdateComponent;
    let fixture: ComponentFixture<QuestionnaireQuestionGroupUpdateComponent>;
    let service: QuestionnaireQuestionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionnaireQuestionGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuestionnaireQuestionGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionnaireQuestionGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionnaireQuestionGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionnaireQuestionGroup(123);
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
        const entity = new QuestionnaireQuestionGroup();
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
