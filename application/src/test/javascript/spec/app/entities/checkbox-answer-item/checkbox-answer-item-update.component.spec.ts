/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerItemUpdateComponent } from 'app/entities/checkbox-answer-item/checkbox-answer-item-update.component';
import { CheckboxAnswerItemService } from 'app/entities/checkbox-answer-item/checkbox-answer-item.service';
import { CheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

describe('Component Tests', () => {
  describe('CheckboxAnswerItem Management Update Component', () => {
    let comp: CheckboxAnswerItemUpdateComponent;
    let fixture: ComponentFixture<CheckboxAnswerItemUpdateComponent>;
    let service: CheckboxAnswerItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckboxAnswerItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckboxAnswerItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxAnswerItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckboxAnswerItem(123);
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
        const entity = new CheckboxAnswerItem();
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
