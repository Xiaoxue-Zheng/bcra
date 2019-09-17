/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxQuestionDetailComponent } from 'app/entities/number-checkbox-question/number-checkbox-question-detail.component';
import { NumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';

describe('Component Tests', () => {
  describe('NumberCheckboxQuestion Management Detail Component', () => {
    let comp: NumberCheckboxQuestionDetailComponent;
    let fixture: ComponentFixture<NumberCheckboxQuestionDetailComponent>;
    const route = ({ data: of({ numberCheckboxQuestion: new NumberCheckboxQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NumberCheckboxQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NumberCheckboxQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.numberCheckboxQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
