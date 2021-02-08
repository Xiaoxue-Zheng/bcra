export interface IRiskFactor {
  id?: number;
  factor?: number;
}

export class RiskFactor implements IRiskFactor {
  constructor(public id?: number, public factor?: number) {}
}
