import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { ReferralConditionComponent } from 'app/entities/referral-condition/referral-condition.component';
import { ReferralConditionService } from 'app/entities/referral-condition/referral-condition.service';
import { ReferralCondition } from 'app/shared/model/referral-condition.model';

describe('Component Tests', () => {
  describe('ReferralCondition Management Component', () => {
    let comp: ReferralConditionComponent;
    let fixture: ComponentFixture<ReferralConditionComponent>;
    let service: ReferralConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [ReferralConditionComponent]
      })
        .overrideTemplate(ReferralConditionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReferralConditionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferralConditionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReferralCondition(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.referralConditions && comp.referralConditions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
