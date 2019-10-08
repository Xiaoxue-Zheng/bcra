/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { QuestionItemDeleteDialogComponent } from 'app/entities/question-item/question-item-delete-dialog.component';
import { QuestionItemService } from 'app/entities/question-item/question-item.service';

describe('Component Tests', () => {
  describe('QuestionItem Management Delete Component', () => {
    let comp: QuestionItemDeleteDialogComponent;
    let fixture: ComponentFixture<QuestionItemDeleteDialogComponent>;
    let service: QuestionItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionItemDeleteDialogComponent]
      })
        .overrideTemplate(QuestionItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionItemService);
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
