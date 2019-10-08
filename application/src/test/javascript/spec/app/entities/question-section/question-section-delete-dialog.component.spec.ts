/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { QuestionSectionDeleteDialogComponent } from 'app/entities/question-section/question-section-delete-dialog.component';
import { QuestionSectionService } from 'app/entities/question-section/question-section.service';

describe('Component Tests', () => {
  describe('QuestionSection Management Delete Component', () => {
    let comp: QuestionSectionDeleteDialogComponent;
    let fixture: ComponentFixture<QuestionSectionDeleteDialogComponent>;
    let service: QuestionSectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionSectionDeleteDialogComponent]
      })
        .overrideTemplate(QuestionSectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionSectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSectionService);
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
