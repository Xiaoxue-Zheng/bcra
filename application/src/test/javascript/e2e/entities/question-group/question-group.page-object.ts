import { element, by, ElementFinder } from 'protractor';

export class QuestionGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-group div table .btn-danger'));
  title = element.all(by.css('jhi-question-group div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class QuestionGroupUpdatePage {
  pageTitle = element(by.id('jhi-question-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierSelect = element(by.id('field_identifier'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdentifierSelect(identifier: string): Promise<void> {
    await this.identifierSelect.sendKeys(identifier);
  }

  async getIdentifierSelect(): Promise<string> {
    return await this.identifierSelect.element(by.css('option:checked')).getText();
  }

  async identifierSelectLastOption(): Promise<void> {
    await this.identifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class QuestionGroupDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionGroup-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionGroup'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
