/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxAnswerDetailComponent } from 'app/entities/number-checkbox-answer/number-checkbox-answer-detail.component';
import { NumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';

describe('Component Tests', () => {
  describe('NumberCheckboxAnswer Management Detail Component', () => {
    let comp: NumberCheckboxAnswerDetailComponent;
    let fixture: ComponentFixture<NumberCheckboxAnswerDetailComponent>;
    const route = ({ data: of({ numberCheckboxAnswer: new NumberCheckboxAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NumberCheckboxAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NumberCheckboxAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.numberCheckboxAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
