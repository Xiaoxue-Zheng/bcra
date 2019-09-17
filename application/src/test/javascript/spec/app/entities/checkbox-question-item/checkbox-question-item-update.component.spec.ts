/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxQuestionItemUpdateComponent } from 'app/entities/checkbox-question-item/checkbox-question-item-update.component';
import { CheckboxQuestionItemService } from 'app/entities/checkbox-question-item/checkbox-question-item.service';
import { CheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

describe('Component Tests', () => {
  describe('CheckboxQuestionItem Management Update Component', () => {
    let comp: CheckboxQuestionItemUpdateComponent;
    let fixture: ComponentFixture<CheckboxQuestionItemUpdateComponent>;
    let service: CheckboxQuestionItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxQuestionItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckboxQuestionItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckboxQuestionItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxQuestionItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckboxQuestionItem(123);
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
        const entity = new CheckboxQuestionItem();
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
