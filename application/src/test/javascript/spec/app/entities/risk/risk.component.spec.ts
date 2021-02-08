import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { RiskComponent } from 'app/entities/risk/risk.component';
import { RiskService } from 'app/entities/risk/risk.service';
import { Risk } from 'app/shared/model/risk.model';

describe('Component Tests', () => {
  describe('Risk Management Component', () => {
    let comp: RiskComponent;
    let fixture: ComponentFixture<RiskComponent>;
    let service: RiskService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskComponent]
      })
        .overrideTemplate(RiskComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RiskComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Risk(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.risks && comp.risks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
