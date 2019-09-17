/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { RadioAnswerItemDetailComponent } from 'app/entities/radio-answer-item/radio-answer-item-detail.component';
import { RadioAnswerItem } from 'app/shared/model/radio-answer-item.model';

describe('Component Tests', () => {
  describe('RadioAnswerItem Management Detail Component', () => {
    let comp: RadioAnswerItemDetailComponent;
    let fixture: ComponentFixture<RadioAnswerItemDetailComponent>;
    const route = ({ data: of({ radioAnswerItem: new RadioAnswerItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioAnswerItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RadioAnswerItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioAnswerItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.radioAnswerItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
