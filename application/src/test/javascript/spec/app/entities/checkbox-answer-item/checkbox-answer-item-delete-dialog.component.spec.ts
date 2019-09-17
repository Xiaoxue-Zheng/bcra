/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerItemDeleteDialogComponent } from 'app/entities/checkbox-answer-item/checkbox-answer-item-delete-dialog.component';
import { CheckboxAnswerItemService } from 'app/entities/checkbox-answer-item/checkbox-answer-item.service';

describe('Component Tests', () => {
  describe('CheckboxAnswerItem Management Delete Component', () => {
    let comp: CheckboxAnswerItemDeleteDialogComponent;
    let fixture: ComponentFixture<CheckboxAnswerItemDeleteDialogComponent>;
    let service: CheckboxAnswerItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerItemDeleteDialogComponent]
      })
        .overrideTemplate(CheckboxAnswerItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxAnswerItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxAnswerItemService);
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
