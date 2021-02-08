import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RiskUpdateComponent } from 'app/entities/risk/risk-update.component';
import { RiskService } from 'app/entities/risk/risk.service';
import { Risk } from 'app/shared/model/risk.model';

describe('Component Tests', () => {
  describe('Risk Management Update Component', () => {
    let comp: RiskUpdateComponent;
    let fixture: ComponentFixture<RiskUpdateComponent>;
    let service: RiskService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RiskUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Risk(123);
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
        const entity = new Risk();
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
