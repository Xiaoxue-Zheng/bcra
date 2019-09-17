/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxDisplayConditionDetailComponent } from 'app/entities/checkbox-display-condition/checkbox-display-condition-detail.component';
import { CheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

describe('Component Tests', () => {
  describe('CheckboxDisplayCondition Management Detail Component', () => {
    let comp: CheckboxDisplayConditionDetailComponent;
    let fixture: ComponentFixture<CheckboxDisplayConditionDetailComponent>;
    const route = ({ data: of({ checkboxDisplayCondition: new CheckboxDisplayCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxDisplayConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckboxDisplayConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxDisplayConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkboxDisplayCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
