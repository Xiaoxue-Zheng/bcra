/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { IdentifiableDataDetailComponent } from 'app/entities/identifiable-data/identifiable-data-detail.component';
import { IdentifiableData } from 'app/shared/model/identifiable-data.model';

describe('Component Tests', () => {
  describe('IdentifiableData Management Detail Component', () => {
    let comp: IdentifiableDataDetailComponent;
    let fixture: ComponentFixture<IdentifiableDataDetailComponent>;
    const route = ({ data: of({ identifiableData: new IdentifiableData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [IdentifiableDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IdentifiableDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdentifiableDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.identifiableData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
