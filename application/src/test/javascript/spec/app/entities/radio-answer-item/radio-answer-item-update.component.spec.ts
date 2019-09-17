/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioAnswerItemUpdateComponent } from 'app/entities/radio-answer-item/radio-answer-item-update.component';
import { RadioAnswerItemService } from 'app/entities/radio-answer-item/radio-answer-item.service';
import { RadioAnswerItem } from 'app/shared/model/radio-answer-item.model';

describe('Component Tests', () => {
  describe('RadioAnswerItem Management Update Component', () => {
    let comp: RadioAnswerItemUpdateComponent;
    let fixture: ComponentFixture<RadioAnswerItemUpdateComponent>;
    let service: RadioAnswerItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioAnswerItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RadioAnswerItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RadioAnswerItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioAnswerItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RadioAnswerItem(123);
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
        const entity = new RadioAnswerItem();
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
