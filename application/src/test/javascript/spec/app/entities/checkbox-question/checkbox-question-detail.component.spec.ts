/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxQuestionDetailComponent } from 'app/entities/checkbox-question/checkbox-question-detail.component';
import { CheckboxQuestion } from 'app/shared/model/checkbox-question.model';

describe('Component Tests', () => {
  describe('CheckboxQuestion Management Detail Component', () => {
    let comp: CheckboxQuestionDetailComponent;
    let fixture: ComponentFixture<CheckboxQuestionDetailComponent>;
    const route = ({ data: of({ checkboxQuestion: new CheckboxQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckboxQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkboxQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
