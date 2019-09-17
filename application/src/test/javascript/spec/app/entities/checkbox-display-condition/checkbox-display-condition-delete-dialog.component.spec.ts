/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { CheckboxDisplayConditionDeleteDialogComponent } from 'app/entities/checkbox-display-condition/checkbox-display-condition-delete-dialog.component';
import { CheckboxDisplayConditionService } from 'app/entities/checkbox-display-condition/checkbox-display-condition.service';

describe('Component Tests', () => {
  describe('CheckboxDisplayCondition Management Delete Component', () => {
    let comp: CheckboxDisplayConditionDeleteDialogComponent;
    let fixture: ComponentFixture<CheckboxDisplayConditionDeleteDialogComponent>;
    let service: CheckboxDisplayConditionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxDisplayConditionDeleteDialogComponent]
      })
        .overrideTemplate(CheckboxDisplayConditionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxDisplayConditionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxDisplayConditionService);
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
