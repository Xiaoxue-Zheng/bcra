import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RiskFactorUpdateComponent } from 'app/entities/risk-factor/risk-factor-update.component';
import { RiskFactorService } from 'app/entities/risk-factor/risk-factor.service';
import { RiskFactor } from 'app/shared/model/risk-factor.model';

describe('Component Tests', () => {
  describe('RiskFactor Management Update Component', () => {
    let comp: RiskFactorUpdateComponent;
    let fixture: ComponentFixture<RiskFactorUpdateComponent>;
    let service: RiskFactorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskFactorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RiskFactorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskFactorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskFactorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RiskFactor(123);
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
        const entity = new RiskFactor();
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
