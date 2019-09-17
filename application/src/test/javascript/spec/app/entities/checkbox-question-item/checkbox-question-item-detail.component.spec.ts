/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxQuestionItemDetailComponent } from 'app/entities/checkbox-question-item/checkbox-question-item-detail.component';
import { CheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

describe('Component Tests', () => {
  describe('CheckboxQuestionItem Management Detail Component', () => {
    let comp: CheckboxQuestionItemDetailComponent;
    let fixture: ComponentFixture<CheckboxQuestionItemDetailComponent>;
    const route = ({ data: of({ checkboxQuestionItem: new CheckboxQuestionItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxQuestionItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckboxQuestionItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxQuestionItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkboxQuestionItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
