/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionItemUpdateComponent } from 'app/entities/radio-question-item/radio-question-item-update.component';
import { RadioQuestionItemService } from 'app/entities/radio-question-item/radio-question-item.service';
import { RadioQuestionItem } from 'app/shared/model/radio-question-item.model';

describe('Component Tests', () => {
  describe('RadioQuestionItem Management Update Component', () => {
    let comp: RadioQuestionItemUpdateComponent;
    let fixture: ComponentFixture<RadioQuestionItemUpdateComponent>;
    let service: RadioQuestionItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RadioQuestionItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RadioQuestionItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioQuestionItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RadioQuestionItem(123);
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
        const entity = new RadioQuestionItem();
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
