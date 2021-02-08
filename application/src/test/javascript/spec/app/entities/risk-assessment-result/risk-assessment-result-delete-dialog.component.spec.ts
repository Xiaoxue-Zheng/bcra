import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RiskAssessmentResultDeleteDialogComponent } from 'app/entities/risk-assessment-result/risk-assessment-result-delete-dialog.component';
import { RiskAssessmentResultService } from 'app/entities/risk-assessment-result/risk-assessment-result.service';

describe('Component Tests', () => {
  describe('RiskAssessmentResult Management Delete Component', () => {
    let comp: RiskAssessmentResultDeleteDialogComponent;
    let fixture: ComponentFixture<RiskAssessmentResultDeleteDialogComponent>;
    let service: RiskAssessmentResultService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RiskAssessmentResultDeleteDialogComponent]
      })
        .overrideTemplate(RiskAssessmentResultDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RiskAssessmentResultDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RiskAssessmentResultService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
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
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
