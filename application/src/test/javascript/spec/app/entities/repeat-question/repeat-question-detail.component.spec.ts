/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatQuestionDetailComponent } from 'app/entities/repeat-question/repeat-question-detail.component';
import { RepeatQuestion } from 'app/shared/model/repeat-question.model';

describe('Component Tests', () => {
  describe('RepeatQuestion Management Detail Component', () => {
    let comp: RepeatQuestionDetailComponent;
    let fixture: ComponentFixture<RepeatQuestionDetailComponent>;
    const route = ({ data: of({ repeatQuestion: new RepeatQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RepeatQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RepeatQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.repeatQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
