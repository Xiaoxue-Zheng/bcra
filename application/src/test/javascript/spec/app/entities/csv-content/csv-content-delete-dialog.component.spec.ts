/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { CsvContentDeleteDialogComponent } from 'app/entities/csv-content/csv-content-delete-dialog.component';
import { CsvContentService } from 'app/entities/csv-content/csv-content.service';

describe('Component Tests', () => {
  describe('CsvContent Management Delete Component', () => {
    let comp: CsvContentDeleteDialogComponent;
    let fixture: ComponentFixture<CsvContentDeleteDialogComponent>;
    let service: CsvContentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvContentDeleteDialogComponent]
      })
        .overrideTemplate(CsvContentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CsvContentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsvContentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
