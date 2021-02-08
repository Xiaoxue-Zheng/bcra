import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RiskFactorDetailComponent } from 'app/entities/risk-factor/risk-factor-detail.component';
import { RiskFactor } from 'app/shared/model/risk-factor.model';

describe('Component Tests', () => {
  describe('RiskFactor Management Detail Component', () => {
    let comp: RiskFactorDetailComponent;
    let fixture: ComponentFixture<RiskFactorDetailComponent>;
    const route = ({ data: of({ riskFactor: new RiskFactor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskFactorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RiskFactorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RiskFactorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load riskFactor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.riskFactor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
