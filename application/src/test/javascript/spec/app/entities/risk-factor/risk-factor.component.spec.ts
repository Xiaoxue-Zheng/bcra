import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { RiskFactorComponent } from 'app/entities/risk-factor/risk-factor.component';
import { RiskFactorService } from 'app/entities/risk-factor/risk-factor.service';
import { RiskFactor } from 'app/shared/model/risk-factor.model';

describe('Component Tests', () => {
  describe('RiskFactor Management Component', () => {
    let comp: RiskFactorComponent;
    let fixture: ComponentFixture<RiskFactorComponent>;
    let service: RiskFactorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskFactorComponent]
      })
        .overrideTemplate(RiskFactorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskFactorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskFactorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RiskFactor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.riskFactors && comp.riskFactors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
