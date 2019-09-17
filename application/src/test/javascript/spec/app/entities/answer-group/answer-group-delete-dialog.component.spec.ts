/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { AnswerGroupDeleteDialogComponent } from 'app/entities/answer-group/answer-group-delete-dialog.component';
import { AnswerGroupService } from 'app/entities/answer-group/answer-group.service';

describe('Component Tests', () => {
  describe('AnswerGroup Management Delete Component', () => {
    let comp: AnswerGroupDeleteDialogComponent;
    let fixture: ComponentFixture<AnswerGroupDeleteDialogComponent>;
    let service: AnswerGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerGroupDeleteDialogComponent]
      })
        .overrideTemplate(AnswerGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerGroupService);
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
