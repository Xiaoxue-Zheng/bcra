/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { DisplayConditionDetailComponent } from 'app/entities/display-condition/display-condition-detail.component';
import { DisplayCondition } from 'app/shared/model/display-condition.model';

describe('Component Tests', () => {
  describe('DisplayCondition Management Detail Component', () => {
    let comp: DisplayConditionDetailComponent;
    let fixture: ComponentFixture<DisplayConditionDetailComponent>;
    const route = ({ data: of({ displayCondition: new DisplayCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [DisplayConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DisplayConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DisplayConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.displayCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
