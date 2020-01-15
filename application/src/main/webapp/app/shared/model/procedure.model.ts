export interface IProcedure {
  id?: number;
  consentResponseId?: number;
  riskAssesmentResponseId?: number;
  participantId?: number;
}

export class Procedure implements IProcedure {
  constructor(
    public id?: number,
    public consentResponseId?: number,
    public riskAssesmentResponseId?: number,
    public participantId?: number
  ) {}
}
