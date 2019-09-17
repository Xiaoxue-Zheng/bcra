/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerItemDetailComponent } from 'app/entities/checkbox-answer-item/checkbox-answer-item-detail.component';
import { CheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

describe('Component Tests', () => {
  describe('CheckboxAnswerItem Management Detail Component', () => {
    let comp: CheckboxAnswerItemDetailComponent;
    let fixture: ComponentFixture<CheckboxAnswerItemDetailComponent>;
    const route = ({ data: of({ checkboxAnswerItem: new CheckboxAnswerItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckboxAnswerItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxAnswerItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkboxAnswerItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
