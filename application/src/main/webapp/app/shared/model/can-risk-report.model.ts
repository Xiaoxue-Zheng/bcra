import { IStudyId } from 'app/shared/model/study-id.model';
import { IUser } from 'app/core/user/user.model';

export interface ICanRiskReport {
  id?: number;
  filename?: string;
  studyId?: IStudyId;
  uploadedBy?: IUser;
  fileData?: any[];
}

export class CanRiskReport implements ICanRiskReport {
  constructor(public id?: number, public filename?: string, public associatedStudyId?: IStudyId, public uploadedBy?: IUser) {}
}
