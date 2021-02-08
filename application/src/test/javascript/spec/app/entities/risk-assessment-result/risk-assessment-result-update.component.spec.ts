import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RiskAssessmentResultUpdateComponent } from 'app/entities/risk-assessment-result/risk-assessment-result-update.component';
import { RiskAssessmentResultService } from 'app/entities/risk-assessment-result/risk-assessment-result.service';
import { RiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';

describe('Component Tests', () => {
  describe('RiskAssessmentResult Management Update Component', () => {
    let comp: RiskAssessmentResultUpdateComponent;
    let fixture: ComponentFixture<RiskAssessmentResultUpdateComponent>;
    let service: RiskAssessmentResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskAssessmentResultUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RiskAssessmentResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskAssessmentResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskAssessmentResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RiskAssessmentResult(123);
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
        const entity = new RiskAssessmentResult();
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
