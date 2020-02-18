import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { ReferralConditionUpdateComponent } from 'app/entities/referral-condition/referral-condition-update.component';
import { ReferralConditionService } from 'app/entities/referral-condition/referral-condition.service';
import { ReferralCondition } from 'app/shared/model/referral-condition.model';

describe('Component Tests', () => {
  describe('ReferralCondition Management Update Component', () => {
    let comp: ReferralConditionUpdateComponent;
    let fixture: ComponentFixture<ReferralConditionUpdateComponent>;
    let service: ReferralConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [ReferralConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReferralConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReferralConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferralConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReferralCondition(123);
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
        const entity = new ReferralCondition();
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
