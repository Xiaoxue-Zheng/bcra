/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { IdentifiableDataDeleteDialogComponent } from 'app/entities/identifiable-data/identifiable-data-delete-dialog.component';
import { IdentifiableDataService } from 'app/entities/identifiable-data/identifiable-data.service';

describe('Component Tests', () => {
  describe('IdentifiableData Management Delete Component', () => {
    let comp: IdentifiableDataDeleteDialogComponent;
    let fixture: ComponentFixture<IdentifiableDataDeleteDialogComponent>;
    let service: IdentifiableDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [IdentifiableDataDeleteDialogComponent]
      })
        .overrideTemplate(IdentifiableDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdentifiableDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdentifiableDataService);
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
