import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { RiskAssessmentResultComponent } from 'app/entities/risk-assessment-result/risk-assessment-result.component';
import { RiskAssessmentResultService } from 'app/entities/risk-assessment-result/risk-assessment-result.service';
import { RiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';

describe('Component Tests', () => {
  describe('RiskAssessmentResult Management Component', () => {
    let comp: RiskAssessmentResultComponent;
    let fixture: ComponentFixture<RiskAssessmentResultComponent>;
    let service: RiskAssessmentResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskAssessmentResultComponent]
      })
        .overrideTemplate(RiskAssessmentResultComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskAssessmentResultComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskAssessmentResultService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RiskAssessmentResult(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.riskAssessmentResults && comp.riskAssessmentResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
