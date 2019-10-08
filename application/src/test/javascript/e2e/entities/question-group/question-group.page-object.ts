import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-group div table .btn-danger'));
  title = element.all(by.css('jhi-question-group div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class QuestionGroupUpdatePage {
  pageTitle = element(by.id('jhi-question-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  identifierSelect = element(by.id('field_identifier'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setIdentifierSelect(identifier) {
    await this.identifierSelect.sendKeys(identifier);
  }

  async getIdentifierSelect() {
    return await this.identifierSelect.element(by.css('option:checked')).getText();
  }

  async identifierSelectLastOption(timeout?: number) {
    await this.identifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class QuestionGroupDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionGroup-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionGroup'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
