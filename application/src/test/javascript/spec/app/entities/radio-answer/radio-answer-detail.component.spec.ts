/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioAnswerDetailComponent } from 'app/entities/radio-answer/radio-answer-detail.component';
import { RadioAnswer } from 'app/shared/model/radio-answer.model';

describe('Component Tests', () => {
  describe('RadioAnswer Management Detail Component', () => {
    let comp: RadioAnswerDetailComponent;
    let fixture: ComponentFixture<RadioAnswerDetailComponent>;
    const route = ({ data: of({ radioAnswer: new RadioAnswer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioAnswerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RadioAnswerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioAnswerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.radioAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
