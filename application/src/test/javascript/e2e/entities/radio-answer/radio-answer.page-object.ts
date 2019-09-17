import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RadioAnswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-radio-answer div table .btn-danger'));
  title = element.all(by.css('jhi-radio-answer div h2#page-heading span')).first();

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

export class RadioAnswerUpdatePage {
  pageTitle = element(by.id('jhi-radio-answer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  async getPageTitle() {
    return this.pageTitle.getText();
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

export class RadioAnswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-radioAnswer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-radioAnswer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
