export interface IPageIdentifier {
  id?: number;
  identifier?: string;
  display_name?: string;
}

export class PageIdentifier implements IPageIdentifier {
  constructor(public id?: number, public identifier?: string, public display_name?: string) {}
}
