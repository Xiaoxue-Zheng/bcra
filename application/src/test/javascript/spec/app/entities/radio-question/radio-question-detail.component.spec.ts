/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionDetailComponent } from 'app/entities/radio-question/radio-question-detail.component';
import { RadioQuestion } from 'app/shared/model/radio-question.model';

describe('Component Tests', () => {
  describe('RadioQuestion Management Detail Component', () => {
    let comp: RadioQuestionDetailComponent;
    let fixture: ComponentFixture<RadioQuestionDetailComponent>;
    const route = ({ data: of({ radioQuestion: new RadioQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RadioQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.radioQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
