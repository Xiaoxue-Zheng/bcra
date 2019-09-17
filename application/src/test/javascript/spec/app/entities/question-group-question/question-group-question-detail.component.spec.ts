/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionGroupQuestionDetailComponent } from 'app/entities/question-group-question/question-group-question-detail.component';
import { QuestionGroupQuestion } from 'app/shared/model/question-group-question.model';

describe('Component Tests', () => {
  describe('QuestionGroupQuestion Management Detail Component', () => {
    let comp: QuestionGroupQuestionDetailComponent;
    let fixture: ComponentFixture<QuestionGroupQuestionDetailComponent>;
    const route = ({ data: of({ questionGroupQuestion: new QuestionGroupQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionGroupQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuestionGroupQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionGroupQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionGroupQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
