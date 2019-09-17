/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxDisplayConditionUpdateComponent } from 'app/entities/checkbox-display-condition/checkbox-display-condition-update.component';
import { CheckboxDisplayConditionService } from 'app/entities/checkbox-display-condition/checkbox-display-condition.service';
import { CheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

describe('Component Tests', () => {
  describe('CheckboxDisplayCondition Management Update Component', () => {
    let comp: CheckboxDisplayConditionUpdateComponent;
    let fixture: ComponentFixture<CheckboxDisplayConditionUpdateComponent>;
    let service: CheckboxDisplayConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxDisplayConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckboxDisplayConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckboxDisplayConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxDisplayConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckboxDisplayCondition(123);
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
        const entity = new CheckboxDisplayCondition();
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
