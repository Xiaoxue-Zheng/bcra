/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerDetailComponent } from 'app/entities/checkbox-answer/checkbox-answer-detail.component';
import { CheckboxAnswer } from 'app/shared/model/checkbox-answer.model';

describe('Component Tests', () => {
  describe('CheckboxAnswer Management Detail Component', () => {
    let comp: CheckboxAnswerDetailComponent;
    let fixture: ComponentFixture<CheckboxAnswerDetailComponent>;
    const route = ({ data: of({ checkboxAnswer: new CheckboxAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckboxAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkboxAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
