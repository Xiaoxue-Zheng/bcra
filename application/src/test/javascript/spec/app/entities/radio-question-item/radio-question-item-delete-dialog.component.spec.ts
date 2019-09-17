/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionItemDeleteDialogComponent } from 'app/entities/radio-question-item/radio-question-item-delete-dialog.component';
import { RadioQuestionItemService } from 'app/entities/radio-question-item/radio-question-item.service';

describe('Component Tests', () => {
  describe('RadioQuestionItem Management Delete Component', () => {
    let comp: RadioQuestionItemDeleteDialogComponent;
    let fixture: ComponentFixture<RadioQuestionItemDeleteDialogComponent>;
    let service: RadioQuestionItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionItemDeleteDialogComponent]
      })
        .overrideTemplate(RadioQuestionItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioQuestionItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioQuestionItemService);
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