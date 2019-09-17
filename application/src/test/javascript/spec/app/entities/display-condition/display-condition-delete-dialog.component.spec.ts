/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { DisplayConditionDeleteDialogComponent } from 'app/entities/display-condition/display-condition-delete-dialog.component';
import { DisplayConditionService } from 'app/entities/display-condition/display-condition.service';

describe('Component Tests', () => {
  describe('DisplayCondition Management Delete Component', () => {
    let comp: DisplayConditionDeleteDialogComponent;
    let fixture: ComponentFixture<DisplayConditionDeleteDialogComponent>;
    let service: DisplayConditionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [DisplayConditionDeleteDialogComponent]
      })
        .overrideTemplate(DisplayConditionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DisplayConditionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisplayConditionService);
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
