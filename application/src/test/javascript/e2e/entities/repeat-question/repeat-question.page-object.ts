import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RepeatQuestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-repeat-question div table .btn-danger'));
  title = element.all(by.css('jhi-repeat-question div h2#page-heading span')).first();

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

export class RepeatQuestionUpdatePage {
  pageTitle = element(by.id('jhi-repeat-question-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  maximumInput = element(by.id('field_maximum'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setMaximumInput(maximum) {
    await this.maximumInput.sendKeys(maximum);
  }

  async getMaximumInput() {
    return await this.maximumInput.getAttribute('value');
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

export class RepeatQuestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-repeatQuestion-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-repeatQuestion'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
