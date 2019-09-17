/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RepeatAnswerDetailComponent } from 'app/entities/repeat-answer/repeat-answer-detail.component';
import { RepeatAnswer } from 'app/shared/model/repeat-answer.model';

describe('Component Tests', () => {
  describe('RepeatAnswer Management Detail Component', () => {
    let comp: RepeatAnswerDetailComponent;
    let fixture: ComponentFixture<RepeatAnswerDetailComponent>;
    const route = ({ data: of({ repeatAnswer: new RepeatAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RepeatAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RepeatAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.repeatAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
