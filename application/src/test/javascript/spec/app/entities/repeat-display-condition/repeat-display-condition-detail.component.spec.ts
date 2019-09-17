/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatDisplayConditionDetailComponent } from 'app/entities/repeat-display-condition/repeat-display-condition-detail.component';
import { RepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

describe('Component Tests', () => {
  describe('RepeatDisplayCondition Management Detail Component', () => {
    let comp: RepeatDisplayConditionDetailComponent;
    let fixture: ComponentFixture<RepeatDisplayConditionDetailComponent>;
    const route = ({ data: of({ repeatDisplayCondition: new RepeatDisplayCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatDisplayConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RepeatDisplayConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RepeatDisplayConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.repeatDisplayCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
