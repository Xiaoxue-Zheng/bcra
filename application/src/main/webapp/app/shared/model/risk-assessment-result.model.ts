import { IParticipant } from 'app/shared/model/participant.model';
import { IRisk } from 'app/shared/model/risk.model';

export interface IRiskAssessmentResult {
  id?: number;
  filename?: string;
  participant?: IParticipant;
  individualRisk?: IRisk;
  populationRisk?: IRisk;
}

export class RiskAssessmentResult implements IRiskAssessmentResult {
  constructor(
    public id?: number,
    public filename?: string,
    public participant?: IParticipant,
    public individualRisk?: IRisk,
    public populationRisk?: IRisk
  ) {}
}
