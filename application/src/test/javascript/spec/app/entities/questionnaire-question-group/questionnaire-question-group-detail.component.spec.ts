/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionnaireQuestionGroupDetailComponent } from 'app/entities/questionnaire-question-group/questionnaire-question-group-detail.component';
import { QuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';

describe('Component Tests', () => {
  describe('QuestionnaireQuestionGroup Management Detail Component', () => {
    let comp: QuestionnaireQuestionGroupDetailComponent;
    let fixture: ComponentFixture<QuestionnaireQuestionGroupDetailComponent>;
    const route = ({ data: of({ questionnaireQuestionGroup: new QuestionnaireQuestionGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionnaireQuestionGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuestionnaireQuestionGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionnaireQuestionGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionnaireQuestionGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
