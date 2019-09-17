/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { RadioAnswerItemDeleteDialogComponent } from 'app/entities/radio-answer-item/radio-answer-item-delete-dialog.component';
import { RadioAnswerItemService } from 'app/entities/radio-answer-item/radio-answer-item.service';

describe('Component Tests', () => {
  describe('RadioAnswerItem Management Delete Component', () => {
    let comp: RadioAnswerItemDeleteDialogComponent;
    let fixture: ComponentFixture<RadioAnswerItemDeleteDialogComponent>;
    let service: RadioAnswerItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioAnswerItemDeleteDialogComponent]
      })
        .overrideTemplate(RadioAnswerItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioAnswerItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioAnswerItemService);
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
