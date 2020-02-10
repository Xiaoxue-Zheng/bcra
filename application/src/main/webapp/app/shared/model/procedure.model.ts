export interface IProcedure {
  id?: number;
  consentResponseId?: number;
  riskAssessmentResponseId?: number;
  participantId?: number;
}

export class Procedure implements IProcedure {
  constructor(
    public id?: number,
    public consentResponseId?: number,
    public riskAssessmentResponseId?: number,
    public participantId?: number
  ) {}
}
