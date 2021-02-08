import { IRiskFactor } from 'app/shared/model/risk-factor.model';

export interface IRisk {
  id?: number;
  lifetimeRisk?: number;
  probNotBcra?: number;
  probBcra1?: number;
  probBcra2?: number;
  riskFactors?: IRiskFactor[];
}

export class Risk implements IRisk {
  constructor(
    public id?: number,
    public lifetimeRisk?: number,
    public probNotBcra?: number,
    public probBcra1?: number,
    public probBcra2?: number,
    public riskFactors?: IRiskFactor[]
  ) {}
}
