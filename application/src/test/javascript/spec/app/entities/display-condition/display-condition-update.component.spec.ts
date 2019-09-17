/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { DisplayConditionUpdateComponent } from 'app/entities/display-condition/display-condition-update.component';
import { DisplayConditionService } from 'app/entities/display-condition/display-condition.service';
import { DisplayCondition } from 'app/shared/model/display-condition.model';

describe('Component Tests', () => {
  describe('DisplayCondition Management Update Component', () => {
    let comp: DisplayConditionUpdateComponent;
    let fixture: ComponentFixture<DisplayConditionUpdateComponent>;
    let service: DisplayConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [DisplayConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DisplayConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisplayConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisplayConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DisplayCondition(123);
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
        const entity = new DisplayCondition();
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
