import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from '../entities/questionnaire/questionnaire.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.scss']
})
export class QuestionnaireComponent implements OnInit {
  questionnaires: IQuestionnaire[];
  filter: string;
  orderProp: string;
  reverse: boolean;

  constructor(private questionnaireService: QuestionnaireService, protected jhiAlertService: JhiAlertService) {
    this.filter = '';
    this.orderProp = 'name';
    this.reverse = false;
  }

  loadAll() {
    this.questionnaireService
      .query()
      .pipe(
        filter((res: HttpResponse<IQuestionnaire[]>) => res.ok),
        map((res: HttpResponse<IQuestionnaire[]>) => res.body)
      )
      .subscribe(
        (res: IQuestionnaire[]) => {
          this.questionnaires = this.sortQuestionnaires(res);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  sortQuestionnaires(questionnaires) {
    for (const questionnaire of questionnaires) {
      questionnaire.questionSections.sort((questionSectionA, questionSectionB) => {
        return questionSectionA.order - questionSectionB.order;
      });
      for (const questionSection of questionnaire.questionSections) {
        questionSection.questionGroup.questions.sort((questionA, questionB) => {
          return questionA.order - questionB.order;
        });
        for (const question of questionSection.questionGroup.questions) {
          question.questionItems.sort((questionItemA, questionItemB) => {
            return questionItemA.order - questionItemB.order;
          });
        }
      }
    }
    return questionnaires;
  }

  ngOnInit() {
    this.loadAll();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
