/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatDisplayConditionUpdateComponent } from 'app/entities/repeat-display-condition/repeat-display-condition-update.component';
import { RepeatDisplayConditionService } from 'app/entities/repeat-display-condition/repeat-display-condition.service';
import { RepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

describe('Component Tests', () => {
  describe('RepeatDisplayCondition Management Update Component', () => {
    let comp: RepeatDisplayConditionUpdateComponent;
    let fixture: ComponentFixture<RepeatDisplayConditionUpdateComponent>;
    let service: RepeatDisplayConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatDisplayConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RepeatDisplayConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RepeatDisplayConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RepeatDisplayConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RepeatDisplayCondition(123);
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
        const entity = new RepeatDisplayCondition();
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
