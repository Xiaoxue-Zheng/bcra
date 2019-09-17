/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerResponseDetailComponent } from 'app/entities/answer-response/answer-response-detail.component';
import { AnswerResponse } from 'app/shared/model/answer-response.model';

describe('Component Tests', () => {
  describe('AnswerResponse Management Detail Component', () => {
    let comp: AnswerResponseDetailComponent;
    let fixture: ComponentFixture<AnswerResponseDetailComponent>;
    const route = ({ data: of({ answerResponse: new AnswerResponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerResponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnswerResponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerResponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.answerResponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
