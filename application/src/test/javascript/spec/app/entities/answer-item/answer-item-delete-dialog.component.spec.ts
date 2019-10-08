/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { AnswerItemDeleteDialogComponent } from 'app/entities/answer-item/answer-item-delete-dialog.component';
import { AnswerItemService } from 'app/entities/answer-item/answer-item.service';

describe('Component Tests', () => {
  describe('AnswerItem Management Delete Component', () => {
    let comp: AnswerItemDeleteDialogComponent;
    let fixture: ComponentFixture<AnswerItemDeleteDialogComponent>;
    let service: AnswerItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerItemDeleteDialogComponent]
      })
        .overrideTemplate(AnswerItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerItemService);
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
