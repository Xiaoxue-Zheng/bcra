/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerGroupDetailComponent } from 'app/entities/answer-group/answer-group-detail.component';
import { AnswerGroup } from 'app/shared/model/answer-group.model';

describe('Component Tests', () => {
  describe('AnswerGroup Management Detail Component', () => {
    let comp: AnswerGroupDetailComponent;
    let fixture: ComponentFixture<AnswerGroupDetailComponent>;
    const route = ({ data: of({ answerGroup: new AnswerGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnswerGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.answerGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
