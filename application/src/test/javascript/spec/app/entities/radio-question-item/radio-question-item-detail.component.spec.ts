/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionItemDetailComponent } from 'app/entities/radio-question-item/radio-question-item-detail.component';
import { RadioQuestionItem } from 'app/shared/model/radio-question-item.model';

describe('Component Tests', () => {
  describe('RadioQuestionItem Management Detail Component', () => {
    let comp: RadioQuestionItemDetailComponent;
    let fixture: ComponentFixture<RadioQuestionItemDetailComponent>;
    const route = ({ data: of({ radioQuestionItem: new RadioQuestionItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RadioQuestionItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioQuestionItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.radioQuestionItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
