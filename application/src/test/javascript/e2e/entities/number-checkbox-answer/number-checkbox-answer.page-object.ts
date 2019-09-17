import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class NumberCheckboxAnswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-number-checkbox-answer div table .btn-danger'));
  title = element.all(by.css('jhi-number-checkbox-answer div h2#page-heading span')).first();

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

export class NumberCheckboxAnswerUpdatePage {
  pageTitle = element(by.id('jhi-number-checkbox-answer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numberInput = element(by.id('field_number'));
  checkInput = element(by.id('field_check'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  getCheckInput(timeout?: number) {
    return this.checkInput;
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

export class NumberCheckboxAnswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-numberCheckboxAnswer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-numberCheckboxAnswer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
