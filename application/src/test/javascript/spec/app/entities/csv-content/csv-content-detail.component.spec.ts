/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CsvContentDetailComponent } from 'app/entities/csv-content/csv-content-detail.component';
import { CsvContent } from 'app/shared/model/csv-content.model';

describe('Component Tests', () => {
  describe('CsvContent Management Detail Component', () => {
    let comp: CsvContentDetailComponent;
    let fixture: ComponentFixture<CsvContentDetailComponent>;
    const route = ({ data: of({ csvContent: new CsvContent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvContentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CsvContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CsvContentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.csvContent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
