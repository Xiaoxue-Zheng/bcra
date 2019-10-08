/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerSectionDetailComponent } from 'app/entities/answer-section/answer-section-detail.component';
import { AnswerSection } from 'app/shared/model/answer-section.model';

describe('Component Tests', () => {
  describe('AnswerSection Management Detail Component', () => {
    let comp: AnswerSectionDetailComponent;
    let fixture: ComponentFixture<AnswerSectionDetailComponent>;
    const route = ({ data: of({ answerSection: new AnswerSection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerSectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnswerSectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerSectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.answerSection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
