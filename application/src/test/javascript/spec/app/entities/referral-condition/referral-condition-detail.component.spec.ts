/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { ReferralConditionDetailComponent } from 'app/entities/referral-condition/referral-condition-detail.component';
import { ReferralCondition } from 'app/shared/model/referral-condition.model';

describe('Component Tests', () => {
  describe('ReferralCondition Management Detail Component', () => {
    let comp: ReferralConditionDetailComponent;
    let fixture: ComponentFixture<ReferralConditionDetailComponent>;
    const route = ({ data: of({ referralCondition: new ReferralCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [ReferralConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReferralConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferralConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.referralCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
