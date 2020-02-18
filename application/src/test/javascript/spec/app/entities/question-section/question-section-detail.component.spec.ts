import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { QuestionSectionDetailComponent } from 'app/entities/question-section/question-section-detail.component';
import { QuestionSection } from 'app/shared/model/question-section.model';

describe('Component Tests', () => {
  describe('QuestionSection Management Detail Component', () => {
    let comp: QuestionSectionDetailComponent;
    let fixture: ComponentFixture<QuestionSectionDetailComponent>;
    const route = ({ data: of({ questionSection: new QuestionSection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionSectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuestionSectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionSectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionSection on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionSection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
