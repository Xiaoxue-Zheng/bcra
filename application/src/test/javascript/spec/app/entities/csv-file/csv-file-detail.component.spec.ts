/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CsvFileDetailComponent } from 'app/entities/csv-file/csv-file-detail.component';
import { CsvFile } from 'app/shared/model/csv-file.model';

describe('Component Tests', () => {
  describe('CsvFile Management Detail Component', () => {
    let comp: CsvFileDetailComponent;
    let fixture: ComponentFixture<CsvFileDetailComponent>;
    const route = ({ data: of({ csvFile: new CsvFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CsvFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CsvFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.csvFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
