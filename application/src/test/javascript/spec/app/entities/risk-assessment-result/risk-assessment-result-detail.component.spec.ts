import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RiskAssessmentResultDetailComponent } from 'app/entities/risk-assessment-result/risk-assessment-result-detail.component';
import { RiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';

describe('Component Tests', () => {
  describe('RiskAssessmentResult Management Detail Component', () => {
    let comp: RiskAssessmentResultDetailComponent;
    let fixture: ComponentFixture<RiskAssessmentResultDetailComponent>;
    const route = ({ data: of({ riskAssessmentResult: new RiskAssessmentResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskAssessmentResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RiskAssessmentResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RiskAssessmentResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load riskAssessmentResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.riskAssessmentResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
