import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RepeatAnswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-repeat-answer div table .btn-danger'));
  title = element.all(by.css('jhi-repeat-answer div h2#page-heading span')).first();

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

export class RepeatAnswerUpdatePage {
  pageTitle = element(by.id('jhi-repeat-answer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantityInput = element(by.id('field_quantity'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return await this.quantityInput.getAttribute('value');
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

export class RepeatAnswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-repeatAnswer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-repeatAnswer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
